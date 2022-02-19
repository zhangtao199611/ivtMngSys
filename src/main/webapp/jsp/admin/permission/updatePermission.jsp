<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>设置权限</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/select.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/wu.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/icon.css"/>
    <script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/select.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/linq.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/area.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen"/>
    <%--    <link rel="stylesheet" href="/static/css/main.css" type="text/css" media="screen" />--%>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" type="text/css" media="screen"/>
    <style type="text/css">
        .fruitRadio {
            color: red;
        }

    </style>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>设置权限</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/PermissionController/setUserPer" method="post"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">员工姓名</label>
                <DIV class="controls">
                    <span style="color: #0e90d2">${memberInfo.memberName}</span>
                    <%--                    <INPUT class="input_from" id="TureName" value="${memberInfo.memberName}" type=text--%>
                    <%--                                             name="memberName" placeholder=" 请输入员工姓名">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">员工工号</label>
                <DIV class="controls">
                    <span style="color: #0e90d2">${memberInfo.jobID}</span>
                    <%--                    <INPUT class="input_from" id="jobID" value="${memberInfo.jobID}" type=text--%>
                    <%--                                             name="jobID" placeholder=" 请输入员工工号">--%>
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="memberID" value="${memberInfo.memberID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <input type="text" name="userName" value="${userName}" style="display:none"/>


            <DIV class="control-group">
                <label class="laber_from"><span style="font-size: 20px">选择权限：</span></label>
                <DIV class="controls">
                    <P class=help-block></P></DIV>
            </DIV>
            <br/><br/>

            <%--            <c:forEach items="${menuInfos}" var="u">--%>
            <%--            <DIV class="control-group">--%>
            <%--                <label class="laber_from">一级菜单：</label>--%>
            <%--                <DIV class="controls">--%>
            <%--                    <span style="color: #0e90d2">${u.menuName}&nbsp;<input type="checkbox" style="width: 15px;height: 15px"/></span>--%>
            <%--                    <P class=help-block></P></DIV>--%>
            <%--            </DIV>--%>

            <%--                <c:forEach items="${u.submenu}" var="s">--%>
            <%--                    <DIV class="control-group">--%>
            <%--                        <label class="laber_from">二级菜单：</label>--%>
            <%--                        <DIV class="controls">--%>
            <%--                            <span style="color: #0e90d2">${s.menuName}&nbsp;<input type="checkbox" style="width: 15px;height: 15px"/></span>--%>
            <%--                            <P class=help-block></P></DIV>--%>
            <%--                    </DIV>--%>

            <%--                    <c:if test="${s.submenu3!='null' || s.submenu3!=null}">--%>
            <%--                        <DIV class="control-group">--%>
            <%--                            <label class="laber_from">三级功能：</label>--%>
            <%--                            <DIV class="controls">--%>
            <%--                                <c:forEach items="${s.submenu3}" var="b">--%>
            <%--                                    <span style="color: #0e90d2">${b.menuName}&nbsp;<input type="checkbox" style="width: 15px;height: 15px"/></span>--%>
            <%--                                </c:forEach>--%>
            <%--                                <P class=help-block></P>--%>
            <%--                            </DIV>--%>
            <%--                        </DIV>--%>
            <%--                    </c:if>--%>
            <%--                </c:forEach>--%>
            <%--            </c:forEach>--%>



                <c:forEach items="${menuInfos}" var="u"><!--一级菜单-->
                <DIV class="control-group">
                    <label class="laber_from"><span style="font-size: 20px">${u.menuID}：${u.menuName}</span>
                        <c:if test="${u.sign==1}">
                            <input type="checkbox" checked="checked" name="${u.menuID}" style="width: 15px;height: 15px"/>
                        </c:if>
                        <c:if test="${u.sign==0}">
                            <input type="checkbox" name="${u.menuID}" style="width: 15px;height: 15px"/>
                        </c:if>
                    </label>
                    <c:forEach items="${u.submenu}" var="s"><!--二级菜单-->
                    <span style="font-size: 18px">${s.menuName}</span><c:if test="${s.sign==1}"><input type="checkbox" checked="checked" name="${s.menuID}" style="width: 15px;height: 15px"/>&nbsp;&nbsp;</c:if>
                    <c:if test="${s.sign==0}"><input type="checkbox"  name="${s.menuID}" style="width: 15px;height: 15px"/>&nbsp;&nbsp;
                    </c:if>
                    <c:forEach items="${s.submenu3}" var="b"><!--三级菜单-->
                        ${b.menuName}<c:if test="${b.sign==1}"><input checked="checked" type="checkbox" style="width: 15px;height: 15px"  name="${b.menuID}"/></c:if><c:if test="${b.sign==0}"><input type="checkbox" style="width: 15px;height: 15px"  name="${b.menuID}"/></c:if>
                    </c:forEach>
                    <br/>
                    </c:forEach>
                        <%--                    <DIV class="controls">--%>
                        <%--                        <input type="checkbox" style="width: 15px;height: 15px"/>--%>
                        <%--                        <P class=help-block></P>--%>
                        <%--                    </DIV>--%>
                    <br/>

                </DIV>
                <br/>
                </c:forEach>

                <br/>
                <DIV class="control-group">
                    <LABEL class="laber_from"></LABEL>
                    <br/>
                    <DIV class="controls">
                        <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" type="submit"
                                style="width:120px;">
                            确认
                        </button>
                        <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="#"
                           onclick="javascript :history.back(-1);" style="width:120px;">
                            返回
                        </a>
                    </DIV>
                </DIV>
            </FORM>
    </div>
</body>

<script type="text/javascript">
    function deptIDOpt(res) {
        var option = null;
        //从后台获取特征来选择
        $.ajax({
            url: "${ctx}/UserInfoController/listStationInfoByStationName?deptID=" + res,
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                option = data;
                //多选插件=>
                var mySelect = $("#mySelect").mySelect({
                    mult: true,//true为多选,false为单选
                    option: option,
                    onChange: function (res) {//选择框值变化返回结果
                        console.log(res);
                        $("#features").val(res);
                    }
                });
                var features = "${memberInfo.stationIDs}";
                var featuresArray = features.split(",");
                //设定原本处于选中状态的特征
                mySelect.setResult(featuresArray);
            }
        });
    }

    $(function () {
        var option = null;
        //从后台获取特征来选择
        $.ajax({
            url: "${ctx}/UserInfoController/listStationInfoByStationName?deptID=${memberInfo.deptID}",
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                option = data;
                //多选插件=>
                var mySelect = $("#mySelect").mySelect({
                    mult: true,//true为多选,false为单选
                    option: option,
                    onChange: function (res) {//选择框值变化返回结果
                        console.log(res);
                        $("#features").val(res);
                    }
                });
                var features = "${memberInfo.stationIDs}";
                var featuresArray = features.split(",");
                //设定原本处于选中状态的特征
                mySelect.setResult(featuresArray);
            }
        });
    });

</script>
</html>