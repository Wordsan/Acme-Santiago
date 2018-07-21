
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ConfigurationSystem;

@Repository
public interface ConfigurationSystemRepository extends JpaRepository<ConfigurationSystem, Integer> {

}
