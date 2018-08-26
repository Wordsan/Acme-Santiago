
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Advertisement;
import domain.CreditCard;
import repositories.AdvertisementRepository;
import security.LoginService;

@Service
@Transactional
public class AdvertisementService {

	/* REPOSITORIES */
	@Autowired
	private AdvertisementRepository advertisementRepository;

	/* SERVICES */
	@Autowired
	private AgentService agentService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConfigurationSystemService configSystemService;

	@Autowired
	private Validator validator;

	/* CONSTRUCTOR */
	public AdvertisementService() {
		super();
	}

	public Advertisement create() {
		Advertisement ad;

		ad = new Advertisement();
		ad.setAgent(this.agentService.getAgentByUserAccountId(LoginService.getPrincipal().getId()));
		ad.setCreditCard(new CreditCard());
		ad.setStartMoment(new Date(System.currentTimeMillis() - 6));

		return ad;
	}

	public Advertisement findOne(int advertisementId) {
		Advertisement ad;

		Assert.notNull(advertisementId);
		ad = this.advertisementRepository.findOne(advertisementId);
		Assert.notNull(ad);

		return ad;
	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> ads;

		ads = this.advertisementRepository.findAll();
		Assert.notNull(ads);

		return ads;
	}

	public Advertisement save(Advertisement ad) {
		Advertisement adSaved;

		ad = this.addEndMoment(ad);
		Assert.notNull(ad);
		Assert.isTrue(ad.getAgent().getId() == this.agentService
				.getAgentByUserAccountId(LoginService.getPrincipal().getId()).getId());
		Assert.isTrue(ad.getStartMoment().before(ad.getEndMoment()));
		Assert.notNull(ad.getCreditCard());
		Assert.isTrue(!ad.getCreditCard().expired());

		adSaved = this.advertisementRepository.save(ad);
		Assert.notNull(adSaved);

		return adSaved;
	}

	public void delete(final Advertisement ad) {
		Assert.notNull(ad);

		Assert.isTrue(ad.getEndMoment().after(new Date(System.currentTimeMillis())));

		// Si es admin o el agent creador
		if ((this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()) != null)
				|| LoginService.getPrincipal().equals(ad.getAgent().getUserAccount())) {
			this.advertisementRepository.delete(ad);
		} else {
			// Lanzamos la excepcion del assert
			throw new IllegalArgumentException();
		}
	}

	public Collection<Advertisement> findAllByAgentId(int agentId) {
		Collection<Advertisement> ads;

		ads = this.advertisementRepository.findAllByAgentId(agentId);
		Assert.notNull(ads);

		return ads;
	}

	public Advertisement getOneAvailableAdvertisementByHikeId(int hikeId) {
		Collection<Advertisement> ads;
		Advertisement ad;
		int randomNum;

		ads = this.advertisementRepository.findAllAvailableAdvertisementsByHikeId(hikeId);
		Assert.notNull(ads);

		if (ads.size() > 0) {
			randomNum = ThreadLocalRandom.current().nextInt(0, ads.size());
			ad = (Advertisement) ads.toArray()[randomNum];
			Assert.notNull(ad);
		} else {
			ad = null;
		}

		return ad;
	}

	public Collection<Advertisement> findAllTabooAdvertisements() {
		Collection<Advertisement> ads;
		Map<Integer, Advertisement> tabooAds;
		String tabooWords;
		String[] tabooWordsArray;

		tabooAds = new HashMap<Integer, Advertisement>();
		Assert.notNull(this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
		tabooWords = this.configSystemService.get().getTabooWords();
		tabooWordsArray = tabooWords.toLowerCase().split(",");
		ads = this.advertisementRepository.findAll();

		for (Advertisement ad : ads) {
			for (String tabooWord : tabooWordsArray) {
				if (ad.getTitle().contains(tabooWord) && !tabooAds.containsKey(ad.getId())) {
					tabooAds.put(ad.getId(), ad);
				}
			}
		}

		return tabooAds.values();
	}

	public Map<String, Double> statsAdvertisements() {
		Map<String, Double> stats;
		Double ratio;

		stats = new HashMap<String, Double>();

		ratio = ((this.findAllTabooAdvertisements().size() * 1.00) / (this.findAll().size() * 1.00));
		stats.put("RATIO", ratio);

		return stats;
	}

	public Advertisement reconstruct(Advertisement advertisement, BindingResult binding) {
		Advertisement advertisementReconstructed;

		if (advertisement.getId() == 0) {
			advertisementReconstructed = this.create();
		} else {
			advertisementReconstructed = this.findOne(advertisement.getId());
		}

		advertisementReconstructed.setTitle(advertisement.getTitle());
		advertisementReconstructed.setBanner(advertisement.getBanner());
		advertisementReconstructed.setTargetUrl(advertisement.getTargetUrl());
		advertisementReconstructed.setDaysDisplayed(advertisement.getDaysDisplayed());
		advertisementReconstructed.setCreditCard(new CreditCard());
		advertisementReconstructed.getCreditCard().setHolderName(advertisement.getCreditCard().getHolderName());
		advertisementReconstructed.getCreditCard().setBrandName(advertisement.getCreditCard().getBrandName());
		advertisementReconstructed.getCreditCard().setCardNumber(advertisement.getCreditCard().getCardNumber());
		advertisementReconstructed.getCreditCard()
				.setExpirationMonth(advertisement.getCreditCard().getExpirationMonth());
		advertisementReconstructed.getCreditCard().setExpirationYear(advertisement.getCreditCard().getExpirationYear());
		advertisementReconstructed.getCreditCard().setCvvCode(advertisement.getCreditCard().getCvvCode());
		advertisementReconstructed.setHike(advertisement.getHike());
		advertisementReconstructed = this.addEndMoment(advertisementReconstructed);

		if ((advertisementReconstructed.getCreditCard().getExpirationMonth() != null)
				&& (advertisementReconstructed.getCreditCard().getExpirationYear() != null)
				&& advertisementReconstructed.getCreditCard().expired()) {
			binding.rejectValue("creditCard.expirationMonth", "advert.creditCard.expired");
			binding.rejectValue("creditCard.expirationYear", "advert.creditCard.expired");
		}

		if (!advertisementReconstructed.getEndMoment().after(new Date(System.currentTimeMillis()))) {
			binding.rejectValue("daysDisplayed", "advert.error.past");
		}

		this.validator.validate(advertisementReconstructed, binding);
		Assert.isTrue(!binding.hasErrors());

		return advertisementReconstructed;
	}

	private Advertisement addEndMoment(Advertisement ad) {
		GregorianCalendar cal;

		cal = new GregorianCalendar();
		cal.setTime(ad.getStartMoment());
		cal.add(Calendar.DATE, ad.getDaysDisplayed());
		ad.setEndMoment(cal.getTime());

		return ad;
	}

	public void flush() {
		this.advertisementRepository.flush();
	}
}
