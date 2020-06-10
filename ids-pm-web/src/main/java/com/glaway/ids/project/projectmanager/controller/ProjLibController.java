/*
 * 文件名：ProjLibController.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年5月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.businessobject.attribute.AdditionalAttributeManager;
import com.glaway.foundation.businessobject.attribute.dto.AdditionalAttributeDto;
import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.common.UploadFile;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.foundation.fdk.dev.service.FeignAttributeService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.system.serial.SerialNumberManager;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.dto.DeliveryStandardDocTypeDto;
import com.glaway.ids.common.feign.FeignIdsCommonServiceI;
import com.glaway.ids.config.auth.ProjLibAuthManager;
import com.glaway.ids.config.auth.ProjectLibraryAuthorityEnum;
import com.glaway.ids.config.constant.RepFileTypeConfigConstants;
import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.constant.BpmnConstants;
import com.glaway.ids.constant.JackrabbitConstants;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.RepFileTypeConstants;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.TempPlanInputsDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.DeliveryStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ProjLibFileDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ProjectLibAuthTemplateLinkDto;
import com.glaway.ids.project.projectmanager.service.*;
import com.glaway.ids.project.projectmanager.vo.AttachmentVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.commons.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 项目库
 * @author wangshen
 * @date 2015-05-11 13:00:25
 * @version V1.0
 */
@Controller
@RequestMapping("/projLibController")
public class ProjLibController extends BaseController {

    /**
     * 操作日志接口
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjLibController.class);


    /**
     * 
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    @Autowired
    private PlanRemoteFeignServiceI planService;

    /**
     * 
     */
    @Autowired
    private FeignRepService repService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;


    @Autowired
    private FeignAttributeService attributeService;


    /**
     * 
     */
    @Autowired
    private ProjLogRemoteFeignServiceI projLogService;



    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;
    
    /**
     * redis缓存服务
     */
    @Autowired
    private RedisService redisService;


    @Autowired
    private ProjectLibTemplateRemoteFeignServiceI projectLibTemplateService;


    @Autowired
    private FeignSystemService feignSystemService;

    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;


    @Autowired
    private FeignRoleService roleService;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoFeignService;

    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardFeignService;

    @Autowired
    private FeignIdsCommonServiceI feignIdsCommonServiceI;

    /**
     * message全局变量<br>
     */
    private String message;


    
    /**
     * 获取消息全局变量
     * 
     * @param
     * @return message
     * @see
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息全局变量
     * 
     * @param message
     * @return
     * @see
     */
    public void setMessage(String message) {
        this.message = message;
    }




    /**
     * 新建文件
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForLocalDoc")
    @ResponseBody
    public AjaxJson doAddForLocalDoc(ProjLibDocumentVo document, HttpServletRequest request,
                                     HttpSession session, RepFileTypeDto repFileType) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        Project project = projectService.getProjectEntity(projectId);
        String projectNo = project.getProjectNumber();
        SerialNumberManager.getDataMap().put("projectNo", projectNo);
        String docattachmentName = request.getParameter("docattachmentName");
        String docattachmentURL = request.getParameter("docattachmentURL");
        String docAttachmentShowName = request.getParameter("docAttachmentShowName");
        String docattachmentNames = "";
        String docattachmentURLs = "";
        String docAttachmentShowNames = "";
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentNames = docattachmentName.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentURLs = docattachmentURL.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docAttachmentShowNames = docAttachmentShowName.trim().toString();
        }

        String type = "文档";
        /**
         * 文件类型由RepFileType
         */
        String fileTypeId= "";
        List<RepFileTypeDto> fileTypeList = repService.getRepFileTypesByFileTypeCode(ResourceUtil.getApplicationInformation().getAppKey(), RepFileTypeConstants.REP_FILE_TYPE_PRO);
        if(!CommonUtil.isEmpty(fileTypeList)){
            fileTypeId = fileTypeList.get(0).getId();
        }
        /*String fileTypeId=repFileTypeQueryService.getFileTypeIdByCode(RepFileTypeConstants.REP_FILE_TYPE_PRO);*/
        document.setFileTypeId(fileTypeId);

        message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createSuccess");
        String dictCode = "secretLevel";

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);

        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curData : curTypes) {
            secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
        }
        try {

            if (projLibService.validateReptDocNum(document.getDocNumber())) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.docNumberExist");
                j.setSuccess(false);
                // j.setMsg(message);
                return j;
            }
            document.setType(1);
            String fileId = projLibService.createFile(document,ResourceUtil.getCurrentUser().getId());

            String useObjectId = request.getParameter("useObjectId");
            String useObjectType = request.getParameter("useObjectType");

            List<InputsDto> list = new ArrayList<>();
            String inputStr =  (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }

            if(CommonUtil.isEmpty(list)){
                list = new ArrayList<InputsDto>();;
            }

            if (CommonUtils.isNotEmpty(docattachmentNames)) {
                for (int i = 0; i < docattachmentNames.split(",").length; i++ ) {

                    RepFileAttachmentDto fileAttachment = new RepFileAttachmentDto();
                    fileAttachment.setAttachmentName(docattachmentNames.split(",")[i]);
                    fileAttachment.setAttachmentURL(docattachmentURLs.split(",")[i]);


                    fileAttachment.setFileId(fileId);
                    projLibService.addRepFileAttachment(fileAttachment);
                    fileAttachment.setFirstName(fileAttachment.getCreateName());
                    fileAttachment.setFirstFullName(fileAttachment.getCreateFullName());
                    fileAttachment.setFirstTime(fileAttachment.getCreateTime());
                    projLibService.updateFileAttachment(fileAttachment);

                    InputsDto inputs = new InputsDto();
                    inputs.setId(UUID.randomUUID().toString());
                    inputs.setUseObjectId(useObjectId);
                    inputs.setUseObjectType(useObjectType);
                    inputs.setName(docAttachmentShowNames.split(",")[i]);
                    inputs.setOriginType("LOCAL");
                    inputs.setTempId(UUID.randomUUID().toString());
                    inputs.setDocName(docattachmentNames.split(",")[i]);
                    inputs.setDocId(docattachmentURLs.split(",")[i]);
                    list.add(inputs);
                }
                String str = JSON.toJSONString(list);
                redisService.setToRedis("INPUTSLIST", useObjectId,str);

            }
            j.setObj(fileId);
            // 计划提交项操作记录
            String message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addDeliverySuccess");
            try {
            }
            catch (Exception e) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveLogFailure");
                log.error(message, e, null, message);
                Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
                throw new GWException(GWConstants.ERROR_2001, params, e);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createFailure");
            j.setSuccess(false);
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 跳转项目库页面
     *
     * @return
     */
    @RequestMapping(params = "goProjLibLayout0")
    public ModelAndView goProjLibLayout0(HttpServletRequest req) {
        String id = req.getParameter("id");
        req.setAttribute("projectId", id);
        req.setAttribute("opFlag", "1");
        req.setAttribute("havePower", "1");
        req.setAttribute("treeType", "0");
        req.setAttribute("rowId", req.getParameter("rowId"));
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibLayout");
    }



    /**
     * 跳转项目库详情页面
     *
     * @return
     */
    @RequestMapping(params = "viewProjectDocDetail")
    public ModelAndView viewProjectDocDetail(HttpServletRequest req) {
        String id = req.getParameter("id");
        String opFlag = req.getParameter("opFlag");
        String oper = req.getParameter("oper");
        String download = req.getParameter("download");
        String detail = req.getParameter("detail");
        String history = req.getParameter("history");
        String viewFlog = req.getParameter("viewFlog");
        if ("false".equals(download)) {
            req.setAttribute("download", false);
        }
        else {
            req.setAttribute("download", true);
        }

        if ("false".equals(detail)) {
            req.setAttribute("detail", false);
        }
        else {
            req.setAttribute("detail", true);
        }

        if ("false".equals(detail)) {
            req.setAttribute("detail", false);
        }
        else {
            req.setAttribute("detail", true);
        }

        if ("false".equals(history)) {
            req.setAttribute("history", false);
        }
        else {
            req.setAttribute("history", true);
        }
        String taskId = req.getParameter("taskId");
        String taskNumber = req.getParameter("taskNumber");
        if (oper != null && oper.equals("docRefused")) {
            req.setAttribute("docRefused", "docRefused");
        }

        ProjLibDocumentVo projLibDocumentVo = planService.getProjDocmentVoById(id);
       /* if ("shenpi".equals(projLibDocumentVo.getStatus())) {
            req.setAttribute("download", true);
            req.setAttribute("history", true);
        }*/

       List<TSTypeDto> types = feignSystemService.getTypesByGroupCode(ResourceUtil.getApplicationInformation().getAppKey(),
                ProjectConstants.PROJ_LIB_SEC_GROUPCODE, projLibDocumentVo.getSecurityLevel());

        if (!CommonUtil.isEmpty(types)) {
            projLibDocumentVo.setSecurityLevel(types.get(0).getTypename());
            projLibDocumentVo.setCreateTimeStr(DateUtil.dateToString(
                    projLibDocumentVo.getCreateTime(), DateUtil.YYYY_MM_DD));
            projLibDocumentVo.setUpdateTimeStr(DateUtil.dateToString(
                    projLibDocumentVo.getUpdateTime(), DateUtil.YYYY_MM_DD));
        }
        if ("1".equals(viewFlog)) {
            req.setAttribute("viewFlog", "1");
        }
        // req.setAttribute("securityLevel", UserUtil.getCurrentUserSecurityLevel());
        FeignJson fJson = repService.getLifeCycleListStr(ResourceUtil.getApplicationInformation().getAppKey(),new RepFileDto());
        String lifeCycleListStr = fJson.getObj().toString();


        req.setAttribute("lifeCycleList", lifeCycleListStr);
        req.setAttribute("doc", projLibDocumentVo);
        req.setAttribute("taskId", taskId);
        req.setAttribute("taskNumber", taskNumber);

        RepFileTypeDto repFileType = repService.getRepFileTypeById(ResourceUtil.getApplicationInformation().getAppKey(),projLibDocumentVo.getFileTypeId());
        if(!CommonUtil.isEmpty(repFileType)) {
            req.setAttribute("repFileTypeName", repFileType.getFileTypeName());
        }

        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDetail");

    }


    /**
     * 跳转项目库页面
     *
     * @return
     */
    @RequestMapping(params = "goProjLibLayout")
    public ModelAndView goProjLibLayout(HttpServletRequest req) {
        String id = req.getParameter("id");
        //String teamId = req.getParameter("teamId");
        boolean isHidProjLibOper = projLibService.isHidProjLibOperForDir(id);
        Object operationCodes = OpeartionUtils.getOperationCodes(req);

        boolean addProjLibFoder = false;
        boolean updateProjLibFoder = false;
        boolean powerProjLibFoder = false;
        boolean updateTreeSequence = false;
        boolean deleteProjLib = false;
        if(!CommonUtil.isEmpty(operationCodes)){
            for (String operationCode : operationCodes.toString().split(",")) {
                if (operationCode.contains("addProjLibFoder")) {
                    addProjLibFoder = true;
                }
                if (operationCode.contains("updateProjLibFoder")) {
                    updateProjLibFoder = true;
                }
                if (operationCode.contains("powerProjLibFoder")) {
                    powerProjLibFoder = true;
                }
                if (operationCode.contains("updateTreeSequence")) {
                    updateTreeSequence = true;
                }
                if (operationCode.contains("deleteProjLib")) {
                    deleteProjLib = true;
                }
            }
        }


        req.setAttribute("addProjLibFoder", addProjLibFoder);
        req.setAttribute("updateProjLibFoder", updateProjLibFoder);
        req.setAttribute("powerProjLibFoder", powerProjLibFoder);
        req.setAttribute("updateTreeSequence", updateTreeSequence);
        req.setAttribute("deleteProjLib", deleteProjLib);

        req.setAttribute("hideRight", "");
        boolean isModify = true;
        String isViewPage = req.getParameter("isViewPage");
        if (StringUtils.isNotEmpty(isViewPage)) {
            if ("true".equals(isViewPage)) {
                isModify = false;
            }
        }
        else {
            isModify = projectService.isModifyForPlan(id);
        }
        if (!addProjLibFoder && !updateProjLibFoder && !powerProjLibFoder) {
            req.setAttribute("hideRight", "1");
        }
        if (!isModify) {
            req.setAttribute("hideRight", "1");
        }
        req.setAttribute("isHidProjLibOper", isHidProjLibOper);
        req.setAttribute("isModify", isModify);
        req.setAttribute("projectId", id);
        //修改teamId根据项目ID区获得团队ID
        FeignJson fJson = projRoleService.getTeamIdByProjectId(id);
        req.setAttribute("teamId",String.valueOf(fJson.getObj()));
        req.setAttribute("updateTreeSequence", updateTreeSequence);

        // 是否刷新右侧树区域
        String refreshTree = req.getParameter("refreshTree");
        if (StringUtil.isNotEmpty(refreshTree)) {
            req.setAttribute("refreshTree", refreshTree);
        }

        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibLayout");
    }


    /**
     * 新增文件夹
     *
     * @return
     */
    @RequestMapping(params = "goAddFolder")
    public ModelAndView goAddFolder(HttpServletRequest req, HttpServletResponse resp) {
        String folderId = req.getParameter("folderId");
        String projectId = req.getParameter("projectId");
        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectId);
        vo.setParentId(folderId);
        vo.setType(0);
        req.setAttribute("doc", vo);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibFolder-add");

    }


    /**
     * 新增文件夹
     *
     * @return
     */
    @RequestMapping(params = "goUpdateFolder")
    public ModelAndView goUpdateFolder(HttpServletRequest req, HttpServletResponse resp) {
        String folderId = req.getParameter("folderId");
        ProjLibDocumentVo vo = projLibService.getProjDocmentVoById(folderId);
        String projectId = projLibService.getProjectIdByFileId(folderId);
        vo.setProjectId(projectId);
        req.setAttribute("doc", vo);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibFolder-add");

    }


    /**
     * 权限管理
     *
     * @return
     */
    @RequestMapping(params = "goPower")
    public ModelAndView goPower(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibPower");
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            Project project = projectService.getProjectEntity(projectId);
            if (null != project) {
                List<RepFileDto> files = projLibService.getFolderTree(projectId, "0","");
                for (RepFileDto file : files) {
                    TreeNode menu = null;
                    if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                        menu = new TreeNode(file.getId(), null, file.getFileName(),
                                file.getFileName(), true);
                    }
                    else {
                        menu = new TreeNode(file.getId(), file.getParentId(), file.getFileName(),
                                file.getFileName(), true);

                    }
                    req.setAttribute("url",
                            "projLibController.do?goPowerList&folderId=" + menu.getId()
                                    + "&projectId=" + projectId + "&id=" + menu.getId());
                    req.setAttribute("fileIdRm", menu.getId());
                    break;
                }

            }
        }
        req.setAttribute("projectId", projectId);
        req.setAttribute("templeteId", "");
        List<ProjectLibAuthTemplateLinkDto> list = projLibService.getProjectLibAuthTemplateLinkId(projectId);
        List<ProjectLibTemplateDto> projectLibTemplateList = projectLibTemplateService.getAllUseProjectLibTemplate();
        if (!CommonUtil.isEmpty(list)) {
            for (ProjectLibTemplateDto p : projectLibTemplateList) {
                if (p.getId().equals(list.get(0).getTemplateId())) {
                    req.setAttribute("templeteId", list.get(0).getTemplateId());
                }
            }
        }
        return mv;
    }


    /**
     * 判断是否有导出权限
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkFileName")
    @ResponseBody
    public AjaxJson checkFileName(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();

        RepFileDto r = new RepFileDto();
        String id = req.getParameter("id");
        String parentId = req.getParameter("parentId");
        String fileName = req.getParameter("fileName");

        if (StringUtil.isNotEmpty(id)) {
            r = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
            r.setFileName(fileName);
        }
        else {
            r.setFileName(fileName);
            r.setParentId(parentId);
        }
        Boolean a = projLibService.checkCategoryNameExist(r);
        if (!a) {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addSuccess"));
        }
        else {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.nameExist"));
            j.setSuccess(false);
        }
        return j;
    }



    /**
     * 更新文件夹
     *
     * @param document
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateFolder")
    @ResponseBody
    public AjaxJson doUpdateFolder(ProjLibDocumentVo document, HttpServletRequest request) {// method
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.updateFileSuccess");

        try {
            String id = document.getId();

            if (StringUtils.isBlank(id)) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.fileIdNotEmpty");
                j.setMsg(message);
                return j;
            }

            boolean isRootFolder = projLibService.isRootFolder(id);
            if (isRootFolder) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.rootCannotUpdate");
                j.setMsg(message);
                return j;
            }

            RepFileDto file = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
            file.setFileName(document.getDocName());
            repService.updateRepFileById(ResourceUtil.getApplicationInformation().getAppKey(),file);
            j.setObj(document.getDocName());

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.updateFileFailure");
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 文件夹删除
     *
     * @return
     */
    @RequestMapping(params = "doDelFolder")
    @SuppressWarnings("finally")
    @ResponseBody
    public AjaxJson doDelFolder(HttpServletRequest req, HttpServletResponse resp) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteFileSuccess");
        String folderStr = req.getParameter("datas");
        String folderId = JSONUtils.parse(folderStr).toString();
        boolean isRootFolder = projLibService.isRootFolder(folderId);
        if (isRootFolder) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.rootCannotDelete");
            j.setMsg(message);
            return j;
        }
        try {
            boolean isSuccess = projLibService.delFileById(folderId);
            if (!isSuccess) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.folderCannotDelete");
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteFileFailure");
            log.error(message, e, folderId, "");
            Object[] params = new Object[] {message,
                    RepFileDto.class.getClass() + " json:" + folderId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 文件夹删除
     *
     * @return
     */
    @RequestMapping(params = "beforeDelFolder")
    @SuppressWarnings("finally")
    @ResponseBody
    public FeignJson beforeDelFolder(HttpServletRequest req, HttpServletResponse resp) {
        String folderStr = req.getParameter("datas");
        String folderId = JSONUtils.parse(folderStr).toString();
        FeignJson j = projLibService.beforeDelFolder(folderId);
        return j;
    }

    /**
     * 项目库菜单树
     *
     * @return
     */
    @RequestMapping(params = "getProjLibTree")
    public void getProjLibTree(HttpServletRequest req, HttpServletResponse resp) {
        String teamId = req.getParameter("teamId");
        String havePower = req.getParameter("havePower");
        String projectId = req.getParameter("projectId");
        String treeType = req.getParameter("treeType");
        List<TreeNode> list = new ArrayList<TreeNode>();
        if ("1".equals(treeType)) {
            havePower = treeType;
        }
        if ("1".equals(havePower)) {
            if (StringUtils.isNotEmpty(projectId)) {
                Project project = projectService.getProjectEntity(projectId);
                if (null != project) {
                    List<RepFileDto> files = projLibService.getFolderTree(projectId, "1",ResourceUtil.getCurrentUser().getId());

                    for (RepFileDto file : files) {
                        TreeNode menu = null;
                        if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                            menu = new TreeNode(file.getId(), null, file.getFileName(),
                                    file.getFileName(), true);
                        }
                        else {
                            menu = new TreeNode(file.getId(), file.getParentId(),
                                    file.getFileName(), file.getFileName(), true);
                        }
                        menu.setDataObject("projLibController.do?goProjDocList&teamId=" + teamId
                                + "&folderId=" + menu.getId() + "&projectId="
                                + projectId + "&canSelect=" + file.getOperStatus());
                        menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
                        menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                        menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                        list.add(menu);
                    }
                    String json = JSONArray.toJSONString(list);
                    TagUtil.ajaxResponse(resp, json);
                }
            }
        }
        else {
            if (StringUtils.isNotEmpty(projectId)) {
                Project project = projectService.getProjectEntity(projectId);
                if (null != project) {
                    List<RepFileDto> files = projLibService.getFolderTree(projectId, "0","");

                    for (RepFileDto file : files) {
                        TreeNode menu = null;
                        if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                            menu = new TreeNode(file.getId(), null, file.getFileName(),
                                    file.getFileName(), true);
                            // menu.setIcon("111.png");
                        }
                        else {
                            menu = new TreeNode(file.getId(), file.getParentId(),
                                    file.getFileName(), file.getFileName(), true);
                            // menu.setIcon("webpage/com/glaway/ids/common/tree-point.png");

                        }
                        // menu.setProjectId(projectId);
                        menu.setDataObject("projLibController.do?goProjDocList&teamId=" + teamId
                                + "&folderId=" + menu.getId() + "&projectId="
                                + projectId);
                        menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
                        menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                        menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                        list.add(menu);

                    }

                    String json = JSONArray.toJSONString(list);
                    TagUtil.ajaxResponse(resp, json);
                }
            }
        }
    }



    /**
     * 跳转项目库文档列表页面
     *
     * @return
     */
    @RequestMapping(params = "goProjDocList")
    public ModelAndView goProjDocList(HttpServletRequest req) {
        String folderId = req.getParameter("folderId");
        String projectId = req.getParameter("projectId");

        Object operationCodes = OpeartionUtils.getOperationCodes(req);
        boolean addProjLibDoc = false;
        boolean deleteProjLibDoc = false;
        boolean approveProjLibDoc = false;
        boolean updateProjLibDoc = false;
        boolean reverseProjLibDoc = false;
        boolean backProjLibDoc = false;
        boolean updatePathProjLibDoc = false;

        if(!CommonUtil.isEmpty(operationCodes)){
            for (String operationCode : operationCodes.toString().split(",")) {
                if (operationCode.contains("addProjLibDoc")) {
                    addProjLibDoc = true;
                }
                if (operationCode.contains("deleteProjLibDoc")) {
                    deleteProjLibDoc = true;
                }
                if (operationCode.contains("approveProjLibDoc")) {
                    approveProjLibDoc = true;
                }
                if (operationCode.contains("updateProjLibDoc")) {
                    updateProjLibDoc = true;
                }
                if (operationCode.contains("reverseProjLibDoc")) {
                    reverseProjLibDoc = true;
                }
                if (operationCode.contains("backProjLibDoc")) {
                    backProjLibDoc = true;
                }
                if (operationCode.contains("updatePathProjLibDoc")) {
                    updatePathProjLibDoc = true;
                }
            }
        }


        req.setAttribute("addProjLibDoc", addProjLibDoc);
        req.setAttribute("deleteProjLibDoc", deleteProjLibDoc);
        req.setAttribute("approveProjLibDoc", approveProjLibDoc);
        req.setAttribute("updateProjLibDoc", updateProjLibDoc);
        req.setAttribute("reverseProjLibDoc", reverseProjLibDoc);
        req.setAttribute("backProjLibDoc", backProjLibDoc);
        req.setAttribute("updatePathProjLibDoc", updatePathProjLibDoc);
        req.setAttribute("webRoot",req.getParameter("webRoot"));

        req.setAttribute("opFlag", req.getParameter("opFlag"));
        req.setAttribute("folderId", folderId);
        req.setAttribute("projectId", projectId);
        boolean isHidProjLibOper = projLibService.isHidProjLibOper(projectId);
        req.setAttribute("isHidProjLibOper", isHidProjLibOper);
        FeignJson fJson = repService.getLifeCycleListStr(ResourceUtil.getApplicationInformation().getAppKey(),new RepFileDto());
        String  lifeCycleListStr = fJson.getObj().toString();

        req.setAttribute("lifeCycleList", lifeCycleListStr);

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(ProjectConstants.PROJ_LIB_SEC_GROUPCODE);

        JSONArray jsonList = new JSONArray();
        JSONObject jobj = null;
        for (TSTypeDto type : curTypes) {
            jobj = new JSONObject();
            jobj.put("code", type.getTypecode());
            jobj.put("name", type.getTypename());
            jsonList.add(jobj);
        }
        String securityLevelStr = jsonList.toString().replaceAll("\"", "'");

        req.setAttribute("securityLevel",
                securityLevelStr);
        List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
        // List<ProjLibRoleFileAuthVo> ProjLibList =
        // projLibService.getProjLibRoleFileAuths(folderId);
        String userId = UserUtil.getInstance().getUser().getId();
        String categoryFileAuths = projLibService.getCategoryFileAuths(folderId, userId);
        boolean isModify = true;
        String isViewPage = req.getParameter("isViewPage");
        if (StringUtils.isNotEmpty(isViewPage)) {
            if ("true".equals(isViewPage)) {
                isModify = false;
            }
        }
//        else {
//            isModify = projectService.isModifyForPlan(projectId);
//        }
        if (StringUtil.isNotEmpty(categoryFileAuths)) {
            for (String t2 : codeList) {
                if (categoryFileAuths.contains(t2) && isModify) {
                    req.setAttribute(t2, true);
                }
                else {
                    req.setAttribute(t2, false);
                }

            }
        }

        req.setAttribute("entityName", BpmnConstants.BPMN_ENTITIY_NAME_PROJECT_FILE);
        req.setAttribute("businessType", BpmnConstants.OBJECT_BUSINESS_BPMN_LINK_BUSINESSTYPE);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDocList");
    }



    /**
     * 查询项目库文档状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusList")
    @ResponseBody
    public void statusList(HttpServletRequest request, HttpServletResponse response) {
        FeignJson fJson = repService.getLifeCycleListStr(ResourceUtil.getApplicationInformation().getAppKey(),new RepFileDto());
        String lifeCycleListStr = fJson.getObj().toString();
        List<LifeCycleStatus> lifeCycleList = JSON.parseArray(lifeCycleListStr,LifeCycleStatus.class);
        String jonStr = JsonUtil.getCodeTitleJson(lifeCycleList, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 新建文件页面
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HttpServletRequest req, HttpServletResponse resp) {
        String folderId = req.getParameter("folderId");
        ProjLibDocumentVo parentVo = new ProjLibDocumentVo();
        parentVo = projLibService.getProjDocmentVoById(folderId);
        FeignJson fJson = projLibService.getDocNamePath(folderId);
        String docNamePath = String.valueOf(fJson.getObj());
        String projectId = req.getParameter("projectId");
        String upload = req.getParameter("upload");
        String download = req.getParameter("download");
        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectId);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        if ("false".equals(upload)) {
            req.setAttribute("upload", false);
            req.setAttribute("uploadPowerFlog", "0");
        }
        else {
            req.setAttribute("upload", true);
            req.setAttribute("uploadPowerFlog", "1");
        }
        if ("false".equals(download)) {
            req.setAttribute("download", false);
        }
        else {
            req.setAttribute("download", true);
        }
        String dictCode = "secretLevel";

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);

        req.setAttribute("fileSecurityLevel", CommonUtil.isEmpty(curTypes.get(0))?"":curTypes.get(0).getTypecode());
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDoc-add");

    }


    /**
     * 批量删除文档
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public FeignJson doBatchDel(HttpServletRequest request) {
        String datasStr = request.getParameter("datas");
        List<ProjLibDocumentVo> docVos = JSONArray.parseArray(datasStr, ProjLibDocumentVo.class);
        FeignJson fJson = projLibService.doBatchDel(docVos,datasStr);
        return fJson;

    }


    /**
     * 新建文件页面
     *
     * @return
     */
    @RequestMapping(params = "goUpdatePath")
    public ModelAndView goUpdatePath(HttpServletRequest req, HttpServletResponse resp) {
        String folderId = req.getParameter("folderId");
        ProjLibDocumentVo parentVo = new ProjLibDocumentVo();
        parentVo = projLibService.getProjDocmentVoById(folderId);
        FeignJson fJson = projLibService.getDocNamePath(folderId);
        String docNamePath = String.valueOf(fJson.getObj());
        String projectId = req.getParameter("projectId");
        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectId);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        String repFileIds = req.getParameter("repFileIds");
        req.setAttribute("repFileIds", repFileIds);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDoc-updatePath");

    }


    /**
     * 更改路径
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdatePath")
    @ResponseBody
    public AjaxJson doUpdatePath(ProjLibDocumentVo document, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.updatePathSuccess");
        try {
            String parentId = request.getParameter("parentId");
            String repFileIds = request.getParameter("repFileIds");
            projLibService.updatePathRepFile(parentId,repFileIds);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.updatePathFailure");
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目库文档列表查询接口
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {

        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);

        String folderId = request.getParameter("folderId");
        String docTypeId = request.getParameter("docTypeId");
        String attributeIds = request.getParameter("attributeIds");
        String projectId = request.getParameter("projectId");
        String projLibDocCreator = request.getParameter("projLibDocCreator");
        String projLibDocModifor = request.getParameter("projLibDocModifor");
        String bizCurrent = request.getParameter("bizCurrent");
        String securityLevel = request.getParameter("securityLevel");
        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("RepFile.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(securityLevel)) {
            ConditionVO vo = new ConditionVO();

            vo.setValueArr(securityLevel.split(","));
            String[] valueArr = vo.getValueArr();
            for (int i = 0; i < vo.getValueArr().length; i++ ) {
                if (i == 0) {
                    valueArr[0] = valueArr[1];
                }
            }
            vo.setValueArr(valueArr);
            vo.setCondition("in");
            vo.setValue(securityLevel);
            vo.setKey("RepFile.securityLevel");
            conditionList.add(vo);
        }


        Map<String,String> nameAndValueMap = new HashMap<String,String>();
        if(!CommonUtil.isEmpty(attributeIds)){
            String [] idArrays = attributeIds.split(";");
            for(String curAttributeIds: idArrays){
                if (curAttributeIds.split("_").length >= 3) {
                    String value = request.getParameter(curAttributeIds);
                    if(!CommonUtil.isEmpty(value)){
                        nameAndValueMap.put(curAttributeIds.split("_")[2], value);
                    }
                }
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("conditionList",conditionList);
        map.put("nameAndValueMap",nameAndValueMap);
        PageList pageList = new PageList();
        if(ResourceUtil.getCurrentUser().getUserType()==0 || ResourceUtil.getCurrentUser().getUserType() == 0){
            pageList = projLibService.queryEntity(map, folderId, projectId,
                    projLibDocCreator, projLibDocModifor,docTypeId,"");
        }else{
            pageList = projLibService.queryEntity(map, folderId, projectId,
                    projLibDocCreator, projLibDocModifor,docTypeId,ResourceUtil.getCurrentUser().getId());
        }
        long count = pageList.getCount();
        String json = JsonUtil.getListJsonWithoutQuote(pageList.getResultList());
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    /**
     * 新建文件
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ProjLibDocumentVo document, HttpServletRequest request,
                          HttpSession session, RepFileTypeDto repFileType) {
        AjaxJson j = new AjaxJson();
        String sad = document.getProjectId();
        String projectId = request.getParameter("projectId");
        Project project = projectService.getProjectEntity(projectId);
        String projectNo = project.getProjectNumber();
        SerialNumberManager.getDataMap().put("projectNo", projectNo);
        String docattachmentName = request.getParameter("docattachmentName");
        String docattachmentURL = request.getParameter("docattachmentURL");
        String docSecurityLevelFrom = request.getParameter("docSecurityLevelFrom");
        String opType = request.getParameter("opType");
        String docattachmentNames = "";
        String docattachmentURLs = "";
        String docSecurityLevelFroms = "";
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentNames = docattachmentName.trim().toString().substring(1);
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentURLs = docattachmentURL.trim().toString().substring(1);
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docSecurityLevelFroms = docSecurityLevelFrom.trim().toString().substring(1);
        }

        String type = "文档";
        String oldFileTypeId="";
        message = type + I18nUtil.getValue("com.glaway.ids.common.msg.create") + I18nUtil.getValue("com.glaway.ids.knowledge.support.success");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        try {
            Enumeration enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String paraName = (String) enu.nextElement();
                String paraValue = request.getParameter(paraName);
                paramsMap.put(paraName, paraValue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String,String[]> reqMap = request.getParameterMap();

        String planId = (String)session.getAttribute("planId");
       /* //TODO
        document.setFileTypeId("4028ef2d504608ba0150462418bf0001");*/

        Enumeration<String> attrNames = request.getParameterNames();
        Map<String,Object> map = new HashMap<>();
        map.put("document",document);
        map.put("repFileType",repFileType);
        map.put("paramsMap",paramsMap);
        map.put("currentUser",ResourceUtil.getCurrentUser());
        map.put("opType",opType);
        map.put("planId",planId);
        FeignJson fJson = projLibService.doAddProjLibDoc(map,docattachmentNames,docattachmentURLs,docSecurityLevelFroms,type);

        try {
            if(fJson.isSuccess()){
                String fileId = String.valueOf(fJson.getObj());
                RepFileDto repFile = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), fileId);
                //软属性保存前，切换到对应的对象，即保存新的对象绑定关系：
                if(!CommonUtil.isEmpty(document.getFileTypeId())){
                    oldFileTypeId = document.getFileTypeId();
                    List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition("com.glaway.foundation.rep.entity.RepFileType", oldFileTypeId, oldFileTypeId);
                    if(!CommonUtil.isEmpty(allList)){
                        Map<String,Object> attrMap = new HashMap<String,Object>();
                        attrMap.put("repFile",repFile);
                        attrMap.put("allList",allList);
                        projLibService.saveEntityAttrAdditionalAttribute(attrMap);
                        Map<String, String> entityAttrAdditionalAttributeValMap = projLibService.saveEntityAttrAdditionalAttributeVal(paramsMap);
                        Map<String,Object> initMap = new HashMap<String,Object>();
                        initMap.put("repFile",repFile);
                        initMap.put("entityAttrAdditionalAttributeValMap",entityAttrAdditionalAttributeValMap);
                        projLibService.initEntityAttrAdditionalAttribute(
                                initMap, repFile.getId(),
                                repFile.getId());
                        //软属性数据保存：
                        Map<String, Object> parmMap = new HashMap<>();
                        parmMap.put("entityAttrName",repFile.getId());
                        parmMap.put("entityAttrVal",repFile.getId());
                        parmMap.put("glObjectId",repFile.getId());
                        parmMap.put("glObjectClassName","com.glaway.foundation.rep.entity.RepFile");
                        parmMap.put("addAttrMap",reqMap);
                        AdditionalAttributeManager.addOrUpdateEntityAttrAdditionalAttribute(
                                repFile, parmMap);


                    }
                }


                TreeNode menu = new TreeNode();
                menu.setId(fileId);
                menu.setPid(document.getParentId());
                menu.setTitle(document.getDocName());
                menu.setName(document.getDocName());
                menu.setOpen(true);
                menu.setDataObject("projLibController.do?goProjDocList&teamId=" + null
                        + "&folderId=" + menu.getId() + "&projectId="
                        + sad + "&canSelect=" + document.getOperStatus());
                menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
                menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                j.setObj(menu);
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }



        }
        catch (Exception e) {
            if(!CommonUtil.isEmpty(opType)){
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addDeliveryFailure");
            }else{
                message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createFailure");
            }
            j.setSuccess(false);
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }

    }


    /**
     * (拖拽方式)更改节点 -------2016年7月20日 15:43:38
     *
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "moveprojLibTreeNode")
    @ResponseBody
    public AjaxJson moveprojLibTreeNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String parentId = request.getParameter("parentId");
        AjaxJson j = new AjaxJson();
        try {
            RepFileDto repFile = new RepFileDto();
            repFile.setId(id);
            repFile.setFileName(name.trim());
            repFile.setParentId(parentId);
            boolean isExist = projLibService.checkNameExist(repFile);
            if (!isExist) {
                repFile = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(),id);
                repFile.setParentId(parentId);
                repService.updateRepFileById(ResourceUtil.getApplicationInformation().getAppKey(),repFile);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.nodeNameExist");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.moveFailure");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * Description: <br>
     * 获取datagride中的密级的下拉框
     *
     * @param request
     * @param response
     * @see
     */

    @RequestMapping(params = "queryDocSecurityLevelList")
    @ResponseBody
    public void queryDocSecurityLevelList(HttpServletRequest request, HttpServletResponse response) {
        String docSecurityLevelFrom = request.getParameter("docSecurityLevelFrom");
        List<TSTypeDto> list = getSecurityLevelJson();
        Map<String, String> nameAndCodeMap = new HashMap<String, String>();
        Map<String, String> codeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curlist : list) {
            nameAndCodeMap.put(curlist.getTypename(), curlist.getTypecode());
            codeAndNameMap.put(curlist.getTypecode(), curlist.getTypename());
        }

        String uuids = request.getParameter("uuids").trim().toString().substring(1);
        String dowmLoadUrls = request.getParameter("dowmLoadUrls").trim().toString().substring(1);
        String attachmentNames = request.getParameter("attachmentNames").trim().toString().substring(
                1);
        String docSecurityLevels = request.getParameter("docSecurityLevels").trim().toString().substring(
                1);

        List<AttachmentVo> attachmentVo = new ArrayList<AttachmentVo>();
        if ("===".equals(uuids)) {}
        else {
            for (int i = 0; i < uuids.split(",").length; i++ ) {
                AttachmentVo addProjLibDocumentVo = new AttachmentVo();
                addProjLibDocumentVo.setAttachmentName(attachmentNames.split(",")[i]);
                addProjLibDocumentVo.setDowmLoadUrl(dowmLoadUrls.split(",")[i]);
                addProjLibDocumentVo.setUuid(uuids.split(",")[i]);
                if (Integer.parseInt(nameAndCodeMap.get(docSecurityLevels.split(",")[i])) <= Integer.parseInt(docSecurityLevelFrom)) {
                    addProjLibDocumentVo.setDocSecurityLevel(docSecurityLevels.split(",")[i]);
                }
                else {
                    addProjLibDocumentVo.setDocSecurityLevel(codeAndNameMap.get(docSecurityLevelFrom));
                }

                attachmentVo.add(addProjLibDocumentVo);
            }
        }
        String json = JsonUtil.getListJsonWithoutQuote(attachmentVo);
        String datagridStr = "{\"rows\":" + json + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * Description: <br>
     * 更改Form的密级导致数据库密级的改变，并将改变的值传到前台。
     *
     * @param request
     * @param response
     * @see
     */

    @RequestMapping(params = "dataBaseListByChangeSecurityLevel")
    @ResponseBody
    public void dataBaseListByChangeSecurityLevel(HttpServletRequest request,
                                                  HttpServletResponse response) {
        String docSecurityLevelFrom = request.getParameter("docSecurityLevelFrom");
        List<TSTypeDto> list = getSecurityLevelJson();
        Map<String, String> nameAndCodeMap = new HashMap<String, String>();
        Map<String, String> codeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curlist : list) {
            nameAndCodeMap.put(curlist.getTypename(), curlist.getTypecode());
            codeAndNameMap.put(curlist.getTypecode(), curlist.getTypename());
        }

        String ids = request.getParameter("ids").trim().toString().substring(1);
        String dowmLoadUrls = request.getParameter("dowmLoadUrls").trim().toString().substring(1);
        String attachmentNames = request.getParameter("attachmentNames").trim().toString().substring(
                1);
        String docSecurityLevels = request.getParameter("docSecurityLevels").trim().toString().substring(
                1);
        String createNames = request.getParameter("createNames").trim().toString().substring(1);
        List<AttachmentVo> attachmentVo = new ArrayList<AttachmentVo>();
        if ("===".equals(ids)) {}
        else {
            for (int i = 0; i < ids.split(",").length; i++ ) {
                AttachmentVo addProjLibDocumentVo = new AttachmentVo();
                addProjLibDocumentVo.setAttachmentName(attachmentNames.split(",")[i]);
                addProjLibDocumentVo.setDowmLoadUrl(dowmLoadUrls.split(",")[i]);
                addProjLibDocumentVo.setId(ids.split(",")[i]);
                addProjLibDocumentVo.setCreateName(createNames.split(",")[i]);
                RepFileAttachmentDto curAttachment = repService.getRepFileAttachmentByAttachmentId(ResourceUtil.getApplicationInformation().getAppKey(),ids.split(",")[i]);
                addProjLibDocumentVo.setCreateTime(curAttachment.getFirstTime());
                if (Integer.parseInt(nameAndCodeMap.get(docSecurityLevels.split(",")[i])) <= Integer.parseInt(docSecurityLevelFrom)) {
                    addProjLibDocumentVo.setDocSecurityLevel(docSecurityLevels.split(",")[i]);
                }
                else {
                    addProjLibDocumentVo.setDocSecurityLevel(codeAndNameMap.get(docSecurityLevelFrom));
                }

                attachmentVo.add(addProjLibDocumentVo);
            }
        }
        String json = JsonUtil.getListJsonWithoutQuote(attachmentVo);
        String datagridStr = "{\"rows\":" + json + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * Description: <br>
     * 获取datagride中的密级的下拉框
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "queryDocSecurityLevelListFile")
    @ResponseBody
    public void queryDocSecurityLevelListFile(HttpServletRequest request,
                                              HttpServletResponse response) {
        List<TSTypeDto> lists = getSecurityLevelJson();
        String jonStr = JsonUtil.getCodeTitleJson(lists, "typename", "typecode");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取密级组
     *
     * @return
     * @see
     */
    @RequestMapping(params = "getSecurityLevelJson")
    public List<TSTypeDto> getSecurityLevelJson() {
        short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
        // 给密级的下拉框设置初始值

        String dictCode = "secretLevel";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curType = tsMap.get(dictCode);
        List<TSTypeDto> list = new ArrayList<TSTypeDto>();
        for (int i = curType.size(); i >= 1; i-- ) {
            if (creatorSecurityLevel >= Integer.parseInt(curType.get(i - 1).getTypecode())) {
                list.add(curType.get(i - 1));
            }
        }
        // String jonStr = JsonUtil.getCodeTitleJson(list, "typename", "typecode");
        // jonStr = jonStr.replaceAll("'", "\"");

        return list;
    }


    /**
     * Description: <br>
     * 1、存储库结构管理<br>
     * 2、添加文件附件<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addFileAttachments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addFileAttachments(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileId = "";
            fileId = planService.getFoldIdByProjectId(request.getParameter("projectId"));
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();
            String attachmentName = "";
            String attachmentURL = "";
            String uuidNew = "";
            String uuid = "";
            String[] fileArr = null;
            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                InputStream fos = mf.getInputStream();// 获取文件流
                attachmentName = mf.getOriginalFilename();
                fileArr = StringUtils.split(attachmentName, "\\.");
                String fileType = fileArr[fileArr.length - 1];
                uuidNew = UUID.randomUUID().toString();
                uuid = uuidNew.replace("-", "") + "." + fileType;
                attachmentURL = JackrabbitUtil.handleFileUpload(fos, uuid,
                        JackrabbitConstants.PROJECT_LIBRARY_FILE_PATH, true);
                AttachmentVo addProjLibDocumentVo = new AttachmentVo();
                addProjLibDocumentVo.setAttachmentName(attachmentName);
                addProjLibDocumentVo.setDowmLoadUrl(attachmentURL);
                addProjLibDocumentVo.setUuid(uuidNew);
                addProjLibDocumentVo.setAttachmentShowName(attachmentName.substring(0, attachmentName.lastIndexOf(".")));
            }
            j.setObj(attachmentName + "," + attachmentURL + "," + uuidNew +"," + attachmentName.substring(0, attachmentName.lastIndexOf("."))+","+UserUtil.getInstance().getUser().getRealName()+"-"+UserUtil.getInstance().getUser().getUserName()+","+new Date());
            // 计划分解需要。使用msg存放fileid
            j.setMsg(fileId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GWException(e);
        }
        finally {
            return j;
        }
    }


    /**
     * Description: <br>
     * 1、文件下载<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "fileDown")
    public void fileDown(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            String fileName = "";
            if (CommonUtils.isNotEmpty(request.getParameter("attachmentName"))) {
                fileName = request.getParameter("attachmentName");
            }
            String filePath = request.getParameter("filePath");
            filePath = URLDecoder.decode(filePath, "UTF-8");
            List<RepFileAttachmentDto> list = repService.getRepFileAttachmentByAttachmentUrl(ResourceUtil.getApplicationInformation().getAppKey(),filePath);
            if (list != null && list.size() > 0) {
                fileName = list.get(0).getAttachmentName();
            }
            filePath = URLDecoder.decode(filePath, "UTF-8");
            is = JackrabbitUtil.downLoadFile(filePath);
            String contentType = "application/x-download";
            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            out = response.getOutputStream();
            byte[] bytes = new byte[0xffff];
            int b = 0;
            while ((b = is.read(bytes, 0, 0xffff)) > 0) {
                out.write(bytes, 0, b);
            }
            is.close();
            out.flush();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        finally {
            try {
                if (!CommonUtil.isEmpty(is)) {
                    is.close();
                }
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }



    /**
     * 跳转项目库树
     *
     * @return
     */
    @RequestMapping(params = "goProjLibTree")
    public ModelAndView goProjLibTree(HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        String havePower = req.getParameter("havePower");
        String treeType = req.getParameter("treeType");
        req.setAttribute("projectId", projectId);
        req.setAttribute("havePower", havePower);
        req.setAttribute("treeType", treeType);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibTree");
    }


    /**
     * 项目库菜单树
     *
     * @return
     */
    @RequestMapping(params = "getProjLibTreeForPower")
    public void getProjLibTreeForPower(HttpServletRequest req, HttpServletResponse resp) {
        String projectId = req.getParameter("projectId");
        List<TreeNode> list = new ArrayList<TreeNode>();
        if (StringUtils.isNotEmpty(projectId)) {
            Project project = projectService.getProjectEntity(projectId);
            if (null != project) {
                List<RepFileDto> files = projLibService.getFolderTree(projectId, "0","");

                for (RepFileDto file : files) {
                    TreeNode menu = null;
                    if (StringUtils.equals(file.getLibId(), file.getParentId())) {
                        menu = new TreeNode(file.getId(), null, file.getFileName(),
                                file.getFileName(), true);
                        // menu.setIcon("111.png");
                    }
                    else {
                        menu = new TreeNode(file.getId(), file.getParentId(), file.getFileName(),
                                file.getFileName(), true);
                        // menu.setIcon("webpage/com/glaway/ids/common/tree-point.png");

                    }
                    // menu.setProjectId(projectId);
                    menu.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
                    menu.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                    menu.setIcon("webpage/com/glaway/ids/common/zTreeIcon_open.png");
                    menu.setDataObject("projLibController.do?goPowerList&folderId=" + menu.getId()
                            + "&projectId=" + projectId);
                    list.add(menu);

                }

                String json = JSONArray.toJSONString(list);
                TagUtil.ajaxResponse(resp, json);
            }
        }

    }


    /**
     * 权限列表
     *
     * @return
     */
    @RequestMapping(params = "goPowerList")
    public ModelAndView goPowerList(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("com/glaway/ids/project/projectmanager/powerList");
        String projectId = req.getParameter("projectId");
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = String.valueOf(fJson.getObj());
        String fileId = req.getParameter("id");
        List<TSRoleDto> list = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);// 获得团队先角色
        List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
        Map<String, String> map = ProjectLibraryAuthorityEnum.getAuthNameCodeMap();
        List<ProjLibRoleFileAuthVo> ProjLibList = projLibService.getProjLibRoleFileAuths(fileId);
        List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
        List<RepFileAuthVo> rList = new ArrayList<RepFileAuthVo>();
        for (TSRoleDto t : list) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setId(t.getRoleCode());
            r.setFileId(fileId);
            r.setRoleId(t.getRoleCode());
            r.setRoleName(t.getRoleName());
            repList.add(r);
        }

        for (String t : codeList) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setId(t);
            r.setCheckName(map.get(t));
            r.setCheckValue(t);
            rList.add(r);
        }
        String checks = "";
        if (!CommonUtil.isEmpty(ProjLibList)) {
            for (TSRoleDto t : list) {
                int a = 0;
                for (ProjLibRoleFileAuthVo p : ProjLibList) {
                    if (t.getId().equals(p.getRoleId())) {
                        for (String t2 : codeList) {
                            checks = checks + p.getAuthMap().get(t2) + ",";
                        }
                        a = 1;
                        break;
                    }
                }
                if (a == 0) {
                    for (String t2 : codeList) {
                        checks = checks + "false" + ",";
                    }
                }
            }
        }
        else {
            for (TSRoleDto t : list) {
                for (String t2 : codeList) {
                    checks = checks + "false" + ",";
                }
            }
        }
        mv.addObject("fileId", fileId);
        mv.addObject("projectId", projectId);
        mv.addObject("docc", rList);
        mv.addObject("docs", repList);
        mv.addObject("checks", checks);
        return mv;
    }



    /**
     * 切换时判断是否保存
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkSave")
    @ResponseBody
    public AjaxJson checkSave(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String checks = req.getParameter("checks");
        String fileId = req.getParameter("fileId");
        String projectId = req.getParameter("projectId");
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = String.valueOf(fJson.getObj());
        List<TSRoleDto> list = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);// 获得团队先角色
        List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
        List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
        for (int i = 0; i < list.size(); i++ ) {
            RepFileAuthVo p = new RepFileAuthVo();
            p.setFileId(fileId);
            p.setRoleId(list.get(i).getId());
            String permissionCode = checks.substring(codeList.size() * i, codeList.size() * i
                    + codeList.size());
            p.setCheckValue(permissionCode);
            repList.add(p);
        }
        Boolean a = projLibService.checkRoleFileAuthExistChange(fileId, repList);
        if (a) {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.isSaveAuth"));
            j.setSuccess(false);
        }
        else {
            j.setSuccess(true);
        }
        return j;
    }



    /**
     * 校验项目编号是否重复
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
            String projectId = req.getParameter("projectId");
            FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
            String teamId = String.valueOf(fJson.getObj());
            List<TSRoleDto> list = roleService.getSysRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);// 获得团队角色
            List<String> codeList = ProjLibAuthManager.getAllAuthActionCode();
            List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
            for (int i = 0; i < list.size(); i++ ) {
                RepFileAuthVo p = new RepFileAuthVo();
                p.setFileId(fileId);
                p.setRoleId(list.get(i).getId());
                String permissionCode = checks.substring(codeList.size() * i, codeList.size() * i
                        + codeList.size());
                p.setCheckValue(permissionCode);
                repList.add(p);
            }
            projLibService.saveProjLibRoleFileAuth(fileId, repList,ResourceUtil.getCurrentUser().getId());

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveAuthFailure");
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
     * 查看最新版本
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doCheckLastVersion")
    @ResponseBody
    public AjaxJson doCheckLastVersion(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        AjaxJson j = new AjaxJson();
        RepFileDto bo = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
        // 根据版本id获取最新的文件id
        RepFileDto file = repService.findBusinessObjectByBizId(ResourceUtil.getApplicationInformation().getAppKey(), bo.getBizId());

        if (bo.getId().equals(file.getId())) {}
        else {
            j.setSuccess(false);
        }

        return j;

    }



    /**
     * 项目库更新页面
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(HttpServletRequest req, HttpServletResponse resp) {
        String docId = req.getParameter("docId");
        String upload = req.getParameter("upload");
        String download = req.getParameter("download");
        String projectId = req.getParameter("projectId");
        String operStatus = req.getParameter("operStatus");
        ProjLibDocumentVo doc = projLibService.getProjDocmentVoById(docId);
        doc.setProjectId(projectId);
        if ("false".equals(upload)) {
            req.setAttribute("upload", false);
            req.setAttribute("uploadPowerFlog", "0");
        }
        else {
            req.setAttribute("upload", true);
            req.setAttribute("uploadPowerFlog", "1");
        }
        if ("false".equals(download)) {
            req.setAttribute("download", false);
        }
        else {
            req.setAttribute("download", true);
        }
        req.setAttribute("method", "update");
        List<TSTypeDto> list = getSecurityLevelJson();
        if (StringUtils.isEmpty(doc.getSecurityLevelId())) {
            doc.setSecurityLevelId(list.get(0).getTypecode());
        }
        else {
            req.setAttribute("fileSecurityLevel", doc.getSecurityLevelId());
        }
        req.setAttribute("doc", doc);
        RepFileTypeDto repFileType = repService.getRepFileTypeById(ResourceUtil.getApplicationInformation().getAppKey(), doc.getFileTypeId());
        if(!CommonUtil.isEmpty(repFileType)) {
            req.setAttribute("repFileTypeName", repFileType.getFileTypeName());
            req.setAttribute("fileTypeId", repFileType.getId());
        }
        //req.setAttribute("type", "update");
        //驳回的标识
        req.setAttribute("operStatus", operStatus);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDoc-update");

    }



    /**
     * 删除库中文件对应的附件 --wqb
     * 2016年7月4日 11:30:41
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAttachmentListDel")
    @ResponseBody
    public AjaxJson doAttachmentListDel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteAttachmentSuccess");
        try {
            String ids = request.getParameter("ids");
            projLibService.attachmentBatchDel(ids);
            log.info(message, I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteAttachmentSuccess"), "");
        }
        catch (Exception e) {
            String id = request.getParameter("id");
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.deleteAttachmentFailure");
            log.error(message, e, id, "");
            Object[] params = new Object[] {message,
                    RepFileAttachmentDto.class.getClass() + " oids:" + id};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }



    /**
     * 跳转项目库详情页面
     *
     * @return
     */
    @RequestMapping(params = "getDocAttachment")
    public void getDocAttachment(HttpServletRequest req, HttpServletResponse resp) {
        String docId = req.getParameter("docId");
        String fileSecurityLevel = req.getParameter("fileSecurityLevel");
        String dictCode = "secretLevel";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);
        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curData : curTypes) {
            secretLevelCodeAndNameMap.put(curData.getTypecode(), curData.getTypename());
        }

        List<AttachmentVo> dataBaseAttachmentVolist = new ArrayList<AttachmentVo>();
        List<RepFileAttachmentDto> attachment = repService.getRepFileAttachmentByFileId(docId);
        if (null != attachment) {
            for (RepFileAttachmentDto attachmentList : attachment) {
                if ("0".equals(attachmentList.getAvaliable())
                    // && creatorSecurityLevel >= attachmentList.getSecurityLevel()
                ) {

                }
                else {
                    AttachmentVo attachmentVo = new AttachmentVo();
                    attachmentVo.setAttachmentName(((RepFileAttachmentDto)attachmentList).getAttachmentName());
                    attachmentVo.setDowmLoadUrl(((RepFileAttachmentDto)attachmentList).getAttachmentURL());
                    attachmentVo.setDocSecurityLevel(secretLevelCodeAndNameMap.get(attachmentList.getSecurityLevel()
                            + ""));
                    if (attachmentList.getFirstTime() != null) {
                        attachmentVo.setCreateTime(attachmentList.getFirstTime());
                    }
                    else {
                        attachmentVo.setCreateTime(attachmentList.getCreateTime());
                    }
                    if (attachmentList.getFirstFullName() != null) {
                        attachmentVo.setCreateName(attachmentList.getFirstFullName() + "-"
                                + attachmentList.getFirstName());
                    }
                    else {
                        attachmentVo.setCreateName(attachmentList.getCreateFullName() + "-"
                                + attachmentList.getCreateName());
                    }
                    attachmentVo.setId(attachmentList.getId());
                    if (CommonUtils.isEmpty(attachmentVo.getChangeSecurityLevel())) {
                        attachmentVo.setChangeSecurityLevel(fileSecurityLevel);
                    }
                    dataBaseAttachmentVolist.add(attachmentVo);
                }
            }
        }

        String json = JsonUtil.toJsonString(dataBaseAttachmentVolist);
        TagUtil.ajaxResponse(resp, json);
    }



    /**
     * 更新文件
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ProjLibDocumentVo document, HttpServletRequest request) {
        String method = request.getParameter("method");
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.updateSuccess");
        String securityLevel1 = request.getParameter("securityLevel");
        String docattachmentName = request.getParameter("docattachmentNameUpdate");
        String docattachmentURL = request.getParameter("docattachmentURLUpdate");
        String docSecurityLevelFrom = request.getParameter("docSecurityLevelFromUpdate");
        String docattachmentIdUpdate = request.getParameter("docattachmentIdUpdate");
        String docattachmentSecurityLevelUpdate = request.getParameter("docattachmentSecurityLevelUpdate");
        String docattachmentNames = "";
        String docattachmentURLs = "";
        String docSecurityLevelFroms = "";
        String docattachmentIdUpdates = "";
        String docattachmentSecurityLevelUpdates = "";
        if (StringUtil.isNotEmpty(docattachmentName)) {
            docattachmentNames = docattachmentName.trim().toString().substring(1);
        }
        if (StringUtil.isNotEmpty(docattachmentURL)) {
            docattachmentURLs = docattachmentURL.trim().toString().substring(1);
        }
        if (StringUtil.isNotEmpty(docSecurityLevelFrom)) {
            docSecurityLevelFroms = docSecurityLevelFrom.trim().toString().substring(1);
        }
        if (StringUtil.isNotEmpty(docattachmentIdUpdate)) {
            docattachmentIdUpdates = docattachmentIdUpdate.trim().toString().substring(1);
        }
        if (StringUtil.isNotEmpty(docattachmentSecurityLevelUpdate)) {
            docattachmentSecurityLevelUpdates = docattachmentSecurityLevelUpdate.trim().toString().substring(
                    1);
        }
        try {
            String id = document.getId();
            String fileTypeId = document.getFileTypeId();
            String procInstId = "";
            List<ProjLibFileDto> list = projLibService.getProjLibFile(id);
            if(!CommonUtil.isEmpty(list)) {
                procInstId = list.get(0).getFeedbackProcInstId();
//                projLibService.updateVariablesAndTodoTask(fileId, procInstId, document.getDocName());
            }
            if (StringUtils.isBlank(id)) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.docIdNotEmpty");
                j.setMsg(message);
                return j;
            }

            short securityLevel = Short.parseShort(document.getSecurityLevel());
            short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
            if (securityLevel > creatorSecurityLevel) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.cannotUpdateHighSecurityLevel");
                j.setSuccess(false);
                return j;
            }
            FeignJson fJson = projLibService.updateFile(method, document, id, message,ResourceUtil.getCurrentUser().getId());
            String curFileId = String.valueOf(fJson.getObj());
            String fileId = curFileId.split(";")[0];
            message = curFileId.split(";")[1];
            String dictCode = "secretLevel";
            Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
            List<TSTypeDto> curTypes = tsMap.get(dictCode);

            //软属性保存前，切换到对应的对象，即保存新的对象绑定关系：
            RepFileDto repFile = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), fileId);


            //TODO
    /*        if(!CommonUtil.isEmpty(repFile.getFileTypeId()) &&
                    repFile.getFileTypeId().equals(fileTypeId) && !CommonUtil.isEmpty(procInstId)) {
                projLibService.updateProcessByFileType(repFile, procInstId);
            }*/


            Map<String, Object> paramsMap = new HashMap<String, Object>();
            try {
                Enumeration enu = request.getParameterNames();
                while (enu.hasMoreElements()) {
                    String paraName = (String) enu.nextElement();
                    String paraValue = request.getParameter(paraName);
                    paramsMap.put(paraName, paraValue);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            Map<String,String[]> reqMap = request.getParameterMap();

            if(!CommonUtil.isEmpty(document.getFileTypeId())){
                List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition(RepFileTypeConfigConstants.ENTITY_URI, document.getFileTypeId(), document.getFileTypeId());
                if(!CommonUtil.isEmpty(allList)){

                    Map<String,Object> attrMap = new HashMap<String,Object>();
                    attrMap.put("repFile",repFile);
                    attrMap.put("allList",allList);
                    projLibService.saveEntityAttrAdditionalAttribute(attrMap);
                    Map<String, String> entityAttrAdditionalAttributeValMap = projLibService.saveEntityAttrAdditionalAttributeVal(paramsMap);
                    //软属性数据保存：
                    Map<String, Object> map = new HashMap<>();
                    map.put("entityAttrName",repFile.getId());
                    map.put("entityAttrVal",repFile.getId());
                    map.put("glObjectId",repFile.getId());
                    map.put("glObjectClassName","com.glaway.foundation.rep.entity.RepFile");
                    map.put("addAttrMap",reqMap);
                    AdditionalAttributeManager.addOrUpdateEntityAttrAdditionalAttribute(
                            repFile, map);
                    Map<String,Object> initMap = new HashMap<String,Object>();
                    initMap.put("repFile",repFile);
                    initMap.put("entityAttrAdditionalAttributeValMap",entityAttrAdditionalAttributeValMap);
                    projLibService.initEntityAttrAdditionalAttribute(
                            initMap, repFile.getId(),
                            repFile.getId());
                }
            }

            if (CommonUtils.isNotEmpty(docattachmentNames)) {
                for (int i = 0; i < docattachmentNames.split(",").length; i++ ) {
                    RepFileAttachmentDto fileAttachment = repService.getRepFileAttachmentByAttachmentId(ResourceUtil.getApplicationInformation().getAppKey(),docattachmentIdUpdates.split(",")[i]);
                    if(CommonUtil.isEmpty(fileAttachment.getId())){
                        fileAttachment = new RepFileAttachmentDto();
                        fileAttachment.setAttachmentName(docattachmentNames.split(",")[i]);
                        fileAttachment.setAttachmentURL(docattachmentURLs.split(",")[i]);
                        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
                        for (TSTypeDto curData : curTypes) {
                            secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
                        }
                        // 保存附件信息
                        String attachmentSecurityLevel = docSecurityLevelFroms.split(",")[i];
                        //附件密集
                        int attachmentLevel = Integer.parseInt(secretLevelCodeAndNameMap.get(attachmentSecurityLevel));
                        //文档密级
                        int docLevel = Integer.parseInt(securityLevel1);
                        //文档密级要大于等于附件密级否则更新附件密级为文档密级
                        if (!CommonUtil.isEmpty(secretLevelCodeAndNameMap.get(attachmentSecurityLevel)) && docLevel >= attachmentLevel) {
                            fileAttachment.setSecurityLevel((short)attachmentLevel);
                        }
                        else {
                            fileAttachment.setSecurityLevel((short)docLevel);
                        }

                        fileAttachment.setFileId(fileId);
                        fileAttachment.setCreateBy(ResourceUtil.getCurrentUser().getId());
                        fileAttachment.setCreateName(ResourceUtil.getCurrentUser().getUserName());
                        fileAttachment.setCreateFullName(ResourceUtil.getCurrentUser().getRealName());
                        fileAttachment.setCreateOrgId(ResourceUtil.getCurrentUserOrg().getId());
                        projLibService.addRepFileAttachment(fileAttachment);
                        if (CommonUtils.isNotEmpty(fileAttachment.getAttachmentName())) {
                            fileAttachment.setFirstName(fileAttachment.getCreateName());
                            fileAttachment.setFirstFullName(fileAttachment.getCreateFullName());
                            fileAttachment.setFirstTime(fileAttachment.getCreateTime());
                        }
                        repService.updateRepFileAttachment(ResourceUtil.getApplicationInformation().getAppKey(),fileAttachment);
                    }

                }
            }
            j.setObj(fileId);
            // 获取上一个版本的附件数据：
            List<RepFileAttachmentDto> attachment = repService.getRepFileAttachmentByFileId(id);
            //组装数据库上一个版本的Id和RepFileAttachment的map
            Map<String,RepFileAttachmentDto> dataMap = new HashMap<String,RepFileAttachmentDto>();
            Map<String,String> dataIdMap = new HashMap<String,String>();
            // getAttachmentByFileId
            if (null != attachment) {
                for (RepFileAttachmentDto repFileAttachment : attachment) {
                    RepFileAttachmentDto curRepFileAttachment = new RepFileAttachmentDto();
                    // 保留以前的附件，加到新版本中：

                    try {
                        curRepFileAttachment = repService.initRepFileAttachmentBusinessObject(ResourceUtil.getApplicationInformation().getAppKey(),ResourceUtil.getCurrentUser().getId(),curRepFileAttachment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    curRepFileAttachment.setCreateBy(repFileAttachment.getCreateBy());
                    curRepFileAttachment.setCreateName(repFileAttachment.getCreateName());
                    curRepFileAttachment.setCreateTime(repFileAttachment.getCreateTime());
                    curRepFileAttachment.setCreateFullName(repFileAttachment.getCreateFullName());
                    curRepFileAttachment.setFirstName(repFileAttachment.getFirstName());
                    curRepFileAttachment.setFirstFullName(repFileAttachment.getFirstFullName());
                    curRepFileAttachment.setFirstTime(repFileAttachment.getFirstTime());
                    curRepFileAttachment.setAvaliable(repFileAttachment.getAvaliable());
                    curRepFileAttachment.setBizCurrent(repFileAttachment.getBizCurrent());
                    curRepFileAttachment.setBizId(repFileAttachment.getBizId());
                    curRepFileAttachment.setSecurityLevel(repFileAttachment.getSecurityLevel());
                    curRepFileAttachment.setAttachmentName(repFileAttachment.getAttachmentName());
                    curRepFileAttachment.setAttachmentURL(repFileAttachment.getAttachmentURL());
                    curRepFileAttachment.setFileId(fileId);
                    curRepFileAttachment.setIsMainFile(repFileAttachment.getIsMainFile());
                    dataMap.put(repFileAttachment.getId(), curRepFileAttachment);
                    dataIdMap.put(repFileAttachment.getId(), repFileAttachment.getId());

                }
            }
            // 筛选后的附件组合
            Map<String, RepFileAttachmentDto> attachmentShowSelectMap = new HashMap<String, RepFileAttachmentDto>();
            List<RepFileAttachmentDto> attachmentShowSelect = repService.getRepFileAttachmentByFileId(id);
            if (null != attachmentShowSelect) {
                for (RepFileAttachmentDto repFileAttachment : attachmentShowSelect) {
                    attachmentShowSelectMap.put(repFileAttachment.getId(), repFileAttachment);
                }
            }

            //获取过滤的附件并保存：
            List<RepFileAttachmentDto> attachmentShow = repService.getRepFileAttachmentByFileId(id);
            if (null != attachmentShow) {
                for (RepFileAttachmentDto repFileAttachment : attachmentShow) {
                    if(null !=attachmentShowSelectMap.get(repFileAttachment.getId())){
                    }else{
                        projLibService.addRepFileAttachment(dataMap.get(repFileAttachment.getId()));
                    }
                }
            }

            if (CommonUtils.isNotEmpty(docattachmentIdUpdates)) {

                for (int i = 0; i < docattachmentIdUpdates.split(",").length; i++ ) {
                    RepFileAttachmentDto repFileAttachment = dataMap.get(docattachmentIdUpdates.split(",")[i]);
                    Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
                    for (TSTypeDto curData : curTypes) {
                        secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
                    }
                    // 保存附件信息
                    String attachmentSecurityLevel = docattachmentSecurityLevelUpdates.split(",")[i];
                    //附件密集
                    int attachmentLevel = Integer.parseInt(secretLevelCodeAndNameMap.get(attachmentSecurityLevel));
                    //文档密级
                    int docLevel = Integer.parseInt(securityLevel1);
                    if(!CommonUtil.isEmpty(repFileAttachment)){
                        if (!CommonUtil.isEmpty(secretLevelCodeAndNameMap.get(attachmentSecurityLevel)) && docLevel >= attachmentLevel) {
                            repFileAttachment.setSecurityLevel((short)attachmentLevel);
                        }
                        else {
                            repFileAttachment.setSecurityLevel((short)docLevel);
                        }
                        repFileAttachment.setCreateBy(ResourceUtil.getCurrentUser().getId());
                        repFileAttachment.setCreateName(ResourceUtil.getCurrentUser().getUserName());
                        repFileAttachment.setCreateFullName(ResourceUtil.getCurrentUser().getRealName());
                        repFileAttachment.setCreateOrgId(ResourceUtil.getCurrentUserOrg().getId());
                        projLibService.addRepFileAttachment(repFileAttachment);
                    }

                }
            }
            //更新流程信息及我的待办
            if(!CommonUtil.isEmpty(procInstId)) {
                projLibService.updateVariablesAndTodoTask(fileId, procInstId, document.getDocName());
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.reviseFailure");
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 获取项目库文档路径
     *
     * @return
     */
    @RequestMapping(params = "getDocNamePath")
    public void getDocNamePath(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        FeignJson fJson = projLibService.getDocNamePath(id);
        String docNamePath = String.valueOf(fJson.getObj());
        TagUtil.ajaxResponse(resp, docNamePath);
    }



    /**
     * 选择路径后判断上传权限
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkUpload")
    @ResponseBody
    public AjaxJson checkUpload(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String userId = UserUtil.getInstance().getUser().getId();
        String folderId = req.getParameter("id");
        RepFileDto r = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(),folderId);
        Boolean detail = false;
        if (!CommonUtil.isEmpty(r)) {
            String categoryFileAuths = projLibService.getCategoryFileAuths(folderId, userId);
            if (StringUtil.isNotEmpty(categoryFileAuths)) {
                if (categoryFileAuths.contains("upload")) {
                    detail = true;
                }
            }
            else {
                detail = false;
            }
        }

        if (detail == true) {
            j.setSuccess(true);
        }
        else {
            j.setSuccess(false);
        }
        return j;
    }



    /**
     * 项目库文档修订
     *
     * @return
     */
    @RequestMapping(params = "goRevise")
    public ModelAndView goRevise(HttpServletRequest req, HttpServletResponse resp) {
        String upload = req.getParameter("upload");
        String download = req.getParameter("download");
        String docId = req.getParameter("docId");
        String projectId = req.getParameter("projectId");
        ProjLibDocumentVo doc = projLibService.getProjDocmentVoById(docId);
        doc.setProjectId(projectId);
        if ("false".equals(upload)) {
            req.setAttribute("upload", false);
            req.setAttribute("uploadPowerFlog", "0");
        }
        else {
            req.setAttribute("upload", true);
            req.setAttribute("uploadPowerFlog", "1");
        }
        if ("false".equals(download)) {
            req.setAttribute("download", false);
        }
        else {
            req.setAttribute("download", true);
        }
        req.setAttribute("method", "revise");
        req.setAttribute("doc", doc);
        String dictCode = "secretLevel";
        short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curType = tsMap.get(dictCode);
        List<Object> list = new ArrayList<Object>();
        for (int i = curType.size(); i >= 1; i-- ) {
            if (creatorSecurityLevel >= Integer.parseInt(curType.get(i - 1).getTypecode())) {
                list.add(curType.get(i - 1));
            }
        }
        if (StringUtils.isEmpty(doc.getSecurityLevelId())) {
            doc.setSecurityLevelId(curType.get(0).getTypecode());
        }
        else {
            req.setAttribute("fileSecurityLevel", doc.getSecurityLevelId());
        }
        RepFileTypeDto repFileType = repService.getRepFileTypeById(ResourceUtil.getApplicationInformation().getAppKey(), doc.getFileTypeId());
        if(!CommonUtil.isEmpty(repFileType)) {
            req.setAttribute("repFileTypeName", repFileType.getFileTypeName());
            if(!"禁用".equals(repFileType.getStatus())){
                req.setAttribute("fileTypeId", repFileType.getId());
            }

        }
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDoc-update");

    }


    /**
     * 批量回退文档
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBackVersion")
    @ResponseBody
    public AjaxJson doBackVersion(HttpServletRequest request) {
        String datasStr = request.getParameter("datas");
        List<ProjLibDocumentVo> docVos = JSONArray.parseArray(datasStr, ProjLibDocumentVo.class);
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.backSuccess");
        String infoId = "";
        try {
            for (ProjLibDocumentVo doc : docVos) {
                String id = doc.getId();
                infoId = id;
                RepFileDto r = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
                String bizId = r.getBizId();
                if (r.getBizCurrent() != null && !r.getBizCurrent().equals("nizhi")) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.onlyEditingBack");
                    j.setSuccess(false);
                    return j;
                }
                else {
                    String verson = r.getBizVersion();
                    String versonNumber = verson.split("\\.")[1];
                    if (versonNumber.equals("1")) {
                        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.cannotBackFirst");
                        j.setSuccess(false);
                        return j;
                    }
                    List<ProjLibDocumentVo> countList = projLibService.getFilesByBizId(bizId);
                    if (!CommonUtil.isEmpty(countList) && countList.size() == 1) {
                        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.cannotBackOnlyOne");
                        j.setSuccess(false);
                        return j;
                    }
                }
                projLibService.backVersion(id, bizId);
            }
            log.info(message, infoId, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.backFailure");
            log.error(message, e, infoId, "");
            Object[] params = new Object[] {message,
                    RepFileDto.class.getClass() + " json:" + datasStr};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 项目文档启动审批流程
     * （动态表单）
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "submitProcess")
    @ResponseBody
    public FeignJson submitProcess(HttpServletRequest request) {

        String id = request.getParameter("id");
        String entityName = request.getParameter("entityName");
        String businessType = request.getParameter("businessType");
        String docName = request.getParameter("docName");
        Map<String,String> params = new HashMap<String, String>();
        params.put("id",id);
        params.put("entityName",entityName);
        params.put("businessType",businessType);
        params.put("docName",docName);
        params.put("userId",ResourceUtil.getCurrentUser().getId());

        FeignJson j = projLibService.submitProcess(params);
        return j;
    }

    /**
     * 删除上传的冗余数据
     */
    @RequestMapping(params = "deleteProjLibJackrabbitFile")
    @ResponseBody
    public void deleteProjLibJackrabbitFile(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String invalidIds = request.getParameter("invalidIds");
        String docattachmentURL = request.getParameter("docattachmentURL");
        Map<String,String> curDelIdMap = new HashMap<String,String>();
        Map<String,String> curIdMap = new HashMap<String,String>();
        if ("add".equals(type)||"update".equals(type)) {
            if(CommonUtils.isNotEmpty(docattachmentURL)){
                String[] curDelId = invalidIds.split(",");
                for (int i = 0; i < curDelId.length; i++ ) {
                    curDelIdMap.put(curDelId[i],curDelId[i]);
                }
                String[] curId = docattachmentURL.split(",");
                for (int i = 0; i < curId.length; i++ ) {
                    curIdMap.put(curId[i],curId[i]);
                }
                //删除冗余数据
                for(int i = 0; i < curDelId.length; i++){
                    if(CommonUtils.isNotEmpty(curDelIdMap.get(curDelId[i]))&&CommonUtils.isEmpty(curIdMap.get(curDelId[i]))){
                        JackrabbitUtil.deleteFile(curDelId[i]);
                    }
                }
            }
        }
        else if("addCancel".equals(type)||"updateCancel".equals(type)){
            if (CommonUtils.isNotEmpty(invalidIds)) {
                String[] curId = invalidIds.split(",");
                for (int i = 0; i < invalidIds.split(",").length; i++ ) {
                    JackrabbitUtil.deleteFile(curId[i]);
                }
            }
        }
    }


    /**
     * 文档类型页面选择
     *
     * @return
     */
    @RequestMapping(params = "goDocType")
    public ModelAndView goDocType(HttpServletRequest req)
    {
        String docTypeId = req.getParameter("docTypeId");
        req.setAttribute("entrance", "delivery");
        req.setAttribute("docTypeId", docTypeId);
        return new ModelAndView("com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigList");
    }


    /**
     * Description: <br>
     * 软属性绑定对象的id传送
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "goShowUserDefinedInfo")
    public ModelAndView goShowUserDefinedInfo(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        String attributeIds = "";
        List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition("com.glaway.foundation.rep.entity.RepFileType", id, id);
        for(EntityAttributeAdditionalAttributeDto row : allList) {
            if(!CommonUtil.isEmpty(row.getAddAttrId())){
                AdditionalAttributeDto additionalAttribute = attributeService.getAdditionalAttribute(ResourceUtil.getApplicationInformation().getAppKey(), row.getAddAttrId());
                String curId = row.getEntityAttrName()+"_"+row.getEntityAttrVal()+"_"+additionalAttribute.getName();
                if(CommonUtil.isEmpty(attributeIds)){
                    attributeIds = curId;
                }else{
                    attributeIds = attributeIds+";"+curId;
                }
            }
        }
        request.setAttribute("attributeIds", attributeIds);
        return new ModelAndView("com/glaway/ids/project/projectmanager/userDefinedList");
    }

    /**
     * Description: <br>
     * 软属性绑定对象的id传送,查看页面
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "goShowUserDefinedInfoView")
    public ModelAndView goShowUserDefinedInfoView(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        String attributeIds = "";
        List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition("com.glaway.foundation.rep.entity.RepFileType", id, id);
        for(EntityAttributeAdditionalAttributeDto row : allList) {
            if(!CommonUtil.isEmpty(row.getAddAttrId())){
                AdditionalAttributeDto additionalAttribute = attributeService.getAdditionalAttribute(ResourceUtil.getApplicationInformation().getAppKey(), row.getAddAttrId());
                String curId = row.getEntityAttrName()+"_"+row.getEntityAttrVal()+"_"+additionalAttribute.getName();
                if(CommonUtil.isEmpty(attributeIds)){
                    attributeIds = curId;
                }else{
                    attributeIds = attributeIds+";"+curId;
                }
            }
        }
        request.setAttribute("attributeIds", attributeIds);
        return new ModelAndView("com/glaway/ids/project/projectmanager/userDefinedList");
    }

    /**
     * 项目库文档回退校验
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doCheckBackVersion")
    @ResponseBody
    public AjaxJson doCheckBackVersion(HttpServletRequest request) {
        String datasStr = request.getParameter("datas");
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.backSuccess");
        try {
            String id = request.getParameter("id");
            RepFileDto r = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), id);
            String bizId = r.getBizId();
            if (r.getBizCurrent() != null && !r.getBizCurrent().equals("nizhi")) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.onlyEditingBack");
                j.setSuccess(false);
                return j;
            }
            else {
                String verson = r.getBizVersion();
                String versonNumber = verson.split("\\.")[1];
                if (versonNumber.equals("1")) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.cannotBackFirst");
                    j.setSuccess(false);
                    return j;
                }
                List<ProjLibDocumentVo> countList = projLibService.getFilesByBizId(bizId);
                if (!CommonUtil.isEmpty(countList) && countList.size() == 1) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.cannotBackOnlyOne");
                    j.setSuccess(false);
                    return j;
                }
            }
            log.info(message, datasStr, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.backFailure");
            log.error(message, e, datasStr, "");
            Object[] params = new Object[] {message,
                    RepFileDto.class.getClass() + " json:" + datasStr};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 跳转项目库详情页面
     *
     * @return
     */
    @RequestMapping(params = "getDocAttachmentView")
    public void getDocAttachmentView(HttpServletRequest req, HttpServletResponse resp) {
        String docId = req.getParameter("docId");
        String viewFlog = req.getParameter("viewFlog");
        //当前用户密级
        short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
        String dictCode = "secretLevel";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);

        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curData : curTypes) {
            secretLevelCodeAndNameMap.put(curData.getTypecode(), curData.getTypename());
        }

        List<RepFileAttachmentDto> attachment = repService.getRepFileAttachmentByFileId(docId);
        List<AttachmentVo> attachmentVoList = new ArrayList<AttachmentVo>();
        if (null != attachment) {
            for (RepFileAttachmentDto attachmentList : attachment) {
                if (!"1".equals(viewFlog)) {
                    if (!"0".equals(attachmentList.getAvaliable())) {
                        //只能查看小于等于自己密级的文件
                        if (creatorSecurityLevel >= attachmentList.getSecurityLevel()) {
                            AttachmentVo attachmentVo = new AttachmentVo();
                            attachmentVo.setAttachmentName(((RepFileAttachmentDto)attachmentList).getAttachmentName());
                            attachmentVo.setDowmLoadUrl(((RepFileAttachmentDto)attachmentList).getAttachmentURL());
                            attachmentVo.setDocSecurityLevel(secretLevelCodeAndNameMap.get(attachmentList.getSecurityLevel()
                                    + ""));
                            if (attachmentList.getFirstTime() != null) {
                                attachmentVo.setCreateTime(attachmentList.getFirstTime());
                            }
                            else {
                                attachmentVo.setCreateTime(attachmentList.getCreateTime());
                            }
                            if (attachmentList.getFirstFullName() != null) {
                                attachmentVo.setCreateName(attachmentList.getFirstFullName() + "-"
                                        + attachmentList.getFirstName());
                            }
                            else {
                                attachmentVo.setCreateName(attachmentList.getCreateFullName() + "-"
                                        + attachmentList.getCreateName());
                            }
                            attachmentVo.setId(attachmentList.getId());
                            attachmentVoList.add(attachmentVo);
                        }
                    }
                }
                else {
                    if (creatorSecurityLevel >= attachmentList.getSecurityLevel()) {
                        AttachmentVo attachmentVo = new AttachmentVo();
                        attachmentVo.setAttachmentName(((RepFileAttachmentDto)attachmentList).getAttachmentName());
                        attachmentVo.setDowmLoadUrl(((RepFileAttachmentDto)attachmentList).getAttachmentURL());
                        attachmentVo.setDocSecurityLevel(secretLevelCodeAndNameMap.get(attachmentList.getSecurityLevel()
                                + ""));
                        if (attachmentList.getFirstTime() != null) {
                            attachmentVo.setCreateTime(attachmentList.getFirstTime());
                        }
                        else {
                            attachmentVo.setCreateTime(attachmentList.getCreateTime());
                        }
                        if (attachmentList.getFirstFullName() != null) {
                            attachmentVo.setCreateName(attachmentList.getFirstFullName() + "-"
                                    + attachmentList.getFirstName());
                        }
                        else {
                            attachmentVo.setCreateName(attachmentList.getCreateFullName() + "-"
                                    + attachmentList.getCreateName());
                        }
                        attachmentVo.setId(attachmentList.getId());
                        attachmentVoList.add(attachmentVo);
                    }
                }
            }
        }
        String json = JsonUtil.toJsonString(attachmentVoList);
        TagUtil.ajaxResponse(resp, json);
    }

    /**
     * 新建文件页面2
     *
     * @return
     */
    @RequestMapping(params = "goAdd2")
    public ModelAndView goAdd2(HttpServletRequest req, HttpServletResponse resp) {
        String projectId = req.getParameter("projectId");
        String deliverableId = req.getParameter("deliverableId");
        String folderId = "";
        FeignJson fj = projLibService.getfolderIdByDeliverableId(deliverableId,projectId);

        if (fj.isSuccess()) {
            folderId = fj.getObj() == null ? "" :  fj.getObj().toString();
        }

        ProjLibDocumentVo parentVo = new ProjLibDocumentVo();
        String docNamePath = null;

        req.setAttribute("upload", true);
        req.setAttribute("download", true);
        String dictCode = "secretLevel";

        String defaultNameType = "";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curType = tsMap.get(dictCode);
        if (!CommonUtil.isEmpty(curType)) {
            defaultNameType = curType.get(0).getTypecode();
        }
        req.setAttribute("fileSecurityLevel", defaultNameType);

        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectId);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        req.setAttribute("yanfa", "yanfa");
        //增加文件类型，文件类型的初始化数据：
        if(!CommonUtil.isEmpty(deliverableId)){
            DeliverablesInfoDto deliverablesInfo = deliverablesInfoFeignService.getDeliverablesInfoEntity(deliverableId);
            //活动交付项库的名称和id的Map：
            Map<String,String> nameAndIdMap = new HashMap<String, String>();
            List<DeliveryStandardDto> allStartDeliveryStandardList = deliveryStandardFeignService.searchUseableDeliveryStandards(new DeliveryStandardDto());
            for(DeliveryStandardDto curDeliveryStandard : allStartDeliveryStandardList){
                nameAndIdMap.put(curDeliveryStandard.getName(), curDeliveryStandard.getId());
            }
            if(!CommonUtil.isEmpty(deliverablesInfo) && !CommonUtil.isEmpty(deliverablesInfo.getName()) && !CommonUtil.isEmpty(nameAndIdMap.get(deliverablesInfo.getName()))){
                DeliveryStandardDto deliveryStandard = deliveryStandardFeignService.getDeliveryStandardEntity(nameAndIdMap.get(deliverablesInfo.getName()));
                DeliveryStandardDocTypeDto docType = deliveryStandardFeignService.getDeliveryStandardDocTypeById(nameAndIdMap.get(deliverablesInfo.getName()));
                deliveryStandard.setDocTypeId(docType.getRepFileTypeId());
                if(!CommonUtil.isEmpty(docType.getRepFileType())) {
                    deliveryStandard.setDocTypeName(docType.getRepFileType().getFileTypeName());
                }
                req.setAttribute("deliveryStandard", deliveryStandard);
            }
        }
        req.setAttribute("opType", req.getParameter("opType"));
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDoc-add");
    }


    /**
     * 新建文件
     *
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForTemplateLocalDoc")
    @ResponseBody
    public AjaxJson doAddForTemplateLocalDoc(ProjLibDocumentVo document, HttpServletRequest request,
                                             HttpSession session, RepFileTypeDto repFileType) {
        AjaxJson j = new AjaxJson();
        String docattachmentName = request.getParameter("docattachmentName");
        String docattachmentURL = request.getParameter("docattachmentURL");
        String docAttachmentShowName = request.getParameter("docAttachmentShowName");
        String docattachmentNames = "";
        String docattachmentURLs = "";
        String docAttachmentShowNames = "";
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentNames = docattachmentName.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentURLs = docattachmentURL.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docAttachmentShowNames = docAttachmentShowName.trim().toString();
        }

        String type = "文档";
        //需求变更typeid变成CODE 唯一值变更  从CODE变更typeid
        String fileTypeId= "";
        List<RepFileTypeDto> fileTypeList = repService.getRepFileTypesByFileTypeCode(ResourceUtil.getApplicationInformation().getAppKey(), RepFileTypeConstants.REP_FILE_TYPE_PRO);
        if(!CommonUtil.isEmpty(fileTypeList)){
            fileTypeId = fileTypeList.get(0).getId();
        }
        /*String fileTypeId=repFileTypeQueryService.getFileTypeIdByCode(RepFileTypeConstants.REP_FILE_TYPE_PRO);*/
        document.setFileTypeId(fileTypeId);
        message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createSuccess");
        String dictCode = "secretLevel";

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);


        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curData : curTypes) {
            secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
        }
        try {
            if (projLibService.validateReptDocNum(document.getDocNumber())) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.docNumberExist");
                j.setSuccess(false);
                return j;
            }
            document.setType(1);
            String fileId = projLibService.createFile(document,ResourceUtil.getCurrentUser().getId());

            String useObjectId = request.getParameter("useObjectId");
            String useObjectType = request.getParameter("useObjectType");

            @SuppressWarnings("unchecked")
            List<InputsDto> list = new ArrayList<>();
            String inputStr =  (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }

            if(CommonUtil.isEmpty(list)){
                list = new ArrayList<InputsDto>();;
            }

            if (CommonUtils.isNotEmpty(docattachmentNames)) {
                for (int i = 0; i < docattachmentNames.split(",").length; i++ ) {

                    RepFileAttachmentDto fileAttachment = new RepFileAttachmentDto();
                    fileAttachment.setAttachmentName(docattachmentNames.split(",")[i]);
                    fileAttachment.setAttachmentURL(docattachmentURLs.split(",")[i]);
                    fileAttachment.setFileId(fileId);
                    projLibService.addRepFileAttachment(fileAttachment);
                    fileAttachment.setFirstName(fileAttachment.getCreateName());
                    fileAttachment.setFirstFullName(fileAttachment.getCreateFullName());
                    fileAttachment.setFirstTime(fileAttachment.getCreateTime());
                    projLibService.updateFileAttachment(fileAttachment);

                    InputsDto inputs = new InputsDto();
                    inputs.setId(UUID.randomUUID().toString());
                    inputs.setUseObjectId(useObjectId);
                    inputs.setUseObjectType(useObjectType);
                    inputs.setName(docAttachmentShowNames.split(",")[i]);
                    inputs.setOriginType("LOCAL");
                    inputs.setTempId(UUID.randomUUID().toString());
                    inputs.setDocName(docattachmentNames.split(",")[i]);
                    inputs.setDocId(docattachmentURLs.split(",")[i]);
                    list.add(inputs);
                }
                String inpStr = JSON.toJSONString(list);
                redisService.setToRedis("INPUTSLIST", useObjectId,inpStr);

            }
            j.setObj(fileId);
            // 计划提交项操作记录
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addDeliverySuccess");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createFailure");
            j.setSuccess(false);
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForPlanChangeLocalDoc")
    @ResponseBody
    public AjaxJson doAddForPlanChangeLocalDoc(ProjLibDocumentVo document, HttpServletRequest request,
                                               HttpSession session, RepFileTypeDto repFileType) {
        String userId = UserUtil.getCurrentUser().getId();
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        Project project = projectService.getProjectEntity(projectId);
        String projectNo = project.getProjectNumber();
        SerialNumberManager.getDataMap().put("projectNo", projectNo);
        String docattachmentName = request.getParameter("docattachmentName");
        String docattachmentURL = request.getParameter("docattachmentURL");
        String docAttachmentShowName = request.getParameter("docAttachmentShowName");
        String docattachmentNames = "";
        String docattachmentURLs = "";
        String docAttachmentShowNames = "";
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentNames = docattachmentName.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docattachmentURLs = docattachmentURL.trim().toString();
        }
        if (StringUtils.isNotEmpty(docattachmentName)) {
            docAttachmentShowNames = docAttachmentShowName.trim().toString();
        }

        String type = "文档";
        /**
         * 文件类型由RepFileType
         */
        String fileTypeId=feignIdsCommonServiceI.getFileTypeIdByCode(RepFileTypeConstants.REP_FILE_TYPE_PRO);
        document.setFileTypeId(fileTypeId);

        message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createSuccess");
        String dictCode = "secretLevel";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curTypes = tsMap.get(dictCode);

        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
        for (TSTypeDto curData : curTypes) {
            secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
        }
        try {

            if (projLibService.validateReptDocNum(document.getDocNumber())) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.docNumberExist");
                j.setSuccess(false);
                return j;
            }
            document.setType(1);
            String fileId = projLibService.createFile(document,userId);

            String useObjectId = request.getParameter("useObjectId");
            String useObjectType = request.getParameter("useObjectType");

            List<TempPlanInputsDto> list = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");

            if(CommonUtil.isEmpty(list)){
                list = new ArrayList<TempPlanInputsDto>();;
            }

            if (CommonUtils.isNotEmpty(docattachmentNames)) {
                for (int i = 0; i < docattachmentNames.split(",").length; i++ ) {

                    RepFileAttachmentDto fileAttachment = new RepFileAttachmentDto();
                    fileAttachment.setAttachmentName(docattachmentNames.split(",")[i]);
                    fileAttachment.setAttachmentURL(docattachmentURLs.split(",")[i]);
                    fileAttachment.setFileId(fileId);
                    projLibService.addRepFileAttachment(fileAttachment);
                    fileAttachment.setFirstName(fileAttachment.getCreateName());
                    fileAttachment.setFirstFullName(fileAttachment.getCreateFullName());
                    fileAttachment.setFirstTime(fileAttachment.getCreateTime());
                    projLibService.addRepFileAttachment(fileAttachment);
//                    projectService.updateRepFileAttachmentDto(fileAttachment);

                    TempPlanInputsDto inputs = new TempPlanInputsDto();
                    String uuid = UUID.randomUUID().toString();
                    inputs.setId(uuid);
                    inputs.setInputId(uuid);
                    inputs.setUseObjectId(useObjectId);
                    inputs.setUseObjectType(useObjectType);
                    inputs.setName(docAttachmentShowNames.split(",")[i]);
                    inputs.setOriginType("LOCAL");
                    inputs.setDocName(docattachmentNames.split(",")[i]);
                    inputs.setDocId(docattachmentURLs.split(",")[i]);
                    list.add(inputs);
                }
                request.getSession().setAttribute("planChange-inputList", list);

            }
            j.setObj(fileId);
            // 计划提交项操作记录
            String message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addDeliverySuccess");
            try {

            }
            catch (Exception e) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveLogFailure");
                log.error(message, e, null, message);
                Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
                throw new GWException(GWConstants.ERROR_2001, params, e);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createFailure");
            j.setSuccess(false);
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
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
        ProjLibDocumentVo projLibDocumentVo = projLibService.getProjDocmentVoById(id);
        request.setAttribute("projLibDocumentVo", projLibDocumentVo);
        return new ModelAndView("com/glaway/ids/project/projectmanager/projLibDoc-showDetail");
    }

    /**
     * 更改节点顺序
     *
     * @see
     */
    @RequestMapping(params = "changeNodeOrder")
    public void changeNodeOrder(String srcId, String destId, String operate,
                                HttpServletRequest req, HttpServletResponse response) {
        ProjLibDocumentVo dest = projLibService.getProjDocmentVoById(destId);

        if (operate.equals("up") || operate.equals("down")) {
            projLibService.changeEachOtherForVo(srcId, destId);
        }
        else if (operate.equals("left")) {
            // 重新设置父节点，并把orderNum设为orderNum最大值+1
            projLibService.updateTreeOrderNum(srcId, dest.getParentId());

        }
        else if (operate.equals("right")) {
            projLibService.updateTreeOrderNum(srcId, destId);

        }
    }


    /**
     * 查询有用的模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectLibTemplateList")
    @ResponseBody
    public void projectLibTemplateList(HttpServletRequest request, HttpServletResponse response) {

        List<ProjectLibTemplateDto> projectLibTemplateList = projectLibTemplateService.getAllUseProjectLibTemplate();
        DataGridReturn demo = new DataGridReturn(projectLibTemplateList.size(),
                projectLibTemplateList);
        String jonStr = com.alibaba.fastjson.JSONObject.toJSONString(demo);
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验项目编号是否重复
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
            String templeteId = req.getParameter("templeteId");
            String projectId = req.getParameter("projectId");
            FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
            String teamId = String.valueOf(fJson.getObj());
            List<TSRoleDto> list = roleService.getRoleListByTeamId(ResourceUtil.getApplicationInformation().getAppKey(),teamId);
            Map<String, List<RepRoleFileAuthDto>> map = projLibService.getTemplateRoleAuths(
                    projectId, templeteId, list);
            projLibService.applyTemplate(projectId, map, templeteId,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
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
     * 获取版本信息
     *
     * @return
     */
    @RequestMapping(params = "getVersionHistory")
    public void getVersionHistory(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 自定义追加查询条件
            List<ProjLibDocumentVo> reviewFactorTemplateList = projLibService.getVersionHistory(
                    request.getParameter("bizId"), Integer.valueOf(request.getParameter("rows")),
                    Integer.valueOf(request.getParameter("page")), true);
            long count = projLibService.getVersionHistory(request.getParameter("bizId"),
                    Integer.valueOf(request.getParameter("rows")),
                    Integer.valueOf(request.getParameter("page")), false).size();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setVersion(
                    1.0).create();
            String json = gson.toJson(reviewFactorTemplateList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(params = "viewProjectDocDetailForSearch")
    public ModelAndView viewProjectDocDetailForSearch(HttpServletRequest req) {
        String id = req.getParameter("id");

        FeignJson fj = projLibService.getDocumentFileAuths(id,
                UserUtil.getCurrentUser().getId());
        String authStr = String.valueOf(fj.getObj());
        String isView = "0";
        String download = "false";
        for (String auth : authStr.split(",")) {
            if ("detail".equals(auth)) {
                isView = "1";
            }
            if ("download".equals(auth)) {
                download = "true";
            }

        }
        ProjLibDocumentVo projLibDocumentVo = projLibService.getProjDocmentVoById(id);
        if ("shenpi".equals(projLibDocumentVo.getStatus())) {
            req.setAttribute("download", true);
            req.setAttribute("history", true);
        }

        List<TSTypeDto> types = feignSystemService.getTypesByGroupCode(ResourceUtil.getApplicationInformation().getAppKey(),
                ProjectConstants.PROJ_LIB_SEC_GROUPCODE, projLibDocumentVo.getSecurityLevel());

     /*   List<TSTypeDto> types = projLibService.getTypesByGroupCode(
                ProjectConstants.PROJ_LIB_SEC_GROUPCODE, projLibDocumentVo.getSecurityLevel());*/

        if (!CommonUtil.isEmpty(types)) {
            projLibDocumentVo.setSecurityLevel(types.get(0).getTypename());
            projLibDocumentVo.setCreateTimeStr(DateUtil.dateToString(
                    projLibDocumentVo.getCreateTime(), DateUtil.FORMAT_ONE));
            projLibDocumentVo.setUpdateTimeStr(DateUtil.dateToString(
                    projLibDocumentVo.getUpdateTime(), DateUtil.FORMAT_ONE));
        }
        if ("0".equals(isView)) {
            return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibWarnPage");
        }
        FeignJson fJson = repService.getLifeCycleListStr(ResourceUtil.getApplicationInformation().getAppKey(),new RepFileDto());
        String lifeCycleListStr = fJson.getObj().toString();
        req.setAttribute("download", download);
        req.setAttribute("viewFlog", "1");
        req.setAttribute("isView", isView);
        req.setAttribute("lifeCycleList", lifeCycleListStr);
        req.setAttribute("doc", projLibDocumentVo);
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLibDetail");
    }

}
