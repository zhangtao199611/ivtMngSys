package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProductInfo {
	private String prodID;
	private String prodName;
	private String classID;
	private String prodImgUrl;
	private BigDecimal prodPrice;
	private String description;
	private int expirationTime;//保质期（天）
	private int standardStatus;//0：标品。1：非标品
	private Date addTime;
	private Date updateTime;
	private String adder;
	private String updater;
	private int status;//产品状态（0：正常，1：停用）
	private String prodUnit;
	private String spellingName;//产品名称拼音


	//----------------以下非原生字段，是业务展示等额外加上的字段--------------------
	private String className;//类别名
	private String adderName;//添加者名字
	private String addTimeStr;//添加时间文本
	private String updateTimeStr;//修改时间文本
	private String updaterName;//修改者名字
	private File prodImgFile;//图片文件

	private String stockID;//库存编号
	private BigDecimal stockCount;//库存量

	private String priceUpdateTimeStr;//最新价格修改时间


	private BigDecimal bePutInStorage;//入库总数
	private BigDecimal totalPrices;//总价
	private String print;//打印状态

	private Date latestTime;//明细最后入库时间
	private String latestTimeStr;//明细最后入库时间字符串




}
