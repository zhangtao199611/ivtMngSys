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
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" type="text/css" media="screen"/>
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

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
                <strong class="am-text-primary am-text-lg"><h2>库存明细详情</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/StockDetailInfoController/returnStockDetailList" method="post" onsubmit="return check()" enctype="multipart/form-data">
<%--            <DIV class="control-group">--%>
<%--                <label class="laber_from">库存明细：</label>--%>
<%--                <DIV class="controls">--%>
<%--                    <span class="input_from" style="color: #0e90d2">${stockDetail.stockDetailID}</span>--%>
<%--&lt;%&ndash;                    <INPUT class="input_from" id="stockID" name="stockID" value="${stockDetail.stockID}" type=text name="loginName" placeholder=" "  disabled="disabled">&ndash;%&gt;--%>
<%--                    <P class=help-block></P>--%>
<%--                </DIV>--%>
<%--            </DIV>--%>


    <DIV class="control-group">
        <LABEL class="laber_from">图片展示：</LABEL>
        <DIV class="controls">
            <c:if test="${stockDetail.photoImg==null}">
                <span class="input_from" style="color: #0e90d2">暂无图片</span>
            </c:if>
            <c:if test="${stockDetail.photoImg!=null}">
                <img style="width: 100%;height: 100%" src="${ctx}/static/${stockDetail.photoImg}">
                <div class="am-btn-group am-btn-group-xs">
                    <button type="button"  onclick="batchPrint()" class="am-btn am-btn-default">
                        <span class="icon-repeat"></span>  翻转
                </div>
            </c:if>
            <%--                    <INPUT class="input_from" id="prodID" value="${stockDetail.prodID}"--%>
            <%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
            <P class=help-block></P></DIV>


    </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">菜品名称：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockDetail.stockID}</span>
<%--                    <INPUT class="input_from" id="prodID" value="${stockDetail.prodID}"--%>
<%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">秤重重量：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockDetail.unit == null}">
                            ${stockDetail.weighingWeight}
                        </c:if>
                        <c:if test="${stockDetail.unit != null}">
                            ${stockDetail.unit}
                        </c:if>
                    </span>
                    <%--                    <INPUT class="input_from" id="prodID" value="${stockDetail.prodID}"--%>
                    <%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">调整重量：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockDetail.adjustWeight == '0.000'}">
                            /
                        </c:if>
                        <c:if test="${stockDetail.adjustWeight != '0.000'}">
                            ${stockDetail.adjustWeight}
                        </c:if>
                    </span>
                    <%--                    <INPUT class="input_from" id="prodID" value="${stockDetail.prodID}"--%>
                    <%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">实际重量：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockDetail.weight}</span>
                    <%--                    <INPUT class="input_from" id="prodID" value="${stockDetail.prodID}"--%>
                    <%--                                             name="prodID" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <LABEL class="laber_from">操作时间：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockDetail.operationTimeStr}</span>
<%--                    <INPUT class="input_from" id="storageDateStr" value="${stockDetail.storageDateStr}"--%>
<%--                                             name="storageDateStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">操作人：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">${stockDetail.operator}</span>
                    <%--                    <INPUT class="input_from" id="updater" value="${stockDetail.updater}"--%>
                    <%--                                             name="updater" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">库存明细类型：</LABEL>
                <DIV class="controls">
                    <span class="input_from" style="color: #0e90d2">
                        <c:if test="${stockDetail.stockDetailType==0}">
                            出库
                        </c:if>
                        <c:if test="${stockDetail.stockDetailType==1}">
                            入库
                        </c:if>
                        <c:if test="${stockDetail.stockDetailType==2}">
                            损耗
                        </c:if>
                        <c:if test="${stockDetail.stockDetailType==3}">
                            退货
                        </c:if>
                    </span>
                <%--                    <INPUT class="input_from" id="storageDateStr" value="${stockDetail.storageDateStr}"--%>
                <%--                                             name="storageDateStr" placeholder=" "  disabled="disabled">--%>
                    <P class=help-block></P></DIV>
            </DIV>
            <input type="text" name="stockID" value="${stockDetail.stockID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <input type="text" name="stockDetailType" value="${stockDetail.stockDetailType}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="#" onclick="javascript :history.back(-1);" id="button" style="width:120px;">
                        返回
                    </a>
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