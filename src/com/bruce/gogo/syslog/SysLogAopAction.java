package com.bruce.gogo.syslog;

import java.net.InetAddress;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bruce.gogo.base.util.Common;
import com.bruce.gogo.dao.SysLogDao;
import com.bruce.gogo.entity.SysLog;

/**
 * AOP注解方法实现日志管理 利用spring AOP 切面技术记录日志 定义切面类（这个是切面类和切入点整天合在一起的),
 * 这种情况是共享切入点情况;
 * 
 * @author admin
 * 
 * @version 1.0v
 */

@Aspect
// 该注解标示该类为切面类
@Component
public class SysLogAopAction {	
	@Autowired
	private SysLogDao sysLogDao;

	@Around("execution(* com.bruce.gogo.service.impl.*.* (..))")
	public Object logAll(ProceedingJoinPoint point) {
		Object result = null;
		// 执行方法名
		String methodName = point.getSignature().getName();
		String className = point.getTarget().getClass().getSimpleName();
		String user = null;
		Long start = 0L;
		Long end = 0L;
		String ip = null;
		// 当前用户
		try {
			// 执行方法所消耗的时间
			start = System.currentTimeMillis();
			result = point.proceed();
			end = System.currentTimeMillis();
			// ip
			ip = InetAddress.getLocalHost().getHostAddress();
			// 登录名
			user = Common.getAuthenticatedUsername();
			// System.out.println("Username:" +user);
		} catch (Throwable e) {
			// e.printStackTrace();
		}
		String name = null;
		// 操作范围
		if (className.indexOf("Resource") > -1) {
			name = "资源管理";
		} else if (className.indexOf("SysRole") > -1) {
			name = "角色管理";
		} else if (className.indexOf("SysUser") > -1) {
			name = "用户管理";
		}
		// 操作类型
		String opertype = "";
		if (methodName.indexOf("saveUserRole") > -1) {
			opertype = "update用户的角色";
		} else if (methodName.indexOf("saveRoleRescours") > -1) {
			opertype = "update角色的权限";
		} else if (methodName.indexOf("add") > -1 || methodName.indexOf("save") > -1) {
			opertype = "save操作";
		} else if (methodName.indexOf("update") > -1 || methodName.indexOf("modify") > -1) {
			opertype = "update操作";
		} else if (methodName.indexOf("delete") > -1) {
			opertype = "delete操作";
		}
		if(!Common.isEmpty(opertype)&&className.indexOf("UserLoginLog")==-1){
			Long time = end - start;
			SysLog log = new SysLog();
			log.setUsername(user);
			log.setModule(name);
			log.setAction(opertype);
			log.setActionTime(time.toString());
			log.setUserIP(ip);
			sysLogDao.add(log);
		}
		return result;
	}
}
