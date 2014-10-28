package ${bussPackage}.${basePackage}.service;

import java.util.List;

import com.bruce.gogo.pulgin.mybatis.plugin.PageView;
import ${bussPackage}.${basePackage}.entity.${className};

/**
 * service 的接口定义(实际使用中可以自行添加)
 */
 
public interface ${className}Service {

	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public PageView find(PageView pageView,${className} ${lowerName});
	
	/**
	 * 通过名字查询
	 * @param t
	 * @return
	 */
	public ${className} findByName( String objName );

	/**
	 * 通过对象查询对象.
	 * @param t
	 * @return
	 */
	public ${className} findByProps(${className} ${lowerName});
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<${className}> findAll(${className} ${lowerName});
	
	
	/**
	 * 添加
	 * @param t
	 */
	public void add(${className} ${lowerName});
	
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(${className} ${lowerName});
	
	
	/**
	 * 修改所有属性.
	 * @param t
	 */
	public void updateAll(${className} ${lowerName});
	
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id);

	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(${className} ${lowerName});	

	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public ${className} getObjById(String objId);
	
	/**
	 * 获得总条数.
	 * @param id
	 * @return
	 */
	public Integer getObjsCount();	
	
}
