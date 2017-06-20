
package com.unnsvc.memebox.config;

import java.io.File;
import java.util.List;

import com.unnsvc.memebox.IMemeboxComponent;

public interface IMemeboxConfig extends IMemeboxComponent {

	public File getDatabase();

	public File getLocation();

	public void addWatchLocation(File location);

	public List<File> getWatchLocations();

	public String serialise();

	public File getBackupLocation();
}
