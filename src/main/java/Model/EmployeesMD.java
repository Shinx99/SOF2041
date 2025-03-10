package Model;

public class EmployeesMD {
    private String empId;
    private String password;
    private String username;
    private boolean role;
    private String gmail;
    private  String level;// Thêm cột Gmail
    private String Gender;

    public EmployeesMD() {}

    public EmployeesMD(String empId, String password, String username, boolean role, String gmail, String level, String gender) {
        this.empId = empId;
        this.password = password;
        this.username = username;
        this.role = role;
        this.gmail = gmail;
        this.level = level;
        Gender = gender;
    }

    public EmployeesMD(String empId, String password, String username, boolean role, String gmail, String level) {
        this.empId = empId;
        this.password = password;
        this.username = username;
        this.role = role;
        this.gmail = gmail;
        this.level = level;
    }

    public EmployeesMD(String empId, String password, String username, boolean role, String gmail) {
        this.empId = empId;
        this.password = password;
        this.username = username;
        this.role = role;
        this.gmail = gmail;
    }

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isRole() { return role; }
    public void setRole(boolean role) { this.role = role; }

    public String getGmail() { return gmail; }
    public void setGmail(String gmail) { this.gmail = gmail; }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
