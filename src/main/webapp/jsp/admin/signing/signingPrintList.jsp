<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>打印清单</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css" />
    <link rel="stylesheet" href="${ctx}/static/css/admin.css" />

    <style type="text/css">
        td {text-align:center; }
        th {text-align:center; }


        .circular1{
            width: 50px;
            height: 30px;
            border-radius: 16px;
            background-color: #ccc;
            transition: .3s;
            cursor: pointer;
        }
        .round-button1{
            width: 30px;
            height: 30px;
            background: #fff;
            border-radius: 50%;
            box-shadow: 0 1px 5px rgba(0,0,0,.5);
            transition: .3s;
            position: relative;
            left: 0;
        }
        .round-button1:hover{
            transform: scale(1.2);
            box-shadow: 0 1px 8px rgba(0,0,0,.5);
        }
        .color{
            color: #00aaee;
            font-weight: bold;
        }
        .bePutInStorage{
            color: #00aaee;
            font-weight: bold;
        }
        .adjust{
            color: red;
            font-weight: bold;
        }
        .weighing{
            color: #1b961b;
            font-weight: bold;
        }
        .first{
            width: 15%;
            color: #00aaee;
        }

    </style>
    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/js/bootbox.min.js"></script>
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">
                打印签单
            </strong><small></small></div>
    </div>
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">
            <span style="font-weight: bold;color: #0e90d2">选择日期：<input type="date" id="dateOn" value="${thatDay}"/><button onclick="dateTimeOnclick()">确定</button></span>
        </strong><small></small></div>
    </div>
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">
            <span style="font-weight: bold;color: #0e90d2">打印日期：${thatDay}</span>
        </strong><small></small></div>
    </div>
    <hr>
    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <%--							<a class="am-btn am-btn-default" href="${ctx}/UserInfoController/">  <span class="am-icon-plus"></span>新增 </a>--%>

                    <!-- 					批量删除 -->
                        <button type="button"  onclick="batchPrint()" class="am-btn am-btn-default">
                            <span class="icon-reorder"></span>  批量打印
                </div>
            </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">

        </div>
        <div class="am-u-sm-12 am-u-md-3">
            <div class="am-input-group am-input-group-sm">
                <!-- 						<input type="text" id="userName"class="am-form-field"> -->
                <span class="am-input-group-btn">
<%--             <button class="am-btn am-btn-default" onclick="serchUser()"type="button">搜索</button>--%>
          </span>
            </div>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main">
                    <thead>
                    <tr>
                        <th class="table-title"  style="width: 5%">
                        <input type="checkbox" id="checkAll"onclick="setAllNo()">
                        </th>
                        <th style="width: 5%" class="table-title">序号</th>
                        <th style="width: 20%" class="table-title">食材</th>
                        <th style="width: 10%" class="table-title">配送数</th>
                        <th style="width: 10%" class="table-title">单位</th>
                        <th style="width: 10%" class="table-title">单价</th>
                        <th style="width: 15%" class="table-title">总价</th>
<%--                        <th style="width: 15%" class="table-title">状态</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${productInfos}" var="u" varStatus="cou">
                        <tr>
                            <td  style="width: 5%">
                                <c:if test="${u.print=='已打印'}">
                                    <input type="checkbox" name="checkb" value="${u.prodID}" class="checkb">
                                </c:if>
                                <c:if test="${u.print=='未打印'}">
                                    <input type="checkbox" checked ='checked' name="checkb" value="${u.prodID}" class="checkb">
                                </c:if>
                            </td>
                            <td  style="width: 5%">${cou.count}</td>
                            <td style="width: 20%" class="first">${u.prodName}</td>
                            <td style="width: 10%" class="weighing">${u.bePutInStorage}</td>
                            <td style="width: 10%" class="adjust ">${u.prodUnit}</td>
                            <td style="width: 10%" class="bePutInStorage">${u.prodPrice}</td>
                            <td style="color: black;width: 15%">${u.totalPrices}</td>
<%--                            <td style="color: black;width: 15%">${u.print}</td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
<%--                <div class="am-cf">--%>
<%--                    共 ${page.allCount} 条记录--%>
<%--                    <div class="am-fr">--%>
<%--                        <ul class="am-pagination">--%>
<%--                            <c:if test="${page.currentPage==1}">--%>
<%--                                <li class="am-disabled"><a href="${ctx}/StockDetailInfoController/nextPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&status=${statuss}">«</a></li>--%>
<%--                            </c:if>--%>
<%--                            <c:if test="${page.currentPage>1}">--%>
<%--                                <li ><a href="${ctx}/StockDetailInfoController/nextPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&status=${statuss}">«</a></li>--%>
<%--                            </c:if>--%>
<%--                            ${page.currentPage}/${page.allPage}页--%>
<%--                            <c:if test="${page.currentPage>=page.allPage}">--%>
<%--                                <li class="am-disabled"><a href="${ctx}/StockDetailInfoController/nextPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&status=${statuss}">»</a></li>--%>
<%--                            </c:if>--%>
<%--                            <c:if test="${page.currentPage<page.allPage}">--%>
<%--                                <li><a href="${ctx}/StockDetailInfoController/nextPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&status=${statuss}">»</a></li>--%>
<%--                            </c:if>--%>
<%--                        </ul>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <hr>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('.circular1').click(function () {
            var left = $('.round-button1').css('left');
            left = parseInt(left);
            if (left == 0) {
                $('.round-button1').css({'left': '22px', 'background-color': '#F00'});
                $(this).css({'background-color': '#e7e7e7', 'box-shadow': '0 0 5px #999 inset'})
            } else {
                $('.round-button1').css({'left': '0', 'background-color': '#fff'})

            }
        })
    })


    <%--$(function(){--%>
    <%--    var resStr = '<%=session.getAttribute("responseStr")%>'--%>
    <%--    if (resStr!="null"){--%>
    <%--        alert(resStr)--%>
    <%--    }else {--%>

    <%--    }--%>
    <%--});--%>


    function setAllNo() {
        var box = document.getElementById("checkAll");
        var loves = document.getElementsByName("checkb");
        if (box.checked == false) {
            for (var i = 0; i < loves.length; i++) {
                loves[i].checked = false;
            }
        } else {
            for (var i = 0; i < loves.length; i++) {
                loves[i].checked = true;
            }
        }
    }

    function serchUser() {
        var str = $("#userName").val();
        window.location.href="serchUser?userName="+str;
    }

    function dateTimeOnclick() {
        window.location.href="${ctx}/SigningInfoController/listSigningAssign?date="+$("#dateOn").val();
    }


    function batchPrint() {
        var loves = document.getElementsByName("checkb");
        var viewZoomConfigStr="";
        for (var i = 0; i < loves.length; i++) {
            if (loves[i].checked){
                viewZoomConfigStr += loves[i].value + ",";
            }
        }
        if (viewZoomConfigStr!=''){
            window.location.href="${ctx}/SigningInfoController/selectPrintInfo?viewZoomConfigStr="+viewZoomConfigStr+"&Day=${Day}&thatDay=${thatDay}";
        }else {
            alert("请选择打印信息！")
        }

    }



</script>

</body>
</html>