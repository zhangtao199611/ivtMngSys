package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.ActionUtil;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.PriceUpdateDetailDao;
import com.example.ivt_mng_sys.entity.PriceUpdateDetail;
import com.example.ivt_mng_sys.service.PriceUpdateDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PriceUpdateDetailServiceImpl implements PriceUpdateDetailService {
    @Resource
    PriceUpdateDetailDao priceUpdateDetailDao;

    /**
     * @sql说明：添加一条价格吸怪明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public int addPriceUpdateDetail(PriceUpdateDetail priceUpdateDetail) {
        return priceUpdateDetailDao.addPriceUpdateDetail(priceUpdateDetail);
    }

    /**
     * @sql说明： 查找产品所有价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public List<PriceUpdateDetail> listPriceUpdateDetailByProdID(String prodID) {
        return setUpdateTimeStr(priceUpdateDetailDao.listPriceUpdateDetailByProdID(prodID));
    }

    /**
     * @sql说明： 分页查找产品价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public List<PriceUpdateDetail> listPriceUpdateDetailByProdIDByPage(String prodID, PageModel pageModel) {
        int totalCount = priceUpdateDetailDao.getPriceUpdateDetailCountByProdID(prodID);
        return setUpdateTimeStr(priceUpdateDetailDao.listPriceUpdateDetailByProdIDByPage(prodID, pageModel.getStart(), pageModel.getPageSize()));
    }

    /**
     * @sql说明： 查找产品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public int getPriceUpdateDetailCount() {
        return priceUpdateDetailDao.getPriceUpdateDetailCount();
    }

    /**
     * @sql说明： 查找某产品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public int getPriceUpdateDetailCountByProdID(String prodID) {
        System.out.println("查看产品 " + prodID + "的价格明细数量");
        int count = priceUpdateDetailDao.getPriceUpdateDetailCountByProdID(prodID);
        System.out.println("产品 " + prodID + "的明细数量=" + count);
        return count;
    }

    /**
     * @sql说明： 查找某菜品最新价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    @Override
    public PriceUpdateDetail findLatestDetailByProdID(String prodID) {
        return priceUpdateDetailDao.findLatestDetailByProdID(prodID);
    }

    private List<PriceUpdateDetail> setUpdateTimeStr(List<PriceUpdateDetail> priceUpdateDetails) {
        for (PriceUpdateDetail detail : priceUpdateDetails) {
            detail.setUpdateTimeStr(ActionUtil.getDateStr(detail.getUpdateTime()));
        }
        return priceUpdateDetails;
    }


}
