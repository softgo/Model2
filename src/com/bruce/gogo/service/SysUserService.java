package com.bruce.gogo.service;

import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.SysUser;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public interface SysUserService{
	
	public PageView find(PageView pageView,SysUser user);
	
	public void add(SysUser user);
	
	public void delete(String id);
	
	public void modify(SysUser user);
	
	public SysUser getById(String id);
	
	public int countUser(String userName,String userPassword);
	
	public SysUser findByName(String userName);
	
	public SysRole findbyUserRole(String userId);
}
