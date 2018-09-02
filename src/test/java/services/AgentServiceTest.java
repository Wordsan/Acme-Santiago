
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Agent;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class AgentServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private AgentService agentService;

	/* TESTS */
	/*
	 * Functional requirements under test:
	 * 3.1 - Register to the system as a agent.
	 */

	/*
	 * Testing:
	 * 3.1 - Register to the system as a agent.
	 */
	protected void templateSignin(final String authenticate, final String agentname, String postalAddress,
			String emailAddress, String phone, String picture, final Class<?> expected) {
		Class<?> caught;
		Agent agent;
		Md5PasswordEncoder encoder;

		caught = null;
		encoder = new Md5PasswordEncoder();
		try {
			this.authenticate(authenticate);
			agent = this.agentService.create();
			agent.getUserAccount().setUsername(agentname);
			agent.getUserAccount().setPassword(encoder.encodePassword(agentname, null));
			agent.setName(agentname);
			agent.setSurname(agentname);
			agent.setPhoneNumber(phone);
			agent.setEmailAddress(emailAddress);
			agent.setPostalAddress(postalAddress);
			agent.setPicture(picture);

			this.agentService.save(agent);
			this.agentService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestSignin() {
		final Object testingData[][] = {
				{ null, "agentTest1", "test address", "testEmail1@acme.com", "+11111111", "http://testimage1.com",
						null }, // Successful,
				{ null, "agentTest2", "test address", "testEmail1@acme.com", null, "http://testimage1.com", null }, // Successful
				{ "agent1", "agentTest2", "test address", "testEmail1@acme.com", "11111111", null,
						IllegalArgumentException.class }, // Failed -> already logged as an agent
				{ "admin", "agentTest3", "test address", "testEmail1@acme.com", null, null,
						IllegalArgumentException.class }, // Failed -> already logged as an administrator
				{ null, "agent1", "test address", "testEmail1@acme.com", null, "http://testimage1.com",
						DataIntegrityViolationException.class } // Failed -> username already exists
		};
		for (Object[] element : testingData) {
			this.templateSignin((String) element[0], (String) element[1], (String) element[2], (String) element[3],
					(String) element[4], (String) element[5], (Class<?>) element[6]);
		}
	}

}
