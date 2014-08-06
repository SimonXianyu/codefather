package com.github.SimonXianyu.codefather.templates;

import java.util.Properties;

/**
 * Created by simon on 14-7-31.
 */
public class TemplateDef {
    private String path = "";
    private String name;
    private String fullname;
    private Properties config;

    public TemplateDef(String tname, TemplateNode node) {
        this.name=tname;
        this.path = node.getPath();
        this.fullname = tname+'/'+node.getPath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }
}
