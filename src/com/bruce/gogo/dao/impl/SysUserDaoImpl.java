package com.bruce.gogo.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bruce.gogo.base.impl.BaseDaoImpl;
import com.bruce.gogo.dao.SysUserDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.SysUser;

@Repository("sysUserDao")
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao
{
	public int countUser(String userName, String userPassword) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userName", userName);
		map.put("userPassword", userPassword);
		return (Integer)getSqlSession().selectOne("sysuser.countUser",map);
	}

	public SysRole findbyUserRole(String userId) {
		return (SysRole)getSqlSession().selectOne("sysrole.findbyUserRole", userId);
	}
	
}
