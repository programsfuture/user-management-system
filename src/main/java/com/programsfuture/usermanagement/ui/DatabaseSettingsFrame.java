package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.DatabaseConfig;
import com.programsfuture.usermanagement.DatabaseConfigService;
import javax.swing.JOptionPane;
import com.programsfuture.usermanagement.DatabaseConnectionService;

public class DatabaseSettingsFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DatabaseSettingsFrame.class.getName());

    public DatabaseSettingsFrame() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        try {
            DatabaseConfig config = DatabaseConfigService.load();

            if (config != null) {
//                txtServer.setText(config.getServer());
//                txtPort.setText(String.valueOf(config.getPort()));
//                txtDatabase.setText(config.getDatabase());
//                txtUsername.setText(config.getUsername());
//                txtPassword.setText(config.getPassword());
//                System.out.println(config.getPassword());

            }

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServer = new javax.swing.JLabel();
        txtServer = new javax.swing.JTextField();
        lblPort = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblDatabase = new javax.swing.JLabel();
        txtDatabase = new javax.swing.JTextField();
        btnTestConnection = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("تنظیمات اتصال به پایگاه داده");
        setName("DatabaseSettingsFrame"); // NOI18N
        setResizable(false);

        lblServer.setText("آدرس سرور");

        txtServer.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblPort.setText("پورت سرور");

        txtPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblUsername.setText("نام کاربری");

        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblPassword.setText("کلمه عبور");

        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblDatabase.setText("نام دیتابیس");

        btnTestConnection.setText("تست اتصال");
        btnTestConnection.addActionListener(this::btnTestConnectionActionPerformed);

        btnSave.setText("ذخیره");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnCancel.setText("انصراف");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPort))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblServer))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDatabase, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(txtPassword)
                            .addComponent(txtUsername))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDatabase, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnTestConnection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServer)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPort)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatabase)
                    .addComponent(txtDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTestConnection)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(419, 258));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        try {
            String server = txtServer.getText().trim();
            String database = txtDatabase.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (server.isEmpty()
                    || database.isEmpty()
                    || username.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "تمام اطلاعات اتصال به پایگاه داده را وارد کنید.",
                        "هشدار",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int port = Integer.parseInt(txtPort.getText().trim());
            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(
                        this,
                        "پورت سرور باید بین 1 و 65535 باشد.",
                        "خطا",
                        JOptionPane.WARNING_MESSAGE
                );
                txtPort.requestFocus();
                return;
            }

            DatabaseConfig config = new DatabaseConfig(
                    server,
                    port,
                    database,
                    username,
                    password,
                    true
            );
            DatabaseConfigService.save(config);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "پورت سرور باید یک عدد معتبر باشد.",
                    "خطا",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnTestConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestConnectionActionPerformed
        try {
            String server = txtServer.getText().trim();
            String database = txtDatabase.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String portText = txtPort.getText().trim();

            if (server.isEmpty()
                    || database.isEmpty()
                    || username.isEmpty()
                    || password.isEmpty()
                    || portText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "تمام اطلاعات اتصال به پایگاه داده را وارد کنید.",
                        "هشدار",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int port = Integer.parseInt(portText);

            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(
                        this,
                        "پورت سرور باید بین 1 و 65535 باشد.",
                        "خطا",
                        JOptionPane.WARNING_MESSAGE
                );
                txtPort.requestFocus();
                return;
            }

            DatabaseConfig config = new DatabaseConfig(
                        server,
                        port,
                        database,
                        username,
                        password,
                        true
                );

            DatabaseConnectionService.testConnection(config);

            JOptionPane.showMessageDialog(
                    this,
                    "اتصال به دیتابیس با موفقیت برقرار شد.",
                    "تست اتصال",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "پورت سرور باید یک عدد معتبر باشد.",
                    "خطا",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnTestConnectionActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new DatabaseSettingsFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTestConnection;
    private javax.swing.JLabel lblDatabase;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
