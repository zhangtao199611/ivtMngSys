package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.ActionUtil;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.PriceUpdateDetailDao;
import com.example.ivt_mng_sys.dao.ProductInfoDao;
import com.example.ivt_mng_sys.entity.PriceUpdateDetail;
import com.example.ivt_mng_sys.entity.ProductInfo;
import com.example.ivt_mng_sys.service.ProductInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
    ProductInfoDao productInfoDao;

    @Resource
    PriceUpdateDetailDao priceUpdateDetailDao;

    /**
     * @sql说明：添加产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Override
    public boolean addProductInfo(ProductInfo productInfo) {
        return productInfoDao.addProductInfo(productInfo) > 0;
    }

    /**
     * @sql说明：修改产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Override
    @Transactional
    public boolean updateproductInfo(ProductInfo productInfo) {
        boolean f = false;
        ProductInfo originProductInfo = productInfoDao.findProductByID(productInfo.getProdID());
        System.out.println("修改前 ==>" + originProductInfo.toString());
        System.out.println("修改后 ==>" + productInfo.toString());
        if (willUpdatePrice(originProductInfo, productInfo)) {
            System.out.println("准备修改价格");

            //添加价格修改明细
            String PriceUpdateDetailID = productInfo.getProdID() + ActionUtil.getDateStrForStringwithNoLiine(new Date());
            PriceUpdateDetail priceUpdateDetail = new PriceUpdateDetail();
            priceUpdateDetail.setPriceUpdateID(PriceUpdateDetailID);
            priceUpdateDetail.setProdID(productInfo.getProdID());
            priceUpdateDetail.setUpdater(productInfo.getUpdater());
            priceUpdateDetail.setProdUnit(productInfo.getProdUnit());
            priceUpdateDetail.setLatestPrice(productInfo.getProdPrice());
            priceUpdateDetail.setUpdateDate(new Date());
            priceUpdateDetail.setUpdateTime(new Date());
            System.out.println(priceUpdateDetailDao.addPriceUpdateDetail(priceUpdateDetail) > 0 ? "价格修改明细添加成功" : "价格修改明细添加失败");
        }
        f = productInfoDao.updateProductInfo(productInfo) > 0;
        return f;
    }

    private boolean willUpdatePrice(ProductInfo originProd, ProductInfo editProd) {
        if (originProd.getProdPrice() == null && editProd.getProdPrice() != null) {
            return true;
        } else if ((originProd.getProdPrice() != null && editProd.getProdPrice() != null) && (originProd.getProdPrice().compareTo(editProd.getProdPrice()) != 0)) {
            return true;
        }
        return false;
    }

    /**
     * @sql说明：删除产品
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public boolean deleteProductByID(String prodID) {
        return productInfoDao.deleteProductByID(prodID) > 0;
    }

    /**
     * @sql说明：恢复类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public boolean recoverProductByID(String prodID) {
        return productInfoDao.recoverProductByID(prodID) > 0;
    }

    /**
     * @sql说明：分页查询数据（产品），按添加时间倒排
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public List<ProductInfo> listProductInfoByPage(PageModel pageModel) {
        return productInfoDao.listProductInfoByPage(pageModel);
    }

    /**
     * @sql说明：分页查询数据（产品），按添加时间倒排
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public List<ProductInfo> findProductInfoByClassID(String classID) {
        return productInfoDao.findProductByClassID(classID);
    }


    /**
     * @sql说明：获取大类数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    @Override
    public int getProductCount() {
        return productInfoDao.getProductCount();
    }


    /**
     * @sql说明：获取大类数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    @Override
    public ProductInfo findProductById(String prodID) {
        return productInfoDao.findProductByID(prodID);
    }


    /**
     * @sql说明：获取产品详情
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    @Override
    public ProductInfo getProductInfoDetail(ProductInfo productInfo) {
        System.out.println("1" + productInfo.toString());
        String adderName = productInfoDao.getAdderName(productInfo.getProdID());
        String updaterName = productInfoDao.getUpdaterName(productInfo.getProdID());
        productInfo.setUpdaterName(updaterName);
        productInfo.setAdderName(adderName);
        productInfo.setAddTimeStr(ActionUtil.getDateStr(productInfo.getAddTime()));
        productInfo.setUpdateTimeStr(ActionUtil.getDateStr(productInfo.getUpdateTime()));
        return productInfo;
    }

    /**
     * @sql说明：根据编号查找标品产品
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    @Override
    public List<ProductInfo> findStandardProductByClassID(String classID) {
        return productInfoDao.findProductByClassIDAndStandardStatus(classID, 0);
    }


    /**
     * @sql说明：根据编号查找非标品产品
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    @Override
    public List<ProductInfo> findUnStandardProductByClassID(String classID) {
        return productInfoDao.findProductByClassIDAndStandardStatus(classID, 1);
    }

    @Override
    public List<ProductInfo> listProductInfoByLikeSpellingName(String spellingName) {
        return productInfoDao.listProductInfoByLikeSpellingName(spellingName);
    }
}
