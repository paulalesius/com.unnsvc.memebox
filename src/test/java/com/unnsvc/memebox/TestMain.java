
package com.unnsvc.memebox;

import java.awt.event.WindowEvent;

import org.junit.Test;

import com.unnsvc.memebox.ui.MainFrame;

public class TestMain {

	@Test
	public void testMain() throws Exception {

		Main.main(null);

		MainFrame mainFrame = (MainFrame) Main.context.getComponent(MainFrame.class.getName());
		mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
		// mainFrame.dispose();

	}
}
