package com.bruce.gogo.codeutil;

import com.gogo.code.gengerate.def.FtlDef;
import com.gogo.code.gengerate.factory.CodeGenerateFactory;

/**
 * 
 * 生成地层代码的工具类.
 * 
 * @author admin
 *
 */
public class CodeProductorUtil {

	public static void main(String[] args) {
		String tableName = "sysuser";
	    String codeName = "用户管理";
	    //String entityPackage = "bruce\\gogo\\"; //如果是目录就用"\\",否则就不用.
	    String entityPackage = "a\\"; //如果是目录就用"\\",否则就不用.
	    CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_01);
	}
	
}
