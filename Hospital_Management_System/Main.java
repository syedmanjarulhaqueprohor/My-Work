package Hospital_Management_System;

public class Main {
    public static void main(String[] args) {
        Doctor doctor=new Doctor("D1","Dr.Prohor",30,"Cardiology");
        Doctor doctor1=new Doctor("D2","Dr.Rafi",32,"Neurologist");
        Nurse nurse=new Nurse("Priyanka","N1",25,"Night");
        Patient patient1=new Patient("Karim","P1",45);
        Patient patient2=new Patient("Karim","P1",45);
        try{
            Appointment a1=new Appointment(doctor,patient1,"10:00 AM");
            doctor.addAppointment(a1);
            Appointment a2= new Appointment(doctor1,patient2,"10:00 AM");
            doctor1.addAppointment(a2);
            boolean x=true;
            Medicine m1=new Medicine("Napa",20.00);
            Medicine m2=new Medicine("Z-max",90.00);
            patient1.addMedicine(m1);
            patient2.addMedicine(m2);
            Bill bill1=new Bill(patient1,800);
            Bill bill2=new Bill(patient2,800);
            if(x){
                System.out.println("Total:" + bill1.calculateBill());
                System.out.println("Total:" + bill2.calculateBill());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
