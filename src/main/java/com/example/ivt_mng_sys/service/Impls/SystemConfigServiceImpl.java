package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.dao.SystemConfigDao;
import com.example.ivt_mng_sys.dao.UserInfoDao;
import com.example.ivt_mng_sys.entity.SystemConfig;
import com.example.ivt_mng_sys.service.SystemConfigService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    @Resource
    private SystemConfigDao systemConfigDao;

    public SystemConfig getSystemConfig() {
        return systemConfigDao.getSystemConfig();
    }

    @Override
    public boolean updateSystemConfig(SystemConfig systemConfig) {
        int i = systemConfigDao.updateSystemConfig(systemConfig);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }
}
