
package com.unnsvc.memebox.config;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.unnsvc.memebox.MemeboxUtils;

public class MemeboxConfigReader {

	private static Logger log = LoggerFactory.getLogger(MemeboxConfigReader.class);
	private File configLocation;

	public MemeboxConfigReader(File configLocation) {

		this.configLocation = configLocation;
	}

	public MemeboxConfig readConfiguration() throws ParserConfigurationException, SAXException, IOException {

		log.debug("Reading from " + configLocation);
		MemeboxConfig prefs = new MemeboxConfig(configLocation);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		// validate
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		StreamSource schemaSource = new StreamSource(MemeboxConfigReader.class.getResourceAsStream("/META-INF/schema/memebox.xsd"));
		Schema schema = schemaFactory.newSchema(new Source[] { schemaSource });
		factory.setSchema(schema);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(configLocation);

		MemeboxUtils.listChildren(document, memeboxNode -> {

			handleMemebox(prefs, memeboxNode);
		});

		return prefs;
	}

	private static void handleMemebox(MemeboxConfig prefs, Node memeboxNode) {

		MemeboxUtils.listChildren(memeboxNode, child -> {

			if (child.getNodeName().equals("storage")) {

				String locationStr = child.getAttributes().getNamedItem("location").getNodeValue();
				File location = new File(locationStr);
				prefs.setStorageLocation(location);
			} else if (child.getNodeName().equals("database")) {

				String locationStr = child.getAttributes().getNamedItem("file").getNodeValue();
				File location = new File(locationStr);
				prefs.setDatabaseFile(location);
			} else if (child.getNodeName().equals("thumbnails")) {

				String widthStr = child.getAttributes().getNamedItem("width").getNodeValue();
				String heightStr = child.getAttributes().getNamedItem("height").getNodeValue();

				ThumbnailsConfig thumbConfig = new ThumbnailsConfig(Integer.valueOf(widthStr), Integer.valueOf(heightStr));
				prefs.setThumbnailsConfig(thumbConfig);
			} else if (child.getNodeName().equals("watch")) {

				String locationStr = child.getAttributes().getNamedItem("location").getNodeValue();
				String autoimportStr = child.getAttributes().getNamedItem("autoimport").getNodeValue();

				boolean autoimport = Boolean.valueOf(autoimportStr);
				File location = new File(locationStr);

				prefs.addWatchLocation(new WatchLocation(location, autoimport));
			} else if (child.getNodeName().equals("backup")) {

				String bacupsStr = child.getAttributes().getNamedItem("location").getNodeValue();
				File location = new File(bacupsStr);
				prefs.setBackupLocation(location);
			}
		});
	}
}
