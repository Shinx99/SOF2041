package Model;

public class ScoreBoard_Statistic {
    //Score Board
    private String St_learnId;
    private String St_name;
    private double St_scoreLn;
    private String St_Classify;

    public ScoreBoard_Statistic() {
    }

    public ScoreBoard_Statistic(String st_learnId, String st_name, double st_scoreLn, String st_Classify) {
        St_learnId = st_learnId;
        St_name = st_name;
        St_scoreLn = st_scoreLn;
        St_Classify = st_Classify;
    }

    public ScoreBoard_Statistic(String st_learnId, String st_name, double st_scoreLn) {
        St_learnId = st_learnId;
        St_name = st_name;
        St_scoreLn = st_scoreLn;
    }

    public String getSt_learnId() {
        return St_learnId;
    }

    public void setSt_learnId(String st_learnId) {
        St_learnId = st_learnId;
    }

    public String getSt_name() {
        return St_name;
    }

    public void setSt_name(String st_name) {
        St_name = st_name;
    }

    public double getSt_scoreLn() {
        return St_scoreLn;
    }

    public void setSt_scoreLn(double st_scoreLn) {
        St_scoreLn = st_scoreLn;
    }

    public String getSt_Classify() {
        return St_Classify;
    }

    public void setSt_Classify(String st_Classify) {
        St_Classify = st_Classify;
    }
}
