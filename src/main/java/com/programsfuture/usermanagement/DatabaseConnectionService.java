package com.programsfuture.usermanagement;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DatabaseConnectionService {

    private static Connection cachedConnection;

    private DatabaseConnectionService() {
    }

    public static synchronized Connection getConnection(DatabaseConfig config)
            throws Exception {

        if (cachedConnection != null && !cachedConnection.isClosed()) {
            return cachedConnection;
        }

        String url = String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=true;trustServerCertificate=true",
                config.getServer(),
                config.getPort(),
                config.getDatabase()
        );

        cachedConnection = DriverManager.getConnection(
                url,
                config.getUsername(),
                config.getPassword()
        );

        return cachedConnection;
    }

    public static void testConnection(DatabaseConfig config)
            throws Exception {

        try (Connection connection = DriverManager.getConnection(
                String.format(
                        "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=true;trustServerCertificate=true",
                        config.getServer(),
                        config.getPort(),
                        config.getDatabase()
                ),
                config.getUsername(),
                config.getPassword()
        )) {
            // Connection test successful.
        }
    }
}
