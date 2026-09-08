package com.programsfuture.usermanagement.model;

import java.awt.Component;
import java.awt.event.ItemEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;

public class CheckBoxTreeCellEditor extends AbstractCellEditor
        implements TreeCellEditor {

    private final JCheckBox checkBox = new JCheckBox();
    private CheckBoxTreeNode currentNode;

    public CheckBoxTreeCellEditor() {
        checkBox.setOpaque(false);

        checkBox.addItemListener((ItemEvent e) -> {

            if (currentNode != null) {
                currentNode.setSelected(
                        e.getStateChange() == ItemEvent.SELECTED
                );
            }

            stopCellEditing();
        });
    }

    @Override
    public Component getTreeCellEditorComponent(
            JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row) {

        if (value instanceof CheckBoxTreeNode node) {

            currentNode = node;

            checkBox.setText(
                    String.valueOf(node.getUserObject())
            );

            checkBox.setSelected(
                    node.isSelected()
            );

        } else {

            currentNode = null;

            checkBox.setText(
                    String.valueOf(value)
            );

            checkBox.setSelected(false);
        }

        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        return currentNode;
    }
}