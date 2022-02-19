package com.example.ivt_mng_sys.controller.StockInfo;

import com.example.ivt_mng_sys.Util.*;
import com.example.ivt_mng_sys.entity.*;
import com.example.ivt_mng_sys.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("StockInfoController")
@Slf4j
public class StockInfoController {
    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private StockDetailService stockDetailService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private CommonUtil commonUtil;
    /**
     * 读取conf.yml配置文件
     * */
    private AllConfiguration allConfiguration = new AllConfiguration();
    @Autowired
    public StockInfoController(AllConfiguration allConfiguration){this.allConfiguration = allConfiguration;}
    /**
     * @功能说明：跳转到库存列表
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @RequestMapping("jumpListStockInfo")
    public String jumpListStockInfo(@RequestParam(required = false)String userName){
        log.info("StockInfoController>>jumpListStockInfo");
        log.info("库存列表>>跳转到库存列表");
        log.info("userName>>"+userName);
        getStockInfoHome(1,"StockInfoController>>jumpListStockInfo");
        commonUtil.userPer(userName);
        return "stockInfo/StockInfoList";
    }
    /**
     * @功能说明：APK首页库存列表
     * @开发时间：2021-1-22
     * @开发人员：张涛
     * */
    @RequestMapping("aPKStorageList")
    @ResponseBody
    public ApiResult aPKStorageList(){
        log.info("StockInfoController>>jumpListStockInfo");
        log.info("库存列表>>跳转到库存列表");
        ApiResult apiResult = new ApiResult();
        Map<String, Object> stockInfoHome = getStockInfoHome(1, "StockInfoController>>jumpListStockInfo");
        apiResult.setResult(200);
        apiResult.setResultData(stockInfoHome.get("stockInfos"));
        apiResult.setPageModel((PageModel) stockInfoHome.get("pageModel"));
        apiResult.setMsg("成功");
        return apiResult;
    }

    /**
     * @功能说明：库存上下页
     * @开发时间：2021-1-14
     * @开发人员：张涛
     * */
    @RequestMapping("stockInfoPage")
    public String stockInfoPage(@RequestParam(required = false)String pageSize,String currentPage){
        log.info("StockInfoController>>stockInfoPage");
        log.info("库存分页>>库存上下页");
        log.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage);
        getStockInfoHome(Integer.parseInt(currentPage),"StockInfoController>>stockInfoPage");
        return "stockInfo/StockInfoList";
    }
    /**
     * @功能说明：APK库存上下页
     * @开发时间：2021-1-22
     * @开发人员：张涛
     * */
    @RequestMapping("apkStockPage")
    @ResponseBody
    public ApiResult apkStockPage(@RequestParam(required = false)String currentPage){
        log.info("StockInfoController>>apkStockPage");
        log.info("库存分页>>APK库存上下页");
        log.info(">>currentPage>>"+currentPage);
        ApiResult apiResult = new ApiResult();
        Map<String, Object> stockInfoHome = getStockInfoHome(Integer.parseInt(currentPage), "StockInfoController>>stockInfoPage");
        apiResult.setResult(200);
        apiResult.setResultData(stockInfoHome.get("stockInfos"));
        apiResult.setPageModel((PageModel) stockInfoHome.get("pageModel"));
        apiResult.setMsg("成功");
        return apiResult;
    }



    /**
     * @功能说明：根据编号跳转到库存详情
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @RequestMapping("jumpFindStockInfo")
    public String jumpFindStockInfo(@RequestParam(required = false)String stockID,String currentPage){
        log.info("StockInfoController>>jumpFindStockInfo");
        log.info("库存列表>>跳转到库存详情");
        log.info("stockID>>"+stockID+">>currentPage>>"+currentPage);
        StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockID);
        ProductInfo productById = productInfoService.findProductById(stockInfoByStockID.getProdID());
        stockInfoByStockID.setUnit(productById.getProdUnit());//单位
        stockInfoByStockID.setProdID(productInfoService.findProductById(stockInfoByStockID.getProdID()).getProdName());//产品名称
        stockInfoByStockID.setStorageDateStr(ActionUtil.getDateStrForDayChinese(stockInfoByStockID.getStorageDate()));//第一次入库时间
        stockInfoByStockID.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfoByStockID.getUpdateDate()));//更新时间
        stockInfoByStockID.setUpdater(memberInfoService.findMemberInfoByJobID(stockInfoByStockID.getUpdater()).getMemberName());//更新人编号
        stockInfoByStockID.setLatestInTimeStr(ActionUtil.getDateStrForDayChinese(stockInfoByStockID.getLatestInTime()));//最近入库时间
        if (stockInfoByStockID.getLatestOutTime()==null){
            stockInfoByStockID.setLatestOutTimeStr("还未出库");
        }else {
            stockInfoByStockID.setLatestOutTimeStr(ActionUtil.getDateStrForDayChinese(stockInfoByStockID.getLatestOutTime()));//最近出库时间
        }
        request.getSession().setAttribute("stockInfo",stockInfoByStockID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "stockInfo/findStockInfo";
    }
    /**
     * @功能说明：apk根据编号跳转到入库明细
     * @开发时间：2021-01-22
     * @开发人员：张涛
     * */
    @RequestMapping("jumpStockDetail")
    @ResponseBody
    public ApiResult jumpStockDetail(@RequestParam(required = false)String stockID,String currentPage){
        log.info("StockInfoController>>jumpFindStockInfo");
        log.info("库存列表>>跳转到库存详情");
        log.info("stockID>>"+stockID+">>currentPage>>"+currentPage);
        ApiResult apiResult = new ApiResult();
        int stockDetailCount = stockDetailService.getStockDetailCount(stockID, Integer.parseInt(allConfiguration.getStockDetailTypeStorage()));
        Map<String, Object> stockDetailPage = getStockDetailPage(1, "StockInfoController>>jumpListStockDetailTypeStorage", stockID, allConfiguration.getStockDetailTypeStorage(), stockDetailCount);
        apiResult.setResult(200);
        apiResult.setResultData(stockDetailPage.get("stockDetails"));
        apiResult.setPageModel((PageModel) stockDetailPage.get("pageModel"));
        return apiResult;
    }
    /**
     * @功能说明：apk根据编号跳转到出库明细
     * @开发时间：2021-01-22
     * @开发人员：张涛
     * */
    @RequestMapping("jumpStockDetailOut")
    @ResponseBody
    public ApiResult jumpStockDetailOut(String stockID,String currentPage){
        log.info("StockInfoController>>jumpStockDetailOut");
        log.info("库存列表>>apk根据编号跳转到出库明细");
        log.info("stockID>>"+stockID+">>currentPage>>"+currentPage);
        ApiResult apiResult = new ApiResult();
        int stockDetailCount = stockDetailService.getStockDetailCount(stockID, Integer.parseInt(allConfiguration.getStockDetailTypeOut()));
        Map<String, Object> stockDetailPage = getStockDetailPage(1, "StockInfoController>>jumpStockDetailOutList", stockID, allConfiguration.getStockDetailTypeOut(), stockDetailCount);
        apiResult.setResult(200);
        apiResult.setResultData(stockDetailPage.get("stockDetails"));
        apiResult.setPageModel((PageModel) stockDetailPage.get("pageModel"));
        return apiResult;
    }


    /**
     * @功能说明：根据编号跳转到出库明细
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @RequestMapping("jumpListStockDetailOut")
    public String jumpListStockDetailOut(String stockID,String currentPage,String userName){
        log.info("StockInfoController>>jumpStockDetailOutList");
        log.info("库存列表>>根据编号跳转到出库明细");
        log.info("stockID>>"+stockID+">>currentPage>>"+currentPage);
        int stockDetailCount = stockDetailService.getStockDetailCount(stockID, Integer.parseInt(allConfiguration.getStockDetailTypeOut()));
        getStockDetailPage(1,"StockInfoController>>jumpStockDetailOutList",stockID,allConfiguration.getStockDetailTypeOut(),stockDetailCount);
        request.getSession().setAttribute("currentPage",currentPage);
        commonUtil.userPer(userName);
        return "stockDetail/stockDetailList";
    }
    /**
     * @功能说明：根据编号跳转到入库明细
     * @开发时间：2021-12-28
     * @开发人员：张涛
     * */
    @RequestMapping("jumpListStockDetailTypeStorage")
    public String jumpListStockDetailTypeStorage(String stockID,String currentPage,String userName){
        log.info("StockInfoController>>jumpListStockDetailTypeStorage");
        log.info("库存列表>>根据编号跳转到入库明细");
        log.info("stockID>>"+stockID+">>currentPage>>"+currentPage);
        int stockDetailCount = stockDetailService.getStockDetailCount(stockID, Integer.parseInt(allConfiguration.getStockDetailTypeStorage()));
        getStockDetailPage(1,"StockInfoController>>jumpListStockDetailTypeStorage",stockID,allConfiguration.getStockDetailTypeStorage(),stockDetailCount);
        request.getSession().setAttribute("currentPage",currentPage);
        commonUtil.userPer(userName);
        return "stockDetail/stockDetailList";
    }

    /**
     * @功能说明：出入上下一页
     * @开发时间：2022-1-3
     * @开发人员：张涛
     * */
//    @RequestMapping("nextPage")
//    public String nextPage(String pageSize,String currentPage,String status){
//        log.info("StockInfoController>>nextPage");
//        log.info("入库明细>>出入上下一页");
//        log.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage);
//        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(status));
//        System.out.println("stockCount="+stockDetailCountByStockDetailType);
//        getStockDetailPage(Integer.parseInt(pageSize),currentPage,);
//        request.getSession().setAttribute("statuss",status);
//        return "stockDetail/stockDetailList";
//    }

    /**
     * @功能说明：出入损分页
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    public Map<String,Object> getStockDetailPage(int page, String current,String stockID,String status,int count){
        log.info("count>>"+count+">>status>>"+status);
        PageModel pageModel = commonUtil.showPage(page, count,"StockInfoController>>"+current+">>getStockDetailPage");
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDetailType(stockID, status, pageModel);
        for (StockDetail stockDetail : stockDetails) {
            stockDetail.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetail.getOperationTime()));//时间
            stockDetail.setOperator(memberInfoService.findMemberInfoByJobID(stockDetail.getOperator()).getMemberName());//操作人
            String stockID1 = stockDetail.getStockID();
            stockDetail.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());//设置食材名称
            System.out.println("食材名称="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());
            stockDetail.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());//设置单位
            stockDetail.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            if (stockDetail.getAdjustWeight()==null){
                stockDetail.setAdjustWeight(new BigDecimal(0));
            }
            if (stockDetail.getOperationTime()==null){
                stockDetail.setOperationTimeStr("/");
            }
        }
        Map<String,Object> map = new HashMap();
        map.put("stockDetails",stockDetails);
        map.put("pageModel",pageModel);
        StockDetail stockDetail = null;
        try {
            stockDetail = stockDetails.get(0);
        } catch (Exception e) {
            log.info("暂无数据:"+e);
            StockDetail stockDetail1 = new StockDetail();
            stockDetail1.setStockDetailType(Integer.parseInt(status));
            stockDetail = stockDetail1;
        }
        request.getSession().setAttribute("status",stockDetail);
        request.getSession().setAttribute("users",stockDetails);
        request.getSession().setAttribute("page", pageModel);
        return map;
    }

    /**
     * @功能说明：返回库存主页方法
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    public Map<String,Object> getStockInfoHome(int page, String current){
        int stockInfoCount = stockInfoService.getStockInfoCount();
        PageModel pageModel = commonUtil.showPage(page, stockInfoCount,"StockInfoController>>"+current+">>getStockInfoHome");
        List<StockInfo> stockInfos = stockInfoService.listStockInfoByPage(pageModel);
        for (StockInfo stockInfo : stockInfos) {
            ProductInfo productById = productInfoService.findProductById(stockInfo.getProdID());
            if (productById.getStandardStatus()==0){
                stockInfo.setTheProduct("标");
                BigDecimal stockCount = stockInfo.getStockCount();
                String[] s = stockCount.toString().split(".000");
                stockInfo.setInteger(s[0]);
            }
            stockInfo.setUnit(productById.getProdUnit());//单位
            stockInfo.setProdID(productInfoService.findProductById(stockInfo.getProdID()).getProdName());//产品名称
            stockInfo.setStorageDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getStorageDate()));//入库时间字符串
            stockInfo.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getUpdateDate()));//最近操作时间
            stockInfo.setUpdater(memberInfoService.findMemberInfoByJobID(stockInfo.getUpdater()).getMemberName());//最近操作人
            if (stockInfo.getLatestOutTime()==null){
                stockInfo.setLatestOutTimeStr("未出货");
            }else {
                stockInfo.setLatestOutTimeStr(ActionUtil.getDateStrForDayChinese(stockInfo.getLatestOutTime()));//最近出库时间
            }
            stockInfo.setLatestInTimeStr(ActionUtil.getDateStrForDayChinese(stockInfo.getLatestInTime()));//最近入库时间
            if (stockInfo.getUpdateDate()==null){
                stockInfo.setUpdateDateStr("/");
            }

        }
        Map<String,Object> map = new HashMap();
        map.put("stockInfos",stockInfos);
        map.put("pageModel",pageModel);
        request.getSession().setAttribute("users",stockInfos);
        request.getSession().setAttribute("page", pageModel);
        return map;
    }
}
