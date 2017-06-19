
package com.unnsvc.memebox;

import com.unnsvc.memebox.preferences.IMemeboxPreferences;

public interface IMemeboxContext {

	public IMemeboxPreferences getPrefs();

	public void addComponent(String identifier, IMemeboxComponent component);

	public IMemeboxComponent getComponent(String identifier);

}
