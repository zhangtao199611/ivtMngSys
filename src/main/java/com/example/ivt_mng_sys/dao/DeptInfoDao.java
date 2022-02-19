package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.DeptInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeptInfoDao {
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from dept_info where deptStatus=#{deptStatus} order by established")
    public List<DeptInfo> listDeptInfo(int deptStatus);
    /**
     * @sql说明：根据编号查询
     * @开发时间：2021-12-14
     * @开发人员：张涛
     * */
    @Select("select * from dept_info where deptID=#{deptID}")
    DeptInfo findDeptInfoByDeptID(String deptID);

    /**
     * @sql说明：查询部门数量
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Select("select count(*) from dept_info where deptStatus=#{deptStatus}")
    int departmentListCount(int deptStatus);

    /**
     * @sql说明：分页查询部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Select("select * from dept_info order by established desc limit #{start},#{pageSize}")
    List<DeptInfo> listDeptInfoByPage(PageModel pageModel);

    /**
     * @sql说明：ajax查询重复数据
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Select("select * from dept_info where deptName=#{deptName}")
    DeptInfo findDeptInfoByDeptName(String deptName);

    /**
     * @sql说明：ajax查询重复数据
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Select("select deptID from dept_info")
    List<DeptInfo> listDeptIDByDeptID();

    /**
     * @sql说明：添加部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Insert("insert into dept_info (deptID,deptName,established,deptExplain,deptStatus) value(#{deptID},#{deptName},#{established},#{deptExplain},#{deptStatus})")
    int addDeptInfo(DeptInfo deptInfo);

    /**
     * @sql说明：修改部门信息
     * @开发时间：2021-12-21
     * @开发人员：张涛
     * */
    @Update("update dept_info set deptName=#{deptName},established=#{established},deptExplain=#{deptExplain},deptStatus=#{deptStatus} where deptID=#{deptID}")
    int updateDeptInfoByDeptID(DeptInfo deptInfo);
    /**
     * @sql说明：根据单位编号查询部门
     * @开发时间：2021-1-13
     * @开发人员：张涛
     * */
    @Select("select * from dept_info where unitID=#{unitID}")
    List<DeptInfo> listDeptInfoByUnitID(String unitID);
    /**
     * @sql说明：部门查询
     * @开发时间：2021-1-13
     * @开发人员：张涛
     * */
    @Select("select * from dept_info where signFlag = 1 and deptStatus=0")
    public List<DeptInfo> listDeptInfoBySianFlag();

}
