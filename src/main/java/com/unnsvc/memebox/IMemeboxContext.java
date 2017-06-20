
package com.unnsvc.memebox;

public interface IMemeboxContext {

	public void addComponent(IMemeboxComponent component);

	public <T> T getComponent(Class<T> type);

	public void destroy();

	public void flushComponents() throws MemeboxException;

}
