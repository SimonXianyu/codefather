package com.github.SimonXianyu.codefather.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to collect Entity definitions in specified directory
 * Created by simon on 2014/12/1.
 */
public class EntityCollector {
    private List<EntityDef> entityDefList = new ArrayList<EntityDef>();

    private File baseDir;

    public EntityCollector(String basePath) {
        this.baseDir = new File(basePath);
        if (null == baseDir || !baseDir.exists() || !baseDir.isDirectory()) {
            throw new IllegalArgumentException("Wrong path for entity directory :"+basePath);
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

        }
    }

    private String joinName(String subpath, String name) {
        if ("".equals(subpath)) {
            return name;
        }
        return subpath+"/"+name;
    }
}
