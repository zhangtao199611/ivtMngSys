/**
 * Created by zxm on 2017/8/29.
 */
$.fn.extend({
   "multiselectInit":function(opt){
       if(typeof opt != "object"){
           alert("参数出错！");
           return;
       }
       var mid = $(this).attr("id");
       if(mid==null||mid==""){
           alert("要设定一个id!");
           return;
       }
       if(!opt.selectData instanceof Array){
            alert("selectData参数错误");
            return;
       }


       $.each(multiselectTools.getInitOption(mid), function (key, value) {
           if (opt[key] == null) {
               opt[key] = value;
           }
       });
       if(opt.isSelectData!=""){
           if(!opt.isSelectData instanceof Array){
               alert("isSelectData参数错误，数据类型应该为数组");
               return;
           }
           if(opt.isSelectData.length>1&&!opt.isMulti){
                alert("单选模式设置错误！");
                return;
           }
       }
       multiselectTools.initWithUI(opt);
       multiselectEvent.initCleanEvent(opt);
       multiselectEvent.initClickEvent(opt);
       multiselectEvent.initDeleteEvent(opt);
   }
});


var multiselectTools={
    "checkStyleArray":{
        "yx":{
            "check":"icon-xuanzhong",
            "uncheck":"icon-weixuanzhong1"
        },
        "yg":{
            "check":"icon-xuanzhong1",
            "uncheck":"icon-weixuanzhong1"
        },
        "fx":{
            "check":"icon-CombinedShapeCopy",
            "uncheck":"icon-weixuanzhong"
        },
        "fg":{
            "check":"icon-xuanzhong3",
            "uncheck":"icon-weixuanzhong"
        }
    },
    "initWithUI":function(opt){
        var appendStr ="";
        appendStr +="<div class='selectShow'>";
        appendStr +="<ul>";
        appendStr +="<li class='cleanItem'><i class='iconfont icon-qingchu'></i></li>";
        appendStr +="</ul>";
        appendStr +="</div>";
        appendStr +="<div class='selectList'>";
        appendStr +="<ul>";
        appendStr +="</ul>";
        appendStr +="</div>";
        $("#"+opt.mid).html(appendStr);
        var selectData = opt.selectData;
        $.each(selectData,function (i,itemObj) {
            $("#"+opt.mid+" .selectList ul").append("<li code='"+itemObj.colId+"' showStr='"+itemObj.colValue+"'><i class='iconfont "+multiselectTools.checkStyleArray[opt.checkStyle].uncheck+"'></i>"+itemObj.colValue+"</li>");
        });
        if(opt.isSelectData!=""){
            $.each(opt.isSelectData,function(i,itemObj){
                multiselectTools.addChecked(itemObj.colId,itemObj.colValue,opt);
                multiselectTools.toChecked(opt,itemObj.colId);
            });
            multiselectTools.setValueInput(opt.mid,opt.inputId);
        }
    },
    /**
     * 让属性value=value的选择项处于选择状态
     * @param opt
     * @param value
     */
    "toChecked":function(opt,value){
        var selectObj = $("#"+opt.mid+" .selectList ul li[code='"+value+"']");
        selectObj.attr("isCheck","true");
        selectObj.find("i").attr("class","iconfont "+multiselectTools.checkStyleArray[opt.checkStyle].check);
    },
    /**
     * 让属性value=value的选择项处于未选择状态
     * @param opt
     * @param value
     */
    "toUnChecked":function(opt,value){
        var selectObj = $("#"+opt.mid+" .selectList ul li[code='"+value+"']");
        selectObj.removeAttr("isCheck");
        selectObj.find("i").attr("class","iconfont "+multiselectTools.checkStyleArray[opt.checkStyle].uncheck);
    },
    /**
     * 获取初始参数
     * @param msId
     */
    "getInitOption":function(mid){
        var option =  {
            "mid":mid,//容器id
            "isSelectData":"",//已经选择的项
            "selectData":"",//选择项数据
            "isMulti":true,//是否多选
            "inputId":"",//输入框的id
            "checkStyle":"fg"//选择的样式
        };
        return option;
    },
    /**
     * 添加已选择项
     * @param value 值
     * @param showStr 值对应的显示参数
     */
    "addChecked":function(value,showStr,opt){
        $("#"+opt.mid+" .selectShow ul").append("<li class='selectItem' code='"+value+"'>"+showStr+"<i class='iconfont icon-shanchu'></i></li>");
//    	 $("#"+opt.mid+" .selectShow ul").append("<li class='selectItem' code='"+value+"'>"+showStr+"shan'chu</li>");
        multiselectEvent.initDeleteEvent(opt);
    },
    /**
     * 移除已选择项
     * @param value
     */
    "removeChecked":function(value,opt){
        $("#"+opt.mid+" .selectShow ul .selectItem[code='"+value+"']").remove();

    },
    /**
     * 获取选中的值
     * @param mid 容器ID
     * @returns {string}
     */
    "getSelectValue":function(mid){
        var str="";
        var checkedObjs =  $("#"+mid+"  .selectList ul li[isCheck='true']");
        $.each(checkedObjs,function (i,itemObj) {
            str+=$(itemObj).attr("code")+",";
        });
        str  = str.substr(0,str.length-1);
        return str;
    },
    /**
     * 设置input的值
     * @param mid 容器的id
     * @param inputId input的ID
     */
    "setValueInput":function(mid,inputId){
        var str = multiselectTools.getSelectValue(mid);
        if(inputId!=""&&multiselectTools.fondExitById(inputId)) {
            $("#" + inputId).val(str);
        }
    },
    /**
     * 清楚所有数据
     * @param mid  容器ID值
     * @param inputId  inputID值
     */
    "cleanAll":function(opt) {
        $("#"+opt.mid+" .selectShow ul .selectItem").remove();
        var objs = $("#" + opt.mid + " .selectList ul li");
        objs.removeAttr("isCheck");
        objs.find("i").attr("class","iconfont "+multiselectTools.checkStyleArray[opt.checkStyle].uncheck);
        if(opt.inputId!=""&&multiselectTools.fondExitById(opt.inputId)){
            $("#"+opt.inputId).val("");
        }
    },
    "fondExitById":function(idStr){
        return  $("#"+idStr).length>0;
    }

};

var multiselectEvent ={
    /**
     * 初始化点击事件
     */
    "initClickEvent":function(opt){
       $("#"+opt.mid+" .selectList ul li").mousedown(function () {

           var isCheck = $(this).attr("isCheck");
           var  value=$(this).attr("code");
           var showStr = $(this).attr("showStr");
           if(isCheck){
               multiselectTools.toUnChecked(opt,value);
               multiselectTools.removeChecked(value,opt);
           }else{
               if(!opt.isMulti){
                   multiselectTools.cleanAll(opt);
               }
               multiselectTools.toChecked(opt,value);
               multiselectTools.addChecked(value,showStr,opt);
           }
           multiselectTools.setValueInput(opt.mid,opt.inputId);
       });
    },
    /**
     * 初始化清除事件
     */
    "initCleanEvent":function(opt){
        $("#"+opt.mid+" .selectShow ul .cleanItem").mousedown(function(){
            multiselectTools.cleanAll(opt);
        });
    },
    /**
     * 初始化删除事件
     */
    "initDeleteEvent":function(opt){
        $("#"+opt.mid+" .selectShow ul .selectItem i").mousedown(function(){
            var deleteObj = $(this).parent();
            var code = deleteObj.attr("code");
            deleteObj.remove();
            if(code!=null){
                multiselectTools.toUnChecked(opt,code);
            }
            multiselectTools.setValueInput(opt.mid,opt.inputId);
        });

    }
};