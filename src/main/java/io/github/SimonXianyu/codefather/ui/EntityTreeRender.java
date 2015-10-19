package io.github.SimonXianyu.codefather.ui;

import io.github.SimonXianyu.codefather.model.EntityDef;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Use entity's name as node text
 * Created by simon on 2015/1/16.
 */
public class EntityTreeRender extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            Object uobj = ((DefaultMutableTreeNode) value).getUserObject();
            if (uobj instanceof EntityDef){
                setText(((EntityDef) uobj).getName());
            }
        }

        return this;
    }
}
