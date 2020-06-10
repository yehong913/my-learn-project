package com.glaway.ids.project.plm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.ContainerDto;
import com.glaway.ids.project.plm.dto.PartMasterDto;
import com.glaway.ids.project.plm.service.PartServiceII;
import com.glaway.ids.project.plm.vo.BomNode;
import com.glaway.ids.project.plm.vo.PartVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.RequestMapUtil;

import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.ConditionVO;

import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *
 * Part ids
 * @date 2019年10月18日
 * @see IDSPartController
 * @since
 */
@RestController
@RequestMapping("/idsPartController")
public class IDSPartController extends BaseController {

    @Autowired
    PartServiceII partServiceI;
    /**
     * 获得现有的部件
     * @param request
     * @param response
     */
    @RequestMapping(params = "getAllExistPart")
    public void getAllExistPart(HttpServletRequest request,HttpServletResponse response){
        String partId = request.getParameter("partId");
        String productId = request.getParameter("productId");
        String viewId = request.getParameter("viewId");
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        List<CheckOutInfoVO> list = partServiceI.findExistPart(conditionList,partId, productId, viewId, UserUtil.getCurrentUser().getId());
        int size = partServiceI.getExistPartSizeByCondition(conditionList,partId, productId,viewId, UserUtil.getCurrentUser().getId());
        String json = JsonUtil.getListJsonWithoutQuote(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + size + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }
    /**
     * 判断子项是否已添加
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "isSonPartExist")
    @ResponseBody
    public AjaxJson isSonPartExist(HttpServletRequest request, HttpServletResponse response){
        AjaxJson resultMsg=new AjaxJson();
        String fatherPartId=request.getParameter("fatherPartId");
        String addIdArray = request.getParameter("addIdArray");
        List<String> sonPartIdList = JSONArray.parseArray(addIdArray, String.class);
        Boolean flag = partServiceI.isSonPartExist(fatherPartId, sonPartIdList);
        resultMsg.setMsg("请求成功");
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("isExist",flag);
        resultMsg.setAttributes(resultMap);
        return resultMsg;
    }

    /**
     * 保存全局替换部件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveExistPart")
    @ResponseBody
    public AjaxJson saveExistPart(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("新增成功");
        //保存后台返回的值
        List<PartVo> resultList = new ArrayList<PartVo>();
        //特定部件添加标志位
        String sub = request.getParameter("sub");
        //当前部件的父id
        String roleAId = request.getParameter("roleAId");
        //当前部件id
        String partId = request.getParameter("partId");
        //获取当前部件的所有父部件不能添加 TODO

        String partArr = request.getParameter("partArray");
        //将list转成json
        net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(partArr);
        List<PartMasterDto> masterList = new ArrayList<PartMasterDto>();
        net.sf.json.JSONObject jo;
        for (int i = 0; i < json.size(); i++) {
            PartMasterDto roleB = new PartMasterDto();
            //得到json对象
            jo = json.getJSONObject(i);
            roleB.setId(jo.getString("bizId"));
            masterList.add(roleB);
        }
        if("alt".equals(sub)){
            //全局部件添加
            resultList = partServiceI.saveExistPart(partId, masterList);
            j.setObj(resultList);
        }else if("sub".equals(sub)){
            //特定部件添加
            resultList = partServiceI.saveSubExistPart(roleAId,partId, masterList);
            j.setObj(resultList);
        }
        return j;
    }

    /*
     * 上下文列表数据
     * @param request
     * @param response
    */
    @RequestMapping(params = "getContextList")
    @ResponseBody
    public void getContextList(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        List<ContainerDto> list = partServiceI.getContextList(UserUtil.getCurrentUser().getId());
        if(!CommonUtil.isEmpty(list)){
            for (ContainerDto container : list) {
                JSONObject obj = new JSONObject();
                obj.put("id", container.getId());
                obj.put("title", container.getName());
                jsonList.add(obj);
            }
        }
        String jonStr = JSON.toJSONString(jsonList);
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入现有的部件
     * @param request
     * @return
     */
    @RequestMapping(params = "insertExistPart")
    @ResponseBody
    public AjaxJson insertExistPart(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String partId = request.getParameter("partId");
        String productId = "";
        String partIds = request.getParameter("partIds");
        //lazy=root为懒加载
        String lazy = request.getParameter("lazy");
        String partIdForNew = request.getParameter("partIdForNew");
        //插入现有的部件masterid
        if(!CommonUtil.isEmpty(partId) && !CommonUtil.isEmpty(partIds)){
            FeignJson fj = partServiceI.insertExistPart(productId,partId, partIds, partIdForNew, UserUtil.getCurrentUser().getId(), lazy);
            j.setSuccess(fj.isSuccess());
            j.setObj(fj.getObj());
            j.setMsg(fj.getMsg());
        }else{
            j.setSuccess(false);
            j.setMsg("插入失败");
        }
        return j;
    }

    /*
     * 获得bom树结构
     * @param request
     * @param response
     */
    @RequestMapping(params = "getPartBomTree")
    @ResponseBody
    public void getPartBomTree(HttpServletRequest request, HttpServletResponse response) {
        List<TreeNode> jsonList = new ArrayList<TreeNode>();
        String partId = request.getParameter("id");
        jsonList = partServiceI.getPartBomTree(partId, UserUtil.getCurrentUser().getId());
        Map<String, String> dataObj;
        String partName= "";
        if (!CommonUtil.isEmpty(jsonList)){
            for (TreeNode treeNode: jsonList) {
                treeNode.setId(treeNode.getId().split("_")[0]);
                dataObj = new HashMap<String, String>();
                dataObj = (Map<String, String>)treeNode.getDataObject();
                partName = dataObj.get("partName");
                treeNode.setTitle(partName.split(",")[0]);
                treeNode.setName(partName.split(",")[1]);
                treeNode.setPid(treeNode.getPid().split("_")[0]);
            }
        }
        BomNode bomNode = getTreeNode(jsonList, "", "",new BomNode());
        List<BomNode> nodeList = new ArrayList<>();
        nodeList.add(bomNode);
        String jonStr = JSON.toJSONString(nodeList);
        System.out.println(jonStr);
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BomNode getTreeNode(List<TreeNode> jsonList,String pid, String id,BomNode bomNode){
        try{
            List<BomNode> childrenNode = new ArrayList<>();
            List<TreeNode> returnNode = new ArrayList<>();
            for (TreeNode node: jsonList) {
                if (StringUtils.isEmpty(node.getPid())){
                    bomNode.setId(node.getId());
                    bomNode.setPid(pid);
                    bomNode.setCode(node.getTitle());
                    bomNode.setName(node.getName());
                    if (StringUtils.isEmpty(id)){
                        id = node.getId();
                    }
                } else if (StringUtils.isNotEmpty(id)&&node.getPid().equals(id)){
                    BomNode chNode = new BomNode();
                    chNode.setId(node.getId());
                    chNode.setPid(node.getPid());
                    chNode.setCode(node.getTitle());
                    chNode.setName(node.getName());
                    childrenNode.add(chNode);
                } else {
                    returnNode.add(node);
                }
            }
            List<BomNode> list = bomNode.getRows();
            if (CommonUtil.isEmpty(list)){
                bomNode.setRows(childrenNode);
            }else {
                for (BomNode node:list) {
                    if (node.getId().equals(childrenNode.get(0).getPid())){
                        node.setRows(childrenNode);
                    }
                }
            }
            if (returnNode.size()>0){
                for (BomNode node: childrenNode) {
                    getTreeNode(returnNode, node.getId(), node.getId(), bomNode);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  bomNode;
    }


}

