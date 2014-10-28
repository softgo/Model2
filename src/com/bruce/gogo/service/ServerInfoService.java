package com.bruce.gogo.service;

import java.util.List;

import com.bruce.gogo.entity.ServerInfo;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

public interface ServerInfoService {
	
	public PageView find(PageView pageView,ServerInfo serverInfo);
	
	public List<ServerInfo> findAll(ServerInfo serverInfo);
	
	public void add(ServerInfo serverInfo);
	
	public void delete(String id);
	
	public ServerInfo getById(String id);
	
	public void modify(ServerInfo serverInfo);
}
