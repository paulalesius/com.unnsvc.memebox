
package com.unnsvc.memebox.ui;

import javax.swing.JProgressBar;

import com.unnsvc.memebox.IMemeboxComponent;
import com.unnsvc.memebox.config.IMemeboxConfig;

public class MemeboxProgressBar extends JProgressBar implements IMemeboxComponent {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroyComponent() {

	}

	@Override
	public void flushComponent(IMemeboxConfig config) {

	}
}
