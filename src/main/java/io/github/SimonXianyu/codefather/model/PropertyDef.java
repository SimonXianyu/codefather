package io.github.SimonXianyu.codefather.model;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for property definition.
 * User: Simon Xianyu
 */
@XmlRootElement
public class PropertyDef extends NamedDef {
    protected String type;
    protected String dbtype;
    protected int length;
    protected boolean nullable = true;

    public boolean isIngrid() {
        return "1".equals(getAttrMap().get("nogrid"));
    }

    public boolean isKey() {
        return false;
    }


    // internal properties----------------------------------------------------------------
    private String camelName;


    public String getCamelName() {
        if (this. camelName == null) {
            camelName = makeCamelName();
        }
        return camelName;
    }
    private String makeCamelName() {
        String[] cols = name.split("_");
        StringBuilder sb = new StringBuilder();
        boolean begin = true;
        for(String col : cols) {
            if (begin) {
                begin =false;
                sb.append(StringUtils.uncapitalize(col));
            } else {
                sb.append(StringUtils.capitalize(col));
            }
        }
        return sb.toString();
    }

    public String getType() {
        return type;
    }

    @XmlAttribute
    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    @XmlAttribute
    public void setLength(int length) {
        this.length = length;
    }

    public String getDbtype() {
        return dbtype;
    }

    @XmlAttribute
    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public boolean isNullable() {
        return nullable;
    }

    @XmlAttribute
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
