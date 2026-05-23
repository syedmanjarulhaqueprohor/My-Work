package Uni_Course_Management;

public class Main {
    public static void main(String[] args) {
        Student student=new Student("Prohor");
        Professor professor=new Professor("Nazmun Nahar");

        Course onlineCourse =new OnlineCourse("OOP");
        Course offlineCourse=new OfflineCourse("DS");

        Assignment assignment1=new Assignment("OOP Assignment 1");
        Assignment assignment2=new Assignment("DS Assignment 1");

        onlineCourse.addAssignment(assignment1);
        offlineCourse.addAssignment(assignment2);

        onlineCourse.submitAssignment(student,assignment1);
        offlineCourse.submitAssignment(student,assignment2);

        professor.gardeAndAssignment(student,assignment1,3.75);
        professor.gardeAndAssignment(student,assignment2,3.50);

        student.viewGrade();
    }
}
