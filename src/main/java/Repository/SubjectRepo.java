package Repository;

import Model.SubjectMD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepo {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";
        return DriverManager.getConnection(url,user,password);
    }

    public List<SubjectMD> ReadSub(){
        List<SubjectMD> listSub = new ArrayList<>();

        try(Connection conn = getConnection();
            CallableStatement cStm = conn.prepareCall(
                    "{CALL dbo.sp_subject}")){
            ResultSet rs = cStm.executeQuery();

            while(rs.next()){
                SubjectMD sub = new SubjectMD();
                sub.setSubId(rs.getString("MaCD"));
                sub.setSubName(rs.getString("TenCD"));
                sub.setFees(rs.getDouble("HocPhi"));
                sub.setDuration(rs.getInt("ThoiLuong"));
                sub.setPicture(rs.getString("Hinh"));
                sub.setDepict(rs.getString("MoTa"));

                listSub.add(sub);
            }
            System.out.println("Read successfully");

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ReadSub fails");

        }


        return listSub;
    }

    public boolean Add(SubjectMD subject){

        try(Connection conn = getConnection();
        CallableStatement stmt = conn.prepareCall("{CALL sp_ManageSubjects(?,?,?,?,?,?,?)}")){

            stmt.setString(1, "ADD");
            stmt.setString(2, subject.getSubId());
            stmt.setString(3, subject.getSubName());
            stmt.setDouble(4, subject.getFees());
            stmt.setInt(5, subject.getDuration());
            stmt.setString(6, subject.getPicture());
            stmt.setString(7, subject.getDepict());


            int rowInserted = stmt.executeUpdate(); // Thực thi câu lệnh

            if (rowInserted > 0) {
                System.out.println("✅ Insert successfully.");
            } else {
                System.out.println("❌ Insert fails.");
            }

            return rowInserted > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean Update(SubjectMD subject) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageSubjects(?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, "UPDATE"); // Hành động "UPDATE"
            stmt.setString(2, subject.getSubId()); // MaCD
            stmt.setString(3, subject.getSubName()); // TenCD
            stmt.setDouble(4, subject.getFees()); // HocPhi
            stmt.setInt(5, subject.getDuration()); // ThoiLuong
            stmt.setString(6, subject.getPicture()); // Hinh
            stmt.setString(7, subject.getDepict()); // MoTa

            int rowUpdated = stmt.executeUpdate(); // Thực thi Stored Procedure

            if (rowUpdated > 0) {
                System.out.println("✅ Update successfully.");
            } else {
                System.out.println("❌ Update fails.");
            }

            return rowUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Delete(String subId) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_ManageSubjects(?, ?, NULL, NULL, NULL, NULL, NULL)}")) {

            stmt.setString(1, "DELETE"); // Hành động "DELETE"
            stmt.setString(2, subId); // MaCD

            int rowDeleted = stmt.executeUpdate(); // Thực thi Stored Procedure

            if (rowDeleted > 0) {
                System.out.println("✅ Delete successfully.");
            } else {
                System.out.println("❌ Delete fails.");
            }

            return rowDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSubjectExist(String subId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM ChuyenDe WHERE MaCD = ?")) {

            stmt.setString(1, subId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;  // Nếu COUNT > 0 tức là đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

