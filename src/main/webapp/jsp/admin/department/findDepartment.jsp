<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>部门详情</title>
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
                <strong class="am-text-primary am-text-lg"><h2>部门详情</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/UserInfoController/updateDepartment" method="post" onsubmit="return check()"
              enctype="multipart/form-data">

            <DIV class="control-group">
                <label class="laber_from">部门名称</label>
                <DIV class="controls"><INPUT class="input_from" id="deptName" name="deptName" value="${deptInfoByDeptID.deptName}" type=text name="loginName" placeholder=" 请修改部门名称"  disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">部门说明</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="deptExplain" value="${deptInfoByDeptID.deptExplain}"
                                             name="deptExplain" placeholder=" 请修改部门名称"  disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="deptID" value="${deptInfoByDeptID.deptID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
<%--                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" style="width:120px;">--%>
<%--                        确认--%>
<%--                    </button>--%>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">


    function checks() {
        var deptName = $("#deptName").val().trim();
        var deptExplain = $("#deptExplain").val().trim();
        if (deptName==null || deptName==""){
            alert("请输入部门名称")
            return false;
        }
        if (deptExplain==null || deptExplain==""){
            alert("请输入部门说明")
            return false;
        }
        return true;
    }


    $("#deptName").blur(function () {
        $.ajax({
            url: "${ctx}/UserInfoController/ajaxEpartmentName",
            data:{
                "deptName":$("#deptName").val().trim()
            },
            type: "get",
            dataType: "json",
            success: function(data) {
                console.log(data);
                var code = data.code;
                if (code!=0){
                    alert(data.data)
                }
            }
        });
    })
</script>
</html>