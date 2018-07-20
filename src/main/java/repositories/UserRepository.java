
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/* Devuelve los usuarios que siguen al usuario dado. */
	@Query("select u from User u join u.followedUsers f where f.id =?1")
	Collection<User> usersThatFollows(int userID);

}
