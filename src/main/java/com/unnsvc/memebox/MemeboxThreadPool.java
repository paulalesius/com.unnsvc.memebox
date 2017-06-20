
package com.unnsvc.memebox;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.unnsvc.memebox.config.IMemeboxConfig;

public class MemeboxThreadPool extends ThreadPoolExecutor implements IMemeboxComponent {

	public MemeboxThreadPool(int nrThreads) {

		super(nrThreads, nrThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public void destroyComponent() {

	}

	@Override
	public void flushComponent(IMemeboxConfig config) {

	}
}
