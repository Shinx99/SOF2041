package Model;

public class SubjectScore_Statistic {
    //Subject Score
    private String St_subId;
    private int St_quantityStudent;
    private double St_MinScore;
    private  double St_MaxScore;
    private  double St_AvgScore;

    public SubjectScore_Statistic() {
    }

    public SubjectScore_Statistic(String st_subId, int st_quantityStudent, double st_MinScore, double st_MaxScore, double st_AvgScore) {
        St_subId = st_subId;
        St_quantityStudent = st_quantityStudent;
        St_MinScore = st_MinScore;
        St_MaxScore = st_MaxScore;
        St_AvgScore = st_AvgScore;
    }

    public String getSt_subId() {
        return St_subId;
    }

    public void setSt_subId(String st_subId) {
        St_subId = st_subId;
    }

    public int getSt_quantityStudent() {
        return St_quantityStudent;
    }

    public void setSt_quantityStudent(int st_quantityStudent) {
        St_quantityStudent = st_quantityStudent;
    }

    public double getSt_MinScore() {
        return St_MinScore;
    }

    public void setSt_MinScore(double st_MinScore) {
        St_MinScore = st_MinScore;
    }

    public double getSt_MaxScore() {
        return St_MaxScore;
    }

    public void setSt_MaxScore(double st_MaxScore) {
        St_MaxScore = st_MaxScore;
    }

    public double getSt_AvgScore() {
        return St_AvgScore;
    }

    public void setSt_AvgScore(double st_AvgScore) {
        St_AvgScore = st_AvgScore;
    }
}
