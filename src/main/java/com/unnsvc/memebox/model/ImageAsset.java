
package com.unnsvc.memebox.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imageAssets")
public class ImageAsset {

	/**
	 * The image identifiers will be their sha1sum
	 */
	@Id
	public int id;

	public void setId(int id) {

		this.id = id;
	}

	public int getId() {

		return id;
	}
}
