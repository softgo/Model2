package com.bruce.gogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.entity.SysUser;
import com.bruce.gogo.entity.UserRole;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.SysRoleService;
import com.bruce.gogo.service.SysUserService;


/**
 * 
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/sysuser/")
public class SysUserController  extends BaseController {
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysRoleService rolesService;
	
	/**
	 * @param model
	 * 存放返回界面的model
	 * @return
	 */
	@RequestMapping("find")
	public String find(Model model, SysUser user, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = userService.find(pageView, user);
		model.addAttribute("pageView", pageView);
		return "/background/sysuser/list";
	}

	/**
	 * 保存数据
	 * 
	 * @param model
	 * @param videoType
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model, SysUser user) {
		userService.add(user);
		return "redirect:find.html";
	}
	
	/**
	 * 修改密码.
	 * 
	 * @param model
	 * @param videoType
	 * @return
	 */
	@RequestMapping("updatePass")
	public String updatePass(Model model,String userId, String userPassword) {
		SysUser user = userService.getById(userId);
		user.setUserPassword(userPassword);
		userService.modify(user);
		return "redirect:find.html";
	}
	
	/**
	 * 修改密码.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("updatePassUI")
	public String updatePassUI(Model model,String userId ) {
		SysUser user = userService.getById(userId);
		model.addAttribute("user", user);
		return "/background/sysuser/updatePass";
	}

	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI() {
		return "/background/sysuser/add";
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param videoTypeId
	 * @return
	 */
	@RequestMapping("deleteById")
	public String deleteById(Model model, String userId) {
		userService.delete(userId);
		return "redirect:find.html";
	}

	/**
	 * 修改界面
	 * 
	 * @param model
	 * @param videoTypeIds
	 * @return
	 */
	@RequestMapping("getById")
	public String getById(Model model, String userId, int type) {
		SysUser user = userService.getById(userId);
		model.addAttribute("user", user);
		 List<SysRole> roles=rolesService.findAll();
		 model.addAttribute("roles", roles);
		if (type == 1) {
			return "/background/sysuser/edit";
		} else {
			return "/background/sysuser/show";
		}
	}

	/**
	 * 更新类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, SysUser user,UserRole userRoles) {
		userService.modify(user);
		if(userRoles.getRoleId()!=null)
		rolesService.saveUserRole(userRoles);
		return "redirect:find.html";
	}

	/**
	 * 删除所选的
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteAll")
	public String deleteAll(Model model, String[] check) {
		for (String string : check) {
			userService.delete(string);
		}
		return "redirect:find.html";
	}
	
	/**
	 * 给用户分配角色界面
	 * @return
	 */
	@RequestMapping("userRole")
	public String userRole(Model model,String userId){
		SysUser user = userService.getById(userId);
		model.addAttribute("user", user);
		List<SysRole> roles = rolesService.findAll();
		model.addAttribute("roles", roles);
		return "/background/sysuser/userRole";
	}
	
	/**
	 * 保存用户分配角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping("allocation")
	public String allocation(Model model,UserRole userRoles){
		String errorCode = "1000";
		try {
			rolesService.saveUserRole(userRoles);
		} catch (Exception e) {
			e.printStackTrace();
			errorCode="1001";
		}
		return errorCode;
	}
	
	
	/**
	 * 查找是否已经存在的名字.
	 * @param model
	 * @param userName
	 * @return
	 */
	@RequestMapping("findByName")
	public void findByName(Model model,String userName){
		String data = null;
		try {
			SysUser sysUser = userService.findByName(userName);
			if (sysUser!=null) {
				data = "({msg:'Y',content:'该用户名已经存在,请重新填入新的用户名!'})";
			}else {
				data = "({msg:'N',content:'没有和该用户名一致的用户!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
}