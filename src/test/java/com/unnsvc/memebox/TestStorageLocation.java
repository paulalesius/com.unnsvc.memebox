
package com.unnsvc.memebox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.unnsvc.memebox.storage.StorageLocation;

public class TestStorageLocation extends AbstractBaseTest {

	@Test
	public void testStorageLocation() throws MemeboxException {

		File locationFile = new File("src/test/resources/memebox.properties");
		StorageLocation location = new StorageLocation(locationFile);
	}

	@Test
	public void testSerialiseStorageLocation() throws FileNotFoundException, IOException, MemeboxException {

		File locationFile = new File("src/test/resources/memebox.properties");
		Properties actual = new Properties();

		try (InputStream is = new FileInputStream(locationFile)) {
			actual.load(is);
		}

		StorageLocation location = new StorageLocation(locationFile);
		Properties serialised = location.serialise();

		Assert.assertEquals(actual, serialised);
	}
}
