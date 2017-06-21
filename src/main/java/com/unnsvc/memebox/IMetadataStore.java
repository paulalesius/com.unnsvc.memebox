
package com.unnsvc.memebox;

import java.util.Properties;
import java.util.Set;

public interface IMetadataStore extends IMemeboxComponent {

	public Set<String> getHashes();

	public String getProperty(String hash, String key);

	public Properties serialise();

	public void setProperty(String hash, String prop, String value) throws MemeboxException;
}
