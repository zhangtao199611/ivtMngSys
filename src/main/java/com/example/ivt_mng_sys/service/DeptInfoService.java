package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.DeptInfo;

import java.util.List;

public interface DeptInfoService {
    /**
     * @接口说明：所有岗位查询
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public List<DeptInfo> listDeptInfo(int deptStatus);
    /**
     * @接口说明：所有岗位查询
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    DeptInfo findDeptInfoByDeptID(String deptID);

    /**
     * @接口说明：所有岗位查询
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    int departmentListCount(int deptStatus);

    /**
     * @接口说明：分页查询所有部门
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    List<DeptInfo> listDeptInfoByPage(PageModel pageModel);

    /**
     * @sql说明：ajax查询重复数据
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    DeptInfo findDeptInfoByDeptName(String deptName);

    /**
     * @sql说明：查询所有部门编号
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    List<DeptInfo> listDeptIDByDeptID();

    /**
     * @sql说明：添加部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    int addDeptInfo(DeptInfo deptInfo);

    /**
     * @sql说明：修改部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    int updateDeptInfoByDeptID(DeptInfo deptInfo);

    /**
     * @sql说明：根据单位编号查询部门
     * @开发时间：2021-1-13
     * @开发人员：张涛
     * */
    List<DeptInfo> listDeptInfoByUnitID(String unitID);

    public List<DeptInfo> listDeptInfoBySianFlag();




}
