
package com.unnsvc.memebox.config;

import java.io.File;
import java.util.List;

import com.unnsvc.memebox.IMemeboxComponent;

public interface IMemeboxConfig extends IMemeboxComponent {

	public File getDatabaseFile();

	public File getStorageLocation();

	public void addWatchLocation(WatchLocation location);

	public List<WatchLocation> getWatchLocations();

	public String serialise();

	public File getBackupLocation();

	public File getImageStorageLocation();
}
