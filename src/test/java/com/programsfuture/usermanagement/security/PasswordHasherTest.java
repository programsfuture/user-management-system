package com.programsfuture.usermanagement.security;

import com.programsfuture.usermanagement.security.PasswordHasher;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PasswordHasherTest {

    @Test
    void hashShouldProduceDifferentHashesForSamePassword() {

        String password = "TestPassword123";

        String hash1 = PasswordHasher.hash(password);
        String hash2 = PasswordHasher.hash(password);

        assertNotEquals(hash1, hash2);
    }

    @Test
    void verifyShouldAcceptCorrectPassword() {

        String password = "TestPassword123";
        String hash = PasswordHasher.hash(password);

        assertTrue(
                PasswordHasher.verify(password, hash)
        );
    }

    @Test
    void verifyShouldRejectIncorrectPassword() {

        String password = "TestPassword123";
        String wrongPassword = "WrongPassword123";

        String hash = PasswordHasher.hash(password);

        assertFalse(
                PasswordHasher.verify(wrongPassword, hash)
        );
    }

    @Test
    void hashShouldRejectBlankPassword() {

        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordHasher.hash(" ")
        );
    }

    @Test
    void verifyShouldRejectInvalidStoredHash() {

        assertFalse(
                PasswordHasher.verify(
                        "TestPassword123",
                        "invalid-hash"
                )
        );
    }
}