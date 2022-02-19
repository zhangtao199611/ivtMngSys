package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.entity.AuthorityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface AuthorityInfoDao {
    @Select("select * from authority_info where authorityID = #{id}")
     public AuthorityInfo findAuthorityInfoById(String id);
}
