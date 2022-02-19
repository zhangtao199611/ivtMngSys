package com.example.ivt_mng_sys.controller.StockInfo;

import com.example.ivt_mng_sys.Util.*;
import com.example.ivt_mng_sys.entity.ProductInfo;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.MemberInfoService;
import com.example.ivt_mng_sys.service.ProductInfoService;
import com.example.ivt_mng_sys.service.StockDetailService;
import com.example.ivt_mng_sys.service.StockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("StockDetailInfoController")
public class StockDetailInfoController {
    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private StockDetailService stockDetailService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private AllConfiguration allConfiguration;
    Logger logger = LoggerFactory.getLogger(StockInfoAPKController.class);

    /**
     * @功能说明：入库明细列表
     * @开发时间：2022-1-2
     * @开发人员：张涛
     * */
    @RequestMapping("jumpStockDetailInfoList")
    public String jumpStockDetailInfoList(String userName){
        logger.info("StockDetailInfoController>>jumpStockDetailInfoList");
        logger.info("入库明细>>入库明细列表");
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()));
        getStockDetailPage(1,"StockDetailInfoController>>jumpStockDetailInfoList",allConfiguration.getStockDetailTypeStorage(),stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",allConfiguration.getStockDetailTypeStorage());
        commonUtil.userPer(userName);
        return "stockDetail/stockDetailList";
    }

    /**
     * @功能说明：返回列表
     * @开发时间：2022-1-2
     * @开发人员：张涛
     * */
    @RequestMapping("returnStockDetailList")
    public String returnStockDetailList(@RequestParam(required = false)String currentPage,String stockDetailType){
        logger.info("StockDetailInfoController>>returnStockDetailList");
        logger.info("明细>>返回列表");
        logger.info("当前页>>"+currentPage+">>状态>>"+stockDetailType);
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(stockDetailType));
        getStockDetailPage(Integer.parseInt(currentPage),"StockDetailInfoController>>returnStockDetailList",stockDetailType,stockDetailCountByStockDetailType);
        return "stockDetail/stockDetailList";
    }

    /**
     * @功能说明：出库明细列表
     * @开发时间：2022-1-2
     * @开发人员：张涛
     * */
    @RequestMapping("jumpStockDetailInfoOutList")
    public String jumpStockDetailInfoOutList(){
        logger.info("StockDetailInfoController>>jumpStockDetailInfoOutList");
        logger.info("入库明细>>出库明细列表");
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(allConfiguration.getStockDetailTypeOut()));
        getStockDetailPage(1,"StockDetailInfoController>>jumpStockDetailInfoList",allConfiguration.getStockDetailTypeOut(),stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",allConfiguration.getStockDetailTypeOut());
        return "stockDetail/stockDetailList";
    }
    /**
     * @功能说明：退货明细列表
     * @开发时间：2022-1-12
     * @开发人员：张涛
     * */
    @RequestMapping("jumpStockDetailInforetReatList")
    public String jumpStockDetailInforetReatList(){
        logger.info("StockDetailInfoController>>jumpStockDetailInforetReatList");
        logger.info("入库明细>>出库明细列表");
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(allConfiguration.getStockDetailReturns()));
        getStockDetailPage(1,"StockDetailInfoController>>jumpStockDetailInfoList",allConfiguration.getStockDetailReturns(),stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",allConfiguration.getStockDetailReturns());
        return "stockDetail/stockDetailList";
    }
    /**
     * @功能说明：出入库明细上下一页
     * @开发时间：2022-1-3
     * @开发人员：张涛
     * */
    @RequestMapping("nextPage")
    public String nextPage(String pageSize,String currentPage,String status){
        logger.info("StockDetailInfoController>>nextPage");
        logger.info("入库明细>>出库明细下一页");
        logger.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage);
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(status));
        System.out.println("stockCount="+stockDetailCountByStockDetailType);
        getStockDetailPage(Integer.parseInt(currentPage),"StockDetailInfoController>>nextPage",status,stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",status);
        return "stockDetail/stockDetailList";
    }
    /**
     * @功能说明：apk根据编号跳转到出入库明细分页
     * @开发时间：2021-01-22
     * @开发人员：张涛
     * */
    @RequestMapping("apkStockCRPage")
    @ResponseBody
    public ApiResult apkStockCRPage(String pageSize,String currentPage,String status){
        logger.info("StockDetailInfoController>>apkStockCRPage");
        logger.info("入库明细>>apk根据编号跳转到出入库明细分页");
        logger.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage);
        ApiResult apiResult = new ApiResult();
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(Integer.parseInt(status));
        System.out.println("stockCount="+stockDetailCountByStockDetailType);
        Map<String, Object> stockDetailPage = getStockDetailPage(Integer.parseInt(currentPage), "StockDetailInfoController>>nextPage", status, stockDetailCountByStockDetailType);
        apiResult.setResult(200);
        apiResult.setResultData(stockDetailPage.get("stockDetails"));
        apiResult.setPageModel((PageModel) stockDetailPage.get("pageModel"));
        return apiResult;
    }

    /**
     * @功能说明：当日入库列表
     * @开发时间：2022-1-4
     * @开发人员：张涛
     * */
    @RequestMapping("theDayListOutStockInfo")
    public String theDayListOutStockInfo(String userName){
        logger.info("StockDetailInfoController>>theDayListOutStockInfo");
        logger.info("入库明细>>当日入库列表");
        int stockDetails = stockDetailService.listTheDayStockDetail(allConfiguration.getStockDetailTypeStorage(),ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusEffect());
        getTheDayStockDetailPage(1,"StockDetailInfoController>>theDayListOutStockInfo",stockDetails,allConfiguration.getStockDetailTypeStorage());
        request.getSession().setAttribute("statuss",allConfiguration.getStockDetailTypeStorage());
        commonUtil.userPer(userName);
        return "stockDetail/theDayStockDetailList";
    }

    /**
     * @功能说明：当日出入库分页
     * @开发时间：2022-1-9
     * @开发人员：张涛
     * */
    @RequestMapping("theDayListOutStockInfoPage")
    public String theDayListOutStockInfoPage(@RequestParam(required = false)String pageSize,String currentPage,String status){
        logger.info("StockDetailInfoController>>theDayListOutStockInfoPage");
        logger.info("入库明细>>当日入库列表");
        logger.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage+">>出入库状态>>"+status);
        int stockDetailCountByStockDetailType = stockDetailService.listStockDetailStockTypeAndStockDateAndStatusCount(status,ActionUtil.getDateStrForString(new Date()),allConfiguration.getStockDetailTypeStatusEffect());
        System.out.println("stockCount="+stockDetailCountByStockDetailType);
        getStockDetailPage(Integer.parseInt(currentPage),"StockDetailInfoController>>theDayListOutStockInfoPage",status,stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",status);
        return "stockDetail/theDayStockDetailList";
    }

    /**
     * @功能说明：当日出库列表
     * @开发时间：2022-1-4
     * @开发人员：张涛
     * */
    @RequestMapping("theDayListJoinStockInfo")
    public String theDayListJoinStockInfo(String userName){
        logger.info("StockDetailInfoController>>theDayListOutStockInfo");
        logger.info("入库明细>>当日入库列表");
        int stockDetails = stockDetailService.listTheDayStockDetail(allConfiguration.getStockDetailTypeOut(),ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusEffect());
        getTheDayStockDetailPage(1,"StockDetailInfoController>>theDayListOutStockInfo",stockDetails,allConfiguration.getStockDetailTypeOut());
        request.getSession().setAttribute("statuss",allConfiguration.getStockDetailTypeOut());
        commonUtil.userPer(userName);
        return "stockDetail/theDayStockDetailList";
    }

    /**
     * @功能说明：库存明细详情
     * @开发时间：2022-1-5
     * @修改时间：2022-01-12
     * @开发人员：张涛
     * */
    @RequestMapping("findStockDetail")
    public String findStockDetail(String stockDetailID,String currentPage){
        logger.info("StockDetailInfoController>>findStockDetail");
        logger.info("入库明细>>库存明细详情");
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        stockDetailByStockDetailID.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdUnit());
        if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getStandardStatus()==0){
            stockDetailByStockDetailID.setUnit(stockDetailByStockDetailID.getWeight().toString());
        }
        stockDetailByStockDetailID.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetailByStockDetailID.getOperationTime()));
        stockDetailByStockDetailID.setOperator(memberInfoService.findMemberInfoByJobID(stockDetailByStockDetailID.getOperator()).getMemberName());
        stockDetailByStockDetailID.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdName());
        request.getSession().setAttribute("stockDetail",stockDetailByStockDetailID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "stockDetail/findStockDetail";
    }
    /**
     * @功能说明：跳转到修改明细页面
     * @开发时间：2022-1-12
     * @开发人员：张涛
     * */
    @RequestMapping("updatePageStockDetail")
    public String updatePageStockDetail(@RequestParam(required = false)String stockDetailID,String currentPage,String userName){
        logger.info("StockDetailInfoController>>updatePageStockDetail");
        logger.info("入库明细>>库存明细详情");
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        stockDetailByStockDetailID.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdUnit());
        if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getStandardStatus()==0){
            stockDetailByStockDetailID.setUnit(stockDetailByStockDetailID.getWeight().toString());
        }
        stockDetailByStockDetailID.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetailByStockDetailID.getOperationTime()));
        stockDetailByStockDetailID.setOperator(memberInfoService.findMemberInfoByJobID(stockDetailByStockDetailID.getOperator()).getMemberName());
        stockDetailByStockDetailID.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdName());
        commonUtil.clearSession();
        request.getSession().setAttribute("stockDetail",stockDetailByStockDetailID);
        request.getSession().setAttribute("currentPage",currentPage);
        commonUtil.userPer(userName);
        return "stockDetail/updateStockDetail";
    }

    /**
     * @功能说明：修改明细
     * @开发时间：2022-1-12
     * @开发人员：张涛
     * */
    @RequestMapping("updateStockDetail")
    public String updateStockDetail(@RequestParam(required = false)String adjustWeight,String weight,String stockDetailID,String currentPage,String userName) throws IOException {
        commonUtil.userPer(userName);
        logger.info("StockDetailInfoController>>updateStockDetail");
        logger.info("入库明细>>修改明细");
        logger.info("调整重量>>"+adjustWeight+">>实际重量>>"+weight+">>库存编号>>"+stockDetailID+">>当前页>>"+currentPage);
        BigDecimal bigDecimal = new BigDecimal(adjustWeight);//调整重量
        BigDecimal bigDecimal1 = new BigDecimal(weight);//实际重量
        BigDecimal subtract1 = bigDecimal1.subtract(bigDecimal);//实际重量减去调整重量
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        if (bigDecimal1.compareTo(bigDecimal)<0){
            StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
            stockDetailByStockDetailID.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdUnit());
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getStandardStatus()==0){
                stockDetailByStockDetailID.setUnit(stockDetailByStockDetailID.getWeight().toString());
            }
            stockDetailByStockDetailID.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetailByStockDetailID.getOperationTime()));
            stockDetailByStockDetailID.setOperator(memberInfoService.findMemberInfoByJobID(stockDetailByStockDetailID.getOperator()).getMemberName());
            stockDetailByStockDetailID.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdName());
            request.getSession().setAttribute("stockDetail",stockDetailByStockDetailID);
            request.getSession().setAttribute("currentPage",currentPage);
            request.getSession().setAttribute("notice","调整重量已超过实际重量，请重新输入！");
            return "stockDetail/updateStockDetail";
        }
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID());
        if (stockDetailByStockDetailID.getStockDetailType()==Integer.parseInt(allConfiguration.getStockDetailTypeOut())){//出库
            BigDecimal stockCount = stockInfoByStockID.getStockCount();
            BigDecimal subtract = stockCount.add(stockDetailByStockDetailID.getWeight());//总库存加上出库
            String format = decimalFormat.format(subtract);
            stockInfoByStockID.setStockCount(new BigDecimal(format));
            BigDecimal add = stockInfoByStockID.getStockCount().subtract(subtract1);//总库存减去调整后的实际重量
            String format1 = decimalFormat.format(add);
            if (format1.substring(0,1).equals("-")){
                System.out.println("222222");
                stockDetailByStockDetailID.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdUnit());
                if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getStandardStatus()==0){
                    stockDetailByStockDetailID.setUnit(stockDetailByStockDetailID.getWeight().toString());
                }
                stockDetailByStockDetailID.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetailByStockDetailID.getOperationTime()));
                stockDetailByStockDetailID.setOperator(memberInfoService.findMemberInfoByJobID(stockDetailByStockDetailID.getOperator()).getMemberName());
                stockDetailByStockDetailID.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdName());
                request.getSession().setAttribute("stockDetail",stockDetailByStockDetailID);
                request.getSession().setAttribute("currentPage",currentPage);
                request.getSession().setAttribute("notice","已超出库存量，无法修改本库存明细量！");
                return "stockDetail/updateStockDetail";
            }
            stockInfoByStockID.setStockCount(new BigDecimal(format1));
            stockInfoByStockID.setLatestOutTime(new Date());
            stockInfoByStockID.setUpdateDate(new Date());
            stockDetailByStockDetailID.setWeight(subtract1);
            stockDetailByStockDetailID.setAdjustWeight(new BigDecimal(adjustWeight));
            boolean b = stockDetailService.updateStockDetail(stockDetailByStockDetailID);
            if (b==true){
                stockInfoService.updateStockInfo(stockInfoByStockID);
            }
        }
        if (stockDetailByStockDetailID.getStockDetailType()==Integer.parseInt(allConfiguration.getStockDetailTypeStorage())){//入库
            StockInfo stockInfoByStockID1 = stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID());
            BigDecimal stockCount = stockInfoByStockID1.getStockCount();
            BigDecimal subtract = stockCount.subtract(stockDetailByStockDetailID.getWeight());
            stockInfoByStockID1.setStockCount(new BigDecimal(decimalFormat.format(subtract)));
            BigDecimal add = stockInfoByStockID1.getStockCount().add(subtract1);
            stockInfoByStockID1.setStockCount(new BigDecimal(decimalFormat.format(add)));
            stockInfoByStockID1.setUpdateDate(new Date());
            stockInfoByStockID1.setLatestInTime(new Date());
            stockDetailByStockDetailID.setWeight(subtract1);
            stockDetailByStockDetailID.setAdjustWeight(bigDecimal);
            stockDetailByStockDetailID.setWeighingWeight(bigDecimal1);
            boolean b = stockDetailService.updateStockDetail(stockDetailByStockDetailID);
            if (b==true){
                stockInfoService.updateStockInfo(stockInfoByStockID1);
            }
        }
        if (stockDetailByStockDetailID.getStockDetailType()==Integer.parseInt(allConfiguration.getStockDetailTypeLoss())){//损耗
        }
        if (stockDetailByStockDetailID.getStockDetailType()==Integer.parseInt(allConfiguration.getStockDetailReturns())){//退货
            BigDecimal stockCount = stockInfoByStockID.getStockCount();
            BigDecimal subtract = stockCount.add(stockDetailByStockDetailID.getWeight());//总库存加上出库
            String format = decimalFormat.format(subtract);
            stockInfoByStockID.setStockCount(new BigDecimal(format));
            BigDecimal add = stockInfoByStockID.getStockCount().subtract(subtract1);//总库存减去调整后的实际重量
            String format1 = decimalFormat.format(add);
            if (format1.substring(0,1).equals("-")){
                System.out.println("222222");
                stockDetailByStockDetailID.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdUnit());
                if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getStandardStatus()==0){
                    stockDetailByStockDetailID.setUnit(stockDetailByStockDetailID.getWeight().toString());
                }
                stockDetailByStockDetailID.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetailByStockDetailID.getOperationTime()));
                stockDetailByStockDetailID.setOperator(memberInfoService.findMemberInfoByJobID(stockDetailByStockDetailID.getOperator()).getMemberName());
                stockDetailByStockDetailID.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID()).getProdID()).getProdName());
                request.getSession().setAttribute("stockDetail",stockDetailByStockDetailID);
                request.getSession().setAttribute("currentPage",currentPage);
                request.getSession().setAttribute("notice","已超出库存量，无法修改本库存明细量！");
                return "stockDetail/updateStockDetail";
            }
            stockInfoByStockID.setStockCount(new BigDecimal(format1));
            stockInfoByStockID.setLatestOutTime(new Date());
            stockInfoByStockID.setUpdateDate(new Date());
            stockDetailByStockDetailID.setWeight(subtract1);
            stockDetailByStockDetailID.setAdjustWeight(new BigDecimal(adjustWeight));
            boolean b = stockDetailService.updateStockDetail(stockDetailByStockDetailID);
            if (b==true){
                stockInfoService.updateStockInfo(stockInfoByStockID);
            }
        }
        int stockDetailCountByStockDetailType = stockDetailService.getStockDetailCountByStockDetailType(stockDetailByStockDetailID.getStockDetailType());
        getStockDetailPage(Integer.parseInt(currentPage),"StockDetailInfoController>>jumpStockDetailInfoList",Integer.toString(stockDetailByStockDetailID.getStockDetailType()),stockDetailCountByStockDetailType);
        request.getSession().setAttribute("statuss",Integer.toString(stockDetailByStockDetailID.getStockDetailType()));
        return "stockDetail/stockDetailList";
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("1.254");//调整重量
        BigDecimal bigDecimal1 = new BigDecimal("1.000");//实际重量
        if (bigDecimal1.compareTo(bigDecimal)<0){
            System.out.println("已超过实际重量");
        }
        System.out.println(bigDecimal1.compareTo(bigDecimal)>0);
    }


    /**
     * @功能说明：当日出库明细列表分页
     * @开发时间：2022-1-4
     * @开发人员：张涛
     * */
    public void getTheDayStockDetailPage(int page,String current,int count,String status){
        PageModel pageModel = commonUtil.showPage(page, count, "StockDetailInfoController>>"+current+"getStockDetailPage");
        List<StockDetail> stockDetails1 = stockDetailService.listStockDetailPageByStockDateAndStatus(status,ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusEffect(), pageModel);
        for (StockDetail stockDetail : stockDetails1) {
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){
                stockDetail.setTheProduct("标");
                BigDecimal stockCount = stockDetail.getWeight();
                String[] s = stockCount.toString().split(".000");
                stockDetail.setInteger(s[0]);
            }
            stockDetail.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetail.getOperationTime()));//时间
            stockDetail.setOperator(memberInfoService.findMemberInfoByJobID(stockDetail.getOperator()).getMemberName());//操作人
            String stockID1 = stockDetail.getStockID();
            stockDetail.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());//设置食材名称
            System.out.println("食材名称="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());
            System.out.println("单位="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            stockDetail.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            if (stockDetail.getAdjustWeight()==null){
                stockDetail.setAdjustWeight(new BigDecimal(0));
            }
        }
        StockDetail stockDetail = null;
        try {
            stockDetail = stockDetails1.get(0);
        } catch (Exception e) {
            logger.info("暂无数据:"+e);
            StockDetail stockDetail1 = new StockDetail();
            stockDetail1.setStockDetailType(Integer.parseInt(status));
            stockDetail = stockDetail1;
        }
        request.getSession().setAttribute("status",stockDetail);
        request.getSession().setAttribute("users",stockDetails1);
        request.getSession().setAttribute("page", pageModel);
    }


    /**
     * @功能说明：出入损分页
     * @开发时间：2021-1-2
     * @开发人员：张涛
     * */
    public Map<String,Object> getStockDetailPage(int page, String current, String status, int count){
        logger.info("StockDetailInfoController>>getStockDetailPage");
        logger.info("入库明细>>入库明细分页");
        logger.info("分页>>"+page+"当前页"+current+"数量"+count);
        PageModel pageModel = commonUtil.showPage(page, count,"StockInfoController>>"+current+">>getStockDetailPage");
        System.out.println("剩余页数="+pageModel.getAllPage());
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockDetailType(Integer.parseInt(status),pageModel);
        for (StockDetail stockDetail : stockDetails) {
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){//标品
                stockDetail.setTheProduct("标");
                BigDecimal stockCount = stockDetail.getWeight();
                String[] s = stockCount.toString().split(".000");
                stockDetail.setInteger(s[0]);
            }
            System.out.println("食材名称222="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());
            stockDetail.setOperationTimeStr(ActionUtil.getDateStrForDayChinese(stockDetail.getOperationTime()));//时间
            System.out.println("名称="+stockDetail.getOperator());
            stockDetail.setOperator(memberInfoService.findMemberInfoByJobID(stockDetail.getOperator()).getMemberName());//操作人
            String stockID1 = stockDetail.getStockID();
            stockDetail.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());//设置食材名称
            System.out.println("食材名称="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdName());
            System.out.println("单位="+productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            stockDetail.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            stockDetail.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockID1).getProdID()).getProdUnit());
            if (stockDetail.getAdjustWeight()==null){
                stockDetail.setAdjustWeight(new BigDecimal(0));
            }
        }
        Map<String,Object> map = new HashMap();
        map.put("stockDetails",stockDetails);
        map.put("pageModel",pageModel);
        StockDetail stockDetail = null;
        try {
            stockDetail = stockDetails.get(0);
        } catch (Exception e) {
            logger.info("暂无数据:"+e);
            StockDetail stockDetail1 = new StockDetail();
            stockDetail1.setStockDetailType(Integer.parseInt(status));
            stockDetail = stockDetail1;
        }
        request.getSession().setAttribute("status",stockDetail);
        request.getSession().setAttribute("users",stockDetails);
        request.getSession().setAttribute("page", pageModel);
        return map;
    }
}
