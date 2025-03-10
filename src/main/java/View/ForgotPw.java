package View;

import javax.swing.*;

public class ForgotPw {
    private JTextField txtUsername;
    private JTextField txtGmail;
    private JButton btnAccept;
    private JPanel panelMain;
    private JFrame frame;

    public ForgotPw(){
        frame = new JFrame("Forgot Password");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JTextField getTxtGmail() {
        return txtGmail;
    }

    public void setTxtGmail(JTextField txtGmail) {
        this.txtGmail = txtGmail;
    }

    public JButton getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(JButton btnAccept) {
        this.btnAccept = btnAccept;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
