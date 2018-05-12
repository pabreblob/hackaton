
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;
	@Autowired
	private AdminService			adminService;


	public ConfigurationService() {
		super();
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(this.adminService.findByPrincipal());

		Assert.notNull(configuration);
		Assert.notNull(this.configurationRepository.findOne(configuration.getId()));
		Assert.isTrue(!configuration.getBannerUrl().equals(""));
		Assert.notNull(configuration.getBannerUrl());
		Assert.isTrue(!configuration.getCurrency().equals(""));
		Assert.notNull(configuration.getCurrency());
		Assert.isTrue(configuration.getPricePerKm() >= 0);
		Assert.isTrue(configuration.getMinimumFee() >= 0);
		Assert.isTrue(configuration.getAdvertisementPrice() >= 0);
		Assert.isTrue(!configuration.getWelcomeEng().equals(""));
		Assert.notNull(configuration.getWelcomeEng());
		Assert.isTrue(!configuration.getWelcomeEsp().equals(""));
		Assert.notNull(configuration.getWelcomeEsp());
		Assert.isTrue(configuration.getLimitReportsWeek() >= 1);
		Assert.notNull(configuration.getFooter());
		Assert.isTrue(!configuration.getFooter().equals(""));
		Assert.isTrue(configuration.getVat() >= 0);
		Assert.notNull(configuration.getLegalTextEng());
		Assert.isTrue(!configuration.getLegalTextEng().equals(""));
		Assert.notNull(configuration.getLegalTextEsp());
		Assert.isTrue(!configuration.getLegalTextEsp().equals(""));
		Assert.notNull(configuration.getCookiesPolicyEng());
		Assert.isTrue(!configuration.getCookiesPolicyEng().equals(""));
		Assert.notNull(configuration.getCookiesPolicyEsp());
		Assert.isTrue(!configuration.getCookiesPolicyEsp().equals(""));
		Assert.notNull(configuration.getContactEng());
		Assert.isTrue(!configuration.getContactEng().equals(""));
		Assert.notNull(configuration.getContactEsp());
		Assert.isTrue(!configuration.getContactEsp().equals(""));
		Assert.notNull(configuration.getAcceptCookiesEng());
		Assert.isTrue(!configuration.getAcceptCookiesEng().equals(""));
		Assert.notNull(configuration.getAcceptCookiesEsp());
		Assert.isTrue(!configuration.getAcceptCookiesEsp().equals(""));
		Assert.isTrue(!configuration.getNationalities().isEmpty());

		return this.configurationRepository.save(configuration);
	}

	public Configuration find() {
		return this.configurationRepository.findAll().get(0);
	}
}
