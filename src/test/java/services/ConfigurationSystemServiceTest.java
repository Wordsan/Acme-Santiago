
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ConfigurationSystem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationSystemServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private ConfigurationSystemService	csService;


	/* TESTS */

	/*
	 * 16. An actor who is authenticated as an administrator must be able to:
	 * 1. Manage a list of taboo words.
	 */

	@Test
	public void driver16_1() {
		final String[] taboo = {
			" ", "sex,viagra,cialis", "SeX,ViAgRa,CiAlIs"
		};
		final Object testingData[][] = {
			{
				"user1", taboo[0], IllegalArgumentException.class
			//Failed -> Principal isn't an admin
			}, {
				"user2", taboo[1], IllegalArgumentException.class
			//Failed -> Principal isn't an admin
			}, {
				"admin", taboo[2], null
			//Successful
			}, {
				"admin1", taboo[2], null
			//Successful
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template16_1((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template16_1(final String authenticate, final String taboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(authenticate);
			final ConfigurationSystem cs = this.csService.get();
			cs.setTabooWords(taboo);
			this.csService.save(cs);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
