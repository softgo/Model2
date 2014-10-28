package com.bruce.gogo.service;


import java.util.List;

import com.bruce.gogo.entity.SysLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public interface SysLogService{
	
	public PageView find(PageView pageView,SysLog log);
	
	public void add(SysLog log);
	
	public void delete(String id);
	
	public void modify(SysLog log);
	
	public SysLog getById(String id);
	
	public List<SysLog> findAll(SysLog log);
	
}
