package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.StationInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StationInfoDao {
    /**
     * @sql说明：根据stationID字段查询数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from station_info where stationID=#{stationID}")
    public StationInfo findStationInfoByStationID(String stationID);
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-12
     * @开发人员：张涛
     * */
    @Select("select * from station_info where stationStatus=#{stationStatus} and deptID=#{deptID}")
    List<StationInfo> listStationInfoByStationStatus(int stationStatus,String deptID);
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    @Select("select count(*) from station_info")
    int stationInfoCount(int stationStatus);
    /**
     * @sql说明：查询分页数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    @Select("select * from station_info order by addTime desc limit #{start},#{pageSize}")
    List<StationInfo> listStationInfoByPage(PageModel pageModel);
    /**
     * @sql说明：ajxa查询是否有重复数据
     * @开发时间：2021-12-22
     * @开发人员：张涛
     * */
    @Select("select stationName from station_info where stationName=#{stationName} and deptID=#{deptID}")
    List<StationInfo> listStationNameByStationName(String stationName,String deptID);
    /**
     * @sql说明：修改岗位信息
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    @Update("update station_info set stationName=#{stationName},deptID=#{deptID},authorityIDs=#{authorityIDs},addTime=#{addTime},stationStatus=#{stationStatus} where stationID=#{stationID}")
    int updateStation(StationInfo stationInfo);
    /**
     * @sql说明：查询全部数据
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    @Select("select * from station_info")
    List<StationInfo> listStationInfo();
    /**
     * @sql说明：插入数据
     * @开发时间：2021-12-23
     * @开发人员：张涛
     * */
    @Insert("insert into station_info (stationID,stationName,deptID,authorityIDs,addTime,stationStatus) value(#{stationID},#{stationName},#{deptID},#{authorityIDs},#{addTime},#{stationStatus})")
    int addStationInfo(StationInfo stationInfo);
}
