/*
 * LegalController.java
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;

@Controller
public class LegalController extends AbstractController {

	@Autowired
	private ConfigurationSystemService configurationSystemService;

	// Constructors -----------------------------------------------------------

	public LegalController() {
		super();
	}

	// About us ---------------------------------------------------------------

	@RequestMapping("/aboutus")
	public ModelAndView aboutUs() {
		ModelAndView result;
		String companyName;
		String address;
		String vatNumber;
		String contactEmail;

		companyName = "Acme Santiago, NPO.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		vatNumber = "R2028110A";
		contactEmail = "infoacmesantiago@acme.com";

		result = new ModelAndView("legal/aboutUs");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("vatNumber", vatNumber);
		result.addObject("contactEmail", contactEmail);

		return result;
	}

	// About us ---------------------------------------------------------------

	@RequestMapping("/terms")
	public ModelAndView termsOfServices() {
		ModelAndView result;
		String companyName;
		String address;
		String vatNumber;
		String contactEmail;
		String tabooWords;

		companyName = "Acme Santiago, NPO.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		vatNumber = "R2028110A";
		contactEmail = "termsacmesantiago@acme.com";
		tabooWords = this.configurationSystemService.get().getTabooWords();

		result = new ModelAndView("legal/termsOfServices");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("vatNumber", vatNumber);
		result.addObject("contactEmail", contactEmail);
		result.addObject("tabooWords", tabooWords);

		return result;
	}

	// About us ---------------------------------------------------------------

	@RequestMapping("/cookies")
	public ModelAndView cookiesPolicy() {
		ModelAndView result;
		String companyName;

		companyName = "Acme Santiago, NPO.";
		result = new ModelAndView("legal/cookiesPolicy");
		result.addObject("companyName", companyName);

		return result;
	}

	// About us ---------------------------------------------------------------

	@RequestMapping("/privacy")
	public ModelAndView privacyPolicy() {
		ModelAndView result;
		String companyName;
		String address;
		String contactEmail;

		companyName = "Acme Santiago, NPO.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		contactEmail = "privacyacmesantiago@acme.com";

		result = new ModelAndView("legal/privacyPolicy");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("contactEmail", contactEmail);

		return result;
	}

}
