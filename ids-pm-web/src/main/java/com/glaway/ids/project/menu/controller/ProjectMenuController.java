package com.glaway.ids.project.menu.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.project.menu.service.ProjectMenuRemoteFeignServiceI;
import com.glaway.ids.project.menu.service.RecentlyProjectRemoteFeignServiceI;
import com.google.gson.Gson;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @Title: projectMenuController
 * @Description: 项目菜单
 * @author wangshen
 * @version V1.0
 */
@Controller
@RequestMapping("/projectMenuController")
public class ProjectMenuController extends BaseController {


    /**
     * 最近访问项目
     */
    @Autowired
    private RecentlyProjectRemoteFeignServiceI recentlyProjectService;


    /**
     * 左侧项目列表树
     */
    @Autowired
    private ProjectMenuRemoteFeignServiceI projectMenuService;


    /**
     * 消息信息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 项目树列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectMenu")
    public ModelAndView projectMenu(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }

        // 项目分析、项目看板、人员负载分析、风险类研发任务等来源，需要跳转至项目管理页面中的不同页签
        String fromType = request.getParameter("fromType");
        if (StringUtils.isNotEmpty(fromType)) {
            request.setAttribute("fromType", fromType);
        }
        
        request.setAttribute("iframeFlag", request.getParameter("iframeFlag"));
        
        // 我的任务计划分解时，需要跳转到项目管理页面，通过planId判断直接打开计划列表页面，使计划高亮展示
        String taskDetailGetPlanId = request.getParameter("taskDetailGetPlanId");
        if (StringUtils.isNotEmpty(taskDetailGetPlanId)) {
            request.setAttribute("taskDetailGetPlanId", taskDetailGetPlanId);
        }

        return new ModelAndView("com/glaway/ids/pm/project/menu/projectMenuList");
    }


    /**
     * Description: <br>获得项目树
     *
     * @param request
     * @param response
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @see
     */
    @RequestMapping(params = "list")
    @ResponseBody
    public void list(HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String insertProjectId = request.getParameter("insertProjectId");
        if (StringUtils.isNotEmpty(insertProjectId)) { // 更新项目最近访问记录
            recentlyProjectService.updateRecentlyByProjectId(insertProjectId,UserUtil.getInstance().getUser());
        }

        String projectId = request.getParameter("projectId"); // 重新加载右侧项目树，如果projectId不为空，则只加载该项目
        String currentUserId = UserUtil.getInstance().getUser().getId();
        String listStr = "";
        FeignJson fj = projectMenuService.constructionProjectMenuTree(projectId,currentUserId);
        if (fj.isSuccess()) {
            listStr = fj.getObj() == null ? "" : fj.getObj().toString();
        }
        /*List<TreeNode> list = JSON.parseObject(JsonFromatUtil.formatJsonToList(listStr),new TypeReference<List<TreeNode>>(){});
        String json = new Gson().toJson(list);*/
        TagUtil.ajaxResponse(response, listStr);
    }


}
