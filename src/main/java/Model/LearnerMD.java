package Model;

import java.sql.Date;

public class LearnerMD {
    private String lnId;
    private String nameLn;
    private Date birthday;
    private boolean gender;
    private String phone;
    private String email;
    private String depict;
    private String empID;
    private Date dOc;   //day of create

    public LearnerMD() {
    }

    public LearnerMD(String lnId, String nameLn, Date birthday, boolean gender, String phone, String email, String depict, String empID, Date dOc) {
        this.lnId = lnId;
        this.nameLn = nameLn;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.depict = depict;
        this.empID = empID;
        this.dOc = dOc;
    }

    public LearnerMD(String lnId, String nameLn, Date birthday, boolean gender, String phone, String email, String note) {
        this.lnId = lnId;
        this.nameLn = nameLn;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.depict = depict;
    }

    public String getLnId() {
        return lnId;
    }

    public void setLnId(String lnId) {
        this.lnId = lnId;
    }

    public String getNameLn() {
        return nameLn;
    }

    public void setNameLn(String nameLn) {
        this.nameLn = nameLn;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public Date getdOc() {
        return dOc;
    }

    public void setdOc(Date dOc) {
        this.dOc = dOc;
    }
}
