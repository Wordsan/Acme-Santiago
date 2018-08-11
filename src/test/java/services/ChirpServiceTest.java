
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Chirp;
import domain.ConfigurationSystem;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChirpServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private ChirpService				chirpService;
	@Autowired
	private UserService					userService;
	@Autowired
	private ConfigurationSystemService	csService;


	/* TESTS */

	@Test
	public void driver15_1() {
		final String[] properties = {
			"11/08/2018 20:20, , ", "01/01/3000,description,title", "11/08/2018 20:20,description1, title1"
		};
		final Object testingData[][] = {
			{
				"user1", properties[0], ConstraintViolationException.class
			}, {
				"user1", properties[1], java.text.ParseException.class
			}, {
				"admin1", properties[2], IllegalArgumentException.class
			}, {
				"user2", properties[2], null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template15_1((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * An actor who is authenticated as a user must be able to:
	 * Post a chirp. Chirps may not be changed or deleted once they are posted.
	 */

	protected void template15_1(final String authenticate, final String properties, final Class<?> expected) {
		Class<?> caught;
		String[] split;
		caught = null;
		try {
			this.authenticate(authenticate);
			final User user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());

			Collection<Chirp> all = new ArrayList<>();
			Chirp saved;
			final Chirp chirp = this.chirpService.create();
			split = properties.split(",");
			final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			chirp.setPostMoment(formater.parse(split[0]));
			chirp.setDescription(split[1]);
			chirp.setTitle(split[2]);
			chirp.setUser(user);

			saved = this.chirpService.save(chirp);
			all = this.chirpService.findAll();
			Assert.isTrue(all.contains(saved));

			Assert.isTrue(user.getChirps().contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * An actor who is authenticated as a user must be able to:
	 * Post a chirp. Chirps may not be changed or deleted once they are posted.
	 */

	@Test
	public void driver15_1_1() {
		final Object testingData[][] = {
			{
				"user1", "chirp1", null
			}, {
				"user2", "chirp2", null
			}, {
				"admin1", "chirp3", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.template15_1_1((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.template15_1_2((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void template15_1_1(final String authenticate, final String chirp, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Chirp c;
		try {
			this.authenticate(authenticate);
			c = this.chirpService.findOne(this.getEntityId(chirp));
			this.chirpService.save(c);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template15_1_2(final String authenticate, final String chirp, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Chirp c;
		try {
			this.authenticate(authenticate);
			c = this.chirpService.findOne(this.getEntityId(chirp));
			this.chirpService.delete(c);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * List the chirps that contain taboo words.
	 */

	@Test
	public void test16_2() {
		this.authenticate("admin1");
		final ConfigurationSystem cs = this.csService.get();
		cs.setTabooWords("yupi");
		this.csService.save(cs);

		this.authenticate("user1");
		final User user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		Chirp chirp, saved;
		chirp = this.chirpService.create();
		chirp.setDescription("yupi");
		chirp.setPostMoment(new Date());
		chirp.setTitle("title");
		chirp.setUser(user);

		saved = this.chirpService.save(chirp);
		Assert.isTrue(this.chirpService.tabooChirps().contains(saved));
	}

	/*
	 * Remove a chirp that he or she thinks is inappropriate.
	 */

	@Test
	public void test16_3() {
		this.authenticate("user1");
		final Chirp chirp = this.chirpService.findOne(this.getEntityId("chirp_1"));
		try {
			this.chirpService.delete(chirp);
			throw new RuntimeException();
		} catch (final IllegalArgumentException i) {

		}

		this.authenticate("admin1");
		this.chirpService.delete(chirp);
		Assert.isTrue(!(this.chirpService.findAll().contains(chirp)));
	}
}
