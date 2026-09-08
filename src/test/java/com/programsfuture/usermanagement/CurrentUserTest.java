package com.programsfuture.usermanagement;

import com.programsfuture.usermanagement.CurrentUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrentUserTest {

    @BeforeEach
    void resetCurrentUser() {
        CurrentUser.clear();
    }

    @Test
    void shouldNotBeLoggedInInitially() {

        assertFalse(CurrentUser.isLoggedIn());
        assertEquals(-1, CurrentUser.getUserId());
    }

    @Test
    void shouldSetUserIdAndLoginState() {

        CurrentUser.setUserId(10);

        assertEquals(10, CurrentUser.getUserId());
        assertTrue(CurrentUser.isLoggedIn());
    }

    @Test
    void shouldClearUserSession() {

        CurrentUser.setUserId(10);

        CurrentUser.clear();

        assertEquals(-1, CurrentUser.getUserId());
        assertFalse(CurrentUser.isLoggedIn());
    }

    @Test
    void zeroUserIdShouldNotBeLoggedIn() {

        CurrentUser.setUserId(0);

        assertEquals(0, CurrentUser.getUserId());
        assertFalse(CurrentUser.isLoggedIn());
    }

    @Test
    void negativeUserIdShouldNotBeLoggedIn() {

        CurrentUser.setUserId(-5);

        assertEquals(-5, CurrentUser.getUserId());
        assertFalse(CurrentUser.isLoggedIn());
    }

    @Test
    void positiveUserIdShouldBeLoggedIn() {

        CurrentUser.setUserId(1);

        assertEquals(1, CurrentUser.getUserId());
        assertTrue(CurrentUser.isLoggedIn());
    }
}