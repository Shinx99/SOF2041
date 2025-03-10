package Repository;

import Model.CoursesMD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursesRepo {
    private Logger log = Logger.getLogger(CoursesRepo.class.getName());

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url, user, password);
    }

    public List<CoursesMD> ReadCourse() {
        List<CoursesMD> listCs = new ArrayList<>();

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall(
                     "{CALL dbo.sp_course}")) {
            ResultSet rs = cStm.executeQuery();

            while(rs.next()){
                CoursesMD cs = new CoursesMD();
                cs.setCourseId(rs.getInt("MaKH"));
                cs.setSubId(rs.getString("MaCD"));
                cs.setFees(rs.getDouble("HocPhi"));
                cs.setDuration(rs.getInt("ThoiLuong"));
                cs.setsOd(rs.getDate("NgayKG"));
                cs.setDepict(rs.getString("GhiChu"));
                cs.setEmpId(rs.getString("MaNV"));
                cs.setdOc(rs.getDate("NgayTao"));

                listCs.add(cs);
            }
            System.out.println("Read Course successfully");

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Read Course fails");
        }

        return listCs;
    }

    //cbSubject
    public Map<String, String> getAllSubjectMap(){

        Map<String, String> subjectsMap = new HashMap<>();

        try(Connection conn = getConnection();
        PreparedStatement ptmt = conn.prepareStatement("SELECT MaCD, TenCD FROM ChuyenDe")){
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()){
                subjectsMap.put(rs.getString("TenCD"), rs.getString("MaCD"));
            }

        }catch(SQLException e){
            e.printStackTrace();
            log.log(Level.WARNING, "Error loading subject.");

        }

        return subjectsMap;
    }

    public boolean Add(CoursesMD course){
        try(Connection conn = getConnection();
            CallableStatement stmt = conn.prepareCall("{CALL sp_ManageCourses(?,NULL,?,?,?,?,?,?,?)}")){

            stmt.setString(1, "ADD");
            stmt.setString(2, course.getSubId());
            stmt.setDouble(3, course.getFees());
            stmt.setInt(4, course.getDuration());
            stmt.setString(5, course.getEmpId());
            stmt.setString(6, course.getDepict());
            stmt.setDate(7, course.getsOd());
            stmt.setDate(8, course.getdOc());

            int rowInserted = stmt.executeUpdate(); // Thực thi câu lệnh

            if (rowInserted > 0) {
                System.out.println("✅ Insert successfully.");
            } else {
                System.out.println("❌ Insert fails.");
            }

            return rowInserted > 0; // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }



    public boolean Update(CoursesMD course){

        try(Connection conn = getConnection();
        CallableStatement stmt = conn.prepareCall("{CALL sp_ManageCourses(?, ?, ?, ?, ?, ?, ?, ?, ?)}")){

            stmt.setString(1, "UPDATE");
            stmt.setInt(2, course.getCourseId());
            stmt.setString(3, course.getSubId());
            stmt.setDouble(4, course.getFees());
            stmt.setInt(5, course.getDuration());
            stmt.setString(6, course.getEmpId());
            stmt.setString(7, course.getDepict());
            stmt.setDate(8, course.getsOd());
            stmt.setDate(9, course.getdOc());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteCourse(int courseId) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageCourses(?, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL)}")) {

            stmt.setString(1, "DELETE");
            stmt.setInt(2, courseId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
