package com.bruce.gogo.service;

import com.bruce.gogo.entity.UserLoginLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public interface UserLoginLogService {
	
	public PageView find(PageView pageView,UserLoginLog userLoginList);
		
	public void add(UserLoginLog userLoginList);

}
