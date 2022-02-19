package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;

import java.math.BigDecimal;
import java.util.List;

public interface StockDetailService {
    /**
     * @功能说明：添加交易明细
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    int addStockDetail(StockDetail stockDetail);
    /**
     * @功能说明：修改交易明细
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    boolean updateStockDetail(StockDetail stockDetail);
    /**
     * @功能说明：根据库存编号和库存类型查询信息
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockIDAndStockDetailType(String stockID, String stockDetailType, PageModel pageModel);
    /**
     * @功能说明：根据库存编号和出入损状态查询数量
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    int getStockDetailCount(String stockID,int stockDetailType);
    /**
     * @功能说明：查询入库最近一次批次号
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    public List<StockDetail> listStockDetailByStockIDAndStockDetailTypeOne(String stockID,String stockDetailType);
    /**
     * @功能说明：根据明细编号查询数据
     * @开发时间：2021-12-31
     * @开发人员：张涛
     * */
    public StockDetail findStockDetailByStockDetailID(String stockDetailID);
    /**
     * @功能说明：查询出入损状态查询数据
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockDetailType(int stockDetailType,PageModel pageModel);
    /**
     * @功能说明：查询出入损状态查询数据数量
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    int getStockDetailCountByStockDetailType(int stockDetailType);
    /**
     * @功能说明：根据库存编号和当天日期查询所有批次号
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockIDStockDate(String stockID,String stockDate);
    /**
     * @功能说明：根据库存编号和当天日期查询所有批次号
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockDetailTypeAndStockDateAndStatus(String stockDetailType,String stockDate);
    /**
     * @功能说明：查询当日的入库库存明细
     * @开发时间：2021-1-4
     * @开发人员：张涛
     * */
    int listTheDayStockDetail(String stockDetailType,String stockDate,String status);
    /**
     * @功能说明：分页查询当日的入库库存明细
     * @开发时间：2021-1-4
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailPageByStockDateAndStatus(String stockDetailType,String stockDate,String status,PageModel pageModel);
    /**
     * @sql说明：根据库存编号、库存明细类型、当日日期、明细状态查询
     * @开发时间：2021-1-6
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(String stockID,String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：根据编号删除明细记录
     * @开发时间：2021-1-7
     * @开发人员：张涛
     * */
    int deleteStockDetailByStockDetailID(String stockDetailID);
    /**
     * @sql说明：判断预出库库存是否大于实际库存
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    List<StockDetail> listIfStockDetailByIDAndTypeAndDateAndStatus(String stockID,String stockDetailType,String stockDate,String status);
    /**
     * @功能说明：查询当日库明细
     * @开发时间：2021-1-10
     * @开发人员：张涛
     * */
    int listStockDetailStockTypeAndStockDateAndStatusCount(String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：获取天之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByAndweek();
    /**
     * @sql说明：获取七天之类的数进行分页
     * @开发时间：2021-1-16
     * @开发人员：张涛
     * */
    int listStockDetailByAnd7Page();
    /**
     * @sql说明：获取七天之类的数字
     * @开发时间：2021-1-10
     * @开发人员：张涛
     * */
    int listStockDetailByAnd7Count(int stockDetailType,String stockID);

    /**
     * @sql说明：获取月之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByAndMonth();
    /**
     * @sql说明：获取月之类的数分页
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    int listStockDetailByAndMonthPage();

    /**
     * @说明：获取月之类的数
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    int listStockDetailByAndMonthCount(int stockDetailType,String stockID);

    /**
     * @sql说明：获取年之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByAndYear();
    /**
     * @sql说明：获取年之类的数分页
     * @开发时间：2021-1-16
     * @开发人员：张涛
     * */
    int listStockDetailByAndYearCountPage();

    /**
     * @sql说明：获取年之类的数
     * @开发时间：2021-1-11
     * @开发人员：张涛
     * */
    int listStockDetailByAndYearCount(int stockDetailType,String stockID);
    /**
     * @sql说明：根据编号查询总数
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    BigDecimal sumStockDetailByWeight(String stockID,String stockDate);
    /**
     * @sql说明：根据编号和日期查询数据
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStockIDAndStockDate(String stockID,String stockDate);

    /**
     * @sql说明：查询已打印未打印的结果
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    BigDecimal sumStockDetailByWeightAndPrint(String stockID,String stockDate,String print);
    /**
     * @sql说明：开始时间到结束时间
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    List<StockDetail> listStockDetailByStartAndEnd(String start,String end);
    /**
     * @sql说明：开始时间到结束时间数量
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    int listCountStockDetailByStartAndEnd(String start,String end);













}
