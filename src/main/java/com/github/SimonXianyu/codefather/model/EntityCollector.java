package com.github.SimonXianyu.codefather.model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to collect Entity definitions in specified directory
 * Created by simon on 2014/12/1.
 */
public class EntityCollector {
    private List<EntityDef> entityDefList = new ArrayList<EntityDef>();

    private Map<String, EntityDef> entityDefMap = new HashMap<String, EntityDef>();

    private File baseDir;

    private EntityParser parser = new EntityParser();

    public EntityCollector(File targetDir) {
        this(targetDir, true);
    }

    public EntityCollector(File targetDir, boolean required) {
        this.baseDir = targetDir;
        if (required &&(!baseDir.exists() || !baseDir.isDirectory())) {
            throw new IllegalArgumentException("Wrong path for entities directory under "+targetDir.getPath());
        }
    }

    public void collect() {
        if (!baseDir.exists()) {
            return;
        }
        walk("", null );
    }
    public void collect(DefaultMutableTreeNode node) {
        walk("", node );
    }

    protected void walk(String subpath, DefaultMutableTreeNode parentNode) {
        File curDir = new File(baseDir, subpath);
        File[] children = curDir.listFiles();
        DefaultMutableTreeNode curNode = new DefaultMutableTreeNode(subpath); // currentNode
        for(File f :children) {
            String fileName = f.getName();
            if (f.isDirectory()) {
                walk(joinName(subpath, fileName), curNode);
            }
            if (!fileName.toLowerCase().endsWith(".xml")) {
                continue;
            }

            EntityDef def = parser.parse(f);
            def.setPath(subpath);
            this.entityDefList.add(def);
            this.entityDefMap.put(def.getName(), def);
            DefaultMutableTreeNode defNode = new DefaultMutableTreeNode(def);
            curNode.add(defNode);
        }
        if (null!= parentNode && curNode.getChildCount()>0) {
            parentNode.add(curNode);
        }
    }

    private String joinName(String subpath, String name) {
        if ("".equals(subpath)) {
            return name;
        }
        return subpath+"/"+name;
    }

    public int countEntity() {
        return this.entityDefList.size();
    }

    public EntityDef getEntity(String entityName) {
        return this.entityDefMap.get(entityName);
    }

    public List<EntityDef> getEntityDefList() {
        return entityDefList;
    }
}
