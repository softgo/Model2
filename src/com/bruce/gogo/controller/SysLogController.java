package com.bruce.gogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.entity.SysLog;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.SysLogService;

/**
 * 
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping(value="/background/syslog/")
public class SysLogController  extends BaseController {
	@Autowired
	private SysLogService logService;
	
	/**
	 * 查询客户登陆信息
	 * @param model
	 * @param log
	 * @param pageNow
	 * @return
	 */
	@RequestMapping(value="find")
	public String queryUserLogin(Model model, SysLog log, String pageNow,String pageSize ){
		PageView pageView = super.findPage(pageNow, pageSize);
		logService.find(pageView, log);
		model.addAttribute("pageView", pageView);
		return "/background/syslog/list";
	}

}
