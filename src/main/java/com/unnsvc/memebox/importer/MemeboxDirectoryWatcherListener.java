
package com.unnsvc.memebox.importer;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMemeboxContext;

public class MemeboxDirectoryWatcherListener implements IMemeboxDirectoryWatcherListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	private IMemeboxContext context;

	public MemeboxDirectoryWatcherListener(IMemeboxContext context) {

		this.context = context;
	}

	@Override
	public void onInitialise(Path path, ESupportedExt ext) {

		log.trace("On initialise " + path + " ext: " + ext);
	}

	@Override
	public void onEntryModify(Path path, ESupportedExt ext) {

		log.trace("On modify " + path + " ext: " + ext);
	}
}
