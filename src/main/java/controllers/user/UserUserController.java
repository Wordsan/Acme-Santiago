/*
 * UserUserController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.User;
import security.LoginService;
import services.UserService;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public UserUserController() {
		super();
	}

	@RequestMapping(value = "/followedUsers", method = RequestMethod.GET)
	public ModelAndView followedUsers() {
		ModelAndView view;
		Collection<User> users;

		users = this.userService
				.usersThatIFollow(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()).getId());
		view = new ModelAndView("user/followedUsers");
		view.addObject("users", users);
		view.addObject("followedUsers", users);
		view.addObject("requestURI", "user/followedUsers.do");

		return view;
	}

	@RequestMapping(value = "/followerUsers", method = RequestMethod.GET)
	public ModelAndView followerUsers() {
		ModelAndView view;
		Collection<User> users;

		users = this.userService.usersThatFollowsMe(
				this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()).getId());
		view = new ModelAndView("user/followerUsers");
		view.addObject("users", users);
		view.addObject("requestURI", "user/followerUsers.do");

		return view;
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView followUser(@RequestParam(required = true) int userId, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		ModelAndView view;

		try {
			this.userService.follow(userId);
			view = new ModelAndView(
					"redirect:" + ((request.getHeader("Referer") != null) ? request.getHeader("Referer") : "/"));
			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			view = new ModelAndView(
					"redirect:" + ((request.getHeader("Referer") != null) ? request.getHeader("Referer") : "/"));
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		return view;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollowUser(@RequestParam(required = true) int userId, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		ModelAndView view;

		try {
			this.userService.unfollow(userId);
			view = new ModelAndView(
					"redirect:" + ((request.getHeader("Referer") != null) ? request.getHeader("Referer") : "/"));
			redirectAttrs.addFlashAttribute("message", "common.message.success");
		} catch (Throwable oops) {
			view = new ModelAndView(
					"redirect:" + ((request.getHeader("Referer") != null) ? request.getHeader("Referer") : "/"));
			redirectAttrs.addFlashAttribute("message", "common.message.error");
		}

		return view;
	}

}
