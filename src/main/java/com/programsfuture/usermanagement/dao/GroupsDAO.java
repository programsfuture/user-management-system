package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class GroupsDAO {

    public List<Map<String, Object>> find(
            String searchText,
            int offset,
            int pageSize
    ) throws Exception {

        String sql = """
            SELECT
                Id,
                Name,
                Description,
                IsActive
            FROM Groups
            WHERE (
                          ? = ''
                          OR Name LIKE ?
                        )
            ORDER BY Name
            OFFSET ? ROWS
            FETCH NEXT ? ROWS ONLY
            """;

        String search = searchText == null
                ? ""
                : searchText.trim();

        String pattern = "%" + search + "%";

        return DatabaseExecutor.executeQuery(
                sql,
                search,
                pattern,
                offset,
                pageSize
        );
    }

    public int count(String searchText) throws Exception {

        String sql = """
            SELECT COUNT(*) AS Total
            FROM Groups
            WHERE (
                          ? = ''
                          OR Name LIKE ?
                        )
            """;

        String search = searchText == null
                ? ""
                : searchText.trim();

        String pattern = "%" + search + "%";

        List<Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(
                        sql,
                        search,
                        pattern
                );

        if (rows.isEmpty()) {
            return 0;
        }

        return ((Number) rows.get(0).get("Total")).intValue();
    }

    public int insert(
            String name,
            String description,
            boolean isActive
    ) throws Exception {

        String sql = """
        INSERT INTO Groups
            (Name, Description, IsActive)
        VALUES (?, ?, ?)
        """;

        return DatabaseExecutor.executeUpdate(
                sql,
                name,
                description,
                isActive
        );
    }

    public int insertAndReturnId(
            Connection connection,
            String name,
            String description,
            boolean isActive
    ) throws Exception {

        String sql = """
        INSERT INTO Groups
            (Name, Description, IsActive)
        OUTPUT INSERTED.Id
        VALUES (?, ?, ?)
        """;

        try (PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setBoolean(3, isActive);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new Exception(
                            "شناسه گروه جدید دریافت نشد."
                    );
                }

                return resultSet.getInt(1);
            }
        }
    }

    public int update(
            Connection connection,
            int id,
            String name,
            String description,
            boolean isActive
    ) throws Exception {

        String sql = """
        UPDATE Groups
        SET
            Name = ?,
            Description = ?,
            IsActive = ?
        WHERE Id = ?
        """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                name,
                description,
                isActive,
                id
        );
    }

    public int update(
            int id,
            String name,
            String description,
            boolean isActive
    ) throws Exception {

        String sql = """
        UPDATE Groups
        SET
            Name = ?,
            Description = ?,
            IsActive = ?
        WHERE Id = ?
        """;

        return DatabaseExecutor.executeUpdate(
                sql,
                name,
                description,
                isActive,
                id
        );
    }

    public int deactivate(int id) throws Exception {

        String sql = """
            UPDATE Groups
            SET IsActive = 0
            WHERE Id = ?
            """;

        return DatabaseExecutor.executeUpdate(
                sql,
                id
        );
    }

    public int deactivate(
            Connection connection,
            int id
    ) throws Exception {

        String sql = """
        UPDATE Groups
        SET IsActive = 0
        WHERE Id = ?
        """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                id
        );
    }

}
