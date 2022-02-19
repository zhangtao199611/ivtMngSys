package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.PriceUpdateDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PriceUpdateDetailService {

    /**
     * @sql说明：添加一条价格吸怪明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    int addPriceUpdateDetail(PriceUpdateDetail priceUpdateDetail);

    /**
     * @sql说明： 查找菜品所有价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    List<PriceUpdateDetail> listPriceUpdateDetailByProdID(String prodID);

    /**
     * @sql说明： 分页查找菜品价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    List<PriceUpdateDetail> listPriceUpdateDetailByProdIDByPage(String prodID, PageModel currenPage);


    /**
     * @sql说明： 查找菜品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    int getPriceUpdateDetailCount();

    /**
     * @sql说明： 查找某菜品价格修改明细数量
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    int getPriceUpdateDetailCountByProdID(String prodID);


    /**
     * @sql说明： 查找某菜品最新价格修改明细
     * @开发时间：2021-1-25
     * @开发人员： 王涛
     */
    PriceUpdateDetail findLatestDetailByProdID(String prodID);
}
