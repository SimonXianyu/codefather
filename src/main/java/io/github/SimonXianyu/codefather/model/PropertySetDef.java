package io.github.SimonXianyu.codefather.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Simon Xianyu
 * Date: 13-2-4
 * Time: 上午11:29
 */
public class PropertySetDef {
    private String id;
    private List<PropertyDef> propertyDefList = new ArrayList<PropertyDef>();
    private Map<String , PropertyDef> propertyDefMap = new HashMap<String, PropertyDef>();

    public void addProperty(PropertyDef def) {
        if (this.propertyDefMap.containsKey(def.getName())) {
            throw new RuntimeException("Duplicated property name:"+def.getName());
        }
        this.propertyDefList.add(def);
        this.propertyDefMap.put(def.getName(), def);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PropertyDef> getPropertyDefList() {
        return propertyDefList;
    }
}
