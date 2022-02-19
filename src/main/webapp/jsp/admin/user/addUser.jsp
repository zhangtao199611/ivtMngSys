<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>添加用户</title>
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
                <strong class="am-text-primary am-text-lg"><h2>添加员工</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="${ctx}/UserInfoController/addMemberInfo" method="post" onsubmit="return checks()"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">员工姓名</label>
                <DIV class="controls"><INPUT class="input_from" id="TureName" type=text name="TureName"
                                             placeholder=" 请输入员工姓名">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">员工工号</label>
                <DIV class="controls"><INPUT class="input_from" id="jobID" type=text name="jobID"
                                             placeholder=" 请输入员工工号">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">联系电话</LABEL>
                <DIV class="controls"><INPUT class="input_from" id="phoneNumber" name="phoneNumber"
                                             placeholder=" 请输入联系电话">
                    <P class=help-block></P></DIV>
            </DIV>

            <input type="text" name="adder" value="${UserInfo.memberID}" style="display:none"/>
            <input type="text" name="currentPage" value="${currentPage}" style="display:none"/>
            <%--		<input type="text" name="compId" value ="${compId}" style = "display:none"/>--%>


            <%--           <DIV class="control-group" id="td" >--%>
            <%--           <DIV >--%>
            <%--            <LABEL class="laber_from" id="tup1">登录账号</LABEL>--%>
            <%--               <DIV  class="controls" >--%>
            <%--                   <select name="loginName" id="loginName">--%>
            <%--                       <c:if test="${account==0}">--%>
            <%--                           <option value="0">--暂无可用账号，请创建!--</option>--%>
            <%--                       </c:if>--%>
            <%--                       <c:if test="${account!=0}">--%>
            <%--                           <option value="0">--请选择--</option>--%>
            <%--                       </c:if>--%>
            <%--                       <c:forEach items="${userInfos}" var="c">--%>
            <%--                           <option value=${c.userID}>${c.loginName}</option>--%>
            <%--                       </c:forEach>--%>
            <%--                   </select>--%>
            <%--                   <P class=help-block></P>--%>
            <%--               </DIV>--%>
            <%--            </DIV>--%>
            <%--          </DIV>--%>

            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="unitID">员工单位</LABEL>
                    <DIV class="controls">
                        <select name="unitID" id="">
                            <c:forEach items="${unitInfos}" var="c">
                                <option onclick="selectUnt(${c.unitID})" value=${c.unitID}>${c.untitName}</option>
                            </c:forEach>
                        </select>
                        <P class=help-block></P>
                    </DIV>
                </DIV>
            </DIV>
            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="tup2">员工部门</LABEL>
                    <DIV class="controls">
                        <select name="deptID" id="deptID">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${DeptInfos}" var="c">
                                <option onclick="selectOpt(${c.deptID})" value=${c.deptID}>${c.deptName}</option>
                            </c:forEach>
                        </select>
                        <P class=help-block></P>
                    </DIV>
                </DIV>
            </DIV>

            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="tup">用户权限(可多选)</LABEL>
                    <input type="text" name="features" id="features" value="" style="display:none"/>
                    <div class="mySelect" style="width: 250px;float: left;  opacity: 1;"></div>
                    <div id="mySelect" class="mySelect" style="width: 250px;float: left; opacity: 1;"></div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" style="width:120px;">
                        确认
                    </button>
                    <a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="#" onclick="javascript :history.back(-1);" style="width:120px;">
                        返回
                    </a>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">
    $(function () {
        var resStr = '<%=session.getAttribute("responseStr")%>'
        if (resStr != "null") {
            alert(resStr)
        } else {

        }
    });

    function checks() {
        var userName = $("#TureName").val().trim();
        var loginName = $("#loginName").val().trim();
        var userTel = $("#phoneNumber").val().trim();
        var features = $("#features").val().trim();
        var deptID = $("#deptID").val().trim();
        var phoneReg = /(^1[3|4|5|7|8|9]\d{9}$)|(^09\d{8}$)/;
        var nameReg = /^[\u4E00-\u9FA5]{2,4}$/;
        if (userName == "" || userName == null) {
            alert("请输入员工姓名");
            return false;
        }
        if (!nameReg.test(userName)) {
            alert("请输入正确的姓名");
            return false;
        }
        if (loginName == "" || loginName == null || loginName == "0") {
            alert("请选择登录账号");
            return false;
        }
        if (deptID == "" || deptID == null || deptID == "0") {
            alert("请选择员工所在部门")
            return false;
        }
        if (userTel == "" || userTel == null) {
            alert("请输入手机号");
            return false;
        }
        if (!phoneReg.test(userTel)) {
            alert("请输入正确的手机号");
            return false;
        }
        if (features == "" || features == null) {
            alert("请选择员工权限");
            return false;
        }
        return true;
    }

    function selectUnt(x) {
        $.ajax({
            url: "${ctx}/UserInfoController/listDeptInfo",
            type: "get",
            data: {
                "unitID": x
            },
            dataType: "json",
            success: function (data) {
                console.log(data.data);
                var html = "";
                $("#deptID").empty();
                for (var e = 0; e < data.data.length; e++) {
                    var depID = data.data[e].deptID;
                    console.log("data=" + data.data[e].deptName)
                    html += '<option onclick="selectOpt(' + depID + ')" value=' + data.data[e].deptID + '>' + data.data[e].deptName + '</option>'
                }
                $("#deptID").append(html); //渲染
            }
        });
    }

    function selectOpt(x) {
        $.ajax({
            url: "${ctx}/UserInfoController/listStationInfoByStationName",
            type: "get",
            data: {
                "deptID": x
            },
            dataType: "json",
            success: function (data) {
                console.log(data);
                $("#mySelect").mySelect({
                    mult: true,//true为多选,false为单选
                    option: data,
                    onChange: function (res) {//选择框值变化返回结果
                        console.log(res);
                        $("#features").val(res);
                    }
                });
            }
        });
    }

    $("#jobID").blur(function () {
        $.ajax({
            url: "${ctx}/UserInfoController/ajaxFindJsbIDORPhoneNumber",
            data: {
                "jobID": $("#jobID").val().trim(),
                "phoneNumber": null
            },
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                var code = data.code;
                if (code != 0) {
                    $("#jobID").val("");
                    alert(data.data)
                }
            }
        });
    })


    $("#phoneNumber").blur(function () {
        $.ajax({
            url: "${ctx}/UserInfoController/ajaxFindJsbIDORPhoneNumber",
            data: {
                "jobID": null,
                "phoneNumber": $("#phoneNumber").val().trim()
            },
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                var code = data.code;
                if (code != 0) {
                    $("#phoneNumber").val("");
                    alert(data.data)
                }
            }
        });
    })

    $(function () {
        var option = null;
        // 从后台获取特征来选择
        $.ajax({
            url: "${ctx}/UserInfoController/listStationInfoByStationName",
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
            }
        });
    });


</script>
</html>