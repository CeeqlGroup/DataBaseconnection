package org.ceeqlgroup.db;//STEP 1. Import required packages

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.sql.*;

public class DbConnection {


    private static final Logger logger = LogManager.getLogger(DbConnection.class);

    private Connection conn;

    DbConnection() {
    }

    public Connection connect() {

        logger.debug("Connecting to database...");

        try {
            conn = DriverManager.getConnection(getDbUrl(), "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public Connection getConnection() {
        return conn;
    }

    public String runQuery(String sql) {
        Statement stmt = null;
        String result = "";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            CustomObjectMapper mapper = new CustomObjectMapper();
            result = mapper.asJson(rs);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return result;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadSampleData() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(getDbUrl(), "", "");
        flyway.migrate();
    }

    private String getDbUrl() {
        return "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    }
}
