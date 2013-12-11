package com.nari.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.nari.exception.XmlParseException;

public class XmlParser {

    public static Document parse(InputStream in) throws XmlParseException {
        Document document = null;
        if (in == null)
            return null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(in);
        } catch (Throwable th) {
            throw new XmlParseException("Exception occured while parsing XML",th);
        }
        return document;
    }

    public static Document parse(String xmlPath) throws XmlParseException {
        InputStream stream = XmlParser.class.getResourceAsStream(xmlPath);
        return parse(stream);
    }
}