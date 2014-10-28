package com.bruce.gogo.base;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.bruce.gogo.base.util.Common;
import com.bruce.gogo.base.util.HtmlUtil;
import com.bruce.gogo.pulgin.mybatis.plugin.PageView;

/**
 * 公共继承的控制类(controller)
 * 
 * @author admin
 */
public abstract class BaseController {
	
    protected Logger logger = Logger.getLogger(BaseController.class);

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    public final static String SUCCESS = "success";

    public final static String MSG = "msg";

    public final static String DATA = "data";

    public final static String LOGOUT_FLAG = "logoutFlag";

    @ModelAttribute
    protected void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 获取IP地址
     * 
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 所有ActionMap 统一从这里获取
     * 
     * @return
     */
    public Map<String, Object> getRootMap() {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        // 添加url到 Map中
        // rootMap.putAll(URLUtils.getUrlMap());
        return rootMap;
    }

    public ModelAndView forword(String viewName, Map context) {
        return new ModelAndView(viewName, context);
    }

    public ModelAndView error(String errMsg) {
        return new ModelAndView("error");
    }

    /**
     * 
     * 提示成功信息
     * 
     * @param message
     * 
     */
    public void sendSuccessMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, true);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    /**
     * 
     * 提示失败信息
     * 
     * @param message
     * 
     */
    public void sendFailureMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, false);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    protected void getQueryString() {
		Enumeration<?> paramNames = request.getParameterNames();
		if (paramNames != null) {
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String paramValues = request.getParameter(paramName);
				if (StringUtils.isNotBlank(paramValues)) {
					System.out.println("parameter：--->> " + paramName + "=" + paramValues);
					// map.addAttribute(paramName, paramValue);
				} else {
					String[] paramValues1 = request.getParameterValues(paramName);
					for (int i = 0; i < paramValues1.length; i++) {
						System.out.println("parameters:---->>>" + paramValues1[i]);
					}
				}
			}
		}
	}
    
    /**
     * 底层分页设置方法
     * @param model
     * @param pageNow
     * @param pageSize
     */
	public PageView findPage(String pageNow,String pageSize ){
		PageView pageView = null;
		//默认显示.
		if(Common.isEmpty(pageNow) && Common.isEmpty(pageSize)){
			pageView = new PageView(1);
		//输入有页数,默认展示条数.
		}else if(!Common.isEmpty(pageNow) && Common.isEmpty(pageSize) ){
			pageView = new PageView(Integer.parseInt(pageNow));
		//设置页数和每页显示的条数.
		}else if(Common.isEmpty(pageNow) && !Common.isEmpty(pageSize)) {
			pageView = new PageView(1,Integer.parseInt(pageSize));
		//设置页数和每页显示的条数.
		}else {
			pageView = new PageView(Integer.parseInt(pageNow),Integer.parseInt(pageSize));
		}
		return pageView;
	}
	
	
	 /**
     * 将json串从action输出到页面
     * 
     * @param String
     *            str
     * @return String
     */
	public static void doAjaxReturn(String obj,HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(obj == null ? "null" : obj);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}

	// 设置页面不缓存
	public void setResponseNoCache() {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 
	 * @param jspTemplateURL 重定向JSP模板
	 * @param staticHtmlPath 目标静态化路径
	 */
	public void jsp2htmlBuilder(String jspTemplateURL, String staticHtmlPath) {
		FileOutputStream fos = null;
		try {
			RequestDispatcher rd = session.getServletContext().getRequestDispatcher(jspTemplateURL);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ServletOutputStream stream = new ServletOutputStream() {
				public void write(byte[] data, int offset, int length) {
					os.write(data, offset, length);
				}
				public void write(int b) throws IOException {
					os.write(b);
				}
			};
			final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			HttpServletResponse rep = new HttpServletResponseWrapper(response) {
				public ServletOutputStream getOutputStream() {
					return stream;
				}
				public PrintWriter getWriter() {
					return pw;
				}
			};
			rd.include(request, rep);
			pw.flush();
			fos = new FileOutputStream(staticHtmlPath);
			os.writeTo(fos);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
