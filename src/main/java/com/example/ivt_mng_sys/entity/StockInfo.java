package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class StockInfo {

	private String stockID;//库存编号(产品编号+时间)
	private String prodID;//产品名称
	private Date storageDate;//入库时间
	private String storageDateStr;//入库时间字符串
	private Date updateDate;//更新时间
	private String updateDateStr;//更新时间
	private String updater;//更新人编号
	private BigDecimal stockCount;////总库存
 	private String unit;////产品单位
	private int batchCount;////批次数
	private Date latestInTime;////最近入库时间
	private String latestInTimeStr;////最近入库时间
	private Date latestOutTime;////最近出库时间
	private String latestOutTimeStr;////最近出库时间

	private String theProduct;//区分标品/非标品

	private String integer;//整数

	private int joinCount;//累计入库数量
	private int outCount;//累计出库数量

	private BigDecimal theTotalInventory;//总库存
}
