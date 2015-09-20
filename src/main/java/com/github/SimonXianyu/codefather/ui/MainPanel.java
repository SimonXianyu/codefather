package com.github.SimonXianyu.codefather.ui;

import com.github.SimonXianyu.codefather.model.EntityDef;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Main panel
 * Created by simon on 2014/12/29.
 */
public class MainPanel extends JPanel {

    private DefaultTreeModel entityTreeModel;
    private DefaultMutableTreeNode rootNode;
    private JTree entityTree;

    private EntityPane entityPane;

    private ResourceBundle resourceBundle;

    public void initGui() {
        rootNode = new DefaultMutableTreeNode();
        rootNode.setAllowsChildren(true);
        entityTreeModel = new DefaultTreeModel(rootNode);
        entityTree = new JTree(entityTreeModel);
        entityTree.setRootVisible(false);
        entityTree.setCellRenderer(new EntityTreeRender());
        JScrollPane treePane = new JScrollPane(entityTree);


        entityPane = new EntityPane();

        JTabbedPane entityTabPane = new JTabbedPane();

        entityTabPane.add(resourceBundle.getString("panel.entity.title"), entityPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, entityTabPane);
        this.setLayout(new BorderLayout());
        this.add(splitPane);
        this.setPreferredSize(new Dimension(500,400));
    }

    private void addRootEntity(EntityDef entityDef) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
        node.setUserObject(entityDef);
        entityTreeModel.insertNodeInto(node, rootNode, rootNode.getChildCount());
    }

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/main");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainPanel mp = new MainPanel();
        mp.setResourceBundle(resourceBundle);
        mp.initGui();

        EntityDef def = new EntityDef();
        def.setName("TestEntity");
        mp.addRootEntity(def);
        def = new EntityDef();
        def.setName("TestEntity2");
        mp.addRootEntity(def);

        mp.entityTreeModel.reload(mp.rootNode);

        frame.setContentPane( mp );
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
