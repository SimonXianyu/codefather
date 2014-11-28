package com.github.SimonXianyu.codefather.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to describe a entity format.
 * Created by simon on 2014/11/28.
 */
public class EntitySchema {
    private Map<String, AttributeDef> modelAttributeMap = new HashMap<String, AttributeDef>();

    private List<AttributeDef> propertyAttributeList = new ArrayList<AttributeDef>();

}
