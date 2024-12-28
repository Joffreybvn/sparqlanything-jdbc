package io.github.sparqlanythingjdbc;

import io.github.sparqlanythingjdbc.utils.LoggingConfig;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {

    private static final Logger LOGGER = LoggingConfig.getLogger();
    public static final String URL_ID = "jdbc:sparql-anything";
    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 1;

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register SparqlAnythingDriver");
        }
    }

    @Override
    public Connection connect(String url, Properties properties) throws SQLException {
        LoggingConfig.configureIfNeeded(properties);
        LOGGER.finest("Calling Driver.connect() method with parameters url=" + url + ", properties=" + properties);

        if (url.startsWith(URL_ID)) {
            LOGGER.info("Opening connection to " + url);
            return new Connection();
        }
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        LOGGER.finest("Calling Driver.acceptsURL() with url=" + url);
        return (url != null && url.startsWith(URL_ID));
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties properties) throws SQLException {
        LOGGER.finest("Calling Driver.getPropertyInfo() with url=" + url + ", properties=" + properties);
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        LOGGER.finest("Calling Driver.getMajorVersion()");
        return MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        LOGGER.finest("Calling Driver.getMinorVersion()");
        return MINOR_VERSION;
    }

    @Override
    public boolean jdbcCompliant() {
        LOGGER.finest("Calling Driver.jdbcCompliant()");
        /* Not JDBC4 compliant because this Driver depends on third-party jars */
        return false;
    }

    @Override
    public Logger getParentLogger() {
        LOGGER.finest("Calling Driver.getParentLogger()");
        return Logger.getGlobal();
    }
}
