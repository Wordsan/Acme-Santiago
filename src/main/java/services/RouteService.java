
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RouteRepository;
import security.LoginService;
import domain.Comment;
import domain.Hike;
import domain.Route;

@Service
@Transactional
public class RouteService {

	/* REPOSITORIES */
	@Autowired
	private RouteRepository			routeRepository;

	/* SERVICES */
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	/* CONSTRUCTOR */
	public RouteService() {
		super();
	}

	/* CRUD */

	public Route create() {
		// Comprobamos que sea un user el que esta creando una route
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		final Route r = new Route();
		r.setComposedHikes(new ArrayList<Hike>());
		r.setComments(new ArrayList<Comment>());
		r.setCreator(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		return r;
	}

	public Collection<Route> findAll() {
		return this.routeRepository.findAll();
	}

	public Route findOne(final int routeID) {
		return this.routeRepository.findOne(routeID);
	}

	public Route save(final Route route) {
		Assert.notNull(route);
		// Comprobamos que el creador de la route sea el mismo que el que va a editar o que sea un admin
		if (LoginService.getPrincipal().equals(route.getCreator().getUserAccount()) || this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()) != null)
			return this.routeRepository.save(route);
		else
			throw new IllegalArgumentException();
	}

	public void delete(final Route route) {
		Assert.notNull(route);

		if ((this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()) != null) || LoginService.getPrincipal().equals(route.getCreator().getUserAccount())) { // Si es admin o el user
																																															// creador

			route.getCreator().getRegistredRoutes().remove(route);
			this.routeRepository.delete(route);

		} else
			// Lanzamos la excepcion del assert
			throw new IllegalArgumentException();
	}

	/* OTHERS */
	public Collection<Route> routesFromCreator(final int userID) {
		return this.routeRepository.routesFromCreator(userID);
	}

	public Map<String, Double> hikesPerRouteStadistics() {
		final Double[] statistics = this.routeRepository.hikesPerRouteStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}

	public Map<String, Double> routeLengthStadistics() {
		final Double[] statistics = this.routeRepository.routeLengthStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}

	public List<Route> searchRoutesFromKeyWord(final String keyWord) {
		List<Route> res;
		res = this.routeRepository.searchRoutesFromKeyWord(keyWord);
		Assert.notNull(res);
		return res;

	}

	public List<Route> outlierRoutes() {
		List<Route> res;
		res = this.routeRepository.outlierRoutes();
		Assert.notNull(res);
		return res;
	}

	public List<Route> routesByLengthRange(final Double l1, final Double l2) {
		List<Route> res;
		res = this.routeRepository.routesByLengthRange(l1, l2);
		Assert.notNull(res);
		return res;
	}

	public Double avgCommentsPerRoute() {
		return this.routeRepository.avgCommentsPerRoute();
	}

	public List<Route> routesByHikesSize() {
		List<Route> res;
		res = this.routeRepository.routesByHikesSize();
		Assert.notNull(res);
		return res;
	}

	public Route reconstruct(final Route route, final BindingResult binding) {
		Route routeReconstructed;

		if (route.getId() == 0)
			routeReconstructed = this.create();
		else
			routeReconstructed = this.findOne(route.getId());

		routeReconstructed.setDescription(route.getDescription());
		routeReconstructed.setName(route.getName());
		routeReconstructed.setLength(route.getLength());
		routeReconstructed.setPictures(route.getPictures());

		this.validator.validate(routeReconstructed, binding);

		Assert.isTrue(!binding.hasErrors());

		return routeReconstructed;
	}
}
