package Controller;

import Model.CoursesMD;
import Repository.CoursesRepo;
import View.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CourseController {
    private Course course;
    private CoursesRepo CsRepo;
    private List<CoursesMD> listCs = new ArrayList<>();
    private int current = 0;
    private Map<String,String> subMap;

    public CourseController(Course course) {
        this.course = course;
        this.CsRepo = new CoursesRepo();

        init();
        FillTable();

    }

    private void LoadTable(){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
                "Course ID",
                "SubjectID",
                "Duration",
                "Fees",
                "First day of school",
                "Creator",
                "Day of creation"
        });

        for (CoursesMD cs : listCs) {
            model.addRow(new Object[]{
                    cs.getCourseId(),
                    cs.getSubId(),
                    cs.getDuration(),
                    cs.getFees(),
                    cs.getsOd(),
                    cs.getEmpId(),
                    cs.getdOc()
            });
        }

        course.getTbCourse().setModel(model);

    }

    private void FillTable(){
        listCs = CsRepo.ReadCourse();

        if(listCs == null || listCs.isEmpty()){
            System.out.println("No data found");
            return;
        }
        LoadTable();
        System.out.println("Fill successfully");
    }

    private void display(int row){
        if(listCs.isEmpty()){
            System.out.println("No data available to display.");
            return;
        }

        if (row < 0 || row >= listCs.size()) {
            System.out.println("Invalid row selected.");
            return;
        }

        CoursesMD cs = listCs.get(row);

        String subName = null;
        for(Map.Entry<String, String>entry : subMap.entrySet()){
            if(entry.getValue().equals(cs.getSubId())){
                subName = entry.getKey();
                break;
            }
        }

        course.getCbSub().setSelectedItem(subName);
        course.getTxtFees().setText(String.valueOf(cs.getFees()));
        course.getTxtDuration().setText(String.valueOf(cs.getDuration()));
        course.getTxtCreator().setText(cs.getEmpId());
        course.getTxtFdos().setText(cs.getsOd().toString());
        course.getTxtDoc().setText(cs.getdOc().toString());
        course.getTxtDescription().setText(cs.getDepict());

    }

    private void fillCbSub(){
        subMap = CsRepo.getAllSubjectMap();

        course.getCbSub().removeAllItems();
        for(String subName : subMap.keySet()){
            course.getCbSub().addItem(subName);
        }
    }

    private void tableRowDoubleClicked(){
        int selectedRow = course.getTbCourse().getSelectedRow();
        if(selectedRow >=0){

            String courseID = course.getTbCourse().getValueAt(selectedRow,0).toString();
            String subjectId = course.getTbCourse().getValueAt(selectedRow, 1).toString();
            String duration = course.getTbCourse().getValueAt(selectedRow, 2).toString();
            String fees = course.getTbCourse().getValueAt(selectedRow, 3).toString();
            String startDate = course.getTbCourse().getValueAt(selectedRow, 4).toString();
            String creator = course.getTbCourse().getValueAt(selectedRow, 5).toString();
            String createDate = course.getTbCourse().getValueAt(selectedRow, 6).toString();

            String subjectName = null;
            for(Map.Entry<String,String>entry : subMap.entrySet()){
                if(entry.getValue().equals(subjectId)){
                    subjectName = entry.getKey();
                    break;
                }
            }

            course.getCbSub().setSelectedItem(subjectName);
            course.getTxtFees().setText(fees);
            course.getTxtDuration().setText(duration);
            course.getTxtCreator().setText(creator);
            course.getTxtFdos().setText(startDate);
            course.getTxtDoc().setText(createDate);

            current = selectedRow;
            course.getTabbedPane1().setSelectedIndex(0);

        }
    }

    private  String getCurrentLoggedInEmployeeId(){
        return LoginController.getLoggedInEmployeeId();
    }

    //check yyyy-MM-dd
    private boolean isValidDateFormat(String dateStr){
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        if(!Pattern.matches(datePattern,dateStr)){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try{
            sdf.parse(dateStr);
            return true;
        }catch (ParseException e){
            return false;
        }

    }


    private void init(){
        course.getTxtCreator().setText(getCurrentLoggedInEmployeeId());
        fillCbSub();

        course.getBtnAdd().addActionListener(e -> AddAction());
        course.getBtnUpdate().addActionListener(e -> UpdateAction());
        course.getBtnDelete().addActionListener(e -> DeleteAction());
        course.getBtnNew().addActionListener(e -> NewAction());

        course.getBtnFirst().addActionListener(e -> FirstAction());
        course.getBtnPrevious().addActionListener(e -> PreviousAction());
        course.getBtnNext().addActionListener(e -> NextAction());
        course.getBtnLast().addActionListener(e -> LastAction());

        course.getTbCourse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    tableRowDoubleClicked();
                    int row = course.getTbCourse().getSelectedRow();
                    if(row>=0){
                        display(row);
                    }
                }
            }
        });

    }



//Left Button
    private void NewAction() {
        String emId = getCurrentLoggedInEmployeeId();

        course.getCbSub().setSelectedItem(0);
        course.getTxtFees().setText("");
        course.getTxtDescription().setText("");
        course.getTxtCreator().setText(emId);
        course.getTxtDoc().setText("");
        course.getTxtFdos().setText("");
        course.getTxtDuration().setText("");

    }

    private void AddAction() {

        //form
        String empId = getCurrentLoggedInEmployeeId();
        String subName = (String) course.getCbSub().getSelectedItem();
        String subID = subMap.get(subName);
        if(subID == null){
            JOptionPane.showMessageDialog(null,
                    "Please select a valid subject!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String feesTxt = course.getTxtFees().getText().trim();
        String durationTxt = course.getTxtDuration().getText().trim();
        String creator = empId;
        String description = course.getTxtDescription().getText().trim();
        String startDateTxt = course.getTxtFdos().getText().trim();
        String createDateTxt = course.getTxtDoc().getText().trim();

        //check
        if(feesTxt.isEmpty() || durationTxt.isEmpty() || creator.isEmpty() || startDateTxt.isEmpty() || createDateTxt.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "Please fill in all required fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double fees;
        int duration;

        try{
            fees = Double.parseDouble(feesTxt);
            duration = Integer.parseInt(durationTxt);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Invalid fees or duration format!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!isValidDateFormat(startDateTxt) || !isValidDateFormat(createDateTxt)){
            JOptionPane.showMessageDialog(null,
                    "Invalid date format! Please enter date in format: yyyy-MM-dd",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        java.sql.Date startDate, createdDate;
        try{
            java.util.Date parsedStartDate = sdf.parse(startDateTxt);
            java.util.Date parsedCreatedDate = sdf.parse(createDateTxt);

            //java.util.Date → java.sql.Date
            startDate = new java.sql.Date(parsedStartDate.getTime());
            createdDate = new java.sql.Date(parsedCreatedDate.getTime());

            if(!createdDate.before(startDate)){
                JOptionPane.showMessageDialog(null,
                        "The create date must be before the start date!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        }catch (ParseException e){
            JOptionPane.showMessageDialog(null,
                    "Invalid date input! Please enter a correct date in format: yyyy-MM-dd", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        CoursesMD cs = new CoursesMD();
        cs.setSubId(subID);
        cs.setFees(fees);
        cs.setDuration(duration);
        cs.setEmpId(creator);
        cs.setDepict(description);
        cs.setsOd(startDate);
        cs.setdOc(createdDate);

        //Repository
        boolean add = CsRepo.Add(cs);
        if(add){
            JOptionPane.showMessageDialog(null,
                    "Added successfully!");
            FillTable();
        }else {
            JOptionPane.showMessageDialog(null,
                    "Failed to add!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }



    }

    private void DeleteAction() {
    }

    private void UpdateAction() {
    }



//Right Button
    private void LastAction() {
        if(!listCs.isEmpty()){
            current = listCs.size()-1;
            display(current);
        } else {
            System.out.println("No records available.");
        }
    }

    private void NextAction() {
        if (!listCs.isEmpty() && current < listCs.size() - 1) {
            current++;
            display(current);
        } else {
            System.out.println("Already at the last record.");
        }
    }


    private void PreviousAction() {
        if(!listCs.isEmpty() && current > 0){
            current--;
            display(current);
        }else{
            System.out.println("Already at the record.");
        }
    }

    private void FirstAction() {
        if(!listCs.isEmpty()){
            current = 0;
            display(current);
        } else {
            System.out.println("No records available.");
        }
    }


}
