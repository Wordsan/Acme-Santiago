
package services;

import java.util.ArrayList;
import java.util.Collection;

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
		return this.userRepository.save(user);
	}

	/* OTHERS */
	public Collection<User> usersThatFollows(final int userID) {
		return this.userRepository.usersThatFollows(userID);
	}
}
