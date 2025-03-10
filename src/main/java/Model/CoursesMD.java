package Model;

import java.sql.Date;

public class CoursesMD {
    private int courseId;
    private String subId;
    private String empId;
    private double fees;
    private int duration;
    private Date sOd;   //School opening day
    private Date dOc;   //Day of create
    private String depict;

    public CoursesMD() {
    }

    public CoursesMD(int courseId, String subId, String empId, double fees, int duration, Date sOd, Date dOc, String depict) {
        this.courseId = courseId;
        this.subId = subId;
        this.empId = empId;
        this.fees = fees;
        this.duration = duration;
        this.sOd = sOd;
        this.dOc = dOc;
        this.depict = depict;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public java.sql.Date getsOd() {
        return sOd;
    }

    public void setsOd(Date sOd) {
        this.sOd = sOd;
    }

    public java.sql.Date getdOc() {
        return dOc;
    }

    public void setdOc(Date dOc) {
        this.dOc = dOc;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }
}
