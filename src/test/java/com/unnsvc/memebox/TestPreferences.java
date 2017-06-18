
package com.unnsvc.memebox;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.unnsvc.memebox.preferences.MemeboxPreferenceReader;
import com.unnsvc.memebox.preferences.MemeboxPreferences;

public class TestPreferences {

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {

		MemeboxPreferences prefs = MemeboxPreferenceReader.readPreferences(new File("src/test/resources/memebox.xml"));
	}
}
