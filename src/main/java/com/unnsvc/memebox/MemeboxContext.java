
package com.unnsvc.memebox;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.config.MemeboxConfig;

public class MemeboxContext implements IMemeboxContext {

	private Logger log = LoggerFactory.getLogger(getClass());
	private IDistributionConfiguration distConfig;
	private Map<Class<?>, IMemeboxComponent> components;

	public MemeboxContext(IDistributionConfiguration distConfig) {

		this.distConfig = distConfig;
		this.components = new HashMap<Class<?>, IMemeboxComponent>();
	}

	@Override
	public IDistributionConfiguration getDistributionConfiguration() {

		return distConfig;
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

	@Override
	public void flushComponents() throws MemeboxException {

		for (Class<?> type : components.keySet()) {

			components.get(type).flushComponent(this.getComponent(MemeboxConfig.class));
		}
	}
}
