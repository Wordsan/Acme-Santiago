
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("chirp/admin")
public class ChirpAdministratorController extends AbstractController {

	/* SERVICES */
	@Autowired
	private ChirpService	chirpService;


	/* CONSTRUCTORS */
	public ChirpAdministratorController() {
		super();
	}

	/* LISTING */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Chirp> chirps;
		chirps = this.chirpService.tabooChirps();

		result = new ModelAndView("chirp/tabooChirps");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/admin/list.do");

		return result;
	}

	/* EDITION */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chirpId) {
		ModelAndView result;

		try {
			final Chirp chirp = this.chirpService.findOne(chirpId);
			this.chirpService.delete(chirp);
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "chirp.commit.error");
		}

		return result;
	}
}
