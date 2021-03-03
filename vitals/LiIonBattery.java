package vitals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class LiIonBattery {

    private Map<BMS, Float> input;

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
        input.put(BMS.TEMPERATURE, temperature);
        input.put(BMS.SOC, soc);
        input.put(BMS.CHARGINGRATE, chargeRate);
    }

    public static final class Temperature {

        private static final float MAX_TEMPERATURE_THRESHOLD = 45;
        private static final float MIN_TEMPERATURE_THRESHOLD = 0;

        private Temperature() {
        }

        public static Predicate<Float> isBeyondThreshold() {
            return soc -> soc > MAX_TEMPERATURE_THRESHOLD;
        }

        public static Predicate<Float> isBelowThreshold() {
            return soc -> soc < MIN_TEMPERATURE_THRESHOLD;
        }
    }

    public static final class StateOfCharge {

        private static final float MAX_SOC = 80;
        private static final float MIN_SOC = 20;

        private StateOfCharge() {
        }

        public static Predicate<Float> isBeyondThreshold() {
            return soc -> soc > MAX_SOC;
        }

        public static Predicate<Float> isBelowThreshold() {
            return soc -> soc < MIN_SOC;
        }
    }

    public static final class ChargingRate {

        private static final float MAX_CHARGING_RATE = 0.8f;

        private ChargingRate() {
        }

        public static Predicate<Float> isBeyondThreshold() {
            return soc -> soc > MAX_CHARGING_RATE;
        }
    }

    private List<ValidateBMSVariants> variants() {
        List<ValidateBMSVariants> validateBMSVariants = new ArrayList<>();
        validateBMSVariants.add(new ValidateBMSVariants(Temperature.isBelowThreshold()
            .test(input.get(BMS.TEMPERATURE)), "Temperature is below threshold value"));
        validateBMSVariants.add(new ValidateBMSVariants(Temperature.isBeyondThreshold()
            .test(input.get(BMS.TEMPERATURE)), "Temperature is above threshold value"));
        validateBMSVariants.add(new ValidateBMSVariants(StateOfCharge.isBelowThreshold()
            .test(input.get(BMS.SOC)), "StateOfCharge is below threshold value"));
        validateBMSVariants.add(new ValidateBMSVariants(StateOfCharge.isBeyondThreshold()
            .test(input.get(BMS.SOC)), "StateOfCharge is above threshold value"));
        validateBMSVariants.add(new ValidateBMSVariants(ChargingRate.isBeyondThreshold()
            .test(input.get(BMS.CHARGINGRATE)), "ChargingRate is above threshold value"));
        return validateBMSVariants;
    }

    public static boolean isBatteryOk(
        float temperature,
        float soc,
        float chargeRate
    )
    {
        LiIonBattery lionBattery = new LiIonBattery(temperature, soc, chargeRate);
        return ValidateBMSVariants.check(lionBattery.variants());
    }

}
