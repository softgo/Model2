package com.bruce.gogo.excel.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
 
/**
 * 报表创建基类
 */
public class ExcelReport {
    
    /** 工作簿 */
    protected HSSFWorkbook workbook = null;
    /** 表格 */
    protected HSSFSheet sheet = null;
    /** 画图管理器 */
    private HSSFPatriarch patriarch = null;
    /** 默认表格名称的表格序号 */
    private int sheetIdx = 1;
    /** 表格行序号 */
    protected int rowIdx = 0;
    /** 默认样式 */
    protected HSSFCellStyle styleDefault = null;
    /** 报表头样式*/
    protected HSSFCellStyle styleTitle = null;
    /** 图片描述样式 */
    protected HSSFCellStyle styleDesc = null;
    /** 标题样式，在子类中定义 */
    protected HSSFCellStyle styleCaption = null;
    /** 表格头样式，在子类中定义 */
    protected HSSFCellStyle styleRowHead = null;
    
    /**
     * 构造函数
     */
    public ExcelReport(){
        
    }
    
    /**
     * 创建相关基本对象
     * @param sheetName sheet页名称
     */
    public void createSheet(String sheetName){
        //创建工作簿
        if(workbook==null){
            workbook = new HSSFWorkbook();
        }
        
        //创建表格
        if(workbook!=null){
            if(sheetName==null || sheetName.equals("")){
                sheet = workbook.createSheet("sheet" + String.valueOf(sheetIdx++));
            }else{
                sheet = workbook.createSheet(sheetName);
            }
            
            //创建画图管理器等
            if(sheet!=null && patriarch==null){
                patriarch = sheet.createDrawingPatriarch();
            }
            
            //创建默认样式
            styleDefault = getStyle((short)10,HSSFColor.BLACK.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_LEFT,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,true);
            styleTitle = getStyle((short)24,HSSFColor.BLACK.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_CENTER,HSSFFont.BOLDWEIGHT_BOLD,false);
            styleDesc = getStyle((short)10,HSSFColor.BLACK.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_LEFT,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,false);

            styleCaption = this.getStyle((short)12,HSSFColor.SKY_BLUE.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_LEFT,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_BOLD,false);
            styleRowHead = this.getStyle((short)10,HSSFColor.BLACK.index,HSSFColor.GREY_25_PERCENT.index,HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_BOLD,true);
        }
    }
    
    
    /**
     * 插入文本
     * @param type 内容类型 1：标题 ；2：行头；3：数据
     * @param value 内容
     * @param width 内容宽度
     */
    public void insertText(int type,String value,int width){
        if(width==0){
            insertText(type,new String[]{value},null);
        }else{
            insertText(type,new String[]{value},new int[]{width});
        }
    }
    
    /**
     * 插入文本内容
     * @param type 内容类型 1：标题 ；2：行头；3：数据
     * @param value 内容数组(一行)
     * @param width 内容宽度，占用列个数
     */
    public void insertText(int type,String[] value,int[] width){
        HSSFRow row = sheet.createRow(++rowIdx);
        HSSFCellStyle style = styleDefault;
        if(type==1){
            row = sheet.createRow(++rowIdx);//标题自动增加一行
            style = styleCaption==null?styleDefault:styleCaption;
        }else if(type==2){
            style = styleRowHead==null?styleDefault:styleRowHead;
        }
        //row.setRowStyle(style);
        
        int colIndex = 0;
        for(int i=0;i<value.length;i++){
            HSSFCell cell = row.createCell(colIndex);
            if(width!=null && width[i]>1){
                for(int j=1;j<width[i];j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(style);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+width[i]-1)); 
                colIndex = colIndex + width[i];
            } else {
                colIndex = colIndex + 1;
            }
            cell.setCellValue(value[i]);
            cell.setCellStyle(style);
        }
    }
    
    /**
     * 合并多行多列单元格后，插入文本内容
     * @param type 内容类型 1：标题（上下左右居中，大字） ；2：正文（居右置顶，小字）；
     * @param value 内容字符串
     * @param rows 内容占用行个数
     * @param cols 内容占用列个数
     */
    public void insertText(int type,String value,int rows, int cols){
        HSSFRow row = sheet.createRow(++rowIdx);
        HSSFCellStyle style = styleDesc;
        if(type==1){
            style = styleTitle==null?styleDesc:styleTitle;
        }
        row = sheet.createRow(rowIdx);
        HSSFCell cell = row.createCell(0); 
        cell.setCellValue(value) ;

        CellRangeAddress region = new CellRangeAddress(rowIdx,rowIdx+rows-1,0,cols-1);
        sheet.addMergedRegion(region);
        
        //设置样式
        for(int i=rowIdx;i<rowIdx+rows;i++){
            HSSFRow row_temp = sheet.getRow(i);
            if(row_temp == null){
                row_temp = sheet.createRow(i);
            }

            for(int j=0;j<cols;j++){
                HSSFCell cell_temp = row_temp.getCell(j);
                if(cell_temp == null ){
                    cell_temp = row_temp.createCell(j);
                }
                cell_temp.setCellStyle(style);
            }
        }

        rowIdx = rowIdx + rows;
    }
    
    /*
    //设置区域样式
    private void setRegionStyle(HSSFSheet sheet, org.apache.poi.hssf.util.Region region , HSSFCellStyle cs) {
        int toprowNum = region.getRowFrom();
        for (int i = region.getRowFrom(); i <= region.getRowTo(); i ++) {
            HSSFRow row = org.apache.poi.hssf.util.HSSFCellUtil.getRow(i, sheet);
            for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
                HSSFCell cell = org.apache.poi.hssf.util.HSSFCellUtil.getCell(row, (short)j);
                cell.setCellStyle(cs);
            }
        }
    }
    //*/
    
    /**
     * 插入表格内容
     * @param type 内容类型 1：标题 ；2：行头；3：数据
     * @param head 表格行头
     * @param value 表格内容数组（多行,一个集合元素是一行数据）
     * @param width 内容宽度，占用列个数
     */
    public void insertTable(String caption, String[] head, List<String[]> value,int[] width,boolean merge){
        insertText(1,new String[]{caption},getColumnNum(width));  //getColumnNum(width)
        insertText(2,head,width);
        if(value!=null){
            int beginRow = rowIdx;
            for(int i=0;i<value.size();i++){
                insertText(3,value.get(i),width);
            }
            
            if(merge) mergeTable(beginRow+1,beginRow+1,beginRow+value.size(),0,value,width);
        }
    }
    
    /**
     * 从左到右合并表格相同列
     * @param baseRow  表格内容开始行
     * @param beginRow 合并列开始行
     * @param endRow   合并列结束行
     * @param mergeCol 合并表格列
     * @param value    表格数据
     * @param width    表格各列占用单元格的个数
     */
    public void mergeTable(int baseRow,int beginRow,int endRow,int mergeCol,List<String[]> value,int[] width){
        //if (mergeCol==2) System.out.println("mergeTable:" + baseRow + " " + beginRow + " " + endRow + " " + mergeCol);
        if(value.get(0)!=null && value.get(0).length>mergeCol && mergeCol<2){
            int startRow = beginRow;
            String oldString = value.get(beginRow-baseRow)[mergeCol];
            //if (mergeCol==2) System.out.println("oldString:" + oldString + " " + (beginRow-baseRow));
            for(int i=beginRow+1;i<=endRow;i++){
                String newString = value.get(i-baseRow)[mergeCol];
                //if (mergeCol==2) System.out.println("newString:" + newString + " " + (i-baseRow));
                if(!oldString.equals(newString)){
                    if(i-startRow>1){
                        sheet.addMergedRegion(new CellRangeAddress(startRow,i-1,getColIndex(mergeCol,width),getColIndex(mergeCol,width)));
                        mergeTable(baseRow,startRow,i-1,mergeCol+1,value,width);
                    }
                    oldString = newString;
                    startRow = i;
                }
            }
            if(startRow<endRow && endRow-startRow>0){
                sheet.addMergedRegion(new CellRangeAddress(startRow,endRow,getColIndex(mergeCol,width),getColIndex(mergeCol,width)));
                mergeTable(baseRow,startRow,endRow,mergeCol+1,value,width);
            }
        }
    }
    
    /**
     * 获取表格合并列的列数
     * @param mergeCol 合并表格的列数
     * @param width 表格各列所占单元格的个数
     * @return
     */
    private int getColIndex(int mergeCol,int[] width){
        int ret = 0;
        for(int i=1;i<=mergeCol;i++){
            ret = ret + width[i-1];
        }
        //System.out.println("mergeCol:" + mergeCol + " " + ret);
        return ret;
    }
    
    /**
     * 插入表格内容
     * @param type 内容类型 1：标题 ；2：行头；3：数据
     * @param head 表格行头
     * @param value 表格内容数组（多行，一个数组成员是一列数据）
     * @param width 内容宽度，占用列个数
     */
    public void insertTable(String caption, String[] head, List<String>[] value,int[] width){

    }
    
    /**
     * 插入图片
     * @param fileName 图片全路径
     * @return 是否插入成功
     */
    public boolean insertImage(String fileName){
        boolean ret = false;
        try{
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File(fileName));
            int width = bufferImg.getWidth();
            int height = bufferImg.getHeight();
            if(height>720){
                height = 720;
                width = width * 720 / height;
            }
            int rows = height/18;
            int cols = width/72;
            ImageIO.write(bufferImg, getFormatExt(fileName), byteArrayOut);
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255, (short) 0, rowIdx+1, (short) cols, rowIdx + rows + 1);
            anchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
            patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), getFormatDef(fileName)));
            for(int i=1;i<=rows;i++){
                HSSFRow rowTmp = sheet.createRow(rowIdx+i);
                rowTmp.createCell(0);
            }
            ret=true;
            rowIdx = rowIdx + rows + 1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * 获取文件后缀名
     * @param fileName 文件名
     * @return 后缀名
     */
    private String getFormatExt(String fileName){
        String ret = "jpg";
        String ext = fileName.substring(fileName.length()-3);
        if(ext.equalsIgnoreCase("png")){
            ret = "png";
        }
        return ret;
    }
    
    /**
     * 获取文件后缀类型
     * @param fileName 文件名
     * @return 后缀类型
     */
    private short getFormatDef(String fileName){
        short ret =  HSSFWorkbook.PICTURE_TYPE_JPEG;
        String ext = fileName.substring(fileName.length()-3);
        if(ext.equalsIgnoreCase("png")){
            ret = HSSFWorkbook.PICTURE_TYPE_PNG;
        }
        return ret;
    }
    
    /**
     * 保存文件
     * @param fileName 文件名
     * @return 是否保存成功
     */
    public boolean save(String fileName){
        boolean ret = false;
        try{
            FileOutputStream fout=new FileOutputStream(fileName);
            workbook.write(fout);
            fout.close();
            ret=true;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
          workbook=null;
        }
        return ret;
    }
    
    /**
     * 获取文件内容缓存
     * @return 文件内容二进制数组
     */
    public byte[] getBuf(){
        byte[] ret = null;
        try{
          java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            workbook.write(baos);
            ret = baos.toByteArray();
            
            // 关闭输出缓存对象
            baos.close();
            baos=null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
          workbook=null;
        }
        return ret;
    }
    
    /**
     * 创建样式
     * @param fontSize 字体大小
     * @param color 字体颜色
     * @param bgColor 背景色
     * @param align 对齐方式
     * @param bold 粗体
     * @return 样式对象
     */
    protected HSSFCellStyle getStyle(short fontSize,short color,short bgColor,short align,short valign,short bold,boolean border){
        HSSFCellStyle style = null;
        if(workbook!=null){
            style = workbook.createCellStyle();
            style.setWrapText(true);   
            style.setFillForegroundColor(bgColor);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            if(border){
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            }
            style.setAlignment(align);
            style.setVerticalAlignment(valign);
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setColor(color);
            font.setBoldweight(bold);
            style.setFont(font);
        }
        return style;
    }
    
    /**
     * 计算所有列占单元格的总个数
     * @param col 各个列占单元格的个数
     * @return 总个数
     */
    protected int[] getColumnNum(int[] col){
        int[] ret = new int[]{0};
        for(int i=0;i<col.length;i++){
            ret[0] = ret[0] + col[i];
        }
        return ret;
    }
    
    /**
     * 插入表格块
     * @param title 表格块标题
     * @param item 表格块元素数组
     * @param value 表格块元素值
     * @param titleWidth 标题占单元格个数
     * @param itemWidth 元素占单元格个数
     * @param valueWidth 内容占单元格个数
     * @param titleColor 表格块标题背景色
     * @param itemColor 表格块元素背景色
     */
    public void insertTableBlock(String title,String[] item,String[] value,int titleWidth,int itemWidth, int valueWidth,short titleColor,short itemColor){
        int beginRow = rowIdx;
        HSSFCellStyle styleTitle = this.getStyle((short)10,HSSFColor.BLACK.index,titleColor,HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,true);
        HSSFCellStyle styleItem = this.getStyle((short)10,HSSFColor.BLACK.index,itemColor,HSSFCellStyle.ALIGN_LEFT,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,true);
        
        for(int i=0;i<item.length;i++){
            int colIndex = 0;
            HSSFRow row = sheet.createRow(++rowIdx);
            
            //插入表格块的标题
            HSSFCell cell = row.createCell(colIndex);
            if(titleWidth>1){
                for(int j=1;j<titleWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleTitle);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+titleWidth-1)); 
                colIndex = colIndex + titleWidth;
            }else{
                colIndex = colIndex + 1;
            }
            if (i==0) {
                cell.setCellValue(title);
            }
            cell.setCellStyle(styleTitle);
            
            //插入表格块的元素
            cell = row.createCell(colIndex);
            if(itemWidth>1){
                for(int j=1;j<itemWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleItem);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+itemWidth-1)); 
                colIndex = colIndex + itemWidth;
            }else{
                colIndex = colIndex + 1;
            }
            cell.setCellValue(item[i]);
            cell.setCellStyle(styleItem);
            
            //插入表格块的内容
            cell = row.createCell(colIndex);
            if(valueWidth>1){
                for(int j=1;j<valueWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleDefault);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+valueWidth-1)); 
                colIndex = colIndex + valueWidth;
            }else{
                colIndex = colIndex + 1;
            }
            cell.setCellValue(value[i]);
            cell.setCellStyle(styleDefault);
        }
        sheet.addMergedRegion(new CellRangeAddress(beginRow+1,beginRow+item.length,0,0)); 
    }
    
    /**
     * 插入表格块
     * @param title 表格块标题
     * @param item 表格块元素数组
     * @param value 表格块元素值
     * @param titleWidth 标题占单元格个数
     * @param itemWidth 元素占单元格个数
     * @param valueWidth 内容占单元格个数
     * @param titleColor 表格块标题背景色
     * @param itemColor 表格块元素背景色
     */
    public void insertTableBlock(String title,String[] item,String[] value,String[] value1,int titleWidth,int itemWidth, int valueWidth,int valueWidth1,short titleColor,short itemColor){
        int beginRow = rowIdx;
        HSSFCellStyle styleTitle = this.getStyle((short)10,HSSFColor.BLACK.index,titleColor,HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,true);
        HSSFCellStyle styleItem = this.getStyle((short)10,HSSFColor.BLACK.index,itemColor,HSSFCellStyle.ALIGN_LEFT,HSSFCellStyle.VERTICAL_TOP,HSSFFont.BOLDWEIGHT_NORMAL,true);
        
        for(int i=0;i<item.length;i++){
            int colIndex = 0;
            HSSFRow row = sheet.createRow(++rowIdx);
            
            //插入表格块的标题
            HSSFCell cell = row.createCell(colIndex);
            if(titleWidth>1){
                for(int j=1;j<titleWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleTitle);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+titleWidth-1)); 
                colIndex = colIndex + titleWidth;
            }else{
                colIndex = colIndex + 1;
            }
            if (i==0) {
                cell.setCellValue(title);
            }
            cell.setCellStyle(styleTitle);
        
            //插入表格块的元素
            cell = row.createCell(colIndex);
            if(itemWidth>1){
                for(int j=1;j<itemWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleItem);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+itemWidth-1)); 
                colIndex = colIndex + itemWidth;
            }else{
                colIndex = colIndex + 1;
            }
            cell.setCellValue(item[i]);
            cell.setCellStyle(styleItem);
            
            //插入表格块的内容
            cell = row.createCell(colIndex);
            if(valueWidth>1){
                for(int j=1;j<valueWidth;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleDefault);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+valueWidth-1)); 
                colIndex = colIndex + valueWidth;
            }else{
                colIndex = colIndex + 1;
            }
            cell.setCellValue(value[i]);
            cell.setCellStyle(styleDefault);
            
            cell = row.createCell(colIndex);
            if(valueWidth1>1){
                for(int j=1;j<valueWidth1;j++){
                    HSSFCell cellTmp = row.createCell(colIndex+j);
                    cellTmp.setCellStyle(styleDefault);
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIdx,rowIdx,colIndex,colIndex+valueWidth1-1)); 
                colIndex = colIndex + valueWidth1;
            }else{
                colIndex = colIndex + 1;
            }
            cell.setCellValue(value1[i]);
            cell.setCellStyle(styleDefault);
        }
        sheet.addMergedRegion(new CellRangeAddress(beginRow+1,beginRow+item.length,0,0)); 
    }
    
    public static void main(String[] args) {
        ExcelReport rep = new ExcelReport();
        rep.createSheet("徐家汇市场调研报告");
        
        //表格行标题
        String[] head = new String[]{"编号","评审类型","评审工作产品","阶段","工作产品类型","评审日期","作者","合计(个)","已确认(个)"};
        
        //表格每列占单元格个数
        int[] width = new int[]{1,1,3,1,2,2,1,1,1};
        
        //表格模拟数据
        List<String[]> data = new java.util.ArrayList<String[]>();
        data.add(new String[]{"1","正式","项目Portal画面","编码","源代码","2011/07/14","李明江","4","0"});
        data.add(new String[]{"1","正式","租赁方案编辑画面","编码","源代码","2011/07/14","韦小粉","4","0"});
        data.add(new String[]{"1","计划","项目管理列表画面","编码","源代码","2011/07/14","李明江","3","0"});
        data.add(new String[]{"1","计划","购置方案编辑画面","编码","源代码","2011/07/14","韦小粉","2","0"});
        data.add(new String[]{"2","计划","物业勘察任务相关画面","编码","源代码","2011/07/14","韦小粉","4","0"});
        data.add(new String[]{"3","计划","附件上传画面","编码","源代码","2011/07/14","韦小粉","1","0"});
        
        //插入表格信息（含大标题）
        rep.insertTable("一、评审检索结果一览", head, data, width, false);
        
        //插入大标题和图片一
        rep.insertText(1, "二、大图片", 13);
        rep.insertImage("e:/1.jpg");
        
        //插入另一个表格（含大标题）
        rep.insertTable("三、评审检索结果一览", head, data, width, true);
        
        //插入大标题和图片二
        rep.insertText(1, "四、小图片", 13);
        rep.insertImage("e:/1.1.jpg");
        
        //插入特定表格（大标题、行头、区段）
        rep.insertText(1, "五、特定表格", 13);
        rep.insertText(2, new String[]{"类别","项目","内容"}, new int[]{3,4,6});
        
        rep.insertTableBlock("基本信息", new String[]{"项目名称","地址","负责人"}, new String[]{"南京润和","龙茗路1300弄58号301室","蔡叶林"}, 3, 4, 6,HSSFColor.YELLOW.index, HSSFColor.BLUE_GREY.index);
        rep.insertTableBlock("商务条件", new String[]{"租金（元/㎡*天）","物业费（元/㎡*天）","递增比例","租期（年）"}, new String[]{"2.3","0.1","0.05","8"}, 3, 4, 6, HSSFColor.RED.index, HSSFColor.BLUE_GREY.index);
        rep.insertTableBlock("产权信息", new String[]{"租金（元/㎡*天）","物业费（元/㎡*天）","递增比例","租期（年）","总租金"}, new String[]{"2.3","0.1","0.05","8","10000"}, 3, 4, 6, HSSFColor.BROWN.index, HSSFColor.BLUE.index);
        
        //保存到文件
        rep.save("e:/3.xls");
        
        System.out.println("ok : " + new java.util.Date());
    }
}
