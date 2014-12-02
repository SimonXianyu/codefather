package com.github.SimonXianyu.codefather.model;

/**
 * User: Simon Xianyu
 * Date: 13-2-2
 * Time: 下午10:10
 */
public class KeyDef extends PropertyDef implements Comparable<KeyDef> {
    private boolean generated;

    @Override
    public boolean isKey() {
        return true;
    }

    public KeyDef() {
        this.nullable = false;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    @Override
    public int compareTo(KeyDef o) {
        return this.getName().compareTo(o.getName());
    }
}