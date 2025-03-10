package Controller;

import Model.LearnerMD;
import Repository.LearnerRepo;
import View.Learner;
import View.Login;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LearnerController {
    private Learner learner;
    private LearnerRepo lnRepo;
    private int current = 0;
    private List<LearnerMD> listLn = new ArrayList<>();
    private TableRowSorter<TableModel> sorter;

    public LearnerController(Learner learner) {
        this.learner = learner;
        this.lnRepo = new LearnerRepo();

        init();
        FillTable();
    }

    private void LoadTable() {
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
                "Learner ID",
                "Full name",
                "Gender",
                "Birthday",
                "Phone number",
                "Email",
                "Employee ID",
                "Day of create"
        });

        for (LearnerMD ln : listLn) {
            model.addRow(new Object[]{
                    ln.getLnId(),
                    ln.getNameLn(),
                    ln.isGender() ? "Male" : "Female",
                    ln.getBirthday(),
                    ln.getPhone(),
                    ln.getEmail(),
                    ln.getEmpID(),
                    ln.getdOc()
            });
        }

        learner.getTbLearner().setModel(model);
    }

    private void FillTable() {
        listLn = lnRepo.ReadLearner();

        if (listLn == null || listLn.isEmpty()) {
            System.out.println("No data found.");
            return;
        }
        LoadTable();
        System.out.println("✅ Fill successfully");
    }

    private void Display(int row) {
        if (listLn.isEmpty() || row < 0 || row >= listLn.size()) {
            System.out.println("Invalid row selected.");
            return;
        }

        LearnerMD ln = listLn.get(row);

        learner.getTxtLearnerID().setText(ln.getLnId());
        learner.getTxtFullname().setText(ln.getNameLn());
        learner.getTxtPhone().setText(ln.getPhone());
        learner.getTxtBirth().setText(ln.getBirthday().toString());
        learner.getTxtEmail().setText(ln.getEmail());
        learner.getTxtDescription().setText(ln.getDepict());
        learner.getRbtMale().setSelected(ln.isGender());
        learner.getRbtFemale().setSelected(!ln.isGender());
    }

    private String getCurrentLoggedInEmployeeId() {
        return LoginController.getLoggedInEmployeeId();
    }

    private void tableRowDoubleClicked(){
        int selectedRow = learner.getTbLearner().getSelectedRow();
        if(selectedRow < 0){
            return;
        }

        String learnerId = learner.getTbLearner().getValueAt(selectedRow,0).toString();
        String fullName = learner.getTbLearner().getValueAt(selectedRow,1).toString();
        String gender = learner.getTbLearner().getValueAt(selectedRow,2).toString();
        String birth = learner.getTbLearner().getValueAt(selectedRow,3).toString();
        String phone = learner.getTbLearner().getValueAt(selectedRow,4).toString();
        String email = learner.getTbLearner().getValueAt(selectedRow,5).toString();


        String description = "";
        for(LearnerMD ln : listLn){
            if(ln.getLnId().equals(learnerId)) {
                description = ln.getDepict();
                break;
            }
        }

        learner.getTxtLearnerID().setText(learnerId);
        learner.getTxtFullname().setText(fullName);
        learner.getTxtPhone().setText(phone);
        learner.getTxtBirth().setText(birth);
        learner.getTxtEmail().setText(email);
        learner.getTxtDescription().setText(description);

        if(gender.equals("Male")){
            learner.getRbtMale().setSelected(true);
            learner.getRbtFemale().setSelected(false);
        }else{
            learner.getRbtMale().setSelected(false);
            learner.getRbtFemale().setSelected(true);
        }

        current = selectedRow;
        learner.getTabbedPane1().setSelectedIndex(0);
    }


    private void init() {
        learner.getBtnAdd().addActionListener(e -> AddAction());
        learner.getBtnDelete().addActionListener(e -> DeleteAction());
        learner.getBtnNew().addActionListener(e -> NewAction());
        learner.getBtnUpdate().addActionListener(e -> UpdateAction());
        learner.getBtnSearch().addActionListener(e -> SearchAction());

        learner.getBtnFirst().addActionListener(e -> FirstAction());
        learner.getBtnLast().addActionListener(e -> LastAction());
        learner.getBtnPrevious().addActionListener(e -> PreviousAction());
        learner.getBtnNext().addActionListener(e -> NextAction());

        learner.getTbLearner().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    tableRowDoubleClicked();
                }
            }
        });

        // Áp dụng bộ lọc tìm kiếm tự động
        learner.getTxtSearch().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { AutoSearchAction(); }
            @Override
            public void removeUpdate(DocumentEvent e) { AutoSearchAction(); }
            @Override
            public void changedUpdate(DocumentEvent e) { AutoSearchAction(); }
        });
    }

    //AutoSearch

    private void AutoSearchAction() {
        String searchTxt = learner.getTxtSearch().getText().trim();

        // Kiểm tra nếu sorter chưa được tạo
        if (sorter == null) {
            sorter = new TableRowSorter<>(learner.getTbLearner().getModel());
            learner.getTbLearner().setRowSorter(sorter);
        }

        if (searchTxt.isEmpty()) {
            sorter.setRowFilter(null); // Hiển thị toàn bộ dữ liệu nếu không có nội dung tìm kiếm
            current = 0;
        } else {
            try {
                // Chuẩn hóa chuỗi tìm kiếm (loại bỏ dấu)
                String normalizedSearchTxt = removeVietnameseAccents(searchTxt);

                sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                        for (int i : new int[]{0, 1, 4}) { // Lọc ở các cột Learner ID, Full Name, Phone
                            String originalValue = entry.getStringValue(i).toLowerCase();
                            String normalizedValue = removeVietnameseAccents(originalValue);

                            if (originalValue.contains(searchTxt.toLowerCase()) ||
                                    normalizedValue.contains(normalizedSearchTxt)) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                sorter.setRowFilter(null);
            }
        }
    }

    // 🔹 Hàm chuẩn hóa (loại bỏ dấu tiếng Việt)
    private String removeVietnameseAccents(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }



    // 🔹 Search Button
    private void SearchAction() {
        String keyword = learner.getTxtSearch().getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter ID or name to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        listLn = lnRepo.SearchLearner(keyword);

        if (listLn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No matching records found!", "Search", JOptionPane.INFORMATION_MESSAGE);
        } else {
            LoadTable();
        }
    }

    // 🔹 New Button
    private void NewAction() {
        learner.getTxtLearnerID().setText("");
        learner.getTxtFullname().setText("");
        learner.getTxtPhone().setText("");
        learner.getTxtBirth().setText("");
        learner.getTxtEmail().setText("");
        learner.getTxtDescription().setText("");
        learner.getRbtFemale().setSelected(false);
        learner.getRbtMale().setSelected(true);
    }

    // 🔹 Add Button
    private void AddAction() {
        String learnerId = learner.getTxtLearnerID().getText().trim();
        String fullName = learner.getTxtFullname().getText().trim();
        String phone = learner.getTxtPhone().getText().trim();
        String birth = learner.getTxtBirth().getText().trim();
        String email = learner.getTxtEmail().getText().trim();
        String depict = learner.getTxtDescription().getText().trim();
        boolean gender = learner.getRbtMale().isSelected();

        // 🔹 Lấy mã nhân viên từ phiên đăng nhập hiện tại
        String empId = getCurrentLoggedInEmployeeId();

        if (learnerId.isEmpty() || fullName.isEmpty() || phone.isEmpty() || birth.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please fill in all required fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        //check exists
        if (lnRepo.isLearnerExist(learnerId)) {
            JOptionPane.showMessageDialog(null, "❌ Learner ID already exists! Please enter a different ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔹 Chuyển đổi ngày sinh từ String -> SQL Date
        java.sql.Date birthDate;
        try {
            birthDate = java.sql.Date.valueOf(birth);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid date format! Use YYYY-MM-DD", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔹 Lấy ngày hiện tại làm ngày đăng ký
        java.sql.Date registerDate = new java.sql.Date(System.currentTimeMillis());

        // 🔹 Tạo đối tượng LearnerMD
        LearnerMD newLearner = new LearnerMD(learnerId, fullName, birthDate, gender, phone, email, depict, empId, registerDate);

        // 🔹 Thêm vào DB
        if (lnRepo.Add(newLearner)) {
            JOptionPane.showMessageDialog(null,
                    "✅ Learner added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            FillTable();
        } else {
            JOptionPane.showMessageDialog(null,
                    "❌ Failed to add learner!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // 🔹 Update Button
    private void UpdateAction() {
        String learnerId = learner.getTxtLearnerID().getText().trim();

        if (learnerId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select a learner to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (LearnerMD ln : listLn) {
            if (ln.getLnId().equals(learnerId)) {
                ln.setNameLn(learner.getTxtFullname().getText());
                ln.setPhone(learner.getTxtPhone().getText());
                ln.setEmail(learner.getTxtEmail().getText());
                ln.setDepict(learner.getTxtDescription().getText());
                ln.setGender(learner.getRbtMale().isSelected());

                if (lnRepo.Update(ln)) {
                    JOptionPane.showMessageDialog(null, "✅ Learner updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    FillTable();
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }
    }

    // 🔹 Delete Button
    private void DeleteAction() {
        String learnerId = learner.getTxtLearnerID().getText().trim();

        if (learnerId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select a learner to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (lnRepo.Delete(learnerId)) {
            JOptionPane.showMessageDialog(null, "✅ Learner deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            FillTable();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Delete failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🔹 Navigation
    private void FirstAction() { current = 0; Display(current); }
    private void LastAction() { current = listLn.size() - 1; Display(current); }
    private void NextAction() { if (current < listLn.size() - 1) current++; Display(current); }
    private void PreviousAction() { if (current > 0) current--; Display(current); }
}
