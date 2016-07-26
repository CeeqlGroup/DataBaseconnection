package org.ceeqlgroup.db;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        //connectToDatabase();
    }

    public Ceeql(String driverName, String url, String username, String password) {
        this(driverName, url, username, password, null);
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void close() throws Exception {

    }
}
