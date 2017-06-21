
package com.unnsvc.memebox;

import java.awt.event.WindowEvent;

import org.junit.Test;

import com.unnsvc.memebox.ui.MainFrame;

public class TestMain extends AbstractBaseTest {

	@Test
	public void testMain() throws Exception {

		Main main = new Main();
		main.startup(getDistProperties(), getConfigLoction());

		MainFrame mainFrame = main.getContext().getComponent(MainFrame.class);
		mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
		// mainFrame.dispose();
	}
}
