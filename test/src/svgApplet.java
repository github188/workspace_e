import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JViewport;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.batik.apps.svgbrowser.DOMViewer;
import org.apache.batik.apps.svgbrowser.DOMViewerController;
import org.apache.batik.apps.svgbrowser.ElementOverlayManager;
import org.apache.batik.apps.svgbrowser.StatusBar;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.Overlay;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.svg.SVGDocument;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class svgApplet extends Applet implements ActionListener {
	static final long serialVersionUID = 222222;

	private JSVGCanvas canvas = new JSVGCanvas();

	private JButton btnOpenFile, btnSaveFile;
	private JButton zoomout, zoomin;
	private JButton domView;
	private JPanel paneButton = new JPanel();
	private JPanel panel = new JPanel();
    private Icon zoomInIcon ;
	private Icon zoomOutIcon;
	private Icon selectIcon,domViewIcon;
	private JScrollPane panePicture = new JScrollPane(canvas,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	private int mouseDownX, mouseDownY;
	private int mouseMoveX, mouseMoveY;

	private String theOpenedFileURI;
	private JFileChooser fcOpenFile = new JFileChooser();
	private double scalezoomout = 0.9;
	private double scalezoomin = 1.1;
	private boolean dragged = false;
	private Cursor handCursor, defaultCursor, sourceCursor;
	private Cursor zoomOutCursor, zoomInCursor;
	private int cursorState = 0;
	private Image handimage = null, zoomInImage, zoomOutImage, selectImage,domViewImage;
	JViewport viewPort = new JViewport();
	protected StatusBar statusBar;
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private InputStream in = null;
	private static org.w3c.dom.Document d = null;
	private static String filename = null;
	

	boolean showWhitespace = true;
	Panel domPanel = new Panel(); 
	DOMViewer2 domviewer = null;
	public static Document getDocument()
	{
		return d;
	}
	public static String getSvgOptionName(){
		return filename;
	}
	public void init() {

		try {
			zoomInImage = getAppletContext().getImage(
					new URL(getCodeBase(), "ZoomIn.gif"));
			zoomOutImage = getAppletContext().getImage(
					new URL(getCodeBase(), "ZoomOut.gif"));
			selectImage = getAppletContext().getImage(
					new URL(getCodeBase(), "query.gif"));
			domViewImage = getAppletContext().getImage(
					new URL(getCodeBase(), "dom-viewer.png"));
		} catch (MalformedURLException ex) {
			System.err.println(ex.getLocalizedMessage());
		}
		selectIcon = new ImageIcon(selectImage);
		zoomInIcon = new ImageIcon(zoomInImage);
		zoomOutIcon = new ImageIcon(zoomOutImage);
		domViewIcon=new ImageIcon(domViewImage);
		// zoomout = new JButton("zoon out");
		// zoomin = new JButton("zoom in");
		// btnOpenFile = new JButton("select File");
		zoomout = new JButton(zoomOutIcon);
		zoomin = new JButton(zoomInIcon);
		btnOpenFile = new JButton(selectIcon);
		btnSaveFile = new JButton("Save File");
		//domView = new JButton("Dom View");
		domView = new JButton(domViewIcon);
		paneButton.add(btnOpenFile);
		paneButton.add(btnSaveFile);
		paneButton.add(zoomin);
		paneButton.add(zoomout);

		paneButton.add(domView);

		this.setLayout(new BorderLayout());
		paneButton.setBackground(Color.CYAN);
		this.add("North", paneButton);

		zoomout.setBackground(paneButton.getBackground());
		zoomin.setBackground(paneButton.getBackground());		
		btnOpenFile.setBackground(paneButton.getBackground());
		btnSaveFile.setBackground(paneButton.getBackground());
		domView.setBackground(paneButton.getBackground());
		btnOpenFile.setBorderPainted(false);
		btnSaveFile.setBorderPainted(false);
		zoomout.setBorderPainted(false);
		zoomin.setBorderPainted(false);
		domView.setBorderPainted(false);
		
		canvas.setPreferredSize(new Dimension(200, 200));
		canvas.revalidate();
		canvas.setDoubleBuffered(true);
		this.add("Center", panePicture);
		this.setVisible(true);
		
		
		viewPort = panePicture.getViewport();
		viewPort.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		// System.out.println(viewPort.getScrollMode());
		// panePicture.setBackground(Color.black);
		defaultCursor = canvas.getCursor();
	
		try {
			handimage = getAppletContext().getImage(
					new URL(getCodeBase(), "handCursor.gif"));
		} catch (MalformedURLException ex) {

			System.err.println(ex.getLocalizedMessage());
		}
	
		Point handCursorPoint = new Point(0, 0);
		handCursor = Toolkit.getDefaultToolkit().createCustomCursor(handimage,
				handCursorPoint, "HandCursor");

		zoomInCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				zoomInImage, handCursorPoint, "HandCursor");

		zoomOutCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				zoomOutImage, handCursorPoint, "HandCursor");
	}

	public void start() {

		canvas.addMouseMotionListener(new MovingAdapter());

		btnOpenFile.addActionListener(this);
		btnSaveFile.addActionListener(this);
		zoomin.addActionListener(this);
		zoomout.addActionListener(this);
		domView.addActionListener(this);
	
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
		btnSaveFile.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnSaveFile.setBackground(Color.pink);
			}

			public void mouseExited(MouseEvent e) {
				btnSaveFile.setBackground(paneButton.getBackground());
			}

		});
		domView.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				domView.setBackground(Color.pink);
			}

			public void mouseExited(MouseEvent e) {
				domView.setBackground(paneButton.getBackground());
			}

		});
		
		canvas.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				mouseDownX = e.getX();
				mouseDownY = e.getY();
				canvas.setCursor(handCursor);
				
			}

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

				mouseMoveX = e.getX();
				mouseMoveY = e.getY();
				AffineTransform tr = canvas.getRenderingTransform();
				tr.translate((double) (mouseMoveX - mouseDownX),
						(double) (mouseMoveY - mouseDownY));
				
				if (tr == null) {
					tr = new AffineTransform();
				}

				canvas.setRenderingTransform(tr);
				
			}
		});
	}

	class MovingAdapter extends MouseAdapter {


	
		public void mouseDragged(MouseEvent e) {
			canvas.setCursor(handCursor);
			if (!dragged) {
				
				mouseDownX = e.getX();
				mouseDownY = e.getY();
				dragged = true;
			} else {
				mouseMoveX = e.getX();
				mouseMoveY = e.getY();
				AffineTransform tr = canvas.getRenderingTransform();
				tr.translate((double) (mouseMoveX - mouseDownX),
						(double) (mouseMoveY - mouseDownY));
				canvas.setRenderingTransform(tr);
				mouseDownX = mouseMoveX;
				mouseDownY = mouseMoveY;

			}
		}
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == btnOpenFile) {
			openFile();
		}

		else if (source == btnSaveFile) {
			saveFile();
		}

		else if (source == zoomout) {

			zoomOut();
		}

		else if (source == zoomin) {
			zoomIn();
		} else if (source == domView) {
			openDOMViewer();

		}
	}

	

	protected class JSVGViewerDOMViewerController
			implements DOMViewerController {

		public boolean canEdit() {
		//	return true;
			return canvas.getUpdateManager()!=null;
		}

		public ElementOverlayManager createSelectionManager() {
			if (canEdit()) {
				return new ElementOverlayManager(canvas);
			}
			return null;
		}

		public org.w3c.dom.Document getDocument() {
			return d;
		}

		public void performUpdate(Runnable r) {
			if (canEdit()) {
				canvas.getUpdateManager().getUpdateRunnableQueue().invokeLater(
						r);
			} else {
				r.run();
			}
		}

		public void removeSelectionOverlay(Overlay selectionOverlay) {
			canvas.getOverlays().remove(selectionOverlay);
		}

		public void selectNode(Node node) {

			
			openDOMViewer();		
			domviewer.selectNode(node);
		}

	}

	private void openDOMViewer() {
		if (domviewer == null || domviewer.isDisplayable()) {
			domviewer = new DOMViewer2(new JSVGViewerDOMViewerController());
			//NodePickerPanel2.setSvgdocument((SVGDocument) d);
			Rectangle fr = getBounds();
			Dimension td = domviewer.getSize();
			domviewer.setLocation(fr.x + (fr.width - td.width) / 2, fr.y
					+ (fr.height - td.height) / 2);
		}
		domviewer.setVisible(true);

	}



	private void zoomOut() {

		AffineTransform dr = canvas.getRenderingTransform();
		dr.translate((double) (0.5 * canvas.getWidth() * (1.0 - scalezoomout)),(double) (0.5 * canvas.getHeight() * (1.0 - scalezoomout)));
		dr.scale(scalezoomout, scalezoomout);
		canvas.setRenderingTransform(dr);
	}

	private void zoomIn() {

		AffineTransform tr = canvas.getRenderingTransform();
		if (tr == null) {
			tr = new AffineTransform();
		}
		tr.translate((double) (0.5 * canvas.getWidth() * (1.0 - scalezoomin)),(double) (
				0.5 * canvas.getHeight() * (1.0 - scalezoomin)));
		tr.scale(scalezoomin, scalezoomin);
		canvas.setRenderingTransform(tr);
	}

	private void saveFile() {

		String str = ".svg" + "" + "," + ".svgz";
		FileFilter filter = new FileNameExtensionFilter(str, "svg");
		fcOpenFile.addChoosableFileFilter(filter);

		int returnVal = fcOpenFile.showSaveDialog(this);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fcOpenFile.getSelectedFile();
				OutputStream ostream = new FileOutputStream(file.getPath());
				Transcoder t = new PNGTranscoder();
				TranscoderInput input = new TranscoderInput(d.toString());
				TranscoderOutput output = new TranscoderOutput(ostream);
				t.transcode(input, output);
				ostream.flush();
				ostream.close();
			}
		} catch (TranscoderException te) {
			System.err.println("Transcoder problem: " + te.toString());
		} catch (IOException ioe) {
			System.err.print(ioe.toString());
		}
	}

	private void openFile() {
		
		
			try {
			
				conn = (Connection) JdbcUtils.getConnection();
				// conn = JdbcUtilsSing.getInstance().getConnection();
				
				
				PreparedStatement ps = null;

			
				String s="select svgfile from svgfiles where svgname=?";
				ps = (PreparedStatement) conn.prepareStatement(s);
		
				ps.setString(1, "anhui.svg");
				rs = ps.executeQuery();

				
				if (rs.next()) {
					in = rs.getBinaryStream("svgfile");

				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JdbcUtils.free(rs, st, conn);
			}
		
		
		try {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			String uri = "";
			d = f.createDocument(uri, in);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setSVGDocument((SVGDocument) d);
		canvas.setCursor(defaultCursor);
	}

}
