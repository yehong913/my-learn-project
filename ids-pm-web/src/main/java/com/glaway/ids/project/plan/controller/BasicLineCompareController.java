package com.glaway.ids.project.plan.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.StringUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.project.plan.dto.BasicLineDto;
import com.glaway.ids.project.plan.dto.BasicLinePlanDto;
import com.glaway.ids.project.plan.service.FeignBasicLineServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.plan.vo.BasicLineColumn;
import com.glaway.ids.project.plan.vo.BasicLineTree;
import com.glaway.ids.project.plan.vo.BasicLineTreeNode;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
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
 * @Title: Controller
 * @Description: 基线对比管理
 * @author xshen
 * @date 2015-03-23 16:32:26
 * @version V1.0
 */
@Controller
@RequestMapping("/basicLineCompareController")
public class BasicLineCompareController extends BaseController {

    /**
     * 基线管理接口
     */
    @Autowired
    private FeignBasicLineServiceI basicLineService;

    @Autowired
    private PlanRemoteFeignServiceI planService;

    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "srcTree")
    public void srcTree(HttpServletRequest request, HttpServletResponse response) {
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        String columns = request.getParameter("columns");

        BasicLineTree tree = mergeTree(id1, id2, "tree1", columns);

        String json = JSON.toJSONString(tree);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "destTree")
    public void destTree(HttpServletRequest request, HttpServletResponse response) {
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        String columns = request.getParameter("columns");

        BasicLineTree tree = mergeTree(id1, id2, "tree2", columns);
        String json = JSON.toJSONString(tree);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "constructTree")
    public void constructTree(HttpServletRequest request, HttpServletResponse response) {
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        String columns = request.getParameter("columns");

        BasicLineTree tree = new BasicLineTree();
        BasicLineDto basicLine = new BasicLineDto();
        List<BasicLinePlanDto> list = new ArrayList<>();
        if(id2.equals("current")){
            basicLine = (BasicLineDto) request.getSession().getAttribute("basicline-contrast");
            list = (List<BasicLinePlanDto>)request.getSession().getAttribute("basicline-contrastList");
        }
        BasicLineDto left = getOrderPlan(id1, id2, "leftTree",basicLine);// 取得左边基线
        BasicLineDto right = getOrderPlan(id1, id2, "rightTree",basicLine);// 取得右边基线
        List<BasicLineTreeNode> leftRoot = constructTree(left, right, columns, "leftTree",list);// 取得左边树结构
        List<BasicLineTreeNode> rightRoot = constructTree(left, right, columns, "rightTree",list);// 取得右边树结构

        tree.setLeftTree(leftRoot);
        tree.setRightTree(rightRoot);
        tree.setColoums(BasicLineTree.setColumns(columns));
        if(StringUtil.isEmpty(left.getBasicLineName())){
            left.setBasicLineName("当前计划");
        }
        if(StringUtil.isEmpty(right.getBasicLineName())){
            right.setBasicLineName("当前计划");
        }
        tree.setLeftName(left.getBasicLineName() + '_'
                         + new SimpleDateFormat("yyyy-MM-dd").format(left.getCreateTime()));// 设置左边树名称
        tree.setRightName(right.getBasicLineName() + '_'
                          + new SimpleDateFormat("yyyy-MM-dd").format(right.getCreateTime()));// 设置右边树名称

        String json = JSON.toJSONString(tree);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 根据顺序获取计划
     * 
     * @param id1
     * @param id2
     * @param order
     * @return
     * @see
     */
    private BasicLineDto getOrderPlan(String id1, String id2, String order,BasicLineDto basicLine) {
        BasicLineDto rtBasicLine = null;

        BasicLineDto basicLine1 = basicLineService.getBasicLineEntity(id1);
        BasicLineDto basicLine2 = new BasicLineDto();
        if(id2.equals("current")){
            basicLine2 =  basicLine;
        }else{
            basicLine2 = basicLineService.getBasicLineEntity(id2);
        }
        BasicLineDto first = basicLine1;
        BasicLineDto last = basicLine2;
        if (!DateUtil.dateCompare(basicLine1.getCreateTime(), basicLine2.getCreateTime())) {
            first = basicLine2;
            last = basicLine1;
        }

        if (order.equals("leftTree")) {
            rtBasicLine = first;
        }
        else {
            rtBasicLine = last;
        }

        return rtBasicLine;
    }

    /**
     * 构建树
     * 
     * @param first
     * @param last
     * @param columnNames
     * @param treeType
     * @return
     * @see
     */
    private List<BasicLineTreeNode> constructTree(BasicLineDto first, BasicLineDto last,
                                                  String columnNames, String treeType,List<BasicLinePlanDto> list) {
        List<BasicLineTreeNode> allList = new ArrayList<BasicLineTreeNode>();// 节点容器
        List<BasicLineTreeNode> otherList = new ArrayList<BasicLineTreeNode>();// 节点容器

        List<BasicLinePlanDto> list1 = new ArrayList<BasicLinePlanDto>();
        List<BasicLinePlanDto> list2 = new ArrayList<BasicLinePlanDto>();
        if (treeType.equals("leftTree")) {
            BasicLinePlanDto basicLinePlan1 = new BasicLinePlanDto();
            basicLinePlan1.setBasicLineId(first.getId());
            list1 = basicLineService.queryBasicLinePlanList(basicLinePlan1, 1, 10, false);

            BasicLinePlanDto basicLinePlan2 = new BasicLinePlanDto();
            basicLinePlan2.setBasicLineId(last.getId());
            if(CommonUtil.isEmpty(list)){
                list2 = basicLineService.queryBasicLinePlanList(basicLinePlan2, 1, 10, false);
            }else{
                list2 = list;
            }
        }
        else {
            BasicLinePlanDto basicLinePlan1 = new BasicLinePlanDto();
            basicLinePlan1.setBasicLineId(last.getId());
            if(CommonUtil.isEmpty(list)){
                list1 = basicLineService.queryBasicLinePlanList(basicLinePlan1, 1, 10, false);
            }else{
                list1 = list;
            }

            BasicLinePlanDto basicLinePlan2 = new BasicLinePlanDto();
            basicLinePlan2.setBasicLineId(first.getId());
            list2 = basicLineService.queryBasicLinePlanList(basicLinePlan2, 1, 10, false);
        }

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
        Map<String, String> statusMap = new HashMap<String, String>();;
        for (LifeCycleStatus status : statusList) {
            statusMap.put(status.getName(), status.getTitle());
        }
  
        for (BasicLinePlanDto plan : list2) {
            BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
          
            otherList.add(node);
        }
        // 将一棵树的所有节点放进容器
        for (BasicLinePlanDto plan : list1) {
            BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
            node.setBelong(treeType);
            allList.add(node);
        }

        for (BasicLineTreeNode node : otherList) {
            for (BasicLineTreeNode exist : allList) {
                if (exist.getPlanId().equals(node.getPlanId())) {
                    if (!exist.compareRecord(node, columnNames)) {
                        exist.setBelong("change");
                        exist.changeNameInit(node, columnNames);// 将改变的属性信息保存起来，返回给前台，前台对TD里元素加深红色使用
                    }
                    else {
                        exist.setBelong("all");
                    }
                    node.setBelong("exist");
                }
            }
            if (node.getBelong() == null) {
                if (treeType.equals("leftTree")) {
                    node.setBelong("rightTree");
                }
                else {
                    node.setBelong("leftTree");
                }
                node.clear();
                allList.add(node);
            }
        }
        for (BasicLineTreeNode exist : allList) {
            if ("all".equals(exist.getBelong()) || "change".equals(exist.getBelong())) {
                if (StringUtil.isEmpty(exist.getParentId())) {
                    BasicLineTreeNode sameLevel = getSameLevelNode(exist, otherList);
                    exist.setParentId(sameLevel.getParentId());
                }
            }
            else if (!exist.getBelong().equals(treeType)) {// 处理来自另一棵树的节点
                BasicLineTreeNode parent = this.getParent(exist, allList);
                if (parent == null) {
                    BasicLineTreeNode sameLevel = getSameLevelNode(exist, otherList);// 获取前另一棵树上同级节点
                    BasicLineTreeNode otherTreeParent = this.getParent(sameLevel, otherList);// 获取同级父节点
                    if (otherTreeParent != null) {
                        BasicLineTreeNode realParent = getSameLevelNode(otherTreeParent, allList);// 获取自己树上的真正父节点
                        exist.setParentId(realParent.getId());// 设置父节点Id
                    }
                }
            }
        }

        Collections.sort(allList, new Comparator<BasicLineTreeNode>() {
            @Override
            public int compare(BasicLineTreeNode o1, BasicLineTreeNode o2) {
                if (o1.getPlanId().compareTo(o2.getPlanId()) > 0) {
                    return 1;
                }
                else if (o1.getPlanId().compareTo(o2.getPlanId()) < 0) {
                    return -1;
                }
                return 0;
            }
        });

        List<BasicLineTreeNode> roots = new ArrayList<BasicLineTreeNode>();
        List<BasicLineTreeNode> nodes = new ArrayList<BasicLineTreeNode>();
        int number=0;
        for (BasicLineTreeNode plan : allList) {
            plan.setRownumber( ++number+"");
            if (CommonUtil.isEmpty(plan.getParentId())) {
                roots.add(plan);
            }
            else {
                nodes.add(plan);
            }
           
        }

        for (BasicLineTreeNode node : roots) {
            processNodes(nodes, node);
        }

        return roots;
    }

    /**
     * 从列表里获取父节点
     * 
     * @param node
     * @param list
     * @return
     * @see
     */
    private BasicLineTreeNode getParent(BasicLineTreeNode node, List<BasicLineTreeNode> list) {
        BasicLineTreeNode rtnode = null;
        if (node.getParentId() == null) {
            return null;
        }
        for (BasicLineTreeNode bt : list) {
            if (node.getParentId().equals(bt.getId())) {
                rtnode = bt;
            }
        }
        return rtnode;
    }

    private BasicLineTreeNode getSameLevelNode(BasicLineTreeNode node, List<BasicLineTreeNode> list) {
        BasicLineTreeNode rtnode = null;
        for (BasicLineTreeNode bt : list) {
            if (node.getPlanId().equals(bt.getPlanId())) {
                rtnode = bt;
            }
        }
        return rtnode;
    }

    /**
     * 合并两棵树，形成一颗
     * 
     * @param id1
     * @param id2
     * @param type
     * @param columnNames
     * @return
     * @see
     */
    private BasicLineTree mergeTree(String id1, String id2, String type, String columnNames) {
        BasicLineTree tree = new BasicLineTree();

        BasicLinePlanDto basicLinePlan1 = new BasicLinePlanDto();
        basicLinePlan1.setBasicLineId(id1);
        List<BasicLinePlanDto> list1 = basicLineService.queryBasicLinePlanList(basicLinePlan1, 1, 10,
            false);

        BasicLinePlanDto basicLinePlan2 = new BasicLinePlanDto();
        basicLinePlan2.setBasicLineId(id2);
        List<BasicLinePlanDto> list2 = basicLineService.queryBasicLinePlanList(basicLinePlan2, 1, 10,
            false);

        List<BasicLineTreeNode> allList = new ArrayList<BasicLineTreeNode>();

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
        Map<String, String> statusMap = new HashMap<String, String>();
        for (LifeCycleStatus status : statusList) {
            statusMap.put(status.getName(), status.getTitle());
        }

        if (type.equals("tree1")) {
            for (BasicLinePlanDto plan : list1) {
                BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
                node.setBelong("tree1");
                allList.add(node);
            }
        }
        else {
            for (BasicLinePlanDto plan : list2) {
                BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
                node.setBelong("tree2");
                allList.add(node);
            }
        }

        List<BasicLineTreeNode> roots = new ArrayList<BasicLineTreeNode>();
        List<BasicLineTreeNode> nodes = new ArrayList<BasicLineTreeNode>();
        for (BasicLineTreeNode plan : allList) {
            if (CommonUtil.isEmpty(plan.getParentId())) {
                roots.add(plan);
            }
            else {
                nodes.add(plan);
            }
        }

        for (BasicLineTreeNode node : roots) {
            processNodes(nodes, node);
        }

        tree.setRoots(roots);
        tree.setColoums(BasicLineTree.setColumns(columnNames));

        return tree;
    }

    @RequestMapping(params = "reConstructTree")
    public void reConstructTree(HttpServletRequest request, HttpServletResponse response) {
        String names = request.getParameter("columns");
        List<BasicLineColumn> columns = BasicLineTree.setColumns(names);

        String json = JSON.toJSONString(columns);
        TagUtil.ajaxResponse(response, json);
    }

    @RequestMapping(params = "tableColor")
    public void tableColor(HttpServletRequest request, HttpServletResponse response) {
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        String columns = request.getParameter("columns");
        BasicLineDto basicLine = new BasicLineDto();
        if(id2.equals("current")){
            basicLine = (BasicLineDto) request.getSession().getAttribute("basicline-contrast");
            List<BasicLineDto> list = (List<BasicLineDto>)request.getSession().getAttribute("basicline-contrastList");
        }
        BasicLineDto left = getOrderPlan(id1, id2, "leftTree",basicLine);// 取得左边基线
        BasicLineDto right = getOrderPlan(id1, id2, "rightTree",basicLine);// 取得右边基线

        BasicLinePlanDto basicLinePlan1 = new BasicLinePlanDto();
        basicLinePlan1.setBasicLineId(left.getId());
        List<BasicLinePlanDto> list1 = basicLineService.queryBasicLinePlanList(basicLinePlan1, 1, 10,
            false);

        BasicLinePlanDto basicLinePlan2 = new BasicLinePlanDto();
        basicLinePlan2.setBasicLineId(right.getId());
        List<BasicLinePlanDto> list2 = basicLineService.queryBasicLinePlanList(basicLinePlan2, 1, 10,
            false);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
        Map<String, String> statusMap = new HashMap<String, String>();
        for (LifeCycleStatus status : statusList) {
            statusMap.put(status.getName(), status.getTitle());
        }

        List<BasicLineTreeNode> allList = new ArrayList<BasicLineTreeNode>();
        for (BasicLinePlanDto plan : list1) {
            BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
            node.setBelong("leftTree");
            allList.add(node);
        }

        for (BasicLinePlanDto plan : list2) {
            BasicLineTreeNode node = BasicLineTreeNode.transfer(plan, statusMap);
            for (BasicLineTreeNode exist : allList) {
                if (exist.getPlanId().equals(node.getPlanId())) {
                    if (!exist.compareRecord(node, columns)) {
                        exist.setBelong("change");// 标志记录改变，前台TR元素加浅红色用
                        exist.changeNameInit(node, columns);// 将改变的属性信息保存起来，返回给前台，前台对TD里元素加深红色使用
                    }
                    else {
                        exist.setBelong("all");
                    }
                    node.setBelong("exist");
                    break;
                }
            }
            if (node.getBelong() == null) {
                node.setBelong("rightTree");
                allList.add(node);
            }
        }

        String json = JSON.toJSONString(allList);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 配置状态下拉列表
     * 
     * @return
     * @see
     */
    @RequestMapping(params = "typeCombox")
    public void typeCombox(HttpServletRequest request, HttpServletResponse response) {
        List<BusinessConfigDto> list = new ArrayList<BusinessConfigDto>();

        BusinessConfigDto add = new BusinessConfigDto();
        add.setId("add");
        add.setName("对比新增内容");

        BusinessConfigDto delete = new BusinessConfigDto();
        delete.setId("delete");
        delete.setName("对比删除内容");

        BusinessConfigDto modify = new BusinessConfigDto();
        modify.setId("modify");
        modify.setName("对比修改内容");

        list.add(add);
        list.add(delete);
        list.add(modify);
        String json = JSON.toJSONString(list);

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

    /**
     * 配置状态下拉列表
     * 
     * @return
     * @see
     */
    @RequestMapping(params = "dataCombox")
    public void dataCombox(HttpServletRequest request, HttpServletResponse response) {
        List<BusinessConfigDto> list = new ArrayList<BusinessConfigDto>();

        BusinessConfigDto add = new BusinessConfigDto();
        add.setId("planLevel");
        add.setName("计划等级");

        BusinessConfigDto delete = new BusinessConfigDto();
        delete.setId("status");
        delete.setName("状态");

        BusinessConfigDto modify = new BusinessConfigDto();
        modify.setId("owner");
        modify.setName("负责人");

        BusinessConfigDto startTime = new BusinessConfigDto();
        startTime.setId("startTime");
        startTime.setName("开始时间");

        BusinessConfigDto endTime = new BusinessConfigDto();
        endTime.setId("endTime");
        endTime.setName("结束时间");

        BusinessConfigDto workTime = new BusinessConfigDto();
        workTime.setId("workTime");
        workTime.setName("工期");

        BusinessConfigDto deliverables = new BusinessConfigDto();
        deliverables.setId("deliverables");
        deliverables.setName("输出");

        list.add(add);
        list.add(delete);
        list.add(modify);
        list.add(startTime);
        list.add(endTime);
        list.add(workTime);
        list.add(deliverables);

        String json = JSON.toJSONString(list);

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

    /**
     * 循环处理树节点
     * 
     * @param all
     * @param parent
     * @see
     */
    private void processNodes(List<BasicLineTreeNode> all, BasicLineTreeNode parent) {
        for (BasicLineTreeNode node : all) {
            if (node.getParentId().equals(parent.getId())) {
                parent.getChildren().add(node);
                processNodes(all, node);
            }
        }
    }

}
