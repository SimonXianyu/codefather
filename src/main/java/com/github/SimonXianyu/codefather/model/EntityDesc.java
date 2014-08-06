package com.github.SimonXianyu.codefather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to describe a entity definition.
 * Created by simon on 14-6-19.
 */
public class EntityDesc {
    private List<AttributeDef> attributeDefs = new ArrayList<AttributeDef>();

    public List<AttributeDef> getAttributeDefs() {
        return attributeDefs;
    }

    public void setAttributeDefs(List<AttributeDef> attributeDefs) {
        this.attributeDefs = attributeDefs;
    }
}
