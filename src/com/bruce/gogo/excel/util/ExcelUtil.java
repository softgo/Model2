package com.bruce.gogo.excel.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
	
	public static <T> void main(String[] args) {
		ExcelUtil testExcel = new ExcelUtil();
		try {
			InputStream is = new FileInputStream(new File("E:/1.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>反射<br>
	 * <b>日期：</b> 2013-5-30 <br>
	 * @param list
	 * @param suffix
	 * @return
	 */
	public <T> Workbook writeExcel2(Map<String,List<T>> ml, String suffix,int lineIndex) {
		Workbook wb=null;
		if(ml==null){
			return null;
		}
		if(null!=suffix && suffix.endsWith("xlsx")){
			//xlsx
			wb=new XSSFWorkbook();
		}else{
			wb=new HSSFWorkbook();
			//xls
		}
		for (Map.Entry<String, List<T>> map : ml.entrySet()) {
			String key = map.getKey();
			Sheet sheet=wb.createSheet(key);
			List<T> list = map.getValue();
			for (int i = 0; i < list.size(); i++) {
				T t = list.get(i);
				Class<?> c = t.getClass();
				Field[] fields = c.getDeclaredFields();
				Row row=sheet.createRow(lineIndex+i);
				for (int j = 0; j < fields.length; j++) {
					try {
						PropertyDescriptor pd = new PropertyDescriptor(fields[j].getName(), c);
						Method method = pd.getReadMethod(); //get 读
						// Method method = pd.getWriteMethod();//set 写
						Object obj = method.invoke(t);
						String cellValue=String.valueOf(obj);
						Cell cell=row.createCell(j);
						if("null".equals(cellValue)){
							cell.setCellValue("");
						}else{
							cell.setCellValue(String.valueOf(obj));
						}
						//System.out.println(obj+"j:"+j);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return  wb;
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>创建excel<br>
	 * <b>日期：</b> 2013-5-30 <br>
	 * @param ml
	 * @param suffix
	 * @return
	 */
	public Workbook writeExcel(Map<String, List<Map<String, Object>>> ml,String suffix,int lineIndex){
		Workbook wb=null;
		if(ml==null){
			return null;
		}
		if(null!=suffix && suffix.endsWith("xlsx")){
			//xlsx
			wb=new XSSFWorkbook();
		}else{
			wb=new HSSFWorkbook();
			//xls
		}
		for (Map.Entry<String, List<Map<String, Object>>> map : ml.entrySet()) {
			String key=map.getKey();
			Sheet sheet=wb.createSheet(key);
			List<Map<String, Object>> lm=map.getValue();
			for(int i=0;i<lm.size();i++){
				Row row=sheet.createRow(lineIndex+i);
				Map<String,Object> mapCell=lm.get(i);
				int colume=0;
				for(Object value:mapCell.values()){
					Cell cell=row.createCell(colume);
					if(value instanceof java.util.Date || value instanceof java.sql.Date){ //日期转换
						try {
							cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else if(value instanceof Integer){ //数字转换
						cell.setCellValue((Integer)value);
					}else{
						cell.setCellValue(String.valueOf(value)); //其他转换成String
					}
					colume++;
				}
			}
		}
		return wb;
	}
	


	/**
	 * 
	 * <br>
	 * <b>功能：</b>读取xls,xlsx<br>
	 * <b>日期：</b> 2013-5-29 <br>
	 * 
	 * @param is
	 * @return
	 */
	public Map<String, List<Object[]>> readExcel(InputStream is,int readline) {
		Workbook wb = null;
		Map<String, List<Object[]>> mapList = null;
		List<Object[]> listArray = null;
		Object[] values = null;
		try {
			// 创建workbook对象
			wb = WorkbookFactory.create(is);
			// 获取sheet页数
			int Sheets = wb.getNumberOfSheets();
			if (Sheets > 0) {
				mapList = new HashMap<String, List<Object[]>>();
			}
			for (int i = 0; i < Sheets; i++) {
				// 每一页
				listArray = new ArrayList<Object[]>();
				// 创建sheet
				Sheet sheet = wb.getSheetAt(i);
				// 总行数
				int rowNum = sheet.getLastRowNum();
				System.out.println("rowNum:" + rowNum);
				for (int j = readline; j < rowNum; j++) {
					// 行
					Row row = sheet.getRow(j);
					// cess
					int cessNum = row.getLastCellNum();
					// 每一行
					values = new Object[cessNum]; // 列数

					//System.out.println("cessNum:" + cessNum);
					for (int x = 0; x < cessNum; x++) {
						Cell cell = row.getCell(x);
						// 判断类型
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							values[x] = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								values[x] = cell.getDateCellValue();
							} else {
								values[x] = cell.getNumericCellValue();
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							values[x] = cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA:
							values[x] = cell.getCellFormula();
							break;
						default:
							values[x] = "";
						}
					}
					listArray.add(values);
				}
				mapList.put(wb.getSheetName(i), listArray);
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>读取xls,xlsx<br>
	 * <b>日期：</b> 2013-5-29 <br>
	 * 
	 * @param is
	 * @param names
	 *            列名称
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> readExcel(InputStream is, String[] names, int readline) {
		Workbook wb = null;
		Map<String, List<Map<String, Object>>> mapList = null;
		List<Map<String, Object>> listMap = null;
		Map<String, Object> map = null;
		try {
			// 创建workbook对象
			wb = WorkbookFactory.create(is);
			// 获取sheet页数
			int Sheets = wb.getNumberOfSheets();
			if (Sheets > 0) {
				mapList = new HashMap<String, List<Map<String, Object>>>();
			}
			for (int i = 0; i < Sheets; i++) {
				// 每一页
				listMap = new ArrayList<Map<String, Object>>();
				// 创建sheet
				Sheet sheet = wb.getSheetAt(i);
				// 总行数
				int rowNum = sheet.getLastRowNum();
				System.out.println("rowNum:" + rowNum);
				for (int j = readline; j < rowNum; j++) {
					// 每一行
					map = new HashMap<String, Object>();
					// 行
					Row row = sheet.getRow(j);
					// cess
					int cessNum = row.getLastCellNum();
					
					//System.out.println("cessNum:" + cessNum);
					for (int x = 0; x < cessNum; x++) {
						Cell cell = row.getCell(x);
						// 判断类型
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							map.put(names[x], cell.getRichStringCellValue().getString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								map.put(names[x], cell.getDateCellValue());
							} else {
								map.put(names[x], cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							map.put(names[x], cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							map.put(names[x], cell.getCellFormula());
							break;
						default:
							map.put(names[x], null);
						}
					}
					listMap.add(map);
				}
				mapList.put(wb.getSheetName(i), listMap);
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>反射<br>
	 * <b>日期：</b> 2013-5-31 <br>
	 * @param is
	 * @param names
	 * @param readline
	 * @param bean
	 * @return
	 */
	public <T> Map<String, List<T>>  readExcel(InputStream is,String[] names, int readline,Class<T> bean){
		Workbook wb = null;
		Map<String, List<T>> mapList = null;
		List<T> listMap = null;
		T t = null;
		try {
			// 创建workbook对象
			wb = WorkbookFactory.create(is);
			// 获取sheet页数
			int Sheets = wb.getNumberOfSheets();
			if (Sheets > 0) {
				mapList = new HashMap<String, List<T>>();
			}
			for (int i = 0; i < Sheets; i++) {
				// 每一页
				listMap = new ArrayList<T>();
				// 创建sheet
				Sheet sheet = wb.getSheetAt(i);
				// 总行数
				int rowNum = sheet.getLastRowNum();
				System.out.println("rowNum:" + rowNum);
				for (int j = readline; j < rowNum; j++) {
					// 每一行
					t = bean.newInstance(); //bean
					// 行
					Row row = sheet.getRow(j);
					// cess
					int cessNum = row.getLastCellNum();
					
					//System.out.println("cessNum:" + cessNum);
					for (int x = 0; x < cessNum; x++) {
						Cell cell = row.getCell(x);
						PropertyDescriptor propertyDes=new PropertyDescriptor(names[x], t.getClass());
						Method method=propertyDes.getWriteMethod();
						// 判断类型
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							method.invoke(t, cell.getRichStringCellValue().getString());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								method.invoke(t, cell.getDateCellValue());
							} else {
								method.invoke(t, cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							method.invoke(t, cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							method.invoke(t, cell.getCellFormula());
							break;
						default:
							//map.put(names[x], null);
						}
					}
					listMap.add(t);
				}
				mapList.put(wb.getSheetName(i), listMap);
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
