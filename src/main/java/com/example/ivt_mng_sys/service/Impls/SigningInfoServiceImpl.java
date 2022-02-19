package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.SigningInfoDao;
import com.example.ivt_mng_sys.entity.SigningInfo;
import com.example.ivt_mng_sys.service.SigningInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SigningInfoServiceImpl implements SigningInfoService {
    @Resource
    private SigningInfoDao signingInfoDao;
    @Override
    public int addSigningInfo(SigningInfo signingInfo) {
        return signingInfoDao.addSigningInfo(signingInfo);
    }

    @Override
    public boolean updateSigningInfo(SigningInfo signingInfo) {
        int i = signingInfoDao.updateSigningInfo(signingInfo);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    public List<SigningInfo> listSigningInfoPage(PageModel pageModel) {
        return signingInfoDao.listSigningInfoPage(pageModel);
    }

    @Override
    public int countSigningInfo() {
        return signingInfoDao.countSigningInfo();
    }

    @Override
    public SigningInfo findSigningInfoBySigningID(String signingID) {
        return signingInfoDao.findSigningInfoBySigningID(signingID);
    }
}
