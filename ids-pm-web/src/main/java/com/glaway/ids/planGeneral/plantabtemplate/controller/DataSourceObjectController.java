package com.glaway.ids.planGeneral.plantabtemplate.controller;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.util.RequestMapUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataRollBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.service.DataSourceObjectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Title: Controller
 * @Description: 计划通用化--数据源对象信息管理
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Controller
@RequestMapping("/dataSourceObjectController")
public class DataSourceObjectController {

    //数据源对象信息管理Service
    @Autowired
    private DataSourceObjectServiceI dataSourceObjectServiceImpl;

    /**
     * 功能描述：跳转实体对象查找页面
     * @return ModelAndView
     */
    @RequestMapping(params = "searchDataSourcePage")
    public ModelAndView searchDataSourcePage(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/searchDataSourcePage");
    }

    /**
     * 功能描述：根据查询条件展示列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        //获取查询条件
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        dataSourceObjectServiceImpl.searchDatagrid(conditionList, response);
    }

    /**
     * 功能描述：根据查询条件展示列表
     * @param response
     * @param dto
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(DataSourceObjectDto dto, HttpServletResponse response) {
        return dataSourceObjectServiceImpl.doSave(dto, response);
    }

    /**
     * 功能描述：删除数据
     * @param id
     */
    @RequestMapping(params = "doDelete")
    @ResponseBody
    public AjaxJson doDelete(String id){
        return dataSourceObjectServiceImpl.doDelete(id);
    }

    /**
     * 功能描述：根据页签模板Id查询所有数据对象(id,objectPath)集合
     * @param response
     * @param tabId
     */
    @RequestMapping(params = "getAllDataSourceObject")
    public void getAllDataSourceObject(String tabId, HttpServletResponse response) {
        dataSourceObjectServiceImpl.getAllDataSourceObject(tabId, response);
    }

    /**
     * 功能描述：保存所有信息
     * @param request
     */
    @RequestMapping(params = "saveAllInfo")
    @ResponseBody
    public AjaxJson saveAllInfo(HttpServletRequest request){
        String dataSourceObjectListStr = request.getParameter("dataSourceObjectList");
        String objectPropertyListStr = request.getParameter("objectPropertyList");
        List<DataSourceObjectDto> dataSourceObjectList = JSON.parseArray(dataSourceObjectListStr, DataSourceObjectDto.class);
        List<ObjectPropertyInfoDto> objectPropertyList = JSON.parseArray(objectPropertyListStr, ObjectPropertyInfoDto.class);
        return dataSourceObjectServiceImpl.saveAllInfo(dataSourceObjectList, objectPropertyList);
    }

    /**
     * 功能描述：根据页签模板Id查询所有数据
     * @param response
     * @param tabId
     */
    @RequestMapping(params = "getAllDataByTabId")
    public void getAllDataByTabId(String tabId, HttpServletResponse response) {
        dataSourceObjectServiceImpl.getAllDataByTabId(tabId, response);
    }

    /**
     * 功能描述：保存所有信息
     * @param request
     */
    @RequestMapping(params = "dataRollBack")
    @ResponseBody
    public AjaxJson dataRollBack(HttpServletRequest request){
        String dataSourceObjectListStr = request.getParameter("dataSourceObjectList");
        String objectPropertyListStr = request.getParameter("objectPropertyList");
        List<DataRollBack> dataSourceObjectList = JSON.parseArray(dataSourceObjectListStr, DataRollBack.class);
        List<String> objectPropertyList = JSON.parseArray(objectPropertyListStr, String.class);
        return dataSourceObjectServiceImpl.dataRollBack(dataSourceObjectList, objectPropertyList);
    }

    /**
     * 功能描述：保存所有信息
     * @param request
     */
    @RequestMapping(params = "updateOrReviseInfo")
    @ResponseBody
    public AjaxJson updateOrReviseInfo(HttpServletRequest request){
        String mainId = request.getParameter("tabId");
        String dataSourceObjectListStr = request.getParameter("dataSourceObjectList");
        String objectPropertyListStr = request.getParameter("objectPropertyList");
        List<DataSourceObjectDto> dataSourceObjectList = JSON.parseArray(dataSourceObjectListStr, DataSourceObjectDto.class);
        List<ObjectPropertyInfoDto> objectPropertyList = JSON.parseArray(objectPropertyListStr, ObjectPropertyInfoDto.class);
        return dataSourceObjectServiceImpl.updateOrReviseInfo(mainId,dataSourceObjectList, objectPropertyList);
    }
}
