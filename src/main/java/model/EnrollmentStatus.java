package model;

public enum EnrollmentStatus {
    // Enumerate the values that the enum can assume.
    success("Enrollment successful!  :o)"),
    secFull("Enrollment failed;  section was full.  :op"),
    prereq("Enrollment failed; prerequisites not satisfied.  :op"),
    prevEnroll("Enrollment failed; previously enrolled.  :op");

    // This represents the value of an enum instance.
    private final String value;

    EnrollmentStatus(String value) {
        this.value = value;
    }
    
    // Accessor for the value of an enum instance.
    public String value() {
        return value;
    }
}
