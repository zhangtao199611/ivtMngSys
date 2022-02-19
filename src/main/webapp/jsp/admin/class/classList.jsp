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
            <strong class="am-text-primary am-text-lg">类别管理</strong><small></small>
        </div>
    </div>
    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <a class="am-btn am-btn-default" @click.prevent="addClass()"> <span
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
                        <th class="table-title">类别名称</th>
                        <th class="table-title">排序</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>


                    <tr v-for="(d,i) in list" :class="d.status==1?'gray_text':''">
                        <td>{{d.className}}</td>
                        <td>
                            <!--up-->
                            <button @click.prevent="upSortClass(d.classID,d.className)" v-if="d.sortValue > 0"
                                    class="am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-onl "><span
                                    class="am-icon-arrow-up"></span></button>

                            <!--down-->
                            <button @click.prevent="downSortClass(d.classID)"
                                    v-if="d.sortValue < page.allCount - 1"
                                    class="am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-onl "><span
                                    class="am-icon-arrow-down"></span></button>
                        <td>
                            <div class="am-btn-group am-btn-group-xs">
                                <button type="button" v-if="d.status == 0"
                                        @click.prevent="updateClass(d.classID,d.className)"
                                        class="am-btn am-btn-default am-btn-xs am-text-secondary">
                                    <span class="am-icon-pencil-square-o"></span> 编辑
                                </button>

                                <button v-if="d.status == 0" type="button"
                                        @click.prevent="delClass(d.classID,d.className)"
                                        class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class="am-icon-trash-o"></span> 删除
                                </button>

                                <button v-if="d.showStatus == 0" type="button"
                                        @click.prevent="showClass(d.classID,d.className)"
                                        class="am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-only">
                                    <span class="am-icon-eye"></span> 展示
                                </button>


                                <button v-if="d.showStatus == 1" type="button"
                                        @click.prevent="hideClass(d.classID,d.className)"
                                        class="am-btn am-btn-default am-btn-xs am-text-warning am-hide-sm-only">
                                    <span class="am-icon-eye-slash"></span> 隐藏
                                </button>

                                <button v-if="d.status == 1" type="button"
                                        @click.prevent="recoverClass(d.classID,d.className)"
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
            page: ''
        },
        mounted() {
            this.$http.get('${ctx}/ClassController/getClassList?currentPage= 1').then(response => {
                console.log(response.data);
                this.list = response.data.resultData;
                this.page = response.data.pageModel;
            })
            ;
        },

        methods: {
            //删除大类
            delClass(id, name) {
                if (confirm("确定删除“" + name + "”吗？删除后所有“" + name + "” 的菜品将不可见。")) {
                    this.$http.get('${ctx}/ClassController/delClass?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("“" + name + "”" + (response.data.result == 1 ? "删除成功" : "删除失败"))
                        }
                    );
                }

            },
            //新增大类
            addClass() {
                var className = prompt("请输入类别名称", "");
                if (className != "" && className != null) {
                    if (confirm("确定添加吗？")) {
                        this.$http.get('${ctx}/ClassController/addClassInfo?className=' + className + "&currentPage= " + this.page.currentPage).then(
                            response => {
                                this.list = response.data.resultData;
                                this.page = response.data.pageModel;
                                alert((response.data.result == 1 ? "添加成功，" : "添加失败，") + (response.data.msg != null ? response.data.msg : ""));
                            }
                        );
                    }
                }

            },
            recoverClass(id, name) {
                if (confirm("确定恢复“" + name + "”吗？此类别将在安卓平板上不可见。")) {
                    this.$http.get('${ctx}/ClassController/recoverClass?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("类别“" + name + "”" + (response.data.result == 1 ? "恢复成功" : "恢复失败"))
                        }
                    );
                }
            },

            showClass(id, name) {
                if (confirm("确定展示“" + name + "”吗？此类别将在安卓平板上可见。")) {
                    this.$http.get('${ctx}/ClassController/showClass?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("类别“" + name + "”" + (response.data.result == 1 ? "展示成功" : "展示失败"))
                        }
                    );
                }
            },

            hideClass(id, name) {
                if (confirm("确定隐藏“" + name + "”吗？此类别将在安卓平板上不可见。")) {
                    this.$http.get('${ctx}/ClassController/hideClass?id=' + id + "&currentPage= " + this.page.currentPage).then(
                        response => {
                            this.list = response.data.resultData;
                            this.page = response.data.pageModel;
                            alert("类别“" + name + "”" + (response.data.result == 1 ? "隐藏成功" : "隐藏失败"))
                        }
                    );
                }
            },

            //修改大类
            updateClass(id, name) {
                var newName = prompt("请输入新的类别名字", name);
                if (newName != "" && newName != null) {
                    if (confirm("确定将“" + name + "”修改为“" + newName + "”吗？")) {
                        this.$http.get('${ctx}/ClassController/updateClassInfo?id=' + id + '&newName=' + newName + "&currentPage= " + this.page.currentPage).then(
                            response => {
                                this.list = response.data.resultData;
                                this.page = response.data.pageModel;
                                alert((response.data.result == 1 ? "更新成功，" : "更新失败，") + (response.data.msg != null ? response.data.msg : ""));
                            }
                        );
                    }
                }

            },

            //分页查询
            listByPage(currentPage) {
                this.$http.get('${ctx}/ClassController/getClassList?currentPage= ' + currentPage).then(response => {
                    console.log(response.data);
                    this.list = response.data.resultData;
                    this.page = response.data.pageModel;
                });
            },


            //上调排序
            upSortClass(id) {
                this.$http.get('${ctx}/ClassController/sortClass?id=' + id + '&upOrDown=' + 1 + '&currentPage= ' + this.page.currentPage).then(response => {
                    console.log(response.data);
                    this.list = response.data.resultData;
                    this.page = response.data.pageModel;
                    alert((response.data.result == 1 ? "排序成功，" : "排序失败，") + (response.data.msg != null ? response.data.msg : ""));
                });
            },

            //下调排序
            downSortClass(id) {
                this.$http.get('${ctx}/ClassController/sortClass?id=' + id + '&upOrDown=' + 0 + '&currentPage= ' + this.page.currentPage).then(response => {
                    console.log(response.data);
                    this.list = response.data.resultData;
                    this.page = response.data.pageModel;
                    alert((response.data.result == 1 ? "排序成功，" : "排序失败，") + (response.data.msg != null ? response.data.msg : ""));
                });
            },


        }

    })


</script>

</body>
</html>