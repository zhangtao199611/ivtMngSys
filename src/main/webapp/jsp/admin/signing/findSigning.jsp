<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>签单详情</title>
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
        body {
            font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
            font-size: 14px;
            line-height: 2.429;
            color: #333;
        }
    </style>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>签单详情</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}//" method="post" onsubmit="return check()"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">签单照片</label>
                <DIV class="controls">
                    <img style="width: 100%;height: 100%" src="${ctx}/static/${signingInfo.sigImg}">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">拍照人员</label>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${signingInfo.adder}</span>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">添加日期</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${signingInfo.addTimeStr}</span>
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="memberID" value="${signingInfo.signingID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>

            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="tup2">归属单位</LABEL>
                    <DIV class="controls">
                        <span class="input_from" style="color: #0e90d2">${signingInfo.unitID}</span>
                        <P class=help-block></P>
                    </DIV>
                </DIV>
            </DIV>

            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="">备注</LABEL>
                    <DIV class="controls">
                        <span class="input_from" style="color: #0e90d2">${signingInfo.remarks}</span>
                        <P class=help-block></P>
                    </DIV>
                </DIV>
            </DIV>

<%--            <DIV class="control-group" id="td">--%>
<%--                <DIV>--%>
<%--                    <LABEL class="laber_from" id="tup">用户权限(可多选)</LABEL>--%>
<%--                    <input type="text" name="stationIDs" id="features" value="${memberInfo.stationIDs}"--%>
<%--                           style="display:none"/>--%>
<%--                    <div class="mySelect" style="width: 250px;float: left;  opacity: 1;"></div>--%>
<%--                    <div id="mySelect" class="mySelect" style="width: 250px;float: left; opacity: 1;"></div>--%>
<%--                </DIV>--%>
<%--            </DIV>--%>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <a class="am-btn am-btn-default am-btn-xs am-text-secondary"  href="#" onclick="javascript :history.back(-1);" style="width:120px;">
                        返回
                    </a>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">

    $(function () {
        var option = null;
        // 从后台获取特征来选择
        $.ajax({
            url: "${ctx}/UserInfoController/listStationInfoByStationName",
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                option = data;
                //多选插件=>
                var mySelect = $("#mySelect").mySelect({
                    mult: true,//true为多选,false为单选
                    option: option,
                    onChange: function (res) {//选择框值变化返回结果
                        console.log(res);
                        $("#features").val(res);
                    }
                });
                var features = "${memberInfo.stationIDs}";
                var featuresArray = features.split(",");
                //设定原本处于选中状态的特征
                mySelect.setResult(featuresArray);
            }
        });
    });

    function check() {
        var userName = $("#TureName").val().trim();
        var loginName = $("#loginName").val().trim();
        var userTel = $("#phoneNumber").val().trim();
        var features = $("#features").val().trim();
        var deptID = $("#deptID").val().trim();
        var phoneReg = /(^1[3|4|5|7|8|9]\d{9}$)|(^09\d{8}$)/;
        var nameReg = /^[\u4E00-\u9FA5]{2,4}$/;
        if (userName == "" || userName == null) {
            alert("请输入员工姓名");
            return false;
        }
        if (!nameReg.test(userName)) {
            alert("请输入正确的姓名");
            return false;
        }
        if (loginName == "" || loginName == null || loginName == "0") {
            alert("请选择登录账号");
            return false;
        }
        if (deptID == "" || deptID == null || deptID == "0") {
            alert("请选择员工所在部门")
            return false;
        }
        if (userTel == "" || userTel == null) {
            alert("请输入手机号");
            return false;
        }
        if (!phoneReg.test(userTel)) {
            alert("请输入正确的手机号");
            return false;
        }
        if (features == "" || features == null) {
            alert("请选择员工权限");
            return false;
        }
    }
</script>
</html>