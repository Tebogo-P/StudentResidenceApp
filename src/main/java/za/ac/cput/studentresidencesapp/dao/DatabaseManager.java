package za.ac.cput.studentresidencesapp.dao;

import za.ac.cput.studentresidencesapp.user.User;
import java.sql.*;

public class DatabaseManager {
           
    private static final String DB_URL = "jdbc:derby://localhost:1527/ResidenceDB;create=true";
    private static final String DB_USERNAME = "administrator";
    private static final String DB_PASSWORD = "admin";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); //help to get connection in one place
    }
    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "USERS", null);

            if (!tables.next()) {
                String createTableSQL = "CREATE TABLE USERS ("
                        + "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "NAME VARCHAR(100) NOT NULL, "
                        + "EMAIL VARCHAR(100) UNIQUE NOT NULL, "
                        + "PASSWORD VARCHAR(100) NOT NULL, "
                        + "PRIMARY KEY (ID))";

                stmt.executeUpdate(createTableSQL);
                System.out.println("✅ USERS table created successfully.");
            } else {
                System.out.println("ℹ USERS table already exists.");
            }

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String name, String email, String password) {
        String sql = "INSERT INTO USERS (NAME, EMAIL, PASSWORD) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if (sqlState != null && sqlState.equals(23505))
                System.err.println("Register failed: email already exists.");
             else {
            System.err.println("Register failed: " + e.getMessage());
            }
        }
        return false;
    }

    
    public static User loginUser(String email, String password) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD")
                );
            }

        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
        return null;
    }

    public static boolean updateUser(String originalEmail, String newName, String newEmail) {
        String sql = "UPDATE USERS SET NAME = ?, EMAIL = ? WHERE EMAIL = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, originalEmail);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUser(String email) {
        String sql = "DELETE FROM USERS WHERE EMAIL = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean changePassword(String email, String oldPassword, String newPassword) {
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.setString(3, oldPassword);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Password change failed: " + e.getMessage());
            return false;
        }
    }

    public static void printAllUsers() {
        String sql = "SELECT ID,NAME,EMAIL FROM USERS";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nCurrent Users:");
            while (rs.next()) {
                System.out.printf(" - %s (%s)%n", rs.getString("NAME"), rs.getString("EMAIL"));
            }

        } catch (SQLException e) {
            System.err.println("Failed to print users: " + e.getMessage());
        }
    }
}
