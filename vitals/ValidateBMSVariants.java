package vitals;

import java.util.List;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class ValidateBMSVariants {

    private final boolean variant;
    private final String message;

    public ValidateBMSVariants(
        boolean variant,
        String message
    )
    {
        this.variant = variant;
        this.message = message;
    }

    public boolean isNotValid()
    {
        return variant;
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
