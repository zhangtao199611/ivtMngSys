package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.DeptInfoDao;
import com.example.ivt_mng_sys.entity.DeptInfo;
import com.example.ivt_mng_sys.service.DeptInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    @Resource
    private DeptInfoDao deptInfoDao;
    @Override
    public List<DeptInfo> listDeptInfo(int deptStatus) {
        return deptInfoDao.listDeptInfo(deptStatus);
    }

    @Override
    public DeptInfo findDeptInfoByDeptID(String deptID) {
        return deptInfoDao.findDeptInfoByDeptID(deptID);
    }

    @Override
    public int departmentListCount(int deptStatus) {
        return deptInfoDao.departmentListCount(deptStatus);
    }

    @Override
    public List<DeptInfo> listDeptInfoByPage(PageModel pageModel) {
        return deptInfoDao.listDeptInfoByPage(pageModel);
    }

    @Override
    public DeptInfo findDeptInfoByDeptName(String deptName) {
        return deptInfoDao.findDeptInfoByDeptName(deptName);
    }

    @Override
    public List<DeptInfo> listDeptIDByDeptID() {
        return deptInfoDao.listDeptIDByDeptID();
    }

    @Override
    public int addDeptInfo(DeptInfo deptInfo) {
        return deptInfoDao.addDeptInfo(deptInfo);
    }

    @Override
    public int updateDeptInfoByDeptID(DeptInfo deptInfo) {
        return deptInfoDao.updateDeptInfoByDeptID(deptInfo);
    }

    @Override
    public List<DeptInfo> listDeptInfoByUnitID(String unitID) {
        return deptInfoDao.listDeptInfoByUnitID(unitID);
    }

    @Override
    public List<DeptInfo> listDeptInfoBySianFlag() {
        return deptInfoDao.listDeptInfoBySianFlag();
    }

}
