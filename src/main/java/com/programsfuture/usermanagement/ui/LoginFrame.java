package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.ThemeManager;
import java.awt.ComponentOrientation;

public class LoginFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());

    public LoginFrame() {
        initComponents();
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        btnTheme.setText(ThemeManager.isDark() ? "تم روشن" : "تم تاریک");
        getRootPane().setDefaultButton(btnLogin);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnTheme = new javax.swing.JButton();
        btnDatabaseSettings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ورود به سیستم");
        setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        setName("LoginFrame"); // NOI18N
        setResizable(false);

        pnlLogin.setToolTipText("");
        pnlLogin.setName("pnlLogin"); // NOI18N

        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("نام کاربری");
        lblUsername.setName("lblUsername"); // NOI18N

        txtUsername.setColumns(20);
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setText("admin");
        txtUsername.setName("txtUsername"); // NOI18N

        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setText("رمز عبور");
        lblPassword.setName("lblPassword"); // NOI18N

        txtPassword.setColumns(20);
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setText("admin");
        txtPassword.setName("txtPassword"); // NOI18N

        btnLogin.setText("ورود");
        btnLogin.setName(""); // NOI18N
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        btnTheme.setText("تم روشن");
        btnTheme.setToolTipText("تغییر تم");
        btnTheme.setName(""); // NOI18N
        btnTheme.addActionListener(this::btnThemeActionPerformed);

        btnDatabaseSettings.setText("تنظیمات دیتابیس");
        btnDatabaseSettings.addActionListener(this::btnDatabaseSettingsActionPerformed);

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsername)
                    .addComponent(lblPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btnDatabaseSettings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTheme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnTheme)
                    .addComponent(btnDatabaseSettings))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(377, 168));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemeActionPerformed

        ThemeManager.toggleTheme();
        btnTheme.setText(ThemeManager.isDark() ? "تم روشن" : "تم تاریک");
    }//GEN-LAST:event_btnThemeActionPerformed

    private void btnDatabaseSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatabaseSettingsActionPerformed

        DatabaseSettingsFrame frame = new DatabaseSettingsFrame();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

    }//GEN-LAST:event_btnDatabaseSettingsActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام کاربری و رمز عبور را وارد کنید.",
                    "ورود",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            com.programsfuture.usermanagement.dao.UserDAO userDAO
                    = new com.programsfuture.usermanagement.dao.UserDAO();

            com.programsfuture.usermanagement.model.User user
                    = userDAO.findByUsername(username);

            if (user == null
                    || !com.programsfuture.usermanagement.security.PasswordHasher.verify(
                            password,
                            user.getPasswordHash())) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "نام کاربری یا رمز عبور اشتباه است.",
                        "ورود",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!user.isActive()) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "این کاربر غیرفعال است.",
                        "ورود",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            com.programsfuture.usermanagement.CurrentUser.setUserId(user.getId());
            com.programsfuture.usermanagement.security.PermissionManager.load();

            MainFrame mainFrame = new MainFrame();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

            this.dispose();

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDatabaseSettings;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnTheme;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
