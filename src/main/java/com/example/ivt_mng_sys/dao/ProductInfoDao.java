package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.ProductInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductInfoDao {

    /**
     * @sql说明：添加产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Insert("insert into product_info (prodID,prodUnit,prodName,spellingName,classID,prodImgUrl,prodPrice,description,expirationTime,standardStatus,addTime,adder) values (#{prodID},#{prodUnit},#{prodName},#{spellingName},#{classID},#{prodImgUrl},#{prodPrice},#{description},#{expirationTime},#{standardStatus},#{addTime},#{adder})")
    public int addProductInfo(ProductInfo productInfo);

    /**
     * @sql说明：修改产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Update("update product_info set prodName = #{prodName}, prodUnit = #{prodUnit}, classID = #{classID}, prodImgUrl = #{prodImgUrl},prodPrice=#{prodPrice},description=#{description},expirationTime=#{expirationTime},standardStatus=#{standardStatus},updateTime=#{updateTime},updater=#{updater} where prodID = #{prodID}")
    public int updateProductInfo(ProductInfo productInfo);

    /**
     * @sql说明：删除产品
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Delete("update product_info set status = 1 where prodID = #{prodID}")
    public int deleteProductByID(String prodID);


    /**
     * @sql说明：恢复类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Delete("update product_info set status = 0 where prodID = #{prodID}")
    public int recoverProductByID(String prodID);


    /**
     * @sql说明：分页查询数据（产品），按添加时间倒排
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select p.*,c.className as className from product_info p left join class_info c on p.classID = c.classID order by addTime desc limit #{start},#{pageSize} ")
    public List<ProductInfo> listProductInfoByPage(PageModel pageModel);


    /**
     * @sql说明：获取产品数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    @Select("select count(*) from product_info")
    public int getProductCount();

    /**
     * @sql说明：根据编号查找产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Select("select * from product_info where prodID = #{prodID}")
    public ProductInfo findProductByID(String prodID);


    /**
     * @sql说明：根据编号查找产品
     * @开发时间：2021-12-25
     * @开发人员：王涛
     */
    @Select("select * from product_info where classID = #{classID}")//top 15
    public List<ProductInfo> findProductByClassID(String classID);

    /**
     * @sql说明：根据编号查找产品(分开标品和非标品 )
     * @开发时间：2021-1-5
     * @开发人员：王涛
     */
    @Select("select * from product_info where classID = #{classID} and standardStatus = #{standardStatus}")
    public List<ProductInfo> findProductByClassIDAndStandardStatus(String classID , int standardStatus);

    /**
     * @sql说明：根据编号查找添加者名字
     * @开发时间：2021-12-26
     * @开发人员：王涛
     */
    @Select("select member_info.memberName from product_info left JOIN member_info on product_info.adder = member_info.userID where prodID =  #{prodID}")
    public String getAdderName(String prodID);


    /**
     * @sql说明：根据编号查找修改者名字
     * @开发时间：2021-12-26
     * @开发人员：王涛
     */
    @Select("select member_info.memberName from product_info left JOIN member_info on product_info.updater = member_info.userID where prodID =  #{prodID}")
    public String getUpdaterName(String prodID);
    /**
     * @sql说明：模糊查询
     * @开发时间：2021-01-12
     * @开发人员：张涛
     */
    @Select("select * from product_info where spellingName like concat('%',#{spellingName},'%')")
    List<ProductInfo> listProductInfoByLikeSpellingName(String spellingName);

}
