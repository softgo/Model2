<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="<%=request.getContextPath()%>/images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;
	              总共&nbsp;${pageView.rowCount}&nbsp;条&nbsp;&nbsp;
	                         分&nbsp;${pageView.pageCount}&nbsp;页显示&nbsp;&nbsp;
	             <!-- 每页显示数据 -->            
	              每页显示&nbsp;
	              <span>
					<select name="pageSizeChoose" id="pageSizeChoose" onchange="ChoosePageSize('${pageView.rowCount}')" >
					    <!-- 默认. 
					    	<option value="${pageView.pageSize}" selected="selected">${pageView.pageSize}</option>
					    -->
					    <!-- 选择值. -->
					    <option value="5"  <c:if test="${pageView.pageSize==5}">selected</c:if>>5</option>
					    <option value="10" <c:if test="${pageView.pageSize==10}">selected</c:if>>10</option>
					    <option value="15" <c:if test="${pageView.pageSize==15}">selected</c:if>>15</option>
					</select>
	              </span>
	              &nbsp;
	                 条&nbsp;&nbsp;
	            
	              当前为&nbsp;${pageView.pageNow}&nbsp;页
	             
              </td>
              
            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40" class="STYLE4">
                  <a href="#" onclick="pageNow('1');">
                  	首页
                  </a>
                  </td>
                  <td width="45" class="STYLE4">
                  <a href="#" onclick="return pageNow('${pageView.pageNow - 1}')">
                  	上一页
                  </a>
                  </td>
                  <td align="center">
                  
                  <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="key">
						<c:if test="${pageView.pageNow==key}">
							&nbsp;<span class="current" style="color: red;font-size: 20px;"> ${key}</span>
						</c:if>
						<c:if test="${pageView.pageNow!=key}">
							&nbsp;
							<span style="font-size: 16px;">
								<a href="#" onclick="pageNow('${key}')">${key}</a>
							  </span>
						</c:if>
					</c:forEach>&nbsp;
                  </td>
                  <td width="45" class="STYLE4">
                  <a href="#" onclick="pageNow('${pageView.pageNow + 1}')">
                  	下一页
                  </a>
                  </td>
                  <td width="40" class="STYLE4">
                  <a href="#" onclick="pageNow('${pageView.pageCount}')">
                 	尾页
                  </a>
                  </td>
                  <td>
                  	<c:if test="${pageView.pageCount > 0}">
						  <span class="text">&nbsp;&nbsp;
						  	<input type="text" size="1" id="pageNowCount" name="pageNowCount" value="${pageView.pageNow}" onkeypress="if(event.keyCode==13)return false;" >
						    <span style="cursor:hand;COLOR: #31659c"><a style="cursor: pointer;text-decoration: none;margin: 0px;padding: 0px;" onclick="GOPage()" >[GO]</a></span>	
						  </span>
					</c:if>
                  </td>
                </tr>
            </table>
            </td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=request.getContextPath()%>/images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table>
    