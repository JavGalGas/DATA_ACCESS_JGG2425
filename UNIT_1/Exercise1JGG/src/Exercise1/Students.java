package Exercise1;

import java.util.ArrayList;

public class Students
{
    private final ArrayList<Student> studentsList = new ArrayList<>();

    // Adds a new student on the list
    //
    public void addStudent(Student alu) {
        studentsList.add(alu);
    }

    // Gets the student whose position is num
    //
    public Student getStudent(int num) throws NullPointerException{
        if (num >= 0 && num <= studentsList.size())
        {
            return (studentsList.get(num));
        }
        return null;
    }

    // Gets the average grade of the students
    //
    public float getAverage() {
        if (studentsList.isEmpty())
            return 0;
        else
        {
            float average = 0;
            for (Student student : studentsList) {
                average += (student).getGrade();
            }
            return (average / studentsList.size());
        }
    }
}
