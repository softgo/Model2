package com.bruce.gogo.dao;

import com.bruce.gogo.base.BaseDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.SysUser;

public interface SysUserDao extends BaseDao<SysUser>{
	
	public int countUser(String userName,String userPassword);
	
	public SysRole findbyUserRole(String userId);
	
}
