package com.programsfuture.usermanagement.data;

import com.programsfuture.usermanagement.DatabaseConfig;
import com.programsfuture.usermanagement.DatabaseConnectionService;
import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseManager {

    private static Connection connection;

    private DatabaseManager() {
    }

    public static synchronized Connection getConnection()
            throws Exception {

        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        DatabaseConfig config
                = com.programsfuture.usermanagement.DatabaseConfigService.load();

        if (config == null) {
            throw new SQLException(
                    "تنظیمات اتصال به دیتابیس یافت نشد."
            );
        }

        connection = DatabaseConnectionService.getConnection(config);

        return connection;
    }

    public static synchronized void closeConnection() {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // نادیده گرفتن
            }

            connection = null;
        }
    }
}