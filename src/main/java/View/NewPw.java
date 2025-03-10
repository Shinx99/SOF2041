package View;

import javax.swing.*;

public class NewPw {
    private JPanel panelMain;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;
    private JButton btnAccept;
    private JFrame frame;

    public NewPw(){
        frame = new JFrame("New Password");
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
