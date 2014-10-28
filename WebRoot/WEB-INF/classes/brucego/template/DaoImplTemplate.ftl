package ${bussPackage}.${basePackage}.dao.impl;

import org.springframework.stereotype.Repository;
import com.bruce.gogo.base.impl.BaseDaoImpl;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.dao.${className}Dao;

/**
 * 实现.
 * 
 * @author admin
 *
 */
 @Repository("${lowerName}Dao")
public class ${className}DaoImpl extends BaseDaoImpl<${className}> implements ${className}Dao{
	
}
