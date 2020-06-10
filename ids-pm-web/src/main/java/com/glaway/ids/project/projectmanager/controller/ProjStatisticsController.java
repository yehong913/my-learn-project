package com.glaway.ids.project.projectmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.PluginConstants;
import com.glaway.ids.common.service.PluginValidateServiceI;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.projectmanager.dto.ProjWarnDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjWarnForGridVo;
import com.glaway.ids.project.projectmanager.vo.ProjWarnVo;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 统计分析
 * @author: sunmeng
 * @ClassName: ProjStatisticsController
 * @Date: 2019/8/14-17:58
 * @since
 */
@Controller
@RequestMapping("/projStatisticsController")
public class ProjStatisticsController extends BaseController {

    @Autowired
    private PluginValidateServiceI pluginValidateService;

    @Autowired
    private ProjectRemoteFeignServiceI projectRemoteFeignServiceI;

    /**
     * Description: <br>跳转统计分析主页面
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goStatistics")
    public ModelAndView goStatistics(HttpServletRequest req) {
        String id = req.getParameter("id");
        String entryPage = "PM";
        req.setAttribute("projectId", id);
        req.setAttribute("entryPage", entryPage);

        // 是否刷新右侧树区域
        String refreshTree = req.getParameter("refreshTree");
        if (StringUtil.isNotEmpty(refreshTree)) {
            req.setAttribute("refreshTree", refreshTree);
        }
        boolean isRiskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);
        req.setAttribute("isRiskPluginValid", isRiskPluginValid);
        return new ModelAndView("com/glaway/ids/project/projectmanager/projStatistics");
    }

    /**
     * Description: <br>跳转预警页面
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goProjWarnPage")
    public ModelAndView goProjWarnPage(HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        req.setAttribute("projectId", projectId);
        List<Map<String,Object>> resList = projectRemoteFeignServiceI.getProjWarnDataForProjStatistics(projectId);
        resList = new ObjectMapper().convertValue(resList,new TypeReference<List<Map<String,Object>>>(){});
        req.setAttribute("resList", JsonUtil.array2json(resList));
        return new ModelAndView("com/glaway/ids/project/projectmanager/projWarnForProjStatistics");
    }

    /**
     * Description: <br>跳转首页预警页面
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goProjPortletWarnPage")
    public ModelAndView goProjPortletWarnPage(HttpServletRequest req) {
        String userId = UserUtil.getCurrentUser().getId();
        FeignJson fj = projectRemoteFeignServiceI.getProj(userId);
        String selectedValue = String.valueOf(fj.getObj());
        if(selectedValue == "null"){
            selectedValue = "";
        }
        req.setAttribute("selectedValue", selectedValue);
        return new ModelAndView("com/glaway/ids/project/projectmanager/projWarnForPortlet");
    }

    /**
     * Description: <br>获取下拉框数据
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "getSelectData")
    public void getSelectData(HttpServletRequest req, HttpServletResponse response) {
        List<Project> portletProjectList = projectRemoteFeignServiceI.getProjectListForPortlet(UserUtil.getInstance().getUser());
        req.setAttribute("portletProjectList", portletProjectList);
        String type = req.getParameter("type");
        String userId = UserUtil.getCurrentUser().getId();
        String proj = null;
        // 1：为计划页面跳转，2为风险页面跳转
        FeignJson fj = new FeignJson();
        if ("1".equals(type)) {
            fj = projectRemoteFeignServiceI.getProj(userId);
        }
        if ("2".equals(type)) {
            fj = projectRemoteFeignServiceI.getProjWarm(userId);
        }
        proj = String.valueOf(fj.getObj());

        String[] projs = null;

        if (StringUtil.isNotEmpty(proj)) {
            projs = proj.split(",");
        }
        JSONArray jList = new JSONArray();
        if (StringUtil.isNotEmpty(projs)) {
            for (String g : projs) {
                if(!CommonUtil.isEmpty(g)){
                    Project project = projectRemoteFeignServiceI.getProjectEntity(g);
                    if (StringUtil.isNotEmpty(project)) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", project.getName());
                        obj.put("text", project.getId());
                        jList.add(obj);
                    }
                }
            }
        }

        if (StringUtil.isNotEmpty(portletProjectList)) {
            for (Project p : portletProjectList) {
                if (StringUtil.isNotEmpty(projs)) {
                    int i = 0;
                    for (String s : projs) {
                        if (p.getId().equals(s)) {}
                        else {
                            i++ ;
                        }
                        if (i == projs.length) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", p.getName());
                            obj.put("text", p.getId());
                            jList.add(obj);
                        }
                    }
                }
                else {
                    JSONObject obj = new JSONObject();
                    obj.put("id", p.getName());
                    obj.put("text", p.getId());
                    jList.add(obj);
                }
            }
        }

        String projListStr = jList.toString();
        TagUtil.ajaxResponse(response, projListStr);
    }

    /**
     * Description: <br>储存计划项目
     *
     * @param projWarn
     * @return
     * @see
     */
    @RequestMapping(params = "ProjWarnPage")
    public void projWarnPage(HttpServletRequest request, HttpServletResponse response, ProjWarnDto projWarn) {
        String projectIds = request.getParameter("projectIds");
        projWarn.setUserName(UserUtil.getCurrentUser().getId());
        projWarn.setProjectNum(projectIds);
        projWarn.setFlag("1"); // 区分标记 0为风险 ，1为计划
        String userId = UserUtil.getCurrentUser().getId();
        FeignJson fj = projectRemoteFeignServiceI.addProjWarn(projWarn, userId, ResourceUtil.getCurrentUserOrg().getId());
        String projListStr = "";
        TagUtil.ajaxResponse(response, projListStr);
    }

    /**
     * 获取计划预警报表数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "getProjWarnReportData")
    public void getProjWarnReportData(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {

            String projectIds = request.getParameter("str");
            List<ProjWarnVo> projWarnList = projectRemoteFeignServiceI.getProjWarnReportData(projectIds);

            String existProjectIds = "";
            //个人积分排名
            List<Map<String, Object>> result= new ArrayList<Map<String,Object>>();
            if(!CommonUtil.isEmpty(projWarnList)) {
                for(ProjWarnVo vo : projWarnList) {
                    if(CommonUtil.isEmpty(existProjectIds)){
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("projectId",vo.getProjectId());
                        map.put("name", vo.getProjectName());
                        map.put("warn",vo.getWarn());
                        if("是".equals(vo.getWarn())){
                            map.put("warnYesNumber", Integer.valueOf(vo.getWarnNumber()));
                        }else{
                            map.put("warnNoNumber", Integer.valueOf(vo.getWarnNumber()));
                        }
                        result.add(map);
                        existProjectIds = vo.getProjectId();
                    }else{
                        if(existProjectIds.contains(vo.getProjectId())){
                            for(Map<String, Object> res : result){
                                if(res.get("projectId").equals(vo.getProjectId())){
                                    if("是".equals(res.get("warn"))){
                                        res.put("warnNoNumber",Integer.valueOf(vo.getWarnNumber()));
                                    }else{
                                        res.put("warnYesNumber",Integer.valueOf(vo.getWarnNumber()));
                                    }
                                    break;
                                }
                            }
                        }else{
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("projectId",vo.getProjectId());
                            map.put("name", vo.getProjectName());
                            map.put("warn",vo.getWarn());
                            if("是".equals(vo.getWarn())){
                                map.put("warnYesNumber", Integer.valueOf(vo.getWarnNumber()));
                            }else{
                                map.put("warnNoNumber", Integer.valueOf(vo.getWarnNumber()));
                            }
                            result.add(map);
                            existProjectIds = existProjectIds+","+vo.getProjectId();
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
        JSONObject main = new JSONObject();
        JSONArray rows = new JSONArray();
        main.put("total", size);
        for (Map m : result) {
            JSONObject item = new JSONObject();
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
     * 统计分析-计划预警列表 查询接口
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "queryProjectwarnGrid")
    public void queryProjectwarnGrid(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");

        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));

        List<ProjWarnForGridVo> list = projectRemoteFeignServiceI.queryProjectwarnGrid(projectId);

        List<ProjWarnForGridVo> resList = new ArrayList<ProjWarnForGridVo>();

        int count = list.size();
        if (count > page * rows) {
            resList = list.subList((page - 1) * rows, page * rows);
        }
        else {
            resList = list.subList((page - 1) * rows, list.size());
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + list.size() + "}";

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
