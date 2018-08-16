
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Hike;
import domain.Route;
import domain.User;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class HikeServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private HikeService hikeService;

	/* REQUIRED SERVICES */
	@Autowired
	private UserService userService;

	@Autowired
	private RouteService routeService;

	/* TESTS */
	/*
	 * Functional requirements under test:
	 * 5.1 - Manage his or her routes, which includes creating, editing, deleting, and listing them.
	 */

	/*
	 * Testing:
	 * 5.1 - Manage his or her routes, which includes creating, editing, deleting, and listing them. (hikes part)
	 */
	protected void templateManageHikes(final String authenticate, String routeBeanId, String name,
			String difficultyLevel, Double length, String description, String pictures, String originCity,
			String destinationCity, final Class<?> expected) {
		Class<?> caught;
		User user;
		Route route;
		Hike hike;
		int hikesLength;

		caught = null;
		try {
			this.authenticate(authenticate);
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
			route = this.routeService.findOne(this.getEntityId(routeBeanId));
			hike = this.hikeService.create(route);
			hike.setName(name);
			hike.setDescription(description);
			hike.setDifficultyLevel(difficultyLevel);
			hike.setLength(length);
			hike.setPictures(pictures);
			hike.setOriginCity(originCity);
			hike.setDestinationCity(destinationCity);
			hike = this.hikeService.save(hike);
			this.hikeService.flush();
			hikesLength = route.getComposedHikes().size();
			route = this.routeService.findOne(this.getEntityId(routeBeanId));
			Assert.isTrue(route.getComposedHikes().contains(hike));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestManageHikes() {
		final Object testingData[][] = {
				{ "user1", "route-3", "test hike", "EASY", 1000.0, "test route", "http://google.es", "c1", "c2", null }, // Successful

				{ "user2", "route-3", "test hike", "EASY", 1000.0, "test route", "http://google.es", "c1", "c2",
						IllegalArgumentException.class }, // Failed -> user isn't the owner of the route

				{ null, "route-3", "test hike", "EASY", 1000.0, "test route", "http://google.es", "c1", "c2",
						IllegalArgumentException.class }, // Failed -> not logged

				{ "user1", "route-3", null, "EASY", 1000.0, "test route", "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> name empty

				{ "user1", "route-3", "test hike", null, 1000.0, "test route", "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> difficulty level empty

				{ "user1", "route-3", "test hike", "ASDA", 1000.0, "test route", "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> difficulty level invalid

				{ "user1", "route-3", "test hike", "ASDA", -10.0, "test route", "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> length invalid

				{ "user1", "route-3", "test hike", "ASDA", null, "test route", "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> length empty

				{ "user1", "route-3", "test hike", "ASDA", 10000.0, null, "http://google.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> description empty

				{ "user1", "route-3", "test hike", "ASDA", 10000.0, "test hike", null, "c1", "c2",
						ConstraintViolationException.class }, // Failed -> pictures empty

				{ "user1", "route-3", "test hike", "ASDA", 10000.0, "test hike", "httpgoogle.es", "c1", "c2",
						ConstraintViolationException.class }, // Failed -> pictures invalid

				{ "user1", "route-3", "test hike", "ASDA", 10000.0, "test hike", "http://google.es", null, "c2",
						ConstraintViolationException.class }, // Failed -> origin city empty

				{ "user1", "route-3", "test hike", "ASDA", 10000.0, "test hike", "http://google.es", "c1", null,
						ConstraintViolationException.class }, // Failed -> destination city empty

		};
		for (Object[] element : testingData) {
			this.templateManageHikes((String) element[0], (String) element[1], (String) element[2], (String) element[3],
					(Double) element[4], (String) element[5], (String) element[6], (String) element[7],
					(String) element[8], (Class<?>) element[9]);
		}
	}

}
