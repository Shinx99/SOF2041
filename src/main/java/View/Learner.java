package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Learner {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JTable tbLearner;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTextField txtLearnerID;
    private JTextArea txtDescription;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnLast;
    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JTextField txtFullname;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtBirth;
    private JRadioButton rbtMale;
    private JRadioButton rbtFemale;
    private JFrame frame;

    public static void main(String[] args) {
        new Learner();
    }

    public Learner(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelMain);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createTable();
    }

    private void createTable(){
        tbLearner.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Learner ID",
                        "Full name",
                        "Gender",
                        "Birthday",
                        "Phone number",
                        "Email",
                        "Employee ID",
                        "Day of create"
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

    public JTable getTbLearner() {
        return tbLearner;
    }

    public void setTbLearner(JTable tbLearner) {
        this.tbLearner = tbLearner;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(JTextField txtSearch) {
        this.txtSearch = txtSearch;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JButton getSearchButton() {
        return btnSearch;
    }

    public void setSearchButton(JButton searchButton) {
        this.btnSearch = searchButton;
    }

    public JTextField getTxtLearnerID() {
        return txtLearnerID;
    }

    public void setTxtLearnerID(JTextField txtLearnerID) {
        this.txtLearnerID = txtLearnerID;
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

    public JTextField getTxtFullname() {
        return txtFullname;
    }

    public void setTxtFullname(JTextField txtFullname) {
        this.txtFullname = txtFullname;
    }

    public JTextField getTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(JTextField txtPhone) {
        this.txtPhone = txtPhone;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtBirth() {
        return txtBirth;
    }

    public void setTxtBirth(JTextField txtBirth) {
        this.txtBirth = txtBirth;
    }

    public JRadioButton getRbtMale() {
        return rbtMale;
    }

    public void setRbtMale(JRadioButton rbtMale) {
        this.rbtMale = rbtMale;
    }

    public JRadioButton getRbtFemale() {
        return rbtFemale;
    }

    public void setRbtFemale(JRadioButton rbtFemale) {
        this.rbtFemale = rbtFemale;
    }
}
