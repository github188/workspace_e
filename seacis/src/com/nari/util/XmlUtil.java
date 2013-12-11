package com.nari.util;

import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {

	public static String getTagValue(Node node) {
		Node cld = node.getFirstChild();
		if (cld == null)
			return null;
		return cld.getNodeValue();
	}

	public static String getTagValue(Element doc, String tagName) {
		if (doc == null)
			return null;
		NodeList nodes = doc.getElementsByTagName(tagName.toLowerCase());
		if (nodes == null || nodes.getLength() <= 0)
			return null;
		return getTagValue(nodes.item(0));
	}

	public static String getTagValue(Document doc, String tagName) {
		if (doc == null)
			return null;
		return getTagValue(doc.getDocumentElement(), tagName);
	}

	private static Element locateElement(NodeList nodes) {
		if (nodes == null)
			return null;
		int len = nodes.getLength();
		if (len == 0)
			return null;
		Element elt = null;
		for (int i = 0; i < len; i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				elt = (Element) n;
			} else {
				continue;
			}
		}
		return elt;
	}

	public static Element getElement(String key, Document doc) {
		return getElement(key, doc.getDocumentElement());
	}

	public static Element getElement(String key, Element doc) {
		Element contentElement = doc;
		StringTokenizer tokenizer = new StringTokenizer(key, ".");
		NodeList nodes = null;
		while (tokenizer.hasMoreTokens()) {
			String tagName = tokenizer.nextToken();
			nodes = contentElement.getElementsByTagName(tagName);
			contentElement = locateElement(nodes);
			if (contentElement == null)
				return null;
		}
		return contentElement;
	}

	public static Element[] getElements(String tagName, Document input) {
		return getElements(tagName, input.getDocumentElement());
	}

	public static Element[] getElements(String tagName, Element input) {
		NodeList nodes = null;
		nodes = input.getElementsByTagName(tagName);
		if (nodes == null)
			return null;
		int len = nodes.getLength();
		if (len == 0)
			return null;
		Element[] elt = new Element[len];
		int j = 0;
		for (int i = 0; i < len; i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				elt[j] = (Element) n;
				j++;
			} else {
				continue;
			}
		}
		return elt;
	}
}