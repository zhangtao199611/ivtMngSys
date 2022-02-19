<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${sysConfig.sysName}</title>
    <link rel="shortcut icon" href="${ctx}/static/img/favicon.ico" type="image/x-icon"/>
    <link href="${ctx}/static/css/Site.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/zy.layout.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/index.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/zy.form.css" rel="stylesheet"/>
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/static/css/buttons.css" rel="stylesheet"/>
    <style type="text/css"></style>
    <link href="${ctx}/static/css/zy.menu.css" rel="stylesheet"/>
</head>
<jsp:include page="./isLogin.jsp"></jsp:include>
<body>
<div class="dvheader">
    <div class="dvheadertools">
				<span class="headerspantitle">
				<!-- logo图片位置 -->
                    <!-- logo图片位置 -->
				<img src="${ctx}${sysConfig.sysLogo}" style="height:30px;width:30px;position:relative;top:18%;"/>
                    ${sysConfig.sysName}</span>
        <ul class="headerultools">
            <li class="headerlitools_info headerlitools" style="background-color: #74abda">
                <div class="headeruserface" style="text-align: center;">
                    <i class="icon-platManager" style="color: black; font-size: 19px;"> </i>
                </div>
                ${userInfo.memberName}
                <i style="margin-left: 8px;"
                   class="icon-caret-down"> </i>
                <ul>
                    <a class="button blue" href="${ctx}/LoginController/turnUpdatePassword?userID=${userInfo.userID}" style="position: absolute;right:260px;top:10px;">修改密码</a>
                    <a class="button blue" href="${ctx}/LoginController/Login"
                       style="position: absolute;right:180px;top:10px;">退出</a>
                </ul>
                <ul class="headerlitools_ulinfo">
                    </li>
                </ul>
    </div>
</div>
<div class="dvcontent">
    <ul class="ulleftmenu" style="border-right: 1px solid #ddd;">

        <c:forEach items="${menuInfos}" var="m">
            <li class="limenuitem">
                <i class="icon-cog menuicon"></i>${m.menuName}<b class="arrow icon-angle-down arrow-down"></b>
                <ul class="ulleftsubitems">
                    <c:forEach items="${m.submenu}" var="s">
                        <a href="${ctx}${s.menuFunctionUrl}?userName=${userInfo.memberID}" target="right">
                            <li>${s.menuName}</li>
                        </a>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>


<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>库存管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/StockInfoController/jumpListStockInfo" target="right">--%>
<%--                    <li>实时库存</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/StockDetailInfoController/jumpStockDetailInfoList" target="right">--%>
<%--                    <li>入库明细</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/StockDetailInfoController/jumpStockDetailInfoOutList" target="right">--%>
<%--                    <li>出库明细</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/StockDetailInfoController/jumpStockDetailInforetReatList" target="right">--%>
<%--                    <li>退货明细</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/StockDetailInfoController/theDayListOutStockInfo" target="right">--%>
<%--                    <li>当日入库明细</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/StockDetailInfoController/theDayListJoinStockInfo" target="right">--%>
<%--                    <li>当日出库明细</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>


<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>食材管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/ProductController/toAddProduct" target="right">--%>
<%--                    <li>添加食材</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/ProductController/toProductList" target="right">--%>
<%--                    <li>食材列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>

<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>类别管理 <b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/ClassController/toClassList" target="right">--%>
<%--                    <li>类别列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>

<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>用户管理 <b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/UserInfoController/jumpAccountList?userName=${userInfo.memberID}"--%>
<%--                   target="right">--%>
<%--                    <li>用户列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/UserInfoController/userInfoList?userName=${userInfo.memberID}"--%>
<%--                   target="right">--%>
<%--                    <li>员工列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>



<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>权限管理 <b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/UserInfoController/jumpPermissionList" target="right">--%>
<%--                    <li>部门列表</li>--%>
<%--                </a>--%>
<%--                <a href="${ctx}/PermissionController/jumpStationList" target="right">--%>
<%--                    <li>岗位列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>
<%--        </li>--%>
<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>报表管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/reportController/reportGeneration?type=1" target="right">--%>
<%--                    <li>周报报表</li>--%>
<%--                </a>--%>

<%--                <a href="${ctx}/reportController/reportGeneration?type=2" target="right">--%>
<%--                    <li>月报报表</li>--%>
<%--                </a>--%>

<%--                <a href="${ctx}/reportController/reportGeneration?type=3" target="right">--%>
<%--                    <li>年报报表</li>--%>
<%--                </a>--%>

<%--                <a href="${ctx}/reportController/customReport" target="right">--%>
<%--                    <li>自定义报表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>


<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>签单管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/SigningInfoController/listSigning" target="right">--%>
<%--                    <li>打印签单</li>--%>
<%--                </a>--%>

<%--                <a href="${ctx}/SigningInfoController/listSigningInfo?userID=${userInfo.loginName}" target="right">--%>
<%--                    <li>签单列表</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>
<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>菜单管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/MenInfoController/jumpMenInfoList" target="right">--%>
<%--                    <li>菜单管理</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>

<%--        <li class="limenuitem">--%>
<%--            <i class="icon-cog menuicon"></i>系统管理<b class="arrow icon-angle-down arrow-down"></b>--%>
<%--            <ul class="ulleftsubitems">--%>
<%--                <a href="${ctx}/SysConfigController/findConfigService" target="right">--%>
<%--                    <li>配置管理</li>--%>
<%--                </a>--%>
<%--            </ul>--%>
<%--        </li>--%>
    </ul>
    <div style="position: absolute; left: 191px; right: 20px; ">
        <iframe scrolling="no"  οnlοad="setIframeHeight(right)" width="100%" height=1800 id="right" name="right" border="none"></iframe>
    </div>
</div>
<script src="${ctx}/static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/plugs/Jqueryplugs.js" type="text/javascript"></script>
<script src="${ctx}/static/js/_layout.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-3.3.1.min1.js"></script>

</body>
<script type="text/javascript">

    $(function () {
        $(".menu ul li").click(function () {
            $(this).find("dl").slideToggle(200).parent().siblings().find("dl").slideUp(200);
            $(this).find("span i").toggleClass('action').parents().siblings().find("span i").removeClass('action');
            $("#right").load(function () {
                $(this).height($(this).contents().find("#content").height() + 140);
            });
        })

        $(".tab ul li").click(function () {
            $(".tab ul li").removeClass('action');
            $(this).addClass('action');
            $(".box .item").hide();
            $(".box .item").eq($(".tab ul li").index(this)).show();
        })
    })

    function setIframeHeight(iframeId){
       alert("shezhi iframe")
        var cwin = document.getElementById(iframeId);
        if (document.getElementById){
            if (cwin && !window.opera){
                if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight){
                    cwin.height = cwin.contentDocument.body.offsetHeight + 20; //FF NS
                }
                else if(cwin.Document && cwin.Document.body.scrollHeight){
                    cwin.height = cwin.Document.body.scrollHeight + 10;//IE
                }
            }else{
                if(cwin.contentWindow.document && cwin.contentWindow.document.body.scrollHeight)
                    cwin.height = cwin.contentWindow.document.body.scrollHeight;//Opera
            }
        }
    };

    function changeHeight() {
        alert("aaa");
//      $(this).height($(this).contents().find("#content").height() + 40);
    };
</script>

</html>