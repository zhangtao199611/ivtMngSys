<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>登录 - 库存管理数据平台</title>
		<link rel="shortcut icon" href="${ctx}/static/img/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="${ctx}/static/layui/css/layui3.css">
		<link rel="stylesheet" href="${ctx}/static/css/login.css">
		
	</head>

	<body>
		<div class="layui-carousel video_mask" id="login_carousel" onclick="out()">
			<div carousel-item>
				<div class="carousel_div1"></div>
				<div class="carousel_div2"></div>
				<div class="carousel_div3"></div>
			</div>
			<div class="login layui-anim layui-anim-up" id= "loginDiv"   style="display:none; position:relative;top:-50%;">
				<div class="layui-logo">
<!-- 					<a href="#" target="_blank"><img src="images/logo1.png" style="width:100px;100px" /></a> -->
				</div>
				<fieldset class="layui-elem-field layui-field-title">
					<legend class="text-white"> ${sysConfig.sysName}登录</legend>
				</fieldset>
				<form >
				<div class="layui-form"  >
					<div class="layui-form-item">
						<input type="text" id="account" lay-verify="required|account" maxlength="20" name="username" placeholder="请输入用户名" autocomplete="off" autofocus="autofocus" class="layui-input">
					</div>
					<div class="layui-form-item">
						<input type="password" id="password" lay-verify="required" maxlength="20"  name="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
<!-- 					<div class="layui-form-item form_code"> -->
<!-- 						<input type="text" id="code" lay-verify="required|code" maxlength="6" placeholder="请输入验证码" autocomplete="off" class="layui-input"> -->
<!-- 						<div class="code"><img id="refCode_login_img" width="118" height="38"></div> -->
<!-- 					</div> -->
					<input type="button" id="loginButton" onclick="login()" class="login_btn layui-btn layui-btn-radius layui-btn-normal"value="登录" lay-submit lay-filter="login">
<%--					<strong class="text-white">还没有账号？ <a href="register.jsp" style="color: cornflowerblue;">立即注册&raquo;</a>--%>
					</strong>
				</div>
				</form>
				<hr class="layui-bg-gray">
				<div class="layui-footer text-white">
					<!-- 底部固定区域 -->
					&nbsp; &nbsp;南京安恒智能物联技术有限公司
				    <br>	&copy;2019-2020 v1.21 
				</div>
			</div>
		</div>

		<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/layui/layui.all.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/login.js"></script>
		
		<script type="text/javascript">
		 function out(){
		 var ui =document.getElementById("loginDiv");
		 ui.style.display="block";}
		 function login(){
		 	var username = $("#account").val();
		 	var password = $("#password").val();
			 $.ajax({
				 url:"${ctx}/LoginController/authLogin",
				 data:{username:username,password:password,
				 "verify":"PEA00503"
				 },
				 async:true,
				 cache:false,
				 type:"POST",
				 success:function(result){
					 if (result.result == 400){
						 alert(result.msg);
						 location.href="${ctx}/LoginController/turnUpdatePassword?userID="+result.resultData.userID;
					 }
					 if (result.result == 200){
						 location.href = "${ctx}/LoginController/toIndex?userName="+username
					 }else {
						 alert(result.msg);
					 }
				 }
			 });
		 }
		</script>
	</body>

</html>