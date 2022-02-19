package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.SigningInfo;

import java.util.Date;
import java.util.List;

public interface SigningInfoService {
    /**
     * @功能说明：添加签单信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    public int addSigningInfo(SigningInfo signingInfo);
    /**
     * @功能说明：修改签单信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    boolean updateSigningInfo(SigningInfo signingInfo);
    /**
     * @功能说明：查询全部信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    List<SigningInfo> listSigningInfoPage(PageModel pageModel);
    /**
     * @功能说明：查询全部信息数量
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    int countSigningInfo();
    /**
     * @功能说明：根据签单编号查询数据
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    SigningInfo findSigningInfoBySigningID(String signingID);





}
