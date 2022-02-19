package com.example.ivt_mng_sys.service;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StationInfo;

import java.util.List;

public interface StationInfoService {
    List<StationInfo> listStationInfoByStationStatus(int stationStatus,String deptID);
    /**
     * @接口说明：根据stationID查询数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    public StationInfo findStationInfoByStationID(String stationID);
    /**
     * @接口说明：查询全部数据数
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    int stationInfoCount(int stationStatus);
    /**
     * @接口说明：查询分页数据数
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    List<StationInfo> listStationInfoByPage(PageModel pageModel);
    /**
     * @接口说明：ajxa查询存在的数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    List<StationInfo> listStationNameByStationName(String stationName,String deptID);
    /**
     * @接口说明：修改岗位信息
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    int updateStation(StationInfo stationInfo);
    /**
     * @接口说明：查询全部数据
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    List<StationInfo> listStationInfo();
    /**
     * @接口说明：添加一条数据
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    int addStationInfo(StationInfo stationInfo);



}
