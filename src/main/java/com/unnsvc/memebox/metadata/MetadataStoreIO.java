
package com.unnsvc.memebox.metadata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMetadataStore;
import com.unnsvc.memebox.MemeboxException;

public class MetadataStoreIO {

	/**
	 * @TODO pattern starts with sha1 hash
	 */
	private Logger log = LoggerFactory.getLogger(MetadataStore.class);
	private File metadataLocation;

	public MetadataStoreIO(File metadataLocation) {

		this.metadataLocation = metadataLocation;
	}

	public IMetadataStore loadMetadata() throws MemeboxException {

		return loadProperties(metadataLocation);
	}

	private IMetadataStore loadProperties(File metadataLocation) throws MemeboxException {

		log.info("Reading from " + metadataLocation);
		Properties properties = new Properties();

		if (metadataLocation.exists()) {
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(metadataLocation))) {

				properties.load(bis);
			} catch (IOException ioe) {

				throw new MemeboxException(ioe);
			}
		}

		/**
		 * Iterate over properties, validate keys, add to metadata
		 */
		IMetadataStore store = new MetadataStore(metadataLocation);
		for (Object keyObj : properties.keySet()) {

			String key = (String) keyObj;
			String val = (String) properties.getProperty(key);

			if (MetadataStore.KEY_PATTERN.matcher(key).matches() && val != null) {

				String[] keyArr = key.split("\\.", 2);
				store.setProperty(keyArr[0], keyArr[1], val);
			} else {

				log.warn("Invalid key, ignoring: " + key);
			}
		}

		log.info("Loaded " + properties.size() + " items from database");
		return store;
	}

	public void saveMetadata(IMetadataStore store) throws MemeboxException {

		log.info("Writing to " + metadataLocation);

		Properties serialised = store.serialise();
		try (OutputStream os = new FileOutputStream(metadataLocation)) {

			serialised.store(os, null);
		} catch (IOException ioe) {

			throw new MemeboxException(ioe);
		}
	}
}
