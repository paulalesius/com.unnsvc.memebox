
package com.unnsvc.memebox;

import org.junit.Test;

import com.unnsvc.memebox.ui.MainFrame;

public class TestMain extends AbstractBaseTest {

	@Test
	public void testMain() throws Exception {

		Main main = new Main();
		main.startup(getDistributionConfiguration(), getConfigLoction());

		MainFrame mainFrame = main.getContext().getComponent(MainFrame.class);
		// mainFrame.dispatchEvent(new WindowEvent(mainFrame,
		// WindowEvent.WINDOW_CLOSING));
		mainFrame.dispose();
	}
}
