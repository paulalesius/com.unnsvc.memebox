
package com.unnsvc.memebox;

import org.junit.Test;

import com.unnsvc.memebox.backup.IMemeboxBackup;
import com.unnsvc.memebox.backup.MemeboxBackup;

public class TestBackup extends AbstractTest {

	@Test
	public void test() throws Exception {

		IMemeboxBackup backup = new MemeboxBackup(getContext());
		backup.performBackup();
	}
}
