
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
			}, {
				"admin1", "comment_1", null
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
			"11/08/2018 20:20, , , , ", "11/08/2018, , , , ", "11/08/2018 20:20,title,text,URL, ", "11/08/2018 20:20,title,text,https://www.url.es/, ", "11/08/2018 20:20,title,text,https://www.url.es/,0",
			"11/08/2018 20:20,title,text,https://www.url.es/,2", "11/08/2018 20:20,title,text,https://www.url.es/,3"
		};
		final Object testingData[][] = {
			{//Probando las properties
				"user1", "user1", "route_1", "", properties[0], ConstraintViolationException.class
			}, {
				"user1", "user1", "route_1", "", properties[1], java.text.ParseException.class
			}, {
				"user1", "user1", "route_1", "", properties[2], ConstraintViolationException.class
			}, {
				"user1", "user1", "route_1", "", properties[3], ConstraintViolationException.class
			}, {//Probando el range
				"user1", "user1", "route_1", "", properties[4], null
			}, {
				"user1", "user1", "route_1", "", properties[5], null
			}, {
				"user1", "user1", "route_1", "", properties[6], null
			}, {//Autenticado no es user
				"admin1", "user1", "route_1", "", properties[4], IllegalArgumentException.class
			}, {//Creador del comment no es el mismo que el de la route
				"user2", "user1", "route_1", "", properties[4], IllegalArgumentException.class
			}, {//Creador del comment no es el mismo que el del hike
				"user2", "user1", "", "hike_1", properties[4], IllegalArgumentException.class
			}, {//La ruta no es suya
				"user1", "user1", "route_8", "", properties[6], IllegalArgumentException.class
			}, {//El hike no es suyo 
				"user1", "user1", "", "hike_12", properties[6], IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateComment((String) testingData[i][0], this.getEntityId(testingData[i][1].toString()), this.getEntityId(testingData[i][2].toString()), this.getEntityId(testingData[i][3].toString()), (String) testingData[i][4],
				(Class<?>) testingData[i][2]);
	}

	protected void templateCreateComment(final String authenticate, final int ownerId, final int routeId, final int hikeId, final String properties, final Class<?> expected) {
		Class<?> caught;
		String[] split;
		caught = null;
		try {
			this.authenticate(authenticate);

			Collection<Comment> all = new ArrayList<>();
			Comment saved;
			final Comment comment = this.commentService.create();
			split = properties.split(",");
			final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			comment.setWriteMoment(formater.parse(split[0]));
			comment.setTitle(split[1]);
			comment.setText(split[2]);
			comment.setPictures(split[3]);
			comment.setRate(new Integer(split[4]));
			final User owner = this.userService.findOne(ownerId);
			comment.setOwner(owner);
			final Route route = this.routeService.findOne(routeId);
			final Hike hike = this.hikeService.findOne(hikeId);
			if (route != null)
				comment.setRoute(route);
			if (hike != null)
				comment.setHike(hike);

			saved = this.commentService.save(comment);
			all = this.commentService.findAll();
			Assert.isTrue(all.contains(saved));

			Assert.isTrue(owner.getHikeComments().contains(saved) || owner.getRouteComments().contains(saved));

			if (route != null)
				Assert.isTrue(route.getComments().contains(saved));
			if (hike != null)
				Assert.isTrue(hike.getComments().contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void test16_4() {
		this.authenticate("admin1");
		final ConfigurationSystem cs = this.csService.get();
		cs.setTabooWords("yupi");
		this.csService.save(cs);

		this.authenticate("user2");
		Comment comment, saved;
		comment = this.commentService.create();
		comment.setTitle("title");
		comment.setText("yupi");
		comment.setPictures("https://www.pic.com/");
		comment.setRate(1);
		final Route r = this.routeService.findOne(this.getEntityId("route_81"));
		comment.setRoute(r);

		saved = this.commentService.save(comment);
		Assert.isTrue(saved.getOwner().getRouteComments().contains(saved));
		Assert.isTrue(saved.getRoute().getComments().contains(saved));
		Assert.isTrue(this.commentService.tabooComments().contains(saved));
	}

	/*
	 * Remove a comment that he or she thinks is inappropriate.
	 */

	@Test
	public void test16_5() {
		this.authenticate("user2");
		final Comment comment = this.commentService.findOne(this.getEntityId("comment_1"));
		try {
			this.commentService.delete(comment);
			throw new RuntimeException();
		} catch (final IllegalArgumentException i) {

		}

		this.authenticate("admin1");
		this.commentService.delete(comment);
		Assert.isTrue(!(this.commentService.findAll().contains(comment)));
		Assert.isTrue(!(comment.getOwner().getRouteComments().contains(comment)));
		Assert.isTrue(!(comment.getRoute().getComments().contains(comment)));
	}

}
