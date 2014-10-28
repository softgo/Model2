package com.system.manager.service;

import java.util.List;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.system.manager.entity.Customer;

/**
 * service 的接口定义(实际使用中可以自行添加)
 */
 
public interface CustomerService {

	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public PageView find(PageView pageView,Customer customer);
	
	/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	public Customer findByName( String objName );

	/**
	 * 通过对象查询对象.
	 * @param t
	 * @return
	 */
	public Customer findByProps(Customer customer);
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<Customer> findAll(Customer customer);
	
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(Customer customer);
	
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(Customer customer);
	
	
	/**
	 * 修改所有属性.
	 * @param t
	 */
	public void updateAll(Customer customer);
	
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id);

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Customer customer);	

	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public Customer getObjById(String objId);
	
	/**
	 * 获得总条数.
	 * @param id
	 * @return
	 */
	public Integer getObjsCount();	
	
}
