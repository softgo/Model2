package com.bruce.gogo.socket.byteutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bruce.gogo.base.util.DataConvert;
import com.bruce.gogo.base.util.DateUtil;
import com.bruce.gogo.base.util.StringUtil;

/**
 * @version: 1.0
 */
public class QueryReader extends ByteManageAbstractUtil {
	
	protected static Logger logger = Logger.getLogger(QueryReader.class);
	public static final String DEFAULT_SPLIT = "|";
	public static final int COMMAND_TYPE_ADD = 0;//添加、更新数据
	public static final int COMMAND_TYPE_DELETE = 1;//删除数据pid
	public static final int COMMAND_TYPE_WARNNING = 2;//报警
	private String readStr;
	private int errorCode = 0;
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getReadStr() {
		return readStr;
	}

	public void setReadStr(String readStr) {
		this.readStr = readStr;
	}
	
	/**
	 * 解析输入流 BufferedInputStream
	 * 
	 * @param reader
	 * @return
	 */
	public static String parseReader(BufferedInputStream reader) {
		byte[] byte_total = null;
		byte[] byte_identifyCode = null;
		String result = "";
		QueryReader readObj = new QueryReader();
		try {
			// 用文件随时记录
			FileOutputStream fos = new FileOutputStream(readObj.getClass().getClassLoader().getResource("socketRes.dat").getPath());

			// BBSDP1.13005 5680 === 先读取前十六个字节：5574之前的
			byte_identifyCode = new byte[16]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = reader.read(byte_identifyCode, 0, 16);
			if (nresult < 0) {
				fos.write(("对不起，读取标准头的前16字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!").getBytes());
				logger.info("ERROR:the package title error;");
				return "";
			}
			fos.write(byte_identifyCode);
			result += new String(byte_identifyCode); // 连接标准头的前16个字节
			// 除验证码外包的长度 5680
			byte[] byte_alllen = new byte[8];
			int nresult2 = reader.read(byte_alllen, 0, 8);
			fos.write(byte_alllen);
			if (nresult2 < 0) {
				fos.write(("对不起，读取标准头的后8个字节时报错：QueryRead-->parseReader() ERROR: the second package did not post!!").getBytes());
				logger.error("ERROR: the second package did not post");
				return "";
			}
			result += new String(byte_alllen); // 连接标准头的后8个字节

			int rlen = new Integer(new String(byte_alllen).trim()).intValue();
			byte_total = new byte[rlen];

			int resCount = 0;
			while (resCount < rlen) {
				int aa = reader.read(byte_total, resCount, rlen - resCount);
				if (aa == -1) {
					break;
				}
				resCount += aa;
			}
			result += new String(byte_total, "GBK").trim(); // 连接后面所有的内容字节
			fos.write(byte_total);
			fos.flush();
			fos.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 读取内容. modify 2014.7.1
	 * @param reader
	 * @param length
	 * @return
	 */
	public static byte[] getReturnXml(BufferedInputStream reader, int length){
		byte[] allArr = new byte[length];
		int bytesRead = 0;
		int cur_read_len = 204800;//每次200K数据
		try { //如果出现异常 退出循环
         while (true) {				
        	if(length - bytesRead > 204800)
        	{
        		cur_read_len = 204800;
        	}
        	else
        		cur_read_len = length - bytesRead;  //1---1583
        	
            int bytesReadLength = 0;
			bytesReadLength = reader.read(allArr, bytesRead, cur_read_len);
			
			if(bytesReadLength==-1)
				break;
			bytesRead+=bytesReadLength;
			
			if (bytesRead >= length) {// end of InputStream
				break;
			}
          }
		} catch (IOException e) {
			logger.info("读取流文件出错了,错误是："+e.getMessage());
		} 
		return allArr;
	}

	/**
	 * 后台系统推送协议解析
	 * @param reader
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static void parseByteReader(BufferedInputStream reader,Socket client) {
		//"fasdf";
		byte[] byte_identifyCode = new byte[8]; // 8字节【验证码】PEOPLE10
		int nresult = 0;
		try {
			nresult = reader.read(byte_identifyCode, 0, 8);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!").getBytes());
			logger.info("ERROR:the package title error;");
		}
		String identifyCode  = new String(byte_identifyCode); // 
		logger.info("验证码：" + identifyCode);
		
		byte[] byte_alllen = new byte[4];// 随后的数据包长度
		int nresult2 = 0;
		try {
			nresult2 = reader.read(byte_alllen, 0, 4);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult2 < 0) {
			logger.error(("对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
		}
		int length = DataConvert.bytesToInt(byte_alllen);//随后的数据包长度
		logger.info("随后的数据包长度：" + length);
		
		byte[] protoArr = new byte[2];//协议类型
		try {
			reader.read(protoArr, 0, 2);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		short protoType = DataConvert.bytesToShort(protoArr);//协议类型
		logger.info("协议类型：" + protoType);
		try {
			reader.read(new byte[16], 0, 16);
			byte[] commandTypeArr = new byte[2];
			reader.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			if (commandType == COMMAND_TYPE_ADD) {//添加数据
				logger.info("添加数据");
				addData(reader, client);
			}
		} catch(Exception e) {
			logger.error("后台系统推送协议解析发生了错误："+e.getMessage());
		}
	}
	
	/**
	 * 添加数据：数据来源于集成推送
	 * 每次收到推送数据需要分别解析：基本信息所有字段、成就、作品、图片、人物关系、黄金搭档等
	 * 
	 * @param reader
	 * @param client
	 * @throws Exception
	 */
	private static void addData(BufferedInputStream reader, Socket client){
		try {
			byte[] pidArr = new byte[30];
			reader.read(pidArr, 0, 30);
			String pid = new String(pidArr).trim();
			byte[] dataContentLenArr = new byte[4];
			reader.read(dataContentLenArr, 0, 4);
			int fLength = DataConvert.bytesToInt(dataContentLenArr);
			logger.info("pid = "+pid+", xml长度fLength = "+fLength);
			//判断文件的长度来减少使用的时间.
			if (fLength==0) {
				logger.info("接收到的人物的PID是："+pid+",但是它却没有相应的xml文件发送过来,所以解析失败,返回-1");
				byte[] proto = assemblyReturnProtocol(-1);
				resBack(proto, client);
			}else {
				//String dataContent = new String(QueryRead.getResXml(reader, fLength), "GBK").trim();
				String dataContent = new String(QueryReader.getReturnXml(reader, fLength), "GBK").trim();
				reader.read(new byte[4], 0, 4);//扩展字段
				//添加数据逻辑--begin
				if (!StringUtil.isNotBlank(pid)) {
					logger.info("没有从字节文件中获取到人物PID,为错误数据,不能入库！");
					return ;
				}
				try {
					logger.info("pid:" + pid);
					logger.info("data:\n" + dataContent);
					//解析xml并入库的,判断是否保存成功!
					boolean result = dealXmlContent(pid, dataContent);
					if (result) {
						//添加数据逻辑--end
						byte[] proto = assemblyReturnProtocol(0);
						resBack(proto, client);
					}else {
						byte[] proto = assemblyReturnProtocol(-1);
						resBack(proto, client);
					}
				} catch(Exception e) {
					logger.info("从socket中解析XML并入库出错了,"+e.getMessage()+",时间："+DateUtil.getStringDate());
					byte[] proto = assemblyReturnProtocol(-1);
					resBack(proto, client);
				}
				
			}
		} catch (Exception e) {
			logger.info("从socket中解析XML并入库出错了,"+e.getMessage());
			byte[] proto = assemblyReturnProtocol(-1);
			resBack(proto, client);
		}
		
	}
	
	/**
	 * 读取xml并解析
	 * @param pid
	 * @param dataContent
	 * @return
	 */
	public static boolean dealXmlContent(String pid, String dataContent) {
		return false;
	}

	/**
	 * 返回给集成系统协议串
	 * @param statusCode 0：成功 非0:错误码(小于0)
	 * @return
	 */
	public static byte[] assemblyReturnProtocol(int statusCode) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		
		byte[] verifyCodeByte = PersonaeSocketUtil.VERIFY_CODE.getBytes();
		int total_data_len = 20;
		byte[] totalDataLenByte = DataConvert.intToByteArray(total_data_len);
		short proto_type = 4;
		byte[] protoTypeByte = DataConvert.shortToBytes(proto_type);
		byte[] retain_field_12 = new byte[12];
		byte[] statusCodeByte = DataConvert.intToByteArray(statusCode);
		
		protoList.add(verifyCodeByte);//状态码
		protoList.add(protoTypeByte);//协议类型4
		protoList.add(retain_field_12);//12位保留字段
		protoList.add(statusCodeByte);//状态码：0  成功 非0：错误码
		protoList.add(totalDataLenByte);//后续数据总长
		
		logger.info("状态码:"+new String(verifyCodeByte)+",后续数据总长:"+new String(totalDataLenByte)+
				",协议类型:"+new String(protoTypeByte)+",位保留字段:"+new String(retain_field_12)+"验证码:"+
				new String(statusCodeByte)+"最终发送的结果是："+ new String( DataConvert.sysCopy(protoList))+" ,len= "+DataConvert.sysCopy(protoList).length);
		
		return DataConvert.sysCopy(protoList);
	}

	/**
	 * @param proto
	 * @param client
	 */
	private static void resBack(byte[] proto, Socket client) {
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议
			logger.info("将要发给客户端的数据是："+new String(proto));
			bfOut.write(proto);
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
	 * 将输入的值转换
	 * @param value
	 * @return
	 */
	public static int getIntVal(String value) {
		if (!StringUtil.isNotBlank(value)) {
			return 0;
		}
		int retValeu = 0;
		try {
			if ("未知".equals(value.trim())) {
				return 0;
			}
			retValeu = Integer.parseInt(value);
		} catch (Exception e) {
			retValeu = 0;
			return retValeu ;
		}
		return retValeu ;
	}
}