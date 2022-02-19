<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>库存详情</title>
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

<body style="line-height: 2.429;">
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>库存详情</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/UserInfoController/updateDepartment" method="post" onsubmit="return check()"
              enctype="multipart/form-data">

            <DIV class="control-group">
                <label class="laber_from">库存编号：</label>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.stockID}</span>
<%--                    <INPUT class="input_from" id="stockID" name="stockID" value="${stockInfo.stockID}" type=text name="loginName" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">食材名称：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.prodID}</span>
<%--                    <INPUT class="input_from" id="prodID" value="${stockInfo.prodID}"--%>
<%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">入库时间：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.storageDateStr}</span>
<%--                    <INPUT class="input_from" id="storageDateStr" value="${stockInfo.storageDateStr}"--%>
<%--                                             name="storageDateStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from">更新时间：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockInfo.updateDateStr=='' || stockInfo.updateDateStr==null }">
                            /
                        </c:if>
                        <c:if test="${stockInfo.updateDateStr!='' || stockInfo.updateDateStr!=null }">
                            ${stockInfo.updateDateStr}
                        </c:if>
                    </span>
<%--                    <INPUT class="input_from" id="updateDateStr" value="${stockInfo.updateDateStr}"--%>
<%--                                             name="updateDateStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">更新人编号：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.updater}</span>
<%--                    <INPUT class="input_from" id="updater" value="${stockInfo.updater}"--%>
<%--                                             name="updater" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">总库存：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.stockCount}</span>
<%--                    <INPUT class="input_from" id="stockCount" value="${stockInfo.stockCount}"--%>
<%--                                             name="stockCount" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">食材单位：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockInfo.unit}</span>
<%--                    <INPUT class="input_from" id="unit" value="${stockInfo.unit}"--%>
<%--                                             name="unit" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">最近入库时间：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockInfo.latestInTimeStr=='' || stockInfo.latestInTimeStr==null}">
                            /
                        </c:if>
                        <c:if test="${stockInfo.latestInTimeStr!='' || stockInfo.latestInTimeStr!=null}">
                            ${stockInfo.latestInTimeStr}
                        </c:if>
                    </span>
<%--                    <INPUT class="input_from" id="latestInTime" value="${stockInfo.latestInTimeStr}"--%>
<%--                                             name="latestInTimeStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">最近出库时间：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockInfo.latestOutTimeStr!='' || stockInfo.latestOutTimeStr!=null}">
                            ${stockInfo.latestOutTimeStr}
                        </c:if>
                    </span>
<%--                    <INPUT class="input_from" id="latestOutTime" value="${stockInfo.latestOutTimeStr}"--%>
<%--                                             name="latestOutTimeStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="stockID" value="${stockInfo.stockID}" style="display:none"/>
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