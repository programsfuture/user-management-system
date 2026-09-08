package com.programsfuture.usermanagement.model;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxTreeNode extends DefaultMutableTreeNode {

    private boolean selected;

    public CheckBoxTreeNode(Object userObject) {
        super(userObject);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}