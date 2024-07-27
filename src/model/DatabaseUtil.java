package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Jump";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Newplayer WHERE username = ? AND player_password = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("User authenticated: " + username);
                    return true;
                } else {
                    System.out.println("Authentication failed for user: " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

