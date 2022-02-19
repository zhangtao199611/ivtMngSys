<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css" />
<link rel="stylesheet" href="${ctx}/static/css/admin.css" />
	<link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

	<style type="text/css">
		td {text-align:center; }
			th {text-align:center;  }
	</style>
<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/js/bootbox.min.js"></script>

</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">${menu.menuName}---菜单功能管理</strong><small></small>
			</div>
		</div>

		<hr>

		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-6">
				<div class="am-btn-toolbar">
					<div class="am-btn-group am-btn-group-xs">
						<a class="am-btn am-btn-default" href="${ctx}/MenInfoController/jumpAddMenInfo3?menuID=${menu.menuID}"> <span
							class="am-icon-plus"></span>新增
						</a>

					</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-3"></div>
			<div class="am-u-sm-12 am-u-md-3">
				<div class="am-input-group am-input-group-sm">
<%-- 					<input type="text" id="devName" class="am-form-field"> <span --%>
<!-- 						class="am-input-group-btn"> -->
<!-- 						<button class="am-btn am-btn-default" type="button" -->
<!-- 							onclick="serchDev()">搜索</button> -->
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
								<th class="table-title">菜单名称</th>
								<th class="table-title" style="width:150px">菜单说明</th>
								<th class="table-title">菜单链接  </th>
								<th class="table-set">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${menus}" var="d">
								<tr>
									<td>${d.menuName}</td>
									<td><c:if test="${d.menuExp==null || d.menuExp==''}">
										/
									</c:if>
										<c:if test="${d.menuExp!=null || d.menuExp!=''}">
											${d.menuExp}
										</c:if></td>
									<td><c:if test="${d.menuFunctionUrl==null || d.menuFunctionUrl==''}">
										/
									</c:if>
										<c:if test="${d.menuFunctionUrl!=null || d.menuFunctionUrl!=''}">
											${d.menuFunctionUrl}
										</c:if></td>
									<td>
											<div class="am-btn-group am-btn-group-xs">
											
												<button type="button" onclick="window.location.href='${ctx}/MenInfoController/jumpUpdateMenuInfo?menuID=${d.menuID}'"
													class="am-btn am-btn-default am-btn-xs am-text-secondary">
													<span class="am-icon-pencil-square-o"></span>  编辑菜单
												</button>
<%--												--%>
<%--												<button  type="button" onclick="window.location.href='pUpdateMenu?menuID=${d.menuID}'"--%>
<%--													class="am-btn am-btn-default am-btn-xs am-text-secondary">--%>
<%--													<span class="icon-th-list"></span>  菜单功能--%>
<%--												</button>--%>
											</div>
									</td>
									<td></td>
									</tr>
							</c:forEach>

						</tbody>
					</table>
					<div class="am-cf">
<%-- 						共 ${page.allCount} 条记录 --%>
						<div class="am-fr">
<!-- 							<ul class="am-pagination"> -->
<%-- 								<c:if test="${page.currentPage==1}"> --%>
<!-- 									<li class="am-disabled"><a -->
<%-- 										href="listProductByPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li> --%>
<%-- 								</c:if> --%>
<%-- 								<c:if test="${page.currentPage>1}"> --%>
<!-- 									<li><a -->
<%-- 										href="listProductByPage?pageSize=${page.pageSize}&currentPage=${page.currentPage-1}">«</a></li> --%>
<%-- 								</c:if> --%>
<%-- 								${page.currentPage}/${page.allPage}页 --%>
<%-- 								<c:if test="${page.currentPage>=page.allPage}"> --%>
<!-- 									<li class="am-disabled"><a -->
<%-- 										href="listProductByPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li> --%>
<%-- 								</c:if> --%>
<%-- 								<c:if test="${page.currentPage<page.allPage}"> --%>
<!-- 									<li><a -->
<%-- 										href="listProductByPage?pageSize=${page.pageSize}&currentPage=${page.currentPage+1}">»</a></li> --%>
<%-- 								</c:if> --%>
<!-- 							</ul> -->
						</div>
					</div>
					<hr>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
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
		function serchDev() {
			var str = $("#devName").val();
			alert(str);
			window.location.href = "searchDev?devName=" + str;
		}

		function deleteProduct() {
			var result = "";
			var count = 0;
			$(".checkb").each(function() {

				if ($(this).is(':checked')) {
					result += $(this).parent().next().html() + ",";
					count++;
				} else {
				}
			});
			if (!confirm("确定删除这" + count + "件产品?")) {
				return;
			}
			window.location.href = "deleteSomeProduct?prodIds=" + result;

		}
	</script>

</body>
</html>