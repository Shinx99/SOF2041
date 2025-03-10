package Controller;

import View.ForgotPw;
import View.CheckOTP;
import View.NewPw;
import Repository.EmployeeRepo;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.Random;

public class ForgotPwController {
    private ForgotPw forgotPw;
    private EmployeeRepo empRepo;
    private String generatedOtp;
    private String empId;

    public ForgotPwController(ForgotPw forgotPw) {
        this.forgotPw = forgotPw;
        this.empRepo = new EmployeeRepo();

        forgotPw.getBtnAccept().addActionListener(e -> handleForgotPw());
    }

    private void handleForgotPw() {
        empId = forgotPw.getTxtUsername().getText().trim();
        String gmail = forgotPw.getTxtGmail().getText().trim();

        if (empId.isEmpty() || gmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!empRepo.isEmployeeExist(empId) || !empRepo.getEmployeeEmail(empId).equalsIgnoreCase(gmail)) {
            JOptionPane.showMessageDialog(null, "Invalid Employee ID or Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = empRepo.getEmployeeEmail(empId);
        if (email == null) {
            JOptionPane.showMessageDialog(null, "Employee email not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo và gửi OTP
        generatedOtp = generateOTP();
        sendOTPToEmail(email, generatedOtp);

        // Mở giao diện nhập OTP
        CheckOTP otpDialog = new CheckOTP();
        otpDialog.getBtnAccept().addActionListener(e -> verifyOtp(otpDialog));
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(90000000) + 10000000); // 8 số ngẫu nhiên
    }

   /* private void sendOTPToEmail(String email, String otp) {
        // Giả lập gửi OTP qua email
        System.out.println("Sending OTP to: " + email + " - OTP: " + otp);
        JOptionPane.showMessageDialog(null, "OTP has been sent to your email!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }*/

    private void verifyOtp(CheckOTP otpDialog) {
        String enteredOtp = otpDialog.getTxtOTP().getText().trim();
        if (enteredOtp.equals(generatedOtp)) {
            JOptionPane.showMessageDialog(null, "OTP verified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            otpDialog.getFrame().dispose();  // Đóng OTP frame

            // Mở giao diện nhập mật khẩu mới
            openNewPasswordFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid OTP!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openNewPasswordFrame() {
        NewPw newPwFrame = new NewPw();
        newPwFrame.getBtnAccept().addActionListener(e -> resetPassword(newPwFrame));
    }

    private void resetPassword(NewPw newPwFrame) {
        String newPassword = new String(newPwFrame.getTxtNewPass().getPassword());
        String confirmPassword = new String(newPwFrame.getTxtConfirmPass().getPassword());

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a new password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = empRepo.resetPassword(empId, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(null, "Password has been reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            newPwFrame.getFrame().dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to reset password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendOTPToEmail(String email, String otp) {
        EmailConfig emailConfig = new EmailConfig();
        final String senderEmail = emailConfig.getSenderEmail();
        final String senderPassword = emailConfig.getSenderPassword();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("OTP Verification");
            msg.setText("Your OTP code is: " + otp);

            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "OTP has been sent to your email!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to send OTP: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
