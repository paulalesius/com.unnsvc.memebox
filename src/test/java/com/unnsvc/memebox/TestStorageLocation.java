
package com.unnsvc.memebox;

import java.io.File;

import org.junit.Test;

import com.unnsvc.memebox.model.StorageLocation;

public class TestStorageLocation {

	@Test
	public void testStorageLocation() throws MemeboxException {

		File locationFile = new File("src/test/resources/memebox.properties");
		StorageLocation location = new StorageLocation(locationFile);

	}
}
