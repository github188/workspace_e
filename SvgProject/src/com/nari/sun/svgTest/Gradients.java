package com.nari.sun.svgTest;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Gradients {
	public static final String COOL_RADIAL_GRADIENT_ID = "cr_grad";
	public static final String VERTICAL_GRADIENT_ID = "v_grad";
	
	public static void insertCoolRadialGradient(Document doc) {
	    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	    Element defs = getOrCreateDefs(doc);

	    Element gradient = doc.createElementNS(svgNS, "radialGradient");
	    gradient.setAttributeNS(null, "id", COOL_RADIAL_GRADIENT_ID);
	    gradient.setAttributeNS(null, "fx", "30%");
	    gradient.setAttributeNS(null, "fy", "30%");
	    
	    Element stop1 = doc.createElementNS(svgNS, "stop");
	    stop1.setAttributeNS(null, "offset", "0%");
	    stop1.setAttributeNS(null, "stop-color", "#fff");
	    gradient.appendChild(stop1);

	    Element stop2 = doc.createElementNS(svgNS, "stop");
	    stop2.setAttributeNS(null, "offset", "100%");
	    stop2.setAttributeNS(null, "stop-color", "#666");
	    gradient.appendChild(stop2);

	    defs.appendChild(gradient);
	}
	
	public static void insertVerticalGradient(Document doc) {
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	    Element defs = getOrCreateDefs(doc);

	    Element gradient = doc.createElementNS(svgNS, "linearGradient");
	    gradient.setAttributeNS(null, "id", VERTICAL_GRADIENT_ID);
	    gradient.setAttributeNS(null, "x1", "0%");
	    gradient.setAttributeNS(null, "x2", "0%");
	    gradient.setAttributeNS(null, "y1", "0%");
	    gradient.setAttributeNS(null, "y2", "100%");
	    
	    Element stop1 = doc.createElementNS(svgNS, "stop");
	    stop1.setAttributeNS(null, "offset", "0%");
	    stop1.setAttributeNS(null, "stop-color", "#666");
	    gradient.appendChild(stop1);

	    Element stop2 = doc.createElementNS(svgNS, "stop");
	    stop2.setAttributeNS(null, "offset", "100%");
	    stop2.setAttributeNS(null, "stop-color", "#fff");
	    gradient.appendChild(stop2);

	    defs.appendChild(gradient);
	}
	
	// The document might already have a <defs> element
	// so we check that and if it doesn't, we add one
	private static Element getOrCreateDefs(Document doc) {
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	    Element root = doc.getDocumentElement();
	    Element defs = null;
	    
	    NodeList nl = root.getElementsByTagNameNS(svgNS, "defs");
	    if (nl.getLength() > 0) {
	      defs = (Element)nl.item(0);
	    }
	    else {
	      defs = doc.createElementNS(svgNS, "defs");
	      root.appendChild(defs);
	    }
	    
	    return defs;
	}
}
