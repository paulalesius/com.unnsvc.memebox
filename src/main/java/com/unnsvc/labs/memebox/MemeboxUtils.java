
package com.unnsvc.labs.memebox;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MemeboxUtils {

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
}
