
package com.unnsvc.memebox.config;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.unnsvc.memebox.MemeboxConstants;
import com.unnsvc.memebox.MemeboxException;

public class MemeboxConfig implements IMemeboxConfig {

	private File location;
	private File databaseFile;
	private List<WatchLocation> watchLocations;
	private File backupLocation;
	private IThumbnailsConfig thumbnailsConfig;

	public MemeboxConfig() {

		watchLocations = new ArrayList<WatchLocation>();
	}

	public void setStorageLocation(File location) {

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
	public File getThumbnailsStorageLocation() {

		return new File(getStorageLocation(), MemeboxConstants.NAME_DATA_THUMBNAIL_DIRECTORY);
	}

	@Override
	public void addWatchLocation(WatchLocation watchLocation) {

		this.watchLocations.add(watchLocation);
	}

	@Override
	public List<WatchLocation> getWatchLocations() {

		return watchLocations;
	}

	public void setDatabaseFile(File databaseFile) {

		this.databaseFile = databaseFile;
	}

	@Override
	public File getDatabaseFile() {

		return databaseFile;
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

	@Override
	public IThumbnailsConfig getThumbnailsConfig() {

		return thumbnailsConfig;
	}

	public void setThumbnailsConfig(IThumbnailsConfig thumbnailsConfig) {

		this.thumbnailsConfig = thumbnailsConfig;
	}

	@Override
	public void flushComponent(IMemeboxConfig config) throws MemeboxException {

		String serialised = serialise();
		File outFile = new File(config.getStorageLocation(), MemeboxConstants.NAME_MEMEBOX_CONFIG);
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile))) {

			bos.write(serialised.getBytes());
		} catch (IOException e) {

			throw new MemeboxException(e);
		}
	}

}
