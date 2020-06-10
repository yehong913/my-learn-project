package com.glaway.ids.config.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.config.vo.EpsConfig;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.util.CommonInitUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.StringUtil;


/**
 * @Title: Controller
 * @Description: 项目分类
 * @author onlineGenerator
 * @date 2015-03-27 17:06:18
 * @version V1.0
 */
@Controller
@RequestMapping("/epsConfigController")
public class EpsConfigController extends BaseController {
    /**
     * Logger for this class
     */
    // private static final Logger logger = Logger.getLogger(EpsConfigController.class);
    private static final OperationLog log = BaseLogFactory.getOperationLog(EpsConfigController.class);

    /** service */
  /*  @Autowired
    private EpsConfigServiceI epsConfigService;*/

    /** message */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;


    /**
     * 项目分类列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "epsConfig")
    public ModelAndView epsConfig(HttpServletRequest request) {
        Object operationCodes = OpeartionUtils.getOperationCodes(request);
        boolean updateEpsConfigCode = false;
        boolean deleteEpsConfigCode = false;
        boolean stopEpsConfigCode = false;
        boolean startEpsConfigCode = false;
        if (String.valueOf(operationCodes).contains("epsUpdate")) {
            updateEpsConfigCode = true;
        }
        if (String.valueOf(operationCodes).contains("epsBatchDel")) {
            deleteEpsConfigCode = true;
        }
        if (String.valueOf(operationCodes).contains("epsBatchStop")) {
            stopEpsConfigCode = true;
        }
        if (String.valueOf(operationCodes).contains("epsBatchStart")) {
            startEpsConfigCode = true;
        }
        request.setAttribute("updateEpsConfigCode", updateEpsConfigCode);
        request.setAttribute("deleteEpsConfigCode", deleteEpsConfigCode);
        request.setAttribute("stopEpsConfigCode", stopEpsConfigCode);
        request.setAttribute("startEpsConfigCode", startEpsConfigCode);
        return new ModelAndView("com/glaway/ids/pm/config/epsConfig/epsConfigList");
    }



    /**
     * 项目分类新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EpsConfig epsConfig, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(epsConfig.getId())) {
            String epsConfigStr = epsConfigService.getEpsConfig(epsConfig.getId());

            epsConfig = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsConfigStr),new TypeReference<EpsConfig>(){});

            req.setAttribute("epsConfig", epsConfig);
        }
        return new ModelAndView("com/glaway/ids/pm/config/epsConfig/epsConfig-add");
    }


    /**
     * 添加项目分类
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EpsConfig epsConfig, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.eps.addSuccess");
        try {
            if (epsConfig.getPath() != null && epsConfig.getPath().equals("2")) {
                String fatherStr = epsConfigService.getEpsConfig(epsConfig.getParentId());

                EpsConfig father  = JSON.parseObject(JsonFromatUtil.formatJsonToList(fatherStr),new TypeReference<EpsConfig>(){});

                if (father.getParentId() != null && father.getParentId().equals("ROOT")) {
                    epsConfig.setParentId(null);
                }
                else {
                    epsConfig.setParentId(father.getParentId());
                }
            }
            epsConfigService.addEpsConfig(epsConfig);
            j.setSuccess(true);
          /*  log.info(message, epsConfig.getId(), epsConfig.getId().toString());*/
        }
        catch (Exception e) {
            j.setSuccess(false);
            if(e.getMessage().contains("NEXTVAL exceeds MAXVALUE")){
                message = I18nUtil.getValue("com.glaway.ids.pm.config.configNoExceedsMaxvalue");
            }else{
                message = e.getMessage();
            }
            log.error(message, e, "", epsConfig.getId().toString());
            Object[] params = new Object[] {message, epsConfig.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    @RequestMapping(params = "epsStatusList")
    @ResponseBody
    public void epsStatusList(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "启用");
        obj.put("name", "启用");
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "禁用");
        obj2.put("name", "禁用");
        jsonList.add(obj2);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 搜索
     * 
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchNodes")
    public void searchNodes(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException {
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String stopFlag = request.getParameter("stopFlag");
        String updateEpsConfigCode = request.getParameter("updateEpsConfigCode");
        String deleteEpsConfigCode = request.getParameter("deleteEpsConfigCode");
        String stopEpsConfigCode = request.getParameter("stopEpsConfigCode");
        String startEpsConfigCode = request.getParameter("startEpsConfigCode");
        Map<String, String> operationCodeMap = new HashMap<String, String>();
        operationCodeMap.put("updateEpsConfigCode", updateEpsConfigCode);
        operationCodeMap.put("deleteEpsConfigCode", deleteEpsConfigCode);
        operationCodeMap.put("stopEpsConfigCode", stopEpsConfigCode);
        operationCodeMap.put("startEpsConfigCode", startEpsConfigCode);
        if (!StringUtil.isEmpty(name)) {
            name = URLDecoder.decode(name, "UTF-8");
        }
        if (!StringUtil.isEmpty(no)) {
            no = URLDecoder.decode(no, "UTF-8");
        }
        if (!StringUtil.isEmpty(stopFlag)) {
            stopFlag = URLDecoder.decode(stopFlag, "UTF-8");
        }

        EpsConfig epsConfig = new EpsConfig();
        epsConfig.setNo(no);
        epsConfig.setName(name);
        if (ConfigStateConstants.START.equals(stopFlag)) {
            stopFlag = "1";
        }
        else if (ConfigStateConstants.STOP.equals(stopFlag)) {
            stopFlag = "0";
        }
        epsConfig.setStopFlag(stopFlag);

        // 获取检索命中的List
        String hitListStr = epsConfigService.searchTreeNode(epsConfig);
        List<EpsConfig> hitList = JSON.parseObject(JsonFromatUtil.formatJsonToList(hitListStr),new TypeReference<List<EpsConfig>>(){});

        // 获取所有根节点List
        String allListStr = epsConfigService.searchTreeNode(new EpsConfig());
        List<EpsConfig> allList = JSON.parseObject(JsonFromatUtil.formatJsonToList(allListStr),new TypeReference<List<EpsConfig>>(){});
        
        Map<String, List<EpsConfig>> parentListMap = new HashMap<String, List<EpsConfig>>();
        Map<String,Object> mapList = new HashMap<String,Object>();
        for (EpsConfig hitEps : hitList) {
            List<EpsConfig> parentList = new ArrayList<EpsConfig>();
            mapList.put("hitEps",hitEps);
            mapList.put("allEpsList",allList);
            mapList.put("allEpsParentList",parentList);
            epsConfigService.getEpsParentList(mapList);
            hitEps.setResult("true");
            parentList.add(hitEps);
            parentListMap.put(hitEps.getId(), parentList);
        }
        Map<String, EpsConfig> resultMap = new HashMap<String, EpsConfig>();
        List<EpsConfig> copyList = new ArrayList<EpsConfig>();
        for (String hitId : parentListMap.keySet()) {
            for (EpsConfig node : parentListMap.get(hitId)) {
                copyList.add(node);
            }
        }
        //用于排序的Map键是对应的位置的值  Value是对应的相关实力的值
        Map<Integer, EpsConfig> resultMapOrder = new HashMap<Integer, EpsConfig>();
        //获取原始数据没有排序字段的值的数据重新赋相关顺序的值
        List<EpsConfig> orderIsNullList = new ArrayList<EpsConfig>();
        //存放原始数据重新复制排序字段后的集合  KEY对应ID VALUE对应实体类
        Map<String, EpsConfig> orderIsNullMap = new HashMap<String, EpsConfig>();
        for (EpsConfig copy : copyList) {
            if (resultMap.get(copy.getId()) == null)
            {
                resultMap.put(copy.getId(), copy);
                
                //如果排序字段的值为空存放到List 中重新赋相关的排序字段
                if(StringUtil.isEmpty(copy.getRankQuality()))
                {
                	orderIsNullList.add(copy);
                }else
                {
                	resultMapOrder.put(Integer.valueOf(copy.getRankQuality()), copy);
                }
            }
        }
        //获取最大的位置的属性的值
        int maxPlace=epsConfigService.getMaxEpsConfigPlace();
        //处理排序字段为空的值 重新赋排序字段
        if(orderIsNullList!=null&&orderIsNullList.size()>0)
        {
        	 for(int i=0;i<orderIsNullList.size();i++)
        	 {
        		 orderIsNullList.get(i).setRankQuality(""+(maxPlace+i+1));
        		 epsConfigService.saveOrUpdateEpsConfig(orderIsNullList.get(i));
        		 
        		 orderIsNullMap.put(orderIsNullList.get(i).getId(), orderIsNullList.get(i));
        	 }
        	 
        	 //重新获取集合在重新排序 
        	  for (EpsConfig copy : copyList) {
                  if (resultMap.get(copy.getId()) == null)
                  {
                      resultMap.put(copy.getId(), copy);
                      resultMapOrder.put(Integer.valueOf(copy.getRankQuality()), copy);
                      //如果排序字段的值为空存放到List 中重新赋相关的排序字段  重新再获取排序的最大值重新排序
                      if(StringUtil.isEmpty(copy.getRankQuality()))
                      {
                      	if(orderIsNullMap.get(copy.getId())!=null)
                      	{
                      		resultMapOrder.put(Integer.valueOf(orderIsNullMap.get(copy.getId()).getRankQuality()), orderIsNullMap.get(copy.getId()));
                      	}
                      }
                  }
              }
        	  //同时在重新获取一次最大的排序字段
        	  maxPlace=epsConfigService.getMaxEpsConfigPlace();
        }
        
        List<EpsConfig> resultList = new ArrayList<EpsConfig>();
       /* for (String id : resultMap.keySet()) {
            for (EpsConfig eps : hitList) {
                if (eps.getName().equals(resultMap.get(id).getName())) {
                    resultMap.get(id).setResult("true");
                }
            }
            resultList.add(resultMap.get(id));
        }*/
        //重新塞值
        for(int i=1;i<maxPlace+1;i++)
        {
        	if(null!=resultMapOrder.get(Integer.valueOf(i)))
        	{
        		EpsConfig epsConfigNew=resultMapOrder.get(Integer.valueOf(i));
        		
        		for (EpsConfig eps : hitList) 
        		{
        			if (eps.getName().equals(epsConfigNew.getName())) 
        			{
        				epsConfigNew.setResult("true");
        			}
        		}
        		resultList.add(epsConfigNew);
        	}
        }
        
        
        
        // List<EpsConfig> list = epsConfigService.searchTreeNode(epsConfig);
        List<JSONObject> tempList = changeCategorysToJSONObjects(resultList, operationCodeMap);
        List<JSONObject> rootList = sortCategoryList(tempList);
        if (rootList == null) {
            rootList = new ArrayList<JSONObject>();
        }
        String resultJSON = JSON.toJSONString(rootList);
        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @RequestMapping(params = "doSearch")
    @ResponseBody
    public AjaxJson doSearch(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException {
        AjaxJson j = new AjaxJson();
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String stopFlag = request.getParameter("stopFlag");
        String updateEpsConfigCode = request.getParameter("updateEpsConfigCode");
        String deleteEpsConfigCode = request.getParameter("deleteEpsConfigCode");
        String stopEpsConfigCode = request.getParameter("stopEpsConfigCode");
        String startEpsConfigCode = request.getParameter("startEpsConfigCode");
        Map<String, String> operationCodeMap = new HashMap<String, String>();
        operationCodeMap.put("updateEpsConfigCode", updateEpsConfigCode);
        operationCodeMap.put("deleteEpsConfigCode", deleteEpsConfigCode);
        operationCodeMap.put("stopEpsConfigCode", stopEpsConfigCode);
        operationCodeMap.put("startEpsConfigCode", startEpsConfigCode);
        if (!StringUtil.isEmpty(name)) {
            name = URLDecoder.decode(name, "UTF-8");
        }
        if (!StringUtil.isEmpty(no)) {
            no = URLDecoder.decode(no, "UTF-8");
        }
        if (!StringUtil.isEmpty(stopFlag)) {
            stopFlag = URLDecoder.decode(stopFlag, "UTF-8");
        }

        EpsConfig epsConfig = new EpsConfig();
        epsConfig.setNo(no);
        epsConfig.setName(name);
        if (ConfigStateConstants.START.equals(stopFlag)) {
            stopFlag = "1";
        }
        else if (ConfigStateConstants.STOP.equals(stopFlag)) {
            stopFlag = "0";
        }
        epsConfig.setStopFlag(stopFlag);

        // 获取检索命中的List
        String hitListStr = epsConfigService.searchTreeNode(epsConfig);
        List<EpsConfig> hitList = JSON.parseObject(JsonFromatUtil.formatJsonToList(hitListStr),new TypeReference<List<EpsConfig>>(){});

        // 获取所有根节点List
        String allListStr = epsConfigService.searchTreeNode(new EpsConfig());
        List<EpsConfig> allList = JSON.parseObject(JsonFromatUtil.formatJsonToList(allListStr),new TypeReference<List<EpsConfig>>(){});
        Map<String, List<EpsConfig>> parentListMap = new HashMap<String, List<EpsConfig>>();
        Map<String,Object> mapList = new HashMap<String,Object>();
        for (EpsConfig hitEps : hitList) {
            List<EpsConfig> parentList = new ArrayList<EpsConfig>();
            mapList.put("hitEps",hitEps);
            mapList.put("allEpsList",allList);
            mapList.put("allEpsParentList",parentList);
            epsConfigService.getEpsParentList(mapList);
            hitEps.setResult("true");
            parentList.add(hitEps);
            parentListMap.put(hitEps.getId(), parentList);
        }
        Map<String, EpsConfig> resultMap = new HashMap<String, EpsConfig>();
        List<EpsConfig> copyList = new ArrayList<EpsConfig>();
        for (String hitId : parentListMap.keySet()) {
            for (EpsConfig node : parentListMap.get(hitId)) {
                copyList.add(node);
            }
        }
      //用于排序的Map键是对应的位置的值  Value是对应的相关实力的值
        Map<Integer, EpsConfig> resultMapOrder = new HashMap<Integer, EpsConfig>();
        //获取原始数据没有排序字段的值的数据重新赋相关顺序的值
        List<EpsConfig> orderIsNullList = new ArrayList<EpsConfig>();
        //存放原始数据重新复制排序字段后的集合  KEY对应ID VALUE对应实体类
        Map<String, EpsConfig> orderIsNullMap = new HashMap<String, EpsConfig>();
        for (EpsConfig copy : copyList) {
            if (resultMap.get(copy.getId()) == null)
            {
                resultMap.put(copy.getId(), copy);
                
                //如果排序字段的值为空存放到List 中重新赋相关的排序字段
                if(StringUtil.isEmpty(copy.getRankQuality()))
                {
                	orderIsNullList.add(copy);
                }else
                {
                	resultMapOrder.put(Integer.valueOf(copy.getRankQuality()), copy);
                }
            }
        }
        //获取最大的位置的属性的值
        int maxPlace=epsConfigService.getMaxEpsConfigPlace();
        //处理排序字段为空的值 重新赋排序字段
        if(orderIsNullList!=null&&orderIsNullList.size()>0)
        {
        	 for(int i=0;i<orderIsNullList.size();i++)
        	 {
        		 orderIsNullList.get(i).setRankQuality(""+(maxPlace+i+1));
        		 epsConfigService.saveOrUpdateEpsConfig(orderIsNullList.get(i));
        		 
        		 orderIsNullMap.put(orderIsNullList.get(i).getId(), orderIsNullList.get(i));
        	 }
        	 
        	 //重新获取集合在重新排序 
        	  for (EpsConfig copy : copyList) {
                  if (resultMap.get(copy.getId()) == null)
                  {
                      resultMap.put(copy.getId(), copy);
                      resultMapOrder.put(Integer.valueOf(copy.getRankQuality()), copy);
                      //如果排序字段的值为空存放到List 中重新赋相关的排序字段  重新再获取排序的最大值重新排序
                      if(StringUtil.isEmpty(copy.getRankQuality()))
                      {
                      	if(orderIsNullMap.get(copy.getId())!=null)
                      	{
                      		resultMapOrder.put(Integer.valueOf(orderIsNullMap.get(copy.getId()).getRankQuality()), orderIsNullMap.get(copy.getId()));
                      	}
                      }
                  }
              }
        	  //同时在重新获取一次最大的排序字段
        	  maxPlace=epsConfigService.getMaxEpsConfigPlace();
        }
        
        List<EpsConfig> resultList = new ArrayList<EpsConfig>();
       /* for (String id : resultMap.keySet()) {
            for (EpsConfig eps : hitList) {
                if (eps.getName().equals(resultMap.get(id).getName())) {
                    resultMap.get(id).setResult("true");
                }
            }
            resultList.add(resultMap.get(id));
        }*/
        //重新塞值
        for(int i=1;i<maxPlace+1;i++)
        {
        	if(null!=resultMapOrder.get(Integer.valueOf(i)))
        	{
        		EpsConfig epsConfigNew=resultMapOrder.get(Integer.valueOf(i));
        		
        		for (EpsConfig eps : hitList) 
        		{
        			if (eps.getName().equals(epsConfigNew.getName())) 
        			{
        				epsConfigNew.setResult("true");
        			}
        		}
        		resultList.add(epsConfigNew);
        	}
        }

        
        // List<EpsConfig> list = epsConfigService.searchTreeNode(epsConfig);
        List<JSONObject> tempList = changeCategorysToJSONObjects(resultList, operationCodeMap);
        List<JSONObject> rootList = sortCategoryList(tempList);
        if (rootList == null) {
            rootList = new ArrayList<JSONObject>();
        }
        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }
    
    /**
     * 将知识分类组装为树节点json
     * 
     * @return
     * @see
     */
    private List<JSONObject> changeCategorysToJSONObjects(List<EpsConfig> list,
                                                          Map<String, String> operationCodeMap) {

        List<JSONObject> rootList = new ArrayList<JSONObject>();

        List<String> parentCategoryIds = new ArrayList<String>();
        Map<String, String> categoryIdMaps = new HashMap<String, String>();

        if (!CommonUtil.isEmpty(list)) {
            for (EpsConfig p : list) {
                categoryIdMaps.put(p.getId(), p.getId());
                String parentCategoryId = p.getParentId();
                if (!StringUtils.isEmpty(parentCategoryId)
                    && !parentCategoryIds.contains(parentCategoryId)) {
                    parentCategoryIds.add(parentCategoryId);
                }
            }
        }
       /* List<EpsConfig> listNew = new ArrayList<EpsConfig>();
        //需要排序的个数
        int count=list.size();
        Map<Integer,EpsConfig> mapList=new HashMap<Integer,EpsConfig>();
        
        int c=0;
        for (EpsConfig p : list) 
        {
        	mapList.put(Integer.valueOf(c++),p);
        }
        //根据排序字段给 项目分类进行排序 冒泡排序重新排序
        for(int i=0;i<count;i++)
        {
        	int rankQuality=Integer.valueOf(mapList.get(Integer.valueOf(i)).getRankQuality()).intValue();
        	
        	for(int y=i+1;y<count;y++)
        	{
        		int rankQualityNew=Integer.valueOf(mapList.get(Integer.valueOf(y)).getRankQuality()).intValue();
        		
        		if(rankQuality>rankQualityNew)
        		{
        			EpsConfig epsConfigOld= new EpsConfig();
        			EpsConfig epsConfigNew= new EpsConfig();
        			
        			epsConfigOld=mapList.get(Integer.valueOf(i));
        			epsConfigNew=mapList.get(Integer.valueOf(y));
        			
        			mapList.put(Integer.valueOf(i), epsConfigNew);
        			mapList.put(Integer.valueOf(y), epsConfigOld);
        		}
        	}
        }
        
        for(int i=0;i<count;i++)
        {
        	listNew.add(mapList.get(Integer.valueOf(i)));
        }*/

        for (EpsConfig p : list) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentId())
                || StringUtils.isEmpty(categoryIdMaps.get(p.getParentId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                if (parentCategoryIds.contains(p.getId())) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getName());
                    treeNode.put("image", "folder.gif");
                    root.put("name", treeNode);
                }
                else {
                    root.put("name", p.getName());
                }

                if (parentCategoryIds.contains(p.getId())) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", generateEpsName(p));
                    treeNode.put("image", "folder.gif");
                    root.put("name", treeNode);
                }
                else {
                    root.put("name", generateEpsName(p));
                }

                root.put("parentId", p.getParentId());
                root.put("no", p.getNo());
                if ("1".equals(p.getStopFlag())) {
                    root.put("stopFlag", ConfigStateConstants.START);
                }
                else {
                    root.put("stopFlag", ConfigStateConstants.STOP);
                }
                root.put("configComment", p.getConfigComment());
                // root.put("namePath", p.getNamePath());
                root.put("optBtn", this.generateOptBtn(p, operationCodeMap));
                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                root.put("result", "true");
                rootList.add(root);
            }
            
            
        }

        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPid(parentCategoryIds, list, rootList.get(i), operationCodeMap);
        }
        return rootList;
    }
    
    
    /**
     * Description:递归查询获取所有子节点
     * 
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(List<String> parentCategoryIds, List<EpsConfig> list,
                                 JSONObject parentObject, Map<String, String> operationCodeMap) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        for (EpsConfig node : list) {
            if (pid.equals(node.getParentId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", node.getId());
                if (parentCategoryIds.contains(node.getId())) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", node.getName());
                    treeNode.put("image", "folder.gif");
                    newNode.put("name", treeNode);
                }
                else {
                    newNode.put("name", node.getName());
                }

                if (parentCategoryIds.contains(node.getId())) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", generateEpsName(node));
                    treeNode.put("image", "folder.gif");
                    newNode.put("name", treeNode);
                }
                else {
                    newNode.put("name", generateEpsName(node));
                }

                newNode.put("parentId", node.getParentId());
                newNode.put("no", node.getNo());
                if ("1".equals(node.getStopFlag())) {
                    newNode.put("stopFlag", ConfigStateConstants.START);
                }
                else {
                    newNode.put("stopFlag", ConfigStateConstants.STOP);
                }
                newNode.put("configComment", node.getConfigComment());
                newNode.put("optBtn", this.generateOptBtn(node, operationCodeMap));
                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                newNode.put("result", "true");
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPid(parentCategoryIds, list, subNodeList.get(i),
                    operationCodeMap);
                JSONObject currentNode = subNodeList.get(i);
                rows.add(currentNode);
                parentObject.put("rows", rows);
            }
        }
        else {
            return;
        }
    }
    
    /**
     * 构造节点名称
     * 
     * @return
     * @see
     */
    private String generateEpsName(EpsConfig eps) {
        if ("true".equals(eps.getResult())) {
            return eps.getName();
        }
        else {
            return "<span style='color:gray'>" + eps.getName() + "</span>";
        }
    }
    
    private boolean hideStopBtn(EpsConfig epsConfig, String stopEpsConfigCode) {
        if ("true".equals(stopEpsConfigCode)) {
            if (epsConfig.getStopFlag().equals("0")) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean hideStartBtn(EpsConfig epsConfig, String startEpsConfigCode) {
        EpsConfig condition = new EpsConfig();
        condition.setId(epsConfig.getParentId());
        String parentStr = epsConfigService.getParentNode(condition);
        EpsConfig parent = new EpsConfig();
        List<EpsConfig> parentList = JSON.parseObject(JsonFromatUtil.formatJsonToList(parentStr),new TypeReference<List<EpsConfig>>(){});
        if(!CommonUtil.isEmpty(parentList)){
            parent = parentList.get(0);
        }


        if ("true".equals(startEpsConfigCode)) {
            if (!CommonUtil.isEmpty(parent.getStopFlag())) {
                if (parent.getStopFlag().equals("0")) {
                    return false;
                }
                else {
                    if (epsConfig.getStopFlag().equals("1")) {
                        return false;
                    }
                    return true;
                }
            }
            else {
                if (epsConfig.getStopFlag().equals("1")) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * 构造分类树操作栏操作按钮
     * 
     * @return
     * @see
     */
    private String generateOptBtn(EpsConfig node, Map<String, String> operationCodeMap) {
        String returnStr = "";
        String modifyBtnStr = "<a class='basis ui-icon-pencil' style='display:inline-block;cursor:pointer;' onClick='updateNodeLine(\""
                              + node.getId() + "\")' title='修改'></a>";
        String deleteBtnStr = "<a class='basis ui-icon-minus' style='display:inline-block;cursor:pointer;' onClick='deleteNodeLine(\""
                              + node.getId() + "\")' title='删除'></a>";
        String stopBtnStr = "<a class='basis ui-icon-forbidden' style='display:inline-block;cursor:pointer;' onClick='startOrStopNodeEpsLine(\""
                            + node.getId() + "\"" + ',' + "\"禁用\")' title='禁用'></a>";
        String startBtnStr = "<a class='basis ui-icon-enable' style='display:inline-block;cursor:pointer;' onClick='startOrStopNodeEpsLine(\""
                             + node.getId() + "\"" + ',' + "\"启用\")' title='启用'></a>";

        if (hideUpdBtn(node, operationCodeMap.get("updateEpsConfigCode"))) {
            returnStr = returnStr + modifyBtnStr;
        }
        if (operationCodeMap.get("deleteEpsConfigCode").equals("true")) {
            returnStr = returnStr + deleteBtnStr;
        }
        if (hideStopBtn(node, operationCodeMap.get("stopEpsConfigCode"))) {
            returnStr = returnStr + stopBtnStr;
        }
        if (hideStartBtn(node, operationCodeMap.get("startEpsConfigCode"))) {
            returnStr = returnStr + startBtnStr;
        }
        return returnStr;
    }
    
    private boolean hideUpdBtn(EpsConfig epsConfig, String updateEpsConfigCode) {
        if ("true".equals(updateEpsConfigCode)) {
            if (epsConfig.getStopFlag().equals("0")) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    
    /**
     * 将list递归排序
     * 
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<JSONObject> sortCategoryList(List<JSONObject> list) {
        JSONObject root = new JSONObject();
        if (list != null && list.size() > 0) {
            root.put("rows", list);
            root.put("name", "ROOT");
            sortTreeCategory(root);
        }

        return (List<JSONObject>)root.get("rows");
    }
    
    /**
     * 排序
     * 
     * @param root
     */
    @SuppressWarnings("unchecked")
    private void sortTreeCategory(JSONObject root) {
        if (root != null) {
            List<JSONObject> childs = (List<JSONObject>)root.get("rows");
            if (!CommonUtil.isEmpty(childs)) {
                Collections.sort(childs, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        return ((o1.get("sortOrder") != null) ? (String)o1.get("sortOrder") : "").compareTo((o2.get("sortOrder") != null) ? (String)o2.get("sortOrder") : "");
                    }
                });
                for (JSONObject c : childs) {
                    sortTreeCategory(c);
                }
            }
        }
    }


    /**
     * 右击项目树 新增项目分类信息
     * @param epsConfig
     * @param req
     * @return
     */
    @RequestMapping(params = "goRightAdd")
    public ModelAndView goRightAdd(EpsConfig epsConfig, HttpServletRequest req)
    {
        String type=req.getParameter("type");
        //如果是从下方新增的判断
        if(StringUtil.isNotEmpty(epsConfig.getParentId())&&type!=null&&type.equals("after"))
        {
            String epsConfigBeforStr =epsConfigService.getEpsConfig(epsConfig.getParentId());

            EpsConfig epsConfigBefor = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsConfigBeforStr),new TypeReference<EpsConfig>(){});

            req.setAttribute("rankQuality",epsConfigBefor.getRankQuality());
        }

        return new ModelAndView("com/glaway/ids/pm/config/epsConfig/epsConfig-addRightClick");
    }




    /**
     * 项目分类编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EpsConfig epsConfig, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(epsConfig.getId())) {
            String epsConfigStr = epsConfigService.getEpsConfig(epsConfig.getId());
            epsConfig = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsConfigStr),new TypeReference<EpsConfig>(){});
            req.setAttribute("epsConfig_", epsConfig);
        }
        return new ModelAndView("com/glaway/ids/pm/config/epsConfig/epsConfig-update");
    }


    /**
     * 更新项目分类
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public FeignJson doUpdate(EpsConfig epsConfig, HttpServletRequest request) {
        CommonInitUtil.initGLVDataForUpdate(epsConfig);
        FeignJson j = epsConfigService.doUpdate(epsConfig);
        return j;
    }


    /**
     * 批量删除项目分类
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.config.eps.deleteSuccess");
        try {
            epsConfigService.doBatchDel(ids);// 代码已重构
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.eps.deleteError");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message, EpsConfig.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 批量删除项目分类判断其下有没有子节点 true 有子节点  false没有子节点
     *
     * @return
     */
    @RequestMapping(params = "doBatchDelIsHaveChildList")
    @ResponseBody
    public FeignJson doBatchDelIsHaveChildList(String ids, HttpServletRequest request) {
        FeignJson j = epsConfigService.doBatchDelIsHaveChildList(ids);
        return j;
    }



    /**
     * 启用或禁用业务配置
     *
     * @param ids
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doStartOrStop")
    @ResponseBody
    public AjaxJson doStartOrStop(String ids, HttpServletRequest request) {
        String state = request.getParameter("state");
        AjaxJson j = new AjaxJson();
        if (state.equals("start")) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.eps.startSuccess");
        }
        else {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.eps.stopSuccess");
        }
        try {
            epsConfigService.doBatchStartOrStop(ids, state);// 代码已重构
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.startOrStopError");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                    EpsConfig.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }



}
