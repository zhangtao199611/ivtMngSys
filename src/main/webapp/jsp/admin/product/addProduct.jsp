<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>添加食材</title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/wu.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/icon.css"/>
    <script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/linq.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/area.js"></script>
    <link rel="stylesheet" href="${ctx}/static/css/add.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="${ctx}/static/utilLib/bootstrap.min.css" type="text/css" media="screen"/>
    <style type="text/css">
        .fruitRadio {
            color: red;
        }

    </style>
    <script src="${ctx}/static/js/vue.min.js"></script>
    <script src="${ctx}/static/js/axios.min.js"></script>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>


<script src="${ctx}/static/js/vue.min.js"></script>
<script src="${ctx}/static/js/axios.min.js"></script>


<body>
<div class="div_from_aoto" style="width: 500px;" id="app">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>添加食材</h2></strong><small></small>
            </div>
        </div>
        <br>
        <FORM action="addProduct" id="addProdForm" method="post" v-on:submit.prevent="onSubmit"
              enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">食材名称</label>
                <DIV class="controls">
                    <INPUT class="input_from" id="prodName" v-model="newProd.prodName"
                           type=text
                           name="prodName" placeholder=" 请输入食材名称">
                    <P class=help-block></P></DIV>
            </DIV>

            <INPUT class="input_from" id="adder" style="display: none"
                   type=text
                   name="adder" placeholder=" " value="${userInfo.userID}">


            <DIV class="control-group">
                <label class="laber_from">食材单位</label>
                <DIV class="controls">

                    <INPUT class="input_from" id="prodUnit" type=text name="prodUnit"
                           v-model="newProd.prodUnit"
                           placeholder=" 请输入食材单位">
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">食材单格</label>
                <DIV class="controls">

                    <INPUT class="input_from" id="prodPrice" type=text name="prodPrice" data-dotrange="{0,2}"
                           onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'').replace('.','$#$').replace(/\./g,'').replace('$#$','.').replace(/\.{2,}/g,'.').replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')"
                           maxlength='20'
                           v-model="newProd.prodPrice"
                           placeholder=" 请输入食材价格">元/{{newProd.prodUnit}}
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <label class="laber_from">食材保质期</label>
                <DIV class="controls">


                    <INPUT class="input_from" id="shelfLife" type="number" name="shelfLife"
                           v-model="newProd.expirationTime"
                           placeholder=" 请输入食材保质期">天<P class=help-block></P></DIV>
            </DIV>


            <DIV class="control-group" id="td">
                <DIV>
                    <LABEL class="laber_from" id="tup">食材图片</LABEL> 图片大小不能超过800kb
                    <input type="file" v-model="newProd.prodImgFile"
                           accept=".bmp,.jpg,.png,.tif,.gif,.pcx,.tga,.exif,.fpx,.svg,.psd,.cdr,.pcd,.dxf,.ufo,.eps,.ai,.raw,.WMF,.webp,.avif"
                           name="prodImgFile">
                    <!--         	<input type="button" value="添加更多.." onclick="addMore()"> -->
                </DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from">食材类别</LABEL>
                <DIV class="controls">
                    <SELECT name="classID" class="input_select" name="classID" id="datasetProperty"
                            v-model="newProd.classID">
                        <option value=null selected=selected>---请选择---</option>
                        <option v-for=" item in classList" :value="item.classID">{{ item.className }}</option>
                    </SELECT>
                </DIV>
            </DIV>


            <DIV class="controls"><label class="laber_from">标品/非标品</label>
                <input name="standardStatus" checked="checked" v-model="newProd.standardStatus" class="fruitRadio"
                       v-model="newProd.standardStatus"
                       id="radio1" style="width:20px;height:20px" type="radio"
                       value="0"/>
                <label for="radio1">标品 </label>
                <input name="standardStatus" id="radio2" v-model="newProd.standardStatus"
                       v-model="newProd.standardStatus"
                       style="width: 20px;height:20px;margin-left: 20px"
                       type="radio" value="1"/>
                <label for="radio2">非标品</label>
            </DIV>
            <br>

            <DIV class="control-group">
                <LABEL class="laber_from">食材说明</LABEL>
                <DIV class="controls"><textarea name="description" style="width:250px;height:150px;" cols="30"
                                                v-model="newProd.description"
                                                rows="4"> </textarea>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">
                <LABEL class="laber_from"></LABEL>
                <br/>
                <DIV class="controls">
                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary" id="button" type="submit"
                            style="width:120px;">确认
                    </button>
                </DIV>
            </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">
    Vue.prototype.$http = axios;
    new Vue({
        el: '#app',
        data: {
            classList: '',
            newProd: {
                classID: null,
                expirationTime: '',
                description: '',
                prodName: '',
                adder: '',
                standardStatus: 0,
                prodUnit: '',
                prodImgFile: '',
                prodPrice: '',
            }
        },
        mounted() {
            this.$http.get('${ctx}/ClassController/getAllClassList').then(response => {
                console.log(response.data);
                this.classList = response.data.resultData;
                this.newProd.classID = null;
                this.newProd.adder = "${userInfo.userID}";
            });
        },

        methods: {
            onSubmit() {
                if (this.newProd.prodName == null || this.newProd.prodName.trim() == "") {
                    alert("请输入食材名");
                    return false;
                } else if (this.newProd.classID == null) {
                    alert("请选择食材所属类别");
                    return false;
                } else if (this.newProd.prodUnit == null) {
                    alert("请输入食材单位")
                    return false;
                } else if (this.newProd.prodImgFile == null) {
                    alert("请选择食材图片")
                    return false;
                }
                $("#addProdForm").submit();
            },
        }
    })
</script>
</html>