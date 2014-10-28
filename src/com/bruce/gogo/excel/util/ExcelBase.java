package com.bruce.gogo.excel.util;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

public class ExcelBase {
	public HttpServletResponse response; // 响应对象
	private HSSFWorkbook workbook; // 产生工作薄对象
	private HSSFSheet sheet; // 产生工作表对象
	private HSSFRow row; // 产生一行
	private HSSFCell cell; // 产生单元格
	private int row_count = 0; // 记录创建到第几行
	private HSSFCellStyle dataStyle;
	private HSSFCellStyle dataStyle_YELLOW;
	private HSSFCellStyle dataStyle_TURQUOISE;
	private static final String YELLOW = "FFFF00";
	private static final String TURQUOISE = "66FFFF";
	private static final String WHITE = "FFFFFF";
	private String sheetName; // 生成Excel的sheet名称

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ExcelBase() {

	}

	/**
	 * 构造方法, 初始化
	 * 
	 * @param sheetName
	 *            Excel的sheet名称
	 * @param response
	 *            响应对象
	 */
	public ExcelBase(String sheetName, HttpServletResponse response) {
		this.sheetName = sheetName;
		this.response = response;

		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
		// 单元格背景颜色-无颜色
		dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 单元格背景颜色-黄色
		dataStyle_YELLOW = workbook.createCellStyle();
		dataStyle_YELLOW.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle_YELLOW.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		dataStyle_YELLOW.setFillForegroundColor(HSSFColor.YELLOW.index);
		dataStyle_YELLOW.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 单元格背景颜色-浅青绿
		dataStyle_TURQUOISE = workbook.createCellStyle();
		dataStyle_TURQUOISE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle_TURQUOISE.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		dataStyle_TURQUOISE
				.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		dataStyle_TURQUOISE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 为了工作表能支持中文,设置字符集为UTF_16
		workbook.setSheetName(0, sheetName);

		this.response.reset();
		this.response.setContentType("application/vnd.ms-excel");
		this.response.setHeader("Content-Disposition", "filename=excel");
	}

	/**
	 * 创建Excel列表头部 (非规则情况 需要合并单元格)
	 * 
	 * @param region
	 *            记录合并单元格的坐标 (起始坐标为 0,0)
	 * @param field_one
	 *            Excel列表头第一行的内容
	 * @param field_two
	 *            Excel列表头第二行的内容
	 * 
	 * 例: int[][] region = { { 0, 0, 1, 0}, 表示 0行0列 与 1行0列 两个单元格合并 { 0, 1, 1,
	 * 1}, 表示 0行1列 与 1行1列 两个单元格合并 { 0, 2, 1, 2}, ... { 0, 3, 1, 3}, ... { 0, 4,
	 * 0, 7}, 表示 0行4列 0行5列 0行6列 0行7列 四个单元格合并 { 0, 8, 0, 11}, ... }; String[]
	 * field_one = {"所属大区", "公司等级", "公司名称", "人数", "按年龄分布划分", "按年龄分布划分",
	 * "按年龄分布划分", "按年龄分布划分", "按年龄分布划分比例", "按年龄分布划分比例", "按年龄分布划分比例",
	 * "按年龄分布划分比例"}; String[] field_two = {"所属大区", "公司等级", "公司名称", "人数",
	 * "22岁以下", "22岁~25岁", "25岁~28岁", "28岁以上", "22岁以下比例", "22岁~25岁比例",
	 * "25岁~28岁比例", "22岁~25岁比例"};
	 * 
	 * 执行效果如下：
	 * 
	 * 所属大区 公司级别 公司名称 人数 按年龄分布划分 按年龄分布划分比例 22岁以下 22岁~25岁 25岁~28岁 28岁以上 22岁以下比例
	 * 22岁~25岁比例 25岁~28岁比例 22岁~25岁比例
	 */
	public void createHead(int[][] region, String[] field_one,
			String[] field_two) {
		// 创建报表头(2行)
		this.createRow();
		for (int i = 0; i < field_one.length; i++) {
			// 创建第一行各个字段名称的单元格
			cell = row.createCell((short) i);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 给单元格内容赋值
			cell.setCellValue(field_one[i]);
			cell.setCellStyle(dataStyle);
		}
		this.createRow();
		for (int i = 0; i < field_two.length; i++) {
			// 创建第一行各个字段名称的单元格
			cell = row.createCell((short) i);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 给单元格内容赋值
			cell.setCellValue(field_two[i]);
			cell.setCellStyle(dataStyle);
		}

		// 合并单元格
		for (int i = 0; i < region.length; i++) {
			Region the_region = new Region(region[i][0], (short) region[i][1],
					region[i][2], (short) region[i][3]);
			sheet.addMergedRegion(the_region);
		}
	}

	/**
	 * 创建Excel列表头部 (规则情况 不需要合并单元格)
	 * 
	 * @param fieldName
	 *            列表头部内容
	 * 
	 * 例-创建三列: String[] fieldName = {"公司", "部门", "岗位"};
	 */
	public void createHead(String[] fieldName) {
		// 创建报表头
		this.createRow();
		for (int i = 0; i < fieldName.length; i++) {
			String key = fieldName[i];

			// 创建第一行各个字段名称的单元格
			cell = row.createCell((short) i);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 给单元格内容赋值
			cell.setCellValue(key);
			cell.setCellStyle(dataStyle);
		}

	}

	/**
	 * 创建Excel
	 * 
	 * @param list
	 *            统计出来的列表内容
	 */
	public void createExcel(List list) {
		try {
			// 写入各条记录,每条记录对应excel表中的一行
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				this.createRow(); // 创建一行
				for (int j = 0; j < obj.length; j++) {
					cell = row.createCell((short) j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dataStyle);
					if (obj[j] == null)
						cell.setCellValue("");
					else if (j == 0
							&& (obj[j].toString().equals("1") || obj[j]
									.toString().equals("0")))
						cell.setCellValue("--");
					else if ("String".equals(obj[j].getClass().getSimpleName()))
						cell.setCellValue((String) obj[j]);
					else
						cell.setCellValue(obj[j].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			OutputStream output = response.getOutputStream();
			output.flush();
			workbook.write(output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建Excel
	 * 
	 * @param list
	 *            统计出来的列表内容
	 */
	public void createExcel(String[] fieldName, List list) {
		try {
			// 写入各条记录,每条记录对应excel表中的一行
			if (list.size() > 65535) {
				int start = 0;
				int mid = 0;
				for (int i = start; i < list.size(); i++) {
					mid += 1;
					if (mid % 65535 == 0) {
						start = start + 65535;
						row_count = 0;
						sheet = workbook.createSheet();
						this.createHead(fieldName);
					}
					Object[] obj = (Object[]) list.get(i);
					this.createRow(); // 创建一行
					for (int j = 0; j < obj.length; j++) {
						cell = row.createCell((short) j);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dataStyle);
						if (obj[j] == null)
							cell.setCellValue("");
						else if (j == 0
								&& (obj[j].toString().equals("1") || obj[j]
										.toString().equals("0")))
							cell.setCellValue("--");
						else if ("String".equals(obj[j].getClass()
								.getSimpleName()))
							cell.setCellValue((String) obj[j]);
						else
							cell.setCellValue(obj[j].toString());
					}
				}
			} else {
				this.createHead(fieldName);
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					this.createRow(); // 创建一行
					for (int j = 0; j < obj.length; j++) {
						cell = row.createCell((short) j);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dataStyle);
						if (obj[j] == null)
							cell.setCellValue("");
						else if (j == 0
								&& (obj[j].toString().equals("1") || obj[j]
										.toString().equals("0")))
							cell.setCellValue("--");
						else if ("String".equals(obj[j].getClass()
								.getSimpleName()))
							cell.setCellValue((String) obj[j]);
						else
							cell.setCellValue(obj[j].toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			OutputStream output = response.getOutputStream();
			output.flush();
			workbook.write(output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出前后台人员比例处理数据
	 * @param list
	 */
	public void createExcel_ba(List list)
	{
    	try
    	{
	    	//写入各条记录,每条记录对应excel表中的一行
    		int f = list.size();
	    	for(int i = 0; i < f; i++)
	    	{
	    		Object[] obj = (Object[])list.get(i);
	    		this.createRow();	//创建一行
	    		if(i+1==f)
	    		{
	    			cell = row.createCell((short)0);
	    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    			cell.setCellValue("合   计");
	    			cell.setCellStyle(dataStyle);
	    			sheet.addMergedRegion(new Region(i+2, (short)0, i+2, (short)2));
	    			for(int j = 3; j < obj.length; j++)
		    		{
		    			cell = row.createCell((short)j);
		    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		    			cell.setCellStyle(dataStyle);
		    			if(obj[j] == null)
		    				cell.setCellValue("");
		    			else if("String".equals(obj[j].getClass().getSimpleName()))
		    				cell.setCellValue((String)obj[j]);
		    			else
		    				cell.setCellValue(obj[j].toString());
		    		}
	    		}
	    		else
	    		{
		    		for(int j = 0; j < obj.length; j++)
		    		{
		    			cell = row.createCell((short)j);
		    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		    			cell.setCellStyle(dataStyle);
		    			if(obj[j] == null)
		    				cell.setCellValue("");
		    			else if("String".equals(obj[j].getClass().getSimpleName()))
		    				cell.setCellValue((String)obj[j]);
		    			else
		    				cell.setCellValue(obj[j].toString());
		    		}
	    		}
	    	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	try 
    	{
    		OutputStream output = response.getOutputStream();
	    	output.flush();
	    	workbook.write(output);
	    	output.flush();
	    	output.close();
    	} 
    	catch (Exception e) 
    	{
	    	e.printStackTrace();
    	}
	}
	/**
	 * 分公司干部任命情况数据处理
	 * @param list
	 */
	public void createExcel_rm(String[][] Array)
	{
		try
    	{
	    	//写入各条记录,每条记录对应excel表中的一行
			String cell_value = "";
			String cell_style = "";
	    	for(int i = 0; i < Array.length; i++)
	    	{
	    		this.createRow();	//创建一行
	    		for(int j = 0; j < Array[i].length; j++)
	    		{
	    			cell = row.createCell((short)j);
	    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    			if(Array[i][j] == null || Array[i][j].toString().equals(""))
	    			{
	    				cell.setCellValue("");
	    				cell.setCellStyle(dataStyle);
	    			}
	    			else
	    			{
	    				cell_value = Array[i][j].toString().substring(Array[i][j].toString().indexOf(">")+1, Array[i][j].toString().lastIndexOf("<"));
		    			cell_style = Array[i][j].toString().substring(Array[i][j].toString().indexOf("#")+1,Array[i][j].toString().indexOf("#")+7);
		    			if(cell_value.equals("")||cell_value==null)
		    			{
		    				cell.setCellValue("");
		    				cell.setCellStyle(dataStyle);
		    			}
		    			else if(cell_value.equals("&nbsp;")) 
		    			{	
		    				cell.setCellValue("");
		    				cell.setCellStyle(dataStyle);
		    			}
		    			else if(!cell_value.equals("&nbsp;")&& cell_style.equals(YELLOW)) 
		    			{	
		    				cell.setCellValue(cell_value);
		    				cell.setCellStyle(dataStyle_YELLOW);
		    			}
		    			else if(!cell_value.equals("&nbsp;")&& cell_style.equals(TURQUOISE)) 
		    			{	
		    				cell.setCellValue(cell_value);
		    				cell.setCellStyle(dataStyle_TURQUOISE);
		    			}
		    			else
		    			{
		    				cell.setCellValue(cell_value);
		    				cell.setCellStyle(dataStyle);
		    			}
	    			}
	    		}
	    	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	try 
    	{
    		OutputStream output = response.getOutputStream();
	    	output.flush();
	    	workbook.write(output);
	    	output.flush();
	    	output.close();
    	} 
    	catch (Exception e) 
    	{
	    	e.printStackTrace();
    	}
	}
	
	/**
	 * 创建一行，并且行的总记录+1
	 */
	private void createRow() {
		row = sheet.createRow(row_count);
		row_count = row_count + 1;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

}
