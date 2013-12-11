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

import org.apache.batik.apps.svgbrowser.StatusBar;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class svgApplet3 extends Applet implements ActionListener {
	static final long serialVersionUID = 222222;

	private JSVGCanvas canvas = new JSVGCanvas();
	// 定义按钮
	private JButton btnOpenFile, btnSaveFile;
	private JButton zoomout, zoomin;
	private JPanel paneButton = new JPanel();
	private JPanel panel = new JPanel();
	// private Icon zoomInIcon = new ImageIcon("ZoomIn.gif");
	// private Icon zoomOutIcon = new ImageIcon("ZoomOut.gif");
	private Icon zoomInIcon, zoomOutIcon, selectIcon;
	private JScrollPane panePicture = new JScrollPane(canvas,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	// 定义鼠标位置
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
	private Image handimage = null, zoomInImage, zoomOutImage, selectImage;
	JViewport viewPort = new JViewport();
	protected StatusBar statusBar;
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private InputStream in = null;
	private org.w3c.dom.Document d = null;
	private String filename = null;

	public void init() {

		// 创建按钮
		try {
			zoomInImage = getAppletContext().getImage(
					new URL(getCodeBase(), "ZoomIn.gif"));
			zoomOutImage = getAppletContext().getImage(
					new URL(getCodeBase(), "ZoomOut.gif"));
			selectImage = getAppletContext().getImage(
					new URL(getCodeBase(), "query.gif"));

		} catch (MalformedURLException ex) {
			System.err.println(ex.getLocalizedMessage());
		}
		selectIcon = new ImageIcon(selectImage);
		zoomInIcon = new ImageIcon(zoomInImage);
		zoomOutIcon = new ImageIcon(zoomOutImage);
		zoomout = new JButton(zoomOutIcon);
		zoomin = new JButton(zoomInIcon);
		btnOpenFile = new JButton("select File");
		btnSaveFile = new JButton("Save File");

		// 按钮添加到Panel
		paneButton.add(btnOpenFile);
		paneButton.add(btnSaveFile);
		paneButton.add(zoomin);
		paneButton.add(zoomout);

		// 设置panel的属性：布局，背景颜色
		this.setLayout(new BorderLayout());
		paneButton.setBackground(Color.CYAN);
		this.add("North", paneButton);

		// 设置按钮的外观
		zoomout.setBackground(paneButton.getBackground());
		zoomin.setBackground(paneButton.getBackground());
		zoomout.setBorderPainted(false);
		zoomin.setBorderPainted(false);
		btnOpenFile.setBackground(paneButton.getBackground());
		btnSaveFile.setBackground(paneButton.getBackground());
		btnOpenFile.setBorderPainted(false);
		btnSaveFile.setBorderPainted(false);
		// canvas初始化设置
		canvas.setPreferredSize(new Dimension(200, 200));
		canvas.revalidate();
		// canvas.setBackground(Color.orange);
		canvas.setDoubleBuffered(true);
		this.add("Center", panePicture);
		// this.add(statusBar = new StatusBar(), BorderLayout.SOUTH);
		this.setVisible(true);

		// 设置滚动窗口视口缓冲模式
		viewPort = panePicture.getViewport();
		viewPort.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		// System.out.println(viewPort.getScrollMode());
		// panePicture.setBackground(Color.black);

		// 得到默认的光标形状
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
		// 设置放大图标
		// handCursorPoint = new Point(0, 0);
		zoomInCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				zoomInImage, handCursorPoint, "HandCursor");
		// 设置缩小图标
		// handCursorPoint = new Point(0, 0);
		zoomOutCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				zoomOutImage, handCursorPoint, "HandCursor");
	}

	public void start() {

		// 鼠标响应
		canvas.addMouseMotionListener(new MovingAdapter());
		// 添加按钮监听
		btnOpenFile.addActionListener(this);
		btnSaveFile.addActionListener(this);
		zoomin.addActionListener(this);
		zoomout.addActionListener(this);

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

		// 鼠标监听canvas
		canvas.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				mouseDownX = e.getX();
				mouseDownY = e.getY();
				// 自定义光标，按压鼠标，如果不是变大或者缩小，改变为手状
				if ((cursorState != 1) && (cursorState != 2))
				// if(handCursor==null)
				{

					canvas.setCursor(handCursor);
				} else {
					sourceCursor = canvas.getCursor();
				}
			}

			// 鼠标单击
			public void mouseClicked(MouseEvent e) {

				if (cursorState == 0) {
					canvas.setCursor(defaultCursor);
				} else if (cursorState == 1) {
					AffineTransform tr = canvas.getRenderingTransform();
					if (tr == null) {
						tr = new AffineTransform();
					}
					tr
							.translate(
									(double) (0.5 * canvas.getWidth() * (1.0 - scalezoomin)),
									(double) (0.5 * canvas.getHeight() * (1.0 - scalezoomin)));
					tr.scale(scalezoomin, scalezoomin);
					canvas.setRenderingTransform(tr);
				} else if (cursorState == 2) {
					AffineTransform dr = canvas.getRenderingTransform();
					dr
							.translate(
									(double) (0.5 * canvas.getWidth() * (1.0 - scalezoomout)),
									(double) (0.5 * canvas.getHeight() * (1.0 - scalezoomout)));
					dr.scale(scalezoomout, scalezoomout);
					canvas.setRenderingTransform(dr);
					// cursorState=0;
				}
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
				if ((cursorState != 1) && (cursorState != 2)) {
					canvas.setCursor(defaultCursor);
				} else {
					canvas.setCursor(sourceCursor);
				}
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

	// 按钮响应
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		// 打开文件
		if (source == btnOpenFile) {
			openFile();
		}
		// 保存文件
		else if (source == btnSaveFile) {
			saveFile();
		}
		// 缩小
		else if (source == zoomout) {

			zoomOut();
		}
		// 放大
		else if (source == zoomin) {
			zoomIn();
		}
	}

	// 图形放大
	private void zoomOut() {
		// System.out.println("zoomin");
		cursorState = 2;
		canvas.setCursor(zoomOutCursor);

		/*
		 * AffineTransform dr=canvas.getRenderingTransform(); //if(dragged) //{
		 * // dr.translate(-dr.getTranslateX(), -dr.getTranslateY());
		 * //canvas.setRenderingTransform(tr); //} //if(dr==null){ //dr=new
		 * AffineTransform(); //} dr.translate((double)(-0.5*canvas.getWidth(
		 * )*(1.0-scalezoomout)),(
		 * double)(-0.5*canvas.getHeight()*(1.0-scalezoomout)));
		 * dr.scale(scalezoomin, scalezoomin); canvas.setRenderingTransform(dr);
		 */
	}

	// 图形缩小
	private void zoomIn() {

		cursorState = 1;
		// if(handCursor==null)

		canvas.setCursor(zoomInCursor);
		/*
		 * AffineTransform tr=canvas.getRenderingTransform(); if(tr==null){
		 * tr=new AffineTransform(); }
		 * 
		 * tr.translate((double)(0.5*canvas.getWidth()*(1.0-scalezoomout)),(
		 * double)(0.5*canvas.getHeight()*(1.0-scalezoomout)));
		 * 
		 * tr.scale(scalezoomout, scalezoomout);
		 * canvas.setRenderingTransform(tr);
		 */
	}

	private void saveFile() {
		// 过滤文件类型
		String str = ".svg" + "" + "," + ".svgz";
		FileFilter filter = new FileNameExtensionFilter(str, "svg");
		fcOpenFile.addChoosableFileFilter(filter);

		int returnVal = fcOpenFile.showSaveDialog(this);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fcOpenFile.getSelectedFile();
				OutputStream ostream = new FileOutputStream(file.getPath());

				Transcoder t = new PNGTranscoder();

				// Creating input and output objects
				// Tr TranscoderInput input = new
				// TranscoderInput(theOpenedFileURI);
				TranscoderInput input = new TranscoderInput(d.toString());
				TranscoderOutput output = new TranscoderOutput(ostream);
				// Transcoding
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

		/*
		 * int returnVal = fcOpenFile.showOpenDialog(this);
		 * 
		 * try { if (returnVal == JFileChooser.APPROVE_OPTION) { File file =
		 * fcOpenFile.getSelectedFile();
		 * 
		 * //theOpenedFileURI = file.toURL().toString();
		 * theOpenedFileURI=file.toURI().toURL().toString();
		 * canvas.setURI(theOpenedFileURI);
		 * 
		 * } } catch (IOException ioe) { System.err.print(ioe.toString()); }
		 * canvas.setCursor(defaultCursor);
		 */

		// 从数据库获取所有svg文件列表
		// String filename=null;
		/*
		 * try { URL url = new URL(this.getCodeBase(), "/getFileName.jsp");
		 * URLConnection urlconn = url.openConnection(); int length =
		 * urlconn.getContentLength(); DataInputStream dataInputStream = new
		 * DataInputStream(urlconn.getInputStream()); byte buffer[] = new
		 * byte[length];
		 * 
		 * dataInputStream.readFully(buffer, 0, length); String str = new
		 * String(buffer); String[] names = str.split(";"); if (null != names) {
		 * // int result = JOptionPane.showOptionDialog(this, "请选择文件名", "文件列表",
		 * 0, 0, null, names , 0); // String uri = this.getCodeBase() + "/"
		 * +Integer.toString(result) + ".svg"; // String uri =
		 * this.getCodeBase() + "/" +filename + ".svg"; //
		 * JOptionPane.showMessageDialog(this, uri); //
		 * JOptionPane.showInputDialog(this,filename); // canvas.setURI(uri);
		 * Object selectedValue =
		 * JOptionPane.showInputDialog(null,"Please Choose one file",
		 * "SVG文件列表",JOptionPane.INFORMATION_MESSAGE, selectIcon,names,
		 * names[0]); filename = (String) selectedValue; }
		 * 
		 * } catch (MalformedURLException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		// Connection conn = null;
		// Statement st = null;
		// ResultSet rs = null;
		// InputStream in=null;
		// org.w3c.dom.Document d =null;
		//		
		try {
			// 建立连接
			conn = (Connection) JdbcUtils.getConnection();
			System.out.println(conn);
			// conn = JdbcUtilsSing.getInstance().getConnection();

			// 创建语句
			PreparedStatement ps = null;

			// 执行语句
			String s = "select svgfile from svg_file_names where svgname=?";
			ps = (PreparedStatement) conn.prepareStatement(s);
			System.out.println(ps);
			ps.setString(1, "anhui");
			rs = ps.executeQuery();
			System.out.println(rs);
			// 处理结果
			if (rs.next()) {
				System.out.println("jinlai");
				// Blob blob = rs.getBlob(1);
				// InputStream in = blob.getBinaryStream();
				in = rs.getBinaryStream("svgfile");
				//
				// /* 创建文件，把读出的存入文件
				// * File file = new File("IMG_0002_bak.jpg"); // OutputStream
				// out
				// * = new BufferedOutputStream( // new FileOutputStream(file));
				// * // byte[] buff = new byte[1024]; // for (int i = 0; (i =
				// * in.read(buff)) > 0;) { // out.write(buff, 0, i); // } //
				// * out.close();
				// */
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

		// SVGDocument文件解析
		try {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			String uri = "";
			d = f.createDocument(uri, in);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		canvas.setSVGDocument((SVGDocument) d);
		canvas.setCursor(defaultCursor);
	}
}
