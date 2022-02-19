<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改密码</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/select.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/wu.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/icon.css"/>
    <script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/select.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/linq.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/area.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen"/>
    <%--    <link rel="stylesheet" href="/static/css/main.css" type="text/css" media="screen" />--%>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" type="text/css" media="screen"/>
    <style type="text/css">
        .fruitRadio {
            color: red;
        }

    </style>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>修改密码</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/LoginController/updatePassWord" method="post" onsubmit="return check()"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">用户姓名</label>
                <DIV class="controls"><INPUT class="input_from" id="memberName" value="${userInfo.memberName}" type=text
                                             name="memberName" placeholder=" 请输入员工姓名" disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <label class="laber_from">登录账号</label>
                <DIV class="controls"><INPUT class="input_from" id="loginName" value="${userInfo.loginName}" type=text
                                             name="memberName" placeholder=" 请输入员工姓名" disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">输入密码</label>
                <DIV class="controls"><INPUT class="input_from" id="loginPassWord1" type=text
                                             name="" placeholder=" 请输入密码">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">确认密码</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="loginPassWord2"
                                             name="loginPassWord" placeholder=" 请确认输入密码">
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="userID" value="${userInfo.userID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>

            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" style="width:120px;">
                        确认
                    </button>
<%--                    ${ctx}/LoginController/toIndex?userName=${userInfo.loginName}--%>
                    <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="#" onclick="javascript :history.back(-1);" style="width:120px;">
                        返回
                    </a>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">
    function returns() {
        location.href = "${ctx}/LoginController/toIndex?userName="+${userInfo.memberName}
    }

    function check() {
        var loginPassWord1 = $("#loginPassWord1").val().trim();
        var loginPassWord2 = $("#loginPassWord2").val().trim();
        if (loginPassWord1 != loginPassWord2){
            alert("密码不一致，请重新输入！")
            return false;
        }
    }
</script>
</html>