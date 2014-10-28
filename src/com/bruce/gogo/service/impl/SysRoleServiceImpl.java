package com.bruce.gogo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.dao.SysRoleDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.UserRole;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.SysRoleService;

@Transactional
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;

	public PageView find(PageView pageView, SysRole roles) {
		List<SysRole> list = sysRoleDao.find(pageView, roles);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(SysRole roles) {
		sysRoleDao.add(roles);
	}

	public void delete(String id) {
		sysRoleDao.deleteById(id);
	}

	public SysRole getById(String id) {
		return sysRoleDao.getObjById(id);
	}

	public void modify(SysRole roles) {
		sysRoleDao.update(roles);
	}

	public List<SysRole> findAll() {
		return sysRoleDao.findAll();
	}
	
	public SysRole findByName(String name) {
		return sysRoleDao.findByName(name);
	}

	public void saveUserRole(UserRole userRoles) {
		sysRoleDao.deleteUserRole(userRoles.getUserId().toString());
		sysRoleDao.saveUserRole(userRoles);
	}

}
