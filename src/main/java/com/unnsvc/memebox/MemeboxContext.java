
package com.unnsvc.memebox;

import com.unnsvc.memebox.preferences.IMemeboxPreferences;

public class MemeboxContext implements IMemeboxContext {

	private IMemeboxPreferences prefs;

	public MemeboxContext(IMemeboxPreferences prefs) {

		this.prefs = prefs;
	}

	@Override
	public IMemeboxPreferences getPrefs() {

		return prefs;
	}
}
