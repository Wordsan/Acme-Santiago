
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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

	/*
	 * Post a chirp. Chirps may not be changed or deleted once they are posted.
	 */

	@Test
	public void test15_1() {
		this.authenticate("user1");
		final User user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());

		Collection<Chirp> all = new ArrayList<>();
		Chirp saved;
		final Chirp chirp = this.chirpService.create();
		chirp.setDescription("description");
		chirp.setPostMoment(new Date());
		chirp.setTitle("title");
		chirp.setUser(user);

		saved = this.chirpService.save(chirp);
		all = this.chirpService.findAll();
		Assert.isTrue(all.contains(saved));

		Assert.isTrue(user.getChirps().contains(saved));

		saved.setDescription("It can't be edited.");

		try {
			this.chirpService.save(saved);
			throw new RuntimeException();
		} catch (final IllegalArgumentException i) {

		}

		try {
			this.chirpService.delete(saved);
			throw new RuntimeException();
		} catch (final IllegalArgumentException i) {

		}
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
