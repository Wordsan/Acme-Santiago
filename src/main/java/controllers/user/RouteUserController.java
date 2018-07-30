/*
 * RouteUserController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.Route;
import domain.User;
import security.LoginService;
import services.RouteService;
import services.UserService;

@Controller
@RequestMapping("/route/user")
public class RouteUserController extends AbstractController {

	@Autowired
	private RouteService routeService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public RouteUserController() {
		super();
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView view;
		Collection<Route> routes;
		User user;

		try {
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());

			routes = this.routeService.routesFromCreator(user.getId());
			view = new ModelAndView("route/myList");
			view.addObject("routes", routes);
			view.addObject("user", user);
			view.addObject("requestURI", "route/user/myList.do");
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/route/list.do");
		}

		return view;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView view;
		Route route;

		route = this.routeService.create();
		view = this.createEditModelAndView(route);
		view.setViewName("route/create");

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) int routeId, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		Route route;

		try {
			route = this.routeService.findOne(routeId);
			Assert.isTrue(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId())
					.equals(route.getCreator()));
			view = this.createEditModelAndView(route);
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/route/list.do");
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Route route, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			route = this.routeService.reconstruct(route, binding);
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(route);
				if (route.getId() == 0) {
					view.setViewName("route/create");
				}
			} else {
				route = this.routeService.save(route);
				view = new ModelAndView("redirect:/route/user/myList.do");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(route);
				if (route.getId() == 0) {
					view.setViewName("route/create");
				}
			} else {
				view = this.createEditModelAndView(route, "common.message.error");
				if (route.getId() == 0) {
					view.setViewName("route/create");
				}
			}
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Route route, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			route = this.routeService.reconstruct(route, binding);
			this.routeService.delete(route);
			view = new ModelAndView("redirect:myList.do");
			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			view = this.createEditModelAndView(route, "common.message.error");
		}

		return view;
	}

	private ModelAndView createEditModelAndView(Route route) {
		return this.createEditModelAndView(route, null);
	}

	private ModelAndView createEditModelAndView(Route route, String message) {
		ModelAndView view;

		view = new ModelAndView("route/edit");
		view.addObject("route", route);
		view.addObject("message", message);

		return view;
	}

}
