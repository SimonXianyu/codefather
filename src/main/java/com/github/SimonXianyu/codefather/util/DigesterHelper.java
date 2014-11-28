package com.github.SimonXianyu.codefather.util;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Rule;

/**
 * This class is used to create Digester instance.
 * It can be used to simply the code.
 * For example, it can be used like this:
 * DigesterHelper dh = new DigesterHelper();
 * dh.createSetObject("entity", EntityDef.class).createSetChild("entity/child",Child.class,"addChild");
 * @author Simon Xianyu
 */
public class DigesterHelper {
	private Digester dig;
    private PropertyGatherRule propertyGatherRule;
	public DigesterHelper(Digester dig) {
		this.dig = dig;
        this.initThreadSafeRules();
	}
	
	public DigesterHelper() {
		this.dig = new Digester();
        this.initThreadSafeRules();
	}

    private void initThreadSafeRules() {
        propertyGatherRule = new PropertyGatherRule();
    }

    public DigesterHelper addCustomRule(String pattern, Rule rule) {
        this.dig.addRule(pattern, rule);
        return this;
    }

    /**
     * Create Object, set properties
     * @param pattern xml path pattern
     * @param clazz target class
     * @return self
     */
	public DigesterHelper createSetObject(String pattern, Class<?> clazz) {
		this.dig.addObjectCreate(pattern, clazz);
		this.dig.addSetProperties(pattern);
        this.dig.addRule(pattern, this.propertyGatherRule);
		return this;
	}

    /**
     * Create Object, set properties, append to parent
     * @param pattern xml path pattern
     * @param clazz target class
     * @param methodName upper class in stack
     * @return self
     */
	public DigesterHelper createSetChild(String pattern, Class<?> clazz,String methodName) {
		this.dig.addObjectCreate(pattern, clazz);
		this.dig.addSetProperties(pattern);
        this.dig.addRule(pattern, this.propertyGatherRule);
		this.dig.addSetNext(pattern, methodName);
		return this;
	}
	public DigesterHelper setPropertyWith(String pattern, String property) {
		dig.addBeanPropertySetter(pattern, property);
		return this;
	}

    public DigesterHelper setBodyText(String pattern, String method) {
        this.dig.addCallMethod(pattern, method , 1);
        this.dig.addCallParam(pattern, 0);
        return this;
    }

    public DigesterHelper createTextElement(String pattern) {
        this.dig.addObjectCreate(pattern, TextElement.class);
        this.dig.addCallMethod(pattern, "setContent", 1);
        this.dig.addCallParam(pattern, 1);
        return this;
    }

    public DigesterHelper createTextElement(String pattern, String methodName) {
        this.dig.addObjectCreate(pattern, TextElement.class);
        this.dig.addCallMethod(pattern, methodName, 1);
        this.dig.addCallParam(pattern, 1);
        return this;
    }

    public DigesterHelper addMapChild(String childPattern,
                                   Class<?> childClass, String mapKey) {
        dig.addObjectCreate(childPattern, childClass);
        dig.addSetProperties(childPattern);
        dig.addRule(childPattern, this.propertyGatherRule);
        dig.addRule(childPattern, new AddMapEntryRule(mapKey));
        return this;
    }
	
	public Digester getDigester() {
		return this.dig;
	}
}