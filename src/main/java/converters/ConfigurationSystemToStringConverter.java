package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ConfigurationSystem;

@Component
@Transactional
public class ConfigurationSystemToStringConverter implements Converter<ConfigurationSystem, String> {

	@Override
	public String convert(final ConfigurationSystem configurationsystem) {

		String result;
		
		if (configurationsystem == null) {
			result = null;
		} else {
			result = String.valueOf(configurationsystem.getId());
		}
		
		return result;
	}

}