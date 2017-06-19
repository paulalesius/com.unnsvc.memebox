package com.unnsvc.memebox.preferences;

import java.io.File;
import java.util.List;

public interface IMemeboxPreferences {

	public File getDatabase();

	public File getLocation();

	public void addWatchLocation(File location);

	public List<File> getWatchLocations();

}
