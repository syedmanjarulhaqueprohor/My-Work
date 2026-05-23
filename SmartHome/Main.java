package SmartHome;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        SmartHub hub = new SmartHub();

        Device light = new Light("LivingRoom");
        Device fan = new Fan("Bedroom");
        Device thermo = new Thermostat("Hall");
        Device lock = new DoorLock("MainGate");

        hub.registerDevice(light);
        hub.registerDevice(fan);
        hub.registerDevice(thermo);
        hub.registerDevice(lock);

        hub.turnOnDevice("LivingRoom");
        hub.turnOnDevice("Bedroom");

        hub.setTimer("LivingRoom", Duration.ofSeconds(10));
        hub.setTimer("Bedroom", Duration.ofSeconds(5));

        hub.turnAllOff();
    }
}
