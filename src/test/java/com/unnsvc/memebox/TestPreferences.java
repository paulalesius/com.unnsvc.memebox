
package com.unnsvc.memebox;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.unnsvc.memebox.config.MemeboxConfigurationReader;
import com.unnsvc.memebox.config.MemeboxConfig;

public class TestPreferences {

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {

		MemeboxConfig prefs = MemeboxConfigurationReader.readPreferences(new File("src/test/resources/memebox.xml"));
	}
}
