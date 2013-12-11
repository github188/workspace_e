package com.nari.sun.svgTest;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class MyApplet extends Applet {
	
	
	@Override
	public void destroy() {
	
		System.out.println("destroy");
	}

	@Override
	public void init() {
	System.out.println("init");
	}

	@Override
	public void start() {
		System.out.println("start");
	}

	@Override
	public void stop() {
		System.out.println("stop");
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.green);
		g.fill3DRect(0, 0, getWidth()-1, getHeight()-1, true);
		
		g.setColor(Color.decode("0x0000ff"));
		g.drawString("你好,这是自定义的颜色", 20, 40);
		
		
		g.setColor(new Color(200,20,155));
		g.drawString("hello applet", 20, 20);
		
		
		//Image image =  getImage(this.getClass().getResource("desert.jpg"));
		//g.drawImage(image, 20, 60, this);
		
	}
	
}
