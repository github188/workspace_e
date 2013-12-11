package com.nari.sun.struts2;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.svg.SVGDocument;

import com.nari.model.person;

public class svgView extends Applet implements ActionListener {

	private JSVGCanvas canvas = new JSVGCanvas();

	private JButton btnOpenFile;
	private JButton zoomout, zoomin;
	private JPanel paneButton = new JPanel();
	// private JPanel panel = new JPanel();
	private JScrollPane panePicture = new JScrollPane(canvas,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// private JFileChooser fcOpenFile = new JFileChooser();
	// private String theOpenedFileURI;
	private String filename = null;
	private SVGDocument svgDocument = null;
	private InputStream is = null;

	public void init() {

		zoomout = new JButton("zoon out");
		zoomin = new JButton("zoom in");
		btnOpenFile = new JButton("open");
		paneButton.add(btnOpenFile);
		paneButton.add(zoomin);
		paneButton.add(zoomout);
		this.setLayout(new BorderLayout());
		paneButton.setBackground(Color.CYAN);
		this.add("North", paneButton);

		canvas.setPreferredSize(new Dimension(200, 200));
		canvas.revalidate();
		canvas.setDoubleBuffered(true);
		this.add("Center", panePicture);
		this.setVisible(true);
	}

	public void start() {
		btnOpenFile.addActionListener(this);
		zoomin.addActionListener(this);
		zoomout.addActionListener(this);
		zoomin.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				zoomin.setBackground(Color.pink);
			}

			public void mouseExited(MouseEvent e) {
				zoomin.setBackground(paneButton.getBackground());
			}

		});
		zoomout.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				zoomout.setBackground(Color.pink);
			}

			public void mouseExited(MouseEvent e) {
				zoomout.setBackground(paneButton.getBackground());
			}

		});
		btnOpenFile.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnOpenFile.setBackground(Color.pink);
			}

			public void mouseExited(MouseEvent e) {
				btnOpenFile.setBackground(paneButton.getBackground());
			}

		});
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == btnOpenFile) {
			openFile();
		}

		else if (source == zoomout) {

			zoomOut();
		}

		else if (source == zoomin) {
			zoomIn();
		}
	}

	private void zoomOut() {

	}

	private void zoomIn() {
		try {
			System.out.println("--------------testsvgAppletView-----------");
			URL url = new URL(this.getCodeBase(), "testMysql.jsp");
			// URL url = new URL(getCodeBase(),
			// "svgServlet/showSvgServlet.class");
			URLConnection urlconn = url.openConnection();
			urlconn.setUseCaches(false);
			urlconn.setDefaultUseCaches(false);
			int length = urlconn.getContentLength();
			DataInputStream dataInputStream = new DataInputStream(urlconn
					.getInputStream());
			byte buffer[] = new byte[length];

			dataInputStream.readFully(buffer, 0, length);
			String str = new String(buffer);
			String[] names = str.split(";");
			if (null != names) {
				Object selectedValue = JOptionPane.showInputDialog(null,
						"Please Choose one file", "SVG文件列表",
						JOptionPane.INFORMATION_MESSAGE, null, names, names[0]);
				filename = (String) selectedValue;
				String uri = this.getCodeBase() + "/" + filename.trim();
				canvas.setURI(uri);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		svgDocument = canvas.getSVGDocument();
	}

	private void openFile() {

		try {
			System.out.println("--------------testOpenFile-----------");
			// String url="http://127.0.0.1:8090/upload.action";
			// HttpURLConnection hurlcon = (HttpURLConnection)new
			// URL(url).openConnection();
			// hurlcon.setDoInput(true);
			// hurlcon.setDoOutput(true);
			// hurlcon.setRequestProperty("Content-Type",
			// "application/x-java-serialized-object");

			// URL url = new URL("qrystat/reportTypeSvgAction.action");
			URL url = new URL("http://127.0.0.1:8090/strutsProject/upload.action");
			URLConnection urlConn = url.openConnection();
			urlConn.setUseCaches(false);
			urlConn.setDefaultUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-Type","application/x-java-serialized-object");
			urlConn.setRequestProperty("Cache-Control", "no-cache");
			InputStream in = urlConn.getInputStream();
			ObjectInputStream objStream = new ObjectInputStream(in);
			person p = (person) objStream.readObject();
			objStream.close();
			System.out.println(p.getUsername());
//			svgDocument = (SVGDocument) objStream.readObject();
//			objStream.close();
//			canvas.setSVGDocument(svgDocument);
		} catch (RuntimeException e) {

			e.printStackTrace();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// //InputStream is = (InputStream) urlConn.getInputStream();
		// org.w3c.dom.Document d = null;
		// System.out.println("-----------------------------------------");
		// try {
		// String parser = XMLResourceDescriptor.getXMLParserClassName();
		// SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		// String uri = "";
		// d = f.createDocument(uri, is);
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }

		// URLConnection urlconn = url.openConnection();
		// urlconn.setUseCaches(false);
		// urlconn.setDefaultUseCaches(false);
		// InputStream is = (InputStream) urlconn.getInputStream();
		// org.w3c.dom.Document d = null;
		// try {
		// String parser = XMLResourceDescriptor.getXMLParserClassName();
		// SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		// String uri = "";
		// d = f.createDocument(uri, is);
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }
		// ObjectInputStream objInputStream = new ObjectInputStream(is);
		// svgDocument = (SVGDocument) objInputStream.readObject();
		// objInputStream.close();

		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// }

	}

}
