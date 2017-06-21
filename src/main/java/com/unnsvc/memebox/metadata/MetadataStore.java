
package com.unnsvc.memebox.metadata;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMetadataStore;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.config.IMemeboxConfig;

public class MetadataStore implements IMetadataStore {

	public static final Pattern HASH_PATTERN = Pattern.compile("[a-z0-9]{40}");
	public static final Pattern KEY_PATTERN = Pattern.compile(HASH_PATTERN.pattern() + "\\.[_a-zA-Z0-9]+");
	private Logger log = LoggerFactory.getLogger(MetadataStore.class);
	// <hash, <property, value>>
	private Map<String, Map<String, String>> metadata;

	public MetadataStore(File storageLocation) throws MemeboxException {

		this.metadata = new HashMap<String, Map<String, String>>();
	}

	@Override
	public Set<String> getHashes() {

		return metadata.keySet();
	}

	@Override
	public String getProperty(String hash, String key) {

		return metadata.get(hash).get(key);
	}

	/**
	 * Use this method because it will validate input
	 */
	@Override
	public void setProperty(String hash, String prop, String value) throws MemeboxException {

		String key = hash + "." + prop;

		if (!KEY_PATTERN.matcher(key).matches() || value == null) {

			throw new MemeboxException("Invalid key or value: " + key + "=" + value);
		}

		Map<String, String> props = metadata.get(hash);
		if (props == null) {

			props = new HashMap<String, String>();
			metadata.put(hash, props);
		}
		props.put(prop, value);
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

	@Override
	public void flushComponent(IMemeboxConfig config) throws MemeboxException {

		File databaseFile = config.getDatabaseFile();
		new MetadataStoreIO(databaseFile).saveMetadata(this);
	}
}
