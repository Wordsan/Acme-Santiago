
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Route;
import domain.User;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RouteServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private RouteService routeService;

	/* REQUIRED SERVICES */
	@Autowired
	private UserService userService;

	/* TESTS */
	/*
	 * Functional requirements under test:
	 * 3.2 - Browse the routes.
	 * 3.3 - Search for routes using a single key word that must appear
	 * somewhere in their names, their descriptions, or their hikes.
	 * 3.4 - Search for routes whose length is in a user-provided range.
	 * 3.5 - Search for routes that have a minimum or a maximum number of hikes.
	 * 5.1 - Manage his or her routes, which includes creating, editing, deleting, and listing them.
	 * 6.1 - Remove a route that he or she thinks is inappropriate. Removing a route involves removing all
	 * of the hikes of which it is composed.
	 */

	/*
	 * Testing:
	 * 3.2 - Browse the routes.
	 * 3.3 - Search for routes using a single key word that must appear
	 * somewhere in their names, their descriptions, or their hikes.
	 * 3.4 - Search for routes whose length is in a user-provided range.
	 * 3.5 - Search for routes that have a minimum or a maximum number of hikes.
	 */
	protected void templateBrowse(final String authenticate, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(authenticate);
			Assert.isTrue(!this.routeService.findAll().isEmpty());
			Assert.isTrue(this.routeService.searchRoutesFromKeyWord("te1").size() == 11);
			Assert.isTrue(this.routeService.routesByLengthRange(21500.0, 23000.0).size() == 29);
			Assert.isTrue(this.routeService.routesByHikesSize().size() == 51);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestBrowse() {
		final Object testingData[][] = { { null, null }, // Successful
				{ "user1", null }, // Successful
				{ "admin", null }, // Successful
		};
		for (Object[] element : testingData) {
			this.templateBrowse((String) element[0], (Class<?>) element[1]);
		}
	}

	/*
	 * Testing:
	 * 5.1 - Manage his or her routes, which includes creating, editing, deleting, and listing them.
	 */
	protected void templateManageRoutes(final String authenticate, String nameCreate, Double lengthCreate,
			String descriptionCreate, String picturesCreate, String nameEdit, Double lengthEdit, String descriptionEdit,
			String picturesEdit, final Class<?> expected) {
		Class<?> caught;
		User user;
		Route route;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			this.routeService.routesFromCreator(user.getId());
			route = this.routeService.create();
			route.setName(nameCreate);
			route.setDescription(descriptionCreate);
			route.setLength(lengthCreate);
			route.setPictures(picturesCreate);
			route = this.routeService.save(route);
			this.routeService.flush();
			Assert.isTrue(this.routeService.routesFromCreator(user.getId()).contains(route));
			route.setName(nameEdit);
			route.setDescription(descriptionEdit);
			route.setLength(lengthEdit);
			route.setPictures(picturesEdit);
			route = this.routeService.save(route);
			this.routeService.flush();
			Assert.isTrue(this.routeService.routesFromCreator(user.getId()).contains(route));
			this.routeService.delete(route);
			this.routeService.flush();
			Assert.isTrue(!this.routeService.routesFromCreator(user.getId()).contains(route));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestManageRoutes() {
		final Object testingData[][] = {
				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0,
						"test 2 route", "http://google.com", null }, // Successful

				{ null, "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0, "test 2 route",
						"http://google.com", IllegalArgumentException.class }, // Failed -> not logged

				{ "admin", "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0,
						"test 2 route", "http://google.com", IllegalArgumentException.class }, // Failed -> administrator logged

				{ "user1", null, null, null, null, "test 2 route", 1200.0, "test 2 route", "http://google.com",
						ConstraintViolationException.class }, // Failed -> create data empty

				{ "user1", "test route", 1000.0, "test route", "http://google.es", null, null, null, null,
						ConstraintViolationException.class }, // Failed -> edit data empty

				{ "user1", null, 1000.0, "test route", "http://google.es", "test 2 route", 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> create name empty

				{ "user1", "test route", null, "test route", "http://google.es", "test 2 route", 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> create length empty

				{ "user1", "test route", -1250.0, "test route", "http://google.es", "test 2 route", 1200.0,
						"test 2 route", "http://google.com", ConstraintViolationException.class }, // Failed -> create length invalid

				{ "user1", "test route", 1000.0, null, "http://google.es", "test 2 route", 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> create  description empty

				{ "user1", "test route", 1000.0, "test route", null, "test 2 route", 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> create  pictures empty

				{ "user1", "test route", 1000.0, "test route", "google.es", "test 2 route", 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> create  pictures invalid

				{ "user1", "test route", 1000.0, "test route", "http://google.es", null, 1200.0, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> edit name  empty

				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", null, "test 2 route",
						"http://google.com", ConstraintViolationException.class }, // Failed -> edit length  empty

				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", -1200.0,
						"test 2 route", "http://google.com", ConstraintViolationException.class }, // Failed -> edit length invalid

				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0, null,
						"http://google.com", ConstraintViolationException.class }, // Failed -> edit description  empty

				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0,
						"test 2 route", null, ConstraintViolationException.class }, // Failed -> edit pictures  empty

				{ "user1", "test route", 1000.0, "test route", "http://google.es", "test 2 route", 1200.0,
						"test 2 route", "htt:gle.com", ConstraintViolationException.class }, // Failed -> edit pictures invalid

		};
		for (Object[] element : testingData) {
			this.templateManageRoutes((String) element[0], (String) element[1], (Double) element[2],
					(String) element[3], (String) element[4], (String) element[5], (Double) element[6],
					(String) element[7], (String) element[8], (Class<?>) element[9]);
		}
	}

	/*
	 * Testing:
	 * 5.1 - Manage his or her routes, which includes creating, editing, deleting, and listing them. (only delete)
	 * 6.1 - Remove a route that he or she thinks is inappropriate. Removing a route involves removing
	 * all of the hikes of which it is composed.
	 */
	protected void templateDeleteRoute(final String authenticate, String routeBeanId, final Class<?> expected) {
		Class<?> caught;
		Route route;

		caught = null;
		try {
			this.authenticate(authenticate);
			route = this.routeService.findOne(this.getEntityId(routeBeanId));
			this.routeService.delete(route);
			Assert.isTrue(!this.routeService.findAll().contains(route));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteRoute() {
		final Object testingData[][] = { { "user2", "route-1", null }, // Successful
				{ "admin", "route-3", null }, // Successful
				{ null, "route-5", IllegalArgumentException.class }, // Failed -> not logged
				{ "user1", "route-2", IllegalArgumentException.class }, // Failed -> user isn't the owner
		};
		for (Object[] element : testingData) {
			this.templateDeleteRoute((String) element[0], (String) element[1], (Class<?>) element[2]);
		}
	}
}
