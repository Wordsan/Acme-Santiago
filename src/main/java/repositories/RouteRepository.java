
package repositories;

import java.util.Collection;
import java.util.List;

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

	/* 3.3 -> Search for routes using a single key word that must appear somewhere in their names, their descriptions, or their hikes. */
	@Query("select r from Route r join r.composedHikes h where r.name like '%?1%' or r.description like '%?1%' or  h.name like '%?1%' or h.originCity like '%?1%' or h.destinationCity like '%?1%'or h.description like '%?1%'")
	List<Route> searchRoutesFromKeyWord(String keyWord);

	/* 6.2 -> The outlier routes according to their lengths. */
	@Query("select r from Route r where r.length <= (select avg(r.length) from Route r) - 3*(select stddev(r.length) from Route r) or r.length >= (select avg(r.length) from Route r) + 3*(select stddev(r.length) from Route r)")
	List<Route> outlierRoutes();

	/* 3.4 -> Search for routes whose length is in a user-provided range. */
	@Query("select r from Route r where r.length between =?1 and =?2")
	List<Route> routesByLengthRange(Double l1, Double l2);

	/* 16.6 -> The average number of comments per route (including their hikes). */
	@Query("select avg(r.comments.size + sum(h.comments)) from Route r join r.composedHikes h")
	Double avgCommentsPerRoute();

	//TODO
	/* 3.5 -> Search for routes that have a minimum or a maximum number of hikes. */
	@Query("")
	List<Route> routesByHikesSize();
}
