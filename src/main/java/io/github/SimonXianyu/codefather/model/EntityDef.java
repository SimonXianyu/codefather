package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.model.ignore.IgnoreFactory;
import io.github.SimonXianyu.codefather.model.ignore.IgnoreMethod;
import io.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Class of entity definition
 * Created by simon on 14-7-28.
 */
@XmlRootElement(name = "entity")
public class EntityDef extends NamedDef {

  private String path = "";
  /**
   * description text
   */
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
  @XmlElements({
      @XmlElement(name = "key", type = KeyDef.class),
      @XmlElement(name = "property")
  }
  )
  private List<PropertyDef> propertyList = new ArrayList<PropertyDef>();
  private List<PropertyDef> nonKeyPropertyList = new ArrayList<PropertyDef>();

  /**
   * The set of Key property definitions.
   */
  private Set<KeyDef> keyDefSet;
  /**
   * Property set reference map. Used to collect.
   */
  private Map<String, Boolean> propertyRefMap = new HashMap<String, Boolean>();

  /**
   * Store the reference for future usage
   */
  private EntityDef parentDef;

  private Set<IgnoreMethod> ignoreSet = new HashSet<IgnoreMethod>();


  private String luName;

  public void setIgnores(String ignores) {
    String[] strings = StringUtils.split(ignores, ",");
    if (ignores != null) {
      for (String str : strings) {
        IgnoreMethod ignoreMethod = IgnoreFactory.createMethod(str.trim());
        if (null != ignoreMethod) {
          this.ignoreSet.add(ignoreMethod);
        }
      }
    }
  }

  public boolean isTemplateIgnored(String templateName) {
    for (IgnoreMethod method : ignoreSet) {
      if (method.match(templateName)) {
        return true;
      }
    }
    return false;
  }

  public boolean isSingleKey() {
    if (LocalUtil.isEmpty(getKeyDefSet())) {
      if (null != getParentDef()) {
        return getParentDef().isSingleKey();
      }
      throw new RuntimeException("no key found with entity : " + getName());
    }
    return this.getKeyDefSet().size() == 1;
  }

  public String getDbTable() {
    if (table != null) {
      return table;
    } else {
      return getLowUnderlineName();
    }
  }

  public String getJavaPackagePath() {
    if (this.path.length() == 0) {
      return "";
    } else {
      return "/" + path;
    }
  }

  public String getJavaPackageName() {
    if (this.path.length() == 0) {
      return "";
    } else {
      return "." + path.replaceAll("/", ".");
    }
  }

  public String getSubPackageName() {
    if (this.path.length() == 0) {
      return "";
    } else {
      return path.replaceAll("/", ".");
    }
  }

  public String getUrlName() {
    if (this.getAttrMap().containsKey("urlname")) {
      return this.getAttrMap().get("urlname");
    }
    return this.getLowUnderlineName();
  }

  /**
   * Return name in lowercase and underline form.
   *
   * @return low case name whose words are separated by underline.
   */
  public String getLowUnderlineName() {
    if (luName == null) {
      StringBuilder strb = new StringBuilder();
      StringBuilder part = new StringBuilder();
      for (int i = 0; i < name.length(); ++i) {
        char ch = name.charAt(i);
        if ('A' <= ch && 'Z' >= ch) {
          if (part.length() > 0) {
            if (strb.length() > 0) {
              strb.append('_');
            }
            strb.append(part);
            part.delete(0, part.length());
          }
        }
        part.append(Character.toLowerCase(ch));
      }
      if (part.length() > 0) {
        if (strb.length() > 0) {
          strb.append('_');
        }
        strb.append(part);
      }
      this.luName = strb.toString();
    }
    return luName;
  }

  /**
   * Used in Digester parsing xml
   */
  public void addProperty(PropertyDef propertyDef) {
    if (propMap.containsKey(propertyDef.getName())) {
      throw new RuntimeException("Failed parse entity, because duplicated : " + getName() + "." + propertyDef.getName());
    }
    if (propMap == null) {
      propMap = new HashMap<>();
    }
    this.propMap.put(propertyDef.getName(), propertyDef);
    this.propertyList.add(propertyDef);
    if (propertyDef instanceof KeyDef) {
      if (null == keyDefSet) {
        keyDefSet = new TreeSet<>();
      }
      for (KeyDef keyDef : this.keyDefSet) {
        if (keyDef.getName().equals(propertyDef.getName())) {
          throw new RuntimeException("Key name must not be duplicated: " + getName() + "." + keyDef.getName());
        }
      }
      this.keyDefSet.add((KeyDef) propertyDef);
    } else {
      nonKeyPropertyList.add(propertyDef);
    }
  }

  /**
   * whether property existing.
   */
  public boolean hasPropertyOrRef(String propName) {
    boolean flag = this.propMap.containsKey(propName);
    return flag || propertyRefMap.containsKey(propName);
  }

  public boolean hasProperty(String propName) {
    return this.propMap.containsKey(propName);
  }

  public PropertyDef getPropertyOf(String name) {
    if (propertyList.size() == 0) {
      return null;
    }
    if (null == propMap) {
      propMap = new HashMap<>();
    }
    if (propMap.size() == 0) {
      for (PropertyDef p : propertyList) {
        propMap.put(p.getName(), p);
      }
    }
    return this.propMap.get(name);
  }

  public String getTable() {
    return table;
  }

  @XmlAttribute
  public void setTable(String table) {
    this.table = table;
  }

  public List<PropertyDef> getNonKeyInheritedProperties() {
    List<PropertyDef> propList = new ArrayList<PropertyDef>();
    propList.addAll(nonKeyPropertyList);
    if (getParentDef() != null) {
      propList.addAll(getParentDef().getNonKeyInheritedProperties());
    }
    return propList;
  }

  public List<PropertyDef> getPropertyList() {
    ArrayList<PropertyDef> result = new ArrayList<PropertyDef>();
    if (parentDef != null) {
      result.addAll(parentDef.getPropertyList());
    }
    result.addAll(propertyList);
    return result;
  }

  public List<PropertyDef> getNonKeyPropertyList() {
    return Collections.unmodifiableList(nonKeyPropertyList);
  }

  /**
   * Get keyDef recursively
   */
  public KeyDef getKeyDef() {
    if (LocalUtil.isEmpty(getKeyDefSet())) {
      // try to get parent key
      KeyDef parentKeyDef = getParentDef() != null ? getParentDef().getKeyDef() : null;
      if (null != parentKeyDef) {
        return parentKeyDef;
      }
      throw new RuntimeException("no keyDef found even with parent");
    }
    if (keyDefSet.size() == 1) {
      return this.keyDefSet.iterator().next();
    }
    throw new RuntimeException("This entity has more than one key");
  }

  public void setKeyDefSet(Set<KeyDef> keyDefSet) {
    this.keyDefSet = keyDefSet;
  }

  public Set<KeyDef> getKeyDefSet() {
    if (null == keyDefSet) {
      keyDefSet = new TreeSet<>();
      for (PropertyDef p : propertyList) {
        if (p instanceof KeyDef) {
          keyDefSet.add((KeyDef) p);
        }
      }
    }
    return Collections.unmodifiableSet(this.keyDefSet);
  }

  public String getParent() {
    return parent;
  }

  @XmlAttribute
  public void setParent(String parent) {
    this.parent = parent;
  }

  public boolean isAbstract() {
    return abstract1;
  }

  @XmlAttribute
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

  @XmlAttribute
  public void setText(String text) {
    this.text = text;
  }

  public String getPath() {
    return path;
  }

  @XmlAttribute
  public void setPath(String path) {
    this.path = path;
  }
}
