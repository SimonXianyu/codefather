package com.github.SimonXianyu.codefather.model;

/**
 * Definition of module
 * Created by Simon Xianyu on 2015/3/30 0030.
 */
public class ModuleDef {
  private String name;
  private String text;
  private String path;
  private String packageName;
  private String entity;

  private EntityDef entityDef;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public EntityDef getEntityDef() {
    return entityDef;
  }

  public void setEntityDef(EntityDef entityDef) {
    this.entityDef = entityDef;
  }
}
