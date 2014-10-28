package com.bruce.gogo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.dao.SysUserDao;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.SysUser;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.SysUserService;

@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;

	public PageView find(PageView pageView, SysUser user) {
		List<SysUser> list = sysUserDao.find(pageView, user);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(SysUser user) {
		sysUserDao.add(user);
		
	}

	public void delete(String id) {
		sysUserDao.deleteById(id);
		
	}

	public SysUser getById(String id) {
		return sysUserDao.getObjById(id);
	}

	public void modify(SysUser user) {
		sysUserDao.update(user);
	}

	public int countUser(String userName, String userPassword) {
		return sysUserDao.countUser(userName, userPassword);
	}

	public SysUser findByName(String userName) {
		return sysUserDao.findByName(userName);
	}

	public SysRole findbyUserRole(String userId) {
		return sysUserDao.findbyUserRole(userId);
	}
}
