<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改明细</title>
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
                <strong class="am-text-primary am-text-lg"><h2>修改明细</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/StockDetailInfoController/updateStockDetail" method="post" onsubmit="return check()"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">食材名称</label>
                <DIV class="controls"><INPUT class="input_from" id="stockID" value="${stockDetail.stockID}" type=text
                                             name="stockID" placeholder=" 请输入食材名称" disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>

<%--            <DIV class="control-group">--%>
<%--                <label class="laber_from">秤重重量</label>--%>
<%--                <DIV class="controls"><INPUT class="input_from" id="weighingWeight" value="${stockDetail.weighingWeight}" type=text--%>
<%--                                             name="weighingWeight" placeholder=" 请输入员工工号">--%>
<%--                    <P class=help-block></P></DIV>--%>
<%--            </DIV>--%>

            <DIV class="control-group">
                <LABEL class="laber_from">调整重量</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="adjustWeight" value="${stockDetail.adjustWeight}"
                                             name="adjustWeight" placeholder=" 请输入联系电话">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">实际重量</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="weight" value="${stockDetail.weight}"
                                             name="weight" placeholder=" 请输入联系电话">
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="stockDetailID" value="${stockDetail.stockDetailID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <input type="text" name="userName" value="${userName}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" style="width:120px;">
                        确认
                    </button>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">

    $(function(){
        var resStr = '<%=session.getAttribute("notice")%>'
        if (resStr!="null"){
            alert(resStr)
        }else {

        }
    });

    function check() {

        var userName = $("#stockID").val().trim();
        var loginName = $("#weighingWeight").val().trim();
        var userTel = $("#adjustWeight").val().trim();
        var features = $("#weight").val().trim();
        if (userName == "" || userName == null) {
            alert("请输入员工姓名");
            return false;
        }
        if (loginName == "" || loginName == null || loginName == "0") {
            alert("请选择登录账号");
            return false;
        }
        if (userTel == "" || userTel == null) {
            alert("请输入手机号");
            return false;
        }
        if (features == "" || features == null) {
            alert("请选择员工权限");
            return false;
        }
        if(userTel == "" || userTel ==null){

        }
    }
</script>
</html>