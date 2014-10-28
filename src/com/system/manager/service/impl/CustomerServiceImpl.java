package com.system.manager.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

import com.system.manager.entity.Customer;
import com.system.manager.dao.CustomerDao;
import com.system.manager.service.CustomerService;

/**
 * 
* 接口的实现.
 */
@Transactional
@Service("customerService")
public class CustomerServiceImpl  implements  CustomerService {

	private final static Logger logger= Logger.getLogger(CustomerService.class);

	@Autowired
    private CustomerDao  customerDao;
		
    /**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public PageView find(PageView pageView,Customer customer ){
		List<Customer> list = customerDao.find(pageView, customer);
		pageView.setRecords(list);
		return pageView;
	}
	
		/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	public Customer findByName(String objName){
			return customerDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param t
	 * @return
	 */
	public Customer findByProps(Customer customer){
		return customerDao.findByProps(customer);
	}
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<Customer> findAll(Customer customer){
		return customerDao.findAll(customer);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id){
		customerDao.deleteById(id);
	}

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Customer customer){
		customerDao.delete(customer);
	}
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(Customer customer){
		customerDao.update(customer);
	}
	
	/**
	 * 修改
	 * @param t
	 */
	public void updateAll(Customer customer){
		customerDao.updateAll(customer);
	}
	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public Customer getObjById(String objId){
		return customerDao.getObjById(objId);
	}
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(Customer customer){
		 customerDao.add(customer);
	}
	
	/**
	 * 添加
	 * @param t
	 */
	public Integer getObjsCount(){
		 return customerDao.getObjsCount();
	}

}
