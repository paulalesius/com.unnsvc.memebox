
package com.unnsvc.memebox;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MemeboxThreadPool extends ThreadPoolExecutor implements IMemeboxComponent {

	public MemeboxThreadPool(int nrThreads) {

		super(nrThreads, nrThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public String getIdentifier() {

		return MemeboxThreadPool.class.getName();
	}
}
