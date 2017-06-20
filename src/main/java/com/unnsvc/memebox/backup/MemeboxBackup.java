
package com.unnsvc.memebox.backup;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfig;
import com.unnsvc.memebox.model.StorageLocation;

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

		IMemeboxConfig config = context.getComponent(MemeboxConfig.class);
		IStorageLocation storageLocation = context.getComponent(StorageLocation.class);
		
		
	}
}
