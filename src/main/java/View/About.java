package View;

import javax.swing.*;

public class About {
    private JTextPane copyright2007FreeSoftwareTextPane;
    private JPanel panelMain;
    private JFrame frame;

    public About(){
        frame = new JFrame("About");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
