<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>签单列表</title>
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
                签单列表
            </strong><small></small></div>
    </div>
    <hr>
    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
<%--                    <span style="font-weight: bold;color: #0e90d2">时间：${toDay}至${thatDay}</span>--%>
                    <a class="am-btn am-btn-default" href="${ctx}/SigningInfoController/goSigningImg?userID=${memberInfoByJobID.jobID}">  <span class="am-icon-plus"></span>新增 </a>

                    <!-- 					批量删除 -->
<%--                        <button type="button"  onclick="deleteComp1()" class="am-btn am-btn-default">--%>
<%--                            <span class="icon-reorder"></span>  周报库存--%>
<%--                        </button>--%>
<%--                        <button type="button"  onclick="deleteComp2()" class="am-btn am-btn-default">--%>
<%--                            <span class="icon-list-ul"></span>  月报库存--%>
<%--                        </button>--%>
<%--                        <button type="button"  onclick="deleteComp3()" class="am-btn am-btn-default">--%>
<%--                            <span class="icon-list-ol"></span>  年报库存--%>
<%--                        </button>--%>
                </div></div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">

        </div>
        <div class="am-u-sm-12 am-u-md-3">
            <div class="am-input-group am-input-group-sm">
                <!-- 						<input type="text" id="userName"class="am-form-field"> -->
                <span class="am-input-group-btn">
<!--             <button class="am-btn am-btn-default" onclick="serchUser()"type="button">搜索</button> -->
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
                        <th class="table-title">
                            <%--										<input type="checkbox" id="checkAll"onclick="setAllNo()">--%>
                        </th>
                        <th style="width: 10%" class="table-title">序号</th>
                        <th style="width: 10%" class="table-title">拍照人员</th>
                        <th style="width: 15%" class="table-title">添加时间</th>
                        <th style="width: 20%" class="table-title">签单地址</th>
                        <th style="width: 15%" class="table-title">照片</th>
                        <th style="width: 20%" class="table-title">备注</th>
                        <th style="width: 10%" class="table-title">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${signingInfos}" var="u" varStatus="cou">
                        <tr>
                            <td>
                                    <%--										<input type="checkbox" name="checkb" class="checkb">--%>
                            </td>
                            <td style="display:table-cell; vertical-align:middle;width: 10%">${cou.count}</td>
                            <td style="display:table-cell; vertical-align:middle;width: 10%" class="first">${u.adder}</td>
                            <td style="display:table-cell; vertical-align:middle;width: 15%" class="weighing">${u.addTimeStr}</td>
                            <td style="display:table-cell; vertical-align:middle;width: 20%" class="adjust ">${u.unitID}</td>
                            <td style="display:table-cell; vertical-align:middle;width: 15%" class="bePutInStorage"><img style="width: 100px;height: 100px" src="${ctx}/static/${u.sigImg}"></td>
                            <td style="display:table-cell; vertical-align:middle;width: 20%" style="color: black;width: 13%">${u.remarks}</td>
                            <td style="width: 10%;display:table-cell; vertical-align:middle;">
                                <div class="am-btn-group am-btn-group-xs">
                                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="button" onclick="javascipt:window.location.href='${ctx}/SigningInfoController/signingDetails?signingID=${u.signingID}&currentPage=${page.currentPage}'"><span class="icon-list"></span>签单详情</button>
<%--                                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="button" onclick="javascipt:window.location.href='${ctx}//?signingID=${u.signingID}&currentPage=${page.currentPage}'"><span class="icon-pencil"></span>签单修改</button>--%>
                                </div>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="am-cf">
                    共 ${page.allCount} 条记录
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${page.currentPage==1}">
                                <li class="am-disabled"><a href="${ctx}/SigningInfoController/signingPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li>
                            </c:if>
                            <c:if test="${page.currentPage>1}">
                                <li ><a href="${ctx}/SigningInfoController/signingPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li>
                            </c:if>
                            ${page.currentPage}/${page.allPage}页
                            <c:if test="${page.currentPage>=page.allPage}">
                                <li class="am-disabled"><a href="${ctx}/SigningInfoController/signingPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li>
                            </c:if>
                            <c:if test="${page.currentPage<page.allPage}">
                                <li><a href="${ctx}/SigningInfoController/signingPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
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
    function deleteComp1() {

    }
    function deleteComp2() {

    }
    function deleteComp3() {

    }
</script>

</body>
</html>