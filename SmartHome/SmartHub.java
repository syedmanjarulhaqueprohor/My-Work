package SmartHome;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SmartHub {
    private Map<String,Device> deviceMap=new HashMap<>();

    public void registerDevice(Device device){
        deviceMap.put(device.getName(),device);
        System.out.println(device.getName()+"Register");
    }
    public void turnOnDevice(String name){
        Device device=deviceMap.get(name);
        if(device!=null){
            device.turnOn();
        }
        else{
            System.out.println("Device not found");
        }
    }
    public void turnOffDevice(String name){
        Device device=deviceMap.get(name);
        if(device!=null){
            device.turnOff();
        }
    }
    public void setTimer(String name, Duration duration){
        Device device=deviceMap.get(name);
        if(device!=null){
            device.setTimer(duration);
        }
    }

    public void turnAllOff(){
        for(Device d:deviceMap.values()){
            d.turnOff();
        }
    }
}
