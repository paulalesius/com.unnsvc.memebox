
package com.unnsvc.memebox.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemeboxPreferences implements IMemeboxPreferences {

	private File location;
	private File database;
	private List<File> watchLocations;

	public MemeboxPreferences() {

		watchLocations = new ArrayList<File>();
	}

	public void setLocation(File location) {

		this.location = location;
	}

	@Override
	public File getLocation() {

		return location;
	}

	@Override
	public void addWatchLocation(File location) {

		this.watchLocations.add(location);
	}

	@Override
	public List<File> getWatchLocations() {

		return watchLocations;
	}

	public void setDatabase(File database) {

		this.database = database;
	}

	@Override
	public File getDatabase() {

		return database;
	}

	@Override
	public String getIdentifier() {

		return MemeboxPreferences.class.getName();
	}

	@Override
	public void destroy() {

	}
}
