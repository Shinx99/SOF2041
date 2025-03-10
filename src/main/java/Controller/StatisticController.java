package Controller;

import Model.Learner_Statistic;
import Model.Revenue_Statistic;
import Model.ScoreBoard_Statistic;
import Model.SubjectScore_Statistic;
import Repository.StatisticRepo;
import View.Statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatisticController {

    private Statistic view;
    private StatisticRepo StRepo;
    private List<Learner_Statistic> st_learner_List = new ArrayList<>();
    private List<Revenue_Statistic> st_revenue_List = new ArrayList<>();
    private List<ScoreBoard_Statistic> st_ScoreB_List = new ArrayList<>();
    private List<SubjectScore_Statistic> st_subject_List = new ArrayList<>();

    public StatisticController(Statistic view) {
        this.view = view;
        this.StRepo = new StatisticRepo();

        loadCbCourse();
        init();

        FillTable();
        FillRevenueTb();
        FillScoreB();

    }

    private void LoadTable() {
        DefaultTableModel model = (DefaultTableModel) view.getTbLearner().getModel();
        model.setRowCount(0);

        for (Learner_Statistic ln : st_learner_List) {
            model.addRow(new Object[]{
                    ln.getSt_year(),
                    ln.getSt_quantityLn(),
                    ln.getSt_FirstDOS(),
                    ln.getSt_LastDOS()
            });
        }

        DefaultTableModel model1 = (DefaultTableModel) view.getTbSubject().getModel();
        model1.setRowCount(0);

        for(SubjectScore_Statistic sj: st_subject_List){
            model1.addRow(new Object[]{
                    sj.getSt_subId(),
                    sj.getSt_quantityStudent(),
                    sj.getSt_MinScore(),
                    sj.getSt_MaxScore(),
                    String.format("%.2f", sj.getSt_AvgScore())
            });
        }


    }

    private void FillTable() {
        st_learner_List = StRepo.getLearnerStatistic();
        st_subject_List = StRepo.getSubjectScoreStatistic();
        LoadTable();
    }

    //Revenue and CbYear

    private void loadScYear(){
        List<Integer> yearList = StRepo.getAvalableYear();
        JComboBox<Integer> cbScYear = view.getCbYear();

        cbScYear.removeAllItems();

        if(yearList.isEmpty()){
            System.out.println("No years available.");
            cbScYear.addItem(-1);
            cbScYear.setEnabled(false);
        }else{
            for(Integer year: yearList){
                cbScYear.addItem(year);
            }
            cbScYear.setEnabled(true);
        }
    }

    private void updateRevenueTable(int selectedYear) {
        st_revenue_List = StRepo.getRevenueStatistic(selectedYear); // Lấy dữ liệu mới
        DefaultTableModel modelud = (DefaultTableModel) view.getTbRevenue().getModel();

        modelud.setRowCount(0); // Xóa dữ liệu cũ

        for(Revenue_Statistic rn: st_revenue_List) {
            modelud.addRow(new Object[]{
                    rn.getSt_SubName(),
                    rn.getSt_quantityCourse(),
                    rn.getSt_quantityStudent(),
                    rn.getSt_revenue(),
                    rn.getSt_lowest(),
                    rn.getSt_hightest(),
                    String.format("%.2f",rn.getSt_avg())
            });
        }

        modelud.fireTableDataChanged();
    }

    private void FillRevenueTb(){
        int selectedYear = Integer.parseInt(Objects.requireNonNull(view.getCbYear().getSelectedItem()).toString());
        if(selectedYear == -1) return;

        st_revenue_List = StRepo.getRevenueStatistic(selectedYear);
        updateRevenueTable(selectedYear);
    }



    //ScoreBoard
        //cbCourse
    private void loadCbCourse() {
        List<String> courseList = StRepo.getCourseList(); // Lấy danh sách tất cả khóa học
        JComboBox<String> cbCourse = view.getCbCourse();

        cbCourse.removeAllItems();

        List<String> filteredCourseList = new ArrayList<>(); // Danh sách khóa học có dữ liệu

        for (String course : courseList) {
            String maCD = course.split(" ")[0]; // Lấy mã khóa học
            int maKH = StRepo.getMaKHFromDB(maCD); // Lấy mã khóa học trong DB

            if (maKH != -1) { // Kiểm tra nếu khóa học hợp lệ
                List<ScoreBoard_Statistic> scoreBoardList = StRepo.getScoreBoardStatistic(maKH);

                if (!scoreBoardList.isEmpty()) { // Chỉ thêm khóa học nếu có dữ liệu bảng điểm
                    filteredCourseList.add(course);
                }
            }
        }

        if (filteredCourseList.isEmpty()) {
            System.out.println("No available courses with data.");
            cbCourse.addItem("No available data");
            cbCourse.setEnabled(false);
        } else {
            for (String course : filteredCourseList) {
                cbCourse.addItem(course);
            }
            cbCourse.setEnabled(true);
        }
    }


    private int getSelectedMaKH(){
        String selectItem = (String) view.getCbCourse().getSelectedItem();
        if(selectItem == null || selectItem.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "Please to select course!","Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        String maCD = selectItem.split(" ")[0];
        int maKH = StRepo.getMaKHFromDB(maCD);

        if(maKH == -1){
            JOptionPane.showMessageDialog(null, "Don't find MaKH!");
            System.out.println("Error makh = -1");
        }

        System.out.println("Selected item from JComboBox: " + selectItem);

        return maKH;
    }


    private void FillScoreB(){
        int maKH = getSelectedMaKH();
        if(maKH == -1) return;

        st_ScoreB_List = StRepo.getScoreBoardStatistic(maKH);

        DefaultTableModel modelSb = (DefaultTableModel) view.getTbScBoard().getModel();
        modelSb.setRowCount(0);

        for(ScoreBoard_Statistic sb: st_ScoreB_List){
            modelSb.addRow(new Object[]{
                    sb.getSt_learnId(),
                    sb.getSt_name(),
                    sb.getSt_scoreLn(),
                    sb.getSt_Classify()

            });
        }
        view.getTbScBoard().setModel(modelSb);
        modelSb.fireTableDataChanged();
    }

    private void init() {
        loadScYear();

        view.getCbYear().addActionListener(e -> FillRevenueTb());
        view.getCbCourse().addActionListener(e -> FillScoreB());

    }


}
