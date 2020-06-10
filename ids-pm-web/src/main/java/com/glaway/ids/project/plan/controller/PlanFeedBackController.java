package com.glaway.ids.project.plan.controller;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.project.plan.dto.PlanFeedBackDto;
import com.glaway.ids.project.plan.service.PlanFeedBackFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 计划反馈
 * @author: sunmeng
 * @ClassName: PlanFeedBackController
 * @Date: 2019/10/14-10:56
 * @since
 */
@Controller
@RequestMapping("/planFeedBackController")
public class PlanFeedBackController extends BaseController {

    @Autowired
    private PlanFeedBackFeignServiceI planFeedBackFeignService;

    @Autowired
    private PlanRemoteFeignServiceI planService;

    @RequestMapping(params = "goPlanFeedBack")
    public ModelAndView goPlanFeedBack(HttpServletRequest request) {
        Object operationCodes = request.getAttribute("operationCodes");
        Boolean planFeedBackSave = false;
        for (String operationCode : operationCodes.toString().split(",")) {
            if (operationCode.contains("planFeedBackSave")) {
                planFeedBackSave = true;
            }
        }
        request.setAttribute("planFeedBackSave", planFeedBackSave);
        return new ModelAndView("com/glaway/ids/project/plan/planFeedBack");

    }

    /**
     * 计划生命周期下拉框初始化
     * @param request
     * @param response
     */
    @RequestMapping(params = "initCombobox")
    @ResponseBody
    public void initCombobox(HttpServletRequest request, HttpServletResponse response) {
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> lifeCycleStatus = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        JSONArray jList = new JSONArray();
        String jonStr = "";
        if (lifeCycleStatus.size() > 0) {
            for (LifeCycleStatus status : lifeCycleStatus) {
                if (!status.getName().equals("INVALID")) { //摒除废弃状态
                    JSONObject obj = new JSONObject();
                    obj.put("id", status.getName());
                    obj.put("name", status.getTitle());
                    jList.add(obj);
                }
            }
            jonStr = jList.toString();
        }
        TagUtil.ajaxResponse(response, jonStr);
    }

    @RequestMapping(params = "queryData")
    @ResponseBody
    public FeignJson queryData(HttpServletRequest request) {
        FeignJson j = planFeedBackFeignService.queryData();
        return j;
    }

    @RequestMapping(params = "saveFeedBack")
    @ResponseBody
    public FeignJson saveFeedBack(HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String infos = request.getParameter("infos");
        String userId = ResourceUtil.getCurrentUser().getId();
        if (!CommonUtil.isEmpty(infos)) {
            List<Object> lists = (List<Object>) JSON.parseArray(infos);
            Map<String,String> params = new HashMap<>();
            for (Object obj : lists) {
                Map<String,String> map = (Map<String,String>)JSON.parse(String.valueOf(obj));
                params.put(map.get("id"),map.get("value"));
            }
            j = planFeedBackFeignService.saveFeedBack(params,userId);
        }
        return j;
    }

    @RequestMapping(params = "calculateWeight")
    @ResponseBody
    public FeignJson calculateWeight(HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String status = request.getParameter("status");
        String deliveryNum = request.getParameter("deliveryNum");
        String nizhiNum = request.getParameter("nizhiNum");
        String shenpiNum = request.getParameter("shenpiNum");
        String guidangNum = request.getParameter("guidangNum");
        String infos = request.getParameter("infos");
        if (!CommonUtil.isEmpty(infos)) {
            List<Object> lists = (List<Object>) JSON.parseArray(infos);
            Map<String,String> params = new HashMap<>();
            for (Object obj : lists) {
                Map<String,String> map = (Map<String,String>)JSON.parse(String.valueOf(obj));
                params.put(map.get("id"),map.get("value"));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            map.put("deliveryNum", deliveryNum);
            map.put("nizhiNum", nizhiNum);
            map.put("shenpiNum", shenpiNum);
            map.put("guidangNum", guidangNum);
            map.put("infos", params);
            j = planFeedBackFeignService.calculateWeight(map);
        }
        return j;
    }
}
