package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StationInfo {
    private String stationID;//岗位唯一编号
    private String stationName;//岗位名称
    private String deptID;//部门编号
    private String authorityIDs;//权限列表
    private Date addTime;//添加时间
    private String addTimeStr;//添加时间字符串
    private int  stationStatus;//权限状态
}
