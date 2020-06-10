package com.glaway.ids.planGeneral.plantabtemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataRollBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.DataSourceObjectFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.DataSourceObjectServiceI;
import com.glaway.ids.planGeneral.plantabtemplate.utils.DatagridStrUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @Title: ServiceImpl
 * @Description: 数据源对象信息管理ServiceImpl
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Service("dataSourceObjectServiceImpl")
public class DataSourceObjectServiceImpl implements DataSourceObjectServiceI {

    //数据源对象信息管理Feign
    @Autowired
    private DataSourceObjectFeign dataSourceObjectFeign;

    /**
     * 功能描述：根据查询条件展示列表(数据过滤)
     * @param conditionList
     */
    @Override
    public void searchDatagrid(List<ConditionVO> conditionList, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        for (ConditionVO conditionVO : conditionList) {
            params.put("sort", conditionVO.getSort());
            params.put(conditionVO.getKey(), conditionVO.getValue());
        }
        PageList pageList = dataSourceObjectFeign.searchDatagrid(params);
        String datagridStr = DatagridStrUtils.getDatagridStr(pageList,true);
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 功能描述：保存数据
     * @param response
     * @param dto
     */
    @Override
    public AjaxJson doSave(DataSourceObjectDto dto, HttpServletResponse response) {
        TSUserDto userDto = UserUtil.getCurrentUser();
        TSDepartDto departDto = userDto.getTSDepart();
        //判断是新增还是修改
        if (StringUtils.isEmpty(dto.getId())){
            dto.setCreateBy(userDto.getUserId());
            dto.setCreateFullName(userDto.getRealName());
            dto.setCreateName(userDto.getUserName());
            dto.setCreateOrgId(departDto.getDeptCode());
            dto.setCreateTime(new Date());
        }else{
            dto.setUpdateBy(userDto.getUserId());
            dto.setUpdateFullName(userDto.getRealName());
            dto.setUpdateName(userDto.getUserName());
            dto.setUpdateOrgId(departDto.getDeptCode());
            dto.setUpdateTime(new Date());
        }
        dto = dataSourceObjectFeign.saveOrUpdate(dto);
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setObj(dto);
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }

    /**
     * 功能描述：删除数据
     * @param id
     */
    @Override
    public AjaxJson doDelete(String id) {
        AjaxJson ajaxJson = new AjaxJson();
        FeignJson feignJson = dataSourceObjectFeign.doDelete(id);
        if (feignJson.isSuccess()){
            ajaxJson.setMsg("数据删除成功");
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("数据删除失败");
        }
        return ajaxJson;
    }

    /**
     * 功能描述：根据页签模板ID查询所有数据对象集合
     * @param response
     * @param tabId
     */
    @Override
    public void getAllDataSourceObject(String tabId, HttpServletResponse response) {
        List<Map<String, String>> mapResponse = dataSourceObjectFeign.getAllDataSourceObject(tabId);
        TagUtil.ajaxResponse(response, JSON.toJSONString(mapResponse));
    }

    /**
     * 功能描述：保存所有信息
     * @param dataSourceObjectList
     * @param objectPropertyList
     */
    @Override
    public AjaxJson saveAllInfo(List<DataSourceObjectDto> dataSourceObjectList, List<ObjectPropertyInfoDto> objectPropertyList) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("dataSourceObjectList", dataSourceObjectList);
        requestMap.put("objectPropertyList", objectPropertyList);
        FeignJson feignJson = dataSourceObjectFeign.saveAllInfo(requestMap);
        if (feignJson.isSuccess()){
            ajaxJson.setMsg("数据保存成功");
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("数据保存失败");
        }
        return ajaxJson;
    }

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param response
     * @param tabId
     */
    @Override
    public void getAllDataByTabId(String tabId, HttpServletResponse response) {
        List<DataSourceObjectDto> allList = dataSourceObjectFeign.getAllDataByTabId(tabId);
        TagUtil.ajaxResponse(response, JSON.toJSON(allList).toString());
    }

    /**
     * 功能描述：数据回滚
     * @param dataSourceObjectList
     * @param objectPropertyList
     */
    @Override
    public AjaxJson dataRollBack(List<DataRollBack> dataSourceObjectList, List<String> objectPropertyList) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("dataSourceObjectList", dataSourceObjectList);
        requestMap.put("objectPropertyList", objectPropertyList);
        FeignJson feignJson = dataSourceObjectFeign.dataRollBack(requestMap);
        if (feignJson.isSuccess()){
            ajaxJson.setMsg("数据回滚成功");
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("数据回滚失败");
        }
        return ajaxJson;
    }

    @Override
    public AjaxJson updateOrReviseInfo(String tabId, List<DataSourceObjectDto> dataSourceObjectList, List<ObjectPropertyInfoDto> objectPropertyList) {
        for (DataSourceObjectDto dto: dataSourceObjectList) {
            dto.setTabId(tabId);
        }
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("dataSourceObjectList", dataSourceObjectList);
        requestMap.put("objectPropertyList", objectPropertyList);
        FeignJson feignJson = dataSourceObjectFeign.updateOrReviseInfo(requestMap);
        if (feignJson.isSuccess()){
            ajaxJson.setMsg("数据保存成功");
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("数据保存失败");
        }
        return ajaxJson;
    }
}
