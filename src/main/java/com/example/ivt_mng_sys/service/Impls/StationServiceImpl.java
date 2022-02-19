package com.example.ivt_mng_sys.service.Impls;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.StationInfoDao;
import com.example.ivt_mng_sys.entity.StationInfo;
import com.example.ivt_mng_sys.service.StationInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StationServiceImpl implements StationInfoService {
    @Resource
    private StationInfoDao stationInfoDao;
    @Override
    public List<StationInfo> listStationInfoByStationStatus(int stationStatus,String deptID) {
        return stationInfoDao.listStationInfoByStationStatus(stationStatus,deptID);
    }

    @Override
    public StationInfo findStationInfoByStationID(String stationID) {
        return stationInfoDao.findStationInfoByStationID(stationID);
    }

    @Override
    public int stationInfoCount(int stationStatus) {
        return stationInfoDao.stationInfoCount(stationStatus);
    }

    @Override
    public List<StationInfo> listStationInfoByPage(PageModel pageModel) {
        return stationInfoDao.listStationInfoByPage(pageModel);
    }

    @Override
    public List<StationInfo> listStationNameByStationName(String stationName,String deptID) {
        return stationInfoDao.listStationNameByStationName(stationName,deptID);
    }

    @Override
    public int updateStation(StationInfo stationInfo) {
        return stationInfoDao.updateStation(stationInfo);
    }

    @Override
    public List<StationInfo> listStationInfo() {
        return stationInfoDao.listStationInfo();
    }

    @Override
    public int addStationInfo(StationInfo stationInfo) {
        return stationInfoDao.addStationInfo(stationInfo);
    }
}
