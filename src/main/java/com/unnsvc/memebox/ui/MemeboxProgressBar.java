
package com.unnsvc.memebox.ui;

import javax.swing.JProgressBar;

import com.unnsvc.memebox.IMemeboxComponent;

public class MemeboxProgressBar extends JProgressBar implements IMemeboxComponent {

	private static final long serialVersionUID = 1L;

	@Override
	public String getIdentifier() {

		return getClass().getName();
	}

	@Override
	public void destroy() {

	}
}
