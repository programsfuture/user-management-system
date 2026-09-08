package com.programsfuture.usermanagement.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordHasher {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 120_000;

    private PasswordHasher() {
    }

    public static String hash(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("رمز عبور نمی‌تواند خالی باشد.");
        }

        try {
            byte[] salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);

            byte[] hash = pbkdf2(password, salt, ITERATIONS);

            return "PBKDF2$"
                    + ITERATIONS
                    + "$"
                    + Base64.getEncoder().encodeToString(salt)
                    + "$"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "الگوریتم رمزنگاری PBKDF2 در سیستم موجود نیست.",
                    e
            );
        }
    }

    public static boolean verify(
            String password,
            String storedHash
    ) {

        if (password == null
                || storedHash == null
                || storedHash.isBlank()) {
            return false;
        }

        try {
            String[] parts = storedHash.split("\\$");

            if (parts.length != 4
                    || !"PBKDF2".equals(parts[0])) {
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);

            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[3]);

            byte[] actualHash = pbkdf2(
                    password,
                    salt,
                    iterations
            );

            return MessageDigest.isEqual(
                    expectedHash,
                    actualHash
            );

        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(
            String password,
            byte[] salt,
            int iterations
    ) throws NoSuchAlgorithmException {

        try {
            javax.crypto.spec.PBEKeySpec spec
                    = new javax.crypto.spec.PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            iterations,
                            256
                    );

            javax.crypto.SecretKeyFactory factory
                    = javax.crypto.SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            return factory.generateSecret(spec).getEncoded();

        } catch (java.security.spec.InvalidKeySpecException e) {
            throw new IllegalStateException(
                    "خطا در ایجاد Hash رمز عبور.",
                    e
            );
        }
    }
}
