package com.example.ivt_mng_sys.entity;

import lombok.Data;

@Data
public class SignInfo {

		private String signId;//标牌编号
		private String stockBatchId;//库存批次号
		private int signStatus;  //标牌当前状态：0，未绑定，1：已绑定

		
	
	
	
}
