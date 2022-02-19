package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.Util.StringUtil;
import com.example.ivt_mng_sys.dao.ClassInfoDao;
import com.example.ivt_mng_sys.dao.DisClassInfoDao;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.DisClassInfo;
import com.example.ivt_mng_sys.service.DisClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DisClassInfoServiceImpl implements DisClassInfoService {
    @Resource
    private DisClassInfoDao disClassInfoDao;

    /**
     * @说明：添加类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public boolean addClassInfo(DisClassInfo classInfo) {
        int sortValue = 0;
        if (disClassInfoDao.getClassCount() > 0) {
            sortValue = disClassInfoDao.getLargestSortCount() + 1;
        }

        int sortValueForCount = sortValue;
        classInfo.setSortValue(sortValue);
        String newID = StringUtil.getTwoCharacterByInt(sortValue);

        boolean f = true;
        while (f) {
            DisClassInfo classInfo1 = disClassInfoDao.findClassInfoById(newID);
            if (classInfo1 != null) {
                sortValueForCount++;
                newID = StringUtil.getTwoCharacterByInt(sortValueForCount);
                System.out.println("newID = " + newID);

            } else {
                f = false;//跳出循环，进行添加
            }
        }
        classInfo.setDisClassID(newID);
        return disClassInfoDao.addClassInfo(classInfo) > 0;
    }

    /**
     * @说明：修改类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public boolean updateClassInfo(DisClassInfo classInfo) {
        return disClassInfoDao.updateClassInfo(classInfo) > 0;
    }

    /**
     * @说明：获取类别列表
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public List<DisClassInfo> listClassInfo() {
        return disClassInfoDao.listClassInfo();
    }


    /**
     * @说明：获取平板上展示的类别列表
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public List<DisClassInfo> listClassInfoShowForAndroidPad() {
        return disClassInfoDao.listShowingClassInfo();
    }

    @Override
    public List<DisClassInfo> listClassInfoByNull() {
        return disClassInfoDao.listClassInfoByNull();
    }

    /**
     * @说明：获取类别数量
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public int getClassInfoCount() {
        return disClassInfoDao.getClassCount();
    }


    /**
     * @说明：删除类别（假删除）
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public boolean deleteClass(String classID) {
        return disClassInfoDao.deleteClassByID(classID) > 0;
    }

    /**
     * @说明：恢复类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public boolean recoverClass(String classID) {
        return disClassInfoDao.recoverClassByID(classID) > 0;
    }

    /**
     * @说明：根据ID查找类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public DisClassInfo findClassInfoById(String classID) {
        return disClassInfoDao.findClassInfoById(classID);
    }

    /**
     * @说明：根据名字查找类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public int findClassInfoCountByName(String className) {
        return disClassInfoDao.findDumpClassInfoByName(className);
    }

    /**
     * @说明：分页查询数据（类别）
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    public List<DisClassInfo> listClassInfoByPage(PageModel pageModel) {
        return disClassInfoDao.listClassInfoByPage(pageModel);
    }


    /**
     * @说明：类别排序升1
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    public boolean sortClassUp(DisClassInfo classInfo) {
        int sortValue = classInfo.getSortValue();
        int upSortValue = sortValue - 1;
        DisClassInfo upClass = disClassInfoDao.findClassBySortValue(upSortValue);
        System.out.println("upClass = " + upClass.toString());
        System.out.println("downClass = " + classInfo.toString());
        upClass.setSortValue(sortValue);
        classInfo.setSortValue(upSortValue);
        System.out.println("then upClass = " + upClass.toString());
        System.out.println("then downClass = " + classInfo.toString());
        boolean f1 = disClassInfoDao.updateClassInfo(classInfo) > 0;
        boolean f2 = disClassInfoDao.updateClassInfo(upClass) > 0;
        return f1 && f2;
    }


    /**
     * @说明：类别排序降1
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @Override
    @Transactional
    public boolean sortClassDown(DisClassInfo classInfo) {
        int sortValue = classInfo.getSortValue();
        int upSortValue = sortValue + 1;
        classInfo.setSortValue(upSortValue);
        DisClassInfo downClass = disClassInfoDao.findClassBySortValue(sortValue + 1);
        downClass.setSortValue(sortValue);
        boolean f1 = disClassInfoDao.updateClassInfo(classInfo) > 0;
        boolean f2 = disClassInfoDao.updateClassInfo(downClass) > 0;
        return f1 && f2;
    }


}
