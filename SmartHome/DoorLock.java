package SmartHome;

import java.time.Duration;

class DoorLock implements Device{
    private String name;
    DoorLock(String name){
        this.name=name;
    }

    @Override
    public void turnOn() {
        System.out.println(name+" DoorLock turn On");
    }

    @Override
    public void turnOff() {
        System.out.println(name+" DoorLock turn Off");
    }

    @Override
    public void setTimer(Duration duration) {
        System.out.println(name+" DoorLock will be turn off in"+duration.toSeconds()+" seconds");
    }

    @Override
    public String getName() {
        return name;
    }
}
