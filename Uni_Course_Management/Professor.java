package Uni_Course_Management;

public class Professor {
    private String name;
    Professor(String name){
        this.name=name;
    }
    public void gardeAndAssignment(Student student,Assignment assignment,double point){
        Grade grade=new Grade(assignment,point);
        student.addGrade(grade);
    }
}
