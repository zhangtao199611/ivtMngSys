package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StockDetail;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface StockDetailDao {
    /**
     * @sql说明：添加交易明细
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    @Insert("insert into stock_detail(stockDetailID,stockBatchID,stockID,stockDetailType,weight,operationTime,stockDate,curBatchNo,stockKeeper,operator,remarks,photoImg,devID,adjustWeight,weighingWeight,status,unitID,print)values(#{stockDetailID},#{stockBatchID},#{stockID},#{stockDetailType},#{weight},#{operationTime},#{stockDate},#{curBatchNo},#{stockKeeper},#{operator},#{remarks},#{photoImg},#{devID},#{adjustWeight},#{weighingWeight},#{status},#{unitID},#{print})")
    int addStockDetail(StockDetail stockDetail);
    /**
     * @sql说明：修改交易明细
     * @开发时间：2021-12-27
     * @开发人员：张涛
     * */
    @Update("update stock_detail set stockBatchID=#{stockBatchID},curBatchNo=#{curBatchNo},stockID=#{stockID},stockDetailType=#{stockDetailType},weight=#{weight},operationTime=#{operationTime},stockDate=#{stockDate},stockKeeper=#{stockKeeper},operator=#{operator},remarks=#{remarks},photoImg=#{photoImg},devID=#{devID},status=#{status},adjustWeight=#{adjustWeight},weighingWeight=#{weighingWeight},print=#{print} where stockDetailID=#{stockDetailID}")
    int updateStockDetail(StockDetail stockDetail);
    /**
     * @sql说明：根据库存编号和出入损状态查询数据
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and status!=0 and stockDetailType=#{stockDetailType} order by operationTime desc limit #{pageModel.start},#{pageModel.pageSize}")
    List<StockDetail> listStockDetailByStockIDAndStockDetailType(String stockID,String stockDetailType,PageModel pageModel);
    /**
     * @sql说明：查询出入损状态查询数据
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockDetailType=#{stockDetailType} and status!=0 order by operationTime desc limit #{pageModel.start},#{pageModel.pageSize}")
    List<StockDetail> listStockDetailByStockDetailType(int stockDetailType,PageModel pageModel);
    /**
     * @sql说明：查询出入损状态查询数据数量
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_detail where stockDetailType=#{stockDetailType} and status!=0 order by operationTime desc")
    int getStockDetailCountByStockDetailType(int stockDetailType);
    /**
     * @sql说明：查询当日库明细
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_detail where stockDetailType=#{stockDetailType} and stockDate=#{stockDate} and status=#{status}")
    int listStockDetailStockTypeAndStockDateAndStatusCount(String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：根据库存编号和出入损状态查询数量
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_detail where stockID=#{stockID} and stockDetailType=#{stockDetailType} and status!=0")
    int getStockDetailCount(String stockID,int stockDetailType);
    /**
     * @sql说明：查询入库最近一次批次号并且
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and stockDetailType=#{stockDetailType} and curBatchNo!=0 order by operationTime desc")
    public List<StockDetail> listStockDetailByStockIDAndStockDetailTypeOne(String stockID,String stockDetailType);
    /**
     * @sql说明：根据明细编号查询数据
     * @开发时间：2021-12-31
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockDetailID=#{stockDetailID}")
    public StockDetail findStockDetailByStockDetailID(String stockDetailID);
    /**
     * @sql说明：根据库存编号和当天日期查询所有批次号
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and stockDate=#{stockDate} order by operationTime desc")
    List<StockDetail> listStockDetailByStockIDStockDate(String stockID,String stockDate);
    /**
     * @sql说明：查询当日的出入库预库存明细
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockDetailType=#{stockDetailType} and stockDate=#{stockDate} order by operationTime desc")// -- and status=#{status}
    List<StockDetail> listStockDetailByStockDetailTypeAndStockDateAndStatus(String stockDetailType,String stockDate);
    /**
     * @sql说明：查询当日的入库库存明细数量
     * @开发时间：2021-1-4
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_detail where stockDetailType=#{stockDetailType} and stockDate=#{stockDate} and status=#{status}")
    int listTheDayStockDetail(String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：分页查询当日的入库库存明细
     * @开发时间：2021-1-4
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockDetailType=#{stockDetailType} and stockDate=#{stockDate} and status=#{status} order by operationTime desc limit #{pageModel.start},#{pageModel.pageSize}")
    List<StockDetail> listStockDetailPageByStockDateAndStatus(String stockDetailType,String stockDate,String status,PageModel pageModel);
    /**
     * @sql说明：根据库存编号、库存明细类型、当日日期、明细状态查询
     * @开发时间：2021-1-6
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and stockDetailType=#{stockDetailType} and stockDate=#{stockDate} and status=#{status}  order by operationTime desc")
    List<StockDetail> listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(String stockID,String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：根据编号删除明细记录
     * @开发时间：2021-1-7
     * @开发人员：张涛
     * */
    @Delete("delete from stock_detail where stockDetailID=#{stockDetailID}")
    int deleteStockDetailByStockDetailID(String stockDetailID);
    /**
     * @sql说明：判断预出库库存是否大于实际库存
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and stockDetailType=#{stockDetailType} and stockDate=#{stockDate} and status=#{status}  order by operationTime desc")
    List<StockDetail> listIfStockDetailByIDAndTypeAndDateAndStatus(String stockID,String stockDetailType,String stockDate,String status);
    /**
     * @sql说明：获取七天之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT * FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= operationTime and status=1 order by operationTime desc")
    List<StockDetail> listStockDetailByAnd7();
    /**
     * @sql说明：获取七天之类的数进行分页
     * @开发时间：2021-1-16
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= operationTime")
    int listStockDetailByAnd7Page();
    /**
     * @sql说明：获取七天之类的数字
     * @开发时间：2021-1-10
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= operationTime and stockDetailType=#{stockDetailType} and stockID=#{stockID} and status=1")
    int listStockDetailByAnd7Count(int stockDetailType,String stockID);
    /**
     * @sql说明：获取月之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT * FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= operationTime and status=1 order by operationTime desc")
    List<StockDetail> listStockDetailByAndMonth();
    /**
     * @sql说明：获取月之类的数分页
     * @开发时间：2021-1-16
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= operationTime  order by operationTime desc")
    int listStockDetailByAndMonthPage();
    /**
     * @sql说明：获取月之类的数
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= operationTime and stockDetailType=#{stockDetailType} and stockID=#{stockID} and status=1")
    int listStockDetailByAndMonthCount(int stockDetailType,String stockID);
    /**
     * @sql说明：获取年之类的数据
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT * FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 365 DAY) <= operationTime and status=1 order by operationTime desc")
    List<StockDetail> listStockDetailByAndYear();
    /**
     * @sql说明：获取年之类的数分页
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 365 DAY) <= operationTime order by operationTime desc ")
    int listStockDetailByAndYearCountPage();
    /**
     * @sql说明：获取年之类的数
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT count(*) FROM stock_detail where DATE_SUB(CURDATE(), INTERVAL 365 DAY) <= operationTime and stockDetailType=#{stockDetailType} and stockID=#{stockID} and status=1")
    int listStockDetailByAndYearCount(int stockDetailType,String stockID);

    /**
     * @sql说明：根据编号查询总数之和
     * @开发时间：2021-1-9
     * @开发人员：张涛
     * */
    @Select("SELECT SUM(weight) AS weight FROM stock_detail where stockID=#{stockID} and stockDate=#{stockDate} group by stockID")
    BigDecimal sumStockDetailByWeight(String stockID,String stockDate);
    /**
     * @sql说明：根据编号和日期查询数据
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockID=#{stockID} and stockDate=#{stockDate} and status=1 order by operationTime desc")
    List<StockDetail> listStockDetailByStockIDAndStockDate(String stockID,String stockDate);

    /**
     * @sql说明：查询已打印未打印的结果
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    @Select("SELECT SUM(weight) AS weight FROM stock_detail where stockID=#{stockID} and stockDate=#{stockDate} and print=#{print} group by stockID")
    BigDecimal sumStockDetailByWeightAndPrint(String stockID,String stockDate,String print);

    /**
     * @sql说明：开始时间到结束时间
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    @Select("select * from stock_detail where stockDate>=#{start} and stockDate<=#{end} and status=1 order by operationTime desc")
    List<StockDetail> listStockDetailByStartAndEnd(String start,String end);
    /**
     * @sql说明：开始时间到结束时间数量
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */
    @Select("select count(*) from stock_detail where stockDate>=#{start} and stockDate<=#{end} and status=1 order by operationTime desc")
    int listCountStockDetailByStartAndEnd(String start,String end);
    /**
     * @sql说明：开始时间到结束时间数量
     * @开发时间：2021-1-15
     * @开发人员：张涛
     * */


}