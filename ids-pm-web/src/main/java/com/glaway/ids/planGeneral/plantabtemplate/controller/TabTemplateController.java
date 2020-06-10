package com.glaway.ids.planGeneral.plantabtemplate.controller;

import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.RequestMapUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 计划通用化--页签模版管理
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Controller
@RequestMapping("/tabTemplateController")
public class TabTemplateController extends BaseController {

    //页签模版管理Service
    @Autowired
    private TabTemplateServiceI tabTemplateServiceImpl;

    /**
     * 功能描述：跳转页签模版查询页面
     * @return ModelAndView
     */
    @RequestMapping(params = "tabTemplateInfoManagePage")
    public ModelAndView tabTemplateInfo(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplateInfoManagePage");
    }

    /**
     * 功能描述：根据查询条件展示列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        //获取查询条件
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        tabTemplateServiceImpl.searchDatagrid(conditionList, response);
    }

    /**
     * 功能描述：批量/单条 启用或禁用页签模版
     * @param ids id集合(“，”分隔)
     * @param status 状态(启用“1”或者禁用“0”)
     * @return AjaxJson
     */
    @RequestMapping(params = "doStartOrStop")
    @ResponseBody
    public AjaxJson doStartOrStop(String ids, String status){
        return tabTemplateServiceImpl.doStartOrStop(ids, status);
    }

    /**
     * 功能描述：批量/单条 删除页签模版
     * @param ids id集合(“，”分隔)
     * @return AjaxJson
     */
    @RequestMapping(params = "doBatchDelete")
    @ResponseBody
    public AjaxJson doBatchDelete(String ids){
        return tabTemplateServiceImpl.doBatchDelete(ids);
    }

    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */
    @RequestMapping(params = "goAddTabTemplateInfo")
    public ModelAndView goAddTabTemplateInfo() {
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplateInfoManage-add");
    }

    /**
     * 功能描述：跳转页签模版修改页面
     * @param id
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "goTebTemplateUpdate")
    public ModelAndView goTebTemplateUpdate(HttpServletRequest request, String id) {
        TabTemplateDto dto = tabTemplateServiceImpl.queryInfoById(id);
        request.setAttribute("tebDto", dto);
        request.setAttribute("msg", "页签模板修改成功");
        request.setAttribute("updateOrRevise", "update");
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplateInfoManage-updateOrRevise");
    }

    /**
     * 功能描述：跳转页签模版修改页面
     * @param id
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "goTebTemplateRevise")
    public ModelAndView goTebTemplateRevise(HttpServletRequest request, String id) {
        TabTemplateDto dto = tabTemplateServiceImpl.queryInfoById(id);
        request.setAttribute("tebDto", dto);
        request.setAttribute("msg", "页签模板修订成功");
        request.setAttribute("updateOrRevise", "revise");
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplateInfoManage-updateOrRevise");
    }

    /**
     * 功能描述：跳转页签模版复制页面
     * @param id
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "goTebTemplateCopy")
    public ModelAndView goTebTemplateCopy(HttpServletRequest request, String id) {
        TabTemplateDto dto = tabTemplateServiceImpl.copyEntity(id);
        request.setAttribute("tebDto", dto);
        request.setAttribute("msg", "页签模板复制成功");
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplateInfoManage-updateOrCopy");
    }

    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(HttpServletRequest request,TabTemplateDto dto){
        return tabTemplateServiceImpl.doSave(request, dto);
    }

    /**
     * 功能描述：跳转页签模版新增页面
     * @return ModelAndView
     */      
    @RequestMapping(params = "goTabView")
    public ModelAndView goTabView(HttpServletRequest request) {
        String id = request.getParameter("id");
        String displayUsage = request.getParameter("displayUsage");
        request.setAttribute("displayUsage",displayUsage);
        if(!CommonUtil.isEmpty(displayUsage) && "2".equals(displayUsage)) {
            TabTemplateDto dto = tabTemplateServiceImpl.queryInfoById(id);
            if(!CommonUtil.isEmpty(dto)) {
                String url = dto.getExternalURL();
                String url1 = url.split("&")[0];
                dto.setExternalURL(url1);
            request.setAttribute("tabTemplateDto",dto); 
            }
        }
        //数据查询
        List<List<List<ObjectPropertyInfoDto>>> lists = tabTemplateServiceImpl.goTabView(request, id);
        request.setAttribute("lists",lists);
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tab-view");
    }

    /**
     * 功能描述：判断名称是否重复
     * @param name
     * @param id
     * @return AjaxJson
     */
    @RequestMapping(params = "isRepeatName")
    @ResponseBody
    public AjaxJson isRepeatName(String name, String id){
        AjaxJson ajaxJson = tabTemplateServiceImpl.isRepeatName(name, id);
        return ajaxJson;
    }

    /**
     * 提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goTabTemSubmitApprove")
    public ModelAndView goSubmitApproveForList(HttpServletRequest req) {
        String tabId = req.getParameter("tabId");
        req.setAttribute("tabId", tabId);
        req.setAttribute("supportFlag", "approve");
        return new ModelAndView("com/glaway/ids/project/plantemplate/tabTemplate-submitApproveForList");
    }

    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    @RequestMapping(params = "doUpdateOrRevise")
    @ResponseBody
    public AjaxJson doUpdateOrRevise(HttpServletRequest request,TabTemplateDto dto){
        String updateOrRevise = request.getParameter("updateOrRevise");
        return tabTemplateServiceImpl.doUpdateOrRevise(updateOrRevise, dto);
    }

    /**
     * 功能描述：撤回
     * @return AjaxJson
     */
    @RequestMapping(params = "doRevoke")
    @ResponseBody
    public FeignJson doRevoke(HttpServletRequest request){
        String id = request.getParameter("id");
        String bizId = request.getParameter("bizId");
        String type = request.getParameter("type");
        String bizVersion = request.getParameter("bizVersion");
        String userId = ResourceUtil.getCurrentUser().getId();
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        Map<String,String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("bizId", bizId);
        params.put("bizVersion", bizVersion);
        params.put("userId", userId);
        params.put("orgId", orgId);
        params.put("type", type);
        return tabTemplateServiceImpl.doRevoke(params);
    }

    /**
     * 功能描述：回退
     * @return FeignJson
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public FeignJson doBack(HttpServletRequest request) {
        String id = request.getParameter("id");
        String bizId = request.getParameter("bizId");
        String type = request.getParameter("type");
        String bizVersion = request.getParameter("bizVersion");
        String userId = ResourceUtil.getCurrentUser().getId();
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        Map<String,String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("bizId", bizId);
        params.put("bizVersion", bizVersion);
        params.put("userId", userId);
        params.put("orgId", orgId);
        params.put("type", type);
        FeignJson j = tabTemplateServiceImpl.doBack(params);
        return j;
    }

    /**
     * 功能描述：提交审批
     * @params
     */
    @RequestMapping(params = "doSubmitApprove")
    @ResponseBody
    public FeignJson doSubmitApprove(HttpServletRequest req) {
        String tabId = req.getParameter("tabId");
        String leader = req.getParameter("leader");
        String deptLeader = req.getParameter("deptLeader");
        String userId = ResourceUtil.getCurrentUser().getId();
        String orgId = ResourceUtil.getCurrentUserOrg().getId();
        Map<String,String> map = new HashMap<String, String>();
        map.put("tabId",tabId);
        map.put("leader",leader);
        map.put("deptLeader",deptLeader);
        map.put("userId",userId);
        map.put("orgId",orgId);
        FeignJson j = tabTemplateServiceImpl.doSubmitApprove(map);
        return j;
    }

    /**
     * 功能描述：版本页面跳转
     * @return
     */
    @RequestMapping(params = "goVersionDetail")
    public ModelAndView goVersionDetail(HttpServletRequest request) {
        String id = request.getParameter("tabId");
        TabTemplateDto dto = tabTemplateServiceImpl.queryInfoById(id);
        request.setAttribute("tabTemplate", dto);
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplate-showHistory");
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
        FeignJson fj = tabTemplateServiceImpl.getVersionDatagridStr(bizId, Integer.valueOf(req.getParameter("rows")),
            Integer.valueOf(req.getParameter("page")));
        Map<String,Object> attributes = new HashMap<>();
        if (fj.isSuccess()) {
            attributes = fj.getAttributes();
            List<TabTemplateDto> tabTemplateDtoList = (List<TabTemplateDto>) attributes.get("list");
            long count = Long.valueOf(attributes.get("count").toString());
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setVersion(
                1.0).create();
            String json = gson.toJson(tabTemplateDtoList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
    }

    /**
     * 功能描述：版本
     * @return
     */
    @RequestMapping(params = "goVersionDetailHistory")
    public ModelAndView goVersionDetailHistory(HttpServletRequest request) {
        String id = request.getParameter("tabId");
        TabTemplateDto dto = tabTemplateServiceImpl.queryInfoById(id);
        request.setAttribute("tabTemplate", dto);
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/tabTemplate-versionHistoryDetail");
    }
}
