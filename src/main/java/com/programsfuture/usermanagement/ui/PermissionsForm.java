package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.CurrentUser;
import com.programsfuture.usermanagement.dao.GroupDAO;
import com.programsfuture.usermanagement.model.CheckBoxTreeNode;
import com.programsfuture.usermanagement.model.PermissionTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.Map;
import com.programsfuture.usermanagement.dao.UserDAO;
import com.programsfuture.usermanagement.model.User;
import java.util.ArrayList;
import com.programsfuture.usermanagement.model.FormTreeNode;
import com.programsfuture.usermanagement.dao.UserCustomPermissionsDAO;
import com.programsfuture.usermanagement.dao.FormDAO;
import com.programsfuture.usermanagement.dao.PermissionDAO;

/**
 *
 * @author Admin
 */
public class PermissionsForm extends javax.swing.JInternalFrame {

    private final UserDAO userDAO = new UserDAO();
    private final List<User> comboUsers = new ArrayList<>();
    private boolean updatingUserCombo = false;

    private final GroupDAO groupDAO = new GroupDAO();
    private final UserCustomPermissionsDAO userCustomPermissionsDAO
            = new UserCustomPermissionsDAO();

    private boolean hasPermission(int permissionId) {

        if (!CurrentUser.isLoggedIn()) {
            return false;
        }

        return com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(3, permissionId);
    }

    public PermissionsForm() {
        initComponents();

        boolean canInsert = hasPermission(2);
        boolean canDelete = hasPermission(4);

        btnSave.setVisible(canInsert);
        menuSave.setVisible(canInsert);
        btnDelete.setVisible(canDelete);
        menuDelete.setVisible(canDelete);

        loadUsers();
        loadForms();
        configurePermissionTreeRules();
        setupUserComboFilter();

        ComboUser.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

    }

    private void setupUserComboFilter() {
        javax.swing.JTextField editor
                = (javax.swing.JTextField) ComboUser.getEditor().getEditorComponent();

        editor.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

                if (updatingUserCombo) {
                    return;
                }

                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    return;
                }

                String text = editor.getText();

                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (!updatingUserCombo) {
                        filterUsers(text);
                    }
                });
            }
        });
    }

    private void filterUsers(String text) {
        if (updatingUserCombo) {
            return;
        }

        String searchText = text == null ? "" : text.trim();

        try {
            updatingUserCombo = true;

            List<User> users;

            if (searchText.isEmpty()) {
                users = userDAO.find("", 0, 20);
            } else {
                int totalUsers = userDAO.count(searchText);

                if (totalUsers <= 0) {
                    comboUsers.clear();
                    ComboUser.removeAllItems();
                    ComboUser.getEditor().setItem(text);
                    return;
                }

                users = userDAO.find(searchText, 0, totalUsers);
            }

            comboUsers.clear();
            ComboUser.removeAllItems();
            ComboUser.getEditor().setItem(text);

            for (User user : users) {
                comboUsers.add(user);

                ComboUser.addItem(
                        user.getFirstName()
                        + " "
                        + user.getLastName()
                        + " (" + user.getUsername() + ")"
                );
            }

            ComboUser.getEditor().setItem(text);

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            updatingUserCombo = false;
        }
    }

    private void applyViewPermissionSelection() {

        DefaultTreeModel model
                = (DefaultTreeModel) TreecheckBoxPermission.getModel();

        Object rootObject = model.getRoot();

        if (!(rootObject instanceof CheckBoxTreeNode root)) {
            return;
        }

        for (int i = 0; i < root.getChildCount(); i++) {

            Object formObject = root.getChildAt(i);

            if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                continue;
            }

            CheckBoxTreeNode viewNode = null;
            boolean hasOtherPermission = false;

            for (int j = 0; j < formNode.getChildCount(); j++) {

                Object permissionObject = formNode.getChildAt(j);

                if (!(permissionObject instanceof CheckBoxTreeNode permissionNode)) {
                    continue;
                }

                Object userObject = permissionNode.getUserObject();

                if (!(userObject instanceof PermissionTreeNode permissionTreeNode)) {
                    continue;
                }

                int permissionId = permissionTreeNode.getId();

                if (permissionId == 1) {
                    viewNode = permissionNode;
                } else if (permissionNode.isSelected()) {
                    hasOtherPermission = true;
                }
            }

            if (hasOtherPermission && viewNode != null) {
                viewNode.setSelected(true);
                formNode.setSelected(true);
            }
        }

        TreecheckBoxPermission.repaint();
    }

    private void configurePermissionTreeRules() {

        TreecheckBoxPermission.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(
                    java.awt.event.MouseEvent e) {

                javax.swing.SwingUtilities.invokeLater(() -> {

                    applyViewPermissionSelection();

                });
            }
        });
    }

    private void loadForms() {
        try {
            CheckBoxTreeNode root
                    = new CheckBoxTreeNode("فرم‌ها");

            FormDAO formDAO = new FormDAO();
            PermissionDAO permissionDAO = new PermissionDAO();

            List<Map<String, Object>> forms
                    = formDAO.findAll();

            List<Map<String, Object>> permissions
                    = permissionDAO.findAll();

            for (Map<String, Object> form : forms) {

                int formId
                        = ((Number) form.get("Id")).intValue();

                String displayName
                        = String.valueOf(form.get("DisplayName"));

                CheckBoxTreeNode formNode
                        = new CheckBoxTreeNode(
                                new FormTreeNode(formId, displayName)
                        );

                for (Map<String, Object> permission : permissions) {

                    int permissionId
                            = ((Number) permission.get("Id")).intValue();

                    String permissionName
                            = String.valueOf(
                                    permission.get("DisplayName")
                            );

                    CheckBoxTreeNode permissionNode
                            = new CheckBoxTreeNode(
                                    new PermissionTreeNode(
                                            permissionId,
                                            permissionName
                                    )
                            );

                    formNode.add(permissionNode);
                }

                root.add(formNode);
            }

            TreecheckBoxPermission.setModel(
                    new DefaultTreeModel(root)
            );

            for (int i = 0;
                    i < TreecheckBoxPermission.getRowCount();
                    i++) {

                TreecheckBoxPermission.expandRow(i);
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

    private void loadUsers() {
        try {
            comboUsers.clear();
            ComboUser.removeAllItems();

            int totalUsers = userDAO.count("");

            if (totalUsers == 0) {
                return;
            }

            List<User> users = userDAO.find("", 0, totalUsers);

            for (User user : users) {
                comboUsers.add(user);

                ComboUser.addItem(
                        user.getFirstName()
                        + " "
                        + user.getLastName()
                        + " (" + user.getUsername() + ")"
                );
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ComboUser = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        TreecheckBoxPermission = new com.programsfuture.usermanagement.model.CheckBoxTree();
        pnlSave = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuSave = new javax.swing.JMenuItem();
        menuDelete = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("تعیین دسترسی کاربران");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("کاربر");

        ComboUser.setEditable(true);
        ComboUser.addActionListener(this::ComboUserActionPerformed);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        TreecheckBoxPermission.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane3.setViewportView(TreecheckBoxPermission);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboUser, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ComboUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSave.setText("ثبت");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnClear.setText("پاک کردن");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnDelete.setText("حذف");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        javax.swing.GroupLayout pnlSaveLayout = new javax.swing.GroupLayout(pnlSave);
        pnlSave.setLayout(pnlSaveLayout);
        pnlSaveLayout.setHorizontalGroup(
            pnlSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSaveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSaveLayout.setVerticalGroup(
            pnlSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSaveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClear)
                    .addComponent(btnDelete))
                .addGap(28, 28, 28))
        );

        jMenu2.setText("Edit");

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSave.setText("ثبت");
        menuSave.addActionListener(this::menuSaveActionPerformed);
        jMenu2.add(menuSave);

        menuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuDelete.setText("حذف");
        menuDelete.addActionListener(this::menuDeleteActionPerformed);
        jMenu2.add(menuDelete);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBounds(0, 0, 808, 546);
    }// </editor-fold>//GEN-END:initComponents

    private User getSelectedUser() {

        int index = ComboUser.getSelectedIndex();

        if (index < 0 || index >= comboUsers.size()) {
            return null;
        }

        User user = comboUsers.get(index);

        String selectedText = String.valueOf(
                ComboUser.getSelectedItem()
        );

        String expectedText
                = user.getFirstName()
                + " "
                + user.getLastName()
                + " (" + user.getUsername() + ")";

        if (!selectedText.equals(expectedText)) {
            return null;
        }

        return user;
    }


    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (!hasPermission(2)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "شما مجوز ثبت دسترسی‌ها را ندارید.",
                    "عدم دسترسی",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        User selectedUser = getSelectedUser();

        if (selectedUser == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک کاربر را از فهرست انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int userId = selectedUser.getId();

        try {

            applyViewPermissionSelection();

            List<int[]> selectedPermissionIds
                    = new ArrayList<>();

            javax.swing.tree.DefaultMutableTreeNode root
                    = (javax.swing.tree.DefaultMutableTreeNode) TreecheckBoxPermission
                            .getModel()
                            .getRoot();

            java.util.Enumeration<?> forms
                    = root.children();

            int savedCount = 0;

            while (forms.hasMoreElements()) {

                Object formObject = forms.nextElement();

                if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                    continue;
                }

                Object formUserObject
                        = formNode.getUserObject();

                if (!(formUserObject instanceof FormTreeNode form)) {
                    continue;
                }

                java.util.Enumeration<?> permissions
                        = formNode.children();

                while (permissions.hasMoreElements()) {

                    Object permissionObject
                            = permissions.nextElement();

                    if (!(permissionObject instanceof CheckBoxTreeNode permissionNode)) {
                        continue;
                    }

                    if (!permissionNode.isSelected()) {
                        continue;
                    }

                    Object permissionUserObject
                            = permissionNode.getUserObject();

                    if (!(permissionUserObject instanceof PermissionTreeNode permission)) {
                        continue;
                    }

                    selectedPermissionIds.add(
                            new int[]{
                                form.getId(),
                                permission.getId()
                            }
                    );

                    savedCount++;
                }
            }

            userCustomPermissionsDAO.replacePermissions(
                    userId,
                    selectedPermissionIds
            );
            if (userId == CurrentUser.getUserId()) {
                com.programsfuture.usermanagement.security.PermissionManager.load();
            }

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "دسترسی‌های کاربر با موفقیت ذخیره شد.\n"
                    + "تعداد دسترسی‌ها: " + savedCount,
                    "ثبت",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
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

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        try {
            DefaultTreeModel model
                    = (DefaultTreeModel) TreecheckBoxPermission.getModel();

            Object rootObject = model.getRoot();

            if (!(rootObject instanceof CheckBoxTreeNode root)) {
                return;
            }

            for (int i = 0; i < root.getChildCount(); i++) {

                Object formObject = root.getChildAt(i);

                if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                    continue;
                }

                formNode.setSelected(false);

                for (int j = 0; j < formNode.getChildCount(); j++) {

                    Object permissionObject = formNode.getChildAt(j);

                    if (permissionObject instanceof CheckBoxTreeNode permissionNode) {
                        permissionNode.setSelected(false);
                    }
                }
            }

            updatingUserCombo = true;
            ComboUser.setSelectedIndex(-1);
            ComboUser.getEditor().setItem("");
            updatingUserCombo = false;

            TreecheckBoxPermission.repaint();

        } finally {
            updatingUserCombo = false;
        }

    }//GEN-LAST:event_btnClearActionPerformed

    private void ComboUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboUserActionPerformed

        if (updatingUserCombo) {
            return;
        }

        if (ComboUser.getSelectedIndex() < 0
                || ComboUser.getSelectedIndex() >= comboUsers.size()) {
            return;
        }

        User selectedUser
                = comboUsers.get(ComboUser.getSelectedIndex());

        loadUserPermissions(selectedUser.getId());
    }//GEN-LAST:event_ComboUserActionPerformed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed

        btnSaveActionPerformed(null);
    }//GEN-LAST:event_menuSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (!hasPermission(4)) {
            return;
        }
        User selectedUser = getSelectedUser();

        if (selectedUser == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک کاربر را از فهرست انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int userId = selectedUser.getId();

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "آیا از حذف دسترسی‌های اختصاصی این کاربر اطمینان دارید؟\n"
                + "پس از حذف، دسترسی‌های کاربر از Groupهای او محاسبه خواهد شد.",
                "تأیید حذف",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (result != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {

            userCustomPermissionsDAO
                    .deleteCustomPermissionsAndConfiguration(userId);
            if (userId == CurrentUser.getUserId()) {
                com.programsfuture.usermanagement.security.PermissionManager.load();
            }

            loadUserPermissions(userId);

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "دسترسی‌های اختصاصی کاربر با موفقیت حذف شد.",
                    "حذف",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
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
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void menuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteActionPerformed

        btnDeleteActionPerformed(null);

    }//GEN-LAST:event_menuDeleteActionPerformed

    private void loadUserPermissions(int userId) {

        try {

            List<Map<String, Object>> rows;

            if (userCustomPermissionsDAO
                    .hasCustomPermissionConfiguration(userId)) {

                rows = userCustomPermissionsDAO
                        .findPermissionIdsByUserId(userId);

            } else {

                rows = groupDAO
                        .findEffectivePermissionsByUserId(userId);
            }

            java.util.Set<String> selectedPermissions
                    = new java.util.HashSet<>();

            for (Map<String, Object> row : rows) {

                int formId
                        = ((Number) row.get("FormId")).intValue();

                int permissionId
                        = ((Number) row.get("PermissionId")).intValue();

                selectedPermissions.add(
                        formId + ":" + permissionId
                );
            }

            javax.swing.tree.DefaultMutableTreeNode root
                    = (javax.swing.tree.DefaultMutableTreeNode) TreecheckBoxPermission
                            .getModel()
                            .getRoot();

            java.util.Enumeration<?> forms
                    = root.children();

            while (forms.hasMoreElements()) {

                Object formObject = forms.nextElement();

                if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                    continue;
                }

                Object formUserObject
                        = formNode.getUserObject();

                if (!(formUserObject instanceof FormTreeNode form)) {
                    continue;
                }

                boolean formSelected = false;

                java.util.Enumeration<?> permissions
                        = formNode.children();

                while (permissions.hasMoreElements()) {

                    Object permissionObject
                            = permissions.nextElement();

                    if (!(permissionObject instanceof CheckBoxTreeNode permissionNode)) {
                        continue;
                    }

                    Object permissionUserObject
                            = permissionNode.getUserObject();

                    if (!(permissionUserObject instanceof PermissionTreeNode permission)) {
                        continue;
                    }

                    boolean selected
                            = selectedPermissions.contains(
                                    form.getId()
                                    + ":"
                                    + permission.getId()
                            );

                    permissionNode.setSelected(selected);

                    if (selected) {
                        formSelected = true;
                    }
                }

                formNode.setSelected(formSelected);
            }

            applyViewPermissionSelection();

            TreecheckBoxPermission.repaint();

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboUser;
    private com.programsfuture.usermanagement.model.CheckBoxTree TreecheckBoxPermission;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JPanel pnlSave;
    // End of variables declaration//GEN-END:variables
}
