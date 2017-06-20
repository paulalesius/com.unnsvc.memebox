
package com.unnsvc.memebox.backup;

import java.io.File;

import com.unnsvc.memebox.IMemeboxComponent;
import com.unnsvc.memebox.MemeboxException;

public interface IMemeboxBackup extends IMemeboxComponent {

	public File performBackup() throws MemeboxException;

}
