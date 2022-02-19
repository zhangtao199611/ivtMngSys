package com.example.ivt_mng_sys.controller.StockInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ivt_mng_sys.Util.ActionUtil;
import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.Util.ApiResult;
import com.example.ivt_mng_sys.Util.CommonUtil;
import com.example.ivt_mng_sys.entity.*;
import com.example.ivt_mng_sys.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @模块说明：标品控制层
 * @创建时间：2021-1-6
 * @开发人员：张涛
 * @标品
 */
@Controller
@RequestMapping("StockInfoAPKStandardController")
@Slf4j
public class StockInfoAPKStandardController {
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
    private MemberInfoService memberInfoService;
    @Resource
    private SigningInfoService signingInfoService;
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();

    @Autowired
    public StockInfoAPKStandardController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }

    /**
     * @功能说明：类别展示
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("normListClassInfo")
    @ResponseBody
    public ApiResult normListClassInfo() {
        ApiResult apiResult = new ApiResult();
        List<ClassInfo> classInfos = classInfoService.listClassInfoByNull();
        apiResult.setResult(200);
        apiResult.setResultData(classInfos);
        return apiResult;
    }

    /**
     * @功能说明：根据类别展示标品产品
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("getStandardProductListByClassID")
    @ResponseBody
    public ApiResult getStandardProductListByClassID(@RequestParam(required = false) String classID) {
        log.info("StockInfoAPKStandardController>>jumpListStockDetailTypeStorage");
        log.info("库存列表>>根据编号跳转到入库明细");
        log.info("classID>>" + classID);
        ApiResult apiResult = new ApiResult();
        List<ProductInfo> standardProductByClassID = productInfoService.findStandardProductByClassID(classID);//标品产品
        System.out.println(standardProductByClassID.size());
        System.out.println(standardProductByClassID.size());
        apiResult.setResultData(standardProductByClassID);
        apiResult.setResult(200);
        return apiResult;
    }

    /**
     * @功能说明：点击标品入库/出库/损耗/退货
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("StockInfoAdmin")
    @ResponseBody
    public ApiResult StockInfoAdmin(@RequestParam(required = false) String code, String prodID, String weight, String userID, String status,String remarks) {
        log.info("StockInfoAPKStandardController>>StockInfoAdmin");
        log.info("库存列表>>点击标品入库");
        log.info("状态>>" + code + "产品编号>>" + prodID + ">>入库数量>>" + weight + ">>用户编号>>" + userID+">>明细状态>>"+status+">>退货理由>>"+remarks);
        ApiResult apiResult = null;
        if (weight.equals("0") || weight.equals("0.000")){
            apiResult.setResult(500);
            apiResult.setResultData(null);
            apiResult.setMsg("请放上货物！");
            return apiResult;
        }
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(prodID);
        if (code.equals(allConfiguration.getStockDetailTypeOut()) && status.equals(allConfiguration.getStockDetailTypeStatusPrestore())){
            boolean b = ifPredictStock(stockInfoByProdID.getStockID(), weight);
            if (b==false){
                ApiResult apiResult1 = new ApiResult();
                apiResult1.setResult(500);
                apiResult1.setMsg("预库存已超过总库存数！");
                return apiResult1;
            }
        }
        if (code.equals(allConfiguration.getStockDetailTypeOut())) {//出库
            apiResult = outJoinStockInfo(prodID, weight, userID, status,code,remarks);
            System.out.println("出货="+apiResult.getResultData());

        }
        if (code.equals(allConfiguration.getStockDetailTypeStorage())) {//入库
            apiResult = addJoinStockInfo(prodID, weight, userID, status);
        }
        if (code.equals(allConfiguration.getStockDetailTypeLoss())) {//损耗

        }
        if (code.equals(allConfiguration.getStockDetailReturns())) {//退货
            apiResult = outJoinStockInfo(prodID, weight, userID, status,code,remarks);
            System.out.println("退货="+apiResult.getResultData());
        }
        return apiResult;
    }

    /**
     * @功能说明：当日标品库存明细展示
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("listDayStockDetail")
    @ResponseBody
    public ApiResult listDayStockDetail(@RequestParam(required = false) String status) {
        log.info("StockInfoAPKStandardController>>listDayStockDetail");
        log.info("库存列表>>当日库存明细展示");
        log.info("库存明细状态>>" + status);
        ApiResult apiResult = new ApiResult();
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockDetailTypeAndStockDateAndStatus(status, ActionUtil.getDateStrForString(new Date()));
        Iterator<StockDetail> iterator = stockDetails.iterator();
        while (iterator.hasNext()) {
            StockDetail next = iterator.next();
            if (productInfoService.findProductById(stockInfoService.findStockInfoByStockID(next.getStockID()).getProdID()).getStandardStatus() == 1) {
                iterator.remove();
            }
        }
        for (StockDetail stockDetail : stockDetails) {
            stockDetail.setProdUnit(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdUnit());//单位
            stockDetail.setStockID(productInfoService.findProductById(stockInfoService.findStockInfoByStockID(stockDetail.getStockID()).getProdID()).getProdName());//设置产品名称
            stockDetail.setOperationTimeStr(ActionUtil.getDateStr(stockDetail.getOperationTime()));//操作时间
        }
        apiResult.setResult(200);
        apiResult.setResultData(stockDetails);
        return apiResult;
    }

    /**
     * @功能说明：有库存的标品类别列表
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("listStockInfoClassStandard")
    @ResponseBody
    public ApiResult listStockInfoClassStandard() {
        log.info("StockInfoAPKStandardController>>listStockInfoClassStandard");
        log.info("库存列表>>有库存的标品类别列表");
        List<StockInfo> stockInfos = stockInfoService.listStockInfo();
        Set<ClassInfo> set = new HashSet<>();
        ApiResult apiResult = new ApiResult();
        for (StockInfo stockInfo : stockInfos) {
            System.out.println("库存=" + stockInfo.getStockCount());
            if (stockInfo.getStockCount() != null || !stockInfo.getStockCount().equals("0.000")) {
                ProductInfo productById = productInfoService.findProductById(stockInfo.getProdID());
                if (productById.getStandardStatus() == 0) {
                    ClassInfo classInfoById = classInfoService.findClassInfoById(productById.getClassID());
                    set.add(classInfoById);
                }
            }
        }
        apiResult.setResultData(set);
        apiResult.setResult(200);
        return apiResult;

    }

    /**
     * @功能说明：根据类别展示库存列表
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("findStockInfoAndClassID")
    @ResponseBody
    public ApiResult findStockInfoAndClassID(String classID) {
        log.info("StockInfoAPKStandardController>>findStockInfoAndClassID");
        log.info("库存列表>>有库存的标品类别列表");
        log.info("类别编号>>" + classID);
        ApiResult apiResult = new ApiResult();
        List<ProductInfo> list = new ArrayList<>();
        List<ProductInfo> productInfoByClassID = productInfoService.findStandardProductByClassID(classID);
        for (ProductInfo productInfo : productInfoByClassID) {
            System.out.println("产品类别=" + productInfo.getProdName());
            System.out.println("产品类别=" + productInfo.getStandardStatus());
            if (productInfo.getStandardStatus() == 0) {
                StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(productInfo.getProdID());
                if (stockInfoByProdID != null && stockInfoByProdID.getStockCount() != null && !(stockInfoByProdID.getStockCount().compareTo(BigDecimal.ZERO) <= 0)) {
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
     * @模块说明：批量入库
     * @创建时间：2021-1-9
     * @开发人员：张涛
     */
    @RequestMapping("bulkStorage")
    @ResponseBody
    public ApiResult bulkStorage(@RequestBody JSONObject stockDetails) {
        log.info("StockInfoAPKStandardController>>showTheQuantityOfExistingStock");
        log.info("库存管理>>展示已有库存的数量");
        log.info("批量入库数据量>>" + stockDetails.getString("stockDetails"));
        JSONArray stockDetailss = stockDetails.getJSONArray("stockDetails");
        List<StockDetail> stockDetails1 = stockDetailss.toJavaList(StockDetail.class);
        System.out.println("stock=" + stockDetails1.size());
        ApiResult apiResult = new ApiResult();
        DecimalFormat decimalFormat = new DecimalFormat("0000.000");
        for (StockDetail stockDetail : stockDetails1) {
            if (stockDetail.getStatus() == Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore())) {
                StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailService.findStockDetailByStockDetailID(stockDetail.getStockDetailID()).getStockID());
                BigDecimal stockCount = stockInfoByStockID.getStockCount();//库存重量
                BigDecimal weight = stockDetail.getWeight();//入库实际重量
                stockInfoByStockID.setStockCount(new BigDecimal(decimalFormat.format(stockCount.add(weight))));//相加的库存重量
                stockInfoByStockID.setUpdateDate(new Date());//更新时间
                stockInfoByStockID.setLatestInTime(new Date());//最近入库时间
                stockDetail.setStockID(stockInfoByStockID.getStockID());//库存编号
                int i = stockInfoService.updateStockInfo(stockInfoByStockID);
                if (i > 0) {
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect()));//生效
                    stockDetail.setOperationTime(new Date());//入库时间
                    boolean b = stockDetailService.updateStockDetail(stockDetail);//修改状态
                    if (b == true) {
                        apiResult.setResult(200);
                        apiResult.setResultData(null);
                    } else {
                        apiResult.setResult(500);
                        apiResult.setResultData(null);
                    }
                } else {
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
        log.info("StockInfoAPKStandardController>>bulkOutStorage");
        log.info("库存管理>>批量出库");
        log.info("批量出库数据量>>" + stockDetails.getString("stockDetails"));
        JSONArray stockDetailss = stockDetails.getJSONArray("stockDetails");
        List<StockDetail> stockDetails1 = stockDetailss.toJavaList(StockDetail.class);
        System.out.println("stock=" + stockDetails1.size());
        ApiResult apiResult = new ApiResult();
        DecimalFormat decimalFormat = new DecimalFormat("0000.000");
        for (StockDetail stockDetail : stockDetails1) {
            if (stockDetail.getStatus() == Integer.parseInt(allConfiguration.getStockDetailTypeStatusPrestore())) {
                StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailService.findStockDetailByStockDetailID(stockDetail.getStockDetailID()).getStockID());
                BigDecimal stockCount = stockInfoByStockID.getStockCount();//库存重量
                BigDecimal weight = stockDetail.getWeight();//出库实际重量
                BigDecimal bigDecimal = new BigDecimal(decimalFormat.format(stockCount.subtract(weight)));
                String format = decimalFormat.format(bigDecimal);
                if (!format.substring(0, 1).equals("-")) {
                    System.out.println("from==" + format.substring(0, 1));
                    stockInfoByStockID.setStockCount(new BigDecimal(decimalFormat.format(stockCount.subtract(weight))));//相减的库存重量
                } else {
                    continue;
                }
                stockInfoByStockID.setUpdateDate(new Date());//更新时间
                stockInfoByStockID.setLatestInTime(new Date());//最近入库时间
                stockDetail.setStockID(stockInfoByStockID.getStockID());//库存编号
                int i = stockInfoService.updateStockInfo(stockInfoByStockID);
                if (i > 0) {
                    stockDetail.setStatus(Integer.parseInt(allConfiguration.getStockDetailTypeStatusEffect()));//生效
                    stockDetail.setOperationTime(new Date());//入库时间
                    boolean b = stockDetailService.updateStockDetail(stockDetail);//修改状态
                    if (b == true) {
                        apiResult.setResult(200);
                        apiResult.setResultData(null);
                    } else {
                        apiResult.setResult(500);
                        apiResult.setResultData(null);
                    }
                } else {
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
     * @功能说明：入库
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    @RequestMapping("addJoinStockInfo")
    @ResponseBody
    public ApiResult addJoinStockInfo(String prodID, String weight, String userID, String status) {
        log.info("StockInfoAPKStandardController>>addJoinStockInfo");
        log.info("库存列表>>入库");
        log.info("产品编号>>" + prodID + ">>入库数量>>" + weight + ">>用户编号>>" + userID + ">>明细状态>>" + status);
        ApiResult apiResult = new ApiResult();
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(prodID);
        if (stockInfoByProdID == null) {
            System.out.println("已走===");
            Map<String, Object> map = addStockDetail(prodID, weight, userID, allConfiguration.getStockDetailTypeStorage(), status,null);
            String aBoolean = map.get("boolean").toString();
            String id = map.get("id").toString();
            System.out.println("aBoolen==" + aBoolean);
            if (aBoolean.equals("true")) {//添加库存
                System.out.println("明细编号>>" + id);
                if (addStockInfo(prodID, weight, userID, status) == false) {
                    stockDetailService.deleteStockDetailByStockDetailID(id);
                    apiResult.setResult(500);
                    apiResult.setResultData("失败，请联系管理员！");
                    return apiResult;
                } else {
                    apiResult.setResult(200);
                    apiResult.setResultData("成功！");
                    return apiResult;
                }
            } else if (aBoolean.equals("false")) {
                System.out.println("true==" + aBoolean);
                apiResult.setResult(500);
                apiResult.setResultData("失败，请联系管理员！");
            }
        } else {//有库存
            if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())) {
                BigDecimal bigDecimal = new BigDecimal(weight);
                BigDecimal stockCount = stockInfoByProdID.getStockCount();//库存
                stockInfoByProdID.setStockCount(stockCount.add(bigDecimal));//当前库存+入库库存=实际库存
            } else {
                stockInfoByProdID.setStockCount(stockInfoByProdID.getStockCount());
            }
            stockInfoByProdID.setUpdateDate(new Date());//更新时间
            stockInfoByProdID.setUpdater(userID);//更新人
            int i = stockInfoByProdID.getBatchCount();
            System.out.println(i++);
            stockInfoByProdID.setBatchCount(i++);
            stockInfoByProdID.setLatestInTime(new Date());//最近入库时间
            Map<String, Object> map = addStockDetail(prodID, weight, userID, allConfiguration.getStockDetailTypeStorage(), status,null);
            String aBoolean = map.get("boolean").toString();
            if (aBoolean.equals("true")) {
                stockInfoService.updateStockInfo(stockInfoByProdID);
                apiResult.setResult(200);
                apiResult.setResultData("成功");
                return apiResult;
            } else {
                apiResult.setResult(500);
                apiResult.setResultData("入库失败，请联系管理员！");
                return apiResult;
            }
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
        log.info("StockInfoAPKController>>getLettersQuery");
        log.info("库存管理>>根据汉字获取首字母");
        log.info("字母>>"+letter+">>状态 1：入库/0：出库>>"+code);
        ApiResult apiResult = new ApiResult();
        if (letter==null || letter.length()==0){
            apiResult.setResult(500);
            apiResult.setResultData(null);
            apiResult.setMsg("为空");
            return apiResult;
        }
        log.info("字母"+letter+"转换小写>>"+letter.toLowerCase());
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
     * @模块说明：接收图片接口
     * @创建时间：2021-1-10
     * @开发人员：张涛
     */
    @RequestMapping("addStockImg")
    @ResponseBody
    public ApiResult addStockImg(@RequestParam("multipartFile") MultipartFile multipartFile){
        String stockDetailID = request.getParameter("stockDetailID");//库存明细编号
        log.info("库存明细编号>>"+stockDetailID+">>图片>>"+multipartFile);
        ApiResult apiResult = new ApiResult();
        StockDetail stockDetail = stockDetailService.findStockDetailByStockDetailID(stockDetailID);
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
        addImg(stockDetail);
        apiResult.setResult(200);
        apiResult.setMsg("成功");
        return apiResult;
    }


    /**
     * @模块说明：签单拍照上传
     * @创建时间：2021-1-14
     * @开发人员：张涛
     */
    @RequestMapping("addSigningImg")
    @ResponseBody
    public ApiResult addSigningImg(@RequestParam("signingFile")MultipartFile signingFile){
        log.info("pad签单拍照上传");
        String userID = request.getParameter("userID");
        log.info("图片文件>>"+signingFile+">>工号>>"+userID);
        MemberInfo memberInfoByJobID = memberInfoService.findMemberInfoByJobID(userID);
        SigningInfo signingInfo = new SigningInfo();
        ApiResult apiResult = new ApiResult();
        if (signingFile != null) {
            String originalFilename = signingFile.getOriginalFilename();
            String s = getClass().getResource("/static/img/signingImg/").getFile();
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
            String dateStrForString = "S" + ActionUtil.getDateStrForString(new Date());
            signingInfo.setSigningID(dateStrForString);//签单编号
            signingInfo.setAdder(userID);
            signingInfo.setAddDate(new Date());
            signingInfo.setUnitID(memberInfoByJobID.getUnitID());//归属单位
            signingInfo.setSigImg(file.getName());
            signingInfo.setSigImgFile(file);
            int i = signingInfoService.addSigningInfo(signingInfo);
            if (i>0){
                addSigningImg(signingInfo);
                apiResult.setResult(200);
                apiResult.setMsg("上传签单照片成功");
            }else {
                apiResult.setResult(500);
                apiResult.setMsg("上传签单照片失败");
            }
        }
        return apiResult;
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
            signingInfo.setSigImg("img/signingImg/" + signingInfo.getSigningID() + "." + signingInfo.getSigImg().split("\\.")[1]);
//            stockDetail.setPhotoImg(stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
            System.out.println("prodImg=" + signingInfo.getSigImg());
            System.out.println(signingInfoService.updateSigningInfo(signingInfo) ? "修改图片成功" : "修改图片失败");
            String file = getClass().getResource("/static/").getFile();
            System.out.println("file"+file);
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
     * @功能说明：判断预出库库存是否大于实际库存
     * @创建时间：2021-1-9
     * @开发人员：张涛
     */
    public boolean ifPredictStock(String stockID, String weight) {
        log.info("StockInfoAPKStandardController>>ifPredictStock");
        log.info("库存列表>>判断预出库库存是否大于实际库存");
        log.info("库存编号>>" + stockID + ">>入库数量>>" + weight);
        log.info("库存编号>>" + stockID + ">>入库状态>>" + allConfiguration.getStockDetailTypeOut()+">>时间>>"+ActionUtil.getDateStrForString(new Date())+">>状态>>"+allConfiguration.getStockDetailTypeStatusPrestore());
        List<StockDetail> stockDetails = stockDetailService.listIfStockDetailByIDAndTypeAndDateAndStatus(stockID, allConfiguration.getStockDetailTypeOut(), ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusPrestore());
        System.out.println("stock="+stockDetails.size());
        if (stockDetails.size()==0){
            return true;
        }
        for (StockDetail stockDetail : stockDetails) {
            System.out.println("stock="+stockDetail.getWeight());
        }
        BigDecimal bigDecimal = null;
        for (StockDetail stockDetail : stockDetails) {
            if (bigDecimal == null) {
                bigDecimal = stockDetail.getWeight();
            } else {
                bigDecimal =  bigDecimal.add(stockDetail.getWeight());
            }
        }
        BigDecimal add = bigDecimal.add(new BigDecimal(weight));
        System.out.println("总数="+add);

        StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockID);
        System.out.println("TOTAL="+stockInfoByStockID.getStockCount());
        System.out.println("ADD="+add);
        if (stockInfoByStockID.getStockCount().compareTo(add) < 0) {
             return false;
        }else {
            return true;
        }
    }

    /**
     * @功能说明：出库/退货
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    public ApiResult outJoinStockInfo(String prodID, String weight, String userID, String status,String code,String remarks) {
        log.info("StockInfoAPKStandardController>>outJoinStockInfo");
        log.info("库存列表>>出库");
        log.info("产品编号>>" + prodID + ">>入库数量>>" + weight + ">>用户编号>>" + userID+">>明细状态>>"+status+">>明细类型>>"+code+">>备注>>"+remarks);
        ApiResult apiResult = new ApiResult();
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(prodID);
        if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())) {
            BigDecimal bigDecimal = new BigDecimal(weight);
            BigDecimal stockCount = stockInfoByProdID.getStockCount();
            BigDecimal subtract = stockCount.subtract(bigDecimal);
            stockInfoByProdID.setStockCount(subtract);//库存
        }
        stockInfoByProdID.setLatestOutTime(new Date());//最近出库时间
        stockInfoByProdID.setUpdateDate(new Date());//更新时间
        stockInfoByProdID.setUpdater(userID);//更新人
        Map<String, Object> map = addStockDetail(prodID, weight, userID, code, status,remarks);
        String aBoolean = map.get("boolean").toString();
        if (aBoolean.equals("false")) {
            apiResult.setResult(500);
            apiResult.setMsg("出库失败，请联系管理员！");
        } else {
            stockInfoService.updateStockInfo(stockInfoByProdID);
            apiResult.setResult(200);
            apiResult.setResultData(map.get("id"));
            apiResult.setMsg("成功");
        }
        return apiResult;
    }

    /**
     * @功能说明：添加一条库存明细
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    public Map<String, Object> addStockDetail(String prodID, String weight, String userID, String stockDetailType, String status,String remarks) {
        log.info("StockInfoAPKStandardController>>addStockDetail");
        log.info("库存列表>>添加一条库存明细");
        log.info("产品编号>>" + prodID + ">>入库数量>>" + weight + ">>用户编号>>" + userID + ">>status>>" + status+">>退货理由>>"+remarks);
        Map<String, Object> map = new HashMap<>();
        ProductInfo product = productInfoService.findProductById(prodID);
        StockInfo stockInfoByProdID = stockInfoService.findStockInfoByProdID(product.getProdID());
        //库存明细编号定义，库存编号加五位随机数
        String dateStrForDayChinese = ActionUtil.getDateStrForString(new Date());
        //拼接库存编号
        String str = product.getProdID() + dateStrForDayChinese;
        String stockId = str + ActionUtil.getRandomString(4);//库存明细编号
        StockDetail stockDetail = new StockDetail();
        stockDetail.setStockDetailID(stockId);//库存明细编号
        if (stockInfoByProdID==null){
            stockDetail.setStockID(str);//库存编号
        }else {
            stockDetail.setStockID(stockInfoByProdID.getStockID());//库存编号
        }
        stockDetail.setStockDetailType(Integer.parseInt(stockDetailType));//库存明细类型
        stockDetail.setWeight(new BigDecimal(weight));//实际入库数量
        stockDetail.setOperationTime(new Date());//操作时间
        stockDetail.setStockDate(ActionUtil.getDateStrForString(new Date()));//当日批次号
        stockDetail.setRemarks(remarks);//备注
        int curBatchNo = getCurBatchNo(str, stockDetailType);
        System.out.println(curBatchNo++);
        stockDetail.setCurBatchNo(curBatchNo++);//当日批次号
        stockDetail.setOperator(userID);//操作人
        System.out.println("明细状态>>"+Integer.parseInt(status));
        if (status.equals(allConfiguration.getStockDetailTypeStatusPrestore())){
            stockDetail.setStatus(0);//预存
        }
        if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())){
            stockDetail.setStatus(1);//生效
        }
        stockDetail.setAdjustWeight(new BigDecimal("0"));//调整重量
        stockDetail.setWeighingWeight(new BigDecimal("0"));//秤重重量
        int i = stockDetailService.addStockDetail(stockDetail);
        if (i > 0) {
            map.put("boolean", true);
            map.put("id", stockDetail);
        } else {
            map.put("boolean", false);
            map.put("id", stockDetail);
        }
        return map;
    }

    /**
     * @功能说明：添加一条库存
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    public boolean addStockInfo(String prodID, String weight, String userID, String status) {
        log.info("StockInfoAPKStandardController>>addStockInfo");
        log.info("库存列表>>添加一条库存");
        log.info("产品编号>>" + prodID + ">>入库数量>>" + weight + ">>用户编号>>" + userID);
        ProductInfo product = productInfoService.findProductById(prodID);
        String dateStrForDayChinese = ActionUtil.getDateStrForString(new Date());
        //拼接库存编号
        String str = product.getProdID() + dateStrForDayChinese;
        StockInfo stockInfo = new StockInfo();
        stockInfo.setStockID(str);//设置编号
        stockInfo.setProdID(prodID);//设置产品编号
        stockInfo.setStorageDate(new Date());//入库时间
        if (status.equals(allConfiguration.getStockDetailTypeStatusEffect())) {
            stockInfo.setStockCount(new BigDecimal(weight));//入库数量
        } else {
            stockInfo.setStockCount(new BigDecimal("0"));//入库数量
        }
        stockInfo.setUnit(product.getProdUnit());//产品单位
        stockInfo.setBatchCount(1);//批次号
        stockInfo.setLatestInTime(new Date());//最近入库时间
        stockInfo.setUpdater(userID);//操作人
        int i = stockInfoService.addStockInfo(stockInfo);//添加时间
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @模块说明：添加图片功能
     * @创建时间：2021-01-12
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
            stockDetail.setPhotoImg("img/" + stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
//            stockDetail.setPhotoImg(stockDetail.getStockDetailID() + "." + stockDetail.getPhotoImg().split("\\.")[1]);
            System.out.println("prodImg=" + stockDetail.getPhotoImg());
            System.out.println(stockDetailService.updateStockDetail(stockDetail) ? "修改图片成功" : "修改图片失败");
            File destFile = new File("/home/ah/tomcat8/webapps/PLANC_CMS/", stockDetail.getPhotoImg());
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
     * @功能说明：获取库存明细批次号
     * @创建时间：2021-1-6
     * @开发人员：张涛
     */
    public int getCurBatchNo(String stockID, String stockDetailType) {
        log.info("StockInfoAPKStandardController>>addStockDetail");
        log.info("库存列表>>添加一条库存明细");
        log.info("库存编号>>" + stockID);
        List<StockDetail> stockDetails = stockDetailService.listStockDetailByStockIDAndStockDetailTypeAndStockDateAndStatus(stockID, stockDetailType, ActionUtil.getDateStrForString(new Date()), allConfiguration.getStockDetailTypeStatusEffect());
        if (stockDetails.size() > 0) {
            return commonUtil.getInitializeStatus3(stockDetails);
        } else {
            return 0;
        }
    }
}
