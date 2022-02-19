package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.entity.UnitInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UnitInfoDao {
    /**
     * @sql说明：查询全部单位
     * @开发时间：2022-01-12
     * @开发人员：张涛
     * */
    @Select("select * from unit_info")
    List<UnitInfo> listUnitInfo();
    /**
     * @sql说明：根据编号查询
     * @开发时间：2022-01-12
     * @开发人员：张涛
     * */
    @Select("select * from unit_info where unitID=#{unitID}")
    UnitInfo findUnitInfoByUnitID(String unitID);
    /**
     * @sql说明：修改单位
     * @开发时间：2022-01-12
     * @开发人员：张涛
     * */

    @Update("update unit_info set untitName=#{untitName},untitLoc=#{untitLoc} where unitID=#{unitID}")
    public int updateUntitName(UnitInfo unitInfo);

}
