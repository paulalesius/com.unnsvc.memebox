
package com.unnsvc.memebox.config;

public class ThumbnailsConfig implements IThumbnailsConfig {

	private int width;
	private int height;

	public ThumbnailsConfig(int width, int height) {

		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {

		return width;
	}

	public void setWidth(int width) {

		this.width = width;
	}

	@Override
	public int getHeight() {

		return height;
	}

	public void setHeight(int height) {

		this.height = height;
	}
}
