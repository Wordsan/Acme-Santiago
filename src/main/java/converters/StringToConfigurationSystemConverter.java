package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ConfigurationSystemRepository;
import domain.ConfigurationSystem;

@Component
@Transactional
public class StringToConfigurationSystemConverter implements Converter<String, ConfigurationSystem> {

	@Autowired
	ConfigurationSystemRepository	configurationsystemRepository;


	@Override
	public ConfigurationSystem convert(final String text) {
		ConfigurationSystem result;
		int id;

		try {
		
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.configurationsystemRepository.findOne(id);
			}
			
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
