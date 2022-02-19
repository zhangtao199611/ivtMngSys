package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.dao.UnitInfoDao;
import com.example.ivt_mng_sys.entity.UnitInfo;
import com.example.ivt_mng_sys.service.UnitInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UnitInfoServiceImpl implements UnitInfoService {
    @Resource
    private UnitInfoDao unitInfoDao;
    @Override
    public List<UnitInfo> listUnitInfo() {
        return unitInfoDao.listUnitInfo();
    }

    @Override
    public UnitInfo findUnitInfoByUnitID(String unitID) {
        return unitInfoDao.findUnitInfoByUnitID(unitID);
    }

    @Override
    public int updateUntitName(UnitInfo unitInfo) {
        return unitInfoDao.updateUntitName(unitInfo);
    }
}
