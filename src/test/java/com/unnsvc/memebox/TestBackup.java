
package com.unnsvc.memebox;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.unnsvc.memebox.backup.IMemeboxBackup;
import com.unnsvc.memebox.backup.MemeboxBackup;

public class TestBackup extends AbstractTest {

	@Test
	public void test() throws Exception {

		IMemeboxBackup backup = new MemeboxBackup(getContext());
		File backupFile = backup.performBackup();

		Assert.assertTrue(backupFile.exists());
		Assert.assertTrue(backupFile.length() > 0);
	}
}
