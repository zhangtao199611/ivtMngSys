package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.Util.StringUtil;
import com.example.ivt_mng_sys.dao.ClassInfoDao;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.service.ClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Resource
    ClassInfoDao classInfoDao;

    /**
     * @说明：添加类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public boolean addClassInfo(ClassInfo classInfo) {
        int sortValue = 0;
        if (classInfoDao.getClassCount() > 0) {
            sortValue = classInfoDao.getLargestSortCount() + 1;
        }

        int sortValueForCount = sortValue;
        classInfo.setSortValue(sortValue);
        String newID = StringUtil.getTwoCharacterByInt(sortValue);

        boolean f = true;
        while (f) {
            ClassInfo classInfo1 = classInfoDao.findClassInfoById(newID);
            if (classInfo1 != null) {
                sortValueForCount++;
                newID = StringUtil.getTwoCharacterByInt(sortValueForCount);
                System.out.println("newID = " + newID);

            } else {
                f = false;//跳出循环，进行添加
            }
        }
        classInfo.setClassID(newID);
        return classInfoDao.addClassInfo(classInfo) > 0;
    }

    /**
     * @说明：修改类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public boolean updateClassInfo(ClassInfo classInfo) {
        return classInfoDao.updateClassInfo(classInfo) > 0;
    }

    /**
     * @说明：获取类别列表
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public List<ClassInfo> listClassInfo() {
        return classInfoDao.listClassInfo();
    }


    /**
     * @说明：获取平板上展示的类别列表
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public List<ClassInfo> listClassInfoShowForAndroidPad() {
        return classInfoDao.listShowingClassInfo();
    }

    @Override
    public List<ClassInfo> listClassInfoByNull() {
        return classInfoDao.listClassInfoByNull();
    }

    /**
     * @说明：获取类别数量
     * @开发时间：2021-12-16
     * @开发人员：王涛
     */
    @Override
    public int getClassInfoCount() {
        return classInfoDao.getClassCount();
    }


    /**
     * @说明：删除类别（假删除）
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public boolean deleteClass(String classID) {
        return classInfoDao.deleteClassByID(classID) > 0;
    }

    /**
     * @说明：恢复类别
     * @开发时间：2021-12-22
     * @开发人员：王涛
     */
    @Override
    public boolean recoverClass(String classID) {
        return classInfoDao.recoverClassByID(classID) > 0;
    }

    /**
     * @说明：根据ID查找类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public ClassInfo findClassInfoById(String classID) {
        return classInfoDao.findClassInfoById(classID);
    }

    /**
     * @说明：根据名字查找类别
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    @Override
    public int findClassInfoCountByName(String className) {
        return classInfoDao.findDumpClassInfoByName(className);
    }

    /**
     * @说明：分页查询数据（类别）
     * @开发时间：2021-12-14
     * @开发人员：王涛
     */
    public List<ClassInfo> listClassInfoByPage(PageModel pageModel) {
        return classInfoDao.listClassInfoByPage(pageModel);
    }


    /**
     * @说明：类别排序升1
     * @开发时间：2021-12-27
     * @开发人员：王涛
     */
    @Override
    public boolean sortClassUp(ClassInfo classInfo) {
        int sortValue = classInfo.getSortValue();
        int upSortValue = sortValue - 1;
        ClassInfo upClass = classInfoDao.findClassBySortValue(upSortValue);
        System.out.println("upClass = " + upClass.toString());
        System.out.println("downClass = " + classInfo.toString());
        upClass.setSortValue(sortValue);
        classInfo.setSortValue(upSortValue);
        System.out.println("then upClass = " + upClass.toString());
        System.out.println("then downClass = " + classInfo.toString());
        boolean f1 = classInfoDao.updateClassInfo(classInfo) > 0;
        boolean f2 = classInfoDao.updateClassInfo(upClass) > 0;
        return f1 && f2;
    }


    /**
     * @说明：类别排序降1
     * @开发时间：2021-12-27
     * @开发人员：王涛
     */
    @Override
    @Transactional
    public boolean sortClassDown(ClassInfo classInfo) {
        int sortValue = classInfo.getSortValue();
        int upSortValue = sortValue + 1;
        classInfo.setSortValue(upSortValue);
        ClassInfo downClass = classInfoDao.findClassBySortValue(sortValue + 1);
        downClass.setSortValue(sortValue);
        boolean f1 = classInfoDao.updateClassInfo(classInfo) > 0;
        boolean f2 = classInfoDao.updateClassInfo(downClass) > 0;
        return f1 && f2;
    }


}
