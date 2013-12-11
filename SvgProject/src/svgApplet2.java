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

public class svgApplet2 extends Applet implements ActionListener {
	static final long serialVersionUID = 222222;

	private JSVGCanvas canvas = new JSVGCanvas();
	// 定义按钮
	private JButton btnOpenFile, btnSaveFile;
	private JButton zoomout, zoomin;
	private JButton domView;
	private JPanel paneButton = new JPanel();
	private JPanel panel = new JPanel();
	// private Icon zoomInIcon = new ImageIcon("ZoomIn.gif");
	// private Icon zoomOutIcon = new ImageIcon("ZoomOut.gif");
	private Icon zoomInIcon, zoomOutIcon, selectIcon,domViewIcon;
	private JScrollPane panePicture = new JScrollPane(canvas,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 定义鼠标的位置
	private int mouseDownX, mouseDownY;
	private int mouseMoveX, mouseMoveY;
	// 定义打开文件，缩放级别，光标等变量
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
	
	// 定义domviewer显示
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
		
		// 设置滚动窗口视口缓冲模式
		viewPort = panePicture.getViewport();
		viewPort.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		// System.out.println(viewPort.getScrollMode());
		// panePicture.setBackground(Color.black);
		defaultCursor = canvas.getCursor();
		// 设置手状图标
		try {
			handimage = getAppletContext().getImage(
					new URL(getCodeBase(), "handCursor.gif"));
		} catch (MalformedURLException ex) {

			System.err.println(ex.getLocalizedMessage());
		}
		// 自定义光标显示
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
		// 添加按钮监听响应
		btnOpenFile.addActionListener(this);
		btnSaveFile.addActionListener(this);
		zoomin.addActionListener(this);
		zoomout.addActionListener(this);
		domView.addActionListener(this);
		// 光标进入和离开按钮监听
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
		// canvas监听
		canvas.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				mouseDownX = e.getX();
				mouseDownY = e.getY();
				canvas.setCursor(handCursor);
				// 自定义光标，按压鼠标，如果不是变大或者缩小，改变为手状
				/*
				 * if ((cursorState != 1) && (cursorState != 2)) //
				 * if(handCursor==null) {
				 * 
				 * canvas.setCursor(handCursor); } else { sourceCursor =
				 * canvas.getCursor(); }
				 */
			}

			public void mouseClicked(MouseEvent e) {

			
				/*
				 * if (cursorState == 0) { canvas.setCursor(defaultCursor); }
				 * else if (cursorState == 1) { AffineTransform tr =
				 * canvas.getRenderingTransform(); if (tr == null) { tr = new
				 * AffineTransform(); } tr.translate((double) (0.5 *
				 * canvas.getWidth() * (1.0 - scalezoomin)),(double) ( 0.5 *
				 * canvas.getHeight() * (1.0 - scalezoomin)));
				 * tr.scale(scalezoomin, scalezoomin);
				 * canvas.setRenderingTransform(tr); } else if (cursorState ==
				 * 2) { AffineTransform dr = canvas.getRenderingTransform();
				 * dr.translate((double) (0.5 * canvas.getWidth() * (1.0 -
				 * scalezoomout)),(double) (0.5 * canvas.getHeight() * (1.0 -
				 * scalezoomout))); dr.scale(scalezoomout, scalezoomout);
				 * canvas.setRenderingTransform(dr); // cursorState=0; }
				 */
			}

			public void mouseReleased(MouseEvent e) {

				mouseMoveX = e.getX();
				mouseMoveY = e.getY();
				AffineTransform tr = canvas.getRenderingTransform();
				tr.translate((double) (mouseMoveX - mouseDownX),
						(double) (mouseMoveY - mouseDownY));
				// tr.translate((double)(mouseMoveX-x),(double)(mouseMoveY-y));
				if (tr == null) {
					tr = new AffineTransform();
				}

				canvas.setRenderingTransform(tr);
				// 释放鼠标，形状改变为原来的形状
				/*
				 * if ((cursorState != 1) && (cursorState != 2)) {
				 * canvas.setCursor(defaultCursor); } else {
				 * canvas.setCursor(sourceCursor); }
				 */	
				canvas.setCursor(defaultCursor);
			}
		});
	}

	class MovingAdapter extends MouseAdapter {


		// 鼠标拖拽响应
		public void mouseDragged(MouseEvent e) {
			canvas.setCursor(handCursor);
			if (!dragged) {
				// System.out.println("Dragge");
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
	// extends CanvasDOMViewerController {
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

	//	cursorState = 2;
	//	canvas.setCursor(zoomOutCursor);
		AffineTransform dr = canvas.getRenderingTransform();
		dr.translate((double) (0.5 * canvas.getWidth() * (1.0 - scalezoomout)),(double) (0.5 * canvas.getHeight() * (1.0 - scalezoomout)));
		dr.scale(scalezoomout, scalezoomout);
		canvas.setRenderingTransform(dr);
	}

	private void zoomIn() {

		// cursorState = 1;
		// canvas.setCursor(zoomInCursor);
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
		
//直接从数据库中读取某个svg文件
//		try {
//			conn = (Connection) JdbcUtils.getConnection();
//			PreparedStatement ps = null;
//			String s = "select svgfile from svg_file_name where svgname=?";
//			ps = (PreparedStatement) conn.prepareStatement(s);
//			ps.setString(1, "2202.svg");
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				in = rs.getBinaryStream("svgfile");
//			}
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//			JdbcUtils.free(rs, st, conn);
//		}
		
		//从数据库获取所有svg文件列表
		//	String filename=null;
			try {
				URL url = new URL(this.getCodeBase(), "/getFileName.jsp");
				URLConnection urlconn = url.openConnection();
				int length = urlconn.getContentLength();			
				DataInputStream dataInputStream = new DataInputStream(urlconn.getInputStream());
				byte buffer[] = new byte[length];				
				dataInputStream.readFully(buffer, 0, length);			
				String str = new String(buffer);
				String[] names = str.split(";");
				if (null != names) {
				    // int result = JOptionPane.showOptionDialog(this, "请选择文件名", "文件列表", 0, 0, null, names , 0);				
					// String uri = this.getCodeBase() + "/" +Integer.toString(result) + ".svg";
					// String uri = this.getCodeBase() + "/" +filename + ".svg";
					// JOptionPane.showMessageDialog(this, uri);
					// JOptionPane.showInputDialog(this,filename);
					// canvas.setURI(uri);	
					Object selectedValue = JOptionPane.showInputDialog(null,"Please Choose one file", "SVG文件列表",JOptionPane.INFORMATION_MESSAGE, selectIcon,names, names[0]);
					filename = (String) selectedValue;
					filename=filename.trim();
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			try {
				// 建立连接
				conn = (Connection) JdbcUtils.getConnection();
				// conn = JdbcUtilsSing.getInstance().getConnection();
				
				// 创建语句
				PreparedStatement ps = null;

				// 执行语句
				String s="select svgfile from svg_file_name where svgname=?";
				ps = (PreparedStatement) conn.prepareStatement(s);
				//ps.setString(1, filename.trim());
				ps.setString(1, filename);
				rs = ps.executeQuery();

				// 处理结果
				if (rs.next()) {
					// Blob blob = rs.getBlob(1);
					// InputStream in = blob.getBinaryStream();
					in = rs.getBinaryStream("svgfile");

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
		
		//SVGDocument文件解析
		try {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			String uri = "";
			d = f.createDocument(uri, in);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//设置文档状态，以打开静态的图形
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setSVGDocument((SVGDocument) d);
		canvas.setCursor(defaultCursor);
	}

}
