package bg.reachup.edu.presentation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

class EnumValueValidator implements ConstraintValidator<EnumValue, CharSequence> {
    private List<String> acceptedValues;
    private boolean caseSensitive;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        acceptedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
        caseSensitive = constraintAnnotation.caseSensitive();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        value = caseSensitive ? value.toString() : value.toString().toUpperCase();
        return acceptedValues.contains(value);
    }
}
