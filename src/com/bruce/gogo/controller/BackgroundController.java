package com.bruce.gogo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.base.util.Common;
import com.bruce.gogo.dao.SysUserDao;
import com.bruce.gogo.entity.Resource;
import com.bruce.gogo.entity.SysUser;
import com.bruce.gogo.entity.UserLoginLog;
import com.bruce.gogo.service.ResourceService;
import com.bruce.gogo.service.UserLoginLogService;


/**
 * 进行管理后台框架界面的类.(到background文件夹下去)
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping ("/background/")
public class BackgroundController extends BaseController
{
	@Autowired
	private SysUserDao userDao;
	@Autowired
	private UserLoginLogService userLoginLogService;
	@Autowired
	private ResourceService resourcesService;
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	
	/**
	 * @return
	 */
	@RequestMapping ("login")
	public String login(Model model,HttpServletRequest request)
	{
		//重新登录时销毁该用户的Session
		Object o = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(null != o){
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		return "/background/framework/login";
	}
	
	@RequestMapping ("loginCheck")
	public String loginCheck(String username,String password){
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("error","支持POST方法提交!");
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				request.setAttribute("error","用户名或密码不能为空!");
				return "/background/framework/login";
			}
			
			// 验证用户账号与密码是否正确
			SysUser users = this.userDao.findByName(username);
			if (users == null || !users.getUserPassword().equals(password)) {
				request.setAttribute("error", "用户或密码不正确!");
			    return "/background/framework/login";
			}
			
			Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			HttpSession session = request.getSession(true);  
		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext); 
		    
		    // 当验证都通过后，把用户信息放在session里
			request.getSession().setAttribute("userSession", users);
			
			// 记录登录信息
			UserLoginLog userLoginList = new UserLoginLog();
			userLoginList.setUserId(users.getUserId());
			userLoginList.setLoginIp(Common.toIpAddr(request));
			userLoginLogService.add(userLoginList);
			
		} catch (AuthenticationException ae) {  
			request.setAttribute("error", "登录异常，请联系管理员！");
		    return "/background/framework/login";
		}
		
		return "redirect:index.html";
	}
	
	
	/**
	 * @return
	 */
	@RequestMapping ("index")
	public String index(Model model)
	{
		return "/background/framework/main";
	}
	
	@RequestMapping ("top")
	public String top(Model model)
	{
		return "/background/framework/top";
	}
	
	@RequestMapping ("left")
	public String left(Model model,HttpServletRequest request)
	{
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			//String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = request.getUserPrincipal().getName();
			List<Resource> resources = resourcesService.getResourcesByUserName(username);
			model.addAttribute("resources", resources);
		} catch (Exception e) {
			//重新登录时销毁该用户的Session
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		return "/background/framework/left";
	}
	
	@RequestMapping ("tab")
	public String tab(Model model)
	{
		return "/background/framework/tab/tab";
	}
	
	@RequestMapping ("center")
	public String center(Model model)
	{
		return "/background/framework/center";
	}
	
}
