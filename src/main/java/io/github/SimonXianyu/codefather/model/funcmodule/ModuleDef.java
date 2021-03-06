package io.github.SimonXianyu.codefather.model.funcmodule;

import io.github.SimonXianyu.codefather.model.EntityDef;
import io.github.SimonXianyu.codefather.model.NamedDef;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition of module
 * Created by Simon Xianyu on 2015/3/30 0030.
 */
@XmlRootElement(name="module")
public class ModuleDef extends NamedDef {
  private String text;
  private String path;
  private String packageName;
  private String entity;

  private EntityDef entityDef;

  private List<FuncGroup> groups = new ArrayList<FuncGroup>();
  private List<FuncDef> funcs = new ArrayList<FuncDef>();

  public void print() {
    System.out.println(name+"["+text+"]: {");
    for(FuncGroup group : groups) {
      group.print();
    }
    for(FuncDef def : funcs) {
      def.print();
    }
    System.out.println("}");
  }

  public void addGroup(FuncGroup fg) {
    groups.add(fg);
  }
  public void addFunc(FuncDef fd) {
    funcs.add(fd);
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

  public String getPackageName() {
    return packageName;
  }
  @XmlAttribute
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getEntity() {
    return entity;
  }
  @XmlAttribute
  public void setEntity(String entity) {
    this.entity = entity;
  }

  public EntityDef getEntityDef() {
    return entityDef;
  }

  public void setEntityDef(EntityDef entityDef) {
    this.entityDef = entityDef;
  }

  @XmlElement(name="group")
  public List<FuncGroup> getGroups() {
    return groups;
  }

  @XmlElement(name="func")
  public List<FuncDef> getFuncs() {
    return funcs;
  }
}
