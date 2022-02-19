<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
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
    </style>
    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/js/bootbox.min.js"></script>
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<jsp:include page="./isLogin.jsp"></jsp:include>

<body>
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


    $(function(){
        var resStr = '<%=session.getAttribute("responseStr")%>'
        if (resStr!="null"){
            alert(resStr)
        }else {

        }
    });


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