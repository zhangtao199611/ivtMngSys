package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.entity.UnitInfo;

import java.util.List;

public interface UnitInfoService {
    /**
     * @sql说明：查询全部单位
     * @开发时间：2022-01-12
     * @开发人员：张涛
     * */
    List<UnitInfo> listUnitInfo();
    /**
     * @sql说明：根据编号查询
     * @开发时间：2022-01-12
     * @开发人员：张涛
     * */
    UnitInfo findUnitInfoByUnitID(String unitID);

    public int updateUntitName(UnitInfo unitInfo);


}
