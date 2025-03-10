package Repository;

import Model.LearnerMD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LearnerRepo {
    Logger log = Logger.getLogger(LearnerRepo.class.getName());

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url, user, password);
    }

    public List<LearnerMD> ReadLearner() {
        List<LearnerMD> listLn = new ArrayList<>();

        try (Connection conn = getConnection();
             CallableStatement cStm = conn.prepareCall("{CALL sp_ManageLearner('READ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)}")) {

            ResultSet rs = cStm.executeQuery();

            while (rs.next()) {
                LearnerMD ln = new LearnerMD();
                ln.setLnId(rs.getString("MaNH"));
                ln.setNameLn(rs.getString("HoTen"));
                ln.setBirthday(rs.getDate("NgaySinh"));
                ln.setGender(rs.getBoolean("GioiTinh"));
                ln.setPhone(rs.getString("DienThoai"));
                ln.setEmail(rs.getString("Email"));
                ln.setDepict(rs.getString("GhiChu"));
                ln.setEmpID(rs.getString("MaNV"));
                ln.setdOc(rs.getDate("NgayDK"));

                listLn.add(ln);
            }
            System.out.println("✅ Read learner successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Read learner fails", e);
        }
        return listLn;
    }

    public boolean Add(LearnerMD learner) {
        if (isLearnerExist(learner.getEmpID())) {
            System.out.println("❌ Employee ID already exist: " + learner.getEmpID());
            return false;
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageLearner(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, "ADD");
            stmt.setString(2, learner.getLnId());
            stmt.setString(3, learner.getNameLn());
            stmt.setDate(4, learner.getBirthday());
            stmt.setBoolean(5, learner.isGender());
            stmt.setString(6, learner.getPhone());
            stmt.setString(7, learner.getEmail());
            stmt.setString(8, learner.getDepict());
            stmt.setString(9, learner.getEmpID());
            stmt.setDate(10, learner.getdOc());

            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("✅ Learner added successfully: " + learner.getLnId());
            } else {
                System.out.println("❌ Insert failed for learner ID: " + learner.getLnId());
            }

            return rowInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Error adding learner", e);
            return false;
        }
    }

    public List<LearnerMD> SearchLearner(String keyword) {
        List<LearnerMD> searchResults = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM NguoiHoc WHERE MaNH LIKE ? OR HoTen LIKE ?")) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LearnerMD ln = new LearnerMD();
                ln.setLnId(rs.getString("MaNH"));
                ln.setNameLn(rs.getString("HoTen"));
                ln.setBirthday(rs.getDate("NgaySinh"));
                ln.setGender(rs.getBoolean("GioiTinh"));
                ln.setPhone(rs.getString("DienThoai"));
                ln.setEmail(rs.getString("Email"));
                ln.setDepict(rs.getString("GhiChu"));
                ln.setEmpID(rs.getString("MaNV"));
                ln.setdOc(rs.getDate("NgayDK"));

                searchResults.add(ln);
            }
            System.out.println("✅ Search completed successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Error searching learner", e);
        }

        return searchResults;
    }

    public boolean Update(LearnerMD learner) {
        if (isLearnerExist(learner.getEmpID())) {
            System.out.println("❌ Employee ID already exist: " + learner.getEmpID());
            return false;
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageLearner(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, "UPDATE");
            stmt.setString(2, learner.getLnId());
            stmt.setString(3, learner.getNameLn());
            stmt.setDate(4, learner.getBirthday());
            stmt.setBoolean(5, learner.isGender());
            stmt.setString(6, learner.getPhone());
            stmt.setString(7, learner.getEmail());
            stmt.setString(8, learner.getDepict());
            stmt.setString(9, learner.getEmpID());
            stmt.setDate(10, learner.getdOc());

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated > 0) {
                System.out.println("✅ Learner updated successfully: " + learner.getLnId());
            } else {
                System.out.println("❌ Update failed for learner ID: " + learner.getLnId());
            }

            return rowUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Error updating learner", e);
            return false;
        }
    }

    public boolean Delete(String learnerId) {
        if (learnerId == null || learnerId.trim().isEmpty()) {
            System.out.println("❌ Invalid learner ID. Cannot delete.");
            return false;
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageLearner(?, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)}")) {

            stmt.setString(1, "DELETE");
            stmt.setString(2, learnerId);

            int rowDeleted = stmt.executeUpdate();
            if (rowDeleted > 0) {
                System.out.println("✅ Learner deleted successfully: " + learnerId);
            } else {
                System.out.println("❌ Delete failed for learner ID: " + learnerId);
            }

            return rowDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Error deleting learner", e);
            return false;
        }
    }

    public boolean isLearnerExist(String learnerId) {
        if (learnerId == null || learnerId.trim().isEmpty()) {
            return false;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM NguoiHoc WHERE MaNH = ?")) {

            stmt.setString(1, learnerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "❌ Error checking employee existence", e);
        }
        return false;
    }
}
