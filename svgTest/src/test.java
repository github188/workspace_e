import java.applet.Applet;
import java.awt.Graphics;


public class test extends Applet {

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
	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		g.drawString("hahahaahah", 0, 30);
	}
	
}
