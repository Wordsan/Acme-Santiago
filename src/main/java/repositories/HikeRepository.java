
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hike;

@Repository
public interface HikeRepository extends JpaRepository<Hike, Integer> {

	/* 6.2 -> The average and the standard deviation of the length of the hikes. */
	@Query("select avg(h.length),sqrt(sum(h.length * h.length) / count(h.length) - (avg(h.length) * avg(h.length))) from Hike h")
	Double[] hikeLengthStadistics();

}
