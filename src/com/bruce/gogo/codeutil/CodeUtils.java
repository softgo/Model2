package com.bruce.gogo.codeutil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 
 * 简易的代码生成器.-- 
 * 
 * 通过配置 CodeConfiguration.xml 来实现操作.
 * 
 * @author admin
 *
 */
public class CodeUtils {

	public static void main(String[] args) {

		List<String> warnings = new ArrayList<String>();
		
		boolean overwrite = true;
		
		String genCfg = "/CodeConfiguration.xml";
		
		File configFile = new File(CodeUtils.class.getResource(genCfg).getFile());
		
		ConfigurationParser cp = new ConfigurationParser(warnings);
		
		Configuration config = null;
		
		try {
			config = cp.parseConfiguration(configFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		
		MyBatisGenerator myBatisGenerator = null;
		
		try {
			
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
