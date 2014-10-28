package com.bruce.gogo.socket.byteutil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.bruce.gogo.base.util.DataConvert;

/**
 * socket 字节传输.服务端.
 * 
 * @author admin
 *
 */
public class TestServer {

	public static int PORT = 10001;

	/**
	 * test it 
	 * @param agrs
	 */
	public static void main(String[] agrs) {
		accepts();
	}
	
	/**
	 * 接收socket传递来的数据
	 */
	public static String accepts() {
		ServerSocket serSocket = null;
		Socket socket = null;
		InputStream ins = null;
		BufferedInputStream bis = null; 
		
		byte[] byte_identifyCode = null;
		String resStr = "";
		String verifyCode = "";
		try {
			serSocket = new ServerSocket(PORT); //设置端口.
			socket = serSocket.accept(); //接收数据.
			ins = socket.getInputStream(); //获得流信息.
			// 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
			bis = new BufferedInputStream(ins, 1024); 
			
			// 先取出标准头中内容长度8字节之前的内容【亦称验证码】
			byte_identifyCode = new byte[8]; 
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				System.out.println("读取标准头的前8字节时报错!!");
			}
			verifyCode = new String(byte_identifyCode); // 验证码
			System.out.println("验证码：" + verifyCode);
			
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			
			//真正数据长度
			int dataLength = length -20;
			System.out.println("后续内容长度：" + length);
			System.out.println("真正数据长度：" + dataLength);
			if (nresult2 < 0) {
				System.out.println("image:对不起，读取标准头的后4个标识长度字节时报错！！");
			}
			
			byte[] protoTypeArr = new byte[2];
			bis.read(protoTypeArr, 0, 2);
			short protoType = DataConvert.bytesToShort(protoTypeArr);
			System.out.println("协议类型：" + protoType);
			
			//保留字段
			byte[] otherArr = new byte[16];
			bis.read(otherArr, 0, 16);
			
			//命令类型
			byte[] commandTypeArr = new byte[2];//命令类型
			bis.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			System.out.println("命令类型：" + commandType);

			//要操作的关系类型个数
			byte[] numbyte = new byte[4];
			bis.read(numbyte,0,4);
			int num = DataConvert.bytesToInt(numbyte);
			System.out.println("要操作的类型个数是："+num);
			
			//关系名称长度.
			byte[] lennamebyte = new byte[4];
			bis.read(lennamebyte, 0, 4);
			int len = DataConvert.bytesToInt(lennamebyte);
			System.out.println("关系名称长度是："+len);
			
			//关系名称
			byte[] namebyte = new byte[4];
			bis.read(namebyte, 0, 4);
			//String relName =new String(namebyte,"utf-8"); 
			String relName =new String(namebyte); 
			System.out.println("关系名称是："+relName);	
			
			
			//关系权重
			byte[] rightbyte = new byte[4];
			bis.read(rightbyte, 0, 4);
			int right = DataConvert.bytesToInt(rightbyte);
			System.out.println("关系权重是："+right);
			
			//是否多值，0为单值，1为多值	
			byte[] ismutilbyte = new byte[1];
			bis.read(ismutilbyte,0,1);
			String ismutil = new String(ismutilbyte);
			System.out.println("是否多值："+ismutil);
			
			return String.valueOf("OK");
			
		} catch (Exception e) {
			System.out.println("协议解析发生异常：" + e.getMessage());
			resStr = null;
		}finally{
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e) {
		   }
		}
		return resStr;
	}
}
