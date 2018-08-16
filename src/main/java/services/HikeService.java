
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Comment;
import domain.Hike;
import domain.Route;
import repositories.HikeRepository;
import security.LoginService;

@Service
@Transactional
public class HikeService {

	/* REPOSITORIES */
	@Autowired
	private HikeRepository hikeRepository;

	/* SERVICES */
	@Autowired
	Validator validator;
	@Autowired
	AdministratorService adminService;

	/* CONSTRUCTOR */
	public HikeService() {
		super();
	}

	/* CRUD */
	public Hike create(final Route route) {
		Hike hike;

		hike = new Hike();
		hike.setComments(new ArrayList<Comment>());
		hike.setRoute(route);

		return hike;
	}

	public Hike findOne(final int hikeId) {
		Hike hike;

		Assert.notNull(hikeId);
		hike = this.hikeRepository.findOne(hikeId);
		Assert.notNull(hike);

		return hike;
	}

	public void delete(final Hike hike) {
		this.hikeRepository.delete(hike);
	}

	public Hike save(final Hike hike) {
		Assert.notNull(hike);
		try {
			Assert.isTrue(LoginService.getPrincipal().equals(hike.getRoute().getCreator().getUserAccount()));
		} catch (final IllegalArgumentException i) {
			Assert.notNull(this.adminService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
		}

		return this.hikeRepository.save(hike);
	}

	/* OTHERS */
	public Map<String, Double> hikeLengthStadistics() {
		final Double[] statistics = this.hikeRepository.hikeLengthStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;
	}

	public Hike reconstruct(final Hike hike, final BindingResult binding) {
		Hike hikeReconstructed;

		if (hike.getId() == 0) {
			hikeReconstructed = this.create(hike.getRoute());
		} else {
			hikeReconstructed = this.findOne(hike.getId());
		}

		hikeReconstructed.setName(hike.getName());
		hikeReconstructed.setDescription(hike.getDescription());
		hikeReconstructed.setDifficultyLevel(hike.getDifficultyLevel());
		hikeReconstructed.setLength(hike.getLength());
		hikeReconstructed.setPictures(hike.getPictures());
		hikeReconstructed.setOriginCity(hike.getOriginCity());
		hikeReconstructed.setDestinationCity(hike.getDestinationCity());

		this.validator.validate(hikeReconstructed, binding);
		Assert.isTrue(!binding.hasErrors());

		return hikeReconstructed;
	}

	public void flush() {
		this.hikeRepository.flush();
	}
}
