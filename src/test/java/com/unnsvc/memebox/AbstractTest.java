
package com.unnsvc.memebox;

import java.io.File;

import org.junit.Before;

import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfigurationReader;
import com.unnsvc.memebox.model.StorageLocation;

public abstract class AbstractTest {

	private IMemeboxContext context;

	@Before
	public void before() throws Exception {

		File configLocation = new File("target/test-classes/memebox.xml");
		IMemeboxConfig prefs = MemeboxConfigurationReader.readPreferences(configLocation);
		IStorageLocation location = new StorageLocation(prefs.getDatabase());
		context = new MemeboxContext();
		context.addComponent(prefs);
		context.addComponent(location);
	}

	protected IMemeboxContext getContext() {

		return context;
	}
}
