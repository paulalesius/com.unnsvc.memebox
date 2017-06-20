
package com.unnsvc.memebox.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The fiest extension provided to constructor, is the default extension
 * 
 * @author noname
 *
 */
public enum ESupportedExt {

	JPG("jpg", "jpeg"), PNG("png"), GIF("gif"), UNRECOGNISED;

	private List<String> exts;

	private ESupportedExt(String... exts) {

		this.exts = new ArrayList<String>(Arrays.asList(exts));
	}

	public List<String> getExts() {

		return exts;
	}

	/**
	 * The default extension for this file type
	 * 
	 * @return
	 */
	public String getDefaultExt() {

		return exts.get(0);
	}

	/**
	 * Return the corresponding enum, or null if not recognised
	 * 
	 * @param extension
	 * @return
	 */
	public static ESupportedExt fromExtension(String extension) {

		for (ESupportedExt supported : ESupportedExt.values()) {

			for (String ext : supported.getExts()) {

				if (extension.toLowerCase().equals(ext)) {

					return supported;
				}
			}
		}

		return ESupportedExt.UNRECOGNISED;
	}
}
