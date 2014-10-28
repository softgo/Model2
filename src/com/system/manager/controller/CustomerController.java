package com.system.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

import com.system.manager.entity.Customer;
import com.system.manager.service.CustomerService;

/**
 * 
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/customer/")
public class  CustomerController  extends BaseController {

	private final static Logger logger= Logger.getLogger(CustomerController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private CustomerService  customerService; 

	/**
	 * @param model
	 * 存放返回界面的model
	 * @return
	 */
	@RequestMapping("find")
	public String find(Model model, Customer customer, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = customerService.find(pageView, customer);
		model.addAttribute("pageView", pageView);
		return "/background/customer/list";
	}
	
	/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
			String data = null;
			try {
				Customer customer = customerService.findByName(objName);
				if (customer != null) {
					data = "({msg:'Y',content:'按需求填写!'})";
				}else {
					data = "({msg:'N',content:'按需求填写!'})";
				}
				//返回.
				ajaxJson(data);
			} catch (Exception e) {
				logger.info("查找出错!"+e.getLocalizedMessage());
			}
	}
	
	/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	@RequestMapping("findByProps")
	public void findByProps(Model model, Customer customer) {
			String data = null;
			try {
				Customer object = customerService.findByProps(customer);
				if (object != null) {
					data = "({msg:'Y',content:'按需求填写!'})";
				}else {
					data = "({msg:'N',content:'按需求填写!'})";
				}
				//返回.
				ajaxJson(data);
			} catch (Exception e) {
				logger.info("查找出错!"+e.getLocalizedMessage());
			}
	}
	

	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI() {
		return "/background/customer/add";
	}

	
	/**
	*
	 * 保存数据
	 * 
	 * @param model
	 * @param videoType
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model, Customer customer) {
		customerService.add(customer);
		return "redirect:find.html";
	}
	
	/**
	
	 * 通过Id获得对象.
	 * 
	 * @param model
	 * @param videoTypeIds
	 * @return
	 */
	@RequestMapping("getById")
	public String getById(Model model, String objId, int type) {
		 Customer customer =customerService.getObjById(objId);
		  model.addAttribute("customer", customer);
		 return "/customer/show";
	}
	
	/**
	 * 通过Id删除
	 * @param model
	 * @param videoTypeId
	 * @return
	 */
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		customerService.deleteById(objId);
		return "redirect:find.html";
	}
	
	/**
	 * 修改对象.
	 * @param model
	 * @param videoTypeId
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, Customer customer) {
		customerService.update(customer);
		return "redirect:find.html";
	}
	
}
