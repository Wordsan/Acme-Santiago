
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	/* SERVICE UNDER TEST */
	@Autowired
	private AdvertisementService advertisementService;

	/* REQUIRED SERVICES */
	@Autowired
	private AgentService agentService;

	@Autowired
	private HikeService hikeService;

	/* TESTS */
	/*
	 * Functional requirements under test:
	 * 4.2 - Register an advertisement and associate it with a hike.
	 * 5.1 - List the advertisements that contain taboo words in its title.
	 * 5.2 - Remove an advertisement that he or she thinks is inappropriate.
	 */

	/*
	 * Testing:
	 * 4.2 - Register an advertisement and associate it with a hike.
	 */
	protected void templateManageAdvertisements(final String authenticate, String titleCreate, String bannerCreate,
			String targetUrlCreate, int daysDisplayedCreate, String hikeBeanIdCreate, CreditCard creditCardCreate,
			String titleEdit, String bannerEdit, String targetUrlEdit, int daysDisplayedEdit, String hikeBeanIdEdit,
			CreditCard creditCardEdit, final Class<?> expected) {
		Class<?> caught;
		Agent agent;
		Advertisement ad;

		caught = null;
		try {
			this.authenticate(authenticate);
			agent = this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId());
			this.advertisementService.findAllByAgentId(agent.getId());
			ad = this.advertisementService.create();
			ad.setTitle(titleCreate);
			ad.setBanner(bannerCreate);
			ad.setTargetUrl(targetUrlCreate);
			ad.setDaysDisplayed(daysDisplayedCreate);
			ad.setHike(this.hikeService.findOne(this.getEntityId(hikeBeanIdCreate)));
			ad.setCreditCard(creditCardCreate);
			ad = this.advertisementService.save(ad);
			this.advertisementService.flush();
			ad.setTitle(titleEdit);
			ad.setBanner(bannerEdit);
			ad.setTargetUrl(targetUrlEdit);
			ad.setDaysDisplayed(daysDisplayedEdit);
			ad.setHike(this.hikeService.findOne(this.getEntityId(hikeBeanIdEdit)));
			ad.setCreditCard(creditCardEdit);
			Assert.isTrue(this.advertisementService.findAllByAgentId(agent.getId()).contains(ad));
			this.advertisementService.delete(ad);
			this.advertisementService.flush();
			Assert.isTrue(!this.advertisementService.findAllByAgentId(agent.getId()).contains(ad));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverTestManageAdvertisements() {
		final Object testingData[][] = {
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false), null }, // Successful
				{ "admin", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						IllegalArgumentException.class }, // Failed -> logged as an admin
				{ "user1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						IllegalArgumentException.class }, // Failed -> logged as a user
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(06, 2018, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						IllegalArgumentException.class }, // Failed -> credit card expired
				{ "agent1", null, null, null, 4, "hike-5", null, "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						IllegalArgumentException.class }, // Failed -> create data empty
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(06, 2018, false), null, null, null, 4, "hike-5", null,
						IllegalArgumentException.class }, // Failed -> create data empty
				{ "agent1", "", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> title empty
				{ "agent1", "test advertisement", "", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> banner empty
				{ "agent1", "test advertisement", "http://google.es", "", 4, "hike-5",
						this.generateCreditCard(11, 2026, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> targetUrl empty
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(11, 2026, true), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> invalid credit card
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2018, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> expired credit card
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "", "http://google.es", "http://google.es", 4,
						"hike-6", this.generateCreditCard(11, 2028, false), ConstraintViolationException.class }, // Failed -> title empty
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "test advertisement", "", "http://google.es", 4,
						"hike-6", this.generateCreditCard(11, 2028, false), ConstraintViolationException.class }, // Failed -> banner empty
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "test advertisement", "http://google.es", "", 4,
						"hike-6", this.generateCreditCard(11, 2028, false), ConstraintViolationException.class }, // Failed -> targetUrl empty
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "test advertisement", "http://google.es",
						"http://google.es", 0, "hike-6", this.generateCreditCard(11, 2028, false),
						ConstraintViolationException.class }, // Failed -> invalid endMoment
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(11, 2028, true),
						ConstraintViolationException.class }, // Failed -> invalid credit card
				{ "agent1", "test advertisement", "http://google.es", "http://google.es", 4, "hike-5",
						this.generateCreditCard(07, 2035, false), "test advertisement", "http://google.es",
						"http://google.es", 4, "hike-6", this.generateCreditCard(9, 2018, false),
						ConstraintViolationException.class }, // Failed -> expired credit card
		};
		for (Object[] element : testingData) {
			this.templateManageAdvertisements((String) element[0], (String) element[1], (String) element[2],
					(String) element[3], (int) element[4], (String) element[5], (CreditCard) element[6],
					(String) element[7], (String) element[8], (String) element[9], (int) element[10],
					(String) element[11], (CreditCard) element[12], (Class<?>) element[13]);
		}
	}

	/*
	 * Testing:
	 * 4.2 - Register an advertisement and associate it with a hike. (only delete)
	 * 5.2 - Remove an advertisement that he or she thinks is inappropriate.
	 */
	protected void templateDeleteAdvertisement(final String authenticate, String adBeanId, final Class<?> expected) {
		Class<?> caught;
		Advertisement ad;

		caught = null;
		try {
			this.authenticate(authenticate);
			ad = this.advertisementService.findOne(this.getEntityId(adBeanId));
			this.advertisementService.delete(ad);
			Assert.isTrue(!this.advertisementService.findAll().contains(ad));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteRoute() {
		final Object testingData[][] = { { "agent1", "advertisement-1", null }, // Successful
				{ "admin", "advertisement-2", null }, // Successful
				//{ null, "advertisement3", IllegalArgumentException.class }, // Failed -> not logged
				//{ "agent-agent2", "advertisement6", IllegalArgumentException.class }, // Failed -> agent isn't the owner
		};
		for (Object[] element : testingData) {
			this.templateDeleteAdvertisement((String) element[0], (String) element[1], (Class<?>) element[2]);
		}
	}

	/*
	 * Testing:
	 * 5.1 - List the advertisements that contain taboo words in its title.
	 */
	protected void templateListTabooAdvertisements(final String authenticate, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(authenticate);
			this.advertisementService.findAllTabooAdvertisements();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverListTabooAdvertisements() {
		final Object testingData[][] = { { "admin", null }, // Successful
				{ null, IllegalArgumentException.class }, // Failed -> not logged
				{ "agent-agent2", IllegalArgumentException.class }, // Failed -> logged as an agent
		};
		for (Object[] element : testingData) {
			this.templateListTabooAdvertisements((String) element[0], (Class<?>) element[1]);
		}
	}

	private CreditCard generateCreditCard(int expirationMonth, int expirationYear, boolean invalidCard) {
		CreditCard creditCard;
		String invalidCardNumber, validCardNumber;

		validCardNumber = "4459735046170527";
		invalidCardNumber = "1234567869543298";

		creditCard = new CreditCard();
		creditCard.setBrandName("VISA");
		creditCard.setHolderName("Test");
		creditCard.setCardNumber((invalidCard) ? invalidCardNumber : validCardNumber);
		creditCard.setCvvCode(865);
		creditCard.setExpirationMonth(expirationMonth);
		creditCard.setExpirationYear(expirationYear);

		return creditCard;
	}
}
