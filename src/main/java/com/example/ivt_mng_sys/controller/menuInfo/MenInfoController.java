package com.example.ivt_mng_sys.controller.menuInfo;

import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.entity.MenuInfo;
import com.example.ivt_mng_sys.service.MenuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("MenInfoController")
@Slf4j
public class MenInfoController {
    @Resource
    private MenuInfoService menuInfoService;
    @Resource
    private HttpServletRequest request;
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();
    @Autowired
    public MenInfoController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }

    /**
     * @功能说明：查询一级菜单列表
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpMenInfoList")
    public String jumpMenInfoList(){
        log.info("菜单管理>>查询一级菜单");
        log.info("MenInfoController>>jumpMenInfoList");
        List<MenuInfo> menuInfos = menuInfoService.listMenuInfo(allConfiguration.getMenuILevel1());//查询一级菜单
        request.getSession().setAttribute("menusLv1",menuInfos);
        request.getSession().setAttribute("lv1Menu",menuInfos);
        return "menInfo/Lv1Menu";
    }
    /**
     * @功能说明：查询二级菜单列表
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpMenInfo2List")
    public String jumpMenInfo2List(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>查询二级菜单列表");
        log.info("MenInfoController>>jumpMenInfo2List");
        log.info("一级菜单编号>>"+menuID);
        List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        request.getSession().setAttribute("menu",menuInfoByMenuID);
        request.getSession().setAttribute("menus",menuInfoListBySuperMenuID);
        return "menInfo/Lv2Menu";
    }
    /**
     * @功能说明：查询三级菜单列表
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpMenInfo3List")
    public String jumpMenInfo3List(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>查询三级菜单列表");
        log.info("MenInfoController>>jumpMenInfo3List");
        log.info("二级菜单编号>>"+menuID);
        List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        request.getSession().setAttribute("menu",menuInfoByMenuID);
        request.getSession().setAttribute("menus",menuInfoListBySuperMenuID);
        return "menInfo/Lv3Menu";
    }
    /**
     * @功能说明：新增菜单页面
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAddMenInfo")
    public String jumpAddMenInfo(){
        log.info("菜单管理>>新增菜单页面");
        log.info("MenInfoController>>jumpAddMenInfo");
        return "menInfo/addLv1Menu";
    }
    /**
     * @功能说明：新增一级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("addMenInfo")
    public String addMenInfo(@RequestParam(required = false)String menuName, String menuExp, String menuUrl, Model model){
        log.info("菜单管理>>新增菜单");
        log.info("MenInfoController>>addMenInfo");
        log.info("菜单名称>>"+menuName+">>菜单说明>>"+menuExp+">>菜单地址>>"+menuUrl);
        if (menuName==null || menuName.equals("")){
            return "menInfo/addLv1Menu";
        }
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setMenuName(menuName);
        menuInfo.setMenuExp(menuExp);
        menuInfo.setMenuFunctionUrl(menuUrl);
        menuInfo.setMenuILevel(allConfiguration.getMenuILevel1());//一级菜单
        menuInfo.setMenuLock(allConfiguration.getMenuLockOF());//生效
        int i = 0;
        try {
            i = menuInfoService.addMenuInfo(menuInfo);
        } catch (Exception e) {
            model.addAttribute("error","添加失败！");
            System.out.println(e);
        }
        if (i>0){
            List<MenuInfo> menuInfos = menuInfoService.listMenuInfo(allConfiguration.getMenuILevel1());//一级菜单
            request.getSession().setAttribute("menusLv1",menuInfos);
            return "menInfo/Lv1Menu";
        }else {
            model.addAttribute("error","添加失败！");
            return "menInfo/addLv1Menu";
        }
    }
    /**
     * @功能说明：编辑一级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpUpdateMenuInfo")
    public String jumpUpdateMenuInfo(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>新增菜单");
        log.info("MenInfoController>>addMenInfo");
        log.info("菜单编号>>"+menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        request.getSession().setAttribute("menu",menuInfoByMenuID);
        return "menInfo/updateMenu";
    }
    /**
     * @功能说明：更新菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("updateMenuInfo")
    public String updateMenuInfo(@RequestParam(required = false)String menuId,String menuName,String menuExp,String menuUrl,String menuLv,String upMenuId){
        log.info("菜单管理>>更新菜单");
        log.info("MenInfoController>>updateMenuInfo");
        log.info("菜单编号>>"+menuId+">>菜单名称>>"+menuName+">>菜单说明>>"+menuExp+">>菜单地址>>"+menuUrl+">>菜单级别>>"+menuLv+">>上级菜单>>"+upMenuId);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuId);
        menuInfoByMenuID.setMenuName(menuName);//菜单名称
        menuInfoByMenuID.setMenuFunctionUrl(menuUrl);//菜单名称
        menuInfoByMenuID.setMenuILevel(menuLv);//菜单级别
        menuInfoByMenuID.setSuperMenuID(upMenuId);//上级菜单
        menuInfoByMenuID.setMenuExp(menuExp);//菜单说明
        int i = menuInfoService.updateMenuInfo(menuInfoByMenuID);//修改菜单
        if (i>0){
            List<MenuInfo> menuInfos = menuInfoService.listMenuInfo(allConfiguration.getMenuILevel1());//查询一级菜单
            request.getSession().setAttribute("menusLv1",menuInfos);
            return "menInfo/Lv1Menu";
        }else {
            return "menInfo/updateMenu";
        }
    }
    /**
     * @功能说明：删除菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("deleteMenuInfo")
    public String deleteMenuInfo(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>删除菜单");
        log.info("MenInfoController>>deleteMenuInfo");
        log.info("菜单编号>>"+menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        menuInfoByMenuID.setMenuLock(allConfiguration.getMenuLockNO());//失效
        int i = menuInfoService.updateMenuInfo(menuInfoByMenuID);
        System.out.println("i>>"+i);
        List<MenuInfo> menuInfos = menuInfoService.listMenuInfo(allConfiguration.getMenuILevel1());//一级菜单
        request.getSession().setAttribute("menusLv1",menuInfos);
        return "menInfo/Lv1Menu";
    }
    /**
     * @功能说明：新增二级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAddMenInfo2")
    public String jumpAddMenInfo2(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>新增二级菜单");
        log.info("MenInfoController>>deleteMenuInfo");
        log.info("一级菜单编号>>"+menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        request.getSession().setAttribute("menu",menuInfoByMenuID);
        request.getSession().setAttribute("menuID",menuID);
        return "menInfo/addLv2Menu";
    }
    /**
     * @功能说明：新增二级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("addMenInfo2")
    public String addMenInfo2(@RequestParam(required = false)String upMenuId,String menuName,String menuExp,String menuUrl){
        log.info("菜单管理>>删除菜单");
        log.info("MenInfoController>>deleteMenuInfo");
        log.info("上级菜单编号>>"+upMenuId+">>菜单名称>>"+menuName+">>菜单说明>>"+menuExp+">>菜单路劲>>"+menuUrl);
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setMenuFunctionUrl(menuUrl);//路劲
        menuInfo.setMenuILevel(allConfiguration.getMenuILevel2());//添加二级菜单
        menuInfo.setSuperMenuID(upMenuId);
        menuInfo.setMenuName(menuName);
        menuInfo.setMenuExp(menuExp);
        menuInfo.setMenuLock(allConfiguration.getMenuLockOF());
        int i = menuInfoService.addMenuInfo(menuInfo);
        if (i>0){
            List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(upMenuId);
            request.getSession().setAttribute("menus",menuInfoListBySuperMenuID);
            request.getSession().setAttribute("menuID",upMenuId);
            return "menInfo/Lv2Menu";
        }else {
            return "menInfo/addLv2Menu";
        }
    }
    /**
     * @功能说明：新增三级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAddMenInfo3")
    public String jumpAddMenInfo3(@RequestParam(required = false)String menuID){
        log.info("菜单管理>>新增三级菜单");
        log.info("MenInfoController>>jumpAddMenInfo3");
        log.info("一级菜单编号>>"+menuID);
        MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(menuID);
        request.getSession().setAttribute("menu",menuInfoByMenuID);
        request.getSession().setAttribute("menuID",menuID);
        return "menInfo/addLv3Menu";
    }
    /**
     * @功能说明：新增三级菜单
     * @开发时间：2022-01-21
     * @开发人员：张涛
     * */
    @RequestMapping("addMenInfo3")
    public String addMenInfo3(@RequestParam(required = false)String upMenuId,String menuName,String menuExp,String menuUrl){
        log.info("菜单管理>>新增三级菜单");
        log.info("MenInfoController>>deleteMenuInfo");
        log.info("上级菜单编号>>"+upMenuId+">>菜单名称>>"+menuName+">>菜单说明>>"+menuExp+">>菜单路劲>>"+menuUrl);
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setMenuFunctionUrl(menuUrl);//路劲
        menuInfo.setMenuILevel(allConfiguration.getMenuILevel3());//添加三级菜单
        menuInfo.setSuperMenuID(upMenuId);
        menuInfo.setMenuName(menuName);
        menuInfo.setMenuExp(menuExp);
        menuInfo.setMenuLock(allConfiguration.getMenuLockOF());
        int i = menuInfoService.addMenuInfo(menuInfo);
        if (i>0){
            List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(upMenuId);
            request.getSession().setAttribute("menus",menuInfoListBySuperMenuID);
            request.getSession().setAttribute("menuID",upMenuId);
            return "menInfo/Lv3Menu";
        }else {
            return "menInfo/addLv3Menu";
        }
    }
}
