package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.ExtraAttributeAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for element has a description and extra attributes.
 * User: Simon Xianyu
 */
public class Described
        implements ExtraAttributeAware
{

    /** Element description, can be declared in property or child element. */
    protected String description;
    protected Map<String, String> attrMap = new HashMap<String, String>();

    @Override
    public void addAttribute(String attributeName, String attributeValue) {
        attrMap.put(attributeName, attributeValue);
    }

    public Map<String, String> getAttrMap() {
        return attrMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
