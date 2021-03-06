
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.ConfigurationSystem;
import domain.Hike;
import domain.Route;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private CommentService				commentService;
	@Autowired
	private ConfigurationSystemService	csService;
	@Autowired
	private RouteService				routeService;
	@Autowired
	private UserService					userService;
	@Autowired
	private HikeService					hikeService;
	@PersistenceContext
	private EntityManager				entityManager;


	/* TESTS */

	/*
	 * 16. An actor who is authenticated as an administrator must be able to:
	 * 4. List the comments that contain taboo words.
	 */

	@Test
	public void driver16_4() {
		final Object testingData[][] = {
			{
				"user1", "comment_1", IllegalArgumentException.class
			//Failed -> Principal isn't an admin
			}, {
				"admin1", "comment_1", null
			//Successful
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template16_4((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), (Class<?>) testingData[i][2]);
	}

	protected void template16_4(final String authenticate, final int commentId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Comment c;
		try {
			c = this.commentService.findOne(commentId);
			this.authenticate("admin1");
			final ConfigurationSystem cs = this.csService.get();
			cs.setTabooWords(c.getText());
			this.csService.save(cs);

			this.authenticate(authenticate);
			Assert.isTrue(this.commentService.tabooComments().contains(c));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Create CRUD
	 */

	@Test
	public void driverCreateComment() {

		final String[] properties = {
			"11/08/2018 20:20, , , , ", "11/08/2018, , , ,0", "11/08/2018 20:20,title,text,URL,0", "11/08/2018 20:20,title,text,https://www.url.es/,0", "11/08/2018 20:20,title,text,https://www.url.es/,0",
			"11/08/2018 20:20,title,text,https://www.url.es/,2", "11/08/2018 20:20,title,text,https://www.url.es/,3", "11/08/2018 20:20, , , ,0"
		};
		final Object testingData[][] = {
			{
				//
				"user1", "user_user1", "route_3", "", properties[0], NumberFormatException.class
			//Failed -> Range is null
			}, {
				"user1", "user_user1", "route_3", "", properties[7], ConstraintViolationException.class
			//Failed -> Title, Text and URL are null
			}, {
				"user1", "user_user1", "route_3", "", properties[1], java.text.ParseException.class
			//Failed -> Moment is invalid
			}, {
				"user1", "user_user1", "route_3", "", properties[2], ConstraintViolationException.class
			//Failed -> URL is invalid 
			}, {
				"user1", "user_user1", "route_3", "", properties[4], null
			//Trying the range = 0
			}, {
				"user1", "user_user1", "route_3", "", properties[5], null
			//Trying the range = 2
			}, {
				"user1", "user_user1", "route_3", "", properties[6], null
			//Trying the range = 3
			}, {
				"admin1", "user_user1", "route_3", "", properties[4], IllegalArgumentException.class
			//Principal isn't a user
			}, {
				"user2", "user_user2", "route_3", "", properties[4], null
			//Creating a comment in a route that it isn't of her/him property.
			}, {
				"user2", "user_user2", "", "hike_5", properties[4], null
			//Creating a comment in a hike that it isn't of her/him property.
			}, {
				"user1", "user_user1", "route_1", "", properties[6], null
			//Creating a comment in a route that it isn't of her/him property.
			}, {
				"user1", "user_user1", "", "hike_1", properties[6], null
			//Creating a comment in a hike that it isn't of her/him property.
			}, {
				"user1", "user_user1", "route_3", "hike_5", properties[4], ConstraintViolationException.class
			//Failed -> It has a hike and a route.
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateComment((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void templateCreateComment(final String authenticate, final int ownerId, final String routeName, final String hikeName, final String properties, final Class<?> expected) {
		Class<?> caught;
		String[] split;
		caught = null;
		try {
			this.authenticate(authenticate);

			Collection<Comment> all = new ArrayList<>();
			Comment saved;
			final Comment comment = this.commentService.create(null, null);
			split = properties.split(",");
			final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			comment.setWriteMoment(formater.parse(split[0]));
			comment.setTitle(split[1]);
			comment.setText(split[2]);
			comment.setPictures(split[3]);
			comment.setRate(new Integer(split[4]));
			final User owner = this.userService.findOne(ownerId);
			comment.setOwner(owner);
			if (routeName != "") {
				final int routeId = this.getEntityId(routeName);
				final Route route = this.routeService.findOne(routeId);
				comment.setRoute(route);
			}
			if (hikeName != "") {
				final int hikeId = this.getEntityId(hikeName);
				final Hike hike = this.hikeService.findOne(hikeId);
				comment.setHike(hike);
			}

			saved = this.commentService.save(comment);
			this.commentService.flush();
			all = this.commentService.findAll();
			Assert.isTrue(all.contains(saved));

			Assert.isTrue(owner.getHikeComments().contains(saved) || owner.getRouteComments().contains(saved));

			if (routeName != "")
				Assert.isTrue(comment.getRoute().getComments().contains(saved));
			if (hikeName != "")
				Assert.isTrue(comment.getHike().getComments().contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			try {
				this.entityManager.flush();
			} catch (final Exception ignored) {
			}
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 16. An actor who is authenticated as an administrator must be able to:
	 * 5. Remove a comment that he or she thinks is inappropriate.
	 */

	@Test
	public void driver16_5() {
		final Object testingData[][] = {
			{
				"user1", "comment_1", IllegalArgumentException.class
			//Failed -> Principal isn't a user
			}, {
				"admin1", "comment_1", null
			//Successful
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template16_5((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), (Class<?>) testingData[i][2]);
	}

	protected void template16_5(final String authenticate, final int commentId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Comment c;
		try {
			this.authenticate(authenticate);
			c = this.commentService.findOne(commentId);
			this.commentService.delete(c);
			Assert.isTrue(!(this.commentService.findAll().contains(c)));
			Assert.isTrue(!(c.getOwner().getRouteComments().contains(c) || c.getOwner().getHikeComments().contains(c)));
			if (c.getHike() != null)
				Assert.isTrue(!(c.getHike().getComments().contains(c)));
			else
				Assert.isTrue(!(c.getRoute().getComments().contains(c)));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
