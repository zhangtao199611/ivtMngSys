<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改配置</title>
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
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

    <style type="text/css">
        .fruitRadio {
            color: red;
        }
        input {
            transition: all 0.30s ease-in-out;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: none;
            padding-left: 10px;
            height: 34px;
            width: 396px;
        }

        .rotate{
            -ms-transform:rotate(90deg); /* IE 9 */
            -moz-transform:rotate(90deg); /* Firefox */
            -webkit-transform:rotate(90deg); /* Safari and Chrome */
            -o-transform:rotate(90deg); /* Opera */
        }　　

    </style>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>修改配置</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/SysConfigController/updateConfigService" method="post" onsubmit="return check()"
              enctype="multipart/form-data">

            <DIV class="control-group">
                <LABEL class="laber_from">版本信息号</LABEL>
                <DIV class="controls"><INPUT class="input_from" value="${systemConfig.sysVersion}"
                                             name="sysVersion" placeholder="版本信息号" disabled="disabled">
                    <P class=help-block></P></DIV>
            </DIV>


            <DIV class="control-group">
                <label class="laber_from">系统名称</label>
                <DIV class="controls "><INPUT class="input_from" id="sysName" value="${systemConfig.sysName}" type=text
                                             name="sysName" placeholder=" 请输入系统名称">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">系统logo</label>
                <DIV class="controls">
                    <img id="imgSpin" style="width: 280px;height: 280px" src="${ctx}${systemConfig.sysLogo}"/>
                    <P class=help-block></P>
                </DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">单位名称</label>
                <DIV class="controls "><INPUT class="input_from" id="untitName" value="${systemConfig.untitName}" type=text
                                              name="untitName" placeholder=" 请输入单位名称">
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="unitID" value="${systemConfig.unitID}" style="display:none"/>


        <%--            <div class="am-btn-group am-btn-group-xs">--%>
<%--                &lt;%&ndash;							<a class="am-btn am-btn-default" href="${ctx}/UserInfoController/">  <span class="am-icon-plus"></span>新增 </a>&ndash;%&gt;--%>

<%--                <!-- 					批量删除 -->--%>
<%--                <button type="button"  onclick="batchPrint()" class="am-btn am-btn-default">--%>
<%--                    <span class="icon-reorder"></span>  批量打印--%>
<%--            </div>--%>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">更换系统logo</label>
                <DIV class="controls">
                    <input type="file"  name="sysLogoFile">
                    <P class=help-block></P>
                </DIV>
            </DIV>


            <DIV class="control-group">
                <LABEL class="laber_from">非标品标题</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="unStandardAppTitle" value="${systemConfig.unStandardAppTitle}"
                                             name="unStandardAppTitle" placeholder=" 请输入APP标题（非标品）">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">标品标题</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="standardAppTitle" value="${systemConfig.standardAppTitle}"
                                             name="standardAppTitle" placeholder=" 请输入APP标题（标品）">
                    <P class=help-block></P></DIV>
            </DIV>

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
    <%--$(function(){--%>
    <%--    var resStr = '<%=session.getAttribute("Info")%>'--%>
    <%--    if (resStr!="null"){--%>
    <%--        alert(resStr)--%>
    <%--    }else {--%>

    <%--    }--%>
    <%--});--%>

    function check() {
        var sysName = $("#sysName").val().trim();
        var unStandardAppTitle = $("#unStandardAppTitle").val().trim();
        var standardAppTitle = $("#standardAppTitle").val().trim();
        var untitName = $("#untitName").val().trim();
        if (sysName == "" || sysName == null) {
            alert("请输入系统名称");
            return false;
        }
        if (unStandardAppTitle == "" || unStandardAppTitle == null) {
            alert("请输入APP标题（非标品）名称");
            return false;
        }
        if (standardAppTitle == "" || standardAppTitle == null) {
            alert("请输入APP标题（标品）名称");
            return false;
        }
        if (untitName == "" || untitName == null){
            alert("请输入单位名称");
            return false;
        }

    }
</script>
</html>