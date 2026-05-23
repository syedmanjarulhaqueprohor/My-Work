package Hospital_Management_System;

class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String time;
     Appointment(Doctor doctor,Patient patient,String time){
         this.doctor=doctor;
         this.patient=patient;
         this.time=time;
     }
     public String getTime(){
         return time;
     }
     public Patient getPatient(){
         return patient;
     }
}
