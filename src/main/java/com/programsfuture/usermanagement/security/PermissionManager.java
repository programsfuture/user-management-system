package com.programsfuture.usermanagement.security;

import com.programsfuture.usermanagement.CurrentUser;
import com.programsfuture.usermanagement.dao.GroupDAO;
import com.programsfuture.usermanagement.dao.UserCustomPermissionsDAO;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PermissionManager {

    private static final Set<String> permissions = new HashSet<>();

    private PermissionManager() {
    }

    public static void load() throws Exception {

        permissions.clear();

        if (!CurrentUser.isLoggedIn()) {
            return;
        }

        int userId = CurrentUser.getUserId();

        UserCustomPermissionsDAO customDAO
                = new UserCustomPermissionsDAO();

        if (customDAO.hasCustomPermissionConfiguration(userId)) {

            List<Map<String, Object>> rows
                    = customDAO.findPermissionIdsByUserId(userId);

            loadRows(rows);
            return;
        }

        GroupDAO groupDAO = new GroupDAO();

        List<Map<String, Object>> rows
                = groupDAO.findEffectivePermissionsByUserId(userId);

        loadRows(rows);
    }

    private static void loadRows(List<Map<String, Object>> rows) {

        for (Map<String, Object> row : rows) {

            int formId
                    = ((Number) row.get("FormId")).intValue();

            int permissionId
                    = ((Number) row.get("PermissionId")).intValue();

            permissions.add(
                    formId + ":" + permissionId
            );
        }
    }

    public static boolean hasPermission(
            int formId,
            int permissionId) {

        return permissions.contains(
                formId + ":" + permissionId
        );
    }

    public static void clear() {
        permissions.clear();
    }
}
