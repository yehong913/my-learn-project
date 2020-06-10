package com.glaway.ids.gantt.controller;


import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.ids.config.controller.PlanBusinessConfigController;
import com.glaway.ids.gantt.vo.GanttData;
import com.glaway.ids.gantt.vo.GanttRelate;
import com.glaway.ids.gantt.vo.GanttRelates;
import com.glaway.ids.gantt.vo.GanttResponse;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PreposePlanDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xshen
 * @version 2015年3月26日
 * @see PlanBusinessConfigController
 * @since
 */
@Controller
@RequestMapping("/ganttController")
public class GanttController extends BaseController {
    /** 业务对象 */
    @Autowired
    private PlanRemoteFeignServiceI planServiceImpl;

    /** 输出接口 */
    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    @Value(value="${spring.application.name}")
    private String appKey;

    @Autowired
    private FeignUserService userService;

    @Autowired
    private FeignDepartService departService;

    /** 日志消息 */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 甘特图数据获取
     * 
     * @param request
     * @param response
     * @throws ParseException
     * @see
     */
    @RequestMapping(params = "getGanttResponse")
    public void getGanttResponse(HttpServletRequest request, HttpServletResponse response)
        throws ParseException {
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectType("PLAN");
        //-------------------------------------------性能优化 将此部分添加------------------------------------------↓
        List<DeliverablesInfoDto> allDeliverablesInfoList = deliverablesInfoService.queryDeliverableList(deliverablesInfo, 1, 20, false);
        Map<String, List<DeliverablesInfoDto>> useObjectIdMap = new HashMap<String, List<DeliverablesInfoDto>>();
        if(allDeliverablesInfoList!=null && allDeliverablesInfoList.size()>0) {
            for(DeliverablesInfoDto info : allDeliverablesInfoList) {
                if(useObjectIdMap.get(info.getUseObjectId()) == null) {
                    List<DeliverablesInfoDto> newList = new ArrayList<DeliverablesInfoDto>();
                    newList.add(info);
                    useObjectIdMap.put(info.getUseObjectId(), newList);
                }
                else {
                    List<DeliverablesInfoDto> currList = useObjectIdMap.get(info.getUseObjectId());
                    currList.add(info);
                    useObjectIdMap.put(info.getUseObjectId(), currList);
                }
            }
        }
        //-------------------------------------------性能优化 将此部分添加------------------------------------------↑
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String projectId = request.getParameter("projectId");
        String planViewId = request.getParameter("planViewInfoId");

        PlanDto p = new PlanDto();
        p.setProjectId(projectId);
        p.setPlanViewInfoId(planViewId);
//        List<Plan> planListTemp = planServiceImpl.queryPlanListForTreegridView(p,planViewId,new ArrayList<ConditionVO>(),"", "", "");
//        List<Plan> planList = planServiceImpl.queryPlanListForTreegridWithView(planListTemp,planViewId);
        
        List<PlanDto> planList = planServiceImpl.getPlanInfoList(p);
        planList = getList(planList,p);
        GanttRelates relates = new GanttRelates();
        GanttResponse data = new GanttResponse();

        //获取系统所有用户
        Map<String, TSUserDto> userDtoMap = userService.getAllUserIdsMap();
        //获取所有部门
        Map<String,TSDepartDto> departDtoMap = departService.getAllDepartMap();
        Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(appKey,"");

        if (planList != null) {
            Collections.sort(planList, new Comparator<PlanDto>() {
                @Override
                public int compare(PlanDto o1, PlanDto o2) {
                    if (o1.getPlanNumber() > o2.getPlanNumber()) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
            });
        }

        int i = 0;
        long totalTime = 0;
        for (PlanDto plan : planList) {
            GanttData data1 = new GanttData();
            data1.setLineNumber(plan.getPlanNumber());
            data1.setId(plan.getId());
            if(plan.getPlanStartTime()!=null) {
                data1.setStart_date(sdf.format(plan.getPlanStartTime()));
            }
            Calendar cal = Calendar.getInstance();
            if(plan.getPlanEndTime()==null) {
                continue;
            }
            cal.setTime(plan.getPlanEndTime());
            cal.add(Calendar.DATE, 1);
            data1.setEnd_date(sdf.format(cal.getTime()));
            data1.setGanttEndDate(sdf.format(plan.getPlanEndTime()));
            data1.setParent(plan.getParentPlanId());

            if (plan.getProgressRate() == null) {
                data1.setProgress(0);
            }
            else {
                double rate = Double.parseDouble(plan.getProgressRate());
                data1.setProgress(rate);
            }

            data1.setText(plan.getPlanName());
            data1.setOpen(false);

            long time1 = System.currentTimeMillis();
            if (plan.getOwnerInfo() != null) {
                data1.setOwner(plan.getOwnerInfo().getRealName() + "-"
                               + plan.getOwnerInfo().getUserName());
                data1.setDepartment(plan.getOwnerInfo().getTSDepart().getDepartname());
            }
            else {
                if (!CommonUtil.isEmpty(plan.getOwner())) {
                    TSUserDto ownerInfo = userDtoMap.get(plan.getOwner());
                    if (!CommonUtil.isEmpty(ownerInfo)) {
                        data1.setOwner(ownerInfo.getRealName()  + "-" + ownerInfo.getUserName());
                    } else {
                        data1.setOwner("");
                    }
                } else {
                    data1.setOwner("");
                }
                if (!CommonUtil.isEmpty(plan.getCreateOrgId())) {
                    TSDepartDto departDto = departDtoMap.get(plan.getCreateOrgId());
                    if (StringUtils.isNotBlank(departDto.getDepartname())) {
                        data1.setDepartment(departDto.getDepartname());
                    }
                } else if (!CommonUtil.isEmpty(plan.getOwner())) {
                    List<TSDepartDto> departDtoList = departMap.get(plan.getOwner());
                    if (departDtoList.size() > 0) {
                        data1.setDepartment(departDtoList.get(0).getDepartname());
                    }
                } else {
                    data1.setDepartment("无");
                }
            }

            totalTime += System.currentTimeMillis() - time1;
            data1.setStatus(plan.getStatus());
            // 计划等级
            if (plan.getPlanLevelInfo() == null) {
                data1.setPlanLevel("无");
            }
            else {
                data1.setPlanLevel(plan.getPlanLevelInfo().getName());
            }
            // //风险等级
            // TSType tSType =
            // typeService.getValueByCondition(TaskFeedbackConstants.TASK_FEEDBACK_RISK,
            // plan.getRisk());
            // if(tSType == null){
            // data1.setRisk("无");
            // }else{
            // data1.setRisk(tSType.getTypename());
            // }
            // data1.setRisk(transferRisk(plan.getRisk()));
            // 输出
            //-------------------------------------------性能优化 将此部分修改------------------------------------------↓
//            List<DeliverablesInfo> deList = deliverablesInfoService.getDeliverablesByUseObeject(
//                "PLAN", plan.getId());
            List<DeliverablesInfoDto> deList = useObjectIdMap.get(plan.getId());
            //-------------------------------------------性能优化 将此部分修改------------------------------------------↑
            String deliInfo = "";
            if (deList!=null && deList.size() > 0) {
                for (DeliverablesInfoDto di : deList) {
                    if ("".equals(deliInfo)) {
                        deliInfo += di.getName();
                    }
                    else {
                        deliInfo += "," + di.getName();
                    }
                }
            }
            else {
                deliInfo = "无";
            }
            data1.setDeliInfo(deliInfo);
            data1.setWorkTime(plan.getWorkTime());

            data.getData().add(data1);

            List<PlanDto> preposes = plan.getPreposeList();
            if (!CommonUtil.isEmpty(preposes)) {
                Collections.sort(preposes, new Comparator<PlanDto>() {
                    @Override
                    public int compare(PlanDto o1, PlanDto o2) {
                        if (o1.getPlanNumber() > o2.getPlanNumber()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    }
                });

                for (PlanDto prepose : preposes) {
                    i = i + 1;
                    GanttRelate relate1 = new GanttRelate();
                    relate1.setId(i + "");
                    relate1.setSource(prepose.getId());
                    relate1.setTarget(plan.getId());
                    relate1.setType("0");

                    relates.getLinks().add(relate1);
                }
            }
        }

        System.out.println(totalTime+"");
        data.setCollections(relates);

        String json = JSON.toJSONString(data);

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(json);
            pw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*    *//**
     * 转换计划等级
     * 
     * @param risk
     * @return
     * @see
     */
    /*
     * private String transferRisk(String risk) {
     * String trans = "无";
     * if (risk == null) {
     * trans = "无";
     * }
     * else {
     * if (risk.equals("nothing")) {
     * trans = "无";
     * }
     * else if (risk.equals("low")) {
     * trans = "低";
     * }
     * else if (risk.equals("middle")) {
     * trans = "中";
     * }
     * else if (risk.equals("high")) {
     * trans = "高";
     * }
     * }
     * return trans;
     * }
     */

    private List<PlanDto> getList(List<PlanDto> planList,PlanDto planDto){
        List<PreposePlanDto> allPreposePlanList =planServiceImpl.allPreposePlanList(planDto.getProjectId());
        Map<String, List<PreposePlanDto>> planIdMap_PreposePlan = new HashMap<String, List<PreposePlanDto>>();
        if (allPreposePlanList != null && allPreposePlanList.size() > 0) {
            for (PreposePlanDto pPlan : allPreposePlanList) {
                if (planIdMap_PreposePlan.get(pPlan.getPlanId()) == null) {
                    List<PreposePlanDto> newList = new ArrayList<PreposePlanDto>();
                    newList.add(pPlan);
                    planIdMap_PreposePlan.put(pPlan.getPlanId(), newList);
                }
                else {
                    List<PreposePlanDto> currList = planIdMap_PreposePlan.get(pPlan.getPlanId());
                    currList.add(pPlan);
                    planIdMap_PreposePlan.put(pPlan.getPlanId(), currList);
                }
            }
        }
        for (PlanDto p : planList) {
            List<PreposePlanDto> preposePlans = planIdMap_PreposePlan.get(p.getId());
            if (!CommonUtil.isEmpty(preposePlans)) {
                for (PreposePlanDto prepose : preposePlans) {
                   String id = prepose.getPreposePlanId();
                   List<PlanDto> list = new ArrayList<>();
                   for (PlanDto p2 : planList){
                       if (p2.getId().equals(id)){
                           list.add(p2);
                           p.setPreposeList(list);
                       }
                   }
                }
            }
        }
        return planList;
    }

}
