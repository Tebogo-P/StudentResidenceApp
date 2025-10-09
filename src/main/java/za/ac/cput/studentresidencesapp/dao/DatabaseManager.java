package za.ac.cput.studentresidencesapp.dao;

import za.ac.cput.studentresidencesapp.user.User;
import java.sql.*;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:derby://localhost:1527/ResidenceDB";

    public static void initDatabase() {
        try ( Connection conn = DriverManager.getConnection(DB_URL);  Statement stmt = conn.createStatement()) {

            String createTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "email TEXT UNIQUE NOT NULL,"
                    + "password TEXT NOT NULL)";
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String name, String email, String password) {
        String sql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
        try ( Connection conn = DriverManager.getConnection(DB_URL);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Register failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateUser(String originalEmail, String newName, String newEmail) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE email = ?";

        try ( Connection conn = DriverManager.getConnection(DB_URL);  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, originalEmail);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public static User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try ( Connection conn = DriverManager.getConnection(DB_URL);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }

    public static boolean deleteUser(String email) {
        try ( Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM USER WHERE EMAIL = ?");
            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean changePassword(String email, String oldPassword, String newPassword) {
    String sql = "UPDATE users SET password = ? WHERE email = ? AND password = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, newPassword);
        pstmt.setString(2, email);
        pstmt.setString(3, oldPassword);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.out.println("Password change failed: " + e.getMessage());
        return false;
    }
}

}
