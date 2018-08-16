
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.User;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class UserServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private UserService userService;

	/* TESTS */
	/*
	 * Functional requirements under test:
	 * 3.1 - Register to the system as a user.
	 * 3.6 - Browse the users of the system and their profiles, which must include
	 * their personal data and the list of routes that they have registered.
	 * 5.2 - Follow/unfollow other users.
	 * 5.3 - Browse the users who he or she follows.
	 * 5.4 - Browse the users who follow him or her.
	 */

	/*
	 * Testing:
	 * 3.1 - Register to the system as a user.
	 */
	protected void templateSignin(final String authenticate, final String username, String postalAddress,
			String emailAddress, String phone, String picture, final Class<?> expected) {
		Class<?> caught;
		User user;
		Md5PasswordEncoder encoder;

		caught = null;
		encoder = new Md5PasswordEncoder();
		try {
			this.authenticate(authenticate);
			user = this.userService.create();
			user.getUserAccount().setUsername(username);
			user.getUserAccount().setPassword(encoder.encodePassword(username, null));
			user.setName(username);
			user.setSurname(username);
			user.setPhoneNumber(phone);
			user.setEmailAddress(emailAddress);
			user.setPostalAddress(postalAddress);
			user.setPicture(picture);

			this.userService.save(user);
			this.userService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestSignin() {
		final Object testingData[][] = {
				{ null, "userTest1", "test address", "testEmail1@acme.com", "+11111111", "http://testimage1.com",
						null }, // Successful,
				{ null, "userTest2", "test address", "testEmail1@acme.com", null, "http://testimage1.com", null }, // Successful
				{ "user1", "userTest2", "test address", "testEmail1@acme.com", "11111111", null,
						IllegalArgumentException.class }, // Failed -> already logged as an user
				{ "admin", "userTest3", "test address", "testEmail1@acme.com", null, null,
						IllegalArgumentException.class }, // Failed -> already logged as an administrator
				{ null, "user1", "test address", "testEmail1@acme.com", null, "http://testimage1.com",
						DataIntegrityViolationException.class } // Failed -> username already exists
		};
		for (Object[] element : testingData) {
			this.templateSignin((String) element[0], (String) element[1], (String) element[2], (String) element[3],
					(String) element[4], (String) element[5], (Class<?>) element[6]);
		}
	}

	/*
	 * Testing:
	 * 3.6 - Browse the users of the system and their profiles,
	 * which must include their personal data and the list of routes that they have registered.
	 */
	protected void templateBrowseAndDisplay(final String authenticate, final int userId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(authenticate);
			this.userService.findAll();
			this.userService.findOne(userId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestBrowseAndDisplay() {
		final Object testingData[][] = { { null, this.getEntityId("user-user1"), null }, // Successful
				{ "user1", this.getEntityId("user-user1"), null }, // Successful
				{ "admin", this.getEntityId("user-user1"), null }, // Successful
				{ null, 0, IllegalArgumentException.class }, // Failed -> user doesn't exists
		};
		for (Object[] element : testingData) {
			this.templateBrowseAndDisplay((String) element[0], (int) element[1], (Class<?>) element[2]);
		}
	}

	/*
	 * Testing:
	 * 5.3 - Browse the users who he or she follows.
	 */
	protected void templateBrowseFollowedUsers(final String authenticate, final Class<?> expected) {
		Class<?> caught;
		User user;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			this.userService.usersThatFollowsMe(user.getId());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestBrowseFollowedUsers() {
		final Object testingData[][] = { { "user1", null }, // Successful
				{ "admin", IllegalArgumentException.class }, // Failed -> administrator logged
				{ null, IllegalArgumentException.class }, // Failed -> not logged
		};
		for (Object[] element : testingData) {
			this.templateBrowseFollowedUsers((String) element[0], (Class<?>) element[1]);
		}
	}

	/*
	 * Testing:
	 * 5.4 - Browse the users who follow him or her.
	 */
	protected void templateBrowseFollowerUsers(final String authenticate, final Class<?> expected) {
		Class<?> caught;
		User user;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			this.userService.usersThatFollowsMe(user.getId());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestBrowseFollowerUsers() {
		final Object testingData[][] = { { "user1", null }, // Successful
				{ "admin", IllegalArgumentException.class }, // Failed -> administrator logged
				{ null, IllegalArgumentException.class }, // Failed -> not logged
		};
		for (Object[] element : testingData) {
			this.templateBrowseFollowerUsers((String) element[0], (Class<?>) element[1]);
		}
	}

	/*
	 * Testing:
	 * 5.2 - Follow/unfollow other users. (only follow)
	 */
	protected void templateFollowUser(final String authenticate, int userToFollowId, final Class<?> expected) {
		Class<?> caught;
		User user, userToFollow;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			userToFollow = this.userService.findOne(userToFollowId);
			this.userService.follow(userToFollowId);
			Assert.isTrue(this.userService.usersThatFollowsMe(user.getId()).contains(userToFollow));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestFollowUser() {
		final Object testingData[][] = { { "user1", this.getEntityId("user-user2"), null }, // Successful
				{ null, this.getEntityId("user-user2"), IllegalArgumentException.class }, // Failed -> not logged
				{ "admin", this.getEntityId("user-user2"), IllegalArgumentException.class }, // Successful
				{ "user1", 0, IllegalArgumentException.class }, // Failed -> user doesn't exists
		};
		for (Object[] element : testingData) {
			this.templateFollowUser((String) element[0], (int) element[1], (Class<?>) element[2]);
		}
	}

	/*
	 * Testing:
	 * 5.2 - Follow/unfollow other users. (only unfollow)
	 */
	protected void templateUnfollowUser(final String authenticate, int userToFollowId, final Class<?> expected) {
		Class<?> caught;
		User user, userToFollow;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			userToFollow = this.userService.findOne(userToFollowId);
			this.userService.unfollow(userToFollowId);
			Assert.isTrue(!this.userService.usersThatFollowsMe(user.getId()).contains(userToFollow));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestUnfollowUser() {
		final Object testingData[][] = { { "user2", this.getEntityId("user-user1"), null }, // Successful
				{ null, this.getEntityId("user-user1"), IllegalArgumentException.class }, // Failed -> not logged
				{ "admin", this.getEntityId("user-user1"), IllegalArgumentException.class }, // Successful
				{ "user1", 0, IllegalArgumentException.class }, // Failed -> user doesn't exists
		};
		for (Object[] element : testingData) {
			this.templateUnfollowUser((String) element[0], (int) element[1], (Class<?>) element[2]);
		}
	}

}
