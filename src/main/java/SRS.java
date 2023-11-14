import model.Course;
import model.EnrollmentStatus;
import model.Professor;
import model.Section;
import model.Student;
import model.StudentBody;
import utils.CourseDAO;
import utils.ProfessorDAO;
import utils.SectionDAO;

public class SRS {

    //student not connected YET to db
    public static StudentBody studentBody
            = new StudentBody();
    
    
    public static void main(String[] args) {
        Professor p1, p2, p3;
        Student s1, s2, s3;
        Course c1, c2, c3, c4, c5;
        Section sec1, sec2, sec3, sec4, sec5, sec6, sec7;
        String semester = "GL2023";
        
        // Professors
        p1 = new Professor("Jacquie Barker", "123-45-6789",
                "Adjunct Professor", "Information Technology");

        p2 = new Professor("John Smith", "567-81-2345",
                "Full Professor", "Chemistry");

        p3 = new Professor("Snidely Whiplash", "987-65-4321",
                "Full Professor", "Physical Education");
       
        ProfessorDAO.registerProfessor(p1);
        ProfessorDAO.registerProfessor(p2);
        ProfessorDAO.registerProfessor(p3);

        // Students
        s1 = new Student("Joe Blow", "111-11-1111", "Math", "M.S.");

        s2 = new Student("Fred Schnurd", "222-22-2222",
                "Information Technology", "Ph. D.");

        s3 = new Student("Mary Smith", "333-33-3333", "Physics", "B.S.");

        // ATTENTION
        // This part has not been modified to 
        // connect to the database
        studentBody.addStudent(s1);
        studentBody.addStudent(s2);
        studentBody.addStudent(s3);

        // --------
        // Courses.
        // --------
        c1 = new Course("CMP101",
                "Beginning Computer Technology", 3);

        c2 = new Course("OBJ101",
                "Object Methods for Software Development", 3);

        c3 = new Course("CMP283",
                "Higher Level Languages (Java)", 3);

        c4 = new Course("CMP999",
                "Living Brain Computers", 3);

        c5 = new Course("ART101",
                "Beginning Basketweaving", 3);

        CourseDAO.registerCourse(c1);
        CourseDAO.registerCourse(c2);
        CourseDAO.registerCourse(c3);
        CourseDAO.registerCourse(c4);
        CourseDAO.registerCourse(c5);

        // Establish some prerequisites (c1 => c2 => c3 => c4).
        c2.addPrerequisite(c1);
        c3.addPrerequisite(c2);
        c4.addPrerequisite(c3);

        // ---------
        // Sections.
        // ---------
        // Schedule sections of each Course by calling the
        // scheduleSection method of Course (which internally
        // invokes the Section constructor). 
        sec1 = c1.scheduleSection('M', "8:10 - 10:00 PM", "GOVT101", 30);

        sec2 = c1.scheduleSection('W', "6:10 - 8:00 PM", "GOVT202", 30);

        sec3 = c2.scheduleSection('R', "4:10 - 6:00 PM", "GOVT105", 25);

        sec4 = c2.scheduleSection('T', "6:10 - 8:00 PM", "SCI330", 25);

        sec5 = c3.scheduleSection('M', "6:10 - 8:00 PM", "GOVT101", 20);

        sec6 = c4.scheduleSection('R', "4:10 - 6:00 PM", "SCI241", 15);

        sec7 = c5.scheduleSection('M', "4:10 - 6:00 PM", "ARTS25", 40);
        
        SectionDAO.registerSection(sec1, semester);
        SectionDAO.registerSection(sec2, semester);
        SectionDAO.registerSection(sec3, semester); 
        SectionDAO.registerSection(sec4, semester);
        SectionDAO.registerSection(sec5, semester);
        SectionDAO.registerSection(sec6, semester);
        SectionDAO.registerSection(sec7, semester);
        
        
        // Recruit a professor to teach each of the sections.
        ProfessorDAO.addTaughtCourse(p3, sec1);
        ProfessorDAO.addTaughtCourse(p2, sec2);
        ProfessorDAO.addTaughtCourse(p1, sec3);
        ProfessorDAO.addTaughtCourse(p3, sec4);
        ProfessorDAO.addTaughtCourse(p1, sec5);
        ProfessorDAO.addTaughtCourse(p2, sec6);
        ProfessorDAO.addTaughtCourse(p3, sec7);

        
        // ATTENTION
        // This part until done has not been modified to 
        // connect to the database
        // only the schedule part
        System.out.println("===============================");
        System.out.println("Student registration has begun!");
        System.out.println("===============================");
        System.out.println();

        // Simulate students attempting to enroll in sections of
        // various courses.
        System.out.println("Student " + s1.getName()
                + " is attempting to enroll in "
                + sec1.toString());

        EnrollmentStatus status = sec1.enroll(s1);
        reportStatus(status);

        // Note the use of a special "housekeeping" method above, reportStatus(), 
        // to interpret and display the outcome of this enrollment request.
        // We could have combined the preceding two lines with
        // a single line instead, as follows:
        //
        //	reportStatus(sec1.enroll(s1));
        //
        // And, since the println() call just above that is also going to
        // be repeated multiple times, we could have combined ALL THREE 
        // LINES of code into a SINGLE line as follows:
        //
        //      attemptToEnroll(s1, sec1);
        // 
        // by writing a more elaborate "housekeeping" method, attemptToEnroll().
        // We will, in fact, do so, and will use the more concise syntax for the 
        // remainder of this program.
        // Try concurrently enrolling the same Student in a different Section
        // of the SAME Course!  This should fail.
        attemptToEnroll(s1, sec2);

        // This enrollment request should be fine ...
        attemptToEnroll(s2, sec2);

        // ... but here, the student in question hasn't satisfied the
        // prerequisities, so the enrollment request should be rejected.
        attemptToEnroll(s2, sec3);

        // These requests should both be fine. 
        attemptToEnroll(s2, sec7);
        attemptToEnroll(s3, sec1);

        // When the dust settles, here's what folks wound up
        // being SUCCESSFULLY registered for:
        //
        // sec1:  s1, s3
        // sec2:  s2
        // sec7:  s2
        // Semester is finished (boy, that was quick!).  
        // Professors assign grades for specific students.
        sec1.postGrade(s1, "C+");
        sec1.postGrade(s3, "A");
        sec2.postGrade(s2, "B+");
        sec7.postGrade(s2, "A-");

        // Let's see if everything got set up properly
        // by calling various display() methods.
        System.out.println("====================");
        System.out.println("Schedule of Classes:");
        System.out.println("====================");
        System.out.println();
        SectionDAO.initializerScheduleOfClasses(semester).display();

        System.out.println("======================");
        System.out.println("Professor Information:");
        System.out.println("======================");
        System.out.println();
        p1.display();
        p2.display();
        p3.display();

        System.out.println("====================");
        System.out.println("Student Information:");
        System.out.println("====================");
        System.out.println();
        s1.display();
        s2.display();
        s3.display();
    }

    // Note that this is a private static housekeeping method ...
    private static void reportStatus(EnrollmentStatus s) {
        System.out.println("Status:  " + s.value());
        System.out.println();
    }

    // ... as is this.
    private static void attemptToEnroll(Student s, Section sec) {
        System.out.println("Student " + s.getName()
                + " is attempting to enroll in "
                + sec.toString());

        // Utilize one housekeeping method from within another!
        reportStatus(sec.enroll(s));
    }
}
