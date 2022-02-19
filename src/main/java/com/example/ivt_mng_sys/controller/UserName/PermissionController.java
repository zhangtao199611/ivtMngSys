package com.example.ivt_mng_sys.controller.UserName;

import com.example.ivt_mng_sys.Util.ActionUtil;
import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.Util.CommonUtil;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.AuthorityInfoDao;
import com.example.ivt_mng_sys.entity.*;
import com.example.ivt_mng_sys.service.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @模块说明：权限管理
 * @创建时间：2021-12-22
 * @开发人员：张涛
 */
@Controller
@RequestMapping("PermissionController")
@Slf4j
public class PermissionController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private DeptInfoService deptInfoService;
    @Resource
    private StationInfoService stationInfoService;
    @Resource
    private AuthorityInfoDao authorityInfoDao;
    @Resource
    private MenuInfoService menuInfoService;
    @Resource
    private CommonUtil commonUtil;
    /**
     * 读取conf.yml配置文件
     */
    private AllConfiguration allConfiguration = new AllConfiguration();

    @Autowired
    public PermissionController(AllConfiguration allConfiguration) {
        this.allConfiguration = allConfiguration;
    }

    Logger logger = LoggerFactory.getLogger(PermissionController.class);


    /**
     * @功能说明：跳转到岗位列表首页
     * @开发时间：2021-12-22
     * @开发人员：张涛
     */
    @RequestMapping("jumpStationList")
    public String jumpStationList(String userName) {
        logger.info("PermissionController>>jumpStationList");
        logger.info("权限管理>>岗位管理");
        logger.info("user=" + request.getSession().getAttribute("userInfo").toString());


        commonUtil.clearSession();
        getStationList(1, "jumpStationList");
        commonUtil.userPer(userName);
        return "stations/stationsList";
    }

    /**
     * @功能说明：跳转到添加岗位
     * @开发时间：2021-12-22
     * @开发人员：张涛
     */
    @RequestMapping("jumpAddStation")
    public String jumpAddStation() {
        logger.info("PermissionController>>jumpAddStation");
        logger.info("岗位管理>>添加岗位");
        List<DeptInfo> list = deptInfoService.listDeptInfo(allConfiguration.getDeptStatusOFF());
        request.getSession().setAttribute("DeptInfos", list);
        return "stations/addStation";
    }

    /**
     * @功能说明：ajax查询是否有重复数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     */
    @RequestMapping("ajaxDepartmentIFName")
    @ResponseBody
    public Json ajaxDepartmentIFName(@RequestParam(required = false) String stationName, String deptID) {
        logger.info("PermissionController>>ajaxDepartmentIFName");
        logger.info("添加岗位>>ajax查询是否有重复数据");
        logger.info("ajax查询是否有重复数据传参>>stationName=" + stationName + ">>deptID>>" + deptID);
        Json json = new Json();
        List<StationInfo> stationInfos = stationInfoService.listStationNameByStationName(stationName, deptID);
        System.out.println("stationInfo=" + stationInfos);
        if (stationInfos.size() > 0) {
            json.setCode(-1);
            json.setData("该岗位已存在！");
        } else {
            json.setCode(0);
            json.setData(null);
        }
        return json;
    }

    /**
     * @功能说明：编辑岗位
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    @RequestMapping("jumpUpdateStations")
    public String jumpUpdateStations(@RequestParam(required = false) String stationID, String currentPage) {
        logger.info("PermissionController>>jumpUpdateStations");
        logger.info("岗位管理>>修改岗位");
        logger.info("编辑岗位>>stationID=" + stationID + ">>currentPage>>" + currentPage);
        StationInfo stationInfoByStationID = stationInfoService.findStationInfoByStationID(stationID);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(stationInfoByStationID.getDeptID());
        List<DeptInfo> list = deptInfoService.listDeptInfo(allConfiguration.getDeptStatusOFF());
        request.getSession().setAttribute("DeptInfo", deptInfoByDeptID);
        request.getSession().setAttribute("DeptInfos", list);
        request.getSession().setAttribute("currentPage", currentPage);
        request.getSession().setAttribute("stationInfo", stationInfoByStationID);
        return "stations/updateStations";
    }

    /**
     * @功能说明：冻结岗位
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    @RequestMapping("freezeStations")
    public String freezeStations(@RequestParam(required = false) String stationID, String currentPage) {
        logger.info("PermissionController>>freezeStations");
        logger.info("岗位管理>>冻结岗位");
        logger.info("冻结岗位>>stationID=" + stationID + ">>currentPage>>" + currentPage);
        StationInfo stationInfoByStationID = stationInfoService.findStationInfoByStationID(stationID);
        List<MemberInfo> memberInfos = memberInfoService.listMenuInfo();
        for (MemberInfo memberInfo : memberInfos) {
            if (memberInfo.getStationIDs().contains(stationInfoByStationID.getStationID()) == true) {
                request.getSession().setAttribute("responseStr", "该岗位还有工作人员，不可冻结！");
                getStationList(Integer.parseInt(currentPage), "freezeStations");
                return "stations/stationsList";
            }
        }
        stationInfoByStationID.setStationStatus(allConfiguration.getStationStatusON());
        int i = stationInfoService.updateStation(stationInfoByStationID);
        request.getSession().setAttribute("responseStr", "已成功冻结" + i + "条数据");
        getStationList(Integer.parseInt(currentPage), "freezeStations");
        return "stations/stationsList";
    }

    /**
     * @功能说明：解冻岗位
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    @RequestMapping("defrostStation")
    public String defrostStation(@RequestParam(required = false) String stationID, String currentPage) {
        logger.info("PermissionController>>defrostStation");
        logger.info("岗位管理>>解冻岗位");
        logger.info("解冻岗位>>stationID=" + stationID + ">>currentPage>>" + currentPage);
        StationInfo stationInfoByStationID = stationInfoService.findStationInfoByStationID(stationID);
        stationInfoByStationID.setStationStatus(allConfiguration.getStationStatusOFF());
        int i = stationInfoService.updateStation(stationInfoByStationID);
        request.getSession().setAttribute("responseStr", "已成功解冻" + i + "条数据");
        getStationList(Integer.parseInt(currentPage), "freezeStations");
        return "stations/stationsList";
    }

    /**
     * @功能说明：添加岗位信息
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    @RequestMapping("addStation")
    public String addStation(@RequestParam(required = false) String deptID, String stationName, String permission) {
        logger.info("PermissionController>>addStation");
        logger.info("添加岗位>>确认添加");
        logger.info("解冻岗位>>deptID=" + deptID + ">>stationName>>" + stationName + ">>permission>>" + permission);
        String[] split = permission.split(",");
        Set set = new HashSet();
        for (String s : split) {
            set.add(s);
        }
        StationInfo stationInfo = new StationInfo();
        List<StationInfo> stationInfos = stationInfoService.listStationInfo();
        int initializeStatus = 0;
        initializeStatus = commonUtil.getInitializeStatus2(stationInfos);
        initializeStatus++;
        if (Integer.toString(initializeStatus).length() < 2) {
            log.info("PermissionController>>addStation>>当前编号为一位数");
            stationInfo.setStationID("0" + Integer.toString(initializeStatus));
        } else {
            log.info("PermissionController>>addStation>>当前编号为两位数");
            stationInfo.setStationID(Integer.toString(initializeStatus));
        }
        stationInfo.setStationName(stationName);
        String s = set.toString().substring(1, set.toString().length() - 1);
        String[] split1 = s.split(", ");
        String s2 = "";
        for (String s1 : split1) {
            s2 += s1 + ",";
        }
        stationInfo.setAuthorityIDs(s2.substring(0, s2.length() - 1));
        stationInfo.setDeptID(deptID);
        stationInfo.setAddTime(new Date());
        stationInfo.setStationStatus(allConfiguration.getStationStatusOFF());
        int i = stationInfoService.addStationInfo(stationInfo);
        log.info("已添加" + i + "条岗位记录");
        commonUtil.clearSession();
        request.getSession().setAttribute("responseStr", "成功添加" + i + "个岗位");
        getStationList(1, "addStation");
        return "stations/stationsList";
    }

    /**
     * @功能说明：查看岗位详情
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    @RequestMapping("findStation")
    public String findDepartment(@RequestParam(required = false) String stationID, String currentPage) {
        log.info("PermissionController>>findStation");
        log.info("岗位管理>>跳转到查看岗位信息");
        log.info("岗位编号=" + stationID + ";当前页=" + currentPage);
        StationInfo stationInfoByStationID = stationInfoService.findStationInfoByStationID(stationID);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(stationInfoByStationID.getDeptID());
        stationInfoByStationID.setDeptID(deptInfoByDeptID.getDeptName());
//        Map<String, Object> mapAuthority = getMapAuthority(stationInfoByStationID.getAuthorityIDs());
//        request.getSession().setAttribute("level1Menu",mapAuthority.get("level1Menu"));//一级菜单
//        request.getSession().setAttribute("level2Menu",mapAuthority.get("level2Menu"));//二级菜单
//        request.getSession().setAttribute("function",mapAuthority.get("function"));//功能
        request.getSession().setAttribute("stationInfo", stationInfoByStationID);
        request.getSession().setAttribute("currentPage", currentPage);
        return "stations/findStation";
    }


    /**
     * @功能说明：跳转到权限管理列表
     * @开发时间：2021-1-22
     * @开发人员：张涛
     */
    @RequestMapping("jumpPerHtml")
    public String jumpPerHtml(@RequestParam(required = false) String memberID, String currentPage,String userName) {
        log.info("PermissionController>>jumpPerHtml");
        log.info("岗位管理>>跳转到权限管理列表");
        logger.info("用户编号>>" + memberID + ">>页码>>" + currentPage);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        UserInfo userInfoByMemberID = userInfoService.findUserInfoByMemberID(memberID);
        char[] chars = userInfoByMemberID.getPermission().toCharArray();
        request.getSession().setAttribute("memberInfo", memberInfoByMemberID);
        List<MenuInfo> menuInfos = menuInfoService.listMenuInfo(allConfiguration.getMenuILevel1());//一级菜单
        for (MenuInfo menuInfo : menuInfos) {
            List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(menuInfo.getMenuID());//对应的二级菜单
            for (MenuInfo info : menuInfoListBySuperMenuID) {
                List<MenuInfo> menuInfoListBySuperMenuID1 = menuInfoService.findMenuInfoListBySuperMenuID(info.getMenuID());//对应的三级菜单
                info.setSubmenu3(menuInfoListBySuperMenuID1);
            }
            menuInfo.setSubmenu(menuInfoListBySuperMenuID);
        }
        System.out.println(menuInfos);
        for (MenuInfo menuInfo : menuInfos) {
            if (String.valueOf(chars[Integer.parseInt(menuInfo.getMenuID())-1]).equals("0")) {
                menuInfo.setSign("0");
            } else {
                menuInfo.setSign("1");
            }
            List<MenuInfo> submenu = menuInfo.getSubmenu();//二级菜单
            for (MenuInfo info : submenu) {
                if (String.valueOf(chars[Integer.parseInt(info.getMenuID())-1]).equals("0")) {
                    info.setSign("0");
                } else {
                    info.setSign("1");
                }
                List<MenuInfo> submenu3 = info.getSubmenu3();//三级菜单
                for (MenuInfo menuInfo1 : submenu3) {
                    System.out.println("标记位>>"+String.valueOf(chars[Integer.parseInt(menuInfo1.getMenuID())-1]).toString());
                    if (String.valueOf(chars[Integer.parseInt(menuInfo1.getMenuID())-1]).equals("0")) {
                        menuInfo1.setSign("0");
                    } else {
                        menuInfo1.setSign("1");
                    }
                }
            }
        }
        commonUtil.userPer(userName);
        request.getSession().setAttribute("menuInfos", menuInfos);
        return "permission/updatePermission";
    }

    /**
     * @功能说明：设置权限
     * @开发时间：2021-1-22
     * @开发人员：张涛
     */
    @RequestMapping("setUserPer")
    public String setUserPer(@RequestParam(required = false) String memberID, String id[],String userName) {
        log.info("PermissionController>>setUserPer");
        log.info("岗位管理>>设置权限");
        logger.info("用户编号>>" + memberID + ">>权限编号>>" + id);
        logger.info("权限信息===>");
        List<MenuInfo> menuInfos = menuInfoService.getlistMenuInfo();
        String permissionStr = "";
        Set<MenuInfo> menuInfoList = new HashSet<>();
        for (int i = 1; i <= menuInfos.size(); i++) {
            logger.info(i + ":" + request.getParameter(i + "") + "");
            if (request.getParameter(i + "") == null) {
                permissionStr = permissionStr + 0;
            } else if (request.getParameter(i + "").equals("on")) {
                menuInfoList.add(menuInfoService.findMenuInfoByMenuID(Integer.toString(i)));
                permissionStr = permissionStr + 1;
            }
        }
        List<MenuInfo> list2 = new ArrayList<>();
        List<MenuInfo> list3 = new ArrayList<>();
        for (MenuInfo menuInfo : menuInfoList) {
            if (menuInfo.getMenuILevel().equals(allConfiguration.getMenuILevel2())){
                list2.add(menuInfo);
            }
            if (menuInfo.getMenuILevel().equals(allConfiguration.getMenuILevel3())){
                list3.add(menuInfo);
            }
        }
        for (MenuInfo menuInfo : list2) {//二级菜单
            menuInfoList.add(menuInfoService.findMenuInfoByMenuID(menuInfo.getSuperMenuID()));
        }

        for (MenuInfo menuInfo : list3) {//三级菜单
            menuInfoList.add(menuInfoService.findMenuInfoByMenuID(menuInfo.getSuperMenuID()));//二级
            menuInfoList.add(menuInfoService.findMenuInfoByMenuID(menuInfoService.findMenuInfoByMenuID(menuInfo.getSuperMenuID()).getSuperMenuID()));//三级
        }
        for (MenuInfo menuInfo : menuInfoList) {
            System.out.println("编号>>"+menuInfo.getMenuID());
        }

        System.out.println("permission = " + permissionStr);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByMemberID(memberID);

        String strBuil = "" ;
        for (MenuInfo menuInfo : menuInfoList) {
            strBuil += menuInfo.getMenuID()+",";
        }
        System.out.println("字符》》"+strBuil);
        if (strBuil.equals("")){
            for (int i = 0; i < menuInfos.size(); i++) {
                strBuil +="0";
            }
            userInfoByUserID.setPermission(strBuil);
            userInfoService.updateUserInfoByUserID(userInfoByUserID);
            getUserHome(1, "权限管理");
            return "user/user";
        }
        String permission = userInfoByUserID.getPermission();
        StringBuilder stringBuilder = new StringBuilder(permission);
        int size = menuInfos.size();//菜单数量
        int length = stringBuilder.length();//用户权限长度
        for (int i = length; i < size; i++) {
            stringBuilder.append("0");
        }
        String s1 = stringBuilder.toString();
        userInfoByUserID.setPermission(s1);
        System.out.println(stringBuilder.length());
        String[] split = strBuil.split(",");
        for (String s : split) {
            int end = Integer.parseInt(s);//结束
            System.out.println("结束>>"+end);
            int start = end - 1;//开始
            System.out.println("开始>>"+start);
            stringBuilder.replace(start, end, "1");
        }
        System.out.println("stringBu》》"+stringBuilder.toString());
        userInfoByUserID.setPermission(stringBuilder.toString());
        userInfoService.updateUserInfoByUserID(userInfoByUserID);
        commonUtil.userPer(userName);
        getUserHome(1, "权限管理");
        return "user/user";
    }

    public static void main(String[] args) {
        String s = "1111111111111111111111111111111111111111111111111111111111111111111";
        System.out.println(s.length());
    }


    /****************************************方法区********************************************************************/
    /**
     * @功能说明：返回员工主页方法
     * @开发时间：2021-12-14
     * @开发人员：张涛
     */
    public Map<String, Object> getUserHome(int page, String current) {
        int memberInfoCount = memberInfoService.getMemberInfoCount(allConfiguration.getStatusDelete());
        PageModel pageModel = commonUtil.showPage(page, memberInfoCount, "UserInfoController>>" + current + ">>getUserHome");
        List<MemberInfo> memberInfos1 = memberInfoService.listMemberInfoByPage(allConfiguration.getStatusDelete(), pageModel);
        for (MemberInfo memberInfos : memberInfos1) {
            String[] split = memberInfos.getStationIDs().split(",");
            String str = "";
            for (String s : split) {
                System.out.println("s====s" + s);
                str = str + stationInfoService.findStationInfoByStationID(s).getStationName() + "+";
            }
            memberInfos.setStationIDs(str.substring(0, str.length() - 1));
            if (memberInfos.getDeptID().equals("0")) {
                memberInfos.setDeptID("未分配部门");
            } else {
                DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(memberInfos.getDeptID());
                if (deptInfoByDeptID != null) {
                    memberInfos.setDeptID(deptInfoByDeptID.getDeptName());
                } else {
                    memberInfos.setDeptID(null);

                }
            }
        }
        Iterator<MemberInfo> iterator = memberInfos1.iterator();
        while (iterator.hasNext()) {
            int status = iterator.next().getStatus();
            if (status == allConfiguration.getStatusDelete()) {
                iterator.remove();
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("memberInfos1", memberInfos1);
        map.put("pageModel", pageModel);
        request.getSession().setAttribute("users", memberInfos1);
        request.getSession().setAttribute("page", pageModel);
        return map;
    }

    /**
     * @功能说明：返回岗位管理主页方法
     * @开发时间：2021-12-22
     * @开发人员：张涛
     */
    public void getStationList(int page, String current) {
        logger.info("配置文件" + Integer.toString(allConfiguration.getStationStatusOFF()));
        int stationInfoCount = stationInfoService.stationInfoCount(allConfiguration.getStationStatusOFF());
        PageModel pageModel = commonUtil.showPage(page, stationInfoCount, "PermissionController>>" + current + ">>getDepartment");
        List<StationInfo> stationInfos = stationInfoService.listStationInfoByPage(pageModel);
        for (StationInfo stationInfo : stationInfos) {
            String dateStrForDayChinese = ActionUtil.getDateStrForDayChinese(stationInfo.getAddTime());
            stationInfo.setAddTimeStr(dateStrForDayChinese);
            DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(stationInfo.getDeptID());
            stationInfo.setDeptID(deptInfoByDeptID.getDeptName());
        }
        request.getSession().setAttribute("stationInfos", stationInfos);
        request.getSession().setAttribute("page", pageModel);
    }

    /**
     * @功能说明：根据岗位权限查询一级菜单、二级菜单、功能
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    public Map<String, Object> getMapAuthority(String authorityGroup) {
        log.info("PermissionController>>getMapAuthority");
        log.info("岗位管理>>根据岗位权限查询一级菜单、二级菜单、功能");
        log.info("权限组=" + authorityGroup);
        String[] split = authorityGroup.split(",");
        Map<String, Object> map = new HashMap<>();
        Set level1Menu = new HashSet();//一级菜单
        Set level2Menu = new HashSet();//二级菜单
        List function = new ArrayList();//功能
        for (String s : split) {
            System.out.println(s);
            //查询一级菜单
            AuthorityInfo authorityInfoById = authorityInfoDao.findAuthorityInfoById(s);
            System.out.println(authorityInfoById);
            MenuInfo menuInfoByMenuID = menuInfoService.findMenuInfoByMenuID(authorityInfoById.getMenuID());
            System.out.println(menuInfoByMenuID);
            if (menuInfoByMenuID.getSuperMenuID() == null) {
                //一级菜单
                level1Menu.add(menuInfoByMenuID);//存入一级菜单
            } else {
                //二级菜单
                level2Menu.add(menuInfoByMenuID);//存入二级菜单
            }
            function.add(authorityInfoById.getAuthorityName());
        }
        map.put("level1Menu", level1Menu);
        map.put("level2Menu", level2Menu);
        map.put("function", function);
        return map;
    }
}
