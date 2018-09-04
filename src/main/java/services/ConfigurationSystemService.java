
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationSystemRepository;
import security.LoginService;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	/* REPOSITORIES */
	@Autowired
	private ConfigurationSystemRepository	configurationSystemRepository;

	/* SERVICES */
	@Autowired
	private AdministratorService			adminService;

	@Autowired
	private Validator						validator;


	/* CONSTRUCTOR */
	public ConfigurationSystemService() {
		super();
	}

	/* CRUD */

	public ConfigurationSystem save(final ConfigurationSystem cs) {
		Assert.notNull(this.adminService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
		return this.configurationSystemRepository.save(cs);
	}

	/* OTHERS */

	public ConfigurationSystem get() {

		ConfigurationSystem result;
		List<ConfigurationSystem> cs = new ArrayList<>();

		cs = this.configurationSystemRepository.findAll();
		result = cs.get(0);

		return result;
	}

	public ConfigurationSystem reconstruct(final ConfigurationSystem cs, final BindingResult binding) {
		ConfigurationSystem result;

		result = this.configurationSystemRepository.findOne(cs.getId());

		result.setTabooWords(cs.getTabooWords());

		this.validator.validate(result, binding);

		Assert.isTrue(!binding.hasErrors());

		return result;
	}
}
