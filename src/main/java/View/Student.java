package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Student {
    private JPanel panelMain;
    private JTextField txtScore;
    private JTable tbStudent;
    private JButton btnAdd;
    private JRadioButton rdoNotEntered;
    private JRadioButton rdoEntered;
    private JButton btnUpdate;
    private JComboBox cbSub;
    private JButton btnDelete;
    private JComboBox cbCourse;
    private JTabbedPane tabbedPane1;
    private JTextField txtSearch;
    private JTable tbLearner;
    private JRadioButton rbtNoSpoint;
    private JRadioButton rbtAll;
    private JRadioButton rbtEnteredScore;
    private JFrame frame;

    public static void main(String[] args) {
        new Student();
    }

    public Student(){
        frame = new JFrame("Student Management");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        CreateTable();

    }

    private void CreateTable(){
        tbStudent.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Order",
                        "Student ID",
                        "Learner ID",
                        "Name",
                        "Score"
                }
        ));

        tbLearner.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Learner ID",
                        "Name",
                        "Gender",
                        "Date of birth",
                        "Phone number",
                        "Email"
                }
        ));
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTextField getTxtScore() {
        return txtScore;
    }

    public void setTxtScore(JTextField txtScore) {
        this.txtScore = txtScore;
    }

    public JTable getTbStudent() {
        return tbStudent;
    }

    public void setTbStudent(JTable tbStudent) {
        this.tbStudent = tbStudent;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JRadioButton getRdoNotEntered() {
        return rdoNotEntered;
    }

    public void setRdoNotEntered(JRadioButton rdoNotEntered) {
        this.rdoNotEntered = rdoNotEntered;
    }

    public JRadioButton getRdoEntered() {
        return rdoEntered;
    }

    public void setRdoEntered(JRadioButton rdoEntered) {
        this.rdoEntered = rdoEntered;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JComboBox getCbSub() {
        return cbSub;
    }

    public void setCbSub(JComboBox cbSub) {
        this.cbSub = cbSub;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JComboBox getCbCourse() {
        return cbCourse;
    }

    public void setCbCourse(JComboBox cbCourse) {
        this.cbCourse = cbCourse;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(JTextField txtSearch) {
        this.txtSearch = txtSearch;
    }

    public JTable getTbLearner() {
        return tbLearner;
    }

    public void setTbLearner(JTable tbLearner) {
        this.tbLearner = tbLearner;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JRadioButton getRbtNoSpoint() {
        return rbtNoSpoint;
    }

    public void setRbtNoSpoint(JRadioButton rbtNoSpoint) {
        this.rbtNoSpoint = rbtNoSpoint;
    }

    public JRadioButton getRbtAll() {
        return rbtAll;
    }

    public void setRbtAll(JRadioButton rbtAll) {
        this.rbtAll = rbtAll;
    }

    public JRadioButton getRbtEnteredScore() {
        return rbtEnteredScore;
    }

    public void setRbtEnteredScore(JRadioButton rbtEnteredScore) {
        this.rbtEnteredScore = rbtEnteredScore;
    }
}
