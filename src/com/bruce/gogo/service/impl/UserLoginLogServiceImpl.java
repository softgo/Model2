package com.bruce.gogo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.dao.UserLoginLogDao;
import com.bruce.gogo.entity.UserLoginLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.UserLoginLogService;

@Transactional
@Service("userLoginLogService")
public class UserLoginLogServiceImpl implements UserLoginLogService{
	
	@Autowired
	private UserLoginLogDao userLoginLogDao;
	
	public PageView find(PageView pageView, UserLoginLog userLoginLog) {
		List<UserLoginLog> list = userLoginLogDao.find(pageView, userLoginLog);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(UserLoginLog userLoginLog) {
		userLoginLogDao.add(userLoginLog);
	}
}
