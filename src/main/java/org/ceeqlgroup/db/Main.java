package org.ceeqlgroup.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {


    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        dbConnection.loadSampleData();
        dbConnection.connect();

        String json = dbConnection.runQuery("SELECT * FROM products");
        System.out.println(json);
        dbConnection.close();

    }
}
