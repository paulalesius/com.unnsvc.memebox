
package com.unnsvc.memebox;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemeboxContext implements IMemeboxContext {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Map<String, IMemeboxComponent> components;

	public MemeboxContext() {

		this.components = new HashMap<String, IMemeboxComponent>();
	}

	@Override
	public void addComponent(IMemeboxComponent component) {

		components.put(component.getIdentifier(), component);
	}

	@Override
	public IMemeboxComponent getComponent(String identifier) {

		return components.get(identifier);
	}

	@Override
	public void destroy() {

	}

	public void debugContext() {

		for (String key : components.keySet()) {

			log.debug(key + ": " + components.get(key).getClass());
		}
	}
}
