package com.mckinsey.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = dbConnection.registerAndOpenConnect();
        Statement stmt = null;
        try {

//            STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT first, last FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            CustomObjectMapper mapper = new CustomObjectMapper();
            System.out.println(mapper.writeValueAsString(rs));

//           rss.serialize();
            while (rs.next()) System.out.println(rs.getString("first"));


            //STEP 6: Clean-up environment
//            rs.close();
//            stmt.close();
//            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}
