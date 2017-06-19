
package com.unnsvc.memebox;

import com.unnsvc.memebox.preferences.IMemeboxPreferences;

public interface IMemeboxContext {

	public IMemeboxPreferences getPrefs();

	public void addComponent(IMemeboxComponent component);

	public IMemeboxComponent getComponent(String identifier);

	public void destroy();

}
