package com.github.SimonXianyu.codefather.templates;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to collect template in node
 * Created by simon on 14-7-31.
 */
public class TemplateNode {
    private String path;
    private Map<String,TemplateDef> templateDefMap = new HashMap<String, TemplateDef>();
    private Map<String, TemplateNode> childNodes = new HashMap<String, TemplateNode>();

    public TemplateNode(String path) {
        this.path = path;
    }

    public TemplateNode(String parentPath, String name) {
        if (StringUtils.isBlank(parentPath)) {
            this.path = name;
        } else {
            this.path = parentPath+"/"+name;
        }
    }

    public void appendTemplate(String name, TemplateDef templateDef) {
        templateDefMap.put(name, templateDef);
    }
    public void appendChild(String name, TemplateNode node) {
        childNodes.put(name, node);
    }
    public boolean hasContent() {
        return childNodes.size() >0 || templateDefMap.size()>0;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, TemplateDef> getTemplateDefMap() {
        return templateDefMap;
    }

    public void setTemplateDefMap(Map<String, TemplateDef> templateDefMap) {
        this.templateDefMap = templateDefMap;
    }

    public Map<String, TemplateNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Map<String, TemplateNode> childNodes) {
        this.childNodes = childNodes;
    }

    public TemplateNode getChild(String name) {
        return childNodes.get(name);
    }
}
