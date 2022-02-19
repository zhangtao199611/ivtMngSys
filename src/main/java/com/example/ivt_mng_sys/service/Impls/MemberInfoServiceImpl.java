package com.example.ivt_mng_sys.service.Impls;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.MemberInfoDao;
import com.example.ivt_mng_sys.dao.StationInfoDao;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.StationInfo;
import com.example.ivt_mng_sys.service.MemberInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
@Service
public class MemberInfoServiceImpl implements MemberInfoService {
    @Resource
    private MemberInfoDao memberInfoDao;

    @Resource
    private StationInfoDao stationInfoDao;

    @Override
    public List<MemberInfo> listMenuInfo() {
        List<MemberInfo> menuInfos = memberInfoDao.listMenuInfo();
        StringBuffer stringBuffer = new StringBuffer();
        for (MemberInfo memberInfo : menuInfos) {
            String stationIDs = memberInfo.getStationIDs();
            String[] split = stationIDs.split(",");
            for (String s : split) {
                StationInfo stationInfoByStationID = stationInfoDao.findStationInfoByStationID(s);
                 s = stationInfoByStationID.getStationName();
            }
            for (String s : split) {
                stringBuffer.append(s);
            }
            memberInfo.setStationIDs(stringBuffer.toString());
        }
        return menuInfos;
    }

    @Override
    public int getMemberInfoCount(int delete) {
        return memberInfoDao.getMemberInfoCount(delete);
    }

    @Override
    public List<MemberInfo> listMemberInfoByPage(int status,PageModel pageModel) {
        return memberInfoDao.listMemberInfoByPage(status,pageModel);
    }

    @Override
    public int addMemberInfo(MemberInfo memberInfo) {
        return memberInfoDao.addMemberInfo(memberInfo);
    }

    @Override
    public MemberInfo findMemberInfoByMemberID(String memberID) {
        return memberInfoDao.findMemberInfoByMemberID(memberID);
    }

    @Override
    public int updateMemberInfoByMemberID(MemberInfo memberInfo) {
        return memberInfoDao.updateMemberInfoByMemberID(memberInfo);
    }

    @Override
    public List<MemberInfo> listMemberInfoByJobIDOrPhoneNumber(String jobID, String phoneNumber) {
        return memberInfoDao.listMemberInfoByJobIDOrPhoneNumber(jobID,phoneNumber);
    }

    @Override
    public List<MemberInfo> listMemberInfoByDeptID(String deptID) {
        return memberInfoDao.listMemberInfoByDeptID(deptID);
    }

    @Override
    public MemberInfo findMemberInfoByJobID(String jobID) {
        return memberInfoDao.findMemberInfoByJobID(jobID);
    }
}
