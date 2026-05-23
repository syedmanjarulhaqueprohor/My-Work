package SmartHome;

import java.time.Duration;

class Fan implements Device{
    private String name;
    Fan(String name){
        this.name=name;
    }

    @Override
    public void turnOn() {
        System.out.println(name+" Fan turn On");
    }

    @Override
     public void turnOff() {
        System.out.println(name+" Fan turn Off");
     }

    @Override
    public void setTimer(Duration duration) {
        System.out.println(name+" Fan will be turn off in"+duration.toSeconds()+" seconds");
    }

    public String getName() {
        return name;
    }
}
