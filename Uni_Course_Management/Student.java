package Uni_Course_Management;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<Grade> grades=new ArrayList<>();
    Student(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void viewGrade(){
        for(Grade grade:grades){
            System.out.println(grade);
        }
    }
    protected void addGrade(Grade grade){
        grades.add(grade);
    }

}
