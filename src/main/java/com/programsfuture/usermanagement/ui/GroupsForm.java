package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.CurrentUser;
import com.programsfuture.usermanagement.dao.UserFormSettingsDAO;
import java.util.List;
import java.util.Map;
import com.programsfuture.usermanagement.dao.GroupDAO;
import com.programsfuture.usermanagement.dao.GroupsDAO;
import com.programsfuture.usermanagement.model.CheckBoxTreeNode;
import com.programsfuture.usermanagement.model.PermissionTreeNode;
import com.programsfuture.usermanagement.model.FormTreeNode;
import com.programsfuture.usermanagement.dao.FormDAO;
import com.programsfuture.usermanagement.dao.PermissionDAO;
import javax.swing.tree.DefaultTreeModel;


public class GroupsForm extends javax.swing.JInternalFrame {

    private final GroupsDAO groupsDAO = new GroupsDAO();

    private int currentPage = 1;

    private int totalRecords = 0;
    private int totalPages = 0;
    private int selectedGroupId = -1;
    private boolean initializing = true;
    private final GroupDAO groupDAO = new GroupDAO();

    private final UserFormSettingsDAO userFormSettingsDAO
            = new UserFormSettingsDAO();

    private void applyViewPermissionSelection() {

        javax.swing.tree.DefaultTreeModel model
                = (javax.swing.tree.DefaultTreeModel) checkBoxTreePermission.getModel();

        CheckBoxTreeNode root
                = (CheckBoxTreeNode) model.getRoot();

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

        checkBoxTreePermission.repaint();
    }

    private void configurePermissionTreeRules() {

        checkBoxTreePermission.addMouseListener(
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

    private void loadGroupPermissions(int groupId) {

        try {
            List<Map<String, Object>> permissions
                    = groupDAO.findPermissionsByGroupId(groupId);

            java.util.Set<String> permissionKeys
                    = new java.util.HashSet<>();

            for (Map<String, Object> row : permissions) {

                int formId
                        = ((Number) row.get("FormId")).intValue();

                int permissionId
                        = ((Number) row.get("PermissionId")).intValue();

                permissionKeys.add(
                        formId + ":" + permissionId
                );
            }

            javax.swing.tree.DefaultTreeModel model
                    = (javax.swing.tree.DefaultTreeModel) checkBoxTreePermission.getModel();

            CheckBoxTreeNode root
                    = (CheckBoxTreeNode) model.getRoot();

            for (int i = 0; i < root.getChildCount(); i++) {

                Object formObject = root.getChildAt(i);

                if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                    continue;
                }

                boolean formSelected = false;

                Object formUserObject = formNode.getUserObject();

                if (!(formUserObject instanceof FormTreeNode formTreeNode)) {
                    continue;
                }

                int formId = formTreeNode.getId();

                for (int j = 0; j < formNode.getChildCount(); j++) {

                    Object permissionObject = formNode.getChildAt(j);

                    if (!(permissionObject instanceof CheckBoxTreeNode permissionNode)) {
                        continue;
                    }

                    Object permissionUserObject
                            = permissionNode.getUserObject();

                    if (!(permissionUserObject instanceof PermissionTreeNode permissionTreeNode)) {
                        continue;
                    }

                    int permissionId = permissionTreeNode.getId();

                    boolean selected = permissionKeys.contains(
                            formId + ":" + permissionId
                    );

                    permissionNode.setSelected(selected);

                    if (selected) {
                        formSelected = true;
                    }
                }

                formNode.setSelected(formSelected);
            }

            checkBoxTreePermission.repaint();

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

    private void updatePaginationInfo(String searchText) {
        try {
            int pageSize = (Integer) spinnerPageSize.getValue();

            totalRecords = groupsDAO.count(searchText);

            totalPages = totalRecords == 0
                    ? 0
                    : (int) Math.ceil((double) totalRecords / pageSize);

            if (totalPages > 0 && currentPage > totalPages) {
                currentPage = totalPages;
            }

            lblTotalRecords.setText(
                    "تعداد رکوردها: " + totalRecords
            );

            lblTotalPages.setText(
                    "تعداد صفحات: " + totalPages
            );

            lblPageInfo.setText(
                    totalPages == 0
                            ? "صفحه 0"
                            : "صفحه " + currentPage + " از " + totalPages
            );

            btnFirst.setEnabled(currentPage > 1);
            btnPrevious.setEnabled(currentPage > 1);
            btnNext.setEnabled(
                    totalPages > 0 && currentPage < totalPages
            );
            btnLast.setEnabled(
                    totalPages > 0 && currentPage < totalPages
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

            checkBoxTreePermission.setModel(
                    new DefaultTreeModel(root)
            );

            for (int i = 0;
                    i < checkBoxTreePermission.getRowCount();
                    i++) {

                checkBoxTreePermission.expandRow(i);
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

    private void loadGroups() {

        try {
            String searchText = txtSearch.getText().trim();
            int pageSize = (Integer) spinnerPageSize.getValue();
            int offset = (currentPage - 1) * pageSize;

            java.util.List<java.util.Map<String, Object>> rows
                    = groupsDAO.find(
                            searchText,
                            offset,
                            pageSize
                    );

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) tblGroups.getModel();

            model.setRowCount(0);

            for (java.util.Map<String, Object> row : rows) {
                model.addRow(new Object[]{
                    row.get("Id"),
                    row.get("Name"),
                    row.get("Description"),
                    row.get("IsActive")
                });
            }

            updatePaginationInfo(searchText);

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

    private void applyPermissions() {

        btnSave.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 2)
        );

        btnUpdate.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 3)
        );

        btnDelete.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 4)
        );

        menuSave.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 2)
        );

        menuUpdate.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 3)
        );

        menuDelete.setVisible(
                com.programsfuture.usermanagement.security.PermissionManager
                        .hasPermission(4, 4)
        );
    }

    private void clearGroupForm() {

        txtGroupsName.setText("");
        TextAreaDescription.setText("");
        CheckBoxActive.setSelected(true);

        selectedGroupId = -1;

        javax.swing.tree.DefaultTreeModel model
                = (javax.swing.tree.DefaultTreeModel) checkBoxTreePermission.getModel();

        CheckBoxTreeNode root
                = (CheckBoxTreeNode) model.getRoot();

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

        checkBoxTreePermission.repaint();
        tblGroups.clearSelection();
    }

    public GroupsForm() {

        initComponents();

        applyPermissions();
        try {
            spinnerPageSize.setValue(
                    userFormSettingsDAO.getPageSize(
                            CurrentUser.getUserId(),
                            4
                    )
            );
        } catch (Exception ex) {
            spinnerPageSize.setValue(10);
        }

        javax.swing.table.DefaultTableCellRenderer rightRenderer
                = new javax.swing.table.DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.RIGHT
        );

        for (int i = 0; i < tblGroups.getColumnCount(); i++) {
            tblGroups.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(rightRenderer);
        }

        tblGroups.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

        txtGroupsName.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );
        TextAreaDescription.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

        txtSearch.setComponentOrientation(
                java.awt.ComponentOrientation.RIGHT_TO_LEFT
        );

        loadGroups();
        loadForms();
        configurePermissionTreeRules();

        initializing = false;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        LabelGroupsName = new javax.swing.JLabel();
        txtGroupsName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        checkBoxTreePermission = new com.programsfuture.usermanagement.model.CheckBoxTree();
        LabelDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TextAreaDescription = new javax.swing.JTextArea();
        CheckBoxActive = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        spinnerPageSize = new javax.swing.JSpinner();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        lblTotalPages = new javax.swing.JLabel();
        lblTotalRecords = new javax.swing.JLabel();
        LabelSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGroups = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuSave = new javax.swing.JMenuItem();
        menuUpdate = new javax.swing.JMenuItem();
        menuDelete = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("دسترسی گروه");

        pnlMain.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LabelGroupsName.setText("نام گروه");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        checkBoxTreePermission.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(checkBoxTreePermission);

        LabelDescription.setText("توضیحات");

        TextAreaDescription.setColumns(20);
        TextAreaDescription.setRows(5);
        jScrollPane3.setViewportView(TextAreaDescription);

        CheckBoxActive.setText("فعال");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addComponent(txtGroupsName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(LabelGroupsName))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                            .addComponent(CheckBoxActive)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelDescription)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelGroupsName)
                            .addComponent(txtGroupsName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelDescription)
                            .addComponent(CheckBoxActive))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSave.setText("ثبت");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnUpdate.setText("ویرایش");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("حذف");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(14, 14, 14))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSearch.setText("جستجو");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        spinnerPageSize.setModel(new javax.swing.SpinnerNumberModel(10, 1, 50, 1));
        spinnerPageSize.addChangeListener(this::spinnerPageSizeStateChanged);

        btnFirst.setText("اول");
        btnFirst.addActionListener(this::btnFirstActionPerformed);

        btnPrevious.setText("قبلی");
        btnPrevious.addActionListener(this::btnPreviousActionPerformed);

        btnNext.setText("بعدی");
        btnNext.addActionListener(this::btnNextActionPerformed);

        btnLast.setText("آخر");
        btnLast.addActionListener(this::btnLastActionPerformed);

        lblPageInfo.setText("صفحه 1");

        lblTotalPages.setText("تعداد صفحات: 0");

        lblTotalRecords.setText("تعداد رکوردها: 0");

        LabelSearch.setText("جستجو");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(lblTotalRecords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalPages)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPageInfo)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(spinnerPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFirst))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(25, 25, 25)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelSearch)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(LabelSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(spinnerPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalRecords)
                        .addComponent(lblTotalPages))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblPageInfo)))
                .addContainerGap())
        );

        tblGroups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "شناسه", "نام گروه", "توضیحات", "فعال"
            }
        ));
        tblGroups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGroupsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGroups);

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed

        btnSaveActionPerformed(null);
    }//GEN-LAST:event_menuSaveActionPerformed

    private void menuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUpdateActionPerformed

        btnUpdateActionPerformed(null);
    }//GEN-LAST:event_menuUpdateActionPerformed

    private void menuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteActionPerformed

        btnDeleteActionPerformed(null);
    }//GEN-LAST:event_menuDeleteActionPerformed

    private void saveGroupPermissions(
            java.sql.Connection connection,
            int groupId
    ) throws Exception {

        applyViewPermissionSelection();

        groupDAO.deletePermissionsByGroupId(
                connection,
                groupId
        );

        javax.swing.tree.DefaultTreeModel model
                = (javax.swing.tree.DefaultTreeModel) checkBoxTreePermission.getModel();

        CheckBoxTreeNode root
                = (CheckBoxTreeNode) model.getRoot();

        for (int i = 0; i < root.getChildCount(); i++) {

            Object formObject = root.getChildAt(i);

            if (!(formObject instanceof CheckBoxTreeNode formNode)) {
                continue;
            }

            Object formUserObject = formNode.getUserObject();

            if (!(formUserObject instanceof FormTreeNode formTreeNode)) {
                continue;
            }

            int formId = formTreeNode.getId();

            for (int j = 0; j < formNode.getChildCount(); j++) {

                Object permissionObject = formNode.getChildAt(j);

                if (!(permissionObject instanceof CheckBoxTreeNode permissionNode)) {
                    continue;
                }

                if (!permissionNode.isSelected()) {
                    continue;
                }

                Object permissionUserObject = permissionNode.getUserObject();

                if (!(permissionUserObject instanceof PermissionTreeNode permissionTreeNode)) {
                    continue;
                }

                groupDAO.addPermission(
                        connection,
                        groupId,
                        formId,
                        permissionTreeNode.getId()
                );
            }
        }
    }


    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(4, 2)) {
            return;
        }

        String groupName = txtGroupsName.getText().trim();
        String description = TextAreaDescription.getText().trim();

        if (groupName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام گروه را وارد کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            txtGroupsName.requestFocus();
            return;
        }

        boolean isActive = CheckBoxActive.isSelected();

        try {
            final int[] groupId = new int[1];

            com.programsfuture.usermanagement.data.DatabaseExecutor
                    .executeTransaction(connection -> {
                        try {
                            groupId[0] = groupsDAO.insertAndReturnId(
                                    connection,
                                    groupName,
                                    description,
                                    isActive
                            );

                            saveGroupPermissions(
                                    connection,
                                    groupId[0]
                            );

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "گروه و دسترسی‌های آن با موفقیت ثبت شدند.",
                    "ثبت",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            clearGroupForm();
            loadGroups();

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

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(4, 3)) {
            return;
        }
        if (selectedGroupId <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک گروه از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String groupName = txtGroupsName.getText().trim();
        String description = TextAreaDescription.getText().trim();

        if (groupName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام گروه را وارد کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            txtGroupsName.requestFocus();
            return;
        }

        boolean isActive = CheckBoxActive.isSelected();

        try {
            com.programsfuture.usermanagement.data.DatabaseExecutor
                    .executeTransaction(connection -> {
                        try {
                            int result = groupsDAO.update(
                                    connection,
                                    selectedGroupId,
                                    groupName,
                                    description,
                                    isActive
                            );

                            if (result <= 0) {
                                throw new Exception(
                                        "ویرایش گروه انجام نشد."
                                );
                            }

                            saveGroupPermissions(
                                    connection,
                                    selectedGroupId
                            );

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "گروه و دسترسی‌های آن با موفقیت ویرایش شد.",
                    "ویرایش",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            clearGroupForm();

            loadGroups();

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    com.programsfuture.usermanagement.data.DatabaseErrorHandler
                            .getUserMessage(ex),
                    "خطا",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(4, 4)) {
            return;
        }

        if (selectedGroupId <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ابتدا یک گروه از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "آیا از حذف گروه انتخاب‌شده اطمینان دارید؟",
                "تأیید حذف",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (result != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {

            final int groupId = selectedGroupId;

            com.programsfuture.usermanagement.data.DatabaseExecutor
                    .executeTransaction(connection -> {

                        try {

                            groupDAO.deletePermissionsByGroupId(
                                    connection,
                                    groupId
                            );

                            int affectedRows = groupsDAO.deactivate(
                                    connection,
                                    groupId
                            );

                            if (affectedRows == 0) {
                                throw new Exception(
                                        "حذف گروه انجام نشد."
                                );
                            }

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "گروه با موفقیت حذف شد.",
                    "حذف",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            clearGroupForm();
            loadGroups();

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


    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        currentPage = 1;
        loadGroups();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void spinnerPageSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerPageSizeStateChanged

        if (initializing) {
            return;
        }

        try {
            int pageSize = (Integer) spinnerPageSize.getValue();

            userFormSettingsDAO.savePageSize(
                    CurrentUser.getUserId(),
                    4,
                    pageSize
            );

            currentPage = 1;
            loadGroups();

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

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        if (currentPage > 1) {
            currentPage = 1;
            loadGroups();
        }

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed

        if (currentPage > 1) {
            currentPage--;
            loadGroups();
        }

    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        if (currentPage < totalPages) {
            currentPage++;
            loadGroups();
        }

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        if (currentPage < totalPages) {
            currentPage = totalPages;
            loadGroups();
        }

    }//GEN-LAST:event_btnLastActionPerformed

    private void tblGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGroupsMouseClicked

        int selectedRow = tblGroups.getSelectedRow();

        if (selectedRow >= 0) {
            selectedGroupId = Integer.parseInt(
                    String.valueOf(
                            tblGroups.getValueAt(selectedRow, 0)
                    )
            );

            txtGroupsName.setText(
                    String.valueOf(
                            tblGroups.getValueAt(selectedRow, 1)
                    )
            );
            Object description = tblGroups.getValueAt(selectedRow, 2);

            TextAreaDescription.setText(
                    description == null
                            ? ""
                            : String.valueOf(description)
            );
            Object isActive = tblGroups.getValueAt(selectedRow, 3);

            CheckBoxActive.setSelected(
                    isActive instanceof Boolean
                    && (Boolean) isActive
            );
            loadGroupPermissions(selectedGroupId);

        }

    }//GEN-LAST:event_tblGroupsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxActive;
    private javax.swing.JLabel LabelDescription;
    private javax.swing.JLabel LabelGroupsName;
    private javax.swing.JLabel LabelSearch;
    private javax.swing.JTextArea TextAreaDescription;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private com.programsfuture.usermanagement.model.CheckBoxTree checkBoxTreePermission;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblTotalPages;
    private javax.swing.JLabel lblTotalRecords;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuUpdate;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JSpinner spinnerPageSize;
    private javax.swing.JTable tblGroups;
    private javax.swing.JTextField txtGroupsName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
