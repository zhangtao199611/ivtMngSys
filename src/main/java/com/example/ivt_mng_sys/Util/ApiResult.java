package com.example.ivt_mng_sys.Util;

import lombok.Data;

/**
 * @类说明：用于ajax和axios操作返回数据给前端
 * @开发时间：2021-12-15
 * @开发人员：王涛
 * */

@Data
public class ApiResult {
    private Object resultData;//返回的数据
    private int result;//操作成功与否，500：不成功，200：成功
    private String msg;//消息
    private PageModel pageModel;//分页信息
}
