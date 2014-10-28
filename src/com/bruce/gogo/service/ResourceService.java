package com.bruce.gogo.service;


import java.util.List;

import com.bruce.gogo.entity.Resource;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;


public interface ResourceService{
	
	public PageView find(PageView pageView,Resource resources);
	
	public void add(Resource resources);
	
	public void delete(String id);
	
	public void modify(Resource resources);
	
	public Resource getById(String id);
	
	public List<Resource> findAll();
	
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getUserResources(String userId);
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getRoleResources(String roleId);
	//<!-- 根据用户名获取该用户的权限-->
	public List<Resource> getResourcesByUserName(String username);
	
	public void saveRoleRescours(String roleId,List<String> list);
	
	//通过属性获得.
	public Resource findByProps(Resource resource);
	
}
