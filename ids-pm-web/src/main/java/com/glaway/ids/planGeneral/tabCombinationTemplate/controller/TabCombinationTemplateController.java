package com.glaway.ids.planGeneral.tabCombinationTemplate.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.ModelMapperUtil;
import com.glaway.foundation.common.util.RequestMapUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.PlanGeneralConstants;
import com.glaway.ids.config.constant.CommonConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.DataSourceObjectFeign;
import com.glaway.ids.planGeneral.plantabtemplate.feign.TabTemplateFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.dto.TabCombinationTemplateDto;
import com.glaway.ids.planGeneral.tabCombinationTemplate.feign.TabCombinationTemplateFeignServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.utils.TabTemplateUtil;
import com.glaway.ids.planGeneral.tabCombinationTemplate.vo.CombinationTemplateVo;
import com.glaway.ids.project.plan.dto.ApprovePlanInfoDto;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PreposePlanDto;
import com.glaway.ids.project.plan.dto.TemporaryPlanDto;
import com.glaway.ids.project.plan.service.FeignPlanChangeServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PreposePlanRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.FlowTaskParentVo;
import com.glaway.ids.util.CommonInitUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description: 页签组合模板Controller
 * @author: sunmeng
 * @ClassName: TabCombinationTemplateController
 * @Date: 2019/8/27-17:57
 * @since
 */
@Controller
@RequestMapping("/tabCombinationTemplateController")
public class TabCombinationTemplateController {

    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    //页签模版管理Service
    @Autowired
    private TabTemplateServiceI tabTemplateServiceImpl;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCbTemplateFeignService;

    @Autowired
    private TabTemplateFeign tabTemplateFeign;
    
    @Autowired
    private PlanRemoteFeignServiceI planService;
    
    //元数据属性Feign
    @Autowired
    private DataSourceObjectFeign dataSourceObjectFeign;
  
    @Autowired
    private FeignUserService userService;
    
    @Autowired
    private FeignDepartService deptService;
    
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;
    
    @Autowired
    private ActivityTypeManageFeign activityTypeManageEntityService;

    @Autowired
    private FeignPlanChangeServiceI feignPlanChangeServiceI;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCombinationTemplateFeignService;


    /**
     * 项目服务实现接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;
    
    @Value(value="${spring.application.name}")
    private String appKey;
    
    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;
    
    /**
     * 计划前置接口
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;
    
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;
    
    /**
     * 流程任务分解接口
     */
    @Autowired
    private RdFlowTaskFlowResolveRemoteFeignServiceI rdFlowTaskFlowResolveService;

    @RequestMapping(params = "goTabCombinationTemplate")
    public ModelAndView goTabCombinationTemplate(HttpServletRequest request) {
        Object operationCodes = request.getAttribute("operationCodes");
        boolean tabCbTemplateAddCode = false;
        boolean tabCbTemplateDeleteCode = false;
        boolean tabCbTemplateEnableCode = false;
        boolean tabCbTemplateDisableCode = false;
        boolean tabCbTemplateUpdateCode = false;
        boolean tabCbTemplateCopyCode = false;
        boolean tabCbTemplateViewCode = false;

        for (String operationCode : operationCodes.toString().split(",")) {
            if (operationCode.contains("tabCbTemplateAddCode")) {
                tabCbTemplateAddCode = true;
            }
            if (operationCode.contains("tabCbTemplateDeleteCode")) {
                tabCbTemplateDeleteCode = true;
            }
            if (operationCode.contains("tabCbTemplateEnableCode")) {
                tabCbTemplateEnableCode = true;
            }
            if (operationCode.contains("tabCbTemplateDisableCode")) {
                tabCbTemplateDisableCode = true;
            }
            if (operationCode.contains("tabCbTemplateUpdateCode")) {
                tabCbTemplateUpdateCode = true;
            }
            if (operationCode.contains("tabCbTemplateCopyCode")) {
                tabCbTemplateCopyCode = true;
            }
            if (operationCode.contains("tabCbTemplateViewCode")) {
                tabCbTemplateViewCode = true;
            }
        }
        request.setAttribute("tabCbTemplateAddCode", tabCbTemplateAddCode);
        request.setAttribute("tabCbTemplateDeleteCode", tabCbTemplateDeleteCode);
        request.setAttribute("tabCbTemplateEnableCode", tabCbTemplateEnableCode);
        request.setAttribute("tabCbTemplateDisableCode", tabCbTemplateDisableCode);
        request.setAttribute("tabCbTemplateUpdateCode", tabCbTemplateUpdateCode);
        request.setAttribute("tabCbTemplateCopyCode", tabCbTemplateCopyCode);
        request.setAttribute("tabCbTemplateViewCode", tabCbTemplateViewCode);
        return new ModelAndView("com/glaway/ids/planGeneral/tabCombinationTemplate/tabCombinationTemplatePage");

    }

    /**
     * 加载数据列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "searchDataGrid")
    @ResponseBody
    public void searchDataGrid(HttpServletRequest request, HttpServletResponse response) {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("condition",conditionList);
        FeignJson j = tabCbTemplateFeignService.queryEntity(params);
        String datagridStr = "";
        if (j.isSuccess()) {
            datagridStr = j.getObj() == null ? "" : j.getObj().toString();
        }
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 页签组合模板新增&修改页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "goAddCbTemplate")
    public ModelAndView goAddCbTemplate(HttpServletRequest request) {
        String tabCbTemplateId = request.getParameter("tabCbTemplateId");
        request.setAttribute("tabCbTemplateId", tabCbTemplateId);
        String type =  request.getParameter("type");
        request.setAttribute("type",type);
        //通过id获取页签组合模板信息
        if (StringUtils.isNotBlank(tabCbTemplateId)) {
            FeignJson fj = tabCbTemplateFeignService.findTabCbTempById(tabCbTemplateId);
            if (fj.isSuccess()) {
                TabCombinationTemplateDto template = new TabCombinationTemplateDto();
                ModelMapperUtil.dtoToVo(fj.getObj(),template);
                if (type.equals("copy")) {
                    template.setName(template.getName()+"副本");
                }
                request.setAttribute("template_", template);
            }
        }
        //显示权限列表
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "3");
        obj.put("name", "/");
        jsonList.add(obj);
        JSONObject obj1 = new JSONObject();
        obj1.put("id", "2");
        obj1.put("name", "启动");
        jsonList.add(obj1);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "0");
        obj2.put("name", "编制");
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "1");
        obj3.put("name", "启动&编制");
        jsonList.add(obj3);
        String jsonResult = JSON.toJSONString(jsonList);
        request.setAttribute("acessList",jsonResult);

        //未删除、启用的页签模板数据
        List<TabTemplateDto> tabTemplates = tabTemplateFeign.getAllTabTemplates("1", PlanGeneralConstants.TABCBTEMPLATE_QIYONG);
        for (TabTemplateDto dto : tabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr = JSON.toJSONString(tabTemplates);
        request.setAttribute("tabTemplates",jsonStr);
        //所有页签模板数据
        List<TabTemplateDto> alltabTemplates = tabTemplateFeign.getAllTabTemplates("", "");
        for (TabTemplateDto dto : alltabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr1 = JSON.toJSONString(alltabTemplates);
        request.setAttribute("alltabTemplates",jsonStr1);
        return new ModelAndView(
                "com/glaway/ids/planGeneral/tabCombinationTemplate/tabCombinationTeplateAdd");
    }

    /**
     * 模板类型下拉列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTemplateTypeCombox")
    @ResponseBody
    public void getTemplateTypeCombox(HttpServletRequest request, HttpServletResponse response) {
        //false查询包括禁用的活动类型
        List<ActivityTypeManageDto> activityTypeManages = activityTypeManageFeign.getAllActivityTypeManage(false);
        JSONArray jList = new JSONArray();
        String jonStr = "";
        if (activityTypeManages.size() > 0) {
            for (ActivityTypeManageDto dto:activityTypeManages) {
                JSONObject obj = new JSONObject();
                obj.put("id", dto.getId());
                obj.put("name", dto.getName());
                jList.add(obj);
            }
            jonStr = jList.toString();
        }
        TagUtil.ajaxResponse(response, jonStr);
    }

    /**
     * 新增，修改，复制模板类型下拉列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTemplateCombox")
    @ResponseBody
    public void getTemplateCombox(HttpServletRequest request, HttpServletResponse response) {
        //false查询未删除的活动类型
        List<ActivityTypeManageDto> activityTypeManages = activityTypeManageFeign.getAllActivityTypeManage(false);
        JSONArray jList = new JSONArray();
        String jonStr = "";
        if (activityTypeManages.size() > 0) {
            for (ActivityTypeManageDto dto:activityTypeManages) {
                JSONObject obj = new JSONObject();
                obj.put("id", dto.getId());
                obj.put("name", dto.getName());
                jList.add(obj);
            }
            jonStr = jList.toString();
        }
        TagUtil.ajaxResponse(response, jonStr);
    }

    /**
     * 页签组合模板生命周期下拉列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTabCbTemplateLifeStyle")
    @ResponseBody
    public void getTabCbTemplateLifeStyle(HttpServletRequest request, HttpServletResponse response) {
        String jonStr = "";
        FeignJson fj = tabCbTemplateFeignService.getLifeCycleStatusList();
        if (fj.isSuccess()) {
            jonStr = fj.getObj() == null ? "" : fj.getObj().toString();
        }
        TagUtil.ajaxResponse(response, jonStr);
    }

    /**
     * 判断活动类型下是否绑定组合页签模板
     * @param request
     * @return
     */
    @RequestMapping(params = "isActivityTypeManageUse")
    @ResponseBody
    public FeignJson isActivityTypeManageUse(HttpServletRequest request) {
        String id = request.getParameter("id");
        String templateId = request.getParameter("templateId");
        FeignJson j = tabCbTemplateFeignService.isActivityTypeManageUse(id,templateId);
        return j;
    }

    /**
     * 新增保存页签组合模板
     * @param tabCombinationTemplateDto
     * @param request
     * @return
     */
    @RequestMapping(params = "saveTabCbTemplateInfo")
    @ResponseBody
    public FeignJson saveTabCbTemplateInfo (TabCombinationTemplateDto tabCombinationTemplateDto,HttpServletRequest request) {
        String type = request.getParameter("type");
        String cbTemplateList = request.getParameter("cbTemplateList");

        List<Object> lists = (List<Object>) JSON.parseArray(cbTemplateList);
        List<CombinationTemplateVo> cbList = new ArrayList<>();
        if (!CommonUtil.isEmpty(lists)) {
            for (Object obj: lists) {
                Map<String,String> cbmap = (Map<String,String>)JSON.parse(String.valueOf(obj));
                CombinationTemplateVo combinationTemplateVo = new CombinationTemplateVo();
                combinationTemplateVo.setName(cbmap.get("name"));
                combinationTemplateVo.setTypeId(cbmap.get("tabName"));
                combinationTemplateVo.setTabType(cbmap.get("tabType"));
                combinationTemplateVo.setDisplayAccess(cbmap.get("displayAccess"));
                cbList.add(combinationTemplateVo);
            }
        }
        CommonInitUtil.initGLVDataForCreate(tabCombinationTemplateDto);
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("type",type);
        param.put("dto",tabCombinationTemplateDto);
        param.put("list",cbList);
        param.put("userId", ResourceUtil.getCurrentUser().getId());
        param.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
        FeignJson j = tabCbTemplateFeignService.saveTabCbTemplateInfo(param);
        return j;
    }

    /**
     * 复制页签组合模板
     * @param tabCombinationTemplateDto
     * @param request
     * @return
     */
    @RequestMapping(params = "copyTabCbTemplateInfo")
    @ResponseBody
    public FeignJson copyTabCbTemplateInfo (TabCombinationTemplateDto tabCombinationTemplateDto,HttpServletRequest request) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.general.copyCbtemplateSuccess");
        String type = request.getParameter("type");
        String cbTemplateList = request.getParameter("cbTemplateList");

        List<Object> lists = (List<Object>) JSON.parseArray(cbTemplateList);
        List<CombinationTemplateVo> cbList = new ArrayList<>();
        if (!CommonUtil.isEmpty(lists)) {
            for (Object obj: lists) {
                Map<String,String> cbmap = (Map<String,String>)JSON.parse(String.valueOf(obj));
                CombinationTemplateVo combinationTemplateVo = new CombinationTemplateVo();
                combinationTemplateVo.setName(cbmap.get("name"));
                combinationTemplateVo.setTypeId(cbmap.get("typeId"));
                combinationTemplateVo.setTabType(cbmap.get("tabType"));
                combinationTemplateVo.setDisplayAccess(cbmap.get("displayAccess"));
                cbList.add(combinationTemplateVo);
            }
        }
        CommonInitUtil.initGLVDataForCreate(tabCombinationTemplateDto);
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("type",type);
        param.put("dto",tabCombinationTemplateDto);
        param.put("list",cbList);
        param.put("userId", ResourceUtil.getCurrentUser().getId());
        param.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
        FeignJson j = tabCbTemplateFeignService.saveTabCbTemplateInfo(param);
        if (j.isSuccess()) {
            j.setMsg(message);
        }
        return j;
    }

    /**
     * 保存页签组合模板
     * @param tabCombinationTemplateDto
     * @param request
     * @return
     */
    @RequestMapping(params = "updateTabCbTemplateInfo")
    @ResponseBody
    public FeignJson updateTabCbTemplateInfo(TabCombinationTemplateDto tabCombinationTemplateDto,HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String cbTemplateList = request.getParameter("cbTemplateList");
        String method = request.getParameter("method");

        List<Object> lists = (List<Object>) JSON.parseArray(cbTemplateList);
        List<CombinationTemplateVo> cbList = new ArrayList<>();
        if (!CommonUtil.isEmpty(lists)) {
            for (Object obj: lists) {
                Map<String,String> cbmap = (Map<String,String>)JSON.parse(String.valueOf(obj));
                CombinationTemplateVo combinationTemplateVo = new CombinationTemplateVo();
                combinationTemplateVo.setName(cbmap.get("name"));
                combinationTemplateVo.setTypeId(cbmap.get("typeId"));
                combinationTemplateVo.setTabType(cbmap.get("tabType"));
                combinationTemplateVo.setDisplayAccess(cbmap.get("displayAccess"));
                cbList.add(combinationTemplateVo);
            }
        }
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("dto",tabCombinationTemplateDto);
        param.put("list",cbList);
        param.put("userId", ResourceUtil.getCurrentUser().getId());
        param.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
        param.put("method",method);
        j = tabCbTemplateFeignService.updateTabCbTemplateInfo(param);
        return j;
    }

    /**
     * 保存页签组合模板并重新提交流程
     * @param tabCombinationTemplateDto
     * @param request
     * @return
     */
    @RequestMapping(params = "backSaveAndSubmit")
    @ResponseBody
    public FeignJson backSaveAndSubmit(TabCombinationTemplateDto tabCombinationTemplateDto,HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String cbTemplateList = request.getParameter("cbTemplateList");
        String method = request.getParameter("method");

        List<Object> lists = (List<Object>) JSON.parseArray(cbTemplateList);
        List<CombinationTemplateVo> cbList = new ArrayList<>();
        if (!CommonUtil.isEmpty(lists)) {
            for (Object obj: lists) {
                Map<String,String> cbmap = (Map<String,String>)JSON.parse(String.valueOf(obj));
                CombinationTemplateVo combinationTemplateVo = new CombinationTemplateVo();
                combinationTemplateVo.setName(cbmap.get("name"));
                combinationTemplateVo.setTypeId(cbmap.get("typeId"));
                combinationTemplateVo.setTabType(cbmap.get("tabType"));
                combinationTemplateVo.setDisplayAccess(cbmap.get("displayAccess"));
                cbList.add(combinationTemplateVo);
            }
        }
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("dto",tabCombinationTemplateDto);
        param.put("list",cbList);
        param.put("userId", ResourceUtil.getCurrentUser().getId());
        param.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
        j = tabCbTemplateFeignService.updateTabCbTemplateInfo(param);
        return j;
    }

    /**
     * 页签组合模板修改时获取模板信息
     * @param tabCbTemplateId
     * @param request
     * @return
     */
    @RequestMapping(params = "getCombTemplateInfos")
    @ResponseBody
    public FeignJson getCombTemplateInfos(@RequestParam String tabCbTemplateId,HttpServletRequest request) {
        FeignJson j = tabCbTemplateFeignService.getCombTemplateInfos(tabCbTemplateId);
        return j;
    }

    /**
     * 页签组合模板删除
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public FeignJson doBatchDel(String ids, HttpServletRequest request) {
        FeignJson j = tabCbTemplateFeignService.doBatchDel(ids);
        return j;
    }

    /**
     * 模板启用禁用
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "doStatusChange")
    @ResponseBody
    public FeignJson doStatusChange(HttpServletRequest req, HttpServletResponse response) {
        FeignJson j = new FeignJson();
        String status = req.getParameter("status");
        String ids = req.getParameter("ids");
        String userId = ResourceUtil.getCurrentUser().getId();
        j = tabCbTemplateFeignService.doStatusChange(ids,status,userId);
        return j;
    }

    /**
     * 获取所有页签模板信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "getAllTabTemplate")
    @ResponseBody
    public void getAllTabTemplate(HttpServletRequest request, HttpServletResponse response) {

        List<TabTemplateDto> tabTemplates = tabTemplateFeign.getAllTabTemplates("1",PlanGeneralConstants.TABCBTEMPLATE_QIYONG);
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        if (tabTemplates.size() >0 ) {
            for (TabTemplateDto dto : tabTemplates) {
                JSONObject obj = new JSONObject();
                obj.put("id", dto.getId());
                obj.put("tabName", dto.getName());
                jsonList.add(obj);
            }
        }
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 版本页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goVersionHistory")
    public ModelAndView goVersionHistory(HttpServletRequest request) {
        String id = request.getParameter("id");
        FeignJson fj = tabCbTemplateFeignService.findTabCbTempById(id);
        TabCombinationTemplateDto template = new TabCombinationTemplateDto();
        if (fj.isSuccess()) {
            ModelMapperUtil.dtoToVo(fj.getObj(),template);
        }
        request.setAttribute("template", template);
        return new ModelAndView("com/glaway/ids/planGeneral/tabCombinationTemplate/tabCbTemplate-showHistory");
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    @RequestMapping(params = "getVersionHistory")
    @ResponseBody
    public void getVersionHistory(HttpServletRequest req, HttpServletResponse response) {
        String bizId = req.getParameter("bizId");
        FeignJson fj = tabCbTemplateFeignService.getVersionDatagridStr(bizId, Integer.valueOf(req.getParameter("rows")),
                Integer.valueOf(req.getParameter("page")));
        Map<String,Object> attributes = new HashMap<>();
        if (fj.isSuccess()) {
            attributes = fj.getAttributes();
            List<TabCombinationTemplateDto> templateDtos = (List<TabCombinationTemplateDto>) attributes.get("list");
            long count = Long.valueOf(attributes.get("count").toString());
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setVersion(
                    1.0).create();
            String json = gson.toJson(templateDtos);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
    }

    /**
     * 页签组合模板详情页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTabCbTemplateLayout")
    public ModelAndView goTabCbTemplateLayout(HttpServletRequest request) {
        String tabCbTemplateId = request.getParameter("id");
        request.setAttribute("viewHistory", request.getParameter("viewHistory"));
        request.setAttribute("tabCbTemplateId", tabCbTemplateId);
        //通过id获取页签组合模板信息
        if (StringUtils.isNotBlank(tabCbTemplateId)) {
            FeignJson fj = tabCbTemplateFeignService.findTabCbTempById(tabCbTemplateId);
            if (fj.isSuccess()) {
                TabCombinationTemplateDto template = new TabCombinationTemplateDto();
                ModelMapperUtil.dtoToVo(fj.getObj(),template);
                request.setAttribute("template_", template);
            }
        }

        //未删除、启用的页签模板数据
        List<TabTemplateDto> tabTemplates = tabTemplateFeign.getAllTabTemplates("1",PlanGeneralConstants.TABCBTEMPLATE_QIYONG);
        for (TabTemplateDto dto : tabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr = JSON.toJSONString(tabTemplates);
        request.setAttribute("tabTemplates",jsonStr);
        //所有页签模板数据
        List<TabTemplateDto> alltabTemplates = tabTemplateFeign.getAllTabTemplates("", "");
        for (TabTemplateDto dto : alltabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr1 = JSON.toJSONString(alltabTemplates);
        request.setAttribute("alltabTemplates",jsonStr1);
        return new ModelAndView("com/glaway/ids/planGeneral/tabCombinationTemplate/tabCombinationTemplateLayout");
    }

    /**
     * 提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goSubmitApprove")
    public ModelAndView goSubmitApprove(HttpServletRequest req) {
        /*//通过页签模板id获取页签模板信息
        TabTemplateDto tabTemplateDto = tabTemplateFeign.queryTabTemplate(req.getParameter("tabCbTemplateId"));
        String tmpId = t.getId();*/
        req.setAttribute("tmpId", req.getParameter("tabCbTemplateId"));
        req.setAttribute("supportFlag", "approve");
        return new ModelAndView("com/glaway/ids/planGeneral/tabCombinationTemplate/tabCbTemplate-submitApprove");
    }

    /**
     * 提交审批
     *
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSubmitApprove")
    @ResponseBody
    public FeignJson doSubmitApprove(HttpServletRequest req, HttpServletResponse response) {
        String tabCbTemplateId = req.getParameter("tabCbTemplateId");
        String leader = req.getParameter("leader");
        String deptLeader = req.getParameter("deptLeader");
        String userId = ResourceUtil.getCurrentUser().getId();
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        Map<String,String> map = new HashMap<String, String>();
        map.put("tabCbTemplateId",tabCbTemplateId);
        map.put("leader",leader);
        map.put("deptLeader",deptLeader);
        map.put("userId",userId);
        map.put("orgId",orgId);
        FeignJson j = tabCbTemplateFeignService.doSubmitApprove(map);
        return j;
    }

    @RequestMapping(params = "doBackVersion")
    @ResponseBody
    public FeignJson doBackVersion(HttpServletRequest request) {
        String id = request.getParameter("id");
        String bizId = request.getParameter("bizId");
        String bizVersion = request.getParameter("bizVersion");
        String type = request.getParameter("type");
        String userId = ResourceUtil.getCurrentUser().getId();
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        Map<String,String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("bizId", bizId);
        params.put("bizVersion", bizVersion);
        params.put("type", type);
        params.put("userId", userId);
        params.put("orgId", orgId);
        FeignJson j = tabCbTemplateFeignService.backVersion(params);
        return j;
    }

    /**
     * 页签组合模板驳回后重新提交
     * @param request
     * @return
     */
    @RequestMapping(params = "goTabCbTTemplateEdit")
    public ModelAndView goTabCbTTemplateEdit(HttpServletRequest request) {
        String tabCbTemplateId = request.getParameter("tabCbTemplateId");
        request.setAttribute("tabCbTemplateId", tabCbTemplateId);
        //通过id获取页签组合模板信息
        if (StringUtils.isNotBlank(tabCbTemplateId)) {
            FeignJson fj = tabCbTemplateFeignService.findTabCbTempById(tabCbTemplateId);
            if (fj.isSuccess()) {
                TabCombinationTemplateDto template = new TabCombinationTemplateDto();
                ModelMapperUtil.dtoToVo(fj.getObj(),template);
                request.setAttribute("template_", template);
            }
        }
        //显示权限列表
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "3");
        obj.put("name", "/");
        jsonList.add(obj);
        JSONObject obj1 = new JSONObject();
        obj1.put("id", "2");
        obj1.put("name", "启动");
        jsonList.add(obj1);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "0");
        obj2.put("name", "编制");
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "1");
        obj3.put("name", "启动&编制");
        jsonList.add(obj3);
        String jsonResult = JSON.toJSONString(jsonList);
        request.setAttribute("acessList",jsonResult);

        //未删除、启用的页签模板数据
        List<TabTemplateDto> tabTemplates = tabTemplateFeign.getAllTabTemplates("1",PlanGeneralConstants.TABCBTEMPLATE_QIYONG);
        for (TabTemplateDto dto : tabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr = JSON.toJSONString(tabTemplates);
        request.setAttribute("tabTemplates",jsonStr);
        //所有页签模板数据
        List<TabTemplateDto> alltabTemplates = tabTemplateFeign.getAllTabTemplates("", "");
        for (TabTemplateDto dto : alltabTemplates) {
            dto.setTabType(TabTemplateUtil.tabTemplateType.get(dto.getTabType()));
        }
        String jsonStr1 = JSON.toJSONString(alltabTemplates);
        request.setAttribute("alltabTemplates",jsonStr1);
        return new ModelAndView(
                "com/glaway/ids/planGeneral/tabCombinationTemplate/tabCombinationTeplateEdit");
    }

    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */
    @RequestMapping(params = "goTabView")
    public ModelAndView goTabView(HttpServletRequest request) {
        String id = request.getParameter("id");
        //数据查询
        List<List<List<List<ObjectPropertyInfoDto>>>> lists = tabTemplateServiceImpl.goTabsView(request, id,"all","1","all");
        request.setAttribute("listss",lists);
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabs-view");
    }
    
    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */
    @RequestMapping(params = "goTabView2")
    @ResponseBody
    public FeignJson goTabView2(HttpServletRequest request) {
        String planId = request.getParameter("planId");
        String displayAccess = request.getParameter("displayAccess");
        String activityId = request.getParameter("activityId");
        String fromDetailType = request.getParameter("fromDetailType");
        String isEnableFlag = request.getParameter("isEnableFlag");
        if("mxgraphPlanChange".equals(fromDetailType) && "1".equals(isEnableFlag)) {
            //变更查看：
            FlowTaskParentVo flowTaskParent = new FlowTaskParentVo();
            flowTaskParent = rdFlowTaskFlowResolveService.getFlowTaskParent(planId);
            if(!CommonUtil.isEmpty(flowTaskParent)) {               
                planId = flowTaskParent.getParentId();
            }
        }

        List<TabCombinationTemplateDto> list = new ArrayList<>();
        if(!CommonUtil.isEmpty(activityId)){
            list = tabCombinationTemplateFeignService.findTabCbTempByActivityId(activityId);
        }else{
            PlanDto planDto = planService.getPlanEntity(planId);
            list = tabCombinationTemplateFeignService.findTabCbTempByActivityId(planDto.getTaskNameType());
        }
        FeignJson combTemplateInfos = new FeignJson();
        if(!CommonUtil.isEmpty(list)){
            combTemplateInfos = tabCbTemplateFeignService.getCombTemplateInfosByPlanId(planId,activityId,displayAccess);
        }else{
            combTemplateInfos.setSuccess(false);
            combTemplateInfos.setMsg("当前计划类型不存在启用的页签组合模板");
        }

        return combTemplateInfos;

    }
    
    public static Map<String, Object> objectToMap(Object obj) throws IllegalArgumentException, IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for(java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }


    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */
    @RequestMapping(params = "goTabCommonDetail")
    public ModelAndView goTabCommonDetail(HttpServletRequest request) {
        String projectId = request.getParameter("id");
        String tabId = request.getParameter("tabId");
        String title = request.getParameter("title"); 
        String planId = request.getParameter("planId");
        String isEnableFlag = request.getParameter("isEnableFlag");
        String fromDetailType = request.getParameter("fromDetailType");
        String onlyReadonly = request.getParameter("onlyReadonly");
        String showProjectInfo = request.getParameter("showProjectInfo");     
        String showBasicButtom = request.getParameter("showBasicButtom");
        String isStandard = request.getParameter("isStandard");
        
        request.setAttribute("showBasicButtom",showBasicButtom);
        if(!CommonUtil.isEmpty(onlyReadonly)) {
            request.setAttribute("onlyReadonly",onlyReadonly);
        }
        String tabTemplateCode = "";
        PlanDto plan = planService.getPlanEntity(planId); 
        if(!CommonUtil.isEmpty(plan)) {
            String index = request.getParameter("index");  
            request.setAttribute("index",index);
            //进入方式：0：编辑，2：查看
            String enterType = request.getParameter("enterType");
            request.setAttribute("enterType",enterType);
            
            String showLog = request.getParameter("showLog");
            if (!CommonUtil.isEmpty(showLog)) {
                request.setAttribute("showLog",showLog);
            }
            //页面input框数据集合
            Map<String,Object> results = new HashMap<>();
            results.put("planId",planId);
            results.put("projectId",projectId);
            results.put("planBizCurrent",plan.getBizCurrent());
            results.put("showProjectInfo",showProjectInfo);
            results.put("enterType",enterType);
            results.put("onlyReadonly",onlyReadonly);
            results.put("fromType",plan.getTaskNameType());
            results.put("isEnableFlag",isEnableFlag);
            request.setAttribute("isEnableFlag",isEnableFlag);         
            results.put("fromDetailType",fromDetailType);
            results.put("preposeIds",plan.getPreposeIds());
            results.put("preposeEndTime","");


            //通过页签模板id获取页签模板信息
            TabTemplateDto tabTemplateDto = tabTemplateFeign.queryTabTemplate(tabId);
            if (!CommonUtil.isEmpty(tabTemplateDto)) {
                tabTemplateCode = tabTemplateDto.getCode();
            }

            if (StringUtils.isNotBlank(tabTemplateCode)) {
                //判断js文件是否存在
                String filePath = request.getSession().getServletContext().getRealPath(CommonConstants.jsPath+tabTemplateCode+".js");
                File file = new File(filePath);
                if (file.exists()) {
                    request.setAttribute("code",tabTemplateCode);
                }
            }

            List<DataSourceObjectDto> dtoList = dataSourceObjectFeign.getAllDataByTabId(tabId);
            String entityPath ="";
            if(dtoList.size()>0) {
                entityPath = dtoList.get(0).getObjectPath();
            }
            
            Map<String, Object> mapEntity = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            Map<String, TSUserDto> allUserMap= null;          
            Map<String, String> planLevelIdAndNameMap= new HashMap<>();
            Map<String, String> planLevelIdAndNameAllMap= new HashMap<>();
            Map<String, String> activityTypeManageIdAndNameMap= new HashMap<>();
            Map<String, String> statusMap= new HashMap<>();
            Map<String, String> taskTypeMap= new HashMap<>();
            if(!CommonUtil.isEmpty(plan.getOwner()) && !CommonUtil.isEmpty(tabTemplateCode)) {
                if(plan.getOwner().equals(UserUtil.getInstance().getUser().getId())) {
                    results.put("isOwner", true);
                }
            }

            if("TC001".equals(tabTemplateCode)) {
                results.put("isStandard",isStandard);
                
                allUserMap= userService.getAllUserIdsMap();
                List<JSONObject> jsonList = new ArrayList<JSONObject>();
                for (String  t : allUserMap.keySet()) {
                    TSUserDto cuTsUserDto  = allUserMap.get(t);
                    JSONObject obj1 = new JSONObject();
                    obj1.put("id", cuTsUserDto.getId());
                    obj1.put("realName", cuTsUserDto.getRealName() + "-" + cuTsUserDto.getUserName());
                    jsonList.add(obj1);
                }
                String jonStrUser = JSON.toJSONString(jsonList);
                String jonStr1 = jonStrUser;
                jonStr1 = jonStr1.replaceAll("\"", "'");
                results.put("userList2",jonStr1);                
                try {
                    map = objectToMap(plan);
                    map.put("bizCurrent", plan.getBizCurrent());
                    map.put("createFullName", plan.getCreateFullName()+"-"+plan.getCreateName());
                    if(!CommonUtil.isEmpty(plan.getCreateTime())) {
                        map.put("createTime",  DateUtil.getStringFromDate(plan.getCreateTime(), DateUtil.YYYY_MM_DD));
                    }
                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        //初始化责任部门：
                        List<TSDepartDto> TSDepartDtoList = deptService.getTSDepartByuserId(appKey,plan.getOwner());
                        if(TSDepartDtoList.size()>0) {
                            map.put("ownerDept", TSDepartDtoList.get(0).getDepartname());
                        }
                    }
                    
                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        map.put("owner", plan.getOwner());
                    }else {
                        map.put("owner", "");
                    }

                    //开始时间：
                    if(!CommonUtil.isEmpty(map.get("planStartTime"))) {
                        map.put("planStartTime",  DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                    }    
                    
                    //结束时间：
                    if(!CommonUtil.isEmpty(map.get("planEndTime"))) {
                        map.put("planEndTime",  DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
                    } 
                    
                    //下达时间：
                    if(!CommonUtil.isEmpty(map.get("assignTime"))) {
                        map.put("assignTime",  DateUtil.getStringFromDate(plan.getAssignTime(), DateUtil.YYYY_MM_DD));
                    }
                    //计划进度：
                    String progressRate = "0.00%";
                    if(!CommonUtil.isEmpty(map.get("progressRate"))) {
                        progressRate = map.get("progressRate")+"%";
                        map.put("progressRate", progressRate);
                    }
                    //上级计划：
                    if(!CommonUtil.isEmpty(map.get("parentPlanId"))) {
                        PlanDto parentPlan = planService.getPlanEntity(map.get("parentPlanId").toString()); 
                        if(!CommonUtil.isEmpty(parentPlan)) {
                            map.put("parentPlanId", parentPlan.getPlanName());
                        }
                    }
                    
                    List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(plan);
                    String preposePlanNames = "";
                    String preposeIdsList = "";
                    if(!CommonUtil.isEmpty(preposePlanList)) {
                        for (PreposePlanDto preposePlan : preposePlanList) {                            
                            PlanDto curPreposePlan = planService.getPlanEntity(preposePlan.getPreposePlanId());
                            if(!CommonUtil.isEmpty(curPreposePlan)) {
                                if(CommonUtil.isEmpty(preposePlanNames)) {
                                    preposePlanNames = curPreposePlan.getPlanName();
                                    preposeIdsList = curPreposePlan.getId();
                                }else {
                                    preposePlanNames = preposePlanNames +","+curPreposePlan.getPlanName();
                                    preposeIdsList = preposeIdsList + "," + curPreposePlan.getId();
                                }
                            }
                        }
                        map.put("preposeNames", preposePlanNames);
                        results.put("preposeIds",preposeIdsList);
                        results.put("allPreposeIds","");
                        
                    }
                    
                    for(String  curKey : map.keySet()) {
                        String curClassFullName = plan.getClass().getName();
                        String curClassName =  curClassFullName.substring(curClassFullName.lastIndexOf(".")+1, curClassFullName.length());
                        if(curClassName.toLowerCase().contains("dto")) {
                            curClassName = curClassName.substring(0,curClassName.length()-3);
                        }
                        mapEntity.put(curClassName+"-"+curKey, map.get(curKey));
                    }

                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // 计划等级
                BusinessConfig planLevel = new BusinessConfig();
                planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                planLevel.setAvaliable("");
                //获取所有的计划等级：
                List<BusinessConfig> planLevelListStrAll = businessConfigService.searchBusinessConfigs(planLevel);
                if(planLevelListStrAll.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelListStrAll) {
                        planLevelIdAndNameAllMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }

                    String jonStr = JsonUtil.getCodeTitleJson(planLevelListStrAll, "id", "name");
                    results.put("planLevelList",jonStr);
                }

                //获取启用的计划等级：
                List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
            //    List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
                if(planLevelList.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelList) {
                        planLevelIdAndNameMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }
                }
                //获取计划类型 ：
                List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageEntityService.getAllActivityTypeManage(false);
                if(activityTypeManageList.size()>0) {
                    for(ActivityTypeManageDto activityTypeManageDto : activityTypeManageList) {
                        activityTypeManageIdAndNameMap.put(activityTypeManageDto.getId(), activityTypeManageDto.getName());
                    }
                }
                
                //状态：
                statusMap.put("EDITING", "拟制中");
                statusMap.put("LAUNCHED", "已发布");
                statusMap.put("TOBERECEIVED", "待接收");
                statusMap.put("ORDERED", "执行中");
                statusMap.put("FEEDBACKING", "完工确认");
                statusMap.put("FINISH", "已完工");
                statusMap.put("INVALID", "已废弃");

                //获取项目相关数据：
                if(!CommonUtil.isEmpty(plan.getProjectId())) {
                    Project project = projectService.getProjectEntity(projectId);
                    if(!CommonUtil.isEmpty(project)) {
                        String curClassFullName = project.getClass().getName();
                        String curClassName1 =  curClassFullName.substring(curClassFullName.lastIndexOf(".")+1, curClassFullName.length());
                        if(curClassName1.toLowerCase().contains("dto")) {
                            curClassName1 = curClassName1.substring(0,curClassName1.length()-3);
                        }

                        mapEntity.put("Plan"+"-"+"projectName", project.getName());
                        
                        String projectManagerNames = "";
                        if(!CommonUtil.isEmpty(project.getProjectManagers())){
                            for(String managerId :project.getProjectManagers().split(",") ){
                                TSUserDto userDto = allUserMap.get(managerId);
                                if(CommonUtil.isEmpty(projectManagerNames)){
                                    projectManagerNames = userDto.getRealName()+"-"+userDto.getUserName();
                                }else{
                                    projectManagerNames = projectManagerNames + "," +userDto.getRealName()+"-"+userDto.getUserName();
                                }
                            }
                            mapEntity.put(curClassName1+"-"+"projectManagerNames", projectManagerNames);
                        }
                        if(!CommonUtil.isEmpty(project.getStartProjectTime())) {
                            mapEntity.put(curClassName1+"-"+"startProjectTime", DateUtil.getStringFromDate(project.getStartProjectTime(), DateUtil.YYYY_MM_DD));
                        }
                        if(!CommonUtil.isEmpty(project.getEndProjectTime())) {
                            mapEntity.put(curClassName1+"-"+"endProjectTime", DateUtil.getStringFromDate(project.getEndProjectTime(), DateUtil.YYYY_MM_DD));
                        }
                        
                        if (!CommonUtil.isEmpty(project.getEps())) {
                            FeignJson fj = epsConfigService.getEpsNamePathById(project.getEps());
                            String epsName = "";
                            if (fj.isSuccess()) {
                                epsName = fj.getObj() == null ? "" : fj.getObj().toString();
                            }
                            mapEntity.put(curClassName1+"-"+"eps", epsName);
                        }
                        String rocess = "0.00%";
                        if(!CommonUtil.isEmpty(project.getProcess())) {
                            rocess = project.getProcess()+"%";                            
                        }
                        mapEntity.put(curClassName1+"-"+"process", rocess);
                    }
                }
                
            }
            else if("TC002".equals(tabTemplateCode)) {
      
            }
            else if("TC003".equals(tabTemplateCode)) {
                //request.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
                results.put("userLevel",UserUtil.getInstance().getUser().getSecurityLevel());
            }
            else if("TC004".equals(tabTemplateCode)) {                       
                String startTime = DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD);
                String endTime = DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD);
                //request.setAttribute("startTime", startTime);
                //request.setAttribute("endTime", endTime);
                results.put("startTime"+index,startTime);
                results.put("endTime"+index,endTime);
            }else if("TC006".equals(tabTemplateCode) || "TC005".equals(tabTemplateCode)) {//风险清单&问题页签获取teamId
                FeignJson teamFj = projRoleService.getTeamIdByProjectId(projectId);
                String teamId = "";
                if (teamFj.isSuccess()) {
                    teamId = teamFj.getObj() == null ? "" : teamFj.getObj().toString();
                }
                //request.setAttribute("teamId", teamId);
                results.put("teamId",teamId);
            }
            
            request.setAttribute("fromType","Plan");
            Map<String,Map<String,String>> loadMap = new HashMap<>();
            //数据查询
            List<List<List<ObjectPropertyInfoDto>>> lists = tabTemplateServiceImpl.goTabView(request, tabId);
            for(List<List<ObjectPropertyInfoDto>> cur2 : lists) {
                for(List<ObjectPropertyInfoDto> cur3 : cur2) {
/*                    for(ObjectPropertyInfoDto cur4 : cur3) {*/
                    for(int i=cur3.size()-1;i>=0; i--) {
                        ObjectPropertyInfoDto cur4 = new ObjectPropertyInfoDto();
                        cur4 = cur3.get(i);
                        if(cur3.get(i).getControl().equals("7") || cur3.get(i).getControl().equals("6")) {                            
                            if(!CommonUtil.isEmpty(cur4.getObjectPath())) {    
                                if(cur4.getObjectPath().contains("TaskCellDeliverItem")) {
                                    cur3.remove(i);
                                    continue;
                                }
                            }
                        }
                        String curEntity = "";
                        if(!CommonUtil.isEmpty(entityPath) && cur4.getObjectPath().equals(entityPath) && cur4.getControl().equals("6")) {
                            String curType =  entityPath.substring(entityPath.lastIndexOf(".")+1, entityPath.length());
                            curEntity = curType;
                            cur4.setId(curType);
                            request.setAttribute("fromType",curType);
                            request.setAttribute("dataGrideId",curType);
                           
                        }
                        
                      //按钮样式绑定 默认新增
                        if (cur4.getControl().equals("8")) {
                            //如果是按钮时，替换id：
                            cur4.setButtonDivId(tabTemplateCode+cur4.getPropertyName()+"Div");

                            //输出按钮隐藏：
                            if("TC003".equals(tabTemplateCode) && (!"ORDERED".equals(plan.getBizCurrent()))) {
                                cur4.setDisplay("3");
                            }
                            if(CommonUtil.isEmpty(cur4.getFormat())) {
                                cur4.setFormat("basis ui-icon-plus");
                            }
                        }

                        String propertyvalue = cur4.getPropertyValue();
                        String objectFullPath = cur4.getObjectPath();
                        if(!CommonUtil.isEmpty(objectFullPath) && !"/".equals(objectFullPath) && !cur4.getControl().equals("7") && !cur4.getControl().equals("6")) {
                            String  objectPath = objectFullPath.substring(objectFullPath.lastIndexOf(".")+1, objectFullPath.length());
                            String  curPropertyvalue = objectPath +"-"+ propertyvalue;
                            cur4.setId(curPropertyvalue);
                            if(!CommonUtil.isEmpty(propertyvalue) && !CommonUtil.isEmpty(mapEntity) && !CommonUtil.isEmpty(mapEntity.get(curPropertyvalue))) {
                                cur4.setValueInfo(mapEntity.get(curPropertyvalue).toString());
                            }

                            if(!CommonUtil.isEmpty(propertyvalue)) {
                                if(!CommonUtil.isEmpty(cur4.getOperationEvent())&& !cur4.getControl().equals("8") && cur4.getId().equals(objectPath +"-"+ "owner")){
                                    Map<String,String> paramMap = new HashMap<>();
                                    String[] oper = cur4.getOperationEvent().split(";");
                                    for(String op : oper){
                                        if(op.indexOf("=") != -1){
                                            String[] operation = op.split("=");
                                            results.put("operationEventForPlanOwner",operation[1].substring(1,operation[1].length()-1));
                                        }
                                    }
                                    loadMap.put(cur4.getId(),paramMap);
                                }

//                                //说明：
//                                if("remark".equals(propertyvalue)) {
//                                    cur4.setId("Plan-remark");
//                                }
//                                
//                                if("planName".equals(propertyvalue)){
//                                    cur4.setId("Plan-planName");
//                                }
                                
                                if("owner".equals(propertyvalue)){
                                    cur4.setValueField("id");
                                    cur4.setTextField("realName");
//                                    cur4.setId("Plan-owner");
                                    cur4.setLoadUrl("taskFlowResolveController.do?projectMembers&parentPlanId="+planId+"");
                                }
                                
                                if("planLevel".equals(propertyvalue)){
                                    cur4.setValueField("id");
                                    cur4.setTextField("name");
//                                    cur4.setId("Plan-planLevel");
                                    cur4.setLoadUrl("planController.do?useablePlanLevelList");
                                }
                                
                                
                                
                                if("preposeIds".equals(propertyvalue)){
//                                    cur4.setId("Plan-preposeIds");
                                    if(!CommonUtil.isEmpty(mapEntity) && !CommonUtil.isEmpty(mapEntity.get("Plan-preposeNames"))) {
                                        cur4.setValueInfo((String)mapEntity.get("Plan-preposeNames"));
                                    }
                                }
                                
//                                if("workTime".equals(propertyvalue)){
//                                    cur4.setId("Plan-workTime");                               
//                                }
//                                if("planStartTime".equals(propertyvalue)){
//                                    cur4.setId("planStartTime");                               
//                                }
//                                
//                                if("planEndTime".equals(propertyvalue)){
//                                    cur4.setId("planEndTime");                               
//                                }
                                
                                if(!CommonUtil.isEmpty(mapEntity) && !CommonUtil.isEmpty(mapEntity.get(curPropertyvalue))) {
                                    //初始化责任人：
                                    if("owner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(mapEntity.get(curPropertyvalue)))) {
                                        cur4.setValueInfo(allUserMap.get(mapEntity.get(curPropertyvalue)).getRealName()+"-"+allUserMap.get(mapEntity.get(curPropertyvalue)).getUserName());                                      
                                    }
                                    
                                    //下达人：
                                    if("assigner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(mapEntity.get(curPropertyvalue)))) {
                                        cur4.setValueInfo(allUserMap.get(mapEntity.get(curPropertyvalue)).getRealName()+"-"+allUserMap.get(mapEntity.get(curPropertyvalue)).getUserName());
                                    }

                                    //初始化责任人：
                                    if("planLevel".equals(propertyvalue)) {
                                        if(!CommonUtil.isEmpty(planLevelIdAndNameMap.get(map.get(propertyvalue)))) {
                                            cur4.setValueInfo(planLevelIdAndNameMap.get(map.get(propertyvalue)));
                                        }else if(!CommonUtil.isEmpty(planLevelIdAndNameAllMap.get(map.get(propertyvalue)))){
                                            cur4.setValueInfo(planLevelIdAndNameAllMap.get(map.get(propertyvalue)));
                                        }
                                    }

                                    //里程碑：
                                    if("milestone".equals(propertyvalue)) {
                                        String milestone = mapEntity.get(curPropertyvalue).toString();
                                        if("true".equals(milestone)) {
                                            cur4.setValueInfo("是");
                                        }else {
                                            cur4.setValueInfo("否");
                                        }
                                        
                                    }
                                    
                                    //获取计划类型 ：
                                    if("taskNameType".equals(propertyvalue)) {
                                        if(!CommonUtil.isEmpty(activityTypeManageIdAndNameMap.get(mapEntity.get(curPropertyvalue)))) {
                                            cur4.setValueInfo(activityTypeManageIdAndNameMap.get(mapEntity.get(curPropertyvalue)));
                                        }else {
                                            cur4.setValueInfo("研发类");
                                        }
                                       
                                    }
                                    
                                    //状态：
                                    if("bizCurrent".equals(propertyvalue)) {
                                        if(!CommonUtil.isEmpty(statusMap.get(mapEntity.get(curPropertyvalue)))) {
                                            cur4.setValueInfo(statusMap.get(mapEntity.get(curPropertyvalue)));
                                        }else {
                                            cur4.setValueInfo("拟制中");
                                        }   
                                    }

                                    //开始时间：
                                    if("planStartTime".equals(propertyvalue)) {
                                        cur4.setDefaultValue(plan.getPlanStartTime());
                                    }
                                    //结束时间：
                                    if("planEndTime".equals(propertyvalue)) {
                                        cur4.setDefaultValue(plan.getPlanEndTime());
                                    }
                                    //创建时间：
                                    if("createTime".equals(propertyvalue)) {
                                        cur4.setDefaultValue(plan.getCreateTime());
                                    }
                                    //发布时间：
                                    if("assignTime".equals(propertyvalue)) {
                                        cur4.setDefaultValue(plan.getAssignTime());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            request.setAttribute("results",results);
            request.setAttribute("lists",lists);
        }
        return new ModelAndView("com/glaway/ids/planGeneral/planGeneral");
    }

    public String stringToJson(String str,boolean isJSJson){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<str.length();i++){
            char c = str.charAt(i);
            switch (c){
                case '\'':
                    if(isJSJson){
                        sb.append("\\\'");
                    }else{
                        sb.append("\'");
                    }
                    break;
                case '\"' :
                    if(!isJSJson){
                        sb.append("\\\"");
                    }else{
                        sb.append("\"");
                    }
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */
    @RequestMapping(params = "goTabCommonDetailForPlanChange")
    public ModelAndView goTabCommonDetailForPlanChange(HttpServletRequest request) {
        String projectId = request.getParameter("id");
        String tabId = request.getParameter("tabId");
        String title = request.getParameter("title");
        String planId = request.getParameter("planId");
        String isEnableFlag = request.getParameter("isEnableFlag");
        String fromDetailType = request.getParameter("fromDetailType");
        String onlyReadonly = request.getParameter("onlyReadonly");
        String showProjectInfo = request.getParameter("showProjectInfo");
        String showBasicButtom = request.getParameter("showBasicButtom");
        String isStandard = request.getParameter("isStandard");
        String taskNumber = request.getParameter("taskNumber");

        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(taskNumber);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto plan = null;
        PlanDto p = null;
        if(!CommonUtil.isEmpty(approve)){
            plan = feignPlanChangeServiceI.getTemporaryPlan(approve.get(0).getPlanId());
            p = planService.getPlanEntity(plan.getPlanId());
            plan.setAssigner(p.getAssigner());
            plan.setAssignTime(p.getAssignTime());
            plan.setCreateTime(p.getCreateTime());
        }

        request.setAttribute("showBasicButtom",showBasicButtom);
        if(!CommonUtil.isEmpty(onlyReadonly)) {
            request.setAttribute("onlyReadonly",onlyReadonly);
        }
        String tabTemplateCode = "";
        if(!CommonUtil.isEmpty(plan)) {
            String index = request.getParameter("index");
            request.setAttribute("index",index);
            //进入方式：0：编辑，2：查看
            String enterType = request.getParameter("enterType");
            request.setAttribute("enterType",enterType);

            String showLog = request.getParameter("showLog");
            if (!CommonUtil.isEmpty(showLog)) {
                request.setAttribute("showLog",showLog);
            }
            //页面input框数据集合
            Map<String,Object> results = new HashMap<>();
            results.put("planId",plan.getPlanId());
            results.put("projectId",projectId);
            results.put("planBizCurrent",plan.getBizCurrent());
            results.put("showProjectInfo",showProjectInfo);
            results.put("enterType",enterType);
            results.put("onlyReadonly",onlyReadonly);
            results.put("fromType",plan.getTaskNameType());
            results.put("isEnableFlag",isEnableFlag);
            request.setAttribute("isEnableFlag",isEnableFlag);
            results.put("fromDetailType","planChange");
            results.put("preposeIds",plan.getPreposeIds());
            results.put("preposeEndTime","");
            results.put("preposeEndTime","");
            results.put("taskNumber",taskNumber);
            results.put("assigner",plan.getAssigner());
            results.put("assignTime",plan.getAssignTime());


            //通过页签模板id获取页签模板信息
            TabTemplateDto tabTemplateDto = tabTemplateFeign.queryTabTemplate(tabId);
            if (!CommonUtil.isEmpty(tabTemplateDto)) {
                tabTemplateCode = tabTemplateDto.getCode();
            }

            if (StringUtils.isNotBlank(tabTemplateCode)) {
                //判断js文件是否存在
                String filePath = request.getSession().getServletContext().getRealPath(CommonConstants.jsPath+tabTemplateCode+".js");
                File file = new File(filePath);
                if (file.exists()) {
                    request.setAttribute("code",tabTemplateCode);
                }
            }

            List<DataSourceObjectDto> dtoList = dataSourceObjectFeign.getAllDataByTabId(tabId);
            String entityPath ="";
            if(dtoList.size()>0) {
                entityPath = dtoList.get(0).getObjectPath();
            }

            Map<String, Object> map = new HashMap<>();
            Map<String, TSUserDto> allUserMap= null;
            Map<String, String> planLevelIdAndNameAllMap= new HashMap<>();
            Map<String, String> planLevelIdAndNameMap= new HashMap<>();
            Map<String, String> activityTypeManageIdAndNameMap= new HashMap<>();
            Map<String, String> statusMap= new HashMap<>();
            Map<String, String> taskTypeMap= new HashMap<>();


            if("TC001".equals(tabTemplateCode)) {
                results.put("isStandard",isStandard);

                allUserMap= userService.getAllUserIdsMap();
                List<JSONObject> jsonList = new ArrayList<JSONObject>();
                for (String  t : allUserMap.keySet()) {
                    TSUserDto cuTsUserDto  = allUserMap.get(t);
                    JSONObject obj1 = new JSONObject();
                    obj1.put("id", cuTsUserDto.getId());
                    obj1.put("realName", cuTsUserDto.getRealName() + "-" + cuTsUserDto.getUserName());
                    jsonList.add(obj1);
                }
                String jonStrUser = JSON.toJSONString(jsonList);
                String jonStr1 = jonStrUser;
                jonStr1 = jonStr1.replaceAll("\"", "'");
                results.put("userList2",jonStr1);
                try {
                    map = objectToMap(plan);
                    map.put("bizCurrent", plan.getBizCurrent());
                    map.put("createFullName", plan.getCreateFullName()+"-"+plan.getCreateName());
                    if(!CommonUtil.isEmpty(plan.getCreateTime())) {
                        map.put("createTime",  DateUtil.getStringFromDate(plan.getCreateTime(), DateUtil.YYYY_MM_DD));
                    }
        /*            if(!CommonUtil.isEmpty(plan.getCreateOrgId())) {
                        //初始化责任部门：
                        TSDepartDto curTSDepartDto = deptService.getTSDepartById(appKey, plan.getCreateOrgId());
                        map.put("ownerDept", curTSDepartDto.getDepartname());
                      }*/

                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        Map<String, List<TSDepartDto>> departMap = deptService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");

                        TSUserDto o  = userService.getUserByUserId(plan.getOwner());
                        if (o != null) {
                            List<TSDepartDto> departDtoList = departMap.get(plan.getOwner());
                            String depName = "";
                            if (!CommonUtil.isEmpty(departDtoList)) {
                                for(TSDepartDto dep : departDtoList){
                                    if(CommonUtil.isEmpty(depName)){
                                        depName = dep.getDepartname();
                                    }else {
                                        depName = depName +","+ dep.getDepartname();
                                    }
                                }
                                map.put("ownerDept", depName);
                            }
                        }

                        map.put("owner", plan.getOwner());
                      }else {
                          map.put("owner", "");
                      }

                    //开始时间：
                    if(!CommonUtil.isEmpty(map.get("planStartTime"))) {
                        map.put("planStartTime",  DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                    }

                    //结束时间：
                    if(!CommonUtil.isEmpty(map.get("planEndTime"))) {
                        map.put("planEndTime",  DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
                    }

                    //下达时间：
                    if(!CommonUtil.isEmpty(map.get("assignTime"))) {
                        map.put("assignTime",  DateUtil.getStringFromDate(plan.getAssignTime(), DateUtil.YYYY_MM_DD));
                    }
                    //计划进度：
                    String progressRate = "0.00%";
                    if(!CommonUtil.isEmpty(map.get("progressRate"))) {
                        progressRate = map.get("progressRate")+"%";
                        map.put("progressRate", progressRate);
                    }
                    //上级计划：
                    if(!CommonUtil.isEmpty(map.get("parentPlanId"))) {
                        PlanDto parentPlan = planService.getPlanEntity(map.get("parentPlanId").toString());
                        if(!CommonUtil.isEmpty(parentPlan)) {
                            map.put("parentPlanId", parentPlan.getPlanName());
                        }
                    }

                    String preposePlanNames = "";
                    String preposeIdsList = "";
                    if(!CommonUtil.isEmpty(plan.getPreposeIds())) {
                        for (String preposeId : plan.getPreposeIds().split(",")) {
                            PlanDto curPreposePlan = planService.getPlanEntity(preposeId);
                            if(CommonUtil.isEmpty(preposePlanNames)) {
                                preposePlanNames = curPreposePlan.getPlanName();
                                preposeIdsList = curPreposePlan.getId();
                            }else {
                                preposePlanNames = preposePlanNames +","+curPreposePlan.getPlanName();
                                preposeIdsList = preposeIdsList + "," + curPreposePlan.getId();
                            }
                        }
                        map.put("preposeIds", preposePlanNames);
                        results.put("preposeIds",preposeIdsList);
                        results.put("allPreposeIds","");

                    }

                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // 计划等级
                BusinessConfig planLevel = new BusinessConfig();
                planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                //获取所有的计划等级：
                List<BusinessConfig> planLevelListStrAll = businessConfigService.searchBusinessConfigs(planLevel);
                if(planLevelListStrAll.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelListStrAll) {
                        planLevelIdAndNameAllMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }

                    String jonStr = JsonUtil.getCodeTitleJson(planLevelListStrAll, "id", "name");
                    results.put("planLevelList",jonStr);
                }

                //获取启用的计划等级：
                List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
          //      List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
                if(planLevelList.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelList) {
                        planLevelIdAndNameMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }
                }

                //获取计划类型 ：
                List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageEntityService.getAllActivityTypeManage(false);
                if(activityTypeManageList.size()>0) {
                    for(ActivityTypeManageDto activityTypeManageDto : activityTypeManageList) {
                        activityTypeManageIdAndNameMap.put(activityTypeManageDto.getId(), activityTypeManageDto.getName());
                    }
                }

                //状态：
                statusMap.put("EDITING", "拟制中");
                statusMap.put("LAUNCHED", "已发布");
                statusMap.put("TOBERECEIVED", "待接收");
                statusMap.put("ORDERED", "执行中");
                statusMap.put("FEEDBACKING", "完工确认");
                statusMap.put("FINISH", "已完工");
                statusMap.put("INVALID", "已废弃");

                //计划类别：
                if(!CommonUtil.isEmpty(plan.getTaskType())) {
                    if("1".equals(plan.getTaskType())) {
                        map.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else if("2".equals(plan.getTaskType())) {
                        map.put("taskType", PlanConstants.PLAN_TYPE_TASK);
                    }else {
                        map.put("taskType", PlanConstants.PLAN_TYPE_FLOW);
                    }
                }

                //获取项目相关数据：
                if(!CommonUtil.isEmpty(plan.getProjectId())) {
                    Project project = projectService.getProjectEntity(projectId);
                    if(!CommonUtil.isEmpty(project)) {
                        map.put("projectName", project.getName());

                        String projectManagerNames = "";
                        if(!CommonUtil.isEmpty(project.getProjectManagers())){
                            for(String managerId :project.getProjectManagers().split(",") ){
                                TSUserDto userDto = allUserMap.get(managerId);
                                if(!CommonUtil.isEmpty(userDto)){
                                    if(CommonUtil.isEmpty(projectManagerNames)){
                                        projectManagerNames = userDto.getRealName()+"-"+userDto.getUserName();
                                    }else{
                                        projectManagerNames = projectManagerNames + "," +userDto.getRealName()+"-"+userDto.getUserName();
                                    }
                                }
                            }
                            map.put("projectManagerNames", projectManagerNames);
                        }
                        if(!CommonUtil.isEmpty(project.getStartProjectTime())) {
                            map.put("startProjectTime", DateUtil.getStringFromDate(project.getStartProjectTime(), DateUtil.YYYY_MM_DD));
                        }
                        if(!CommonUtil.isEmpty(project.getEndProjectTime())) {
                            map.put("endProjectTime", DateUtil.getStringFromDate(project.getEndProjectTime(), DateUtil.YYYY_MM_DD));
                        }

                        if (!CommonUtil.isEmpty(project.getEps())) {
                            FeignJson fj = epsConfigService.getEpsNamePathById(project.getEps());
                            String epsName = "";
                            if (fj.isSuccess()) {
                                epsName = fj.getObj() == null ? "" : fj.getObj().toString();
                            }
                            map.put("eps", epsName);
                        }
                        String rocess = "0.00%";
                        if(!CommonUtil.isEmpty(project.getProcess())) {
                            rocess = project.getProcess()+"%";
                        }
                        map.put("process", rocess);
                    }
                }

            }
            else if("TC002".equals(tabTemplateCode)) {

            }
            else if("TC003".equals(tabTemplateCode)) {
                //request.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
                results.put("userLevel",UserUtil.getInstance().getUser().getSecurityLevel());
            }
            else if("TC004".equals(tabTemplateCode)) {
                String startTime = DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD);
                String endTime = DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD);
                //request.setAttribute("startTime", startTime);
                //request.setAttribute("endTime", endTime);
                results.put("startTime"+index,startTime);
                results.put("endTime"+index,endTime);
            }else if("TC006".equals(tabTemplateCode) || "TC005".equals(tabTemplateCode)) {//风险清单&问题页签获取teamId
                FeignJson teamFj = projRoleService.getTeamIdByProjectId(projectId);
                String teamId = "";
                if (teamFj.isSuccess()) {
                    teamId = teamFj.getObj() == null ? "" : teamFj.getObj().toString();
                }
                //request.setAttribute("teamId", teamId);
                results.put("teamId",teamId);
            }

            request.setAttribute("fromType","Plan");
            //数据查询
            List<List<List<ObjectPropertyInfoDto>>> lists = tabTemplateServiceImpl.goTabView(request, tabId);
            for(List<List<ObjectPropertyInfoDto>> cur2 : lists) {
                for(List<ObjectPropertyInfoDto> cur3 : cur2) {
/*                    for(ObjectPropertyInfoDto cur4 : cur3) {*/
                    for(int i=cur3.size()-1;i>=0; i--) {
                        ObjectPropertyInfoDto cur4 = new ObjectPropertyInfoDto();
                        cur4 = cur3.get(i);
                        if(cur3.get(i).getControl().equals("7") || cur3.get(i).getControl().equals("6")) {
                            if(!CommonUtil.isEmpty(cur4.getObjectPath())) {
                                if(cur4.getObjectPath().contains("TaskCellDeliverItem")) {
                                    cur3.remove(i);
                                    continue;
                                }
                            }
                        }
                        if(!CommonUtil.isEmpty(entityPath) && cur4.getObjectPath().equals(entityPath) && cur4.getControl().equals("6")) {
                            String curType =  entityPath.substring(entityPath.lastIndexOf(".")+1, entityPath.length());
                            cur4.setId(curType);
                            request.setAttribute("fromType",curType);
                            request.setAttribute("dataGrideId",curType);

                        }
                        String propertyvalue = cur4.getPropertyValue();

                        if(!CommonUtil.isEmpty(propertyvalue) && !CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get(propertyvalue))) {
                            cur4.setValueInfo(map.get(propertyvalue).toString());
                        }

                        //按钮样式绑定 默认新增
                        if (cur4.getControl().equals("8")) {
                            //输出按钮隐藏：
                            if("TC003".equals(tabTemplateCode) && (!"ORDERED".equals(plan.getBizCurrent()))) {
                                cur4.setDisplay("3");
                            }
                            if(CommonUtil.isEmpty(cur4.getFormat())) {
                                cur4.setFormat("basis ui-icon-plus");
                            }
                        }
                        if(!CommonUtil.isEmpty(propertyvalue)) {
                            //说明：
                            if("remark".equals(propertyvalue)) {
                                cur4.setId("Plan-remark");
                            }

                            if("planName".equals(propertyvalue)){
                                cur4.setId("Plan-planName");
                            }

                            if("owner".equals(propertyvalue)){
                                cur4.setValueField("id");
                                cur4.setTextField("realName");
                                cur4.setId("Plan-owner");
                                cur4.setLoadUrl("taskFlowResolveController.do?projectMembers&parentPlanId="+planId+"");
                            }

                            if("planLevel".equals(propertyvalue)){
                                cur4.setValueField("id");
                                cur4.setTextField("name");
                                cur4.setId("Plan-planLevel");
                                cur4.setLoadUrl("planController.do?useablePlanLevelList");
                            }

                            if("preposeIds".equals(propertyvalue)){
                                cur4.setId("Plan-preposeIds");
                                if(!CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get("preposeIds"))) {
                                    cur4.setValueInfo((String)map.get("preposeIds"));
                                }
                            }

                            if("workTime".equals(propertyvalue)){
                                cur4.setId("Plan-workTime");
                            }
                            if("planStartTime".equals(propertyvalue)){
                                cur4.setId("planStartTime");
                            }

                            if("planEndTime".equals(propertyvalue)){
                                cur4.setId("planEndTime");
                            }

                            if(!CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get(propertyvalue))) {
                                //初始化责任人：
                                if("owner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(map.get(propertyvalue)))) {
                                    cur4.setValueInfo(allUserMap.get(map.get(propertyvalue)).getRealName()+"-"+allUserMap.get(map.get(propertyvalue)).getUserName());
                                }

                                //下达人：
                                if("assigner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(map.get(propertyvalue)))) {
                                    cur4.setValueInfo(allUserMap.get(map.get(propertyvalue)).getRealName()+"-"+allUserMap.get(map.get(propertyvalue)).getUserName());
                                }

                                //初始化责任人：
                                if("planLevel".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(planLevelIdAndNameMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(planLevelIdAndNameMap.get(map.get(propertyvalue)));
                                    }else if(!CommonUtil.isEmpty(planLevelIdAndNameAllMap.get(map.get(propertyvalue)))){
                                        cur4.setValueInfo(planLevelIdAndNameAllMap.get(map.get(propertyvalue)));
                                    }
                                }
                                //里程碑：
                                if("milestone".equals(propertyvalue)) {
                                    String milestone = map.get(propertyvalue).toString();
                                    if("true".equals(milestone)) {
                                        cur4.setValueInfo("是");
                                    }else {
                                        cur4.setValueInfo("否");
                                    }
                                }

                                //开始时间：
                                if("planStartTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getPlanStartTime());
                                }
                                //结束时间：
                                if("planEndTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getPlanEndTime());
                                }
                                //创建时间：
                                if("createTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getCreateTime());
                                }
                                //发布时间：
                                if("assignTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getAssignTime());
                                }

                                //获取计划类型 ：
                                if("taskNameType".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(activityTypeManageIdAndNameMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(activityTypeManageIdAndNameMap.get(map.get(propertyvalue)));
                                    }else {
                                        cur4.setValueInfo("研发类");
                                    }

                                }

                                //状态：
                                if("bizCurrent".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(statusMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(statusMap.get(map.get(propertyvalue)));
                                    }else {
                                        cur4.setValueInfo("拟制中");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            request.setAttribute("results",results);
            request.setAttribute("lists",lists);

        }
        return new ModelAndView("com/glaway/ids/planGeneral/planGeneral");
    }
    
    /**
     * 交付物新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddInheritForChange")
    public ModelAndView goAddInheritForChange(DeliverablesInfoDto deliverablesInfo,
                                              HttpServletRequest req) {
        String cellIdForDocument = req.getParameter("cellId");
        String parentPlanIdForDocument = req.getParameter("parentPlanId");
        req.getSession().setAttribute("cellIdForDocument", cellIdForDocument);
        req.getSession().setAttribute("parentPlanIdForDocument", parentPlanIdForDocument);
        req.setAttribute("parentPlanId", parentPlanIdForDocument);
        return new ModelAndView("com/glaway/ids/pm/project/task/changeInheritDocument-add");
    }
}
