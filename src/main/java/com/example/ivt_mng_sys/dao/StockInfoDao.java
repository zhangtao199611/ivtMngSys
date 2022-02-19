package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StockInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StockInfoDao {
    /**
     * @sql说明：根据stockInfoID字段查询数据
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    @Select("select * from stock_info where prodID=#{prodID}")
    public StockInfo findStockInfoByProdID(String prodID);
    /**
     * @sql说明：添加新库存
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    @Insert("insert into stock_info (stockID,prodID,storageDate,updateDate,updater,stockCount,unit,batchCount,latestInTime,latestOutTime)values(#{stockID},#{prodID},#{storageDate},#{updateDate},#{updater},#{stockCount},#{unit},#{batchCount},#{latestInTime},#{latestOutTime})")
    int addStockInfo(StockInfo stockInfo);
    /**
     * @sql说明：修改库存
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @Update("update stock_info set prodID=#{prodID},storageDate=#{storageDate},updateDate=#{updateDate},updater=#{updater},stockCount=#{stockCount},unit=#{unit},batchCount=#{batchCount},latestInTime=#{latestInTime},latestOutTime=#{latestOutTime} where stockID=#{stockID}")
    int updateStockInfo(StockInfo stockInfo);
    /**
     * @sql说明：根据编号查询
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @Select("select * from stock_info where stockID=#{stockID}")
    StockInfo findStockInfoByStockID(String stockID);
    /**
     * @sql说明：查询所有数据
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select * from stock_info")
    List<StockInfo> listStockInfo();
    /**
     * @sql说明：查询所有数据记录数
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_info")
    int getStockInfoCount();

    /**
     * @sql说明：分页查询所有数据
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select * from stock_info order by updateDate desc limit #{start},#{pageSize}")
    List<StockInfo> listStockInfoByPage(PageModel pageModel);
    /**
     * @sql说明：查询所有已有库存数据
     * @开发时间：2021-1-3
     * @开发人员：张涛
     * */
    @Select("select * from stock_info where stockCount!=0.000 || stockCount!=null")
    List<StockInfo> listStockInfoByStockCountNEQ0();
}