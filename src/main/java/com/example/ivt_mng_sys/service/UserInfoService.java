package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoService {
    public boolean addUserInfo(UserInfo userInfo);
    public UserInfo login(String loginName,String loginPassWord);
    /**
     * @接口说明：根据lock查询数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public List<UserInfo> listUserByLock(int lock);

    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    @Select("select * from user_info order by addTime")
    List<UserInfo> listUserInfo();

    /**
     * @sql说明：查询全部数据数
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public int getUserInfoCount(int lockStatus);
    /**
     * @sql说明：分页查询数据
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public List<UserInfo> listUserInfoByPage(int lockStatus,PageModel pageModel);
    /**
     * @sql说明：根据编号查询
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public UserInfo findUserInfoByUserID(String userID);

    /**
     * @sql说明：修改信息
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    public int updateUserInfoByUserID(UserInfo userInfo);
    /**
     * @sql说明：根据登录名查询用户ID
     * @开发时间：2021-12-24
     * @开发人员：张涛
     * */
    UserInfo findUserInfoByLoginName(String loginName);
    /**
     * @sql说明：根据用户编号查询
     * @开发时间：2021-12-24
     * @开发人员：张涛
     * */
    UserInfo findUserInfoByMemberID(String memberID);





}
