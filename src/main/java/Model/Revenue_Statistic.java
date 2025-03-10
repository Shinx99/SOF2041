package Model;

import java.util.Date;

public class Revenue_Statistic {
    //Revenue
    private String St_SubName;
    private int St_quantityCourse;
    private int St_quantityStudent;
    private double St_revenue;
    private double St_lowest;
    private double St_hightest;
    private double St_avg;

    public Revenue_Statistic() {
    }

    public Revenue_Statistic(String st_SubName, int st_quantityCourse, int st_quantityStudent, double st_revenue, double st_lowest, double st_hightest, double st_avg) {
        St_SubName = st_SubName;
        St_quantityCourse = st_quantityCourse;
        St_quantityStudent = st_quantityStudent;
        St_revenue = st_revenue;
        St_lowest = st_lowest;
        St_hightest = st_hightest;
        St_avg = st_avg;
    }

    public Revenue_Statistic(String st_SubName, int st_quantityCourse, double st_revenue, double st_lowest, double st_hightest, double st_avg) {
        St_SubName = st_SubName;
        St_quantityCourse = st_quantityCourse;
        St_revenue = st_revenue;
        St_lowest = st_lowest;
        St_hightest = st_hightest;
        St_avg = st_avg;
    }

    public String getSt_SubName() {
        return St_SubName;
    }

    public void setSt_SubName(String st_SubName) {
        St_SubName = st_SubName;
    }

    public int getSt_quantityCourse() {
        return St_quantityCourse;
    }

    public void setSt_quantityCourse(int st_quantityCourse) {
        St_quantityCourse = st_quantityCourse;
    }

    public double getSt_revenue() {
        return St_revenue;
    }

    public void setSt_revenue(double st_revenue) {
        St_revenue = st_revenue;
    }

    public double getSt_lowest() {
        return St_lowest;
    }

    public void setSt_lowest(double st_lowest) {
        St_lowest = st_lowest;
    }

    public double getSt_hightest() {
        return St_hightest;
    }

    public void setSt_hightest(double st_hightest) {
        St_hightest = st_hightest;
    }

    public double getSt_avg() {
        return St_avg;
    }

    public void setSt_avg(double st_avg) {
        St_avg = st_avg;
    }

    public int getSt_quantityStudent() {
        return St_quantityStudent;
    }

    public void setSt_quantityStudent(int st_quantityStudent) {
        St_quantityStudent = st_quantityStudent;
    }
}
