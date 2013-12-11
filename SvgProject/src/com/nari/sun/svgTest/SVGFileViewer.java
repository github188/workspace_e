package com.nari.sun.svgTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;

public class SVGFileViewer extends JFrame implements ActionListener {
	static final long serialVersionUID = 222222;

	// A button to open the dialog
	private JButton btnOpenFile;
	
	// A file chooser
	private JFileChooser fcOpenFile = new JFileChooser();
	
	// The canvas
	private JSVGCanvas canvas = new JSVGCanvas();
	
	public SVGFileViewer() {
		// Usual Swing-specific code
		super("SVG File Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		btnOpenFile = new JButton("Open File...");
		btnOpenFile.addActionListener(this);
		panel.setLayout(new BorderLayout());
		panel.add("North", btnOpenFile);
		
		// Setting the size of the canvas
		canvas.setMySize(new Dimension(300, 300));
		
		// Preparing the components for displaying
		panel.add("Center", canvas);
		this.setContentPane(panel);
		this.pack();
		this.setBounds(150, 150, this.getWidth(), this.getHeight());
	}
	
	public static void main(String[] args) {
		SVGFileViewer viewer = new SVGFileViewer();
		viewer.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == btnOpenFile) {
			int returnVal = fcOpenFile.showOpenDialog(this);
			try {
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fcOpenFile.getSelectedFile();
		            canvas.setURI(file.toURL().toString());
		        }
			} catch (IOException ioe) {
				System.err.print(ioe.toString());
			}
		}
	}

}
