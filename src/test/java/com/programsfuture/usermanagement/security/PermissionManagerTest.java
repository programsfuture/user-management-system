package com.programsfuture.usermanagement.security;

import com.programsfuture.usermanagement.security.PermissionManager;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermissionManagerTest {

    @BeforeEach
    void clearPermissions() {
        PermissionManager.clear();
    }

    @Test
    void shouldDenyPermissionWhenNothingIsLoaded() {

        assertFalse(
                PermissionManager.hasPermission(1, 1)
        );
    }

    @Test
    void clearShouldRemovePreviouslyLoadedPermissions() throws Exception {

        var method = PermissionManager.class
                .getDeclaredMethod(
                        "loadRows",
                        List.class
                );

        method.setAccessible(true);

        method.invoke(
                null,
                List.of(
                        Map.of(
                                "FormId", 2,
                                "PermissionId", 3
                        )
                )
        );

        assertTrue(
                PermissionManager.hasPermission(2, 3)
        );

        PermissionManager.clear();

        assertFalse(
                PermissionManager.hasPermission(2, 3)
        );
    }

    @Test
    void shouldDistinguishFormAndPermissionIds() throws Exception {

        var method = PermissionManager.class
                .getDeclaredMethod(
                        "loadRows",
                        List.class
                );

        method.setAccessible(true);

        method.invoke(
                null,
                List.of(
                        Map.of(
                                "FormId", 2,
                                "PermissionId", 3
                        )
                )
        );

        assertTrue(
                PermissionManager.hasPermission(2, 3)
        );

        assertFalse(
                PermissionManager.hasPermission(2, 4)
        );

        assertFalse(
                PermissionManager.hasPermission(3, 3)
        );
    }
}