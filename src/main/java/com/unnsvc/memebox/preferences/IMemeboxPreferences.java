
package com.unnsvc.memebox.preferences;

import java.io.File;
import java.util.List;

import com.unnsvc.memebox.IMemeboxComponent;

public interface IMemeboxPreferences extends IMemeboxComponent {

	public File getDatabase();

	public File getLocation();

	public void addWatchLocation(File location);

	public List<File> getWatchLocations();

}
