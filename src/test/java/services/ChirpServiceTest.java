
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

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
	 * 15.1:
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
			this.chirpService.flush();
			all = this.chirpService.findAll();
			Assert.isTrue(all.contains(saved));

			Assert.isTrue(user.getChirps().contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 15.1:
	 * An actor who is authenticated as a user must be able to:
	 * Post a chirp. Chirps may not be changed or deleted once they are posted.
	 */

	@Test
	public void driver15_1_1() {
		final Object testingData[][] = {
			{
				"user1", "chirp_1", IllegalArgumentException.class
			}, {
				"user2", "chirp_1", IllegalArgumentException.class
			}, {
				"admin1", "chirp_1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template15_1_1((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driver15_1_2() {
		final Object testingData[][] = {
			{
				"user1", "chirp_1", IllegalArgumentException.class
			}, {
				"user2", "chirp_1", IllegalArgumentException.class
			}, {
				"admin1", "chirp_1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template15_1_2((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
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
	 * 16. An actor who is authenticated as an administrator must be able to:
	 * 2. List the chirps that contain taboo words.
	 */

	@Test
	public void driver16_2() {
		final Object testingData[][] = {
			{
				"user1", "chirp_1", IllegalArgumentException.class
			}, {
				"admin1", "chirp_1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template16_2((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), (Class<?>) testingData[i][2]);
	}

	protected void template16_2(final String authenticate, final int chirpId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Chirp c;
		try {
			c = this.chirpService.findOne(chirpId);
			this.authenticate("admin1");
			final ConfigurationSystem cs = this.csService.get();
			cs.setTabooWords(c.getDescription());
			this.csService.save(cs);

			this.authenticate(authenticate);
			Assert.isTrue(this.chirpService.tabooChirps().contains(c));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 16. An actor who is authenticated as an administrator must be able to:
	 * 3. Remove a chirp that he or she thinks is inappropriate.
	 */

	@Test
	public void driver16_3() {
		final Object testingData[][] = {
			{
				"user1", "chirp_1", IllegalArgumentException.class
			}, {
				"admin1", "chirp_1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template16_3((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), (Class<?>) testingData[i][2]);
	}

	protected void template16_3(final String authenticate, final int chirpId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Chirp c;
		try {
			this.authenticate(authenticate);
			c = this.chirpService.findOne(chirpId);
			this.chirpService.delete(c);
			Assert.isTrue(!(this.chirpService.findAll().contains(c)));
			Assert.isTrue(!c.getUser().getChirps().contains(c));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
