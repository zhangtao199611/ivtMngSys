package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.entity.MenuInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MenuInfoDao {
    /**
     * @sql说明：根据编号查询一级菜单
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    @Select("select * from menu_info where menuID=#{menuID}")
    MenuInfo findMenuInfoByMenuID(String menuID);
    /**
     * @sql说明：查询全部菜单数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     */
    @Select("select * from menu_info")
    List<MenuInfo> getlistMenuInfo();

    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    @Select("select * from menu_info where menuILevel=#{menuILevel} and menuLock=0")
    List<MenuInfo> listMenuInfo(String menuILevel);
    /**
     * @sql说明：根据编号查询superMenuID
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    @Select("select * from menu_info where superMenuID=#{superMenuID}")
    List<MenuInfo> findMenuInfoListBySuperMenuID(String superMenuID);
    /**
     * @sql说明：添加菜单
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    @Insert("insert into menu_info (menuID,menuFunctionUrl,menuILevel,superMenuID,menuName,menuExp,menuLock) values (#{menuID},#{menuFunctionUrl},#{menuILevel},#{superMenuID},#{menuName},#{menuExp},#{menuLock})")
    int addMenuInfo(MenuInfo menuInfo);
    /**
     * @sql说明：修改菜单
     * @开发时间：2021-01-21
     * @开发人员：张涛
     * */
    @Update("update menu_info set menuFunctionUrl=#{menuFunctionUrl},menuILevel=#{menuILevel},superMenuID=#{superMenuID},menuName=#{menuName},menuExp=#{menuExp},menuLock=#{menuLock} where menuID=#{menuID}")
    int updateMenuInfo(MenuInfo menuInfo);
}
