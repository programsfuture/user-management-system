package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.CurrentUser;
import java.awt.Color;
import java.awt.Font;

public class MainFrame extends javax.swing.JFrame {

    private PositionsForm positionsForm;
    private UserManagementForm userManagementForm;
    private PermissionsForm permissionsForm;
    private GroupsForm groupsForm;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    public MainFrame() {
        initComponents();
        applyPermissions();

        //راست چین کردن پنل سمت راست
        taskPaneContainer.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        taskPaneUserManagement.setOpaque(true);
        taskPaneUserManagement.getContentPane().setBackground(
                javax.swing.UIManager.getColor("Panel.background")
        );

        //تعیین محل آیکن لیبل های منو بغل
        lblPositions.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblPositions.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUserManagement.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblUserManagement.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPermissions.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblPermissions.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        lblGroups.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblGroups.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);

    }

    private void applyPermissions() {
        lblPositions.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager.hasPermission(1, 1)
        );

        lblUserManagement.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager.hasPermission(2, 1)
        );

        lblPermissions.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager.hasPermission(3, 1)
        );

        lblGroups.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager.hasPermission(4, 1)
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        taskPaneContainer = new org.jdesktop.swingx.JXTaskPaneContainer();
        taskPaneUserManagement = new org.jdesktop.swingx.JXTaskPane();
        lblPositions = new javax.swing.JLabel();
        lblGroups = new javax.swing.JLabel();
        lblUserManagement = new javax.swing.JLabel();
        lblPermissions = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        menuLogout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 888, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        taskPaneContainer.setName("taskPaneContainer"); // NOI18N

        taskPaneUserManagement.setTitle("مدیریت کاربران");
        taskPaneUserManagement.setName("taskPaneUserManagement"); // NOI18N

        lblPositions.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPositions.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPositions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Positions.png"))); // NOI18N
        lblPositions.setText("سمت‌ها");
        lblPositions.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblPositionsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblPositionsFocusLost(evt);
            }
        });
        lblPositions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPositionsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPositionsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPositionsMouseExited(evt);
            }
        });
        taskPaneUserManagement.getContentPane().add(lblPositions);

        lblGroups.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGroups.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Groups.png"))); // NOI18N
        lblGroups.setText("گروه بندی");
        lblGroups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGroupsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGroupsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGroupsMouseExited(evt);
            }
        });
        taskPaneUserManagement.getContentPane().add(lblGroups);

        lblUserManagement.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUserManagement.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/UserManagement.png"))); // NOI18N
        lblUserManagement.setText("مدیریت کاربران");
        lblUserManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUserManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUserManagementMouseExited(evt);
            }
        });
        taskPaneUserManagement.getContentPane().add(lblUserManagement);

        lblPermissions.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPermissions.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPermissions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/permission.png"))); // NOI18N
        lblPermissions.setText("دسترسی ها");
        lblPermissions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPermissionsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPermissionsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPermissionsMouseExited(evt);
            }
        });
        taskPaneUserManagement.getContentPane().add(lblPermissions);

        javax.swing.GroupLayout taskPaneContainerLayout = new javax.swing.GroupLayout(taskPaneContainer);
        taskPaneContainer.setLayout(taskPaneContainerLayout);
        taskPaneContainerLayout.setHorizontalGroup(
            taskPaneContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(taskPaneUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        taskPaneContainerLayout.setVerticalGroup(
            taskPaneContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taskPaneContainerLayout.createSequentialGroup()
                .addComponent(taskPaneUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(601, 601, 601))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        menuLogout.setText("خروج از حساب");
        menuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLogoutMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuLogout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taskPaneContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
            .addComponent(taskPaneContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1065, 727));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblUserManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserManagementMouseEntered
        // تغییر وضعیت لیبل بعد از ورود موس
        lblUserManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUserManagement.setForeground(new java.awt.Color(178, 34, 34));
        lblUserManagement.setFont(lblUserManagement.getFont().deriveFont(Font.BOLD));
        lblUserManagement.setOpaque(true);
        lblUserManagement.setBackground(new Color(0, 120, 215));

    }//GEN-LAST:event_lblUserManagementMouseEntered

    private void lblUserManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserManagementMouseExited
        // تغییر وضعیت لیبل بعد از خروج موس
        lblUserManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblUserManagement.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
        lblUserManagement.setFont(lblUserManagement.getFont().deriveFont(Font.PLAIN));
        lblUserManagement.setOpaque(false);
        lblUserManagement.setBackground(null);
    }//GEN-LAST:event_lblUserManagementMouseExited

    private void lblPositionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPositionsMouseEntered
        // تغییر وضعیت لیبل بعد از ورود موس
        lblPositions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPositions.setForeground(new java.awt.Color(178, 34, 34));
        lblPositions.setFont(lblPositions.getFont().deriveFont(Font.BOLD));
        lblPositions.setOpaque(true);
        lblPositions.setBackground(new Color(0, 120, 215));
    }//GEN-LAST:event_lblPositionsMouseEntered

    private void lblPositionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPositionsMouseExited
        // تغییر وضعیت لیبل بعد از خروج موس
        lblPositions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblPositions.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
        lblPositions.setFont(lblPositions.getFont().deriveFont(Font.PLAIN));
        lblPositions.setOpaque(false);
        lblPositions.setBackground(null);
    }//GEN-LAST:event_lblPositionsMouseExited

    private void lblPermissionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPermissionsMouseEntered
        // تغییر وضعیت لیبل بعد از ورود موس
        lblPermissions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPermissions.setForeground(new java.awt.Color(178, 34, 34));
        lblPermissions.setFont(lblPermissions.getFont().deriveFont(Font.BOLD));
        lblPermissions.setOpaque(true);
        lblPermissions.setBackground(new Color(0, 120, 215));

    }//GEN-LAST:event_lblPermissionsMouseEntered

    private void lblPermissionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPermissionsMouseExited
        // تغییر وضعیت لیبل بعد از خروج موس
        lblPermissions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblPermissions.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
        lblPermissions.setFont(lblPermissions.getFont().deriveFont(Font.PLAIN));
        lblPermissions.setOpaque(false);
        lblPermissions.setBackground(null);
    }//GEN-LAST:event_lblPermissionsMouseExited

    private void lblPositionsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblPositionsFocusGained

        lblPositions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPositions.setForeground(new java.awt.Color(178, 34, 34));
        lblPositions.setFont(lblPositions.getFont().deriveFont(Font.BOLD));
        lblPositions.setOpaque(true);
        lblPositions.setBackground(new Color(0, 120, 215));
    }//GEN-LAST:event_lblPositionsFocusGained

    private void lblPositionsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblPositionsFocusLost

        lblPositions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblPositions.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
        lblPositions.setFont(lblPositions.getFont().deriveFont(Font.PLAIN));
        lblPositions.setOpaque(false);
        lblPositions.setBackground(null);
    }//GEN-LAST:event_lblPositionsFocusLost

    private void lblPositionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPositionsMouseClicked

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(1, 1)) {
            return;
        }

        if (positionsForm == null || positionsForm.isClosed()) {
            positionsForm = new PositionsForm();
            jDesktopPane1.add(positionsForm);
        }

        try {
            positionsForm.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            // نادیده گرفتن
        }

        positionsForm.setVisible(true);
        positionsForm.toFront();
    }//GEN-LAST:event_lblPositionsMouseClicked

    private void lblUserManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserManagementMouseClicked

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(2, 1)) {
            return;
        }
        if (userManagementForm == null || userManagementForm.isClosed()) {
            userManagementForm = new UserManagementForm();
            jDesktopPane1.add(userManagementForm);
        }

        try {
            userManagementForm.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            // نادیده گرفتن
        }

        userManagementForm.setVisible(true);
        userManagementForm.toFront();
    }//GEN-LAST:event_lblUserManagementMouseClicked

    private void menuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLogoutMouseClicked

        com.programsfuture.usermanagement.security.PermissionManager.clear();
        CurrentUser.clear();

        new LoginFrame().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_menuLogoutMouseClicked

    private void lblPermissionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPermissionsMouseClicked

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(3, 1)) {
            return;
        }
        if (permissionsForm == null || permissionsForm.isClosed()) {
            permissionsForm = new PermissionsForm();
            jDesktopPane1.add(permissionsForm);
        }

        try {
            permissionsForm.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            // نادیده گرفتن
        }

        permissionsForm.setVisible(true);
        permissionsForm.toFront();
    }//GEN-LAST:event_lblPermissionsMouseClicked

    private void lblGroupsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGroupsMouseEntered

        lblGroups.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGroups.setForeground(new java.awt.Color(178, 34, 34));
        lblGroups.setFont(lblGroups.getFont().deriveFont(Font.BOLD));
        lblGroups.setOpaque(true);
        lblGroups.setBackground(new Color(0, 120, 215));
    }//GEN-LAST:event_lblGroupsMouseEntered

    private void lblGroupsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGroupsMouseExited

        // تغییر وضعیت لیبل بعد از خروج موس
        lblGroups.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblGroups.setForeground(javax.swing.UIManager.getColor("Label.foreground"));
        lblGroups.setFont(lblGroups.getFont().deriveFont(Font.PLAIN));
        lblGroups.setOpaque(false);
        lblGroups.setBackground(null);
    }//GEN-LAST:event_lblGroupsMouseExited

    private void lblGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGroupsMouseClicked

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(4, 1)) {
            return;
        }

        if (groupsForm == null || groupsForm.isClosed()) {
            groupsForm = new GroupsForm();
            jDesktopPane1.add(groupsForm);
        }

        try {
            groupsForm.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            // نادیده گرفتن
        }

        groupsForm.setVisible(true);
        groupsForm.toFront();


    }//GEN-LAST:event_lblGroupsMouseClicked

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

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblGroups;
    private javax.swing.JLabel lblPermissions;
    private javax.swing.JLabel lblPositions;
    private javax.swing.JLabel lblUserManagement;
    private javax.swing.JMenu menuLogout;
    private org.jdesktop.swingx.JXTaskPaneContainer taskPaneContainer;
    private org.jdesktop.swingx.JXTaskPane taskPaneUserManagement;
    // End of variables declaration//GEN-END:variables
}
