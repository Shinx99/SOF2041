package View;

import javax.swing.*;

public class Login {
    private JPanel panelMain;
    private JTextField txtUsername;
    private JButton btnLogin;
    private JButton btnExit;
    private JPanel pnLogo;
    private JPasswordField txtPassword;
    private JButton btnForgotPw;
    private JFrame frame;

    public Login(){
        frame = new JFrame("Login");
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

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public JPanel getPnLogo() {
        return pnLogo;
    }

    public void setPnLogo(JPanel pnLogo) {
        this.pnLogo = pnLogo;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JButton getBtnForgotPw() {
        return btnForgotPw;
    }

    public void setBtnForgotPw(JButton btnForgotPw) {
        this.btnForgotPw = btnForgotPw;
    }
}
