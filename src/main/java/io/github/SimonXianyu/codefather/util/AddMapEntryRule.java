package io.github.SimonXianyu.codefather.util;

import org.apache.commons.digester3.Rule;
import org.xml.sax.Attributes;

import java.util.Map;

public class AddMapEntryRule extends Rule {

	private String key;
	private String keyName;
	
	public AddMapEntryRule() {
		super();
	}
	
	public AddMapEntryRule(String keyname) {
		super();
		this.keyName = keyname;
	}
	
	@Override
	public void begin(String namespace, String name, Attributes attributes)
			throws Exception {
		super.begin(namespace, name, attributes);
		
		if (attributes.getLength()<1) {
			throw new RuntimeException("Cannot choose attribute for key");
		}
		if (keyName!=null) {
			key = attributes.getValue(keyName);
		} else if (attributes.getValue("key")!=null) {
			key = attributes.getValue("key");
		} else if (attributes.getValue("name")!=null) {
			key = attributes.getValue("name");
		} else {
			key = attributes.getValue(0);
		}
		if (key == null) {
			throw new RuntimeException("Key value is null");
		}
	}

	@Override
	public void end(String namespace, String name) throws Exception {
		if (key != null) {
			Object current = this.getDigester().peek(0);
			Object parent = this.getDigester().peek(1);
            if (parent instanceof  Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Object> target = (Map<String, Object>) parent;
                target.put(key, current);
            } else {
				throw new RuntimeException("Parent should be an instance of Map");
			}
		}
		key = null;
		super.end(namespace, name);
	}
}
