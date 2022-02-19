package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.ClassInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassInfoService {
    /**
     * @sql说明：添加类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public boolean addClassInfo(ClassInfo classInfo);

    /**
     * @sql说明：修改类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public boolean updateClassInfo(ClassInfo classInfo);

    /**
     * @sql说明：获取大类列表
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public List<ClassInfo> listClassInfo();

    /**
     * @sql说明：获取大类数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public int getClassInfoCount();


    /**
     * @sql说明：删除类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public boolean deleteClass(String classID);

    /**
     * @sql说明：删除类别
     * @开发时间：2021-12-22
     * @开发人员：王涛
     */
    public boolean recoverClass(String classID);

    /**
     * @sql说明：删除类别
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public ClassInfo findClassInfoById(String classID);

    /**
     * @sql说明：分页查询大类
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public List<ClassInfo> listClassInfoByPage(PageModel pageModel);


    /**
     * @说明：根据名字查找类别数量
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public int findClassInfoCountByName(String className);


    /**
     * @说明：顺序向上调整
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public boolean sortClassUp(ClassInfo classInfo) ;

    /**
     * @说明：顺序向下调整
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public boolean sortClassDown(ClassInfo classInfo) ;

    /**
     * @说明：获取需要展示的类别
     * @开发时间：2021-12-15
     * @开发人员：王涛
     */
    public List<ClassInfo> listClassInfoShowForAndroidPad();
    /**
     * @说明：查询所有类别
     * @开发时间：2021-1-6
     * @开发人员：张涛
     */
    public List<ClassInfo> listClassInfoByNull();

}
