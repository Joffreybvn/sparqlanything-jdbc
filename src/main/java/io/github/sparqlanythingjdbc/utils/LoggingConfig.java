package io.github.sparqlanythingjdbc.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public final class LoggingConfig {

    private static final String LOGGER_NAME = "io.github.sparqlanythingjdbc";
    private static boolean configured = false;

    // We'll create one "root" logger for the entire driver
    private static final Logger LOGGER = Logger.getLogger(LOGGER_NAME);

    private LoggingConfig() {
        // Prevent instantiation
    }

    public static synchronized void configureIfNeeded(Properties info) {
        if (configured) {
            return;
        }

        String levelStr = info.getProperty("loggerLevel");
        String fileStr  = info.getProperty("loggerFile");

        // If no logging level specified, skip any special config
        if (levelStr == null) {
            configured = true;
            return;
        }

        // Parse the level
        Level level;
        try {
            level = Level.parse(levelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            level = Level.INFO; // default
        }

        // Remove any default handlers
        for (Handler h : LOGGER.getHandlers()) {
            LOGGER.removeHandler(h);
        }

        if (fileStr != null && !fileStr.trim().isEmpty()) {
            try {
                // File logging
                FileHandler fileHandler = new FileHandler(fileStr, true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(level);
                LOGGER.addHandler(fileHandler);
            } catch (IOException e) {
                System.err.println("Could not create FileHandler. Reason: " + e);
                addConsoleHandler(level);
            }
        } else {
            // loggerLevel only, so print to console
            addConsoleHandler(level);
        }

        // Set level on the logger itself
        LOGGER.setLevel(level);
        configured = true;
    }

    /**
     * Gives access to the shared logger for the entire JDBC driver.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /** Helper to set up a ConsoleHandler */
    private static void addConsoleHandler(Level level) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(level);
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
    }
}
