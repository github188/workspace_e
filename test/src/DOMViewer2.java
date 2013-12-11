import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.batik.apps.svgbrowser.AbstractCompoundCommand;
import org.apache.batik.apps.svgbrowser.DOMDocumentTree;
import org.apache.batik.apps.svgbrowser.DOMDocumentTreeController;
import org.apache.batik.apps.svgbrowser.DOMViewerController;
import org.apache.batik.apps.svgbrowser.ElementOverlayController;
import org.apache.batik.apps.svgbrowser.ElementOverlayManager;
import org.apache.batik.apps.svgbrowser.HistoryBrowser;
import org.apache.batik.apps.svgbrowser.HistoryBrowserInterface;
import org.apache.batik.apps.svgbrowser.NodePickerController;
import org.apache.batik.apps.svgbrowser.NodeTemplates;
import org.apache.batik.apps.svgbrowser.DOMDocumentTree.DOMDocumentTreeAdapter;
import org.apache.batik.apps.svgbrowser.DOMDocumentTree.DOMDocumentTreeEvent;
import org.apache.batik.apps.svgbrowser.DOMDocumentTree.DropCompletedInfo;
import org.apache.batik.apps.svgbrowser.DropDownHistoryModel.RedoPopUpMenuModel;
import org.apache.batik.apps.svgbrowser.DropDownHistoryModel.UndoPopUpMenuModel;
import org.apache.batik.apps.svgbrowser.HistoryBrowser.DocumentCommandController;

import org.apache.batik.apps.svgbrowser.NodeTemplates.NodeTemplateDescriptor;
import org.apache.batik.bridge.svg12.ContentManager;
import org.apache.batik.bridge.svg12.DefaultXBLManager;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.dom.xbl.NodeXBL;
import org.apache.batik.dom.xbl.XBLManager;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.util.XMLConstants;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.util.gui.DropDownComponent;
import org.apache.batik.util.gui.resource.ActionMap;
import org.apache.batik.util.gui.resource.ButtonFactory;
import org.apache.batik.util.gui.resource.MissingListenerException;
import org.apache.batik.util.resources.ResourceManager;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.svg.SVGDocument;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DOMViewer2 extends JFrame implements ActionMap {

	/**
	 * The resource file name
	 */
	protected static final String RESOURCE = "org.apache.batik.apps.svgbrowser.resources.DOMViewerMessages";

	/**
	 * The resource bundle
	 */
	protected static ResourceBundle bundle;

	/**
	 * The resource manager
	 */
	protected static ResourceManager resources;

	static {
		bundle = ResourceBundle.getBundle(RESOURCE, Locale.getDefault());
		resources = new ResourceManager(bundle);
	}

	/**
	 * The map that contains the listeners
	 */
	protected Map listeners = new HashMap();

	/**
	 * The button factory.
	 */
	protected ButtonFactory buttonFactory;

	/**
	 * The panel.
	 */
	protected Panel panel;

	/**
	 * Whether to show text nodes that contain only whitespace in the tree.
	 */
	protected boolean showWhitespace = true;

	/**
	 * Whether "capturing click" tool is enabled. If enabled, the element being
	 * clicked on is found and selected in the DOMViewer's document tree
	 */
	protected boolean isCapturingClickEnabled;

	/**
	 * The DOMViewer controller.
	 */
	protected DOMViewerController domViewerController;

	/**
	 * Manages the element selection on the canvas.
	 */
	protected ElementOverlayManager elementOverlayManager;

	/**
	 * Whether painting the overlay on the canvas is enabled.
	 */
	protected boolean isElementOverlayEnabled;

	/**
	 * The history browsing manager. Manages undo / redo.
	 */
	protected HistoryBrowserInterface historyBrowserInterface;

	/**
	 * Whether the DOMViewer can be used for editing the document.
	 */
	protected boolean canEdit = true;

	/**
	 * The button for enabling and disabling the overlay.
	 */
	protected JToggleButton overlayButton;

	// ---------------改写数据库-------------
	private OutputStreamWriter writer = null;
	private ByteArrayOutputStream bos = null;
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private InputStream is = null;
	private byte[] bytes;
	private static String EOL;
	static {
		try {
			EOL = System.getProperty("line.separator", "\n");
		} catch (SecurityException e) {
			EOL = "\n";
		}
	}

	/**
	 * Creates a new DOMViewer panel.
	 */
	public DOMViewer2(DOMViewerController controller) {
		
		super(resources.getString("Frame.title"));
		setSize(resources.getInteger("Frame.width"), resources
				.getInteger("Frame.height"));
		System.out.println("start");
		domViewerController = controller;

		elementOverlayManager = domViewerController.createSelectionManager();
		if (elementOverlayManager != null) {
			elementOverlayManager
					.setController(new DOMViewerElementOverlayController());
		}
		historyBrowserInterface = new HistoryBrowserInterface(
				new DocumentCommandController(controller));

		listeners.put("CloseButtonAction", new CloseButtonAction());
		listeners.put("UndoButtonAction", new UndoButtonAction());
		listeners.put("RedoButtonAction", new RedoButtonAction());
		listeners.put("CapturingClickButtonAction",
				new CapturingClickButtonAction());
		listeners.put("OverlayButtonAction", new OverlayButtonAction());

		// Create the main panel
		panel = new Panel();
		// 这是一个内部类

		getContentPane().add(panel);

		JPanel p = new JPanel(new BorderLayout());

		// 确认是否显示文本节点按钮
		JCheckBox cb = new JCheckBox(resources
				.getString("ShowWhitespaceCheckbox.text"));
		cb.setSelected(showWhitespace);
		// 事件的监听
		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				setShowWhitespace(ie.getStateChange() == ItemEvent.SELECTED);
			}
		});

		p.add(cb, BorderLayout.WEST);
		p.add(getButtonFactory().createJButton("CloseButton"),
				BorderLayout.EAST);
		getContentPane().add(p, BorderLayout.SOUTH);

		// Set the document
		Document document = domViewerController.getDocument();
		if (document != null) {
			// panel.setDocument(document, (ViewCSS) document
			// .getDocumentElement());
			panel.setDocument(document, null);
		}
	//	System.out.println("s_DOMViewer2");

	}

	/**
	 * Sets whether to show text nodes that contain only whitespace in the tree.
	 */
	public void setShowWhitespace(boolean state) {
		showWhitespace = state;
		if (panel.document != null)
			panel.setDocument(panel.document);
	//	System.out.println("s_setShowWhitespace");
	}

	/**
	 * Sets the document to display.
	 */
	public void setDocument(Document doc) {
		panel.setDocument(doc);
	//	System.out.println("s_setDocument");
	}

	/**
	 * Sets the document to display and its ViewCSS.
	 */
	public void setDocument(Document doc, ViewCSS view) {
		panel.setDocument(doc, view);
	//	System.out.println("s_setDocument2");
	}

	/**
	 * Whether the document can be edited using the DOMViewer.
	 * 
	 * @return True if the document can be edited throught the DOMViewer
	 */
	public boolean canEdit() {
	//	System.out.println("s_canEdit");
		return domViewerController.canEdit() && canEdit;

	}

	/**
	 * Enables / disables the DOMViewer to be used to edit the documents.
	 * 
	 * @param canEdit
	 *            True - The DOMViewer can be used to edit the documents
	 */
	public void setEditable(boolean canEdit) {
		this.canEdit = canEdit;
	//	System.out.println("s_setEditable");
	}

	/**
	 * Selects and scrolls to the given node in the document tree.
	 * 
	 * @param node
	 *            The node to be selected
	 */
	public void selectNode(Node node) {
		panel.selectNode(node);
		//System.out.println("s_selectNode");
	}

	/**
	 * Resets the history.
	 */
	public void resetHistory() {
		historyBrowserInterface.getHistoryBrowser().resetHistory();
	//	System.out.println("s_resetHistory");
	}

	/**
	 * Gets buttonFactory. 按钮生产
	 */
	private ButtonFactory getButtonFactory() {
		if (buttonFactory == null) {
			buttonFactory = new ButtonFactory(bundle, this);
		}
	//	System.out.println("s_ButtonFactory");
		return buttonFactory;
	}

	// ActionMap

	/**
	 * Returns the action associated with the given string or null on error
	 * 
	 * @param key
	 *            the key mapped with the action to get
	 * @throws MissingListenerException
	 *             if the action is not found
	 */
	public Action getAction(String key) throws MissingListenerException {
	//	System.out.println("s_getAction");
		return (Action) listeners.get(key);
	}

	/**
	 * Groups the document changes that were made out of the document into a
	 * single change and adds this change to the history browser.
	 */
	private void addChangesToHistory() {
		historyBrowserInterface.performCurrentCompoundCommand();
	//	System.out.println("s_addChangesToHistory");
	}

	/**
	 * The action associated with the 'Close' button of the viewer panel
	 */
	protected class CloseButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (panel.attributePanel.panelHiding()) {
				panel.tree.setSelectionRow(0);
				DOMViewer2.this.dispose();
			}
		//	System.out.println("s_CloseButtonAction");
		}
	}

	/**
	 * The action associated with the 'Undo' dropdown button of the viewer panel
	 */
	protected class UndoButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			addChangesToHistory();
			historyBrowserInterface.getHistoryBrowser().undo();
		//	System.out.println("s_UndoButtonAction");
		}

	}

	/**
	 * The action associated with the 'Redo' dropdown button of the viewer panel
	 */
	protected class RedoButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			addChangesToHistory();
			historyBrowserInterface.getHistoryBrowser().redo();
		//	System.out.println("s_RedoButtonAction");
		}
	}

	/**
	 * The action associated with the 'Capturing-click' toggle button of the
	 * viewer panel.
	 */
	protected class CapturingClickButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			JToggleButton btn = (JToggleButton) e.getSource();
			isCapturingClickEnabled = btn.isSelected();
			if (!isCapturingClickEnabled) {
				btn.setToolTipText(resources
						.getString("CapturingClickButton.tooltip"));
			} else {
				btn.setToolTipText(resources
						.getString("CapturingClickButton.disableText"));
			}
		//	System.out.println("s_CapturingClickButtonAction");
		}
	}

	/**
	 * Toggles the element highlighting overlay.
	 */
	protected void toggleOverlay() {
		isElementOverlayEnabled = overlayButton.isSelected();
		if (!isElementOverlayEnabled) {
			overlayButton.setToolTipText(resources
					.getString("OverlayButton.tooltip"));
		} else {
			overlayButton.setToolTipText(resources
					.getString("OverlayButton.disableText"));
		}
		// Refresh overlay
		if (elementOverlayManager != null) {
			elementOverlayManager.setOverlayEnabled(isElementOverlayEnabled);
			elementOverlayManager.repaint();
		}
	//	System.out.println("s_toggleOverlay");
	}

	/**
	 * The action associated with the 'overlay' toggle button of the viewer
	 * panel.
	 */
	protected class OverlayButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			toggleOverlay();
		}
	}

	/**
	 * NodePickerController implementation.
	 */
	protected class DOMViewerNodePickerController implements
			NodePickerController {

		public boolean isEditable() {
			return DOMViewer2.this.canEdit();
		}

		public boolean canEdit(Element el) {
			if (panel == null || panel.document == null || true
			/* || panel.document.getDocumentElement() != el */) {
				return true;
			}
			return false;
		}
	}

	/**
	 * DOMDocumentTreeController implementation.
	 */
	protected class DOMViewerDOMDocumentTreeController implements
			DOMDocumentTreeController {

		public boolean isDNDSupported() {
			return canEdit();
		}
	}

	/**
	 * ElementOverlayController implementation.
	 */
	protected class DOMViewerElementOverlayController implements
			ElementOverlayController {

		public boolean isOverlayEnabled() {
			return canEdit() && isElementOverlayEnabled;
		}
	}

	/**
	 * The panel that contains the viewer.
	 */
	public class Panel extends JPanel {

		// DOM Mutation event names.

		public static final String NODE_INSERTED = "DOMNodeInserted";
		public static final String NODE_REMOVED = "DOMNodeRemoved";
		public static final String ATTRIBUTE_MODIFIED = "DOMAttrModified";
		public static final String CHAR_DATA_MODIFIED = "DOMCharacterDataModified";

		/**
		 * The DOM document.
		 */
		public Document document;

		/**
		 * "Node inserted" DOM Mutation Listener.
		 */
		protected EventListener nodeInsertion;

		/**
		 * "Node removed" DOM Mutation Listener.
		 */
		protected EventListener nodeRemoval;

		/**
		 * "Attribute modified" DOM Mutation Listener.
		 */
		protected EventListener attrModification;

		/**
		 * "Character data modified" DOM Mutation Listener.
		 */
		protected EventListener charDataModification;

		/**
		 * Capturing "click" event type listener. Allows the "capturing click"
		 * option
		 */
		protected EventListener capturingListener;

		/**
		 * The ViewCSS object associated with the document.
		 */
		protected ViewCSS viewCSS;

		/**
		 * The tree.
		 */
		protected DOMDocumentTree tree;

		/**
		 * The split pane.
		 */
		protected JSplitPane splitPane;

		/**
		 * The right panel.
		 */
		protected JPanel rightPanel = new JPanel(new BorderLayout());

		/**
		 * The properties table.
		 */
		protected JTable propertiesTable = new JTable();

		/**
		 * The panel to show the nodes attributes.
		 */

		protected NodePickPanel attributePanel = new NodePickPanel(
				new DOMViewerNodePickerController());
		{
			attributePanel.addListener(new NodePickPanel.NodePickerAdapter() {

				public void updateElement(NodePickPanel.NodePickerEvent event) {
					String result = event.getResult();
					Element targetElement = (Element) event.getContextNode();
					Element newElem = wrapAndParse(result, targetElement);

					addChangesToHistory();

					AbstractCompoundCommand cmd = historyBrowserInterface
							.createNodeChangedCommand(newElem);
					Node parent = targetElement.getParentNode();
					Node nextSibling = targetElement.getNextSibling();
					cmd.addCommand(historyBrowserInterface
							.createRemoveChildCommand(parent, targetElement));
					cmd.addCommand(historyBrowserInterface
							.createInsertChildCommand(parent, nextSibling,
									newElem));
					historyBrowserInterface.performCompoundUpdateCommand(cmd);

					attributePanel.setPreviewElement(newElem);

			//		System.out.println("s_updateElement");
					// selectNewNode(newElem);
				}

				public void addNewElement(NodePickPanel.NodePickerEvent event) {
					String result = event.getResult();
					Element targetElement = (Element) event.getContextNode();
					Element newElem = wrapAndParse(result, targetElement);

					addChangesToHistory();

					historyBrowserInterface.appendChild(targetElement, newElem);

					attributePanel.setPreviewElement(newElem);
			//		System.out.println("s_addNewElement");
					// selectNewNode(newElem);
				}

				/**
				 * Parses the given string into an element after editing, or
				 * adding new element. The element that should be parsed has to
				 * have all the active prefixes set, so it has to be wrapped
				 * with the wrapper element that has all the mentioned prefixes
				 * bound to the appopriate namespaces. Wrapper element string
				 * has all the active prefixes set using the parentNode.
				 * 
				 * @param toParse
				 *            The string that should be parsed into svg element
				 * @param startingNode
				 *            The node from where to start looking the prefixes
				 * @return The parsed element
				 */
				private Element wrapAndParse(String toParse, Node startingNode) {
					// Fill the prefix map
					System.out.println(toParse);
					Map prefixMap = new HashMap();
					int j = 0;
					for (Node currentNode = startingNode; currentNode != null; currentNode = currentNode
							.getParentNode()) {
						NamedNodeMap nMap = currentNode.getAttributes();
						for (int i = 0; nMap != null && i < nMap.getLength(); i++) {
							Attr atr = (Attr) nMap.item(i);
							String prefix = atr.getPrefix();
							String localName = atr.getLocalName();
							String namespaceURI = atr.getValue();
							if (prefix != null
									&& prefix.equals(SVGConstants.XMLNS_PREFIX)) {
								String attrName = SVGConstants.XMLNS_PREFIX
										+ ":" + localName;
								if (!prefixMap.containsKey(attrName)) {
									prefixMap.put(attrName, namespaceURI);
								}
							}
							// Start from parentNode searching for the xmlns
							if ((j != 0 || currentNode == document
									.getDocumentElement())
									&& atr.getNodeName().equals(
											SVGConstants.XMLNS_PREFIX)
									&& !prefixMap
											.containsKey(SVGConstants.XMLNS_PREFIX)) {
								prefixMap.put(SVGConstants.XMLNS_PREFIX, atr
										.getNodeValue());
							}
						}
						j++;
					}
					// System.out.println(panel.document);
					Document doc = panel.document;
					// System.out.println(doc);
					SAXDocumentFactory df = new SAXDocumentFactory(doc
							.getImplementation(), XMLResourceDescriptor
							.getXMLParserClassName());
					URL urlObj = null;
					if (doc instanceof SVGOMDocument) {
						urlObj = ((SVGOMDocument) doc).getURLObject();
					}
					String uri = (urlObj == null) ? "" : urlObj.toString();

					Node node = DOMUtilities.parseXML(toParse, doc, uri,
							prefixMap, SVGConstants.SVG_SVG_TAG, df);

				//	System.out.println(node.getTextContent());
				
					return (Element) node.getFirstChild();
				}

				/**
				 * Selects the given element. Needs to be wrapped with the
				 * UpdateManager to wait for the previous command to be executed
				 * by HistoryBrowser
				 * 
				 * @param elem
				 *            The element to select
				 */
				private void selectNewNode(final Element elem) {
					domViewerController.performUpdate(new Runnable() {
						public void run() {
							selectNode(elem);
						};
					});
			//		System.out.println("s_selectNewNode");
				}
			});
		}
		
		
		///////////----------------------------以上是nodepickpanel的东东

		/**
		 * The layout for the attribute panel.
		 */
		protected GridBagConstraints attributePanelLayout = new GridBagConstraints();
		{
			attributePanelLayout.gridx = 1;
			attributePanelLayout.gridy = 1;
			attributePanelLayout.gridheight = 2;
			attributePanelLayout.weightx = 1.0;
			attributePanelLayout.weighty = 1.0;
			attributePanelLayout.fill = GridBagConstraints.BOTH;
		}

		/**
		 * The layout for the properties table.
		 */
		protected GridBagConstraints propertiesTableLayout = new GridBagConstraints();
		{
			propertiesTableLayout.gridx = 1;
			propertiesTableLayout.gridy = 3;
			propertiesTableLayout.weightx = 1.0;
			propertiesTableLayout.weighty = 1.0;
			propertiesTableLayout.fill = GridBagConstraints.BOTH;
		}

		/**
		 * The element panel.
		 */
		protected JPanel elementPanel = new JPanel(new GridBagLayout());
		{
			JScrollPane pane2 = new JScrollPane();
			pane2.setBorder(BorderFactory.createCompoundBorder(BorderFactory
					.createEmptyBorder(2, 0, 2, 2), BorderFactory
					.createCompoundBorder(BorderFactory.createTitledBorder(
							BorderFactory.createEmptyBorder(), resources
									.getString("CSSValuesPanel.title")),
							BorderFactory.createLoweredBevelBorder())));
			pane2.getViewport().add(propertiesTable);

			// 2/3 attributePanel, 1/3 propertiesTable
			elementPanel.add(attributePanel, attributePanelLayout);
			elementPanel.add(pane2, propertiesTableLayout);
		}
        //--------以上是样式表
		/**
		 * The CharacterData panel text area.
		 */
		protected class CharacterPanel extends JPanel {

			/**
			 * Associated node.
			 */
			protected Node node;

			/**
			 * The text area.
			 */
			protected JTextArea textArea = new JTextArea();

			/**
			 * Constructor.
			 * 
			 * @param layout
			 *            The LayoutManager
			 */
			public CharacterPanel(BorderLayout layout) {
				super(layout);
			}

			/**
			 * Gets the textArea.
			 * 
			 * @return textArea
			 */
			public JTextArea getTextArea() {
				return textArea;
			}

			/**
			 * Sets the textArea.
			 * 
			 * @param textArea
			 *            the text area to set
			 */
			public void setTextArea(JTextArea textArea) {
				this.textArea = textArea;
			}

			/**
			 * Gets the node.
			 * 
			 * @return node
			 */
			public Node getNode() {
				return node;
			}

			/**
			 * Sets the node.
			 * 
			 * @param node
			 *            the node to set
			 */
			public void setNode(Node node) {
				this.node = node;
			}
		}

		/**
		 * The CharacterData node panel.
		 */
		protected CharacterPanel characterDataPanel = new CharacterPanel(
				new BorderLayout());
		{
			characterDataPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(2, 0, 2, 2), BorderFactory
							.createCompoundBorder(BorderFactory
									.createTitledBorder(BorderFactory
											.createEmptyBorder(), resources
											.getString("CDataPanel.title")),
									BorderFactory.createLoweredBevelBorder())));
			JScrollPane pane = new JScrollPane();
			JTextArea textArea = new JTextArea();
			characterDataPanel.setTextArea(textArea);
			pane.getViewport().add(textArea);
			characterDataPanel.add(pane);

			textArea.setEditable(true);
			textArea.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					if (canEdit()) {
						Node contextNode = characterDataPanel.getNode();
						String newValue = characterDataPanel.getTextArea()
								.getText();
						switch (contextNode.getNodeType()) {
						case Node.COMMENT_NODE:
						case Node.TEXT_NODE:
						case Node.CDATA_SECTION_NODE:
							addChangesToHistory();
							historyBrowserInterface.setNodeValue(contextNode,
									newValue);
							break;
						}
					}
				}
			});
		}

		/**
		 * The documentInfo panel text area.
		 */
		protected JTextArea documentInfo = new JTextArea();

		/**
		 * The documentInfo node panel.
		 */
		protected JPanel documentInfoPanel = new JPanel(new BorderLayout());
		{
			documentInfoPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(2, 0, 2, 2),
					BorderFactory.createCompoundBorder(BorderFactory
							.createTitledBorder(BorderFactory
									.createEmptyBorder(), resources
									.getString("DocumentInfoPanel.title")),
							BorderFactory.createLoweredBevelBorder())));
			JScrollPane pane = new JScrollPane();
			pane.getViewport().add(documentInfo);
			documentInfoPanel.add(pane);
			documentInfo.setEditable(false);
		}

		
		
		/**
		 * Creates a new Panel object.
		 */
		public Panel() {
			super(new BorderLayout());
			setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEmptyBorder(), resources
					.getString("DOMViewerPanel.title")));

			JToolBar tb = new JToolBar(resources
					.getString("DOMViewerToolbar.name"));
			tb.setFloatable(false);

			// Undo
			JButton undoButton = getButtonFactory().createJToolbarButton(
					"UndoButton");
			undoButton.setDisabledIcon(new ImageIcon(getClass().getResource(
					resources.getString("UndoButton.disabledIcon"))));
			DropDownComponent undoDD = new DropDownComponent(undoButton);
			undoDD.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
			undoDD.setMaximumSize(new Dimension(44, 25));
			undoDD.setPreferredSize(new Dimension(44, 25));
			tb.add(undoDD);
			UndoPopUpMenuModel undoModel = new UndoPopUpMenuModel(undoDD
					.getPopupMenu(), historyBrowserInterface);
			undoDD.getPopupMenu().setModel(undoModel);

			// Redo
			JButton redoButton = getButtonFactory().createJToolbarButton(
					"RedoButton");
			redoButton.setDisabledIcon(new ImageIcon(getClass().getResource(
					resources.getString("RedoButton.disabledIcon"))));
			DropDownComponent redoDD = new DropDownComponent(redoButton);
			redoDD.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
			redoDD.setMaximumSize(new Dimension(44, 25));
			redoDD.setPreferredSize(new Dimension(44, 25));
			tb.add(redoDD);
			RedoPopUpMenuModel redoModel = new RedoPopUpMenuModel(redoDD
					.getPopupMenu(), historyBrowserInterface);
			redoDD.getPopupMenu().setModel(redoModel);

			// Capturing click toggle button
			JToggleButton capturingClickButton = getButtonFactory()
					.createJToolbarToggleButton("CapturingClickButton");
			capturingClickButton.setEnabled(true);
			capturingClickButton.setPreferredSize(new Dimension(32, 25));
			tb.add(capturingClickButton);

			// Overlay toggle button
			overlayButton = getButtonFactory().createJToolbarToggleButton(
					"OverlayButton");
			overlayButton.setEnabled(true);
			overlayButton.setPreferredSize(new Dimension(32, 25));
			tb.add(overlayButton);

			// Add toolbar
			add(tb, BorderLayout.NORTH);

			// DOMDocumentTree
			TreeNode root;
			root = new DefaultMutableTreeNode(resources
					.getString("EmptyDocument.text"));
			tree = new DOMDocumentTree(root,
					new DOMViewerDOMDocumentTreeController());
			tree.setCellRenderer(new NodeRenderer());
			tree.putClientProperty("JTree.lineStyle", "Angled");
			// Add the listeners to DOMDocumentTree
			tree.addListener(new DOMDocumentTreeAdapter() {

				public void dropCompleted(DOMDocumentTreeEvent event) {
					DropCompletedInfo info = (DropCompletedInfo) event
							.getSource();

					addChangesToHistory();

					AbstractCompoundCommand cmd = historyBrowserInterface
							.createNodesDroppedCommand(info.getChildren());

					int n = info.getChildren().size();
					for (int i = 0; i < n; i++) {
						Node node = (Node) info.getChildren().get(i);
						// If the node has its ancestor in selected nodes,
						// it should stay as it's child
						if (!DOMUtilities.isAnyNodeAncestorOf(info
								.getChildren(), node)) {
							cmd.addCommand(historyBrowserInterface
									.createInsertChildCommand(info.getParent(),
											info.getSibling(), node));
						}
					}
					historyBrowserInterface.performCompoundUpdateCommand(cmd);
				}
			});
			tree.addTreeSelectionListener(new DOMTreeSelectionListener());
			tree.addMouseListener(new TreePopUpListener());
			JScrollPane treePane = new JScrollPane();
			treePane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
					.createEmptyBorder(2, 2, 2, 0), BorderFactory
					.createCompoundBorder(BorderFactory.createTitledBorder(
							BorderFactory.createEmptyBorder(), resources
									.getString("DOMViewer.title")),
							BorderFactory.createLoweredBevelBorder())));
			treePane.getViewport().add(tree);
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, // Continuous
					// layout
					treePane, rightPanel);
			int loc = resources.getInteger("SplitPane.dividerLocation");
			splitPane.setDividerLocation(loc);
			add(splitPane);
		}

		/**
		 * Sets the document to display.
		 */
		public void setDocument(Document doc) {
			setDocument(doc, null);

		}
		public Document getDocument()
		{
			return this.document;
		}
		/**
		 * Sets the document to display and its ViewCSS.
		 */
		public void setDocument(Document doc, ViewCSS view) {
			if (document != null) {
				if (document != doc) {
					removeDomMutationListeners(document);
					addDomMutationListeners(doc);
					removeCapturingListener(document);
					addCapturingListener(doc);
				}
		//	System.out.println("s_setDocument");
			} else {
				addDomMutationListeners(doc);
				addCapturingListener(doc);
			}
			resetHistory();
			document = doc;
			viewCSS = view;
			TreeNode root = createTree(doc, showWhitespace);
			((DefaultTreeModel) tree.getModel()).setRoot(root);
			if (rightPanel.getComponentCount() != 0) {
				rightPanel.remove(0);
				splitPane.revalidate();
				splitPane.repaint();
			}
		//	System.out.println("s_setDocument2");
		}

		/**
		 * Registers DOM Mutation Listener on the given document.
		 * 
		 * @param doc
		 *            The given document
		 */
		protected void addDomMutationListeners(Document doc) {
			EventTarget target = (EventTarget) doc;
			nodeInsertion = new NodeInsertionHandler();
			target.addEventListener(NODE_INSERTED, nodeInsertion, true);
			nodeRemoval = new NodeRemovalHandler();
			target.addEventListener(NODE_REMOVED, nodeRemoval, true);
			attrModification = new AttributeModificationHandler();
			target.addEventListener(ATTRIBUTE_MODIFIED, attrModification, true);
			charDataModification = new CharDataModificationHandler();
			target.addEventListener(CHAR_DATA_MODIFIED, charDataModification,
					true);
		//	System.out.println("s_addDomMutationListeners");
		}

		/**
		 * Removes previously registered mutation listeners from the document.
		 * 
		 * @param doc
		 *            The document
		 */
		protected void removeDomMutationListeners(Document doc) {
			if (doc != null) {
				EventTarget target = (EventTarget) doc;
				target.removeEventListener(NODE_INSERTED, nodeInsertion, true);
				target.removeEventListener(NODE_REMOVED, nodeRemoval, true);
				target.removeEventListener(ATTRIBUTE_MODIFIED,
						attrModification, true);
				target.removeEventListener(CHAR_DATA_MODIFIED,
						charDataModification, true);
			}
		//	System.out.println("s_removeDomMutationListeners");
		}

		/**
		 * Registers capturing "click" listener on the document element of the
		 * given document.
		 * 
		 * @param doc
		 *            The given document
		 */
		protected void addCapturingListener(Document doc) {
			EventTarget target = (EventTarget) doc.getDocumentElement();
			capturingListener = new CapturingClickHandler();
			target.addEventListener(SVGConstants.SVG_CLICK_EVENT_TYPE,
					capturingListener, true);
		//	System.out.println("s_addCapturingListener");
		}

		/**
		 * Removes previously registered capturing "click" event listener from
		 * the document element of the given document.
		 * 
		 * @param doc
		 *            The given document
		 */
		protected void removeCapturingListener(Document doc) {
			if (doc != null) {
				EventTarget target = (EventTarget) doc.getDocumentElement();
				target.removeEventListener(SVGConstants.SVG_CLICK_EVENT_TYPE,
						capturingListener, true);
			}
		}

		/**
		 * Handles "DOMNodeInserted" event.
		 */
		protected class NodeInsertionHandler implements EventListener {

			public void handleEvent(final Event evt) {
				Runnable runnable = new Runnable() {
					public void run() {
						MutationEvent mevt = (MutationEvent) evt;
						Node targetNode = (Node) mevt.getTarget();
						DefaultMutableTreeNode parentNode = findNode(tree,
								targetNode.getParentNode());
						DefaultMutableTreeNode insertedNode = (DefaultMutableTreeNode) createTree(
								targetNode, showWhitespace);
						DefaultTreeModel model = (DefaultTreeModel) tree
								.getModel();
						DefaultMutableTreeNode newParentNode = (DefaultMutableTreeNode) createTree(
								targetNode.getParentNode(), showWhitespace);
						// Finds where to insert the node in the tree
						int index = findIndexToInsert(parentNode, newParentNode);
						if (index != -1) {
							model.insertNodeInto(insertedNode, parentNode,
									index);
						}
						attributePanel.updateOnDocumentChange(mevt.getType(),
								targetNode);
					}
				};
				refreshGUI(runnable);
				registerDocumentChange((MutationEvent) evt);
				try {
					bos = new ByteArrayOutputStream();
					writer = new OutputStreamWriter(bos, "utf-8");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {

					writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
					writer.write(EOL);
					SVGDocument svgdocument = (SVGDocument)document;
					Node fc = svgdocument.getFirstChild();
					if (fc.getNodeType() != Node.DOCUMENT_TYPE_NODE) {

						writer.write("<!DOCTYPE svg PUBLIC '");
						writer.write(SVGConstants.SVG_PUBLIC_ID);
						writer.write("' '");
						writer.write(SVGConstants.SVG_SYSTEM_ID);
						writer.write("'>");
						writer.write(EOL);
						writer.write(EOL);
					}
					Element root = svgdocument.getRootElement();
					root.setAttributeNS(XMLConstants.XML_NAMESPACE_URI,
							"xml:base", svgdocument.getURL());
					DOMUtilities.writeDocument(svgdocument, writer);
					writer.flush();
					bytes = bos.toByteArray();
					is = new ByteArrayInputStream(bytes);

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						// bos.close();
						writer.close();

					} catch (IOException ie) {
						ie.printStackTrace();
					}
				}
				try {

				//	String filename=null;
				//	filename=svgApplet.getSvgOptionName();
					conn = (Connection) JdbcUtils.getConnection();
					String sql = "update svgfiles set svgfile=? where svgname=?";
					ps = (PreparedStatement) conn.prepareStatement(sql);
					ps.setBinaryStream(1, is, bytes.length);
				 	ps.setString(2, "anhui.svg");
					//ps.setString(2, filename);
					int i = ps.executeUpdate();
				
				} catch (SQLException se) {
					se.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException ie) {
						ie.printStackTrace();
					}
					JdbcUtils.free(rs, ps, conn);
				}
			//	System.out.println("s_NodeInsertionHandler");
			}

			/**
			 * Compares the children of the two tree nodes and returns the index
			 * of the first difference.
			 * 
			 * @param parentNode
			 *            The old tree node
			 * @param newParentNode
			 *            The new tree node
			 * @return first child that differs
			 */
			protected int findIndexToInsert(DefaultMutableTreeNode parentNode,
					DefaultMutableTreeNode newParentNode) {
				int index = -1;
				if (parentNode == null || newParentNode == null) {
					return index;
				}
				// Finds the index where to insert new node
				Enumeration oldChildren = parentNode.children();
				Enumeration newChildren = newParentNode.children();
				int count = 0;
				while (oldChildren.hasMoreElements()) {
					// Current DefaultMutableTreeNode node being processed
					DefaultMutableTreeNode currentOldChild = (DefaultMutableTreeNode) oldChildren
							.nextElement();
					DefaultMutableTreeNode currentNewChild = (DefaultMutableTreeNode) newChildren
							.nextElement();
					Node oldChild = ((NodeInfo) currentOldChild.getUserObject())
							.getNode();
					Node newChild = ((NodeInfo) currentNewChild.getUserObject())
							.getNode();
					if (oldChild != newChild) {
						return count;
					}
					count++;
				}
				return count;
			}
		}

		/**
		 * Handles "DOMNodeRemoved" event.
		 */
		protected class NodeRemovalHandler implements EventListener {

			public void handleEvent(final Event evt) {
				Runnable runnable = new Runnable() {
					public void run() {
						MutationEvent mevt = (MutationEvent) evt;
						Node targetNode = (Node) mevt.getTarget();
						DefaultMutableTreeNode treeNode = findNode(tree,
								targetNode);
						DefaultTreeModel model = (DefaultTreeModel) tree
								.getModel();
						if (treeNode != null) {
							model.removeNodeFromParent(treeNode);
						}
						attributePanel.updateOnDocumentChange(mevt.getType(),
								targetNode);
					}
				};
				refreshGUI(runnable);
				registerDocumentChange((MutationEvent) evt);
			//	System.out.println("s_NodeRemovalHandler");
			}
		}

		/**
		 * Handles "DOMAttrModified" event.
		 */
		protected class AttributeModificationHandler implements EventListener {

			public void handleEvent(final Event evt) {
				Runnable runnable = new Runnable() {
					public void run() {
						MutationEvent mevt = (MutationEvent) evt;
						Element targetElement = (Element) mevt.getTarget();
						// Repaint the target node
						DefaultTreeModel model = (DefaultTreeModel) tree
								.getModel();

						model.nodeChanged(findNode(tree, targetElement));
						attributePanel.updateOnDocumentChange(mevt.getType(),
								targetElement);
					}
				};

				refreshGUI(runnable);
				registerDocumentChange((MutationEvent) evt);
			//	System.out.println("s_AttributeModificationHandler");
			}
		}

		/**
		 * Handles "DOMCharacterDataModified" event.
		 */
		protected class CharDataModificationHandler implements EventListener {
			public void handleEvent(final Event evt) {
				Runnable runnable = new Runnable() {
					public void run() {
						MutationEvent mevt = (MutationEvent) evt;
						Node targetNode = (Node) mevt.getTarget();
						if (characterDataPanel.getNode() == targetNode) {
							characterDataPanel.getTextArea().setText(
									targetNode.getNodeValue());
							attributePanel.updateOnDocumentChange(mevt
									.getType(), targetNode);
						}
					}
				};
				refreshGUI(runnable);
				if (characterDataPanel.getNode() == evt.getTarget()) {
					registerDocumentChange((MutationEvent) evt);
				}
			//	System.out.println("s_CharDataModificationHandler");
			}
		}

		/**
		 * Checks whether the DOMViewer can be used to edit the document and if
		 * true refreshes the DOMViewer after the DOM Mutation event occured.
		 * 
		 * @param runnable
		 *            The runnable to invoke for refresh
		 */
		protected void refreshGUI(Runnable runnable) {
			if (canEdit()) {
				try {
					SwingUtilities.invokeAndWait(runnable);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			//	System.out.println("s_refreshGUI");
			}
		}

		/**
		 * Adds the "DOMNodeInserted" Mutation event to current history
		 * browser's interface compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerNodeInserted(MutationEvent mevt) {
			Node targetNode = (Node) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createNodeInsertedCommand(targetNode
									.getParentNode(), targetNode
									.getNextSibling(), targetNode));
		//	System.out.println("s_registerNodeInserted");
		}

		/**
		 * Adds the "DOMNodeRemoved" Mutation event to current history browser's
		 * interface compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerNodeRemoved(MutationEvent mevt) {
			Node targetNode = (Node) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createNodeRemovedCommand(mevt.getRelatedNode(),
									targetNode.getNextSibling(), targetNode));
		//	System.out.println("s_registerNodeRemoved");
		}

		/**
		 * Adds the "DOMAttrModified" Mutation event, of the
		 * MutationEvent.ADDITION type to current history browser's interface
		 * compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerAttributeAdded(MutationEvent mevt) {
			Element targetElement = (Element) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createAttributeAddedCommand(targetElement, mevt
									.getAttrName(), mevt.getNewValue(), null));
		//	System.out.println("s_registerAttributeAdded");
		}

		/**
		 * Adds the "DOMAttrModified" Mutation event, of the
		 * MutationEvent.REMOVAL type to current history browser's interface
		 * compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */

		protected void registerAttributeRemoved(MutationEvent mevt) {
			Element targetElement = (Element) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createAttributeRemovedCommand(targetElement, mevt
									.getAttrName(), mevt.getPrevValue(), null));
		//	System.out.println("s_registerAttributeRemoved");
		}

		/**
		 * Adds the "DOMAttrModified" Mutation event, of the
		 * MutationEvent.MODIFICATION type to current history browser's
		 * interface compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerAttributeModified(MutationEvent mevt) {
			Element targetElement = (Element) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createAttributeModifiedCommand(targetElement, mevt
									.getAttrName(), mevt.getPrevValue(), mevt
									.getNewValue(), null));
		//	System.out.println("s_registerAttributeModified");
		}

		/**
		 * Checks what type of the "DOMAttrModified" mutation event occured, and
		 * invokes the appropriate method to register the change.
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerAttributeChanged(MutationEvent mevt) {
			System.out.println("s_registerAttributeChanged");
			switch (mevt.getAttrChange()) {
			case MutationEvent.ADDITION:
				registerAttributeAdded(mevt);
				break;
			case MutationEvent.REMOVAL:
				registerAttributeRemoved(mevt);
				break;
			case MutationEvent.MODIFICATION:
				registerAttributeModified(mevt);
				break;
			default:
				registerAttributeModified(mevt);
				break;
			}
		
		}

		/**
		 * Adds the "DOMCharDataModified" Mutation event to current history
		 * browser's interface compound command
		 * 
		 * @param mevt
		 *            The Mutation Event
		 */
		protected void registerCharDataModified(MutationEvent mevt) {
			System.out.println("s_registerCharDataModified");
			Node targetNode = (Node) mevt.getTarget();
			historyBrowserInterface
					.addToCurrentCompoundCommand(historyBrowserInterface
							.createCharDataModifiedCommand(targetNode, mevt
									.getPrevValue(), mevt.getNewValue()));
		}

		/**
		 * Checks if the document change that occured should be registered. If
		 * the document change has occured out of the DOMViewer, the state of
		 * the history browser should be HistoryBrowserState.IDLE. Otherwise, if
		 * the DOMViewer caused the change, one of the following states is
		 * active: HistoryBrowserState.EXECUTING, HistoryBrowserState.UNDOING,
		 * HistoryBrowserState.REDOING. This method should be invoked while the
		 * document change is occuring (in mutation event handlers), otherwise,
		 * if put in another thread, the state flag becomes invalid. Also checks
		 * if the DOMViewer can be used to edit the document. If not, then the
		 * change shouldn't be registered by the HistoryBrowser
		 * 
		 * @return True if the DOMViewer can edit the document and the history
		 *         browser state is IDLE at the moment
		 */
		protected boolean shouldRegisterDocumentChange() {
			return canEdit()
					&& historyBrowserInterface.getHistoryBrowser().getState() == HistoryBrowser.IDLE;
		}

		/**
		 * Puts the document change in the current history browser's interface
		 * compound command if the document change occured outside of the
		 * DOMViewer.
		 * 
		 * @param mevt
		 *            The info on the event. Use to describe the document change
		 *            to the history browser
		 */
		protected void registerDocumentChange(MutationEvent mevt) {
			System.out.println("s_registerDocumentChange");
			if (shouldRegisterDocumentChange()) {
				String type = mevt.getType();
				if (type.equals(NODE_INSERTED)) {
					registerNodeInserted(mevt);
				} else if (type.equals(NODE_REMOVED)) {
					registerNodeRemoved(mevt);
				} else if (type.equals(ATTRIBUTE_MODIFIED)) {
					registerAttributeChanged(mevt);
				} else if (type.equals(CHAR_DATA_MODIFIED)) {
					registerCharDataModified(mevt);
				}
			}
		}

		/**
		 * Handles the capturing "mouseclick" event.
		 */
		protected class CapturingClickHandler implements EventListener {
			public void handleEvent(Event evt) {
				if (isCapturingClickEnabled) {
					Element targetElement = (Element) evt.getTarget();
					selectNode(targetElement);
				}
			//	System.out.println("s_CapturingClickHandler");
			}
		}

		/**
		 * Creates a swing tree from a DOM document.
		 */
		protected MutableTreeNode createTree(Node node, boolean showWhitespace) {
		
			DefaultMutableTreeNode result;
			result = new DefaultMutableTreeNode(new NodeInfo(node));
			for (Node n = node.getFirstChild(); n != null; n = n
					.getNextSibling()) {
				if (!showWhitespace && (n instanceof org.w3c.dom.Text)) {
					String txt = n.getNodeValue();
					if (txt.trim().length() == 0)
						continue;
				}
				result.add(createTree(n, showWhitespace));
			}
			if (node instanceof NodeXBL) {
				Element shadowTree = ((NodeXBL) node).getXblShadowTree();
				if (shadowTree != null) {
					DefaultMutableTreeNode shadowNode = new DefaultMutableTreeNode(
							new ShadowNodeInfo(shadowTree));
					shadowNode.add(createTree(shadowTree, showWhitespace));
					result.add(shadowNode);
				}
			}
			if (node instanceof XBLOMContentElement) {
				AbstractDocument doc = (AbstractDocument) node
						.getOwnerDocument();
				XBLManager xm = doc.getXBLManager();
				if (xm instanceof DefaultXBLManager) {
					DefaultMutableTreeNode selectedContentNode = new DefaultMutableTreeNode(
							new ContentNodeInfo(node));
					DefaultXBLManager dxm = (DefaultXBLManager) xm;
					ContentManager cm = dxm.getContentManager(node);
					if (cm != null) {
						NodeList nl = cm
								.getSelectedContent((XBLOMContentElement) node);
						for (int i = 0; i < nl.getLength(); i++) {
							selectedContentNode.add(createTree(nl.item(i),
									showWhitespace));
						}
						result.add(selectedContentNode);
					}
				}
				// System.out.println("create tree");
			}

			return result;
		}

		/**
		 * Finds and returns the node in the tree that represents the given node
		 * in the document.
		 * 
		 * @param theTree
		 *            The given JTree
		 * @param node
		 *            The given org.w3c.dom.Node
		 * @return Node or null if not found
		 */
		protected DefaultMutableTreeNode findNode(JTree theTree, Node node) {
			System.out.println("s_findNode");
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) theTree
					.getModel().getRoot();
			Enumeration treeNodes = root.breadthFirstEnumeration();
			while (treeNodes.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) treeNodes
						.nextElement();
				NodeInfo userObject = (NodeInfo) currentNode.getUserObject();
				if (userObject.getNode() == node) {
					return currentNode;
				}
			}
			return null;
		}

		/**
		 * Finds and selects the given node in the document tree.
		 * 
		 * @param targetNode
		 *            The node to be selected
		 */
		public void selectNode(final Node targetNode) {
			System.out.println("s_selectNode");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					DefaultMutableTreeNode node = findNode(tree, targetNode);
					if (node != null) {
						TreeNode[] path = node.getPath();
						TreePath tp = new TreePath(path);
						// Changes the selection
						tree.setSelectionPath(tp);
						// Expands and scrolls the TreePath to visible if
						// needed
						tree.scrollPathToVisible(tp);
					}
				}
			});
		}

		/**
		 * Tree popup listener.
		 */
		protected class TreePopUpListener extends MouseAdapter {

			/**
			 * The actual pop-up menu.
			 */
			protected JPopupMenu treePopupMenu;

			/**
			 * Creates new pop up listener.
			 */
			public TreePopUpListener() {
				treePopupMenu = new JPopupMenu();

				// "Insert new node" menu
				treePopupMenu.add(createTemplatesMenu(resources
						.getString("ContextMenuItem.insertNewNode")));

				// "Create new element..." item
				JMenuItem item = new JMenuItem(resources
						.getString("ContextMenuItem.createNewElement"));
				treePopupMenu.add(item);
				item.addActionListener(new TreeNodeAdder());

				// "Remove selection" item
				item = new JMenuItem(resources
						.getString("ContextMenuItem.removeSelection"));
				item.addActionListener(new TreeNodeRemover());
				treePopupMenu.add(item);
			}

			public void mouseReleased(MouseEvent e) {
				// Show the pop-up component
				if (e.isPopupTrigger() && e.getClickCount() == 1) {
					if (tree.getSelectionPaths() != null) {
						showPopUp(e);
					}
				}
			}

			// Handles selection on the tree
			public void mousePressed(MouseEvent e) {
				JTree sourceTree = (JTree) e.getSource();
				TreePath targetPath = sourceTree.getPathForLocation(e.getX(), e
						.getY());
				if (!e.isControlDown() && !e.isShiftDown()) {
					sourceTree.setSelectionPath(targetPath);
				} else {
					sourceTree.addSelectionPath(targetPath);
				}
				// Show the pop-up component
				if (e.isPopupTrigger() && e.getClickCount() == 1) {
					showPopUp(e);
				}
			}

			/**
			 * Shows this popup menu if the DOMViewer can be used to edti the
			 * document.
			 */
			private void showPopUp(MouseEvent e) {
				if (canEdit()) {
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					// Don't show the pop up menu for the root element
					if (path != null && path.getPathCount() > 1) {
						treePopupMenu.show((Component) e.getSource(), e.getX(),
								e.getY());
					}
				}
			}
		}

		/**
		 * Handles tree pop-up menu action for adding new node.
		 */
		protected class TreeNodeAdder implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				// Prompt for the node name
				System.out.println("s_TreeNodeAdder");
				NodePickPanel.NameEditorDialog nameEditorDialog = new NodePickPanel.NameEditorDialog(
						DOMViewer2.this);
				nameEditorDialog.setLocationRelativeTo(DOMViewer2.this);
				int results = nameEditorDialog.showDialog();
				if (results == NodePickPanel.NameEditorDialog.OK_OPTION) {
					Element elementToAdd = document.createElementNS(
							SVGConstants.SVG_NAMESPACE_URI, nameEditorDialog
									.getResults());
					if (rightPanel.getComponentCount() != 0) {
						rightPanel.remove(0);
					}
					rightPanel.add(elementPanel);

					// Pass the parent node as the selected one
					TreePath[] treePaths = tree.getSelectionPaths();
					if (treePaths != null) {
						TreePath treePath = treePaths[treePaths.length - 1];
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
								.getLastPathComponent();
						NodeInfo nodeInfo = (NodeInfo) node.getUserObject();
						// Enter the attributePanel 'add new element' mode
						attributePanel.enterAddNewElementMode(elementToAdd,
								nodeInfo.getNode());
					}
				}
			
			}
		}

		/**
		 * Parser for the Element template.
		 */
		protected class NodeTemplateParser implements ActionListener {

			/**
			 * The string to parse.
			 */
			protected String toParse;

			/**
			 * The node type.
			 */
			protected short nodeType;

			/**
			 * Constructor.
			 * 
			 * @param toParse
			 *            The string to parse
			 */
			public NodeTemplateParser(String toParse, short nodeType) {
				this.toParse = toParse;
				this.nodeType = nodeType;
			}

			public void actionPerformed(ActionEvent e) {
				System.out.println("s_NodeTemplateParser");
				Node nodeToAdd = null;
				switch (nodeType) {
				case Node.ELEMENT_NODE:
					URL urlObj = null;
					if (document instanceof SVGOMDocument) {
						urlObj = ((SVGOMDocument) document).getURLObject();
					}
					String uri = (urlObj == null) ? "" : urlObj.toString();
					Map prefixes = new HashMap();
					prefixes.put(SVGConstants.XMLNS_PREFIX,
							SVGConstants.SVG_NAMESPACE_URI);
					prefixes.put(SVGConstants.XMLNS_PREFIX + ":"
							+ SVGConstants.XLINK_PREFIX,
							SVGConstants.XLINK_NAMESPACE_URI);
					SAXDocumentFactory df = new SAXDocumentFactory(document
							.getImplementation(), XMLResourceDescriptor
							.getXMLParserClassName());
					DocumentFragment documentFragment = (DocumentFragment) DOMUtilities
							.parseXML(toParse, document, uri, prefixes,
									SVGConstants.SVG_SVG_TAG, df);
					nodeToAdd = documentFragment.getFirstChild();
					break;
				case Node.TEXT_NODE:
					nodeToAdd = document.createTextNode(toParse);
					break;
				case Node.COMMENT_NODE:
					nodeToAdd = document.createComment(toParse);
					break;
				case Node.CDATA_SECTION_NODE:
					nodeToAdd = document.createCDATASection(toParse);
				}

				// Append the new node to the parentNode
				TreePath[] treePaths = tree.getSelectionPaths();
				if (treePaths != null) {
					TreePath treePath = treePaths[treePaths.length - 1];
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
							.getLastPathComponent();
					NodeInfo nodeInfo = (NodeInfo) node.getUserObject();

					addChangesToHistory();

					historyBrowserInterface.appendChild(nodeInfo.getNode(),
							nodeToAdd);
				}
			}
		}

		/**
		 * Creates JMenu menu using {@link NodeTemplates}.
		 * 
		 * @param name
		 *            The name of the submenu
		 * @return The JMenu submenu
		 */
		protected JMenu createTemplatesMenu(String name) {
			System.out.println("s_createTemplatesMenu");
			NodeTemplates templates = new NodeTemplates();
			JMenu submenu = new JMenu(name);

			// Create submenus
			HashMap menuMap = new HashMap();
			ArrayList categoriesList = templates.getCategories();
			int n = categoriesList.size();
			for (int i = 0; i < n; i++) {
				String category = categoriesList.get(i).toString();
				JMenu currentMenu = new JMenu(category);
				submenu.add(currentMenu);
				// Add submenus to the map
				menuMap.put(category, currentMenu);
			}

			// Sort the value list and then iterate through node templates
			ArrayList values = new ArrayList(templates.getNodeTemplatesMap()
					.values());
			Collections.sort(values, new Comparator() {
				public int compare(Object o1, Object o2) {
					NodeTemplateDescriptor n1 = (NodeTemplateDescriptor) o1;
					NodeTemplateDescriptor n2 = (NodeTemplateDescriptor) o2;
					return n1.getName().compareTo(n2.getName());
				}
			});
			Iterator iter = values.iterator();
			while (iter.hasNext()) {
				NodeTemplateDescriptor desc = (NodeTemplateDescriptor) iter
						.next();
				String toParse = desc.getXmlValue();
				short nodeType = desc.getType();
				String nodeCategory = desc.getCategory();
				JMenuItem currentItem = new JMenuItem(desc.getName());
				currentItem.addActionListener(new NodeTemplateParser(toParse,
						nodeType));
				JMenu currentSubmenu = (JMenu) menuMap.get(nodeCategory);
				currentSubmenu.add(currentItem);
			}
			return submenu;
		}

		/**
		 * Handles tree pop-up menu action for removing nodes.
		 */
		protected class TreeNodeRemover implements ActionListener {
		
			public void actionPerformed(ActionEvent e) {
				System.out.println("s_TreeNodeRemover");
				addChangesToHistory();

				AbstractCompoundCommand cmd = historyBrowserInterface
						.createRemoveSelectedTreeNodesCommand(null);
				TreePath[] treePaths = tree.getSelectionPaths();
				for (int i = 0; treePaths != null && i < treePaths.length; i++) {
					TreePath treePath = treePaths[i];
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
							.getLastPathComponent();
					NodeInfo nodeInfo = (NodeInfo) node.getUserObject();
					if (DOMUtilities.isParentOf(nodeInfo.getNode(), nodeInfo
							.getNode().getParentNode())) {
						cmd.addCommand(historyBrowserInterface
								.createRemoveChildCommand(nodeInfo.getNode()
										.getParentNode(), nodeInfo.getNode()));
					}
				}
				historyBrowserInterface.performCompoundUpdateCommand(cmd);

			}
		}

		/**
		 * To listen to the tree selection.
		 */
		protected class DOMTreeSelectionListener implements
				TreeSelectionListener {

			/**
			 * Called when the selection changes.
			 */
			public void valueChanged(TreeSelectionEvent ev) {
				System.out.println("s_valueChanged");
				// Manages the selection overlay
				if (elementOverlayManager != null) {
					handleElementSelection(ev);
				}

				DefaultMutableTreeNode mtn;
				mtn = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();

				if (mtn == null) {
					return;
				}

				if (rightPanel.getComponentCount() != 0) {
					rightPanel.remove(0);
				}

				Object nodeInfo = mtn.getUserObject();
				if (nodeInfo instanceof NodeInfo) {
					Node node = ((NodeInfo) nodeInfo).getNode();
					switch (node.getNodeType()) {
					case Node.DOCUMENT_NODE:
						documentInfo
								.setText(createDocumentText((Document) node));
						rightPanel.add(documentInfoPanel);
						break;
					case Node.ELEMENT_NODE:
						propertiesTable.setModel(new NodeCSSValuesModel(node));
						attributePanel.promptForChanges();
						attributePanel.setPreviewElement((Element) node);
						rightPanel.add(elementPanel);
						break;
					case Node.COMMENT_NODE:
					case Node.TEXT_NODE:
					case Node.CDATA_SECTION_NODE:
						characterDataPanel.setNode(node);
						characterDataPanel.getTextArea().setText(
								node.getNodeValue());
						rightPanel.add(characterDataPanel);
					}
				}

				splitPane.revalidate();
				splitPane.repaint();
			}

			protected String createDocumentText(Document doc) {
				StringBuffer sb = new StringBuffer();
				sb.append("Nodes: ");
				sb.append(nodeCount(doc));
			//	System.out.println(sb.toString());
				return sb.toString();

			}

			protected int nodeCount(Node node) {
				int result = 1;
				for (Node n = node.getFirstChild(); n != null; n = n
						.getNextSibling()) {
					result += nodeCount(n);
				}
				return result;
			}

			/**
			 * Processes element selection overlay.
			 * 
			 * @param ev
			 *            Tree selection event
			 */
			protected void handleElementSelection(TreeSelectionEvent ev) {
				System.out.println("s_handleElementSelection");
				TreePath[] paths = ev.getPaths();
				for (int i = 0; i < paths.length; i++) {
					TreePath path = paths[i];
					DefaultMutableTreeNode mtn = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					Object nodeInfo = mtn.getUserObject();
					if (nodeInfo instanceof NodeInfo) {
						Node node = ((NodeInfo) nodeInfo).getNode();
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (ev.isAddedPath(path)) {
								elementOverlayManager
										.addElement((Element) node);
							} else {
								elementOverlayManager
										.removeElement((Element) node);
							}
						}
					}
				}
				elementOverlayManager.repaint();
			}
		}

		/**
		 * To render the tree nodes.
		 */
		protected class NodeRenderer extends DefaultTreeCellRenderer {

			/**
			 * The icon used to represent elements.
			 */
			protected ImageIcon elementIcon;

			/**
			 * The icon used to represent comments.
			 */
			protected ImageIcon commentIcon;

			/**
			 * The icon used to represent processing instructions.
			 */
			protected ImageIcon piIcon;

			/**
			 * The icon used to represent text.
			 */
			protected ImageIcon textIcon;

			/**
			 * Creates a new NodeRenderer object.
			 */
			public NodeRenderer() {
				String s;
				s = resources.getString("Element.icon");
				elementIcon = new ImageIcon(getClass().getResource(s));
				s = resources.getString("Comment.icon");
				commentIcon = new ImageIcon(getClass().getResource(s));
				s = resources.getString("PI.icon");
				piIcon = new ImageIcon(getClass().getResource(s));
				s = resources.getString("Text.icon");
				textIcon = new ImageIcon(getClass().getResource(s));
			}

			/**
			 * Sets the value of the current tree cell.
			 */
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean sel, boolean expanded, boolean leaf,
					int row, boolean hasFocus) {
		//		System.out.println("s_getTreeCellRendererComponent");
				super.getTreeCellRendererComponent(tree, value, sel, expanded,
						leaf, row, hasFocus);
				switch (getNodeType(value)) {
				case Node.ELEMENT_NODE:
					setIcon(elementIcon);
					break;
				case Node.COMMENT_NODE:
					setIcon(commentIcon);
					break;
				case Node.PROCESSING_INSTRUCTION_NODE:
					setIcon(piIcon);
					break;
				case Node.TEXT_NODE:
				case Node.CDATA_SECTION_NODE:
					setIcon(textIcon);
					break;
				}
				return this;
			}

			/**
			 * Returns the DOM type of the given object.
			 * 
			 * @return the type or -1.
			 */
			protected short getNodeType(Object value) {
				DefaultMutableTreeNode mtn = (DefaultMutableTreeNode) value;
				Object obj = mtn.getUserObject();
				if (obj instanceof NodeInfo) {
					Node node = ((NodeInfo) obj).getNode();
					return node.getNodeType();
				}
				return -1;
			}
		}

		/**
		 * To display the CSS properties of a DOM node in a table.
		 */
		protected class NodeCSSValuesModel extends AbstractTableModel {

			/**
			 * The node.
			 */
			protected Node node;

			/**
			 * The computed style.
			 */
			protected CSSStyleDeclaration style;

			/**
			 * The property names.
			 */
			protected List propertyNames;

			/**
			 * Creates a new NodeAttributesModel object.
			 */
			public NodeCSSValuesModel(Node n) {
				node = n;
				if (viewCSS != null) {
					style = viewCSS.getComputedStyle((Element) n, null);
					propertyNames = new ArrayList();
					if (style != null) {
						for (int i = 0; i < style.getLength(); i++) {
							propertyNames.add(style.item(i));
						}
						Collections.sort(propertyNames);
					}
				}
			}

			/**
			 * Returns the name to give to a column.
			 */
			public String getColumnName(int col) {
				if (col == 0) {
					return resources.getString("CSSValuesTable.column1");
				} else {
					return resources.getString("CSSValuesTable.column2");
				}
			}

			/**
			 * Returns the number of columns in the table.
			 */
			public int getColumnCount() {
				return 2;
			}

			/**
			 * Returns the number of rows in the table.
			 */
			public int getRowCount() {
				if (style == null) {
					return 0;
				}
				return style.getLength();
			}

			/**
			 * Whether the given cell is editable.
			 */
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			/**
			 * Returns the value of the given cell.
			 */
			public Object getValueAt(int row, int col) {
				String prop = (String) propertyNames.get(row);
				if (col == 0) {
					return prop;
				} else {
					return style.getPropertyValue(prop);
				}
			}
		}

	} // class Panel

	/**
	 * To store the nodes informations
	 */
	protected static class NodeInfo {
		/**
		 * The DOM node.
		 */
		protected Node node;

		/**
		 * Creates a new NodeInfo object.
		 */
		public NodeInfo(Node n) {
			node = n;
		}

		/**
		 * Returns the DOM Node associated with this node info.
		 */
		public Node getNode() {
			return node;
		}

		/**
		 * Returns a printable representation of the object.
		 */
		public String toString() {
			if (node instanceof Element) {
				Element e = (Element) node;
				String id = e.getAttribute(SVGConstants.SVG_ID_ATTRIBUTE);
				if (id.length() != 0) {
					return node.getNodeName() + " \"" + id + "\"";
				}
			}
			return node.getNodeName();
		}
	}

	/**
	 * To store the node information for a shadow tree.
	 */
	protected static class ShadowNodeInfo extends NodeInfo {

		/**
		 * Creates a new ShadowNodeInfo object.
		 */
		public ShadowNodeInfo(Node n) {
			super(n);
		}

		/**
		 * Returns a printable representation of the object.
		 */
		public String toString() {
			return "shadow tree";
		}
	}

	/**
	 * To store the node information for an xbl:content node's selected content.
	 */
	protected static class ContentNodeInfo extends NodeInfo {

		/**
		 * Creates a new ContentNodeInfo object.
		 */
		public ContentNodeInfo(Node n) {
			super(n);
		}

		/**
		 * Returns a printable representation of the object.
		 */
		public String toString() {
			return "selected content";
		}
	}

	// -----------------------------

}
