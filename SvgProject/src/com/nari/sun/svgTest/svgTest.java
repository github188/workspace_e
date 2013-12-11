package com.nari.sun.svgTest;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;


public class svgTest {

	public static JPanel jPanel = new JPanel();
	public static JSVGCanvas svgCanvas = new JSVGCanvas();
	public static void main(String[] args) {
	
	   JButton  jb = new JButton("dakai");
	   
	   jPanel.add("Center", svgCanvas);
	   jPanel.add(jb);
	   jPanel.setVisible(true);
	}

}
