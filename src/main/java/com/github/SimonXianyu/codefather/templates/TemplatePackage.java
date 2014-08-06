package com.github.SimonXianyu.codefather.templates;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 14-7-31.
 */
public class TemplatePackage {
    private String path;
    private Map<String, TemplateDef> templateDefMap;

    public TemplatePackage(String path) {
        this.path = path;
        this.templateDefMap = new HashMap<String, TemplateDef>();
    }


}
