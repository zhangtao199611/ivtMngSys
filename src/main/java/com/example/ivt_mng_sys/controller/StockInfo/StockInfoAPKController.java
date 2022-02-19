package com.example.ivt_mng_sys.controller.StockInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ivt_mng_sys.Util.*;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.ProductInfo;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.ClassInfoService;
import com.example.ivt_mng_sys.service.ProductInfoService;
import com.example.ivt_mng_sys.service.StockDetailService;
import com.example.ivt_mng_sys.service.StockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @模块说明：安卓库存交易
 * @创建时间：2021-12-26
 * @开发人员：张涛
 * @非标品接口
 */
@Controller
@RequestMapping("StockInfoAPKController")
public class StockInfoAPKController {
    @Resource
    private StockInfoService stockInfoService;
    @Resource
    private StockDetailService stockDetailService;
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;


//    private String filePage = "home/ah/static/img/intruderImg";//本地路径
//    private String fileRelativaPage = "/static/**";//虚拟路径
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();

    @Autowired
    public StockInfoAPKController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }

    Logger logger = LoggerFactory.getLogger(StockInfoAPKController.class);

    /**
     * @param :productID=食材编号，weight=重量，remarks=备注,userID=用户编号，code=操作状态,multipartFile=图片,devID=设备编号
     * @模块说明：库存
     * @创建时间：2021-12-27
     * @开发人员：张涛
     */
    @RequestMapping("addListInventory")
    @ResponseBody
    public ApiResult addListInventory(@RequestParam(required = false) String productID, String weight, String userID, String code, String remarks, MultipartFile multipartFile, String devID, String adjustWeight, String status) {
        logger.info("StockController>>addListInventory");
        logger.info("库存管理>>添加库存");
        logger.info("食材编号>>" + productID + ">>实际重量>>" + weight + ">>调整重量>>" + adjustWeight + ">>用户名=" + userID + ">>状态>>" + code + ">>备注>>" + remarks + ">>设备编号>>" + devID + ">>图片文件>>" + multipartFile + ">>调整重量>>" + adjustWeight + ">>明细状态>>" + status);
        ApiResult apiResult = new ApiResult();
        if (weight.equals("0.000")){
            apiResult.setResult(500);
            apiResult.setResultData(null);
            apiResult.setMsg("请放上货物！");
            return apiResult;
        }
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productID);
        if (code.equals(allConfiguration.getStockDetailTypeOut()) && status.equals(allConfiguration.getStockDetailTypeStatusPrestore())){
            boolean b = ifPredictStock(stockInfoByProdID.getStockID(), weight);
            if (b==false){
                ApiResult apiResult1 = new ApiResult();
                apiResult1.setResult(500);
                apiResult1.setMsg("预库存已超过总库存数！");
                return apiResult1;
            }
        }
        if (code.equals(allConfiguration.getStorage())) {//入库状态
            ApiResult storage = getStorageFunction(productID, weight, code, userID, remarks, multipartFile, devID, adjustWeight, status);
            System.out.println("apiResult="+storage.getResultData());
            return storage;
        }
        if (code.equals(allConfiguration.getOutStorage())) {//出库状态
            ApiResult putsFunction = getPutsFunction(productID, weight, userID, remarks, devID, multipartFile, adjustWeight, status,code);
            return putsFunction;
        }
        if (code.equals(allConfiguration.getLoss())) {//损耗状态
            ApiResult lossFunction = getLossFunction(productID, weight, userID, remarks, devID);
            return lossFunction;
        }
        if (code.equals(allConfiguration.getStockDetailReturns())) {//退货状态
            ApiResult putsFunction = getPutsFunction(productID, weight, userID, remarks, devID, multipartFile, adjustWeight, status,code);
            return putsFunction;
        }
        return apiResult;
    }

    /**
     * @模块说明：接收图片接口
     * @创建时间：2021-1-10
     * @开发人员：张涛
     */
    @RequestMapping("addStockImg")
    @ResponseBody
    public ApiResult addStockImg(@RequestParam("multipartFile") MultipartFile multipartFile){
        String stockDetailID = request.getParameter("stockDetailID");//库存明细编号
        logger.info("库存明细编号>>"+stockDetailID+">>图片>>"+multipartFile);
        ApiResult apiResult = new ApiResult();
        StockDetail stockDetail = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        if (multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String s = getClass().getResource("/static/").getFile();
            File file = new File(s + originalFilename);
            OutputStream out = null;
            try {
                //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = multipartFile.getBytes();
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
            stockDetail.setPhotoImg(file.getName());//出入库照片
            stockDetail.setPhotoImgFile(file);
        }
        addImg(stockDetail);
        apiResult.setResult(200);
        apiResult.setMsg("成功");
        return apiResult;
    }



    /**
     * @模块说明：展示当日出入预库存
     * @创建时间：2021-1-3
     * @开发人员：张涛
     */
    @RequestMapping("listStockDetail")
    @ResponseBody
    public ApiResult listStockDetail(@RequestParam(required = false) String status) {
        logger.info("StockController>>listStockDetail");
        logger.info("库存管理>>展示当日预库存");
        logger.info("状态>>" + status);
        ApiResult apiResult = new ApiResult();
        List<StockDetail> stockDetails = null;
        if (status.equals(allConfiguration.getStockDetailTypeOut())) {//出库
            stockDetails = stockDetailService.listStockDetailByStockDetailTypeAndStockDateAndStatus(allConfiguration.getStockDetailTypeOut(), ActionUtil.getDateStrForString(new Date()));
            System.out.println(stockDetails.size());
        }
        if (status.equals(allConfiguration.getStockDetailTypeStorage())) {//入库
            stockDetails = stockDetailService.listStockDetailByStockDetailTypeAndStockDateAndStatus(allConfiguration.getStockDetailTypeStorage(), ActionUtil.getDateStrForString(new Date()));
        }
        Iterator<StockDetail> iterator = stockDetails.iterator();
        while (iterator.hasNext()){
            StockDetail next = iterator.next();
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(next.getStockID()).getProdID()).getStandardStatus()==0){
                iterator.remove();
            }
        }
        try {
            for (StockDetail stockDetail : stockDetails) {
                stockDetail.setOperationTimeStr(ActionUtil.getDateStr(stockDetail.getOperationTime()));//出入库损耗时间字符串
                stockDetail.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());//产品名称
            }
            apiResult.setResultData(stockDetails);
            apiResult.setResult(200);
        } catch (Exception e) {
            logger.info("没有数据=" + e);
            apiResult.setResultData(stockDetails);
            apiResult.setResult(500);
        }
        return apiResult;
    }

    /**
     * @模块说明：展示已有库存的数量
     * @创建时间：2021-1-3
     * @开发人员：张涛
     */
    @RequestMapping("showTheQuantityOfExistingStock")
    @ResponseBody
    public ApiResult showTheQuantityOfExistingStock() {
        logger.info("StockController>>showTheQuantityOfExistingStock");
        logger.info("库存管理>>展示已有库存的数量");
        ApiResult apiResult = new ApiResult();
        List<StockInfo> stockInfos = stockInfoService.listStockInfoByStockCountNEQ0();
        for (StockInfo stockInfo : stockInfos) {
            String stockID = stockInfo.getStockID();
            System.out.println("stock=" + stockID);
            stockInfo.setProdID(productInfoService.findProductById(stockInfo.getProdID()).getProdName());//设置产品名称
        }
        if (stockInfos.size() != 0) {
            apiResult.setResult(200);
            apiResult.setResultData(stockInfos);
        } else {
            apiResult.setResult(500);
            apiResult.setResultData(stockInfos);
        }
        return apiResult;
    }

    /**
     * @模块说明：批量入库
     * @创建时间：2021-1-3
     * @开发人员：张涛
     */
    @RequestMapping("bulkStorage")
    @ResponseBody
    public ApiResult bulkStorage(@RequestBody JSONObject stockDetails) {
        logger.info("StockController>>showTheQuantityOfExistingStock");
        logger.info("库存管理>>展示已有库存的数量");
        logger.info("批量入库数据量>>" + stockDetails.getString("stockDetails"));
        JSONArray stockDetailss = stockDetails.getJSONArray("stockDetails");
        List<StockDetail> stockDetails1 = stockDetailss.toJavaList(StockDetail.class);
        System.out.println("stock="+stockDetails1.size());
        ApiResult apiResult = new ApiResult();
        DecimalFormat decimalFormat = new DecimalFormat("0000.000");
        for (StockDetail stockDetail : stockDetails1) {
            if (stockDetail.getStatus()==Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore())){
                StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailService.findStockDetailByStockDetailID(stockDetail.getStockDetailID()).getStockID());
                BigDecimal stockCount = stockInfoByStockID.getStockCount();//库存重量
                BigDecimal weight = stockDetail.getWeight();//入库实际重量
                stockInfoByStockID.setStockCount(new BigDecimal(decimalFormat.format(stockCount.add(weight))));//相加的库存重量
                stockInfoByStockID.setUpdateDate(new Date());//更新时间
                stockInfoByStockID.setLatestInTime(new Date());//最近入库时间
                stockDetail.setStockID(stockInfoByStockID.getStockID());//库存编号
                int i = stockInfoService.updateStockInfo(stockInfoByStockID);
                if (i>0){
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect()));//生效
                    stockDetail.setOperationTime(new Date());//入库时间
                    boolean b = stockDetailService.updateStockDetail(stockDetail);//修改状态
                    if (b==true){
                        apiResult.setResult(200);
                        apiResult.setResultData(null);
                    }else {
                        apiResult.setResult(500);
                        apiResult.setResultData(null);
                    }
                }else{
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore()));//预存
                    stockDetailService.updateStockDetail(stockDetail);//修改状态
                    apiResult.setResult(500);
                    apiResult.setResultData(null);
                }
            }
        }
        return apiResult;
    }

    /**
     * @模块说明：批量出库
     * @创建时间：2021-1-5
     * @开发人员：张涛
     */
    @RequestMapping("bulkOutStorage")
    @ResponseBody
    public ApiResult bulkOutStorage(@RequestBody JSONObject stockDetails) {
        logger.info("StockController>>bulkOutStorage");
        logger.info("库存管理>>批量出库");
        logger.info("批量出库数据量>>" + stockDetails.getString("stockDetails"));
        JSONArray stockDetailss = stockDetails.getJSONArray("stockDetails");
        List<StockDetail> stockDetails1 = stockDetailss.toJavaList(StockDetail.class);
        System.out.println("stock="+stockDetails1.size());
        ApiResult apiResult = new ApiResult();
        DecimalFormat decimalFormat = new DecimalFormat("0000.000");
        for (StockDetail stockDetail : stockDetails1) {
            if (stockDetail.getStatus()==Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore())){
                StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailService.findStockDetailByStockDetailID(stockDetail.getStockDetailID()).getStockID());
                BigDecimal stockCount = stockInfoByStockID.getStockCount();//库存重量
                BigDecimal weight = stockDetail.getWeight();//出库实际重量
                BigDecimal bigDecimal = new BigDecimal(decimalFormat.format(stockCount.subtract(weight)));
                String format = decimalFormat.format(bigDecimal);
                if (!format.substring(0,1).equals("-")){
                    System.out.println("from=="+format.substring(0,1));
                    stockInfoByStockID.setStockCount(new BigDecimal(decimalFormat.format(stockCount.subtract(weight))));//相减的库存重量
                }else {
                    continue;
                }
                stockInfoByStockID.setUpdateDate(new Date());//更新时间
                stockInfoByStockID.setLatestInTime(new Date());//最近入库时间
                stockDetail.setStockID(stockInfoByStockID.getStockID());//库存编号
                int i = stockInfoService.updateStockInfo(stockInfoByStockID);
                if (i>0){
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect()));//生效
                    stockDetail.setOperationTime(new Date());//入库时间
                    boolean b = stockDetailService.updateStockDetail(stockDetail);//修改状态
                    if (b==true){
                        apiResult.setResult(200);
                        apiResult.setResultData(null);
                    }else {
                        apiResult.setResult(500);
                        apiResult.setResultData(null);
                    }
                }else{
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore()));//预存
                    stockDetailService.updateStockDetail(stockDetail);//修改状态
                    apiResult.setResult(500);
                    apiResult.setResultData(null);
                }
            }
        }
        return apiResult;
    }

    /**
     * @模块说明：查询已有库存产品的类别
     * @创建时间：2021-1-4
     * @开发人员：张涛
     */
    @RequestMapping("listStockAndClass")
    @ResponseBody
    public ApiResult listStockAndClass(){
        logger.info("StockController>>listStockAndClass");
        logger.info("库存管理>>查询已有库存产品的类别");
        List<StockInfo> stockInfos = stockInfoService.listStockInfo();
        Set<ClassInfo> set  = new HashSet<>();
        ApiResult apiResult = new ApiResult();
        for (StockInfo stockInfo : stockInfos) {
            System.out.println("库存="+stockInfo.getStockCount());
            if (stockInfo.getStockCount()!=null || !stockInfo.getStockCount().equals("0.000")){
                ProductInfo productById = productInfoService.findProductById(stockInfo.getProdID());
                ClassInfo classInfoById = classInfoService.findClassInfoById(productById.getClassID());
                set.add(classInfoById);
            }
        }
        apiResult.setResultData(set);
        apiResult.setResult(200);
        return apiResult;
    }

    /**
     * @模块说明：根据类别编号查询库存数据
     * @创建时间：2021-1-4
     * @开发人员：张涛
     */
    @RequestMapping("listStockByClassID")
    @ResponseBody
    public ApiResult listStockByClassID(String classID){
        logger.info("StockController>>listStockAndClass");
        logger.info("库存管理>>查询已有库存产品的类别");
        logger.info("类别编号>>"+classID);
        ApiResult apiResult = new ApiResult();
        List<ProductInfo> list  = new ArrayList<>();
        List<ProductInfo> productInfoByClassID = productInfoService.findUnStandardProductByClassID(classID);
        for (ProductInfo productInfo : productInfoByClassID) {
            if (productInfo.getStandardStatus()==1){
                StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productInfo.getProdID());
                if (stockInfoByProdID!=null && stockInfoByProdID.getStockCount()!=null && !(stockInfoByProdID.getStockCount().compareTo(BigDecimal.ZERO)<=0)){
                    productInfo.setStockID(stockInfoByProdID.getStockID());//库存编号
                    productInfo.setStockCount(stockInfoByProdID.getStockCount());//库存
                    list.add(productInfo);
                }
            }
        }
        apiResult.setResultData(list);
        apiResult.setResult(200);
        return apiResult;
    }


    /**
     * @模块说明：实时修改预库存数据
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("updatePrestoreStock")
    @ResponseBody
    public ApiResult updatePrestoreStock(@RequestParam(required = false)String stockDetailID,String adjustWeight){
        logger.info("StockController>>addStockDetail");
        logger.info("库存管理>>实时修改预库存数据");
        logger.info("库存明细编号>>" + stockDetailID+">>调整重量>>"+adjustWeight);
        ApiResult apiResult = new ApiResult();
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        stockDetailByStockDetailID.setAdjustWeight(new BigDecimal(adjustWeight));//调整重量
        BigDecimal weighingWeight = stockDetailByStockDetailID.getWeighingWeight();//秤重重量
        stockDetailByStockDetailID.setWeight(weighingWeight.subtract(new BigDecimal(adjustWeight)));//实际重量
        boolean b = stockDetailService.updateStockDetail(stockDetailByStockDetailID);
        if (b==true){
            apiResult.setResult(200);
            apiResult.setMsg("已修改成功");
        }else {
            apiResult.setResult(500);
            apiResult.setMsg("失败");
        }
        return apiResult;
    }
    /**
     * @功能说明：根据首字母查询菜品
     * @创建时间：2021-1-12
     * @开发人员：张涛
     */
    @RequestMapping("getLettersQuery")
    @ResponseBody
    public ApiResult getLettersQuery(@RequestParam(required = false)String letter,String code){
        logger.info("StockInfoAPKController>>getLettersQuery");
        logger.info("库存管理>>根据汉字获取首字母");
        logger.info("字母>>"+letter+">>状态 1：入库/0：出库>>"+code);
        ApiResult apiResult = new ApiResult();
        if (letter==null || letter.length()==0){
            apiResult.setResult(500);
            apiResult.setResultData(null);
            apiResult.setMsg("为空");
            return apiResult;
        }
        logger.info("字母"+letter+"转换小写>>"+letter.toLowerCase());
        letter.toLowerCase();
        List<ProductInfo> productInfos = productInfoService.listProductInfoByLikeSpellingName(letter.toLowerCase());
        if (code.equals(allConfiguration.getStockDetailTypeStorage())){//入库
            apiResult.setResult(200);
            apiResult.setResultData(productInfos);
            apiResult.setMsg("成功！");
        }
        if (code.equals(allConfiguration.getStockDetailTypeOut())){//出库
            Iterator<ProductInfo> iterator = productInfos.iterator();
            while (iterator.hasNext()){
                ProductInfo next = iterator.next();
                StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(next.getProdID());
                if (stockInfoByProdID==null || stockInfoByProdID.getStockCount().compareTo(new BigDecimal("0"))<=0){
                    iterator.remove();
                }else {
                    next.setStockCount(stockInfoByProdID.getStockCount());
                }
            }
            apiResult.setResult(200);
            apiResult.setResultData(productInfos);
            apiResult.setMsg("成功！");
        }
        for (ProductInfo productInfo : productInfos) {
            System.out.println("名称="+productInfo.getProdName());
        }
        return apiResult;
    }

    /**
     * @功能说明：判断预出库库存是否大于实际库存
     * @创建时间：2021-1-9
     * @开发人员：张涛
     */
    public boolean ifPredictStock(String stockID, String weight) {
        logger.info("StockInfoAPKStandardController>>ifPredictStock");
        logger.info("库存列表>>判断预出库库存是否大于实际库存");
        logger.info("库存编号>>" + stockID + ">>入库数量>>" + weight);
        List<StockDetail> stockDetails = stockDetailService.listIfStockDetailByIDAndTypeAndDateAndStatus(stockID, allConfiguration.getStockDetailTypeOut(), ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusPrestore());
        if (stockDetails.size()==0){
            return true;
        }
        System.out.println("数量="+stockDetails.size());
        for (StockDetail stockDetail : stockDetails) {
            System.out.println("stock="+stockDetail.getWeight());
        }
        BigDecimal bigDecimal = null;
        for (StockDetail stockDetail : stockDetails) {
            if (bigDecimal == null) {
                bigDecimal = stockDetail.getWeight();
            } else {
                bigDecimal = bigDecimal.add(stockDetail.getWeight());
            }
        }
        System.out.println("相加="+bigDecimal);
        BigDecimal add = bigDecimal.add(new BigDecimal(weight));
        System.out.println("总数="+add);
        StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockID);
        if (stockInfoByStockID.getStockCount().compareTo(add) < 0) {
            return false;
        }else {
            return true;
        }
    }


    /**
     * @模块说明：添加交易记录
     * @创建时间：2021-12-26
     * @开发人员：张涛
     */
    public StockDetail addStockDetail(String str, String stockID, String code, String userID, BigDecimal count, String remarks, MultipartFile multipartFile, String devID, String adjustWeight, String status) {
        logger.info("StockController>>addStockDetail");
        logger.info("库存管理>>添加交易记录");
        logger.info("库存编号>>" + str + ">>食材编号>>" + stockID + ">>明细类型>>" + code + ">>用户名=" + userID + ">>状态>>" + code + ">>备注>>" + remarks + ">>当前存入数量>>" + count + ">>调整重>>" + adjustWeight + ">>明细状态>>" + status);
        StockDetail stockDetail = new StockDetail();
        //库存明细编号定义，库存编号加五位随机数
        String stockId = str + ActionUtil.getRandomString(4);
        stockDetail.setStockDetailID(stockId);//库存明细编号
        stockDetail.setStockID(str);//库存编号
        if (code.equals(allConfiguration.getStockDetailTypeStorage())) { //入库
            stockDetail.setStockDetailType(Integer.parseInt(allConfiguration.getStockDetailTypeStorage()));//交易类型入库
            DecimalFormat decimalFormat = new DecimalFormat("0000.000");
            if (!adjustWeight.equals("0")) {
                System.out.println("adju=" + adjustWeight);
                BigDecimal bigDecimal = new BigDecimal(adjustWeight);
                stockDetail.setAdjustWeight(bigDecimal);//调整重量
                BigDecimal subtract = count.subtract(bigDecimal);//上秤重量-调整重量=实际重量
                stockDetail.setWeight(new BigDecimal(decimalFormat.format(subtract)));//实际数量
            } else {
                stockDetail.setWeight(count);//实际重量
                stockDetail.setAdjustWeight(new BigDecimal(0));//调整重量
            }
            stockDetail.setWeighingWeight(count);//秤重重量
        }
        if (code.equals(allConfiguration.getStockDetailTypeOut())) {//出库
            stockDetail.setStockDetailType(Integer.parseInt(allConfiguration.getStockDetailTypeOut()));//交易类型出库
            stockDetail.setAdjustWeight(new BigDecimal(0));//调整重量
            stockDetail.setWeighingWeight(count);//秤重重量
            stockDetail.setWeight(count);//数量
        }
        if (code.equals(allConfiguration.getStockDetailTypeLoss())) {//损耗
            stockDetail.setStockDetailType(Integer.parseInt(allConfiguration.getStockDetailTypeLoss()));//交易类型损耗
            stockDetail.setWeight(count);//数量
        }
        if (code.equals(allConfiguration.getStockDetailReturns())) {//退货
            stockDetail.setStockDetailType(Integer.parseInt(allConfiguration.getStockDetailReturns()));//交易类型出库
            stockDetail.setAdjustWeight(new BigDecimal(0));//调整重量
            stockDetail.setWeighingWeight(count);//秤重重量
            stockDetail.setWeight(count);//数量
        }
        stockDetail.setOperationTime(new Date());//时间
        stockDetail.setOperator(userID);//交易人
        stockDetail.setRemarks(remarks);//备选
        if (multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            File file = new File("/home/ah/tomcat8/webapps/PLANC_CMS/img/" + originalFilename);
            OutputStream out = null;
            try {
                //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = multipartFile.getBytes();
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
            stockDetail.setPhotoImg(file.getName());//出入库照片
            stockDetail.setPhotoImgFile(file);
        }
        stockDetail.setDevID(devID);//出入库设备
        stockDetail.setStockDate(ActionUtil.getDateStrForString(new Date()));//当天日期
        stockDetail.setStatus(Integer.parseInt(status));//明细状态
        stockDetail.setCurBatchNo(0);
        int i = stockDetailService.addStockDetail(stockDetail);
        logger.info("已成功增加" + i + "条交易记录");
        if (i > 0) {
            if (multipartFile != null) {
                logger.info("准备上传图片");
                addImg(stockDetail);
            }
            updateCurBatchNoAndStatus(stockDetail.getStockDetailID(), status);
            return stockDetail;
        } else {
            return null;
        }
    }

    /**
     * @模块说明：添加图片功能
     * @创建时间：2021-12-27
     * @开发人员：张涛
     */
    private void addImg(StockDetail stockDetail) {
        System.out.println("StockController.addImg()");
        System.out.println("PhotoImgFile=" + stockDetail.getPhotoImgFile());
        System.out.println("上传图片::" + stockDetail.getPhotoImgFile().length());
        if (stockDetail.getPhotoImgFile().length() > 819200) {
            PrintWriter out = null;
            //ActionUtil.sendMessege(response, "图片超过了800kb");
        }
        InputStream is;
        try {
            is = new FileInputStream(stockDetail.getPhotoImgFile());


            // 获得食材编号为图片命名，更新到数据库
            System.out.println("明细编号：" + stockDetail.getStockDetailID());
            System.out.println("明细图片文件名：" + stockDetail.getPhotoImg());
            System.out.println("明细图片文件名2：" + stockDetail.getPhotoImg().split("\\.")[1]);
            stockDetail.setPhotoImg("img/shopImg/" + stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
//            stockDetail.setPhotoImg(stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
            System.out.println("prodImg=" + stockDetail.getPhotoImg());
            System.out.println(stockDetailService.updateStockDetail(stockDetail) ? "修改图片成功" : "修改图片失败");
            String file = getClass().getResource("/static/").getFile();
            File destFile = new File(file, stockDetail.getPhotoImg());
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
     * @模块说明：入库功能
     * @创建时间：2021-12-27
     * @开发人员：张涛
     */
    public ApiResult getStorageFunction(String productID, String weight, String code, String userID, String remarks, MultipartFile multipartFile, String devID, String adjustWeight, String status) {
        logger.info("StockController>>getStorageFunction");
        logger.info("库存管理>>入库功能");
        logger.info("库存编号=" + productID + ">>状态=" + code + ">>用户名=" + userID + ">>状态>>" + code + ">>status>>" + status);
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        ApiResult apiResult = new ApiResult();
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productID);
        ProductInfo product = productInfoService.findProductById(productID);
        StockInfo stockInfo = null;
        if (stockInfoByProdID == null) {
            stockInfo = new StockInfo();
        } else {
            stockInfo = stockInfoService.findStockInfoByStockID(stockInfoByProdID.getStockID());
        }
        if (stockInfoByProdID == null) {
            String dateStrForDayChinese = ActionUtil.getDateStrForString(new Date());
            //拼接库存编号
            String str = product.getProdID() + dateStrForDayChinese;
            stockInfo.setStockID(str);//库存编号
            stockInfo.setStockCount(new BigDecimal(0));
        }
        stockInfo.setProdID(product.getProdID());//产品名称
        BigDecimal bigDecimal = new BigDecimal(weight);
        stockInfo.setUnit(product.getProdUnit());//产品单位
        if (stockInfoByProdID == null) {
            if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())) {//判断直接入库
                String format = decimalFormat.format(bigDecimal);
                if (remarks == null) {
                    stockInfo.setStockCount(new BigDecimal(format));//产品总量
                } else {
                    BigDecimal bigDecimal1 = new BigDecimal(adjustWeight);//调整重量
                    BigDecimal subtract = bigDecimal.subtract(bigDecimal1);//秤重重量-调整重量=实际重量
                    stockInfo.setStockCount(new BigDecimal(decimalFormat.format(subtract)));
                }
                stockInfo.setStorageDate(new Date());//入库时间
                stockInfo.setBatchCount(1);//批次数
            }
        } else {
            BigDecimal stockCount = stockInfoByProdID.getStockCount();
            System.out.println("stockCount>>" + stockCount + ">>bigDecimal>>" + bigDecimal);
            if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())) {//判断直接入库
                if (stockCount == null) {
                    stockCount = new BigDecimal("0.000");
                }
                BigDecimal add = stockCount.add(bigDecimal);//相加
                String format = decimalFormat.format(bigDecimal);
                logger.info("数量相加>>产品总量+当前入库量=" + add);
                stockInfo.setStockCount(add);//产品总量
                int batchCount = stockInfoByProdID.getBatchCount();
                logger.info("批次数量>>批次总量+当前批次数=" + batchCount++);
                stockInfo.setBatchCount(batchCount++);  //批次数
                stockInfo.setLatestInTime(new Date());  //最近入库时间
            }
        }
        if (stockInfoByProdID != null) {
            stockInfo.setUpdateDate(new Date());    //最近一次更新时间
            stockInfo.setUpdater(userID);
        } else {
            stockInfo.setUpdater(userID);
        }
        int i = 0;
        if (stockInfoByProdID == null) {
            StockDetail b = addStockDetail(stockInfo.getStockID(), productID, code, userID, bigDecimal, remarks, multipartFile, devID, adjustWeight, status);
            if (b != null) {
                i = stockInfoService.addStockInfo(stockInfo);
                if (i > 0) {
                    apiResult.setResult(200);
                    apiResult.setResultData(b);
                    logger.info("已存入" + i + "条数据");
                } else {
                    logger.info("存入失败" + i + "条数据");
                    apiResult.setResult(500);
                    apiResult.setMsg("存入失败" + i + "条" + product.getProdName() + "库存，请联系管理员");
                    apiResult.setResultData(b);
                    return apiResult;
                }
            } else {
                apiResult.setResult(500);
                apiResult.setMsg("存入失败" + i + "条" + product.getProdName() + "库存明细，请联系管理员");
                apiResult.setResultData(b);
                return apiResult;
            }
        } else {
            StockDetail b = addStockDetail(stockInfo.getStockID(), productID, code, userID, bigDecimal, remarks, multipartFile, devID, adjustWeight, status);
            if (b == null) {
                apiResult.setResult(500);
                apiResult.setMsg("存入失败" + i + "条" + product.getProdName() + "库存明细，请联系管理员");
                apiResult.setResultData(b);
                return apiResult;
            } else {
                i = stockInfoService.updateStockInfo(stockInfo);
                if (i > 0) {
                    logger.info("已存入" + i + "条数据");
                    apiResult.setResult(200);
                    apiResult.setResultData(b);
                    return apiResult;
                } else {
                    logger.info("存入失败" + i + "条数据");
                    apiResult.setResult(500);
                    apiResult.setMsg("存入失败" + i + "条" + product.getProdName() + "库存，请联系管理员");
                    apiResult.setResultData(b);
                    return apiResult;
                }
            }
        }

        return apiResult;
    }

    /**
     * @模块说明：损耗功能
     * @创建时间：2021-12-28
     * @开发人员：张涛
     */
    public ApiResult getLossFunction(String productID, String weight, String userID, String remarks, String devID) {
        logger.info("StockController>>getLossFunction");
        logger.info("库存管理>>损耗功能");
        logger.info("库存编号=" + productID + ">>损耗=" + weight + ">>用户名=" + userID + ">>备注>>" + remarks + ">>devID>>" + devID);
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productID);
        BigDecimal stockCount = stockInfoByProdID.getStockCount();//总库存数量
        BigDecimal bigDecimal = new BigDecimal(weight);//字符转换Big类型
        ApiResult apiResult = new ApiResult();
        return apiResult;
    }

    /**
     * @模块说明：出库功能
     * @创建时间：2021-12-28
     * @开发人员：张涛
     */
    public ApiResult getPutsFunction(String productID, String weight, String userID, String remarks, String devID, MultipartFile multipartFile, String adjustWeight, String status,String code) {
        logger.info("StockController>>getPutsFunction");
        logger.info("库存管理>>出库功能");
        logger.info("食材编号=" + productID + ">>出库数量>>" + weight + ">>调整重量>>" + adjustWeight + ">>用户名=" + userID + ">>备注>>" + remarks + ">>设备编号>>" + devID+">>明细类型>>"+code);
        ApiResult apiResult = new ApiResult();
        StockInfo stockInfoByProdID1 = stockInfoService.findStockInfoByProdID(productID);
        BigDecimal bigDecimal2 = new BigDecimal(weight);
        BigDecimal subtract1 = stockInfoByProdID1.getStockCount().subtract(bigDecimal2);
        if (subtract1.compareTo(BigDecimal.ZERO)<=0){
            apiResult.setResult(500);
            apiResult.setResultData("已超出已有库存");
            return apiResult;
        }
        if (status.equals(allConfiguration.getStockDetailTypeStatusPrestore())){
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productID);
            BigDecimal bigDecimal = new BigDecimal(weight);
            System.out.println("预出库！");
            StockDetail b = addStockDetail(stockInfoByProdID.getStockID(), stockInfoByProdID.getProdID(), allConfiguration.getStockDetailTypeOut(), userID, bigDecimal, remarks, multipartFile, devID, adjustWeight, allConfiguration.getStockDetailTypeStatusPrestore());
            if (b==null){
                apiResult.setResult(500);
                apiResult.setResultData(null);
                apiResult.setMsg("错误");
            }else {
                apiResult.setResult(200);
                apiResult.setResultData(b);
                apiResult.setMsg("成功");
            }
            return apiResult;
        }
            DecimalFormat decimalFormat = new DecimalFormat("0.000");
            StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productID);
            BigDecimal stockCount = stockInfoByProdID.getStockCount();
            BigDecimal bigDecimal = new BigDecimal(weight);
            BigDecimal subtract = stockCount.subtract(bigDecimal);//减去出库数量
            String format = decimalFormat.format(subtract);//计算出四舍五入
            if (format.substring(0, 1).equals("-")) {
                apiResult.setResult(500);
                apiResult.setResultData(null);
                apiResult.setMsg("错误");
                return apiResult;
            }
            stockInfoByProdID.setStockCount(new BigDecimal(format));//插入计算出来的重量
            stockInfoByProdID.setUpdater(userID);//当前出库人
            stockInfoByProdID.setUpdateDate(new Date());//出库时间
            stockInfoByProdID.setLatestOutTime(new Date());//最近出库一次时间
            int i = stockInfoService.updateStockInfo(stockInfoByProdID);
            if (i > 0) {
                logger.info("已成功出货" + i + "条记录");
                StockDetail b = addStockDetail(stockInfoByProdID.getStockID(), stockInfoByProdID.getProdID(), code, userID, bigDecimal, remarks, multipartFile, devID, adjustWeight, status);
                if (b != null) {
                    apiResult.setResult(200);
                    apiResult.setResultData(b);
                } else {
                    apiResult.setResult(500);
                    apiResult.setResultData(b);
                }
            } else {
                logger.info("已失败出货记录");
                apiResult.setResult(500);
                apiResult.setResultData(null);
                apiResult.setMsg("错误");
            }
        return apiResult;
    }

    /**
     * @模块说明：查询入库最近批次号
     * @创建时间：2021-12-30
     * @开发人员：张涛
     */
    public StockDetail getBatchCount(@RequestParam(required = false) String stockID, String status) {
        logger.info("库存编号>>" + stockID + ">>明细状态>>" + status);
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDetailTypeOne(stockID, status);
        System.out.println(stockDetails);
        StockDetail stockDetail = null;
        if (stockDetails.size() != 0) {
            logger.info("明细数据>>" + stockDetails.get(stockDetails.size() - 1));
            if (stockDetails.get(stockDetails.size() - 1) == null) {
                stockDetail = null;
            } else {
                stockDetail = stockDetails.get(stockDetails.size() - 1);
            }
        } else {
            stockDetail = null;
        }
        return stockDetail;
    }

    /**
     * @模块说明：处理当前明细批次号和明细状态
     * @创建时间：2021-12-31
     * @开发人员：张涛
     */
    public boolean updateCurBatchNoAndStatus(String stockDetailID, String status) {
        logger.info("StockController>>updateCurBatchNoAndStatus");
        logger.info("库存管理>>处理当前明细批次号和明细状态");
        logger.info("库存明细编号>>" + stockDetailID + ">>状态>>" + status);
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
        if (status.equals(allConfiguration.getStockDetailTypeStatusPrestore())) {//预存
            //查询最近的批次号
            StockDetail batchCount = getBatchCount(stockDetailByStockDetailID.getStockID(), status);//查询批次号
            if (batchCount != null) {
                if (batchCount.getStatus() == Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect())) {//表示没有预存数据
                    if (batchCount.getStockDate() == ActionUtil.getDateStrForString(new Date())) {
                        stockDetailByStockDetailID.setStockDate(batchCount.getStockDate());//当天日期
                        String substring = Integer.toString(batchCount.getCurBatchNo()).substring(9, Integer.toString(batchCount.getCurBatchNo()).length() - 1);
                        int i = Integer.parseInt(substring);
                        stockDetailByStockDetailID.setCurBatchNo(i++);
                        stockDetailByStockDetailID.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore()));//与库存编号
                    } else {
                        stockDetailByStockDetailID.setStockDate(ActionUtil.getDateStrForString(new Date()));//当天日期
                        stockDetailByStockDetailID.setCurBatchNo(1);//批次号
                        stockDetailByStockDetailID.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore()));//与库存编号
                    }
                } else if (batchCount.getStatus() == Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore())) {//表示有预存数据
                    stockDetailByStockDetailID.setCurBatchNo(batchCount.getCurBatchNo());//预存批次号
                    stockDetailByStockDetailID.setStockDate(batchCount.getStockDate());//当天日期
                    stockDetailByStockDetailID.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore()));//预存状态
                }
            } else {
                List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDStockDate(stockDetailByStockDetailID.getStockID(), ActionUtil.getDateStrForString(new Date()));
                int initializeStatus3 = commonUtil.getInitializeStatus3(stockDetails);
                System.out.println("批次号为：" + initializeStatus3++);
                stockDetailByStockDetailID.setCurBatchNo(initializeStatus3++);//明细批次号
            }
        } else {//直接入库
            StockDetail batchCount = getBatchCount(stockDetailByStockDetailID.getStockID(), status);
            stockDetailByStockDetailID.setStockDate(ActionUtil.getDateStrForString(new Date()));//当天日期
            if (batchCount != null) {
                if (batchCount.getStockDate().equals(ActionUtil.getDateStrForString(new Date()))) {//判断是否为当天日期
                    if (batchCount.getCurBatchNo() != 0) {
                        int curBatchNo = batchCount.getCurBatchNo();
                        System.out.println(curBatchNo++);
                        stockDetailByStockDetailID.setCurBatchNo(curBatchNo++);//明细批次号
                    } else {
                        stockDetailByStockDetailID.setCurBatchNo(1);//明细批次号
                    }
                } else {
                    stockDetailByStockDetailID.setCurBatchNo(1);//明细批次号
                }
            } else {
                stockDetailByStockDetailID.setCurBatchNo(1);//明细批次号
            }
            stockDetailByStockDetailID.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect()));//明细状态
        }
        return stockDetailService.updateStockDetail(stockDetailByStockDetailID);
    }

    /**
     * @模块说明：删除库存明细、删除库存
     * @创建时间：2021-12-31
     * @开发人员：张涛
     */
    public boolean deleteStockInfoAndStockDetail() {
        return false;
    }
}
