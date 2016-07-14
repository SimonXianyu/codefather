package io.github.SimonXianyu.codefather.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Simon Xianyu
 * Date: 13-2-2
 * Time: 下午10:10
 */
@XmlRootElement(name="key")
public class KeyDef extends PropertyDef implements Comparable<KeyDef> {
    private boolean generated;
    private String generator;

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

    public String getGenerator() {
        return generator;
    }
    @XmlAttribute
    public void setGenerator(String generator) {
        this.generator = generator;
    }
    @XmlAttribute
    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    @Override
    public int compareTo(KeyDef o) {
        return this.getName().compareTo(o.getName());
    }
}
