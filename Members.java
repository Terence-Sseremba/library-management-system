
// NAAMALA MARY BEATRICE
// stores the details of the person registering at the library.

public class Members {

    private String name;
    private String studentNumber;
    private String regNumber;

    // Constructor: sets all details once, when the member is created
    public Members(String name, String studentNumber, String regNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.regNumber = regNumber;
    }

    // Getters so other classes can read the details
    public String getName() { return name; }
    public String getStudentNumber() { return studentNumber; }
    public String getRegNumber() { return regNumber; }
}
