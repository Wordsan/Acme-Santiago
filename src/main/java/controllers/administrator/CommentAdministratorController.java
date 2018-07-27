
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import utilities.ControllerUtils;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("comment/admin")
public class CommentAdministratorController extends AbstractController {

	/* SERVICES */
	@Autowired
	private CommentService	commentService;


	/* CONSTRUCTORS */
	public CommentAdministratorController() {
		super();
	}

	/* LISTING */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Comment> comments;
		comments = this.commentService.tabooComments();

		result = new ModelAndView("comment/tabooComments");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/admin/list.do");

		return result;
	}

	/* EDITION */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {
		ModelAndView result;

		try {
			final Comment comment = this.commentService.findOne(commentId);
			this.commentService.delete(comment);
			result = ControllerUtils.redirect("/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "comment.commit.error");
		}

		return result;
	}

}
