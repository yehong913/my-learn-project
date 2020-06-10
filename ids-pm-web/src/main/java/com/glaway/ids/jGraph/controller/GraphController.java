package com.glaway.ids.jGraph.controller;


import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.ids.config.controller.PlanBusinessConfigController;
import com.glaway.ids.gantt.vo.GanttData;
import com.glaway.ids.gantt.vo.GanttRelate;
import com.glaway.ids.gantt.vo.GanttRelates;
import com.glaway.ids.gantt.vo.GanttResponse;
import com.glaway.ids.jGraph.vo.NetworkVO;
import com.glaway.ids.jGraph.vo.PlanLinkVO;
import com.glaway.ids.jGraph.vo.PlanVO;
import com.glaway.ids.jGraph.vo.SwimLaneVO;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PreposePlanDto;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xshen
 * @version 2015年3月26日
 * @see PlanBusinessConfigController
 * @since
 */
@Controller
@RequestMapping("/graphController")
public class GraphController extends BaseController {

    /** 业务对象 */
    @Autowired
    private PlanRemoteFeignServiceI planServiceImpl;

    /** 日志消息 */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 画图
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "drawPlan")
    public void drawPlan(HttpServletRequest request, HttpServletResponse response) {
        List<PlanVO> planList = new ArrayList<PlanVO>();
        String projectId = request.getParameter("projectId");
        String planViewInfoId = request.getParameter("planViewInfoId");

        GanttResponse data = getGanttData(projectId,planViewInfoId);

        if (data.getData() != null) {
            List<PlanVO> pvList = getPlanVOsByGLPlanActivityGanttView(data);
            List<String> links = getLinks(data);

            if (!CommonUtil.isEmpty(pvList)) {
                planList = pvList;
                if (!CommonUtil.isEmpty(planList)) {
                    try {
                        drawPlanGraph(request, response, pvList, links);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取甘特图数据
     * 
     * @param projectId
     * @return
     * @see
     */
    private GanttResponse getGanttData(String projectId, String planViewInfoId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PlanDto p = new PlanDto();
        p.setProjectId(projectId);
        p.setPlanViewInfoId(planViewInfoId);
        List<PlanDto> planList = planServiceImpl.getPlanInfoList(p);
        planList = getList(planList,p);
        GanttRelates relates = new GanttRelates();
        GanttResponse data = new GanttResponse();

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
        for (PlanDto plan : planList) {
            GanttData data1 = new GanttData();
            data1.setLineNumber(plan.getPlanNumber());
            data1.setId(plan.getId());
            data1.setStart_date(sdf.format(plan.getPlanStartTime()));
            data1.setEnd_date(sdf.format(plan.getPlanEndTime()));
            data1.setParent(plan.getParentPlanId());
            data1.setWorkTime(plan.getWorkTime());
            if (plan.getProgressRate() == null) {
                data1.setProgress(0);
            }
            else {
                double rate = Double.parseDouble(plan.getProgressRate()) / 100;
                data1.setProgress(rate);
            }

            data1.setText(plan.getPlanName());
            data1.setOpen(false);
            if (plan.getOwnerInfo() != null) {
                data1.setOwner(plan.getOwnerInfo().getRealName() + "-"
                               + plan.getOwnerInfo().getUserName());
            }
            else {
                data1.setOwner("");
            }
            data1.setStatus(plan.getStatus());
            // data1.setRisk(transferRisk(plan.getRisk()));
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

        data.setCollections(relates);
        return data;
    }

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

    /**
     * plan转planVo
     * 
     * @param planView
     * @return
     * @see
     */
    private List<PlanVO> getPlanVOsByGLPlanActivityGanttView(GanttResponse planView) {
        Map<String, PlanVO> tmpMap = new HashMap<String, PlanVO>();
        List<PlanVO> planVOs = new ArrayList<PlanVO>();
        List<GanttData> planList = planView.getData();
        List<GanttRelate> linkList = planView.getCollections().getLinks();
        if (!CommonUtil.isEmpty(planList)) {
            for (int i = 0; i < planList.size(); i++ ) {
                PlanVO pv = new PlanVO();
                pv.setUuid(planList.get(i).getId());// 主键id
                pv.setPlanOid(planList.get(i).getId() + "");// 计划
                pv.setLineNumber(planList.get(i).getLineNumber() + "");// 行号
                pv.setPlanName(planList.get(i).getText());// 计划名称
                pv.setPlanDep("");// 计划执行部门
                pv.setBeginTime(DateUtil.stringtoDate(planList.get(i).getStart_date(),
                    "yyyy-MM-dd"));// 计划开始时间
                pv.setEndTime(DateUtil.stringtoDate(planList.get(i).getEnd_date(), "yyyy-MM-dd"));// 计划结束时间
                pv.setStatus(planList.get(i).getStatus());// 计划状态
                pv.setDelayDays(0);// 计划拖期天数
                pv.setExecuteStatus(planList.get(i).getStatus() == null ? "" : planList.get(i).getStatus());
                if (planList.get(i).getWorkTime() == null) {
                    pv.setTimes(0);// 工时
                }
                else {
                    pv.setTimes((int)Double.parseDouble(planList.get(i).getWorkTime()));// 工时
                }
                planVOs.add(pv);
                // }
            }
        }
        if (!CommonUtil.isEmpty(planVOs) && !CommonUtil.isEmpty(linkList)) {
            for (PlanVO pv : planVOs) {
                tmpMap.put(pv.getPlanOid(), pv);
            }
            for (GanttRelate link : linkList) {
                for (PlanVO pv : planVOs) {
                    if (pv.getPlanOid().equals(link.getTarget())) {
                        if (link.getType().equals("0")) {// FS
                            if (CommonUtil.isEmpty(pv.getParentOid())) {
                                pv.setParentOid(link.getSource());
                            }
                            else {
                                pv.setParentOid(pv.getParentOid() + "," + link.getSource());
                            }
                        }
                        else {// SS、FF
                            try {
                                if (CommonUtil.isEmpty(pv.getParentOid())) {
                                    pv.setParentOid(tmpMap.get(link.getSource()).getParentOid());
                                }
                                else {
                                    pv.setParentOid(pv.getParentOid() + ","
                                                    + tmpMap.get(link.getSource()).getParentOid());
                                }
                            }
                            catch (Exception ex) {
                                // System.out.println(ex.getMessage());
                            }
                        }
                        break;
                    }
                }
            }
        }
        return planVOs;
    }

    /**
     * 获取关系
     * 
     * @param planView
     * @return
     * @see
     */
    private List<String> getLinks(GanttResponse planView) {
        List<GanttRelate> linkList = planView.getCollections().getLinks();
        List<String> links = new ArrayList<String>();
        for (GanttRelate link : linkList) {
            links.add(link.getSource() + ",," + link.getTarget() + ",," + link.getType());
        }
        return links;
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

    /**
     * 计算当前计划以及子计划总工时 此为递归方法
     * 
     * @param plan
     * @param plans
     * @return
     */
    private int totalTimes(PlanVO plan, List<PlanVO> plans) {
        int total = plan.getTimes();
        for (PlanVO p : plans) {
            if (p.getParentOid() != null && !p.getPlanOid().equals(plan.getPlanOid())) {
                String[] parentOids = p.getParentOid().split(",");
                for (String oid : parentOids) {
                    if (oid.equals(plan.getPlanOid())) {
                        total += totalTimes(p, plans);
                    }
                }
            }
        }
        return total;
    }

    /**
     * 设置该计划下的所有
     * 
     * @param plan
     * @param plans
     */
    private void setChildPoid(PlanVO plan, List<PlanVO> plans) {
        List<String> childPoids = null;
        for (PlanVO p : plans) {
            if (!com.glaway.foundation.common.util.StringUtil.isEmpty(p.getParentOid())
                && !p.getPlanOid().equals(plan.getPlanOid())) {
                for (String poid : p.getParentOid().split(",")) {
                    if (poid.equals(plan.getPlanOid())) {
                        if (childPoids == null) {
                            childPoids = new ArrayList<String>();
                        }
                        childPoids.add(p.getPlanOid());
                    }
                }
            }
        }
        plan.setChildPoid(childPoids);
    }

    /**
     * 计算当前计划以及子计划树节点总数
     * 
     * @param plan
     * @param plans
     * @return
     */
    private int getPlanTreeTotal(PlanVO plan, List<PlanVO> plans) {
        int total = plan.getPlanTreeTotal() <= 0 ? 1 : plan.getPlanTreeTotal();
        for (PlanVO p : plans) {
            if (p.getParentOid() != null && !p.getPlanOid().equals(plan.getPlanOid())) {
                String[] parentOids = p.getParentOid().split(",");
                for (String oid : parentOids) {
                    if (oid.equals(plan.getPlanOid())) {
                        total += getPlanTreeTotal(p, plans);
                    }
                }
            }
        }
        return total;
    }

    /**
     * 比较两个日期大小
     * 
     * @param dat1
     * @param dat2
     * @return true 表示第二个日期在后
     */
    private static boolean dateCompare(Date dat1, Date dat2) {
        boolean dateComPareFlag = true;
        if (dat2.compareTo(dat1) != 1) {
            dateComPareFlag = false; //
        }
        return dateComPareFlag;
    }

    /**
     * 获取计划列表中最小（开始）时间 已挑战，取最小（完成）时间
     * 
     * @param plans
     * @return
     */
    private Date getMinDate(List<PlanVO> plans) {
        Date minDate = null;
        for (PlanVO p : plans) {
            if (minDate == null) {
                minDate = p.getEndTime();
            }
            else {
                if (dateCompare(p.getEndTime(), minDate)) {
                    minDate = p.getEndTime();
                }
            }
        }
        return minDate;
    }

    /**
     * 获取计划列表中最大（结束）时间
     * 
     * @param plans
     * @return
     */
    private Date getMaxDate(List<PlanVO> plans) {
        Date maxDate = null;
        for (PlanVO p : plans) {
            if (maxDate == null) {
                maxDate = p.getEndTime();
            }
            else {
                if (dateCompare(maxDate, p.getEndTime())) {
                    maxDate = p.getEndTime();
                }
            }
        }
        return maxDate;
    }

    /**
     * 获取泳道
     * 
     * @param minDate
     *            最早开始时间
     * @param maxDate
     *            最晚结束时间
     * @param stepLength
     *            泳道间间隔时间（单位：月）
     * @return
     */
    private List<Date> getSwimLaneDateList2(Date minDate, Date maxDate, int stepLength,
                                            List<PlanVO> planVOs) {
        List<Date> swimLaneDateList = new ArrayList<Date>();// 用于存放泳道时间的list
        int i = 0;// 循环递增变量
        Date maxDate_ = new Date();
        maxDate_.setYear(maxDate.getYear());
        maxDate_.setMonth(maxDate.getMonth());
        for (;;) {
            boolean breakAction = false;// 退出循环条件，当条件满足时退出
            Date tmpDate = new Date();
            tmpDate.setYear(minDate.getYear());
            tmpDate.setMonth((minDate.getMonth() + stepLength * i++ ));
            // 比较是否超出最晚结束时间， 一旦超出， 循环结束
            if (tmpDate.getTime() > maxDate_.getTime()) {
                breakAction = true;
            }
            else {
                swimLaneDateList.add(tmpDate);
            }
            if (breakAction) {
                break;
            }
        }
        return swimLaneDateList;
    }

    /**
     * 设置泳道下标 此为递归方法
     * 
     * @param plans
     *            计划list
     * @param swimLaneDateList
     *            泳道时间list
     * @param stepLength
     *            泳道间隔步长（单位：月） 1-按月 3-按季度 6-半年 12-年 系统默认按月
     */
    private void setSwimLaneIdx(List<PlanVO> plans, List<Date> swimLaneDateList, int stepLength) {
        if (stepLength == 1) {
            // 通过两层循环 计算每个计划归属与哪个泳道下
            for (PlanVO p : plans) {
                // 循环泳道时间，根据当前计划时间判断当前计划是否该泳道
                for (int idx = 0; idx < swimLaneDateList.size(); idx++ ) {
                    if (DateUtil.dateToString(p.getEndTime(), DateUtil.MONTG_DATE_FORMAT).equals(
                        DateUtil.dateToString(swimLaneDateList.get(idx),
                            DateUtil.MONTG_DATE_FORMAT))) {
                        p.setSwimLaneIdx(idx);
                        break;
                    }
                }
            }
        }
        else {
            for (PlanVO p : plans) {
                for (int idx = 0; idx < swimLaneDateList.size(); idx++ ) {
                    int pDate = Integer.parseInt(DateUtil.dateToString(p.getEndTime(),
                        DateUtil.MONTG_DATE_FORMAT).replace("-", ""));
                    int swimLaneDate_begin = Integer.parseInt(DateUtil.dateToString(
                        swimLaneDateList.get(idx), DateUtil.MONTG_DATE_FORMAT).replace("-", ""));
                    Date tmpDate = new Date();
                    tmpDate.setYear(swimLaneDateList.get(idx).getYear());
                    tmpDate.setMonth(swimLaneDateList.get(idx).getMonth() + stepLength);
                    tmpDate.setDate(1);
                    int swimLaneDate_end = Integer.parseInt(DateUtil.dateToString(tmpDate,
                        DateUtil.MONTG_DATE_FORMAT).replace("-", ""));
                    if (swimLaneDate_begin <= pDate && pDate < swimLaneDate_end) {
                        p.setSwimLaneIdx(idx);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 获取根计划 即parentOid为null
     * 
     * @param plans
     * @return
     */
    private List<PlanVO> getRootPlan(List<PlanVO> plans) {
        List<PlanVO> rootPlan = new ArrayList<PlanVO>();
        for (PlanVO p : plans) {
            if (com.glaway.foundation.common.util.StringUtil.isEmpty(p.getParentOid())) {
                rootPlan.add(p);
            }
        }
        return rootPlan;
    }

    /**
     * 获取关键路径上的计划节点 以','逗号分隔
     * 
     * @param rootPlan
     *            所有根计划
     * @param plans
     *            所有计划项
     * @return
     */
    private List<String> getCriticalPath(List<PlanVO> rootPlan, List<PlanVO> plans) {
        List<String> vipList = new ArrayList<String>();
        for (PlanVO p : rootPlan) {
            String planOid = p.getPlanOid();
            // 获取关键路径上的子计划
            List<String> vipChild = new ArrayList<String>();
            getVipChildPlan(planOid, plans, vipChild);
            if (vipChild.size() > 0) {
                for (String oid : vipChild) {
                    planOid += "," + oid;
                }
                vipList.add(planOid);
            }
        }
        return vipList;
    }

    /**
     * 获取计划的子计划关键节点 以','逗号分隔
     * 
     * @param planOid
     * @param plans
     * @param
     * @return
     */
    private void getVipChildPlan(String planOid, List<PlanVO> plans, List<String> vipList) {
        PlanVO plan = null;
        for (PlanVO p : plans) {
            if (p.getParentOid() != null && !p.getPlanOid().equals(planOid)) {
                for (String parentOid : p.getParentOid().split(",")) {
                    if (parentOid.equals(planOid)) {
                        if (plan == null) {
                            plan = p;
                        }
                        else {
                            if (p.getTotalTimes() > plan.getTotalTimes()) {
                                plan = p;
                            }
                        }
                    }
                }
            }
        }
        if (plan != null) {
            vipList.add(plan.getPlanOid());
            getVipChildPlan(plan.getPlanOid(), plans, vipList);
        }
    }

    /**
     * 根据泳道的个数 排出每个泳道的计划 计算x轴坐标位置
     * 
     * @param plans
     * @param swimLaneCount
     * @return
     */
    private List<List<PlanVO>> setPlanX(List<PlanVO> plans, int swimLaneCount) {
        List<List<PlanVO>> ydPlanList = new ArrayList<List<PlanVO>>();
        // 将plans存放至hashmap中 key为planOid
        Map<String, PlanVO> planListMap = new HashMap<String, PlanVO>();
        for (PlanVO p : plans) {
            planListMap.put(p.getPlanOid(), p);
        }
        // 根据泳道个数遍历所有计划
        for (int i = 0; i < swimLaneCount; i++ ) {
            // 将泳道内计划整理出来
            List<PlanVO> plist = new ArrayList<PlanVO>();
            for (PlanVO p : plans) {
                if (p.getSwimLaneIdx() == i) {
                    plist.add(p);
                }
            }
            // 冒泡排序该泳道内计划
            String[] oidArr = new String[plist.size()];// 放planOid 用数组进行冒泡排序
            if (plist.size() > 1) {
                for (int j = 0; j < plist.size(); j++ ) {
                    oidArr[j] = plist.get(j).getPlanOid();
                }
                String tmpPoid = null;
                for (int ii = 0; ii < oidArr.length; ii++ ) {
                    for (int jj = ii; jj < oidArr.length; jj++ ) {
                        if (planListMap.get(oidArr[jj]).getEndTime().getTime() < planListMap.get(
                            oidArr[ii]).getEndTime().getTime()) {
                            tmpPoid = oidArr[ii];
                            oidArr[ii] = oidArr[jj];
                            oidArr[jj] = tmpPoid;
                        }
                    }
                }
                plist = new ArrayList<PlanVO>();
                for (String poid : oidArr) {
                    plist.add(planListMap.get(poid));
                }
                int x = 0;// 定义x坐标轴 默认从0计算
                for (int arri = 0; arri < oidArr.length; arri++ ) {
                    if (arri == 0) {
                        planListMap.get(oidArr[arri]).setX(x);
                    }
                    else {
                        // 当计划时间在一个点上是 x轴坐标不变
                        if (planListMap.get(oidArr[arri]).getEndTime().getTime() == planListMap.get(
                            oidArr[arri - 1]).getEndTime().getTime()) {
                            planListMap.get(oidArr[arri]).setX(x);
                        }
                        // 当时间不在一个点上时 x轴坐标+1
                        else {
                            planListMap.get(oidArr[arri]).setX(++x);
                        }
                    }
                }
            }
            ydPlanList.add(plist);
        }
        return ydPlanList;
    }

    /**
     * 根据泳道的个数 排出每个泳道的计划 计划冒泡（倒序）排序
     * 
     * @param plans
     * @param ydCount
     * @return
     */
    private List<List<PlanVO>> getSwimLanePlanList(List<PlanVO> plans, int ydCount) {
        List<List<PlanVO>> ydPlanList = new ArrayList<List<PlanVO>>();
        // 将plans存放至hashmap中 key为planOid
        Map<String, PlanVO> planListMap = new HashMap<String, PlanVO>();
        for (PlanVO p : plans) {
            planListMap.put(p.getPlanOid(), p);
        }
        // 根据泳道个数遍历所有计划
        for (int i = 0; i < ydCount; i++ ) {
            // 将泳道内计划整理出来
            List<PlanVO> plist = new ArrayList<PlanVO>();
            for (PlanVO p : plans) {
                if (p.getSwimLaneIdx() == i) {
                    plist.add(p);
                }
            }
            // 冒泡排序该泳道内计划
            String[] oidArr = new String[plist.size()];// 放planOid 用数组进行冒泡排序
            if (plist.size() > 1) {
                for (int j = 0; j < plist.size(); j++ ) {
                    oidArr[j] = plist.get(j).getPlanOid();
                }
                String tmpPoid = null;
                for (int ii = 0; ii < oidArr.length; ii++ ) {
                    for (int jj = ii; jj < oidArr.length; jj++ ) {
                        if (planListMap.get(oidArr[jj]).getTotalTimes() > planListMap.get(
                            oidArr[ii]).getTotalTimes()) {
                            tmpPoid = oidArr[ii];
                            oidArr[ii] = oidArr[jj];
                            oidArr[jj] = tmpPoid;
                        }
                    }
                }
                plist = new ArrayList<PlanVO>();
                for (String poid : oidArr) {
                    plist.add(planListMap.get(poid));
                }
            }
            ydPlanList.add(plist);
        }
        return ydPlanList;
    }

    /**
     * 递归计算纵向y轴多少 共多少行
     * 计算规则 1.关键路径在一行上； 2.离散节点占一行；
     * 3.关键路径上面的计划，该计划没有后置计划或后置计划总工时少的往上排，第一个后置计划和当前计划在（占）同一行。关键路径下面的计划，
     * 计划没有子节点或子节点数少的往下排，第一个子计划和当前计划在同一行。
     * 
     * @param plans
     */
    private int calcPlanRow(List<PlanVO> rootPlan, List<PlanVO> plans) {
        // 将plans存放至hashmap中 key为planOid
        Map<String, PlanVO> planListMap = new HashMap<String, PlanVO>();
        for (PlanVO p : plans) {
            planListMap.put(p.getPlanOid(), p);
        }
        int rows = 0;
        List<Integer> childRow = new ArrayList<Integer>();
        for (PlanVO rootP : rootPlan) {
            rows++ ;
            calcPlanChildRow(childRow, rootP, planListMap);
        }
        for (int childs : childRow) {
            rows += childs;
        }
        return rows;
    }

    private void calcPlanChildRow(List<Integer> childRow, PlanVO curPlan,
                                  Map<String, PlanVO> planListMap) {
        if (!CommonUtil.isEmpty(curPlan.getChildPoid())) {
            childRow.add(curPlan.getChildPoid().size() - 1);
            for (String poid : curPlan.getChildPoid()) {
                calcPlanChildRow(childRow, planListMap.get(poid), planListMap);
            }
        }
    }

    /**
     * 计算所有计划的Y坐标
     * 
     * @param criticalPaths
     * @param rootPlan
     * @param plans
     * @param totalRow
     */
    private void setPlanY(List<String> criticalPaths, List<PlanVO> rootPlan, List<PlanVO> plans,
                          int totalRow) {
        // 所有关键路径的计划节点
        List<String> totalVipPath = new ArrayList<String>();
        for (String path : criticalPaths) {
            for (String poid : path.split(",")) {
                totalVipPath.add(poid);
            }
        }
        // 所有计划放置Map
        Map<String, PlanVO> planListMap = new HashMap<String, PlanVO>();
        String[] totalPlanOid = new String[plans.size()];
        int strArrIdx = 0;
        for (PlanVO p : plans) {
            planListMap.put(p.getPlanOid(), p);
            totalPlanOid[strArrIdx++ ] = p.getPlanOid();
        }
        // 设置所有后置计划
        for (PlanVO p : plans) {
            List<String> totalList = new ArrayList<String>();
            setPlanTotalChild(p, plans, planListMap, totalList);
            p.setTotalChildPoid(totalList);
        }
        // 冒泡排序 排序计划 计划数多的排上面
        String tmpPoid;
        for (int i = 0; i < totalPlanOid.length; i++ ) {
            for (int j = i; j < totalPlanOid.length; j++ ) {
                if (planListMap.get(totalPlanOid[j]).getPlanTreeTotal() > planListMap.get(
                    totalPlanOid[i]).getPlanTreeTotal()) {
                    tmpPoid = totalPlanOid[i];
                    totalPlanOid[i] = totalPlanOid[j];
                    totalPlanOid[j] = tmpPoid;
                }
            }
        }
        List<PlanVO> sortList = new ArrayList<PlanVO>();
        for (String poid : totalPlanOid) {
            sortList.add(planListMap.get(poid));
        }
        // 设置每个计划图层号
        int panelIdx = 0;
        for (PlanVO rootP : rootPlan) {
            rootP.setPanelIdx(panelIdx++ );
            if (!CommonUtil.isEmpty(rootP.getTotalChildPoid())) {
                for (String poid : rootP.getTotalChildPoid()) {
                    for (PlanVO pv : plans) {
                        if (pv.getPlanOid().equals(poid)) {
                            //pv.setPanelIdx(rootP.getPanelIdx());
                            pv.setPanelIdx(panelIdx++ );
                            break;
                        }
                    }
                }
            }
        }
        // 设置关键路径计划节点的Y坐标
        for (PlanVO rootP : rootPlan) {
            rootP.setY(totalRow);
            if (rootP.getChildPoid() != null) {
                // 关键路径计划设置
                setVipPlanRowIdx(rootP, planListMap, totalVipPath, totalRow);
                // 关键路径计划设置结束， 递归计算后置计划的y坐标
                calcChildPlanY(rootP, planListMap, totalVipPath);
            }
        }
    }

    /**
     * 设置所有后置计划
     * 
     * @param plan
     * @param plans
     * @param planListMap
     * @param totalList
     */
    private void setPlanTotalChild(PlanVO plan, List<PlanVO> plans,
                                   Map<String, PlanVO> planListMap, List<String> totalList) {
        if (!CommonUtil.isEmpty(plan.getChildPoid())) {
            for (String poid : plan.getChildPoid()) {
                if (!totalList.contains(poid)) {
                    totalList.add(poid);
                }
                if (!CommonUtil.isEmpty(planListMap.get(poid).getChildPoid())) {
                    setPlanTotalChild(planListMap.get(poid), plans, planListMap, totalList);
                }
            }
        }
    }

    /**
     * 递归计算后置计划的y坐标
     * 
     * @param plan
     * @param planListMap
     * @param vipPath
     */
    private void calcChildPlanY(PlanVO plan, Map<String, PlanVO> planListMap, List<String> vipPath) {
        setChildPlanRowIdx(plan, planListMap, vipPath);
        if (!CommonUtil.isEmpty(plan.getChildPoid())) {
            for (String childP : plan.getChildPoid()) {
                calcChildPlanY(planListMap.get(childP), planListMap, vipPath);
            }
        }
    }

    /**
     * 设置一个计划的后置计划的具体y坐标
     * 
     * @param plan
     * @param planListMap
     * @param vipPath
     * @param
     */
    private void setChildPlanRowIdx(PlanVO plan, Map<String, PlanVO> planListMap,
                                    List<String> vipPath) {
        if (!CommonUtil.isEmpty(plan.getChildPoid())) {
            boolean vip = vipPath.contains(plan.getPlanOid());
            // 如果计划是在关键路径 那么它的后续计划将上下摆放
            if (vip) {
                // 那么后续计划中必然有一个是关键路径上的计划，先找到那条计划
                int vipRow = 0;// 计算关键路径会占多少行
                for (String poid : plan.getChildPoid()) {
                    PlanVO p = planListMap.get(poid);
                    // 找到关键路径的计划
                    if (vipPath.contains(poid)) {
                        for (String id : p.getTotalChildPoid()) {
                            // 剔除关键路径的计划
                            if (!vipPath.contains(id)) {
                                vipRow++ ;
                            }
                        }
                    }
                }

                boolean up = true;
                int upRow = 0;
                int downRow = 0;
                for (String poid : plan.getChildPoid()) {
                    PlanVO p = planListMap.get(poid);
                    //p.setPanelIdx(plan.getPanelIdx());
                    // 剔除关键路径的计划
                    if (!vipPath.contains(poid)) {
                        int row = 0;
                        if (CommonUtil.isEmpty(p.getTotalChildPoid())) {
                            row = 1;
                        }
                        for (String id : p.getTotalChildPoid()) {
                            // 剔除关键路径的计划
                            if (!vipPath.contains(id)) {
                                row++ ;
                            }
                        }
                        if (up) {
                            upRow += row;
                            p.setY(plan.getY() - (vipRow / 2 + vipRow % 2) - upRow);
                            up = false;
                        }
                        else {
                            downRow += row;
                            p.setY(plan.getY() + (vipRow / 2) + downRow);
                            up = true;
                        }
                    }
                }
            }
            // 如果计划不在关键路径 那么剩下的后续计划将自上而下摆放 第一个后置计划的Y坐标继承自前置计划的Y坐标
            else {
                // 计算每个计划占多少行 可算出总共占多少行 计算规则，子计划中剔除关键路径的计划
                int downRow = 0;
                for (String poid : plan.getChildPoid()) {
                    PlanVO p = planListMap.get(poid);
                    // 剔除关键路径的计划
                    if (!vipPath.contains(poid)) {
                        int row = 0;
                        if (CommonUtil.isEmpty(p.getTotalChildPoid())) {
                            row = 1;
                        }
                        for (String id : p.getTotalChildPoid()) {
                            // 剔除关键路径的计划
                            if (!vipPath.contains(poid)) {
                                row++ ;
                            }
                        }
                        downRow += row;
                        p.setY(plan.getY() - 1 + downRow);
                    }
                }
            }
        }
    }

    /**
     * 设置关键路径计划节点的行号
     * 
     * @param plan
     * @param planListMap
     * @param vipPath
     * @param curRow
     */
    private void setVipPlanRowIdx(PlanVO plan, Map<String, PlanVO> planListMap,
                                  List<String> vipPath, int curRow) {
        if (!CommonUtil.isEmpty(plan.getChildPoid())) {
            // 检查后置计划节点是否存在关键路径的节点
            for (String childPoid : plan.getChildPoid()) {
                // 遇到关键路径的节点，设置在同一行
                if (vipPath.contains(childPoid)) {
                    planListMap.get(childPoid).setY(curRow);
                    //planListMap.get(childPoid).setPanelIdx(plan.getPanelIdx());
                    setVipPlanRowIdx(planListMap.get(childPoid), planListMap, vipPath, curRow);
                }
            }
        }
    }

    /**
     * 计算计划的x和y坐标轴 核心算法
     * 
     * @param planVOs
     * @param maxYdIdxArr
     *            每个泳道最大x坐标
     */
    private void calcCore(List<PlanVO> planVOs, Integer[] maxYdIdxArr) {
        int maxPanelIdx = 0;// 最大图层号
        Map<Integer, Integer> panelMap = null;// 图层信息map key图层idx value图层最小y坐标点
        int maxYdIdx = 0;// 最大泳道下标
        for (PlanVO planVO : planVOs) {
            // 循环比较 找到最大的图层号
            if (planVO.getPanelIdx() > maxPanelIdx) {
                maxPanelIdx = planVO.getPanelIdx();
            }
            // 循环比较 找到每个图层号下最小的y坐标点
            if (CommonUtil.isEmpty(panelMap)) {
                panelMap = new HashMap<Integer, Integer>();
                panelMap.put(planVO.getPanelIdx(), planVO.getY());
            }
            else {
                if (CommonUtil.isEmpty(panelMap.get(planVO.getPanelIdx()))
                    || (planVO.getY() < panelMap.get(planVO.getPanelIdx()))) {
                    panelMap.put(planVO.getPanelIdx(), planVO.getY());
                }
            }
            // 循环比较 找到最大的泳道下标
            if (planVO.getSwimLaneIdx() > maxYdIdx) {
                maxYdIdx = planVO.getSwimLaneIdx();
            }
        }
        // 开始计算合并图层后 每个计划节点所在整个图层的y坐标轴
        int tmpY = 0;// 比较计算用 y坐标轴
        int panelMaxY = 0;// 比较计算用 图层最大y坐标轴
        for (int panelIdx = 0; panelIdx <= maxPanelIdx; panelIdx++ ) {
            int panelMinY = panelMap.get(panelIdx);
            for (PlanVO planVO : planVOs) {
                if (planVO.getPanelIdx() == panelIdx) {
                    planVO.setY(planVO.getY() - (panelMinY - tmpY));
                    if (planVO.getY() > panelMaxY) {
                        panelMaxY = planVO.getY();
                    }
                }
            }
            tmpY = panelMaxY + 1;
        }
        // 开始计算合并泳道后 每个计划节点所在整个泳道的x坐标轴
        int tmpX = 0;// 比较计算用 x坐标轴
        for (int i = 0; i < maxYdIdxArr.length; i++ ) {
            for (PlanVO planVO : planVOs) {
                if (planVO.getSwimLaneIdx() == i) {
                    planVO.setX(planVO.getX() + tmpX);
                }
            }
            tmpX += maxYdIdxArr[i];
        }
    }

    /**
     * 画图
     * 
     * @param request
     * @param response
     * @param planList
     * @param links
     * @throws IOException
     * @see
     */
    private void drawPlanGraph(HttpServletRequest request, HttpServletResponse response,
                               List<PlanVO> planList, List<String> links)
        throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int width = 80;// 矩阵的宽
        int height = 80;// 矩阵的高

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int stepLength = 1;// 泳道间隔步长（单位：月） 1-按月 3-按季度 6-半年 12-年 系统默认按月
        if (!com.glaway.foundation.common.util.StringUtil.isEmpty(request.getParameter("m"))) {
            try {
                stepLength = Integer.parseInt(request.getParameter("m"));
            }
            catch (Exception ex) {
                stepLength = 1;
            }
        }
        // 递归该计划（含后置计划）计算并设置总工时
        for (PlanVO p : planList) {
            p.setTotalTimes(totalTimes(p, planList));
        }

        // 设置该计划下的所有后置计划
        for (PlanVO p : planList) {
            setChildPoid(p, planList);
        }
        // 递归该计划（后置计划）计算并设置计划树所有节点的个数
        for (PlanVO p : planList) {
            p.setPlanTreeTotal(getPlanTreeTotal(p, planList));
        }
        // 计算计划最早开始时间
        Date minDate = getMinDate(planList);
        // 计算计划最晚结束时间
        Date maxDate = getMaxDate(planList);
        // 计算最早开始时间到最晚结束时间需要的总工时（单位：天）
        // 计算泳道（按月）
        List<Date> swimLaneDateList = getSwimLaneDateList2(minDate, maxDate, stepLength, planList);
        // 根据泳道 计算并设置计划属于哪个泳道
        setSwimLaneIdx(planList, swimLaneDateList, stepLength);
        // 获取根计划 这里不排出离散计划，即只要没有后置计划的就都归为根计划
        List<PlanVO> rootPlan = getRootPlan(planList);
        // 获取关键路径上的计划节点 以','逗号分隔
        List<String> criticalPaths = getCriticalPath(rootPlan, planList);
        // 开始计算矩阵总行数总列数 rows与columns
        // 根节点计划的计划树总数之和 可计算矩阵的rows
        int rows = 0;
        for (PlanVO plan : rootPlan) {
            rows += plan.getPlanTreeTotal();
        }
        // 不重复的计划时间 可算出矩阵的columns 这里不重复需要逻辑判断
        int columns = 0;
        Map<String, String> tmpMap = new HashMap<String, String>();
        for (PlanVO p : planList) {
            if (tmpMap.size() == 0) {
                columns++ ;
                tmpMap.put(dateFormat.format(p.getEndTime()), dateFormat.format(p.getEndTime()));
            }
            else {
                if (tmpMap.get(dateFormat.format(p.getEndTime())) == null) {
                    columns++ ;
                    tmpMap.put(dateFormat.format(p.getEndTime()),
                        dateFormat.format(p.getEndTime()));
                }
            }
        }
        // 循环泳道 将泳道内计划节点横向排序 冒泡排序 计划结束时间早的排最最左面
        List<List<PlanVO>> swimLanePlanList = setPlanX(planList, swimLaneDateList.size());

        // 循环泳道 将泳道内计划节点纵向排序 冒泡排序 计划多的排最上面
        swimLanePlanList = getSwimLanePlanList(planList, swimLaneDateList.size());
        int totalRow = calcPlanRow(rootPlan, planList);
        // 计算所有计划的Y坐标
        setPlanY(criticalPaths, rootPlan, planList, totalRow);
        // 获取每个泳道的最大x轴
        Integer[] maxYdIdxArr = new Integer[swimLaneDateList.size()];
        for (int i = 0; i < swimLaneDateList.size(); i++ ) {
            int maxIdx = 0;
            for (PlanVO p : planList) {
                if (p.getSwimLaneIdx() == i && (p.getX() > maxIdx)) {
                    maxIdx = p.getX();
                }
            }
            maxYdIdxArr[i] = maxIdx + 1;
        }
        // 计算计划的x和y坐标轴
        calcCore(planList, maxYdIdxArr);

        Map<Integer, Integer> criticalPathMap = new HashMap<Integer, Integer>();// key图层号
        
        // value当前图层关键路径的y坐标
        // 找到关键路径那条连线的y坐标
        for (PlanVO rootP : rootPlan) {
            criticalPathMap.put(rootP.getPanelIdx(), rootP.getY());
            if (!CommonUtil.isEmpty(rootP.getTotalChildPoid())) {
                for (String poid : rootP.getTotalChildPoid()) {
                    for (PlanVO pv : planList) {
                        if (pv.getPlanOid().equals(poid)) {
                            //pv.setPanelIdx(rootP.getPanelIdx());
                            criticalPathMap.put(pv.getPanelIdx(), pv.getY());
                            break;
                        }
                    }
                }
            }
        }
        
        /*for (PlanVO p : rootPlan) {
            criticalPathMap.put(p.getPanelIdx(), p.getY());
        }*/

        NetworkVO networkVO = new NetworkVO();
        int maxY = 0;
        // 画计划节点
        List<PlanVO> planVOs = new ArrayList<PlanVO>();
        for (PlanVO p : planList) {
            PlanVO newVO = new PlanVO();
            BeanUtils.copyProperties(p, newVO);
            if (p.getY() > maxY) {
                maxY = p.getY();
            }
            newVO.setX((p.getX() * width + width));
            newVO.setY((p.getY() * height + height));
            newVO.setBeginTimeStr(DateUtil.dateToString(newVO.getBeginTime(),
                DateUtil.LONG_DATE_FORMAT));
            newVO.setEndTimeStr(DateUtil.dateToString(newVO.getEndTime(),
                DateUtil.LONG_DATE_FORMAT));
            String color = "#000000,#ffffff,#000000";// 是否超期
            if (newVO.getExecuteStatus().indexOf("已完成") >= 0
                || newVO.getExecuteStatus().indexOf("已完工") >= 0) {// 灰色色调
                color = "#525252";// 计划节点边框色
                color += ",#e3e3e3";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("待接收") >= 0) {// 绿色色调
                color = "#006006";// 计划节点边框色
                color += ",#bee4be";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("已发布") >= 0) {// 绿色色调
                color = "#006006";// 计划节点边框色
                color += ",#bee4be";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("已下达") >= 0) {// 绿色色调
                color = "#006006";// 计划节点边框色
                color += ",#bee4be";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("完工反馈中") >= 0) {// 红色色调
                color = "#ff0000";// 计划节点边框色
                color += ",#fde0e0";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("开工中") >= 0) {// 橙色色调
                color = "#f5570e";// 计划节点边框色
                color += ",#fa8d5a";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            else if (newVO.getExecuteStatus().indexOf("拟制中") >= 0) {// 蓝色色调
                color = "#0000ff";// 计划节点边框色
                color += ",#00bfff";// 计划节点填充色
                color += ",#000000";// 计划节点字体色
            }
            newVO.setColor(color);
            planVOs.add(newVO);
        }
        // 画泳道
        List<SwimLaneVO> swimVOs = new ArrayList<SwimLaneVO>();
        int ydTitleX = 0;
        Map<Integer, Integer> ydMap = new HashMap<Integer, Integer>();// key泳道下标 value当前泳道的x坐标
        int minY = 50;
        maxY = (maxY + 2) * height;
        SwimLaneVO swimLane = new SwimLaneVO();
        swimLane.setType("0");// 0-线 1-矩形
        swimLane.setSource((width - 10) + "," + minY);
        swimLane.setTarget((width - 10) + "," + maxY);
        swimVOs.add(swimLane);

        int swimLaneLineX = 0;
        int closeX = 0;
        for (int i = 0; i < maxYdIdxArr.length; i++ ) {// 循环每个泳道内的计划，找到最大x坐标点的计划，以最大x坐标轴计划为依据，向右画出泳道线
            swimLaneLineX += maxYdIdxArr[i];
            swimLane = new SwimLaneVO();// 泳道竖线
            swimLane.setType("0");// 0-线 1-矩形
            swimLane.setSource((width - 10 + width * swimLaneLineX) + "," + minY);
            swimLane.setTarget((width - 10 + width * swimLaneLineX) + "," + maxY);
            closeX = width - 10 + width * swimLaneLineX;
            swimVOs.add(swimLane);
            ydMap.put(i, swimLaneLineX);
            String swimLaneTitle = DateUtil.dateToString(swimLaneDateList.get(i),
                DateUtil.MONTG_DATE_FORMAT);
            if (stepLength > 1) {
                Date tmpDate = new Date();
                tmpDate.setYear(swimLaneDateList.get(i).getYear());
                tmpDate.setMonth(swimLaneDateList.get(i).getMonth() + stepLength - 1);
                swimLaneTitle += "至" + DateUtil.dateToString(tmpDate, DateUtil.MONTG_DATE_FORMAT);
            }
            swimLane = new SwimLaneVO();// 泳道矩阵标题
            swimLane.setType("1");// 0-线 1-矩形
            swimLane.setTarget((maxYdIdxArr[i] * width) + "," + 50);
            swimLane.setSource((width - 10 + width * swimLaneLineX - maxYdIdxArr[i] * width) + ","
                               + 10);
            swimLane.setTitle(swimLaneTitle);
            swimVOs.add(swimLane);
            ydTitleX = (width - 10 + width * swimLaneLineX) - 20;
            if (maxYdIdxArr[i] > 1) {
                ydTitleX -= maxYdIdxArr[i] / 2 * width;
            }
            ydTitleX -= width / 2;

        }
        swimLane = new SwimLaneVO();// 泳道闭合横线
        swimLane.setType("0");// 0-线 1-矩形
        swimLane.setSource((width - 10) + "," + maxY);
        swimLane.setTarget(closeX + "," + maxY);
        swimVOs.add(swimLane);

        Map<String, PlanVO> planMap = new HashMap<String, PlanVO>();
        for (PlanVO p : planList) {
            planMap.put(p.getPlanOid(), p);
        }
        // 画连线
        List<PlanLinkVO> edgeLinks = new ArrayList<PlanLinkVO>();
        int edgeIdx = 0;
        String strokeColor = "blue";
        for (PlanVO p : planList) {
            if (!CommonUtil.isEmpty(p.getChildPoid())) {
                for (String poid : p.getChildPoid()) {
                    strokeColor = "blue";
                    if (!CommonUtil.isEmpty(criticalPaths)) {
                        for (String str : criticalPaths) {
                            List<String> tmpList = new ArrayList<String>();
                            for (String vipPoid : str.split(",")) {
                                tmpList.add(vipPoid);
                            }
                            if (tmpList.contains(poid) && tmpList.contains(p.getPlanOid())) {
                                strokeColor = "red";
                            }
                        }
                    }
                    PlanLinkVO link = new PlanLinkVO();
                    link.setColor(strokeColor);
                    link.setSource(p.getPlanOid());
                    link.setTarget(poid);
                    link.setType("");// 这条连线的样式 fs、ss、ff 以及虚线； 默认为虚线
                    if (!CommonUtil.isEmpty(links)) {
                        if (links.contains(link.getSource() + ",," + link.getTarget() + ",,0")) {
                            links.remove(link.getSource() + ",," + link.getTarget() + ",,0");
                            link.setType("fs");
                        }
                        else if (links.contains(link.getSource() + ",," + link.getTarget() + ",,1")) {
                            links.remove(link.getSource() + ",," + link.getTarget() + ",,0");
                            link.setType("ss");
                        }
                        if (links.contains(link.getSource() + ",," + link.getTarget() + ",,2")) {
                            links.remove(link.getSource() + ",," + link.getTarget() + ",,0");
                            link.setType("ff");
                        }
                    }
                    List<String> linePoint = new ArrayList<String>();
                    // 判断两计划之间是否跨越了泳道，如果跨越泳道 且两计划不做同一y坐标上，那么需要在泳道线上加折点
                    if (planMap.get(poid).getSwimLaneIdx() != p.getSwimLaneIdx()
                        && planMap.get(poid).getY() != p.getY()) {
                        int pY = (p.getY() + 1) * height + 30;// 当前计划的前置计划的y坐标
                        int ppY = (planMap.get(poid).getY() + 1) * height + 30;// 当前计划的y坐标
                        int subY = (criticalPathMap.get(p.getPanelIdx()) + 1) * height + 30;// 当前图层关键路径y坐标
                                                                                            // 用于下方逻辑比较用
                        int ydX = ydMap.get(p.getSwimLaneIdx());
                        int pointX = 0;
                        int pointY = 0;
                        if (pY <= subY && ppY <= subY) {// 属于关键路径上方的折线
                            if (pY < ppY) {// 如果 当前计划在前置计划的上方，那么折点的x轴取泳道的x轴，y取当前计划的y坐标
                                pointX = (ydX + 1) * width - 10;
                                pointY = pY;
                            }
                            else {// 否则 当前计划在前置计划的下方
                                pointX = (ydX + 1) * width - 10;
                                pointY = ppY;
                            }
                            if (linePoint.contains(pointX + "," + pointY)) {
                                for (;;) {
                                    pointY += 10;
                                    if (!linePoint.contains(pointX + "," + pointY)) {
                                        break;
                                    }
                                }
                            }
                        }
                        else {// 属于关键路径下方的折线
                            if (pY < ppY) {
                                pointX = (ydX + 1) * width - 10;
                                pointY = ppY;
                            }
                            else {
                                pointX = ydX;
                                pointY = pY;
                            }
                            if (linePoint.contains(pointX + "," + pointY)) {
                                for (;;) {
                                    pointY -= 10;
                                    if (!linePoint.contains(pointX + "," + pointY)) {
                                        break;
                                    }
                                }
                            }
                        }
                        String[] point = {pointX + "," + pointY};
                        link.setPoint(point);
                    }
                    edgeLinks.add(link);
                    edgeIdx++ ;
                }
            }
        }
        if (!CommonUtil.isEmpty(links)) {
            for (String str : links) {
                PlanLinkVO link = new PlanLinkVO();
                link.setSource(str.split(",,")[0]);
                link.setTarget(str.split(",,")[1]);
                // 这条连线的样式 fs、ss、ff 以及虚线； 默认为虚线
                if (str.split(",,")[2].equals("1")) {
                    link.setType("ss");
                }
                else if (str.split(",,")[2].equals("2")) {
                    link.setType("ff");
                }
                link.setColor("#ff6600");
                edgeLinks.add(link);
            }
        }
        networkVO.setPlanVOs(planVOs);
        networkVO.setSwimLaneVOs(swimVOs);
        networkVO.setPlanLinkVOs(edgeLinks);
        out.print(JSON.toJSONString(networkVO));
        out.flush();
        out.close();
    }
}
