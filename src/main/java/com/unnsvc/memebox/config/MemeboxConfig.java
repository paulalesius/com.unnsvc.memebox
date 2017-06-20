
package com.unnsvc.memebox.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemeboxConfig implements IMemeboxConfig {

	private File location;
	private File database;
	private List<File> watchLocations;
	private File backupLocation;

	public MemeboxConfig() {

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
