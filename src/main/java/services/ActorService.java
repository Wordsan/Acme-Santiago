
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	/* REPOSITORIES */
	@Autowired
	private ActorRepository	actorRepository;


	/* SERVICES */

	/* CONSTRUCTOR */
	public ActorService() {
		super();
	}

	/* CRUD */

	/* OTHERS */
	public Actor getActorByUserAccountId(final int userAccountID) {
		return this.actorRepository.getActorByUserAccountId(userAccountID);
	}

}
