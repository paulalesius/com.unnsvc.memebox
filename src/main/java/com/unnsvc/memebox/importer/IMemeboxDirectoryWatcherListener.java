
package com.unnsvc.memebox.importer;

import java.nio.file.Path;

public interface IMemeboxDirectoryWatcherListener {

	/**
	 * On modify handles both create and modify events
	 * 
	 * @param path
	 */
	public void onEntryModify(Path path, ESupportedExt fileType);

	/**
	 * This is called on application startup
	 * 
	 * @param path
	 */
	public void onInitialise(Path path, ESupportedExt fileType);
}
