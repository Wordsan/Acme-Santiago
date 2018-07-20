
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	/* RELATIONSHIPS */

	private Collection<User>	followedUsers;
	private Collection<Route>	registredRoutes;
	private Collection<Chirp>	chirps;
	private Collection<Comment>	routeComments;
	private Collection<Comment>	hikeComments;


	@Valid
	@NotNull
	@OneToMany
	public Collection<User> getFollowedUsers() {
		return this.followedUsers;
	}

	public void setFollowedUsers(final Collection<User> followedUsers) {
		this.followedUsers = followedUsers;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Route> getRegistredRoutes() {
		return this.registredRoutes;
	}

	public void setRegistredRoutes(final Collection<Route> registredRoutes) {
		this.registredRoutes = registredRoutes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	public void setChirps(final Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "owner")
	public Collection<Comment> getRouteComments() {
		return this.routeComments;
	}

	public void setRouteComments(final Collection<Comment> routeComments) {
		this.routeComments = routeComments;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "owner")
	public Collection<Comment> getHikeComments() {
		return this.hikeComments;
	}

	public void setHikeComments(final Collection<Comment> hikeComments) {
		this.hikeComments = hikeComments;
	}
}
