package com.example.ivt_mng_sys.controller.reportInfo;

import com.example.ivt_mng_sys.Util.ActionUtil;
import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.Util.CommonUtil;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.DeptInfo;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.ProductInfoService;
import com.example.ivt_mng_sys.service.StockDetailService;
import com.example.ivt_mng_sys.service.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @功能说明：报表接口控制层
 * @开发人员：张涛
 * @开发时间：2022-01-10
 * */
@RequestMapping("reportController")
@Controller
@Slf4j
public class ReportController {
    @Resource
    private StockDetailService stockDetailService;
    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommonUtil commonUtil;
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();
    @Autowired
    public ReportController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }
    /**
     * @功能说明：报表展示
     * @开发人员：张涛
     * @开发时间：2022-01-10
     * */
    @RequestMapping("reportGeneration1")
    public String reportGeneration1(){
        weekReportPage(1);
        return "report/reportList";
    }
    @RequestMapping("reportGeneration2")
    public String reportGeneration2(){
        monthReportPage(1);
        return "report/reportList";
    }
    @RequestMapping("reportGeneration3")
    public String reportGeneration3(){
        yearReportPage(1);
        return "report/reportList";
    }


    @RequestMapping("reportGeneration")
    public String reportGeneration(@RequestParam(required = false)String type){
        if (type.equals("1")){  //周报
            weekReportPage(1);
            return "report/reportList";
        }
        if (type.equals("2")){//月报
            monthReportPage(1);
            return "report/reportList";
        }
        if (type.equals("3")){//年报
            yearReportPage(1);
            return "report/reportList";
        }
        if (type.equals("4")){//自定义
            return "report/reportList";
        }
        return "report/reportList";
    }


    /**
     * @功能说明：自定义报表
     * @开发人员：张涛
     * @开发时间：2022-01-15
     * */
    @RequestMapping("customReport")
    public String customReport(){
        log.info("reportController>>customReport");
        commonUtil.clearSession();
        request.getSession().setAttribute("start","0000/00/00");
        request.getSession().setAttribute("end","0000/00/00");
        return "report/customReportList";
    }


    /**
     * @功能说明：自定义报表
     * @开发人员：张涛
     * @开发时间：2022-01-15
     * */
    @RequestMapping("customReportList")
    public String customReportList(String start,String end) throws ParseException {
        log.info("reportController>>customReportList");
        log.info("开始时间>>"+start+">>结束时间>>"+end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date starts = sdf.parse(start);//开始时间
        Date ends = sdf.parse(end);//结束时间
        customReportListPage(1,"reportController>>customReportList",starts,ends);
        request.getSession().setAttribute("start",start);
        request.getSession().setAttribute("end",end);
        return "report/customReportList";
    }
    /**
     * @功能说明：自定义上下页查询
     * @开发人员：张涛
     * @开发时间：2022-01-10
     * */
    @RequestMapping("customReportListUpDown")
    public String customReportListUpDown(@RequestParam(required = false)String pageSize,String currentPage,String starts,String ends) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date start = sdf.parse(starts);//开始时间
        Date end = sdf.parse(ends);//结束时间
        System.out.println(pageSize);
        System.out.println(currentPage);
        customReportListPage(Integer.parseInt(currentPage),"reportController>>customReportListUpDown",start,end);
        return "report/customReportList";
    }
    /**
     * @功能说明：自定义分页
     * @开发人员：张涛
     * @开发时间：2022-01-10
     * */
    public void customReportListPage(int page,String current,Date starts,Date ends){
        Set<StockInfo> set = new HashSet<>();
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStartAndEnd(ActionUtil.getDateStrForString(starts), ActionUtil.getDateStrForString(ends));
        for (StockDetail stockDetail : stockDetails) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetail.getStockID());
            stockInfoByStockID.setProdID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){
                stockInfoByStockID.setTheProduct("标");
                BigDecimal stockCount = stockInfoByStockID.getStockCount();
                String[] s = stockCount.toString().split(".000");
                stockInfoByStockID.setInteger(s[0]);
            }
            stockInfoByStockID.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdUnit());
            stockInfoByStockID.setJoinCount(stockDetailService.listStockDetailByAndYearCount(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()),stockInfoByStockID.getStockID()));//入库总数量
            stockInfoByStockID.setOutCount(stockDetailService.listStockDetailByAndYearCount(Integer.parseInt(allConfiguration.getStockDetailTypeOut()),stockInfoByStockID.getStockID()));//出库总数量
            set.add(stockInfoByStockID);
        }
        PageModel pageModel = commonUtil.showPage(page, set.size(),"reportController>>"+current+">>customReportListPage");
        for (StockInfo stockInfo : set) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockInfo.getStockID());
            if (productInfoService.findProductById(stockInfoByStockID.getProdID()).getStandardStatus()==0){
                stockInfo.setTheProduct("标");
                String[] s = stockInfo.getStockCount().toString().split(".000");
                stockInfo.setInteger(s[0]);
            }
            stockInfo.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getUpdateDate()));//最近操作时间
        }
        List<StockInfo> list = new ArrayList<>();
        for (StockInfo stockInfo : set) {
            list.add(stockInfo);
        }
        Map map = groupList(list);
        int pages = page-1;

        request.getSession().setAttribute("users",map.get("keyName"+pages));
        request.getSession().setAttribute("page", pageModel);
    }


    /**
     * @功能说明：上下页展示
     * @开发人员：张涛
     * @开发时间：2022-01-10
     * */
    @RequestMapping("upDownWeekReportPage")
    public String UpDownWeekReportPage(String pageSize,String currentPage,String period){
        log.info("pageSize>>"+pageSize+">>currentPage>>"+currentPage);
        if (period.equals("1")){
            weekReportPage(Integer.parseInt(currentPage));
        }
        if (period.equals("2")){
            monthReportPage(Integer.parseInt(currentPage));
        }
        if (period.equals("3")){
            yearReportPage(Integer.parseInt(currentPage));
        }
        return "report/reportList";
    }

    /**
     * @功能说明：周报表分页
     * @开发时间：2022-01-16
     * @开发人员：张涛
     * */
    public void weekReportPage(int page){
        Set<StockInfo> set = new HashSet<>();
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByAndweek();
        for (StockDetail stockDetail : stockDetails) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetail.getStockID());
            stockInfoByStockID.setProdID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){
                stockInfoByStockID.setTheProduct("标");
                BigDecimal stockCount = stockInfoByStockID.getStockCount();
                String[] s = stockCount.toString().split(".000");
                stockInfoByStockID.setInteger(s[0]);
            }
            stockInfoByStockID.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdUnit());
            stockInfoByStockID.setJoinCount(stockDetailService.listStockDetailByAnd7Count(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()),stockInfoByStockID.getStockID()));//入库总数量
            stockInfoByStockID.setOutCount(stockDetailService.listStockDetailByAnd7Count(Integer.parseInt(allConfiguration.getStockDetailTypeOut()),stockInfoByStockID.getStockID()));//出库总数量
            set.add(stockInfoByStockID);
        }
        PageModel pageModel = commonUtil.showPage(page, set.size(),"reportController>>"+"七天报表"+">>customReportListPage");

        System.out.println("start="+pageModel.getStart());
        System.out.println("currentPage="+pageModel.getCurrentPage());
        System.out.println("pageSize="+pageModel.getPageSize());
        for (StockInfo stockInfo : set) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockInfo.getStockID());
            if (productInfoService.findProductById(stockInfoByStockID.getProdID()).getStandardStatus()==0){
                stockInfo.setTheProduct("标");
                String[] s = stockInfo.getStockCount().toString().split(".000");
                stockInfo.setInteger(s[0]);
            }
            stockInfo.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getUpdateDate()));//最近操作时间
        }
        List<StockInfo> list = new ArrayList<>();
        for (StockInfo stockInfo : set) {
            list.add(stockInfo);
        }
        Map map = groupList(list);
        int pages = page-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        request.getSession().setAttribute("period","1");
        request.getSession().setAttribute("thatDay", ActionUtil.getDateStrForDay(new Date()));//当天日期
        request.getSession().setAttribute("toDay", preMonday);//之前日期
        request.getSession().setAttribute("users",map.get("keyName"+pages));
        request.getSession().setAttribute("page", pageModel);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map groupList(List list){
        int listSize=list.size();
        int toIndex=10;
        Map map = new HashMap();     //用map存起来新的分组后数据
        int keyToken = 0;
        for(int i = 0;i<list.size();i+=10){
            if(i+10>listSize){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex=listSize-i;
            }
            List newList = list.subList(i,i+toIndex);
            map.put("keyName"+keyToken, newList);
            keyToken++;
        }

        return map;
    }

    /**
     * @功能说明：月报表分页
     * @开发时间：2022-01-16
     * @开发人员：张涛
     * */
    public void monthReportPage(int page){
        Set<StockInfo> set = new HashSet<>();
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByAndMonth();
        for (StockDetail stockDetail : stockDetails) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetail.getStockID());
            stockInfoByStockID.setProdID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){
                stockInfoByStockID.setTheProduct("标");
                BigDecimal stockCount = stockInfoByStockID.getStockCount();
                String[] s = stockCount.toString().split(".000");
                stockInfoByStockID.setInteger(s[0]);
            }
            stockInfoByStockID.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdUnit());
            stockInfoByStockID.setJoinCount(stockDetailService.listStockDetailByAndMonthCount(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()),stockInfoByStockID.getStockID()));//入库总数量
            stockInfoByStockID.setOutCount(stockDetailService.listStockDetailByAndMonthCount(Integer.parseInt(allConfiguration.getStockDetailTypeOut()),stockInfoByStockID.getStockID()));//出库总数量
            set.add(stockInfoByStockID);
        }
        PageModel pageModel = commonUtil.showPage(page, set.size(),"reportController>>"+"30天报表"+">>customReportListPage");
        for (StockInfo stockInfo : set) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockInfo.getStockID());
            if (productInfoService.findProductById(stockInfoByStockID.getProdID()).getStandardStatus()==0){
                stockInfo.setTheProduct("标");
                String[] s = stockInfo.getStockCount().toString().split(".000");
                stockInfo.setInteger(s[0]);
            }
            stockInfo.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getUpdateDate()));//最近操作时间
        }
        List<StockInfo> list = new ArrayList<>();
        for (StockInfo stockInfo : set) {
            list.add(stockInfo);
        }
        Map map = groupList(list);
        int pages = page-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 30);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        request.getSession().setAttribute("period","2");
        request.getSession().setAttribute("thatDay", ActionUtil.getDateStrForDay(new Date()));//当天日期
        request.getSession().setAttribute("toDay", preMonday);//之前日期
        request.getSession().setAttribute("users",map.get("keyName"+pages));
        request.getSession().setAttribute("page", pageModel);
    }

    /**
     * @功能说明：年报表分页
     * @开发时间：2022-01-16
     * @开发人员：张涛
     * */
    public void yearReportPage(int page){
        Set<StockInfo> set = new HashSet<>();
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByAndYear();
        for (StockDetail stockDetail : stockDetails) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetail.getStockID());
            stockInfoByStockID.setProdID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getStandardStatus()==0){
                stockInfoByStockID.setTheProduct("标");
                BigDecimal stockCount = stockInfoByStockID.getStockCount();
                String[] s = stockCount.toString().split(".000");
                stockInfoByStockID.setInteger(s[0]);
            }
            stockInfoByStockID.setUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdUnit());
            stockInfoByStockID.setJoinCount(stockDetailService.listStockDetailByAndMonthCount(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()),stockInfoByStockID.getStockID()));//入库总数量
            stockInfoByStockID.setOutCount(stockDetailService.listStockDetailByAndMonthCount(Integer.parseInt(allConfiguration.getStockDetailTypeOut()),stockInfoByStockID.getStockID()));//出库总数量
            set.add(stockInfoByStockID);
        }
        PageModel pageModel = commonUtil.showPage(page, set.size(),"reportController>>"+"30天报表"+">>customReportListPage");
        for (StockInfo stockInfo : set) {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockInfo.getStockID());
            if (productInfoService.findProductById(stockInfoByStockID.getProdID()).getStandardStatus()==0){
                stockInfo.setTheProduct("标");
                String[] s = stockInfo.getStockCount().toString().split(".000");
                stockInfo.setInteger(s[0]);
            }
            stockInfo.setUpdateDateStr(ActionUtil.getDateStrForDayChinese(stockInfo.getUpdateDate()));//最近操作时间
        }
        List<StockInfo> list = new ArrayList<>();
        for (StockInfo stockInfo : set) {
            list.add(stockInfo);
        }
        Map map = groupList(list);
        int pages = page-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 365);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        request.getSession().setAttribute("period","3");
        request.getSession().setAttribute("thatDay", ActionUtil.getDateStrForDay(new Date()));//当天日期
        request.getSession().setAttribute("toDay", preMonday);//之前日期
        request.getSession().setAttribute("users",map.get("keyName"+pages));
        request.getSession().setAttribute("page", pageModel);
    }
}