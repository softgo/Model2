package com.bruce.gogo.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;


/**
 * 集合持久层的公用的增，删，改，查类
 * 
 * <T> 表示传入实体类
 * 
 * @author admin
 * @version 1.0v
 * @param <T>
 */

public class BaseDaoImpl<T> extends SqlSessionDaoSupport{
	
	/**
	 * 
	 * 获取传过来的泛型类名字
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClassName(){
		//在父类中得到子类声明的父类的泛型信息  
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		//clazz.getSimpleName().toString().toLowerCase(); 这里是获取实体类的简单名称，再把类名转为小写
		return clazz.getSimpleName().toString().toLowerCase();
	}
	
	public List<T> find(PageView pageView,T t) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("paging", pageView);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName()+".find",map);  //find xml 中select ID的名字
	}
	
	public T findByName(String objName) {
		return getSqlSession().selectOne(this.getClassName()+".findByName",objName); //findByName xml 中select ID的名字
	}
	
	public T findByProps(T t) {
		return getSqlSession().selectOne(this.getClassName()+".findByProps",t); //findByProps xml 中select ID的名字
	}
	
	public List<T> findAll(T t) {
		return getSqlSession().selectList(this.getClassName()+".findAll",t); //findAll xml 中select ID的名字
	}
	
	public void add(T t) {
		getSqlSession().insert(this.getClassName()+".add",t); //add xml 中insert ID的名字
	}
	
	
	public void update(T t) {
		getSqlSession().update(this.getClassName()+".update",t);  //update xml 中update ID的名字
	}
	
	
	public void updateAll(T t) {
		getSqlSession().update(this.getClassName()+".updateAll",t);  //updateAll xml 中update ID的名字
	}
	
	
	public void deleteById(String id) {
		getSqlSession().delete(this.getClassName()+".deleteById",id); //deleteById xml 中delete ID的名字
	}
	
	
	public void delete(T t) {
		getSqlSession().delete(this.getClassName()+".delete",t); //delete xml 中delete ID的名字
	}
	
	
	@SuppressWarnings("unchecked")
	public T getObjById(String id) {
		return (T)getSqlSession().selectOne(this.getClassName()+".getObjById",id); //getObjById xml 中select ID的名字
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer getObjsCount() {
		return getSqlSession().selectOne(this.getClassName()+".getObjsCount"); //getObjsCount xml 中select ID的名字
	}
	
}
