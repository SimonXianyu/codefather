package io.github.SimonXianyu.codefather.model;

import java.util.List;

/**
 *
 * Created by simon on 14-8-19.
 */
public class EntityNode {
    /** Used to store related path */
    private String path;

    private List<EntityNode> children;
    private List<EntityDef> entities;

    public EntityNode(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<EntityNode> getChildren() {
        return children;
    }

    public void setChildren(List<EntityNode> children) {
        this.children = children;
    }

    public List<EntityDef> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDef> entities) {
        this.entities = entities;
    }
}
