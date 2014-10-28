package com.bruce.gogo.mail.util;

/** 
 *
 */
public class Email {
	
	private	String tolist,subject,content,from,cclist;//目的地址串，主题，内容，来源，抄送地址串
	private String file;
	private String protocol,mailhost,mailport,user,password;
	private String auth;
	
	public String getTolist() {
		return tolist;
	}
	public void setTolist(String tolist) {
		this.tolist = tolist;
	}
	public String getCclist() {
		return cclist;
	}
	public void setCclist(String cclist) {
		this.cclist = cclist;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMailhost() {
		return mailhost;
	}
	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMailport() {
		return mailport;
	}
	public void setMailport(String mailport) {
		this.mailport = mailport;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}


}
