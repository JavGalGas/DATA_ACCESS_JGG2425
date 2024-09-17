package Exercise1;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Juan");
        System.out.println(student.getName());  //Juan
        student.setGrade(4);
        System.out.println(student.getGrade()); //4
        System.out.println(student.Passed());   //false

        Students studentsList = new Students();
        studentsList.addStudent(student);
        Student student1 = studentsList.getStudent(0);
        System.out.println(student.equals(student1)); //true
        student1 = new Student();
        student1.setGrade(8);
        studentsList.addStudent(student1);
        System.out.println(studentsList.getAverage());  //6
    }
}
