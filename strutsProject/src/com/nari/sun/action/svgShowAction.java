package com.nari.sun.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.svg.SVGDocument;

import com.nari.model.person;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

public class svgShowAction extends ActionSupport {
	
	
	@Override
	public String execute() throws Exception {
		System.out.println("-------------ReprotTypeSvgAction----");
		//File file = new File("D:\\workspace\\strutsProject\\src\\com\\nari\\sun\\action\\1.svg");
		//InputStream in = new BufferedInputStream(new FileInputStream(file));
		//SVGDocument svgDocument = null;
		// try {
		// String parser = XMLResourceDescriptor.getXMLParserClassName();
		// SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		// String uri = "";
		// svgDocument = (SVGDocument) f.createDocument(uri, in);
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/x-java-serialized-object");
		response.setHeader("Cache-Control", "no-cache");
		OutputStream out = response.getOutputStream();
	    ObjectOutputStream  objStream = new ObjectOutputStream(out);
	    person p = new person();
	    p.setUsername("sungang");
	    p.setPassword("1111");
	    objStream.writeObject(p);
	    // System.out.println(device.getChildDevs().size());
	    // objStream.writeObject(svgDocument);
	    // objStream.close();
	    out.flush();
	    out.close();
	    objStream.close();
		return SUCCESS;
	}

}
