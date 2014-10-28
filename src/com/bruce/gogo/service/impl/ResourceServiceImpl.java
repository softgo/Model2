package com.bruce.gogo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.base.util.Common;
import com.bruce.gogo.dao.ResourceDao;
import com.bruce.gogo.entity.ResourceRole;
import com.bruce.gogo.entity.Resource;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.ResourceService;

@Transactional
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	public PageView find(PageView pageView, Resource resources) {
		List<Resource> list = resourceDao.find(pageView, resources);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(Resource resources) {
		resourceDao.add(resources);
	}

	public void delete(String id) {
		resourceDao.deleteById(id);
	}

	public Resource getById(String id) {
		return resourceDao.getObjById(id);
	}

	public void modify(Resource resources) {
		resourceDao.update(resources);
	}

	public List<Resource> findAll() {
		return resourceDao.findAll();
	}

	public List<Resource> getUserResources(String userId) {
		
		return resourceDao.getUserResources(userId);
	}
	
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getRoleResources(String roleId){
		return resourceDao.getRoleResources(roleId);
	}
	
	public void saveRoleRescours(String roleId,List<String> list) {
			resourceDao.deleteRoleRescours(roleId);
			for (String rId : list) {
				if(!Common.isEmpty(rId)){
					ResourceRole resourceRoles = new ResourceRole(); 
					resourceRoles.setRescId(Integer.parseInt(rId));
					resourceRoles.setRoleId(Integer.parseInt(roleId));
					resourceDao.saveRoleRescours(resourceRoles);
				}
			}
	}

	public List<Resource> getResourcesByUserName(String username) {
		return resourceDao.getResourcesByUserName(username);
	}

	public Resource findByProps(Resource resource) {
		return resourceDao.findByProps(resource);
	}

}
