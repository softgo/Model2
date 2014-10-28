package com.bruce.gogo.mail.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.bruce.gogo.base.util.DateUtil;

/**
 *
 */
public class SendMail {

	public final Logger logger = Logger.getLogger(SendMail.class);
	
	private static SendMail instance = null; 

	private SendMail() { 

	} 

	public static SendMail getInstance() {
		if (instance == null) {
			instance = new SendMail();
		}
		return instance;
	} 

	public void send(Email email) {
		try {
			Properties p = new Properties(); // Properties p = System.getProperties();
			p.put("mail.smtp.auth", email.getAuth());
			p.put("mail.transport.protocol", email.getProtocol()==null?"":email.getProtocol());
			p.put("mail.smtp.host", email.getMailhost());
			p.put("mail.smtp.port", email.getMailport());
			// 建立会话
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // 建立信息
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			msg.setFrom(new InternetAddress(email.getFrom())); // 发件人

			// 发送,
			if (email.getTolist() != null) {
				InternetAddress[] iaToList = new InternetAddress().parse(email.getTolist());
				msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
			}

			// 抄送
			if (email.getCclist() != null) {
				InternetAddress[] iaToListcs = new InternetAddress().parse(email.getCclist());
				msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
			}

			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(email.getSubject()); // 主题
			msg.setText(email.getContent()); // 内容
			// 显示以html格式的文本内容
			messageBodyPart.setContent(email.getContent(),
					"text/html;charset=gbk");
			multipart.addBodyPart(messageBodyPart);

			if (email.getFile() != null) { // 附件
				addTach(email.getFile() + "entrance_alarm_"+ DateUtil.currentDateNext() + ".txt", multipart);
			}

			msg.setContent(multipart);
			// 邮件服务器进行验证
			Transport tran = session.getTransport("smtp");
			tran.connect(email.getMailhost(), email.getUser(), email
					.getPassword());
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			logger.info("邮件发送成功");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//添加一个附件 
	public void addTach(String file,Multipart multipart) throws MessagingException, UnsupportedEncodingException{ 
         MimeBodyPart mailArchieve = new MimeBodyPart(); 
         FileDataSource fds = new FileDataSource(file); 
         mailArchieve.setDataHandler(new DataHandler(fds)); 
         mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),"GBK","B")); 
         multipart.addBodyPart(mailArchieve); 
	} 
	
	/**
	 * 获取邮件发送列表
	 * @param mailArray
	 * @return
	 */
	public String getMailList(String[] mailArray) { 
		StringBuffer toList = new StringBuffer(); 
		int length = mailArray.length; 
		if (mailArray != null && length < 2) { 
			toList.append(mailArray[0]); 
		} else { 
			for (int i = 0; i < length; i++) { 
				toList.append(mailArray[i]); 
				if (i != (length - 1)) { 
					toList.append(","); 
				} 
			} 
		} 
		return toList.toString(); 
	} 
	
	public Email getMailInfo() throws UnsupportedEncodingException{
		Properties prop = new Properties();
		InputStream fis =  getClass().getClassLoader().getResourceAsStream("mail.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Email email = new Email();
		email.setMailhost(prop.getProperty("mailhost"));
		email.setFrom(prop.getProperty("from"));
		email.setUser(prop.getProperty("user"));
		email.setPassword(prop.getProperty("password"));
		email.setMailport(prop.getProperty("smtpport"));
		email.setAuth(prop.getProperty("auth"));
		email.setCclist(prop.getProperty("ctoMailList"));
		email.setTolist(prop.getProperty("toMailList"));
		email.setProtocol(prop.getProperty("protocol"));
		email.setContent("您好：<br><br>新添加应用，请查看管理后台并修改相应合作方配置信息");
		email.setFile(null);
		email.setSubject("新添加应用");
		return email;
	}
	
	/**
	 * 写文件
	 */
	public void writerFile(String filePath,List<?> list){
		try {
			StringBuffer buffer = new StringBuffer();
			String seperator = System.getProperty("line.separator");
			if (seperator==null) {
				seperator = "\r\n";
			}
			File dest = new File(filePath);
			BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));
			logger.info("export size=="+list.size());
			buffer.append("入口ID" + "\t\t");
			buffer.append("入口名称" + "\t\t\t\t");
			buffer.append("时间" + "\t\t");
			buffer.append("下载数" + "\t\t");
			buffer.append("下载失败数" + "\t\t");
			buffer.append("抽取数" + "\t\t");
			buffer.append("抽取失败数" + "\t\t");
			buffer.append(seperator);
			writer.write(buffer.toString()); 
			buffer.delete(0, buffer.length());
			for(int i=0;i<list.size();i++){
//				Loginfo info = list.get(i);
//				int eid = info.getEntranceId();
//				Entranceinfo einfo = EntranceManagerAction.getEntranceById(eid);
//				buffer.append(eid+"\t\t");
//				if (einfo != null) {
//					buffer.append(einfo.getEname()+"\t\t");
//				} else {
//					buffer.append("none"+"\t\t");
//				}
//				buffer.append(info.getStime()+"\t\t");
//				buffer.append(info.getDownloadNum()+"\t\t"+info.getDownloadFailedNum()+"\t\t");
//				buffer.append(info.getExtractNum() + "\t\t" + info.getExtractFailedNum());
				buffer.append(seperator);
				writer.write(buffer.toString()); 
				buffer.delete(0, buffer.length());
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	public static void main(String args[]) { 
//		SendMail send = SendMail.getInstance(); 
//		try {
//			Email mailVo = send.getMailInfo();
//			send.writerFile(mailVo.getFile()+"entrance_alarm_"+DateTime.yesterDateNext()+".txt");
//			send.send(send.getMailInfo());
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	} 
}
