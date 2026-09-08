package com.programsfuture.usermanagement.model;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxTreeCellRenderer extends JCheckBox
        implements TreeCellRenderer {

    public CheckBoxTreeCellRenderer() {
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        if (value instanceof CheckBoxTreeNode node) {

            Object userObject = node.getUserObject();

            setText(
                    userObject == null
                            ? ""
                            : userObject.toString()
            );

            boolean checked = node.isSelected();

            setSelected(checked);
            setEnabled(tree.isEnabled());

        } else {

            setText(
                    value == null
                            ? ""
                            : value.toString()
            );

            setSelected(false);
            setEnabled(tree.isEnabled());
        }

        return this;
    }
}