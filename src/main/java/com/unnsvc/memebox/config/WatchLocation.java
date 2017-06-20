
package com.unnsvc.memebox.config;

import java.io.File;

public class WatchLocation {

	private File location;
	private boolean autoimport;

	public WatchLocation(File location, boolean autoimport) {

		this.location = location;
		this.autoimport = autoimport;
	}

	public File getLocation() {

		return location;
	}

	public boolean getAutoimport() {

		return autoimport;
	}

	@Override
	public String toString() {

		return "WatchLocation [location=" + location + ", autoimport=" + autoimport + "]";
	}

}
