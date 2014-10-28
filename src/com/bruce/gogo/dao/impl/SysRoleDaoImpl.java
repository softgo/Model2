package com.bruce.gogo.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.bruce.gogo.base.impl.BaseDaoImpl;
import com.bruce.gogo.dao.SysRoleDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.UserRole;


@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao
{

	public List<SysRole> findAll() {
		return getSqlSession().selectList("sysrole.findAll");
	}

	public void saveUserRole(UserRole userRoles ) {
		getSqlSession().insert("sysrole.saveUserRole", userRoles);
	}

	public void deleteUserRole(String userId) {
		getSqlSession().delete("sysrole.deleteUserRole", userId);
	}

}
