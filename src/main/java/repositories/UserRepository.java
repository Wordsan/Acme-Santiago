
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/* Devuelve los usuarios que siguen al usuario dado. */
	@Query("select u from User u join u.followedUsers f where f.id =?1")
	Collection<User> usersThatFollowsMe(int userID);

	/* Devuelve los usuarios que sigue un usuario */
	@Query("select u.followedUsers from User u where u.id=?1")
	Collection<User> usersThatIFollow(int userID);

	/* 6.2 -> The average and the standard deviation of routes per user. */
	@Query("select avg(u.registredRoutes.size),sqrt(sum(u.registredRoutes.size * u.registredRoutes.size) / count(u.registredRoutes.size) - (avg(u.registredRoutes.size) * avg(u.registredRoutes.size))) from User u")
	Double[] routesPerUserStadistics();

	/* Devuelve el user a partir de la userAccountID */
	@Query("select u from User u where u.userAccount.id=?1")
	User getUserByUserAccountId(int userAccountID);

	/* Display a stream with the chirps posted by all of the users that he or she follows. */
	@Query("select f.chirps from User u join u.followedUsers f where u.id =?1")
	Collection<Chirp> chirpsStream(int userID);

	/* 16.6 -> The average number of chirps per user. */
	@Query("select avg(u.chirps.size) from User u")
	Double avgChirpsPerUser();
}
