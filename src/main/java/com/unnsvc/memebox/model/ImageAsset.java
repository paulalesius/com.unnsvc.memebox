
package com.unnsvc.memebox.model;

import javax.persistence.Column;
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
	@Column(unique = true)
	public String hash;

	public void setId(String hash) {

		this.hash = hash;
	}

	public String getId() {

		return hash;
	}
}
