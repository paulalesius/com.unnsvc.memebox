
package com.unnsvc.memebox;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemeboxContext implements IMemeboxContext {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Map<Class<?>, IMemeboxComponent> components;

	public MemeboxContext() {

		this.components = new HashMap<Class<?>, IMemeboxComponent>();
	}

	@Override
	public void addComponent(IMemeboxComponent component) {

		components.put(component.getClass(), component);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getComponent(Class<T> type) {

		return (T) components.get(type);
	}

	@Override
	public void destroy() {

		for (IMemeboxComponent component : components.values()) {

			try {

				component.destroyComponent();
			} catch (Throwable throwable) {

				log.error("Exception while destroying: " + component.getClass().getName(), throwable);
			}
		}
	}

	public void debugContext() {

		for (Class<?> type : components.keySet()) {

			log.debug(type.getClass().getName() + ": " + components.get(type).getClass());
		}
	}
}
