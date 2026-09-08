package com.programsfuture.usermanagement.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class CheckBoxTree extends JTree {

    public CheckBoxTree() {
        super();

        setCellRenderer(new CheckBoxTreeCellRenderer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                TreePath path = getPathForLocation(
                        e.getX(),
                        e.getY()
                );

                if (path == null) {
                    return;
                }

                Object object = path.getLastPathComponent();

                if (!(object instanceof CheckBoxTreeNode node)) {
                    return;
                }

                boolean newState = !node.isSelected();

                if (newState) {
                    selectNodeAndChildren(node);
                    selectAllParents(node);
                } else {
                    deselectNodeAndChildren(node);
                }

                repaint();
            }
        });
    }

    /**
     * Select the node and all of its descendants.
     */
    private void selectNodeAndChildren(
            CheckBoxTreeNode node) {

        node.setSelected(true);

        for (int i = 0; i < node.getChildCount(); i++) {

            Object child = node.getChildAt(i);

            if (child instanceof CheckBoxTreeNode childNode) {
                selectNodeAndChildren(childNode);
            }
        }
    }

    /**
     * Select all parents of the given node.
     *
     * A selected child always requires all of its parents
     * to be selected.
     */
    private void selectAllParents(
            CheckBoxTreeNode node) {

        TreeNode parent = node.getParent();

        while (parent instanceof CheckBoxTreeNode parentNode) {

            parentNode.setSelected(true);

            parent = parentNode.getParent();
        }
    }

    /**
     * Deselect the node and all of its descendants.
     *
     * Parents are intentionally not deselected.
     * This allows a form to remain selected while all of
     * its child permissions are turned off.
     */
    private void deselectNodeAndChildren(
            CheckBoxTreeNode node) {

        node.setSelected(false);

        for (int i = 0; i < node.getChildCount(); i++) {

            Object child = node.getChildAt(i);

            if (child instanceof CheckBoxTreeNode childNode) {
                deselectNodeAndChildren(childNode);
            }
        }
    }
}