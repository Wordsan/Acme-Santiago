/*
 * ProfileController.java
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Agent;
import domain.User;
import forms.SigninForm;
import services.AgentService;
import services.UserService;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {

	@Autowired
	private UserService userService;

	@Autowired
	private AgentService agentService;

	public SigninController() {
		super();
	}

	@RequestMapping(value = "/user/signin", method = RequestMethod.GET)
	public ModelAndView signinUser() {
		ModelAndView view;
		SigninForm signinForm;

		signinForm = new SigninForm();

		view = this.signinUserModelAndView(signinForm);

		return view;
	}

	@RequestMapping(value = "/user/signin", method = RequestMethod.POST, params = "signin")
	public ModelAndView signinUser(SigninForm signinForm, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		User user;

		try {
			user = this.userService.signinReconstruct(signinForm, binding);
			if (binding.hasErrors()) {
				view = this.signinUserModelAndView(signinForm);
			} else {
				this.userService.save(user);
				view = new ModelAndView("redirect:/");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.signinUserModelAndView(signinForm);
			} else {
				view = this.signinUserModelAndView(signinForm, "common.message.error");
			}
		}

		return view;
	}

	@RequestMapping(value = "/agent/signin", method = RequestMethod.GET)
	public ModelAndView signinAgent() {
		ModelAndView view;
		SigninForm signinForm;

		signinForm = new SigninForm();

		view = this.signinAgentModelAndView(signinForm);

		return view;
	}

	@RequestMapping(value = "/agent/signin", method = RequestMethod.POST, params = "signin")
	public ModelAndView signinAgent(SigninForm signinForm, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView view;
		Agent agent;

		try {
			agent = this.agentService.signinReconstruct(signinForm, binding);
			if (binding.hasErrors()) {
				view = this.signinAgentModelAndView(signinForm);
			} else {
				this.agentService.save(agent);
				view = new ModelAndView("redirect:/");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				view = this.signinAgentModelAndView(signinForm);
			} else {
				view = this.signinAgentModelAndView(signinForm, "common.message.error");
			}
		}

		return view;
	}

	protected ModelAndView signinUserModelAndView(SigninForm signinForm) {
		return this.signinUserModelAndView(signinForm, null);
	}

	protected ModelAndView signinUserModelAndView(SigninForm signinForm, String message) {
		ModelAndView view;

		view = new ModelAndView("signin/user");
		view.addObject("signinForm", signinForm);
		view.addObject("message", message);
		view.addObject("requestUri", "security/user/signin.do");

		return view;
	}

	protected ModelAndView signinAgentModelAndView(SigninForm signinForm) {
		return this.signinAgentModelAndView(signinForm, null);
	}

	protected ModelAndView signinAgentModelAndView(SigninForm signinForm, String message) {
		ModelAndView view;

		view = new ModelAndView("signin/agent");
		view.addObject("signinForm", signinForm);
		view.addObject("message", message);
		view.addObject("requestUri", "security/agent/signin.do");

		return view;
	}

}
