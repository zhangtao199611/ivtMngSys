package com.example.ivt_mng_sys.controller.dishesInfo;

import com.example.ivt_mng_sys.Util.ApiResult;
import com.example.ivt_mng_sys.Util.CommonUtil;
import com.example.ivt_mng_sys.Util.PageModel;
import com.example.ivt_mng_sys.dao.ClassInfoDao;
import com.example.ivt_mng_sys.dao.DisClassInfoDao;
import com.example.ivt_mng_sys.entity.ClassInfo;
import com.example.ivt_mng_sys.entity.DisClassInfo;
import com.example.ivt_mng_sys.service.ClassInfoService;
import com.example.ivt_mng_sys.service.DisClassInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("DisClassInfoController")
@Slf4j
public class DisClassInfoController {
//    @Resource
//    private DisClassInfoDao disClassInfoDao;
    @Resource
    private DisClassInfoService disClassInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommonUtil commonUtil;


    /**
     * @param : 无
     * @功能说明：前往类别列表
     * @开发时间：2021-01-27
     * @开发人员：张涛
     */
    @RequestMapping("/toClassList")
    public String goToClassList() {
        return "disClassInfo/disClassInfoList";
    }


    /**
     * @param : currentPage 当前页码，className 新添加的类别名
     * @return : 返回最新的列表数据和page信息
     * @功能说明：添加类别
     * @开发时间：2021-01-27
     * @开发人员：张涛
     */
    @RequestMapping("/addClassInfo")
    @ResponseBody
    public ApiResult addClassInfo(String className, Integer currentPage) {
        System.out.println("className = " + className);
        DisClassInfo classInfo = new DisClassInfo();
        classInfo.setClassName(className);
        String msg = null;
        boolean f = false;
        if (disClassInfoService.findClassInfoCountByName(className) > 0) {
            msg = "该类别已存在。";
        } else {
            f = disClassInfoService.addClassInfo(classInfo);
        }
        System.out.println(f ? "添加类别成功" : "添加类别失败");
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(f ? 1 : 0);
        result.setMsg(msg);
        return result;
    }


    /**
     * @param : currentPage 当前页码，id 删除的类别编号
     * @return : 返回最新的列表数据和page信息
     * @功能说明：删除类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/delClass")
    @ResponseBody
    public ApiResult delClass(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("id=" + id);
        System.out.println("currentPage=" + currentPage);
        boolean f = disClassInfoService.deleteClass(id);
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }


    /**
     * @param : currentPage 当前页码，id 展示的类别号（在安卓界面上展示供选择）
     * @return : 返回最新的列表数据和page信息
     * @功能说明：展示类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/showClass")
    @ResponseBody
    public ApiResult showClass(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("id=" + id);
        System.out.println("currentPage=" + currentPage);
        boolean f = hideOrShowClass(1, id);
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }


    /**
     * @param : currentPage 当前页码，id 展示的类别号（在安卓界面上展示供选择）
     * @return : 返回最新的列表数据和page信息
     * @功能说明：展示类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/hideClass")
    @ResponseBody
    public ApiResult hideClass(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("id=" + id);
        System.out.println("currentPage=" + currentPage);
        boolean f = hideOrShowClass(0, id);
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }


    /**
     * @param : id 展示的类别号（在安卓界面上展示供选择），展示的参数showStatus
     * @return : 返回最新的列表数据和page信息
     * @功能说明：设置类别是否展示
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    private boolean hideOrShowClass(int showStatus, String id) {
        DisClassInfo classInfo = disClassInfoService.findClassInfoById(id);
        classInfo.setShowStatus(showStatus);
        boolean f = disClassInfoService.updateClassInfo(classInfo);
        return f;
    }


    /**
     * @param : currentPage 当前页码，id 删除的类别编号
     * @return : 返回最新的列表数据和page信息
     * @功能说明：恢复类别
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/recoverClass")
    @ResponseBody
    public ApiResult recoverClass(@RequestParam(required = false) String id, Integer currentPage) {
        System.out.println("currentPage=" + currentPage);
        boolean f = disClassInfoService.recoverClass(id);
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>delClassList");
        result.setResult(delResult);
        result.setMsg(f ? "success" : "fail");
        return result;
    }

    /**
     * @param : currentPage 当前页码，className 新的类别名
     * @return : 返回最新的列表数据和page信息
     * @功能说明：修改类别名
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/updateClassInfo")
    @ResponseBody
    public ApiResult updateClassInfo(@RequestParam(required = false) String id, @RequestParam(required = false) String newName, Integer currentPage) {
        String msg = null;
        boolean f = false;
        if (disClassInfoService.findClassInfoCountByName(newName) > 0) {
            msg = "名称重复。";
        } else {
            f = updateClass(id, newName);
        }
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>updateClassInfo");
        result.setResult(delResult);
        result.setMsg(msg);
        return result;
    }

    /**
     * @param : id 修改的类别编号，className 新的大雷鸣
     * @return : 返回删除结果
     * @功能说明：修改类别名实际操作
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    private boolean updateClass(String id, String newName) {
        System.out.println("updateClass  id=" + id);
        DisClassInfo classInfo = disClassInfoService.findClassInfoById(id);
        classInfo.setClassName(newName);
        boolean f = disClassInfoService.updateClassInfo(classInfo);
        return f;
    }


    /**
     * @param : currentPage 当前页码，id 类别编号， upOrDown 1：上，0：下
     * @return : 返回最新的列表数据和page信息
     * @功能说明：修改类别名
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/sortClass")
    @ResponseBody
    public ApiResult sortClass(@RequestParam(required = false) String id, @RequestParam(required = false) int upOrDown, Integer currentPage) {
        String msg = null;
        boolean f = false;
        DisClassInfo classInfo = disClassInfoService.findClassInfoById(id);
        if (upOrDown == 1) {
            System.out.println("升序");
            f =  disClassInfoService.sortClassUp(classInfo);
        } else {
            System.out.println("降序");
            f =  disClassInfoService.sortClassDown(classInfo);
        }
        int delResult = f ? 1 : 0;
        ApiResult result = getClassListOfResult(currentPage, "ClassInfoController>>updateClassInfo");
        result.setResult(delResult);
        result.setMsg(msg);
        return result;
    }


    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：页面载入时，过来查询数据
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/getClassList")
    @ResponseBody
    public ApiResult getClassList(Integer currentPage) {
        return getClassListOfResult(currentPage, "ClassInfoController>>getClassList");
    }

    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：添加产品时，过来查询数据
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/getAllClassList")
    @ResponseBody
    public ApiResult getAllClassList() {
        List<DisClassInfo> classInfos = disClassInfoService.listClassInfo();
        System.out.println("类别数量=" + classInfos.size());
        ApiResult apiResult = new ApiResult();
        apiResult.setResult(1);
        apiResult.setResultData(classInfos);
        return apiResult;
    }


    /**
     * @param : currentPage 当前页码
     * @return : 返回最新的列表数据和page信息
     * @功能说明：添加产品时，过来查询数据
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    @RequestMapping("/getAllClassListForAndroid")
    @ResponseBody
    public ApiResult getAllClassListForAndroid() {
        List<DisClassInfo> classInfos = disClassInfoService.listClassInfoShowForAndroidPad();
        System.out.println("类别数量=" + classInfos.size());
        ApiResult apiResult = new ApiResult();
        apiResult.setResult(1);
        apiResult.setResultData(classInfos);
        return apiResult;
    }



    /**
     * @param : currentPage 当前页码 ,location 张涛工具类中的代码位置信息
     * @return : 返回修改后的数据
     * @功能说明：增删改查等操作后，封装后从这里走
     * @开发时间：2021-1-27
     * @开发人员：张涛
     */
    private ApiResult getClassListOfResult(Integer currentPage, String location) {
        PageModel pageModel = commonUtil.showPage(currentPage, disClassInfoService.getClassInfoCount(), location);
        ApiResult result = new ApiResult();
        List<DisClassInfo> classList = disClassInfoService.listClassInfoByPage(pageModel);
        result.setResultData(classList);
        result.setPageModel(pageModel);
        return result;
    }


}
