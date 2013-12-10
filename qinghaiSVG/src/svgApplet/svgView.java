package svgApplet;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


import org.apache.batik.swing.JSVGCanvas;

public class svgView extends Applet implements ActionListener {

	private JSVGCanvas canvas = new JSVGCanvas();

	private JButton btnOpenFile;
	private JButton zoomout, zoomin;
	private JPanel paneButton = new JPanel();
	// private JPanel panel = new JPanel();
	private JScrollPane panePicture = new JScrollPane(canvas,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	private JFileChooser fcOpenFile = new JFileChooser();
	private String theOpenedFileURI;
	private String filename = null;

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

	}

	private void openFile() {

		try {
			System.out.println("--------------test-----------");
			//URL url = new URL(this.getCodeBase(), "testMysql.jsp");
			URL url = new URL(getCodeBase(), "svgServlet/showSvgServlet.class");
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
				String uri = this.getCodeBase() + "/" +filename.trim();
				canvas.setURI(uri);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// int result = JOptionPane.showOptionDialog(this, "请选择文件名", "文件列表", 0,
		// 0, null, names , 0);
		// String uri = this.getCodeBase() + "/" +Integer.toString(result) +
		// ".svg";
		// String uri = this.getCodeBase() + "/" +filename + ".svg";
		// JOptionPane.showMessageDialog(this, uri);
		// canvas.setURI(uri);

	}

}
