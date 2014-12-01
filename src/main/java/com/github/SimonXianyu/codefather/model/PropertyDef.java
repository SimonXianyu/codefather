package com.github.SimonXianyu.codefather.model;

import org.apache.commons.lang.StringUtils;

/**
 * Class for property definition.
 * User: Simon Xianyu
 */
public class PropertyDef extends Described {
    protected String name;
    protected String type;
    protected String dbtype;
    protected int length;
    protected boolean nullable = true;

    public boolean isIngrid() {
        return "1".equals(attrMap.get("nogrid"));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
