<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改岗位</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css" />
    <link rel="stylesheet" href="${ctx}/static/css/select.css" />

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/icon.css" />
    <script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/select.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/linq.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/area.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen" />
    <%--    <link rel="stylesheet" href="/static/css/main.css" type="text/css" media="screen" />--%>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" type="text/css" media="screen" />
    <style type="text/css">
        .fruitRadio{color:red;}

        .checkbox1{
            width: 20px;
        }
        .checkboxText{
            font-size: 15px;
            display: inline;
        }

    </style>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>修改岗位</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM  action="${ctx}/UserInfoController/" method="post"  onsubmit="return checks()" enctype="multipart/form-data" >
            <DIV class="control-group" id="td" >
                <DIV>
                    <LABEL class="laber_from" id="tup2">所属部门</LABEL>
                    <DIV  class="controls" >
                        <select name="deptID" id="deptID">
                            <option value="${DeptInfo.deptName}">${DeptInfo.deptName}</option>
                            <c:forEach items="${DeptInfos}" var="c">
                                <option value=${c.deptID}>${c.deptName}</option>
                            </c:forEach>
                        </select>
                        <P class=help-block></P>
                    </DIV>
                </DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">岗位名称</label>
                <DIV  class="controls" ><INPUT class="input_from" id="stationName"  value="${stationInfo.stationName}" type=text name="stationName" placeholder=" 请输入部门说明"><P class=help-block></P></DIV>
            </DIV>
            <DIV class="control-group">
                <label class="laber_from">请修改权限</label>
            </DIV>
            <br/>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">账户管理</label>
                <DIV class="controls">
                    <input class="checkbox1" type="checkbox" name="interest" value="修改账户"><div class="checkboxText">查看账户</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="修改账户"><div class="checkboxText">修改账户</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="冻结账户"><div class="checkboxText">冻结账户</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="恢复账户"><div class="checkboxText">恢复账户</div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">员工管理</label>
                <DIV class="controls">
                    <input class="checkbox1" type="checkbox" name="interest" value="查看员工"><div class="checkboxText">查看员工</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="新增员工"><div class="checkboxText">新增员工</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="冻结员工"><div class="checkboxText">冻结员工</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="修改员工"><div class="checkboxText">修改员工</div>
                    <br/>
                    <input class="checkbox1" type="checkbox" name="interest" value="恢复员工"><div class="checkboxText">恢复员工</div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">产品管理</label>
                <DIV class="controls">
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">查看产品</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">新增产品</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">冻结产品</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">修改产品</div>
                    <br/>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">恢复产品</div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">类别管理</label>
                <DIV class="controls">
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">查看类别</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">新增类别</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">冻结类别</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">修改类别</div>
                    <br/>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">恢复类别</div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <label class="laber_from">库存管理</label>
                <DIV class="controls">
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">查看库存</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">新增库存</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">冻结库存</div>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">修改库存</div>
                    <br/>
                    <input class="checkbox1" type="checkbox" name="interest" value="音乐"><div class="checkboxText">恢复库存</div>
                </DIV>
            </DIV>
            <br/>
            <DIV class="control-group">
                <LABEL class="laber_from" ></LABEL>
                <br />
                <DIV class="controls" ><button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button"  style="width:120px;" >确认</button></DIV>
            </DIV>
        </FORM>
    </div>
</body>
<script type="text/javascript">
    function checks() {
        var deptID = $("#deptID").val().trim();
        if (deptID==null || deptID=="" || deptID==0){
            alert("请选择部门")
            return false;
        }
        return true;
    }
    $("#stationName").blur(function () {
        if ($("#stationName").val().trim()!=""){
            $.ajax({
                url: "${ctx}/PermissionController/ajaxDepartmentIFName",
                data:{
                    "stationName":$("#stationName").val().trim()
                },
                type: "get",
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    var code = data.code;
                    if (code!=0){
                        $("#stationName").val("");
                        alert(data.data)
                    }
                }
            });
        }
    })

</script>
</html>