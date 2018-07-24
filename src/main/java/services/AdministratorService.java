
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AdministratorRepository;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	/* REPOSITORIES */
	@Autowired
	private AdministratorRepository	adminRepository;


	/* SERVICES */

	/* CONSTRUCTOR */
	public AdministratorService() {
		super();
	}

	/* CRUD */

	/* OTHERS */
	public Administrator getAdminByUserAccountId(final int userAccountID) {
		return this.adminRepository.getAdminByUserAccountId(userAccountID);
	}

}
