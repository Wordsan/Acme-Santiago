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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

}
