package com.nari.util.ws;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;

public class ClientAuthenticationHandler extends AbstractHandler {

    private String username = null;

    private String password = null;

    public ClientAuthenticationHandler() { 
    }

    public ClientAuthenticationHandler(String username,String password) { 
    	this.username = username; 
        this.password = password;
    }

    public void setUsername(String username) { 
        this.username = username; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public void invoke(MessageContext context) throws Exception {
        //为SOAP Header构造验证信息
        Element el = new Element("header"); 
        context.getOutMessage().setHeader(el); 
        //context.getOutMessage().setProperty("CJ", "CJ");
        Element auth = new Element("AuthenticationToken"); 
        Element username_el = new Element("Username"); 
        username_el.addContent(username); 
        Element password_el = new Element("Password"); 
        password_el.addContent(password); 
        auth.addContent(username_el); 
        auth.addContent(password_el); 
        el.addContent(auth); 
    } 
}