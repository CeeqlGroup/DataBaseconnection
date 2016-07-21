package com.mckinsey.db;//STEP 1. Import required packages

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class DbConnection {


    private Connection conn;
    private String dbUrl;

    DbConnection()
    {
        this.dbUrl = getDbUrl();
    }


    public Connection registerAndOpenConnect() { //String JDBCdriver){
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(dbUrl, "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public Connection getConnection() {
        return conn;
    }


    public void loadSampleData() {
    }

    private String getDbUrl() {
        return "jdbc:h2:mem:test";
    }
}
