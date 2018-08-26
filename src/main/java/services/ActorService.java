
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import repositories.ActorRepository;

@Service
@Transactional
public class ActorService {

	/* REPOSITORIES */
	@Autowired
	private ActorRepository actorRepository;

	/* SERVICES */
	@Autowired
	private Validator validator;

	/* CONSTRUCTOR */
	public ActorService() {
		super();
	}

	public boolean existsActorWithUsername(String username) {
		boolean exists;

		if (this.actorRepository.getActorByUsername(username) == null) {
			exists = false;
		} else {
			exists = true;
		}

		return exists;
	}

}
