package com.example.ivt_mng_sys.entity;

import lombok.Data;

@Data
public class DisClassInfo {
    private String disClassID;//菜品类别编号
    private String className;//菜品类别名称
    private int sortValue;//菜品类别排序值
    private int statuss;//类别状态（0：正常，1：停用）
    private int showStatus;//是否展示：1展示，0不展示
    private String explains;//类别说明

}
