
package com.unnsvc.memebox.model;

import java.util.HashMap;
import java.util.Map;

import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.model.StoragePath.EPath;

public class StorageLocation implements IStorageLocation {

	private Map<String, Map<EPath, String>> metadata;

	public StorageLocation() {

		this.metadata = new HashMap<String, Map<EPath, String>>();
	}

	public String getProperty(String hash, EPath path) {

		return metadata.get(hash).get(path);
	}
}
