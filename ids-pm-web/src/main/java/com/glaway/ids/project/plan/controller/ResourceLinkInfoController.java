package com.glaway.ids.project.plan.controller;


import com.alibaba.fastjson.JSONArray;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.TempPlanResourceLinkInfoDto;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.plan.vo.CheckResourceUsedRateVO;
import com.glaway.ids.project.plan.vo.ResourceUsedRateVO;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 资源
 * @author blcao
 * @date 2015-03-30 09:12:53
 * @version V1.0
 */
@Controller
@RequestMapping("/resourceLinkInfoController")
public class ResourceLinkInfoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ResourceLinkInfoController.class);


    /**
     * 资源
     */
    @Autowired
    private ResourceRemoteFeignServiceI resourceService;


    @Autowired
    private ResourceFolderRemoteFeignServiceI resourceFolderService;

    /**
     * 
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Autowired
    private PlanRemoteFeignServiceI planService;

    @Autowired
    private ProjectRemoteFeignServiceI projectFeignService;


    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoService;

    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkService;

    /**
     * 修改时判断资源时间收敛于计划
     * 
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "pdResource")
    @ResponseBody
    public AjaxJson pdResource(PlanDto plan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotEmpty(plan.getId())) {
            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setUseObjectId(plan.getId());
            List<ResourceLinkInfoDto> reInfoList = resourceLinkInfoService.queryResourceList(
                resourceLinkInfo, 1, 10, false);
            List<PlanDto> ParentPlanList = planService.getPlanAllChildren(plan);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String time1 = sdf.format(plan.getPlanStartTime());
            String time2 = sdf.format(plan.getPlanEndTime());
            for (ResourceLinkInfoDto info : reInfoList) {
                String time3 = sdf.format(info.getStartTime());
                String time4 = sdf.format(info.getEndTime());
                if ((Integer.parseInt(time4) > Integer.parseInt(time2))
                    || (Integer.parseInt(time1) > Integer.parseInt(time3))) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {info.getResourceInfo().getName(),
                            sdf2.format(info.getStartTime()), sdf2.format(info.getEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.project.plan.resource.pdResourceOne", arguments));
                    break;
                }
            }

            for (PlanDto p : ParentPlanList) {
                if (!plan.getId().equals(p.getId())) {
                    String time3 = sdf.format(p.getPlanStartTime());
                    String time4 = sdf.format(p.getPlanEndTime());
                    if ((Integer.parseInt(time4) > Integer.parseInt(time2))
                        || (Integer.parseInt(time1) > Integer.parseInt(time3))) {
                        AjaxJson j2 = new AjaxJson();
                        j2.setSuccess(false);
                        Object[] arguments = new String[] {p.getPlanName(),
                                sdf2.format(p.getPlanStartTime()), sdf2.format(p.getPlanEndTime())};
                        j2.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.project.plan.resource.checkChildrenTime", arguments));
                        j = j2;
                        break;
                    }
                }
            }
        }
        return j;
    }



    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    public void list(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                     HttpServletResponse response) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        if (resourceLinkInfo != null && StringUtils.isNotEmpty(resourceLinkInfo.getUseObjectId())
                && StringUtils.isNotEmpty(resourceLinkInfo.getUseObjectType())) {
            List<ResourceLinkInfoDto> resourceLinkInfoList2 = resourceLinkInfoService.queryResourceList(
                    resourceLinkInfo, 1, 10, false);
            List<ResourceDto> list = resourceService.getAllResourceInfos();
            Map<String, String> resourcePathMap = new HashMap<String, String>();
            Map<String, String> resourceNameMap = new HashMap<String, String>();
            for (ResourceDto resource : list) {
                resourcePathMap.put(resource.getId(), resource.getPath());
                resourceNameMap.put(resource.getId(), resource.getName());
            }
            for (ResourceLinkInfoDto info : resourceLinkInfoList2) {
                if (info.getResourceInfo() != null) {
                    info.setResourceName(resourceNameMap.get(info.getResourceId()));
                    info.setResourceType(resourcePathMap.get(info.getResourceId()));
                }

            }
            DataGridReturn data = new DataGridReturn(resourceLinkInfoList2.size(),
                    resourceLinkInfoList2);
            String json = gson.toJson(data);
            TagUtil.ajaxResponse(response, json);

        }
    }

    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "listForPlan")
    public void listForPlan(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                     HttpServletResponse response) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
        List<ResourceDto> list = resourceService.getAllResourceInfos();
        Map<String, String> resourcePathMap = new HashMap<String, String>();
        Map<String, String> resourceNameMap = new HashMap<String, String>();
        for (ResourceDto resource : list) {
            resourcePathMap.put(resource.getId(), resource.getPath());
            resourceNameMap.put(resource.getId(), resource.getName());
        }
        for (ResourceLinkInfoDto info : resourceLinkInfoList2) {
            info.setResourceName(resourceNameMap.get(info.getResourceId()));
            info.setResourceType(resourcePathMap.get(info.getResourceId()));
            for(ResourceDto resource : list){
                if(info.getResourceId().equals(resource.getId())){
                    info.setResourceInfo(resource);
                    break;
                }
            }
        }
        DataGridReturn data = new DataGridReturn(resourceLinkInfoList2.size(),
                resourceLinkInfoList2);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }



    /**
     * 资源新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest req) {
        try {
            String useObjectId = req.getParameter("useObjectId");
            String useObjectType = req.getParameter("useObjectType");
            String planStartTime = req.getParameter("planStartTime");
            String planEndTime = req.getParameter("planEndTime");
            req.getSession().setAttribute("planStartTime", planStartTime);
            req.getSession().setAttribute("planEndTime", planEndTime);
            req.getSession().setAttribute("useObjectId", useObjectId);
            req.getSession().setAttribute("useObjectType", useObjectType);
            resourceLinkInfo.setStartTime(DateUtil.getDateFromString(planStartTime,
                    DateUtil.YYYY_MM_DD));
            resourceLinkInfo.setEndTime(DateUtil.getDateFromString(planEndTime,
                    DateUtil.YYYY_MM_DD));
            req.setAttribute("resourceLinkInfo_", resourceLinkInfo);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("com/glaway/ids/pm/project/plan/planResource-addList");

    }


    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "combotree")
    public void combotree(HttpServletRequest request, HttpServletResponse response) {
        List<TreeNode> forderTree = resourceFolderService.getTreeNodesForPm();
        String json = JSONArray.toJSONString(forderTree);
        TagUtil.ajaxResponse(response, json);

    }



    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridlist")
    @ResponseBody
    public void datagridlist(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                             HttpServletResponse response) {
        List<ResourceDto> searchInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchInfoList");
        List<ResourceDto> searchRmInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchRmInfoList");
        if (searchInfoList == null) {
            searchInfoList = new ArrayList<ResourceDto>();
        }
        if (searchRmInfoList == null) {
            searchRmInfoList = new ArrayList<ResourceDto>();
        }

        List<ResourceDto> resourceListTemp = resourceService.searchUsables(new ResourceDto());
        List<ResourceDto> resourceListTemp2 = new ArrayList<ResourceDto>();
        int c = 0;
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        resourceLinkInfo.setUseObjectId(useObjectId);
        resourceLinkInfo.setUseObjectType(useObjectType);
        resourceLinkInfo.setAvaliable("1");
        List<ResourceLinkInfoDto> resourceLinkInfoList2 = resourceLinkInfoService.queryResourceList(
                resourceLinkInfo, 1, 10, false);
        if (resourceListTemp != null) {
            if (resourceLinkInfoList2 != null && resourceLinkInfoList2.size() == 0) {
                resourceListTemp2.addAll(resourceListTemp);
            }
            else {
                for (int i = 0; i < resourceListTemp.size(); i++ ) {
                    c = 0;
                    if (resourceLinkInfoList2 != null) {
                        for (int j = 0; j < resourceLinkInfoList2.size(); j++ ) {
                            if (resourceLinkInfoList2.get(j).getResourceInfo() != null) {
                                if (resourceLinkInfoList2.get(j).getResourceId().equals(
                                        resourceListTemp.get(i).getId())) {}
                                else {
                                    c++ ;
                                }
                            }

                        }
                    }
                    if (resourceLinkInfoList2 != null && c == resourceLinkInfoList2.size()) {
                        resourceListTemp2.add(resourceListTemp.get(i));
                    }

                }
            }
        }

        if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {

            try
            {
                //修改BUG资源在业务控制中设置开始时间和结束时间应该和计划中的时间进行匹配
                String planStartTime = (String)request.getSession().getAttribute("planStartTime");
                String planEndTime =  (String)request.getSession().getAttribute("planEndTime");
                List<ResourceDto> resourceListTempOld = new ArrayList<ResourceDto>();
                //复制原先的集合用于判断需要删除的资源
                for (int i=0;i<resourceListTemp2.size();i++)
                {
                    resourceListTempOld.add(resourceListTemp2.get(i));
                }


                for (int i=0;i<resourceListTempOld.size();i++)
                {
                    //判断资源设置中的开始时间与结束时间知不是在计划的开始时间与结束时间的区间内 不是移除  true 代表第二个日期在后面  修改BUG 资源时间与计划时间有交集就可以  及资源时间  结束时间 大于等于计划时间  或者开始时间小于等于计划的结束时间
                    if(StringUtils.isNotEmpty(DateUtil.getStringFromDate(resourceListTempOld.get(i).getStartTime(), DateUtil.YYYY_MM_DD))&&
                            StringUtils.isNotEmpty(DateUtil.getStringFromDate(resourceListTempOld.get(i).getEndTime(), DateUtil.YYYY_MM_DD)))
                    {
	    				/*if(!(DateUtil.dateCompare(resourceListTempOld.get(i).getStartTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD))
	    						||DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD), resourceListTempOld.get(i).getEndTime())))
	    				{
	    					resourceListTemp2.remove(resourceListTempOld.get(i));
	    				}*/
                        if(DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD),resourceListTempOld.get(i).getStartTime())
                                ||DateUtil.dateCompare(resourceListTempOld.get(i).getEndTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD)))
                        {
                            resourceListTemp2.remove(resourceListTempOld.get(i));
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {
                searchInfoList.clear();
                searchInfoList.addAll(resourceListTemp2);
                searchRmInfoList.clear();
                searchRmInfoList.addAll(resourceListTemp2);

                String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceListTemp2);
                String datagridStr = "{\"rows\":" + json + ",\"total\":" + resourceListTemp2.size()
                        + "}";

                request.getSession().setAttribute("searchInfoList", searchInfoList);
                request.getSession().setAttribute("searchRmInfoList", searchRmInfoList);

                TagUtil.ajaxResponse(response, datagridStr);
            }

        }

    }
    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridlistForPlan")
    @ResponseBody
    public void datagridlistForPlan(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                             HttpServletResponse response) {
        List<ResourceDto> searchInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchInfoList");
        List<ResourceDto> searchRmInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchRmInfoList");
        if (searchInfoList == null) {
            searchInfoList = new ArrayList<ResourceDto>();
        }
        if (searchRmInfoList == null) {
            searchRmInfoList = new ArrayList<ResourceDto>();
        }

        List<ResourceDto> resourceListTemp = resourceService.searchUsables(new ResourceDto());
        List<ResourceDto> resourceListTemp2 = new ArrayList<ResourceDto>();
        int c = 0;
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        resourceLinkInfo.setUseObjectId(useObjectId);
        resourceLinkInfo.setUseObjectType(useObjectType);
        resourceLinkInfo.setAvaliable("1");
        List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
        if(CommonUtil.isEmpty(resourceLinkInfoList2)){
            resourceLinkInfoList2 = new ArrayList<>();
        }
        if (resourceListTemp != null) {
            if (CommonUtil.isEmpty(resourceLinkInfoList2)) {
                resourceListTemp2.addAll(resourceListTemp);
            }
            else {
                for (int i = 0; i < resourceListTemp.size(); i++ ) {
                    c = 0;
                    if (!CommonUtil.isEmpty(resourceLinkInfoList2)) {
                        for (int j = 0; j < resourceLinkInfoList2.size(); j++ ) {
                            if (resourceLinkInfoList2.get(j).getResourceInfo() != null) {
                                if (resourceLinkInfoList2.get(j).getResourceId().equals(
                                        resourceListTemp.get(i).getId())) {}
                                else {
                                    c++ ;
                                }
                            }

                        }
                    }
                    if (resourceLinkInfoList2 != null && c == resourceLinkInfoList2.size()) {
                        resourceListTemp2.add(resourceListTemp.get(i));
                    }

                }

               /* for(ResourceDto resourceDto : resourceListTemp){
                    for(ResourceLinkInfoDto rLink : resourceLinkInfoList2){
                        if(!resourceDto.getId().equals(rLink.getResourceId())){
                            resourceListTemp2.add(resourceDto);
                        }
                    }
                }*/
            }
        }

        if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {

            try
            {
                //修改BUG资源在业务控制中设置开始时间和结束时间应该和计划中的时间进行匹配
                String planStartTime = (String)request.getSession().getAttribute("planStartTime");
                String planEndTime =  (String)request.getSession().getAttribute("planEndTime");
                List<ResourceDto> resourceListTempOld = new ArrayList<ResourceDto>();
                //复制原先的集合用于判断需要删除的资源
                for (int i=0;i<resourceListTemp2.size();i++)
                {
                    resourceListTempOld.add(resourceListTemp2.get(i));
                }


                for (int i=0;i<resourceListTempOld.size();i++)
                {
                    //判断资源设置中的开始时间与结束时间知不是在计划的开始时间与结束时间的区间内 不是移除  true 代表第二个日期在后面  修改BUG 资源时间与计划时间有交集就可以  及资源时间  结束时间 大于等于计划时间  或者开始时间小于等于计划的结束时间
                    if(StringUtils.isNotEmpty(DateUtil.getStringFromDate(resourceListTempOld.get(i).getStartTime(), DateUtil.YYYY_MM_DD))&&
                            StringUtils.isNotEmpty(DateUtil.getStringFromDate(resourceListTempOld.get(i).getEndTime(), DateUtil.YYYY_MM_DD)))
                    {
	    				/*if(!(DateUtil.dateCompare(resourceListTempOld.get(i).getStartTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD))
	    						||DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD), resourceListTempOld.get(i).getEndTime())))
	    				{
	    					resourceListTemp2.remove(resourceListTempOld.get(i));
	    				}*/
                        if(DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD),resourceListTempOld.get(i).getStartTime())
                                ||DateUtil.dateCompare(resourceListTempOld.get(i).getEndTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD)))
                        {
                            resourceListTemp2.remove(resourceListTempOld.get(i));
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {
                searchInfoList.clear();
                searchInfoList.addAll(resourceListTemp2);
                searchRmInfoList.clear();
                searchRmInfoList.addAll(resourceListTemp2);

                String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceListTemp2);
                String datagridStr = "{\"rows\":" + json + ",\"total\":" + resourceListTemp2.size()
                        + "}";

                request.getSession().setAttribute("searchInfoList", searchInfoList);
                request.getSession().setAttribute("searchRmInfoList", searchRmInfoList);

                TagUtil.ajaxResponse(response, datagridStr);
            }

        }

    }

    /**
     * 修改资源
     *
     * @param request
     * @return
     * @throws
     * @seepdResource
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateResourceForPlan")
    @ResponseBody
    public AjaxJson doUpdateResourceForPlan(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        try{
            String isPlanChange = request.getParameter("isPlanChange");
            if(CommonUtil.isEmpty(isPlanChange) || !"planChange".equals(isPlanChange)) {
                List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
                if(CommonUtil.isEmpty(resourceLinkInfoList2)){
                    resourceLinkInfoList2 = new ArrayList<>();
                }

                for (ResourceLinkInfoDto dto : resourceLinkInfoList2) {
                    if(resourceLinkInfo.getId().equals(dto.getId())){
                        dto.setUseRate(resourceLinkInfo.getUseRate());
                        dto.setStartTime(resourceLinkInfo.getStartTime());
                        dto.setEndTime(resourceLinkInfo.getEndTime());
                    }
                }
                request.getSession().setAttribute("resourceListForPlan",resourceLinkInfoList2);
            }else if("planChange".equals(isPlanChange)){
                //单条变更的取数：
                TempPlanResourceLinkInfoDto resourceLinkInfoDto = null;
                List<TempPlanResourceLinkInfoDto> resourceLinkInfoList2 = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute("planChange-resourceLinkInfoList");
                if(CommonUtil.isEmpty(resourceLinkInfoList2)){
                    resourceLinkInfoList2 = new ArrayList<>();
                }
                for (TempPlanResourceLinkInfoDto dto : resourceLinkInfoList2) {
                    if(resourceLinkInfo.getId().equals(dto.getId())){
                        dto.setUseRate(resourceLinkInfo.getUseRate());
                        dto.setStartTime(resourceLinkInfo.getStartTime());
                        dto.setEndTime(resourceLinkInfo.getEndTime());
                    }
                }
                request.setAttribute("resourceListForPlan", resourceLinkInfoDto);
            }
        }
        catch (Exception e) {
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增交付物
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForPlan")
    @ResponseBody
    public AjaxJson doAddForPlan(String ids, ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        resourceLinkInfo.setResourceName(request.getParameter("name"));
        String failMessage = "";
        String failMessageCode = "";
        resourceLinkInfo.setId(null);
        String useObjectId = (String)request.getSession().getAttribute("useObjectId");
        String useObjectType = (String)request.getSession().getAttribute("useObjectType");
        String planStartTimeStr = (String)request.getSession().getAttribute("planStartTime");
        String planEndTimeStr = (String)request.getSession().getAttribute("planEndTime");

        try {

            if(CommonUtil.isEmpty(ids)){
                ids = "";
            }

            resourceLinkInfo.setUseObjectId(useObjectId);
            resourceLinkInfo.setUseObjectType(useObjectType);
            resourceLinkInfo.setAvaliable("1");

            List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
            if(CommonUtil.isEmpty(resourceLinkInfoList2)){
                resourceLinkInfoList2 = new ArrayList<>();
            }

            Date planStartTime = new Date();
            Date planEndTime = new Date();
            try {
                planStartTime = DateUtil.getDateFromString(planStartTimeStr, DateUtil.YYYY_MM_DD);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                planEndTime = DateUtil.getDateFromString(planEndTimeStr, DateUtil.YYYY_MM_DD);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (String id : ids.split(",")) {
                ResourceLinkInfoDto resourceLinkInfoTemp = new ResourceLinkInfoDto();
                // Resource resource = resourceService.getEntity(Resource.class, id);
                resourceLinkInfoTemp.setId(UUIDGenerator.generate());
                resourceLinkInfoTemp.setUseObjectId(useObjectId);
                resourceLinkInfoTemp.setStartTime(planStartTime);
                resourceLinkInfoTemp.setEndTime(planEndTime);
                resourceLinkInfoTemp.setUseObjectType(useObjectType);
                resourceLinkInfoTemp.setUseRate("100");
                resourceLinkInfoTemp.setResourceId(id);
                resourceLinkInfoList2.add(resourceLinkInfoTemp);
            }

            request.getSession().setAttribute("resourceListForPlan",resourceLinkInfoList2);
            log.info(message, resourceLinkInfo.getId(), resourceLinkInfo.getId().toString());
        }
        catch (Exception e) {
            log.error(failMessage, e, "", resourceLinkInfo.getId().toString());
            Object[] params = new Object[] {failMessage, resourceLinkInfo.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }
    /**
     * 新增交付物
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(String ids, ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        resourceLinkInfo.setResourceName(request.getParameter("name"));
        String failMessage = "";
        String failMessageCode = "";
        resourceLinkInfo.setId(null);
        String useObjectId = (String)request.getSession().getAttribute("useObjectId");
        String useObjectType = (String)request.getSession().getAttribute("useObjectType");
        String planStartTime = (String)request.getSession().getAttribute("planStartTime");
        String planEndTime = (String)request.getSession().getAttribute("planEndTime");

        try {

            if(CommonUtil.isEmpty(ids)){
                ids = "";
            }
            resourceLinkInfoService.doAddResourceForWork(ids, useObjectId, planStartTime,
                    planEndTime, useObjectType);
            log.info(message, resourceLinkInfo.getId(), resourceLinkInfo.getId().toString());
        }
        catch (Exception e) {
            log.error(failMessage, e, "", resourceLinkInfo.getId().toString());
            Object[] params = new Object[] {failMessage, resourceLinkInfo.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 新增交付物
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings({"finally", "unchecked"})
    @RequestMapping(params = "search")
    @ResponseBody
    public AjaxJson search(String name, String no, String path, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        List<ResourceDto> searchInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchInfoList");
        List<ResourceDto> searchRmInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchRmInfoList");
        try {
            List<ResourceDto> searchInfoListTemp = new ArrayList<ResourceDto>();
            for (ResourceDto r : searchRmInfoList) {
                Boolean a = false;
                if (StringUtils.isNotEmpty(path)) {
                    for (String pt : path.split(",")) {
                        if (r.getPath().contains(pt)) {
                            a = true;
                        }
                    }
                }
                else {
                    a = true;
                }
                if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                    searchInfoListTemp.add(r);
                }
            }
            searchInfoList.clear();
            searchInfoList.addAll(searchInfoListTemp);

            request.getSession().setAttribute("searchInfoList", searchInfoList);
            request.getSession().setAttribute("searchRmInfoList", searchRmInfoList);

            log.info(message, name, name);
        }
        catch (Exception e) {
            log.error(failMessage, e, "", name.toString());
            Object[] params = new Object[] {failMessage, name.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridSearchlist")
    @ResponseBody
    public void datagridSearchlist(HttpServletRequest request, HttpServletResponse response) {

        List<ResourceDto> searchInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchInfoList");
        List<ResourceDto> searchRmInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "searchRmInfoList");

        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
        resourceLinkInfo.setUseObjectId(useObjectId);
        resourceLinkInfo.setUseObjectType(useObjectType);
        resourceLinkInfo.setAvaliable("1");
        List<ResourceLinkInfoDto> resourceLinkInfoList = resourceLinkInfoService.queryResourceList(
                resourceLinkInfo, 1, 10, false);
        Map<String, String> currentResourceMap = new HashMap<String, String>();
        for (ResourceLinkInfoDto in : resourceLinkInfoList) {
            if (StringUtils.isNotEmpty(in.getResourceId())) {
                currentResourceMap.put(in.getResourceId(), in.getResourceId());
            }
        }
        List<ResourceDto> list = new ArrayList<ResourceDto>();

        if (!CommonUtil.isEmpty(searchInfoList)) {
            if (CommonUtil.isEmpty(resourceLinkInfoList)) {
                list.addAll(searchInfoList);
            }
            else {
                for (ResourceDto r : searchInfoList) {
                    if (StringUtils.isEmpty(currentResourceMap.get(r.getId()))) {
                        list.add(r);
                    }
                }
            }
        }
        try {
            //修改BUG资源在业务控制中设置开始时间和结束时间应该和计划中的时间进行匹配
            String planStartTime = (String)request.getSession().getAttribute("planStartTime");
            String planEndTime =  (String)request.getSession().getAttribute("planEndTime");
            //复制原先集合用于判断要删除的地方
            List<ResourceDto> listOld = new ArrayList<ResourceDto>();
            for (int i=0;i<list.size();i++)
            {
                listOld.add(list.get(i));
            }

            for (int i=0;i<listOld.size();i++)
            {
                //判断资源设置中的开始时间与结束时间知不是在计划的开始时间与结束时间的区间内 不是移除
                if(StringUtils.isNotEmpty(DateUtil.getStringFromDate(listOld.get(i).getStartTime(), DateUtil.YYYY_MM_DD))&&
                        StringUtils.isNotEmpty(DateUtil.getStringFromDate(listOld.get(i).getEndTime(), DateUtil.YYYY_MM_DD)))
                {
/*				if(!(DateUtil.dateCompare(listOld.get(i).getStartTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD))
						&&DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD), listOld.get(i).getEndTime())))
				{
					list.remove(listOld.get(i));
				}*/
                    if(DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD),listOld.get(i).getStartTime())
                            ||DateUtil.dateCompare(listOld.get(i).getEndTime(),DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD)))
                    {
                        list.remove(listOld.get(i));
                    }
                }
            }



            String json = com.alibaba.fastjson.JSONArray.toJSONString(list);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + list.size() + "}";
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");

            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(datagridStr);
            pw.flush();
            request.getSession().setAttribute("searchInfoList", searchInfoList);
            request.getSession().setAttribute("searchRmInfoList", searchRmInfoList);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看时、资源tab页
     *
     * @return
     */
    @RequestMapping(params = "goResourceCheck")
    public ModelAndView goResourceCheck(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        String planId = req.getParameter("useObjectId");
        PlanDto plan = planService.getPlanEntity(planId);
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        req.setAttribute("planId", planId);
        req.setAttribute("beTask", "false");
        String startTime = DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD);
        String endTime = DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD);
        req.setAttribute("startTime", startTime);
        req.setAttribute("endTime", endTime);
        return new ModelAndView("com/glaway/ids/project/plan/planResource-check");
    }

    /**
     * 获取datagrid中的值
     *
     * @param request
     * @param response
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "modifyResourceMass")
    @ResponseBody
    public AjaxJson modifyResourceMass(String ids, String useRates, String startTimes,
                                       String endTimes, HttpServletRequest request,
                                       HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        try {
            String[] id = ids.split(",");

            List<ResourceLinkInfoDto> resourceLst = new ArrayList<ResourceLinkInfoDto>();
            for (int i = 0; i < id.length; i++ ) {
                ResourceLinkInfoDto p = resourceLinkInfoService.getResourceLinkInfoEntity(id[i]);
                resourceLst.add(p);
            }

            FeignJson fj = planFlowForworkService.modifyResourceMassForWork(resourceLst, useRates, endTimes,
                    startTimes);
            if (!fj.isSuccess()) {
              j.setSuccess(fj.isSuccess());
              j.setMsg(fj.getMsg());
            }
        }
        catch (Exception e) {
            log.error(failMessage, e, "", ids);
            Object[] params = new Object[] {failMessage, ids};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 批量删除资源
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public FeignJson doBatchDel(String ids, HttpServletRequest request) {
        FeignJson j = new FeignJson();
        List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
        if(!CommonUtil.isEmpty(ids)){
            for(String id : ids.split(",")){
                for(ResourceLinkInfoDto dto : resourceLinkInfoList2){
                    if(id.equals(dto.getId())){
                        resourceLinkInfoList2.remove(dto);
                        break;
                    }
                }
            }
        }
        request.getSession().setAttribute("resourceListForPlan",resourceLinkInfoList2);
      //  FeignJson j = planFlowForworkService.doBatchDel(ids);
        return j;
    }

    /**
     * 资源编辑页面跳转
     *
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(params = "goModifyResourceForPlan")
    public ModelAndView goModifyResourceForPlan(ResourceLinkInfoDto tempPlanResourceLinkInfo, HttpServletRequest request)
            throws ParseException, UnsupportedEncodingException {
        String isPlanChange = request.getParameter("isPlanChange");
        if(CommonUtil.isEmpty(isPlanChange) || !"planChange".equals(isPlanChange)) {
            ResourceLinkInfoDto resourceLinkInfoDto = null;
            List<ResourceLinkInfoDto> resourceLinkInfoList2 = (List<ResourceLinkInfoDto>) request.getSession().getAttribute("resourceListForPlan");
            if(!CommonUtil.isEmpty(tempPlanResourceLinkInfo.getId())){
                for(ResourceLinkInfoDto dto : resourceLinkInfoList2){
                    if(tempPlanResourceLinkInfo.getId().equals(dto.getId())){
                        resourceLinkInfoDto = dto;
                        break;
                    }
                }
            }
            request.setAttribute("tempPlanResourceLinkInfo_", resourceLinkInfoDto);
        }else if("planChange".equals(isPlanChange)){
            //单条变更的取数：
            TempPlanResourceLinkInfoDto resourceLinkInfoDto = null;
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList2 = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute("planChange-resourceLinkInfoList");
            if(!CommonUtil.isEmpty(tempPlanResourceLinkInfo.getId())){
                for(TempPlanResourceLinkInfoDto dto : resourceLinkInfoList2){
                    if(tempPlanResourceLinkInfo.getId().equals(dto.getId())){
                        resourceLinkInfoDto = dto;
                        break;
                    }
                }
            }
            request.setAttribute("tempPlanResourceLinkInfo_", resourceLinkInfoDto);
            request.setAttribute("isPlanChange", isPlanChange);
        }

        request.setAttribute("planStartTime", tempPlanResourceLinkInfo.getStartTime());
        request.setAttribute("planEndTime", tempPlanResourceLinkInfo.getEndTime());
        return new ModelAndView("com/glaway/ids/pm/project/task/resource-modifyForPlanUpdate");
    }

    /**
     * 修改资源
     *
     * @return
     */
    @RequestMapping(params = "doModifyResourceForPlan")
    @ResponseBody
    public FeignJson doModifyResourceForPlan(ResourceLinkInfoDto tempPlanResourceLinkInfo, HttpServletRequest request) {
        FeignJson j = new FeignJson();
        List<ResourceLinkInfoDto> resourceLinkInfoList2 = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
        if(!CommonUtil.isEmpty(tempPlanResourceLinkInfo.getId())){
            for(ResourceLinkInfoDto dto : resourceLinkInfoList2){
                if(tempPlanResourceLinkInfo.getId().equals(dto.getId())){
                    dto.setStartTime(tempPlanResourceLinkInfo.getStartTime());
                    dto.setEndTime(tempPlanResourceLinkInfo.getEndTime());
                    break;
                }
            }
        }
        request.getSession().setAttribute("resourceListForPlan",resourceLinkInfoList2);
        //  FeignJson j = planFlowForworkService.doBatchDel(ids);
        return j;
    }


    /**
     * 批量删除资源(流程分解)
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDelForPlanResolve")
    @ResponseBody
    public FeignJson doBatchDelForPlanResolve(String ids, HttpServletRequest request) {
        FeignJson j = planFlowForworkService.doBatchDel(ids);
        return j;
    }


    /**
     * 修改时子计划时间收敛于计划
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "checkChildrenTime")
    @ResponseBody
    public AjaxJson checkChildrenTime(PlanDto plan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        if (StringUtils.isNotEmpty(plan.getId())) {
            /*
             * ResourceLinkInfo resourceLinkInfo = new ResourceLinkInfo();
             * resourceLinkInfo.setUseObjectId(plan.getId());
             * List<ResourceLinkInfo> reInfoList = resourceLinkInfoService.queryResourceList(
             * resourceLinkInfo, 1, 10, false);
             */
            List<PlanDto> ParentPlanList = planService.getPlanAllChildren(plan);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String time1 = sdf.format(plan.getPlanStartTime());
            String time2 = sdf.format(plan.getPlanEndTime());
            /*
             * for (ResourceLinkInfo info : reInfoList) {
             * String time3 = sdf.format(info.getStartTime());
             * String time4 = sdf.format(info.getEndTime());
             * if ((Integer.parseInt(time4) > Integer.parseInt(time2))
             * || (Integer.parseInt(time1) > Integer.parseInt(time3))) {
             * j.setSuccess(false);
             * j.setMsg("资源【" + info.getResourceInfo().getName() + "】的时间" + info.getStartTime() +
             * "~"
             * + info.getEndTime() + "没有收敛于计划时间，请重新修改计划时间！");
             * break;
             * }
             * }
             */

            for (PlanDto p : ParentPlanList) {
                if (!plan.getId().equals(p.getId())) {
                    String time3 = sdf.format(p.getPlanStartTime());
                    String time4 = sdf.format(p.getPlanEndTime());
                    if ((Integer.parseInt(time4) > Integer.parseInt(time2))
                            || (Integer.parseInt(time1) > Integer.parseInt(time3))) {
                        AjaxJson j2 = new AjaxJson();
                        j2.setSuccess(false);
                        Object[] arguments = new String[] {p.getPlanName(),
                                DateUtil.dateToString(p.getPlanStartTime(),"yyyy-MM-dd"), DateUtil.dateToString(p.getPlanEndTime(),"yyyy-MM-dd")};
                        j2.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.resource.checkChildrenTime", arguments));
                        j = j2;
                        break;
                    }
                }
            }
        }
        return j;
    }


    /**
     * 资源预估使用率图
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "goToUsedRateReport")
    public ModelAndView goToUsedRateReport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/resourcereport/usedReport");
        String resourceId = request.getParameter("resourceId");
        // 资源占用时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date start = DateUtil.stringtoDate(startTime, DateUtil.YYYY_MM_DD);
        Date end = DateUtil.stringtoDate(endTime, DateUtil.YYYY_MM_DD);
        Date startNew = TimeUtil.getExtraDate(start,
                Integer.valueOf("-" + PlanConstants.RESOURCE_TIMEAREA_ADDDAY));
        Date endNew = TimeUtil.getExtraDate(end, PlanConstants.RESOURCE_TIMEAREA_ADDDAY);
        // 资源占用时间前后5天
        String startDate = DateUtil.getStringFromDate(startNew, DateUtil.YYYY_MM_DD);
        String endDate = DateUtil.getStringFromDate(endNew, DateUtil.YYYY_MM_DD);
        mav.addObject("resourceId", resourceId);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("url", "resourceLinkInfoController.do?usedReportDetail");
        return mav;
    }


    /**
     * 资源管理编详情页面跳转
     *
     * @return
     */
    @RequestMapping(params = "usedReportDetail")
    public ModelAndView usedReportDetail(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/resourcereport/usedReportDetail");
        String resourceId = request.getParameter("resourceId");
        // 计划时间时间前后5天
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        mav.addObject("resourceId", resourceId);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        return mav;
    }

    /**
     * 资源已使用率图
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "drawResourceUsedRate")
    public void drawResourceUsedRate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String resourceId = request.getParameter("resourceId");
            String startTime = request.getParameter("startDate");
            String endTime = request.getParameter("endDate");
            ResourceDto resource = resourceService.getEntity(resourceId);
            Date startNew = DateUtil.getDateFromString(startTime, DateUtil.YYYY_MM_DD);
            Date endNew = DateUtil.getDateFromString(endTime, DateUtil.YYYY_MM_DD);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startNew);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endNew);
            int betweenDays = TimeUtil.getDaysBetween(startCalendar, endCalendar);

            List<ResourceUsedRateVO> list = new ArrayList<ResourceUsedRateVO>();
            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setResourceId(resourceId);
            resourceLinkInfo.setStartTime(startNew);
            resourceLinkInfo.setEndTime(endNew);
            List<Map<String, Object>> infoList = resourceLinkInfoService.getAllLinkInfo(resourceLinkInfo);

            for (int i = 0; i <= betweenDays; i++ ) {
                ResourceUsedRateVO vo = new ResourceUsedRateVO();
                String date = DateUtil.getStringFromDate(TimeUtil.getExtraDate(startNew, i),
                        DateUtil.YYYY_MM_DD);
                // dateList.add(date);
                vo.setDate(date);
                if (StringUtils.isNotEmpty(resource.getOccupationWarn())) {
                    // occupationWarnList.add(Double.valueOf(resource.getOccupationWarn()));
                    vo.setOccupationWarn(resource.getOccupationWarn());
                }
                // 逐天统计当天的资源已使用率
                Date oneBetweenStartAndEnd = TimeUtil.getExtraDate(startNew, i);
                String usedRateStr = "0";
                double usedRate = 0.0d;
                for (Map<String, Object> map : infoList) {
                    Date startDate = new Date();
                    Date endDate = new Date();
                    if (!CommonUtil.isEmpty(map.get("STARTTIME"))) {
                        startDate = new Date((Long) map.get("STARTTIME"));
                    }
                    if (!CommonUtil.isEmpty(map.get("ENDTIME"))) {
                        endDate = new Date((Long) map.get("ENDTIME"));
                    }
                    String useRate = (String)map.get("USERATE");
                    if (oneBetweenStartAndEnd.compareTo(startDate) >= 0
                            && oneBetweenStartAndEnd.compareTo(endDate) <= 0) {
                        PlanDto plan1 = planService.getPlanEntity(
                                (String)map.get("USEOBJECTID"));
                        Project project = plan1.getProject();

                        // 如果计划的结束时间早于资源的占用结束时间,释放资源
                        if ("finish".equalsIgnoreCase(plan1.getBizCurrent())
                                && oneBetweenStartAndEnd.compareTo(plan1.getActualEndTime()) > 0) {
                            useRate = "0";
                        }
                        else if ("invalid".equalsIgnoreCase(plan1.getBizCurrent()) && !CommonUtil.isEmpty(plan1.getInvalidTime())
                                && oneBetweenStartAndEnd.compareTo(plan1.getInvalidTime()) > 0) {
                            useRate = "0";
                        }
                        else if (null != project
                                && "CLOSED".equalsIgnoreCase(project.getBizCurrent())
                                && oneBetweenStartAndEnd.compareTo(project.getCloseTime()) > 0) {
                            useRate = "0";
                        }
                        else if (null != project
                                && "PAUSED".equalsIgnoreCase(project.getBizCurrent())
                                && oneBetweenStartAndEnd.compareTo(project.getPauseTime()) > 0) {
                            useRate = "0";
                        }
                        usedRate += Double.valueOf(useRate);
                    }
                }
                usedRateStr = String.valueOf(usedRate);
                vo.setUsedRate(usedRateStr);

                list.add(vo);
            }
            Gson gson = new Gson();
            String json = gson.toJson(list);
            TagUtil.ajaxResponse(response, json);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 资源预估使用率图
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "goToToBeUsedRateReport")
    public ModelAndView goToToBeUsedRateReport(HttpServletRequest request,
                                               HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/resourcereport/toBeUsedReport");
        String resourceId = request.getParameter("resourceId");
        String resourceLinkId = request.getParameter("resourceLinkId");
        String resourceUseRate = request.getParameter("resourceUseRate");
        // 资源占用时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String useObjectId = request.getParameter("useObjectId");
        Date start = DateUtil.stringtoDate(startTime, DateUtil.YYYY_MM_DD);
        Date end = DateUtil.stringtoDate(endTime, DateUtil.YYYY_MM_DD);
        Date startNew = TimeUtil.getExtraDate(start,
                Integer.valueOf("-" + PlanConstants.RESOURCE_TIMEAREA_ADDDAY));
        Date endNew = TimeUtil.getExtraDate(end, PlanConstants.RESOURCE_TIMEAREA_ADDDAY);
        // 资源占用时间前后5天
        String startDate = DateUtil.getStringFromDate(startNew, DateUtil.YYYY_MM_DD);
        String endDate = DateUtil.getStringFromDate(endNew, DateUtil.YYYY_MM_DD);
        mav.addObject("resourceId", resourceId);
        mav.addObject("resourceLinkId", resourceLinkId);
        mav.addObject("resourceUseRate", resourceUseRate);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("startTime", startTime);
        mav.addObject("endTime", endTime);
        mav.addObject("useObjectId", useObjectId);
        mav.addObject("url", "resourceLinkInfoController.do?reportDetail");
        return mav;
    }

    /**
     * 资源管理编详情页面跳转
     *
     * @return
     */
    @RequestMapping(params = "reportDetail")
    public ModelAndView reportDetail(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/resourcereport/toBeUsedReportDetail");
        String resourceId = request.getParameter("resourceId");
        String resourceLinkId = request.getParameter("resourceLinkId");
        String resourceUseRate = request.getParameter("resourceUseRate");
        // 资源占用时间前后5天
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        // 资源占用时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String useObjectId = request.getParameter("useObjectId");
        mav.addObject("resourceId", resourceId);
        mav.addObject("resourceLinkId", resourceLinkId);
        mav.addObject("resourceUseRate", resourceUseRate);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("startTime", startTime);
        mav.addObject("endTime", endTime);
        mav.addObject("useObjectId", useObjectId);
        return mav;
    }

    /**
     * 资源预估使用率图
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "drawResourceToBeUsedRate")
    public void drawResourceToBeUsedRate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String resourceId = request.getParameter("resourceId");
            String resourceUseRate = request.getParameter("resourceUseRate");
            // 资源占用前后5天时间
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            // 资源占用时间
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String useObjectId = request.getParameter("useObjectId");
            ResourceDto resource = resourceService.getEntity(resourceId);
            Date startNew = DateUtil.getDateFromString(startDate, DateUtil.YYYY_MM_DD);
            Date endNew = DateUtil.getDateFromString(endDate, DateUtil.YYYY_MM_DD);
            Date start = DateUtil.getDateFromString(startTime, DateUtil.YYYY_MM_DD);
            Date end = DateUtil.getDateFromString(endTime, DateUtil.YYYY_MM_DD);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startNew);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endNew);
            int betweenDays = TimeUtil.getDaysBetween(startCalendar, endCalendar);
            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setStartTime(startNew);
            resourceLinkInfo.setEndTime(endNew);
            // resourceLinkInfo.setId(resourceLinkId);
            resourceLinkInfo.setUseObjectId(useObjectId);
            resourceLinkInfo.setResourceId(resourceId);
            List<ResourceUsedRateVO> list = new ArrayList<ResourceUsedRateVO>();
            List<Map<String, Object>> infoList = resourceLinkInfoService.getAllLinkInfo(resourceLinkInfo);

            for (int i = 0; i <= betweenDays; i++ ) {
                ResourceUsedRateVO vo = new ResourceUsedRateVO();
                String date = DateUtil.getStringFromDate(TimeUtil.getExtraDate(startNew, i),
                        DateUtil.YYYY_MM_DD);
                vo.setDate(date);
                if (StringUtils.isNotEmpty(resource.getOccupationWarn())) {
                    vo.setOccupationWarn(resource.getOccupationWarn());
                }
                resourceLinkInfo.setParamTime(TimeUtil.getExtraDate(start, i));
                // 逐天统计当天的资源已使用率
                Date oneBetweenStartAndEnd = TimeUtil.getExtraDate(startNew, i);
                double usedRate = 0.0d;
                for (Map<String, Object> map : infoList) {
                    Date sDate = new Date();
                    Date eDate = new Date();
                    if (!CommonUtil.isEmpty(map.get("STARTTIME"))) {
                        sDate = new Date((Long) map.get("STARTTIME"));
                    }
                    if (!CommonUtil.isEmpty(map.get("ENDTIME"))) {
                        eDate = new Date((Long) map.get("ENDTIME"));
                    }
                    String useRate = (String)map.get("USERATE");
                    if (oneBetweenStartAndEnd.compareTo(sDate) >= 0
                            && oneBetweenStartAndEnd.compareTo(eDate) <= 0) {
                        PlanDto plan1 = planService.getPlanEntity((String)map.get("USEOBJECTID"));
                        Project project = plan1.getProject();
                        if (project == null) {
                            project = projectFeignService.getProjectEntity(plan1.getProjectId());
                        }
                        if (project != null) {
                            // 如果计划的结束时间早于资源的占用结束时间,释放资源
                            Date tempDate = new Date();
                            if (plan1.getActualEndTime() == null) {
                                tempDate = plan1.getPlanEndTime();
                            }
                            else {
                                tempDate = plan1.getActualEndTime();
                            }
                            if ("finish".equalsIgnoreCase(plan1.getBizCurrent())
                                    && oneBetweenStartAndEnd.compareTo(tempDate) > 0) {
                                // tempDate.compareTo(startDate)<0
                                // //计划的完成时间早于资源占用的开始时间或者计划的开始时间,则释放资源
                                useRate = "0";
                            }
                            else if ("invalid".equalsIgnoreCase(plan1.getBizCurrent())
                                    && (plan1.getInvalidTime() != null && oneBetweenStartAndEnd.compareTo(plan1.getInvalidTime()) > 0)) {
                                // plan1.getInvalidTime().compareTo(startDate)<0
                                // //计划的废弃时间早于资源占用的开始时间或者计划的开始时间,则释放资源
                                useRate = "0";
                            }
                            else if ("CLOSED".equalsIgnoreCase(project.getBizCurrent())
                                    && ("EDITING".equalsIgnoreCase(plan1.getBizCurrent())
                                    || "ORDERED".equalsIgnoreCase(plan1.getBizCurrent()) || "FEEDBACKING".equalsIgnoreCase(plan1.getBizCurrent()))
                                    && (project.getCloseTime() != null && oneBetweenStartAndEnd.compareTo(project.getCloseTime()) > 0)) {
                                useRate = "0";
                            }
                            else if ("PAUSED".equalsIgnoreCase(project.getBizCurrent())
                                    && ("EDITING".equalsIgnoreCase(plan1.getBizCurrent())
                                    || "ORDERED".equalsIgnoreCase(plan1.getBizCurrent()) || "FEEDBACKING".equalsIgnoreCase(plan1.getBizCurrent()))
                                    && oneBetweenStartAndEnd.compareTo(project.getPauseTime()) > 0) {
                                useRate = "0";
                            }
                        }
                        usedRate += Double.valueOf(useRate);
                    }
                }

                vo.setUsedRate(String.valueOf(usedRate));
                if (StringUtils.isNotEmpty(resourceUseRate)) {
                    if (TimeUtil.getExtraDate(startNew, i).getTime() >= start.getTime()
                            && TimeUtil.getExtraDate(startNew, i).getTime() <= end.getTime()) {
                        vo.setWillBeUsed(String.valueOf(Double.valueOf(usedRate)
                                + Double.valueOf(resourceUseRate)));
                    }
                    else {
                        vo.setWillBeUsed(String.valueOf(usedRate));
                    }
                }

                list.add(vo);
            }
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();
            String useObjectIdTemp = useObjectId;
            if(StringUtil.isEmpty(useObjectId)){
                useObjectIdTemp = "nothing";
            }
//            map.put("json", list);
//            map.put("date", "&startDate=" + startDate + "&endDate=" + endDate + "&useObjectId="
//                    + useObjectIdTemp);
            String json = gson.toJson(list);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询Datagride数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "conditionSearchForCreatePlanAddResource")
    @ResponseBody
    public void conditionSearchForCreatePlanAddResource(HttpServletRequest request, HttpServletResponse response) {
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        if(CommonUtil.isEmpty(rows)){
            rows = 10;
        }
        if(CommonUtil.isEmpty(page)){
            page = 1;
        }

        String resourceId = request.getParameter("resourceId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String useobjectid = request.getParameter("useObjectId");
        if(CommonUtil.isEmpty(useobjectid) && "undefined".equals(useobjectid)){
            useobjectid = "";
        }

        List<CheckResourceUsedRateVO> list = resourceLinkInfoService.conditionSearchForCheckResource(resourceId,startDate,endDate,page,rows,useobjectid);
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
