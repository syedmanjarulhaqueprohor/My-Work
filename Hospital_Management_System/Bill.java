package Hospital_Management_System;

import java.util.List;

public class Bill {
    private Patient patient;
    private double visitingFee;
    private List<Medicine> medicines;
    Bill(Patient patient,double visitingFee){
        this.patient=patient;
        this.visitingFee=visitingFee;
        this.medicines=patient.getMedicines();
    }
    public double calculateBill(){
        double total=visitingFee;
        for(Medicine medicine:medicines){
            total+=medicine.getPrice();
        }
        return total;
    }
}
