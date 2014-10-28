package com.bruce.gogo.dao;

import java.util.List;

import com.bruce.gogo.base.BaseDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.UserRole;

public interface SysRoleDao extends BaseDao<SysRole>{
	
	public List<SysRole> findAll();
	
	public void deleteUserRole(String userId);
	
	public void saveUserRole(UserRole userRoles);
	
}
