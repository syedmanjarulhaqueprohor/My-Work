package Hospital_Management_System;

class Nurse extends Person{
    private String shift;
    Nurse(String name,String id,int age,String shift){
        super(id, name, age);
        this.shift=shift;
    }
}
