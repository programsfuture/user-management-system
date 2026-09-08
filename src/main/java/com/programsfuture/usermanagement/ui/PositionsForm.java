package com.programsfuture.usermanagement.ui;

import com.programsfuture.usermanagement.CurrentUser;

public class PositionsForm extends javax.swing.JInternalFrame {

    private final com.programsfuture.usermanagement.dao.PositionDAO positionDAO
            = new com.programsfuture.usermanagement.dao.PositionDAO();
    private int selectedPositionId = -1;
    private int currentPage = 1;
    private int totalPages = 0;
    private int totalRecords = 0;
    private boolean initializing = true;

    private final com.programsfuture.usermanagement.dao.UserFormSettingsDAO userFormSettingsDAO
            = new com.programsfuture.usermanagement.dao.UserFormSettingsDAO();

    private boolean hasPermission(int permissionId) {

        if (!CurrentUser.isLoggedIn()) {
            return false;
        }

        return com.programsfuture.usermanagement.security.PermissionManager
                .hasPermission(1, permissionId);
    }

    public PositionsForm() {

        initComponents();
        btnSave.setVisible(hasPermission(2));
        btnUpdate.setVisible(hasPermission(3));
        btnDelete.setVisible(hasPermission(4));

        menuSave.setVisible(hasPermission(2));
        menuUpdate.setVisible(hasPermission(3));
        menuDelete.setVisible(hasPermission(4));

        javax.swing.table.DefaultTableCellRenderer rightRenderer
                = new javax.swing.table.DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        for (int i = 0; i < tblPositions.getColumnCount(); i++) {
            tblPositions.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        tblPositions.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        txtPositionName.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        txtDescription.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        txtSearch.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);

        try {
            spinnerPageSize.setValue(
                    userFormSettingsDAO.getPageSize(CurrentUser.getUserId(), 1)
            );
        } catch (Exception ex) {
            spinnerPageSize.setValue(10);
        }

        loadPositions();
        initializing = false;

    }

    private void updatePaginationInfo(String searchText) {
        try {
            int pageSize = (Integer) spinnerPageSize.getValue();

            totalRecords = positionDAO.count(searchText);

            totalPages = totalRecords == 0
                    ? 0
                    : (int) Math.ceil((double) totalRecords / pageSize);

            if (totalPages > 0 && currentPage > totalPages) {
                currentPage = totalPages;
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

    private void loadPositions() {
        try {
            String searchText = txtSearch.getText().trim();
            int pageSize = (Integer) spinnerPageSize.getValue();
            int offset = (currentPage - 1) * pageSize;

            java.util.List<com.programsfuture.usermanagement.model.Position> positions
                    = positionDAO.find(searchText, offset, pageSize);

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) tblPositions.getModel();

            model.setRowCount(0);

            for (com.programsfuture.usermanagement.model.Position position : positions) {
                model.addRow(new Object[]{
                    position.getPositionId(),
                    position.getPositionName(),
                    position.getDescription()
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        LabelPositionName = new javax.swing.JLabel();
        txtPositionName = new javax.swing.JTextField();
        LabelDescription = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
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
        tblPositions = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuSave = new javax.swing.JMenuItem();
        menuUpdate = new javax.swing.JMenuItem();
        menuDelete = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("تعریف سمت‌ها");

        pnlMain.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LabelPositionName.setText("نام سمت");

        LabelDescription.setText("توضیحات");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPositionName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPositionName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelDescription, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelPositionName)
                    .addComponent(txtPositionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelDescription)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tblPositions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "شناسه", "نام سمت", "توضیحات"
            }
        ));
        tblPositions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPositionsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPositions);

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
        );

        setBounds(0, 0, 606, 612);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (!hasPermission(2)) {
            return;
        }
        String positionName = txtPositionName.getText().trim();
        String description = txtDescription.getText().trim();

        if (positionName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام سمت را وارد کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            txtPositionName.requestFocus();
            return;
        }

        try {
            com.programsfuture.usermanagement.model.Position position
                    = new com.programsfuture.usermanagement.model.Position(
                            0,
                            positionName,
                            description.isEmpty() ? null : description,
                            true
                    );

            int affectedRows = positionDAO.insert(position);

            if (affectedRows == 0) {
                throw new Exception("ثبت سمت انجام نشد.");
            }

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "سمت با موفقیت ثبت شد.",
                    "ثبت",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            txtPositionName.setText("");
            txtDescription.setText("");

            selectedPositionId = -1;

            loadPositions();

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

    private void tblPositionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPositionsMouseClicked

        int selectedRow = tblPositions.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        selectedPositionId = Integer.parseInt(
                String.valueOf(tblPositions.getValueAt(selectedRow, 0))
        );

        txtPositionName.setText(
                String.valueOf(tblPositions.getValueAt(selectedRow, 1))
        );

        Object description = tblPositions.getValueAt(selectedRow, 2);
        txtDescription.setText(
                description == null ? "" : String.valueOf(description)
        );


    }//GEN-LAST:event_tblPositionsMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        if (!hasPermission(3)) {
            return;
        }

        if (selectedPositionId == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "لطفاً یک سمت را از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String positionName = txtPositionName.getText().trim();
        String description = txtDescription.getText().trim();

        if (positionName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "نام سمت را وارد کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            txtPositionName.requestFocus();
            return;
        }

        try {
            com.programsfuture.usermanagement.model.Position position
                    = new com.programsfuture.usermanagement.model.Position(
                            selectedPositionId,
                            positionName,
                            description.isEmpty() ? null : description,
                            true
                    );

            positionDAO.update(position);

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "سمت با موفقیت ویرایش شد.",
                    "ویرایش",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            selectedPositionId = -1;
            txtPositionName.setText("");
            txtDescription.setText("");

            loadPositions();

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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (!hasPermission(4)) {
            return;
        }
        if (selectedPositionId == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "لطفاً یک سمت را از جدول انتخاب کنید.",
                    "هشدار",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "آیا از حذف این سمت اطمینان دارید؟",
                "تأیید حذف",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (result != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {
            positionDAO.delete(selectedPositionId);

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "سمت با موفقیت حذف شد.",
                    "حذف",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            selectedPositionId = -1;
            txtPositionName.setText("");
            txtDescription.setText("");

            loadPositions();

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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        currentPage = 1;
        loadPositions();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        if (currentPage < totalPages) {
            currentPage++;
            loadPositions();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed

        if (currentPage > 1) {
            currentPage--;
            loadPositions();
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

        if (currentPage > 1) {
            currentPage = 1;
            loadPositions();
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        if (currentPage < totalPages) {
            currentPage = totalPages;
            loadPositions();
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
                    1,
                    pageSize
            );

            currentPage = 1;
            loadPositions();

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
    private javax.swing.JLabel LabelDescription;
    private javax.swing.JLabel LabelPositionName;
    private javax.swing.JLabel LabelSearch;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblTotalPages;
    private javax.swing.JLabel lblTotalRecords;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuUpdate;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JSpinner spinnerPageSize;
    private javax.swing.JTable tblPositions;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtPositionName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
