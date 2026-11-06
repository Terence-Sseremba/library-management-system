public class StudentMember extends Member {
    private String studentNumber;
    private String regNumber;

    public StudentMember(String name, String studentNumber, String regNumber) {
        super(name);
        this.studentNumber = studentNumber;
        this.regNumber = regNumber;
    }

    public String getStudentNumber() { return studentNumber; }
    public String getRegNumber() { return regNumber; }

    @Override
    public int getBorrowLimit() { return 2; }
}
