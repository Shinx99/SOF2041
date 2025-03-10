package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Employee {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JTextField txtEmID;
    private JRadioButton rdoManage;
    private JRadioButton rdoEmployee;
    private JButton btnAdd;
    private JTable tbEmployee;
    private JTextField txtEmPW;
    private JTextField txtConfirmPW;
    private JTextField txtFullname;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnFirst;
    private JButton btnLast;
    private JButton btnPrevious;
    private JButton btnNext;
    private JTextField txtEmail;
    private JComboBox cblevel;
    private JComboBox cbGender;
    private JFrame frame;

    public static void main(String[] args) {
        new Employee();
    }

    public Employee(){
        frame = new JFrame();
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createTable();

    }

    private void createTable(){
        tbEmployee.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "Employee ID",
                        "Password",
                        "Username",
                        "Role"
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

    public JTextField getTxtEmID() {
        return txtEmID;
    }

    public void setTxtEmID(JTextField txtEmID) {
        this.txtEmID = txtEmID;
    }

    public JRadioButton getRdoManage() {
        return rdoManage;
    }

    public void setRdoManage(JRadioButton rdoManage) {
        this.rdoManage = rdoManage;
    }

    public JRadioButton getRdoEmployee() {
        return rdoEmployee;
    }

    public void setRdoEmployee(JRadioButton rdoEmployee) {
        this.rdoEmployee = rdoEmployee;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JTable getTbEmployee() {
        return tbEmployee;
    }

    public void setTbEmployee(JTable tbEmployee) {
        this.tbEmployee = tbEmployee;
    }

    public JTextField getTxtEmPW() {
        return txtEmPW;
    }

    public void setTxtEmPW(JTextField txtEmPW) {
        this.txtEmPW = txtEmPW;
    }

    public JTextField getTxtConfirmPW() {
        return txtConfirmPW;
    }

    public void setTxtConfirmPW(JTextField txtConfirmPW) {
        this.txtConfirmPW = txtConfirmPW;
    }

    public JTextField getTxtFullname() {
        return txtFullname;
    }

    public void setTxtFullname(JTextField txtFullname) {
        this.txtFullname = txtFullname;
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

    public JButton getBtnFirst() {
        return btnFirst;
    }

    public void setBtnFirst(JButton btnFirst) {
        this.btnFirst = btnFirst;
    }

    public JButton getBtnLast() {
        return btnLast;
    }

    public void setBtnLast(JButton btnLast) {
        this.btnLast = btnLast;
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

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JComboBox getCblevel() {
        return cblevel;
    }

    public void setCblevel(JComboBox cblevel) {
        this.cblevel = cblevel;
    }

    public JComboBox getCbGender() {
        return cbGender;
    }

    public void setCbGender(JComboBox cbGender) {
        this.cbGender = cbGender;
    }
}
