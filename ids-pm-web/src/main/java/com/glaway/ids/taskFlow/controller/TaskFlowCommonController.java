package com.glaway.ids.taskFlow.controller;

import com.glaway.foundation.activiti.core.dto.MyStartedTaskDto;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:  任务流程公共Controller
 * @author: sunmeng
 * @ClassName: TaskFlowCommonController
 * @Date: 2019/8/23-11:47
 * @since
 */
@Controller
@RequestMapping("/taskFlowCommonController")
public class TaskFlowCommonController {

    private static final OperationLog log = BaseLogFactory.getOperationLog(TaskFlowCommonController.class);

    @Autowired
    private FeignRepService repService;

    @Autowired
    private WorkFlowFacade workFlowFacade;

    @Autowired
    private PlanRemoteFeignServiceI planFeignServiceI;

    @Value(value="${spring.application.name}")
    private String appKey;

    /**
     * message全局变量<br>
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 状态查看流程页面跳转
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "taskFlowList")
    public ModelAndView taskFlowList(HttpServletRequest request, HttpServletResponse response) {
        String taskNumber = "";
        String type = "";
        if (StringUtils.isNotEmpty(request.getParameter("taskNumber"))) {
            taskNumber = request.getParameter("taskNumber");
        }
        if (StringUtils.isNotEmpty(request.getParameter("type"))) {
            type = request.getParameter("type");
        }
        request.setAttribute("taskNumber", taskNumber);
        request.setAttribute("type", type);
        return new ModelAndView("com/glaway/ids/taskFlow/taskFlowList");
    }

    /**
     * 任务列表数据接口
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "getFlowTaskList")
    public void getFlowTaskList(HttpServletRequest request, HttpServletResponse response) {

        try {
            String page = "1";
            String rows = "10";
            Map<String, Object> conditions = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                page = StringUtils.defaultString(request.getParameter("page"), "1");
            }
            else {
                conditions.put("isOrder", "true");
            }
            if (StringUtils.isNotEmpty(request.getParameter("rows"))) {
                rows = StringUtils.defaultString(request.getParameter("rows"), "10");
            }

            MyStartedTaskDto myStartedTask = new MyStartedTaskDto();
            if (StringUtils.isNotEmpty(request.getParameter("taskNumber"))) {
                // 通过计划编号获得FORMid
                String type = request.getParameter("type");
                FeignJson fj = planFeignServiceI.getTaskNumberByPlan(request.getParameter("taskNumber"), type);
                String taskNumber = "";
                if (fj.isSuccess()) {
                    taskNumber = fj.getObj() == null ? "" : fj.getObj().toString();
                }
                myStartedTask.setTaskNumber(taskNumber);
            }
            //项目库所有状态查询
            RepFileDto r = repService.getRepFileByRepFileId(appKey,request.getParameter("taskNumber"));
            if(!CommonUtil.isEmpty(r)&&StringUtils.isNotEmpty(r.getBizId())){
                String bizId = r.getBizId();
                //获取版本下面的所有
                List<RepFileDto> repFileList = repService.getRepFileByBizId(appKey,bizId);
                if(!CommonUtil.isEmpty(repFileList)){
                    String taskNumber = request.getParameter("taskNumber");
                    for (RepFileDto rep : repFileList) {
                        if(!rep.getId().equals(taskNumber)){
                            taskNumber += ("," + rep.getId());
                        }
                    }
                    myStartedTask.setTaskNumber(taskNumber);
                }
            }

            FeignJson fj = workFlowFacade.getWorkFlowFinishedTaskService().getMyStartedTaskByMyStartedTask(appKey,myStartedTask, Integer.valueOf(page), Integer.valueOf(rows));
            List<MyStartedTaskDto> list = new ArrayList<>();
            long count = 0;
            if (fj.isSuccess()) {
                Map<String,Object> attributes = fj.getAttributes();
                list = (List<MyStartedTaskDto>) attributes.get("resultList");
                count = Integer.valueOf(attributes.get("count").toString());
            }
            String json = JsonUtil.getListJsonWithoutQuote(list);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
        catch (Exception e) {
            log.error(message, e);
        }
    }
}
