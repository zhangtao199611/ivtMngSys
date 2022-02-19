package com.example.ivt_mng_sys.controller.UserName;

import com.alibaba.fastjson.JSON;
import com.example.ivt_mng_sys.Util.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @模块说明：用户管理
 * @创建时间：2021-12-12
 * @开发人员：张涛
 * */
@Controller
@RequestMapping("UserInfoController")
@Slf4j
public class UserInfoController {
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
    private CommonUtil commonUtil;
    @Resource
    private UnitInfoService unitInfoService;

    /**
     * 读取conf.yml配置文件
     * */
    private AllConfiguration allConfiguration = new AllConfiguration();
    @Autowired
    public UserInfoController(AllConfiguration allConfiguration){
        this.allConfiguration = allConfiguration;
    }

    Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    /**
     * @功能说明：员工展示首页
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * @param ：入参：null  出参：跳转页面user.jsp
     * */
    @RequestMapping("userInfoList")
    public String userInfoList(String userName){
        logger.info("用户管理>>人员管理");
        commonUtil.clearSession();
        getUserHome(1,"updateUser");
        commonUtil.userPer(userName);
        return "user/user";
    }


    /**
     * @功能说明：员工上下页
     * @开发时间：2021-1-16
     * @开发人员：张涛
     * */
    @RequestMapping("upDownUserInfo")
    public String upDownUserInfo(@RequestParam(required = false)String pageSize,String currentPage){
        log.info("pageSize="+pageSize+">>currentPage>>"+currentPage);
        getUserHome(Integer.parseInt(currentPage),"updateUser");
        return "user/user";
    }

    /**
     * @功能说明：跳转到添加员工信息页面
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAddUser")
    public String jumpAddUser(String userName){
        logger.info("人员管理>>新增人员");
        //登录账号选择
        List<UserInfo> userInfos = userInfoService.listUserByLock(allConfiguration.getLockException());
        if (userInfos.size()==0){
            request.getSession().setAttribute("account",0);
        }
        request.getSession().setAttribute("userInfos",userInfos);
        //部门选择
        List<DeptInfo> deptInfos = deptInfoService.listDeptInfo(allConfiguration.getDeptStatusOFF());
        request.getSession().setAttribute("DeptInfos",deptInfos);
        //食堂选择
        List<UnitInfo> unitInfos = unitInfoService.listUnitInfo();
        commonUtil.userPer(userName);
        request.getSession().setAttribute("unitInfos",unitInfos);
        return "user/addUser";
    }
    /**
     * @功能说明：部门查询
     * @开发时间：2021-1-13
     * @开发人员：张涛
     * */
    @RequestMapping("listDeptInfo")
    @ResponseBody
    public Json listDeptInfo(@RequestParam(required = false)String unitID){
        System.out.println("单位编号="+unitID);
        Json json = new Json();
        List<DeptInfo> list = deptInfoService.listDeptInfoByUnitID(unitID);
        request.getSession().setAttribute("DeptInfos",list);
        json.setCode(200);
        json.setData(list);
        return json;
    }

    /**
     * @功能说明：权限查询
     * @开发时间：2021-12-13
     * @开发人员：张涛
     * */
    @RequestMapping("listStationInfoByStationName")
    @ResponseBody
    public Object listFeaturesAjaxForOption(String deptID) {
        if (deptID.length()<2){
            deptID = "0"+deptID;
        }

        List<StationInfo> featuresList =  stationInfoService.listStationInfoByStationStatus(allConfiguration.getStationStatusOFF(),deptID);
        List<Map> optionList = new ArrayList<Map>();
        for (StationInfo features : featuresList) {
			/*		前端组件的数据格式：
			 *          {label:"选项1",value:0},
			            {label:"选项2",value:1},
			            {label:"选项3",value:2},
			            {label:"选项4",value:3},
			            {label:"选项5",value:4},
			            {label:"选项6",value:5}
			 */
            Map<String, Object> map = new HashMap<>();
            map.put("label", features.getStationName());
            map.put("value", features.getStationID());
            optionList.add(map);
        }
        return JSON.toJSON(optionList);
    }

    /**
     * @功能说明：添加员工信息再跳转到员工主页
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @RequestMapping("addMemberInfo")
    public String addMemberInfo(@RequestParam(required = false)String TureName,String jobID,String phoneNumber,String deptID,String[] features,String unitID){
        logger.info("添加员工>>确认添加");
        logger.info("名称>>"+TureName+">>工号>>"+jobID+">>号码>>"+phoneNumber+">>部门编号>>"+deptID+">>权限>>"+features+">>单位编号>>"+unitID);
        if (TureName.equals("") || jobID.equals("") || jobID.equals("") || phoneNumber.equals("") || deptID.equals("") || features.length==0 || unitID.equals("")){
            commonUtil.clearSession();
            List<DeptInfo> deptInfos = deptInfoService.listDeptInfo(allConfiguration.getDeptStatusOFF());
            request.getSession().setAttribute("DeptInfos",deptInfos);
            request.getSession().setAttribute("responseStr","请输入员工信息");
            return "user/addUser";
        }
            StringBuffer stringBuffer = new StringBuffer();
            for (String feature : features) {
                stringBuffer.append(feature+",");
            }
            String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            String userId = "M" + ActionUtil.getDateStrForString(new Date()) + ActionUtil.getRandomString(4);
            String accountID = addAccount(userId, jobID);
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setMemberID(userId);
            memberInfo.setUserID(accountID);
            memberInfo.setDeptID(deptID);
            memberInfo.setStationIDs(substring);
            memberInfo.setMemberName(TureName);
            memberInfo.setJobID(jobID);
            memberInfo.setStatus(allConfiguration.getStatusOFF());
            memberInfo.setPhoneNumber(phoneNumber);
            memberInfo.setAddTime(new Date());
            memberInfo.setUnitID(unitID);
            int i = memberInfoService.addMemberInfo(memberInfo);
            log.info("插入员工"+i+"条数据");
            commonUtil.clearSession();
            request.getSession().setAttribute("responseStr","成功插入"+i+"条数据");
            getUserHome(1,"updateUser");
            return "user/user";
    }

    /**
     * @功能说明：ajax查询是否有重复工号和手机号码
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    @RequestMapping("ajaxFindJsbIDORPhoneNumber")
    @ResponseBody
    public Json ajaxFindJsbIDORPhoneNumber(String jobID,String phoneNumber){
        logger.info("添加员工>>验证是否有重复数据");
        Json json = new Json();
        if (jobID.equals("")){
            List<MemberInfo> memberInfos = memberInfoService.listMemberInfoByJobIDOrPhoneNumber("", phoneNumber);
            if (memberInfos.size()!=0){
                json.setCode(1);
                json.setData("您输入的手机号码已重复");
            }else {
                json.setCode(0);
                json.setData(null);
            }
        }else if (phoneNumber.equals("")){
            List<MemberInfo> memberInfos = memberInfoService.listMemberInfoByJobIDOrPhoneNumber(jobID, "");
            if (memberInfos.size()!=0){
                json.setCode(1);
                json.setData("您输入的工号已重复");
            }else {
                if (jobID.length()!=6){
                    json.setCode(1);
                    json.setData("请输入工号长度为6位得");
                }else {
                    json.setCode(0);
                    json.setData(null);
                }
            }
        }
        return json;
    }

    /**
     * @功能说明：跳转到修改用户界面
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * @param :入参memberID=员工编号  currentPage=页码
     * */
    @RequestMapping("jumpUpdateUser")
    public String jumpUpdateUser(@RequestParam(required = false)String memberID,String currentPage,String userName){
        log.info("员工管理>>编辑员工");
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        try {
            memberInfoByMemberID.setDeptIDName(deptInfoService.findDeptInfoByDeptID(memberInfoByMemberID.getDeptID()).getDeptName());
        } catch (Exception e) {
            log.info("员工管理>>编辑员工>>报错信息："+e);
            memberInfoByMemberID.setDeptIDName("还未安排部门");
        }
        List<DeptInfo> deptInfos = deptInfoService.listDeptInfo(allConfiguration.getDeptStatusOFF());
        commonUtil.userPer(userName);
        request.getSession().setAttribute("DeptInfos",deptInfos);
        request.getSession().setAttribute("memberInfo",memberInfoByMemberID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "user/updateUser";
    }

    /**
     * @功能说明：修改用户信息
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * @param :入参memberID=员工编号
     * */
    @RequestMapping("updateUser")
    public String updateUser(MemberInfo memberInfo,String userName){
        log.info("修改员工>>确认修改");
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberInfo.getMemberID());
        memberInfoByMemberID.setDeptID(memberInfo.getDeptID());
        memberInfoByMemberID.setStationIDs(memberInfo.getStationIDs());
        memberInfoByMemberID.setMemberName(memberInfo.getMemberName());
        memberInfoByMemberID.setJobID(memberInfo.getJobID());
        memberInfoByMemberID.setPhoneNumber(memberInfo.getPhoneNumber());
        memberInfoByMemberID.setUpdateTime(new Date());
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        log.info("员工信息已修改"+i+"条数据");
        commonUtil.clearSession();
        commonUtil.userPer(userName);
        request.getSession().setAttribute("responseStr","成功修改"+i+"数据");
        getUserHome(Integer.parseInt(memberInfo.getCurrentPage()),"updateUser");
        return "user/user";
    }

    /**
     * @功能说明：冻结员工
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @RequestMapping("freezeUser")
    public String freezeUser(@RequestParam(required = false)String memberID,String currentPage,String userName){
        log.info("员工管理>>冻结员工");
        System.out.println(memberID);
        System.out.println(currentPage);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        memberInfoByMemberID.setStatus(allConfiguration.getStatusNO());//用户冻结
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        log.info("已冻结"+i+"条用户");
        try {
            UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(memberInfoByMemberID.getUserID());
            userInfoByUserID.setLockStatus(allConfiguration.getLockException());//账户冻结
            userInfoService.updateUserInfoByUserID(userInfoByUserID);
        } catch (Exception e) {
            log.info("空指针异常："+e);
        }
        commonUtil.clearSession();
        getUserHome(Integer.parseInt(currentPage), "deleteUser");
        commonUtil.userPer(userName);
        request.getSession().setAttribute("responseStr","成功冻结"+i+"条数据");
        return "user/user";
    }

    /**
     * @功能说明：删除员工
     * @开发时间：2021-12-19
     * @开发人员：张涛
     * */
    @RequestMapping("deleteUser")
    public String deleteUser(@RequestParam(required = false)String memberID,String currentPage,String userName){
        log.info("员工管理>>删除员工");
        System.out.println(memberID);
        System.out.println(currentPage);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        memberInfoByMemberID.setStatus(allConfiguration.getStatusDelete());//员工删除
        memberInfoByMemberID.setUpdateTime(new Date());//删除时间
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        log.info("已删除"+i+"条用户");
        try {
            UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(memberInfoByMemberID.getUserID());
            userInfoByUserID.setLockStatus(allConfiguration.getLockDelete());//账户冻结
            userInfoService.updateUserInfoByUserID(userInfoByUserID);
        } catch (Exception e) {
            log.info("空指针异常："+e);
        }
        commonUtil.clearSession();
        getUserHome(Integer.parseInt(currentPage), "deleteUser");
        commonUtil.userPer(userName);
        request.getSession().setAttribute("responseStr","成功删除"+i+"条数据");
        return "user/user";
    }

    /**
     * @功能说明：恢复员工
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @RequestMapping("recoverUser")
    public String recoverUser(@RequestParam(required = false)String memberID,String currentPage,String userName){
        log.info("UserInfoController>>recoverUser");
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        memberInfoByMemberID.setStatus(allConfiguration.getStatusOFF());
        memberInfoByMemberID.setUpdateTime(new Date());
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(memberInfoByMemberID.getUserID());
        userInfoByUserID.setLockStatus(allConfiguration.getLockNormal());
        userInfoService.updateUserInfoByUserID(userInfoByUserID);
        commonUtil.clearSession();
        commonUtil.userPer(userName);
        getUserHome(Integer.parseInt(currentPage),"UserInfoController>>recoverUser");
        request.getSession().setAttribute("responseStr","成功恢复"+i+"条数据");
        return "user/user";
    }

    /**
     * @功能说明：员工详情
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @RequestMapping("detailsUser")
    public String detailsUser(@RequestParam(required = false)String memberID,String currentPage){
        log.info("UserInfoController>>detailsUser");
        System.out.println(memberID);
        System.out.println(currentPage);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(memberInfoByMemberID.getDeptID());
        request.getSession().setAttribute("dept",deptInfoByDeptID);
        request.getSession().setAttribute("memberInfo",memberInfoByMemberID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "user/findUser";
    }

    @RequestMapping("findUserInfo")
    public String findUserInfo(@RequestParam(required = false)String userID,String currentPage){
        log.info("UserInfoController>>detailsUser");
        System.out.println(userID);
        System.out.println(currentPage);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(userID);
        userInfoByUserID.setUserID(memberInfoService.findMemberInfoByMemberID(userInfoByUserID.getMemberID()).getMemberName());
        userInfoByUserID.setLatestLoginTimeStr(ActionUtil.getDateStrForDayChinese(userInfoByUserID.getLatestLoginTime()));
        userInfoByUserID.setAddTimeStr(ActionUtil.getDateStrForDayChinese(userInfoByUserID.getAddTime()));
        userInfoByUserID.setUpdateTimeStr(ActionUtil.getDateStrForDayChinese(userInfoByUserID.getUpdateTime()));
        request.getSession().setAttribute("userInfoByUserID",userInfoByUserID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "user/findAccount";
    }




    /**************************************账户管理********************************************************************/
    /**
     * @功能说明：跳转到账户管理列表
     * @开发时间：2021-12-15
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAccountList")
    public String jumpAccountList(@RequestParam(required = false)String userName){
        log.info("用户管理>>账户管理");
        log.info("UserInfoController>>jumpAccountList");
        commonUtil.clearSession();
        request.getSession().setAttribute("userName",userName);
        getAccount(1,"UserInfoController>>jumpAccountList");
        commonUtil.userPer(userName);
        return "user/account";
    }
    /**
     * @功能说明：账户上下页分页
     * @开发时间：2021-01-17
     * @开发人员：张涛
     * */
    @RequestMapping("upDownPage")
    public String upDownPage(@RequestParam(required = false)String pageSize,String currentPage){
        getAccount(Integer.parseInt(currentPage),"UserInfoController>>jumpAccountList");
        return "user/account";
    }

    public static void main(String[] args) {
        String word = "123456";
        String s = MD5Util.MD5(word);
        System.out.println(s);
    }


    /**
     * @功能说明：跳转到账户修改界面
     * @开发时间：2021-12-20
     * @开发人员：张涛
     * */
    @RequestMapping("jumpUpdateAccount")
    public String jumpUpdateAccount(@RequestParam(required = false)String userID,String currentPage){
        log.info("账户管理>>编辑按钮");
        log.info("UserInfoController>>jumpUpdateAccount");
        log.info("账户编号="+userID+">>当前页码="+currentPage);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(userID);
        try {
            MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(userInfoByUserID.getMemberID());
            userInfoByUserID.setMemberID(memberInfoByMemberID.getMemberName());
        } catch (Exception e) {
            log.info("空指针异常="+e);
        }
        request.getSession().setAttribute("currentPage",currentPage);
        request.getSession().setAttribute("memberInfo",userInfoByUserID);
        return "user/updateAccount";
    }

    /**
     * @功能说明：修改账户信息
     * @开发时间：2021-12-20
     * @开发人员：张涛
     * */
    @RequestMapping("updateAccount")
    public String updateAccount(@RequestParam(required = false)String loginName,String loginPassWord,String userID,String currentPage){
        log.info("账户编辑>>确认修改");
        log.info("UserInfoController>>updateAccount");
        log.info("账户账号="+loginName+";账号密码="+loginPassWord+";账户编号="+userID+";当前页面="+currentPage);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(userID);
        userInfoByUserID.setLoginPassWord(loginPassWord);
        userInfoByUserID.setUpdateTime(new Date());
        int i = userInfoService.updateUserInfoByUserID(userInfoByUserID);
        log.info("已修改"+i+"条记录");
        commonUtil.clearSession();
        request.getSession().setAttribute("responseStr","成功修改"+i+"数据");
        getAccount(Integer.parseInt(currentPage),"UserInfoController>>updateAccount");
        return "user/account";
    }

    /**
     * @功能说明：冻结账户
     * @开发时间：2021-12-20
     * @开发人员：张涛
     * */
    @RequestMapping("freezeAccount")
    public String freezeAccount(@RequestParam(required = false)String userID,String currentPage,String userName){
        log.info("账户管理>>冻结账户");
        UserInfo userInfoByUserID1 = userInfoService.findUserInfoByUserID(userID);
        String memberID = userInfoByUserID1.getMemberID();
        System.out.println(memberID);
        System.out.println(currentPage);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        memberInfoByMemberID.setStatus(allConfiguration.getStatusNO());//用户冻结
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        log.info("已冻结"+i+"条账户");
        try {
            UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(memberInfoByMemberID.getUserID());
            userInfoByUserID.setLockStatus(allConfiguration.getLockException());//账户冻结
            userInfoService.updateUserInfoByUserID(userInfoByUserID);
        } catch (Exception e) {
            log.info("空指针异常："+e);
        }
        commonUtil.clearSession();
        commonUtil.userPer(userName);
        getAccount(Integer.parseInt(currentPage), "UserInfoController>>freezeAccount");
        request.getSession().setAttribute("responseStr","成功冻结"+i+"条数据");
        return "user/account";
    }
    /**
     * @功能说明：恢复账户
     * @开发时间：2021-12-20
     * @开发人员：张涛
     * */
    @RequestMapping("recoverAccount")
    public String recoverAccount(@RequestParam(required = false)String userID,String currentPage,String userName){
        log.info("UserInfoController>>recoverUser");
        UserInfo userInfoByUserID1 = userInfoService.findUserInfoByUserID(userID);
        String memberID = userInfoByUserID1.getMemberID();
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(memberID);
        memberInfoByMemberID.setStatus(allConfiguration.getStatusOFF());
        memberInfoByMemberID.setUpdateTime(new Date());
        int i = memberInfoService.updateMemberInfoByMemberID(memberInfoByMemberID);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(memberInfoByMemberID.getUserID());
        userInfoByUserID.setLockStatus(allConfiguration.getLockNormal());
        userInfoService.updateUserInfoByUserID(userInfoByUserID);
        commonUtil.clearSession();
        getAccount(Integer.parseInt(currentPage),"UserInfoController>>recoverAccount");
        commonUtil.userPer(userName);
        request.getSession().setAttribute("responseStr","成功恢复"+i+"条数据");
        return "user/account";
    }
    /**
     * @功能说明：返回账户主页
     * @开发时间：2021-1-10
     * @开发人员：张涛
     * */
    @RequestMapping("returnHome")
    public String returnHome(@RequestParam(required = false)String currentPage){
        log.info("UserInfoController>>returnHome");
        log.info("currentPage>>"+currentPage);
        String[] split = currentPage.split(",");
        System.out.println(split[0]);
        getAccount(Integer.parseInt(split[0]),"UserInfoController>>returnHome");
        return "user/account";
    }
    /**
     * @功能说明：返回员工主页
     * @开发时间：2021-1-10
     * @开发人员：张涛
     * */


    /*****************************************部门管理*******************************************************************/

    /**
     * @功能说明：跳转到部门列表
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpPermissionList")
    public String jumpPermissionList(String userName){
        log.info("UserInfoController>>jumpPermissionList");
        log.info("用户管理>>部门管理");
        commonUtil.clearSession();
        getDepartmentList(1,"UserInfoController>>jumpPermissionList");
        commonUtil.userPer(userName);
        return "department/department";
    }
    /**
     * @功能说明：跳转到添加部门界面
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpAddEpartment")
    public String jumpAdddEpartment(){
        log.info("UserInfoController>>jumpAddEpartment");
        log.info("部门管理>>添加部门");
        return "department/addEpartment";
    }



    /**
     * @功能说明：添加部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("addEpartment")
    public String addEpartment(@RequestParam(required = false)String deptName,String deptExplain){
        log.info("UserInfoController>>addEpartment");
        log.info("部门管理>>添加部门");
        log.info("部门名称="+deptName+";部门说明="+deptExplain);
        DeptInfo deptInfo = new DeptInfo();
        List<DeptInfo> list = deptInfoService.listDeptIDByDeptID();
        int initializeStatus = 0;
        initializeStatus = commonUtil.getInitializeStatus(list);
        initializeStatus++;
        if (Integer.toString(initializeStatus).length()<2){
            log.info("UserInfoController>>addEpartment>>当前编号为一位数");
            deptInfo.setDeptID("0"+Integer.toString(initializeStatus));
        }else {
            log.info("UserInfoController>>addEpartment>>当前编号为两位数");
            deptInfo.setDeptID(Integer.toString(initializeStatus));
        }
        deptInfo.setDeptName(deptName);
        deptInfo.setEstablished(new Date());
        deptInfo.setDeptExplain(deptExplain);
        deptInfo.setDeptStatus(allConfiguration.getDeptStatusOFF());
        int i = deptInfoService.addDeptInfo(deptInfo);
        log.info("已添加"+i+"条部门记录");
        commonUtil.clearSession();
        request.getSession().setAttribute("responseStr","成功添加"+i+"个部门");
        getDepartmentList(1,"UserInfoController>>addEpartment");
        return "department/department";
    }

    /**
     * @功能说明：ajax查询是否有重复部门数据
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("ajaxEpartmentName")
    @ResponseBody
    public Json ajaxEpartmentName(String deptName){
        logger.info("添加部门>>验证是否有重复数据");
        Json json = new Json();
        DeptInfo deptInfoByDeptName = deptInfoService.findDeptInfoByDeptName(deptName);
        if (deptInfoByDeptName!=null){
            json.setCode(-1);
            json.setData("该部门已存在！");
        }else {
            json.setCode(0);
            json.setData(null);
        }
        return json;
    }
    /**
     * @功能说明：删除部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("deleteDepartment")
    public String deleteDepartment(@RequestParam(required = false)String deptID,String currentPage){
        log.info("UserInfoController>>deleteDepartment");
        log.info("部门管理>>删除部门");
        log.info("部门编号="+deptID+";当前页="+currentPage);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(deptID);
        List<MemberInfo> memberInfos = memberInfoService.listMemberInfoByDeptID(deptInfoByDeptID.getDeptID());
        if (memberInfos.size()>0){
            commonUtil.clearSession();
            request.getSession().setAttribute("responseStr",deptInfoByDeptID.getDeptName()+"还有"+memberInfos.size()+"人，不可冻结");
        }else {
            deptInfoByDeptID.setDeptStatus(allConfiguration.getDeptStatusNO());
            int i = deptInfoService.updateDeptInfoByDeptID(deptInfoByDeptID);
            commonUtil.clearSession();
            request.getSession().setAttribute("responseStr","已成功冻结"+i+"条记录");
        }
        getDepartmentList(Integer.parseInt(currentPage),"UserInfoController>>deleteDepartment");
        return "department/department";
    }

    /**
     * @功能说明：结冻部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("defrostDepartment")
    public String defrostDepartment(@RequestParam(required = false)String deptID,String currentPage){
        log.info("UserInfoController>>deleteDepartment");
        log.info("部门管理>>删除部门");
        log.info("部门编号="+deptID+";当前页="+currentPage);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(deptID);
        deptInfoByDeptID.setDeptStatus(allConfiguration.getDeptStatusOFF());
        int i = deptInfoService.updateDeptInfoByDeptID(deptInfoByDeptID);
        commonUtil.clearSession();
        request.getSession().setAttribute("responseStr","已成功结冻"+i+"条记录");
        getDepartmentList(Integer.parseInt(currentPage),"UserInfoController>>deleteDepartment");
        return "department/department";
    }

    /**
     * @功能说明：跳转修改部门页面
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("jumpUpdateDepartment")
    public String jumpUpdateDepartment(@RequestParam(required = false)String deptID,String currentPage){
        log.info("UserInfoController>>jumpUpdateDepartment");
        log.info("部门管理>>跳转到修改部门信息");
        log.info("部门编号="+deptID+";当前页="+currentPage);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(deptID);
        request.getSession().setAttribute("deptInfoByDeptID",deptInfoByDeptID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "department/updateDepartment";
    }

    @RequestMapping("findDepartment")
    public String findDepartment(@RequestParam(required = false)String deptID,String currentPage){
        log.info("UserInfoController>>findDepartment");
        log.info("部门管理>>跳转到查看部门信息");
        log.info("部门编号="+deptID+";当前页="+currentPage);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(deptID);
        request.getSession().setAttribute("deptInfoByDeptID",deptInfoByDeptID);
        request.getSession().setAttribute("currentPage",currentPage);
        return "department/findDepartment";
    }

    /**
     * @功能说明：修改部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @RequestMapping("updateDepartment")
    public String UpdateDepartment(@RequestParam(required = false)String deptID,String deptName,String deptExplain,String currentPage){
        log.info("UserInfoController>>updateDepartment");
        log.info("修改部门信息>>确认修改");
        log.info("部门编号="+deptID+";当前页="+currentPage);
        DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(deptID);
        deptInfoByDeptID.setDeptName(deptName);
        deptInfoByDeptID.setDeptExplain(deptExplain);
        int i = deptInfoService.updateDeptInfoByDeptID(deptInfoByDeptID);
        log.info("部门信息已修改"+i+"条数据");
        commonUtil.clearSession();
        request.getSession().setAttribute("responseStr","成功修改"+i+"数据");
        getDepartmentList(Integer.parseInt(currentPage),"updateDepartment");
        return "department/department";
    }


    /*****************************************方法区*******************************************************************/

    /**
     * @功能说明：返回员工主页方法
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    public Map<String,Object> getUserHome(int page,String current){
        int memberInfoCount = memberInfoService.getMemberInfoCount(allConfiguration.getStatusDelete());
        PageModel pageModel = commonUtil.showPage(page, memberInfoCount,"UserInfoController>>"+current+">>getUserHome");
        List<MemberInfo> memberInfos1 = memberInfoService.listMemberInfoByPage(allConfiguration.getStatusDelete(),pageModel);
        for (MemberInfo memberInfos : memberInfos1) {
            String[] split = memberInfos.getStationIDs().split(",");
            String str = "";
            for (String s : split) {
                System.out.println("s====s"+s);
                str = str + stationInfoService.findStationInfoByStationID(s).getStationName()+"+";
            }
            memberInfos.setStationIDs(str.substring(0,str.length()-1));
            if (memberInfos.getDeptID().equals("0")){
                memberInfos.setDeptID("未分配部门");
            }else {
                DeptInfo deptInfoByDeptID = deptInfoService.findDeptInfoByDeptID(memberInfos.getDeptID());
                if (deptInfoByDeptID!=null){
                    memberInfos.setDeptID(deptInfoByDeptID.getDeptName());
                }else {
                    memberInfos.setDeptID(null);

                }
            }
        }
        Iterator<MemberInfo> iterator = memberInfos1.iterator();
        while (iterator.hasNext()){
            int status = iterator.next().getStatus();
            if (status==allConfiguration.getStatusDelete()){
                iterator.remove();
            }
        }
        Map<String,Object> map = new HashMap();
        map.put("memberInfos1",memberInfos1);
        map.put("pageModel",pageModel);
        request.getSession().setAttribute("users",memberInfos1);
        request.getSession().setAttribute("page", pageModel);
        return map;
    }
    /**
     * @功能说明：返回账户主页方法
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public void getAccount(int page,String current){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        int UserInfoCount = userInfoService.getUserInfoCount(allConfiguration.getLockDelete());
        PageModel pageModel = commonUtil.showPage(page, UserInfoCount,"UserInfoController>>"+current+">>getUserHome");
        List<UserInfo> userInfos = userInfoService.listUserInfoByPage(allConfiguration.getLockDelete(),pageModel);
        for (UserInfo userInfo : userInfos) {
            MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(userInfo.getMemberID());
            userInfo.setMemberID(memberInfoByMemberID.getMemberName());
            if (userInfo.getLatestLoginTime()==null){
                userInfo.setLatestLoginTimeStr("未激活账号");
            }else {
                userInfo.setLatestLoginTimeStr(sf.format(userInfo.getLatestLoginTime()));
            }
            if (userInfo.getLatestLoginDevID()==null){
                userInfo.setLatestLoginDevID("还未登录设备");
            }
        }
        request.getSession().setAttribute("userInfos",userInfos);
        request.getSession().setAttribute("page", pageModel);
    }
    /**
     * @功能说明：添加账户方法
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public String addAccount(String memberID,String loginName){
        log.info("添加账户信息");
        String accountID = "U" + ActionUtil.getDateStrForString(new Date()) + ActionUtil.getRandomString(4);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(accountID);
        userInfo.setMemberID(memberID);
        userInfo.setLoginName(loginName);
        userInfo.setLoginPassWord(MD5Util.MD5(allConfiguration.getPassword()));//初始化密码
        userInfo.setAddTime(new Date());
        log.info("待激活="+Integer.toString(allConfiguration.getLockToActivate()));
        userInfo.setLockStatus(allConfiguration.getLockToActivate());//待激活
        userInfoService.addUserInfo(userInfo);
        return accountID;
    }

    /**
     * @功能说明：返回部门管理主页方法
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    public void getDepartmentList(int page,String current){
        int departmentListCount = deptInfoService.departmentListCount(allConfiguration.getDeptStatusOFF());
        PageModel pageModel = commonUtil.showPage(page, departmentListCount,"UserInfoController>>"+current+">>getDepartment");
        List<DeptInfo> deptInfos = deptInfoService.listDeptInfoByPage(pageModel);
        for (DeptInfo deptInfo : deptInfos) {
            String dateStrForDayChinese = ActionUtil.getDateStrForDayChinese(deptInfo.getEstablished());
            deptInfo.setEstablishedStr(dateStrForDayChinese);
        }
        request.getSession().setAttribute("deptInfos",deptInfos);
        request.getSession().setAttribute("page", pageModel);
    }
}
