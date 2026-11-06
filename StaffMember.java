// Staff use a staff number and department instead of student identifiers.
public class StaffMember extends Member {
    private String staffNumber;
    private String department;

    public StaffMember(String name, String staffNumber, String department) {
        super(name);
        this.staffNumber = staffNumber;
        this.department = department;
    }

    public String getStaffNumber() { return staffNumber; }
    public String getDepartment() { return department; }

    @Override
    public int getBorrowLimit() { return 3; }
}
