package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
public class SigningInfo {
    private String signingID;//签单拍照编号
    private String sigImg;//签单拍照图片
    private String adder;//添加人员
    private Date addDate;//添加日期
    private String unitID;//归属单位
    private String remarks;//备注
    private Date addTime;//添加时间

    private File sigImgFile;//照片文件
    private String addTimeStr;//添加时间

}
