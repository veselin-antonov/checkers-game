package bg.reachup.edu.presentation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

class UsernameValidator implements ConstraintValidator<Username, CharSequence> {
    private static final Pattern pattern = Pattern.compile("\\w{6,18}");

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return pattern.matcher(value).matches();
    }
}
