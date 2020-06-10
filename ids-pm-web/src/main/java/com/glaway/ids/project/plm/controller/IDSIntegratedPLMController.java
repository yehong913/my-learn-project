package com.glaway.ids.project.plm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.RequestMapUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.PlmTypeDefinitionDto;
import com.glaway.ids.project.plm.service.IDSIntegratedPLMService;
import com.glaway.ids.project.plm.vo.ComboTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * IDS控制层
 *
 *
 * @date 2019年10月17日
 * @see IDSIntegratedPLMController
 * @since
 */
@RestController
@RequestMapping("/idsIntegratedPLMController")
public class IDSIntegratedPLMController extends BaseController {

    @Autowired
    private IDSIntegratedPLMService idsIntegratedPLMService;

    /**
     * 添加受影响对象上下文下拉
     *
     * @param request
     * @param response
     * @throws FdException
     * @throws IOException
     */
    @RequestMapping(params = "getPrimaryObjectContext")
    public void getPrimaryObjectContext(HttpServletRequest request, HttpServletResponse response) throws FdException, IOException {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        jsonList = idsIntegratedPLMService.getPrimaryObjectContext(jsonList, UserUtil.getCurrentUser().getId());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String jsonResult = JSON.toJSONString(jsonList);
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
        out.close();
    }

    /**
     * 所有对象状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getStatusListForLib")
    public void getStatusListForLib(
        HttpServletRequest request, HttpServletResponse response) {
        //获取所有生命周期状态
        FeignJson fj = idsIntegratedPLMService.getAllLifeCycleStatusListJson();
        String jonStr = (String)fj.getObj();
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 选择对象(文档，图档，部件)分页查询
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "selectForChangeExceptBaselineList")
    public void selectForChangeExceptBaselineList(
        HttpServletRequest request, HttpServletResponse response) {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        List<CheckOutInfoVO> list = idsIntegratedPLMService.findObjectsForChangeExceptBaselineByCondition(
            conditionList, UserUtil.getCurrentUser().getId());
        int size = idsIntegratedPLMService.getObjectForChangeExceptBaselineSizeByCondition(
            conditionList, UserUtil.getCurrentUser().getId());
        String json = JsonUtil.getListJsonWithoutQuote(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + size + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     ***对外接口** --------------待修改
     * 获取类型combotree 节点列表  树形式展示
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTypeCombotreeList")
    public void getTypeCombotreeList(HttpServletRequest request, HttpServletResponse response){
        String typeId = request.getParameter("typeId");
        List<ComboTreeNode> treeList = new ArrayList<ComboTreeNode>();
        if(!CommonUtil.isEmpty(typeId)){
            //获取所有子类型，包括自己
            List<PlmTypeDefinitionDto> typelist = new ArrayList<PlmTypeDefinitionDto>();
            List<PlmTypeDefinitionDto> list =idsIntegratedPLMService.getChildTypeList(typelist, typeId);
            if(!CommonUtil.isEmpty(list)){
                for (PlmTypeDefinitionDto plmTypeDefinition : list) {
                    ComboTreeNode node = new ComboTreeNode();
                    node.setId(plmTypeDefinition.getId());
                    node.setName(plmTypeDefinition.getTypeName());
                    node.setPid(plmTypeDefinition.getParentId());
                    node.setIconSkin(plmTypeDefinition.getIconName());
                    Map<String, String> dataObj = new HashMap<String, String>();
                    dataObj.put("generatorInfoId", plmTypeDefinition.getGenerateRuleId());
                    dataObj.put("instantiation", plmTypeDefinition.getInstantiation());
                    node.setDataObject(dataObj);
                    treeList.add(node);
                }
            }
        }
        TagUtil.ajaxResponse(response, JSON.toJSONString(treeList));
    }

}