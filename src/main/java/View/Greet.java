package View;

import Controller.LoginController;

import javax.swing.*;
import java.util.List;

public class Greet {

    private JPanel panelMain;
    private JProgressBar progressBar1;
    private JFrame frame;

    public static void main(String[] args) {
        new Greet();
    }

    public Greet(){
        frame = new JFrame("Greet Window");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        progressBar1.setStringPainted(true);

        loadProgressBar();
    }

    private void loadProgressBar(){
        SwingWorker<Void,Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for(int i=0; i<= 100; i++){
                    Thread.sleep(30);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() -1);
                progressBar1.setValue(value);
            }

            @Override
            protected void done() {
                frame.dispose();
                Login view = new Login();
                new LoginController(view);

            }
        };
        worker.execute();
    }


}
