package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MemberInfo {
	private String memberID;//人员ID
	private String userID;//用户唯一编号
	private String deptID;//部门编号
	private String stationIDs;//人员岗位（1到多个-考虑到身兼多职的情况）
	private String memberName;//人员姓名
	private String jobID;//工号
	private String phoneNumber;//手机号码
	private int status;//状态
	private Date addTime;//添加时间
	private String adder;//添加人
	private String IDCard;//身份证号码
	private Date updateTime;//修改人
	private String deptIDName;//部门名称
	private String currentPage;//页码
	private String unitID;//单位名称编号

	private UserInfo userInfo;


}
