
package com.unnsvc.memebox;

import com.unnsvc.memebox.config.IMemeboxConfig;

/**
 * A memebox component will be stored in the context so it can be accessed
 * throughout the application
 * 
 * @author noname
 *
 */
public interface IMemeboxComponent {

	public void destroyComponent() throws MemeboxException;

	/**
	 * Flush component to disk
	 */
	public void flushComponent(IMemeboxConfig config) throws MemeboxException;
}
