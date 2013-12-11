package com.nari.coherence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author 陈建国
 *
 * @创建时间:2010-1-20 下午02:18:02
 *
 * @类描述: 
 *	
 */
public class FrontScheme {
	
	private static final Logger log = Logger.getLogger(FrontScheme.class);
	/**
	 * @desc 获取前置机机器名
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getName () throws IOException{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("front.properties");
		Properties p = new Properties();
		p.load(inputStream);
		return p.getProperty("machine.name");
	}

	public static void main(String[] args){
		FrontScheme frontScheme = new FrontScheme();

		try {
			System.out.println(frontScheme.getName());
		} catch (IOException e) {
			System.out.println("没有找到文件！");
			e.printStackTrace();
		}
	}
}
