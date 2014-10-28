package com.bruce.gogo.base;

import java.util.List;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

/**
 * 集合持久层的公用的增，删，改，查接口
 * 
 * <T> 表示传入实体类
 * 
 * @author admin
 * 
 * @version 1.0v
 * 
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public List<T> find(PageView pageView,T t);
	
	/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	public T findByName(String objName);
	
	/**
	 * 通过对象查找对象
	 * @param t
	 * @return
	 */
	public T findByProps(T t);
	
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<T> findAll(T t);
	
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(T t);
	
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(T t);
	
	
	/**
	 * 修改所有属性.
	 * @param t
	 */
	public void updateAll(T t);
	
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id);

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(T t);	

	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public T getObjById(String objId);
	
	/**
	 * 获得总条数.
	 * @param id
	 * @return
	 */
	public Integer getObjsCount();	
	
}
