package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import com.programsfuture.usermanagement.model.UserPositionHistory;

public class UserPositionHistoryDAO {

    public int insert(UserPositionHistory history) throws Exception {

        String sql = """
            INSERT INTO UserPositionHistory
                (UserId, OldPositionId, NewPositionId,
                 ChangedByUserId, ChangedDateShamsi)
            VALUES (?, ?, ?, ?, ?)
            """;

        return DatabaseExecutor.executeUpdate(
                sql,
                history.getUserId(),
                history.getOldPositionId(),
                history.getNewPositionId(),
                history.getChangedByUserId(),
                history.getChangedDateShamsi()
        );
    }
}
