/*
 * RouteUserController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.Advertisement;
import services.AdvertisementService;

@Controller
@RequestMapping("/advertisement/administrator")
public class AdvertisementAdministratorController extends AbstractController {

	@Autowired
	private AdvertisementService advertisementService;

	// Constructors -----------------------------------------------------------

	public AdvertisementAdministratorController() {
		super();
	}

	@RequestMapping(value = "/tabooAdvertisements", method = RequestMethod.GET)
	public ModelAndView listTaboo() {
		ModelAndView view;
		Collection<Advertisement> advertisements;

		advertisements = this.advertisementService.findAllTabooAdvertisements();
		view = new ModelAndView("advertisement/tabooList");
		view.addObject("advertisements", advertisements);

		return view;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteAdvertisement(@RequestParam int advertisementId, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		Advertisement ad;

		view = new ModelAndView("redirect:/advertisement/administrator/tabooAdvertisements.do");

		try {
			ad = this.advertisementService.findOne(advertisementId);
			if (ad.getEndMoment().after(new Date(System.currentTimeMillis()))) {
				this.advertisementService.delete(ad);
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			} else {
				redirectAttrs.addFlashAttribute("message", "advert.error.finished");
			}
		} catch (Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		return view;
	}

}
