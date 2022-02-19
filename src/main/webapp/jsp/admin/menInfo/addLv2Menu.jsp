<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增规格</title>

<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css" />
<link rel="stylesheet" href="${ctx}/static/css/admin.css" />

<link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${ctx}/static/utilLib/bootstrap.min.css" type="text/css" media="screen" />

</head>
<jsp:include page="../isLogin.jsp"></jsp:include>
<body>
<div class="div_from_aoto" style="width: 500px;">
<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">${menu.menuName}--新增二级菜单</strong><small></small>
			</div>
		</div>
		<hr>

    <FORM  action="${ctx}/MenInfoController/addMenInfo2" method="post">
    
<%--     	<DIV class="control-group">		--%>
<%--            <label class="laber_from">菜单编号</label>--%>
<%--            <DIV  class="controls" ><INPUT class="input_from" type=text id="menuId"name="menuId"placeholder=" 请输入菜单编号"><P class=help-block></P></DIV>--%>
<%--        </DIV>--%>

    		<input type ="text" value="${menu.menuID}" name="upMenuId" style="display:none"/>
        <DIV class="control-group">		
            <label class="laber_from">菜单名称</label>
            <DIV  class="controls" ><INPUT class="input_from" type=text id="menuName"name="menuName"placeholder=" 请输入菜单名称"><P class=help-block></P></DIV>
        </DIV>
        
        
          <DIV class="control-group">
            <label class="laber_from">菜单说明</label>
            <DIV  class="controls" ><INPUT class="input_from" type=text id="menuExp" name="menuExp"placeholder=" 请输入菜单说明"><P class=help-block></P></DIV>
        </DIV>
        
       	 <DIV class="control-group">
            <label class="laber_from">菜单地址</label>
            <DIV  class="controls" ><INPUT class="input_from"  style="width:300px;"  type=text id="menuUrl" name="menuUrl"placeholder=" 请输入菜单地址"><P class=help-block></P></DIV>
        </DIV>

        
        <input type="text" value="2" name="menuLv" style="display:none"/>

			 
        <DIV class="control-group">
            <LABEL class="laber_from" ></LABEL>
            <br />
            <DIV class="controls" ><button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="submit" onmouseover="mouse()"style="width:120px;" >确认</button></DIV>
        </DIV>
    </FORM>
</div>
</body>
	<script type="text/javascript">
	
	
function mouse() {
	var menuName = $("#menuName").val().trim();
	var menuExp = $("#menuExp").val().trim();
	var menuUrl = $("#menuUrl").val().trim();
    if (menuName == "") {
        alert("菜单名不能为空");
        $("#button").disabled = true;
    }
    else	if (menuExp == "") {
        alert("请输入菜单说明");
        $("#button").disabled = true;
    }
    else	if (menuUrl == "") {
        alert("请输入菜单链接");
        $("#button").disabled = true;
    }
}
 </script>
	 
</html>