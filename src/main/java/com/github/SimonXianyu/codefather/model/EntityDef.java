package com.github.SimonXianyu.codefather.model;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Class of entity definition
 * Created by simon on 14-7-28.
 */
public class EntityDef extends Described {

    private String path = "";
    /**
     * The name of entity, usually the file name.
     * First character upper case.
     */
    private String name;

    /** description text */
    private String text;
    /**
     * The database table name
     */
    private String table;
    /**
     * Parent entity name
     */
    private String parent;
    /**
     * The flag of this entity is abstract.
     */
    private boolean abstract1;
    /**
     * Property map including key and normal properties.
     * Key is property name. value is property definition.
     * Used to access property definition by name
     */
    private Map<String, PropertyDef> propMap = new HashMap<String, PropertyDef>();
    /**
     * List of property definition. Used to iterate.
     */
    private List<PropertyDef> propertyList = new ArrayList<PropertyDef>();
    private List<PropertyDef> nonKeyPropertyList = new ArrayList<PropertyDef>();

    /**
     * The set of Key property definitions.
     */
    private Set<KeyDef> keyDefSet = new TreeSet<KeyDef>();
    /**
     * Property set reference map. Used to collect.
     */
    private Map<String, Boolean> propertyRefMap = new HashMap<String, Boolean>();

    /** Store the reference for future usage */
    private EntityDef parentDef;

    private Set<String> ignoreSet = new HashSet<String>();

    public void setIgnores(String ignores) {
        String[] strings = StringUtils.split(ignores, ",");
        if (ignores != null) {
            for(String str : strings) {
                this.ignoreSet.add(str.trim());
            }
        }
    }

    public boolean isTemplateIgnored(String templateName) {
        for(String pattern : ignoreSet) {
            if (pattern.equalsIgnoreCase(templateName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingleKey() {
        return this.keyDefSet.size() == 1;
    }

    public String getUrlName() {
        if (this.attrMap.containsKey("urlname")) {
            return this.attrMap.get("urlname");
        }
        return this.getLowUnderlineNname();
    }
    /**
     * Return name in lowercase and underline form.
     * @return low case name whose words are separated by underline.
     */
    public String getLowUnderlineNname() {
        StringBuilder strb = new StringBuilder();
        StringBuilder part = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if ('A' <= ch && 'Z' >= ch) {
                if (part.length()>0) {
                    if (strb.length()>0) {
                        strb.append('_');
                    }
                    strb.append(part);
                    part.delete(0, part.length());
                }
            }
            part.append(Character.toLowerCase(ch));
        }
        if (part.length()>0) {
            if (strb.length()>0) {
                strb.append('_');
            }
            strb.append(part);
        }
        return strb.toString();
    }

    /** Used in Digester parsing xml */
    public void addProperty(PropertyDef propertyDef) {
        if (propMap.containsKey(propertyDef.getName())) {
            throw new RuntimeException("Failed parse entity, because duplicated ");
        }
        this.propMap.put(propertyDef.getName(), propertyDef);
        this.propertyList.add(propertyDef);
        if (propertyDef instanceof KeyDef) {
            for(KeyDef keyDef : this.keyDefSet) {
                if (keyDef.getName().equals(propertyDef.getName())) {
                    throw new RuntimeException("Key name must not be duplicated");
                }
            }
            this.keyDefSet.add((KeyDef) propertyDef);
        } else {
            nonKeyPropertyList.add(propertyDef);
        }
    }

    /** whether property existing. */
    public boolean hasPropertyOrRef(String propName) {
        boolean flag = this.propMap.containsKey(propName);
        return flag || propertyRefMap.containsKey(propName);
    }

    public boolean hasProperty(String propName) {
        return this.propMap.containsKey(propName);
    }

    public PropertyDef getPropertyOf(String name) {
        return this.propMap.get(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        if (table != null) {
            return table;
        } else {
            return name;
        }
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<PropertyDef> getPropertyList() {
        return Collections.unmodifiableList(propertyList);
    }

    public List<PropertyDef> getNonKeyPropertyList() {
        return Collections.unmodifiableList(nonKeyPropertyList);
    }

    public KeyDef getKeyDef() {
        if (this.keyDefSet.size()==1) {
            return this.keyDefSet.iterator().next();
        } else {
            throw new RuntimeException("This entity has more than one key");
        }
    }

    public Set<KeyDef> getKeyDefSet() {
        return Collections.unmodifiableSet(this.keyDefSet);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isAbstract() {
        return abstract1;
    }

    public void setAbstract(boolean abstract1) {
        this.abstract1 = abstract1;
    }

    public EntityDef getParentDef() {
        return parentDef;
    }

    public void setParentDef(EntityDef parentDef) {
        this.parentDef = parentDef;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
