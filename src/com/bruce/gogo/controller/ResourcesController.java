package com.bruce.gogo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.base.util.Common;
import com.bruce.gogo.entity.Resource;
import com.bruce.gogo.entity.SysRole;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import com.bruce.gogo.service.ResourceService;
import com.bruce.gogo.service.SysUserService;

/**
 * @author admin
 * @version 1.0v
 */
@Controller
@RequestMapping(value="/background/resources/")
public class ResourcesController extends BaseController {
	@Autowired
	private ResourceService resourcesService;
	
	@Autowired
	private SysUserService userService;
	
	/**
	 * 跳到新增页面
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addUI(Model model){
		List<Resource> resources = resourcesService.findAll();
		model.addAttribute("resources", resources);
		return "/background/resources/add";
	} 
	
	/**
	 * 保存新增
	 * @param model
	 * @param resources
	 * @return
	 */
	@RequestMapping(value="add")
	public String add(Model model,Resource resources){
		resourcesService.add(resources);
		return "redirect:find.html";
	}
	
	/**
	 * 分页查询
	 * @param model
	 * @param resources
	 * @param pageNow
	 * @return
	 */
	@RequestMapping(value="find")
	public String find(Model model,Resource resources,String pageNow,String pageSize){
		
		PageView pageView = super.findPage(pageNow, pageSize);
		
		pageView = resourcesService.find(pageView, resources);
		model.addAttribute("pageView", pageView);
		return "/background/resources/list";
	}
	
	/**
	 * 根据id删除
	 * @param model
	 * @param resourcesId
	 * @return
	 */
	@RequestMapping(value="deleteById")
	public String deleteById(Model model,String resourcesId){
		resourcesService.delete(resourcesId);
		return "redirect:find.html";
	}
	
	/**
	 * 查询单条记录
	 * @param model
	 * @param resourcesId
	 * @param type
	 * @return
	 */
	@RequestMapping(value="getById")
	public String getById(Model model,String resourcesId,int typeKey){
		Resource resources = resourcesService.getById(resourcesId);
		model.addAttribute("resources", resources);
		List<Resource> resLists = resourcesService.findAll();
		model.addAttribute("resLists", resLists);
		if(typeKey == 1){
			return "/background/resources/edit";
		}else{
			return "/background/resources/show";
		}
	}
	
	/**
	 * 更新修改的信息
	 * @param model
	 * @param resources
	 * @return
	 */
	@RequestMapping(value="update")
	public String updateResources(Model model,Resource resources){
		resourcesService.modify(resources);
		return "redirect:find.html";
	}
	
	@RequestMapping(value="deleteAll")
	public String deleteAll(String[] check,Model model){
		for(String id : check){
			resourcesService.delete(id);
		}
		return "redirect:find.html";
	}
	
	/**
	 * 某个用户拥有的权限
	 * @return
	 */
	@RequestMapping(value="permissioUser")
	public String permissioUser(Model model,String userId){
		List<Resource> resources = resourcesService.getUserResources(userId);
		List<Resource> allRes = resourcesService.findAll();
		StringBuffer sb = new StringBuffer();
		sb.append("var data = [];");
		for (Resource r : allRes) {
			boolean flag = false;
			for (Resource ur : resources) {//用户拥有的权限
				if (ur.getId().equals(r.getId())) {
					sb.append("data.push({ fid: '"
							+ r.getId() + "', pfid: '"
							+ r.getParentId()
							+ "', fname: '" + r.getName()
							+ "',ischecked: true});");
					flag = true;
				}
			}
			if (!flag) {
				sb.append("data.push({ fid: '"
						+ r.getId() + "', pfid: '"
						+ r.getParentId()
						+ "', fname: '" + r.getName()
						+ "'});");

			}
		}
		SysRole roles = userService.findbyUserRole(userId);
		if(roles!=null){
			model.addAttribute("roleId", roles.getId());
		}
		
		model.addAttribute("resources", sb);
		
		return "/background/resources/permissioUser";
	}
	/**
	 * 某个角色拥有的权限
	 * @return
	 */
	@RequestMapping(value="permissioRole")
	public String permissioRole(Model model,String roleId){
		List<Resource> resources = resourcesService.getRoleResources(roleId);
		List<Resource> allRes = resourcesService.findAll();
		StringBuffer sb = new StringBuffer();
		sb.append("var data = [];");
		for (Resource r : allRes) {
			boolean flag = false;
			for (Resource ur : resources) {//用户拥有的权限
				if (ur.getId().equals(r.getId())) {
					sb.append("data.push({ fid: '"
							+ r.getId() + "', pfid: '"
							+ r.getParentId()
							+ "', fname: '" + r.getName()
							+ "',ischecked: true});");
					flag = true;
				}
			}
			if (!flag) {
				sb.append("data.push({ fid: '"
						+ r.getId() + "', pfid: '"
						+ r.getParentId()
						+ "', fname: '" + r.getName()
						+ "'});");

			}
		}
		model.addAttribute("roleId", roleId);
		model.addAttribute("resources", sb);
		return "/background/resources/permissioUser";
	}
	@ResponseBody
	@RequestMapping(value="saveRoleRescours")
	public String saveRoleRescours(String roleId,String rescId){
		String errorCode = "1000";
		List<String> list = Common.removeSameItem(Arrays.asList(rescId.split(",")));
		try {
			resourcesService.saveRoleRescours(roleId, list);
		} catch (Exception e) {
			e.printStackTrace();
			errorCode="1001";
		}
		return errorCode;
	}
	
	
	@RequestMapping(value="findByName")
	public void findByName(Model model,String name){
		String data = null;
		try {
			Resource resource = new Resource();
			resource.setName(name);
			//判断.
			Resource tempObj = resourcesService.findByProps(resource);
			if (tempObj!=null) {
				data = "({msg:'Y',content:'该信息已经存在,请重新填入新的对应信息!'})";
			}else {
				data = "({msg:'N',content:'没有和该信息一致的信息!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="findByKey")
	public void findByKey(Model model,String resKey){
		String data = null;
		try {
			Resource resource = new Resource();
			resource.setResKey(resKey);
			//判断.
			Resource tempObj = resourcesService.findByProps(resource);
			if (tempObj!=null) {
				data = "({msg:'Y',content:'该KEY已经存在,请重新填入新的对应KEY信息!'})";
			}else {
				data = "({msg:'N',content:'没有和该KEY一致的信息!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="findByUrl")
	public void findByUrl(Model model,String resUrl){
		String data = null;
		try {
			Resource resource = new Resource();
			resource.setResUrl(resUrl);
			//判断.
			Resource tempObj = resourcesService.findByProps(resource);
			if (tempObj!=null) {
				data = "({msg:'Y',content:'该URL已经存在,请重新填入新的URL信息!'})";
			}else {
				data = "({msg:'N',content:'没有和该URL一致的信息!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
}
