
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ChirpRepository;
import domain.Chirp;

@Service
@Transactional
public class ChirpService {

	/* REPOSITORIES */
	@Autowired
	private ChirpRepository	chirpRepository;


	/* SERVICES */

	/* CONSTRUCTOR */
	public ChirpService() {
		super();
	}

	/* CRUD */

	public Chirp create() {
		final Chirp c = new Chirp();
		return c;
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public Chirp findOne(final int chirpID) {
		return this.chirpRepository.findOne(chirpID);
	}

	//	public Chirp save(final Chirp chirp) {
	//		return this.chirpRepository.save(chirp);
	//	}

	public void delete(final Chirp chirp) {
		this.chirpRepository.delete(chirp);
	}

	/* OTHERS */
}
