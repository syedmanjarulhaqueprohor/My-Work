package SmartHome;

import java.time.Duration;

public class Thermostat implements Device{
    private String name;
    Thermostat(String name){
        this.name=name;
    }

    @Override
    public void turnOn() {
        System.out.println(name+" Thermostat turn On");
    }

    @Override
    public void turnOff() {
        System.out.println(name+" Thermostat turn Off");
    }

    @Override
    public void setTimer(Duration duration) {
        System.out.println(name+" Thermostat will be turn off in"+duration.toSeconds()+" seconds");
    }

    @Override
    public String getName() {
        return name;
    }
}
