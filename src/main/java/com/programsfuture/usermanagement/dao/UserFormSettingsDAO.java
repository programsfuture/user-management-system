package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;

import java.util.List;
import java.util.Map;

public class UserFormSettingsDAO {

    private static final int DEFAULT_PAGE_SIZE = 10;

    public int getPageSize(int userId, int formId) throws Exception {

        String sql = """
            SELECT PageSize
            FROM UserFormSettings
            WHERE UserId = ? AND FormId = ?
            """;

        List<Map<String, Object>> rows = DatabaseExecutor.executeQuery(
                sql,
                userId,
                formId
        );

        if (rows.isEmpty()) {
            return DEFAULT_PAGE_SIZE;
        }

        return ((Number) rows.get(0).get("PageSize")).intValue();
    }

    public int savePageSize(
            int userId,
            int formId,
            int pageSize
    ) throws Exception {

        String sql = """
            MERGE UserFormSettings AS target
            USING (SELECT ? AS UserId, ? AS FormId, ? AS PageSize) AS source
            ON target.UserId = source.UserId
               AND target.FormId = source.FormId
            WHEN MATCHED THEN
                UPDATE SET PageSize = source.PageSize
            WHEN NOT MATCHED THEN
                INSERT (UserId, FormId, PageSize)
                VALUES (source.UserId, source.FormId, source.PageSize);
            """;

        return DatabaseExecutor.executeUpdate(
                sql,
                userId,
                formId,
                pageSize
        );
    }
}