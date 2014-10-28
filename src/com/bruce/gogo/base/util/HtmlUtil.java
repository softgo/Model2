package com.bruce.gogo.base.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>admin<br>
 * <b>日期：</b> Dec 14, 2013 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {

    /**
     * 
     * <br>
     * <b>功能：</b>输出json格式<br>
     * <b>作者：</b>admin<br>
     * <b>日期：</b> Dec 14, 2013 <br>
     * 
     * @param response
     * @param jsonStr
     * @throws Exception
     */
    public static void writerJson(HttpServletResponse response, String jsonStr) {
        writer(response, jsonStr);
    }

    public static void writerJson(HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            writer(response, JSONUtil.toJSONString(object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * <br>
     * <b>功能：</b>输出HTML代码<br>
     * <b>作者：</b>admin<br>
     * <b>日期：</b> Dec 14, 2013 <br>
     * 
     * @param response
     * @param htmlStr
     * @throws Exception
     */
    public static void writerHtml(HttpServletResponse response, String htmlStr) {
        writer(response, htmlStr);
    }

    private static void writer(HttpServletResponse response, String str) {
        try {
            // 设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** add by wanghc* */
    /**
     * 
     * @param status
     * @param result
     * @param jsonArray
     * @return
     */
    public static String getResultJson(boolean status, String result, final Object... jsonArray) {
        return getJsonString("success", status, "msg", result, jsonArray);
    }

    /**
     * @param jsonArray
     * @return
     */
    public static String getJsonString(final Object... jsonArray) {

        StringBuffer sb = new StringBuffer("{");
        int len = jsonArray.length;
        if (jsonArray != null && len > 0) {
            for (int i = 0; i < len; i++) {
                if (jsonArray[i] instanceof Object[]) {
                    Object[] _arry = (Object[]) jsonArray[i];
                    if (i != 0 && _arry.length > 0)
                        sb.append(",");
                    for (int j = 0; j < _arry.length; j++) {
                        if (j != 0)
                            sb.append(",");
                        sb.append("\"").append(_arry[j]).append("\":");
                        ++j;
                        if (_arry[j] instanceof String) {
                            sb.append("\"").append(_arry[j]).append("\"");
                        } else if (_arry[j] instanceof Integer) {
                            sb.append(_arry[j]);
                        } else if (_arry[j] instanceof Boolean) {
                            sb.append(_arry[j]);
                        } else if (_arry[j] instanceof Double) {
                            sb.append(_arry[j]);
                        }
                    }

                } else {
                    if (i != 0)
                        sb.append(",");
                    sb.append("\"").append(jsonArray[i]).append("\":");
                    ++i;
                    if (jsonArray[i] instanceof String) {
                        sb.append("\"").append(jsonArray[i]).append("\"");
                    } else if (jsonArray[i] instanceof Integer) {
                        sb.append(jsonArray[i]);
                    } else if (jsonArray[i] instanceof Boolean) {
                        sb.append(jsonArray[i]);
                    } else if (jsonArray[i] instanceof Double) {
                        sb.append(jsonArray[i]);
                    }

                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
    	String jsonString = getJsonString("aa","bb","cc","dd","ee",false);
    	System.out.println(jsonString);
	}
}
