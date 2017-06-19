
package com.unnsvc.memebox.storage;

import java.io.File;

import com.unnsvc.memebox.IStorageLocation;

public class StorageLocation implements IStorageLocation {

	private File location;

	public StorageLocation(File location) {

		this.location = location;
	}
}
