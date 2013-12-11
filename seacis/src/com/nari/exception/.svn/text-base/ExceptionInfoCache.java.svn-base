package com.nari.exception;

import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.nari.util.XmlParser;
import com.nari.util.XmlUtil;

public class ExceptionInfoCache {

	private static final ExceptionInfoCache SINGLETON = new ExceptionInfoCache();

	private HashMap<String, ExceptionInfoDTO> ecache = new HashMap<String, ExceptionInfoDTO>();

	private ExceptionInfoCache() {
		try {
			Document doc = XmlParser.parse("/exception_info.xml");
			Element[] elements = XmlUtil.getElements("exception", doc);
			if (elements != null && elements.length > 0) {
				for (int i = 0; i < elements.length; i++) {
					Element exElement = elements[i];
					addException(exElement);
				}
			}
		} catch (Throwable t) {
			ecache = null;
		}
	}

	private void addException(Element exElement) {
		String exName = exElement.getAttribute("name");
		String context = XmlUtil.getTagValue(exElement, "contextind");
		String messageCode = XmlUtil.getTagValue(exElement, "messagecode");
		boolean contextInd = false;
		boolean confirmationInd = false;
		if (context != null)
			contextInd = Boolean.valueOf(context).booleanValue();
		String confirmation = XmlUtil.getTagValue(exElement, "confirmationind");
		if (confirmation != null)
			confirmationInd = Boolean.valueOf(confirmation).booleanValue();
		String loggingType = XmlUtil.getTagValue(exElement, "loggingtype");
		ExceptionInfoDTO exInfo = new ExceptionInfoDTO(messageCode, contextInd,
				confirmationInd, loggingType);
		ecache.put(exName, exInfo);
	}

	public static ExceptionInfoCache getInstance() {
		return SINGLETON;
	}

	public ExceptionInfoDTO getExceptionInfo(String errorId) {
		if (ecache == null)
			return null;
		return (ExceptionInfoDTO) ecache.get(errorId);
	}
}