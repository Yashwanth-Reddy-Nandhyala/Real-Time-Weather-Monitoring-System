package sourceCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // Update as necessary
    private static final String USER = "DEMO"; // Update with your username
    private static final String PASSWORD = "demo"; // Update with your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
