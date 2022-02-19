package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.dao.MenuInfoDao;
import com.example.ivt_mng_sys.entity.MenuInfo;
import com.example.ivt_mng_sys.service.MenuInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuInfoServiceImpl implements MenuInfoService {
    @Resource
    private MenuInfoDao menuInfoDao;
    @Override
    public MenuInfo findMenuInfoByMenuID(String menuID) {
        return menuInfoDao.findMenuInfoByMenuID(menuID);
    }

    @Override
    public List<MenuInfo> getlistMenuInfo() {
        return menuInfoDao.getlistMenuInfo();
    }

    @Override
    public List<MenuInfo> listMenuInfo(String menuILevel) {
        return menuInfoDao.listMenuInfo(menuILevel);
    }

    @Override
    public List<MenuInfo> findMenuInfoListBySuperMenuID(String superMenuID) {
        return menuInfoDao.findMenuInfoListBySuperMenuID(superMenuID);
    }

    @Override
    public int addMenuInfo(MenuInfo menuInfo) {
        return menuInfoDao.addMenuInfo(menuInfo);
    }

    @Override
    public int updateMenuInfo(MenuInfo menuInfo) {
        return menuInfoDao.updateMenuInfo(menuInfo);
    }
}
