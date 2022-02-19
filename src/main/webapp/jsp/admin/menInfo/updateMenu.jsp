<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>更新公司</title>

<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css" />
<link rel="stylesheet" href="${ctx}/static/css/admin.css" />
<link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${ctx}/static/utilLib/bootstrap.min.css" type="text/css" media="screen" />
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body> 
<div class="div_from_aoto" style="width: 500px;">
<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">更新菜单</strong><small></small>
			</div>
		</div>
<hr>
    <FORM  action="${ctx}/MenInfoController/updateMenuInfo" method="post">
        <DIV class="control-group">
        <input type="text" name="menuId" id="menuId" value ="${menu.menuID}" style="display:none"/>

            <label class="laber_from">菜单名称</label>
            <DIV  class="controls" ><INPUT class="input_from"  value="${menu.menuName}" type=text id="menuName" name="menuName"placeholder=" 请输入菜单名称"><P class=help-block></P></DIV>
        </DIV>
        
          <DIV class="control-group">
        <DIV class="control-group">
            <LABEL class="laber_from">菜单说明</LABEL>
            <DIV  class="controls" ><INPUT class="input_from"   value="${menu.menuExp}" id="menuExp" type=text name="menuExp" placeholder=" 请输入菜单说明"><P class=help-block></P></DIV>
        </DIV>
              <c:if test="${menu.menuILevel==1}">
              <DIV class="control-group" style="display: none">
                  <LABEL class="laber_from" >菜单地址</LABEL>
                  <DIV  class="controls" ><INPUT class="input_from" style="width:300px;"value="${menu.menuFunctionUrl}"type=text  id="menuUrl" name="menuUrl" placeholder=" 请输入菜单地址"><P class=help-block></P></DIV>
              </DIV>
              </c:if>
              <c:if test="${menu.menuILevel==2 || menu.menuILevel==3}">
              <DIV class="control-group">
                  <LABEL class="laber_from" >菜单地址</LABEL>
                  <DIV  class="controls" ><INPUT class="input_from" style="width:300px;"value="${menu.menuFunctionUrl}"type=text  id="menuUrl" name="menuUrl" placeholder=" 请输入菜单地址"><P class=help-block></P></DIV>
              </DIV>
              </c:if>
        <br>
              <c:if test="${menu.menuILevel==3}">
              <DIV class="control-group" style="display: none">
              </c:if>
                  <c:if test="${menu.menuILevel!=3}">
                  <DIV class="control-group" >
                      </c:if>
            <LABEL class="laber_from">菜单等级</LABEL>
            <DIV  class="controls" >
                <SELECT class="input_select" name="menuLv"  id="menuLv" onchange="listLv1Menu()">
                    <c:if test="${menu.menuILevel==1}"> <OPTION value="1" selected="selected" >1级菜单</OPTION>
                    								<OPTION value="2">2级菜单</OPTION></c:if>
          	 	  	<c:if test="${menu.menuILevel==2}"> <OPTION value="1" >1级菜单</OPTION>
                    								<OPTION value="2" selected="selected">2级菜单</OPTION></c:if>
                    <c:if test="${menu.menuILevel==3}"> <OPTION value="3" >菜单功能</OPTION></c:if>
                </SELECT>
            </DIV>
        </DIV>
        <br>
        
        <c:if test="${menu.menuILevel==1 || menu.menuILevel==3}">
            <DIV class="control-group" id="upMenuSelect" style="display:none">
           </c:if>
            <c:if test="${menu.menuILevel==2}">
            <DIV class="control-group" id="upMenuSelect"  >
           </c:if>
            <label class="laber_from">上级菜单 </label>
 				<DIV  class="controls" >
                <SELECT class="input_select" name="upMenuId" id="lv1MenuSelect">
                    <c:if test="${menu.menuILevel==3}">
                        <option  id="emptySelect" value="${menu.superMenuID}" style="display: none">请选择</option>
                    </c:if>
                    <c:if test="${menu.menuILevel!=3}">
                        <option  id="emptySelect" value="" style="display: none">请选择</option>
                    </c:if>
                    <c:forEach items="${lv1Menu}" var="u">
                        <c:if test="${u.menuID ne menu.menuID}"><option   value="${u.menuID}">${u.menuName}</option></c:if>
<%--                        <c:if test="${u.menuID eq menu.menuID}">  <option   value="${u.menuID}"  selected="selected">${u.menuName}</option> </c:if>--%>
                    </c:forEach>
                </SELECT>
            </DIV>        </DIV>
        
        <DIV class="control-group">
            <LABEL class="laber_from" ></LABEL>
            <br />
            <DIV class="controls" ><button class="am-btn am-btn-default am-btn-xs am-text-secondary" onmouseover="mouse()" style="width:120px;" >确认</button></DIV>
        </DIV>
    </FORM>
</div>
</body>

<script type="text/javascript">
 		function listLv1Menu(){
 			var menuId= $("#menuId").val();
 			var menuLv=$("#menuLv").val();
 			if(menuLv==1){
 				$("#upMenuSelect").val( );
 				$("#upMenuSelect").hide();
 			}
 			else {	alert ("请选择上级菜单");  		 
 				$("#upMenuSelect").show();
 			 $.ajax({
 				type : "POST",
 				url : "${ctx}/MenInfoController/jumpMenInfoList",
 				data : {},
 				dataType : "json",
 				async : true,
 				success : function(data) {	
 					$("#lv1MenuSelect").empty();
 					for (var i = 0; i < data.length; i++) {
 						if(menuId!=data[i].menuID){
						var $option = $("<option value='"+data[i].menuID+"'>"
								+ data[i].menuName + "</option>");
							$("#lv1MenuSelect").append($option);
							 }
					}
 				},
 				error : function(xhr, textStatus, errorThrown) {
 				}
 			});
 			}
 		}

	function mouse() {
		var menuName = $("#menuName").val().trim();
		var menuExp = $("#menuExp").val().trim();
		var menuLv=$("#menuLv").val();
		var upMenuId=$("#lv1MenuSelect").val();
		if (menuName == "") {
			alert("菜单名称不能为空");
			$("#button").disabled = true;
		}
		else if (menuExp == "") {
			alert("菜单说明不能为空");
			$("#button").disabled = true;
		}
		else if (menuLv==2&&upMenuId==""){
			alert("请选择上级菜单");
		}
		else if (menuLv==2&&upMenuId==null){
			alert("请选择上级菜单");
		}
	 
	}
</script>
</html>