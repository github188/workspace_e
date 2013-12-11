package com.nari.sun.svgTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class SVGWriter extends JFrame implements ActionListener {
	static final long serialVersionUID = 333333;
	
	// Namespace string, to be used throughout the class
	protected final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

	// Swing components
	protected JTextField txtInput = new JTextField(30);
	protected JButton btnDisplay = new JButton("Display");
	protected JSVGCanvas canvas = new JSVGCanvas();
	
	// The document to contain the text
	protected Document document;
	
	// A reference to the <text> element of our SVG document
	protected Element textElement;
	
	// Constructor
	public SVGWriter(String appName) {
		// Everything is as usual here
		super(appName);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JPanel p = new JPanel();
		
		btnDisplay.addActionListener(this);
		
		canvas.setMySize(new Dimension(500, 300));
		
		// The following line is needed for our canvas to react
		// on every change of the document associated with it
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		
		p.add(txtInput);
		p.add(btnDisplay);
		
		panel.setLayout(new BorderLayout());
		panel.add("North", p);
		panel.add("Center", canvas);
		this.setContentPane(panel);
		this.pack();
		this.setBounds(150, 150, this.getWidth(), this.getHeight());
		
		// Creating the SVG document
		DOMImplementation dom = SVGDOMImplementation.getDOMImplementation();
		document = dom.createDocument(svgNS, "svg", null);
		
		// Finally, the document is associated with the canvas
		canvas.setDocument(document);
	}
	
	// The entry point of the application
	public static void main(String[] args) {
		SVGWriter writer = new SVGWriter("SVG Writer");
		writer.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		// We might add more buttons later,
		// so let's do everything properly
		Object source = ae.getSource();
		if (source == btnDisplay) {
			drawText(txtInput.getText());
		}
	}
	
	// This is where the text is created 
	// (or modified if it was already created before)
	protected void drawText(final String txt) {
		
		// If there is no proper text, do nothing
		if (txt.trim().equals("")) return;
		
		// As in the previous chapter, we put our code into a Runnable object
		Runnable r = new Runnable() {
			public void run() {
				
				// If the <text> element wasn't already created
				if (textElement == null) {
					
					// Get a reference to the <svg> element of the document
					Element root = document.getDocumentElement();
					
					// Create the <text> element
					textElement = document.createElementNS(svgNS, "text");
					
					// Create the text node with the text 
					// which will become the content of the <text> element
					Text text = document.createTextNode(txt);
					textElement.appendChild(text);
					
					// Set the attributes of the <text> element
					textElement.setAttributeNS(null, "x", "90");
					textElement.setAttributeNS(null, "y", "70");
					textElement.setAttributeNS(null, "writing-mode", "tb");
					textElement.setAttributeNS(null, "glyph-orientation-vertical", "0");
					//textElement.setAttributeNS(null, "text-anchor", "start");
					//textElement.setAttributeNS(null, "rotate", "270");
					textElement.setAttributeNS(null, "font-family", "Verdana, Arial, sans-serif");
					textElement.setAttributeNS(null, "font-size", "40");
					
					// Notice that we set the font color here
					textElement.setAttributeNS(null, "fill", "slateblue");
					
					// And finally, the new element is appended to the root
					root.appendChild(textElement);
				}
				// If the <text> element was already created before
				else {
					// Get a reference to the text node of this element
					Text text = (Text)textElement.getFirstChild();
					
					// and set its content appropriately
					text.setNodeValue(txt);
				}
			}
		};
		
		// Running our code in the UpdateManager thread
		UpdateManager um = canvas.getUpdateManager();
		um.getUpdateRunnableQueue().invokeLater(r);
	}
}
