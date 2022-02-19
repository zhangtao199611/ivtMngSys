package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.MemberInfo;

import java.util.List;

public interface MemberInfoService {
    /**
     * @接口说明：查询全部
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public List<MemberInfo> listMenuInfo();
    /**
     * @接口说明：总数
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public int getMemberInfoCount(int delete);
    /**
     * @接口说明：分页查询
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public List<MemberInfo> listMemberInfoByPage(int status,PageModel pageModel);
    /**
     * @接口说明：添加员工
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    int addMemberInfo(MemberInfo memberInfo);
    /**
     * @接口说明：查询一条数据
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    MemberInfo findMemberInfoByMemberID(String memberID);
    /**
     * @接口说明：修改信息
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    int updateMemberInfoByMemberID(MemberInfo memberInfo);
    /**
     * @接口说明：查询工号或手机号
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    List<MemberInfo> listMemberInfoByJobIDOrPhoneNumber(String jobID,String phoneNumber);

    /**
     * @接口说明：根据部门编号查询员工
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    List<MemberInfo> listMemberInfoByDeptID(String deptID);
    /**
     * @sql说明：根据工号查询
     * @开发时间：2021-12-29
     * @开发人员：张涛
     */
    MemberInfo findMemberInfoByJobID(String jobID);




}
