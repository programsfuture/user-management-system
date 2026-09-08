package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import com.programsfuture.usermanagement.model.User;

public class UserDAO {

    public int insert(User user) throws Exception {
        String sql = """
        INSERT INTO Users
            (FirstName, LastName, Username, PasswordHash, IsActive, PositionId)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        return DatabaseExecutor.executeUpdate(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPasswordHash(),
                user.isActive(),
                user.getPositionId()
        );

    }

    public int insertAndReturnId(
            java.sql.Connection connection,
            User user
    ) throws Exception {

        String sql = """
        INSERT INTO Users
            (FirstName, LastName, Username, PasswordHash, IsActive, PositionId)
        OUTPUT INSERTED.Id
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (java.sql.PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setObject(1, user.getFirstName());
            statement.setObject(2, user.getLastName());
            statement.setObject(3, user.getUsername());
            statement.setObject(4, user.getPasswordHash());
            statement.setObject(5, user.isActive());
            statement.setObject(6, user.getPositionId());

            try (java.sql.ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new Exception(
                            "شناسه کاربر جدید دریافت نشد."
                    );
                }

                return resultSet.getInt(1);
            }
        }
    }

    public int update(User user) throws Exception {

        return DatabaseExecutor.executeUpdate(
                """
            UPDATE Users
            SET FirstName = ?,
                LastName = ?,
                Username = ?,
                PasswordHash = CASE
                    WHEN ? IS NULL OR LTRIM(RTRIM(?)) = ''
                    THEN PasswordHash
                    ELSE ?
                END,
                IsActive = ?,
                PositionId = ?
            WHERE Id = ?
            """,
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getPasswordHash(),
                user.getPasswordHash(),
                user.isActive(),
                user.getPositionId(),
                user.getId()
        );
    }

    public int update(
            java.sql.Connection connection,
            User user
    ) throws Exception {

        String sql = """
        UPDATE Users
        SET FirstName = ?,
            LastName = ?,
            Username = ?,
            PasswordHash = CASE
                WHEN ? IS NULL OR LTRIM(RTRIM(?)) = ''
                THEN PasswordHash
                ELSE ?
            END,
            IsActive = ?,
            PositionId = ?
        WHERE Id = ?
        """;

        try (java.sql.PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setObject(1, user.getFirstName());
            statement.setObject(2, user.getLastName());
            statement.setObject(3, user.getUsername());
            statement.setObject(4, user.getPasswordHash());
            statement.setObject(5, user.getPasswordHash());
            statement.setObject(6, user.getPasswordHash());
            statement.setObject(7, user.isActive());
            statement.setObject(8, user.getPositionId());
            statement.setObject(9, user.getId());

            return statement.executeUpdate();
        }
    }

    public int delete(int userId) throws Exception {
        String sql = """
        DELETE FROM Users
        WHERE Id = ?
        """;

        return DatabaseExecutor.executeUpdate(sql, userId);
    }

    public int delete(
            java.sql.Connection connection,
            int userId
    ) throws Exception {

        String sql = """
        DELETE FROM Users
        WHERE Id = ?
        """;

        try (java.sql.PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setObject(1, userId);

            return statement.executeUpdate();
        }
    }

    public User findById(int userId) throws Exception {

        String sql = """
        SELECT Id,
               FirstName,
               LastName,
               Username,
               PasswordHash,
               IsActive,
               PositionId
        FROM Users
        WHERE Id = ?
        """;

        java.util.List<java.util.Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(sql, userId);

        if (rows.isEmpty()) {
            return null;
        }

        java.util.Map<String, Object> row = rows.get(0);

        return new User(
                ((Number) row.get("Id")).intValue(),
                (String) row.get("FirstName"),
                (String) row.get("LastName"),
                (String) row.get("Username"),
                (String) row.get("PasswordHash"),
                (Boolean) row.get("IsActive"),
                row.get("PositionId") == null
                ? null
                : ((Number) row.get("PositionId")).intValue()
        );
    }

    public int count(String searchText) throws Exception {
        String sql = """
        SELECT COUNT(*) AS TotalRecords
        FROM Users
        WHERE FirstName LIKE ?
           OR LastName LIKE ?
           OR Username LIKE ?
           OR CONCAT(FirstName, ' ', LastName) LIKE ?
        """;

        String search = "%" + (searchText == null ? "" : searchText.trim()) + "%";

        java.util.List<java.util.Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(
                        sql,
                        search,
                        search,
                        search,
                        search
                );

        return ((Number) rows.get(0).get("TotalRecords")).intValue();
    }

    public java.util.List<User> find(
            String searchText,
            int offset,
            int limit
    ) throws Exception {

        String sql = """
        SELECT u.Id,
               u.FirstName,
               u.LastName,
               u.Username,
               u.PasswordHash,
               u.IsActive,
               u.PositionId,
               p.PositionName
        FROM Users u
        LEFT JOIN Positions p ON p.PositionId = u.PositionId
        WHERE u.FirstName LIKE ?
           OR u.LastName LIKE ?
           OR u.Username LIKE ?
           OR CONCAT(u.FirstName, ' ', u.LastName) LIKE ?
        ORDER BY u.Id
        OFFSET ? ROWS
        FETCH NEXT ? ROWS ONLY
        """;

        String search = "%" + (searchText == null ? "" : searchText.trim()) + "%";

        java.util.List<java.util.Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(
                        sql,
                        search,
                        search,
                        search,
                        search,
                        offset,
                        limit
                );

        java.util.List<User> users = new java.util.ArrayList<>();

        for (java.util.Map<String, Object> row : rows) {
            User user = new User(
                    ((Number) row.get("Id")).intValue(),
                    (String) row.get("FirstName"),
                    (String) row.get("LastName"),
                    (String) row.get("Username"),
                    (String) row.get("PasswordHash"),
                    (Boolean) row.get("IsActive"),
                    row.get("PositionId") == null
                    ? null
                    : ((Number) row.get("PositionId")).intValue()
            );

            user.setPositionName(
                    (String) row.get("PositionName")
            );

            users.add(user);
        }

        return users;
    }

    public User findByUsername(String username) throws Exception {

        String sql = """
        SELECT Id,
               FirstName,
               LastName,
               Username,
               PasswordHash,
               IsActive,
               PositionId
        FROM Users
        WHERE Username = ?
        """;

        java.util.List<java.util.Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(sql, username);

        if (rows.isEmpty()) {
            return null;
        }

        java.util.Map<String, Object> row = rows.get(0);

        return new User(
                ((Number) row.get("Id")).intValue(),
                (String) row.get("FirstName"),
                (String) row.get("LastName"),
                (String) row.get("Username"),
                (String) row.get("PasswordHash"),
                (Boolean) row.get("IsActive"),
                row.get("PositionId") == null
                ? null
                : ((Number) row.get("PositionId")).intValue()
        );
    }

    

}
