package com.bruce.gogo.base.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisDB {
	
	 private static SqlSessionFactory sqlMapper ;
	 
	 static {
		String resource = "/configXml/spring-application.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlMapper = new SqlSessionFactoryBuilder().build(reader);
	}
	 
	 
	public static SqlSession getSqlSession(){
		if(sqlMapper == null ){
			return null;
		}
		return sqlMapper.openSession();
	}

	public static void close(){
		 getSqlSession().close();
	}
	
	
	public static void commit(){
		 getSqlSession().commit();
	}
	

	public static void rollback(){
		 getSqlSession().rollback();
	}
}
