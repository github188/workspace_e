package com.nari.webserviceconfig;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;
import org.jdom.Text;

/**
 * WebService服务认证类
 * 对传入的SOAP Header的信息进行验证
 * 客户端需要加入认证信息
 */
public class AuthenticationHandler extends AbstractHandler{

	 public void invoke(MessageContext cfx) throws Exception{
		 Element userElem = null;
		 Element passwordElem = null;
		 String username = null;
         String password = null;
         
         Text usertex = null;
         Text passwordtex = null;
         Element scplAuthen = null;
         if(cfx.getInMessage().getHeader() == null){
             throw new org.codehaus.xfire.fault.XFireFault("请求必须包含验证信息",org.codehaus.xfire.fault.XFireFault.SENDER);
         }
         cfx.getOutMessage();
       //  cfx.getInMessage().getProperty("CJ");
         //采集系统认证 xfire
         if (cfx.getInMessage().getHeader().getChild("AuthenticationToken") != null) {
        	 Element ele = (Element) (cfx.getInMessage().getHeader().getContent().get(0));
        	 userElem = (Element) ele.getContent().get(0);
             passwordElem = (Element) ele.getContent().get(1);
             usertex = (Text)userElem.getContent().get(0);
             passwordtex = (Text)passwordElem.getContent().get(0);
             username = usertex.getText();
             password = passwordtex.getValue();
         } else {
             //营销验证 axis 1.4
//             userElem = (Element) (cfx.getInMessage().getHeader().getContent().get(0));
//             passwordElem = (Element) (cfx.getInMessage().getHeader().getContent().get(1));
//             usertex = (Text)userElem.getContent().get(0);
//             passwordtex = (Text)passwordElem.getContent().get(0);
//             username = usertex.getText();
//             password = passwordtex.getValue();
             //四川盘龙验证
        	 scplAuthen =(Element) (cfx.getInMessage().getHeader().getContent().get(1));
        	 userElem = (Element) (scplAuthen.getContent().get(1));
        	 
        	 passwordElem = (Element) (scplAuthen.getContent().get(3));
        	 usertex = (Text)userElem.getContent().get(0);
        	 passwordtex = (Text)passwordElem.getContent().get(0);
        	 username = usertex.getValue();
        	 password = passwordtex.getValue();
             
         }

         try{ 
            if(username.equals("sea") && password.equals("sea")){
            	System.out.println("身份验证通过");
            }else{
        	    throw new Exception();
        	}	   
         }catch(Exception e){ 
            throw new org.codehaus.xfire.fault.XFireFault("非法的用户名和密码",org.codehaus.xfire.fault.XFireFault.SENDER); 
         } 
	 }
}