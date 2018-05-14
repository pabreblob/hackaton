
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

		return this.configurationRepository.save(configuration);
	}

	public Configuration find() {
		return this.configurationRepository.findAll().get(0);
	}
}
