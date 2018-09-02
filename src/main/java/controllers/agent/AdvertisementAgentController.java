/*
 * RouteUserController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.agent;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.Advertisement;
import domain.Agent;
import domain.Hike;
import security.LoginService;
import services.AdvertisementService;
import services.AgentService;
import services.HikeService;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private HikeService hikeService;

	// Constructors -----------------------------------------------------------

	public AdvertisementAgentController() {
		super();
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView view;
		Collection<Advertisement> advertisements;
		Agent agent;

		try {
			agent = this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId());

			advertisements = this.advertisementService.findAllByAgentId(agent.getId());
			view = new ModelAndView("advertisement/myList");
			view.addObject("advertisements", advertisements);
			view.addObject("agent", agent);
			view.addObject("requestURI", "advertisement/agent/myList.do");
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/advertisement/list.do");
		}

		return view;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView view;
		Advertisement advertisement;

		advertisement = this.advertisementService.create();
		view = this.createEditModelAndView(advertisement);
		view.setViewName("advertisement/create");

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) int advertisementId, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		Advertisement advertisement;

		advertisement = this.advertisementService.findOne(advertisementId);
		if (advertisement.getEndMoment().before(new Date(System.currentTimeMillis()))) {
			view = new ModelAndView("redirect:/advertisement/agent/myList.do");
			redirectAttrs.addFlashAttribute("message", "advert.error.finished");
		} else if (this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId())
				.equals(advertisement.getAgent())) {
			view = new ModelAndView("redirect:/advertisement/agent/myList.do");
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		} else {
			view = this.createEditModelAndView(advertisement);
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Advertisement advertisement, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			advertisement = this.advertisementService.reconstruct(advertisement, binding);
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(advertisement);
				if (advertisement.getId() == 0) {
					view.setViewName("advertisement/create");
				}
			} else {
				advertisement = this.advertisementService.save(advertisement);
				view = new ModelAndView("redirect:/advertisement/agent/myList.do");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.createEditModelAndView(advertisement);
				if (advertisement.getId() == 0) {
					view.setViewName("advertisement/create");
				}
			} else {
				view = this.createEditModelAndView(advertisement, "common.message.error");
				if (advertisement.getId() == 0) {
					view.setViewName("advertisement/create");
				}
			}
		}

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Advertisement advertisement, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			advertisement = this.advertisementService.reconstruct(advertisement, binding);
			this.advertisementService.delete(advertisement);
			view = new ModelAndView("redirect:myList.do");
			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			view = this.createEditModelAndView(advertisement, "common.message.error");
		}

		return view;
	}

	private ModelAndView createEditModelAndView(Advertisement advertisement) {
		return this.createEditModelAndView(advertisement, null);
	}

	private ModelAndView createEditModelAndView(Advertisement advertisement, String message) {
		ModelAndView view;
		Collection<Hike> hikes;

		hikes = this.hikeService.findAll();

		view = new ModelAndView("advertisement/edit");
		view.addObject("advertisement", advertisement);
		view.addObject("hikes", hikes);
		view.addObject("requestUri", "advertisement/agent/edit.do");
		view.addObject("message", message);

		return view;
	}

}
