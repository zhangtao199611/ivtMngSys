package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.dao.StockBatchDao;
import com.example.ivt_mng_sys.entity.StockBatch;
import com.example.ivt_mng_sys.service.StockBatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockBatchServiceImpl implements StockBatchService {
    @Resource
    private StockBatchDao stockBatchDao;
    @Override
    public int addStockDetail(StockBatch stockBatch) {
        return stockBatchDao.addStockDetail(stockBatch);
    }
}
