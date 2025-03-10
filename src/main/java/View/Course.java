package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Course {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JComboBox cbSub;
    private JTextField txtFdos;
    private JTextArea txtDescription;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnLast;
    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JTable tbCourse;
    private JTextField txtFees;
    private JTextField txtCreator;
    private JTextField txtDuration;
    private JTextField txtDoc;
    private final JFrame frame;

    public static void main(String[] args) {
        new Course();
    }
    public Course(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelMain);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        creatTable();
    }

    private void creatTable(){
        tbCourse.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Course ID",
                        "SubjectID",
                        "Duration",
                        "Fees",
                        "First day of school",
                        "Creator",
                        "Day of creation"

                }
        ));
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JComboBox getCbSub() {
        return cbSub;
    }

    public void setCbSub(JComboBox cbSub) {
        this.cbSub = cbSub;
    }

    public JTextField getTxtFdos() {
        return txtFdos;
    }

    public void setTxtFdos(JTextField txtFdos) {
        this.txtFdos = txtFdos;
    }

    public JTextArea getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(JTextArea txtDescription) {
        this.txtDescription = txtDescription;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnNew() {
        return btnNew;
    }

    public void setBtnNew(JButton btnNew) {
        this.btnNew = btnNew;
    }

    public JButton getBtnLast() {
        return btnLast;
    }

    public void setBtnLast(JButton btnLast) {
        this.btnLast = btnLast;
    }

    public JButton getBtnFirst() {
        return btnFirst;
    }

    public void setBtnFirst(JButton btnFirst) {
        this.btnFirst = btnFirst;
    }

    public JButton getBtnPrevious() {
        return btnPrevious;
    }

    public void setBtnPrevious(JButton btnPrevious) {
        this.btnPrevious = btnPrevious;
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public void setBtnNext(JButton btnNext) {
        this.btnNext = btnNext;
    }

    public JTable getTbCourse() {
        return tbCourse;
    }

    public void setTbCourse(JTable tbCourse) {
        this.tbCourse = tbCourse;
    }

    public JTextField getTxtFees() {
        return txtFees;
    }

    public void setTxtFees(JTextField txtFees) {
        this.txtFees = txtFees;
    }

    public JTextField getTxtCreator() {
        return txtCreator;
    }

    public void setTxtCreator(JTextField txtCreator) {
        this.txtCreator = txtCreator;
    }

    public JTextField getTxtDuration() {
        return txtDuration;
    }

    public void setTxtDuration(JTextField txtDuration) {
        this.txtDuration = txtDuration;
    }

    public JTextField getTxtDoc() {
        return txtDoc;
    }

    public void setTxtDoc(JTextField txtDoc) {
        this.txtDoc = txtDoc;
    }

    public JFrame getFrame() {
        return frame;
    }


}
