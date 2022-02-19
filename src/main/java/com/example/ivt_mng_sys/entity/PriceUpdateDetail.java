package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PriceUpdateDetail {
    private String priceUpdateID;
    private String prodID;
    private Date updateTime;
    private String updater;
    private Date updateDate;
    private BigDecimal latestPrice;
    private String prodUnit;

    //------额外展示信息-----
    private String updaterName;
    private String updateTimeStr;
}
