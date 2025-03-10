package Controller;

import View.ForgotPw;
import View.Login;
import Repository.EmployeeRepo;
import Model.EmployeesMD;
import View.Main;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class LoginController {

    private static String loggedInEmployeeId;

    public static String getLoggedInEmployeeId() {
        return loggedInEmployeeId;
    }

    private Login login;
    private EmployeeRepo empRepo;
    private List<EmployeesMD> listUser;

    public LoginController(Login login) {
        this.login = login;
        this.empRepo = new EmployeeRepo();

        init();
    }

    private boolean checkValue(){
        return !login.getTxtUsername().getText().isEmpty() && login.getTxtPassword().getPassword().length >0;
    }

    private boolean checkUser(){
        boolean usernameExists = false;

        if(listUser == null || listUser.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "No users found in database!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for(EmployeesMD emp: listUser){
            if(login.getTxtUsername().getText().equals(emp.getEmpId())){
                usernameExists = true;

                if(new String(login.getTxtPassword().getPassword()).equals(emp.getPassword())){
                    return true;
                }
            }
        }

        if(!usernameExists){
            JOptionPane.showMessageDialog(null,
                    "Username do not found! Please check again!",
                    "Login",
                    JOptionPane.WARNING_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null,
                    "Incorrect password! Please try again.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE);
        }

        return false;
    }

    public void init(){
        login.getBtnLogin().addActionListener(e ->LoginAction());
        login.getBtnExit().addActionListener(e ->ExitAction());
        login.getBtnForgotPw().addActionListener(e -> BtnForgotPwAction());

    }

    private void LoginAction() {
        listUser = empRepo.ReadEmp();

        if(!checkValue()){
            JOptionPane.showMessageDialog(null,
                    "Please enter both username and password!",
                    "Login",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!checkUser()){
            return;
        }

        EmployeesMD loggedUser = null;
        for(EmployeesMD user: listUser){
            if(login.getTxtUsername().getText().equals(user.getEmpId()) &&
            new String(login.getTxtPassword().getPassword()).equals(user.getPassword())){
                loggedUser = user;
                break;
            }
        }

        if(loggedUser != null){

            loggedInEmployeeId = loggedUser.getEmpId();
            System.out.println("🔹 Logged-in Employee ID: " + loggedInEmployeeId); // Debug log

            JOptionPane.showMessageDialog(null,
                    "Login successfully!");
            login.getFrame().setVisible(false);

            if(loggedUser.isRole()){
                Main main = new Main();
                new MainController(main);

                main.getFrame().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        login.getFrame().setEnabled(true);
                        login.getFrame().toFront();
                    }
                });

            }else if(!loggedUser.isRole()){
                Main main = new Main();
                new MainController(main);

                main.getJmnStatistics().setVisible(false);


                main.getFrame().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        login.getFrame().setEnabled(true);
                        login.getFrame().toFront();
                    }
                });

            }else{
                JOptionPane.showMessageDialog(null,
                        "Invalid username or password!",
                        "Login",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void ExitAction() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Do you want to leave?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);
        if(choice == JOptionPane.YES_NO_OPTION){
            JFrame ExitFrame = (JFrame) SwingUtilities.getWindowAncestor(login.getPanelMain());
            ExitFrame.dispose();
        }

    }

    private void BtnForgotPwAction() {
            ForgotPw forgotPwFrame = new ForgotPw();
            new ForgotPwController(forgotPwFrame);
    }


}
