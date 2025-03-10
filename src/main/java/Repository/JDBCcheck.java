package Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCcheck {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EduSys;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "songlong";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connect successfully!");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR JDBC");
        }

    }
}
