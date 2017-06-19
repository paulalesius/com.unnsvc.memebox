
package com.unnsvc.memebox;

import java.util.HashMap;
import java.util.Map;

public class MemeboxContext implements IMemeboxContext {

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
}
