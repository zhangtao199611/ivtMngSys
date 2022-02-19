package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SystemConfigDao {
    @Select("select * from sys_config")
    public SystemConfig getSystemConfig();

    @Update("update sys_config set sysLogo=#{sysLogo},sysVersion=#{sysVersion},unStandardAppTitle=#{unStandardAppTitle},standardAppTitle=#{standardAppTitle} where sysName=#{sysName}")
    public int updateSystemConfig(SystemConfig systemConfig);
}
