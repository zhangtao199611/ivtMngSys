package com.example.ivt_mng_sys.controller.sysConfigInfo;

import com.example.ivt_mng_sys.Util.CommonUtil;
import com.example.ivt_mng_sys.entity.StockDetail;
import com.example.ivt_mng_sys.entity.SystemConfig;
import com.example.ivt_mng_sys.entity.UnitInfo;
import com.example.ivt_mng_sys.service.SystemConfigService;
import com.example.ivt_mng_sys.service.UnitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("SysConfigController")
@Slf4j
public class SysConfigController {
    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UnitInfoService unitInfoService;
    @Resource
    private CommonUtil commonUtil;

    @RequestMapping("findConfigService")
    public String findConfigService(){
        log.info("SysConfigController>>findConfigService");
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        List<UnitInfo> unitInfos = unitInfoService.listUnitInfo();
        UnitInfo unitInfo = unitInfos.get(0);
        systemConfig.setUntitName(unitInfo.getUntitName());//单位名称
        systemConfig.setUnitID(unitInfo.getUnitID());//单位编号
        request.getSession().setAttribute("systemConfig",systemConfig);
        return "findConfig/updateConfig";
    }

    @RequestMapping("updateConfigService")
    public String updateConfigService(@RequestParam("sysLogoFile")MultipartFile sysLogoFile){
        System.out.println("filename="+sysLogoFile.getOriginalFilename());
        String sysName = request.getParameter("sysName");
        String standardAppTitle = request.getParameter("standardAppTitle");
        String unStandardAppTitle = request.getParameter("unStandardAppTitle");
        String untitName = request.getParameter("untitName");
        String unitID = request.getParameter("unitID");
        log.info("系统名称>>"+sysName);
        log.info("标品app标题>>"+standardAppTitle);
        log.info("非标品app标题>>"+unStandardAppTitle);
        log.info("单位名称>>"+untitName);
        log.info("单位ID>>"+unitID);
        UnitInfo unitInfoByUnitID = unitInfoService.findUnitInfoByUnitID(unitID);
        unitInfoByUnitID.setUntitName(untitName);
        unitInfoService.updateUntitName(unitInfoByUnitID);
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        systemConfig.setSysName(sysName);//系统名称
        systemConfig.setUnStandardAppTitle(unStandardAppTitle);
        systemConfig.setStandardAppTitle(standardAppTitle);
        if (sysLogoFile != null) {
            String file1 = getClass().getResource("/static/img/logo/").getFile();
            System.out.println("static="+file1);
            String originalFilename = sysLogoFile.getOriginalFilename();
            File file = new File(file1 + originalFilename);
            OutputStream out = null;
            try {
                //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = sysLogoFile.getBytes();
                for (int i = 0; i < ss.length; i++) {
                    out.write(ss[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(file.toURI());
            System.out.println(file);
            System.out.println(file.getName());
            systemConfig.setSysLogo(file.getName());
            systemConfig.setSysLogoFile(file);
            addImg(systemConfig);
        }
        SystemConfig systemConfig1 = systemConfigService.getSystemConfig();
        log.info("SysConfigController>>updateConfigService");
        request.getSession().setAttribute("systemConfig",systemConfig1);
        request.getSession().setAttribute("Info","修改成功！");
        return "findConfig/updateConfig";
    }


    /**
     * @模块说明：添加图片功能
     * @创建时间：2021-01-16
     * @开发人员：张涛
     */
    private void addImg(SystemConfig systemConfig) {
        System.out.println("StockController.addImg()");
        System.out.println("PhotoImgFile=" + systemConfig.getSysLogoFile());
        System.out.println("上传图片::" + systemConfig.getSysLogoFile().length());
        if (systemConfig.getSysLogoFile().length() > 819200) {
            PrintWriter out = null;
            //ActionUtil.sendMessege(response, "图片超过了800kb");
        }
        InputStream is;
        try {
            is = new FileInputStream(systemConfig.getSysLogoFile());
            // 获得食材编号为图片命名，更新到数据库
//            System.out.println("明细编号：" + systemConfig.getStockDetailID());
            System.out.println("明细图片文件名：" + systemConfig.getSysLogo());
//            System.out.println("明细图片文件名2：" + systemConfig.getSysLogo().split("\\.")[1]);
//            systemConfig.getSysLogo("/static/img/logo/" + systemConfig.getStockDetailID() + "." + systemConfig.getPhotoImg().split("\\.")[1]);
            systemConfig.setSysLogo("/static/img/logo/" +systemConfig.getSysLogo());
            System.out.println("getSysLogo=" + systemConfig.getSysLogo());
            System.out.println(systemConfigService.updateSystemConfig(systemConfig) ? "修改图片成功" : "修改图片失败");
            String file1 = getClass().getResource("/").toString();
            System.out.println("static="+file1);
            File destFile = new File(file1, systemConfig.getSysLogo());
            //先刪除存在的图片，在写入
            if (destFile != null) {
                destFile.delete();
            }
            OutputStream os = new FileOutputStream(destFile);
            byte[] buffer = new byte[400];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
