package Hospital_Management_System;

import java.util.ArrayList;
import java.util.List;

class Doctor extends Person{
    private String specialization;
    private List<Appointment> appointments;
    Doctor(String id,String name,int age,String specialization){
        super(id, name, age);
        this.specialization=specialization;
        this.appointments=new ArrayList<>();
    }
    public void addAppointment(Appointment appointment) throws Exception{
        for(Appointment i:appointments){
            if(i.getTime().equals(appointment.getTime())){
                throw new Exception("Appointment overlap");
            }
        }
        appointments.add(appointment);
    }
}
