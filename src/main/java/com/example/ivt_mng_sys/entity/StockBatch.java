package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
@Data
public class StockBatch {
	private String stockBatchId;
	private String stockId;
	private String prodId;
	private Date storageDate;
	private Date updateDate;
	private String warehouser;//入库人编号
	private String updater;//更新人编号
	private String unit;//单人
	private int batchCount;//数量
	private Date expirationDate;//货物到期时间
	private BigDecimal batchQuantity;//当前批次数量
	private ArrayList<String> signIds;//批次标牌集合（多袋的情况）
	
	

}
