
package com.unnsvc.memebox.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemeboxPreferences implements IMemeboxPreferences {

	private File location;
	private List<File> watchLocations;

	public MemeboxPreferences() {

		watchLocations = new ArrayList<File>();
	}

	public void setLocation(File location) {

		this.location = location;
	}

	public File getLocation() {

		return location;
	}

	public void addWatchLocation(File location) {

		this.watchLocations.add(location);
	}

	public List<File> getWatchLocations() {

		return watchLocations;
	}
}
