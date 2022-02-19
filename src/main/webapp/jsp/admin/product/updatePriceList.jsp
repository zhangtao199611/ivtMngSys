<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="${ctx}/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/admin.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/pagination.css">
    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

    <script src="${ctx}/static/js/pagination.js"></script>
    <script src="${ctx}/static/js/jquery.min.js"></script>

    <script src="${ctx}/static/js/vue.min.js"></script>
    <script src="${ctx}/static/js/axios.min.js"></script>

    <style type="text/css">
        td {
            text-align: center;
        }

        th {
            text-align: center;
        }

        .gray_text {
            color: gray
        }

        .selected_prods {
            background-color: #03A9F4;
            color: white;
            border-radius: 10%;
            margin-left: 20px;
            padding: 3px;
            margin-bottom: 10px
        }

    </style>
    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/js/bootbox.min.js"></script>

</head>

<jsp:include page="../isLogin.jsp"></jsp:include>

<body>
<div class="admin-content-body" id="app">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">食材管理</strong><small></small>
        </div>
    </div>
    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <a class="am-btn am-btn-default" href="${ctx}/ProductController/toAddProduct"> <span
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
                        <th class="table-title">价格修改</th>
                        <th class="table-title">食材名称</th>
                        <th class="table-title">食材图片</th>
                        <th class="table-title">食材类别</th>
                        <th class="table-title">单位</th>
                        <th class="table-title">标品/非标品</th>
                        <th class="table-title">保质期（天）</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr v-for="(d,i) in list" :title="d.description" :class="d.status==1?'gray_text':''">
                        <td style="display:table-cell; vertical-align:middle"><input type="checkbox"
                                                                                     :checked='d.ischecked'
                                                                                     v-on:change="showSelectedProd(event,d)"
                                                                                     v-model="updatePriceProdList"
                                                                                     :id="'d.prodID'" :value="d">{{d.prodID}}
                        </td>
                        <td style="display:table-cell; vertical-align:middle"><a v-on:click.stop="seeProduct(d.prodID)">{{d.prodName}}</a>
                        </td>
                        <td style="display:table-cell; vertical-align:middle"><img :src="'${ctx}'+d.prodImgUrl"
                                                                                   width="auto" height="140px"/></td>
                        <td style="display:table-cell; vertical-align:middle">{{d.className}}</td>
                        <td style="display:table-cell; vertical-align:middle">{{d.prodUnit}}</td>
                        <td style="display:table-cell; vertical-align:middle">{{d.standardStatus == 0 ? '标品' : '非标品'}}
                        </td>
                        <td style="display:table-cell; vertical-align:middle">{{d.expirationTime}}</td>
                        <td style="display:table-cell; vertical-align:middle">
                            <div class="am-btn-group am-btn-group-xs">
                                <button type="button" v-if="d.status == 0"
                                        @click.prevent="updateProduct(d.prodID)"
                                        class="am-btn am-btn-default am-btn-xs am-text-secondary">
                                    <span class="am-icon-pencil-square-o"></span> 编辑
                                </button>

                                <button type="button" v-if="d.status == 0"
                                        @click.prevent="toPriceDetailList(d.prodID)"
                                        class="am-btn am-btn-default am-btn-xs am-text-success">
                                    <span class="am-icon-list-ol"></span> 价格修改明细
                                </button>

                                <button v-if="d.status == 0" type="button"
                                        @click.prevent="delProd(d.prodID,d.prodName)"
                                        class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class="am-icon-trash-o"></span> 删除
                                </button>

                                <button v-if="d.status == 1" type="button"
                                        @click.prevent="recoverProduct(d.prodID,d.prodName)"
                                        class="am-btn am-btn-default am-btn-xs am-text-success am-hide-sm-only">
                                    <span class="icon-share-alt"></span> 恢复
                                </button>
                            </div>
                        </td>
                        <td></td>
                    </tr>

                    </tbody>
                </table>
                <div class="am-cf">
                    共 {{page.allCount}} 条记录
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <li v-if="page.currentPage==1" class="am-disabled">
                                <a>«</a>
                            </li>
                            <li v-if="page.currentPage>1">
                                <a @click.prevent="listByPage(page.currentPage-1)">«</a>
                            </li>
                            {{page.currentPage}}/{{page.allPage}}页
                            <li v-if="page.currentPage>=page.allPage" class="am-disabled"><a>»</a>
                            </li>
                            <li v-if="page.currentPage<page.allPage">
                                <a @click.prevent="listByPage(page.currentPage+1)">»</a>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="selected_prod">
                    <h2>修改价格产品：</h2>
                    <font v-for="(d,i) in updatePriceProdList" :title="d.description" class="selected_prods"
                          :class="d.status==1?'gray_text':''">{{d.prodName}}</font>
                </div>
                <button class="am-btn am-btn-default am-btn-xs am-text-secondary"
                        style="float: right;margin-right: 30px;"
                        @click.prevent="submitUpdatePriceProdList()"
                        style="width:120px;">确认
                </button>
                <hr>
            </form>
        </div>
        <hr>
        </form>
    </div>
</div>
</div>

<!-- 	分页 -->
<script type="text/javascript">

</script>
<script type="text/javascript">
    Vue.prototype.$http = axios;
    new Vue({
        el: '#app',
        data: {
            list: '',
            page: '',
            cPage: '',
            updatePriceProdList: [],
        },
        mounted() {
            this.cPage = 1;
            this.cPage = ${currentPage};
            this.$http.get('${ctx}/ProductController/getProductList?currentPage= ' + this.cPage).then(response => {
                console.log(response.data);
                this.list = response.data.resultData;
                this.page = response.data.pageModel;
            });
        },

        methods: {
            //删除食材
            delProd(id, name) {
                if (confirm("确定删除“" + name + "”吗？")) {
                    this.$http.get('${ctx}/ProductController/delProduct?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("“" + name + "”" + (response.data.result == 1 ? "删除成功" : "删除失败"))
                        }
                    );
                }

            },
            //恢复食材
            recoverProduct(id, name) {
                if (confirm("确定恢复“" + name + "”吗？")) {
                    this.$http.get('${ctx}/ProductController/recoverProduct?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("食材“" + name + "”" + (response.data.result == 1 ? "恢复成功" : "恢复失败"))
                        }
                    );
                }
            },

            //修改食材
            updateProduct(id) {
                window.location.href = "${ctx}/ProductController/toUpdateProduct?id=" + id + "&currentPage=" + this.page.currentPage;
            },

            //查看食材详情
            seeProduct(id) {
                window.location.href = "${ctx}/ProductController/toProductDetail?id=" + id + "&currentPage=" + this.page.currentPage;
            },

            //查看食材价格修改明细
            toPriceDetailList(id) {
                window.location.href = "${ctx}/ProductController/toPriceDetailList?prodID=" + id;
            },


            //分页查询
            listByPage(currentPage) {
                this.$http.get('${ctx}/ProductController/getProductList?currentPage= ' + currentPage).then(response => {
                    console.log(response.data);
                    this.list = response.data.resultData;
                    this.page = response.data.pageModel;
                });
            },

            //查看选中产品（调试用用）
            showSelectedProd(event, prod) {
                console.log(prod.ischecked);
                console.log(this.updatePriceProdList.length);
                console.log(this.updatePriceProdList);
            },

            //上传修改价格产品列表给后台
            submitUpdatePriceProdList() {
                alert("准备提交");
                this.$http.post(
                    "${ctx}/ProductController/setUpdatPriceProdList?", this.updatePriceProdList
                ).then((value) => {

                }).catch((value) => {
                    this.$message.error("")
                })
            }

        }


    });

</script>

</body>
</html>