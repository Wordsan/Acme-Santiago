
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import domain.Chirp;
import domain.Comment;
import domain.Route;
import domain.User;

@Service
@Transactional
public class UserService {

	/* REPOSITORIES */
	@Autowired
	private UserRepository	userRepository;


	/* SERVICES */

	/* CONSTRUCTOR */
	public UserService() {
		super();
	}

	/* CRUD */

	public User create() {
		//Comprobamos que no se esté autentificado
		Assert.isTrue(LoginService.getPrincipal().equals(null));
		final User u = new User();
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

	public User save(final User user) {
		Assert.isTrue(LoginService.getPrincipal().equals(user.getUserAccount()));
		return this.userRepository.save(user);
	}

	/* OTHERS */
	public Collection<User> usersThatFollowsMe(final int userID) {
		return this.userRepository.usersThatFollowsMe(userID);
	}

	public Collection<User> usersThatIFollow(final int userID) {
		return this.userRepository.usersThatIFollow(userID);
	}

	public Map<String, Double> routesPerUserStadistics() {
		final Double[] statistics = this.userRepository.routesPerUserStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}

	public User getUserByUserAccountId(final int userAccountID) {
		return this.userRepository.getUserByUserAccountId(userAccountID);
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
}
