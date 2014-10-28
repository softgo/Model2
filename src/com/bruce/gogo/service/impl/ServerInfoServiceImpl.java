package com.bruce.gogo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.dao.ServerInfoDao;
import com.bruce.gogo.entity.ServerInfo;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.ServerInfoService;

@Transactional
@Service("serverInfoService")
public class ServerInfoServiceImpl implements ServerInfoService{
	@Autowired
	private ServerInfoDao serverInfoDao;

	public void add(ServerInfo serverInfo) {
		serverInfoDao.add(serverInfo);
	}

	public void delete(String id) {
		serverInfoDao.deleteById(id);
	}

	public ServerInfo getById(String id) {
		return serverInfoDao.getObjById(id);
	}
	
	//编译指令
	public PageView find(PageView pageView, ServerInfo serverInfo) {
		List<ServerInfo> list = serverInfoDao.find(pageView, serverInfo);
		pageView.setRecords(list);
		return pageView;
	}
	
	public List<ServerInfo> findAll(ServerInfo serverInfo) {
		return serverInfoDao.findAll(serverInfo);
	}

	public void modify(ServerInfo serverInfo) {
		serverInfoDao.update(serverInfo);
	}
	
}
