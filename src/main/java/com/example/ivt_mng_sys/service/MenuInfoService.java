package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.MenuInfo;

import java.util.List;

public interface MenuInfoService {
    /**
     * @sql说明：功能描述
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    MenuInfo findMenuInfoByMenuID(String menuID);

    List<MenuInfo> getlistMenuInfo();
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    List<MenuInfo> listMenuInfo(String menuILevel);
    /**
     * @sql说明：根据编号查询superMenuID
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    List<MenuInfo> findMenuInfoListBySuperMenuID(String superMenuID);
    /**
     * @sql说明：添加菜单
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    int addMenuInfo(MenuInfo menuInfo);
    /**
     * @sql说明：修改菜单
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    int updateMenuInfo(MenuInfo menuInfo);







}
