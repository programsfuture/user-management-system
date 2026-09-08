package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import java.util.List;
import java.util.Map;

public class FormDAO {

    public List<Map<String, Object>> findAll() throws Exception {

        String sql = """
            SELECT
                Id,
                FormName,
                DisplayName,
                IsActive
            FROM Forms
            WHERE IsActive = 1
            ORDER BY Id
            """;

        return DatabaseExecutor.executeQuery(sql);
    }
}