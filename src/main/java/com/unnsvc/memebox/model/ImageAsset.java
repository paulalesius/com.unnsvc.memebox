
package com.unnsvc.memebox.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imageAssets")
public class ImageAsset {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

	public void setId(int id) {

		this.id = id;
	}

	public int getId() {

		return id;
	}
}
