package Controller;

import View.ChangePassword;
import Repository.EmployeeRepo;
import Model.EmployeesMD;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChangePwController {
    private ChangePassword changePassword;
    private EmployeeRepo empRepo;
    private List<EmployeesMD> listUser = new ArrayList<>();

    public ChangePwController(ChangePassword changePassword) {
        this.changePassword = changePassword;
        this.empRepo = new EmployeeRepo();

        init();
    }

    private boolean checkValue(){
        return !changePassword.getTxtUsername().getText().isEmpty() &&
                changePassword.getTxtCurrentPass().getPassword().length>0;
    }

    private boolean checkUser() {
        String enteredUsername = changePassword.getTxtUsername().getText().trim();
        String enteredCurrentPassword = new String(changePassword.getTxtCurrentPass().getPassword()).trim();

        if (enteredUsername.isEmpty() || enteredCurrentPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username and current password cannot be empty!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        listUser = empRepo.ReadEmp(); // Lấy danh sách nhân viên từ DB

        if (listUser == null || listUser.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No users found in database!",
                    "Change Password",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean usernameExists = false;
        boolean passwordCorrect = false;

        for (EmployeesMD user : listUser) {
            if (enteredUsername.equals(user.getEmpId())) {
                usernameExists = true;

                // So sánh mật khẩu với mật khẩu đã lưu (giả sử đã `hash`)
                if (enteredCurrentPassword.equals(user.getPassword())) {
                    passwordCorrect = true;
                }
                break;
            }
        }

        if (!usernameExists) {
            JOptionPane.showMessageDialog(null,
                    "Username not found! Please check again!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!passwordCorrect) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect password! Please try again.",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean checkNewPw() {
        String currentPw = new String(changePassword.getTxtCurrentPass().getPassword()).trim();
        String newPw = new String(changePassword.getTxtNewPass().getPassword()).trim();
        String confirmPw = new String(changePassword.getTxtConfirmPass().getPassword()).trim();

        if (newPw.isEmpty() || confirmPw.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "New password and confirm password cannot be empty!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!newPw.equals(confirmPw)) {
            JOptionPane.showMessageDialog(null,
                    "New password and confirm password do not match!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (newPw.equals(currentPw)) {
            JOptionPane.showMessageDialog(null,
                    "New password must be different from the current password!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (newPw.length() < 6) {
            JOptionPane.showMessageDialog(null,
                    "New password must be at least 8 characters long!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra mật khẩu có số, chữ hoa, ký tự đặc biệt (nếu cần)
        /*if (!newPw.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$")) {
            JOptionPane.showMessageDialog(null,
                    "Password must contain at least one uppercase letter, one digit, and one special character (@#$%^&+=)!",
                    "Change Password",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }*/

        return true;
    }



    private void init() {
        changePassword.getBtnChange().addActionListener(e ->ChangeAction());
        changePassword.getBtnCancel().addActionListener(e ->CancelAction());
    }

    private void ChangeAction() {
        if (!checkValue()) {
            JOptionPane.showMessageDialog(null,
                    "Please fill in all required fields!",
                    "Change Password",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!checkUser()) {
            return;
        }

        if (!checkNewPw()) {
            return;
        }

        EmployeesMD updateUser = new EmployeesMD();
        updateUser.setEmpId(changePassword.getTxtUsername().getText());
        updateUser.setPassword(new String(changePassword.getTxtNewPass().getPassword())); // Hash nếu cần

        boolean success = empRepo.UpdateEmp(updateUser);

        if (success) {
            JOptionPane.showMessageDialog(null,
                    "Password changed successfully!",
                    "Change Password",
                    JOptionPane.INFORMATION_MESSAGE);
            changePassword.getFrame().dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to change password! Please try again.",
                    "Change Password",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CancelAction() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to cancel?",
                "Cancel",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            JFrame exitFrame = (JFrame) SwingUtilities.getWindowAncestor(changePassword.getPanelMain());
            if (exitFrame != null) {
                exitFrame.dispose();
            }
        }
    }



}
