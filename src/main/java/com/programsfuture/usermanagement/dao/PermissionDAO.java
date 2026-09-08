package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import java.util.List;
import java.util.Map;

public class PermissionDAO {

    public List<Map<String, Object>> findAll() throws Exception {

        String sql = """
            SELECT
                Id,
                PermissionName,
                DisplayName,
                IsActive
            FROM Permissions
            WHERE IsActive = 1
            ORDER BY Id
            """;

        return DatabaseExecutor.executeQuery(sql);
    }
}
