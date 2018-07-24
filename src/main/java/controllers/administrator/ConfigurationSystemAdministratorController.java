
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ConfigurationSystemService;
import controllers.AbstractController;

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

}
