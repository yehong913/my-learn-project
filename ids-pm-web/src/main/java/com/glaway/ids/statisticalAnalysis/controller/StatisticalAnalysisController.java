package com.glaway.ids.statisticalAnalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.ExcelVo;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignGroupService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.PluginConstants;
import com.glaway.ids.common.service.PluginValidateServiceI;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.planGeneral.tabCombinationTemplate.vo.CombinationTemplateVo;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjWarnVo;
import com.glaway.ids.statisticalAnalysis.service.StatisticalAnalysisFeignServiceI;
import com.glaway.ids.statisticalAnalysis.vo.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 统计分析
 * @author: sunmeng
 * @ClassName: StatisticalAnalysisController
 * @Date: 2019/8/16-16:05
 * @since
 */
@Controller
@RequestMapping("/statisticalAnalysisController")
public class StatisticalAnalysisController extends BaseController {

    @Autowired
    private PluginValidateServiceI pluginValidateService;

    @Autowired
    private StatisticalAnalysisFeignServiceI statisticalAnalysisFeignService;

    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;

    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    @Autowired
    private FeignGroupService groupService;

    @Autowired
    private FeignDepartService departService;

    /**
     * 接口
     */
    @Value(value="${spring.application.name}")
    private String appKey;


    /**
     * 项目看板页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectBoard")
    public ModelAndView goProjectBoard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(
                "com/glaway/ids/project/statisticalAnalysis/projectBoardMore");
        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        List<ProjectBoardVo> voList = new ArrayList<ProjectBoardVo>();

        String pageNumber = request.getParameter("pageNumber");
        if (StringUtils.isNotEmpty(pageNumber)) {
            mv.addObject("pageNumberForPb", pageNumber);
        }
        else {
            pageNumber = "1";
            mv.addObject("pageNumberForPb", pageNumber);
        }
        mv.addObject("pageSizeForPb", "8");
        int totalSize = 0;
        List<ProjectBoardVo> totalList = new ArrayList<>();
        if (isPmo) {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(null,
                    Integer.parseInt(pageNumber), 8, true);
            /*totalList = statisticalAnalysisFeignService.getProjectBoardVoList(null,
                    Integer.parseInt(pageNumber), 8, false);*/
            totalSize = statisticalAnalysisFeignService.getProjectBoardVoListSize(null,
                    Integer.parseInt(pageNumber), 8, false);
        }
        else {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(userId,
                    Integer.parseInt(pageNumber), 8, true);
            /*totalList = statisticalAnalysisFeignService.getProjectBoardVoList(userId,
                    Integer.parseInt(pageNumber), 8, false);*/
            totalSize = statisticalAnalysisFeignService.getProjectBoardVoListSize(userId,
                    Integer.parseInt(pageNumber), 8, false);
        }
        mv.addObject("pageTotalForPb", totalSize);
        request.setAttribute("voList", voList);
        boolean riskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);
        request.setAttribute("riskPluginValid", riskPluginValid);
        return mv;
    }

    /**
     * 项目看板首页
     *
     * @return
     */
    @RequestMapping(params = "goProjectBoardPortlet")
    public ModelAndView goProjectBoardPortlet(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(
                "com/glaway/ids/project/statisticalAnalysis/projectBoardForPortlet");
        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        List<ProjectBoardVo> voList = new ArrayList<ProjectBoardVo>();
        if (isPmo) {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(null, 1, 2, true);
        }
        else {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(userId, 1, 2, true);
        }
        request.setAttribute("voList", voList);
        boolean riskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);
        request.setAttribute("riskPluginValid", riskPluginValid);
        return mv;
    }

    /**
     * 项目看板页面跳转after
     *
     * @return
     */
    @RequestMapping(params = "goProjectBoardAfter")
    public ModelAndView goProjectBoardAfter(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(
                "com/glaway/ids/project/statisticalAnalysis/projectBoardMore-after");
        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        List<ProjectBoardVo> voList = new ArrayList<ProjectBoardVo>();

        String pageNumber = request.getParameter("pageNumber");
        if (StringUtils.isNotEmpty(pageNumber)) {
            mv.addObject("pageNumberForPb", pageNumber);
        }
        else {
            pageNumber = "1";
            mv.addObject("pageNumberForPb", pageNumber);
        }
        mv.addObject("pageSizeForPb", "8");
        if (isPmo) {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(null,
                    Integer.parseInt(pageNumber), 8, true);
        }
        else {
            voList = statisticalAnalysisFeignService.getProjectBoardVoList(userId,
                    Integer.parseInt(pageNumber), 8, true);
        }
        /*List<ProjectBoardVo> totalList = statisticalAnalysisFeignService.getProjectBoardVoList(userId,
                Integer.parseInt(pageNumber), 8, false);*/
        int totalSize = statisticalAnalysisFeignService.getProjectBoardVoListSize(userId,
                Integer.parseInt(pageNumber), 8, false);
        mv.addObject("pageTotalForPb", totalSize);
        request.setAttribute("voList", voList);
        boolean riskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);
        request.setAttribute("riskPluginValid", riskPluginValid);
        return mv;
    }

    /**
     * 项目分析页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysis")
    public ModelAndView goProjectAnalysis(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis");
    }

    /**
     * 项目分析页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectBoardPic")
    public ModelAndView goProjectBoardPicExp(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectBoard-pic");
    }

    /**
     * 项目分析tab页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysisTab")
    public ModelAndView goProjectAnalysisTab(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis-tab");
    }

    /**
     * 项目分析时刻图跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysisPic")
    public ModelAndView goProjectAnalysisPic(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        List<ProjectAnalysisVo> voList = statisticalAnalysisFeignService.getMilestoneVoList(projectId);
        request.setAttribute("voList", voList);
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis-pic");
    }

    /**
     * 项目分析计划达成率图跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysisRate")
    public ModelAndView goProjectAnalysisRate(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis-rate");
    }

    /**
     * 项目分析计已延期任务跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysisDelay")
    public ModelAndView goProjectAnalysisDelay(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis-delay");
    }

    /**
     * 项目分析计月度达成率
     *
     * @return
     */
    @RequestMapping(params = "goProjectAnalysisMonth")
    public ModelAndView goProjectAnalysisMonth(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String curYear = sdf.format(new Date());
        request.setAttribute("projectId", projectId);
        request.setAttribute("curYear", curYear);
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/projectAnalysis-month");
    }

    /**
     * 人员负载分析页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goLaborAnalysis")
    public ModelAndView goLaborAnalysis(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/laborLoadAnalysis");
    }

    /**
     * 计划变更分析页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goPlanChangeAnalysis")
    public ModelAndView goPlanChangeAnalysis(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/project/statisticalAnalysis/planChangeAnalysis");
    }

    /**
     * 查询项目分析计划达成率
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchRateDatagrid")
    public void searchRateDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        List<CompleteRateVo> rateVoList = new ArrayList<CompleteRateVo>();
        FeignJson wbsFj = statisticalAnalysisFeignService.getWBSCompleteRateVo(projectId, "'WBS计划'",
                null, null);
        CompleteRateVo wbsVo = new CompleteRateVo();
        ModelMapperUtil.dtoToVo(wbsFj.getObj(),wbsVo);
        FeignJson taskFj = statisticalAnalysisFeignService.getCompleteRateVo(projectId,
                "'任务计划','流程计划'", null, null);
        CompleteRateVo taskVo = new CompleteRateVo();
        ModelMapperUtil.dtoToVo(taskFj.getObj(),taskVo);
        rateVoList.add(wbsVo);
        rateVoList.add(taskVo);
        request.setAttribute("rateVoList", rateVoList);
        String datagridStr = "{\"rows\":" + JSON.toJSONString(rateVoList) + ",\"total\":" + 2
                + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 查询项目分析计月度达成率
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchMonthDatagrid")
    public void searchMonthDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        String year = request.getParameter("year");
        if (year == null || "".equals(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            year = sdf.format(new Date());
        }
        List<MonthRateVo> monthVoList = new ArrayList<MonthRateVo>();
        FeignJson fj = statisticalAnalysisFeignService.getMonthRateVoList(projectId, year);
        if (fj.isSuccess()) {
            //ModelMapperUtil.dtoToVo(fj.getObj(),monthVoList);
            monthVoList = new ObjectMapper().convertValue(fj.getObj(),new com.fasterxml.jackson.core.type.TypeReference<List<MonthRateVo>>(){});
        }
        request.setAttribute("monthVoList", monthVoList);
        String datagridStr = "{\"rows\":" + JSON.toJSONString(monthVoList) + ",\"total\":" + 12
                + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 查询项目分析计已延期任务
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchDelayDatagrid")
    public void searchDelayDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        List<DelayTaskVo> delayVoList = new ArrayList<DelayTaskVo>();
        List<DelayTaskVo> forCount = statisticalAnalysisFeignService.getDelayTaskVoList(projectId,
                Integer.parseInt(page), Integer.parseInt(rows), false);
        int total = forCount.size();
        delayVoList = statisticalAnalysisFeignService.getDelayTaskVoList(projectId,
                Integer.parseInt(page), Integer.parseInt(rows), true);
        request.setAttribute("delayVoList", delayVoList);
        String datagridStr = "{\"rows\":" + JSON.toJSONString(delayVoList) + ",\"total\":" + total
                + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 月度达成率年份下拉
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "yearCombo")
    public void yearCombo(HttpServletRequest request, HttpServletResponse response) {
        FeignJson fj = statisticalAnalysisFeignService.getYearCombobox();
        String jonStr = "";
        if (fj.isSuccess()) {
            jonStr = fj.getObj() == null ? "" : fj.getObj().toString();
        }
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目类型下拉
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "projTypeCombo")
    public void projTypeCombo(HttpServletRequest request, HttpServletResponse response) {
        String epsTreeStr = epsConfigService.getEpsTreeNodes();
        List<TreeNode> forderTree = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsTreeStr),new TypeReference<List<TreeNode>>(){});
        String json = com.alibaba.fastjson.JSONArray.toJSONString(forderTree);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 项目下拉框
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "projCombo")
    @ResponseBody
    public void projCombo(HttpServletRequest request, HttpServletResponse response) {
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        FeignJson j = statisticalAnalysisFeignService.getProjectCombobox(orgId);
        String jonStr = "";
        if (j.isSuccess()) {
            jonStr = j.getObj() == null ? "" : j.getObj().toString();
        }
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计划负责人部门下拉
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "planOwnerDepartCombo")
    public void planOwnerDepartCombo(HttpServletRequest request, HttpServletResponse response) {
        List<TSDepartDto> list = departService.getAllDepart();
        List<TreeNode> forderTree = new ArrayList<TreeNode>();
        for (TSDepartDto d : list) {// 获取所有跟节点
            TreeNode treeNode = transfer(d);
            forderTree.add(treeNode);
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(forderTree);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 人员负载分析排列顺序下拉
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "orderTypeCombo")
    public void orderTypeCombo(HttpServletRequest request, HttpServletResponse response) {
        String[] strArr = {"按计划总数排列", "按延期未完成排列", "按正常未完成排列"}; // id分别为1，2，3
        JSONArray jList = new JSONArray();
        for (int i = 1; i <= 3; i++ ) {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", strArr[i - 1]);
            jList.add(obj);
        }
        String jonStr = jList.toString();
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换树节点
     *
     * @param d
     * @return
     * @see
     */
    private TreeNode transfer(TSDepartDto d) {
        TreeNode node = new TreeNode();
        node.setId(d.getId());
        if (d.getTSPDepart() == null) {
            node.setPid("ROOT");
        }
        else {
            node.setPid(d.getTSPDepart().getId());
        }
        node.setName(d.getDepartname());
        node.setTitle(d.getDepartname());
        node.setOpen(true);
        return node;
    }

    /**
     * Description: <br>
     * 1、下载模板<br>
     *
     * @see
     */
    @RequestMapping(params = "doExport")
    public void doDownTemplate(HttpServletRequest req, HttpServletResponse response)
            throws FdException, Exception {
        String projectId = req.getParameter("projectId");
        String projectName = req.getParameter("projectName");
        String year = req.getParameter("year");
        if (StringUtil.isNotEmpty(projectId)) {
            projectId = URLDecoder.decode(projectId, "UTF-8");
        }
        if (StringUtil.isNotEmpty(projectName)) {
            projectName = URLDecoder.decode(projectName, "UTF-8");
        }
        if (StringUtil.isNotEmpty(year)) {
            year = URLDecoder.decode(year, "UTF-8");
        }
        if ("1".equals(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            year = sdf.format(new Date());
        }

        fileDownLoad(response, projectId, projectName, year);

    }

    /**
     * 下载项目excel
     */
    @SuppressWarnings("unchecked")
    private void fileDownLoad(HttpServletResponse response, String projectId, String projectName,
                              String year) {
        List<ExcelVo> excelVoList = new ArrayList<ExcelVo>();

        // 项目进度
        List<ProjectAnalysisVo> milestoneList = statisticalAnalysisFeignService.getMilestoneVoList(projectId);
        List<String> milestoneColumns = Arrays.asList(new String[] {"pname:计划名称", "rate:进度",
                "status:状态", "aname:负责人", "starttime:开始时间", "endtime:结束时间", "worktime:工期"});
        ExcelVo milestoneExcelVo = new ExcelVo();
        milestoneExcelVo.setTitle("项目进度");
        milestoneExcelVo.setColumns(milestoneColumns);
        milestoneExcelVo.setDataList(milestoneList);
        excelVoList.add(milestoneExcelVo);

        // 计划达成率
        List<CompleteRateVo> rateList = new ArrayList<CompleteRateVo>();

        FeignJson wbsFj = statisticalAnalysisFeignService.getWBSCompleteRateVo(projectId, "'WBS计划'",
                null, null);
        CompleteRateVo wbsVo = new CompleteRateVo();
        ModelMapperUtil.dtoToVo(wbsFj.getObj(),wbsVo);
        FeignJson taskFj = statisticalAnalysisFeignService.getCompleteRateVo(projectId,
                "'任务计划','流程计划'", null, null);
        CompleteRateVo taskVo = new CompleteRateVo();
        ModelMapperUtil.dtoToVo(taskFj.getObj(),taskVo);
        rateList.add(wbsVo);
        rateList.add(taskVo);
        List<String> completeRateColumns = Arrays.asList(new String[] {"type:统计方式", "rate:达成率",
                "total:总计划", "complete:已完成", "uncomplete:正常未完成", "delay:延期未完成"});
        ExcelVo completeRateExcelVo = new ExcelVo();
        completeRateExcelVo.setTitle("计划达成率");
        completeRateExcelVo.setColumns(completeRateColumns);
        completeRateExcelVo.setDataList(rateList);
        excelVoList.add(completeRateExcelVo);

        // 已延期任务
        List<DelayTaskVo> delayTaskList = statisticalAnalysisFeignService.getDelayTaskVoList(projectId,
                0, 0, false);
/*        List<String> delayTaskColumns = Arrays.asList(new String[] {"pname:计划名称", "rate:进度",
            "status:状态", "level:计划等级", "oname:负责人", "stime:开始时间", "etime:结束时间", "risk:风险",
            "aname:下达人", "atime:下达时间", "type:计划类别"});*/
        List<String> delayTaskColumns = Arrays.asList(new String[] {"pname:计划名称", "rate:进度",
                "status:状态", "level:计划等级", "oname:负责人", "stime:开始时间", "etime:结束时间",
                "aname:发布人", "atime:发布时间", "type:计划类别"});
        ExcelVo delayTaskExcelVo = new ExcelVo();
        delayTaskExcelVo.setTitle("已延期任务");
        delayTaskExcelVo.setColumns(delayTaskColumns);
        delayTaskExcelVo.setDataList(delayTaskList);
        excelVoList.add(delayTaskExcelVo);

        // 月度达成率
        List<MonthRateVo> monthList = new ArrayList<MonthRateVo>();
        FeignJson fj = statisticalAnalysisFeignService.getMonthRateVoList(projectId, year);
        if (fj.isSuccess()) {
            ModelMapperUtil.dtoToVo(fj.getObj(),monthList);
        }
        List<String> monthColumns = Arrays.asList(new String[] {"month:月份", "wbstotal:底层WBS计划数",
                "wbscomplete:底层WBS完成数", "wbsrate:底层WBS达成率", "tasktotal:底层任务计划数",
                "taskcomplete:底层任务完成数", "taskrate:底层任务达成率"});
        ExcelVo monthExcelVo = new ExcelVo();
        monthExcelVo.setTitle(year + "年计划月度达成率");
        monthExcelVo.setColumns(monthColumns);
        monthExcelVo.setDataList(monthList);
        excelVoList.add(monthExcelVo);

        HSSFWorkbook workbook = POIExcelUtil.getInstance().exportExcel(true, excelVoList,
                "yyyy-MM-dd");
        String excelName = POIExcelUtil.createExcelName("true", projectName + "-项目分析", null);
        POIExcelUtil.responseReportWithName(response, workbook, excelName);
    }

    /**
     * 判断是否是项目团队成员
     *
     * @author zhousuxia
     * @version 2019年5月21日
     * @see StatisticalAnalysisController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkIsTeamUser")
    @ResponseBody
    public AjaxJson checkIsTeamUser(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        try{
            String projectId = request.getParameter("projectId");
            if (!CommonUtil.isEmpty(projectId)) {
                // 判断用户为项目团队成员后，才能加“最近访问”
                List<TSUserDto> userlist = projRoleService.getUserInProject(projectId);
                if (!CommonUtil.isEmpty(UserUtil.getInstance().getUser().getId())) {
                    for (TSUserDto tsUser : userlist) {
                        if (UserUtil.getInstance().getUser().getId().equals(tsUser.getId())) {
                            j.setSuccess(true);
                            break;
                        }
                    }
                }
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    /**
     * 获取计划预警报表数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "getProjectBoardReportData")
    public void getProjectBoardReportData(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {

            String projectId = request.getParameter("projectId");
            String planLevel = request.getParameter("planLevel");
            List<ProjectBoardReportDataVo> projectBoardList = statisticalAnalysisFeignService.getProjectBoardReportData(projectId,planLevel);

            String existNames = "";
            //个人积分排名
            List<Map<String, Object>> result= new ArrayList<Map<String,Object>>();
            if(!CommonUtil.isEmpty(projectBoardList)) {
                for(ProjectBoardReportDataVo vo : projectBoardList) {
                    if(CommonUtil.isEmpty(existNames)){
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("name",vo.getName());
                        map.put("action", vo.getAction());
                        map.put("number"+vo.getOrder(),vo.getNumber());
                        result.add(map);
                        existNames = vo.getName();
                    }else{
                        if(existNames.contains(vo.getName())){
                            for(Map<String, Object> res : result){
                                if(res.get("name").equals(vo.getName())){
                                    res.put("number"+vo.getOrder(),vo.getNumber());
                                    break;
                                }
                            }
                        }else{
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("name",vo.getName());
                            map.put("action", vo.getAction());
                            map.put("number"+vo.getOrder(),vo.getNumber());
                            result.add(map);
                            existNames = existNames+","+vo.getName();
                        }
                    }

                }
            }
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");

            writer = response.getWriter();
            writer.println(getJson(result, Long.parseLong(result.size()+ "")));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally{
            try {
                writer.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * 将结果集转化为列表json格式
     *
     * @param result
     *            结果集
     * @param size
     *            总大小
     * @return 处理好的json格式
     */
    @SuppressWarnings("rawtypes")
    public static String getJson(List<Map<String, Object>> result, Long size) {
        com.alibaba.fastjson.JSONObject main = new com.alibaba.fastjson.JSONObject();
        com.alibaba.fastjson.JSONArray rows = new com.alibaba.fastjson.JSONArray();
        main.put("total", size);
        for (Map m : result) {
            com.alibaba.fastjson.JSONObject item = new com.alibaba.fastjson.JSONObject();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String key = (String)it.next();
                String value = String.valueOf(m.get(key));
                key = key.toLowerCase();
                if (key.contains("time") || key.contains("date")) {
                    value = datatimeFormat(value);
                }
                item.put(key, value);
            }
            rows.add(item);
        }
        main.put("rows", rows);
        return main.toString();
    }

    /**
     * 将毫秒数去掉
     *
     * @param datetime
     * @return
     */
    public static String datatimeFormat(String datetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat dateFormatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(datetime);
            return dateFormatTo.format(d);
        }
        catch (Exception e) {
            return datetime;
        }
    }

    /**
     * 人员负载分析列表数据加载
     * @param request
     * @param response
     */
    @RequestMapping(params = "searchlaborLoadList")
    public void searchlaborLoadList(HttpServletRequest request, HttpServletResponse response) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String departId = request.getParameter("departId");
        String projectId = request.getParameter("projectId");
        String projectType = request.getParameter("projectType");
        String owner = request.getParameter("owner");
        String seq = request.getParameter("seq");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("rows",rows);
        params.put("page",page);
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("departId",departId);
        params.put("projectId",projectId);
        params.put("projectType",projectType);
        params.put("owner",owner);
        params.put("seq",seq);
        FeignJson j = statisticalAnalysisFeignService.searchlaborLoadList(params);
        String datagridStr = "";
        if (j.isSuccess()) {
            datagridStr = j.getObj() == null ? "" : j.getObj().toString();
        }
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 风险定性分析报表数据获取
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getLaborLoadListCharts")
    @ResponseBody
    public FeignJson getLaborLoadListCharts(HttpServletRequest request, HttpServletResponse response) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String departId = request.getParameter("departId");
        String projectId = request.getParameter("projectId");
        String projectType = request.getParameter("projectType");
        String owner = request.getParameter("owner");
        String seq = request.getParameter("seq");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("departId",departId);
        params.put("projectId",projectId);
        params.put("projectType",projectType);
        params.put("owner",owner);
        params.put("seq",seq);
        FeignJson j = statisticalAnalysisFeignService.getLaborLoadListCharts(params);
        return j;
    }


    /**
     * 获取计划分析的查询数据：
     *
     * @author wqb
     * @version 2019年12月4日 09:37:33
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getPlanChangeHighchartsInfo")
    @ResponseBody
    public AjaxJson getPlanChangeHighchartsInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String projectId = request.getParameter("projectId");
            String changeDateS = request.getParameter("changeDateS");
            String changeDateE = request.getParameter("changeDateE");
            String startDateS = request.getParameter("startDateS");
            String startDateE = request.getParameter("startDateE");
            String endDateS = request.getParameter("endDateS");
            String endDateE = request.getParameter("endDateE");
            String projectType = request.getParameter("projectType");
            String projectManager = request.getParameter("projectManager");
            String projectOwner = request.getParameter("projectOwner");

            String condition = "";
            String conditionForManager = "";

            if(!CommonUtil.isEmpty(changeDateS)&&!CommonUtil.isEmpty(changeDateE)){
                condition += " and (to_date(to_char(t.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + changeDateS + "', 'yyyy-mm-dd') and "
                        + " to_date(to_char(t.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + changeDateE + "', 'yyyy-mm-dd'))";
            }
            if(!CommonUtil.isEmpty(startDateS)&&!CommonUtil.isEmpty(startDateE)){
                condition += " and (to_date(to_char(t.planstarttime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + startDateS + "', 'yyyy-mm-dd') and "
                        + " to_date(to_char(t.planstarttime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + startDateE + "', 'yyyy-mm-dd'))";
            }
            if(!CommonUtil.isEmpty(endDateS)&&!CommonUtil.isEmpty(endDateE)){
                condition += " and (to_date(to_char(t.planendtime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + endDateS + "', 'yyyy-mm-dd') and "
                        + " to_date(to_char(t.planendtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + endDateE + "', 'yyyy-mm-dd'))";
            }
            if(!CommonUtil.isEmpty(projectType)){
                condition += " and p.eps in (" + projectType + ")";
            }
            if(!CommonUtil.isEmpty(projectId)){
                condition += " and p.id = '" + projectId + "'";
            }
            if(!CommonUtil.isEmpty(projectManager)){
                conditionForManager += " and u.realname||u.username like '%" + projectManager + "%'";
            }
            if(!CommonUtil.isEmpty(projectOwner)){
                condition += " and uu.realname||uu.username like '%" + projectOwner + "%'";
            }

            FeignJson feignJson = statisticalAnalysisFeignService.getPlanChangeHighchartsInfo(condition,conditionForManager,"");
            if(feignJson.isSuccess()) {
                j.setObj(feignJson.getObj());
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }

    /**
     * 查询Datagride数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "conditionSearch")
    @ResponseBody
    public void conditionSearch(HttpServletRequest request, HttpServletResponse response) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        if(CommonUtil.isEmpty(rows)){
            rows = 10;
        }
        if(CommonUtil.isEmpty(page)){
            page = 1;
        }
        String projectId = request.getParameter("projectId");
        String changeDateS = request.getParameter("changeDateS");
        String changeDateE = request.getParameter("changeDateE");
        String startDateS = request.getParameter("startDateS");
        String startDateE = request.getParameter("startDateE");
        String endDateS = request.getParameter("endDateS");
        String endDateE = request.getParameter("endDateE");
        String projectType = request.getParameter("projectType");
        String projectManager = request.getParameter("projectManager");
        String projectOwner = request.getParameter("projectOwner");

        String condition = "";
        String conditionForManager = "";

        if(!CommonUtil.isEmpty(changeDateS)&&!CommonUtil.isEmpty(changeDateE)){
            condition += " and (to_date(to_char(t.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + changeDateS + "', 'yyyy-mm-dd') and "
                    + " to_date(to_char(t.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + changeDateE + "', 'yyyy-mm-dd'))";
        }
        if(!CommonUtil.isEmpty(startDateS)&&!CommonUtil.isEmpty(startDateE)){
            condition += " and (to_date(to_char(t.planstarttime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + startDateS + "', 'yyyy-mm-dd') and "
                    + " to_date(to_char(t.planstarttime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + startDateE + "', 'yyyy-mm-dd'))";
        }
        if(!CommonUtil.isEmpty(endDateS)&&!CommonUtil.isEmpty(endDateE)){
            condition += " and (to_date(to_char(t.planendtime,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + endDateS + "', 'yyyy-mm-dd') and "
                    + " to_date(to_char(t.planendtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + endDateE + "', 'yyyy-mm-dd'))";
        }
        if(!CommonUtil.isEmpty(projectType)){
            condition += " and p.eps in (" + projectType + ")";
        }
        if(!CommonUtil.isEmpty(projectId)){
            condition += " and p.id = '" + projectId + "'";
        }
        if(!CommonUtil.isEmpty(projectManager)){
            conditionForManager += " and u.realname||u.username like '%" + projectManager + "%'";
        }
        if(!CommonUtil.isEmpty(projectOwner)){
            condition += " and uu.assignername like '%" + projectOwner + "%'";
        }
        List<planChangeAnalysisVo> list = statisticalAnalysisFeignService.conditionSearch( condition,conditionForManager,page,rows);
        int total = 0;
        if(list.size()>0){
            total = list.get(0).getAllNumber();
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + total + "}";

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(datagridStr);
            pw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
