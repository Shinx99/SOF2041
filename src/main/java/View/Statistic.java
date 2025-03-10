package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Statistic {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel panelMain;
    private JTable tbLearner;
    private JComboBox cbCourse;
    private JTable tbScBoard;
    private JTable tbSubject;
    private JComboBox cbYear;
    private JTable tbRevenue;
    private JFrame frame;

    public static void main(String[] args) {
        new Statistic();
    }

    public Statistic(){
        frame = new JFrame();
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createTable();

    }

    private void createTable(){
        tbScBoard.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Learner ID",
                        "Name",
                        "Score",
                        "Classify"
                }
        ));

        tbLearner.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Year",
                        "Learner Number",
                        "Earliest Signup",
                        "Last Signup"

                }
        ));

        tbSubject.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Subject",
                        "Students Number",
                        "Lowest",
                        "Hightest",
                        "Average"
                }
        ));

        tbRevenue.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Subject",
                        "Course Number",
                        "Student Number",
                        "Revenue",
                        "Lowest",
                        "Highest",
                        "Average"
                }

        ));
    }


    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTable getTbLearner() {
        return tbLearner;
    }

    public void setTbLearner(JTable tbLearner) {
        this.tbLearner = tbLearner;
    }

    public JComboBox getCbCourse() {
        return cbCourse;
    }

    public void setCbCourse(JComboBox cbCourse) {
        this.cbCourse = cbCourse;
    }

    public JTable getTbScBoard() {
        return tbScBoard;
    }

    public void setTbScBoard(JTable tbScBoard) {
        this.tbScBoard = tbScBoard;
    }

    public JTable getTbSubject() {
        return tbSubject;
    }

    public void setTbSubject(JTable tbSubject) {
        this.tbSubject = tbSubject;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JComboBox getCbYear() {
        return cbYear;
    }

    public void setCbYear(JComboBox cbYear) {
        this.cbYear = cbYear;
    }

    public JTable getTbRevenue() {
        return tbRevenue;
    }

    public void setTbRevenue(JTable tbRevenue) {
        this.tbRevenue = tbRevenue;
    }
}
