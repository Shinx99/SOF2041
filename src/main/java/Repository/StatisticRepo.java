package Repository;

import Model.*;
import View.Statistic;

import javax.swing.*;
import java.security.PublicKey;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticRepo {
    private Logger log = Logger.getLogger(StatisticRepo.class.getName());

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url, user, password);
    }

    //Xu ly ScoreBoard

        //cbCourse
    public List<String> getCourseList(){
        List<String> courseList = new ArrayList<>();

        try(Connection conn = getConnection();
        PreparedStatement pstm = conn.prepareStatement(
                "SELECT MaCD, NgayKG FROM KhoaHoc ORDER BY NgayKG DESC, MaCD"
        )){
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                String maCD = rs.getString("MaCD");
                Date ngayKG = rs.getDate("NgayKG");

                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(ngayKG);

                String formattedCourse = maCD +" ("+ formattedDate +")";

                courseList.add(formattedCourse);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return courseList;
    }

    public int getMaKHFromDB(String maCD) {
        int maKH = -1;

        try (Connection conn = getConnection();
             PreparedStatement ptm = conn.prepareStatement(
                     "SELECT TOP 1 MaKH FROM KhoaHoc WHERE MaCD = ? ORDER BY NgayKG DESC"
             )) {
            ptm.setString(1, maCD);
            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                maKH = rs.getInt("MaKH");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error getMaKHFromDB");
        }
        return maKH;
    }

    //ScoreBoard
    public List<ScoreBoard_Statistic> getScoreBoardStatistic(int maKH) {
        List<ScoreBoard_Statistic> listSB_St = new ArrayList<>();

        if (maKH <= 0) {
            System.out.println("Invalid MaKH: " + maKH);
            return listSB_St;
        }

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall("{CALL dbo.sp_BangDiem(?)}")
        ) {
            cStm.setInt(1, maKH);
            ResultSet rs = cStm.executeQuery();

            while (rs.next()) {
                ScoreBoard_Statistic sb = new ScoreBoard_Statistic();
                sb.setSt_learnId(rs.getString("MaNH"));
                sb.setSt_name(rs.getString("HoTen"));
                sb.setSt_scoreLn(rs.getDouble("Diem"));

                double score = sb.getSt_scoreLn();
                if (score >= 9.0) {
                    sb.setSt_Classify("Xuất sắc");
                } else if (score >= 7.0) {
                    sb.setSt_Classify("Khá");
                } else if (score >= 5.0) {
                    sb.setSt_Classify("Trung bình");
                } else {
                    sb.setSt_Classify("Chưa đạt");
                }

                listSB_St.add(sb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getScoreBoardStatistic");
        }

        return listSB_St;
    }

    public List<Learner_Statistic> getLearnerStatistic() {
        List<Learner_Statistic> listLn_St = new ArrayList<>();

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall("{CALL dbo.sp_LuongNguoiHoc}")
        ) {
            ResultSet rs = cStm.executeQuery();

            while (rs.next()) {
                Learner_Statistic ln = new Learner_Statistic();
                ln.setSt_year(rs.getInt("Nam"));
                ln.setSt_quantityLn(rs.getInt("SoLuong"));
                ln.setSt_FirstDOS(rs.getDate("DauTien"));
                ln.setSt_LastDOS(rs.getDate("CuoiCung"));

                listLn_St.add(ln);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listLn_St;
    }

    public List<SubjectScore_Statistic> getSubjectScoreStatistic() {
        List<SubjectScore_Statistic> listSubSc_St = new ArrayList<>();


        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall("{CALL dbo.sp_DiemChuyenDe}")
        ) {
            ResultSet rs = cStm.executeQuery();

            while (rs.next()) {
                SubjectScore_Statistic sj = new SubjectScore_Statistic();
                sj.setSt_subId(rs.getString("ChuyenDe"));
                sj.setSt_quantityStudent(rs.getInt("SoHV"));
                sj.setSt_MinScore(rs.getDouble("ThapNhat"));
                sj.setSt_MaxScore(rs.getDouble("CaoNhat"));
                sj.setSt_AvgScore(rs.getDouble("TrungBinh"));

                listSubSc_St.add(sj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listSubSc_St;
    }

    public List<Revenue_Statistic> getRevenueStatistic(int year) {
        List<Revenue_Statistic> listRvn = new ArrayList<>();

        if (year <= 0) {
            System.out.println("Invalid year: " + year);
            return listRvn;
        }

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall("{CALL dbo.sp_DoanhThu(?)}")
        ) {
            cStm.setInt(1, year);
            ResultSet rs = cStm.executeQuery();
            while (rs.next()) {
                Revenue_Statistic rn = new Revenue_Statistic();
                rn.setSt_SubName(rs.getString("ChuyenDe"));
                rn.setSt_quantityCourse(rs.getInt("SoKH"));
                rn.setSt_quantityStudent(rs.getInt("SoHV"));
                rn.setSt_revenue(rs.getDouble("DoanhThu"));
                rn.setSt_lowest(rs.getDouble("ThapNhat"));
                rn.setSt_hightest(rs.getDouble("CaoNhat"));
                rn.setSt_avg(rs.getDouble("TrungBinh"));

                listRvn.add(rn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRvn;
    }

    //cbYear
    public List<Integer> getAvalableYear(){
        List<Integer> yearList = new ArrayList<>();

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT DISTINCT YEAR(kh.NgayKG) AS Year\n" +
                        "        FROM KhoaHoc kh\n" +
                        "        JOIN HocVien hv ON kh.MaKH = hv.MaKH\n" +
                        "        WHERE hv.Diem IS NOT NULL\n" +
                        "        ORDER BY Year DESC")){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                yearList.add(rs.getInt("Year"));
            }

        }catch (SQLException e){
            e.printStackTrace();
            log.log(Level.WARNING,"Error in getAvailableYears",e);
        }
        return yearList;
    }

}
