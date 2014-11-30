package com.github.SimonXianyu.codefather.model;

/**
 * Pojo for store attributes definition.
 * Created by simon on 14-6-19.
 */
public class AttributeDef {
    private String name;
    private AttrValueType attrType;
    private String defaultValue;
    private boolean required;
    private boolean basic;
    /** Available values, separated by a comma. Ex. "0,1" */
    private String enums;

    @Override
    public String toString() {
        return "AttributeDef{" +
                "name='" + name + '\'' +
                ", type=" + attrType +
                ", defaultValue='" + defaultValue + '\'' +
                ", required=" + required +
                ", basic=" + basic +
                ", enums='" + enums + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String typeName) {
        this.attrType = AttrValueType.fromString(typeName);
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public String getEnums() {
        return enums;
    }

    public void setEnums(String enumValues) {
        this.enums = enumValues;
    }

    public String getDefault() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AttrValueType getAttrType() {
        return attrType;
    }

    public void setAttrType(AttrValueType attrType) {
        this.attrType = attrType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
