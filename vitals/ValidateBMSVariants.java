package vitals;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class ValidateBMSVariants {

    private final Predicate<Float> variant;
    private final String message;
    private final Float value;

    public ValidateBMSVariants(
        Predicate<Float> variant,
        Float value,
        String message
    )
    {
        this.value = value;
        this.variant = variant;
        this.message = message;
    }

    public boolean isNotValid()
    {
        return variant.test(value);
    }

    public String getMessage()

    {
        return message;
    }

    public static boolean check(List<ValidateBMSVariants> validateBMSVariants) {
        ValidateBMSVariants batteryStatus =
            validateBMSVariants.stream().filter(ValidateBMSVariants::isNotValid).findFirst().orElse(null);
        if (batteryStatus != null) {
            System.out.println(batteryStatus.getMessage());
            return false;
        }
        return true;
    }
}
