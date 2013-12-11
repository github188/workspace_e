import java.text.DateFormat;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class lifeCycleApplet extends JApplet {
	JTextArea textarea =  new JTextArea();
	public lifeCycleApplet()
	{
		add(new JScrollPane(textarea));
	}
	private void addText(String text)
	{
		DateFormat df = DateFormat.getDateTimeInstance();
		String msg = df.format(new java.util.Date())+":"+text+"\n";
		textarea.append(msg);
		System.out.println(msg);
		
	}
	@Override
	public void destroy() {
		addText("destroy");
	}
	@Override
	public void init() {
		addText("init");
	}
	@Override
	public void start() {
		addText("start");
	}
	@Override
	public void stop() {
		addText("stop");
	}
	
	
}
