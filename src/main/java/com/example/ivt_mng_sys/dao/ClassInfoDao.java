package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.MemberInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassInfoDao {

    /**
     * @sql说明：添加类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Insert("insert into class_info (classID,className,classLevel,superClassID,status,sortValue) values (#{classID},#{className},#{classLevel},#{superClassID},#{status},#{sortValue})")
    public int addClassInfo(ClassInfo classInfo);

    /**
     * @sql说明：修改类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Update("update class_info set sortValue = #{sortValue}, className = #{className}, classLevel = #{classLevel}, superClassID = #{superClassID},status=#{status},showStatus = #{showStatus}  where classID = #{classID}")
    public int updateClassInfo(ClassInfo classInfo);

    /**
     * @sql说明：获取大类列表
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select * from class_info order by sortValue")
    public List<ClassInfo> listClassInfo();


    /**
     * @sql说明：获取大类列表
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select * from class_info where showStatus = 1 order by sortValue")
    public List<ClassInfo> listShowingClassInfo();



    /**
     * @sql说明：删除类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Delete("update class_info set status = 1 where classID = #{classID}")
    public int deleteClassByID(String classID);

    /**
     * @sql说明：恢复类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Delete("update class_info set status = 0 where classID = #{classID}")
    public int recoverClassByID(String classID);


    /**
     * @sql说明：根据编号查找类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select * from class_info where classID = #{classID}")
    public ClassInfo findClassInfoById(String classID);


    /**
     * @sql说明：分页查询数据
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select * from class_info order by sortValue limit #{start},#{pageSize}")
    public List<ClassInfo> listClassInfoByPage(PageModel pageModel);


    /**
     * @sql说明：根据名字查类别数量（用于查重）
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Select("select count(*) from class_info where className = #{className}")
    public int findDumpClassInfoByName(String className);

    /**
     * @sql说明：获取大类数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    @Select("select count(*) from class_info")
    public int getClassCount();


    /**
     * @说明：获取最大sort值
     * @开发时间：2021-12-27
     * @开发人员：王涛
     */
    @Select("select max(sortValue) from class_info")
    public int getLargestSortCount();

    /**
     * @说明：根据sortValue获取ClassInfo
     * @开发时间：2021-12-27
     * @开发人员：王涛
     */
    @Select("select * from class_info where sortValue = #{sortValue}")
    public ClassInfo findClassBySortValue(int sortValue);
    /**
     * @说明：查询所有正常类别
     * @开发时间：2021-1-6
     * @开发人员：张涛
     */
    @Select("select * from class_info where status=0")
    public List<ClassInfo> listClassInfoByNull();
}
