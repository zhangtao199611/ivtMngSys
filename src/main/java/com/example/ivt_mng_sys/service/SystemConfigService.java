package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.entity.SystemConfig;
import org.springframework.stereotype.Service;

@Service
public interface SystemConfigService {
    public SystemConfig getSystemConfig();
    public boolean updateSystemConfig(SystemConfig systemConfig);

}
