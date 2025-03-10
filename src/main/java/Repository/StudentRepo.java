package Repository;

import Model.StudentMD;
import Model.LearnerMD;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRepo {
    private static final Logger log = Logger.getLogger(StudentRepo.class.getName());

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url, user, password);
    }

    // 🔹 Load danh sách sinh viên của khóa học
    public List<StudentMD> getStudentListByCourse(String maCD, String ngayKG) {
        List<StudentMD> studentList = new ArrayList<>();

        String sql = "SELECT hv.MaHV, hv.MaNH, nh.HoTen, hv.Diem " +
                "FROM HocVien hv " +
                "JOIN NguoiHoc nh ON hv.MaNH = nh.MaNH " +
                "JOIN KhoaHoc kh ON hv.MaKH = kh.MaKH " +
                "WHERE kh.MaCD = ? AND kh.NgayKG = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maCD);
            stmt.setDate(2, java.sql.Date.valueOf(ngayKG));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StudentMD sd = new StudentMD();
                sd.setSdId(rs.getInt("MaHV"));
                sd.setLnId(rs.getString("MaNH"));
                sd.setSdName(rs.getString("HoTen"));
                sd.setScore(rs.getDouble("Diem"));

                studentList.add(sd);
            }

            System.out.println("✅ Loaded students for MaCD: " + maCD + " - NgayKG: " + ngayKG);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading students for MaCD: " + maCD + " - NgayKG: " + ngayKG);
        }

        return studentList;
    }

    public int getMaKHFromDB(String maCD, String ngayKG) {
        int maKH = -1;
        String sql = "SELECT MaKH FROM KhoaHoc WHERE MaCD = ? AND NgayKG = ?";

        try (Connection conn = getConnection();
             PreparedStatement ptm = conn.prepareStatement(sql)) {
            ptm.setString(1, maCD);
            ptm.setString(2, ngayKG);
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



    // 🔹 Load danh sách người học chưa đăng ký khóa học
    public List<LearnerMD> ReadLearn_sd(int maKH) {
        List<LearnerMD> listLn_sd = new ArrayList<>();
        String sql = "{CALL dbo.sp_learner_sd(?)}";

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall(sql)) {

            cStm.setInt(1, maKH);
            ResultSet rs = cStm.executeQuery();

            while (rs.next()) {
                LearnerMD ln = new LearnerMD();
                ln.setLnId(rs.getString("MaNH"));
                ln.setNameLn(rs.getString("HoTen"));
                ln.setGender(rs.getBoolean("GioiTinh"));
                ln.setBirthday(rs.getDate("NgaySinh"));
                ln.setPhone(rs.getString("DienThoai"));
                ln.setEmail(rs.getString("Email"));
                listLn_sd.add(ln);
            }

            log.info("✅ Read learner list successfully.");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "❌ Read learner list failed: " + e.getMessage(), e);
        }
        return listLn_sd;
    }

    // 🔹 Load combobox chuyên đề
    public Map<String, String> getSubjectMap() {
        Map<String, String> subjectsMap = new HashMap<>();
        String sql = "SELECT MaCD, TenCD FROM ChuyenDe";

        try (Connection conn = getConnection();
             PreparedStatement ptmt = conn.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {

            while (rs.next()) {
                subjectsMap.put(rs.getString("TenCD"), rs.getString("MaCD"));
            }

            log.info("✅ Loaded subjects successfully.");
        } catch (SQLException e) {
            log.log(Level.WARNING, "❌ Error loading subjects: " + e.getMessage(), e);
        }

        return subjectsMap;
    }

    // 🔹 Load combobox khóa học theo chuyên đề
    public List<String> getCourseList(String maCD) {
        List<String> courseList = new ArrayList<>();

        String sql = "SELECT MaCD, NgayKG FROM KhoaHoc WHERE MaCD = ? ORDER BY NgayKG DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maCD);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("NgayKG"));
                String formattedCourse = rs.getString("MaCD") + " (" + formattedDate + ")";
                courseList.add(formattedCourse);
            }

            System.out.println("✅ Loaded courses for MaCD: " + maCD);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error loading courses for MaCD: " + maCD);
        }

        return courseList;
    }



    // 🔹 Thêm sinh viên vào khóa học
    public boolean addStudentToCourse(int maKH, String maNH) {
        String sql = "INSERT INTO HocVien (MaKH, MaNH, Diem) VALUES (?, ?, 0)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKH);
            stmt.setString(2, maNH);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("✅ Added student to course successfully.");
                return true;
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "❌ Failed to add student to course: " + e.getMessage(), e);
        }

        return false;
    }

    // 🔹 Xóa sinh viên khỏi khóa học
    public boolean deleteStudentFromCourse(int maHV) {
        String sql = "DELETE FROM HocVien WHERE MaHV = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maHV);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log.info("✅ Deleted student from course successfully.");
                return true;
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "❌ Failed to delete student from course: " + e.getMessage(), e);
        }

        return false;
    }

    // 🔹 Cập nhật điểm số của sinh viên
    public boolean updateStudentScore(int maHV, double newScore) {
        String sql = "UPDATE HocVien SET Diem = ? WHERE MaHV = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newScore);
            stmt.setInt(2, maHV);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("✅ Updated student score successfully.");
                return true;
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "❌ Failed to update student score: " + e.getMessage(), e);
        }

        return false;
    }
}
