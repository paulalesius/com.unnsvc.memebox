
package com.unnsvc.memebox.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceManager implements IPersistenceManager {

	INSTANCE;

	private EntityManagerFactory emFactory;

	private PersistenceManager() {

		// "jpa-example" was the value of the name attribute of the
		// persistence-unit element.

		emFactory = Persistence.createEntityManagerFactory("memeboxUnit");
	}

	public EntityManager getEntityManager() {

		return emFactory.createEntityManager();
	}

	public void close() {

		emFactory.close();
	}
}