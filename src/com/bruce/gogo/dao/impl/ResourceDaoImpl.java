package com.bruce.gogo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bruce.gogo.base.impl.BaseDaoImpl;
import com.bruce.gogo.dao.ResourceDao;
import com.bruce.gogo.entity.ResourceRole;
import com.bruce.gogo.entity.Resource;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDao
{

	public List<Resource> findAll() {
		return getSqlSession().selectList("resource.findAll");
	}
	
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getUserResources(String userId){
		return getSqlSession().selectList("resource.getUserResources",userId);
	}
	
	//<!-- 根据用户名获取该用户的权限-->
	public List<Resource> getResourcesByUserName(String username){
		return getSqlSession().selectList("resource.getResourcesByUserName",username);
	}
	
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getRoleResources(String roleId){
		return getSqlSession().selectList("resource.getRoleResources",roleId);
	}
	
	public void saveRoleRescours(ResourceRole resourceRoles){
		 getSqlSession().insert("resource.saveRoleRescours",resourceRoles);
	}
	
	public void deleteRoleRescours(String roleId){
		getSqlSession().delete("resource.deleteRoleRescours",roleId);
	}

	/**
	 * 返回空的.
	 */
	public Resource findByProps(Resource resource) {
		return getSqlSession().selectOne("resource.findByProps", resource);
	}
}
