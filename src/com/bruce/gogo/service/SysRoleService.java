package com.bruce.gogo.service;


import java.util.List;

import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.UserRole;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public interface SysRoleService{
	
	public PageView find(PageView pageView,SysRole roles);
	
	public void add(SysRole roles);
	
	public void delete(String id);
	
	public void modify(SysRole roles);
	
	public SysRole getById(String id);
	
	public List<SysRole> findAll();
	
	public SysRole findByName(String name);
	
	public void saveUserRole(UserRole userRoles);
}
