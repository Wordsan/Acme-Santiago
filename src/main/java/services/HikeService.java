
package services;

import java.util.HashMap;
import java.util.Map;

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
	public Map<String, Double> hikeLengthStadistics() {
		final Double[] statistics = this.hikeRepository.hikeLengthStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}
}
