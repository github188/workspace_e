package com.nari.util.ws;

import java.io.IOException;
import java.util.Properties;


public class PropertiesUtils {
	private static Properties xfireProperties = new Properties();
	static {
		try {
			xfireProperties.load(PropertiesUtils.class
					.getResourceAsStream("/xfire.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public  static String getProperty(String name)
	{
		return xfireProperties.getProperty(name);
	}
}
