package com.bruce.gogo.socket.byteutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.bruce.gogo.base.util.DataConvert;
import com.bruce.gogo.base.util.PropertiesUtils;

/**
 * 人物库管理后台与集成管理后台通信协议的拼装
 * 
 * 使用时候，需要将该 server 放入到 spring的bean 文件中去,否则不起作用.
 *
 */
public class PersonaeServer implements Runnable {
	
	public final Logger logger = Logger.getLogger(PersonaeServer.class);
	
	private ServerSocket server = null;  
	
	public static ThreadPoolExecutor executor = null;
	/**
	 * 初始化方法，工程启动时调用
	 */
	public  void initRevc(){
		  try {
			  executor = new ThreadPoolExecutor(200,300,200,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(300),
					  new ThreadPoolExecutor.CallerRunsPolicy()
			  );
			  logger.debug("PersonaeServer start to run");  
			  server = new ServerSocket(Integer.parseInt(getOperatePort("socket_port","25105")));
			  PersonaeServer recv = new PersonaeServer();
			  recv.setServer(server);
			  Thread thread = new Thread(recv);
			  thread.start(); 
			 
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
	}
	
	public void run() {
		Socket client = null;	
		while(true){
			try {
				logger.debug("PersonaeServer befor server.accept()");
			    client = this.getServer().accept();
			    client.setSoTimeout(60000*10);
			    logger.debug("PersonaeServer after server.accept()");
			    //socket 中传递过来的数据.这个是程序获取数据的入口
			    //QueryRead.parseByteReader(new BufferedInputStream(client.getInputStream()), client);
			    //2014-8-4
			    QueryReader.parseByteReader(new BufferedInputStream(client.getInputStream()), client);
			    
			    //resSuccess(client);//发送接收成功标识
			} catch (IOException e) {
				logger.error(e.getMessage());
				//resFailed(client);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				//resFailed(client);
			  }
          }
	}
	
	/**
	  * 读配置文件取接收端口
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	private String getOperatePort(String property,String defaultValue){
		String value =  PropertiesUtils.getProperty(property, defaultValue, "/configSocket/task_config.properties");
		logger.debug(property+" value=="+value);
	    return value;
	}
	
	/**
	 * 成功发送的同步响应串
	 * @return
	 */
	public void resSuccess(Socket client){
		try {
			BufferedOutputStream bfOut = new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			short ordertype = 0;//添加
			bfOut.write(getResSuccessProto(ordertype,0));
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 拼装接收的返回协议
	 * @param order:命令类型,content:命令主体
	 * @return
	 */
	public  byte[] getResSuccessProto(short order,int status){
		byte[] verfy = "PEOPLE10".getBytes();
		int length = 20;//数据总长,后台不解析，故可以用0
		short proto = 1;//协议类型
		byte[] protoArr = DataConvert.shortToBytes(proto); 
		short command_type = 1;//命令类型
		byte[] commandArr = DataConvert.shortToBytes(command_type);
		byte[] keep = new byte[12];
		byte[] statusArr = DataConvert.intToByteArray(status);
		
		List<byte[]> alist = new ArrayList<byte[]>();
		alist.add(verfy);//验证码
		alist.add(DataConvert.intToByteArray(length));//数据总长
		alist.add(protoArr);//协议类型
		alist.add(commandArr);//命令类型
		alist.add(keep);//保留字段
		alist.add(statusArr);//状态码：0成功 -1失败
		
		byte[] headProto = DataConvert.sysCopy(alist);
		return headProto;
	}
	
	/**
	 * 失败发送的同步响应串
	 * @return
	 */
	private void resFailed(Socket client){
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议	
			short ordertype = 0;
			bfOut.write(getResSuccessProto(ordertype,-1));
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}
}
