
package com.unnsvc.memebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.unnsvc.memebox.metadata.MetadataStoreIO;

/**
 * @TODO make storage location tested
 * @author noname
 *
 */
public class TestMetadataStore extends AbstractBaseTest {

	@Test
	public void testSerialiseProperties() throws FileNotFoundException, IOException, MemeboxException {

		// load test data
		IMetadataStore loaded = new MetadataStoreIO(new File("src/test/resources/test-memebox.properties")).loadMetadata();
		// save to different file
		new MetadataStoreIO(new File("target/test-save-memebox.properties")).saveMetadata(loaded);
		// load again
		IMetadataStore loadedAgain = new MetadataStoreIO(new File("target/test-save-memebox.properties")).loadMetadata();

		Properties loadedProps = loaded.serialise();
		Properties loadedAgainProps = loadedAgain.serialise();

		Assert.assertEquals(loadedProps.size(), loadedAgainProps.size());
		for (Object key : loadedProps.keySet()) {

			String hash = (String) key;
			String val = loadedProps.getProperty(hash);
			Assert.assertNotNull(val);

			String otherVal = loadedProps.getProperty(hash);
			Assert.assertEquals(val, otherVal);
		}
	}
}
