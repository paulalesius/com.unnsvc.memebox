
package com.unnsvc.memebox;

import org.junit.Before;

import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.storage.StorageLocation;

public abstract class AbstractTest extends AbstractBaseTest {

	private IMemeboxContext context;

	@Before
	public void beforeAbstractTest() throws Exception {

		IMemeboxConfig prefs = getConfigIo().readConfiguration();
		IStorageLocation location = new StorageLocation(prefs.getDatabaseFile());
		context = new MemeboxContext(getDistProperties());
		context.addComponent(prefs);
		context.addComponent(location);
	}

	protected IMemeboxContext getContext() {

		return context;
	}
}
