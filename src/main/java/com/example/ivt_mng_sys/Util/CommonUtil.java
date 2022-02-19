package com.example.ivt_mng_sys.Util;

import com.example.ivt_mng_sys.dao.MemberInfoDao;
import com.example.ivt_mng_sys.dao.StationInfoDao;
import com.example.ivt_mng_sys.entity.*;
import com.example.ivt_mng_sys.service.DeptInfoService;
import com.example.ivt_mng_sys.service.Impls.DeptInfoServiceImpl;
import com.example.ivt_mng_sys.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class CommonUtil {
    @Resource
    private HttpServletRequest request;
    @Resource
    private MemberInfoDao memberInfoDao;
    @Resource
    private StationInfoDao stationInfoDao;
    @Resource
    private UserInfoService userInfoService;
    Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * @功能说明：根据页码查询
     * @date：2021-12-13
     * @name:张涛
     */
    public PageModel showPage(int currentPage, int sum, String location) {
        logger.info("请求来源=" + location + ">>" + "总展示第" + currentPage + "页" + ">>总数=" + sum);
        PageModel page = new PageModel(10, currentPage, sum);
        return page;
    }

    /**
     * @功能说明：根据页码查询
     * @date：2021-12-13
     * @name:张涛
     */
    public PageModel showPagePrint(int currentPage, int sum, String location) {
        logger.info("请求来源=" + location + ">>" + "总展示第" + currentPage + "页" + ">>总数=" + sum);
        PageModel page = new PageModel(20, currentPage, sum);
        return page;
    }

    /**
     * @功能说明：清空Session中的值
     * @开发时间：2021-12-14
     * @开发人员：张涛
     */
    public void clearSession() {
        Enumeration em = request.getSession().getAttributeNames();
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        request.getSession().setAttribute("userInfo", userInfo);
    }

    /**
     * @功能说明：冒泡排序方法
     * @开发时间：2021-12-21
     * @开发人员：张涛
     */
    //设备状态初始化工具方法
    public int getInitializeStatus(List<DeptInfo> list) {
        /*
         * 初始化设备状态
         * */
        int initializeStatus = 0;//最大值
        try {
            int[] numbers = new int[list.size()];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(list.get(i).getDeptID());
            }
            //需进行length-1次冒泡
            for (int i = 0; i < numbers.length - 1; i++) {
                //System.out.println(numbers[i]);
                for (int j = 0; j < numbers.length - 1 - i; j++) {
                    if (numbers[j] > numbers[j + 1]) {
                        int temp = numbers[j];
                        numbers[j] = numbers[j + 1];
                        numbers[j + 1] = temp;
                    }
                }
            }
            initializeStatus = numbers[numbers.length - 1];
        } catch (Exception e) {
            log.info("部门表暂无数据，导致异常错误=" + e);
            initializeStatus = 00;
        }
        return initializeStatus;
    }


    /**
     * @功能说明：冒泡排序方法
     * @开发时间：2021-12-23
     * @开发人员：张涛
     */
    //设备状态初始化工具方法
    public int getInitializeStatus2(List<StationInfo> list) {
        /*
         * 初始化设备状态
         * */
        int initializeStatus = 0;//最大值
        try {
            int[] numbers = new int[list.size()];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(list.get(i).getStationID());
            }
            //需进行length-1次冒泡
            for (int i = 0; i < numbers.length - 1; i++) {
                //System.out.println(numbers[i]);
                for (int j = 0; j < numbers.length - 1 - i; j++) {
                    if (numbers[j] > numbers[j + 1]) {
                        int temp = numbers[j];
                        numbers[j] = numbers[j + 1];
                        numbers[j + 1] = temp;
                    }
                }
            }
            initializeStatus = numbers[numbers.length - 1];
        } catch (Exception e) {
            log.info("部门表暂无数据，导致异常错误=" + e);
            initializeStatus = 00;
        }
        return initializeStatus;
    }

    /**
     * @功能说明：冒泡排序方法
     * @开发时间：2021-12-21
     * @开发人员：张涛
     */
    //设备状态初始化工具方法
    public int getInitializeStatus3(List<StockDetail> list) {
        /*
         * 初始化设备状态
         * */
        int initializeStatus = 0;//最大值
        try {
            int[] numbers = new int[list.size()];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = list.get(i).getCurBatchNo();
            }
            //需进行length-1次冒泡
            for (int i = 0; i < numbers.length - 1; i++) {
                //System.out.println(numbers[i]);
                for (int j = 0; j < numbers.length - 1 - i; j++) {
                    if (numbers[j] > numbers[j + 1]) {
                        int temp = numbers[j];
                        numbers[j] = numbers[j + 1];
                        numbers[j + 1] = temp;
                    }
                }
            }
            initializeStatus = numbers[numbers.length - 1];
        } catch (Exception e) {
            log.info("部门表暂无数据，导致异常错误=" + e);
            initializeStatus = 00;
        }
        return initializeStatus;
    }

    /**
     * @功能说明：权限管理
     * @开发时间：2021-1-22
     * @开发人员：张涛
     */
    public void userPer(String userName){
        UserInfo userInfoByLoginName = userInfoService.findUserInfoByMemberID(userName);
        String permission = userInfoByLoginName.getPermission();
        char[] chars = permission.toCharArray();
        List<String> permissions = new ArrayList();
        for (int i = 0; i < chars.length; i++) {
            permissions.add(String.valueOf(chars[i]));
        }
        for (String s : permissions) {
            System.out.println("字符串>>"+s);
        }
        request.getSession().setAttribute("userName",userName);
        request.getSession().setAttribute("permissions",permissions);
    }

    /**
     * @功能说明：根据用户编号查询是否具有功能权限
     * @开发时间：2021-12-24
     * @开发人员：张涛
     */
    public boolean findUserIFByID(String verify, String userName) {
        return true;
//        log.info("userName=" + userName);
//        log.info("verify=" + verify);
//        MemberInfo memberInfoByMemberID = memberInfoDao.findMemberInfoByMemberID(userName);
//        System.out.println(memberInfoByMemberID);
//        List list = new ArrayList();
//        System.out.println("权限=" + memberInfoByMemberID.getStationIDs());
//        String[] stationIDs = memberInfoByMemberID.getStationIDs().split(",");
////        for (String stationID : stationIDs) {
////            if (stationInfoDao.findStationInfoByStationID(stationID).getAuthorityIDs().contains(verify) == true) {
////                list.add(verify);
////            }
////        }
//        if (list.size() > 0) {
//            return true;
//        } else {
//            request.getSession().setAttribute("responseStr", "对不起，您不满足权限！无法查看，请联系管理员！");
//            return false;
//        }
    }

}
