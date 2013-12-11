package com.nari.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author 陈建国
 *
 * @创建时间:2010-1-26 上午09:01:57
 *
 * @类描述:
 *	
 */
public class ModuleLogFilter implements Filter {

	static Logger LOG = Logger.getLogger(ModuleLogFilter.class);
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		StringBuffer msg = new StringBuffer();
		if (LOG.isDebugEnabled()) {
			msg.append("\n		<request>\r\n")
			   .append("			<url>")
			   .append(request.getServletPath())
			   .append("</url>\r\n");
			if (request.getParameter("method") != null) {
				msg.append("			<method>" + request.getParameter("method"))
				   .append("</method>\r\n");
			}

			Iterator itParams = request.getParameterMap().entrySet().iterator();
			msg.append("		</request>\n");
			while (itParams.hasNext()) {

				Map.Entry param = (Map.Entry) itParams.next();
				if (!("method").equals(param.getKey())) {
					String[] values = (String[]) param.getValue();
					for (int i = 0; i < values.length; i++) {
						msg.append("			<param name=\"")
						   .append(param.getKey())
						   .append("\">")
						   .append(values[i])
						   .append("</param>\r\n");
					}
				}
			}
			LOG.debug(msg);
		}
		chain.doFilter(sRequest, sResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
