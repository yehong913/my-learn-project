package com.glaway.ids.project.projecttemplate.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.businessobject.service.BusinessObjectServiceI;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.foundation.common.dto.FdTeamRoleDto;
import com.glaway.foundation.common.dto.FdTeamRoleUserDto;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.ExcelVo;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignTeamService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.auth.ProjLibAuthManager;
import com.glaway.ids.config.auth.ProjectLibraryAuthorityEnum;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.NameStandardSwitchConstants;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.constant.SwitchConstants;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.project.menu.dto.ProjTemplateMenuDto;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.NameStandardDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.DeliveryStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.NameStandardRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.constant.PlantemplateConstant;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateExcelVo;
import com.glaway.ids.project.plantemplate.utils.SupportFlagConstants;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.FeignProjRolesServiceI;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectLibTemplateRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTmpLibAuthLibTmpLinkDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTmplTeamLinkDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateLibRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRoleRemoteFeignServiceI;
import com.glaway.ids.util.CodeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 项目模板Controller
 * @author wangshen
 * @date 2015-03-23 15:59:25
 * @version V1.0
 */
@Controller
@RequestMapping("/projTemplateController")
public class ProjTemplateController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjTemplateController.class);


    @Autowired
    private ProjTemplateRemoteFeignServiceI projTemplateService;


    @Autowired
    private FeignRoleService roleService;

    @Autowired
    private ProjTemplateRoleRemoteFeignServiceI projTemplateRoleService;

    /**
     * 配置业务接口
     */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;


    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    @Autowired
    private FeignRepService repService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProjectLibTemplateRemoteFeignServiceI projectLibTemplateService;

    @Autowired
    private ProjTemplateLibRemoteFeignServiceI projTemplateLibService;

    @Autowired
    private FeignProjRolesServiceI projRoleService;

    @Autowired
    private FeignTeamService teamService;

    @Autowired
    private ProjTemplateDetailRemoteFeignServiceI projTemplateDetailService;

    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

    @Autowired
    private PlanTemplateRemoteFeignServiceI planTemplateService;

    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;

    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardService;

    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    /**
     *  message
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 项目模板列表 页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "projTemplateList")
    public ModelAndView projTemplateList(HttpServletRequest request) {
        boolean isPTOM = projTemplateService.isPTOM(ResourceUtil.getCurrentUser().getId());
        request.setAttribute("isPTOM", isPTOM);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projectTemplateList");
    }

    /**
     * 批量删除项目模板
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(HttpServletRequest request) {
        String templateStr = request.getParameter("templates");
        List<ProjTemplateDto> templates = JSONArray.parseArray(templateStr, ProjTemplateDto.class);
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteSuccess");
        try {
            projTemplateService.deleteTemplate(templates);
            log.info(message);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteFail");
            log.error(message, e, templateStr, "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " json:" + templateStr};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 启用或禁用模板
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doOpenOrClose")
    @ResponseBody
    public AjaxJson doOpenOrClose(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String method = StringUtils.upperCase(request.getParameter("method"));
        String templateStr = request.getParameter("templates");
        List<ProjTemplateDto> templates = JSONArray.parseArray(templateStr, ProjTemplateDto.class);
        if (method.equalsIgnoreCase("open")) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.enableSuccess");
        }
        else {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.disableSuccess");
        }
        try {
            for (ProjTemplateDto template : templates) {
                template = projTemplateService.getProjTemplateEntity(template.getId());
                if (method.equalsIgnoreCase("open")) {
                    projTemplateService.openOrClose(template, ProjectConstants.PROJTEMPLATE_QIYONG);
                }
                else {
                    projTemplateService.openOrClose(template, ProjectConstants.PROJTEMPLATE_JINYONG);
                }
            }
          /*  log.info(message, templateStr, "");*/
        }
        catch (Exception e) {
            log.error(message, e, templateStr, "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() + " templateStr:" + templateStr};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 项目模板列表查询接口
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        String projTmplCreator = request.getParameter("projTmplCreator");
        PageList pageList = projTemplateService.queryEntity(conditionList,projTmplCreator,ResourceUtil.getCurrentUserOrg().getId());
        ObjectMapper mapper = new ObjectMapper();
        List<ProjTemplateDto> list = mapper.convertValue(pageList.getResultList(),new com.fasterxml.jackson.core.type.TypeReference<List<ProjTemplateDto>>(){});
        FeignJson fj = projTemplateService.getLifeCycleStatusList();
        List<LifeCycleStatus> statusList = JSON.parseArray(String.valueOf(fj.getObj()),LifeCycleStatus.class);
        Map<String, String> statusMap = new HashMap<String, String>();
        for(LifeCycleStatus status : statusList){
            statusMap.put(status.getName(), status.getTitle());
        }
        if(!CommonUtil.isEmpty(list)){
            for(ProjTemplateDto teProjTemplate : list){
                teProjTemplate.setStatus(statusMap.get(teProjTemplate.getBizCurrent()));
            }
        }

        long count = pageList.getCount();
        String json = JsonUtil.getListJsonWithoutQuote(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 项目模板详情主页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goAddProjTemplate")
    public ModelAndView goAddProjTemplate(HttpServletRequest request) {
        String templateId = request.getParameter("id");
        request.setAttribute("templateId", templateId);
        request.setAttribute("method", request.getParameter("method"));
        request.getSession().setAttribute("projTemplateRoleList",new ArrayList<TSRoleDto>());
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplate-add");
    }


    /**
     * 新增项目模板
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSaveNewTemplate")
    @ResponseBody
    public AjaxJson doSaveNewTemplate(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String templateId = request.getParameter("templateId");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String method = request.getParameter("method");
        try {
            List<TSRoleDto> roleList = (List<TSRoleDto>)request.getSession().getAttribute("projTemplateRoleList");
            Map<String,Object> map = new HashMap<>();
            map.put("currentUser",ResourceUtil.getCurrentUser());
            map.put("projTemplateRoleList",roleList);
            FeignJson fj = projTemplateService.doSaveNewTemplate(templateId,name,remark,method,map,ResourceUtil.getCurrentUserOrg().getId());
            if(fj.isSuccess()){
                ObjectMapper mapper = new ObjectMapper();
                ProjTemplateDto dto = JSON.parseObject(JSON.toJSONString(fj.getObj()),ProjTemplateDto.class);
                j.setObj(dto);
                //缺陷21384，处理方式为将团队信息放入session,最后点击确定时再保存，而不是新增角色时即生效
               /* List<TSRoleDto> projTemplateRoleList = new ArrayList<>();
                TSRoleDto roleDto = new TSRoleDto();
                roleDto.setId(UUIDGenerator.generate());
                roleDto.setRoleCode("manager");
                roleDto.setRoleName("项目经理");
                projTemplateRoleList.add(roleDto);
                request.getSession().setAttribute("projTemplateRoleList",projTemplateRoleList);*/
            }

            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveSuccess");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveFail");
            log.error(message, e, templateId, "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() + " templateId:" + templateId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 复制项目模板
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getCurrentId")
    @ResponseBody
    public AjaxJson getCurrentId( HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message=I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveSuccess");
        String templateId = request.getParameter("templateId");
        try {
            if(StringUtils.isNotBlank(templateId)) {
                ProjTemplateDto template = projTemplateService.getProjTemplateEntity(templateId);
                String bizId = template.getBizId();
                if(CommonUtil.isEmpty(bizId)){
                    bizId = "";
                }
                template = projTemplateService.getProjTemplateByBizId(bizId);
                ProjTemplateDto view = new ProjTemplateDto();
                view.setProjTmplName(template.getProjTmplName());
                view.setRemark(template.getRemark());
                view.setPersientId(template.getId());
                j.setObj(view);
                j.setSuccess(true);
            }else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveFail");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveFail");
            log.error(message, e, templateId, "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() + " templateId:" + templateId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目模板详情主页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "getAddTemplate")
    public void getAddTemplate(HttpServletRequest request, HttpServletResponse response) {
        ProjTemplateMenuDto condition = new ProjTemplateMenuDto();
        condition.setStatus(1);
        List<ProjTemplateMenuDto> menus = projTemplateService.searchProjTemplateMenu(condition);
        List<TreeNode> list = new ArrayList<TreeNode>();
        for (ProjTemplateMenuDto menu : menus) {
            ProjTemplateMenuDto child = new ProjTemplateMenuDto();
            try {
                PropertyUtils.copyProperties(child, menu);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isBlank(child.getParentId())) {
                // 父节点
                TreeNode node = new TreeNode(menu.getId(), menu.getParentId(),child.getText(), child.getText(), true);
                node.setDataObject(child);
                list.add(node);
            }
            else {
                if (StringUtils.isNotBlank(child.getUrl())) {
                    child.setUrl(child.getUrl() + "Add&isIframe=true&templateId=" );

                }
                TreeNode node = new TreeNode(menu.getId(), menu.getParentId(),child.getText(), child.getText(), true);
                node.setDataObject(child);
                list.add(node);
            }

        }

        String json = JsonUtil.toJsonString(list);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 回退项目模版版本
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBackVersion")
    @ResponseBody
    public AjaxJson doBackVersion(HttpServletRequest request) {
        String id = request.getParameter("id");
        String bizId = request.getParameter("bizId");
        String bizVersion = request.getParameter("bizVersion");
        String type = request.getParameter("type");
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.projectemplate.backSuccess"+type);
        try {
            ProjTemplateDto r = projTemplateService.getProjTemplateEntity(id);
            if(!CommonUtil.isEmpty(r.getBizVersion())){
                if(!bizVersion.equals(r.getBizVersion())){
                    message = I18nUtil.getValue("com.glaway.ids.pm.projectemplate.backCannotNew"+type);
                    j.setSuccess(false);
                    return j;
                }
                String verson = r.getBizVersion();
                String vs[] = verson.split("\\.");
                if(StringUtils.equalsIgnoreCase(type, "Min")){
                    if (vs[1].equals("1")) {
                        message = I18nUtil.getValue("com.glaway.ids.pm.projectemplate.cannotBackFirst"+type);
                        j.setSuccess(false);
                        return j;
                    }
                }
                if(StringUtils.equalsIgnoreCase(type, "Maj")){
                    if (vs[0].equals("A")) {
                        message = I18nUtil.getValue("com.glaway.ids.pm.projectemplate.cannotBackFirst"+type);
                        j.setSuccess(false);
                        return j;
                    }
                }
            }
            projTemplateService.backToVersion(id, bizId, type,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            log.info(message);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.projectemplate.backFailure"+type);
            log.error(message, e, id, "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " oids:" + id};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目模板详情新增
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplateDetailAdd")
    public ModelAndView goTemplateDetailAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        request.setAttribute("method", request.getParameter("method"));
        if((StringUtils.isNotBlank(request.getParameter("init"))))
        {

            request.setAttribute("init", request.getParameter("init"));
        }
        ProjTemplateDto template = new ProjTemplateDto();
        if(!CommonUtil.isEmpty(templateId))
        {
            ProjTemplateDto template2 = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = template2.getBizId();
            if(!CommonUtil.isEmpty(template2))
            {
                if(CommonUtil.isEmpty(template2.getBizId())){
                    bizId = "";
                }
                template = projTemplateService.getProjTemplateByBizId(bizId);
               /* String userName = UserUtil.getFormatUserNameId(template.getCreateBy());
                template.setCreateName(userName);*/
            }
        }
        if(StringUtils.equals(request.getParameter("copy"), "copy"))
        {
            request.setAttribute("copy", request.getParameter("copy"));
            template.setProjTmplName(template.getProjTmplName()+"副本");
            template.setRemark(template.getRemark());
        }
        request.setAttribute("templateId", template.getId());
        request.setAttribute("template", template);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateDetailAdd");
    }

    /**
     * 获得项目模板下的角色列表
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkTemplateNameRepeat")
    @ResponseBody
    public AjaxJson checkTemplateNameRepeat(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameNoRepeat");
        String name = request.getParameter("name");
        String id = request.getParameter("templateId");
        String type = request.getParameter("type");
        try {
            if(StringUtils.isNotBlank(name)){
                if(isTemplateNameRepeat(name,id,type)){
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameRepeat");
                    j.setSuccess(false);
                }else{
                    j.setSuccess(true);
                }
            }else{
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameNoEmpty");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameQueryFail");
            log.error(message, e, "", "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);

        }finally{

            j.setMsg(message);
            return j;
        }
    }

    private boolean isTemplateNameRepeat(String name, String tmpId,String type) {
        try {
            if(StringUtils.isNotBlank(name)){
                ProjTemplateDto p=new ProjTemplateDto();
                p.setProjTmplName(name);
                List<ProjTemplateDto> list = new ArrayList<ProjTemplateDto>();
                if(StringUtils.isNotBlank(tmpId) && !"copy".equals(type)){
                    ProjTemplateDto t = projTemplateService.getProjTemplateEntity(tmpId);
                    list = projTemplateService.getProjTemplateListByNameAndBizId(t.getBizId(),tmpId);
                }
                else{
                    list = projTemplateService.getProjTemplateListByName(name);
                }
                if(list.size()>0){
                    return true;
                }else {
                    return false;
                }

            }else{
                return false;
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameQueryFail");
            log.error(message, e, "", "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);

        }
    }


    /**
     * 复制项目模板
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doCopyProjectTemplate")
    @ResponseBody
    public AjaxJson doCopyProjectTemplate(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message=I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveSuccess");
        String templateId = request.getParameter("templateId");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String newTmpId = "";
        try {
            ProjTemplateDto p = new ProjTemplateDto();
            p.setProjTmplName(name);
            p.setRemark(remark);
            if(StringUtils.isNotBlank(templateId)) {
                FeignJson fj = projTemplateService.copyProjTemplate(p, templateId,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
                newTmpId = String.valueOf(fj.getObj());
                ProjTemplateDto view = new ProjTemplateDto();
                view.setProjTmplName(name);
                view.setRemark(remark);
                view.setPersientId(newTmpId);
                j.setObj(view);
                log.info(message);
            }else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveFail");
                log.error(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveFail");
            log.error(message, e, templateId, "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() + " templateId:" + templateId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
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
        ProjTemplateDto template = projTemplateService.getProjTemplateEntity(id);
        request.setAttribute("template", template);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplate-showHistory");
    }


    /**
     * 获取版本信息
     *
     * @return
     */
    @RequestMapping(params = "getVersionHistory")
    public void getVersionHistory(HttpServletRequest req, HttpServletResponse response) {
        try {
            String bizId = req.getParameter("bizId");
            // 自定义追加查询条件
            FeignJson fj = projTemplateService.getVersionHistoryAndCount(bizId, Integer.valueOf(req.getParameter("rows")),
                    Integer.valueOf(req.getParameter("page")));
            Map<String,Object> map = (Map<String,Object>)fj.getObj();
            ObjectMapper mapper = new ObjectMapper();
            List<ProjTemplateDto> projTemplateList = mapper.convertValue(map.get("projTemplateList"),new com.fasterxml.jackson.core.type.TypeReference<List<ProjTemplateDto>>(){});
            long count = Long.valueOf(String.valueOf(map.get("count")));
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setVersion(
                    1.0).create();
            String json = gson.toJson(projTemplateList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 项目模板详情主页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goProjTemplateLayout")
    public ModelAndView goProjTemplateLayout(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("viewHistory", request.getParameter("viewHistory"));
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateLayout");
    }

    /**
     * 项目模板详情跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplateDetail")
    public ModelAndView goTemplateDetail(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        if(CommonUtil.isEmpty(projectTemplateId)){
            projectTemplateId = "";
        }
        ProjTemplateDto template = projTemplateService.getProjTemplateEntity(projectTemplateId);
        String userName = template.getCreateFullName() +'-'+template.getCreateName();
        template.setCreateName(userName);
        FeignJson fj = projTemplateService.getLifeCycleStatusList();
        List<LifeCycleStatus> statusList = JSON.parseArray(String.valueOf(fj.getObj()),LifeCycleStatus.class);
      //  List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStr,LifeCycleStatus.class);

        for(LifeCycleStatus status : statusList){
            if(!CommonUtil.isEmpty(template.getBizCurrent()) && status.getName().equals(template.getBizCurrent())){
                template.setStatus(status.getTitle());
                break;
            }
        }
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("projectTemplate", template);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateDetail");
    }


    /**
     * 项目模板详情主页面跳转
     *
     * @param request
     * @see
     */
    @RequestMapping(params = "getProjTemplateLayout")
    public void getProjTemplateLayout(HttpServletRequest request, HttpServletResponse response) {
        String projectTemplateId = request.getParameter("projectTemplateId");
        ProjTemplateDto template= projTemplateService.getProjTemplateEntity(projectTemplateId);
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("viewHistory", request.getParameter("viewHistory"));
        String type = request.getParameter("type");
        ProjTemplateMenuDto condition = new ProjTemplateMenuDto();
        condition.setStatus(1);
        List<ProjTemplateMenuDto> menus = projTemplateService.searchProjTemplateMenu(condition);
        List<TreeNode> list = new ArrayList<TreeNode>();
        for (ProjTemplateMenuDto menu : menus) {
            ProjTemplateMenuDto child = new ProjTemplateMenuDto();
            try {
                PropertyUtils.copyProperties(child, menu);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if (StringUtils.isBlank(child.getParentId())) {
                // 父节点
                TreeNode node = new TreeNode(menu.getId(), menu.getParentId(),child.getText(), child.getText(), true);
                if("add".equalsIgnoreCase(type)){}else{
                    node.setName(template.getProjTmplName());
                }
                node.setDataObject(child);
                list.add(node);
            }
            else {
                if (StringUtils.isNotBlank(child.getUrl())) {
                    if("add".equalsIgnoreCase(type)){
                        child.setUrl(child.getUrl() + "&type=add&isIframe=true" );
                    }else{
                        child.setUrl(child.getUrl() + "&isIframe=true&id=" + template.getId());
                    }

                }
                TreeNode node = new TreeNode(menu.getId(), menu.getParentId(),child.getText(), child.getText(), true);
                node.setDataObject(child);
                list.add(node);
            }
        }
        String json = JsonUtil.toJsonString(list);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 项目模板计划跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplatePlan")
    public ModelAndView goTemplatePlan(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        ProjTemplateDto template = projTemplateService.getProjTemplateEntity(projectTemplateId);
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("projectTemplate", template);
        request.setAttribute("viewHistory", request.getParameter("viewHistory"));
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplatePlanList");
    }

    /**
     * 项目模板详细列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjTemplateTeam")
    public ModelAndView goProjTemplateTeam(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        request.setAttribute("projectTemplateId", projectTemplateId);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateTeam");
    }

    /**
     * 模板团队列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "getProjTemplateTeam")
    public void getProjTemplateTeam(HttpServletRequest request, HttpServletResponse response) {
        List<TSRoleDto> roles = null;
        String projTemplateId = request.getParameter("projectTemplateId");
        FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(projTemplateId);
        String teamId = String.valueOf(fj.getObj());
        if (StringUtils.isNotBlank(projTemplateId)) {
            roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
        }
        String json = JsonUtil.getListJsonWithoutQuote(roles);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 项目模板项目库跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplateLib")
    public ModelAndView goTemplateLib(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        request.setAttribute("templateId", projectTemplateId);
        ProjTmplTeamLinkDto projTmplTeamLink=new ProjTmplTeamLinkDto();
        projTmplTeamLink.setProjTemplateId(projectTemplateId);
        FeignJson fj= projTemplateRoleService.getTeamIdByTemplateId(projectTemplateId);
        String libId = String.valueOf(fj.getObj());
        List<RepFileDto> roots = repService.getRootDirsByParams(ResourceUtil.getApplicationInformation().getAppKey(),libId,"0",libId);
        String rootId="";
        if (!CommonUtil.isEmpty(roots) && roots.size() == 1) {
            rootId = roots.get(0).getId();

        }
        request.setAttribute("rootId", rootId);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateLib");
    }

    /**
     * 权限列表
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goPowerList")
    public ModelAndView goPowerList(HttpServletRequest request, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("com/glaway/ids/project/projecttemplate/projectTemplate-power");
        String isView = request.getParameter("isView");
        String fileId = request.getParameter("fileId");
        String templateId = request.getParameter("templateId");
        List<TSRoleDto> roleList = null;
        if (StringUtils.isNotBlank(templateId)) {
            ProjTemplateDto p = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = p.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            templateId = template.getId();
            FeignJson fj = projTemplateRoleService.getTeamIdByTemplateId(templateId);
            String teamId = String.valueOf(fj.getObj());
            roleList= roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
        }
        List<String> authActionCodeList = ProjLibAuthManager.getAllAuthActionCode();
        Map<String, String> map = ProjectLibraryAuthorityEnum.getAuthNameCodeMap();
        List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
        List<RepFileAuthVo> rList = new ArrayList<RepFileAuthVo>();
        for (TSRoleDto role : roleList) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setId(role.getId());
            r.setFileId(fileId);
            r.setRoleId(role.getId());
            r.setRoleCode(role.getRoleCode());
            r.setRoleName(role.getRoleName());
            repList.add(r);
        }

        for (String code : authActionCodeList) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setCheckName(map.get(code));
            r.setCheckValue(code);
            rList.add(r);
        }
        String checks = "";
        List<RepRoleFileAuthDto> roleFileAuthsList = repService.getRepRoleFileAuthList(fileId);
        if(!projTemplateLibService.judgeAndAddValidRoleAuthSize(roleFileAuthsList)){
            roleFileAuthsList = repService.getRepRoleFileAuthList(fileId);
        }
        List<ProjLibRoleFileAuthVo> plrfVoList = projTemplateLibService.convertProjTemplateRoleFileAuthsVO(roleFileAuthsList);
        if (!CommonUtil.isEmpty(plrfVoList)) {
            if(!CommonUtil.isEmpty(repList)){
                for(RepFileAuthVo auth :repList ){
                    for (ProjLibRoleFileAuthVo p : plrfVoList) {
                        if(p.getRoleId().equals(auth.getRoleId())){
                            for (String code : authActionCodeList) {
                                checks = checks + p.getAuthMap().get(code) + ",";
                            }
                        }

                    }
                }
            }

        }
        else {
            for (TSRoleDto role : roleList) {
                for (String code : authActionCodeList) {
                    checks = checks + "false" + ",";
                }
            }
        }
        request.setAttribute("isView", isView);
        request.setAttribute("splitFlag", authActionCodeList.size());
        request.setAttribute("fileId", fileId);
        request.setAttribute("templateId", templateId);
        request.setAttribute("checks", checks);
        mv.addObject("docc", rList);
        mv.addObject("docs", repList);
        return mv;
    }


    /**
     * 项目模板项目库权限菜单树
     *
     * @return
     */
    @RequestMapping(params = "getProjLibTree")
    public void getProjLibTree(HttpServletRequest req, HttpServletResponse resp) {

        String templateId = req.getParameter("templateId");
        List<TreeNode> list = new ArrayList<TreeNode>();
        if (StringUtils.isNotEmpty(templateId)) {
            ProjTemplateDto template2 = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = template2.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            if (null != template) {
                req.setAttribute("templateId", template.getId());
                List<RepFileDto> files = projTemplateService.getFolderTree(template.getId(),"","");

                for (RepFileDto  file : files) {
                    TreeNode menu =null;
                    if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                        menu = new TreeNode(file.getId(), null,
                                file.getFileName(), file.getFileName(), true);
                    }
                    else {
                        menu = new TreeNode(file.getId(),
                                file.getParentId(), file.getFileName(),
                                file.getFileName(), true);

                    }
                    menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
                    menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                    menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                    menu.setOpen(true);
                    menu.setDataObject(file);
                    list.add(menu);
                }

            }
        }else{
            // 项目模板创建成功才可以切换项目选项时，可以删除此分支
            TreeNode menu = new TreeNode("001",
                    null, "通用项目模板",
                    "通用项目模板", true);
            menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
            menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setOpen(true);
            menu.setDataObject(new RepFileDto());
            list.add(menu);
        }
        String json = JSONArray.toJSONString(list);
        TagUtil.ajaxResponse(resp, json);
    }


    /**
     * 项目模板项目库跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplateLibAdd")
    public ModelAndView goTemplateLibAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        request.setAttribute("method", request.getParameter("method"));
        if(StringUtils.isNotBlank(templateId)){

            ProjTemplateDto template2 = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = template2.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            request.setAttribute("templateId", template.getId());
            ProjTmplTeamLinkDto projTmplTeamLink=new ProjTmplTeamLinkDto();
            projTmplTeamLink.setProjTemplateId(template.getId());
            FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(template.getId());
            String libId = String.valueOf(fj.getObj());
            List<RepFileDto> roots = repService.getRootDirsByParams(ResourceUtil.getApplicationInformation().getAppKey(),libId,"0",libId);
            String rootId="";
            if (!CommonUtil.isEmpty(roots) && roots.size() == 1) {
                rootId = roots.get(0).getId();

            }
            request.setAttribute("rootId", rootId);
        }
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateLibAdd");
    }


    /**
     * 新增文件夹
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "finally"})
    @RequestMapping(params = "doAddFolder")
    @ResponseBody
    public AjaxJson doAddFolder(HttpServletRequest req, HttpServletResponse resp) {
        AjaxJson j = new AjaxJson();
        String message = "";
        String id = req.getParameter("id");
        String parentId = req.getParameter("parentId");
        String type = req.getParameter("type");
        String fileName = req.getParameter("fileName");
        String templateId = req.getParameter("templateId");
        TreeNode menu = new TreeNode();
        try {
            if(StringUtils.isNotBlank(id)){ // update folder
                RepFileDto folder = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
                folder.setFileName(fileName);
                repService.updateRepFileById(ResourceUtil.getApplicationInformation().getAppKey(), folder);
                String pid = folder.getParentId();
                menu.setId(id);
                menu.setPid(pid);
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.updateSuccess");
                j.setSuccess(true);
            }else {
                RepFileDto folder = new RepFileDto();
                folder.setParentId(parentId);
                folder.setFileType(Integer.valueOf(type));
                folder.setFileName(fileName);

                ProjTemplateDto p = projTemplateService.getProjTemplateEntity(templateId);
                String bizId = p.getBizId();
                if(CommonUtil.isEmpty(bizId)){
                    bizId = "";
                }
                ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
                FeignJson fj =projTemplateService.getLibIdByTemplateId(template.getId());
                String libId = String.valueOf(fj.getObj());
                folder.setLibId(libId);
                folder.setSecurityLevel(Short.valueOf("1"));
               /* folder.setFileTypeId("");*/
                FeignJson res = repService.addRepFile(ResourceUtil.getApplicationInformation().getAppKey(),folder,ResourceUtil.getCurrentUser().getId());
                String fileid = String.valueOf(res.getObj());
                menu.setId(fileid);
                menu.setPid(parentId);
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.addSuccess");
                j.setSuccess(true);
            }
        }
        catch (NumberFormatException e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.operationOccurError");
            j.setSuccess(false);
            log.error(message, e, "", "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " json:" };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            menu.setTitle(fileName);
            menu.setName(fileName);
            menu.setOpen(true);
            menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
            menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setDataObject(new RepFileDto());
            j.setObj(menu);
            j.setMsg(message);
            return j;
        }

    }

    /**
     * 修稿文件夹
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "finally"})
    @RequestMapping(params = "doUpdateFolder")
    @ResponseBody
    public AjaxJson doUpdateFolder(HttpServletRequest req, HttpServletResponse resp) {
        AjaxJson j = new AjaxJson();
        String message = "";
        String id = req.getParameter("id");
        String fileName = req.getParameter("fileName");
        TreeNode menu = new TreeNode();
        try {
            if(StringUtils.isNotBlank(id)){ // update folder
                RepFileDto folder = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
                folder.setFileName(fileName);
                repService.updateRepFileById(ResourceUtil.getApplicationInformation().getAppKey(), folder);
                String pid = folder.getParentId();
                menu.setId(id);
                menu.setPid(pid);
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.updateSuccess");
                j.setSuccess(true);
            }else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.notObtainPleaseTryAgin");
                j.setSuccess(false);
            }
        }
        catch (NumberFormatException e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folder.operationOccurError");
            j.setSuccess(false);
            log.error(message, e, "", "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " json:" };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            menu.setTitle(fileName);
            menu.setName(fileName);
            menu.setOpen(true);
            menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
            menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            menu.setDataObject(new RepFileDto());
            j.setObj(menu);
            j.setMsg(message);
            return j;
        }

    }


    /**
     * 权限管理
     *
     * @return
     */
    @RequestMapping(params = "goPower")
    public ModelAndView goPower(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("com/glaway/ids/project/projecttemplate/projLibPower");
        String templateId = req.getParameter("templateId");

        if (StringUtils.isNotEmpty(templateId)) {
            ProjTemplateDto p = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = p.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            templateId = template.getId();
            List<RepFileDto> files = projLibService.getFolderTree(templateId,"","");
            for (RepFileDto file : files) {
                if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                    req.setAttribute("url",
                            "projTemplateController.do?goPowerList&fileId=" + file.getId()
                                    + "&templateId=" + templateId + "&id=" + file.getId());
                    req.setAttribute("fileIdRm", file.getId());
                    break;
                }
            }
        }
        req.setAttribute("templateId", templateId);
        req.setAttribute("libTmpId", "");
        List<ProjTmpLibAuthLibTmpLinkDto> links = projTemplateService.getProjTmpLibAuthLibTmpLinkByTemplateId(templateId);
        List<ProjectLibTemplateDto> projectLibTemplateList = projectLibTemplateService.getAllUseProjectLibTemplate();
        if (!CommonUtil.isEmpty(links)) {
            for (ProjectLibTemplateDto p : projectLibTemplateList) {
                if (p.getId().equals(links.get(0).getLibTmpId())) {
                    req.setAttribute("libTmpId", links.get(0).getLibTmpId());
                }
            }
        }
        return mv;
    }


    /**
     * 切换时判断是否保存
     *
     * @param req
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "checkSave")
    @ResponseBody
    public AjaxJson checkSave(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String checks = req.getParameter("checks");
        String fileId = req.getParameter("fileId");
        String templateId = req.getParameter("templateId");
        List<TSRoleDto> roles = null;
        if (StringUtils.isNotBlank(templateId)) {
            ProjTemplateDto pp = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = pp.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            templateId = template.getId();
            FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
            String teamId = String.valueOf(fj.getObj());
            roles=roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
            List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
            List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
            for (int i = 0; i < roles.size(); i++ ) {
                RepFileAuthVo p = new RepFileAuthVo();
                p.setFileId(fileId);
                p.setRoleId(roles.get(i).getId());
                String permissionCode = "";
                if(!CommonUtil.isEmpty(checks)){
                    permissionCode =  checks.substring(codeList.size() * i, codeList.size() * i
                            + codeList.size());
                }

                p.setCheckValue(permissionCode);
                repList.add(p);
            }
            if (projLibService.checkRoleFileAuthExistChange(fileId, repList)) {
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.isSaveAuth"));
                j.setSuccess(false);
            }
            else {
                j.setSuccess(true);
            }
        }
        return j;
    }


    /**
     * 保存项目模板 设置的项目库目录角色权限
     *
     * @param req
     * @param rep
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSavePower")
    @ResponseBody
    public AjaxJson doSavePower(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveAuthSuccess");
        try {
            String checks = req.getParameter("checks");
            String fileId = req.getParameter("fileId");
            String templateId = req.getParameter("templateId");
            if(StringUtils.isNotBlank(templateId)) {
                ProjTemplateDto pp = projTemplateService.getProjTemplateEntity(templateId);
                String bizId = pp.getBizId();
                if(CommonUtil.isEmpty(bizId)){
                    bizId = "";
                }
                ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
                templateId = template.getId();
                FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());
                List<TSRoleDto> roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
                List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
                List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
                for (int i = 0; i < roles.size(); i++ ) {
                    RepFileAuthVo p = new RepFileAuthVo();
                    p.setFileId(fileId);
                    p.setRoleId(roles.get(i).getId());
                    String permissionCode = checks.substring(codeList.size() * i, codeList.size() * i
                            + codeList.size());
                    p.setCheckValue(permissionCode);
                    repList.add(p);
                }
                projLibService.saveProjLibRoleFileAuth(fileId, repList,ResourceUtil.getCurrentUser().getId());
                j.setSuccess(true);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveAuthFailure");
            j.setSuccess(false);
            String fileId = "";
            log.error(message, e);
            Object[] params = new Object[] {message, fileId.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目库模板目录角色权限应用到项目模板的项目库目录角色权限中
     *
     * @param req
     * @param rep
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "applyTemplete")
    @ResponseBody
    public AjaxJson applyTemplete(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.useAuthSuccess");
        try {
            String templateId = req.getParameter("templateId");
            String libId = req.getParameter("libId");

            projTemplateLibService.applyTemplete(templateId,libId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.useAuthFailure");
            String fileId = "";
            log.error(message, e, fileId, fileId.toString());
            Object[] params = new Object[] {message, fileId.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增项目模板 团队列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjTemplateTeamAdd")
    public ModelAndView goProjTemplateTeamAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        request.setAttribute("method", request.getParameter("method"));
        if(StringUtils.isNotBlank(templateId))
        {
            ProjTemplateDto p = projTemplateService.getProjTemplateEntity(templateId);
            String bizId = p.getBizId();
            if(CommonUtil.isEmpty(bizId)){
                bizId = "";
            }
            ProjTemplateDto template = projTemplateService.getProjTemplateByBizId(bizId);
            templateId = template.getId();
        }else{

        }
        request.setAttribute("templateId", templateId);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateTeamAdd");
    }


    /**
     * 获得项目模板下的角色列表
     *
     * @return
     * @see
     */
    @RequestMapping(params = "refreshTeamList")
    @ResponseBody
    public AjaxJson refreshTeamList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String templateId = request.getParameter("templateId");
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        TSRoleDto tsRoleDto = roleService.getRoleByRoleCode("manager");
        if(StringUtils.isNotBlank(templateId)){
            FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
            String teamId = String.valueOf(fj.getObj());
            List<TSRoleDto> roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
            if(CommonUtil.isEmpty(roles)){
                TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
                JSONObject root = new JSONObject();
                root.put("id", role.getId());
                JSONObject treeNode = new JSONObject();
                treeNode.put("value", role.getRoleName());
                treeNode.put("image", "person.png");
                root.put("roleName", treeNode);

                root.put("roleCode",
                        role.getRoleCode() != null ? role.getRoleCode() : "");

                root.put("iconCls", "basis ui-icon-person");
                rootList.add(root);
            }else{  // 初始新增时默认角色为项目经理
                for(TSRoleDto role: roles)
                {
                    JSONObject root = new JSONObject();
                    root.put("id", role.getId());
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", role.getRoleName());
                    treeNode.put("image", "person.png");
                    root.put("roleName", treeNode);
                    root.put("roleCode",
                            role.getRoleCode() != null ? role.getRoleCode() : "");
                    root.put("iconCls", "basis ui-icon-person");
                    rootList.add(root);
                }
            }
        }else{
            TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
            JSONObject root = new JSONObject();
            root.put("id", role.getId());
            JSONObject treeNode = new JSONObject();
            treeNode.put("value", role.getRoleName());
            treeNode.put("image", "person.png");
            root.put("roleName", treeNode);

            root.put("roleCode",
                    role.getRoleCode() != null ? role.getRoleCode() : "");

            root.put("iconCls", "basis ui-icon-person");
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }

    /**
     * 获得项目模板下的角色列表
     *
     * @return
     * @see
     */
    @RequestMapping(params = "refreshTeamListFromSession")
    @ResponseBody
    public AjaxJson refreshTeamListFromSession(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String templateId = request.getParameter("templateId");
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        TSRoleDto tsRoleDto = roleService.getRoleByRoleCode("manager");
        if(StringUtils.isNotBlank(templateId)){
          /*  FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
            String teamId = String.valueOf(fj.getObj());*/
            List<TSRoleDto> roles = (List<TSRoleDto>) request.getSession().getAttribute("projTemplateRoleList");
            if(CommonUtil.isEmpty(roles)){
                TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
                JSONObject root = new JSONObject();
                root.put("id", role.getId());
                JSONObject treeNode = new JSONObject();
                treeNode.put("value", role.getRoleName());
                treeNode.put("image", "person.png");
                root.put("roleName", treeNode);

                root.put("roleCode",
                        role.getRoleCode() != null ? role.getRoleCode() : "");

                root.put("iconCls", "basis ui-icon-person");
                rootList.add(root);
            }else{  // 初始新增时默认角色为项目经理
                for(TSRoleDto role: roles)
                {
                    JSONObject root = new JSONObject();
                    root.put("id", role.getId());
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", role.getRoleName());
                    treeNode.put("image", "person.png");
                    root.put("roleName", treeNode);
                    root.put("roleCode",
                            role.getRoleCode() != null ? role.getRoleCode() : "");
                    root.put("iconCls", "basis ui-icon-person");
                    rootList.add(root);
                }
            }
        }else{
            TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
            JSONObject root = new JSONObject();
            root.put("id", role.getId());
            JSONObject treeNode = new JSONObject();
            treeNode.put("value", role.getRoleName());
            treeNode.put("image", "person.png");
            root.put("roleName", treeNode);

            root.put("roleCode",
                    role.getRoleCode() != null ? role.getRoleCode() : "");

            root.put("iconCls", "basis ui-icon-person");
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }


    /**
     * 添加项目模板角色
     *
     * @return
     */
    @SuppressWarnings("finally")
    /*@RequestMapping(params = "doBatchAdd")
    @ResponseBody
    public AjaxJson doBatchAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        String codes = request.getParameter("roles");
        List<String> roleIds = JSONArray.parseArray(codes, String.class);
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addSuccess");

        try {
            if(StringUtils.isBlank(templateId)){
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
                j.setSuccess(false);
            }else{
                FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());
                List<TSRoleDto> roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
                String containRoleIdS="manager";
                if(!CommonUtil.isEmpty(roles)){
                    for(TSRoleDto role: roles)
                    {
                        containRoleIdS+=","+role.getRoleCode();
                    }}
                for(String code: roleIds){
                    log.info(message, codes, "");
                    //团队角色已经保存 无需重复保存
                    if(containRoleIdS.contains(code))
                        continue;
                    projRoleService.addTeamRoleByCode(teamId, code);
                }

                //新增角色  如果没有选中的角色 除了项目经理之外  删除没有选择的角色
                List<TSRoleDto> list = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
                List<FdTeamRoleDto> fdTeamRoleDtoList = new ArrayList<>();
                if(list!=null&&list.size()>0)
                {
                    for (TSRoleDto tsRole : list)
                    {
                        //判断新增  团队的角色除了项目经理之外  有无没不需要添加的角色  有删除团队中的角色
                        if(!codes.contains(tsRole.getRoleCode())&&!"manager".equals(tsRole.getRoleCode()))
                        {
                            FdTeamRoleDto roleDto = new FdTeamRoleDto();
                            roleDto.setTeamId(teamId);
                            roleDto.setRoleId(tsRole.getId());
                            fdTeamRoleDtoList.add(roleDto);
                        }
                    }
                }
                teamService.deleteFdTeamRoleByTeamIdAndRoleId(fdTeamRoleDtoList);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
            log.error(message, e, "", codes);
            Object[] params = new Object[] {message, codes};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }*/
    /**
     * 添加项目模板角色
     *
     * @return
     */
    @RequestMapping(params = "doBatchAdd")
    @ResponseBody
    public AjaxJson doBatchAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        String codes = request.getParameter("roles");
        List<String> roleIds = JSONArray.parseArray(codes, String.class);
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addSuccess");

        try {
            if(StringUtils.isBlank(templateId)){
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
                j.setSuccess(false);
            }else{
           /*     FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());*/
                List<TSRoleDto> roles = (List<TSRoleDto>)request.getSession().getAttribute("projTemplateRoleList");
                String containRoleIdS="manager";
                if(!CommonUtil.isEmpty(roles)){
                    for(TSRoleDto role: roles)
                    {
                        containRoleIdS+=","+role.getRoleCode();
                    }}
                for(String code: roleIds){
                    //团队角色已经保存 无需重复保存
                    if(!containRoleIdS.contains(code)){
                        TSRoleDto roleDto = roleService.getRoleByRoleCode(code);
                        roles.add(roleDto);
                        /*projRoleService.addTeamRoleByCode(teamId, code);*/
                    }
                    log.info(message, codes, "");
                }
                request.getSession().setAttribute("projTemplateRoleList",roles);
                j.setSuccess(true);
                //新增角色  如果没有选中的角色 除了项目经理之外  删除没有选择的角色
               /* List<TSRoleDto> list = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
                List<FdTeamRoleDto> fdTeamRoleDtoList = new ArrayList<>();
                if(list!=null&&list.size()>0)
                {
                    for (TSRoleDto tsRole : list)
                    {
                        //判断新增  团队的角色除了项目经理之外  有无没不需要添加的角色  有删除团队中的角色
                        if(!codes.contains(tsRole.getRoleCode())&&!"manager".equals(tsRole.getRoleCode()))
                        {
                            FdTeamRoleDto roleDto = new FdTeamRoleDto();
                            roleDto.setTeamId(teamId);
                            roleDto.setRoleId(tsRole.getId());
                            fdTeamRoleDtoList.add(roleDto);
                        }
                    }
                }
                teamService.deleteFdTeamRoleByTeamIdAndRoleId(fdTeamRoleDtoList);*/
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
            log.error(message, e, "", codes);
            Object[] params = new Object[] {message, codes};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 获取已被选中的角色
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getUsedRolesList")
    @ResponseBody
    public AjaxJson getUsedRolesList(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        String templateId = req.getParameter("templateId");
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateSuccess");

        try {
            if(StringUtils.isNotBlank(templateId)){
                FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());
                List<TSRoleDto> roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
                String obj = "";
                for (TSRoleDto role : roles) {
                    obj = obj + role.getId() + ",";
                }
                if (null != obj && !"".equals(obj)) {
                    obj = obj.substring(0, obj.length() - 1);
                }
                j.setObj(obj);
                j.setSuccess(true);

            }else{
                TSRoleDto tsRoleDto = roleService.getRoleByRoleCode("manager");
                TSRoleDto manager = roleService.getRoleByRoleId(tsRoleDto.getId());
                j.setObj(manager.getId());
                j.setSuccess(true);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateFailure");
            log.error(message, e);
            Object[] params = new Object[] {message, templateId};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目模板名称重复校验
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
   /* @RequestMapping(params = "doBatchDelRole")
    @ResponseBody
    public AjaxJson doBatchDelRole(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteRoleSuccess");
        String templateId=request.getParameter("templateId");
        String roles=request.getParameter("roles");
        String[] roleIds = roles.split(",");
        try {
            if(!CommonUtil.isEmpty(roles)){
                FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());
                List<FdTeamRoleDto> fdTeamRoleDtoList = new ArrayList<FdTeamRoleDto>();
                for(String id : roleIds){
                    FdTeamRoleDto roleDto = new FdTeamRoleDto();
                    roleDto.setTeamId(teamId);
                    roleDto.setRoleId(id);
                    fdTeamRoleDtoList.add(roleDto);
                }
                teamService.deleteFdTeamRoleByTeamIdAndRoleId(fdTeamRoleDtoList);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteRoleException");
            log.error(message, e);
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }
    }*/
    /**
     * 项目模板名称重复校验
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doBatchDelRole")
    @ResponseBody
    public AjaxJson doBatchDelRole(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteRoleSuccess");
        String templateId=request.getParameter("templateId");
        String roles=request.getParameter("roles");
        String[] roleIds = roles.split(",");
        try {
            if(!CommonUtil.isEmpty(roles)){

                List<TSRoleDto> roleList = (List<TSRoleDto>) request.getSession().getAttribute("projTemplateRoleList");
                for(String roleId : roleIds){
                    for(TSRoleDto role : roleList){
                        if(roleId.equals(role.getId())){
                            roleList.remove(role);
                            break;
                        }
                    }
                }

                request.getSession().setAttribute("projTemplateRoleList",roleList);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.projecttemplate.deleteRoleException");
            log.error(message, e);
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 获得项目模板下的角色列表
     *
     * @return
     * @see
     */
    @RequestMapping(params = "getTeamList")
    public void getTeamList(HttpServletRequest request, HttpServletResponse response) {
        String templateId = request.getParameter("templateId");
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        TSRoleDto tsRoleDto = roleService.getRoleByRoleCode("manager");
        if(StringUtils.isNotBlank(templateId)){
            FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
            String teamId = String.valueOf(fj.getObj());
            List<TSRoleDto> roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
            if(CommonUtil.isEmpty(roles)){
                TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
                JSONObject root = new JSONObject();
                root.put("id", role.getId());
                JSONObject treeNode = new JSONObject();
                treeNode.put("value", role.getRoleName());
                treeNode.put("image", "person.png");
                root.put("roleName", treeNode);

                root.put("roleCode",
                        role.getRoleCode() != null ? role.getRoleCode() : "");

                root.put("iconCls", "basis ui-icon-person");
                rootList.add(root);
            }else{  // 初始新增时默认角色为项目经理
                for(TSRoleDto role: roles)
                {
                    JSONObject root = new JSONObject();
                    root.put("id", role.getId());
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", role.getRoleName());
                    treeNode.put("image", "person.png");
                    root.put("roleName", treeNode);
                    root.put("roleCode",
                            role.getRoleCode() != null ? role.getRoleCode() : "");
                    root.put("iconCls", "basis ui-icon-person");
                    rootList.add(root);
                }
            }
        }else{
            TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
            JSONObject root = new JSONObject();
            root.put("id", role.getId());
            JSONObject treeNode = new JSONObject();
            treeNode.put("value", role.getRoleName());
            treeNode.put("image", "person.png");
            root.put("roleName", treeNode);
            root.put("roleCode",
                    role.getRoleCode() != null ? role.getRoleCode() : "");
            root.put("iconCls", "basis ui-icon-person");
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得项目模板下的角色列表
     *
     * @return
     * @see
     */
    @RequestMapping(params = "getTeamListFromSession")
    public void getTeamListFromSession(HttpServletRequest request, HttpServletResponse response) {
        String templateId = request.getParameter("templateId");
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        TSRoleDto tsRoleDto = roleService.getRoleByRoleCode("manager");
        if(StringUtils.isNotBlank(templateId)){
            List<TSRoleDto> roles = new ArrayList<>();
            roles = (List<TSRoleDto>)request.getSession().getAttribute("projTemplateRoleList");
            if(CommonUtil.isEmpty(roles)){
                FeignJson fj =projTemplateRoleService.getTeamIdByTemplateId(templateId);
                String teamId = String.valueOf(fj.getObj());
                roles = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
            }
            if(CommonUtil.isEmpty(roles)){
                roles = new ArrayList<>();
            }
            request.getSession().setAttribute("projTemplateRoleList",roles);
         //   List<TSRoleDto> roles = (List<TSRoleDto>)request.getSession().getAttribute("projTemplateRoleList");
            if(CommonUtil.isEmpty(roles)){
                TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
                JSONObject root = new JSONObject();
                root.put("id", role.getId());
                JSONObject treeNode = new JSONObject();
                treeNode.put("value", role.getRoleName());
                treeNode.put("image", "person.png");
                root.put("roleName", treeNode);

                root.put("roleCode",
                        role.getRoleCode() != null ? role.getRoleCode() : "");

                root.put("iconCls", "basis ui-icon-person");
                rootList.add(root);
            }else{  // 初始新增时默认角色为项目经理
                for(TSRoleDto role: roles)
                {
                    JSONObject root = new JSONObject();
                    root.put("id", role.getId());
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", role.getRoleName());
                    treeNode.put("image", "person.png");
                    root.put("roleName", treeNode);
                    root.put("roleCode",
                            role.getRoleCode() != null ? role.getRoleCode() : "");
                    root.put("iconCls", "basis ui-icon-person");
                    rootList.add(root);
                }
            }
        }else{
            TSRoleDto role = roleService.getRoleByRoleId(tsRoleDto.getId());
            JSONObject root = new JSONObject();
            root.put("id", role.getId());
            JSONObject treeNode = new JSONObject();
            treeNode.put("value", role.getRoleName());
            treeNode.put("image", "person.png");
            root.put("roleName", treeNode);
            root.put("roleCode",
                    role.getRoleCode() != null ? role.getRoleCode() : "");
            root.put("iconCls", "basis ui-icon-person");
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 项目模板 修改计划跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplatePlanAdd")
    public ModelAndView goTemplatePlanAdd(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        request.setAttribute("method", request.getParameter("method"));
        ProjTemplateDto template = new ProjTemplateDto();
        if(!CommonUtil.isEmpty(templateId) && !"undefined".equals(templateId))
        {
            template = projTemplateService.getProjTemplateEntity(templateId);
            request.setAttribute("projectTemplate", template);
        }
        String bizId = template.getBizId();
        if(CommonUtil.isEmpty(bizId)){
            bizId = "";
        }
        ProjTemplateDto projTemplate2 = projTemplateService.getProjTemplateByBizId(bizId);
        String newTemplateId = projTemplate2.getId();
        String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", templateId);
        if(CommonUtil.isEmpty(detailStr)){
            if(CommonUtil.isEmpty(newTemplateId)){
                newTemplateId = "";
            }
            List<PlanDto> projTmplPlanList = projTemplateDetailService.convertPlanjTemplateDetail2Plan(newTemplateId);
            Collections.sort(projTmplPlanList, new Comparator<PlanDto>() {
                @Override
                public int compare(PlanDto o1, PlanDto o2) {
                    return o1.getStoreyNo().compareTo(o2.getStoreyNo());
                }});
            String delStr = JSON.toJSONString(projTmplPlanList);
            redisService.setToRedis("PROJTMPPLANLIST", templateId, delStr);
        }

        // 和计划模板共用一个计划页面，type用于区分来源是 计划模板还是项目模板
        request.setAttribute("projectTemplateId", newTemplateId);
        request.setAttribute("type", "projtemplate");
        request.setAttribute("url", "projTemplateDetailController.do?getPlanList");
        return new ModelAndView("com/glaway/ids/project/plan/planTemplateAdd");
    }


    /**
     * 检查项目模板新增目录，目录名是否已经存在
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkFileName")
    @ResponseBody
    public AjaxJson checkFileName(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameNoRepeat");
        String templateId = request.getParameter("templateId");
        String fileName = request.getParameter("fileName");

        try {
            if(StringUtils.isNotBlank(fileName)){
                if(StringUtils.isNotBlank(templateId))
                {
                    if(projTemplateLibService.isFileNameRepeat(templateId, fileName)) {
                        message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folderNameExists");
                        j.setSuccess(false);
                    } else {
                        message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folderNameNotRepeat");
                        j.setSuccess(true);
                    }
                } else {
                    j.setSuccess(false);
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.notExists");
                }

            }else{
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folderNameCannotEmpty");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.folderNameQueryFail");
            log.error(message, e, "", "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goSubmitApprove")
    public ModelAndView goSubmitApprove(HttpServletRequest req) {
        ProjTemplateDto t = projTemplateService.getProjTemplateEntity(req.getParameter("projTmpId"));
        String tmpId = t.getId();
        req.setAttribute("tmpId", tmpId);
        req.setAttribute("supportFlag", "approve");
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplate-submitApprove");
    }


    /**
     * 提交审批
     *
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSubmitApprove")
    @ResponseBody
    public AjaxJson doSubmitApprove(HttpServletRequest req, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projTmpId = req.getParameter("projTmpId");
        String leader = req.getParameter("leader");
        String deptLeader = req.getParameter("deptLeader");

        message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveSuccess");
        try{

            projTemplateService.startProjTemplateProcess(projTmpId,leader,deptLeader,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());

            j.setObj(projTmpId);
            log.info(message);

        }catch(Exception e){
            System.out.println(e.getMessage());
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveFail");
            log.error(message, e, projTmpId, "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " oids:" + projTmpId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            return j;
        }
    }

    /**
     * 驳回提交审批
     *
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBackSubmit")
    @ResponseBody
    public AjaxJson doBackSubmit(HttpServletRequest req, HttpServletResponse response) {
        String taskId = req.getParameter("taskId");
        String projTmpId = req.getParameter("projTmpId");
        ProjTemplateDto projTemplate = new ProjTemplateDto();
        if(CommonUtil.isEmpty(projTmpId)){
            projTmpId = "";
        }
        projTemplate = projTemplateService.getProjTemplateEntity(projTmpId);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projtemplate.projtemplate.doSubmitApproveSuccess");
        try {
            projTemplateService.completeProjTemplateProcess(projTemplate, ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            j.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projtemplate.projtemplate.doSubmitApproveFail");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目模板新增页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goSaveAsTemplate")
    public ModelAndView goSaveAsTemplate(HttpServletRequest request) {
        ProjTemplateDto projectTemplate = new ProjTemplateDto();
        String projectId = request.getParameter("id");
        TSUserDto user = UserUtil.getInstance().getUser();
        projectTemplate.setCreateName(user.getRealName() + "-" + user.getUserName());
        projectTemplate.setCreateTime(new Date());
        request.setAttribute("projectTemplate", projectTemplate);
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateSaveAs");
    }


    /**
     * 项目模板名称重复校验
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "vidateRepeat")
    @ResponseBody
    public AjaxJson vidateRepeat(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String name=request.getParameter("name");
        try {
            List<ProjTemplateDto> list =new ArrayList<ProjTemplateDto>();
            ProjTemplateDto searchTemplate=new ProjTemplateDto();
            searchTemplate.setProjTmplName(projTemplate.getProjTmplName());
            if(StringUtils.isNotBlank(name)){
                searchTemplate.setProjTmplName(name);
            }
            list = projTemplateService.searchProjTemplate(searchTemplate);

            if(projTemplate.getId()==null){
                if(!CommonUtil.isEmpty(list)){
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameRepeat");
                    j.setSuccess(false);
                }
            }
            else{
                if(!CommonUtil.isEmpty(list)){
                    if(list.size()==1){
                        ProjTemplateDto template = list.get(0);
                        if(!StringUtils.equals(template.getId(), projTemplate.getId())){
                            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameRepeat");
                            j.setSuccess(false);
                        }
                    }
                    else{
                        message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameRepeat");
                        j.setSuccess(false);
                    }
                }

            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.nameQueryFail");
            log.error(message, e);
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally{
            j.setMsg(message);
            return j;
        }

    }


    /**
     * 项目模板新增保存
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSaveAsTemplate")
    @ResponseBody
    public AjaxJson doSaveAsTemplate(ProjTemplateDto projTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveAsSuccess");
        String templateId = "";
        String projectId = request.getParameter("projectId");
        Project project=  projectService.getProjectEntity(projectId);
        List<String> plans = projTemplate.getSavePlans();
        List<String> teams = projTemplate.getSaveTeams();
        List<String> libs = projTemplate.getSaveLibs();
        List<String> libPower = projTemplate.getSaveLibPower();
        try {
            if(plans==null && teams==null && libs==null && null==projTemplate.getSaveLibPower()){
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projecttemplate.saveAsSelectOne");
                j.setSuccess(false);
            }
            else {
                Project p= new Project();
                BeanUtil.copyBeanNotNull2Bean(project, p);
                Map<String,Object> map = new HashMap<>();
                map.put("projTemplate",projTemplate);
                map.put("project",project);
                map.put("plans",plans);
                map.put("teams",teams);
                map.put("libs",libs);
                map.put("libPower",libPower);
                map.put("curUser",ResourceUtil.getCurrentUser());
                map.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
                map.put("projectId",projectId);
                FeignJson fj= projTemplateService.SaveAsTemplate(map);
                templateId = String.valueOf(fj.getObj());
            }
        }
        catch (Exception e) {
            message = e.getMessage();
            log.error(message, e, projectId, "");
            Object[] params = new Object[] {message, ProjTemplateDto.class.getClass() + " projectId:" + projectId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setObj(templateId);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增文件夹
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelFolder")
    @ResponseBody
    public AjaxJson doDelFolder(HttpServletRequest req, HttpServletResponse resp) {
        AjaxJson j = new AjaxJson();
        String message = "";
        String idsStr = req.getParameter("ids");
       /* idsStr = idsStr.substring(1,idsStr.length()-1);*/
        String folderId = JSONUtils.parse(idsStr).toString();
        boolean isRootFolder = projLibService.isRootFolder(folderId);
        try {
            if (isRootFolder) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.rootCannotDelete");
                j.setSuccess(false);
            }else{
                // 删除目录文件夹 和 对应的角色权限关系
                boolean isSuccess =  projLibService.delFileAndAuthById(folderId);
                if (!isSuccess) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.fullFileCannotDelete");
                    j.setSuccess(false);
                }else{
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteFileSuccess");
                    j.setSuccess(true);
                }
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteFileFailure");
            j.setSuccess(false);
            log.error(message, e, "", "");
            Object[] params = new Object[] {message,
                    ProjTemplateDto.class.getClass() + " json:" };// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            j.setMsg(message);
            return j;
        }

    }

    @RequestMapping(params = "doImportExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doImportExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectTemplateId = request.getParameter("projectTemplateId");
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importExcel.success");
        try {
            String typeName = PlantemplateConstant.TEMPLATE_EXPORT_NAME;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                //存储导入的数据
                List<PlanTemplateExcelVo> dataTempList = new ArrayList<PlanTemplateExcelVo>();
                //存储错误信息
                Map<String, String> errorMsgMap = new HashMap<String, String>();
                // 获取上传文件对象
                MultipartFile file = entity.getValue();
                InputStream is = file.getInputStream();
                Workbook book = WorkbookFactory.create(is);
                Sheet sheet = book.getSheetAt(0);
                List<String> headers = Arrays.asList(new String[]{"序号", "父计划序号", "计划名称", "计划类型", "计划等级",
                        "参考工期（天）", "里程碑", "是否必要", "前置计划序号", "交付项名称"});
                if (!POIExcelUtil.doJudgeImport(sheet, headers)) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.errorTemplate")+ ";");
                    j.setSuccess(false);
                    log.error(typeName + "：导入失败，模板有问题");
                    book.close();
                    is.close();
                    return j;
                }
                else{
                    try{
                        //getImportDataList(sheet, dataTempList, errorMsgMap, projectTemplateId);
                        List<Map<String,Object>> map= getDataList(sheet);
                        FeignJson fj = projTemplateService.getImportDataList(map,ResourceUtil.getCurrentUser().getId(), projectTemplateId,ResourceUtil.getCurrentUserOrg().getId());
                        Map<String,Object> returnMap = new HashMap<>();
                        if (fj.isSuccess()){
                            returnMap = (Map<String, Object>)fj.getObj();
                            errorMsgMap = (Map<String, String>)returnMap.get("errorMsgMap");
                            dataTempList = (List<PlanTemplateExcelVo>)returnMap.get("dataTempList");
                        }
                    }
                    catch(Exception e){
                        j.setMsg(e.getMessage());
                        j.setSuccess(false);
                        e.printStackTrace();
                        return j;
                    }
                    if (0 < errorMsgMap.size()) {
                        j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.invalidData"));
                        j.setSuccess(false);
                        log.error(typeName + "：批量导入失败，部分数据无效");
                        //组装数据传到前台
                        Map<String, Object> dataAndErrorMap=new HashMap<String, Object>();
                        dataAndErrorMap.put("dataTempList", dataTempList);
                        dataAndErrorMap.put("errorMsgMap", errorMsgMap);
                        j.setObj(dataAndErrorMap);
                        book.close();
                        is.close();
                        return j;
                    }
                    else{
                        //成功返回模板ID
                        j.setObj(projectTemplateId);
                        log.info(message);
                    }
                }
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importFail");
            log.error(message, e);
            j.setMsg(e.getMessage());
            j.setSuccess(false);
            return j;
        }
        //校验成功
        j.setSuccess(true);
        j.setMsg(message);
        return j;
    }

    private void getImportDataList(Sheet sheet, List<PlanTemplateExcelVo> dataTempList, Map<String, String> errorMsgMap,
                                   String projectTemplateId) {
        // 获取当前系统是否启用标准名称库
        FeignJson jsonF = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(jsonF.getObj());
        boolean isStandard = false;
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                || NameStandardSwitchConstants.FORCEUSENAMESTANDARD
                .equals(switchStr)
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE
                .equals(switchStr)) {
            isStandard = true;
        }
        Map<String, String> standardNameMap = new HashMap<String, String>();
        Map<String, String> deliveryNameMap = new HashMap<String, String>();
        if (isStandard) {
            NameStandardDto ns = new NameStandardDto();
            ns.setStopFlag(ConfigStateConstants.START);
            List<NameStandardDto> list = nameStandardService.searchNameStandardsAccurate(ns);
            for (NameStandardDto n : list) {
                standardNameMap.put(n.getName(), n.getActiveCategory());
            }

            DeliveryStandardDto ds = new DeliveryStandardDto();
            ds.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> list2 = deliveryStandardService.searchDeliveryStandardAccurate(ds);
            for (DeliveryStandardDto d : list2) {
                deliveryNameMap.put(d.getName(), d.getName());
            }
        }
        Iterator<Row> it = sheet.iterator();
        Map<String, String> planLevelMap = new HashMap<String, String>();
        BusinessConfig businessConfig = new BusinessConfig();
        businessConfig.setConfigType(ConfigTypeConstants.PLANLEVEL);
        businessConfig.setStopFlag(ConfigStateConstants.START);
        businessConfig.setAvaliable("1");
        List<BusinessConfig> planLevelConfigs = businessConfigService.searchUseableBusinessConfigs(businessConfig);
   //     List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getName(), confog.getId());
        }
        // 用于校验和预设数据的保存
        Map<String, PlanTemplateExcelVo> excelMap = new HashMap<String, PlanTemplateExcelVo>();
        // 计划序号集合
        List<String> numList = new ArrayList<String>();
        while (it.hasNext()) {
            Row r = it.next();
            int rowNum = r.getRowNum();
            Row row = sheet.getRow(rowNum);
            if (rowNum >= 3) {
                Cell numberCell = row.getCell(0);
                Cell parentNumberCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                Cell taskNameTypeCell = row.getCell(3);
                Cell levelCell = row.getCell(4);
                Cell worktimeCell = row.getCell(5);
                Cell milestoneCell = row.getCell(6);
                Cell isNecessaryCell = row.getCell(7);
                Cell preposeNumberCell = row.getCell(8);
                Cell deliverNameCell = row.getCell(9);

                String number = POIExcelUtil.getCellValue(numberCell).trim();
                String parentNumber = POIExcelUtil.getCellValue(parentNumberCell).trim();
                String name = POIExcelUtil.getCellValue(nameCell).trim();
                String taskNameType = POIExcelUtil.getCellValue(taskNameTypeCell).trim();
                String level = POIExcelUtil.getCellValue(levelCell).trim();
                String worktime = POIExcelUtil.getCellValue(worktimeCell).trim();
                String milestone = POIExcelUtil.getCellValue(milestoneCell).trim();
                String isNecessary = POIExcelUtil.getCellValue(isNecessaryCell).trim();
                String preposeNumbers = POIExcelUtil.getCellValue(preposeNumberCell).trim();
                preposeNumbers = preposeNumbers.replace("，", ",");
                String deliverNames = POIExcelUtil.getCellValue(deliverNameCell).trim();
                deliverNames = deliverNames.replace("，", ",");

                String checkStr = number+";" + name + ";" + taskNameType + ";" + level + ";" + worktime + ";" + milestone + ";" +
                        deliverNames + ";" + rowNum;
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("rowNum",rowNum);
                paramMap.put("checkStr",checkStr);
                paramMap.put("switchStr",switchStr);
                paramMap.put("standardNameMap",standardNameMap);
                paramMap.put("errorMsgMap",errorMsgMap);
                paramMap.put("deliveryNameMap",deliveryNameMap);
                paramMap.put("numList",numList);
                paramMap.put("planLevelMap",planLevelMap);
                planTemplateService.checkData(paramMap);

                PlanTemplateExcelVo vo =  new PlanTemplateExcelVo();
                //计划ID
                vo.setId(UUIDGenerator.generate());
                vo.setPlanNumber(number);
                vo.setParentNo(parentNumber);
                vo.setPlanName(name);
                vo.setTaskNameType(taskNameType);
                vo.setPlanLevel(level);
                vo.setWorktime(worktime);
                vo.setMilestone(milestone);
                vo.setIsNecessary(isNecessary);
                vo.setPreposeNos(preposeNumbers);
                vo.setDeliverablesName(deliverNames);
                vo.setRowNum(rowNum);
                dataTempList.add(vo);
                excelMap.put(number, vo);
            }
        }
        // 若模板中的业务数据为空，则提示且不进行导入操作
        if (CommonUtil.isEmpty(dataTempList)) {
            throw new GWException(
                    I18nUtil.getValue("com.glaway.ids.common.importDataIsNull"));
        } else if (dataTempList.size() < 1) {
            throw new GWException(
                    I18nUtil.getValue("com.glaway.ids.pm.project.plan.importExcel.checkLeastOnePlan"));
        }
        //父计划,前置计划
        Map<String,Object> map = new HashMap<>();
        map.put("excelMap",excelMap);
        map.put("errorMsgMap",errorMsgMap);
        map.put("numList",numList);
        planTemplateService.checkData2(map);
        if (0 == errorMsgMap.size()) {
            //计划模板ID
            ProjTemplateDto projectTemplate = new ProjTemplateDto();
            projectTemplate.setId(projectTemplateId);
            Map<String,Object> objMap = new HashMap<>();
            objMap.put("projectTemplate",projectTemplate);
            objMap.put("dataTempList",dataTempList);
            objMap.put("excelMap",excelMap);
            objMap.put("planLevelMap",planLevelMap);
            objMap.put("switchStr",switchStr);
            objMap.put("curUser",ResourceUtil.getCurrentUser());
            objMap.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
            projTemplateService.saveProjectTemplateDetailByExcel(objMap);
        }
    }

    private List<Map<String,Object>> getDataList(Sheet sheet){
        List <Map<String,Object>> list = new ArrayList<>();
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row r = it.next();
            int rowNum = r.getRowNum();
            Row row = sheet.getRow(rowNum);
            if (rowNum >= 3) {
                Cell numberCell = row.getCell(0);
                Cell parentNumberCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                Cell taskNameTypeCell = row.getCell(3);
                Cell levelCell = row.getCell(4);
                Cell worktimeCell = row.getCell(5);
                Cell milestoneCell = row.getCell(6);
                Cell isNecessaryCell = row.getCell(7);
                Cell preposeNumberCell = row.getCell(8);
                Cell deliverNameCell = row.getCell(9);

                String number = POIExcelUtil.getCellValue(numberCell).trim();
                String parentNumber = POIExcelUtil.getCellValue(parentNumberCell).trim();
                String name = POIExcelUtil.getCellValue(nameCell).trim();
                String taskNameType = POIExcelUtil.getCellValue(taskNameTypeCell).trim();
                String level = POIExcelUtil.getCellValue(levelCell).trim();
                String worktime = POIExcelUtil.getCellValue(worktimeCell).trim();
                String milestone = POIExcelUtil.getCellValue(milestoneCell).trim();
                String isNecessary = POIExcelUtil.getCellValue(isNecessaryCell).trim();
                String preposeNumbers = POIExcelUtil.getCellValue(preposeNumberCell).trim();
                preposeNumbers = preposeNumbers.replace("，", ",");
                String deliverNames = POIExcelUtil.getCellValue(deliverNameCell).trim();
                deliverNames = deliverNames.replace("，", ",");

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("number", number);
                paramMap.put("rowNum", rowNum);
                paramMap.put("parentNumber", parentNumber);
                paramMap.put("taskNameType", taskNameType);
                paramMap.put("name", name);
                paramMap.put("worktime", worktime);
                paramMap.put("level",level);
                paramMap.put("milestone", milestone);
                paramMap.put("preposeNumbers", preposeNumbers);
                paramMap.put("deliverName", deliverNames);
                paramMap.put("isNecessary", isNecessary);
                list.add(paramMap);
            }
        }
        return list;
    }


    /**
     * 导出计划excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public void exportXls(PlanDto plan, HttpServletRequest request, HttpServletResponse response,
                          DataGrid dataGrid) {
        response.setContentType("application/vnd.ms-excel");
        String projectTemplateId = request.getParameter("projectTemplateId");
        ProjTemplateDto projTemplate = projTemplateService.getProjTemplateEntity(projectTemplateId);
        ProjTemplateDto projTemplate2 =projTemplateService.getProjTemplateByBizId(projTemplate.getBizId());

        List<PlanTemplateExcelVo> resultList = new ArrayList<PlanTemplateExcelVo>();
        Map<String, String> planLevelMap = new HashMap<String, String>();
        BusinessConfig businessConfig = new BusinessConfig();
        businessConfig.setConfigType(ConfigTypeConstants.PLANLEVEL);
        businessConfig.setStopFlag(ConfigStateConstants.START);
        businessConfig.setAvaliable("1");
        List<BusinessConfig> planLevelConfigs = businessConfigService.searchUseableBusinessConfigs(businessConfig);
     //   List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getId(), confog.getName());
        }
        Map<String, String> acticityTypaManagerMap = activityTypeManageFeign.getAllActivityTypeManageMap();
        try {
            //模板里面的计划ID-序号
            Map<String, String> map = new HashMap<String, String>();
            List<PlanDto> detailList = new ArrayList<>();
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projTemplate2.getId());
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
            if(!CommonUtil.isEmpty(detailList)) {
                for(PlanDto detail : detailList) {
                    map.put(detail.getId(), String.valueOf(detail.getPlanNumber()));
                }
                for(PlanDto detail : detailList) {
                    PlanTemplateExcelVo vo = new PlanTemplateExcelVo();
                    if(!CommonUtil.isEmpty(detail.getPlanNumber())) {
                        vo.setPlanNumber(String.valueOf(detail.getPlanNumber()));
                    }
                    if(!CommonUtil.isEmpty(detail.getParentPlanId())) {
                        vo.setParentNo(map.get(detail.getParentPlanId()));
                    }
                    vo.setPlanName(detail.getPlanName());
                    if(!CommonUtil.isEmpty(detail.getPlanLevel())) {
                        vo.setPlanLevel(planLevelMap.get(detail.getPlanLevel()));
                    }
                    vo.setWorktime(detail.getWorkTime());
                    if("true".equals(detail.getMilestone())) {
                        vo.setMilestone("是");
                    } else {
                        vo.setMilestone("否");
                    }
                    if("true".equals(detail.getIsNecessary())) {
                        vo.setIsNecessary("是");
                    } else {
                        vo.setIsNecessary("否");
                    }
                    String deliverablesName = "";
                    List<DeliverablesInfoDto> list = detail.getDeliInfoList();
                    for(DeliverablesInfoDto d : list) {
                        if (!CommonUtil.isEmpty(d)) {
                            if(!CommonUtil.isEmpty(deliverablesName)) {
                                deliverablesName = deliverablesName + "," + d.getName();
                            } else {
                                deliverablesName = d.getName();
                            }
                        }
                    }
                    if(!CommonUtil.isEmpty(deliverablesName)) {
                        vo.setDeliverablesName(deliverablesName);
                    }
                    String preposeNos = "";
                    if(!CommonUtil.isEmpty(detail.getPreposeIds())) {
                        String[] preposeIds = detail.getPreposeIds().split(",");
                        for(String preposeId : preposeIds) {
                            if(!CommonUtil.isEmpty(preposeNos)) {
                                preposeNos = preposeNos + "," + map.get(preposeId);
                            } else {
                                preposeNos = map.get(preposeId);
                            }
                        }
                    }

                    if (!CommonUtil.isEmpty(detail.getTaskNameType()) && !CommonUtil.isEmpty(acticityTypaManagerMap)) {
                        String taskNameType = acticityTypaManagerMap.get(detail.getTaskNameType()) == null ? "" : acticityTypaManagerMap.get(detail.getTaskNameType());
                        vo.setTaskNameType(taskNameType);
                    }
                    vo.setPreposeNos(preposeNos);
                    resultList.add(vo);
                }
            }
            fileDownLoad(response, resultList, null, "true");
            log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plan.exportExcelSuccess"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出Excel（包含下载模板）
     *
     * @return
     */
    private void fileDownLoad(HttpServletResponse response, List<PlanTemplateExcelVo> dataList, Map<String, String> errorMsgMap, String isExport) {
        String title = PlantemplateConstant.PROJECT_TEMPLATE_EXPORT_PLAN_NAME;
        String excelName = POIExcelUtil.createExcelName(isExport, title, errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        String attention = "注意事项：参考工期为1~9999之间的整数（里程碑工期可以为0）；前置计划序号和交付物物可以为多个，用逗号隔开";
        List<String> columns = new ArrayList<String>();
        columns = Arrays.asList(new String[] {"planNumber:序号", "parentNo:父计划序号", "planName:计划名称","taskNameType:计划类型",
                "planLevel:计划等级", "worktime:参考工期（天）", "milestone:里程碑", "isNecessary:是否必要",
                "preposeNos:前置计划序号", "deliverablesName:交付项名称"});
        List<String> requiredHeaders = Arrays.asList(new String[] {"序号", "计划名称", "计划类型", "里程碑", "参考工期（天）"});
        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        List<String> validationDataList = new ArrayList<String>();
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
        for(BusinessConfig c : planLevelList) {
            validationDataList.add(c.getName());
        }

        List<ActivityTypeManageDto> allActivityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(false);
        List<String> validationDataList3 = new ArrayList<String>();
        for (ActivityTypeManageDto dto: allActivityTypeManageList){
            validationDataList3.add(dto.getName());
        }

        List<String> validationDataList2 = new ArrayList<String>();
        validationDataList2.add("是");
        validationDataList2.add("否");

        validationDataMap.put("planLevel", validationDataList);
        validationDataMap.put("milestone", validationDataList2);
        validationDataMap.put("isNecessary", validationDataList2);
        validationDataMap.put("taskNameType", validationDataList3);
        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle(title);
        excelVo.setAttention(attention);
        excelVo.setColumns(columns);
        excelVo.setDataList(dataList);
        excelVo.setErrorMsgMap(errorMsgMap);
        excelVo.setRequiredHeaders(requiredHeaders);
        excelVo.setValidationDataMap(validationDataMap);

        HSSFWorkbook workbook = POIExcelUtil.getInstance().exportExcel(bExport, excelVo, "yyyy/MM/dd");

        POIExcelUtil.responseReportWithName(response, workbook, excelName);
    }

    /**
     * Description: <br>
     * 下载Excel模板
     *
     * @see
     */
    @RequestMapping(params = "doDownloadExcelTemplate")
    public void doDownloadExcelTemplate(HttpServletRequest request, HttpServletResponse response) throws FdException, Exception {
        String isExport=request.getParameter("isExport");
        List<PlanTemplateExcelVo> planList = new ArrayList<PlanTemplateExcelVo>();
        fileDownLoad(response, planList, null, isExport);
    }

}
