
package com.unnsvc.memebox.ui;

import java.util.concurrent.Callable;

import com.unnsvc.memebox.IMemeboxContext;

/**
 * This worker will first check whether the image artifact has a corresponding
 * thumbnail, if not then generate one, else just load existing one. Afterward,
 * replace thumbnail in library with thumbnail.
 * 
 * @author noname
 *
 */
public class LibraryThumbnailWorker implements Callable<Void> {
	
	private IMemeboxContext context;
	private LibraryScrollablePanel libraryPane;

	public LibraryThumbnailWorker(IMemeboxContext context, LibraryScrollablePanel libraryPane) {

		this.context = context;
		this.libraryPane  = libraryPane;
	}

	@Override
	public Void call() throws Exception {

		return null;
	}
}
