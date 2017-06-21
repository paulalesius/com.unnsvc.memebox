
package com.unnsvc.memebox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DistributionConfiguration implements IDistributionConfiguration {

	public static final String PROP_DEFAULT_CONFIG = "memeboxDefaultConfig";
	public static final String PROP_PROFILE = "memeboxProfile";
	private Properties properties;

	public DistributionConfiguration() throws MemeboxException {

		properties = new Properties();
		try (InputStream is = getClass().getResourceAsStream("/META-INF/distribution.properties")) {

			properties.load(is);
		} catch (IOException ioe) {

			throw new MemeboxException(ioe);
		}
	}

	@Override
	public String getProperty(String property) {

		return properties.getProperty(property);
	}
}
