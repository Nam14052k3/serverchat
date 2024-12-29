package com.raven.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor to enforce Singleton
    private DatabaseConnection() {
    }

    // Get the singleton instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Method to connect to the database
    public void connectToDatabase() {
        String server = "DESKTOP-7EOCORJ";
        String port = "1433";
        String database = "chat_application";
        String userName = "sa";
        String password = "123";
        String url = "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database + ";encrypt=true;trustServerCertificate=true;";

        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("Kết nối thành công đến cơ sở dữ liệu!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get the current connection
    public Connection getConnection() {
        return connection;
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Kết nối cơ sở dữ liệu đã đóng.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
