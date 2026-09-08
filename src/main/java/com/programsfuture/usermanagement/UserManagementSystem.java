package com.programsfuture.usermanagement;

import com.programsfuture.usermanagement.ui.LoginFrame;
import javax.swing.SwingUtilities;

public class UserManagementSystem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThemeManager.applySavedTheme();

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        });
    }
}
