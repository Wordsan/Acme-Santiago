
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChirpService;
import services.UserService;
import utilities.ControllerUtils;
import controllers.AbstractController;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

	/* SERVICES */
	@Autowired
	private ChirpService	chirpService;
	@Autowired
	private UserService		userService;


	/* CONSTRUCTORS */
	public ChirpUserController() {
		super();
	}

	@RequestMapping(value = "/streamChirps", method = RequestMethod.GET)
	public ModelAndView streamChirps() {
		ModelAndView result;
		Collection<Chirp> sChirps;
		final User u = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		sChirps = this.userService.chirpsStream(u.getId());

		result = new ModelAndView("chirp/streamChirps");
		result.addObject("chirps", sChirps);
		result.addObject("requestURI", "chirp/user/streamChirps.do");

		return result;
	}

	/* CREATION */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.create();
		result = this.createEditModelAndView(chirp);
		result.addObject("requestURI", "chirp/user/edit.do");

		return result;
	}

	/* EDITION */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Chirp chirp, final BindingResult binding) {
		ModelAndView result;

		chirp = this.chirpService.reconstruct(chirp, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(chirp);
		else
			try {
				this.chirpService.save(chirp);
				result = ControllerUtils.redirect("/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirp, "chirp.commit.error");
			}
		return result;
	}

	/* ANCILLARY METHODS */
	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;
		result = this.createEditModelAndView(chirp, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("chirp/create");
		result.addObject("chirp", chirp);

		result.addObject("message", messageCode);
		return result;
	}
}
