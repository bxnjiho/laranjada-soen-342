package com.laranjada.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private final String url;
    private final String user;
    private final String password;

    // Private Constructor
    private DBConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        url = dotenv.get("DB_URL");
        user = dotenv.get("DB_USER");
        password = dotenv.get("DB_PASS");

        if (url == null || user == null || password == null) {
            throw new SQLException("Database credentials are not set in environment variables.");
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new SQLException("Failed to establish database connection.", ex);
        }
    }

    // Get Singleton Instance
    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Get Connection
    public Connection getConnection() {
        return connection;
    }
}
