package com.programsfuture.usermanagement.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DatabaseExecutor {

    private DatabaseExecutor() {
    }

    public static int executeUpdate(
            String sql,
            Object... parameters
    ) throws Exception {

        Connection connection = DatabaseManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, parameters);

            return statement.executeUpdate();
        }
    }

    public static int executeUpdate(
            Connection connection,
            String sql,
            Object... parameters
    ) throws Exception {

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, parameters);

            return statement.executeUpdate();
        }
    }

    public static List<Map<String, Object>> executeQuery(
            String sql,
            Object... parameters
    ) throws Exception {

//        long startTime = System.currentTimeMillis();

        Connection connection = DatabaseManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, parameters);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<Map<String, Object>> rows = new ArrayList<>();

                var metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {

                    Map<String, Object> row = new LinkedHashMap<>();

                    for (int i = 1; i <= columnCount; i++) {
                        row.put(
                                metaData.getColumnLabel(i),
                                resultSet.getObject(i)
                        );
                    }

                    rows.add(row);
                }

//                System.out.println(
//                        "DatabaseExecutor Time: "
//                        + (System.currentTimeMillis() - startTime)
//                        + " ms"
//                );

                return rows;
            }
        }
    }

    public static Connection getConnection() throws Exception {
        return DatabaseManager.getConnection();
    }

    public static void executeTransaction(
            java.util.function.Consumer<Connection> transaction
    ) throws Exception {

        Connection connection = DatabaseManager.getConnection();
        boolean originalAutoCommit = connection.getAutoCommit();

        try {
            connection.setAutoCommit(false);

            transaction.accept(connection);

            connection.commit();

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                e.addSuppressed(rollbackException);
            }

            throw e;

        } finally {
            connection.setAutoCommit(originalAutoCommit);
        }
    }

    private static void setParameters(
            PreparedStatement statement,
            Object... parameters
    ) throws SQLException {

        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }
}
