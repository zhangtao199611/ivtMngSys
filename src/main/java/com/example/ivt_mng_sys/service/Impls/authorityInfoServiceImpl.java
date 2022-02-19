package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.dao.AuthorityInfoDao;
import com.example.ivt_mng_sys.entity.AuthorityInfo;
import com.example.ivt_mng_sys.service.authorityInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class authorityInfoServiceImpl implements authorityInfoService {

    @Autowired
    AuthorityInfoDao authorityInfoDao;

    @Override
    public AuthorityInfo findAuthorityInfoById(String id) {
        return authorityInfoDao.findAuthorityInfoById(id);
    }
}
