package model;

import java.util.HashMap;

/**
 *
 * @author AERO
 */
public class StudentBody {
       private HashMap<String, Student> students;

    public StudentBody() {
        this.students = new HashMap<String, Student>();
    }
    
    public void display()
    {
        for(Student s : students.values())
        {
            s.display();
            System.out.println("");
        }
    }
    
    public void addStudent(Student s)
    {
        students.put(s.getSsn(), s);
    }
    
    public Student findStudent(String ssn)
    {
        return (Student) students.get(ssn);
    }
    
    public boolean isEmpty()
    {
        if(students.size() == 0) return true;
        else return false;
    } 
}
