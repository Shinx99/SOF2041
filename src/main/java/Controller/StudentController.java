package Controller;

import Model.LearnerMD;
import Model.ScoreBoard_Statistic;
import Model.StudentMD;
import Repository.StudentRepo;
import View.Learner;
import View.Student;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StudentController {
    private Student student;
    private StudentRepo sdRepo;
    private int current = 0;
    private List<StudentMD> listSd = new ArrayList<>();
    private List<LearnerMD> listLn = new ArrayList<>();
    private TableRowSorter<TableModel> sorter;
    private Map<String, String> subjectMap;

    public StudentController(Student student) {
        this.student = student;
        this.sdRepo = new StudentRepo();

        init();
        FillComboBoxSubject();
        FillTableLn();
        FillTableSd();
    }


    private void FillComboBoxSubject() {
        JComboBox<String> cbSub = student.getCbSub();

        if (cbSub == null) {
            System.out.println("⚠️ Warning: cbChuyenDe is null! UI not fully initialized.");
            return;
        }

        cbSub.removeAllItems();

        subjectMap = sdRepo.getSubjectMap();
        if(subjectMap == null || subjectMap.isEmpty()){
            System.out.println("⚠️ No subjects found in database.");
            return;
        }

        for (String subName : subjectMap.keySet()) {
            cbSub.addItem(subName);
        }

        if (cbSub.getItemCount() > 0) {
            cbSub.setSelectedIndex(0);
            FillComboBoxCourse();
        }
    }


    private void FillComboBoxCourse() {
        JComboBox<String> cbCourse = student.getCbCourse();
        String selectedSubject = (String) student.getCbSub().getSelectedItem();

        if (cbCourse == null || selectedSubject == null || selectedSubject.isEmpty()) {
            System.out.println("⚠️ Warning: cbCourse is null or no subject selected.");
            return;
        }

        cbCourse.removeAllItems();

        String maCD = subjectMap.get(selectedSubject);
        if (maCD == null) {
            System.out.println("⚠️ Warning: No MaCD found for selected subject.");
            return;
        }

        List<String> courseList = sdRepo.getCourseList(maCD);
        if(courseList == null || courseList.isEmpty()){
            System.out.println("⚠️ No courses available for subject: " + selectedSubject);
            return;
        }

        for (String course : courseList) {
            cbCourse.addItem(course);
        }

        if (cbCourse.getItemCount() > 0) {
            cbCourse.setSelectedIndex(0);
        }

        FillTableLn();
        FillTableSd();
    }


    //table Leanner
    private void LoadTableLn() {
        DefaultTableModel model1 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };

        model1.setColumnIdentifiers(new Object[]{"Learner ID", "Name", "Gender", "Birthday", "Phone", "Email"});
        model1.setRowCount(0); // Xóa dữ liệu cũ

        for (LearnerMD ln : listLn) {
            model1.addRow(new Object[]{
                    ln.getLnId(),
                    ln.getNameLn(),
                    ln.isGender() ? "Male" : "Female",
                    ln.getBirthday(),
                    ln.getPhone(),
                    ln.getEmail(),
            });
        }

        student.getTbLearner().setModel(model1);

        // Cập nhật lại sorter sau khi load dữ liệu
        sorter = new TableRowSorter<>(student.getTbLearner().getModel());
        student.getTbLearner().setRowSorter(sorter);
    }

    private void FillTableLn() {
        int maKH = getSelectedCourseId();

        if (maKH == -1) {
            System.out.println("⚠️ No valid course selected. Clearing learner table.");
            return; // Không tải dữ liệu nếu không có khóa học hợp lệ
        }

        listLn = sdRepo.ReadLearn_sd(maKH);

        if (listLn == null || listLn.isEmpty()) {
            System.out.println("⚠️ No learners found for selected course.");
            return; // Không hiển thị dữ liệu nếu không có người học
        }

        LoadTableLn();
        System.out.println("✅ Learner Table Loaded Successfully");
    }


    //table Student
    private void LoadTableSd() {
        DefaultTableModel model2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };

        model2.setColumnIdentifiers(new Object[]{"Order", "Student ID", "Learner ID", "Name", "Score"});
        model2.setRowCount(0); // Xóa dữ liệu cũ

        int order = 1;
        for (StudentMD sd : listSd) {
            model2.addRow(new Object[]{
                    order++,
                    sd.getSdId(),
                    sd.getLnId(),
                    sd.getSdName(),
                    sd.getScore()
            });
        }

        student.getTbStudent().setModel(model2);
    }

    private void FillTableSd() {
        String selectedCourse = (String) student.getCbCourse().getSelectedItem();

        if (selectedCourse == null || selectedCourse.trim().isEmpty()) {
            System.out.println("⚠️ No course selected. Clearing student table.");
            return; // Không tải dữ liệu nếu không có khóa học hợp lệ
        }

        if(!selectedCourse.matches("^\\w+ \\(\\d{4}-\\d{2}-\\d{2}\\)$")){
            System.out.println("⚠️ Invalid course format: " + selectedCourse);
            return;
        }

        // Tách MaCD và NgayKG từ cbCourse
        String[] parts = selectedCourse.split(" ");
        if (parts.length < 2) {
            System.out.println("⚠️ Invalid course format. Clearing student table.");
            return;
        }

        String maCD = parts[0]; // Mã chuyên đề
        String ngayKG = parts[1].replaceAll("[()]", ""); // Ngày khai giảng (định dạng yyyy-MM-dd)

        // Lấy danh sách sinh viên từ CSDL
        listSd = sdRepo.getStudentListByCourse(maCD, ngayKG);

        if (listSd == null || listSd.isEmpty()) {
            System.out.println("⚠️ No students found for MaCD: " + maCD + " - NgayKG: " + ngayKG);
            return;
        }

        // Load dữ liệu vào bảng
        LoadTableSd();

        System.out.println("✅ Student Table Loaded Successfully");
    }

    private void AutoSearchAction() {
        String searchTxt = student.getTxtSearch().getText().trim();

        if (sorter == null) {
            sorter = new TableRowSorter<>(student.getTbLearner().getModel());
            student.getTbLearner().setRowSorter(sorter);
        }

        if (searchTxt.isEmpty()) {
            sorter.setRowFilter(null);
            current = 0;
        } else {
            try {
                String normalizedSearchTxt = removeVietnameseAccents(searchTxt);

                sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                        for (int i : new int[]{0, 1, 4}) { // Lọc theo Learner ID, Full Name, Phone
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

    private String removeVietnameseAccents(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private int getSelectedCourseId() {
        String selectedItem = (String) student.getCbCourse().getSelectedItem();

        if (selectedItem == null || selectedItem.trim().isEmpty()) {
            return -1;
        }

        System.out.println("Selected Course: " + selectedItem); // Debugging

        // Kiểm tra xem có đúng định dạng "MaCD (NgayKG)"
        if (!selectedItem.matches("^\\w+ \\(\\d{4}-\\d{2}-\\d{2}\\)$")) {
            JOptionPane.showMessageDialog(null, "Invalid course format!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        try {
            String[] parts = selectedItem.split(" ");
            String maCD = parts[0]; // Mã chuyên đề
            String ngayKG = parts[1].replaceAll("[()]", ""); // Ngày khai giảng
            return sdRepo.getMaKHFromDB(maCD, ngayKG);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error parsing course ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    //Filter - score at Student table
    private void FilterFromScore() {

        DefaultTableModel model = (DefaultTableModel) student.getTbStudent().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        student.getTbStudent().setRowSorter(sorter);

        if (student.getRbtAll().isSelected()) {
            sorter.setRowFilter(null);

        } else if (student.getRbtEnteredScore().isSelected()) {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    Object scoreObj = entry.getValue(4);
                    if(scoreObj == null){
                        return false;
                    }
                    try{
                        double score = Double.parseDouble(scoreObj.toString());
                        return score >0;

                    }catch(NumberFormatException e){
                        return false;
                    }
                }
            });

        } else if (student.getRbtNoSpoint().isSelected()) {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    Object scoreObj = entry.getValue(4);
                    if(scoreObj == null){
                        return false;
                    }
                    try{
                        double score = Double.parseDouble(scoreObj.toString());
                        return score == 0;

                    }catch (NumberFormatException e){
                        return false;
                    }
                }
            });

        }

    }


    private void init() {
        student.getCbSub().addActionListener(e -> FillComboBoxCourse());

        student.getCbCourse().addActionListener(e -> {
            FillTableLn();
            FillTableSd();
            student.getRbtAll().setSelected(true);
            FilterFromScore();
        });

        student.getBtnDelete().addActionListener(e -> DeleteAction());
        student.getBtnUpdate().addActionListener(e -> UpdateAction());
        student.getBtnAdd().addActionListener(e -> AddAction());

        student.getRbtAll().addActionListener(e -> FilterFromScore());
        student.getRbtEnteredScore().addActionListener(e -> FilterFromScore());
        student.getRbtNoSpoint().addActionListener(e -> FilterFromScore());

        student.getTxtSearch().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                AutoSearchAction();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                AutoSearchAction();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                AutoSearchAction();
            }
        });


    }


    private void AddAction() {
        int maKH = getSelectedCourseId();
        if (maKH == -1) return;

        int selectedRow = student.getTbLearner().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a learner to add!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNH = student.getTbLearner().getValueAt(selectedRow, 0).toString();

        if (sdRepo.addStudentToCourse(maKH, maNH)) {
            JOptionPane.showMessageDialog(null, "✅ Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            FillTableLn();
            FillTableSd();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Failed to add student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void UpdateAction() {
        int selectedRow = student.getTbStudent().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a student to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maHV = (int) student.getTbStudent().getValueAt(selectedRow, 1);
        double newScore;

        try {
            newScore = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter new score:", "Update Score", JOptionPane.QUESTION_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid score!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sdRepo.updateStudentScore(maHV, newScore)) {
            JOptionPane.showMessageDialog(null, "✅ Score updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            FillTableSd();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Failed to update score!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void DeleteAction() {
        int selectedRow = student.getTbStudent().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a student to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maHV = (int) student.getTbStudent().getValueAt(selectedRow, 1);

        if (sdRepo.deleteStudentFromCourse(maHV)) {
            JOptionPane.showMessageDialog(null, "✅ Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            FillTableSd();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Failed to delete student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
