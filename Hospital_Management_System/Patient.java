package Hospital_Management_System;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Patient extends Person{
    private String medical_history;
    private List<Medicine> medicines;
    Patient(String name,String id,int age){
        super(id, name, age);
        this.medicines=new ArrayList<>();
    }
    public void setMedical_history(String medical_history) {
        this.medical_history = medical_history;
    }
    public String getMedical_history() {
        return medical_history;
    }
    public void addMedicine(Medicine medicine){
        medicines.add(medicine);
    }
    public List<Medicine> getMedicines() {
        return medicines;
    }
}
