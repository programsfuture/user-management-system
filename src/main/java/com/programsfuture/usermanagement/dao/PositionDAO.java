package com.programsfuture.usermanagement.dao;

import com.programsfuture.usermanagement.data.DatabaseExecutor;
import com.programsfuture.usermanagement.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PositionDAO {

    public int insert(Position position) throws Exception {
        String sql = """
            INSERT INTO Positions (PositionName, Description, IsActive)
            VALUES (?, ?, ?)
            """;

        return DatabaseExecutor.executeUpdate(
                sql,
                position.getPositionName(),
                position.getDescription(),
                position.isActive()
        );
    }

    public int update(Position position) throws Exception {
        String sql = """
            UPDATE Positions
            SET PositionName = ?,
                Description = ?,
                IsActive = ?
            WHERE PositionId = ?
            """;

        return DatabaseExecutor.executeUpdate(
                sql,
                position.getPositionName(),
                position.getDescription(),
                position.isActive(),
                position.getPositionId()
        );
    }

    public int delete(int positionId) throws Exception {
        String sql = """
            DELETE FROM Positions
            WHERE PositionId = ?
            """;

        return DatabaseExecutor.executeUpdate(sql, positionId);
    }

    public int count(String searchText) throws Exception {
        String sql = """
        SELECT COUNT(*) AS TotalRecords
        FROM Positions
        WHERE PositionName LIKE ?
           OR Description LIKE ?
        """;

        String search = "%" + searchText + "%";

        List<Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(sql, search, search);

        return ((Number) rows.get(0).get("TotalRecords")).intValue();
    }

    public List<Position> find(String searchText, int offset, int limit) throws Exception {
        String sql = """
            SELECT PositionId, PositionName, Description, IsActive
            FROM Positions
            WHERE PositionName LIKE ?
               OR Description LIKE ?
            ORDER BY PositionId
            OFFSET ? ROWS
            FETCH NEXT ? ROWS ONLY
            """;

        String search = "%" + searchText + "%";

        List<Map<String, Object>> rows
                = DatabaseExecutor.executeQuery(
                        sql,
                        search,
                        search,
                        offset,
                        limit
                );

        List<Position> positions = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            positions.add(new Position(
                    ((Number) row.get("PositionId")).intValue(),
                    (String) row.get("PositionName"),
                    (String) row.get("Description"),
                    (Boolean) row.get("IsActive")
            ));
        }

        return positions;
    }
}
