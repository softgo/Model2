package com.bruce.gogo.socket.byteutil;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;



public class SysConf {

	String filename;

	public SysConf(String filename) {
		this.filename = filename;
	}

	public Properties read() throws Exception {
		String path = getConfPath();
		Properties props = new Properties();
		InputStream is = new java.io.FileInputStream(new File(path + filename));
		props.load(is);
		is.close();
		props.put("system.path", sysPath);
		return props;
	}

	private static String confPath;

	private static String sysPath;

	private static String webPath;

	public String getConfPath() throws Exception {
		if (confPath != null)
			return confPath;
		sysPath = sysPath();
		confPath = sysPath + "conf" + File.separator;
		return confPath;
	}

	public String getSysPath() throws Exception {
		if (sysPath != null)
			return sysPath;
		sysPath = sysPath();
		confPath = sysPath + "conf" + File.separator;
		return sysPath;
	}

	public String getWebPath() throws Exception {
		if (webPath != null)
			return webPath;
		sysPath();
		return webPath;
	}

	private String sysPath() throws Exception {
		ClassLoader cl = this.getClass().getClassLoader();
		String classname = this.getClass().getName().replace('.', '/')
				+ ".class";
		String res = null;
		if (cl != null) {
			java.net.URL url = cl.getResource(classname);
			if (url != null) {
				String path = url.getFile();
				int fileStrPosition = path.indexOf("file:/");
				int begin = 0;
				int end = path.length();
				if (fileStrPosition >= 0)
					begin = fileStrPosition + 5;
				end = path.indexOf("WEB-INF/");
				if (end > 0) {
					String rf = path.substring(begin, end);
					webPath = rf;
					File f = new File(rf + "WEB-INF/conf/");
					if (f.exists())
						res = new File(rf + "WEB-INF/").getAbsolutePath()

						+ File.separator;
					else
						res = new File(rf).getParentFile().getAbsolutePath()
								+ File.separator;
				} else { //
					res = new File(".").getAbsolutePath();
					res = res.substring(0, res.length() - 1);
				}
			}
		}
		return java.net.URLDecoder.decode(res, "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		SysConf sc = new SysConf("log4j.conf");
		System.out.println(sc.read());
	}
}
