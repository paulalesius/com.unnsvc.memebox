
package com.unnsvc.memebox.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemeboxPreferences {

	private File location;
	private List<File> watchLocations;

	public MemeboxPreferences() {

		watchLocations = new ArrayList<File>();
	}

	public void setLocation(File location) {

		this.location = location;
	}

	public void addWatchLocation(File location) {

		this.watchLocations.add(location);
	}

}
