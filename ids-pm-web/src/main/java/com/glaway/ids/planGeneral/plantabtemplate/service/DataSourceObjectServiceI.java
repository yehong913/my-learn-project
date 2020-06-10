package com.glaway.ids.planGeneral.plantabtemplate.service;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataRollBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Title: ServiceI
 * @Description: 数据源对象信息管理ServiceI
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
public interface DataSourceObjectServiceI {

    /**
     * 功能描述：根据查询条件展示列表(数据过滤)
     * @param conditionList
     */
    void searchDatagrid(List<ConditionVO> conditionList, HttpServletResponse response);

    /**
     * 功能描述：保存数据
     * @param response
     * @param dto
     */
    AjaxJson doSave(DataSourceObjectDto dto, HttpServletResponse response);

    /**
     * 功能描述：删除数据
     * @param id
     */
    AjaxJson doDelete(String id);

    /**
     * 功能描述：根据页签模板ID查询所有数据对象集合
     * @param response
     * @param tabId
     */
    void getAllDataSourceObject(String tabId, HttpServletResponse response);

    /**
     * 功能描述：保存所有信息
     * @param dataSourceObjectList
     * @param objectPropertyList
     */
    AjaxJson saveAllInfo(List<DataSourceObjectDto> dataSourceObjectList, List<ObjectPropertyInfoDto> objectPropertyList);

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param response
     * @param tabId
     */
    void getAllDataByTabId(String tabId, HttpServletResponse response);

    /**
     * 功能描述：数据回滚
     * @param dataSourceObjectList
     * @param objectPropertyList
     */
    AjaxJson dataRollBack(List<DataRollBack> dataSourceObjectList, List<String> objectPropertyList);

    /**
     * 功能描述：保存所有信息
     * @param dataSourceObjectList
     * @param objectPropertyList
     */
    AjaxJson updateOrReviseInfo(String tabId,List<DataSourceObjectDto> dataSourceObjectList, List<ObjectPropertyInfoDto> objectPropertyList);
}
