package com.github.SimonXianyu.codefather.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Simon Xianyu on 2015/9/25 0025.
 */
public class PathNode<T extends Described> {
    private String parentPath;
    private String name;
    protected List<PathNode<T>> children = new ArrayList<PathNode<T>>();
    protected List<T> elements = new ArrayList<T>();

    public PathNode() {
    }

    public PathNode(String parentPath, String name) {
        this.parentPath = parentPath;
        this.name = name;
    }
    public String getFullPath() {
        if (StringUtils.isNotBlank(parentPath)) {
            return parentPath +"/"+name;
        }
        return name;
    }

    public void addChild(PathNode<T> node) {
        children.add(node);
    }
    public void addElement(T e) {
        elements.add(e);
    }

    public List<PathNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<PathNode<T>> children) {
        this.children = children;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
