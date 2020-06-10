package com.glaway.ids.config.controller;


import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.log.SystemLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.ExcelVo;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.util.param.JudgeRangeParam;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.extend.hqlsearch.HqlGenerateUtil;
import com.glaway.foundation.core.common.hibernate.qbc.CriteriaQuery;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.CommonConfigConstants;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.BusinessConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.config.vo.SelectConfig;
import com.glaway.ids.util.CommonInitUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * @author xshen
 * @version 2015年3月26日
 * @see PlanBusinessConfigController
 * @since
 */
@Controller
@RequestMapping("/planBusinessConfigController")
public class PlanBusinessConfigController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanBusinessConfigController.class);

    /** 业务对象 */
    @Autowired
    private PlanBusinessConfigServiceI planBusinessConfigService;


    @Autowired
    private BusinessConfigRemoteFeignServiceI businessFeignService;



    /** 日志消息 */
    private String message;

    /**
     * 系统日志
     */
    private final SystemLog sysLog = BaseLogFactory.getSystemLog(PlanBusinessConfigController.class);

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 业务配置列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "businessConfig")
    public ModelAndView businessConfig(HttpServletRequest request) {
        String type = request.getParameter("type");
        request.setAttribute("display", ConfigTypeConstants.getName(type));
        return new ModelAndView("com/glaway/ids/pm/config/businessConfigList");
    }
    
    /**
     * 业务配置列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "planLevelIndex")
    public ModelAndView planLevelIndex(HttpServletRequest request) {
        String type = request.getParameter("type");
        request.setAttribute("display", ConfigTypeConstants.getName(type));
        return new ModelAndView("com/glaway/ids/pm/config/businessConfigList");
    }
    
    
    /**
     * 业务配置列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "changeTypeBusinessConfig")
    public ModelAndView changeTypeBusinessConfig(HttpServletRequest request) {
        String type = request.getParameter("type");
        request.setAttribute("display", ConfigTypeConstants.getName(type));
        Object operationCodes = OpeartionUtils.getOperationCodes(request);
        boolean updateEpsConfigCode = false;
        boolean deleteEpsConfigCode = false;
        boolean stopEpsConfigCode = false;
        boolean startEpsConfigCode = false;
        for (String operationCode : operationCodes.toString().split(",")) {
            if (operationCode.contains("BConfigModPLANCHANGECATEGORY")) {
                updateEpsConfigCode = true;
                continue;
            }
            if (operationCode.contains("BConfigDelPLANCHANGECATEGORY")) {
                deleteEpsConfigCode = true;
                continue;
            }
            if (operationCode.contains("BConfigStoPLANCHANGECATEGORY")) {
                stopEpsConfigCode = true;
                continue;
            }
            if (operationCode.contains("BConfigSatPLANCHANGECATEGORY")) {
                startEpsConfigCode = true;
                continue;
            }

        }
        request.setAttribute("updateEpsConfigCode", updateEpsConfigCode);
        request.setAttribute("deleteEpsConfigCode", deleteEpsConfigCode);
        request.setAttribute("stopEpsConfigCode", stopEpsConfigCode);
        request.setAttribute("startEpsConfigCode", startEpsConfigCode);
        return new ModelAndView("com/glaway/ids/pm/config/businessConfigList-changeType");
    }

    /**
     * Description: <br>
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        ConditionVO vo = new ConditionVO();
        vo.setCondition("eq");
        vo.setKey("BusinessConfig.configType");
        vo.setValue(type);
        conditionList.add(vo);

        PageList pageList = planBusinessConfigService.queryEntity(conditionList, true);
        long count = pageList.getCount();
        String datagridStr = "{\"rows\":" + JSON.toJSONString(pageList.getResultList())
                             + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }
    
    
    
    /**
     * 搜索
     * 
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagridForChangeType")
    public void searchDatagridForChangeType(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException {
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String stopFlag = request.getParameter("stopFlag");
        String updateEpsConfigCode = request.getParameter("updateEpsConfigCode");
        String deleteEpsConfigCode = request.getParameter("deleteEpsConfigCode");
        String startEpsConfigCode = request.getParameter("startEpsConfigCode");
        String stopEpsConfigCode = request.getParameter("stopEpsConfigCode");
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

        BusinessConfig epsConfig = new BusinessConfig();
        epsConfig.setNo(no);
        epsConfig.setName(name);
        if (ConfigStateConstants.START.equals(stopFlag)) {
            stopFlag = "1";
        }
        else if (ConfigStateConstants.STOP.equals(stopFlag)) {
            stopFlag = "0";
        }
        epsConfig.setStopFlag(stopFlag);
        epsConfig.setConfigType("PLANCHANGECATEGORY");
        // 获取检索命中的List
        List<BusinessConfig> hitList = planBusinessConfigService.searchTreeNode(epsConfig);
        // 获取所有根节点List
        BusinessConfig epsConfigNew = new BusinessConfig();
        epsConfigNew.setConfigType("PLANCHANGECATEGORY");
        
        List<BusinessConfig> allList = planBusinessConfigService.searchTreeNode(epsConfigNew);
        Map<String, List<BusinessConfig>> parentListMap = new HashMap<String, List<BusinessConfig>>();
        for (BusinessConfig hitEps : hitList) {
            List<BusinessConfig> parentList = new ArrayList<BusinessConfig>();
            planBusinessConfigService.getBusinessConfigParentList(hitEps, allList, parentList);
            hitEps.setResult("true");
            parentList.add(hitEps);
            parentListMap.put(hitEps.getId(), parentList);
        }
        Map<String, BusinessConfig> resultMap = new HashMap<String, BusinessConfig>();
        List<BusinessConfig> copyList = new ArrayList<BusinessConfig>();
        for (String hitId : parentListMap.keySet()) {
            for (BusinessConfig node : parentListMap.get(hitId)) {
                copyList.add(node);
            }
        }
        //用于排序的Map键是对应的位置的值  Value是对应的相关实力的值
        Map<Integer, BusinessConfig> resultMapOrder = new HashMap<Integer, BusinessConfig>();
        //获取原始数据没有排序字段的值的数据重新赋相关顺序的值
        List<BusinessConfig> orderIsNullList = new ArrayList<BusinessConfig>();
       //存放原始数据重新复制排序字段后的集合  KEY对应ID VALUE对应实体类
        Map<String, BusinessConfig> orderIsNullMap = new HashMap<String, BusinessConfig>();
        for (BusinessConfig copy : copyList) {
            if (resultMap.get(copy.getId()) == null) {
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
        int maxPlace=planBusinessConfigService.getMaxPlace("PLANCHANGECATEGORY");
        //处理排序字段为空的值 重新赋排序字段
        if(orderIsNullList!=null&&orderIsNullList.size()>0)
        {
        	 for(int i=0;i<orderIsNullList.size();i++)
        	 {
        		 orderIsNullList.get(i).setRankQuality(""+(maxPlace+i+1));
        		 planBusinessConfigService.businessConfigSaveOrUpdate(orderIsNullList.get(i));
        		 orderIsNullMap.put(orderIsNullList.get(i).getId(), orderIsNullList.get(i));
        	 }
        	 
        	 //重新获取集合在重新排序 
       	  for (BusinessConfig copy : copyList) {
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
       	  maxPlace=planBusinessConfigService.getMaxPlace("PLANCHANGECATEGORY");
        	 
        }
        
        List<BusinessConfig> resultList = new ArrayList<BusinessConfig>();
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
        		BusinessConfig businessConfigNew=resultMapOrder.get(Integer.valueOf(i));
        		
        		for (BusinessConfig eps : hitList) 
        		{
        			if (eps.getName().equals(businessConfigNew.getName())) 
        			{
        				businessConfigNew.setResult("true");
        			}
        		}
        		resultList.add(businessConfigNew);
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

        BusinessConfig epsConfig = new BusinessConfig();
        epsConfig.setNo(no);
        epsConfig.setName(name);
      /*  if (ConfigStateConstants.START.equals(stopFlag)) {
            stopFlag = "1";
        }
        else if (ConfigStateConstants.STOP.equals(stopFlag)) {
            stopFlag = "0";
        }*/
        epsConfig.setStopFlag(stopFlag);
        epsConfig.setConfigType("PLANCHANGECATEGORY");
        // 获取检索命中的List
        List<BusinessConfig> hitList = planBusinessConfigService.searchTreeNode(epsConfig);
        // 获取所有根节点List
        BusinessConfig epsConfigNew = new BusinessConfig();
        epsConfigNew.setConfigType("PLANCHANGECATEGORY");
        
        List<BusinessConfig> allList = planBusinessConfigService.searchTreeNode(epsConfigNew);
       
        Map<String, List<BusinessConfig>> parentListMap = new HashMap<String, List<BusinessConfig>>();
        for (BusinessConfig hitEps : hitList) {
            List<BusinessConfig> parentList = new ArrayList<BusinessConfig>();
            planBusinessConfigService.getBusinessConfigParentList(hitEps, allList, parentList);
            hitEps.setResult("true");
            parentList.add(hitEps);
            parentListMap.put(hitEps.getId(), parentList);
        }
        Map<String, BusinessConfig> resultMap = new HashMap<String, BusinessConfig>();
        List<BusinessConfig> copyList = new ArrayList<BusinessConfig>();
        for (String hitId : parentListMap.keySet()) {
            for (BusinessConfig node : parentListMap.get(hitId)) {
                copyList.add(node);
            }
        }
     
      //用于排序的Map键是对应的位置的值  Value是对应的相关实力的值
        Map<Integer, BusinessConfig> resultMapOrder = new HashMap<Integer, BusinessConfig>();
        //获取原始数据没有排序字段的值的数据重新赋相关顺序的值
        List<BusinessConfig> orderIsNullList = new ArrayList<BusinessConfig>();
       //存放原始数据重新复制排序字段后的集合  KEY对应ID VALUE对应实体类
        Map<String, BusinessConfig> orderIsNullMap = new HashMap<String, BusinessConfig>();
        for (BusinessConfig copy : copyList) {
            if (resultMap.get(copy.getId()) == null) {
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
        int maxPlace=planBusinessConfigService.getMaxPlace("PLANCHANGECATEGORY");
        //处理排序字段为空的值 重新赋排序字段
        if(orderIsNullList!=null&&orderIsNullList.size()>0)
        {
        	 for(int i=0;i<orderIsNullList.size();i++)
        	 {
        		 orderIsNullList.get(i).setRankQuality(""+(maxPlace+i+1));
        		 planBusinessConfigService.businessConfigSaveOrUpdate(orderIsNullList.get(i));
        		 orderIsNullMap.put(orderIsNullList.get(i).getId(), orderIsNullList.get(i));
        	 }
        	 
        	 //重新获取集合在重新排序 
       	  for (BusinessConfig copy : copyList) {
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
       	  maxPlace=planBusinessConfigService.getMaxPlace("PLANCHANGECATEGORY");
        	 
        }
        
        List<BusinessConfig> resultList = new ArrayList<BusinessConfig>();
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
        		BusinessConfig businessConfigNew=resultMapOrder.get(Integer.valueOf(i));
        		
        		for (BusinessConfig eps : hitList) 
        		{
        			if (eps.getName().equals(businessConfigNew.getName())) 
        			{
        				businessConfigNew.setResult("true");
        			}
        		}
        		resultList.add(businessConfigNew);
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
     * 将知识分类组装为树节点json
     * 
     * @return
     * @see
     */
    private List<JSONObject> changeCategorysToJSONObjects(List<BusinessConfig> list,
                                                          Map<String, String> operationCodeMap) {

        List<JSONObject> rootList = new ArrayList<JSONObject>();

        List<String> parentCategoryIds = new ArrayList<String>();
        Map<String, String> categoryIdMaps = new HashMap<String, String>();

        if (!CommonUtil.isEmpty(list)) {
            for (BusinessConfig p : list) {
                categoryIdMaps.put(p.getId(), p.getId());
                String parentCategoryId = p.getParentId();
                if (!StringUtils.isEmpty(parentCategoryId)
                    && !parentCategoryIds.contains(parentCategoryId)) {
                    parentCategoryIds.add(parentCategoryId);
                }
            }
        }

        for (BusinessConfig p : list) {
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
                if ("启用".equals(p.getStopFlag())) {
                    root.put("stopFlag", ConfigStateConstants.START);
                }
                else {
                    root.put("stopFlag", ConfigStateConstants.STOP);
                }
                root.put("configComment", p.getConfigComment());
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
     * 构造节点名称
     * 
     * @return
     * @see
     */
    private String generateEpsName(BusinessConfig eps) {
        if ("true".equals(eps.getResult())) {
            return eps.getName();
        }
        else {
            return "<span style='color:gray'>" + eps.getName() + "</span>";
        }
    }
    
    
    /**
     * 构造分类树操作栏操作按钮
     * 
     * @return
     * @see
     */
    private String generateOptBtn(BusinessConfig node, Map<String, String> operationCodeMap) {
        String returnStr = "";
        String modifyBtnStr = "<a class='basis ui-icon-pencil' style='display:inline-block;cursor:pointer;' onClick='beforeupdateLine(\""
                              + node.getId() + "\")' title='修改'></a>";
        String deleteBtnStr = "<a class='basis ui-icon-minus' style='display:inline-block;cursor:pointer;' onClick='beforedeleteLine(\""
                              + node.getId() + "\")' title='删除'></a>";
        String startBtnStr = "<a class='basis ui-icon-enable' style='display:inline-block;cursor:pointer;' onClick='beforestartLine(\""
            + node.getId() + "\"" + ',' + "\"启用\")' title='启用'></a>";
        String stopBtnStr = "<a class='basis ui-icon-forbidden' style='display:inline-block;cursor:pointer;' onClick='beforestopLine(\""
                            + node.getId() + "\"" + ',' + "\"禁用\")' title='禁用'></a>";

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
    
    
    private boolean hideUpdBtn(BusinessConfig epsConfig, String updateEpsConfigCode) {
        if ("true".equals(updateEpsConfigCode)) {
            if (epsConfig.getStopFlag().equals("禁用")) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean hideStopBtn(BusinessConfig epsConfig, String stopEpsConfigCode) {
        if ("true".equals(stopEpsConfigCode)) {
            if (epsConfig.getStopFlag().equals("禁用")) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean hideStartBtn(BusinessConfig epsConfig, String startEpsConfigCode) {
        BusinessConfig parent = null;
        if(!CommonUtil.isEmpty(epsConfig.getParentId())){
            BusinessConfig condition = new BusinessConfig();
            condition.setId(epsConfig.getParentId());
            parent = planBusinessConfigService.getParentNode(condition);
        }
        if ("true".equals(startEpsConfigCode)) {
            if (parent != null && parent.getConfigType().equals(epsConfig.getConfigType())) {
                if (parent.getStopFlag().equals("禁用")) {
                    return false;
                }
                else {
                    if (epsConfig.getStopFlag().equals("启用")) {
                        return false;
                    }
                    return true;
                }
            }
            else {
                if (epsConfig.getStopFlag().equals("启用")) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Description:递归查询获取所有子节点
     * 
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(List<String> parentCategoryIds, List<BusinessConfig> list,
                                 JSONObject parentObject, Map<String, String> operationCodeMap) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        for (BusinessConfig node : list) {
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
                if ("启用".equals(node.getStopFlag())) {
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
     * 搜索配置项
     * 
     * @param businessConfig
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchConfigs")
    @ResponseBody
    public void searchConfigs(BusinessConfig businessConfig, HttpServletRequest request,
                              HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String type = request.getParameter("type");
        BusinessConfig condition = new BusinessConfig();
        condition.setConfigType(type);
        condition.setName(businessConfig.getName());
        condition.setNo(businessConfig.getNo());
        if ("START".equals(businessConfig.getStopFlag())) {
            condition.setStopFlag(ConfigStateConstants.START);
        }
        else if ("STOP".equals(businessConfig.getStopFlag())) {
            condition.setStopFlag(ConfigStateConstants.STOP);
        }

        List<BusinessConfig> list = this.planBusinessConfigService.searchBusinessConfigsForPage(
            condition, Integer.parseInt(page), Integer.parseInt(rows));
        long count = this.planBusinessConfigService.getSearchCount(condition);

        String datagridStr = "{\"rows\":" + JSON.toJSONString(list) + ",\"total\":" + count + "}";

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

    /**
     * 搜索配置项
     * 
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchConfigsFromParams")
    @ResponseBody
    public void searchConfigsFromParams(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        String ids = request.getParameter("ids");
        name = URLDecoder.decode(name, "UTF-8");
        no = URLDecoder.decode(no, "UTF-8");

        BusinessConfig condition = new BusinessConfig();
        condition.setConfigType(type);
        condition.setName(name);
        condition.setNo(no);
        condition.setStopFlag(ConfigStateConstants.START);

        List<BusinessConfig> list = new ArrayList<BusinessConfig>();
        long count = 0;
        if (!StringUtil.isEmpty(ids)) {
            list = this.planBusinessConfigService.searchDeliverablesForPage(condition,
                Integer.parseInt(page), Integer.parseInt(rows), ids);
            count = this.planBusinessConfigService.getDeliverablesCount(condition, ids);
        }
        else {
            list = this.planBusinessConfigService.searchBusinessConfigsForPage(condition,
                Integer.parseInt(page), Integer.parseInt(rows));
            count = this.planBusinessConfigService.getSearchCount(condition);
        }

        String datagridStr = "{\"rows\":" + JSON.toJSONString(list) + ",\"total\":" + count + "}";

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

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param businessConfig
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(BusinessConfig businessConfig, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        String type = request.getParameter("type");
        businessConfig.setConfigType(type);
        CriteriaQuery cq = new CriteriaQuery(BusinessConfig.class, dataGrid);
        // 查询条件组装器
        HqlGenerateUtil.installHql(cq, businessConfig,
            request.getParameterMap());
        try {
            message = I18nUtil.getValue("com.glaway.ids.common.config.querySuccess");
            // message = "业务配置查询成功";
            // 自定义追加查询条件
            cq.add();


            planBusinessConfigService.getDataGrid(cq, true);

            TagUtil.datagrid(response, dataGrid);
            log.info(message, null, cq.getDetachedCriteria().toString());
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.common.config.queryError");
            log.error(message, e, null, cq.getDetachedCriteria().toString());
            Object[] params = new Object[] {message, cq.getDetachedCriteria().toString()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
    }

    /**
     * 删除业务配置
     * 
     * @param businessConfig
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(BusinessConfig businessConfig, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        businessConfig = planBusinessConfigService.getBusinessConfig(businessConfig.getId());
        String type = request.getParameter("type");
        message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.deleteSuccess");
        try {
            planBusinessConfigService.logicDelete(businessConfig);
            log.info(message);
        }
        catch (Exception e) {
            message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.deleteError");
            log.error(message, e, businessConfig.getId(), businessConfig.getId().toString());
            Object[] params = new Object[] {message,
                businessConfig.getClass() + " oid:" + businessConfig.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2004, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 批量删除业务配置
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public FeignJson doBatchDel(String ids, HttpServletRequest request) {
        FeignJson feignJson = new FeignJson();
        String type = request.getParameter("type");
        feignJson = businessFeignService.doBatchDel(ids, type);// 代码已重构
        return feignJson;
    }

    /**
     * 获取类型
     * 
     * @param type
     * @return
     */
    private String getType(String type) {
        if ("PHARSE".equals(type)) {
            type = I18nUtil.getValue("com.glaway.ids.common.config.projectStage");
        }
        else if ("PLANLEVEL".equals(type)) {
            type = I18nUtil.getValue("com.glaway.ids.common.config.planLevel");
        }
        else if ("PLANCHANGECATEGORY".equals(type)) {
            type = I18nUtil.getValue("com.glaway.ids.common.config.planChangeType");
        }
        return type;
    }

    /**
     * 添加业务配置
     * 
     * @param businessConfig
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(BusinessConfig businessConfig, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        
        String type = request.getParameter("type");
        message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.addSuccess");
        try {
        	if(!(businessConfig.getConfigType().equals("PLANCHANGECATEGORY")))
        	{
        		businessConfig.setConfigType(type);
        	}else
        	{
        		 message = getType(businessConfig.getConfigType()) + I18nUtil.getValue("com.glaway.ids.common.config.addSuccess");
        	}
            
            planBusinessConfigService.add(businessConfig);
            log.info(message, businessConfig.getId(), businessConfig.getId().toString());
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = e.getMessage();
            if (e.getMessage().contains("NEXTVAL exceeds MAXVALUE")) {
                message = I18nUtil.getValue("com.glaway.ids.common.config.configNoExceedsMaxvalue");
            }
            log.error(message, e, "", businessConfig.getId().toString());
            Object[] params = new Object[] {message, businessConfig.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }



    /**
     *  添加业务配置
     * @param businessConfig
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForPharse")
    @ResponseBody
    public FeignJson doAddForPharse(BusinessConfig businessConfig, HttpServletRequest request) {
        String type = request.getParameter("type");
        CommonInitUtil.initGLVDataForCreate(businessConfig);
        FeignJson result = businessFeignService.doAdd(businessConfig,type);
       /* j = MapTransformUtils.mapToAjaxJson(result.get("result"));*/
        return result;
    }

    /**
     * 更新业务配置
     * 
     * @param businessConfig
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(BusinessConfig businessConfig, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("type");
        message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.updateSuccess");
        BusinessConfig t = planBusinessConfigService.getBusinessConfig(businessConfig.getId());
        try {
            BeanUtil.copyBeanNotNull2Bean(businessConfig, t);
            planBusinessConfigService.modify(t);
            log.info(message, businessConfig.getId(), businessConfig.getId().toString());
        }
        catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = e.getMessage();
            log.error(message, e, businessConfig.getId(), businessConfig.getId().toString());
            Object[] params = new Object[] {message, businessConfig.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }



    /**
     * 更新业务配置
     *
     * @param businessConfig
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateForPharse")
    @ResponseBody
    public AjaxJson doUpdateForPharse(BusinessConfig businessConfig, HttpServletRequest request)
    {
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("type");
        CommonInitUtil.initGLVDataForUpdate(businessConfig);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("entity",businessConfig);
        map.put("type",type);
        Map<String,Object> result = businessFeignService.doUpdate(map);
        j = JSON.parseObject(JSON.toJSONString(result.get("result")), AjaxJson.class);
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
        String type = request.getParameter("type");
        if (state.equals("start")) {
            message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.startSuccess");
        }
        else {
            message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.stopSuccess");
        }
        try {
            businessFeignService.doBatchStartOrStop(ids, state);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = getType(type) + I18nUtil.getValue("com.glaway.ids.common.config.startOrStopError");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                BusinessConfig.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 业务配置新增页面跳转
     * 
     * @param businessConfig
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(BusinessConfig businessConfig, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(businessConfig.getId())) {
            businessConfig = planBusinessConfigService.getBusinessConfig(businessConfig.getId());
            req.setAttribute("businessConfig", businessConfig);
        }
        return new ModelAndView("com/glaway/ids/pm/config/businessConfig-add");
    }
    /**
     * 业务配置新增计划变更类别
     * @param businessConfig
     * @param req
     * @return
     */
    @RequestMapping(params="goAddPlanChangeType")
    public ModelAndView goAddPlanChangeType(BusinessConfig businessConfig, HttpServletRequest req)
    {
    	String addType=req.getParameter("addType");
    	//在下方插入的时候  传相关排序字段属性值到增加的页面
    	if(null!=addType&&"after".equals(addType)&&StringUtil.isNotEmpty(businessConfig.getParentId()))
    	{
    		BusinessConfig businessConfigNew=planBusinessConfigService.getBusinessConfig(businessConfig.getParentId());
    		
    		 req.setAttribute("rankQualityChange",businessConfigNew.getRankQuality());
    	}
    	
    	if (StringUtil.isNotEmpty(businessConfig.getId())) 
    	{
            businessConfig = planBusinessConfigService.getBusinessConfig(businessConfig.getId());
            req.setAttribute("businessConfig", businessConfig);
        }
    	
    	return new ModelAndView("com/glaway/ids/config/businessConfig-addPlanChange");
    }
    /**
     * 业务配置编辑页面跳转
     * 
     * @param businessConfig
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(BusinessConfig businessConfig, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(businessConfig.getId())) {
            businessConfig = planBusinessConfigService.getBusinessConfig(businessConfig.getId());
            req.setAttribute("businessConfig_", businessConfig);
        }
        return new ModelAndView("com/glaway/ids/pm/config/businessConfig-update");
    }

    /**
     * 配置下拉列表
     * 
     * @return
     * @see
     */
    @RequestMapping(params = "combox")
    public void combox(HttpServletRequest request, HttpServletResponse response) {
        BusinessConfig businessConfig = new BusinessConfig();
        String type = request.getParameter("type");
        String sid = request.getParameter("sid");// 默认选中的id
        businessConfig.setConfigType(type);
        businessConfig.setStopFlag(ConfigStateConstants.START);

        List<BusinessConfig> list = planBusinessConfigService.searchBusinessConfigs(businessConfig);
        List<SelectConfig> listw = new ArrayList<SelectConfig>();
        for (BusinessConfig bc : list) {
            SelectConfig sc = SelectConfig.transfer(bc);
            if (sid != null && !sid.equals("")) {
                if (sc.getId().equals(sid)) {
                    sc.setSelected(true);
                }
            }
            listw.add(sc);
        }

        String json = JSON.toJSONString(listw);

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
    @RequestMapping(params = "stateCombox")
    public void stateCombox(HttpServletRequest request, HttpServletResponse response) {
        List<BusinessConfig> list = new ArrayList<BusinessConfig>();
        BusinessConfig start = new BusinessConfig();
        start.setId("START");
        start.setName(ConfigStateConstants.START);

        BusinessConfig stop = new BusinessConfig();
        stop.setId("STOP");
        stop.setName(ConfigStateConstants.STOP);

        BusinessConfig all = new BusinessConfig();
        all.setId("*");
        all.setName(I18nUtil.getValue("com.glaway.ids.common.lable.selectall"));

        list.add(start);
        list.add(stop);
        list.add(all);
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
     * 配置关键资源下拉列表
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "keyResourceCombox")
    public void keyResourceCombox(HttpServletRequest request, HttpServletResponse response) {
        List<BusinessConfig> list = new ArrayList<BusinessConfig>();
        BusinessConfig start = new BusinessConfig();
        start.setId(I18nUtil.getValue("com.glaway.ids.common.config.true"));
        start.setName(I18nUtil.getValue("com.glaway.ids.common.config.true"));

        BusinessConfig stop = new BusinessConfig();
        stop.setId(I18nUtil.getValue("com.glaway.ids.common.config.false"));
        stop.setName(I18nUtil.getValue("com.glaway.ids.common.config.false"));

        BusinessConfig all = new BusinessConfig();
        all.setId("*");
        all.setName(I18nUtil.getValue("com.glaway.ids.common.lable.selectall"));

        list.add(start);
        list.add(stop);
        list.add(all);
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
     * Description: <br>
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "getEntityById")
    public void getEntityById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        BusinessConfig businessConfig = planBusinessConfigService.getBusinessConfig(id);

        String json = JSON.toJSONString(businessConfig);

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
     * Description: <br>
     * 1、跳转导入页面<br>
     * 
     * @return
     * @see
     */
    @RequestMapping(params = "goImport")
    public ModelAndView goImport(HttpServletRequest req) {
        String configType = req.getParameter("paramType");
        req.setAttribute("configType", configType);
        String configName = ConfigTypeConstants.getName(configType);
        req.setAttribute("configName", configName);
        return new ModelAndView("com/glaway/ids/pm/config/businessConfig-import");
    }

    /**
     * Description: <br>
     * 1、导入数据<br>
     * 
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "doImportExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doImportExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String configType = request.getParameter("configType");
        FeignJson fj = planBusinessConfigService.getConfigTypeName(configType);
        String typeName = String.valueOf(fj.getObj());
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String res = "";

        try {
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                // 存储导入的数据
                List<BusinessConfig> dataTempList = new ArrayList<BusinessConfig>();
                // 存储错误信息
                Map<String, String> errorMsgMap = new HashMap<String, String>();

                MultipartFile file = entity.getValue();// 获取上传文件对象
                InputStream is = file.getInputStream();
                HSSFWorkbook book = new HSSFWorkbook(is);
                HSSFSheet sheet = book.getSheetAt(0);
                List<String> headers = null;
                if ("PHARSE".equals(configType)) {
                    headers = Arrays.asList(new String[] {
                        typeName + I18nUtil.getValue("com.glaway.ids.common.lable.code"),
                        typeName + I18nUtil.getValue("com.glaway.ids.common.lable.name")});
                }
                else {
                    headers = Arrays.asList(new String[] {typeName
                                                          + I18nUtil.getValue("com.glaway.ids.common.lable.name")});
                }

                if (!POIExcelUtil.doJudgeImport(sheet, headers)) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.errorTemplate"));
                    j.setSuccess(false);
                    log.error(typeName
                              + "："
                              + I18nUtil.getValue("com.glaway.ids.common.batchImportFailure.errorTemplate"));

                    // 组装数据传到前台
                    // ExcelUtil.addErrorMsg(2, "模板有问题，请重新下载模板;", errorMsgMap);
                    // Map<String, Object> dataAndErrorMap = new HashMap<String, Object>();
                    // dataAndErrorMap.put("dataTempList", dataTempList);
                    // dataAndErrorMap.put("errorMsgMap", errorMsgMap);
                    // j.setObj(dataAndErrorMap);

                    book.close();
                    is.close();

                    return j;
                }

                res = doGetImportDataList(sheet, configType, dataTempList, errorMsgMap); // 对导入数据进行处理
                if (CommonConfigConstants.DATA_IS_NULL.equals(res)) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importDataIsNull"));
                    j.setSuccess(false);
                    log.error(typeName + "："
                              + I18nUtil.getValue("com.glaway.ids.common.importDataIsNull"));
                    book.close();
                    is.close();
                    return j;
                }
                else if (0 < errorMsgMap.size()) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.invalidData"));
                    j.setSuccess(false);
                    log.error(typeName
                              + "："
                              + I18nUtil.getValue("com.glaway.ids.common.batchImportFailure.invalidData"));

                    // 组装数据传到前台
                    Map<String, Object> dataAndErrorMap = new HashMap<String, Object>();
                    dataAndErrorMap.put("dataTempList", dataTempList);
                    dataAndErrorMap.put("errorMsgMap", errorMsgMap);
                    j.setObj(dataAndErrorMap);

                    book.close();
                    is.close();

                    return j;
                }
                book.close();
                is.close();
            }
        }
        catch (IOException e) {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.notStream"));
            j.setSuccess(false);
            log.error(typeName + "："
                      + I18nUtil.getValue("com.glaway.ids.common.batchImportFailure.notStream"));
            return j;
        }
        catch (OfficeXmlFileException e) {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.errorTemplate"));
            j.setSuccess(false);
            return j;
        }
        catch (Exception e) {
            if (e.getMessage().contains("NEXTVAL exceeds MAXVALUE")) {
                j.setMsg(I18nUtil.getValue("com.glaway.ids.common.config.configNoExceedsMaxvalue"));
            }
            j.setSuccess(false);
            log.error(typeName + "："
                      + I18nUtil.getValue("com.glaway.ids.common.config.configNoExceedsMaxvalue"));
            return j;
        }
        Object[] arguments = new String[] {res};
        j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importSuccessForAdd", arguments));
        j.setSuccess(true);
        sysLog.info(typeName + "：" + I18nUtil.getValue("com.glaway.ids.common.batchImportSuccess"));
        return j;
    }

    /**
     * Description: <br>
     * 1、从导入的文件对数据进行处理<br>
     * 
     * @return
     * @see
     */
    private String doGetImportDataList(HSSFSheet sheet, String configType,
                                       List<BusinessConfig> dataTempList,
                                       Map<String, String> errorMsgMap) {
        List<String> dataFromExcel = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        List<String> nos = new ArrayList<String>();
        List<String> configComment = new ArrayList<String>();
        FeignJson fj = planBusinessConfigService.getConfigTypeName(configType);
        String typeName = String.valueOf(fj.getObj());
        List<BusinessConfig> list = new ArrayList<BusinessConfig>();
        list = planBusinessConfigService.getBusinessConfigListByConfigType(configType);
        Map<String, String> noMap = new HashMap<String, String>();
        Map<String, String> nameMap = new HashMap<String, String>();
        for (BusinessConfig bc : list) {
            noMap.put(bc.getNo(), bc.getName());
            nameMap.put(bc.getName(), bc.getNo());
        }
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row r = it.next();
            int rowNum = r.getRowNum();
            HSSFRow row = sheet.getRow(rowNum);
            if (rowNum >= 3) {
                if ("PHARSE".equals(configType)) {
                    HSSFCell bcNoCell = row.getCell(0);
                    HSSFCell bcNameCell = row.getCell(1);
                    HSSFCell bcConfigCommentCell = row.getCell(2);
                    String bcNo = POIExcelUtil.getCellValue(bcNoCell);
                    String bcName = POIExcelUtil.getCellValue(bcNameCell);
                    String bcConfigComment = POIExcelUtil.getCellValue(bcConfigCommentCell);

                    String strForBc = "";
                    JudgeRangeParam bcNoParam = new JudgeRangeParam();
                    bcNoParam.setKey(bcNo.trim());
                    bcNoParam.setMinLength(1);
                    bcNoParam.setMaxLength(30);
                    bcNoParam.setRow(rowNum);
                    bcNoParam.setValue(typeName
                                       + I18nUtil.getValue("com.glaway.ids.common.lable.code"));
                    POIExcelUtil.doJudgeRange(bcNoParam, errorMsgMap);

                    JudgeRangeParam bcNameParam = new JudgeRangeParam();
                    bcNameParam.setKey(bcName.trim());
                    bcNameParam.setMinLength(1);
                    bcNameParam.setMaxLength(30);
                    bcNameParam.setRow(rowNum);
                    bcNameParam.setValue(typeName
                                         + I18nUtil.getValue("com.glaway.ids.common.lable.name"));
                    POIExcelUtil.doJudgeRange(bcNameParam, errorMsgMap);

                    JudgeRangeParam bcConfigCommentParam = new JudgeRangeParam();
                    bcConfigCommentParam.setKey(bcConfigComment.trim());
                    bcConfigCommentParam.setMinLength(1);
                    bcConfigCommentParam.setMaxLength(200);
                    bcConfigCommentParam.setRow(rowNum);
                    bcConfigCommentParam.setValue(I18nUtil.getValue("com.glaway.ids.common.lable.remark"));
                    POIExcelUtil.doJudgeRange(bcConfigCommentParam, errorMsgMap);

                    names.add(bcName);
                    nos.add(bcNo);
                    configComment.add(bcConfigComment);

                    if ("".equals(bcNo)) {
                        POIExcelUtil.addErrorMsg(
                            rowNum,
                            typeName + I18nUtil.getValue("com.glaway.ids.common.config.configCodeIsNull"),
                            errorMsgMap);
                    }
                    if ("".equals(bcName)) {
                        POIExcelUtil.addErrorMsg(
                            rowNum,
                            typeName + I18nUtil.getValue("com.glaway.ids.common.config.configNameIsNull"),
                            errorMsgMap);
                    }
                    if (!("".equals(bcNo) && "".equals(bcName))) {
                        strForBc = bcNo + "," + bcName + "," + bcConfigComment + "," + configType;
                        dataFromExcel.add(strForBc);

                        // 需要做性能优化
                        /*
                         * boolean hasSameCode=standardProblemService.hasSameCode(bcNo);
                         * boolean hasSameName=standardProblemService.hasSameName(bcName);
                         */
                        if (noMap.get(bcNo) != null) {
                            POIExcelUtil.addErrorMsg(
                                rowNum,
                                typeName
                                    + I18nUtil.getValue("com.glaway.ids.common.config.configCodeIsExits"),
                                errorMsgMap);
                        }
                        if (nameMap.get(bcName) != null) {
                            POIExcelUtil.addErrorMsg(
                                rowNum,
                                typeName
                                    + I18nUtil.getValue("com.glaway.ids.common.config.configNameIsExits"),
                                errorMsgMap);
                        }
                    }

                    // 判断导入的列表中是否有相同的数据存在
                    Iterator<Row> it1 = sheet.iterator();
                    while (it1.hasNext()) {
                        Row r1 = it1.next();
                        int rowNum1 = r1.getRowNum();
                        int realRowNum1 = rowNum1 + 1;
                        HSSFRow row1 = sheet.getRow(rowNum1);
                        if (rowNum1 >= 3) {
                            HSSFCell bcNoCell1 = row1.getCell(0);
                            HSSFCell bcNameCell1 = row1.getCell(1);
                            String bcNo1 = POIExcelUtil.getCellValue(bcNoCell1);
                            String bcName1 = POIExcelUtil.getCellValue(bcNameCell1);
                            Object[] arguments = new String[] {realRowNum1 + ""};
                            if (rowNum > rowNum1 && bcNo1.equals(bcNo) && !bcNo1.equals("")) {
                                POIExcelUtil.addErrorMsg(rowNum, I18nUtil.getValue(
                                    "com.glaway.ids.common.config.configCodeIsExitsLine", arguments),
                                    errorMsgMap);
                            }
                            if (rowNum > rowNum1 && bcName1.equals(bcName) && !bcName.equals("")) {
                                POIExcelUtil.addErrorMsg(rowNum, I18nUtil.getValue(
                                    "com.glaway.ids.common.config.configNameIsExitsLine", arguments),
                                    errorMsgMap);
                            }
                        }
                    }

                    BusinessConfig bc = new BusinessConfig();
                    bc.setNo(bcNo);
                    bc.setName(bcName);
                    bc.setConfigComment(bcConfigComment);
                    bc.setStopFlag("1");
                    dataTempList.add(bc);
                }
                else {
                    HSSFCell bcNameCell = row.getCell(0);
                    HSSFCell bcConfigCommentCell = row.getCell(1);
                    String bcName = POIExcelUtil.getCellValue(bcNameCell);
                    String bcConfigComment = POIExcelUtil.getCellValue(bcConfigCommentCell);

                    String strForBc = "";

                    JudgeRangeParam bcNameParam = new JudgeRangeParam();
                    bcNameParam.setKey(bcName.trim());
                    bcNameParam.setMinLength(1);
                    bcNameParam.setMaxLength(30);
                    bcNameParam.setRow(rowNum);
                    bcNameParam.setValue(typeName
                                         + I18nUtil.getValue("com.glaway.ids.common.lable.name"));
                    POIExcelUtil.doJudgeRange(bcNameParam, errorMsgMap);

                    JudgeRangeParam bcConfigCommentParam = new JudgeRangeParam();
                    bcConfigCommentParam.setKey(bcConfigComment.trim());
                    bcConfigCommentParam.setMinLength(1);
                    bcConfigCommentParam.setMaxLength(200);
                    bcConfigCommentParam.setRow(rowNum);
                    bcConfigCommentParam.setValue(I18nUtil.getValue("com.glaway.ids.common.lable.remark"));
                    POIExcelUtil.doJudgeRange(bcConfigCommentParam, errorMsgMap);

                    names.add(bcName);
                    configComment.add(bcConfigComment);
                    if ("".equals(bcName)) {
                        POIExcelUtil.addErrorMsg(
                            rowNum,
                            typeName + I18nUtil.getValue("com.glaway.ids.common.config.configNameIsNull"),
                            errorMsgMap);
                    }
                    if (!"".equals(bcName)) {
                        strForBc = bcName + "," + bcConfigComment + "," + configType;
                        dataFromExcel.add(strForBc);
                        if (nameMap.get(bcName) != null) {
                            POIExcelUtil.addErrorMsg(
                                rowNum,
                                typeName
                                    + I18nUtil.getValue("com.glaway.ids.common.config.configNameIsExits"),
                                errorMsgMap);
                        }
                    }

                    // 判断导入的列表中是否有相同的数据存在
                    Iterator<Row> it1 = sheet.iterator();
                    while (it1.hasNext()) {
                        Row r1 = it1.next();
                        int rowNum1 = r1.getRowNum();
                        int realRowNum1 = rowNum1 + 1;
                        HSSFRow row1 = sheet.getRow(rowNum1);
                        if (rowNum1 >= 3) {
                            HSSFCell bcNameCell1 = row1.getCell(0);
                            String bcName1 = POIExcelUtil.getCellValue(bcNameCell1);
                            Object[] arguments = new String[] {realRowNum1 + ""};
                            if (rowNum > rowNum1 && bcName1.equals(bcName) && !bcName.equals("")) {
                                POIExcelUtil.addErrorMsg(rowNum, I18nUtil.getValue(
                                    "com.glaway.ids.common.config.configNameIsExitsLine", arguments),
                                    errorMsgMap);
                            }
                        }
                    }

                    BusinessConfig bc = new BusinessConfig();
                    bc.setName(bcName);
                    bc.setConfigComment(bcConfigComment);
                    bc.setStopFlag("1");
                    dataTempList.add(bc);
                }
            }

        }

        String res = "";
        if (0 == dataTempList.size()) {
            res = CommonConfigConstants.DATA_IS_NULL;
        }
        else if (0 == errorMsgMap.size()) {
            FeignJson feignJson = planBusinessConfigService.doData(dataFromExcel, configType,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            if (feignJson.isSuccess()) {
                res = feignJson.getObj() == null ? CommonConfigConstants.DATA_IS_NULL : feignJson.getObj().toString();
            }
        }
        return res;
    }

    /**
     * Description: <br>
     * 1、下载错误报告<br>
     * 
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "downErrorReport")
    public void downErrorReport(HttpServletRequest request, HttpServletResponse response)
        throws FdException, Exception {
        String configType = request.getParameter("paramType");
        String objStr = request.getParameter("dataListAndErrorMap");
        Map<String, Object> jsonMap = (Map<String, Object>)JSON.parse(objStr);

        List<BusinessConfig> dataTempList = (List<BusinessConfig>)jsonMap.get("dataTempList");
        Map<String, String> errorMsgMap = (Map<String, String>)jsonMap.get("errorMsgMap");

        fileDownLoad(response, dataTempList, errorMsgMap, configType, "error");
    }

    /**
     * 导出数据或下载模板
     * 
     * @see
     */
    @RequestMapping(params = "doDownloadBusinessConfig")
    public void doDownloadBusinessConfig(HttpServletRequest req, HttpServletResponse response)
        throws FdException, Exception {
        String configType = req.getParameter("configType");
        String downloadType = req.getParameter("downloadType");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String stopFlag = req.getParameter("stopFlag");
        List<BusinessConfig> businessConfigList = new ArrayList<BusinessConfig>();
        if (StringUtil.isNotEmpty(name)) {
            name = URLDecoder.decode(name, "UTF-8");
        }
        if (StringUtil.isNotEmpty(no)) {
            no = URLDecoder.decode(no, "UTF-8");
        }
        if (StringUtil.isNotEmpty(stopFlag)) {
            stopFlag = URLDecoder.decode(stopFlag, "UTF-8");
        }
        if (!"0".equals(downloadType)) {
            // BusinessConfig bc = new BusinessConfig();
            // bc.setNo(no);
            // bc.setName(name);
            // bc.setStopFlag(stopFlag);
            // bc.setConfigType(configType);

            List<ConditionVO> conditionList = new ArrayList<ConditionVO>();

            ConditionVO vo = new ConditionVO();
            vo.setCondition("eq");
            vo.setKey("BusinessConfig.configType");
            vo.setValue(configType);
            conditionList.add(vo);

            ConditionVO noVo = new ConditionVO();
            noVo.setCondition("like");
            noVo.setKey("BusinessConfig.no");
            noVo.setValue(no);
            conditionList.add(noVo);

            ConditionVO nameVo = new ConditionVO();
            nameVo.setCondition("like");
            nameVo.setKey("BusinessConfig.name");
            nameVo.setValue(name);
            conditionList.add(nameVo);

            ConditionVO stopFlagVo = new ConditionVO();
            stopFlagVo.setCondition("like");
            stopFlagVo.setKey("BusinessConfig.stopFlag");
            stopFlagVo.setValue(stopFlag);
            conditionList.add(stopFlagVo);

            PageList pageList = planBusinessConfigService.queryEntity(conditionList, false);
            businessConfigList = pageList.getResultList();
            // businessConfigList = this.planBusinessConfigService.searchBusinessConfigs(bc);
            fileDownLoad(response, businessConfigList, null, configType, "true");
        }
        else { // 下载模板
            fileDownLoad(response, null, null, configType, "temp");
        }
    }

    /**
     * 下载项目excel模板
     * 
     * @return
     */
    private void fileDownLoad(HttpServletResponse response, List<BusinessConfig> dataList,
                              Map<String, String> errorMsgMap, String configType, String isExport) {

        FeignJson fj = planBusinessConfigService.getConfigTypeName(configType);

        String typeName = String.valueOf(fj.getObj());

        String excelName = POIExcelUtil.createExcelName(isExport, typeName, errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        String attention = I18nUtil.getValue("com.glaway.ids.common.config.configImportant");
        Object[] arguments = new String[] {typeName, typeName};
        if ("PHARSE".equals(configType)) {
            attention = attention
                        + I18nUtil.getValue("com.glaway.ids.common.config.configCheckForPharse",
                            arguments);
        }
        else {
            attention = attention
                        + I18nUtil.getValue("com.glaway.ids.common.config.configCheck", arguments);
        }
        List<String> columns = null;
        List<String> requiredHeaders = null;
        if ("PHARSE".equals(configType)) {
            columns = Arrays.asList(new String[] {
                "no:" + typeName + I18nUtil.getValue("com.glaway.ids.common.lable.code"),
                "name:" + typeName + I18nUtil.getValue("com.glaway.ids.common.lable.name"),
                "configComment:" + I18nUtil.getValue("com.glaway.ids.common.lable.remark") + " "});

            requiredHeaders = Arrays.asList(new String[] {
                typeName + I18nUtil.getValue("com.glaway.ids.common.lable.code"),
                typeName + I18nUtil.getValue("com.glaway.ids.common.lable.name")});

        }
        else {
            columns = Arrays.asList(new String[] {
                "name:" + typeName + I18nUtil.getValue("com.glaway.ids.common.lable.name"),
                "configComment:" + I18nUtil.getValue("com.glaway.ids.common.lable.remark") + " "});

            requiredHeaders = Arrays.asList(new String[] {typeName
                                                          + I18nUtil.getValue("com.glaway.ids.common.lable.name")});

        }
        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle(typeName);
        excelVo.setAttention(attention);
        excelVo.setColumns(columns);
        excelVo.setRequiredHeaders(requiredHeaders);
        excelVo.setDataList(dataList);
        excelVo.setValidationDataMap(validationDataMap);
        excelVo.setErrorMsgMap(errorMsgMap);

        HSSFWorkbook workbook = POIExcelUtil.getInstance().exportExcel(bExport, excelVo,
            "yyyy-MM-dd");

        POIExcelUtil.responseReportWithName(response, workbook, excelName);
    }


}
