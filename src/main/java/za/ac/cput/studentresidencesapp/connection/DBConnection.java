package za.ac.cput.studentresidencesapp.connection;

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection derbyConnection() throws SQLException {
        String DATABASE_URL = "jdbc:derby://localhost:1527/ResidenceDB";
        String username = "administrator";
        String password = "admin";

        return DriverManager.getConnection(DATABASE_URL, username, password);
    }
}
