package com.github.SimonXianyu.codefather.model;

import com.github.SimonXianyu.codefather.model.schema.AttributeDef;

import java.util.*;

/**
 * This class is used to describe a entity format.
 * Created by simon on 2014/11/28.
 */
public class EntitySchema {
    private Map<String, AttributeDef> modelAttributeMap = new HashMap<String, AttributeDef>();

    private Map<String, AttributeDef>  propertyAttributeMap = new HashMap<String, AttributeDef>();
//    private List<AttributeDef> propertyAttributeList = new ArrayList<AttributeDef>();

    public Map<String, AttributeDef> getModelAttributes() {
        return Collections.unmodifiableMap(modelAttributeMap);
    }

    public Map<String , AttributeDef> getPropertyAttributes() {
        return Collections.unmodifiableMap(propertyAttributeMap);
    }

    public void setModelAttributeMap(Map<String, AttributeDef> modelAttributeMap) {
        this.modelAttributeMap = modelAttributeMap;
    }

    public void setPropertyAttributeMap(Map<String, AttributeDef> propertyAttributeMap) {
        this.propertyAttributeMap = propertyAttributeMap;
    }
}
