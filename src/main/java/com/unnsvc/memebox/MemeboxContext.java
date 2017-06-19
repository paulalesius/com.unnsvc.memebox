
package com.unnsvc.memebox;

import java.util.HashMap;
import java.util.Map;

import com.unnsvc.memebox.preferences.IMemeboxPreferences;

public class MemeboxContext implements IMemeboxContext {

	private IMemeboxPreferences prefs;
	private Map<String, IMemeboxComponent> components;

	public MemeboxContext(IMemeboxPreferences prefs) {

		this.prefs = prefs;
		this.components = new HashMap<String, IMemeboxComponent>();
	}

	@Override
	public IMemeboxPreferences getPrefs() {

		return prefs;
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
