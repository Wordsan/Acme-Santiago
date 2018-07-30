/*
 * ProfileController.java
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.User;
import forms.SigninForm;
import services.UserService;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {

	@Autowired
	private UserService userService;

	public SigninController() {
		super();
	}

	@RequestMapping(value = "/user/signin", method = RequestMethod.GET)
	public ModelAndView signinUser() {
		ModelAndView view;
		SigninForm signinForm;

		signinForm = new SigninForm();

		view = this.signinModelAndView(signinForm);

		return view;
	}

	@RequestMapping(value = "/user/signin", method = RequestMethod.POST, params = "signin")
	public ModelAndView signinUser(SigninForm signinForm, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		User user;

		try {
			user = this.userService.signinReconstruct(signinForm, binding);
			if (binding.hasErrors()) {
				view = this.signinModelAndView(signinForm);
			} else {
				this.userService.save(user);
				view = new ModelAndView("redirect:/");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.signinModelAndView(signinForm);
			} else {
				view = this.signinModelAndView(signinForm, "common.message.error");
			}
		}

		return view;
	}

	protected ModelAndView signinModelAndView(SigninForm signinForm) {
		return this.signinModelAndView(signinForm, null);
	}

	protected ModelAndView signinModelAndView(SigninForm signinForm, String message) {
		ModelAndView view;

		view = new ModelAndView("signin/signin");
		view.addObject("signinForm", signinForm);
		view.addObject("message", message);

		return view;
	}

}
