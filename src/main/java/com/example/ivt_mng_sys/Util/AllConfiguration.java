package com.example.ivt_mng_sys.Util;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component
@PropertySource("classpath:configurationFile.yml")
@ConfigurationProperties("configuration")
@Data
public class AllConfiguration {
    //账户状态
    private int lockNormal;//账户正常
    private int lockException;//账户异常
    private int lockToActivate;//待激活
    private int lockDelete;//账户删除
    private String password;//初始化密码
    //用户状态
    private int statusOFF;//用户状态开启
    private int statusNO;//用户状态冻结
    private int statusDelete;//用户删除
    //部门状态
    private int deptStatusOFF;//开启
    private int deptStatusNO;//关闭
    //岗位状态
    private int stationStatusOFF;//开启
    private int stationStatusON;//关闭
    //出库入库损耗
    private String storage;//存储
    private String outStorage;//出库
    private String loss;//损耗
    //交易类型
    private String stockDetailTypeOut;//出库
    private String stockDetailTypeStorage;//入库
    private String stockDetailTypeLoss;//损耗
    private String stockDetailReturns;//退货
    //明细状态
    private String stockDetailTypeStatusPrestore;//预存
    private String stockDetailTypeStatusEffect;//生效
    //打印状态
    private int printStatusOK;//打印状态1：已打印
    private int printStatusNO;//打印状态2:未打印
    //菜单级别
    private String menuILevel1;//一级菜单
    private String menuILevel2;//二级菜单
    private String menuILevel3;//三级菜单
    private String menuLockOF;//菜单生效
    private String menuLockNO;//菜单失效
}
