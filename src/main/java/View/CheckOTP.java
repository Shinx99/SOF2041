package View;

import javax.swing.*;

public class CheckOTP {
    private JPanel panelMain;
    private JTextField txtOTP;
    private JButton btnAccept;
    private JFrame frame;

    public CheckOTP(){
        frame = new JFrame("OTP");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTextField getTxtOTP() {
        return txtOTP;
    }

    public void setTxtOTP(JTextField txtOTP) {
        this.txtOTP = txtOTP;
    }

    public JButton getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(JButton btnAccept) {
        this.btnAccept = btnAccept;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
