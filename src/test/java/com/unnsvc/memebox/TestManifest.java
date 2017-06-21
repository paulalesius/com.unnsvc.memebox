
package com.unnsvc.memebox;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestManifest {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void testReadProperties() throws MemeboxException {

		IDistributionConfiguration manifest = new DistributionConfiguration();

		String defaultConfig = manifest.getProperty(DistributionConfiguration.PROP_DEFAULT_CONFIG);
		Assert.assertNotNull(defaultConfig);
		Assert.assertTrue(defaultConfig.equals("target/test-classes/memebox.xml"));

		String defaultProfile = manifest.getProperty(DistributionConfiguration.PROP_PROFILE);
		Assert.assertNotNull(defaultProfile);
		Assert.assertTrue(defaultProfile.equals("test"));
	}
}
