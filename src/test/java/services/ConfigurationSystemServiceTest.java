
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationSystemServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private ConfigurationSystemService	csService;


	/* TESTS */

	/*
	 * Manage a list of taboo words.
	 */

	@Test
	public void test16_1() {
		this.authenticate("user1");
		final ConfigurationSystem cs = this.csService.get();
		cs.setTabooWords("jeje");

		try {
			this.csService.save(cs);
			throw new RuntimeException();
		} catch (final IllegalArgumentException e) {
		}
	}

}
