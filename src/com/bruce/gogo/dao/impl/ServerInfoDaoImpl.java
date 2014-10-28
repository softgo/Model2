package com.bruce.gogo.dao.impl;

import org.springframework.stereotype.Repository;

import com.bruce.gogo.base.impl.BaseDaoImpl;
import com.bruce.gogo.dao.ServerInfoDao;
import com.bruce.gogo.entity.ServerInfo;

@Repository("serverInfoDao")
public class ServerInfoDaoImpl extends BaseDaoImpl<ServerInfo> implements ServerInfoDao{


}
