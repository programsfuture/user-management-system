package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.dao.PositionDAO;
import com.programsfuture.usermanagement.dao.UserDAO;
import com.programsfuture.usermanagement.model.User;

import com.programsfuture.usermanagement.CurrentUser;
import com.programsfuture.usermanagement.model.UserPositionHistory;
import datePicker.classes.Roozh;
import com.programsfuture.usermanagement.dao.GroupDAO;

public class UserManagementForm extends javax.swing.JInternalFrame {

    private final UserDAO userDAO = new UserDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    private final java.util.Map<String, Integer> positionIds = new java.util.HashMap<>();

    private int selectedUserId = -1;
    private int currentPage = 1;
    private int totalPages = 0;
    private int totalRecords = 0;
    private final com.programsfuture.usermanagement.dao.UserFormSettingsDAO userFormSettingsDAO
            = new com.programsfuture.usermanagement.dao.UserFormSettingsDAO();
    private boolean initializing = true;

    private final GroupDAO groupDAO = new GroupDAO();

    private void loadGroups() {

        try {

            javax.swing.tree.DefaultMutableTreeNode root
                    = new javax.swing.tree.DefaultMutableTreeNode("گروه‌ها");

            java.util.List<java.util.Map<String, Object>> groups
                    = groupDAO.findAll();

            for (java.util.Map<String, Object> row : groups) {

                int groupId = ((Number) row.get("Id")).intValue();
                String groupName = (String) row.get("Name");

                com.programsfuture.usermanagement.model.CheckBoxTreeNode groupNode
                        = new com.programsfuture.usermanagement.model.CheckBoxTreeNode(
                                new com.programsfuture.usermanagement.model.GroupTreeNode(
                                        groupId,
                                        groupName
                                )
                        );

                root.add(groupNode);
            }

            javax.swing.tree.DefaultTreeModel model
                    = new javax.swing.tree.DefaultTreeModel(root);

            checkBoxTreeGroups.setModel(model);

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

    private void loadUserGroups(int userId) {

        try {

            java.util.List<java.util.Map<String, Object>> userGroups
                    = groupDAO.findByUserId(userId);

            javax.swing.tree.TreeModel model
                    = checkBoxTreeGroups.getModel();

            javax.swing.tree.DefaultMutableTreeNode root
                    = (javax.swing.tree.DefaultMutableTreeNode) model.getRoot();

            java.util.Set<Integer> selectedGroupIds
                    = new java.util.HashSet<>();

            for (java.util.Map<String, Object> row : userGroups) {

                int groupId = ((Number) row.get("Id")).intValue();
                selectedGroupIds.add(groupId);

            }

            java.util.Enumeration<?> children = root.children();

            while (children.hasMoreElements()) {

                Object childObject = children.nextElement();

                if (!(childObject instanceof com.programsfuture.usermanagement.model.CheckBoxTreeNode groupNode)) {
                    continue;
                }

                Object userObject = groupNode.getUserObject();

                if (!(userObject instanceof com.programsfuture.usermanagement.model.GroupTreeNode group)) {
                    continue;
                }

                groupNode.setSelected(
                        selectedGroupIds.contains(group.getId())
                );
            }

            checkBoxTreeGroups.repaint();

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

    private void saveUserGroups(
            java.sql.Connection connection,
            int userId
    ) throws Exception {

        groupDAO.deleteUserGroupsByUserId(
                connection,
                userId
        );

        javax.swing.tree.TreeModel model
                = checkBoxTreeGroups.getModel();

        javax.swing.tree.DefaultMutableTreeNode root
                = (javax.swing.tree.DefaultMutableTreeNode) model.getRoot();

        java.util.Enumeration<?> children
                = root.children();

        while (children.hasMoreElements()) {

            Object childObject = children.nextElement();

            if (!(childObject instanceof com.programsfuture.usermanagement.model.CheckBoxTreeNode groupNode)) {
                continue;
            }

            if (!groupNode.isSelected()) {
                continue;
            }

            Object userObject = groupNode.getUserObject();

            if (!(userObject instanceof com.programsfuture.usermanagement.model.GroupTreeNode group)) {
                continue;
            }

            groupDAO.addUserToGroup(
                    connection,
                    userId,
                    group.getId()
            );
        }
    }

    private boolean hasPermission(int permissionId) {

        if (!CurrentUser.isLoggedIn()) {
            return false;
        }

        return com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(2, permissionId);
    }

    private void applyPermissions() {
        btnSave.setVisible(hasPermission(2));
        btnUpdate.setVisible(hasPermission(3));
        btnDelete.setVisible(hasPermission(4));

        menuSave.setVisible(hasPermission(2));
        menuUpdate.setVisible(hasPermission(3));
        menuDelete.setVisible(hasPermission(4));
    }

    public UserManagementForm() {
        initComponents();
        applyPermissions();

        chkIsActive.setSelected(true);

        try {
            spinnerPageSize.setValue(
                    userFormSettingsDAO.getPageSize(
                            CurrentUser.getUserId(),
                            2
                    )
            );
        } catch (Exception ex) {
            spinnerPageSize.setValue(10);
        }

        loadPositions();
        loadGroups();
        loadUsers();

        tblUsers.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

        javax.swing.table.DefaultTableCellRenderer rightRenderer
                = new javax.swing.table.DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.RIGHT
        );

        for (int i = 0; i < tblUsers.getColumnCount(); i++) {
            tblUsers.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(rightRenderer);
        }

        txtFirstName.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );
        txtLastName.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );
        cmbPosition.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );
        txtSearch.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

        initializing = false;
    }

    private void loadPositions() {
        try {
            cmbPosition.removeAllItems();

            java.util.List<com.programsfuture.usermanagement.model.Position> positions
                    = positionDAO.find("", 0, 1000);

            for (com.programsfuture.usermanagement.model.Position position : positions) {
                cmbPosition.addItem(position.getPositionName());
                positionIds.put(position.getPositionName(), position.getPositionId());
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
            String searchText = txtSearch.getText().trim();
            int pageSize = (Integer) spinnerPageSize.getValue();
            int offset = (currentPage - 1) * pageSize;

            totalRecords = userDAO.count(searchText);

            totalPages = totalRecords == 0
                    ? 0
                    : (int) Math.ceil((double) totalRecords / pageSize);

            if (totalPages > 0 && currentPage > totalPages) {
                currentPage = totalPages;
            }

            java.util.List<User> users
                    = userDAO.find(searchText, offset, pageSize);

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) tblUsers.getModel();

            model.setRowCount(0);

            for (User user : users) {
                model.addRow(new Object[]{
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getPositionName(),
                    user.isActive() ? "فعال" : "غیرفعال"
                });
            }

            lblTotalRecords.setText("تعداد رکوردها: " + totalRecords);
            lblTotalPages.setText("تعداد صفحات: " + totalPages);

            lblPageInfo.setText(
                    totalPages == 0
                            ? "صفحه 0"
                            : "صفحه " + currentPage + " از " + totalPages
            );

            btnFirst.setEnabled(currentPage > 1);
            btnPrevious.setEnabled(currentPage > 1);
            btnNext.setEnabled(totalPages > 0 && currentPage < totalPages);
            btnLast.setEnabled(totalPages > 0 && currentPage < totalPages);

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

        pnlPagination = new javax.swing.JPanel();
        LabelSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        lblTotalPages = new javax.swing.JLabel();
        lblTotalRecords = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        spinnerPageSize = new javax.swing.JSpinner();
        pnlUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        cmbPosition = new javax.swing.JComboBox<>();
        chkIsActive = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        checkBoxTreeGroups = new com.programsfuture.usermanagement.model.CheckBoxTree();
        pnlSave = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuSave = new javax.swing.JMenuItem();
        menuUpdate = new javax.swing.JMenuItem();
        menuDelete = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("مدیریت کاربران");

        pnlPagination.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LabelSearch.setText("جستجو");

        btnSearch.setText("جستجو");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        lblPageInfo.setText("صفحه 1");

        lblTotalPages.setText("تعداد صفحات: 0");

        lblTotalRecords.setText("تعداد رکوردها: 0");

        btnFirst.setText("اول");
        btnFirst.addActionListener(this::btnFirstActionPerformed);

        btnPrevious.setText("قبلی");
        btnPrevious.addActionListener(this::btnPreviousActionPerformed);

        btnNext.setText("بعدی");
        btnNext.addActionListener(this::btnNextActionPerformed);

        btnLast.setText("آخر");
        btnLast.addActionListener(this::btnLastActionPerformed);

        spinnerPageSize.setModel(new javax.swing.SpinnerNumberModel(10, 1, 50, 1));
        spinnerPageSize.addChangeListener(this::spinnerPageSizeStateChanged);

        javax.swing.GroupLayout pnlPaginationLayout = new javax.swing.GroupLayout(pnlPagination);
        pnlPagination.setLayout(pnlPaginationLayout);
        pnlPaginationLayout.setHorizontalGroup(
            pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPaginationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlPaginationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalRecords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalPages)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPageInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPaginationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrevious)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFirst)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPaginationLayout.setVerticalGroup(
            pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(spinnerPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalRecords)
                        .addComponent(lblTotalPages))
                    .addGroup(pnlPaginationLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblPageInfo)))
                .addContainerGap())
        );

        pnlUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("نام");

        jLabel2.setText("نام خانوادگی");

        jLabel3.setText("نام کاربری");

        jLabel4.setText("کلمه عبور");

        jLabel5.setText("سمت");

        chkIsActive.setSelected(true);
        chkIsActive.setText("فعال");

        jLabel6.setText("گروها");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        checkBoxTreeGroups.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(checkBoxTreeGroups);

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtLastName)
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                    .addComponent(chkIsActive, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(130, 130, 130)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlUserLayout.createSequentialGroup()
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(pnlUserLayout.createSequentialGroup()
                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername)
                            .addComponent(cmbPosition, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUserLayout.setVerticalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkIsActive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSave.setText("ثبت");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnUpdate.setText("ویرایش");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("حذف");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setText("پاک کردن");
        btnClear.addActionListener(this::btnClearActionPerformed);

        javax.swing.GroupLayout pnlSaveLayout = new javax.swing.GroupLayout(pnlSave);
        pnlSave.setLayout(pnlSaveLayout);
        pnlSaveLayout.setHorizontalGroup(
            pnlSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSaveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
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
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addGap(28, 28, 28))
        );

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "شناسه", "نام", "نام خانوادگی", "نام کاربری", "سمت", "وضعیت"
            }
        ));
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        jMenu2.setText("Edit");

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSave.setText("ثبت");
        menuSave.addActionListener(this::menuSaveActionPerformed);
        jMenu2.add(menuSave);

        menuUpdate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuUpdate.setText("ویرایش");
        menuUpdate.addActionListener(this::menuUpdateActionPerformed);
        jMenu2.add(menuUpdate);

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
                    .addComponent(pnlSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPagination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        if (currentPage > 1) {
            currentPage = 1;
            loadUsers();
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed

        if (currentPage > 1) {
            currentPage--;
            loadUsers();
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        if (currentPage < totalPages) {
            currentPage++;
            loadUsers();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        if (currentPage < totalPages) {
            currentPage = totalPages;
            loadUsers();
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void spinnerPageSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerPageSizeStateChanged

        if (initializing) {
            return;
        }

        try {
            int pageSize = (Integer) spinnerPageSize.getValue();

            userFormSettingsDAO.savePageSize(
                    CurrentUser.getUserId(),
                    2,
                    pageSize
            );

            currentPage = 1;
            loadUsers();

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_spinnerPageSizeStateChanged

    private void clearUserForm() {

        txtFirstName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");

        chkIsActive.setSelected(true);

        selectedUserId = -1;

        if (cmbPosition.getItemCount() > 0) {
            cmbPosition.setSelectedIndex(0);
        }

        javax.swing.tree.TreeModel model
                = checkBoxTreeGroups.getModel();

        javax.swing.tree.DefaultMutableTreeNode root
                = (javax.swing.tree.DefaultMutableTreeNode) model.getRoot();

        java.util.Enumeration<?> children = root.children();

        while (children.hasMoreElements()) {

            Object childObject = children.nextElement();

            if (childObject instanceof com.programsfuture.usermanagement.model.CheckBoxTreeNode groupNode) {

                groupNode.setSelected(false);
            }
        }

        checkBoxTreeGroups.repaint();

        tblUsers.clearSelection();
    }


    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (!hasPermission(2)) {
            return;
        }

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String username = txtUsername.getText().trim();
        String passwordText = new String(txtPassword.getPassword());

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || username.isEmpty()
                || passwordText.isBlank()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام، نام خانوادگی، نام کاربری و رمز عبور الزامی است.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String password = com.programsfuture.usermanagement.security.PasswordHasher.hash(
                passwordText
        );

        String selectedPosition = (String) cmbPosition.getSelectedItem();

        Integer positionId = selectedPosition == null
                ? null
                : positionIds.get(selectedPosition);

        try {

            User user = new User(
                    0,
                    firstName,
                    lastName,
                    username,
                    password,
                    chkIsActive.isSelected(),
                    positionId
            );

            final int[] insertedUserId = {-1};

            com.programsfuture.usermanagement.data.DatabaseExecutor.executeTransaction(
                    connection -> {

                        try {

                            insertedUserId[0]
                            = userDAO.insertAndReturnId(
                                    connection,
                                    user
                            );

                            saveUserGroups(
                                    connection,
                                    insertedUserId[0]
                            );

                        } catch (Exception e) {

                            throw new RuntimeException(e);
                        }
                    }
            );

            loadUsers();
            clearUserForm();

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "کاربر با موفقیت ثبت شد.",
                    "ثبت",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(e),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        clearUserForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (!hasPermission(3)) {
            return;
        }

        if (selectedUserId == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک کاربر را از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String username = txtUsername.getText().trim();
        String passwordText = new String(txtPassword.getPassword());

        String password = passwordText.isBlank()
                ? null
                : com.programsfuture.usermanagement.security.PasswordHasher.hash(
                        passwordText
                );

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || username.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام، نام خانوادگی و نام کاربری الزامی است.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String selectedPosition = (String) cmbPosition.getSelectedItem();

        Integer positionId = selectedPosition == null
                ? null
                : positionIds.get(selectedPosition);

        try {

            User oldUser = userDAO.findById(selectedUserId);

            if (oldUser == null) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "کاربر موردنظر پیدا نشد.",
                        "هشدار",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            User user = new User(
                    selectedUserId,
                    firstName,
                    lastName,
                    username,
                    password,
                    chkIsActive.isSelected(),
                    positionId
            );

            UserPositionHistory history = null;

            if (!java.util.Objects.equals(
                    oldUser.getPositionId(),
                    positionId)) {

                if (!CurrentUser.isLoggedIn()) {
                    throw new Exception(
                            "کاربر جاری مشخص نیست؛ امکان ثبت تاریخچه تغییر سمت وجود ندارد."
                    );
                }

                history = new UserPositionHistory();

                history.setUserId(selectedUserId);
                history.setOldPositionId(oldUser.getPositionId());
                history.setNewPositionId(positionId);
                history.setChangedByUserId(CurrentUser.getUserId());

                Roozh roozh = new Roozh();
                java.util.Calendar now = java.util.Calendar.getInstance();

                roozh.GregorianToPersian(
                        now.get(java.util.Calendar.YEAR),
                        now.get(java.util.Calendar.MONTH) + 1,
                        now.get(java.util.Calendar.DAY_OF_MONTH)
                );

                history.setChangedDateShamsi(
                        String.format(
                                "%04d/%02d/%02d",
                                roozh.getYear(),
                                roozh.getMonth(),
                                roozh.getDay()
                        )
                );
            }

            final UserPositionHistory finalHistory = history;

            com.programsfuture.usermanagement.data.DatabaseExecutor.executeTransaction(
                    connection -> {

                        try {

                            userDAO.update(
                                    connection,
                                    user
                            );

                            if (finalHistory != null) {

                                String historySql = """
                            INSERT INTO UserPositionHistory
                                (UserId, OldPositionId, NewPositionId,
                                 ChangedByUserId, ChangedDateShamsi)
                            VALUES (?, ?, ?, ?, ?)
                            """;

                                try (java.sql.PreparedStatement statement
                                = connection.prepareStatement(historySql)) {

                                    statement.setObject(
                                            1,
                                            finalHistory.getUserId()
                                    );

                                    statement.setObject(
                                            2,
                                            finalHistory.getOldPositionId()
                                    );

                                    statement.setObject(
                                            3,
                                            finalHistory.getNewPositionId()
                                    );

                                    statement.setObject(
                                            4,
                                            finalHistory.getChangedByUserId()
                                    );

                                    statement.setObject(
                                            5,
                                            finalHistory.getChangedDateShamsi()
                                    );

                                    statement.executeUpdate();
                                }
                            }

                            saveUserGroups(
                                    connection,
                                    selectedUserId
                            );

                        } catch (Exception e) {

                            throw new RuntimeException(e);
                        }
                    }
            );

            loadUsers();
            clearUserForm();

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "کاربر با موفقیت ویرایش شد.",
                    "ویرایش",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(e),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked

        int selectedRow = tblUsers.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        selectedUserId = Integer.parseInt(
                tblUsers.getValueAt(selectedRow, 0).toString()
        );

        Object firstName = tblUsers.getValueAt(selectedRow, 1);
        Object lastName = tblUsers.getValueAt(selectedRow, 2);
        Object username = tblUsers.getValueAt(selectedRow, 3);
        Object status = tblUsers.getValueAt(selectedRow, 5);
        Object positionValue = tblUsers.getValueAt(selectedRow, 4);

        txtFirstName.setText(firstName == null ? "" : firstName.toString());
        txtLastName.setText(lastName == null ? "" : lastName.toString());
        txtUsername.setText(username == null ? "" : username.toString());

        chkIsActive.setSelected(
                status != null && "فعال".equals(status.toString())
        );

        if (positionValue != null) {
            String positionName = positionValue.toString();

            if (positionIds.containsKey(positionName)) {
                cmbPosition.setSelectedItem(positionName);
            } else if (cmbPosition.getItemCount() > 0) {
                cmbPosition.setSelectedIndex(0);
            }
        } else if (cmbPosition.getItemCount() > 0) {
            cmbPosition.setSelectedIndex(0);
        }

        loadUserGroups(selectedUserId);

    }//GEN-LAST:event_tblUsersMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        currentPage = 1;
        loadUsers();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!hasPermission(4)) {
            return;
        }

        if (selectedUserId == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک کاربر را از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "آیا از حذف این کاربر اطمینان دارید؟",
                "تأیید حذف",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (result != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {

            final int userId = selectedUserId;

            com.programsfuture.usermanagement.data.DatabaseExecutor.executeTransaction(
                    connection -> {

                        try {

                            groupDAO.deleteUserGroupsByUserId(
                                    connection,
                                    userId
                            );

                            int deletedRows = userDAO.delete(
                                    connection,
                                    userId
                            );

                            if (deletedRows == 0) {
                                throw new Exception(
                                        "کاربر موردنظر برای حذف پیدا نشد."
                                );
                            }

                        } catch (Exception e) {

                            throw new RuntimeException(e);
                        }
                    }
            );

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "کاربر با موفقیت حذف شد.",
                    "حذف",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            clearUserForm();
            loadUsers();

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(e),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed

        btnSaveActionPerformed(null);
    }//GEN-LAST:event_menuSaveActionPerformed

    private void menuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUpdateActionPerformed

        btnUpdateActionPerformed(null);
    }//GEN-LAST:event_menuUpdateActionPerformed

    private void menuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteActionPerformed

        btnDeleteActionPerformed(null);
    }//GEN-LAST:event_menuDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private com.programsfuture.usermanagement.model.CheckBoxTree checkBoxTreeGroups;
    private javax.swing.JCheckBox chkIsActive;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblTotalPages;
    private javax.swing.JLabel lblTotalRecords;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuUpdate;
    private javax.swing.JPanel pnlPagination;
    private javax.swing.JPanel pnlSave;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JSpinner spinnerPageSize;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
