package vitals;

public final class Main {
    private Main(){

    }

    public static void main(String[] args) {
        assert(LiIonBattery.isBatteryOk(25, 70, 0.7f) == true);
        assert(LiIonBattery.isBatteryOk(50, 85, 0.0f) == false);
        assert(LiIonBattery.isBatteryOk(-1, 85, 0.0f) == false);
        assert(LiIonBattery.isBatteryOk(25, 85, 0.0f) == false);
        assert(LiIonBattery.isBatteryOk(25, 15, 0.0f) == false);
        assert(LiIonBattery.isBatteryOk(25, 25, 0.9f) == false);
    }
}
