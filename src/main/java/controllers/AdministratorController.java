/*
 * AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.HikeService;
import services.RouteService;
import services.UserService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@Autowired
	private RouteService routeService;

	@Autowired
	private HikeService hikeService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdvertisementService advertisementService;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView view;
		Map<String, Object> statistics;

		statistics = new HashMap<>();
		statistics.put("routesPerUser", Arrays.asList(this.userService.routesPerUserStadistics()));
		statistics.put("hikesPerRoute", Arrays.asList(this.routeService.hikesPerRouteStadistics()));
		statistics.put("routesLength", Arrays.asList(this.routeService.routeLengthStadistics()));
		statistics.put("hikesLength", Arrays.asList(this.hikeService.hikeLengthStadistics()));
		statistics.put("outlierRoutes", this.routeService.outlierRoutes());

		statistics.put("chirpsPerUser", Arrays.asList(this.userService.avgChirpsPerUser()));
		statistics.put("users25PercentChirps", this.userService.less25ChirpUsers());
		statistics.put("users75PercentChirps", this.userService.more75ChirpUsers());
		statistics.put("commentsPerRoute", Arrays.asList(this.routeService.avgCommentsPerRoute()));
		statistics.put("ratioRoutesWithWithoutAdvertisements",
				this.routeService.ratioRoutesWithWithoutAdvertisements());
		statistics.put("ratioTabooAdvertisements", this.advertisementService.statsAdvertisements());

		view = new ModelAndView("administrator/dashboard");
		view.addObject("statistics", statistics);

		return view;
	}
}
