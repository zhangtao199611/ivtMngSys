package com.example.ivt_mng_sys.controller.UserName;
import com.example.ivt_mng_sys.dao.AuthorityInfoDao;
import com.example.ivt_mng_sys.entity.AuthorityInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


public class AuthorityInfoController {
    @Resource
    AuthorityInfoDao authorityInfoDao;


    @RequestMapping("/getSearchItemResultList")
    @ResponseBody
    public AuthorityInfo getSearchItemResultList(String id){
        AuthorityInfo authorityInfo = authorityInfoDao.findAuthorityInfoById(id);
        System.out.println("id="+id);
        return authorityInfo;
    }


}
