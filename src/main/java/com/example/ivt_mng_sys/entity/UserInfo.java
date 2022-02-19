package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
	private String userID;//用户唯一编号
	private String memberID;//人员ID
	private String loginName;//登录名
	private String loginPassWord;//密码
	private Date addTime;//添加日期
	private Date updateTime;//修改日期
	private String adder;//添加者
	private String updater;//修改者
	private Date latestLoginTime;//最近登录时间
	private String latestLoginDevID;//最近登录设备
	private int lockStatus;//锁：0正常，1冻结
	private String permission;//权限编制位

	private String latestLoginTimeStr;//最近登录时间字符
	private String memberName;//用户名称
	private String addTimeStr;//添加时间字符串
	private String updateTimeStr;//添加时间字符串
}
