package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.MemberInfoDao;
import com.example.ivt_mng_sys.dao.StockDetailDao;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.StockDetailService;
import com.example.ivt_mng_sys.service.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class StockDetailServiceImpl implements StockDetailService {
    @Resource
    private StockDetailDao stockDetailDao;
    @Resource
    private MemberInfoDao memberInfoDao;
    /**
     * 读取conf.yml配置文件
     * */
    private AllConfiguration allConfiguration = new AllConfiguration();
    @Autowired
    public StockDetailServiceImpl(AllConfiguration allConfiguration){this.allConfiguration = allConfiguration;}
    @Override
    public int addStockDetail(StockDetail stockDetail) {
        stockDetail.setPrint(allConfiguration.getPrintStatusNO());
        String operator = stockDetail.getOperator();//出入库人
        MemberInfo memberInfoByJobID = memberInfoDao.findMemberInfoByJobID(operator);
        stockDetail.setUnitID(memberInfoByJobID.getUnitID());//归属单位
        System.out.println("service入库明细状态"+stockDetail.getStatus());
        return stockDetailDao.addStockDetail(stockDetail);
    }

    @Override
    public boolean updateStockDetail(StockDetail stockDetail) {
        System.out.println("service修改>>"+stockDetail.getStatus());
        int i = stockDetailDao.updateStockDetail(stockDetail);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<StockDetail> listStockDetailByStockIDAndStockDetailType(String stockID, String stockDetailType, PageModel pageModel) {
        return stockDetailDao.listStockDetailByStockIDAndStockDetailType(stockID,stockDetailType,pageModel);
    }

    @Override
    public int getStockDetailCount(String stockID, int stockDetailType) {
        return stockDetailDao.getStockDetailCount(stockID,stockDetailType);
    }

    @Override
    public List<StockDetail> listStockDetailByStockIDAndStockDetailTypeOne(String stockID, String stockDetailType) {
        return stockDetailDao.listStockDetailByStockIDAndStockDetailTypeOne(stockID,stockDetailType);
    }

    @Override
    public StockDetail findStockDetailByStockDetailID(String stockDetailID) {
        return stockDetailDao.findStockDetailByStockDetailID(stockDetailID);
    }

    @Override
    public List<StockDetail> listStockDetailByStockDetailType(int stockDetailType, PageModel pageModel) {
        return stockDetailDao.listStockDetailByStockDetailType(stockDetailType,pageModel);
    }

    @Override
    public int getStockDetailCountByStockDetailType(int stockDetailType) {
        return stockDetailDao.getStockDetailCountByStockDetailType(stockDetailType);
    }

    @Override
    public List<StockDetail> listStockDetailByStockIDStockDate(String stockID, String stockDate) {
        return stockDetailDao.listStockDetailByStockIDStockDate(stockID,stockDate);
    }

    @Override
    public List<StockDetail> listStockDetailByStockDetailTypeAndStockDateAndStatus(String stockDetailType, String stockDate) {
        return stockDetailDao.listStockDetailByStockDetailTypeAndStockDateAndStatus(stockDetailType,stockDate);
    }

    @Override
    public int listTheDayStockDetail(String stockDetailType,String stockDate, String status) {
        return stockDetailDao.listTheDayStockDetail(stockDetailType,stockDate,status);
    }

    @Override
    public List<StockDetail> listStockDetailPageByStockDateAndStatus(String stockDetailType,String stockDate, String status, PageModel pageModel) {
        return stockDetailDao.listStockDetailPageByStockDateAndStatus(stockDetailType,stockDate,status,pageModel);
    }

    @Override
    public List<StockDetail> listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(String stockID, String stockDetailType, String stockDate, String status) {
        return stockDetailDao.listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(stockID,stockDetailType,stockDate,status);
    }

    @Override
    public int deleteStockDetailByStockDetailID(String stockDetailID) {
        return stockDetailDao.deleteStockDetailByStockDetailID(stockDetailID);
    }

    @Override
    public List<StockDetail> listIfStockDetailByIDAndTypeAndDateAndStatus(String stockID, String stockDetailType, String stockDate, String status) {
        return stockDetailDao.listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(stockID,stockDetailType,stockDate,status);
    }

    @Override
    public int listStockDetailStockTypeAndStockDateAndStatusCount(String stockDetailType, String stockDate, String status) {
        return stockDetailDao.listStockDetailStockTypeAndStockDateAndStatusCount(stockDetailType,stockDate,status);
    }

    @Override
    public List<StockDetail> listStockDetailByAndweek() {
        return stockDetailDao.listStockDetailByAnd7();
    }

    @Override
    public int listStockDetailByAnd7Page() {
        return stockDetailDao.listStockDetailByAnd7Page();
    }

    @Override
    public int listStockDetailByAnd7Count(int stockDetailType,String stockID) {
        return stockDetailDao.listStockDetailByAnd7Count(stockDetailType,stockID);
    }

    @Override
    public List<StockDetail> listStockDetailByAndMonth() {
        return stockDetailDao.listStockDetailByAndMonth();
    }

    @Override
    public int listStockDetailByAndMonthPage() {
        return stockDetailDao.listStockDetailByAndMonthPage();
    }

    @Override
    public int listStockDetailByAndMonthCount(int stockDetailType,String stockID) {
        return stockDetailDao.listStockDetailByAndMonthCount(stockDetailType,stockID);
    }

    @Override
    public List<StockDetail> listStockDetailByAndYear() {
        return stockDetailDao.listStockDetailByAndYear();
    }

    @Override
    public int listStockDetailByAndYearCountPage() {
        return stockDetailDao.listStockDetailByAndYearCountPage();
    }

    @Override
    public int listStockDetailByAndYearCount(int stockDetailType,String stockID) {
        return stockDetailDao.listStockDetailByAndYearCount(stockDetailType,stockID);
    }

    @Override
    public BigDecimal sumStockDetailByWeight(String stockID,String stockDate) {
        return stockDetailDao.sumStockDetailByWeight(stockID,stockDate);
    }

    @Override
    public List<StockDetail> listStockDetailByStockIDAndStockDate(String stockID, String stockDate) {
        return stockDetailDao.listStockDetailByStockIDAndStockDate(stockID,stockDate);
    }

    @Override
    public BigDecimal sumStockDetailByWeightAndPrint(String stockID, String stockDate, String print) {
        return stockDetailDao.sumStockDetailByWeightAndPrint(stockID,stockDate,print);
    }

    @Override
    public List<StockDetail> listStockDetailByStartAndEnd(String start, String end) {
        return stockDetailDao.listStockDetailByStartAndEnd(start,end);
    }

    @Override
    public int listCountStockDetailByStartAndEnd(String start, String end) {
        return stockDetailDao.listCountStockDetailByStartAndEnd(start,end);
    }

}
