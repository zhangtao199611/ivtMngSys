package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.util.Date;

//部门
@Data
public class DeptInfo {
	private String deptID;//部门编号
	private String deptName;//部门名称
	private Date established;//部门成立时间
	private String  deptExplain;//部门说明
	private int deptStatus;//部门状态
	private String establishedStr;//部门成立时间字符串

	private int sort;//排序
}
