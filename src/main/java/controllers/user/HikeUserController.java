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

import java.util.ArrayList;
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
import domain.Hike;
import domain.Route;
import security.LoginService;
import services.HikeService;
import services.RouteService;
import services.UserService;

@Controller
@RequestMapping("/hike/user")
public class HikeUserController extends AbstractController {

	@Autowired
	private HikeService hikeService;

	@Autowired
	private RouteService routeService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public HikeUserController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int routeId) {
		ModelAndView view;
		Route route;
		Hike hike;

		try {
			route = this.routeService.findOne(routeId);
			hike = this.hikeService.create(route);
			view = this.createEditModelAndView(hike);
			view.setViewName("hike/create");
		} catch (Throwable opps) {
			view = new ModelAndView("redirect:/hike/user/myList.do");
		}
		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) int hikeId, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		Hike hike;

		try {
			hike = this.hikeService.findOne(hikeId);
			Assert.isTrue(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId())
					.equals(hike.getRoute().getCreator()));
			view = this.createEditModelAndView(hike);
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/hike/list.do");
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Hike hike, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			hike = this.hikeService.reconstruct(hike, binding);
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(hike);
				if (hike.getId() == 0) {
					view.setViewName("hike/create");
				}
			} else {
				hike = this.hikeService.save(hike);
				view = new ModelAndView("redirect:/route/display.do?routeId=" + hike.getRoute().getId());
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(hike);
				if (hike.getId() == 0) {
					view.setViewName("hike/create");
				}
			} else {
				view = this.createEditModelAndView(hike, "common.message.error");
				if (hike.getId() == 0) {
					view.setViewName("hike/create");
				}
			}
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Hike hike, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			hike = this.hikeService.reconstruct(hike, binding);
			this.hikeService.delete(hike);
			view = new ModelAndView("redirect:/route/display.do?routeId=" + hike.getRoute().getId());
			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			view = this.createEditModelAndView(hike, "common.message.error");
		}

		return view;
	}

	private ModelAndView createEditModelAndView(Hike hike) {
		return this.createEditModelAndView(hike, null);
	}

	private ModelAndView createEditModelAndView(Hike hike, String message) {
		ModelAndView view;
		Collection<String> difficultyLevelOptions;

		difficultyLevelOptions = new ArrayList<String>();
		difficultyLevelOptions.add("EASY");
		difficultyLevelOptions.add("MEDIUM");
		difficultyLevelOptions.add("DIFFICULT");

		view = new ModelAndView("hike/edit");
		view.addObject("hike", hike);
		view.addObject("message", message);
		view.addObject("difficultyLevelOptions", difficultyLevelOptions);

		return view;
	}

}
