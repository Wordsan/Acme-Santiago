
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RouteRepository;
import security.LoginService;
import security.UserAccount;
import domain.Hike;
import domain.Route;

@Service
@Transactional
public class RouteService {

	/* REPOSITORIES */
	@Autowired
	private RouteRepository	routeRepository;

	/* SERVICES */
	@Autowired
	private HikeService		hikeService;


	/* CONSTRUCTOR */
	public RouteService() {
		super();
	}

	/* CRUD */

	public Route create() {
		//Comprobamos que sea un user el que esta creando una route
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("USER"));
		final Route r = new Route();
		return r;
	}

	public Collection<Route> findAll() {
		return this.routeRepository.findAll();
	}

	public Route findOne(final int routeID) {
		return this.routeRepository.findOne(routeID);
	}

	public Route save(final Route route) {
		final UserAccount uA = LoginService.getPrincipal();
		//TODO: Comprobar que uA es el user de la route
		return this.routeRepository.save(route);
	}

	public void delete(final Route route) {
		for (final Hike h : route.getComposedHikes())
			this.hikeService.delete(h);

		this.routeRepository.delete(route);
	}

	/* OTHERS */
	public Collection<Route> routesFromCreator(final int userID) {
		return this.routeRepository.routesFromCreator(userID);
	}
}
