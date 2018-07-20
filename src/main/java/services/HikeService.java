
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.HikeRepository;
import domain.Hike;

@Service
@Transactional
public class HikeService {

	/* REPOSITORIES */
	@Autowired
	private HikeRepository	hikeRepository;


	/* SERVICES */

	/* CONSTRUCTOR */
	public HikeService() {
		super();
	}

	/* CRUD */

	public void delete(final Hike hike) {
		this.hikeRepository.delete(hike);
	}

	/* OTHERS */

}
