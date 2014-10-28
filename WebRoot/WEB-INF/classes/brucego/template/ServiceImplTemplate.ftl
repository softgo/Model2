package ${bussPackage}.${basePackage}.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.dao.${className}Dao;
import ${bussPackage}.${basePackage}.service.${className}Service;

/**
 * 
* 接口的实现.
 */
@Transactional
@Service("${lowerName}Service")
public class ${className}ServiceImpl  implements  ${className}Service {

	private final static Logger logger= Logger.getLogger(${className}Service.class);

	@Autowired
    private ${className}Dao  ${lowerName}Dao;
		
    /**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public PageView find(PageView pageView,${className} ${lowerName} ){
		List<${className}> list = ${lowerName}Dao.find(pageView, ${lowerName});
		pageView.setRecords(list);
		return pageView;
	}
	
		/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	public ${className} findByName(String objName){
			return ${lowerName}Dao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param t
	 * @return
	 */
	public ${className} findByProps(${className} ${lowerName}){
		return ${lowerName}Dao.findByProps(${lowerName});
	}
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<${className}> findAll(${className} ${lowerName}){
		return ${lowerName}Dao.findAll(${lowerName});
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id){
		${lowerName}Dao.deleteById(id);
	}

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(${className} ${lowerName}){
		${lowerName}Dao.delete(${lowerName});
	}
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(${className} ${lowerName}){
		${lowerName}Dao.update(${lowerName});
	}
	
	/**
	 * 修改
	 * @param t
	 */
	public void updateAll(${className} ${lowerName}){
		${lowerName}Dao.updateAll(${lowerName});
	}
	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public ${className} getObjById(String objId){
		return ${lowerName}Dao.getObjById(objId);
	}
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(${className} ${lowerName}){
		 ${lowerName}Dao.add(${lowerName});
	}
	
	/**
	 * 添加
	 * @param t
	 */
	public Integer getObjsCount(){
		 return ${lowerName}Dao.getObjsCount();
	}

}
