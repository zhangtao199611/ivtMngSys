package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.MemberInfoDao;
import com.example.ivt_mng_sys.dao.StationInfoDao;
import com.example.ivt_mng_sys.dao.UserInfoDao;
import com.example.ivt_mng_sys.entity.MemberInfo;
import com.example.ivt_mng_sys.entity.UserInfo;
import com.example.ivt_mng_sys.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private MemberInfoDao memberInfoDao;



    @Override
    public boolean addUserInfo(UserInfo userInfo) {
        List<MemberInfo> memberInfos = memberInfoDao.listMenuInfo();
        userInfo.setPermission("0");
        String permission = userInfo.getPermission();
        StringBuilder stringBuilder = new StringBuilder(permission);
        int size = memberInfos.size();//菜单数量
        int length = permission.length();//用户权限长度
        for (int i = length; i < size; i++) {
            stringBuilder.append("0");
        }
        userInfo.setPermission(stringBuilder.toString());
        int result = userInfoDao.addUser(userInfo);
         return result>0;
    }

    @Override
    public UserInfo login(String loginName, String loginPassWord) {
        UserInfo userInfo = userInfoDao.findUserByUserNameAndId(loginName,loginPassWord);
        return userInfo;
    }

    @Override
    public List<UserInfo> listUserByLock(int lock) {
        return userInfoDao.listUserByLock(lock);
    }

    @Override
    public List<UserInfo> listUserInfo() {
        return userInfoDao.listUserInfo();
    }

    @Override
    public int getUserInfoCount(int lockStatus) {
        return userInfoDao.getUserInfoCount(lockStatus);
    }

    @Override
    public List<UserInfo> listUserInfoByPage(int lockStatus,PageModel pageModel) {
        return userInfoDao.listUserInfoByPage(lockStatus,pageModel);
    }

    @Override
    public UserInfo findUserInfoByUserID(String userID) {
        return userInfoDao.findUserInfoByUserID(userID);
    }

    @Override
    public int updateUserInfoByUserID(UserInfo userInfo) {
        return userInfoDao.updateUserInfoByUserID(userInfo);
    }

    @Override
    public UserInfo findUserInfoByLoginName(String loginName) {
        return userInfoDao.findUserInfoByLoginName(loginName);
    }

    @Override
    public UserInfo findUserInfoByMemberID(String memberID) {
        return userInfoDao.findUserInfoByMemberID(memberID);
    }

}
