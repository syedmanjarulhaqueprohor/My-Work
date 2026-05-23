package Uni_Course_Management;

public class Grade {
private Assignment assignment;
private double point;
Grade(Assignment assignment,double point){
    this.assignment=assignment;
    this.point=point;
}
@Override
    public String toString(){
    return assignment.getTitle() + " : " + point;
}
}
