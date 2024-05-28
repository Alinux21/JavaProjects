package com.example.demo.jdbc;

import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("1234");
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }
    
    private Database() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            System.err.println("Error at getting connection from the database:" + ex);
        }
        return null;
    }

}
