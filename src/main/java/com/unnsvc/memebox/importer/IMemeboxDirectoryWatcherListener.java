
package com.unnsvc.memebox.importer;

import java.nio.file.Path;

import com.unnsvc.memebox.MemeboxException;

public interface IMemeboxDirectoryWatcherListener {

	/**
	 * On modify handles both create and modify events
	 * 
	 * @param path
	 */
	public void onEntryModify(boolean autoimport, Path path, ESupportedExt fileType) throws MemeboxException;

	/**
	 * This is called on application startup
	 * 
	 * @param path
	 */
	public void onInitialise(boolean autoimport, Path path, ESupportedExt fileType) throws MemeboxException;
}
