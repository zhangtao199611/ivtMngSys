package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.entity.AuthorityInfo;
import org.springframework.stereotype.Service;

@Service
public interface authorityInfoService {
    public AuthorityInfo findAuthorityInfoById(String id);
}
