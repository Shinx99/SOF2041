package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailConfig {
    private String senderEmail;
    private String senderPassword;

    public EmailConfig() {
        loadConfig();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
            senderEmail = props.getProperty("email.sender");
            senderPassword = props.getProperty("email.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderPassword() {
        return senderPassword;
    }
}
