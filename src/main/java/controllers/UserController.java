/*
 * RouteController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Route;
import domain.User;
import services.RouteService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@Autowired
	private RouteService routeService;

	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView view;
		Collection<User> users;

		users = this.userService.findAll();
		view = new ModelAndView("user/list");
		view.addObject("users", users);
		view.addObject("requestURI", "user/list.do");

		return view;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) int userId) {
		ModelAndView view;
		User user;
		Collection<Route> routes;

		user = this.userService.findOne(userId);

		routes = this.routeService.routesFromCreator(user.getId());

		view = new ModelAndView("user/display");
		view.addObject("user", user);
		view.addObject("routes", routes);
		view.addObject("requestURI", "user/display.do?userId=" + user.getId());

		return view;
	}

}
