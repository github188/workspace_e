package com.nari.sun.svgTest;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.util.XMLConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class SVGWriterTurbo extends SVGWriter {
	static final long serialVersionUID = 333333;
	
	// The current text element number
	private int pathNum;
	
	// Three references to text nodes
	private Text[] texts = new Text[3];
	
	public SVGWriterTurbo(String appName) {
		super(appName);
		
		// We are going to create all the document structure
		// in the constructor this time
		
		// First of all, let's add the three different paths
		
		// Get a reference to the <svg> element of the document
		Element root = document.getDocumentElement();
		
		// Add the <defs> element
		Element defs = document.createElementNS(svgNS, "defs");
	    root.appendChild(defs);
	    
	    // Now add three paths and give them fixed ids
	    Element path0 = document.createElementNS(svgNS, "path");
	    path0.setAttributeNS(null, "id", "path0");
	    path0.setAttributeNS(null, "d", "M 20 20 L 150 280");
	    defs.appendChild(path0);
	    
	    Element path1 = document.createElementNS(svgNS, "path");
	    path1.setAttributeNS(null, "id", "path1");
	    path1.setAttributeNS(null, "d", "M 350 280 L 480 20");
	    defs.appendChild(path1);
	    
	    Element path2 = document.createElementNS(svgNS, "path");
	    path2.setAttributeNS(null, "id", "path2");
	    path2.setAttributeNS(null, "d", "M 100 290 A 150 150 0 0 1 400 290");
	    defs.appendChild(path2);
	    
	    // Append the <defs> element to the root
	    root.appendChild(defs);
	    
	    // Create and add three <text> elements,
	    // each of them with a <textPath> child which in its turn
	    // has a text node as a child
	    for (int i = 0; i < 3; i++) {
	    	
	    	// The <use> elements are needed only if you want to show 
	    	// the paths themselves; here you can specify a stroke for them
	    	Element use = document.createElementNS(svgNS, "use");
	    	use.setAttributeNS(XMLConstants.XLINK_NAMESPACE_URI, "href", "#path" + i);
	    	use.setAttributeNS(null, "fill", "none");
	    	use.setAttributeNS(null, "stroke", "orchid");
	    	root.appendChild(use);
	    	
	    	// The <text> element
	    	textElement = document.createElementNS(svgNS, "text");
	    	
	    	// The <textPath> element
	    	Element textPath = document.createElementNS(svgNS, "textPath");
	    	textPath.setAttributeNS(XMLConstants.XLINK_NAMESPACE_URI, "href", "#path" + i);
	    	
	    	// Finally, the text node which will contain an empty string 
	    	// in the beginning
	    	Text text = document.createTextNode("");
	    	
	    	// Save a reference to each text node into the texts[] array
	    	// so that we'll be able to change their content 
	    	// from the drawText() method 
	    	texts[i] = text;
	    	
	    	// Finally, append the elements to each other as needed
	    	textPath.appendChild(text);
	    	textElement.appendChild(textPath);
	    	textElement.setAttributeNS(null, "font-family", "Verdana");
	    	textElement.setAttributeNS(null, "font-size", "20");
	    	textElement.setAttributeNS(null, "fill", "olive");
	    	textElement.setAttributeNS(null, "pointer-events", "none");
	    	root.appendChild(textElement);
	    }
	}
	
	// The entry point of the application
	public static void main(String[] args) {
		SVGWriterTurbo writer = new SVGWriterTurbo("SVG Writer Turbo");
		writer.setVisible(true);
	}
	
	// This is where the text is created 
	// (or modified if it was already created before)
	protected void drawText(final String txt) {
		
		// If there is no proper text, do nothing
		if (txt.trim().equals("")) return;
		
		// As in the previous cases, we put our code into a Runnable object
		Runnable r = new Runnable() {
			public void run() {
				// Put the entered phrase onto a current path
				texts[pathNum].setData(txt);
				
				// Increment the current path number
				if (++pathNum > 2) pathNum -= 3;
			}
		};
		
		// Running our code in the UpdateManager thread
		UpdateManager um = canvas.getUpdateManager();
		um.getUpdateRunnableQueue().invokeLater(r);
	}
}
