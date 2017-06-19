
package com.unnsvc.memebox.model;

public class StoragePath {

	private String hash;
	private EPath path;

	public StoragePath(String hash, EPath path) {

		this.hash = hash;
		this.path = path;
	}

	public enum EPath {

	}

	public String serialise() {

		return hash + "." + path.name().toLowerCase();
	}
}
