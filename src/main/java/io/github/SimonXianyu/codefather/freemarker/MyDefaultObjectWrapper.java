package io.github.SimonXianyu.codefather.freemarker;

import io.github.SimonXianyu.codefather.model.Described;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 *
 * Created by simon on 15/6/23.
 */
public class MyDefaultObjectWrapper extends DefaultObjectWrapper {
  public MyDefaultObjectWrapper() {
  }

  @Override
  protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
    if (obj instanceof Described) {
      return new BeanDynaAttrModel(obj, this);
    }
    return super.handleUnknownType(obj);
  }
}
