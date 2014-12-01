package com.github.SimonXianyu.codefather.util;

import org.apache.commons.digester3.Rule;
import org.xml.sax.Attributes;

/**
 * Rule to collect attributes which are not mapping to POJO's field.
 * When top instance is ExtraAttributeAware, this rule will iterate attributes and call top's addAttribute with each attribute.
 * This make definition be expandable..
 * User: Simon Xianyu
 * Date: 13-2-4
 * Time: 上午11:39
 */
public class PropertyGatherRule extends Rule {
    @Override
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
        Object top = getDigester().peek();
        if (!(top instanceof ExtraAttributeAware)) {
            return;
        }
        ExtraAttributeAware target = (ExtraAttributeAware) top;

//        Map<String, String> values = new HashMap<String, String>();
        for (int i=0; i<attributes.getLength();++i) {
            String attrName = attributes.getLocalName(i);
            if ("".equals(attrName)) {
                attrName = attributes.getQName(i);
            }

            target.addAttribute(attrName, attributes.getValue(i));
        }
    }
}
