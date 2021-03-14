package vitals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import static vitals.BatteryManagementSystem.*;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class LiIonBattery {

    private Map<BatteryManagementSystem, Float> input;

    public LiIonBattery(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        saveInput(temperature, soc, chargeRate);
    }

    private void saveInput(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        input = new TreeMap<>();
        input.put(TEMPERATURE, temperature);
        input.put(SOC, soc);
        input.put(CHARGINGRATE, chargeRate);
    }

    public static Predicate<Float> isAboveThreshold(float maxThreshold) {
        return value -> value > maxThreshold;
    }

    public static Predicate<Float> isBelowThreshold(float minThreshold) {
        return value -> value < minThreshold;
    }

    private List<ValidateBMSVariants> variants() {
        List<ValidateBMSVariants> validateBMSVariants = new ArrayList<>();
        validateBMSVariants.add(new ValidateBMSVariants(isBelowThreshold(Temperature.MIN_TEMPERATURE_THRESHOLD),
              input.get(TEMPERATURE), "Temperature is below threshold value"
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isAboveThreshold(Temperature.MAX_TEMPERATURE_THRESHOLD),
              input.get(TEMPERATURE), "Temperature is above threshold value"
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isBelowThreshold(StateOfCharge.MIN_SOC),
              input.get(SOC), "StateOfCharge is below threshold value"
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isAboveThreshold(StateOfCharge.MAX_SOC),
              input.get(SOC), "StateOfCharge is above threshold value"
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isAboveThreshold(ChargingRate.MAX_CHARGING_RATE),
              input.get(CHARGINGRATE), "ChargingRate is above threshold value"
        ));
        return validateBMSVariants;
    }

    public static boolean isBatteryOk(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        LiIonBattery lionBattery = new LiIonBattery(temperature, soc, chargeRate);
        ValidateBMSVariants validateBMSVariants = ValidateBMSVariants.check(lionBattery.variants());
        if (validateBMSVariants != null) {
            System.out.println(validateBMSVariants.getMessage());
            return false;
        }
        return true;
    }

    public static final class Temperature {

        private static final float MAX_TEMPERATURE_THRESHOLD = 45;
        private static final float MIN_TEMPERATURE_THRESHOLD = 0;

        private Temperature() {
        }
    }

    public static final class StateOfCharge {

        private static final float MAX_SOC = 80;
        private static final float MIN_SOC = 20;

        private StateOfCharge() {
        }
    }

    public static final class ChargingRate {

        private static final float MAX_CHARGING_RATE = 0.8f;

        private ChargingRate() {
        }
    }

}
