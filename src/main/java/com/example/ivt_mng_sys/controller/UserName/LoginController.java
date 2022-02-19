package com.example.ivt_mng_sys.controller.UserName;

import com.example.ivt_mng_sys.Util.AllConfiguration;
import com.example.ivt_mng_sys.Util.ApiResult;
import com.example.ivt_mng_sys.Util.MD5Util;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.MenuInfo;
import com.example.ivt_mng_sys.entity.SystemConfig;
import com.example.ivt_mng_sys.entity.UserInfo;
import com.example.ivt_mng_sys.service.MemberInfoService;
import com.example.ivt_mng_sys.service.MenuInfoService;
import com.example.ivt_mng_sys.service.SystemConfigService;
import com.example.ivt_mng_sys.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("LoginController")
public class LoginController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SystemConfigService systemConfigService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private HttpServletRequest request;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private MenuInfoService menuInfoService;
    @Resource
    private AllConfiguration allConfiguration;


    @RequestMapping("Login")
    public String login(HttpServletRequest request) {
        logger.info("前往登录页");
        saveUserInfoToPage(request, null);
        saveSystemConfigToPage(request);
        return "login";
    }

    @RequestMapping("authLogin")
    @ResponseBody
    public ApiResult testLogin(String username, String password, String verify) {
        logger.info("登录功能编号>>" + verify);
        UserInfo userInfo = userInfoService.login(username, MD5Util.MD5(password));
        saveUserInfoToPage(request, userInfo);
        ApiResult apiResult = new ApiResult();
        if (userInfo != null) {
            if (userInfo.getLockStatus() == allConfiguration.getLockException()) {
                apiResult.setResult(500);
                apiResult.setMsg("账户异常，请联系管理员！");
                apiResult.setResultData(userInfo);
                return apiResult;//异常
            }
            if (userInfo.getLockStatus() == allConfiguration.getLockToActivate()) {
                apiResult.setResult(400);
                apiResult.setMsg("请修改初始密码！");
                apiResult.setResultData(userInfo);
                return apiResult;//未激活
            }
            if (userInfo.getLockStatus() == allConfiguration.getLockDelete()) {
                apiResult.setResult(300);
                apiResult.setMsg("账号已删除，请联系管理员！");
                apiResult.setResultData(userInfo);
                return apiResult;//删除
            }
        }
        request.getSession().setAttribute("userInfo", userInfo);
        if (userInfo == null) {
            apiResult.setResult(250);
            apiResult.setMsg("账户密码错误，请重新输入！");
            apiResult.setResultData(userInfo);
            return apiResult;
        } else {
            userInfo.setLatestLoginTime(new Date());
            userInfoService.updateUserInfoByUserID(userInfo);
            //第二步：将想要保存到数据存入session中

            apiResult.setResult(200);
            apiResult.setMsg("登录成功！");
            apiResult.setResultData(userInfo);
            System.out.println(userInfo);
            return apiResult;
        }

    }


    @RequestMapping("androidAuthLogin")
    @ResponseBody
    public ApiResult padAuthLogin(String username, String password) {
        UserInfo userInfo = userInfoService.login(username, MD5Util.MD5(password));
        if (userInfo != null) {
            userInfo.setLatestLoginTime(new Date());
            userInfoService.updateUserInfoByUserID(userInfo);
        }
        boolean f = false;
        f = userInfo == null ? false : true;
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(f);
        apiResult.setMsg(f ? "登录成功" : "账号或密码错误");
        apiResult.setResult(f ? 1 : 0);
        return apiResult;
    }

    /**
     * @功能说明：跳转到修改密码
     * @开发时间：2022-01-13
     * @开发人员：张涛
     */
    @RequestMapping("turnUpdatePassword")
    public String turnUpdatePassword(@RequestParam(required = false) String userID) {
        System.out.println("userID=" + userID);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(userID);
        System.out.println("userID2=" + userInfoByUserID);
        MemberInfo memberInfoByMemberID = memberInfoService.findMemberInfoByMemberID(userInfoByUserID.getMemberID());
        userInfoByUserID.setMemberName(memberInfoByMemberID.getMemberName());
        request.getSession().setAttribute("userInfo", userInfoByUserID);
        return "user/updatePassWord";
    }

    /**
     * @功能说明：修改密码
     * @开发时间：2022-01-13
     * @开发人员：张涛
     */
    @RequestMapping("updatePassWord")
    public String updatePassWord(@RequestParam(required = false) String loginPassWord, String userID) {
        logger.info("密码>>" + loginPassWord + ">>用户>>" + userID);
        UserInfo userInfoByUserID = userInfoService.findUserInfoByUserID(userID);
        if (userInfoByUserID.getLockStatus() == allConfiguration.getLockToActivate()) {
            userInfoByUserID.setLockStatus(allConfiguration.getLockNormal());
            userInfoByUserID.setLatestLoginTime(new Date());
        }
        userInfoByUserID.setLoginPassWord(MD5Util.MD5(loginPassWord));
        userInfoByUserID.setUpdateTime(new Date());
        userInfoService.updateUserInfoByUserID(userInfoByUserID);
        UserInfo userInfoByLoginName = userInfoService.findUserInfoByLoginName(userInfoByUserID.getLoginName());
        request.getSession().setAttribute("loginUser", userInfoByLoginName);
        return "index";
    }

    @RequestMapping("androidGetSystemConfig")
    @ResponseBody
    public ApiResult getSystemConfig() {
        SystemConfig config = systemConfigService.getSystemConfig();
        ApiResult apiResult = new ApiResult();
        apiResult.setResultData(config);
        apiResult.setMsg("系统参数");
        apiResult.setResult(1);
        return apiResult;
    }

    private void saveUserInfoToPage(HttpServletRequest request, UserInfo userInfo) {
        if (userInfo != null) {
            System.out.println(" ======userInfo = " + userInfo.toString());
        }
        request.getSession().setAttribute("userInfo", userInfo);
    }

    private void saveSystemConfigToPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        System.out.println("systemConfig=" + systemConfig.toString());
        session.setAttribute("sysConfig", systemConfig);
    }

    @RequestMapping("toIndex")
    public String toIndex(@RequestParam(required = false) String userName) {
        System.out.println("toIndex=" + userName);
        UserInfo userInfoByLoginName = userInfoService.findUserInfoByLoginName(userName);
        String permissionStr = userInfoByLoginName.getPermission();
        StringBuilder stringBuilder = new StringBuilder(permissionStr);
        char[] permissionArr = permissionStr.toCharArray();
        List<MenuInfo> menuInfos = menuInfoService.getlistMenuInfo();
        int size = menuInfos.size();//菜单数量
        int length = stringBuilder.length();//用户权限长度
        for (int i = length; i < size; i++) {
            stringBuilder.append("0");
        }
        System.out.println("str=="+stringBuilder.length());
        userInfoByLoginName.setPermission(stringBuilder.toString());
        userInfoService.updateUserInfoByUserID(userInfoByLoginName);
//        int totalCount = 0 ;
//        totalCount = menuInfos.size();
//        for (MenuInfo menuInfo : menuInfos) {
//            List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(menuInfo.getMenuID());
//            menuInfo.setSubmenu(menuInfoListBySuperMenuID);
//            totalCount = totalCount+menuInfoListBySuperMenuID.size();
//        }
        List<MenuInfo> showMenuInfos = new ArrayList<MenuInfo>();
        for (int i = 0; i < permissionArr.length; i++) {
            String pStr = String.valueOf(permissionArr[i]);
            if (pStr.equals("1")) {
                showMenuInfos.add(menuInfos.get(i));
            }
        }
        Iterator<MenuInfo> iterator = showMenuInfos.iterator();
        while (iterator.hasNext()){
            MenuInfo next = iterator.next();
            if (next.getMenuILevel().equals(allConfiguration.getMenuILevel1())){
                List<MenuInfo> menuInfoListBySuperMenuID = menuInfoService.findMenuInfoListBySuperMenuID(next.getMenuID());
                Iterator<MenuInfo> iterator1 = menuInfoListBySuperMenuID.iterator();
                while (iterator1.hasNext()){
                    MenuInfo next1 = iterator1.next();//二级菜单
                    String menuID = next1.getMenuID();
                    int i = Integer.parseInt(String.valueOf(permissionArr[Integer.parseInt(menuID)-1]));
                    if (i==0){
                        iterator1.remove();
                    }
                }
                next.setSubmenu(menuInfoListBySuperMenuID);
            }else {
                iterator.remove();
            }
        }
        request.getSession().setAttribute("userInfo", userInfoByLoginName);
        request.getSession().setAttribute("menuInfos", showMenuInfos);
        return "index";
    }

}
