<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>库存列表</title>
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
        .first{
            width: 25%;
            color: #00aaee;
        }
        .color-button1{
            color: #1b961b;
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
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">库存列表</strong><small></small></div>
    </div>

    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <%--							<a class="am-btn am-btn-default" href="${ctx}/UserInfoController/">  <span class="am-icon-plus"></span>新增 </a>--%>

                    <!-- 					批量删除 -->
                    <%--						<button type="button"  onclick="deleteComp()" class="am-btn am-btn-default">--%>
                    <%--							<span class="am-icon-minus"></span>  批量删除 --%>
                    <%--						</button>--%>
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
                        <th class="table-title">序号</th>
                        <th class="table-title">食材名称</th>
                        <th class="table-type">库存</th>
                        <th class="table-title">库存变动时间</th>
                        <th class="table-type">操作人</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="u" varStatus="cou">
                        <tr>
                            <td>
                                    <%--										<input type="checkbox" name="checkb" class="checkb">--%>
                            </td>
                            <td >${cou.count}</td>
                            <td class="first"><span style="color: black;font-weight: bold">${u.theProduct}  </span><a href="${ctx}/StockInfoController/jumpFindStockInfo?stockID=${u.stockID}&currentPage=${page.currentPage}">${u.prodID}</a></td>
                            <td class="color-button1"><font style="font-weight: bold;width: 14%">
                                <c:if test="${u.integer==null}">
                                    ${u.stockCount}
                                </c:if>
                                <c:if test="${u.integer!=null}">
                                    ${u.integer}
                                </c:if>
                            </font><font style="color: black">${u.unit}</font></td>
                            <td style="width: 14%">${u.updateDateStr}</td>
                            <td >${u.updater}</td>
                            <td style="width: 20%">
                                <div class="am-btn-group am-btn-group-xs">
                                    <c:forEach items="${permissions}" var="p" varStatus="s">
                                        <c:if test="${s.count=='32'}">
                                            <c:if test="${p=='1'}">
                                                <button class="am-btn am-btn-default am-btn-xs am-text-secondary" style="color: blue" type="button" onclick="javascipt:window.location.href='${ctx}/StockInfoController/jumpListStockDetailOut?stockID=${u.stockID}&currentPage=${page.currentPage}&userName=${userName}'"><span class="icon-list"></span>出库明细</button>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach items="${permissions}" var="p" varStatus="s">
                                        <c:if test="${s.count=='31'}">
                                            <c:if test="${p=='1'}">
                                                <button class="am-btn am-btn-default am-btn-xs am-text-secondary" style="color: red" type="button" onclick="javascipt:window.location.href='${ctx}/StockInfoController/jumpListStockDetailTypeStorage?stockID=${u.stockID}&currentPage=${page.currentPage}&userName=${userName}'"><span class="icon-list"></span>入库明细</button>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
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
                                <li class="am-disabled"><a href="${ctx}/StockInfoController/stockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li>
                            </c:if>
                            <c:if test="${page.currentPage>1}">
                                <li ><a href="${ctx}/StockInfoController/stockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li>
                            </c:if>
                            ${page.currentPage}/${page.allPage}页
                            <c:if test="${page.currentPage>=page.allPage}">
                                <li class="am-disabled"><a href="${ctx}/StockInfoController/stockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li>
                            </c:if>
                            <c:if test="${page.currentPage<page.allPage}">
                                <li><a href="${ctx}/StockInfoController/stockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li>
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
    function deleteComp() {
        var result = "";
        var count = 0;
        $(".checkb").each(function() {

            if ($(this).is(':checked')) {
                result += $(this).parent().next().html() + ",";
                count++;
            } else {
            }
        });
        if (!confirm("确定删除这" + count + "个用户?")) {
            return;
        }
        window.location.href = "deleteSomeUser?userIds=" + result;
    }
</script>

</body>
</html>