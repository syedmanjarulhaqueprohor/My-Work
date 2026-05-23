package Uni_Course_Management;

public class OfflineCourse extends Course{
    OfflineCourse(String name){
        super(name);
    }
    @Override
    public void submitAssignment(Student student,Assignment assignment){
        System.out.println(student.getName()+" submitted offline "+assignment.getTitle());
    }
}
