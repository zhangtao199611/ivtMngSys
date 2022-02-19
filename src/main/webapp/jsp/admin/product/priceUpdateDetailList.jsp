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
            <strong class="am-text-primary am-text-lg">{{productInfo.prodName}} - <font
                    color="black">价格修改明细</font></strong><small></small>
        </div>
    </div>
    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <a style="margin-left: 10px" class="am-btn am-btn-default"
                       href="${ctx}/ProductController/backToProductList"> <span
                            class="am-icon-caret-left"></span>返回
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
                        <th class="table-title">最新价格</th>
                        <th class="table-title">单位</th>
                        <th class="table-title">修改时间</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr v-for="(d,i) in list" :title="d.description" :class="d.status==1?'gray_text':''">
                        <td style="display:table-cell; vertical-align:middle"><font
                                color="blue">{{d.latestPrice}}</font>元
                        </td>
                        <td style="display:table-cell; vertical-align:middle">{{d.prodUnit}}</td>
                        <td style="display:table-cell; vertical-align:middle">{{d.updateTimeStr}}</td>
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
            productInfo: ''
        },
        mounted() {
            this.cPage = 1;
            this.cPage = ${priceDetailCurrentPage};
            console.log("priceDetailCurrentPage = " +${priceDetailCurrentPage})
            console.log("cPage = " + this.cPage);
            //获取价格明细列表
            this.$http.get('${ctx}/ProductController/getPriceUpdateDetailByProdIDByPage?prodID=${prodID}&priceDetailCurrentPage=' + this.cPage).then(response => {
                console.log(response.data);
                this.list = response.data.resultData;
                this.page = response.data.pageModel;
            });

            //获取产品详情
            this.$http.get('${ctx}/ProductController/getProductDetailById?id=${prodID}').then(response => {
                console.log(response.data);
                this.productInfo = response.data.resultData;
            })
            ;
        },

        methods: {
            //删除菜品
            delProd(id, name) {
                if (confirm("确定删除“" + name + "”吗？")) {
                    this.$http.get('${ctx}/ProductController/delProduct?id=' + id + "&priceDetailCurrentPage= " + this.page.priceDetailCurrentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("“" + name + "”" + (response.data.result == 1 ? "删除成功" : "删除失败"))
                        }
                    );
                }

            },
            //恢复菜品
            recoverProduct(id, name) {
                if (confirm("确定恢复“" + name + "”吗？")) {
                    this.$http.get('${ctx}/ProductController/recoverProduct?id=' + id + "&priceDetailCurrentPage= " + this.page.priceDetailCurrentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("菜品“" + name + "”" + (response.data.result == 1 ? "恢复成功" : "恢复失败"))
                        }
                    );
                }
            },

            //修改菜品
            updateProduct(id) {
                window.location.href = "${ctx}/ProductController/toUpdateProduct?id=" + id + "&priceDetailCurrentPage=" + this.page.priceDetailCurrentPage;
            },

            //查看菜品详情
            seeProduct(id) {
                window.location.href = "${ctx}/ProductController/toProductDetail?id=" + id + "&priceDetailCurrentPage=" + this.page.priceDetailCurrentPage;
            },

            //分页查询
            listByPage(priceDetailCurrentPage) {
                this.$http.get('${ctx}/ProductController/getPriceDetailList?priceDetailCurrentPage= ' + priceDetailCurrentPage + "&prodID=${prodID}").then(response => {
                    console.log(response.data);
                    this.list = response.data.resultData;
                    this.page = response.data.pageModel;
                });
            },
        }

    })


</script>

</body>
</html>