package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninDatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Jump";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin123";

    // Method to check if a username already exists in the database
    public static boolean usernameExists(String username) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM Newplayer WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        }
        return false;
    }

    // Method to insert a new user into the database
    public static void insertUser(String firstName, String lastName, String email, String username, String password, String confirmPassword) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Newplayer(first_name, last_name, email, username, player_password, confirm_password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, username);
                statement.setString(5, password);
                statement.setString(6, confirmPassword);
                statement.executeUpdate();
            }
        }
    }
}
