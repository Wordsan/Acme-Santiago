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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Hike;
import domain.Route;
import domain.User;
import security.LoginService;
import services.RouteService;
import services.UserService;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {

	@Autowired
	private RouteService routeService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public RouteController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Double minLength, @RequestParam(required = false) Double maxLength,
			@RequestParam(required = false) String numHikesFilter) {
		ModelAndView view;
		Collection<Route> routes;

		if ((keyword != null)) {
			routes = this.routeService.searchRoutesFromKeyWord(keyword);
		} else if ((minLength != null) && (maxLength != null)) {
			routes = this.routeService.routesByLengthRange(minLength, maxLength);
		} else if ((numHikesFilter != null)) {
			routes = this.routeService.routesByHikesSize();
		} else {
			routes = this.routeService.findAll();
		}
		view = new ModelAndView("route/list");
		view.addObject("routes", routes);
		view.addObject("requestURI", "route/list.do");
		view.addObject("keyword", keyword);
		view.addObject("minLength", minLength);
		view.addObject("maxLength", maxLength);

		return view;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) int routeId, HttpServletRequest request) {
		ModelAndView view;
		Route route;
		User user;
		String backURI;
		Collection<Hike> hikes;

		try {
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		} catch (Throwable oops) {
			user = null;
		}

		route = this.routeService.findOne(routeId);
		hikes = route.getComposedHikes();
		backURI = ((request.getHeader("Referer") != null) ? request.getHeader("Referer") : "/");

		view = new ModelAndView("route/display");
		view.addObject("route", route);
		view.addObject("hikes", hikes);
		view.addObject("user", user);
		view.addObject("backURI", backURI);
		view.addObject("requestURI", "route/display.do");

		return view;
	}

}
