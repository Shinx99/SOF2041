package Controller;

import Model.EmployeesMD;
import Repository.EmployeeRepo;
import View.Employee;
import View.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeController {
    private Employee emp;
    private EmployeeRepo empRepo;
    private int current = 0;
    private List<EmployeesMD> empList = new ArrayList<>();
    private List<String> levelList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();

    public EmployeeController(Employee emp) {
        this.emp = emp;
        this.empRepo = new EmployeeRepo();

        init();
        FillTable();

    }


    private void LoadTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
                "Employee ID",
                "Password",
                "Username",
                "Role",
                "Email",
                "Level",
                "Gender"
        });

        for (EmployeesMD employee : empList) {
            model.addRow(new Object[]{
                    employee.getEmpId(),
                    employee.getPassword(),  // Không thay đổi dữ liệu gốc, chỉ thay đổi hiển thị
                    employee.getUsername(),
                    employee.isRole() ? "Manager" : "Employee",
                    (employee.getGmail() != null) ? employee.getGmail() : "",
                    employee.getLevel(),
                    employee.getGender()
            });
        }

        emp.getTbEmployee().setModel(model);

        // Đặt PasswordRenderer cho cột mật khẩu (cột 2, index = 1)
        emp.getTbEmployee().getColumnModel().getColumn(1).setCellRenderer(new PasswordRenderer());

        model.fireTableDataChanged();
    }



    private void FillTable() {
        empList = empRepo.ReadEmp();

        if (empList == null || empList.isEmpty()) {
            System.out.println("No employee data found.");
            return;
        }

        LoadTable();
    }

    private void Display(int row) {

        if (empList.isEmpty() || row < 0 || row >= empList.size()) {
            System.out.println("Invalid row selected.");
            return;
        }

        EmployeesMD empMD = empList.get(row);

        emp.getTxtEmID().setText(empMD.getEmpId());
        emp.getTxtEmPW().setText("********");
        emp.getTxtConfirmPW().setText("********");
        emp.getTxtFullname().setText(empMD.getUsername());
        emp.getTxtEmail().setText(empMD.getGmail());

        //Level
        String level = empMD.getLevel();
        if(level == null || level.trim().isEmpty()){
            emp.getCblevel().setSelectedIndex(-1);
        }else{
            emp.getCblevel().setSelectedItem(level);
        }

        //Gender
        String gender = empMD.getGender();
        if(gender == null || gender.trim().isEmpty()){
            emp.getCbGender().setSelectedIndex(-1);
        }else{
            emp.getCbGender().setSelectedItem(gender);
        }

        if (empMD.isRole()) {
            emp.getRdoManage().setSelected(true);
            emp.getRdoEmployee().setSelected(false);
        } else {
            emp.getRdoManage().setSelected(false);
            emp.getRdoEmployee().setSelected(true);
        }
    }

    private void tableRowDoubleClicked() {
        int selectedRow = emp.getTbEmployee().getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String empID = emp.getTbEmployee().getValueAt(selectedRow, 0).toString();
        String fullName = emp.getTbEmployee().getValueAt(selectedRow, 2).toString();
        String roleTxt = emp.getTbEmployee().getValueAt(selectedRow, 3).toString();
        Object gmailObj = emp.getTbEmployee().getValueAt(selectedRow, 4);
        String gmailTxt = (gmailObj != null) ? gmailObj.toString() : ""; // Kiểm tra null trước khi gọi toString()

        String level = null;
        String gender = null;
        //Duyệt for để lấy lever và gender
        for(EmployeesMD ep:empList){
            if(ep.getEmpId().equals(empID)){
                level = ep.getLevel();
                gender = ep.getGender();
            }

        }

        emp.getCblevel().setSelectedItem(level);
        emp.getCbGender().setSelectedItem(gender);

        emp.getTxtEmID().setText(empID);
        emp.getTxtEmPW().setText("********");
        emp.getTxtConfirmPW().setText("********");
        emp.getTxtFullname().setText(fullName);
        emp.getTxtEmail().setText(gmailTxt); // Tránh null

        if ("Manager".equals(roleTxt)) {
            emp.getRdoManage().setSelected(true);
            emp.getRdoEmployee().setSelected(false);
        } else {
            emp.getRdoManage().setSelected(false);
            emp.getRdoEmployee().setSelected(true);
        }



        current = selectedRow;
        emp.getTabbedPane1().setSelectedIndex(0);
    }


    //get All lẻ fill combobox
    private void FillcbLevel() {
        // Lấy danh sách trình độ từ database
        levelList = empRepo.getAllLevel();

        // Kiểm tra nếu danh sách rỗng
        if (levelList == null || levelList.isEmpty()) {
            System.out.println("Không có dữ liệu trình độ nào để hiển thị!");
            return;
        }

        // Xóa tất cả các mục cũ trong JComboBox
        emp.getCblevel().removeAllItems();

        // Thêm từng trình độ vào JComboBox
        for (String level : levelList) {
            if (level != null && !level.trim().isEmpty()) { // Kiểm tra tránh lỗi NULL
                emp.getCblevel().addItem(level);
            }
        }
    }

    private void FillcbGender(){
        genderList = empRepo.getAllGender();

        if(genderList.isEmpty() || genderList == null){
            System.out.println("Gender no data found!");
            return;
        }

        emp.getCbGender().removeAllItems();

        for(String gender:genderList){
            if( gender != null && !gender.trim().isEmpty()){
                emp.getCbGender().addItem(gender);
            }
        }


    }



    private void init() {
        FillcbLevel();
        FillcbGender();
        emp.getBtnAdd().addActionListener(e -> BtnAddActionListener());
        emp.getBtnNew().addActionListener(e -> BtnNewActionListener());
        emp.getBtnDelete().addActionListener(e -> BtnDeleteActionListener());
        emp.getBtnUpdate().addActionListener(e -> BtnUpdateActionListener());

        emp.getBtnFirst().addActionListener(e -> BtnFirstActionListener());
        emp.getBtnLast().addActionListener(e -> BtnLastActionListener());
        emp.getBtnNext().addActionListener(e -> BtnNextActionListener());
        emp.getBtnPrevious().addActionListener(e -> BtnPreviousActionListener());

        emp.getTbEmployee().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    tableRowDoubleClicked();
                }
            }
        });

    }

    //Button
    private void BtnAddActionListener() {
        String empId = emp.getTxtEmID().getText().trim();
        String password = emp.getTxtEmPW().getText().trim();
        String confirmPw = emp.getTxtConfirmPW().getText().trim();
        String fullName = emp.getTxtFullname().getText().trim();
        String gmail = emp.getTxtEmail().getText().trim();
        boolean isManager = emp.getRdoManage().isSelected(); // Store role as boolean
        String level = emp.getCblevel().getSelectedItem().toString();
        String gender = emp.getCbGender().getSelectedItem().toString();





        // Check if any required field is empty
        if (empId.isEmpty() || password.isEmpty() || confirmPw.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please fill in all required fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (EmployeesMD emp : empList) {
            if (emp.getEmpId().equals(empId)) {
                JOptionPane.showMessageDialog(null,
                        "Employee already exists!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Check if passwords match
        if (!password.equals(confirmPw)) {
            JOptionPane.showMessageDialog(null,
                    "Passwords do not match!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new employee object
        EmployeesMD newEmp = new EmployeesMD(empId, password, fullName, isManager,gmail,level,gender);

        // Save the new employee
        boolean success = empRepo.Add(newEmp); // Giả sử EmployeeRepo có `Add()` trả về boolean
        if (success) {
            empList.add(newEmp);
            LoadTable(); // Cập nhật bảng

            JOptionPane.showMessageDialog(null,
                    "Employee added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Reset fields
            emp.getTxtEmID().setText("");
            emp.getTxtEmPW().setText("");
            emp.getTxtConfirmPW().setText("");
            emp.getTxtFullname().setText("");
            emp.getRdoManage().setSelected(false);
            emp.getTxtEmail().setText("");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to add employee!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void BtnNewActionListener() {
        emp.getTxtEmID().setText("");
        emp.getTxtEmPW().setText("");
        emp.getTxtConfirmPW().setText("");
        emp.getTxtFullname().setText("");
        emp.getRdoManage().setSelected(false);
        emp.getRdoEmployee().setSelected(true);
        emp.getTxtEmail().setText("");
    }

    private void BtnDeleteActionListener() {

    }

    private void BtnUpdateActionListener() {

    }


    //Button First, last, previous, next
    private void BtnFirstActionListener() {
        if (!empList.isEmpty()) {
            current = 0;
            Display(current);
        }
    }

    private void BtnLastActionListener() {
        if (!empList.isEmpty()) {
            current = empList.size() - 1;
            Display(current);
        }
    }

    private void BtnNextActionListener() {
        if (!empList.isEmpty() && current < empList.size() - 1) {
            current++;
            Display(current);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Already at the last record.");
        }
    }

    private void BtnPreviousActionListener() {
        if (!empList.isEmpty() && current > 0) {
            current--;
            Display(current);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Already at the first record.");
        }

    }


}
