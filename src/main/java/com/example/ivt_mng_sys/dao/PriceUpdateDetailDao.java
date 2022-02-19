package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.MenuInfo;
import com.example.ivt_mng_sys.entity.PriceUpdateDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * priceUpdateID
 * prodID
 * updateTime
 * updater
 */
@Mapper
public interface PriceUpdateDetailDao {
    /**
     * @sql说明：添加一条价格吸怪明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Insert("insert into price_update_detail (priceUpdateID,prodID,updateTime,updateDate,updater,latestPrice,prodUnit) value(#{priceUpdateID},#{prodID},#{updateTime},#{updateDate},#{updater},#{latestPrice},#{prodUnit})")
    int addPriceUpdateDetail(PriceUpdateDetail priceUpdateDetail);

    /**
     * @sql说明： 查找菜品所有价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Select("select * from price_update_detail where prodID = #{prodID}")
    List<PriceUpdateDetail> listPriceUpdateDetailByProdID(String prodID);

    /**
     * @sql说明： 查找菜品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Select("select count(*) from price_update_detail where prodID = #{prodID}")
    int getPriceUpdateDetailCount();

    /**
     * @sql说明： 分页查找菜品价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Select("select * from price_update_detail where prodID = #{prodID} limit #{start},#{pageSize} ")
    List<PriceUpdateDetail> listPriceUpdateDetailByProdIDByPage(String prodID, int start, int pageSize);


    /**
     * @sql说明： 查找某菜品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Select("select count(*) from price_update_detail where prodID = #{prodID}")
    int getPriceUpdateDetailCountByProdID(String prodID);

    /**
     * @sql说明： 查找某菜品最新价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Select("select * from price_update_detail where prodID = #{prodID} order by updateTime desc limit 1")
    PriceUpdateDetail findLatestDetailByProdID(String prodID);


}
