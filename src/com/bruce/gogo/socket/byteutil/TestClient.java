package com.bruce.gogo.socket.byteutil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import com.bruce.gogo.base.util.DataConvert;

/**
 * socket 字节传输.客户端.
 * 
 * @author admin
 *
 */
public class TestClient {
	public static void main(String[] args) throws Exception {
		sendMsg();
	}
	
	/**
	 * 发送数据.
	 */
	public static void sendMsg() {
		Socket socket = null;
		OutputStream outs = null;
		try {
			socket = new Socket("127.0.0.1", TestServer.PORT);
			outs = socket.getOutputStream();
			short type = 3; //协议类型.		
			byte[] datas = assemblyRelationshipProtocol(type);
			
			//发送数据.
			outs.write(datas,0,datas.length);
			outs.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outs.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*********************************要测试的协议******************************************/
	
	/**
	 * 测试协议的
	 */
	public static byte[] assemblyRelationshipProtocol(int type) {
		try {
			//List<byte[]> protoList = new ArrayList<byte[]>();
			List<byte[]> protoList = new LinkedList<byte[]>();
			int dataLen = 0;
			
			//要操作的关系类型个数
			int relNum = 1;
			byte[] numByte = DataConvert.intToByteArray(relNum);
			dataLen += 4;
			
			//关系名称
			String relName = "朋友";
			byte[] nameByte = relName.getBytes();
			int relNameLen = nameByte.length;
			
			//关系名称长度.
			byte[] lenByte = DataConvert.intToByteArray(relNameLen);
			dataLen += 4;
			
			//关系名称
			dataLen += relNameLen;
			System.out.println("传入的关系名称长度是："+relNameLen);
			
			//start 2014.7.4
			//关系权重
			int right  = 1;
			byte[] rightByte = DataConvert.intToByteArray(right);
			dataLen += 4;
			
			//是否多值，0为单值，1为多值	
			String IsMult = "1";
			byte[] Is_multiByte = IsMult.getBytes();
			dataLen += 1;
			//end 2014.7.4
			
			byte[] headProtoByte = null;
			short add=8,del=9;
			if (type == 1) { //添加.
				headProtoByte = assemblyHeadProtocol(add, dataLen);
			} else {
				headProtoByte = assemblyHeadProtocol(del, dataLen);
			}
			
			protoList.add(headProtoByte);
			System.out.println("头文件的信息是："+new String(headProtoByte)+"， 文件头的信息长度是："+headProtoByte.length+	",权重的字节长度是："+rightByte.length+ ",是否多值的字节长度是："+Is_multiByte.length);
			
			protoList.add(numByte);
			protoList.add(lenByte);
			protoList.add(nameByte);
				
			protoList.add(rightByte);
			protoList.add(Is_multiByte);
			
			System.out.println("添加删除最终的文件串是："+new String(DataConvert.sysCopy(protoList))+", 发送的字节长度是："+DataConvert.sysCopy(protoList).length);
			return DataConvert.sysCopy(protoList);
		
		} catch (Exception e) {
			System.out.println("拼接字节串出现了问题："+e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 请求数据包头
	 * @param command_type 命令类型
	 * @param total_data_len 数据总长（除包头外）+20即为后续所有数据总长
	 * @return
	 */
	public static byte[] assemblyHeadProtocol(short command_type, int total_data_len) {
		
		//List<byte[]> protoList = new ArrayList<byte[]>();
		List<byte[]> protoList = new LinkedList<byte[]>();
		String VERIFY_CODE = "PEOPLE10";
		byte[] verifyCodeByte = VERIFY_CODE.getBytes();//[8]验证码
		byte[] dataLenByte = DataConvert.intToByteArray(total_data_len + 20);//[4]数据总长
		
		short type = 3;  //协议类型
		byte[] protoTypeByte = DataConvert.shortToBytes(type);//[2]协议类型
		byte[] commandTypeByte = DataConvert.shortToBytes(command_type);//[2]命令类型
		byte[] retain_field_16 = new byte[16];//16位保留字段
		
		protoList.add(verifyCodeByte);//[8]验证码
		protoList.add(dataLenByte);//[4]数据总长
		protoList.add(protoTypeByte);//[2]协议类型
		protoList.add(retain_field_16);//[16]保留字段
		protoList.add(commandTypeByte);//[2]命令类型
		byte[] proto = DataConvert.sysCopy(protoList);
		
		System.out.println("最终给的协议数据是："+new String(proto));
		
		return proto;
	}

	/**
	 * char 30 由数据库整数转换为byte[30]
	 * @param id
	 * @return
	 */
	private static byte[] convertTo30ByteArr(String id) {
		String str = id + "";
		if (str.length() == 30) {
			return str.getBytes();
		}
		StringBuffer buff = new StringBuffer();
		buff.append(str);
		for (int i = 0; i < 30 - str.length(); i++) {
			buff.append(" ");
		}
		return buff.toString().getBytes();
	}
}
