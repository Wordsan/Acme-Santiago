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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Hike;
import services.HikeService;

@Controller
@RequestMapping("/hike")
public class HikeController extends AbstractController {

	@Autowired
	private HikeService hikeService;

	// Constructors -----------------------------------------------------------

	public HikeController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) int hikeId) {
		ModelAndView view;
		Hike hike;

		// hike = this.hikeService.findOne(hikeId);
		hike = null;

		view = new ModelAndView("hike/display");
		view.addObject("hike", hike);

		return view;
	}

}
