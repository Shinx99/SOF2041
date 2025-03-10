package View;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private JPanel painelMain;
    private JButton btnLogOut;
    private JButton btnExit;
    private JMenuItem mniLogin;
    private JMenuItem mniLogOut;
    private JMenuItem mniChangePass;
    private JMenuItem mniExit;
    private JMenuItem mniLearners;
    private JMenuItem mniSubjects;
    private JMenuItem mniCourses;
    private JMenuItem mniEmployee;
    private JMenuItem mniLEY;
    private JMenuItem mniCourseTranscript;
    private JMenuItem mniSFC;
    private JMenuItem mniRevenue;
    private JMenuItem mniGuide;
    private JMenuItem mniAbout;
    private JButton btnSubject;
    private JButton btnLearner;
    private JButton btnCourse;
    private JButton btnGuide;
    private JLabel lblState;
    private JLabel lblClock;
    private JButton btnStudent;
    private JMenuItem mniStudent;
    private JMenu JmnStatistics;
    private JFrame frame;

    public static void main(String[] args) {
        new Main();
    }


    public Main(){

        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Giao diện hiện đại
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Manager");
        frame.setContentPane(painelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mniLogOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
        mniExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,0));

        mniLearners.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.CTRL_DOWN_MASK));
        mniSubjects.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,InputEvent.CTRL_DOWN_MASK));
        mniCourses.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,InputEvent.CTRL_DOWN_MASK));
        mniStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.CTRL_DOWN_MASK));
        mniEmployee.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,InputEvent.CTRL_DOWN_MASK));

        mniLEY.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.SHIFT_DOWN_MASK));
        mniCourseTranscript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,InputEvent.SHIFT_DOWN_MASK));
        mniSFC.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,InputEvent.SHIFT_DOWN_MASK));
        mniRevenue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.SHIFT_DOWN_MASK));

        mniGuide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));

        StartClock();

    }


    private void StartClock(){
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdf.format(new Date());
            lblClock.setText(currentTime);
        });
        timer.start();
    }


    public JPanel getPainelMain() {
        return painelMain;
    }

    public void setPainelMain(JPanel painelMain) {
        this.painelMain = painelMain;
    }

    public JButton getBtnLogOut() {
        return btnLogOut;
    }

    public void setBtnLogOut(JButton btnLogOut) {
        this.btnLogOut = btnLogOut;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public JMenuItem getMniLogin() {
        return mniLogin;
    }

    public void setMniLogin(JMenuItem mniLogin) {
        this.mniLogin = mniLogin;
    }

    public JMenuItem getMniLogOut() {
        return mniLogOut;
    }

    public void setMniLogOut(JMenuItem mniLogOut) {
        this.mniLogOut = mniLogOut;
    }

    public JMenuItem getMniChangePass() {
        return mniChangePass;
    }

    public void setMniChangePass(JMenuItem mniChangePass) {
        this.mniChangePass = mniChangePass;
    }

    public JMenuItem getMniExit() {
        return mniExit;
    }

    public void setMniExit(JMenuItem mniExit) {
        this.mniExit = mniExit;
    }

    public JMenuItem getMniLearners() {
        return mniLearners;
    }

    public void setMniLearners(JMenuItem mniLearners) {
        this.mniLearners = mniLearners;
    }

    public JMenuItem getMniSubjects() {
        return mniSubjects;
    }

    public void setMniSubjects(JMenuItem mniSubjects) {
        this.mniSubjects = mniSubjects;
    }

    public JMenuItem getMniCourses() {
        return mniCourses;
    }

    public void setMniCourses(JMenuItem mniCourses) {
        this.mniCourses = mniCourses;
    }

    public JMenuItem getMniEmployee() {
        return mniEmployee;
    }

    public void setMniEmployee(JMenuItem mniEmployee) {
        this.mniEmployee = mniEmployee;
    }

    public JMenuItem getMniLEY() {
        return mniLEY;
    }

    public void setMniLEY(JMenuItem mniLEY) {
        this.mniLEY = mniLEY;
    }

    public JMenuItem getMniCourseTranscript() {
        return mniCourseTranscript;
    }

    public void setMniCourseTranscript(JMenuItem mniCourseTranscript) {
        this.mniCourseTranscript = mniCourseTranscript;
    }

    public JMenuItem getMniSFC() {
        return mniSFC;
    }

    public void setMniSFC(JMenuItem mniSFC) {
        this.mniSFC = mniSFC;
    }

    public JMenuItem getMniRevenue() {
        return mniRevenue;
    }

    public void setMniRevenue(JMenuItem mniRevenue) {
        this.mniRevenue = mniRevenue;
    }

    public JMenuItem getMniGuide() {
        return mniGuide;
    }

    public void setMniGuide(JMenuItem mniGuide) {
        this.mniGuide = mniGuide;
    }

    public JMenuItem getMniAbout() {
        return mniAbout;
    }

    public void setMniAbout(JMenuItem mniAbout) {
        this.mniAbout = mniAbout;
    }

    public JButton getBtnSubject() {
        return btnSubject;
    }

    public void setBtnSubject(JButton btnSubject) {
        this.btnSubject = btnSubject;
    }

    public JButton getBtnLearner() {
        return btnLearner;
    }

    public void setBtnLearner(JButton btnLearner) {
        this.btnLearner = btnLearner;
    }

    public JButton getBtnCourse() {
        return btnCourse;
    }

    public void setBtnCourse(JButton btnCourse) {
        this.btnCourse = btnCourse;
    }

    public JButton getBtnGuide() {
        return btnGuide;
    }

    public void setBtnGuide(JButton btnGuide) {
        this.btnGuide = btnGuide;
    }

    public JLabel getLblState() {
        return lblState;
    }

    public void setLblState(JLabel lblState) {
        this.lblState = lblState;
    }

    public JLabel getLblClock() {
        return lblClock;
    }

    public void setLblClock(JLabel lblClock) {
        this.lblClock = lblClock;
    }

    public JButton getBtnStudent() {
        return btnStudent;
    }

    public void setBtnStudent(JButton btnStudent) {
        this.btnStudent = btnStudent;
    }

    public JMenuItem getMniStudent() {
        return mniStudent;
    }

    public void setMniStudent(JMenuItem mniStudent) {
        this.mniStudent = mniStudent;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JMenu getJmnStatistics() {
        return JmnStatistics;
    }

    public void setJmnStatistics(JMenu jmnStatistics) {
        JmnStatistics = jmnStatistics;
    }
}
