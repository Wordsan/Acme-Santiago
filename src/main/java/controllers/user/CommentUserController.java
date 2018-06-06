
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CommentService;
import services.HikeService;
import services.RouteService;
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
	@Autowired
	private HikeService		hikeService;


	/* CONSTRUCTORS */
	public CommentUserController() {
		super();
	}

	/* CREATION */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int routeOrhikeId) {
		ModelAndView result;
		Comment comment;
		Route route;
		Hike hike;

		try {
			route = this.routeService.findOne(routeOrhikeId);
			hike = this.hikeService.findOne(routeOrhikeId);
			comment = this.commentService.create(route, hike);
			result = this.createEditModelAndView(comment);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:window.history.go(-1)");
		}

		result.addObject("requestURI", "comment/user/edit.do");

		return result;
	}

	/* EDITION */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment, final BindingResult binding, final RedirectAttributes redirectAttrs) {
		ModelAndView result = new ModelAndView();

		try {
			comment = this.commentService.reconstruct(comment, binding);
			if (binding.hasErrors()) {
				if (comment.isValidHasEitherRouteOrHike() == false)
					result = this.createEditModelAndView(comment, "comment1.commit.error");
				else
					result = this.createEditModelAndView(comment);
			} else {
				this.commentService.save(comment);
				if (comment.getHike() != null)
					result = new ModelAndView("redirect:/hike/display.do?hikeId=" + comment.getHike().getId());
				else
					result = new ModelAndView("redirect:/route/display.do?routeId=" + comment.getRoute().getId());
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				if (comment.isValidHasEitherRouteOrHike() == false)
					result = this.createEditModelAndView(comment, "comment1.commit.error");
				else
					result = this.createEditModelAndView(comment);

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

		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);

		result.addObject("message", messageCode);
		return result;
	}
}
