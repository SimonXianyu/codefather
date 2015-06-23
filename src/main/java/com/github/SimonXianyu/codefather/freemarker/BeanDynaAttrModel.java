package com.github.SimonXianyu.codefather.freemarker;

import com.github.SimonXianyu.codefather.model.Described;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Map;

/**
 * Model to expose dynamic attribute in freemarker.
 * Created by simon on 15/6/23.
 */
public class BeanDynaAttrModel extends BeanModel {
  private SimpleHash attrMap;

  public BeanDynaAttrModel(Object object, BeansWrapper wrapper) {
    super(object, wrapper);
    attrMap = new SimpleHash();
    if (object instanceof Described) {
      for(Map.Entry<String, String> e : ((Described)object).getAttrMap().entrySet()) {
        attrMap.put(e.getKey(),e.getValue());
      }
    }
  }

  @Override
  public TemplateModel get(String key) throws TemplateModelException {
    TemplateModel result = super.get(key);
    if (null == result) {
      result = attrMap.get(key);
    }
    return result;
  }
}
