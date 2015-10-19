package io.github.SimonXianyu.codefather.model;

/**
 *
 * Created by Simon Xianyu on 2015/10/19 0019.
 */
abstract public class NamedDef extends Described implements INamedDef {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
