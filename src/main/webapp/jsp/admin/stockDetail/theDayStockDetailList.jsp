<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>明细列表</title>
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
        <c:if test="${status.stockDetailType==0}">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">当日出库明细</strong><small></small></div>
        </c:if>
        <c:if test="${status.stockDetailType==1}">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">当日入库明细</strong><small></small></div>
        </c:if>
        <c:if test="${status.stockDetailType==2}">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">当日损耗明细</strong><small></small></div>
        </c:if>
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
                            <th class="table-title">食材</th>
                        <th class="table-title">秤重重量</th>
                        <th class="table-title">调整重量</th>
                        <c:if test="${status.stockDetailType==0}">
                            <th class="table-title">出库重量</th>
                        </c:if>
                        <c:if test="${status.stockDetailType==1}">
                            <th class="table-title">入库重量</th>
                        </c:if>
                        <th class="table-title">时间</th>
                        <th class="table-title">人员</th>
                        <th class="table-type">照片</th>
                            <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="u" varStatus="cou">
                        <tr>
                            <td>
                                    <%--										<input type="checkbox" name="checkb" class="checkb">--%>
                            </td>
                            <td style="display:table-cell; vertical-align:middle">${cou.count}</td>
                            <td style="display:table-cell; vertical-align:middle" class="first"><span style="color: black;font-weight: bold">${u.theProduct}</span><a href="${ctx}/ProductController/toProductDetail?id=${u.stockDetailID}&currentPage=${page.currentPage}">${u.stockID}</a></td>
                            <td style="display:table-cell; vertical-align:middle" class="weighing">
                                    <c:if test="${u.weighingWeight=='0.000'}">
                                        <span style="color: black;font-weight: initial">/</span>
                                    </c:if>
                                <c:if test="${u.weighingWeight!='0.000'}">
                            ${u.weighingWeight} <font style="font-weight: initial;color: black">${u.unit}</font></td>
                        </c:if>
                            <td style="display:table-cell; vertical-align:middle" class="adjust ">
                                <c:if test="${u.adjustWeight=='0.000'}">
                                    <font style="font-weight: initial;color: black">/</font>
                                </c:if>
                                <c:if test="${u.adjustWeight!='0.000'}">
                                    ${u.adjustWeight} <font style="font-weight: initial;color: black">${u.unit} </font>
                                    <c:if test="${u.remarks!='null'}">
                                        ${u.remarks}
                                    </c:if>
                                </c:if>
                            </td>
                            <td style="display:table-cell; vertical-align:middle" class="bePutInStorage">
                                <c:if test="${u.integer==null}">
                                    ${u.weight} <font style="font-weight: initial;color: black">${u.unit}</font>
                                </c:if>
                                <c:if test="${u.integer!=null}">
                                    ${u.integer} <font style="font-weight: initial;color: black">${u.unit}</font>
                                </c:if>
                            </td>
                            <td style="color: black;width: 13%;display:table-cell; vertical-align:middle">${u.operationTimeStr}</td>
                            <td style="color: black;display:table-cell; vertical-align:middle">${u.operator}</td>
                            <c:if test="${u.photoImg==null}">
                                <td>暂无图片</td>
                            </c:if>
                            <c:if test="${u.photoImg!=null}">
                                <td><img style="width: 100px;height: 100px" src="${ctx}/static/${u.photoImg}"></td>
                            </c:if>
                            <td style="width: 8%;display:table-cell; vertical-align:middle">
                                <div class="am-btn-group am-btn-group-xs">
                                    <c:forEach items="${permissions}" var="p" varStatus="s">
                                        <c:if test="${s.count=='66'}">
                                            <c:if test="${p=='1'}">
                                                <button class="am-btn am-btn-default am-btn-xs am-text-secondary" type="button" onclick="javascipt:window.location.href='${ctx}/StockDetailInfoController/findStockDetail?stockDetailID=${u.stockDetailID}&currentPage=${page.currentPage}'"><span class="icon-list"></span>明细详情</button>
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
                                <li class="am-disabled"><a href="${ctx}/StockDetailInfoController/theDayListOutStockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&status=${statuss}">«</a></li>
                            </c:if>
                            <c:if test="${page.currentPage>1}">
                                <li ><a href="${ctx}/StockDetailInfoController/theDayListOutStockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&status=${statuss}">«</a></li>
                            </c:if>
                            ${page.currentPage}/${page.allPage}页
                            <c:if test="${page.currentPage>=page.allPage}">
                                <li class="am-disabled"><a href="${ctx}/StockDetailInfoController/theDayListOutStockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&status=${statuss}">»</a></li>
                            </c:if>
                            <c:if test="${page.currentPage<page.allPage}">
                                    <li><a href="${ctx}/StockDetailInfoController/theDayListOutStockInfoPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&status=${statuss}">»</a></li>
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