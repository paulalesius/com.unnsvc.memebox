
package com.unnsvc.memebox.backup;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.MemeboxException;

public class MemeboxBackup implements IMemeboxBackup {

	private IMemeboxContext context;

	public MemeboxBackup(IMemeboxContext context) {

		this.context = context;
	}

	@Override
	public void destroyComponent() throws MemeboxException {

	}

	@Override
	public void performBackup() {

	}
}
