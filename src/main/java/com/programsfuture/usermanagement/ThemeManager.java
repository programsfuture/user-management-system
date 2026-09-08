package com.programsfuture.usermanagement;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class ThemeManager {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(ThemeManager.class.getName());

    private static final Preferences PREFS
            = Preferences.userNodeForPackage(ThemeManager.class);

    private static final String THEME_KEY = "darkTheme";

    private static boolean dark
            = PREFS.getBoolean(THEME_KEY, true);

    private ThemeManager() {
    }

    public static void applySavedTheme() {

        try {
            UIManager.setLookAndFeel(
                    dark ? new FlatDarkLaf() : new FlatLightLaf()
            );

        } catch (Exception ex) {

            logger.log(
                    java.util.logging.Level.WARNING,
                    "خطا در اعمال تم ذخیره‌شده.",
                    ex
            );
        }
    }

    public static void toggleTheme() {

        dark = !dark;

        PREFS.putBoolean(THEME_KEY, dark);

        try {
            UIManager.setLookAndFeel(
                    dark ? new FlatDarkLaf() : new FlatLightLaf()
            );

            for (java.awt.Window window : java.awt.Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }

        } catch (Exception ex) {

            logger.log(
                    java.util.logging.Level.WARNING,
                    "خطا در تغییر تم.",
                    ex
            );
        }
    }

    public static boolean isDark() {
        return dark;
    }
}