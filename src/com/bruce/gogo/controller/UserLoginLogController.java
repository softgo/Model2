package com.bruce.gogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.entity.UserLoginLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.UserLoginLogService;


/**
 * 
 * @author admin
 * @version 1.0v
 */
@Controller
@RequestMapping(value="/background/syslog/")
public class UserLoginLogController  extends BaseController {
	
	@Autowired
	private UserLoginLogService userLoginLogService ;
	
	/**
	 * 查询客户登陆信息
	 * @param model
	 * @param userLoginLog
	 * @param pageNow
	 * @return
	 */
	@RequestMapping(value="findlogin")
	public String find(Model model, UserLoginLog userLoginLog, String pageNow,String pageSize ){
		/**
		 * request 方式取得界面上的值.tempId 为界面的 userId .
		 */
		int userId = ServletRequestUtils.getIntParameter(request, "tempId",-1);
		if (userId>0) {
			userLoginLog.setUserId(userId);
			//设置值.
			request.setAttribute("userId",userId);
		}
		PageView pageView = super.findPage(pageNow, pageSize);
		userLoginLogService.find(pageView, userLoginLog);
		model.addAttribute("pageView", pageView);
		return "/background/syslog/loginList";
	}
}
