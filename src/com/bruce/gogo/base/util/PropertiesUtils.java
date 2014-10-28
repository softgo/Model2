package com.bruce.gogo.base.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * 对属性文件操作的工具类
 * 获取，新增，修改
 * 注意：	以下方法读取属性文件会缓存问题,在修改属性文件时，不起作用，
 *　InputStream in = PropertiesUtils.class.getResourceAsStream("configPros/config.properties");
 *　解决办法：
 *　String savePath = PropertiesUtils.class.getResource("configPros/config.properties").getPath();
 * @author admin
 * @version 1.0v
 */
public class PropertiesUtils {
	
	static Logger logger  = Logger.getLogger(PropertiesUtils.class.getName());
	
	//属性文件的文件地址.
	public static String propsFilePath = "/configPros/config.properties"; 
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getProperty("fromEmail", "demo@qq.com", "configPros","config.properties"));
		System.out.println(getProperty("toEmail", "demo@qq.com",propsFilePath));
		System.out.println("-------------------------------------------------------------------------");
		Properties prop = new Properties();
		InputStream in = PropertiesUtils.class.getResourceAsStream(propsFilePath);
		try {
			prop.load(in);
			Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
				System.out.println((e.getKey().toString() + "=" + e.getValue().toString()));
			}
		} catch (Exception e) {
			logger.error("测试失败!");
		}
	}
	
	  /**
	    * 封装了Properties类的getProperty函数,使p变量对子类透明.
	    * @param key : 键值
	    * @param propFileName : 文件名称.
	    * @return
	    */

	/**
	 * 封装了Properties类的getProperty函数,使p变量对子类透明.
	 * @param key
	 * @param defaultValue
	 * @param filePath:文件夹的路径:/configPros/config.properties
	 * @return
	 */
	public static String getProperty(String key, String defaultValue,String filePath) {
    	Properties prop = new Properties();
        InputStream stream = null;
        String result = null;
        try {
        	stream = PropertiesUtils.class.getResourceAsStream(filePath);
            if (stream != null){
                prop.load(stream);
                //获得值.
                result = prop.getProperty(key, defaultValue);
             }
        } catch (IOException e) {
        	logger.error("加载Properties流文件失败!"+e.getLocalizedMessage());
        }finally {
            if (stream != null) {
                try {
                	stream.close();
                	stream=null;
                	prop.clear();
                    prop=null;
                } catch (IOException e) {
                	logger.error("关闭流操作失败!"+e.getLocalizedMessage());
                }
            }
        }
        return result;
    }
    
   /**
    * 
    * @param key
    * @param defaultValue
	 * @param fileName文件名字...configPros
	 * @param propsFileName:props文件名字...config.properties
    * @return
    */
    public static String getProperty(String key, String defaultValue,String fileName,String propsFileName) {
    	Properties prop = new Properties();
    	InputStream stream = null;
    	String result = null;
    	try {
    		stream = PropertiesUtils.class.getResourceAsStream("/"+fileName+"/"+propsFileName);
    		if (stream != null){
    			prop.load(stream);
    			//获得值.
    			result = prop.getProperty(key, defaultValue);
    		}
    	} catch (IOException e) {
    		logger.error("加载Properties流文件失败!"+e.getLocalizedMessage());
    	}finally {
    		if (stream != null) {
    			try {
    				stream.close();
    				stream=null;
    				prop.clear();
    				prop=null;
    			} catch (IOException e) {
    				logger.error("关闭流操作失败!"+e.getLocalizedMessage());
    			}
    		}
    	}
    	return result;
    }

	
	/**
	 * 获取属性文件的数据 根据key获取值
	 * @param filePath 文件夹名　(注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * @param key
	 * @return
	 */
	public static String findPropertiesKey(String key,String filePath) {
		try {
			Properties prop = getProperties(filePath);
			return prop.getProperty(key);
		} catch (Exception e) {
			logger.error("获取"+filePath+"文件中的值失败!"+e.getLocalizedMessage());
			return "";
		}
	}
	
	/**
	 * 获取属性文件的数据 根据key获取值
	 * @param key
	 * @param fileName文件名字...configPros
	 * @param propsFileName:props文件名字...config.properties
	 * @return
	 */
	public static String findPropertiesKey(String key ,String fileName,String propsFileName) {
		try {
			Properties prop = getProperties(fileName,propsFileName);
			return prop.getProperty(key);
		} catch (Exception e) {
			logger.error("获取"+propsFileName+"文件中的值失败!"+e.getLocalizedMessage());
			return "";
		}
	}

	/**
	 * 返回　Properties
	 * @param filePath:文件夹的路径:/configPros/config.properties
	 * @return
	 */
	public static Properties getProperties(String filePath){
		Properties prop = new Properties();
		String savePath = PropertiesUtils.class.getResource(filePath).getPath();
		//以下方法读取属性文件会缓存问题
        //InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
		try {
			InputStream in =new BufferedInputStream(new FileInputStream(savePath));  
			prop.load(in);
		} catch (Exception e) {
			logger.error("加载"+filePath+"Properties文件失败!"+e.getLocalizedMessage());
			return null;
		}
		return prop;
	}
	
	/**
	 * 返回　Properties
	 * @param fileName:文件夹的值...configPros
	 * @param propsFileName:props文件名字...config.properties
	 * @return
	 */
	public static Properties getProperties(String fileName,String propsFileName){
		Properties prop = new Properties();
		String savePath = PropertiesUtils.class.getResource("/"+fileName+"/"+propsFileName).getPath();
		//以下方法读取属性文件会缓存问题
		//InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
		try {
			InputStream in =new BufferedInputStream(new FileInputStream(savePath));  
			prop.load(in);
		} catch (Exception e) {
			logger.error("加载"+propsFileName+"Properties文件失败!"+e.getLocalizedMessage());
			return null;
		}
		return prop;
	}
	
	/**
	 * 写人信息到props文件中去.
	 * @param key:key
	 * @param value:value
	 * @param filePath:文件夹的路径:/configPros/config.properties
	 */
	public static void modifyProperties(String key, String value,String filePath) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties(filePath);
			prop.setProperty(key, value);
			//String path = PropertiesUtils.class.getResource("/configPros/config.properties").getPath();
			String path = PropertiesUtils.class.getResource(filePath).getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			logger.error("修改"+filePath+"下文件属性失败!"+e.getLocalizedMessage());
		}
	}
	
	/**
	 * 写人信息到props文件中去.
	 * @param key:key
	 * @param value:value
	 * @param fileName:文件夹的值...configPros
	 * @param propsFileName:props文件名字...config.properties
	 */
	public static void modifyProperties(String key, String value,String fileName,String propsFileName) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties(fileName,propsFileName);
			prop.setProperty(key, value);
			String path = PropertiesUtils.class.getResource("/"+fileName+"/"+propsFileName).getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			logger.error("修改"+propsFileName+"文件属性失败!"+e.getLocalizedMessage());
		}
	}
}
