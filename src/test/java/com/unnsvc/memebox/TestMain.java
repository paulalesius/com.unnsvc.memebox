
package com.unnsvc.memebox;

import java.awt.event.WindowEvent;
import java.io.File;

import org.junit.Test;

import com.unnsvc.memebox.ui.MainFrame;

public class TestMain {

	@Test
	public void testMain() throws Exception {

		File configLocation = new File("src/test/resources/memebox.xml");

		Main main = new Main();
		main.startup(configLocation);
		
		MainFrame mainFrame = main.getContext().getComponent(MainFrame.class);
		mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
		// mainFrame.dispose();
	}
}
