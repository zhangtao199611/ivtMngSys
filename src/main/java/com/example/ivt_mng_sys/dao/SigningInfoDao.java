package com.example.ivt_mng_sys.dao;

import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.entity.SigningInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface SigningInfoDao {
    /**
     * @功能说明：添加签单信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    @Insert("insert into signing_info (signingID,sigImg,adder,addDate,unitID,remarks,addTime)values(#{signingID},#{sigImg},#{adder},#{addDate},#{unitID},#{remarks},#{addTime})")
    public int addSigningInfo(SigningInfo signingInfo);
    /**
     * @功能说明：修改签单信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    @Update("update signing_info set sigImg=#{sigImg},adder=#{adder},addDate=#{addDate},unitID=#{unitID},remarks=#{remarks},addTime=#{addTime} where signingID=#{signingID}")
    int updateSigningInfo(SigningInfo signingInfo);
    /**
     * @功能说明：查询签单信息
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    @Select("select * from signing_info order by addTime desc limit #{start},#{pageSize}")
    List<SigningInfo> listSigningInfoPage(PageModel pageModel);
    /**
     * @功能说明：查询签单信息数量
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    @Select("select count(*) from signing_info")
    int countSigningInfo();
    /**
     * @功能说明：根据签单编号查询数据
     * @开发时间：2022-01-14
     * @开发人员：张涛
     * */
    @Select("select * from signing_info where signingID=#{signingID}")
    SigningInfo findSigningInfoBySigningID(String signingID);
}