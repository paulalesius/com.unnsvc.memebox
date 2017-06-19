
package com.unnsvc.memebox.storage;

public class StoragePath {

	private String hash;

	public StoragePath(String hash) {

		this.hash = hash;
	}

	public enum EPath {

		ORIGINAL_NAME, DESCRIPTION
	}

}
