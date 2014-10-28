package ${bussPackage}.${basePackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.bruce.gogo.base.BaseController;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.service.${className}Service;

/**
 * 
 * @author admin
 * 
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/${lowerName}/")
public class  ${className}Controller  extends BaseController {

	private final static Logger logger= Logger.getLogger(${className}Controller.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ${className}Service  ${lowerName}Service; 

	/**
	 * @param model
	 * 存放返回界面的model
	 * @return
	 */
	@RequestMapping("find")
	public String find(Model model, ${className} ${lowerName}, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = ${lowerName}Service.find(pageView, ${lowerName});
		model.addAttribute("pageView", pageView);
		return "/${lowerName}/list";
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
				${className} ${lowerName} = ${lowerName}Service.findByName(objName);
				if (${lowerName} != null) {
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
	public void findByProps(Model model, ${className} ${lowerName}) {
			String data = null;
			try {
				${className} object = ${lowerName}Service.findByProps(${lowerName});
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
	*
	 * 保存数据
	 * 
	 * @param model
	 * @param videoType
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model, ${className} ${lowerName}) {
		${lowerName}Service.add(${lowerName});
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
		 ${className} ${lowerName} =${lowerName}Service.getObjById(objId);
		  model.addAttribute("${lowerName}", ${lowerName});
		 return "/${lowerName}/show";
	}
	
	/**
	 * 通过Id删除
	 * @param model
	 * @param videoTypeId
	 * @return
	 */
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		${lowerName}Service.deleteById(objId);
		return "redirect:find.html";
	}
	
	/**
	 * 修改对象.
	 * @param model
	 * @param videoTypeId
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, ${className} ${lowerName}) {
		${lowerName}Service.update(${lowerName});
		return "redirect:find.html";
	}
	
}
