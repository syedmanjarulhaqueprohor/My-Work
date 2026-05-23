package Uni_Course_Management;

import java.util.ArrayList;
import java.util.List;

public class Course {
    protected String name;
    protected List<Assignment> assignments=new ArrayList<>();
    Course(String name){
        this.name=name;
    }
    public void addAssignment(Assignment assignment){
        assignments.add(assignment);
    }
    public void submitAssignment(Student student,Assignment assignment){

    }
}
