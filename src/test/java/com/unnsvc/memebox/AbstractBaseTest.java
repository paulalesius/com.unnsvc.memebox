
package com.unnsvc.memebox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;

import com.unnsvc.memebox.config.MemeboxConfigReader;

public abstract class AbstractBaseTest {

	protected String readFromClasspath(String classpathLocation) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (InputStream is = TestConfig.class.getResourceAsStream(classpathLocation)) {

			int buff = -1;
			while ((buff = is.read()) != -1) {

				baos.write(buff);
			}
		}

		return new String(baos.toByteArray());
	}

	private MemeboxConfigReader configIo;
	private Properties distProperties;
	private File configLoction;

	@Before
	public void beforeAbstractBaseTest() throws MemeboxException {

		distProperties = Main.loadDistributionProperties();
		configLoction = new File(distProperties.getProperty(MemeboxConstants.DISTRIBUTION_MEMEBOXCONFIG));
		configIo = new MemeboxConfigReader(configLoction);
	}

	public MemeboxConfigReader getConfigIo() {

		return configIo;
	}

	public Properties getDistProperties() {

		return distProperties;
	}

	public File getConfigLoction() {

		return configLoction;
	}
}
