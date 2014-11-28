package com.github.SimonXianyu.codefather.model;

/**
 * Pojo for store attributes definition.
 * Created by simon on 14-6-19.
 */
public class AttributeDef {
    private boolean fixed;
    private String name;
    private AttrValueType type;

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttrValueType getType() {
        return type;
    }

    public void setType(AttrValueType type) {
        this.type = type;
    }
}
