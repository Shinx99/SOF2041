package Model;

public class SubjectMD {
    private String subId;
    private String subName;
    private double fees;
    private int duration;
    private String picture;
    private String depict;

    public SubjectMD() {
    }

    public SubjectMD(String subId, String subName, double fees, int duration, String picture, String depict) {
        this.subId = subId;
        this.subName = subName;
        this.fees = fees;
        this.duration = duration;
        this.picture = picture;
        this.depict = depict;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }
}
