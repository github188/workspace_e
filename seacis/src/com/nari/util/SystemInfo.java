package com.nari.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.nari.exception.ExceptionHandle;

public class SystemInfo {
	private static boolean isWindows = false;
	private long lastUsed = 0;
	private long lastTotal = 0;
	private static Properties prop = null;
	public SystemInfo() {
		initOsSystem();
	}
	private void initOsSystem() {		
		try {
			if(prop == null) {
				prop = new Properties();
				prop.load(this.getClass().getClassLoader().getResourceAsStream("webservice.properties"));
			}
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
		String osName =prop.getProperty("webservice.os");
		osName = osName == null ? "" : osName.trim();
		if (osName.indexOf("Windows") > -1) {
			isWindows = true;
		}
	}
	/**
	 * 取总内存
	 * @return
	 */
	public long getTotalMemory(){
		long TotalMemoryValue = 0;
		TotalMemoryValue = Runtime.getRuntime().totalMemory()>>>20;
		return TotalMemoryValue;
	}
	/**
	 * 取空闲内存
	 * @return
	 */
	public long getFreeMemory(){
		long FreeMemoryValue = 0;
		FreeMemoryValue = Runtime.getRuntime().freeMemory()>>>20;
		return FreeMemoryValue;
	}	
	/**
	 * 取CPU消耗
	 * @return
	 */
	public double getCPUUsage() {
		String[] scriptCmds = null;
		if ( isWindows ) {
			scriptCmds = new String[] {
					"strComputer = \".\"",
					"Set objWMIService = GetObject(\"winmgmts:\" _",
					" & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\")",
					"Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor \",,48)",
					"load = 0", "n = 0", "For Each objItem in colItems",
					" load = load + objItem.LoadPercentage", " n = n + 1",
					"Next", "Wscript.Echo (load/n)" };
		} else {
			scriptCmds = new String[] {
					"cat /proc/stat | head -n 1"
					/*"user=`cat /proc/stat | head -n 1 | awk '{print $2}'`",
					"nice=`cat /proc/stat | head -n 1 | awk '{print $3}'`",
					"system=`cat /proc/stat | head -n 1 | awk '{print $4}'`",
					"idle=`cat /proc/stat | head -n 1 | awk '{print $5}'`",
					"iowait=`cat /proc/stat | head -n 1 | awk '{print $6}'`",
					"irq=`cat /proc/stat | head -n 1 | awk '{print $7}'`",
					"softirq=`cat /proc/stat | head -n 1 | awk '{print $8}'`",
					"let used=$user+$nice+$system+$iowait+$irq+$softirq",
					"let total=$used+$idle", "echo \"$used $total\""*/					
					};
		}
		return parseResult(execute(scriptCmds));
	}
	private String[] execute(String[] commands) {
		String[] strs = null;
		File scriptFile = null;
		try {
			List<String> cmdList = new ArrayList<String>();
			if ( isWindows ) {
				scriptFile = new File("monitor.vbs");
				cmdList.add("CMD.EXE");
				cmdList.add("/C");
				cmdList.add("CSCRIPT.EXE");
				cmdList.add("//NoLogo");
			} else {
				scriptFile = new File("monitor.sh");
				cmdList.add("/bin/bash");
			}
			if( !scriptFile.exists() || scriptFile.length() == 0 ){
				PrintWriter writer = new PrintWriter(scriptFile);
				for (int i = 0; i < commands.length; i++) {
					writer.println(commands[i]);
				}
				writer.flush();
				writer.close();
			}
			String fileName = scriptFile.getCanonicalPath();
			cmdList.add(fileName);

			ProcessBuilder pb = new ProcessBuilder(cmdList);
			Process p = pb.start();
			p.waitFor();

			String line = null;
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			List<String> stdoutList = new ArrayList<String>();
			while ((line = stdout.readLine()) != null) {
				stdoutList.add(line);
			}

			BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			List<String> stderrList = new ArrayList<String>();
			while ((line = stderr.readLine()) != null) {
				stderrList.add(line);
			}
			if( stderrList.size()>0 ){
				//log.warn("CPUMonitor stderr="+stderrList);
			}
			strs = stdoutList.toArray(new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*finally {
			if (scriptFile != null)
				scriptFile.delete();
		}*/		
		return strs;
	}
	private double parseResult(String[] strs) {
		double value = 0;
		if ( isWindows ) {
			String strValue = strs[0];
			try{
				value = Double.parseDouble(strValue);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			String strValue = strs[0];
			String[] values = strValue.split(" ");
			Vector<String> vv = new Vector<String>(10);
			for( String v: values ){
				if( v.length() != 0 )
					vv.add(v);
			}
			values = vv.toArray(new String[vv.size()]);
			if (values.length == 2) {
				long used = Long.parseLong(values[0]);
				long total = Long.parseLong(values[1]);

				if (lastUsed > 0 && lastTotal > 0) {
					long deltaUsed = used - lastUsed;
					long deltaTotal = total - lastTotal;
					if (deltaTotal > 0) {
						value = ( (long)( ((deltaUsed * 100) / deltaTotal) * 10) ) / 10;
					}
				}
				lastUsed = used;
				lastTotal = total;
			}
			else if(values.length >= 8 ){
				int index = 1;
				long _user = Long.parseLong(values[index++]);
				long _nice = Long.parseLong(values[index++]);
				long _system = Long.parseLong(values[index++]);
				long _idle = Long.parseLong(values[index++]);
				long _iowait = Long.parseLong(values[index++]);
				long _irq = Long.parseLong(values[index++]);
				long _softirq = Long.parseLong(values[index++]);
				long used = _user + _nice + _system + _iowait + _irq + _softirq ;
				long total = used + _idle ;
				if (lastUsed > 0 && lastTotal > 0) {
					long deltaUsed = used - lastUsed;
					long deltaTotal = total - lastTotal;
					if (deltaTotal > 0) {
						value = ( (long)( ((deltaUsed * 100) / deltaTotal) * 10) ) / 10;
					}
				}
				lastUsed = used;
				lastTotal = total;
			}
		}
		return value;
	}
}