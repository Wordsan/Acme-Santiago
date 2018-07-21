
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

	@Query("select r from Route r where r.creator=?1")
	Collection<Route> routesFromCreator(int userID);

	/* 6.2 -> The average and the standard deviation of hikes per route. */
	@Query("select avg(r.composedHikes.size),sqrt(sum(r.composedHikes.size * r.composedHikes.size) / count(r.composedHikes.size) - (avg(r.composedHikes.size) * avg(r.composedHikes.size))) from Route r")
	Double[] hikesPerRouteStadistics();

	/* 6.2 -> The average and the standard deviation of the length of the routes. */
	@Query("select avg(r.length),sqrt(sum(r.length * r.length) / count(r.length) - (avg(r.length) * avg(r.length))) from Route r")
	Double[] routeLengthStadistics();

	/* 6.2 -> The outlier routes according to their lengths. */

}
