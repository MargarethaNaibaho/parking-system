package com.ntt.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection getConnection(){
        try {
            String URL = "jdbc:postgresql://localhost:5432/parking_system";
            String USER = "postgres";
            String PASSWORD = "enigmaMartha1414$";

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to Connect to PostgreSQL database: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
