package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class StockDetail {
	private String stockDetailID;//库存明细编号
	private String stockBatchID;//库存批次编号
	private String stockID;//库存编号
	private int stockDetailType;//0,出库，1，入库，2，损耗
	private BigDecimal weight;//出库/入库/损耗数量
	private Date operationTime;//出库/入库/损耗时间
	private String operationTimeStr;//出库/入库/损耗时间
	private String stockDate;//当天日期，当周期号使用，stockDate+curBatchNo=周期号
	private int curBatchNo;//批次号
	private String stockKeeper;//当事库管编号
	private String operator;//出/入库人
	private String remarks;//备注（选填）
	private String photoImg;//出入库照片
	private File photoImgFile;//产品图片文件
	private String devID;//出入库设备
	private int status;//明细状态（0：预存、1：生效）
	private BigDecimal adjustWeight;//调整重量
	private BigDecimal weighingWeight;//秤重重量
	private String unit;//重量
	private String unitID;//单位编号


	private String prodUnit;//产品单位
	private Integer print;//打印（1：已打印，null：未打印）

	private String theProduct;//标品
	private String integer;//标品数量
}