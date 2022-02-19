<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>添加菜品</title>
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

        input {
            background-color: white;
            border: none;
        }

        textarea {
            background-color: white;
            border: none;
        }
    </style>
    <script src="${ctx}/static/js/vue.min.js"></script>
    <script src="${ctx}/static/js/axios.min.js"></script>
</head>
<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div id="app" class="div_from_aoto" style="width: 500px;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg"><h2>菜品详情</h2></strong><small></small>
            </div>
        </div>

        <div style="display: flex;flex-direction: row;align-items: center;height: 240px;width: 100%;padding-left: 20px">
            <img :src="'${ctx}'+editProd.prodImgUrl" width="auto" height="200px"/></div>

        <br>
        <FORM action="addProduct" method="post" enctype="multipart/form-data">
            <DIV class="control-group">
                <label class="laber_from">菜品名称</label>
                <DIV class="controls"><INPUT class="input_from" id="prodName" v-model="editProd.prodName" type=text
                                             disabled/>
                    <P class=help-block></P></DIV>
            </DIV>

            <DIV class="control-group">

                <DIV class="control-group">
                    <label class="laber_from">菜品保质期</label>
                    <DIV class="controls"><INPUT style="width: auto" id="shelfLife" type="number"
                                                 v-model="editProd.expirationTime" type=text name="shelfLife"
                                                 disabled>天
                        <P class=help-block></P></DIV>
                </DIV>

                <DIV class="control-group">
                    <label class="laber_from">菜品单位</label>
                    <DIV class="controls"><INPUT class="input_from"
                                                 v-model="editProd.prodUnit" type=text name="shelfLife"
                                                 disabled>
                        <P class=help-block></P></DIV>
                </DIV>

                <DIV class="control-group">
                    <label class="laber_from">最新价格 </label>
                    <DIV class="controls">
                        <INPUT
                                v-model="editProd.prodPrice+'元/'+editProd.prodUnit" style="width: 80px" type=text
                                name="shelfLife"
                                disabled> （更新时间：{{editProd.priceUpdateTimeStr}}）
                    </DIV>
                </DIV>

                <DIV class="control-group">
                    <LABEL class="laber_from">菜品类别</LABEL>
                    <DIV class="controls">
                        <SELECT v-model="editProd.classID" disabled class="input_select" name="lClass"
                                id="datasetProperty">
                            <option value=null selected=selected>---请选择---</option>
                            <option v-for="item in classList" :value="item.classID">{{ item.className }}</option>
                        </SELECT>
                    </DIV>
                </DIV>
                <DIV class="controls">
                    <label class="laber_from">标品/非标品</label>
                    <label style="position:relative; top:7px"> {{editProd.standardStatus == 0?'标品':'非标品'}}</label>
                </DIV>
                <br>

                <DIV class="control-group">
                    <label class="laber_from">添加人</label>
                    <DIV class="controls"><INPUT class="input_from" v-model="editProd.adderName" type=text
                                                 placeholder=" " disabled/>
                        <P class=help-block></P></DIV>
                </DIV>

                <DIV class="control-group">
                    <label class="laber_from">添加时间</label>
                    <DIV class="controls"><INPUT class="input_from" v-model="editProd.addTimeStr" type=text
                                                 disabled/>
                        <P class=help-block></P></DIV>
                </DIV>


                <DIV v-if="editProd.updater" class="control-group">
                    <label class="laber_from">修改人</label>
                    <DIV class="controls"><INPUT class="input_from" v-model="editProd.updaterName" type=text
                                                 placeholder=" " disabled/>
                        <P class=help-block></P></DIV>
                </DIV>

                <DIV v-if="editProd.updateTimeStr" class="control-group">
                    <label class="laber_from">修改时间</label>
                    <DIV class="controls"><INPUT class="input_from" v-model="editProd.updateTimeStr" type=text
                                                 disabled/>
                        <P class=help-block></P></DIV>
                </DIV>


                <DIV class="control-group">
                    <LABEL class="laber_from">菜品说明</LABEL>
                    <DIV class="controls"><textarea disabled name="prodExp" v-model="editProd.description"
                                                    style="width:250px;height:150px;" cols="30"
                                                    rows="4"> </textarea>
                        <P class=help-block></P></DIV>
                </DIV>

                <DIV class="control-group">
                    <LABEL class="laber_from"></LABEL>
                    <br/>
                    <DIV class="controls">
                        <button class="am-btn am-btn-default am-btn-xs am-text-secondary"
                                type="submit" @click.prevent="toEditProduct()"
                                style="width:120px;">编辑
                        </button>

                        <button class="am-btn am-btn-default am-btn-xs am-text-secondary"
                                style="margin-left: 50px"
                                @click.prevent="backToMainPage()"
                                style="width:120px;">返回上页
                        </button>
                    </DIV>

                    <DIV class="control-group">
                        <LABEL class="laber_from"></LABEL>
                        <br/>
                    </DIV>
        </FORM>
    </div>
</body>

<script type="text/javascript">
    Vue.prototype.$http = axios;
    new Vue({
        el: '#app',
        data: {
            classList: null,
            editProd: {
                classID: '',
                expirationTime: '',
                description: '',
                prodName: '',
                adder: '',
                standardStatus: '',
                prodUnit: '',
                priceUpdateTimeStr: '',
            },
        },
        mounted() {
            this.$http.get('${ctx}/ProductController/getProductDetailById?id=${prodID}').then(response => {
                console.log(response.data);
                this.editProd = response.data.resultData;
            })
            ;
            this.$http.get('${ctx}/ClassController/getAllClassList').then(response => {
                console.log(response.data);
                this.classList = response.data.resultData;
                this.newProd.updater = "${userInfo.userID}";
            })
            ;
        },

        methods: {


            checkProd() {
                if (this.editProd.prodName == null || this.editProd.prodName == '') {
                    alert("请输入菜品名");
                    return false;
                } else if (this.editProd.classID == null) {
                    alert("请选择菜品类别");
                    return false;
                } else if (this.editProd.expirationTime == 0 || this.editProd.expirationTime == null) {
                    alert("请输入保质期");
                    return false;
                }
                return true;
            },

            backToMainPage() {
                window.location.href = "${ctx}/ProductController/backToProductList"
            },

            toEditProduct() {
                window.location.href = "${ctx}/ProductController/toUpdateProduct?id=" + this.editProd.prodID + "&currentPage=" + ${currentPage};
            }

        }

    })

</script>
</html>