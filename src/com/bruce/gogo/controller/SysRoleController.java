package com.bruce.gogo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.entity.Resource;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.ResourceService;
import com.bruce.gogo.service.SysRoleService;


/**
 * 
 * @author admin
 * @version 1.0v
 */
@Controller
@RequestMapping(value="/background/sysrole/")
public class SysRoleController  extends BaseController {
	@Autowired
	private SysRoleService rolesService;
	@Autowired
	private ResourceService resourcesService;
	/**
	 * 跳到新增页面
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addUI(Model model){
		this.permissio(model);
		return "/background/sysrole/add";
	} 
	/**
	 * 权限树
	 * @return
	 */
	@RequestMapping(value="permissio")
	public String permissio(Model model){
		List<Resource> allRes = resourcesService.findAll();
		StringBuffer sb = new StringBuffer();
		sb.append("var data = [];");
		for (Resource r : allRes) {
				sb.append("data.push({ fid: '"
						+ r.getId() + "', pfid: '"
						+ r.getParentId()
						+ "', fname: '" + r.getName()
						+ "'});");

			}
		model.addAttribute("resources", sb);
		return "/background/resources/permissioUser";
	}
	/**
	 * 保存新增
	 * @param model
	 * @param role
	 * @return
	 */
	@RequestMapping(value="add")
	public String add(Model model,SysRole role,String rescId){
		rolesService.add(role);
		return "redirect:find.html";
	}
	
	/**
	 * 分页查询
	 * @param model
	 * @param role
	 * @param pageNow
	 * @return
	 */
	@RequestMapping(value="find")
	public String find(Model model,SysRole role, String pageNow,String pageSize ){
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = rolesService.find(pageView, role);
		model.addAttribute("pageView", pageView);
		return "/background/sysrole/list";
	}
	
	/**
	 * 根据id删除
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="deleteById")
	public String deleteById(Model model,String roleId){
		rolesService.delete(roleId);
		return "redirect:find.html";
	}
	
	/**
	 * 查询单条记录
	 * @param model
	 * @param roleId
	 * @param type
	 * @return
	 */
	@RequestMapping(value="getById")
	public String getById(Model model,String roleId,int typeKey){
		SysRole role = rolesService.getById(roleId);
		model.addAttribute("role", role);
		if(typeKey == 1){
			return "/background/sysrole/edit";
		}else{
			return "/background/sysrole/show";
		}
	}
	
	/**
	 * 更新修改的信息
	 * @param model
	 * @param role
	 * @return
	 */
	@RequestMapping(value="update")
	public String updaterole(Model model,SysRole role){
		rolesService.modify(role);
		return "redirect:find.html";
	}
	
	@RequestMapping(value="deleteAll")
	public String deleteAll(String[] check,Model model){
		for(String id : check){
			rolesService.delete(id);
		}
		return "redirect:find.html";
	}
	
	/**
	 * 查找是否已经存在的名字.
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping("findByName")
	public void findByName(Model model,String name){
		String data = null;
		try {
			SysRole sysRole = rolesService.findByName(name);
			if (sysRole!=null) {
				data = "({msg:'Y',content:'该角色名已经存在,请重新填入新的角色!'})";
			}else {
				data = "({msg:'N',content:'没有和该角色名名一致的角色!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
//	/**
//	 * 某个用户拥有的权限
//	 * @return
//	 */
//	@RequestMapping(value="permissioUser")
//	public String permissioUser(Model model,String userId){
//		List<role> role = rolesService.getUserrole(userId);
//		List<role> allRes = rolesService.findAll();
//		StringBuffer sb = new StringBuffer();
//		sb.append("var data = [];");
//		for (role r : allRes) {
//			boolean flag = false;
//			for (role ur : role) {//用户拥有的权限
//				if (ur.getId().equals(r.getId())) {
//					sb.append("data.push({ fid: '"
//							+ r.getId() + "', pfid: '"
//							+ r.getParentId()
//							+ "', fname: '" + r.getName()
//							+ "',ischecked: true});");
//					flag = true;
//				}
//			}
//			if (!flag) {
//				sb.append("data.push({ fid: '"
//						+ r.getId() + "', pfid: '"
//						+ r.getParentId()
//						+ "', fname: '" + r.getName()
//						+ "'});");
//
//			}
//		}
//		Roles roles = userService.findbyUserRole(userId);
//		model.addAttribute("roles", roles);
//		model.addAttribute("role", sb);
//		return "/background/role/permissioUser";
//	}
	
	
}
