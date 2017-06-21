
package com.unnsvc.memebox;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.unnsvc.memebox.config.IMemeboxConfig;

public class TestConfig extends AbstractBaseTest {

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {

		IMemeboxConfig prefs = getConfigIo().readConfiguration();
	}

	@Test
	public void testSerialise() throws Exception {

		String actual = readFromClasspath("/memebox.xml");

		IMemeboxConfig prefs = getConfigIo().readConfiguration();
		String serialised = prefs.serialise();
		Assert.assertEquals(actual, serialised);
	}

}
