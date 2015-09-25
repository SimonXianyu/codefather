package com.github.SimonXianyu.codefather.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Simon Xianyu on 2015/9/25 0025.
 */
abstract public class BaseDefinitionCollector<T extends Described> {
    protected List<T> defList = new ArrayList<T>();

    protected File baseDir;

    protected PathNode<T> rootNode = new PathNode<T>();

    public BaseDefinitionCollector(File targetDir) {
        this.baseDir = targetDir;
    }

    public void collect() {
        walk("", rootNode);
    }

    private void walk(String parent, PathNode<T> rootNode) {
        File curDir = new File(baseDir, parent);
        File[] files = curDir.listFiles();
        if (null == files || files.length <=0) {
            return;
        }
        for(File f : files) {
            if (f.isDirectory()) {
                PathNode<T> node = new PathNode<T>(parent, f.getName());
                walk(parent+"/"+f.getName(), node);
                if (node.getChildren().size()>0 || node.getElements().size()>0) {
                    rootNode.addChild(node);
                }
            } else if (f.isFile() && f.getName().toLowerCase().endsWith(".xml")) {
                T def = doParse(f);
                rootNode.addElement(def);
                defList.add(def);
            }

        }
    }

    abstract protected T doParse(File f);

    public List<T> getDefList() {
        return defList;
    }
}
