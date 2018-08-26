
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

	@Query("select a from Advertisement a where a.agent.id=?1")
	Collection<Advertisement> findAllByAgentId(int agentId);

	@Query("select a from Advertisement a where current_timestamp between a.startMoment and a.endMoment and a.hike.id = ?1")
	Collection<Advertisement> findAllAvailableAdvertisementsByHikeId(int hikeId);

	@Query("select a from Advertisement a where a.title like ?1")
	Collection<Advertisement> findAllTabooAdvertisements(String tabooWordsQuery);
}
