
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.ConfigurationSystem;
import domain.Route;

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


	/* TESTS */

	/*
	 * List the comments that contain taboo words.
	 */

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
