package SmartHome;

import java.time.Duration;

public interface Device {
    void turnOn();
    void turnOff();
    void setTimer(Duration duration);
     String getName();
}
