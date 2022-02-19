package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.MemberInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MemberInfoDao {
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from member_info order by addTime")
    public List<MemberInfo> listMenuInfo();

    /**
     * @sql说明：查询全部数据数
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select count(*) from member_info where status!=#{delete}")
    public int getMemberInfoCount(int delete);

    /**
     * @sql说明：分页查询数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from member_info where status!=#{status} order by memberName='管理员' desc, addTime desc limit #{pageModel.start},#{pageModel.pageSize}")
    public List<MemberInfo> listMemberInfoByPage(int status,PageModel pageModel);

    /**
     * @sql说明：插入一条数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Insert("insert into member_info (memberID,userID,deptID,stationIDs,memberName,jobID,IDCard,status,phoneNumber,addTime,updateTime,adder) value(#{memberID},#{userID},#{deptID},#{stationIDs},#{memberName},#{jobID},#{IDCard},#{status},#{phoneNumber},#{addTime},#{updateTime},#{adder})")
    int addMemberInfo(MemberInfo memberInfo);
    /**
     * @sql说明：查询一条数据
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @Select("select * from member_info where memberID=#{memberID}")
    public MemberInfo findMemberInfoByMemberID(String memberID);
    /**
     * @sql说明：修改数据
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */

    @Update("update member_info set userID=#{userID},deptID=#{deptID},stationIDs=#{stationIDs},memberName=#{memberName},jobID=#{jobID},IDCard=#{IDCard},status=#{status},phoneNumber=#{phoneNumber},addTime=#{addTime},updateTime=#{updateTime},adder=#{adder} where memberID=#{memberID}")
    int updateMemberInfoByMemberID(MemberInfo memberInfo);

    /**
     * @sql说明：查询工号和手机号码
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @Select("select * from member_info where jobID=#{jobID} || phoneNumber=#{phoneNumber}")
    List<MemberInfo> listMemberInfoByJobIDOrPhoneNumber(String jobID,String phoneNumber);

    /**
     * @sql说明：根据部门编号查询员工
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @Select("select * from member_info where deptID=#{deptID}")
    List<MemberInfo> listMemberInfoByDeptID(String deptID);
    /**
     * @sql说明：根据工号查询用户
     * @开发时间：2021-12-29
     * @开发人员：张涛
     * */
    @Select("select * from member_info where jobID=#{jobID}")
    MemberInfo findMemberInfoByJobID(String jobID);
}
