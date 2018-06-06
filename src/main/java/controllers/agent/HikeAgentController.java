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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Agent;
import domain.Hike;
import security.LoginService;
import services.AgentService;
import services.HikeService;

@Controller
@RequestMapping("/hike/agent")
public class HikeAgentController extends AbstractController {

	@Autowired
	private HikeService hikeService;

	@Autowired
	private AgentService agentService;

	// Constructors -----------------------------------------------------------

	public HikeAgentController() {
		super();
	}

	@RequestMapping(value = "/hikesWithAdvertisements", method = RequestMethod.GET)
	public ModelAndView listWithAdvertisements() {
		ModelAndView view;
		Collection<Hike> hikes;
		Agent agent;

		try {
			agent = this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId());

			hikes = this.hikeService.findAllHikesWithAdvertisementByAgentId(agent.getId());
			view = new ModelAndView("hike/withAdList");
			view.addObject("hikes", hikes);
			view.addObject("agent", agent);
			view.addObject("requestURI", "hike/agent/hikesWithAdvertisements.do");
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/");
		}

		return view;
	}

	@RequestMapping(value = "/hikesWithoutAdvertisements", method = RequestMethod.GET)
	public ModelAndView listWithoutAdvertisements() {
		ModelAndView view;
		Collection<Hike> hikes;
		Agent agent;

		try {
			agent = this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId());

			hikes = this.hikeService.findAllHikesWithoutAdvertisementByAgentId(agent.getId());
			view = new ModelAndView("hike/withoutAdList");
			view.addObject("hikes", hikes);
			view.addObject("agent", agent);
			view.addObject("requestURI", "hike/agent/hikesWithoutAdvertisements.do");
		} catch (Throwable oops) {
			view = new ModelAndView("redirect:/");
		}

		return view;
	}
}
