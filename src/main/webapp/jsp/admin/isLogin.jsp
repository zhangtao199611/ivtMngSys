<%@ page import="com.example.ivt_mng_sys.entity.UserInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>

<body>


<%
    UserInfo user = (UserInfo) session.getAttribute("userInfo");
    if (user == null) {
%>

<script type="text/javascript" language="javascript">
    alert("您未登录，请先登录");
    if (window != top) {
        top.location.href = '${ctx}/LoginController/Login';
    } else {
        window.location = '${ctx}/LoginController/Login';                            // 跳转到登录界面
    }
    // 弹出错误信息
</script>
<%
    }
%>
</body>
</html>