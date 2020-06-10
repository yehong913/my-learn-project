/*
 * 文件名：ProjLibTemplateController.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2016年6月29日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.auth.ProjLibAuthManager;
import com.glaway.ids.config.auth.ProjectLibraryAuthorityEnum;
import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.config.dto.ProjectLibTemplateFileCategoryDto;
import com.glaway.ids.project.projectmanager.service.ProjectLibTemplateRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;


/**
 * 项目库权限模板
 * 
 * @author blcao
 * @version 2016年6月29日
 * @see ProjectLibTemplateController
 * @since
 */
@Controller
@RequestMapping("/projectLibTemplateController")
public class ProjectLibTemplateController {

    /**
     * 消息信息
     */
    private String message = "";

    /**
     * 项目库权限模板处理
     */
    @Autowired
    private ProjectLibTemplateRemoteFeignServiceI projectLibTemplateService;

    @Autowired
    private FeignRoleService roleService;



    /**
     * 详情页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        ProjectLibTemplateDto projectLibTemplate = projectLibTemplateService.getProjLibTemplateEntity(templateId);
        FeignJson fj = projectLibTemplateService.getTemplateCategoryRootNodeId(templateId);
        String rootId = String.valueOf(fj.getObj());
        request.setAttribute("name", projectLibTemplate.getName());
        request.setAttribute("remark", projectLibTemplate.getRemark());
        request.setAttribute("templateId", templateId);
        request.setAttribute("rootId", rootId);
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-viewDetail");
    }


    /**
     * 权限列表
     *
     * @return
     */
    @RequestMapping(params = "goPowerList")
    public ModelAndView goPowerList(HttpServletRequest request, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("com/glaway/ids/config/projectLibTemplate-power");
        String isView = request.getParameter("isView");
        String fileId = request.getParameter("fileId");
        String templateId = request.getParameter("templateId");
        List<TSRoleDto> roleList = roleService.getCommonRole();
        List<String> authActionCodeList = ProjLibAuthManager.getAllAuthActionCode();
        Map<String, String> map = ProjectLibraryAuthorityEnum.getAuthNameCodeMap();
        List<RepFileAuthVo> repList = new ArrayList<RepFileAuthVo>();
        List<RepFileAuthVo> rList = new ArrayList<RepFileAuthVo>();
        for (TSRoleDto role : roleList) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setId(role.getRoleCode());
            r.setFileId(fileId);
            r.setRoleCode(role.getRoleCode());
            r.setRoleName(role.getRoleName());
            r.setRoleId(role.getId());
            repList.add(r);
        }

        for (String code : authActionCodeList) {
            RepFileAuthVo r = new RepFileAuthVo();
            r.setCheckName(map.get(code));
            r.setCheckValue(code);
            rList.add(r);
        }
        String checks = "";
        List<ProjLibRoleFileAuthVo> plrfVoList = projectLibTemplateService.getProjLibTemplateRoleFileAuths(fileId);
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
     * 项目库权限模板菜单树
     *
     * @return
     */
    @RequestMapping(params = "getProjectLibTemplateTree")
    public void getProjectLibTemplateTree(HttpServletRequest request, HttpServletResponse response) {
        String projectLibTemplateId = request.getParameter("templateId");
        List<TreeNode> list = new ArrayList<TreeNode>();
        if (StringUtils.isNotEmpty(projectLibTemplateId)) {
            ProjectLibTemplateDto projectLibTemplate = projectLibTemplateService.getProjLibTemplateEntity(projectLibTemplateId);
            if (null != projectLibTemplate) {
                list = projectLibTemplateService.getProjectLibTemplateFileCategorys(projectLibTemplateId);
                String json = JSONArray.toJSONString(list);
                TagUtil.ajaxResponse(response, json);
            }
        }
    }


    /**
     * 列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goProjectLibTemplate")
    public ModelAndView goProjectLibTemplate(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/config/projectLibTemplateList");
    }

    /**
     * 删除项目权限模板
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelete")
    @ResponseBody
    public AjaxJson doDelete(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        try {
            projectLibTemplateService.deleteProjectLibTemplateByIds(ids);
            message = I18nUtil.getValue("com.glaway.ids.config.projectLibTemplate.deleteSuccess");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.deleteError");
            e.printStackTrace();
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    @RequestMapping(params = "searchDatagrid")
    @ResponseBody
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        if (StringUtil.isNotEmpty(request.getParameter("projectLibTemplateCreator"))) {
            String createName = request.getParameter("projectLibTemplateCreator");
            params.put("userName", createName);
        }
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);

        Map<String,Object> map = new HashMap<>();
        map.put("conditionList",conditionList);
        map.put("params",params);

        PageList pageList = projectLibTemplateService.queryProjectLibTemplates(map);
        long count = pageList.getCount();
        List<ProjectLibTemplateDto> list = pageList.getResultList();
        String json = JsonUtil.getListJsonWithoutQuote(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    /**
     * 启用或禁用项目库权限模板
     *
     * @param ids
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doStartOrStop")
    @ResponseBody
    public AjaxJson doStartOrStop(String ids, HttpServletRequest request) {
        String status = request.getParameter("status");
        AjaxJson j = new AjaxJson();
        String msg = "";
        if (status.equals("1")) {
            msg = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateStart");
        }
        else {
            msg = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateStop");
        }
        try {
            FeignJson fj = projectLibTemplateService.startOrStopTemplateByIds(ids, status,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            message = msg + fj.getMsg();
        }
        catch (Exception e) {
            message = msg + I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.error");
            e.printStackTrace();
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-add");
    }

    /**
     * 添加项目库权限模板
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.addSuccess");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        try {

            FeignJson fj = projectLibTemplateService.saveProjectLibTemplate(name,remark,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            if(!fj.isSuccess()){
                j.setSuccess(false);
                message = fj.getMsg();
            }
            j.setObj(fj.getObj());

        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.addError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 权限管理
     *
     * @return
     */
    @RequestMapping(params = "goLibMenu")
    public ModelAndView goLibMenu(HttpServletRequest request, HttpServletResponse response) {
        String templateId = request.getParameter("templateId");
        request.setAttribute("templateId", templateId);
        FeignJson fj = projectLibTemplateService.getTemplateCategoryRootNodeId(templateId);
        String rootId = String.valueOf(fj.getObj());
        List<String> authActionCodeList = ProjLibAuthManager.getAllAuthActionCode();
        request.setAttribute("splitFlag", authActionCodeList.size());
        request.setAttribute("rootId", rootId);
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-lib");
    }


    /**
     * 权限页面切换校验
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doCheck")
    @ResponseBody
    public AjaxJson doCheck(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String resultStr = request.getParameter("resultStr");
        String templateId = request.getParameter("templateId");
        String fileId = request.getParameter("fileId");
        List<TSRoleDto> roleList = roleService.getCommonRole();
        List<RepFileAuthVo> rfaVoList = new ArrayList<RepFileAuthVo>();
        String[] resultArr = resultStr.split(",");
        for (int resultArrI = 0; resultArrI < resultArr.length; resultArrI++ ) {
            RepFileAuthVo vo = new RepFileAuthVo();
            vo.setRoleId(roleList.get(resultArrI).getId());
            vo.setCheckValue(resultArr[resultArrI]);
            rfaVoList.add(vo);
        }
        try {
            boolean isChange = projectLibTemplateService.checkRoleFileAuthExistChange(templateId,
                    fileId, rfaVoList);
            if (isChange) {
                j.setSuccess(true);
            }
            else {
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
        }
        finally {
            return j;
        }
    }

    /**
     * 添加项目库权限模板
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.addSuccess");
        String resultStr = request.getParameter("resultStr");
        String templateId = request.getParameter("templateId");
        String fileId = request.getParameter("fileId");
        List<TSRoleDto> roleList = roleService.getCommonRole();
        List<RepFileAuthVo> rfaVoList = new ArrayList<RepFileAuthVo>();
        String[] resultArr = resultStr.split(",");
        for (int resultArrI = 0; resultArrI < resultArr.length; resultArrI++ ) {
            RepFileAuthVo vo = new RepFileAuthVo();
            vo.setRoleId(roleList.get(resultArrI).getId());
            vo.setCheckValue(resultArr[resultArrI]);
            rfaVoList.add(vo);
        }
        try {
            FeignJson fj = projectLibTemplateService.saveProjLibRoleFileAuth(templateId, fileId, rfaVoList,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());;
            j.setSuccess(true);
            message = "权限模板添加"+fj.getMsg();
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.addError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增树节点页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddTreeNode")
    public ModelAndView goAddTreeNode(HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String templateId = request.getParameter("templateId");
        request.setAttribute("parentId", parentId);
        request.setAttribute("templateId", templateId);
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-addTreeNode");
    }

    /**
     * 新增树节点
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddTreeNode")
    @ResponseBody
    public AjaxJson doAddTreeNode(HttpServletRequest request) {
        String name = request.getParameter("name");
        String parentId = request.getParameter("parentId");
        String templateId = request.getParameter("templateId");
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.treeAddSuccess");
        try {

            FeignJson fj = projectLibTemplateService.doAddTreeNode(name,parentId,templateId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            j.setObj(fj.getObj());
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.treeAddError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 修改树节点页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdateTreeNode")
    public ModelAndView goUpdateTreeNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        String templateId = request.getParameter("templateId");
        ProjectLibTemplateFileCategoryDto ptfc = projectLibTemplateService.getProjectLibTemplateCategoryEntity(id);
        request.setAttribute("name", ptfc.getName());
        request.setAttribute("id", id);
        request.setAttribute("templateId", templateId);
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-updateTreeNode");
    }

    /**
     * 修改树节点
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateTreeNode")
    @ResponseBody
    public AjaxJson doUpdateTreeNode(HttpServletRequest request) {
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.treeUpdateSuccess");
        try {

            FeignJson fj = projectLibTemplateService.doUpdateTreeNode(id,name,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());


        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.config.projectLibTemplate.treeUpdatError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 检查树节点是否含有子节点
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkTreeNode")
    @ResponseBody
    public AjaxJson checkTreeNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        AjaxJson j = new AjaxJson();
        try {
            boolean haveChildren = projectLibTemplateService.checkCategoryExistChildNode(id);
            if (haveChildren) {
                j.setSuccess(true);
            }
            else {
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
        }
        finally {
            return j;
        }
    }


    /**
     * 删除树节点
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDeleteTreeNode")
    @ResponseBody
    public AjaxJson doDeleteTreeNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.treedeleteSuccess");
        try {
            projectLibTemplateService.deleteProjectLibTemplateFileCategory(id);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.treedeleteError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 移动树节点
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "moveTreeNode")
    @ResponseBody
    public AjaxJson moveTreeNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String targetId = request.getParameter("targetId");
        String moveType = request.getParameter("moveType");
        AjaxJson j = new AjaxJson();
        try {

            FeignJson fj = projectLibTemplateService.doMoveNode(id,name,targetId, moveType,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            message = "";
            if (String.valueOf(fj.getMsg()).length() > 0) {
                j.setSuccess(false);
                message = fj.getMsg();
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.moveError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 修改页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        ProjectLibTemplateDto projectLibTemplate = projectLibTemplateService.getProjLibTemplateEntity(templateId);
        request.setAttribute("templateId", templateId);
        request.setAttribute("name", projectLibTemplate.getName());
        request.setAttribute("remark", projectLibTemplate.getRemark());
        return new ModelAndView("com/glaway/ids/config/projectLibTemplate-update");
    }



    /**
     * 修改项目库权限模板
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.updateSuccess");
        String templateId = request.getParameter("templateId");
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        try {

            FeignJson fj = projectLibTemplateService.doUpdateProjectLibTemplate(templateId,name,remark,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            if(!fj.isSuccess()){
                j.setSuccess(false);
                message = fj.getMsg();
            }
            j.setObj(fj.getObj());
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.config.projectLibTemplate.updateError");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

}
