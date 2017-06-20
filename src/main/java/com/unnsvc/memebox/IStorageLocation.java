
package com.unnsvc.memebox;

import java.util.Map;
import java.util.Properties;

public interface IStorageLocation extends IMemeboxComponent {

	public Map<String, Map<String, String>> getMetadata();

	public String getProperty(String hash, String key);

	public Properties serialise();

}
