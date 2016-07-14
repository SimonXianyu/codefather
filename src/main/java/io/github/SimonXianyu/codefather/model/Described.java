package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.ExtraAttributeAware;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;
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

    protected Map<String, String> attrMap;
    protected Map<QName, String> attrMap2 = new HashMap<>();

    @Override
    public void addAttribute(String attributeName, String attributeValue) {
        if (null == attrMap) {
            attrMap = new HashMap<>();
        }
        attrMap.put(attributeName, attributeValue);
    }

    public Map<String, String> getAttrMap() {
        if (null == attrMap) {
            attrMap = new HashMap<>();
            for(Map.Entry<QName, String> e: attrMap2.entrySet()) {
                this.attrMap.put(e.getKey().getLocalPart(), e.getValue());
            }
        }
        return attrMap;
    }

    @XmlAnyAttribute
    public void setAttrMap2(Map<QName, String> attrMap) {
        this.attrMap2 = attrMap;
    }

    public Map<QName, String> getAttrMap2() {
        return attrMap2;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }

}
