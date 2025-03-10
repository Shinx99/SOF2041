package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Subject {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JTable tbSubject;
    private JTextField txtSubID;
    private JTextArea txtDescription;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnLast;
    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JLabel lbPic;
    private JTextField txtSubName;
    private JTextField txtSubDuration;
    private JTextField txtSubFees;
    private JFrame frame;

    public static void main(String[] args) {
        new Subject();
    }

    public Subject(){
        frame = new JFrame();
        frame.setContentPane(panelMain);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        createTable();
    }

    private void createTable(){
        tbSubject.setModel(new DefaultTableModel(
                null,
                new String[]{
                     "Subject ID",
                     "Subject name",
                     "Fees",
                     "Duration",
                     "Picture"
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

    public JTable getTbSubject() {
        return tbSubject;
    }

    public void setTbSubject(JTable tbSubject) {
        this.tbSubject = tbSubject;
    }

    public JTextField getTxtSubID() {
        return txtSubID;
    }

    public void setTxtSubID(JTextField txtSubID) {
        this.txtSubID = txtSubID;
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

    public JLabel getLbPic() {
        return lbPic;
    }

    public void setLbPic(JLabel lbPic) {
        this.lbPic = lbPic;
    }

    public JTextField getTxtSubName() {
        return txtSubName;
    }

    public void setTxtSubName(JTextField txtSubName) {
        this.txtSubName = txtSubName;
    }

    public JTextField getTxtSubDuration() {
        return txtSubDuration;
    }

    public void setTxtSubDuration(JTextField txtSubDuration) {
        this.txtSubDuration = txtSubDuration;
    }

    public JTextField getTxtSubFees() {
        return txtSubFees;
    }

    public void setTxtSubFees(JTextField txtSubFees) {
        this.txtSubFees = txtSubFees;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}


