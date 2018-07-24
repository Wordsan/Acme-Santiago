
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import security.LoginService;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	/* REPOSITORIES */
	@Autowired
	private ChirpRepository				chirpRepository;

	/* SERVICES */
	@Autowired
	private UserService					userService;
	@Autowired
	private ConfigurationSystemService	csService;
	@Autowired
	private AdministratorService		adminService;


	/* CONSTRUCTOR */
	public ChirpService() {
		super();
	}

	/* CRUD */

	public Chirp create() {
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		final Chirp c = new Chirp();
		final User u = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		c.setUser(u);
		return c;
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public Chirp findOne(final int chirpID) {
		return this.chirpRepository.findOne(chirpID);
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		final User u = chirp.getUser();
		u.getChirps().add(chirp);
		this.userService.save(u);

		return this.chirpRepository.save(chirp);
	}

	public void delete(final Chirp chirp) {
		//16.3
		Assert.notNull(this.adminService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
		Assert.notNull(chirp);
		final User u = chirp.getUser();
		u.getChirps().remove(chirp);
		this.userService.save(u);

		this.chirpRepository.delete(chirp);
	}

	/* OTHERS */
	public List<Chirp> tabooChirps() {
		List<Chirp> all;
		final List<Chirp> res = new ArrayList<Chirp>();
		all = this.chirpRepository.findAll();
		final String[] tabooWords = this.csService.get().getTabooWords().toLowerCase().split(",");
		for (final Chirp c : all)
			for (final String s : tabooWords)
				if ((c.getDescription().toLowerCase().contains(s) || c.getTitle().toLowerCase().contains(s)))
					res.add(c);
		return res;
	}
}
