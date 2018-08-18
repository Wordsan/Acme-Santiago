
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RouteService;
import utilities.ControllerUtils;
import controllers.AbstractController;
import domain.Comment;
import domain.Hike;
import domain.Route;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	/* SERVICES */
	@Autowired
	private CommentService	commentService;
	@Autowired
	private RouteService	routeService;


	/* CONSTRUCTORS */
	public CommentUserController() {
		super();
	}

	/* CREATION */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.create();
		result = this.createEditModelAndView(comment);
		result.addObject("requestURI", "comment/user/edit.do");

		return result;
	}

	/* EDITION */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment, final BindingResult binding) {
		ModelAndView result;

		comment = this.commentService.reconstruct(comment, binding);

		if (binding.hasErrors())
			if (comment.isValidHasEitherRouteOrHike() == false)
				result = this.createEditModelAndView(comment, "comment1.commit.error");
			else
				result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = ControllerUtils.redirect("/welcome/index.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}

		return result;
	}
	/* ANCILLARY METHODS */
	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;
		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode) {
		ModelAndView result;
		Collection<Route> routes = new ArrayList<Route>();
		final Collection<Hike> hikes = new ArrayList<Hike>();

		routes = this.routeService.routesFromCreator(comment.getOwner().getId());
		for (final Route r : routes)
			hikes.addAll(r.getComposedHikes());

		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("routes", routes);
		result.addObject("hikes", hikes);

		result.addObject("message", messageCode);
		return result;
	}
}
