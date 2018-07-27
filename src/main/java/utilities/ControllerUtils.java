
package utilities;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ControllerUtils {

	public static ModelAndView redirect(final String url) {
		final RedirectView view = new RedirectView(url);

		// Fix root-relative url handling.
		view.setContextRelative(true);

		// Allow adding query parameters to the ModelAndView.
		view.setExposeModelAttributes(true);

		final ModelAndView result = new ModelAndView(view);
		return result;
	}

}
