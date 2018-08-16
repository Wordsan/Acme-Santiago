/*
 * WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import security.LoginService;
import security.UserAccount;
import services.AdministratorService;
import services.UserService;

@Controller
@RequestMapping("/welcome")

public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Services
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private UserService userService;

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");

		try {
			LoginService.getPrincipal();
			final UserAccount uA = LoginService.getPrincipal();
			final User u = this.userService.getUserByUserAccountId(uA.getId());
			if (u != null) {
				name = u.getName() + " " + u.getSurname();
			} else {
				name = this.adminService.getAdminByUserAccountId(uA.getId()).getName() + " "
						+ this.adminService.getAdminByUserAccountId(uA.getId()).getSurname();
			}
		} catch (final Throwable i) {

		}

		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}
}
