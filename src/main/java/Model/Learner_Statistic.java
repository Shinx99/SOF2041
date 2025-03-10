package Model;

import java.util.Date;

public class Learner_Statistic {
    //Learner
    private int St_year;
    private int St_quantityLn;
    private Date St_FirstDOS;
    private Date St_LastDOS;

    public Learner_Statistic() {
    }

    public Learner_Statistic(int st_year, int st_quantityLn, Date st_FirstDOS, Date st_LastDOS) {
        St_year = st_year;
        St_quantityLn = st_quantityLn;
        St_FirstDOS = st_FirstDOS;
        St_LastDOS = st_LastDOS;
    }

    public int getSt_year() {
        return St_year;
    }

    public void setSt_year(int st_year) {
        St_year = st_year;
    }

    public int getSt_quantityLn() {
        return St_quantityLn;
    }

    public void setSt_quantityLn(int st_quantityLn) {
        St_quantityLn = st_quantityLn;
    }

    public Date getSt_FirstDOS() {
        return St_FirstDOS;
    }

    public void setSt_FirstDOS(Date st_FirstDOS) {
        St_FirstDOS = st_FirstDOS;
    }

    public Date getSt_LastDOS() {
        return St_LastDOS;
    }

    public void setSt_LastDOS(Date st_LastDOS) {
        St_LastDOS = st_LastDOS;
    }
}

