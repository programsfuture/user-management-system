package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class UserCustomPermissionsDAO {

    

    public List<Map<String, Object>> findPermissionIdsByUserId(int userId)
            throws Exception {

        String sql = """
            SELECT
                ucp.FormId,
                ucp.PermissionId
            FROM UserCustomPermissions ucp
            INNER JOIN Forms f
                ON f.Id = ucp.FormId
            INNER JOIN Permissions p
                ON p.Id = ucp.PermissionId
            WHERE ucp.UserId = ?
              AND f.IsActive = 1
              AND p.IsActive = 1
            ORDER BY ucp.FormId, ucp.PermissionId
            """;

        return DatabaseExecutor.executeQuery(sql, userId);
    }

    

    

    public void deleteCustomPermissionsAndConfiguration(int userId)
            throws Exception {

        ensureConfigurationTable();

        Connection connection = DatabaseExecutor.getConnection();
        boolean originalAutoCommit = connection.getAutoCommit();

        String deletePermissionsSql = """
            DELETE FROM UserCustomPermissions
            WHERE UserId = ?
            """;

        String deleteConfigurationSql = """
            DELETE FROM UserCustomPermissionConfigurations
            WHERE UserId = ?
            """;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement deletePermissionsStatement
                    = connection.prepareStatement(deletePermissionsSql); PreparedStatement deleteConfigurationStatement
                    = connection.prepareStatement(deleteConfigurationSql)) {

                deletePermissionsStatement.setInt(1, userId);
                deletePermissionsStatement.executeUpdate();

                deleteConfigurationStatement.setInt(1, userId);
                deleteConfigurationStatement.executeUpdate();

                connection.commit();
            }

        } catch (Exception ex) {

            try {
                connection.rollback();
            } catch (Exception rollbackException) {
                ex.addSuppressed(rollbackException);
            }

            throw ex;

        } finally {
            connection.setAutoCommit(originalAutoCommit);
        }
    }

    

    public boolean hasCustomPermissionConfiguration(int userId)
            throws Exception {

        ensureConfigurationTable();

        String sql = """
            SELECT TOP 1 UserId
            FROM UserCustomPermissionConfigurations
            WHERE UserId = ?
            """;

        return !DatabaseExecutor.executeQuery(sql, userId).isEmpty();
    }

    public void replacePermissions(
            int userId,
            List<int[]> permissions
    ) throws Exception {

        ensureConfigurationTable();

        Connection connection = DatabaseExecutor.getConnection();
        boolean originalAutoCommit = connection.getAutoCommit();

        String deleteSql = """
            DELETE FROM UserCustomPermissions
            WHERE UserId = ?
            """;

        String insertSql = """
            INSERT INTO UserCustomPermissions
                (UserId, FormId, PermissionId)
            VALUES (?, ?, ?)
            """;

        String configurationSql = """
            MERGE UserCustomPermissionConfigurations AS target
            USING (SELECT ? AS UserId) AS source
            ON target.UserId = source.UserId
            WHEN MATCHED THEN
                UPDATE SET UpdatedAt = SYSDATETIME()
            WHEN NOT MATCHED THEN
                INSERT (UserId)
                VALUES (source.UserId);
            """;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStatement
                    = connection.prepareStatement(deleteSql); PreparedStatement insertStatement
                    = connection.prepareStatement(insertSql); PreparedStatement configurationStatement
                    = connection.prepareStatement(configurationSql)) {

                deleteStatement.setInt(1, userId);
                deleteStatement.executeUpdate();

                for (int[] permission : permissions) {
                    insertStatement.setInt(1, userId);
                    insertStatement.setInt(2, permission[0]);
                    insertStatement.setInt(3, permission[1]);
                    insertStatement.addBatch();
                }

                if (!permissions.isEmpty()) {
                    insertStatement.executeBatch();
                }

                configurationStatement.setInt(1, userId);
                configurationStatement.executeUpdate();

                connection.commit();
            }

        } catch (Exception ex) {

            try {
                connection.rollback();
            } catch (Exception rollbackException) {
                ex.addSuppressed(rollbackException);
            }

            throw ex;

        } finally {
            connection.setAutoCommit(originalAutoCommit);
        }
    }

    private void ensureConfigurationTable()
            throws Exception {

        String sql = """
            IF OBJECT_ID(
                'dbo.UserCustomPermissionConfigurations',
                'U'
            ) IS NULL
            BEGIN
                CREATE TABLE UserCustomPermissionConfigurations (
                    UserId INT NOT NULL
                        PRIMARY KEY,
                    UpdatedAt DATETIME2 NOT NULL
                        DEFAULT SYSDATETIME()
                );
            END
            """;

        DatabaseExecutor.executeUpdate(sql);
    }
}
