package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.ProductInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductInfoService {
    /**
     * @sql说明：添加产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    public boolean addProductInfo(ProductInfo productInfo);

    /**
     * @sql说明：修改产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Transactional
    public boolean updateproductInfo(ProductInfo productInfo);

    /**
     * @sql说明：删除产品
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public boolean deleteProductByID(String prodID);


    /**
     * @sql说明：恢复类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public boolean recoverProductByID(String prodID);


    /**
     * @sql说明：分页查询数据（产品），按添加时间倒排
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public List<ProductInfo> listProductInfoByPage(PageModel pageModel);


    /**
     * @sql说明：获取产品数量`
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public int getProductCount();


    /**
     * @sql说明：根据编号查找产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    public ProductInfo findProductById(String prodID);


    /**
     * @sql说明：获取产品详情
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    public ProductInfo getProductInfoDetail(ProductInfo productInfo);


    /**
     * @sql说明：根据classID来查找产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    public List<ProductInfo> findProductInfoByClassID(String classID);


    /**
     * @sql说明：根据编号查找标品产品
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    public List<ProductInfo> findStandardProductByClassID(String classID);


    /**
     * @sql说明：根据编号查找非标品产品
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    public List<ProductInfo> findUnStandardProductByClassID(String classID);

    /**
     * @sql说明：模糊查询
     * @开发时间：2021-01-12
     * @开发人员：张涛
     */
    List<ProductInfo> listProductInfoByLikeSpellingName(String spellingName);

}
