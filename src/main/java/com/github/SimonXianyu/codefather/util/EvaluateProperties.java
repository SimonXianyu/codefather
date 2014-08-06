package com.github.SimonXianyu.codefather.util;

import org.mvel2.templates.TemplateRuntime;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This extends java properties to implement reading
 * User: Simon Xianyu
 * Date: 13-2-7
 * Time: 下午1:54
 */
public class EvaluateProperties extends Properties {
    private Map<String, Object> context = new HashMap<String, Object>();

    public EvaluateProperties() {
    }

    public EvaluateProperties(Map<String, Object> baseContext) {
        context.putAll(baseContext);
    }

    public void applyBaseContext(Map<String, Object> baseContext) {
        context.putAll(baseContext);
    }


    @Override
    public synchronized Object put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null");
        }
        Object resultValue;
        if (value != null) {
            String valueText = value.toString();
            resultValue = TemplateRuntime.eval(valueText, this.context);
        } else {
            resultValue = "";
        }
        this.context.put(key.toString(), resultValue);
        return super.put(key, resultValue);
    }
}
