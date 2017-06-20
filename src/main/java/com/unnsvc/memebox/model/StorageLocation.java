
package com.unnsvc.memebox.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.MemeboxException;

public class StorageLocation implements IStorageLocation {

	private Logger log = LoggerFactory.getLogger(StorageLocation.class);
	// <hash, <property, value>>
	private Map<String, Map<String, String>> metadata;
	/**
	 * @TODO pattern starts with sha1 hash
	 */
	private Pattern pattern = Pattern.compile("[_a-zA-Z0-9]+\\.[_a-zA-Z0-9]+");

	public StorageLocation(File storageLocation) throws MemeboxException {

		log.info("Loading database from: " + storageLocation);
		this.metadata = new HashMap<String, Map<String, String>>();
		loadProperties(storageLocation);
	}

	public void loadProperties(File storageLocation) throws MemeboxException {

		Properties properties = new Properties();

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(storageLocation))) {

			properties.load(bis);
		} catch (IOException ioe) {

			throw new MemeboxException(ioe);
		}

		for (Object keyObj : properties.keySet()) {

			String key = (String) keyObj;
			validate(key);
			String[] keyArr = key.split("\\.", 2);

			Map<String, String> itemProps = metadata.get(keyArr[0]);

			if (itemProps == null) {
				itemProps = new HashMap<String, String>();
				metadata.put(keyArr[0], itemProps);
			}

			itemProps.put(key, properties.getProperty(key));
		}

		log.info("Loaded " + metadata.size() + " items from database");
	}

	private boolean validate(String key) {

		if (!pattern.matcher(key).matches()) {

			log.error("Invalid key: " + key);
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Map<String, String>> getMetadata() {

		return metadata;
	}

	@Override
	public String getProperty(String hash, String key) {

		return metadata.get(hash).get(key);
	}

	@Override
	public void destroyComponent() {

	}

	@Override
	public Properties serialise() {

		Properties props = new Properties();
		for (String key : metadata.keySet()) {

			Map<String, String> assetProps = metadata.get(key);
			for (String assetPropsKey : assetProps.keySet()) {

				props.put(key + "." + assetPropsKey, assetProps.get(assetPropsKey));
			}
		}
		return props;
	}
}
