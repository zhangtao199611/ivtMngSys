<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>打印清单</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css"/>

    <style type="text/css">
        td {
            text-align: center;
        }

        th {
            text-align: center;
        }

        .circular1 {
            width: 50px;
            height: 30px;
            border-radius: 16px;
            background-color: #ccc;
            transition: .3s;
            cursor: pointer;
        }

        .round-button1 {
            width: 30px;
            height: 30px;
            background: #fff;
            border-radius: 50%;
            box-shadow: 0 1px 5px rgba(0, 0, 0, .5);
            transition: .3s;
            position: relative;
            left: 0;
        }

        .round-button1:hover {
            transform: scale(1.2);
            box-shadow: 0 1px 8px rgba(0, 0, 0, .5);
        }

        .color {
            color: #00aaee;
            font-weight: bold;
        }

        .bePutInStorage {
            color: #00aaee;
            font-weight: bold;
        }

        .adjust {
            color: red;
            font-weight: bold;
        }

        .weighing {
            color: #1b961b;
            font-weight: bold;
        }

        .first {
            width: 15%;
            color: #00aaee;
        }

        .title {
            width: 100%;
            height: auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center
        }

        .title_font {
            font-size: 20px;
            font-weight: bold;
            font-family: "Arial", "Microsoft YaHei", "微软雅黑";
        }

        .title_time {
            width: auto;
            height: 100%;
            padding-left: 1%;
        }

        .title_block {
            width: 70%;
            height: 100%;
        }

        .title_second_line {
            width: 100%;
            height: auto;
        }

        .bottom_box {
            width: 100%;
            height: auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .bottom_sign_area {
            width: 100%;
            height: 250px;
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            margin-top: 0px;
        }

        .bottom_date_and_page_area {

            width: 100%;
            height: 30px;
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            font-size: 5px;
            position: relative;
            bottom: 200px;
        }

        .bottom_sign_area_item {
            width: 33.3%;
            height: 100%;
            font-weight: normal;
        }

        table {
            padding-bottom: 0px;
            margin-bottom: 0px;
        }

    </style>

    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/js/bootbox.min.js"></script>
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>

<button onclick="doPrint()">打印</button>
<!--startprint-->
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">
            入库单
        </strong><small></small></div>
    </div>

    <div class="title"><font class="title_font" align="center">${untitName}入库清单</font>
        <div class="title_second_line">
            <div class="am-fl am-cf title_time"><strong class="am-text-primary am-text-lg">
                <span style="font-weight: bold;color: #0e90d2">入库时间：<font
                        id="latestTime">${productInfos[0].latestTimeStr}</font></span>
                <input style="display: none" id="viewZoomConfigStr" value="${viewZoomConfigStr}"/>
                <input style="display: none" id="Day" value="${Day}"/>
                <input style="display: none" id="thatDay" value="${thatDay}"/>
            </strong><small></small></div>

            <div class="am-fl am-cf title_block"><strong class="am-text-primary am-text-lg">
            </div>
        </div>
    </div>


    <hr>
    <div class="am-g">

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
                        <th style="border:1px solid black; width: 8%">序号</th>
                        <th style="border:1px solid black; width: 30%">食材</th>
                        <th style="border:1px solid black;width: 19%">配送数</th>
                        <th style="border:1px solid black;width: 13%">单价</th>
                        <th style="border:1px solid black;width: 14%">总价</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${productInfos}" var="u" varStatus="cou">
                        <c:if test="${empty u.prodName}">
                            <tr>
                                <td style="border:1px solid black;"></td>

                                <td style="border:1px solid black;" class="first">${u.prodName}</td>
                                <td style="border:1px solid black;"
                                    class="weighing">${u.bePutInStorage}${u.prodUnit}</td>

                                <td style="border:1px solid black;"
                                    class="bePutInStorage">${u.prodPrice}${u.prodUnit}</td>

                                <td style="border:1px solid black;
                                    style=" color: black;width: 13%
                                ">${u.totalPrices}<font style="visibility: hidden">站位</font>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty u.prodName}">

                            <tr>
                                <td style="border:1px solid black;">${cou.count}</td>

                                <td style="border:1px solid black;" class="first">${u.prodName}</td>
                                <td style="border:1px solid black;"
                                    class="weighing">${u.bePutInStorage}${u.prodUnit}</td>

                                <td style="border:1px solid black;"
                                    class="bePutInStorage">${u.prodPrice}元/${u.prodUnit}</td>
                                <td style="border:1px solid black;"
                                    style="color: black;width: 13%">${u.totalPrices}元
                                </td>
                            </tr>

                        </c:if>
                    </c:forEach>

                    <tr>
                        <td style="border:0px solid black;collapse: 2"> </td>
                        <td style="border:0px solid black;" class="first"> 打印时间：<font id="print_time"> </font></td>
                        <td style="border:0px solid black;"
                            class="weighing"> 第${page.currentPage}页 （共${page.allPage}页）
                        </td>

                        <td style="border:0px solid black;"
                            class="bePutInStorage">合计:
                        </td>
                        <td style="border:0px solid black;"
                            style="color: black;width: 13%">${pageAmount}元
                        </td>
                    </tr>

                    </tbody>


                </table>
                <div class="bottom_box">
                    <div class="bottom_sign_area">
                        <div class="bottom_sign_area_item">
                            <font size="3px">送货方签字：</font>________________
                        </div>
                        <div class="bottom_sign_area_item">
                            <font size="3px">${infoList[0].deptName}签字：</font>________________
                        </div>
                        <div class="bottom_sign_area_item">
                            <font size="3px">${infoList[1].deptName}签字：</font>________________
                        </div>
                    </div>

                    <div class="bottom_date_and_page_area">
                        <div style="width: 43%">
                        </div>
                        <div class="bottom_sign_area_item">

                        </div>
                        <div class="bottom_sign_area_item">

                        </div>
                    </div>
                </div>

                <!--endprint-->

                <div class="am-cf">
                    共 ${page.allCount} 条记录
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${page.currentPage==1}">
                                <li class="am-disabled"><a
                                        href="${ctx}/SigningInfoController/upDownPrintPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&viewZoomConfigStr=${viewZoomConfigStr}&Day=${Day}">«</a>
                                </li>
                            </c:if>
                            <c:if test="${page.currentPage>1}">
                                <li>
                                    <a href="${ctx}/SigningInfoController/upDownPrintPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}&viewZoomConfigStr=${viewZoomConfigStr}&Day=${Day}">«</a>
                                </li>
                            </c:if>
                            ${page.currentPage}/${page.allPage}页
                            <c:if test="${page.currentPage>=page.allPage}">
                                <li class="am-disabled"><a
                                        href="${ctx}/SigningInfoController/upDownPrintPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&viewZoomConfigStr=${viewZoomConfigStr}&Day=${Day}">»</a>
                                </li>
                            </c:if>
                            <c:if test="${page.currentPage<page.allPage}">
                                <li>
                                    <a href="${ctx}/SigningInfoController/upDownPrintPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}&viewZoomConfigStr=${viewZoomConfigStr}&Day=${Day}">»</a>
                                </li>
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
        window.location.href = "serchUser?userName=" + str;
    }


    function doPrint() {
        getNowFormatDateThenShow();
        bdhtml = window.document.body.innerHTML;
        sprnstr = "<!--startprint-->"; //开始打印标识字符串有17个字符
        eprnstr = "<!--endprint-->"; //结束打印标识字符串
        prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17); //从开始打印标识之后的内容
        prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
        window.document.body.innerHTML = prnhtml; //把需要打印的指定内容赋给body.innerHTML
        window.print(); //调用浏览器的打印功能打印指定区域
        window.document.body.innerHTML = bdhtml;//重新给页面内容赋值；
    }


    function getNowFormatDateThenShow() {
        var currentdate = getFormatDate(new Date());
        $("#print_time").html(currentdate);
    }

    function getFormatDate(date) {
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }


    function firstDateLaterThenSencondDate(firstDate, secondDate) {
        if (firstDate.getTime() > secondDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }


</script>

</body>
</html>