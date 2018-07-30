/*
 * RouteAdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import security.LoginService;
import services.AdministratorService;
import services.RouteService;

@Controller
@RequestMapping("/route/administrator")
public class RouteAdministratorController extends AbstractController {

	@Autowired
	private RouteService routeService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public RouteAdministratorController() {
		super();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) int routeId, RedirectAttributes redirectAttrs) {
		ModelAndView view;

		try {
			Assert.notNull(this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
			this.routeService.delete(this.routeService.findOne(routeId));

			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		view = new ModelAndView("redirect:/route/list.do");

		return view;
	}

}
