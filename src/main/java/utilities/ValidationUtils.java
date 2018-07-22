package utilities;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtils {
	public static <Type> void validateBean(Type bean)
	{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Type>> errors = validator.validate(bean);

		if (!errors.isEmpty()) {
			throw new ConstraintViolationException(
				"validateBean failed: " + errors.toString(),
				new HashSet<ConstraintViolation<?>>(errors)
			);
		}
	}
}
