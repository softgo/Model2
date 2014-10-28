package com.bruce.gogo.socket.byteutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bruce.gogo.base.util.DataConvert;

/**
 * 人物库管理后台与集成管理后台通信协议的拼装
 *
 */
public class PersonaeSocketUtil {
	
	protected static final Log log = LogFactory.getLog(PersonaeSocketUtil.class.getClass());
	
	public static final String VERIFY_CODE = "PEOPLE10";//验证码byte[8]
	
	public int dataLen = 0;//数据总长
	public static short protocol_type = 3;//协议类型 3:数据集成系统
	public static byte[] retain_field_16 = new byte[16];//16位保留字段
	public short command_type = 0;//命令类型
	
	public int person_id = 0;//人物id
	
	//添加人物、更新个人信息、成就、作品、人物关系、图片、黄金搭档等
	public static final short COMMAND_ADD = 2;//添加
	public static final short COMMAND_DELETE = 3;//删除
	public static final short COMMAND_UPDATE = 4;//更新
	
	//更新个人信息页，出现“聚”按钮，点击后与后台通信，获得集成后台聚类信息并显示
	public static final short COMMAND_SEARCH_PID_LID = 5;//查询Pid下Lid信息
	public static final short COMMAND_PICKOUT_PID_LID = 6;//摘除pid、lid关系
	
	//人物状态信息修改：人物有“草稿”，“发布”两种状态
	public static final short COMMAND_CHANGE_STATUS = 7;//置状态 草稿或发布
	
	//【配置管理】作品类型、人物关系的配置管理
	public static final short COMMAND_ADD_RELATIONSHIP = 8;//【配置管理】添加作品类型
	public static final short COMMAND_DELETE_RELATIONSHIP = 9;//【配置管理】删除作品类型
	
	//修改人名.
	public static final short COMMAND_CHANGE_NAME = 10;//修改人名
	
	//2014-8-19
	public static final short COMMAND_ADD_NEWMAN = 11;//新添加一个娱乐人物.
	
	/**
	 * 添加人物、修改个人信息、作品、成就、图片、人物关系、黄金搭档 通用
	 * 后台添加、更新信息统一走这段代码
	 * 
	 * @param xmlData : 生成的xml数据集合,
	 * @param command_type : 命令类型,
	 * @param pid : 人物Id,
	 * @return
	 */
	public static byte[] assemblyPersonProtocol(String xmlData, short command_type, String pid) {
		log.info("添加人物、修改个人信息、作品、成就、图片、人物关系、黄金搭档 通用,给集成发送的XML数据是：\n"+xmlData+",pid = "+pid);
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		byte[] pidByte = new byte[30];
		if (StringUtils.isNotBlank(pid)) {
			pidByte = convertTo30ByteArr(pid);
		}
		total_data_len += 30;
	
		byte[] xmlDataByte = xmlData.getBytes();
		int xmlDataLen = xmlDataByte.length;
		total_data_len += xmlDataLen;
		
		byte[] xmlDataLenByte = DataConvert.intToByteArray(xmlDataLen);
		xmlDataLen += 4;
		
		byte[] expandLenByte = DataConvert.intToByteArray(0);
		xmlDataLen += 4;
		
		byte[] headProto = assemblyHeadProtocol(command_type, total_data_len);
		log.info("数据包头的长度是："+headProto.length);
		
		//数据包头
		protoList.add(headProto);
		//子数据部分
		protoList.add(pidByte);
		protoList.add(xmlDataLenByte);
		protoList.add(xmlDataByte);
		protoList.add(expandLenByte);
		
		byte[] proto = DataConvert.sysCopy(protoList);
		log.info("添加人物、修改个人信息、作品、成就、图片、人物关系、黄金搭档 通用,给集成发送的数据是："+new String(proto)+"， 全部发送的数据长度是："+proto.length);
		return proto;
	}
	
	/**
	 * 删除.
	 * @param xmlData
	 * @param command_type
	 * @param pid
	 * @return
	 */
	public static byte[] deletePIdPersonProtocol(String xmlData, short command_type, String pid) {
		log.info("删除人物xml：\n"+xmlData+",pid = "+pid);
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		byte[] pidByte = new byte[30];
		if (StringUtils.isNotBlank(pid)) {
			pidByte = convertTo30ByteArr(pid);
		}
		total_data_len += 30;
		
		byte[] xmlDataByte = xmlData.getBytes();
		int xmlDataLen = xmlDataByte.length;
		total_data_len += xmlDataLen;
		
		byte[] xmlDataLenByte = DataConvert.intToByteArray(xmlDataLen);
		log.info("xmlDataLenByte =" +xmlDataLenByte.length);
		//xmlDataLen += 4;
		
		total_data_len +=xmlDataLenByte.length;
		byte[] expandLenByte = DataConvert.intToByteArray(0);
		log.info("expandLenByte =" +expandLenByte.length);
		total_data_len +=expandLenByte.length;
		//xmlDataLen += 4;
		
		byte[] headProto = assemblyHeadProtocol(command_type, total_data_len);
		log.info("数据包头的长度是："+headProto.length);
		//数据包头
		protoList.add(headProto);
		//子数据部分
		protoList.add(pidByte);
		protoList.add(xmlDataLenByte);
		protoList.add(xmlDataByte);
		protoList.add(expandLenByte);
		
		byte[] proto = DataConvert.sysCopy(protoList);
		log.info("添加人物、修改个人信息、作品、成就、图片、人物关系、黄金搭档 通用,给集成发送的数据是："+new String(proto)+"， 全部发送的数据长度是："+proto.length);
		return proto;
	}
	
	/**
	 * 查询pid下lid信息
	 * @param pid
	 * @return
	 */
	public static byte[] assemblySearchPidLidProtocol(String pid) {
		log.info("查询pid下lid信息, pid = "+pid);
		List<byte[]> protoList = new ArrayList<byte[]>();
		
		int total_data_len = 0;
		byte[] pidByte = convertTo30ByteArr(pid);
		total_data_len += 30;
		
		byte[] headProto = assemblyHeadProtocol(COMMAND_SEARCH_PID_LID, total_data_len);
		
		protoList.add(headProto);
		protoList.add(pidByte);
		log.info("查询pid下lid信息,最终的信息是： "+new String(DataConvert.sysCopy(protoList)));
		return DataConvert.sysCopy(protoList);
	}
	/**
	 * 摘除pid lid关系
	 * @param pid
	 * @param lid
	 * @return
	 */
	public static byte[] assemblyPickoutPidLidProtocol(String lid, String oldPid, String newPid) {
		log.info("摘除pid lid关系,lid= "+lid+", oldPid = "+oldPid+" , newPid = "+newPid);
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		
		int lidNum = 1;
		byte[] lidNumByte = DataConvert.intToByteArray(lidNum);
		total_data_len += 4;
	
		byte[] lidByte = convertTo30ByteArr(lid);
		total_data_len += 30;
		
		byte[] oldPidByte = convertTo30ByteArr(oldPid);
		total_data_len += 30;

		byte[] newPidByte = convertTo30ByteArr(newPid);
		total_data_len += 30;
		
//		byte[] expandLenByte = DataConvert.intToByteArray(0);
//		total_data_len += 4;
		
		byte[] headProto = assemblyHeadProtocol(COMMAND_PICKOUT_PID_LID, total_data_len);
		
		protoList.add(headProto);
		protoList.add(lidNumByte);
		protoList.add(lidByte);
		protoList.add(oldPidByte);
		protoList.add(newPidByte);
//		protoList.add(expandLenByte);
		
		return DataConvert.sysCopy(protoList);
	}
	/**
	 * 置状态：0：草稿 1：发布
	 * @param pid
	 * @param status
	 * @return
	 */
	public static byte[] assemblyChangeStatusProtocol(String pid, Integer status) {
		log.info("置状态：0：草稿 1：发布, 传入的Pid = "+pid+", status = "+status);
		int dataLen = 0;
		List<byte[]> protoList = new ArrayList<byte[]>();
		
		byte[] pidByte = convertTo30ByteArr(pid);
		dataLen += 30;
		
		byte[] stateByte = new byte[]{status.byteValue()};
		dataLen += 1;
		
		byte[] headProtoByte = assemblyHeadProtocol(COMMAND_CHANGE_STATUS, dataLen);
		protoList.add(headProtoByte);
		
		protoList.add(pidByte);
		protoList.add(stateByte);
		
		return DataConvert.sysCopy(protoList);
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

	/**
	 * 请求数据包头
	 * @param command_type 命令类型
	 * @param total_data_len 数据总长（除包头外）+20即为后续所有数据总长
	 * @return
	 */
	public static byte[] assemblyHeadProtocol(short command_type, int total_data_len) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		
		byte[] verifyCodeByte = VERIFY_CODE.getBytes();//[8]验证码
		byte[] dataLenByte = DataConvert.intToByteArray(total_data_len + 20);//[4]数据总长
		byte[] protoTypeByte = DataConvert.shortToBytes(protocol_type);//[2]协议类型
		byte[] commandTypeByte = DataConvert.shortToBytes(command_type);//[2]命令类型
		
		log.info("数据总长是："+DataConvert.bytesToInt(dataLenByte)+",后续数据的总长是："+DataConvert.bytesToInt(dataLenByte));
		
		protoList.add(verifyCodeByte);//[8]验证码
		protoList.add(dataLenByte);//[4]数据总长
		protoList.add(protoTypeByte);//[2]协议类型
		protoList.add(retain_field_16);//[16]保留字段
		protoList.add(commandTypeByte);//[2]命令类型
		
		byte[] proto = DataConvert.sysCopy(protoList);
		log.info("请求数据包头的信息是："+new String(proto));
		return proto;
	}
	/**
	 * 返回给集成系统：0：成功 非0:状态码
	 * @param statusCode
	 * @return
	 */
	public static byte[] assemblyReturnProtocol(Integer statusCode) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		byte[] verifyCodeByte = VERIFY_CODE.getBytes();
		int total_data_len = 20;
		byte[] totalDataLenByte = DataConvert.intToByteArray(total_data_len);
		short proto_type = 4;
		byte[] protoTypeByte = DataConvert.shortToBytes(proto_type);
		byte[] retain_field_12 = new byte[12];
		int status_code = statusCode.intValue();
		byte[] statusCodeByte = DataConvert.intToByteArray(status_code);
		
		protoList.add(verifyCodeByte);//状态码
		protoList.add(totalDataLenByte);//后续数据总长
		protoList.add(protoTypeByte);//协议类型4
		protoList.add(retain_field_12);//12位保留字段
		protoList.add(statusCodeByte);//状态码：0  成功 非0：错误码
		log.info("返回集成系统的字符串是："+new String(DataConvert.sysCopy(protoList)));
		return DataConvert.sysCopy(protoList);
	}
	
  /*************************************************************************** 获取 socket 返回的值的信息 **************************************************************************/

	/**
	 * 摘除人物id与聚类lid之间的关联关系
	 * @param lid
	 * @param oldPid	原pid
	 * @param newPid	新pid
	 * @return
	 * @throws Exception
	 */
	public static String pickoutPidAndLidSocket(String lid, String oldPid, String newPid ) throws Exception {
		byte[] proto = assemblyPickoutPidLidProtocol(lid, oldPid, newPid);
		//MetasearchConf metasearch = new MetasearchConf("db_to_its_oi.conf"); 2014-8-20
		MetasearchConf metasearch = new MetasearchConf("db_to_its_new.conf");
		log.info("摘除人物id与聚类lid之间的关联关系:\nIp："+metasearch.getIp()+",\nport:"+metasearch.getPort()+",\n result String:"+new String(proto));
		SocketUtil socket = new SocketUtil(metasearch.getIp(),metasearch.getPort(), metasearch.getCharset());
		socket.connect();
		socket.sendByte(proto);
		String res = socket.recvStatusCode(COMMAND_PICKOUT_PID_LID);
		socket.close();
		return res;
	}
	/**
	 * 摘除人物id与聚类lid之间的关联关系
	 * @param lid
	 * @param oldPid	原pid
	 * @param newPid	新pid
	 * @return
	 * @throws Exception
	 */
	public static String pickoutPidAndLidSocketNew(String lid, String oldPid, String newPid ) throws Exception {
		byte[] proto = assemblyPickoutPidLidProtocol(lid, oldPid, newPid);
		MetasearchConf metasearch = new MetasearchConf("db_to_its_new.conf");
		log.info("摘除人物id与聚类lid之间的关联关系:\nIp："+metasearch.getIp()+",\nport:"+metasearch.getPort()+",\n result String:"+new String(proto));
		SocketUtil socket = new SocketUtil(metasearch.getIp(),metasearch.getPort(), metasearch.getCharset());
		socket.connect();
		socket.sendByte(proto);
		String res = socket.recvStatusCode(COMMAND_PICKOUT_PID_LID);
		socket.close();
		return res;
	}
	
	/**
	 * 修改人物状态
	 * @param pid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public static String changeStatusSocket(String pid, Integer status) throws Exception {
		byte[] proto = assemblyChangeStatusProtocol(pid, status);
		//MetasearchConf metasearch = new MetasearchConf("db_to_its_oi.conf"); 2014-8-20
		MetasearchConf metasearch = new MetasearchConf("db_to_its_new.conf");
		log.info("修改人物状态:\nIp："+metasearch.getIp()+",\nport:"+metasearch.getPort()+",\n result String:"+new String(proto));
		SocketUtil socket = new SocketUtil(metasearch.getIp(),metasearch.getPort(), metasearch.getCharset());
		socket.connect();
		socket.sendByte(proto);
		String res = socket.recvStatusCode(COMMAND_CHANGE_STATUS);
		socket.close();
		return res;
	}

	
	/**
	 * 修改人名
	 * @param pid 
	 * @param oldName 旧名字
	 * @param newName 新名字
	 * @param commandType 命令类型 10
	 * @return
	 * @throws Exception
	 */
	public static String changeNameSocket(String pid, String oldName, String newName, String oldBirth, String newBirth, String xmlData, short commandType) throws Exception {
		byte[] proto = assemblyChangeNameProtocol(pid, oldName, newName, oldBirth, newBirth,xmlData, commandType);
		//MetasearchConf metasearch = new MetasearchConf("db_to_its_oi.conf"); 2014-8-20
		MetasearchConf metasearch = new MetasearchConf("db_to_its_new.conf");
		log.info("修改人名:\nIp："+metasearch.getIp()+",\nport:"+metasearch.getPort()+",\n result String:"+new String(proto));
		SocketUtil socket = new SocketUtil(metasearch.getIp(),metasearch.getPort(), metasearch.getCharset());
		socket.connect();
		socket.sendByte(proto);
		String res = socket.recvStatusCode(commandType);
		socket.close();
		return res;
	}
	/**
	 * 修改人名byte[]
	 * @param pid
	 * @param oldName
	 * @param newName
	 * @param commandType 10
	 * @return
	 */
	private static byte[] assemblyChangeNameProtocol(String pid,
			String oldName, String newName, String oldBirth, String newBirth, short commandType) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		
		byte[] pidByte = convertTo30ByteArr(pid);
		total_data_len += 30;
		
		byte[] oldNameByte = oldName.getBytes();
		int oldNameLen = oldNameByte.length;
		total_data_len += oldNameLen;
		byte[] oldNameLenByte = DataConvert.intToByteArray(oldNameLen);
		total_data_len += 4;
		
		byte[] newNameByte = newName.getBytes();
		int newNameLen = newNameByte.length;
		total_data_len += newNameLen;
		byte[] newNameLenByte = DataConvert.intToByteArray(newNameLen);
		total_data_len += 4;
		
		byte[] oldBirthByte = oldBirth.getBytes();
		int oldBirthLen = oldBirthByte.length;
		total_data_len += oldBirthLen;
		byte[] oldBirthLenByte = DataConvert.intToByteArray(oldBirthLen);
		total_data_len += 4;
		
		byte[] newBirthByte = newBirth.getBytes();
		int newBirthLen = newBirthByte.length;
		total_data_len += newBirthLen;
		byte[] newBirthLenByte = DataConvert.intToByteArray(newBirthLen);
		total_data_len += 4;
		
		
		byte[] expandLenByte = DataConvert.intToByteArray(0);
		total_data_len += 4;
		
		byte[] headProtoByte = null;
		headProtoByte = assemblyHeadProtocol(commandType, total_data_len);
		protoList.add(headProtoByte);
		protoList.add(pidByte);
		protoList.add(oldNameLenByte);
		protoList.add(oldNameByte);
		protoList.add(newNameLenByte);
		protoList.add(newNameByte);
		protoList.add(oldBirthLenByte);
		protoList.add(oldBirthByte);
		protoList.add(newBirthLenByte);
		protoList.add(newBirthByte);
		protoList.add(expandLenByte);
		return DataConvert.sysCopy(protoList);
	}
	/**
	 * 修改人名byte[]
	 * @param pid
	 * @param oldName
	 * @param newName
	 * @param commandType 10
	 * @return
	 */
	private static byte[] assemblyChangeNameProtocol(String pid,
			String oldName, String newName, String oldBirth, String newBirth,String xmlData, short commandType) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		int total_data_len = 0;
		//pid
		byte[] pidByte = convertTo30ByteArr(pid);
		total_data_len += 30;
		//old-name
		byte[] oldNameByte = oldName.getBytes();
		int oldNameLen = oldNameByte.length;
		total_data_len += oldNameLen;
		//old-name-len
		byte[] oldNameLenByte = DataConvert.intToByteArray(oldNameLen);
		total_data_len += 4;
		
		//new-name
		byte[] newNameByte = newName.getBytes();
		int newNameLen = newNameByte.length;
		total_data_len += newNameLen;
		//new-name-len
		byte[] newNameLenByte = DataConvert.intToByteArray(newNameLen);
		total_data_len += 4;
		
		//old-birth
		byte[] oldBirthByte = oldBirth.getBytes();
		int oldBirthLen = oldBirthByte.length;
		total_data_len += oldBirthLen;
		//old-birth-len
		byte[] oldBirthLenByte = DataConvert.intToByteArray(oldBirthLen);
		total_data_len += 4;
		
		//new-brith
		byte[] newBirthByte = newBirth.getBytes();
		int newBirthLen = newBirthByte.length;
		total_data_len += newBirthLen;
		//new-birth-len
		byte[] newBirthLenByte = DataConvert.intToByteArray(newBirthLen);
		total_data_len += 4;
		
		//data
		byte[] dataByte = xmlData.getBytes();
		int xmlLen = dataByte.length;
		total_data_len += xmlLen;
		
		//byte[] dataByteLen = DataConvert.intToByteArray(xmlLen);
		//total_data_len += dataByteLen.length;
		
		//datalen
		byte[] expandLenByte = DataConvert.intToByteArray(0);
		total_data_len += 4;
		
		byte[] headProtoByte = null;
		headProtoByte = assemblyHeadProtocol(commandType, total_data_len);
		protoList.add(headProtoByte);
		protoList.add(pidByte);
		protoList.add(oldNameLenByte);
		protoList.add(oldNameByte);
		protoList.add(newNameLenByte);
		protoList.add(newNameByte);
		protoList.add(oldBirthLenByte);
		protoList.add(oldBirthByte);
		protoList.add(newBirthLenByte);
		protoList.add(newBirthByte);
		protoList.add(dataByte);
		//protoList.add(dataByteLen);
		protoList.add(expandLenByte);
		return DataConvert.sysCopy(protoList);
	}
}
