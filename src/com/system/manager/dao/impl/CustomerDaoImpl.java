package com.system.manager.dao.impl;

import org.springframework.stereotype.Repository;
import com.bruce.gogo.base.impl.BaseDaoImpl;

import com.system.manager.entity.Customer;
import com.system.manager.dao.CustomerDao;

/**
 * 实现.
 * 
 * @author admin
 *
 */
 @Repository("customerDao")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao{
	
}
