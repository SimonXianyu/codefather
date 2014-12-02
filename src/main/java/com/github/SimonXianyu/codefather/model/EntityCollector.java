package com.github.SimonXianyu.codefather.model;

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
        this.baseDir = targetDir;
        if ( !baseDir.exists() || !baseDir.isDirectory()) {
            throw new IllegalArgumentException("Wrong path for entities directory under "+targetDir.getPath());
        }
    }

    public void collect() {
        walk("");
    }

    protected void walk(String subpath) {
        File curDir = new File(baseDir, subpath);
        File[] children = curDir.listFiles();
        for(File f :children) {
            String fileName = f.getName();
            if (f.isDirectory()) {
                walk(joinName(subpath, fileName));
            }
            if (!fileName.toLowerCase().endsWith(".xml")) {
                continue;
            }

            EntityDef def = parser.parse(f);
            def.setPath(subpath);
            this.entityDefList.add(def);
            this.entityDefMap.put(def.getName(), def);
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
