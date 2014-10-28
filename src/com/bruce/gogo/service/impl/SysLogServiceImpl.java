package com.bruce.gogo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.dao.SysLogDao;
import com.bruce.gogo.entity.SysLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.SysLogService;

@Transactional
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao ;

	public PageView find(PageView pageView, SysLog log) {
		List<SysLog> list = sysLogDao.find(pageView, log);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(SysLog log) {
		sysLogDao.add(log);
	}

	public void delete(String id) {
		sysLogDao.deleteById(id);
	}

	public SysLog getById(String id) {
		return sysLogDao.getObjById(id);
	}

	public void modify(SysLog log) {
		sysLogDao.update(log);
	}

	public List<SysLog> findAll(SysLog log) {
		return sysLogDao.findAll(log);
	}

}
