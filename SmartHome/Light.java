package SmartHome;

import java.time.Duration;

class Light implements Device {
    private String name;
    Light(String name){
        this.name=name;
    }

    @Override
    public void turnOn() {
        System.out.println(name+" Light turn On");
    }

    @Override
    public void turnOff() {
        System.out.println(name+" Light turn Off");
    }

    @Override
    public void setTimer(Duration duration) {
        System.out.println(name+" Light will be turn off in "+duration.toSeconds()+" seconds");
    }

    @Override
    public String getName() {
        return name;
    }
}
