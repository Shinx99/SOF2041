package Repository;


import Model.EmployeesMD;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeRepo {
    private Logger log = Logger.getLogger(EmployeeRepo.class.getName());


    private Connection getConnection() throws SQLException{
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url,user,password);
    }

    public List<EmployeesMD> ReadEmp(){
        List<EmployeesMD> listEmp = new ArrayList<>();

        try(Connection conn = getConnection();
            CallableStatement cStm = conn.prepareCall(
                    "{CALL dbo.getEmployee}")){

            ResultSet rs = cStm.executeQuery();

            while(rs.next()){
                EmployeesMD emp = new EmployeesMD();
                emp.setEmpId(rs.getString("MaNV"));
                emp.setPassword(rs.getString("MatKhau"));
                emp.setUsername(rs.getString("HoTen"));
                emp.setRole(rs.getBoolean("VaiTro"));
                emp.setGmail(rs.getString("Gmail"));
                emp.setLevel(rs.getString("TrinhDo"));
                emp.setGender(rs.getString("GioiTinh"));

                listEmp.add(emp);
            }
        }catch (SQLException e){
            e.printStackTrace();
            log.log(Level.WARNING,"Error ReadEmp");
        }
        return listEmp;
    }

    public boolean Add(EmployeesMD emp) {
        if (isEmployeeExist(emp.getEmpId())) {
            log.log(Level.WARNING, "Employee ID already exists: " + emp.getEmpId());
            return false;
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageEmployees_test(?, ?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, "ADD");
            stmt.setString(2, emp.getEmpId());
            stmt.setString(3, emp.getPassword());
            stmt.setString(4, emp.getUsername());
            stmt.setBoolean(5, emp.isRole());

            // Kiểm tra nếu email là NULL, đặt thành giá trị mặc định
            if (emp.getGmail() != null && !emp.getGmail().trim().isEmpty()) {
                stmt.setString(6, emp.getGmail());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            // Kiểm tra nếu trình độ là NULL, đặt thành giá trị mặc định
            if (emp.getLevel() != null && !emp.getLevel().trim().isEmpty()) {
                stmt.setString(7, emp.getLevel());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            // Kiểm tra nếu GioiTinh là NULL, đặt thành giá trị mặc định
            if(emp.getGender() != null && !emp.getGender().trim().isEmpty()){
                stmt.setString(8,emp.getGender());
            }else{
                stmt.setNull(8, java.sql.Types.VARCHAR);
            }


            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("✅ Employee added successfully: " + emp.getEmpId());
            } else {
                System.out.println("❌ Insert failed for employee ID: " + emp.getEmpId());
            }

            return rowInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error AddNewEmp");
            return false;
        }
    }


    public boolean UpdateEmp(EmployeesMD emp) {
        if (emp == null || emp.getEmpId() == null || emp.getEmpId().trim().isEmpty()) {
            log.log(Level.WARNING, "Invalid employee data. Cannot update.");
            return false;
        }

        if (!isEmployeeExist(emp.getEmpId())) {
            log.log(Level.WARNING, "Employee ID not found: " + emp.getEmpId());
            return false;
        }

        // Lấy HoTen hiện tại từ database nếu không nhập tên mới
        String currentFullName = emp.getUsername();
        if (currentFullName == null || currentFullName.trim().isEmpty()) {
            currentFullName = getEmployeeFullName(emp.getEmpId()); // Lấy từ DB
            if (currentFullName == null) {
                log.log(Level.WARNING, "Cannot find HoTen for Employee ID: " + emp.getEmpId());
                return false;
            }
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageEmployees(?, ?, ?, ?, ?,?)}")) {

            stmt.setString(1, "UPDATE");
            stmt.setString(2, emp.getEmpId());
            stmt.setString(3, emp.getPassword());
            stmt.setString(4, currentFullName);
            stmt.setBoolean(5, emp.isRole());
            stmt.setString(6,emp.getGmail());


            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Employee updated successfully: " + emp.getEmpId());
            } else {
                System.out.println("❌ Update failed for employee ID: " + emp.getEmpId());
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error UpdateEmp");
            return false;
        }
    }


    public boolean DeleteEmp(String empID) {
        if (empID == null || empID.trim().isEmpty()) {
            log.log(Level.WARNING, "Invalid employee ID. Cannot delete.");
            return false;
        }

        if (!isEmployeeExist(empID)) {
            log.log(Level.WARNING, "Employee ID not found: " + empID);
            return false;
        }

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageEmployees(?, ?, null, null, null)}")) {

            stmt.setString(1, "DELETE");
            stmt.setString(2, empID);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Employee deleted successfully: " + empID);
            } else {
                System.out.println("❌ Delete failed for employee ID: " + empID);
            }

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error DeleteEmp");
            return false;
        }
    }


    public boolean isEmployeeExist(String empID) {
        String query = "SELECT COUNT(*) FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, empID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error checking employee existence: " + empID);
        }
        return false;
    }

    public String getEmployeeFullName(String empId) {
        String query = "SELECT HoTen FROM NhanVien WHERE MaNV = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, empId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("HoTen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error fetching employee full name: " + empId);
        }
        return null; // Trả về NULL nếu không tìm thấy
    }


    //Forgot Password

    public boolean resetPassword(String empId, String newPassword) {
        if (!isEmployeeExist(empId)) {
            log.log(Level.WARNING, "Employee ID not found: " + empId);
            return false;
        }

        // Không mã hóa, lưu trực tiếp mật khẩu vào database
        String query = "UPDATE NhanVien SET MatKhau = ? WHERE MaNV = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newPassword);  // Lưu mật khẩu trực tiếp
            stmt.setString(2, empId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Password updated successfully for Employee ID: " + empId);
                return true;
            } else {
                System.out.println("❌ No rows updated! Check Employee ID: " + empId);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error resetting password for employee: " + empId);
            return false;
        }
    }


    public String getEmployeeEmail(String empId) {
        String query = "SELECT Gmail FROM NhanVien WHERE MaNV = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Gmail"); // Lấy email từ database
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Error fetching email for Employee ID: " + empId);
        }

        return null; // Trả về null nếu không tìm thấy email
    }



    //xử lý cbLevel

    public List<String> getAllLevel(){
        List<String> levelList = new ArrayList<>();

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT DISTINCT TrinhDo FROM NhanVien WHERE TrinhDo IS NOT NULL")){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                levelList.add(rs.getString("TrinhDo"));
            }

        }catch(SQLException e){

            log.log(Level.WARNING,"Error getAllLevel");
        }
        return levelList;
    }


    //Xử lý Gender

    public List<String> getAllGender(){
        List<String> genderList = new ArrayList<>();

        try(Connection conn = getConnection();
        PreparedStatement stmt= conn.prepareStatement(
                "SELECT DISTINCT GioiTinh FROM NhanVien WHERE GioiTinh IS NOT NULL")){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                genderList.add(rs.getString("GioiTinh"));
            }


        }catch (SQLException e){
            log.log(Level.WARNING, "Error getAllGender");
        }



        return genderList;
    }


}
