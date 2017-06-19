
package com.unnsvc.memebox;

/**
 * A memebox component will be stored in the context so it can be accessed
 * throughout the application
 * 
 * @author noname
 *
 */
public interface IMemeboxComponent {

	public String getIdentifier();

	public void destroy();
}
