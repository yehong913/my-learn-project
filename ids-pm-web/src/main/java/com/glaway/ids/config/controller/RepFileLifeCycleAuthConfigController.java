/*
 * 文件名：StandardProblemController.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：syc
 * 修改时间：2016年5月3日
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.glaway.ids.config.auth.ProjLibAuthManager;
import com.glaway.ids.config.auth.ProjectLibraryAuthorityEnum;
import com.glaway.ids.config.constant.CommonConstants;
import com.glaway.ids.config.entity.RepFile;
import com.glaway.ids.config.service.RepFileLifeCycleAuthConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaway.foundation.businessobject.initrule.service.BusinessObjectInitRuleServiceI;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.system.lifecycle.entity.LifeCyclePermission;
import com.glaway.foundation.system.lifecycle.entity.LifeCyclePolicy;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.system.lifecycle.service.LifeCyclePermissionServiceI;
import com.glaway.foundation.system.lifecycle.service.LifeCyclePolicyServiceI;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;


/**
 * 问题名称Controller
 * 项目库文档生命周期权限配置
 * 
 * @author blcao
 * @version 2016年7月4日
 * @see RepFileLifeCycleAuthConfigController
 * @since
 */
@Controller
@RequestMapping("/repFileLifeCycleAuthConfigController")
public class RepFileLifeCycleAuthConfigController {
    /**
     * 操作日志信息
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(RepFileLifeCycleAuthConfigController.class);

    /**
     * 注入生命周期策略服务接口
     */
    @Autowired
    private LifeCyclePolicyServiceI lifeCyclePolicyService;

    /**
     * 生命周期状态服务接口<br>
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;

    /**
     * 注入businessObject初始化规则服务接口
     */
    @Autowired
    private BusinessObjectInitRuleServiceI initRuleService;

    /**
     * 注入生命周期权限策略服务接口<br>
     */
    @Autowired
    private LifeCyclePermissionServiceI permissionService;

    /**
     * 
     */
    @Autowired
    private SessionFacade sessionFacade;

    @Autowired
    private RepFileLifeCycleAuthConfigRemoteFeignServiceI repFileLifeCycleAuthConfigRemoteFeignService;

    /**
     * 返回给页面的消息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 加载明细列表[lifecyclestatus]
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "repLifeCycleStatusAuthConfig")
    public ModelAndView repLifeCycleStatusAuthConfig(LifeCyclePolicy lifeCyclePolicy,
                                                     HttpServletRequest req) {

        String lifeCyclePolicyEntityStr = repFileLifeCycleAuthConfigRemoteFeignService.queryLifeCyclePolicyList(CommonConstants.REPFILE_LIFECYCLE_POLICY_NAME);
        List<LifeCyclePolicy> lifeCyclePolicyEntityList  = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeCyclePolicyEntityStr),new TypeReference<List<LifeCyclePolicy>>(){});
        if (!CommonUtil.isEmpty(lifeCyclePolicyEntityList)) {
            String policyId = lifeCyclePolicyEntityList.get(0).getId();
            try {
                String lifeCycleStatusEntityStr = repFileLifeCycleAuthConfigRemoteFeignService.queryLifeCycleStatusEntityList(policyId);
                List<LifeCycleStatus> lifeCycleStatusEntityList = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeCycleStatusEntityStr),new TypeReference<List<LifeCycleStatus>>(){});
                req.setAttribute("lifeCycleStatusList", lifeCycleStatusEntityList);
                List<LifeCyclePermission> permissionList = permissionService.getPermissionListByPolicyId(
                        policyId, RepFile.class);
                Map<String, HashMap<ProjectLibraryAuthorityEnum, Boolean>> permissionMap = new HashMap<String, HashMap<ProjectLibraryAuthorityEnum, Boolean>>();
                for (LifeCyclePermission permission : permissionList) {
                    String permissionCode = permission.getPermissionCode();
                    // 根据permissionCode查找所有的授权行为
                    HashMap<ProjectLibraryAuthorityEnum, Boolean> perm = (HashMap<ProjectLibraryAuthorityEnum, Boolean>) ProjLibAuthManager.getActionMapByPermissionCode(Long.valueOf(permissionCode));
                    permissionMap.put(permission.getStatusName(), perm);
                }
                req.setAttribute("permissionMap", permissionMap);
            }
            catch (Exception e) {
                log.error(message, e);
            }

        }
        return new ModelAndView("com/glaway/ids/pm/config/repLifeCycleStatusAuthConfig");
    }


    /**
     * 更新life_cycle_policy
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveConfig")
    @ResponseBody
    public AjaxJson saveConfig(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.repFileLifeCycleAuthConfig.saveSuccess");
        try {
            // 保存生命周期权限
            String permissions = request.getParameter("permissions");
            String objectClass = request.getParameter("objectClass");
            if (permissions != null && !"".equals(permissions)) {
                // permissionArray是一组生命周期状态的权限
                JSONArray permissionArray = JSON.parseArray(permissions);
                for (int i = 0; i < permissionArray.size(); i++ ) {
                    // 根据顺序找到每一个状态的一组值
                    JSONObject permissionObj = permissionArray.getJSONObject(i);
                    String statusName = permissionObj.getString("statusName");
                    JSONArray actions = permissionObj.getJSONArray("actions");
                    // 遍历所有行为，组成一条permissionCode
                    if (actions != null) {
                        List<ProjectLibraryAuthorityEnum> actionList = new ArrayList<ProjectLibraryAuthorityEnum>();
                        for (int k = 0; k < actions.size(); k++ ) {
                            JSONObject actionObj = actions.getJSONObject(k);
                            boolean checked = actionObj.getBoolean("checked");
                            if (checked) {
                                ProjectLibraryAuthorityEnum action = ProjectLibraryAuthorityEnum.valueOfActionCode(actionObj.getString("actionCode"));
                                if (action != null) {
                                    actionList.add(action);
                                }
                            }
                        }
                        long permissionCode = ProjLibAuthManager.encodeAuthorityActions(actionList);
                        // 保存这条permissionCode
                        LifeCyclePermission permission = new LifeCyclePermission();
                        permission.setObjectClass(objectClass);
                        permission.setPolicyName(CommonConstants.REPFILE_LIFECYCLE_POLICY_NAME);
                        permission.setStatusName(statusName);
                        List<LifeCyclePermission> permissionList = permissionService.findByExample(permission);
                        // 如果有数据修改，否则新增
                        if (permissionList != null && permissionList.size() > 0) {
                            permission = permissionList.get(0);
                        }
                        permission.setPermissionCode(String.valueOf(permissionCode));
                        permissionService.update(permission);
                    }
                }
            }
            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.repFileLifeCycleAuthConfig.saveError");
            j.setSuccess(false);
            log.error(message, e);
            Object[] params = new Object[] {message, e.getMessage()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


}
