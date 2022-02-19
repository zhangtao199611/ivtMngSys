package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.io.File;

/**
 * 系统配置
 */
@Data
public class SystemConfig {
    private String sysName;//系统名称
    private String sysLogo;//系统
    private File sysLogoFile;//系统
    private String sysVersion;//版本信息号
    private String standardAppTitle;//标品app标题
    private String unStandardAppTitle;//非标品app标题

    private String untitName;//单位名称
    private String unitID;//单位编号
}
