package model;

import java.util.ArrayList;
import java.util.Collection;

public class Student extends Person {
    private String major;
    private String degree;
    private Transcript transcript;
    private ArrayList<Section> attends;

    public Student(String name, String ssn, String major, String degree) {
        super(name, ssn);
        this.setMajor(major);
        this.setDegree(degree);
        this.setTranscript(new Transcript(this));
        this.attends = new ArrayList<Section>();
    }

    public Student(String ssn) {
        this("TBD", ssn, "TBD", "TBD");
    }

    
    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }

    public void setTranscript(Transcript t) {
        transcript = t;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void display() {
        super.display();
        System.out.println("Student-Specific Information:");
        System.out.println("\tMajor:  " + this.getMajor());
        System.out.println("\tDegree:  " + this.getDegree());
        this.displayCourseSchedule();
        this.printTranscript();
        System.out.println();
    }

    public String toString() {
        return this.getName() + " (" + this.getSsn() + ") [" + this.getDegree()
                + " - " + this.getMajor() + "]";
    }

    public void displayCourseSchedule() {
        System.out.println("Course Schedule for " + this.getName());

        for (Section s : attends) {
            if (s.getGrade(this) == null) {
                System.out.println("\tCourse No.:  "
                        + s.getRepresentedCourse().getCourseNo());
                System.out.println("\tSection No.:  "
                        + s.getSectionNo());
                System.out.println("\tCourse Name:  "
                        + s.getRepresentedCourse().getCourseName());
                System.out.println("\tMeeting Day and Time Held:  "
                        + s.getDayOfWeek() + " - "
                        + s.getTimeOfDay());
                System.out.println("\tRoom Location:  "
                        + s.getRoom());
                System.out.println("\tProfessor's Name:  "
                        + s.getInstructor().getName());
                System.out.println("\t-----");
            }
        }
    }

    public void addSection(Section s) {
        attends.add(s);
    }

    public void dropSection(Section s) {
        attends.remove(s);
    }

    // Determine whether the Student is already enrolled in THIS
    // EXACT Section.
    public boolean isEnrolledIn(Section s) {
        if (attends.contains(s)) {
            return true;
        } else {
            return false;
        }
    }

    // Determine whether the Student is already enrolled in ANOTHER
    // Section of this SAME Course.
    public boolean isCurrentlyEnrolledInSimilar(Section s1) {
        boolean foundMatch = false;

        Course c1 = s1.getRepresentedCourse();

        for (Section s2 : attends) {
            Course c2 = s2.getRepresentedCourse();
            if (c1 == c2) {
                // There is indeed a Section in the attends()
                // ArrayList representing the same Course.
                // Check to see if the Student is CURRENTLY
                // ENROLLED (i.e., whether or not he has
                // yet received a grade).  If there is no
                // grade, he/she is currently enrolled; if
                // there is a grade, then he/she completed
                // the course some time in the past.
                if (s2.getGrade(this) == null) {
                    // No grade was assigned!  This means
                    // that the Student is currently
                    // enrolled in a Section of this
                    // same Course.
                    foundMatch = true;
                    break;
                }
            }
        }

        return foundMatch;
    }

    public void printTranscript() {
        this.getTranscript().display();
    }

    public Collection<Section> getEnrolledSections() {
        return attends;
    }
}
