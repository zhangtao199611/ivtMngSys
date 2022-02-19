package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.StockInfoDao;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.StockInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StockInfoServiceImpl implements StockInfoService {
    @Resource
    private StockInfoDao stockInfoDao;

    @Override
    public StockInfo findStockInfoByProdID(String prodID) {
        return stockInfoDao.findStockInfoByProdID(prodID);
    }

    @Override
    public int addStockInfo(StockInfo stockInfo) {
        stockInfo.setUpdateDate(new Date());
        return stockInfoDao.addStockInfo(stockInfo);
    }

    @Override
    public int updateStockInfo(StockInfo stockInfo) {
        stockInfo.setUpdateDate(new Date());
        return stockInfoDao.updateStockInfo(stockInfo);
    }

    @Override
    public StockInfo findStockInfoByStockID(String stockID) {
        return stockInfoDao.findStockInfoByStockID(stockID);
    }

    @Override
    public List<StockInfo> listStockInfo() {
        return stockInfoDao.listStockInfo();
    }

    @Override
    public int getStockInfoCount() {
        return stockInfoDao.getStockInfoCount();
    }

    @Override
    public List<StockInfo> listStockInfoByPage(PageModel pageModel) {
        return stockInfoDao.listStockInfoByPage(pageModel);
    }

    @Override
    public List<StockInfo> listStockInfoByStockCountNEQ0() {
        return stockInfoDao.listStockInfoByStockCountNEQ0();
    }
}
