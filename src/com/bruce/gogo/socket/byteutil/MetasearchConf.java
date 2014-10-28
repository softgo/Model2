package com.bruce.gogo.socket.byteutil;

import java.util.Properties;

import com.bruce.gogo.base.util.PropertiesUtils;

/**
 *普通提交配置类
 *
 */
public class MetasearchConf {
	
	Properties pros;

	String ip;

	int port;

	String charset;

	String confpath;

	public MetasearchConf() throws Exception {
		this("App_nw_oi.conf");
	}

	public MetasearchConf(String fpath) throws Exception {
		confpath = fpath;
		pros = PropertiesUtils.getProperties("/configSocket/task_config.properties");
		init();
	}

	public void init() throws Exception {

		this.ip = pros.getProperty("socket.ip");
		this.port = Integer.parseInt(pros.getProperty("socket.port").trim().toLowerCase());
		this.charset = pros.getProperty("socket.charset");
	}

	public void check() throws Exception {
		checkip(ip);
		checkport(port);
		checkcharset(charset);
	}

	public void checkip(String ip) {
//		if (!ip.equals("127.0.0.1"))
//			;
//		throw new RuntimeException(ip
//				+ " is invalidation value.must be 127.0.0.1");
	}

	public void checkport(int port) {
//		if (port != 4009)
//			throw new RuntimeException(port
//					+ " is invalidation value.must be 4009");
	}

	public void checkcharset(String charset) {
//		if (!charset.equals("gb2312"))
//			throw new RuntimeException(charset
//					+ "is invalidation value. must be gb2312");
	}

	public void checkcount(int count) {
//		if (count != 10)
//			throw new RuntimeException(count
//					+ "is invalidation value.must be 10");
	}

	public void checkfilepath(String filepath) {
//		if (!filepath.equals("e:\\metasearchresult.xml"))
//			throw new RuntimeException(filepath
//					+ "is invalidation value.must be e:\\metasearchresult.xml");
	}

	public void checklength(int length) {
//		if (length != length)
//			throw new RuntimeException(length
//					+ " is invalidation value.must be 1024");
	}

	public void checkforcefresh(int forcefresh) {
//		if (forcefresh != 0 || forcefresh != 1)
//			throw new RuntimeException(forcefresh
//					+ "is invalidation value.must be in 1 or 0");
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getConfpath() {
		return confpath;
	}

	public void setConfpath(String confpath) {
		this.confpath = confpath;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Properties getPros() {
		return pros;
	}

	public void setPros(Properties pros) {
		this.pros = pros;
	}
}