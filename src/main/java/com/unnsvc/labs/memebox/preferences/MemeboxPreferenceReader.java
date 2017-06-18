
package com.unnsvc.labs.memebox.preferences;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.unnsvc.labs.memebox.MemeboxUtils;

public class MemeboxPreferenceReader {

	public MemeboxPreferenceReader() {

	}

	public static MemeboxPreferences readPreferences(File location) throws ParserConfigurationException, SAXException, IOException {

		MemeboxPreferences prefs = new MemeboxPreferences();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		// validate
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		StreamSource schemaSource = new StreamSource(MemeboxPreferenceReader.class.getResourceAsStream("/META-INF/schema/memebox.xsd"));
		Schema schema = schemaFactory.newSchema(new Source[] { schemaSource });
		factory.setSchema(schema);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(location);

		MemeboxUtils.listChildren(document, memeboxNode -> {

			handleMemebox(prefs, memeboxNode);
		});

		return prefs;
	}

	private static void handleMemebox(MemeboxPreferences prefs, Node memeboxNode) {

		MemeboxUtils.listChildren(memeboxNode, child -> {

			if (child.getNodeName().equals("storage")) {

				String locationStr = child.getAttributes().getNamedItem("location").getNodeValue();
				File location = new File(locationStr);
				prefs.setLocation(location);
			} else if (child.getNodeName().equals("watch")) {

				String locationStr = child.getAttributes().getNamedItem("location").getNodeValue();
				File location = new File(locationStr);
				prefs.addWatchLocation(location);
			}
		});
	}
}
