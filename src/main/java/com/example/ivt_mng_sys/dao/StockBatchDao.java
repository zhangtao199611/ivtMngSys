package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.entity.StockBatch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockBatchDao {
    /**
     * @sql说明：添加批次编号
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    @Insert("insert into stock_detail(stockBatchID,stockID,prodID,storageDate,updateDate,warehouser,updater,unit,expirationDate,batchQuantity,signIDs)values(#{stockBatchID},#{stockID},#{prodID},#{storageDate},#{updateDate},#{warehouser},#{updater},#{unit},#{expirationDate},#{batchQuantity},#{signIDs})")
    int addStockDetail(StockBatch stockBatch);
}
