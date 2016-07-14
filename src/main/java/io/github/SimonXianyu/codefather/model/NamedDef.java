package io.github.SimonXianyu.codefather.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Created by Simon Xianyu on 2015/10/19 0019.
 */
abstract public class NamedDef extends Described implements INamedDef {
    protected String name;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }
}
