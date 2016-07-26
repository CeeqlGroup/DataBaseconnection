package org.ceeqlgroup.db;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

public class Ceeql implements AutoCloseable {

    private final static Logger log = LogManager.getLogger(Ceeql.class);

    private final String driverName;
    private final String url;
    private final String username;
    private final String password;

    private boolean isConnected;

    private final MetricRegistry metricRegistry;

    public Ceeql(String driverName, String url, String username, String password, MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
        this.driverName = driverName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.isConnected = false;

        connectToDatabase();
    }

    public Ceeql(String driverName, String url, String username, String password) {
        this(driverName, url, username, password, null);
    }

    public boolean isConnected() {
        return isConnected;
    }


    private String connectToDatabase() {
        try {
            log.debug("Connecting with driver " + driverName + " to " + url);

            Class clazz = Class.forName(driverName);
            log.debug(clazz);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("dataSourceClassName", driverName);
            config.setPoolName("HikariPool" + "." + url + "." + username);

            if (metricRegistry != null) {
                config.setMetricRegistry(metricRegistry);
            }

            DataSource ds = new HikariDataSource(config);

            //this.dbi = new DBI(ds);
            //this.dbi.setSQLLog(new Log4JLog());

            this.isConnected = true;
            return CeeqlMessage.message("Connected");
        } catch (Exception e) {
            this.isConnected = false;
            return CeeqlError.errorType(e.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void close() {
        this.isConnected = false;
    }
}
