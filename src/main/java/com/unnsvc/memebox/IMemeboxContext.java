
package com.unnsvc.memebox;

public interface IMemeboxContext {

	public void addComponent(IMemeboxComponent component);

	public IMemeboxComponent getComponent(String identifier);

	public void destroy();

}
