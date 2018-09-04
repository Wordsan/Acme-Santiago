/*
 * HikeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Advertisement;
import domain.Hike;
import domain.User;
import security.LoginService;
import services.AdvertisementService;
import services.HikeService;
import services.UserService;
import domain.Hike;
import domain.User;

@Controller
@RequestMapping("/hike")
public class HikeController extends AbstractController {

	@Autowired
	private HikeService	hikeService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private UserService userService;


	// Constructors -----------------------------------------------------------

	public HikeController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int hikeId) {
		ModelAndView view;
		Hike hike;
		Advertisement advertisement;
		User user;
		String[] pictures;

		try {
			user = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		} catch (final Throwable oops) {
			user = null;
		}

		hike = this.hikeService.findOne(hikeId);
		advertisement = this.advertisementService.getOneAvailableAdvertisementByHikeId(hike.getId());

		pictures = hike.getPictures().split(",");
		view = new ModelAndView("hike/display");
		view.addObject("hike", hike);
		view.addObject("user", user);
		view.addObject("comments", hike.getComments());
		view.addObject("pictures", pictures);
		view.addObject("advertisement", advertisement);

		return view;
	}

}
