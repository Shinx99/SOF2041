package View;

import javax.swing.*;

public class ChangePassword {
    private JPanel panelMain;
    private JTextField txtUsername;
    private JPasswordField txtCurrentPass;
    private JButton btnChange;
    private JButton btnCancel;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;
    private JFrame frame;

    public static void main(String[] args) {
        new ChangePassword();
    }

    public ChangePassword(){
        frame = new JFrame("Change Password");
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

    public JPasswordField getTxtCurrentPass() {
        return txtCurrentPass;
    }

    public void setTxtCurrentPass(JPasswordField txtCurrentPass) {
        this.txtCurrentPass = txtCurrentPass;
    }

    public JButton getBtnChange() {
        return btnChange;
    }

    public void setBtnChange(JButton btnChange) {
        this.btnChange = btnChange;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JPasswordField getTxtNewPass() {
        return txtNewPass;
    }

    public void setTxtNewPass(JPasswordField txtNewPass) {
        this.txtNewPass = txtNewPass;
    }

    public JPasswordField getTxtConfirmPass() {
        return txtConfirmPass;
    }

    public void setTxtConfirmPass(JPasswordField txtConfirmPass) {
        this.txtConfirmPass = txtConfirmPass;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
