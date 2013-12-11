package com.nari.sun.svgTest;

import org.apache.batik.bridge.UpdateManager;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class SVGWriterPlus extends SVGWriter {
	static final long serialVersionUID = 333333;
	
	public SVGWriterPlus(String appName) {
		super(appName);
	}
	
	// The entry point of the application
	public static void main(String[] args) {
		SVGWriterPlus writer = new SVGWriterPlus("SVG Writer Plus");
		writer.setVisible(true);
	}
	
	// This is where the text is created 
	// (or modified if it was already created before)
	protected void drawText(final String txt) {
		
		// If there is no proper text, do nothing
		if (txt.trim().equals("")) return;
		
		// As in the previous chapter, we put our code into a Runnable object
		Runnable r = new Runnable() {
			public void run() {
					
				// Get a reference to the <svg> element of the document
				Element root = document.getDocumentElement();
				
				if (root.hasChildNodes()) {
					NodeList nl = root.getChildNodes();
					for (int i = 0; i < nl.getLength(); i++)
						root.removeChild(nl.item(i));
				}
				
				String[] words = txt.split("\\s");
				
				// Create the <text> element
				textElement = document.createElementNS(svgNS, "text");
				
				// Create the first <tspan> and put the first word
				// into it
				Element tspan1 = document.createElementNS(svgNS, "tspan");
				Text text1 = document.createTextNode(words[0]);
				tspan1.appendChild(text1);
				
				// Set the attributes of the first <tspan>
				tspan1.setAttributeNS(null, "dx", "-5");
				tspan1.setAttributeNS(null, "dy", "-10");
				tspan1.setAttributeNS(null, "font-weight", "bold");
				tspan1.setAttributeNS(null, "font-style", "italic");
				
				textElement.appendChild(tspan1);
				
				// If there is a second word, create a <tspan> for it
				// and set some different attributes
				if (words.length > 1) {
					Element tspan2 = document.createElementNS(svgNS, "tspan");
					Text text2 = document.createTextNode(words[1]);
					tspan2.appendChild(text2);
					
					// Set the attributes of the first <tspan>
					tspan2.setAttributeNS(null, "dx", "-50");
					tspan2.setAttributeNS(null, "dy", "40");
					tspan2.setAttributeNS(null, "letter-spacing", "0.5em");
					tspan2.setAttributeNS(null, "font-style", "italic");
					tspan2.setAttributeNS(null, "fill", "forestgreen");
					
					textElement.appendChild(tspan2);
				}
				
				// If there is a third word, create a <tspan> for it also
				// and set some different attributes
				if (words.length > 2) {
					Element tspan3 = document.createElementNS(svgNS, "tspan");
					Text text3 = document.createTextNode(words[2]);
					tspan3.appendChild(text3);
					
					// Set the attributes of the first <tspan>
					tspan3.setAttributeNS(null, "dx", "20");
					tspan3.setAttributeNS(null, "dy", "80");
					tspan3.setAttributeNS(null, "rotate", "-35 -20 -5 10 25 40 55 70 95 130 135 140 145");
					tspan3.setAttributeNS(null, "font-weight", "bold");
					tspan3.setAttributeNS(null, "font-size", "60");
					tspan3.setAttributeNS(null, "fill", "goldenrod");
					
					textElement.appendChild(tspan3);
				}
				
				// Finally, do all same things with the fourth word,
				// if it exists of course
				if (words.length > 3) {
					Element tspan4 = document.createElementNS(svgNS, "tspan");
					Text text4 = document.createTextNode(words[3]);
					tspan4.appendChild(text4);
					
					// Set the attributes of the first <tspan>
					tspan4.setAttributeNS(null, "dx", "-30");
					tspan4.setAttributeNS(null, "dy", "100 -20 -20 -20 -20 -20 -20 -20 -20 -20 -20 -20 -20");
					
					textElement.appendChild(tspan4);
				}
				
				// Set the attributes of the <text> element
				textElement.setAttributeNS(null, "x", "130");
				textElement.setAttributeNS(null, "y", "70");
				textElement.setAttributeNS(null, "font-family", "Verdana, Arial, sans-serif");
				textElement.setAttributeNS(null, "font-size", "40");
				textElement.setAttributeNS(null, "fill", "slateblue");
				
				// And finally, the new element is appended to the root
				root.appendChild(textElement);
			}
		};
		
		// Running our code in the UpdateManager thread
		UpdateManager um = canvas.getUpdateManager();
		um.getUpdateRunnableQueue().invokeLater(r);
	}
}
