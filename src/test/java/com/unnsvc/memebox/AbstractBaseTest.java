
package com.unnsvc.memebox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractBaseTest {

	protected String readFromClasspath(String classpathLocation) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (InputStream is = TestConfig.class.getResourceAsStream(classpathLocation)) {

			int buff = -1;
			while ((buff = is.read()) != -1) {

				baos.write(buff);
			}
		}

		return new String(baos.toByteArray());
	}
}
