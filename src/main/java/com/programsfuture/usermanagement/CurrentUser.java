
package com.programsfuture.usermanagement;

public final class CurrentUser {

    private static int userId = -1;

    private CurrentUser() {
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        CurrentUser.userId = userId;
    }

    public static boolean isLoggedIn() {
        return userId > 0;
    }

    public static void clear() {
        userId = -1;
    }
}
