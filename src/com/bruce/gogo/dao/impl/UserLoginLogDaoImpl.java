package com.bruce.gogo.dao.impl;

import org.springframework.stereotype.Repository;

import com.bruce.gogo.base.impl.BaseDaoImpl;
import com.bruce.gogo.dao.UserLoginLogDao;
import com.bruce.gogo.entity.UserLoginLog;

@Repository("userLoginLogDao")
public class UserLoginLogDaoImpl extends BaseDaoImpl<UserLoginLog> implements UserLoginLogDao{


}
