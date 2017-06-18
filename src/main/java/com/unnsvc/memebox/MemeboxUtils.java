
package com.unnsvc.memebox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MemeboxUtils {

	private static Logger log = LoggerFactory.getLogger(MemeboxUtils.class);

	public static void listChildren(Document document, MemeboxListChildrenCallback callback) {

		NodeList childNodes = document.getChildNodes();
		listChildren(childNodes, callback);
	}

	private static void listChildren(NodeList childNodes, MemeboxListChildrenCallback callback) {

		for (int i = 0; i < childNodes.getLength(); i++) {

			Node child = childNodes.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				callback.onNode(child);
			}
		}
	}

	public static void listChildren(Node node, MemeboxListChildrenCallback callback) {

		NodeList childNodes = node.getChildNodes();
		listChildren(childNodes, callback);
	}

	public interface MemeboxListChildrenCallback {

		public void onNode(Node node);
	}

	/**
	 * @TODO options should allow manual configuration of this, saved in
	 *       memebox.xml
	 * @throws Exception
	 */
	public static void configureLookAndFeel() throws Exception {

		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

			if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {

				javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;
			} else if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getClassName())) {

				javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;
			}

			log.debug("Available look and feel: " + info.getClassName());
		}

		// try {
		//
		// System.err.println("System look and feel is " +
		// UIManager.getSystemLookAndFeelClassName());
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (UnsupportedLookAndFeelException e1) {
		// try {
		// System.err.println("Configuring crossplatform");
		// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		// } catch (UnsupportedLookAndFeelException e2) {
		//
		// throw new Exception("Failed to set Look and Feel, this is a bug,
		// please report.");
		// }
		// }
	}
}
