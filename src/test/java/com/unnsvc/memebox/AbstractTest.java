
package com.unnsvc.memebox;

import org.junit.Before;

import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.metadata.MetadataStore;

public abstract class AbstractTest extends AbstractBaseTest {

	private IMemeboxContext context;

	@Before
	public void beforeAbstractTest() throws Exception {

		IMemeboxConfig prefs = getConfigIo().readConfiguration();
		IMetadataStore location = new MetadataStore(prefs.getDatabaseFile());
		context = new MemeboxContext(getDistributionConfiguration());
		context.addComponent(prefs);
		context.addComponent(location);
	}

	protected IMemeboxContext getContext() {

		return context;
	}
}
