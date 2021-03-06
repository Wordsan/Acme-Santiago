
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	/* Devuelve el actor a partir de la userAccountID */
	@Query("select a from Actor a where a.userAccount.id=?1")
	Actor getActorByUserAccountId(int userAccountID);

}
