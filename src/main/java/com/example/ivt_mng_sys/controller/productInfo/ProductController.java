package com.example.ivt_mng_sys.controller.productInfo;

import com.example.ivt_mng_sys.Util.*;
import com.example.ivt_mng_sys.entity.PriceUpdateDetail;
import com.example.ivt_mng_sys.entity.ProductInfo;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.StockInfo;
import com.example.ivt_mng_sys.service.PriceUpdateDetailService;
import com.example.ivt_mng_sys.service.ProductInfoService;
import com.example.ivt_mng_sys.service.StockDetailService;
import com.example.ivt_mng_sys.service.StockInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("ProductController")
public class ProductController {
    @Resource
    ProductInfoService productInfoService;
    @Resource
    StockDetailService stockDetailService;
    @Resource
    StockInfoService stockInfoService;
    @Resource
    PriceUpdateDetailService priceUpdateDetailService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommonUtil commonUtil;


    /**
     * @param : 无
     * @功能说明：前往菜品列表界面
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    @RequestMapping("/toProductList")
    public String goToProductList() {
        System.out.println("goTOProdulist");
        int currentPage = 1;
//        if (request.getSession().getAttribute("currentPage") != null) {
//            currentPage = (int) request.getSession().getAttribute("currentPage");
//            System.out.println("currentPage = " + currentPage);
//        }
        setCurrentPage(currentPage);
        return "product/productList";
    }


    /**
     * @param : 无
     * @功能说明：前往添加菜品界面
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @RequestMapping("/toAddProduct")
    public String goToAddProduct() {
        return "product/addProduct";
    }


    /**
     * @param : 无
     * @功能说明：前往修改菜品界面
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @RequestMapping("/toUpdateProduct")
    public String goToUpdateProduct(String id, int currentPage) {
        request.getSession().setAttribute("prodID", id);
        setCurrentPage(currentPage);
        return "product/updateProduct";
    }

    /**
     * @param : 无
     * @功能说明：前往菜品详情
     * @开发时间：2021-12-26
     * @开发人员：王涛
     */
    @RequestMapping("/toProductDetail")
    public String toProductDetail(String id, int currentPage) {
        StockDetail stockDetailByStockDetailID = stockDetailService.findStockDetailByStockDetailID(id);
        if (stockDetailByStockDetailID == null) {
            request.getSession().setAttribute("prodID", id);
            setCurrentPage(currentPage);
        } else {
            StockInfo stockInfoByStockID = stockInfoService.findStockInfoByStockID(stockDetailByStockDetailID.getStockID());
            request.getSession().setAttribute("prodID", stockInfoByStockID.getProdID());
            setCurrentPage(currentPage);
        }
        return "product/productDetail";
    }


    /**
     * @param : 无
     * @功能说明：添加菜品
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @PostMapping("addProduct")
    public String addProduct(@RequestParam("prodImgFile") MultipartFile prodImgFile) {
        ProductInfo productInfo = generateNewProduct();
        if (prodImgFile != null && prodImgFile.getSize() > 0) {
            saveProdImg(prodImgFile, productInfo);
        }
        ApiResult apiResult = new ApiResult();
        boolean f = productInfoService.addProductInfo(productInfo);
        apiResult.setResult(f ? 1 : 0);
        return "product/productList";
    }

    /**
     * @param : 无
     * @功能说明：返回菜品黎贝澳
     * @开发时间：2022-1-15
     * @开发人员：王涛
     */
    @RequestMapping("backToProductList")
    public String backToProductList() {
        return "product/productList";
    }

    /**
     * @param : 无
     * @功能说明：用前端数据生成新菜品
     * @开发时间：2022-1-3
     * @开发人员：王涛
     */
    private ProductInfo generateNewProduct() {
        PinyinUtil pinyinUtil = new PinyinUtil();
        String prodUnit = request.getParameter("prodUnit");
        String prodName = request.getParameter("prodName");
        String classID = request.getParameter("classID");
        String shelfLifeStr = request.getParameter("shelfLife");
        int shelfLife = Integer.valueOf(shelfLifeStr);
        String standardStatusStr = request.getParameter("standardStatus");
        int standardStatus = Integer.valueOf(standardStatusStr);
        String description = request.getParameter("description");
        String adder = request.getParameter("aProductController/addProductdder");
        String prodPriceStr = request.getParameter("prodPrice");
        String pinYinHeadChar = pinyinUtil.getPinYinHeadChar(prodName);//首字母
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProdUnit(prodUnit);
        productInfo.setProdName(prodName);
        productInfo.setSpellingName(pinYinHeadChar);
        productInfo.setAdder(adder);
        productInfo.setClassID(classID);
        productInfo.setStandardStatus(standardStatus);
        productInfo.setDescription(description);
        productInfo.setExpirationTime(shelfLife);
        if (prodPriceStr != null) {
            productInfo.setProdPrice(new BigDecimal(prodPriceStr));
        }
        generateProdIDAndSetProdAddtime(productInfo);
        return productInfo;
    }


    /**
     * @param : 无
     * @功能说明：保存菜品图片
     * @开发时间：2022-1-3
     * @开发人员：王涛
     */
    private void saveProdImg(MultipartFile prodImgFile, ProductInfo productInfo) {
        if (prodImgFile != null) {
            String originalFilename = prodImgFile.getOriginalFilename();
            String suffix = originalFilename.split("\\.")[originalFilename.split("\\.").length - 1];
            System.out.println("suffix = " + suffix);
            String newName = productInfo.getProdID() + "." + suffix;
            File file = new File("/home/ah/tomcat8/webapps/PLANC_CMS/img/prodImg/" + newName);
            if (file.exists()) {
                file.delete();
            }
            OutputStream out = null;
            try {
                //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = prodImgFile.getBytes();
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
            productInfo.setProdImgUrl("/static/img/prodImg/" + file.getName());
        }
    }

    private ProductInfo generateProdIDAndSetProdAddtime(ProductInfo productInfo) {
        int number = productInfoService.getProductCount() + 1;
        String prodID = productInfo.getClassID() + StringUtil.getFourCharacterByInt(number);
        productInfo.setProdID(prodID);
        productInfo = setProdAddtime(productInfo);
        return productInfo;
    }


    private ProductInfo setProdAddtime(ProductInfo productInfo) {
        Date addTime = new Date();
        productInfo.setAddTime(addTime);
        return productInfo;
    }


    /**
     * @param : 无
     * @功能说明：修改菜品
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @RequestMapping("/updateProduct")
    public String updateProduct(@RequestParam("prodImgFile") MultipartFile prodImgFile) {
        System.out.println("图片= " + prodImgFile);
        ProductInfo productInfo = generateEditProduct();
        productInfo.setUpdateTime(new Date());
        System.out.println("prodImgFile=" + prodImgFile);
        if (prodImgFile != null && prodImgFile.getSize() > 0) {
            System.out.println("图片不为空，保存图片");
            System.out.println("prodImgFile 的长度 =" + prodImgFile.getSize());
            saveProdImg(prodImgFile, productInfo);
        }
        System.out.println("修改后的菜品=" + productInfo.toString());
        boolean f = productInfoService.updateproductInfo(productInfo);
        System.out.println(f ? "菜品修改成功" : "菜品修改失败");
        return "product/productList";
    }

    /**
     * @param : 无
     * @功能说明：用前端数据生成新菜品
     * @开发时间：2022-1-3
     * @开发人员：王涛
     */
    private ProductInfo generateEditProduct() {
        String prodID = request.getParameter("prodID");
        String prodPrice = request.getParameter("prodPrice");
        String prodName = request.getParameter("prodName");
        String classID = request.getParameter("classID");
        String shelfLifeStr = request.getParameter("shelfLife");
        String prodUnit = request.getParameter("prodUnit");
        String description = request.getParameter("description");
        String prodImgUrl = request.getParameter("prodImgUrl");
        System.out.println("description = " + description);
        int shelfLife = Integer.valueOf(shelfLifeStr);
        String standardStatusStr = request.getParameter("standardStatus");
        int standardStatus = Integer.valueOf(standardStatusStr);
        String updater = request.getParameter("updater");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProdName(prodName);
        productInfo.setUpdater(updater);
        productInfo.setClassID(classID);
        productInfo.setStandardStatus(standardStatus);
        productInfo.setDescription(description);
        productInfo.setExpirationTime(shelfLife);
        productInfo.setProdUnit(prodUnit);
        productInfo.setProdID(prodID);
        productInfo.setProdImgUrl(prodImgUrl);
        if (prodPrice != null) {
            productInfo.setProdPrice(new BigDecimal(prodPrice));
        }
        return productInfo;
    }


    /**
     * @param : 无
     * @功能说明：根据编号查找菜品
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @RequestMapping("/getProductById")
    @ResponseBody
    public ApiResult getProductById(String id) {
        ProductInfo productInfo = productInfoService.findProductById(id);
        System.out.println("菜品详情=");
        System.out.println(productInfo.toString());
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(productInfo);
        return apiResult;
    }


    /**
     * @param : 无
     * @功能说明：根据编号查找菜品（带详情）
     * @开发时间：2021-12-24
     * @开发人员：王涛
     */
    @RequestMapping("/getProductDetailById")
    @ResponseBody
    public ApiResult getProductDetailById(String id) {
        ProductInfo productInfo = productInfoService.findProductById(id);
        //加上详情信息，如添加时间，添加人，修改时间，修改人
        productInfo = productInfoService.getProductInfoDetail(productInfo);
        PriceUpdateDetail priceUpdateDetail = priceUpdateDetailService.findLatestDetailByProdID(id);
        if (priceUpdateDetail != null) {
            System.out.println("价格更新明细详情 == >" + priceUpdateDetail.toString());

            productInfo.setPriceUpdateTimeStr(ActionUtil.getDateStrForDay(priceUpdateDetail.getUpdateTime()));
        }
        System.out.println("菜品详情=  " + productInfo.toString());
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(productInfo);
        return apiResult;
    }


    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：页面载入时，过来查询数据
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/getProductList")
    @ResponseBody
    public ApiResult getProductList(Integer currentPage) {
        setCurrentPage(currentPage);
        return getProductListOfResult(currentPage, "ProductController>>getProductList");
    }


    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：页面载入时，过来查询数据
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/getProductListByClassID")
    @ResponseBody
    public ApiResult getProductListByClassID(@RequestParam String classID) {
        List<ProductInfo> productInfos = productInfoService.findProductInfoByClassID(classID);
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(productInfos);
        return apiResult;
    }

    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：获取非标品设备列表
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    @RequestMapping("/getUnStandardProductListByClassID")
    @ResponseBody
    public ApiResult getUnStandardProductListByClassID(@RequestParam String classID) {
        List<ProductInfo> productInfos = productInfoService.findUnStandardProductByClassID(classID);
        for (ProductInfo productInfo : productInfos) {
            System.out.println("prodctInfo=" + productInfo.getStandardStatus());
        }
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(productInfos);
        return apiResult;
    }

    /**
     * @param : currentPage 当前页码 ,location 张涛工具类中的代码位置信息
     * @return : 返回修改后的数据
     * @功能说明：增删改查等操作后，封装后从这里走
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    private ApiResult getProductListOfResult(Integer currentPage, String location) {
        PageModel pageModel = commonUtil.showPage(currentPage, productInfoService.getProductCount(), location);
        ApiResult result = new ApiResult();
        List<ProductInfo> productInfos = productInfoService.listProductInfoByPage(pageModel);
        result.setResultData(productInfos);
        result.setPageModel(pageModel);
        return result;
    }

    /**
     * @param : currentPage 当前页码 ,location 张涛工具类中的代码位置信息
     * @return : 返回修改后的数据
     * @功能说明：增删改查等操作后，封装后从这里走
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    private ApiResult getPriceUpdateDetailListOfResult(Integer currentPage, String location, String prodID) {
        PageModel pageModel = commonUtil.showPage(currentPage, priceUpdateDetailService.getPriceUpdateDetailCountByProdID(prodID), location);
        ApiResult result = new ApiResult();
        List<PriceUpdateDetail> details = priceUpdateDetailService.listPriceUpdateDetailByProdIDByPage(prodID, pageModel);
        System.out.println("展示" + details.size() + "条明细");
        result.setResultData(details);
        result.setPageModel(pageModel);
        return result;
    }


    /**
     * @param : currentPage 当前页码，id 删除的类别编号
     * @return : 返回最新的列表数据和page信息
     * @功能说明：删除菜品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/delProduct")
    @ResponseBody
    public ApiResult delProduct(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("id=" + id);
        System.out.println("currentPage=" + currentPage);
        boolean f = productInfoService.deleteProductByID(id);
        int delResult = f ? 1 : 0;
        ApiResult result = getProductListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }


    /**
     * @param : currentPage 当前页码，id 删除的类别编号
     * @return : 返回最新的列表数据和page信息
     * @功能说明：恢复菜品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/recoverProduct")
    @ResponseBody
    public ApiResult recoverProduct(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("id=" + id);
        System.out.println("currentPage=" + currentPage);
        boolean f = productInfoService.recoverProductByID(id);
        int delResult = f ? 1 : 0;
        ApiResult result = getProductListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }

    private void setCurrentPage(int currentPage) {
        request.getSession().setAttribute("currentPage", currentPage);
    }


    /**
     * @param : 无
     * @功能说明：前往菜品价格明细列表界面
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    @RequestMapping("/toPriceDetailList")
    public String toPriceDetailList(@RequestParam(required = false) String prodID) {
        System.out.println("toPriceDetailList");
        int currentPage = 1;
        request.getSession().setAttribute("prodID", prodID.trim());
        request.getSession().setAttribute("priceDetailCurrentPage", currentPage);
        return "product/priceUpdateDetailList";
    }


    /**
     * @param : currentPage 当前页码 prodID 菜品编号
     * @return : 获取菜品价格明细信息
     * @功能说明：删除菜品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/getPriceUpdateDetailByProdIDByPage")
    @ResponseBody
    public ApiResult getPriceUpdateDetailByProdIDByPage(@RequestParam(required = false) String prodID, Integer priceDetailCurrentPage) {
        System.out.println("prodID=" + prodID.trim());
        System.out.println("currentPage=" + priceDetailCurrentPage);
        ApiResult result = getPriceUpdateDetailListOfResult(priceDetailCurrentPage, "getPriceUpdateDetailByProdIDByPage", prodID);
        return result;
    }


    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：页面载入时，过来查询数据
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @RequestMapping("/getPriceDetailList")
    @ResponseBody
    public ApiResult getPriceDetailList(Integer priceDetailCurrentPage, String prodID) {
        request.getSession().setAttribute("priceDetailCurrentPage", priceDetailCurrentPage);
        return getPriceUpdateDetailListOfResult(priceDetailCurrentPage, "ProductController>>getPriceDetailList", prodID);
    }

    /**
     * @return : 跳转到修改价格页面
     * @功能说明：页面载入时，过来查询数据
     * @开发时间：2021-1-17
     * @开发人员：王涛
     */
    @RequestMapping("/setUpdatPriceProdList")
    public String setUpdatPriceProdList(@RequestBody List<ProductInfo> updatePriceProdList) {
        System.out.println("修改价格的产品数量=  " + updatePriceProdList.size());
        request.getSession().setAttribute("updatePriceProdList", updatePriceProdList);
        return "product/updatePriceList";
    }


}
