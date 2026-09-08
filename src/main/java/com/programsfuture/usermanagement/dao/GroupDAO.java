package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class GroupDAO {

    public List<Map<String, Object>> findAll() throws Exception {

        String sql = """
        SELECT
            Id,
            Name,
            Description,
            IsActive
        FROM Groups
        WHERE IsActive = 1
        ORDER BY Name
        """;

        return DatabaseExecutor.executeQuery(sql);
    }

    public Map<String, Object> findByName(String name) throws Exception {

        String sql = """
        SELECT
            Id,
            Name,
            Description,
            IsActive
        FROM Groups
        WHERE Name = ?
        """;

        List<Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(sql, name);

        if (rows.isEmpty()) {
            return null;
        }

        return rows.get(0);
    }

    public List<Map<String, Object>> findByUserId(int userId) throws Exception {

        String sql = """
        SELECT
            g.Id,
            g.Name,
            g.Description,
            g.IsActive
        FROM Groups g
        INNER JOIN UserGroups ug
            ON ug.GroupId = g.Id
        WHERE ug.UserId = ?
          AND g.IsActive = 1
        ORDER BY g.Name
        """;

        return DatabaseExecutor.executeQuery(sql, userId);
    }

    public int addUserToGroup(int userId, int groupId) throws Exception {

        String sql = """
        INSERT INTO UserGroups
            (UserId, GroupId)
        VALUES (?, ?)
        """;

        return DatabaseExecutor.executeUpdate(
                sql,
                userId,
                groupId
        );
    }

    public int removeUserFromGroup(int userId, int groupId) throws Exception {

        String sql = """
        DELETE FROM UserGroups
        WHERE UserId = ?
          AND GroupId = ?
        """;

        return DatabaseExecutor.executeUpdate(
                sql,
                userId,
                groupId
        );
    }

    public List<Map<String, Object>> findPermissionsByGroupId(int groupId)
            throws Exception {

        String sql = """
        SELECT
            GroupId,
            FormId,
            PermissionId
        FROM GroupFormPermissions
        WHERE GroupId = ?
        ORDER BY FormId, PermissionId
        """;

        return DatabaseExecutor.executeQuery(sql, groupId);
    }

    public List<Map<String, Object>> findEffectivePermissionsByUserId(int userId)
            throws Exception {

        String sql = """
        SELECT DISTINCT
            gfp.FormId,
            gfp.PermissionId
        FROM GroupFormPermissions gfp
        INNER JOIN UserGroups ug
            ON ug.GroupId = gfp.GroupId
        INNER JOIN Groups g
            ON g.Id = ug.GroupId
        INNER JOIN Forms f
            ON f.Id = gfp.FormId
        INNER JOIN Permissions p
            ON p.Id = gfp.PermissionId
        WHERE ug.UserId = ?
          AND g.IsActive = 1
          AND f.IsActive = 1
          AND p.IsActive = 1
        ORDER BY
            gfp.FormId,
            gfp.PermissionId
        """;

        return DatabaseExecutor.executeQuery(sql, userId);
    }

    public int deletePermissionsByGroupId(
            Connection connection,
            int groupId
    ) throws Exception {

        String sql = """
        DELETE FROM GroupFormPermissions
        WHERE GroupId = ?
        """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                groupId
        );
    }

    public int addPermission(
            Connection connection,
            int groupId,
            int formId,
            int permissionId
    ) throws Exception {

        String sql = """
        INSERT INTO GroupFormPermissions
            (GroupId, FormId, PermissionId)
        VALUES (?, ?, ?)
        """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                groupId,
                formId,
                permissionId
        );
    }

    public int deleteUserGroupsByUserId(int userId) throws Exception {

        String sql = """
        DELETE FROM UserGroups
        WHERE UserId = ?
        """;

        return DatabaseExecutor.executeUpdate(sql, userId);
    }

    public int deleteUserGroupsByUserId(
            Connection connection,
            int userId
    ) throws Exception {

        String sql = """
    DELETE FROM UserGroups
    WHERE UserId = ?
    """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                userId
        );
    }

    public int addUserToGroup(
            Connection connection,
            int userId,
            int groupId
    ) throws Exception {

        String sql = """
    INSERT INTO UserGroups
        (UserId, GroupId)
    VALUES (?, ?)
    """;

        return DatabaseExecutor.executeUpdate(
                connection,
                sql,
                userId,
                groupId
        );
    }

}
