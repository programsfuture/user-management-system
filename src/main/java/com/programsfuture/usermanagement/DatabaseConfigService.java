package com.cognitivekernel.warehouse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class DatabaseConfigService {

    private static final String APP_DIR = ".cgw";
    private static final String CONFIG_FILE = "db.conf";

    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    private static DatabaseConfig cachedConfig;

    /*
     * این مقدار فقط برای تولید کلید رمزنگاری استفاده می‌شود.
     * برای امنیت بسیار بالا مناسب نیست، چون داخل برنامه قرار دارد.
     */
    private static final String APPLICATION_SECRET
            = "CGW-Database-Configuration-2026";

    private DatabaseConfigService() {
    }

    private static Path getAppDirectory() {
        return Paths.get(
                System.getProperty("user.home"),
                APP_DIR
        );
    }

    private static Path getConfigFile() {
        return getAppDirectory().resolve(CONFIG_FILE);
    }

    private static SecretKeySpec getKey()
            throws Exception {

        String material
                = APPLICATION_SECRET
                + "|"
                + System.getProperty("user.name");

        byte[] hash = MessageDigest.getInstance("SHA-256")
                .digest(material.getBytes(StandardCharsets.UTF_8));

        return new SecretKeySpec(hash, "AES");
    }

    public static void save(DatabaseConfig config)
            throws Exception {

        SecretKeySpec key = getKey();

        String plainText = String.join("\n",
                config.getServer(),
                String.valueOf(config.getPort()),
                config.getDatabase(),
                config.getUsername(),
                config.getPassword()
        );

        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                key,
                new GCMParameterSpec(GCM_TAG_LENGTH, iv)
        );

        byte[] encrypted = cipher.doFinal(
                plainText.getBytes(StandardCharsets.UTF_8)
        );

        String result
                = Base64.getEncoder().encodeToString(iv)
                + "\n"
                + Base64.getEncoder().encodeToString(encrypted);

        Files.createDirectories(getAppDirectory());

        Files.writeString(
                getConfigFile(),
                result,
                StandardCharsets.UTF_8
        );
        cachedConfig = config;
    }

    public static DatabaseConfig load()
            throws Exception {

        if (cachedConfig != null) {
            return cachedConfig;
        }

        if (!Files.exists(getConfigFile())) {
            return null;
        }

        SecretKeySpec key = getKey();

        String content = Files.readString(
                getConfigFile(),
                StandardCharsets.UTF_8
        );

        String[] parts = content.split("\\R");

        if (parts.length != 2) {
            throw new IllegalStateException(
                    "فرمت فایل تنظیمات نامعتبر است."
            );
        }

        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encrypted = Base64.getDecoder().decode(parts[1]);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(
                Cipher.DECRYPT_MODE,
                key,
                new GCMParameterSpec(GCM_TAG_LENGTH, iv)
        );

        byte[] decrypted = cipher.doFinal(encrypted);

        String[] values = new String(
                decrypted,
                StandardCharsets.UTF_8
        ).split("\n", -1);

        if (values.length != 5) {
            throw new IllegalStateException(
                    "اطلاعات تنظیمات دیتابیس نامعتبر است."
            );
        }

        cachedConfig = new DatabaseConfig(
                values[0],
                Integer.parseInt(values[1]),
                values[2],
                values[3],
                values[4],
                true
        );

        return cachedConfig;
    }
}
