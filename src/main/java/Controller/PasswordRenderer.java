package Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PasswordRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value != null) {
            // Chuyển đổi mật khẩu thành chuỗi ký tự '*'
            String maskedPassword = "*".repeat(value.toString().length());
            value = maskedPassword;
        } else {
            value = ""; // Nếu giá trị null, để trống
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
