
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Agent;
import forms.SigninForm;
import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AgentService {

	/* REPOSITORIES */
	@Autowired
	private AgentRepository agentRepository;

	/* SERVICES */
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	/* CONSTRUCTOR */
	public AgentService() {
		super();
	}

	/* CRUD */

	public Agent create() {
		UserAccount userAccount;
		Authority authority;
		Agent agent;
		Boolean anonymous;
		// Comprobamos que no se esté autentificado
		try {
			LoginService.getPrincipal();
			anonymous = false;
		} catch (Throwable oops) {
			anonymous = true;
		}

		Assert.isTrue(anonymous);
		agent = new Agent();
		userAccount = new UserAccount();
		authority = new Authority();
		authority.setAuthority("AGENT");
		userAccount.addAuthority(authority);
		agent.setUserAccount(userAccount);

		return agent;
	}

	public Collection<Agent> findAll() {
		return this.agentRepository.findAll();
	}

	public Agent findOne(final int userId) {
		Agent agent;

		agent = this.agentRepository.findOne(userId);
		Assert.notNull(agent);

		return agent;
	}

	public Agent save(final Agent agent) {
		return this.agentRepository.save(agent);
	}

	public Agent getAgentByUserAccountId(final int userAccountID) {
		Agent agent;

		Assert.notNull(userAccountID);
		agent = this.agentRepository.getAgentByUserAccountId(userAccountID);
		Assert.notNull(agent);

		return agent;
	}

	public Agent signinReconstruct(SigninForm signinForm, BindingResult binding) {
		Agent agent;
		Md5PasswordEncoder encoder;

		this.validator.validate(signinForm, binding);

		if (!signinForm.getPassword().equals(signinForm.getConfirmPassword())) {
			binding.rejectValue("password", "signin.validation.passwords");
			binding.rejectValue("confirmPassword", "signin.validation.passwords");
		}

		if ((signinForm.getConditionsAccepted() == null) || (!signinForm.getConditionsAccepted())) {
			binding.rejectValue("conditionsAccepted", "signin.validation.conditionsAccepted");
		}

		if (this.actorService.existsActorWithUsername(signinForm.getUsername())) {
			binding.rejectValue("username", "signin.validation.username");
		}

		Assert.isTrue(!binding.hasErrors());

		agent = this.create();
		encoder = new Md5PasswordEncoder();

		agent.getUserAccount().setUsername(signinForm.getUsername());
		agent.getUserAccount().setPassword(encoder.encodePassword(signinForm.getPassword(), null));
		agent.setName(signinForm.getName());
		agent.setSurname(signinForm.getSurname());
		agent.setPicture(signinForm.getPicture());
		agent.setPostalAddress(signinForm.getPostalAddress());
		agent.setPhoneNumber(signinForm.getPhoneNumber());
		agent.setEmailAddress(signinForm.getEmailAddress());

		return agent;
	}

	public void flush() {
		this.agentRepository.flush();
	}
}
