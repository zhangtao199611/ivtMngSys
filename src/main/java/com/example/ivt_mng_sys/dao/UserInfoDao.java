package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * userID
 * memberID
 * loginName
 * loginPassWord
 * stationID
 * position
 * addTime
 * updateTime
 * adder
 * updater
 * latestLoginTime
 * latestLoginDevID
 * lock
 */
@Mapper
public interface UserInfoDao {
    @Insert( "insert into user_info(userID,memberID,loginName,loginPassWord,addTime,adder,lockStatus,permission) values (#{userID},#{memberID},#{loginName},#{loginPassWord},#{addTime},#{adder},#{lockStatus},#{permission})" )
    public int addUser(UserInfo user);

    @Select("select * from user_info where loginName = #{loginName} and loginPassWord = #{loginPassWord}")
    public UserInfo findUserByUserNameAndId(String loginName,String loginPassWord);

    /**
     * @sql说明：根据lockStatus字段查询数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from user_info where lockStatus=#{lock}")
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
    @Select("select count(*) from user_info where lockStatus!=#{lockStatus}")
    public int getUserInfoCount(int lockStatus);

    /**
     * @sql说明：分页查询数据
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    @Select("select * from user_info where lockStatus!=#{lockStatus} order by loginName='admin' desc, addTime desc limit #{pageModel.start},#{pageModel.pageSize}")
    public List<UserInfo> listUserInfoByPage(int lockStatus,PageModel pageModel);
    /**
     * @sql说明：根据编号查询
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    @Select("select * from user_info where userID=#{userID}")
    public UserInfo findUserInfoByUserID(String userID);
    /**
     * @sql说明：修改账户信息
     * @开发时间：2021-12-16
     * @开发人员：张涛
     * */
    @Update("update user_info set memberID=#{memberID},loginName=#{loginName},loginPassWord=#{loginPassWord},addTime=#{addTime},updateTime=#{updateTime},adder=#{adder},updater=#{updater},latestLoginTime=#{latestLoginTime},latestLoginDevID=#{latestLoginDevID},lockStatus=#{lockStatus},permission=#{permission} where userID=#{userID}")
    public int updateUserInfoByUserID(UserInfo userInfo);
    /**
     * @sql说明：根据用户名查询账号
     * @开发时间：2021-12-24
     * @开发人员：张涛
     * */
    @Select("select * from user_info where loginName=#{loginName}")
    UserInfo findUserInfoByLoginName(String loginName);
    /**
     * @sql说明：根据用户编号查询
     * @开发时间：2021-12-24
     * @开发人员：张涛
     * */
    @Select("select * from user_info where memberID=#{memberID}")
    UserInfo findUserInfoByMemberID(String memberID);

}
