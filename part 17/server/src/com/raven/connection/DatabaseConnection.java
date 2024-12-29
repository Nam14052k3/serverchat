package com.raven.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {

    }

    public void connectToDatabase() throws SQLException {
        String server = "DESKTOP-7EOCORJ";
        String port = "1433";
        String database = "chat_application";
        String userName = "sa";
        String password = "123";
        String connectionUrl = "jdbc:sqlserver://DESKTOP-7EOCORJ:1433;databaseName=chat_application;encrypt=false";
        connection = java.sql.DriverManager.getConnection(connectionUrl, userName, password);



        // Load the SQL Server JDBC Driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found.", e);
        }

        // Establish the connection
        connection = java.sql.DriverManager.getConnection(connectionUrl, userName, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
