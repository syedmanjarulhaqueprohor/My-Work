package Uni_Course_Management;

public class OnlineCourse extends Course{
    OnlineCourse(String name){
        super(name);
    }
    @Override
    public void submitAssignment(Student student,Assignment assignment){
        System.out.println(student.getName()+" submitted online "+assignment.getTitle());
    }
}
