package io.github.SimonXianyu.codefather.model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Simon Xianyu on 2015/9/25 0025.
 */
abstract public class AbstractDefinitionCollector<T extends Described> {
    protected List<T> defList = new ArrayList<T>();

    protected File baseDir;

    protected PathNode<T> rootNode = new PathNode<T>();
    protected Map<String, T> defMap = new HashMap<String, T>();

    public AbstractDefinitionCollector(File targetDir) {
        this.baseDir = targetDir;
    }

    public void fillTree(DefaultMutableTreeNode treeNode) {
        walkTree(rootNode, treeNode);
    }

    private void walkTree(PathNode<T> pathNode, DefaultMutableTreeNode treeNode) {
        for(PathNode<T> pn : pathNode.getChildren()) {
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(pn.getName());
            walkTree(pn, subNode);
            if (subNode.getChildCount()>0) {
                treeNode.add(subNode);
            }
        }
        for(T e: pathNode.getElements()) {
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(e);
            treeNode.add(subNode);
        }
    }

    public void collect() {
        walk("", rootNode);
        onCollected();
    }

    /** do some thing after all entities are collected */
    protected void onCollected() {}

    private void walk(String parent, PathNode<T> rootNode) {
        File curDir = new File(baseDir, parent);
        File[] files = curDir.listFiles();
        if (null == files || files.length <=0) {
            return;
        }
        for(File f : files) {
            if (f.isDirectory()) {
                PathNode<T> node = new PathNode<T>(parent, f.getName());
                walk(joinName(parent, f.getName()) , node);
                if (node.getChildren().size()>0 || node.getElements().size()>0) {
                    rootNode.addChild(node);
                }
            } else if (f.isFile() && f.getName().toLowerCase().endsWith(".xml")) {
                T def = doParse(parent, f);
                rootNode.addElement(def);
                defList.add(def);
            }

        }
    }

    abstract protected T doParse(String path, File f);

    public List<T> getDefList() {
        return defList;
    }

    protected String joinName(String subpath, String name) {
        if ("".equals(subpath)) {
            return name;
        }
        return subpath+"/"+name;
    }

    public int countEntity() {
        return this.defList.size();
    }

    public T getDefByName(String name) {
        return this.defMap.get(name);
    }
}
