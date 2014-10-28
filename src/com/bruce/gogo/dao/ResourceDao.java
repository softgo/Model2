package com.bruce.gogo.dao;


import java.util.List;

import com.bruce.gogo.base.BaseDao;
import com.bruce.gogo.entity.ResourceRole;
import com.bruce.gogo.entity.Resource;

public interface ResourceDao extends BaseDao<Resource>{
	
	public List<Resource> findAll();
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getUserResources(String userId);
	//<!-- 根据角色Id获取该角色的权限-->
	public List<Resource> getRoleResources(String roleId);
	//<!-- 根据用户名获取该用户的权限-->
	public List<Resource> getResourcesByUserName(String username);
	
	public void saveRoleRescours(ResourceRole resourceRoles);
	
	public void deleteRoleRescours(String roleId);
	
	//通过属性获得.
	public Resource findByProps(Resource resource);
}
