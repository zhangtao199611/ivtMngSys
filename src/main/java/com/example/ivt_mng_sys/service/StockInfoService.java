package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StockInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StockInfoService {
    /**
     * @功能说明：根据stockInfoID字段查询数据
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    public StockInfo findStockInfoByProdID(String prodID);
    /**
     * @功能说明：添加新库存
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    int addStockInfo(StockInfo stockInfo);
    /**
     * @功能说明：修改库存
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    int updateStockInfo(StockInfo stockInfo);
    /**
     * @功能说明：根据编号查询
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    StockInfo findStockInfoByStockID(String stockID);
    /**
     * @功能说明：查询所有库存
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    List<StockInfo> listStockInfo();
    /**
     * @功能说明：查询所有库存数量
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    int getStockInfoCount();
    /**
     * @功能说明：分页查询所有库存数量
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    List<StockInfo> listStockInfoByPage(PageModel pageModel);
    /**
     * @sql说明：查询所有已有库存数据
     * @开发时间：2021-1-3
     * @开发人员：张涛
     * */
    List<StockInfo> listStockInfoByStockCountNEQ0();




}
