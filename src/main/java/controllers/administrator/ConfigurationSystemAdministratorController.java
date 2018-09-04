
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ConfigurationSystemService;
import utilities.ControllerUtils;
import controllers.AbstractController;
import domain.ConfigurationSystem;

@Controller
@RequestMapping("configurationSystem/admin")
public class ConfigurationSystemAdministratorController extends AbstractController {

	/* SERVICES */
	@Autowired
	private ConfigurationSystemService	csService;


	/* CONSTRUCTORS */
	public ConfigurationSystemAdministratorController() {
		super();
	}

	/* EDITION */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ConfigurationSystem cs;
		cs = this.csService.get();
		result = this.createEditModelAndView(cs);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ConfigurationSystem cs, final BindingResult binding, final RedirectAttributes redirectAttrs) {
		ModelAndView result = new ModelAndView();

		try {
			cs = this.csService.reconstruct(cs, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(cs);
			else {
				this.csService.save(cs);
				result = ControllerUtils.redirect("/welcome/index.do");
				redirectAttrs.addFlashAttribute("message", "common.message.success");
			}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(cs, "cs.commit.error");

		}

		return result;
	}

	/* ANCILLARY METHODS */
	protected ModelAndView createEditModelAndView(final ConfigurationSystem cs) {
		ModelAndView result;

		result = this.createEditModelAndView(cs, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ConfigurationSystem cs, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configurationSystem/edit");
		result.addObject("configurationSystem", cs);
		result.addObject("message", messageCode);

		return result;
	}
}
