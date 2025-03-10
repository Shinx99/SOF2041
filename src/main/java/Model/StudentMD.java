package Model;

public class StudentMD {
    private int sdId;
    private int subId;
    private String lnId;
    private String sdName;
    private double score;

    public StudentMD() {
    }

    public StudentMD(int sdId, int subId, String lnId, double score) {
        this.sdId = sdId;
        this.subId = subId;
        this.lnId = lnId;
        this.score = score;
    }

    public StudentMD(int sdId, String lnId, String sdName, double score) {
        this.sdId = sdId;
        this.lnId = lnId;
        this.sdName = sdName;
        this.score = score;
    }

    public int getSdId() {
        return sdId;
    }

    public void setSdId(int sdId) {
        this.sdId = sdId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getLnId() {
        return lnId;
    }

    public void setLnId(String lnId) {
        this.lnId = lnId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }
}
