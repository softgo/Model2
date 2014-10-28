package com.bruce.gogo.base.impl;

import java.util.List;

import com.bruce.gogo.base.BaseDao;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public abstract class BaseService<T> {

	public abstract BaseDao<T> baseDao();
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public List<T> find(PageView pageView,T t){
		return baseDao().find(pageView, t);
	}
	
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<T> findAll(T t){
		return baseDao().findAll(t);
	}
	
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(T t){
		baseDao().add(t);
	}
	
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(T t){
		baseDao().update(t);
	}
	
	
	/**
	 * 修改所有属性.
	 * @param t
	 */
	public void updateAll(T t){
		baseDao().updateAll(t);
	}
	
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id){
		baseDao().deleteById(id);
	}

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(T t){
		baseDao().delete(t);
	}

	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public T getObjById(String objId){
		return baseDao().getObjById(objId);
	}
	
	/**
	 * 获得总条数.
	 * @param id
	 * @return
	 */
	public Integer getObjsCount(){
		return baseDao().getObjsCount();
	}
	
}
