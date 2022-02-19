package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.DisClassInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DisClassInfoDao {

    /**
     * @sql说明：添加类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */


    @Insert("insert into disClass_info (disClassID,className,sortValue,statuss,explains,showStatus) values (#{disClassID},#{className},#{sortValue},#{statuss},#{explains},#{showStatus})")
    public int addClassInfo(DisClassInfo classInfo);

    /**
     * @sql说明：修改类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */

    @Update("update disClass_info set className=#{className},sortValue=#{sortValue},statuss=#{statuss},explains=#{explains},showStatus=#{showStatus} where disClassID=#{disClassID}")
    public int updateClassInfo(DisClassInfo classInfo);

    /**
     * @sql说明：获取大类列表
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info order by sortValue")
    public List<DisClassInfo> listClassInfo();


    /**
     * @sql说明：获取大类列表
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info where showStatus = 1 order by sortValue")
    public List<DisClassInfo> listShowingClassInfo();



    /**
    /**
     * @sql说明：删除类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Delete("update disClass_info set statuss = 1 where disClassID = #{disClassID}")
    public int deleteClassByID(String disClassID);

    /**
     * @sql说明：恢复类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Delete("update disClass_info set statuss = 0 where disClassID = #{disClassID}")
    public int recoverClassByID(String disClassID);


    /**
     * @sql说明：根据编号查找类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info where disClassID = #{disClassID}")
    public DisClassInfo findClassInfoById(String disClassID);


    /**
     * @sql说明：分页查询数据
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info order by sortValue limit #{start},#{pageSize}")
    public List<DisClassInfo> listClassInfoByPage(PageModel pageModel);


    /**
     * @sql说明：根据名字查类别数量（用于查重）
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select count(*) from disClass_info where className = #{className}")
    public int findDumpClassInfoByName(String className);

    /**
     * @sql说明：获取大类数量
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select count(*) from disClass_info")
    public int getClassCount();


    /**
     * @说明：获取最大sort值
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select max(sortValue) from disClass_info")
    public int getLargestSortCount();

    /**
     * @说明：根据sortValue获取ClassInfo
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info where sortValue = #{sortValue}")
    public DisClassInfo findClassBySortValue(int sortValue);
    /**
     * @说明：查询所有正常类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Select("select * from disClass_info where statuss=0")
    public List<DisClassInfo> listClassInfoByNull();
}
