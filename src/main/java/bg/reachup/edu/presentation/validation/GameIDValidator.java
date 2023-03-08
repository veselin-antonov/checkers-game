package bg.reachup.edu.presentation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class GameIDValidator implements ConstraintValidator<GameID, CharSequence> {
    private static final Pattern pattern = Pattern.compile("\\d{1,19}");

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return pattern.matcher(value).matches();
    }
}
