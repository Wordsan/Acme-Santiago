
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Chirp;
import domain.Comment;
import domain.Route;
import domain.User;
import forms.SigninForm;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	/* REPOSITORIES */
	@Autowired
	private UserRepository userRepository;

	/* SERVICES */
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	/* CONSTRUCTOR */
	public UserService() {
		super();
	}

	/* CRUD */

	public User create() {
		UserAccount userAccount;
		Authority authority;
		User u;
		Boolean anonymous;
		// Comprobamos que no se esté autentificado
		try {
			LoginService.getPrincipal();
			anonymous = false;
		} catch (Throwable oops) {
			anonymous = true;
		}

		Assert.isTrue(anonymous);
		u = new User();
		userAccount = new UserAccount();
		authority = new Authority();
		authority.setAuthority("USER");
		userAccount.addAuthority(authority);
		u.setUserAccount(userAccount);
		u.setFollowedUsers(new ArrayList<User>());
		u.setRegistredRoutes(new ArrayList<Route>());
		u.setChirps(new ArrayList<Chirp>());
		u.setRouteComments(new ArrayList<Comment>());
		u.setHikeComments(new ArrayList<Comment>());

		return u;
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int userId) {
		User user;

		user = this.userRepository.findOne(userId);
		Assert.notNull(user);

		return user;
	}

	public User save(final User user) {
		return this.userRepository.save(user);
	}

	/* OTHERS */
	public Collection<User> usersThatFollowsMe(final int userID) {
		Collection<User> users;

		Assert.notNull(userID);
		users = this.userRepository.usersThatFollowsMe(userID);
		Assert.notNull(users);

		return users;
	}

	public Collection<User> usersThatIFollow(final int userID) {
		Collection<User> users;

		Assert.notNull(userID);
		users = this.userRepository.usersThatIFollow(userID);
		Assert.notNull(users);

		return users;
	}

	public void follow(int userId) {
		User userLogged, userToFollow;

		userLogged = this.getUserByUserAccountId(LoginService.getPrincipal().getId());
		userToFollow = this.findOne(userId);

		if (!userLogged.getFollowedUsers().contains(userToFollow)) {
			userLogged.getFollowedUsers().add(userToFollow);
			this.save(userLogged);
		}

		Assert.isTrue(userLogged.getFollowedUsers().contains(userToFollow));

	}

	public void unfollow(int userId) {
		User userLogged, userToFollow;

		userLogged = this.getUserByUserAccountId(LoginService.getPrincipal().getId());
		userToFollow = this.findOne(userId);

		if (userLogged.getFollowedUsers().contains(userToFollow)) {
			userLogged.getFollowedUsers().remove(userToFollow);
			this.save(userLogged);
		}

		Assert.isTrue(!userLogged.getFollowedUsers().contains(userToFollow));

	}

	public Map<String, Double> routesPerUserStadistics() {
		final Double[] statistics = this.userRepository.routesPerUserStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}

	public User getUserByUserAccountId(final int userAccountID) {
		User user;

		Assert.notNull(userAccountID);
		user = this.userRepository.getUserByUserAccountId(userAccountID);
		Assert.notNull(user);

		return user;
	}

	public Collection<Chirp> chirpsStream(final int userID) {
		return this.userRepository.chirpsStream(userID);
	}

	public Double avgChirpsPerUser() {
		return this.userRepository.avgChirpsPerUser();
	}

	public Collection<User> more75ChirpUsers() {
		return this.userRepository.more75ChirpUsers();
	}

	public Collection<User> less25ChirpUsers() {
		return this.userRepository.less25ChirpUsers();
	}

	public User signinReconstruct(SigninForm signinForm, BindingResult binding) {
		User user;
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

		user = this.create();
		encoder = new Md5PasswordEncoder();

		user.getUserAccount().setUsername(signinForm.getUsername());
		user.getUserAccount().setPassword(encoder.encodePassword(signinForm.getPassword(), null));
		user.setName(signinForm.getName());
		user.setSurname(signinForm.getSurname());
		user.setPicture(signinForm.getPicture());
		user.setPostalAddress(signinForm.getPostalAddress());
		user.setPhoneNumber(signinForm.getPhoneNumber());
		user.setEmailAddress(signinForm.getEmailAddress());

		return user;
	}

	public void flush() {
		this.userRepository.flush();
	}
}
