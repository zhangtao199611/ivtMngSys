package com.example.ivt_mng_sys.controller.signingInfo;

import com.example.ivt_mng_sys.Util.*;
import com.example.ivt_mng_sys.entity.*;
import com.example.ivt_mng_sys.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("SigningInfoController")
public class SigningInfoController {
    @Resource
    private StockDetailService stockDetailService;
    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SigningInfoService signingInfoService;
    @Resource
    private UnitInfoService unitInfoService;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private DeptInfoService deptInfoService;
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();

    @Autowired
    public SigningInfoController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }

    /**
     * @功能说明：签单列表展示
     * @开发时间：2022-01-14
     * @开发人员：张涛
     */
    @RequestMapping("listSigningInfo")
    public String listSigningInfo(@RequestParam(required = false) String userID) {
        log.info("签单管理>>签单列表展示");
        log.info("SigningInfoController>>listSigningInfo");
        log.info("userID>>" + userID);
        MemberInfo memberInfoByJobID = memberInfoService.findMemberInfoByJobID(userID);
        int i = signingInfoService.countSigningInfo();
        signingInfoPage(1, i, "SigningInfoController>>listSigningInfo");
        request.getSession().setAttribute("memberInfoByJobID", memberInfoByJobID);
        return "signing/signingList";
    }

    /**
     * @功能说明：签单分页
     * @开发时间：2022-01-14
     * @开发人员：张涛
     */
    @RequestMapping("signingPage")
    public String signingPage(@RequestParam(required = false) String pageSize, String currentPage) {
        log.info("签单管理>>签单分页");
        log.info("SigningInfoController>>signingPage");
        log.info("size>>" + pageSize + ">>currentPage>>" + currentPage);
        int i = signingInfoService.countSigningInfo();
        signingInfoPage(Integer.parseInt(currentPage), i, "SigningInfoController>>signingPage");
        return "signing/signingList";
    }

    /**
     * @功能说明：查看签单详情
     * @开发时间：2022-01-14
     * @开发人员：张涛
     */
    @RequestMapping("signingDetails")
    public String signingDetails(@RequestParam(required = false) String signingID, String currentPage) {
        log.info("签单管理>>签单分页");
        log.info("SigningInfoController>>signingDetails");
        log.info("签单编号>>" + signingID);
        SigningInfo signingInfoBySigningID = signingInfoService.findSigningInfoBySigningID(signingID);
        signingInfoBySigningID.setAddTimeStr(ActionUtil.getDateStrForDayChinese(signingInfoBySigningID.getAddTime()));//设置添加时间
        signingInfoBySigningID.setAdder(memberInfoService.findMemberInfoByJobID(signingInfoBySigningID.getAdder()).getMemberName());
        signingInfoBySigningID.setUnitID(unitInfoService.findUnitInfoByUnitID(signingInfoBySigningID.getUnitID()).getUntitName());//归属名称
        request.getSession().setAttribute("signingInfo", signingInfoBySigningID);
        request.getSession().setAttribute("currentPage", currentPage);
        return "signing/findSigning";
    }

    /**
     * @功能说明：展示当天打印信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     */
    @RequestMapping("listSigning")
    public String listSigning() {
        log.info("签单管理>>展示打印信息");
        log.info("SigningInfoController>>listSigning");
        List<StockInfo> stockInfos = stockInfoService.listStockInfo();
        Set<ProductInfo> productInfos = new HashSet<>();
        for (StockInfo stockInfo : stockInfos) {
            BigDecimal bigDecimal = stockDetailService.sumStockDetailByWeight(stockInfo.getStockID(), ActionUtil.getDateStrForString(new Date()));
            stockInfo.setTheTotalInventory(bigDecimal);
        }
        Iterator<StockInfo> iterator = stockInfos.iterator();
        while (iterator.hasNext()) {
            StockInfo next = iterator.next();
            if (next.getTheTotalInventory() == null) {
                iterator.remove();
            }
        }
        for (StockInfo stockInfo : stockInfos) {
            ProductInfo productById = productInfoService.findProductById(stockInfo.getProdID());
            productById.setBePutInStorage(stockInfo.getTheTotalInventory());//入库总数
            BigDecimal multiply = null;
            if (productById.getStandardStatus() == 0) {//标品
                multiply = stockInfo.getTheTotalInventory().multiply(productById.getProdPrice());//入库总数*单价=总价
            } else if (productById.getStandardStatus() == 1) {//非表品
                System.out.println("sss>" + productById.getProdPrice());
                if (productById.getTotalPrices() == null) {
                    productById.setProdPrice(new BigDecimal("0.00"));
                }
                BigDecimal add = productById.getProdPrice().add(productById.getProdPrice());
                multiply = stockInfo.getTheTotalInventory().multiply(add);//入库总数*单价*2=总价
            }
            productById.setTotalPrices(multiply);//总价
            productInfos.add(productById);
        }
        for (ProductInfo productInfo : productInfos) {
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productInfo.getProdID());
            BigDecimal bigDecimal1 = stockDetailService.sumStockDetailByWeightAndPrint(stockInfoByProdID.getStockID(), ActionUtil.getDateStrForString(new Date()), Integer.toString(allConfiguration.getPrintStatusNO()));
            if (bigDecimal1 == null) {
                productInfo.setPrint("已打印");
            } else {
                productInfo.setPrint("未打印");
            }
        }
        request.getSession().setAttribute("productInfos", productInfos);
        request.getSession().setAttribute("thatDay", ActionUtil.getDateStrForDay(new Date()));
        request.getSession().setAttribute("Day", ActionUtil.getDateStrForString(new Date()));
        return "signing/signingPrintList";
    }


    /**
     * @功能说明：展示指定日期打印信息
     * @开发时间：2022-01-15
     * @开发人员：张涛
     */
    @RequestMapping("listSigningAssign")
    public String listSigningAssign(String date) throws ParseException {
        log.info("签单管理>>展示指定日期打印信息");
        log.info("SigningInfoController>>listSigningAssign");
        log.info("指定日期>>" + date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date parse = sdf.parse(date);
        System.out.println("时间转换=" + ActionUtil.getDateStrForString(parse));
        List<StockInfo> stockInfos = stockInfoService.listStockInfo();
        Set<ProductInfo> productInfos = new HashSet<>();
        for (StockInfo stockInfo : stockInfos) {
            BigDecimal bigDecimal = stockDetailService.sumStockDetailByWeight(stockInfo.getStockID(), ActionUtil.getDateStrForString(parse));
            stockInfo.setTheTotalInventory(bigDecimal);
        }
        Iterator<StockInfo> iterator = stockInfos.iterator();
        while (iterator.hasNext()) {
            StockInfo next = iterator.next();
            if (next.getTheTotalInventory() == null) {
                iterator.remove();
            }
        }
        for (StockInfo stockInfo : stockInfos) {
            ProductInfo productById = productInfoService.findProductById(stockInfo.getProdID());
            productById.setBePutInStorage(stockInfo.getTheTotalInventory());//入库总数
            BigDecimal multiply = null;
            if (productById.getStandardStatus() == 0) {//标品
                multiply = stockInfo.getTheTotalInventory().multiply(productById.getProdPrice());//入库总数*单价=总价
            } else if (productById.getStandardStatus() == 1) {//非表品
                if(productById.getProdPrice()==null){
                    productById.setProdPrice(new BigDecimal(0));
                }
                BigDecimal add = productById.getProdPrice().add(productById.getProdPrice());
                multiply = stockInfo.getTheTotalInventory().multiply(add);//入库总数*单价*2=总价
            }
            productById.setTotalPrices(multiply);//总价
            productInfos.add(productById);
        }
        for (ProductInfo productInfo : productInfos) {
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productInfo.getProdID());
            BigDecimal bigDecimal1 = stockDetailService.sumStockDetailByWeightAndPrint(stockInfoByProdID.getStockID(), ActionUtil.getDateStrForString(parse), Integer.toString(allConfiguration.getPrintStatusNO()));
            if (bigDecimal1 == null) {
                productInfo.setPrint("已打印");
            } else {
                productInfo.setPrint("未打印");
            }
        }
        request.getSession().setAttribute("productInfos", productInfos);
        request.getSession().setAttribute("thatDay", date);
        request.getSession().setAttribute("Day", ActionUtil.getDateStrForString(parse));
        return "signing/signingPrintList";
    }

    /**
     * @功能说明：选择打印信息
     * @开发时间：2022-01-15
     * @开发人员：张涛
     */
    @RequestMapping("selectPrintInfo")
    public String selectPrintInfo(String viewZoomConfigStr, String Day, String thatDay) {
        log.info("签单管理>>展示打印信息");
        log.info("SigningInfoController>>selectPrintInfo");
        log.info("产品编号>>" + viewZoomConfigStr + ">>时间>>" + Day + "时间2" + thatDay);
        String[] split = viewZoomConfigStr.split(",");
        Set<ProductInfo> productInfos = new HashSet<>();
        for (String s : split) {
            System.out.println("分割后的产品编号=" + s);
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(s);
            ProductInfo productById = productInfoService.findProductById(s);
            BigDecimal bigDecimal = stockDetailService.sumStockDetailByWeight(stockInfoByProdID.getStockID(), Day);
            productById.setBePutInStorage(bigDecimal);//总数
            BigDecimal multiply = null;
            if (productById.getStandardStatus() == 0) {//标品
                multiply = bigDecimal.multiply(productById.getProdPrice());//入库总数*单价=总价
            } else if (productById.getStandardStatus() == 1) {//非表品
                if (productById.getProdPrice() == null) {
                    productById.setProdPrice(new BigDecimal(0));
                }
                BigDecimal add = productById.getProdPrice().add(productById.getProdPrice());
                multiply = bigDecimal.multiply(add);//入库总数*单价*2=总价
            }
            productById.setTotalPrices(multiply);//总价
            productInfos.add(productById);
        }
        //分页
        List<ProductInfo> list = new ArrayList<>();
        for (ProductInfo productInfo : productInfos) {
            //拿到明细的最新时间
            System.out.println("day=>>" + Day);
            System.out.println("stockID=>>" + stockInfoService.findStockInfoByProdID(productInfo.getProdID()).getStockID());
            List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDate(stockInfoService.findStockInfoByProdID(productInfo.getProdID()).getStockID(), Day);
            if (stockDetails.size() != 0) {
                System.out.println("长度= = " + stockDetails.size());
                System.out.println("vv>>" + stockDetails.get(0));
                StockDetail stockDetail = stockDetails.get(0);
                productInfo.setLatestTime(stockDetail.getOperationTime());
                productInfo.setLatestTimeStr(ActionUtil.getDateStrForDayChinese(stockDetail.getOperationTime()));
                list.add(productInfo);
            }
        }

        List<ProductInfo> prodList = new ArrayList<>(productInfos);
        getLatestTime(prodList);
        upDownPage(1, prodList);

        request.getSession().setAttribute("sdf5", ActionUtil.getDateStrForStringwithNoLiine(new Date()));
        request.getSession().setAttribute("thatDay", thatDay);
        request.getSession().setAttribute("Day", Day);
        request.getSession().setAttribute("viewZoomConfigStr", viewZoomConfigStr);
        return "signing/signingPrint";
    }

    /**
     * @功能说明：上下页
     * @开发时间：2022-01-17
     * @开发人员：张涛
     */
    @RequestMapping("upDownPrintPage")
    public String upDownPrintPage(String pageSize, String currentPage, String viewZoomConfigStr, String Day) {
        log.info("pageSize=" + pageSize);
        log.info("currentPage=" + currentPage);
        String[] split = viewZoomConfigStr.split(",");
        Set<ProductInfo> productInfos = new HashSet<>();
        for (ProductInfo productInfo : productInfos) {
            //拿到明细的最新时间
            List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDate(stockInfoService.findStockInfoByProdID(productInfo.getProdID()).getStockID(), Day);
            StockDetail stockDetail = stockDetails.get(0);
            productInfo.setLatestTime(stockDetail.getOperationTime());
            productInfo.setLatestTimeStr(ActionUtil.getDateStrForDayChinese(stockDetail.getOperationTime()));
        }
        for (String s : split) {
            System.out.println("分割后的产品编号=" + s);
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(s);
            ProductInfo productById = productInfoService.findProductById(s);
            BigDecimal bigDecimal = stockDetailService.sumStockDetailByWeight(stockInfoByProdID.getStockID(), Day);
            productById.setBePutInStorage(bigDecimal);//总数
            BigDecimal multiply = null;
            if (productById.getStandardStatus() == 0) {//标品
                multiply = bigDecimal.multiply(productById.getProdPrice());//入库总数*单价=总价
            } else if (productById.getStandardStatus() == 1) {//非表品
                if (productById.getProdPrice() == null) {
                    productById.setProdPrice(new BigDecimal(0));
                }
                BigDecimal add = productById.getProdPrice().add(productById.getProdPrice());
                multiply = bigDecimal.multiply(add);//入库总数*单价*2=总价
            }
            productById.setTotalPrices(multiply);//总价
            productInfos.add(productById);
        }

        List<ProductInfo> prodList = new ArrayList<>(productInfos);
        getLatestTime(prodList);
        upDownPage(Integer.parseInt(currentPage), prodList);
        return "signing/signingPrint";
    }


    //页面中需要最新时间，所以用这个来取一下，放在集合第一个中带过去。
    private void getLatestTime(List<ProductInfo> prodList) {
        Date latestTime = new Date(5000);
        System.out.println("产品数量=" + prodList.size());
        for (ProductInfo prod : prodList) {
            if (prod.getLatestTime() != null && latestTime.getTime() < prod.getLatestTime().getTime()) {
                latestTime = prod.getLatestTime();
            }
        }
        prodList.get(0).setLatestTime(latestTime);
        prodList.get(0).setLatestTimeStr(ActionUtil.getDateStr(latestTime));
    }

//    public void upDownPage(int page, Set<ProductInfo> productInfos) {
//        PageModel pageModel = commonUtil.showPagePrint(page, productInfos.size(), "selectPrintInfo");
//        List<ProductInfo> list = new ArrayList<>();
//        for (ProductInfo productInfo : productInfos) {
//            list.add(productInfo);
//        }
//        Map map = groupList(list);
//        int pages = page - 1;
//
//        String untitName = null;
//        List<DeptInfo> infoList = null;
//        try {
//            List<UnitInfo> unitInfos = unitInfoService.listUnitInfo();
//            untitName = unitInfos.get(0).getUntitName();
//            infoList = new ArrayList<>();
//            List<DeptInfo> list1 = deptInfoService.listDeptInfoBySianFlag();
//            for (DeptInfo deptInfo : list1) {
//                if (deptInfo.getSort() == 1) {
//                    infoList.add(deptInfo);
//                }
//                if (deptInfo.getSort() == 2) {
//                    infoList.add(deptInfo);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        request.getSession().setAttribute("infoList", infoList);//部门编号
//        request.getSession().setAttribute("untitName", untitName);//单位名称
//        request.getSession().setAttribute("productInfos", map.get("keyName" + pages));
//        request.getSession().setAttribute("page", pageModel);
//    }

    public void upDownPage(int page, List<ProductInfo> productInfos) {
        PageModel pageModel = commonUtil.showPagePrint(page, productInfos.size(), "selectPrintInfo");
        System.out.println("proid==" + productInfos.size());
        List<ProductInfo> list = new ArrayList<>();
        for (ProductInfo productInfo : productInfos) {
            list.add(productInfo);
        }

        List list2 = startPage(list, page, 20);
        System.out.println("list2>>" + list2.size());
        if (list2.size() < 20) {
            int i = 20 - list2.size();
            for (int j = 0; j < i; j++) {
                list2.add(new ProductInfo());
            }
        }
//        Map map = groupList(list);
//        int pages = page - 1;

        String untitName = null;
        List<DeptInfo> infoList = null;
        try {
            List<UnitInfo> unitInfos = unitInfoService.listUnitInfo();
            untitName = unitInfos.get(0).getUntitName();
            infoList = new ArrayList<>();
            List<DeptInfo> list1 = deptInfoService.listDeptInfoBySianFlag();
            for (DeptInfo deptInfo : list1) {
                if (deptInfo.getSort() == 1) {
                    infoList.add(deptInfo);
                }
                if (deptInfo.getSort() == 2) {
                    infoList.add(deptInfo);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        List<ProductInfo> productInfos1 = list2;
        System.out.println("prod==" + productInfos1.size());
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (ProductInfo productInfo : productInfos1) {
            if (productInfo.getTotalPrices() != null) {
                bigDecimal = bigDecimal.add(productInfo.getTotalPrices());//总价相加
            }
        }
        System.out.println("总价>>" + bigDecimal);
        request.getSession().setAttribute("pageAmount", bigDecimal);
        request.getSession().setAttribute("infoList", infoList);//部门编号
        request.getSession().setAttribute("untitName", untitName);//单位名称
        request.getSession().setAttribute("productInfos", list2);
        request.getSession().setAttribute("page", pageModel);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map groupList(List list) {
        int listSize = list.size();
        int maxLen = 20;
        int toIndex = maxLen;
        Map map = new HashMap();     //用map存起来新的分组后数据
        int keyToken = 0;
        List resultList;
        for (int i = 0; i < list.size(); i += maxLen) {
            if (i + maxLen > listSize) {        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = listSize - i;
            }
            if (list.size() > (i + toIndex)) {
                resultList = list.subList(i, i + toIndex);

            } else {
                resultList = list;
            }
            int leftCount = maxLen - listSize;
            for (int j = 0; j < leftCount; j++) {
                resultList.add(new ProductInfo());
            }
            map.put("keyName" + keyToken, resultList);
            keyToken++;
        }

        return map;
    }


    public List startPage(List list, Integer pageNum, Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }


    /**
     * @功能说明：清单打印部分
     * @开发时间：2022-01-15
     * @开发人员：张涛
     */
    @RequestMapping("printInfo")
    public String printInfo(@RequestParam(required = false) String prodID, String Day, String thatDay) {
        log.info("签单管理>>展示打印信息");
        log.info("SigningInfoController>>printInfo");
        log.info("产品编号>>" + prodID + ">>选择打印时间>>" + Day);
        String[] split = prodID.split(",");
        Set<ProductInfo> productInfos = new HashSet<>();
        for (String s : split) {
            System.out.println("分割后的产品编号=" + s);
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(s);
            ProductInfo productById = productInfoService.findProductById(s);
            List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDate(stockInfoByProdID.getStockID(), Day);
            for (StockDetail stockDetail : stockDetails) {
                stockDetail.setPrint(allConfiguration.getPrintStatusOK());//打印状态
                stockDetailService.updateStockDetail(stockDetail);
            }
            BigDecimal bigDecimal1 = stockDetailService.sumStockDetailByWeightAndPrint(stockInfoByProdID.getStockID(), Day, Integer.toString(allConfiguration.getPrintStatusNO()));
            if (bigDecimal1 == null) {
                productById.setPrint("已打印");
            } else {
                productById.setPrint("未打印");
            }
            BigDecimal bigDecimal = stockDetailService.sumStockDetailByWeight(stockInfoByProdID.getStockID(), Day);
            productById.setBePutInStorage(bigDecimal);//总数

            BigDecimal multiply = null;
            if (productById.getStandardStatus() == 0) {//标品
                multiply = bigDecimal.multiply(productById.getProdPrice());//入库总数*单价=总价
            } else if (productById.getStandardStatus() == 1) {//非表品
                BigDecimal add = productById.getProdPrice().add(productById.getProdPrice());
                multiply = bigDecimal.multiply(add);//入库总数*单价*2=总价
            }
            productById.setTotalPrices(multiply);//总价
            productInfos.add(productById);
        }
        request.getSession().setAttribute("productInfos", productInfos);
        request.getSession().setAttribute("thatDay", thatDay);
        request.getSession().setAttribute("Day", Day);
        return "signing/signingPrintList";
    }

    /**
     * @模块说明：跳转签单拍照上传页面
     * @创建时间：2021-1-14
     * @开发人员：张涛
     */
    @RequestMapping("goSigningImg")
    public String goSigningImg(@RequestParam(required = false) String userID) {
        log.info("SigningInfoController>>goSigningImg");
        log.info("userID=" + userID);
        MemberInfo memberInfoByJobID = memberInfoService.findMemberInfoByJobID(userID);
        request.getSession().setAttribute("memberInfoByJobID", memberInfoByJobID);
        return "signing/addSigning";
    }


    /**
     * @模块说明：签单拍照上传
     * @创建时间：2021-1-14
     * @开发人员：张涛
     */
    @RequestMapping("addSigningImg")
    public String addSigningImg(@RequestParam("signingFile") MultipartFile signingFile) {
        log.info("pad签单拍照上传");
        String userID = request.getParameter("userID");
        String remarks = request.getParameter("remarks");
        log.info("图片文件>>" + signingFile + ">>工号>>" + userID + ">>备注>>" + remarks);
        MemberInfo memberInfoByJobID = memberInfoService.findMemberInfoByJobID(userID);
        SigningInfo signingInfo = new SigningInfo();
        ApiResult apiResult = new ApiResult();
        if (signingFile != null) {
            String originalFilename = signingFile.getOriginalFilename();
            String file1 = getClass().getResource("/static/").getFile();
            String s = getClass().getResource("/static/").getFile();
            System.out.println("路径>>" + s);
            File file = new File(s + originalFilename);
            OutputStream out = null;
            try {
                //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = signingFile.getBytes();
                for (int i = 0; i < ss.length; i++) {
                    out.write(ss[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(file.toURI());
            System.out.println(file);
            System.out.println(file.getName());
            String dateStrForString = "S" + ActionUtil.getDateStrForString(new Date()) + ActionUtil.getRandomString(2);
            signingInfo.setSigningID(dateStrForString);//签单编号
            signingInfo.setAdder(userID);
            signingInfo.setAddDate(new Date());
            signingInfo.setAddTime(new Date());
            signingInfo.setUnitID(memberInfoByJobID.getUnitID());//归属单位
            signingInfo.setSigImg(file.getName());
            signingInfo.setSigImgFile(file);
            signingInfo.setRemarks(remarks);
            int i = signingInfoService.addSigningInfo(signingInfo);
            if (i > 0) {
                addSigningImg(signingInfo);
                apiResult.setResult(200);
                apiResult.setMsg("上传签单照片成功");
            } else {
                apiResult.setResult(500);
                apiResult.setMsg("上传签单照片失败");
            }
        }
        int i = signingInfoService.countSigningInfo();
        signingInfoPage(1, i, "SigningInfoController>>addSigningImg");
        request.getSession().setAttribute("memberInfoByJobID", memberInfoByJobID);
        return "signing/signingList";
    }

    /**
     * @模块说明：添加签单图片功能
     * @创建时间：2021-01-14
     * @开发人员：张涛
     */
    private void addSigningImg(SigningInfo signingInfo) {
        System.out.println("StockController.addImg()");
        System.out.println("SigImgFile=" + signingInfo.getSigImgFile());
        System.out.println("上传图片::" + signingInfo.getSigImgFile().length());
        if (signingInfo.getSigImgFile().length() > 819200) {
            PrintWriter out = null;
            //ActionUtil.sendMessege(response, "图片超过了800kb");
        }
        InputStream is;
        try {
            is = new FileInputStream(signingInfo.getSigImgFile());
            // 获得食材编号为图片命名，更新到数据库
            System.out.println("签单编号：" + signingInfo.getSigningID());
            System.out.println("明细图片文件名：" + signingInfo.getSigImg());
            System.out.println("明细图片文件名2：" + signingInfo.getSigImg().split("\\.")[1]);
            signingInfo.setSigImg("img/shopImg/" + signingInfo.getSigningID() + "." + signingInfo.getSigImg().split("\\.")[1]);
//            stockDetail.setPhotoImg(stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
            System.out.println("prodImg=" + signingInfo.getSigImg());
            System.out.println(signingInfoService.updateSigningInfo(signingInfo) ? "修改图片成功" : "修改图片失败");
            String file = getClass().getResource("/static/").getFile();
            System.out.println("file" + file);
            File destFile = new File(file, signingInfo.getSigImg());
            //先刪除存在的图片，在写入
            if (destFile != null) {
                destFile.delete();
            }
            OutputStream os = new FileOutputStream(destFile);
            byte[] buffer = new byte[400];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @功能说明：签单分页查询
     * @开发时间：2022-01-14
     * @开发人员：张涛
     */
    public List<SigningInfo> signingInfoPage(int page, int current, String locahost) {
        PageModel pageModel = commonUtil.showPage(page, current, locahost);
        List<SigningInfo> signingInfos = signingInfoService.listSigningInfoPage(pageModel);
        for (SigningInfo signingInfo : signingInfos) {
            signingInfo.setAddTimeStr(ActionUtil.getDateStrForDayChinese(signingInfo.getAddTime()));//设置添加时间
            signingInfo.setAdder(memberInfoService.findMemberInfoByJobID(signingInfo.getAdder()).getMemberName());
            signingInfo.setUnitID(unitInfoService.findUnitInfoByUnitID(signingInfo.getUnitID()).getUntitName());//归属名称
        }
        request.getSession().setAttribute("signingInfos", signingInfos);
        request.getSession().setAttribute("page", pageModel);
        return signingInfos;
    }
}
