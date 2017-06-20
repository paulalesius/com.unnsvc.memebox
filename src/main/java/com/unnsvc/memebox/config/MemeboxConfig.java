
package com.unnsvc.memebox.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.unnsvc.memebox.MemeboxConstants;

public class MemeboxConfig implements IMemeboxConfig {

	private File location;
	private File database;
	private List<WatchLocation> watchLocations;
	private File backupLocation;

	public MemeboxConfig() {

		watchLocations = new ArrayList<WatchLocation>();
	}

	public void setLocation(File location) {

		this.location = location;
	}

	@Override
	public File getStorageLocation() {

		return location;
	}
	
	@Override
	public File getImageStorageLocation() {
		
		return new File(getStorageLocation(), MemeboxConstants.NAME_DATA_IMAGE_DIRECTORY);
	}

	@Override
	public void addWatchLocation(WatchLocation watchLocation) {

		this.watchLocations.add(watchLocation);
	}

	@Override
	public List<WatchLocation> getWatchLocations() {

		return watchLocations;
	}

	public void setDatabase(File database) {

		this.database = database;
	}

	@Override
	public File getDatabaseFile() {

		return database;
	}

	@Override
	public void destroyComponent() {

	}

	@Override
	public String serialise() {

		return new MemeboxConfigSerialiser(this).getSerialised();
	}

	@Override
	public File getBackupLocation() {

		return backupLocation;
	}

	public void setBackupLocation(File backupLocation) {

		this.backupLocation = backupLocation;
	}
}
